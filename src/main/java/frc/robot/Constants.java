// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class Controllers {
    public static final int XBOX_CONTROLLER = 0;
    public static final int LOGITECH_JOYSTICK = 1;
  }

  public static class DriveTrainConstants {
    public static final int LEFT_FRONT = 5;
    public static final int LEFT_BACK = 6;
    public static final int LEFT_TOP = 1;
    public static final int RIGHT_FRONT = 7;
    public static final int RIGHT_BACK = 8;
    public static final int RIGHT_TOP = 3;
    public static final double WHEEL_DIAMETER = Units.inchesToMeters(6);
    public static final double WHEEL_CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER;
    public static final double TRACK_WIDTH = Units.inchesToMeters(20.5);
    public static final int COUNTS_PER_REVOLUTION = 4096;
    public static final double GEAR_RATIO = 10.86;
    public static final int WHEEL_RADIUS = 3;

    public static final double kTurnP = 1;
    public static final double kTurnI = 0;
    public static final double kTurnD = 0;

    public static final double kMaxTurnRateDegPerS = 100;
    public static final double kMaxTurnAccelerationDegPerSSquared = 300;

    public static final double kTurnToleranceDeg = 5;
    public static final double kTurnRateToleranceDegPerS = 10; // degrees per second
    public static final boolean kGyroReversed = false;

    public static final int SOLENOID_SHIFT = 2;
  }
  public static class PlungerConstants {
    public static final int SOLENOID_PLUNGER = 0;
    public static final boolean UP = true;
    public static final boolean DOWN = true;
  }

  public static class GrabConstants {
    public static final int SOLENOID_GRAB = 2;
    public static final int SOLENOID_TILT = 1;
    public static final boolean GRAB = true;
    public static final boolean TILT = true;
    public static final int TOP = 8;
    public static final int BOTTOM = 9;
  }
  public static class Balance {
    public static final double BALANCED_DRIVE_KP = 0.015; // P (Proportional) constant of a PID loop
    public static final double BALANCE_GOAL_DEGREES = 0;
    public static final double BALANCE_ANGLE_TRESHOLD_DEGREES = 0.5;
    public static final double BALANCE_BACKWARD_MULTIPLIER = 1.35;
  }

  public static class Lights {
      public static final int BLUE_R = 0;
      public static final int BLUE_G = 0;
      public static final int BLUE_B = 50;
  
      public static final int ORANGE_R = 50;
      public static final int ORANGE_G = 10;
      public static final int ORANGE_B = 0;
  
      public static final int RAINBOW_FIRST_HUE = 1;
    
      public static final int CHASE_DELAY_MS = 100; // Make a multiple of 20ms
      public static final int CHASE_COMMAND_FREQUENCY = CHASE_DELAY_MS / 20;
  }

  public static class ShuffleboardConstants {
    public static final String AUTO_TAB = "Autonomous";
  }
  public static class AutoConstants {
    public static final int SHORT_DISTANCE = 2;
    public static final int SHORT_SPEED = 2;
    public static final int LONG_DISTANCE = 2;
    public static final int LONG_SPEED = 2;
  }
}
