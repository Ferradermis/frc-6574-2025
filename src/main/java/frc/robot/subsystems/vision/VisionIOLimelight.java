// Copyright 2021-2025 FRC 6328
// http://github.com/Mechanical-Advantage
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// version 3 as published by the Free Software Foundation or
// available in the root directory of this project.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.

package frc.robot.subsystems.vision;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.DoubleArrayPublisher;
import edu.wpi.first.networktables.DoubleArraySubscriber;
import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.networktables.IntegerArrayPublisher;
import edu.wpi.first.networktables.IntegerSubscriber;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotController;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

/** IO implementation for real Limelight hardware. */
public class VisionIOLimelight implements VisionIO {
  private final Supplier<Rotation2d> rotationSupplier;
  private final DoubleArrayPublisher orientationPublisher;
  private final IntegerArrayPublisher tagFilterPublisher;

  private final DoubleSubscriber latencySubscriber;
  private final DoubleSubscriber txSubscriber;
  private final DoubleSubscriber tySubscriber;
  private final IntegerSubscriber idSubscriber;
  private final DoubleArraySubscriber megatag1Subscriber;
  private final DoubleArraySubscriber megatag2Subscriber;

  private long[] tagFilter;
  private String cameraName;

  /**
   * Creates a new VisionIOLimelight.
   *
   * @param name The configured name of the Limelight.
   * @param rotationSupplier Supplier for the current estimated rotation, used for MegaTag 2.
   */
  public VisionIOLimelight(String name, Supplier<Rotation2d> rotationSupplier) {
    cameraName = name;
    var table = NetworkTableInstance.getDefault().getTable(name);
    this.rotationSupplier = rotationSupplier;
    orientationPublisher = table.getDoubleArrayTopic("robot_orientation_set").publish();
    tagFilterPublisher = table.getIntegerArrayTopic("fiducial_id_filters_set").publish();
    latencySubscriber = table.getDoubleTopic("tl").subscribe(0.0);
    txSubscriber = table.getDoubleTopic("tx").subscribe(0.0);
    tySubscriber = table.getDoubleTopic("ty").subscribe(0.0);
    idSubscriber = table.getIntegerTopic("tid").subscribe(0);
    megatag1Subscriber = table.getDoubleArrayTopic("botpose_wpiblue").subscribe(new double[] {});
    megatag2Subscriber =
        table.getDoubleArrayTopic("botpose_orb_wpiblue").subscribe(new double[] {});

    setDefaultTagFilter();
  }

  @Override
  public void updateInputs(VisionIOInputs inputs) {
    inputs.cameraName = this.cameraName;
    // Update connection status based on whether an update has been seen in the last 250ms
    inputs.connected =
        ((RobotController.getFPGATime() - latencySubscriber.getLastChange()) / 1000) < 250;

    // Update target observation
    inputs.latestTargetObservation =
        new TargetObservation(
            Rotation2d.fromDegrees(txSubscriber.get()), Rotation2d.fromDegrees(tySubscriber.get()),Long.valueOf(idSubscriber.get()).intValue());

    // Update orientation for MegaTag 2
    orientationPublisher.accept(
        new double[] {rotationSupplier.get().getDegrees(), 0.0, 0.0, 0.0, 0.0, 0.0});
    tagFilterPublisher.accept(tagFilter);
    NetworkTableInstance.getDefault()
        .flush(); // Increases network traffic but recommended by Limelight

    // Read new pose observations from NetworkTables
    Set<Integer> tagIds = new HashSet<>();
    List<PoseObservation> poseObservations = new LinkedList<>();
    // Added booleans to control which pases we will use for debugging purpose and may want to
    // control in code
    boolean useMega1 = DriverStation.isDisabled();
    boolean useBoth = false;
    int closestTag = 0;
    double closedDist = 9999.0;
    if (useMega1 || useBoth)
      for (var rawSample : megatag1Subscriber.readQueue()) {
        if (rawSample.value.length == 0) continue;
        for (int i = 11; i < rawSample.value.length; i += 7) {
          double tagDist = rawSample.value[i+5];
          int tag = (int) rawSample.value[i];
          tagIds.add(tag);
          if(tagDist < closedDist) {
              closedDist = tagDist;
              closestTag = tag;
          }
        }
        poseObservations.add(
            new PoseObservation(
                cameraName,

                // Timestamp, based on server timestamp of publish and latency
                rawSample.timestamp * 1.0e-6 - rawSample.value[6] * 1.0e-3,

                // 3D pose estimate
                parsePose(rawSample.value),

                // Ambiguity, using only the first tag because ambiguity isn't applicable for
                // multitag
                rawSample.value.length >= 18 ? rawSample.value[17] : 0.0,

                // Tag count
                (int) rawSample.value[7],

                // Average tag distance
                rawSample.value[9],

                // Observation type
                PoseObservationType.MEGATAG_1,
                closestTag,
                closedDist));
      }
      closestTag = 0;
      closedDist = 9999.0;
    if (!useMega1 || useBoth)
      for (var rawSample : megatag2Subscriber.readQueue()) {
        if (rawSample.value.length == 0) continue;
        for (int i = 11; i < rawSample.value.length; i += 7) {
          double tagDist = rawSample.value[i+5];
          int tag = (int) rawSample.value[i];
          tagIds.add(tag);
          if(tagDist < closedDist) {
              closedDist = tagDist;
              closestTag = tag;
          }
        }
        poseObservations.add(
            new PoseObservation(
                cameraName,

                // Timestamp, based on server timestamp of publish and latency
                rawSample.timestamp * 1.0e-6 - rawSample.value[6] * 1.0e-3,

                // 3D pose estimate
                parsePose(rawSample.value),

                // Ambiguity, zeroed because the pose is already disambiguated
                0.0,

                // Tag count
                (int) rawSample.value[7],

                // Average tag distance
                rawSample.value[9],

                // Observation type
                PoseObservationType.MEGATAG_2,
                closestTag,
                closedDist));
      }

    // Save pose observations to inputs object
    inputs.poseObservations = new PoseObservation[poseObservations.size()];
    for (int i = 0; i < poseObservations.size(); i++) {
      inputs.poseObservations[i] = poseObservations.get(i);
    }

    // Save tag IDs to inputs objects
    inputs.tagIds = new int[tagIds.size()];
    int i = 0;
    for (int id : tagIds) {
      inputs.tagIds[i++] = id;
    }
  }

  /** Parses the 3D pose from a Limelight botpose array. */
  private static Pose3d parsePose(double[] rawLLArray) {
    return new Pose3d(
        rawLLArray[0],
        rawLLArray[1],
        rawLLArray[2],
        new Rotation3d(
            Units.degreesToRadians(rawLLArray[3]),
            Units.degreesToRadians(rawLLArray[4]),
            Units.degreesToRadians(rawLLArray[5])));
  }

  @Override
  public void setTagIdFilter(int[] filter) {
    this.tagFilter = Arrays.stream(filter).mapToLong((i) -> Integer.toUnsignedLong(i)).toArray();
  }
}
