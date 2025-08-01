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

  public static final class ButtonConstants {
    // Controller Ports
    public static final int DRIVER_CONTROLLER_PORT = 0;
    public static final int OPERATOR_CONTROLLER_PORT = 1;
    public static final int BUTTON_BOARD_PORT = 1;

    // Button Board Buttons
    public static final int REEF_BUTTON_1 = 1;
    public static final int REEF_BUTTON_2 = 2;
    public static final int REEF_BUTTON_3 = 3;
    public static final int REEF_BUTTON_4 = 4;
    public static final int REEF_BUTTON_5 = 5;
    public static final int REEF_BUTTON_6 = 6;
    public static final int REEF_BUTTON_7 = 7;
    public static final int REEF_BUTTON_8 = 8;
    public static final int REEF_BUTTON_9 = 9;
    public static final int REEF_BUTTON_10 = 10;
    public static final int REEF_BUTTON_11 = 11;
    public static final int REEF_BUTTON_12 = 12;

    public static final int L4_BUTTON = 13;
    public static final int L3_BUTTON = 14;
    public static final int L2_BUTTON = 15;
    public static final int L1_BUTTON = 16;
    public static final int SOURCE_BUTTON = 17;
    public static final int HIGH_ALGAE_BUTTON = 18;
    public static final int LOW_ALGAE_BUTTON = 19;
    public static final int PROCESSOR_BUTTON = 20;
    public static final int HOME_BUTTON = 21;
    public static final int BARGE_BUTTON = 22;
    public static final int CLIMB_BUTTON = 23;
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
    public static final double LEVEL_FOUR_ELEVATOR_HEIGHT = 23.0 * 39.37;
    public static final double LEVEL_THREE_ELEVATOR_HEIGHT = 12.3 * 39.37;
    public static final double LEVEL_TWO_ELEVATOR_HEIGHT = 4.765 * 39.37;
    public static final double LEVEL_ONE_ELEVATOR_HEIGHT = 0.0; 
    public static final double ALGAE_ONE_ELEVATOR_HEIGHT = 5.990 * 39.37;
    public static final double ALGAE_TWO_ELEVATOR_HEIGHT = 12.961 * 39.37;
    public static final double ALGAE_GROUND_ELEVATOR_HEIGHT = 6.780 * 39.87;
    public static final double CORAL_GROUND_ELEVATOR_HEIGHT = 2.640 * 39.87;
    public static final double PROCESSOR_ELEVATOR_HEIGHT = 0 * 39.87;
    public static final double CHUTE_ELEVATOR_HEIGHT = 5.838 * 39.37;
    public static final double HOME_ELEVATOR_HEIGHT = 0.0;
    public static final double ALGAE_NET_ELEVATOR_HEIGHT = 24.455 * 39.37;
    public static final double VERTICAL_GROUND_CORAL_INTAKE_ELEVATOR_HEIGHT = 2.804 * 39.37;

    // Pivot Constants
    public static final double LEVEL_FOUR_PIVOT_ANGLE = 0.094;
    public static final double LEVEL_THREE_PIVOT_ANGLE = 0.103;
    public static final double LEVEL_TWO_PIVOT_ANGLE = 0.116;
    public static final double LEVEL_THREE_LOWER_ANGLE = 0.050;
    public static final double PIVOT_LOWER_ANGLE = 0.025;
    public static final double LEVEL_ONE_PIVOT_ANGLE = 0.116;
    public static final double ALGAE_ONE_PIVOT_ANGLE = 0.059;
    public static final double ALGAE_TWO_PIVOT_ANGLE = 0.059;
    public static final double ALGAE_GROUND_PIVOT_ANGLE = -0.100;
    public static final double CORAL_GROUND_PIVOT_ANGLE = -0.090;
    public static final double PROCESSOR_PIVOT_ANGLE = 0.0;
    public static final double CHUTE_PIVOT_ANGLE = 0.128;
    public static final double HOME_PIVOT_ANGLE = 0.152;
    public static final double ALGAE_HOME_PIVOT_ANGLE = 0.137;
    public static final double ALGAE_NET_PIVOT_ANGLE = 0.180; 
    public static final double CLIMB_PIVOT_ANGLE = 0.039;
    public static final double VERTICAL_GROUND_CORAL_INTAKE_PIVOT_ANGLE = -0.071;

    // Turret Constants
    public static final double VERTICAL_TURRET_ANGLE = 2.225;
    public static final double AUTO_ALIGN_VERTICAL_TURRET_ANGLE = -2.284;
    public static final double HORIZONTAL_TURRET_ANGLE = 0.0;
    public static final double ALGAE_HORIZONTAL_TURRET_ANGLE = -4.521;

    // Climber Constants
    public static final double CLIMBER_UP_ANGLE = -60.0;
    public static final double CLIMBER_DOWN_ANGLE = 56.358;

  }

  public static final class AutoAlignConstants {
    // Left Setpoints
    public static final double yLeftSetpoint = -0.16;
    public static final double xLeftSetpoint = -0.78;
    public static final double rotLeftSetpoint = 2.22;

    // Left Scoring Setpoints
    public static final double yLeftScoringSetpoint = -0.13;
    public static final double xLeftScoringSetpoint = -0.52;
    public static final double rotLeftScoringSetpoint = 1.91;

    // Right Setpoints
    public static final double yRightSetpoint = 0.22;
    public static final double xRightSetpoint = -0.84;
    public static final double rotRightSetpoint = -5.13;

    // Right Scoring Setpoints
    public static final double yRightScoringSetpoint = 0.23;
    public static final double xRightScoringSetpoint = -0.52;
    public static final double rotRightScoringSetpoint = -4.98;

    // Timer values (in seconds)
    public static final double dontSeeTagTime = 1.0;
    public static final double validationTime = 0.5;
    public static final double timeoutTime = 2.0;
  }
}
