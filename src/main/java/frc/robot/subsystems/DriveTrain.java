// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Encoder;
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
import frc.robot.Constants;
import frc.robot.Constants.DriveTrainConstants;

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

  private final Solenoid solenoidShift = new Solenoid(PneumaticsModuleType.CTREPCM, SOLENOID_SHIFT);

  private final SlewRateLimiter speedLimiter = new SlewRateLimiter(2.5);
  private final SlewRateLimiter twistLimiter = new SlewRateLimiter(2.0);
  private final SlewRateLimiter autoDriveLimiter = new SlewRateLimiter(1);

  private final AHRS navx = new AHRS(SerialPort.Port.kMXP);

  private ShuffleboardTab autoTab = Shuffleboard.getTab(AUTO_TAB);
  public GenericEntry zAdjust = autoTab.add("Z Adjust", 0).getEntry();

  public DriveTrain(){
    TalonFXConfiguration configs = new TalonFXConfiguration();
    configs.primaryPID.selectedFeedbackSensor = FeedbackDevice.IntegratedSensor;
    leftTop.configAllSettings(configs);
    rightTop.configAllSettings(configs);

    leftTop.setNeutralMode(NeutralMode.Brake);
    rightTop.setNeutralMode(NeutralMode.Brake);
    // leftBack.setNeutralMode(NeutralMode.Brake);
    // rightBack.setNeutralMode(NeutralMode.Brake);
    // leftFront.setNeutralMode(NeutralMode.Brake);
    // rightFront.setNeutralMode(NeutralMode.Brake);

    leftTop.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 20);
    rightTop.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 20);

  }
 

   public void drive(XboxController controller){
   drive.arcadeDrive(controller.getRightX(), controller.getRightY());
   }
  public void driveWithDeadpan(Joystick joystick) {
    
  }
  public void initilizeSensors() {
    setRightSelectedSensorPosition(0);
    zeroGyro();
  }
  public void driveJoystick(Joystick joystick) {
   drive.arcadeDrive(twistLimiter.calculate(joystick.getZ()*0.8), speedLimiter.calculate(joystick.getY()));
    
   //drive.arcadeDrive((Math.pow(joystick.getZ(),3)), Math.pow(joystick.getY(),3));
    reportToShuffleboard(joystick);

   // double adjustedZ = getAdjustedZ(joystick);
   // drive.arcadeDrive(adjustedZ, joystick.getY(), true);
  }
  public void driveTimed(int direction, double speed) {
    double howToDrive = direction * speed;
    drive.tankDrive(howToDrive, -howToDrive);
  }
  public void driveDistance(int direction, double speed) {
    System.out.println(" start DriveDistance direction" + direction);
    System.out.println("start driveDistance speed" + speed);
    double howToDrive = autoDriveLimiter.calculate(direction * speed);
    drive.tankDrive(howToDrive, -howToDrive);
  }
  public void turnToAngle(double speed, double requestedDegrees) {
    System.out.println("start turn to angle");
    //TODO:  Let's try adding speed to the other side
    drive.tankDrive(speed, speed);
  }
  public void driveForBalance(double leftPercentPower, double rightPercentPower) {
    drive.tankDrive(leftPercentPower, rightPercentPower);
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

    SmartDashboard.putNumber("NavX yaw", navx.getYaw());
    SmartDashboard.putNumber("NavX pitch", navx.getPitch());
    SmartDashboard.putNumber("NavX roll", navx.getRoll());
    SmartDashboard.putNumber("NavX angle", navx.getAngle());
    //SmartDashboard.putNumber("Right Position", getRightPosition());
    //SmartDashboard.putNumber("Left Position", getLeftPosition());
    SmartDashboard.putNumber("Right Motor Output Percent", getRightMotorOutputPercent());
    SmartDashboard.putNumber("Left Motor Output Percent", getLeftMotorOutputPercent());
    SmartDashboard.putNumber("Right Selected Sensor Position", getRightSelectedSensorPosition());
    SmartDashboard.putNumber("Left Selected Sensor Position", getLeftSelectedSensorPosition());
    if (joystick != null) {
      SmartDashboard.putNumber("Joystick Z Value", joystick.getZ());
      SmartDashboard.putNumber("Joystick Adjusted Z Value", getAdjustedZ(joystick));
      SmartDashboard.putNumber("Joystick Y Value", joystick.getY());
    };
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
    return rightTop.getMotorOutputPercent();
  }
  public double getLeftMotorOutputPercent() {
    return leftTop.getMotorOutputPercent();
    
  }
  public double getRightSelectedSensorPosition() {
    return rightTop.getSelectedSensorPosition();
  }
  public double getLeftSelectedSensorPosition(){
    return leftTop.getSelectedSensorPosition();
    
  }
  public double nativeUnitsToDistanceMeters(double sensorCounts) {
    double motorRotations = (double)sensorCounts / COUNTS_PER_REVOLUTION;
    double wheelRotations = motorRotations/GEAR_RATIO;
    double positionInMeters = wheelRotations * (2*Math.PI * Units.inchesToMeters(wheelRotations));
    return positionInMeters;
  }

  public double distanceToNativeUnits(double distanceInInches) {
    double wheelRotations =  distanceInInches / (Math.PI * (WHEEL_DIAMETER));
    double motorRotations = GEAR_RATIO * wheelRotations;
    int sensorCounts = (int) motorRotations * COUNTS_PER_REVOLUTION;
    return sensorCounts;

  }
  public int countsGivenInches (double distance) {
    return (int) Math.round(10.86 * 94/(3.1415* 6) * Constants.DriveTrainConstants.COUNTS_PER_REVOLUTION);
  }
  public void setRightSelectedSensorPosition(double position) {
    ErrorCode error = rightTop.setSelectedSensorPosition(position);
    System.out.println("Set to zero " + error);  

  }
  public void zeroHeading() {
    navx.reset();
  }

  /**
   * Returns the heading of the robot.
   *
   * @return the robot's heading in degrees, from 180 to 180
   */
  public double getHeading() {
    double heading = Math.IEEEremainder(navx.getAngle(), 360) * (DriveTrainConstants.kGyroReversed ? -1.0 : 1.0);
    System.out.println("Heading" + heading );
    return heading;
  }
  public double getTurnRate() {
    return navx.getRate() * (DriveTrainConstants.kGyroReversed ? -1.0 : 1.0);
  }
  // Speed will be measured in meters/second
  }
