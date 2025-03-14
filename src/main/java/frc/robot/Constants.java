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

package frc.robot;

import edu.wpi.first.wpilibj.RobotBase;

/**
 * This class defines the runtime mode used by AdvantageKit. The mode is always "real" when running
 * on a roboRIO. Change the value of "simMode" to switch between "sim" (physics sim) and "replay"
 * (log replay from a file).
 */
public final class Constants {
  public static final Mode simMode = Mode.SIM;
  public static final Mode currentMode = RobotBase.isReal() ? Mode.REAL : simMode;
  public static final boolean tuningMode = true;

  public static enum Mode {
    /** Running on a real robot. */
    REAL,

    /** Running a physics simulator. */
    SIM,

    /** Replaying from a log file. */
    REPLAY
  }

  public static final class CANConstants {
    public static final int CLIMBER_ID = 15;
    public static final int ELEVATOR_RIGHT_ID = 16;
    public static final int ELEVATOR_LEFT_ID = 17;
    public static final int PIVOT_ID = 18;
    public static final int PIVOT_CANCODER_ID = 19;
    public static final int TURRET_ID = 20;
    public static final int END_EFFECTOR_ID = 21;
    public static final int LOCKING_SERVO_ID = 1;
  }

  public static final class PositionConstants {
    // Elevator Constants
    public static final double LEVEL_FOUR_ELEVATOR_HEIGHT = 20.85 * 39.37;
    public static final double LEVEL_THREE_ELEVATOR_HEIGHT = 10.25 * 39.37;
    public static final double LEVEL_TWO_ELEVATOR_HEIGHT = 3.75 * 39.37;
    public static final double LEVEL_ONE_ELEVATOR_HEIGHT = 0.0; 
    public static final double ALGAE_ONE_ELEVATOR_HEIGHT = 0.0; // TODO: Find correct value, defaulted at 0 for now :)
    public static final double ALGAE_TWO_ELEVATOR_HEIGHT = 0.0; // TODO: Find correct value, defaulted at 0 for now :)
    public static final double GROUND_ELEVATOR_HEIGHT = 0.0; // TODO: Find correct value, defaulted at 0 for now :)
    public static final double CHUTE_ELEVATOR_HEIGHT = 3.75 * 39.37;
    public static final double HOME_ELEVATOR_HEIGHT = 0.0;

    // Pivot Constants
    public static final double LEVEL_FOUR_PIVOT_ANGLE = 0.094;
    public static final double LEVEL_THREE_PIVOT_ANGLE = 0.097;
    public static final double LEVEL_TWO_PIVOT_ANGLE = 0.108;
    public static final double LEVEL_ONE_PIVOT_ANGLE = 0.087;
    public static final double ALGAE_ONE_PIVOT_ANGLE = 0.0; // TODO: Find correct value, defaulted at 0 for now :)
    public static final double ALGAE_TWO_PIVOT_ANGLE = 0.0; // TODO: Find correct value, defaulted at 0 for now :)
    public static final double GROUND_PIVOT_ANGLE = 0.0; // TODO: Find correct value, defaulted at 0 for now :)
    public static final double CHUTE_PIVOT_ANGLE = 0.155;
    public static final double HOME_PIVOT_ANGLE = 0.182;

    // Turret Constants
    public static final double VERTICAL_TURRET_ANGLE = 2.225;
    public static final double HORIZONTAL_TURRET_ANGLE = 0.0;

    // Climber Constants
    public static final double CLIMBER_UP_ANGLE = 0.0; // TODO: Find correct value, defaulted at 0 for now :)
    public static final double CLIMBER_DOWN_ANGLE = 0.0; // TODO: Find correct value, defaulted at 0 for now :)

  }
}
