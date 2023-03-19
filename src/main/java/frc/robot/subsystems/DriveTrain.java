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

  private final Encoder leftEncoder = new Encoder (0,1);
  private final Encoder rightEncoder = new Encoder (2,3);

  //private final SlewRateLimiter speedLimiter = new SlewRateLimiter(0.8);
  //private final SlewRateLimiter twistLimiter = new SlewRateLimiter(0.8);

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

    leftTop.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 20);
    rightTop.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 20);
    leftEncoder.setDistancePerPulse(WHEEL_CIRCUMFERENCE/360); //if am-3132
    rightEncoder.setDistancePerPulse(WHEEL_CIRCUMFERENCE/360); //if am-3132
  }
 

   public void drive(XboxController controller){
   drive.arcadeDrive(controller.getRightX(), controller.getRightY());
   }
  public void driveWithDeadpan(Joystick joystick) {
    
  }
  public void driveJoystick(Joystick joystick) {
   // drive.arcadeDrive(speedLimiter.calculate(joystick.getZ()), twistLimiter.calculate(joystick.getY()));
    
    drive.arcadeDrive((Math.pow(joystick.getZ(),3))*0.7, Math.pow(joystick.getY(),3)*0.7);
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
    double howToDrive = direction * speed;
    drive.tankDrive(howToDrive, -howToDrive);
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
    SmartDashboard.putNumber("Right Motor Output Percent", getRightMotorOutputPercent());
    SmartDashboard.putNumber("Left Motor Output Percent", getLeftMotorOutputPercent());
    SmartDashboard.putNumber("Right Selected Sensor Position", getRightSelectedSensorPosition());
    SmartDashboard.putNumber("Left Selected Sensor Position", getLeftSelectedSensorPosition());
    SmartDashboard.putNumber("Encoder Left Speed", getLeftSpeed());
    SmartDashboard.putNumber("Encoder Right Speed", getRightSpeed());
    SmartDashboard.putNumber("Encoder Average Speed", getAverageEncoderSpeed());
    SmartDashboard.putNumber("Encoder Left Distance", getLeftDistance());
    SmartDashboard.putNumber("Encoder Right Distance", getRightDistance());
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
  public void setRightSelectedSensorPosition(double position) {
    ErrorCode error = rightTop.setSelectedSensorPosition(position);
    System.out.println("Set to zero " + error);  

  }
  // Speed will be measured in meters/second
  public double getLeftSpeed() {
    return leftEncoder.getRate() / 1000; // Multiply by 1000 to convert from millimeters to meters
  }
  public double getRightSpeed() {
    return rightEncoder.getRate() / 1000; // Multiply by 1000 to convert from millimeters to meters
  }
  public double getAverageEncoderSpeed() {
    return (getLeftSpeed() + getRightSpeed()) / 2;
  }

  // Distance will be measured in meters
  public double getLeftDistance() {
    return leftEncoder.getDistance() / 1000; // Multiply by 1000 to convert from millimeters to meters
  }
  public double getRightDistance() {
    return rightEncoder.getDistance() / 1000; // Multiply by 1000 to convert from millimeters to meters
  }
  public double getAverageEncoderDistance() {
    return (getLeftDistance() + getRightDistance()) / 2;
  }

  // Zero the drivetrain encoders
  public void resetEncoders() {
		leftEncoder.reset();
    rightEncoder.reset();
	}
  
}
