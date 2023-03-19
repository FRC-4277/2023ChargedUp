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

    public static final int SOLENOID_SHIFT = 0;
    public static final int SOLENOID_PLUNGER = 3;
  }
  public static class PlungerConstants {
    public static final int SOLENOID_PLUNGER = 3;
    public static final boolean UP = true;
    public static final boolean DOWN = true;
  }

  public static class GrabConstants {
    public static final int SOLENOID_GRAB = 2;
    public static final int SOLENOID_TILT = 1;
    public static final boolean GRAB = true;
    public static final boolean TILT = true;
    public static final int TOP = 60;
    public static final int BOTTOM = 44;
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
