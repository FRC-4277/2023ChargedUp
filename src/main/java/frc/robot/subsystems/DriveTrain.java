// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.DriveTrainConstants.*;
import static frc.robot.Constants.ShuffleboardConstants.*;

public class DriveTrain extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  private final WPI_TalonFX leftFront = new WPI_TalonFX(LEFT_FRONT);
  private final WPI_TalonFX leftBack = new WPI_TalonFX(LEFT_BACK);
  private final WPI_TalonFX leftTop = new WPI_TalonFX(LEFT_TOP);
  private final WPI_TalonFX rightFront = new WPI_TalonFX(RIGHT_FRONT);
  private final WPI_TalonFX rightBack = new WPI_TalonFX(RIGHT_BACK);
  private final WPI_TalonFX rightTop = new WPI_TalonFX(RIGHT_TOP);

  private MotorControllerGroup leftGroup = new MotorControllerGroup(leftFront, leftBack, leftTop);
  private MotorControllerGroup rightGroup = new MotorControllerGroup(rightFront, rightBack, rightTop);

  private final DifferentialDrive drive = new DifferentialDrive(leftGroup, rightGroup);

  private final Solenoid solenoidShift = new Solenoid(PneumaticsModuleType.REVPH, SOLENOID_SHIFT);

  private AHRS navx = new AHRS(SerialPort.Port.kMXP);

  private ShuffleboardTab autoTab = Shuffleboard.getTab(AUTO_TAB);
  public GenericEntry zAdjust = autoTab.add("Z Adjust", 0).getEntry();

  // private AHRS navx = new AHRS(SerialPort.Port.kMXP);
  /**
   * Example command factory method.
   *
   * @return a command
   */
  public CommandBase exampleMethodCommand() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
          /* one-time action goes here */
        });
  }

  /**
   * An example method querying a boolean state of the subsystem (for example, a
   * digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */
  public boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    return false;
  }

  // public void drive(XboxController controller){
  // drive.arcadeDrive(controller.getRightX(), controller.getRightY());

  public void driveJoystick(Joystick joystick) {
    reportToShuffleboard(joystick);

    double adjustedZ = getAdjustedZ(joystick);
    drive.arcadeDrive(adjustedZ, joystick.getY(), true);
  }
  public void driveTimed(int direction, double speed) {
    double howToDrive = direction * speed;
    drive.tankDrive(howToDrive, howToDrive);
  }

  public void stop() {
    drive.stopMotor();
  }

  public double getAdjustedZ(Joystick joystick) {
    double shuffleboardAdjustedZ = zAdjust.getDouble(0);
    return joystick.getZ() + shuffleboardAdjustedZ;
  }

  @Override
  public void periodic() {

    // This method will be called once per scheduler run
  }

  public void reportToShuffleboard(Joystick joystick) {
    SmartDashboard.putNumber("Joystick Z Value", joystick.getZ());
    SmartDashboard.putNumber("Joystick Adjusted Z Value", getAdjustedZ(joystick));
    SmartDashboard.putNumber("Joystick Y Value", joystick.getY());
    SmartDashboard.putNumber("NavX yaw", navx.getYaw());
    SmartDashboard.putNumber("NavX pitch", navx.getPitch());
    SmartDashboard.putNumber("NavX roll", navx.getRoll());
    SmartDashboard.putNumber("NavX angle", navx.getAngle());
    //SmartDashboard.putNumber("Right Position", getRightPosition());
    //SmartDashboard.putNumber("Left Position", getLeftPosition());
    //SmartDashboard.putNumber("Right Motor Output Percent", getRightMotorOutputPercent());
    //SmartDashboard.putNumber("Left Motor Output Percent", getLeftMotorOutputPercent());
    //SmartDashboard.putNumber("Right Selected Sensor Position", getRightSelectedSensorPosition());
    //SmartDashboard.putNumber("Left Selected Sensor Position", getLeftSelectedSensorPosition());
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  public void toggleShift() {
    solenoidShift.toggle();

  }
  public void zeroGyro() {
    System.out.println("NavX Connected: " + navx.isConnected());
    navx.reset();
  }
  public double getYaw() {
    return navx.getYaw();
  }
  public double getPitch() {
    return navx.getPitch();
  }
  public double getRoll() {
    return navx.getRoll();
  }
  public double getAngle() {
    return navx.getAngle();
  }
  public double getRightPosition() {
    return rightTop.getActiveTrajectoryPosition();
  }
  public double getLeftPosition() {
    return leftTop.getActiveTrajectoryPosition();

  }
  public double getRightMotorOutputPercent() {
    return rightTop.getActiveTrajectoryPosition();
  }
  public double getLeftMotorOutputPercent() {
    return leftTop.getActiveTrajectoryPosition();
    
  }
  public double getRightSelectedSensorPosition() {
    return rightTop.getActiveTrajectoryPosition();
  }
  public double getLeftSelectedSensorPosition(){
    return leftTop.getActiveTrajectoryPosition();
    
  }
}
