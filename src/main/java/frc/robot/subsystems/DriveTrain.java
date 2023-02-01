// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.DriveTrainConstants.*;

public class DriveTrain extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  private final WPI_TalonFX leftFollower = new WPI_TalonFX(FRONT_LEFT);
  private final WPI_TalonFX leftLead = new WPI_TalonFX (BACK_LEFT);
  private final WPI_TalonFX rightFollower = new WPI_TalonFX(FRONT_RIGHT);
  private final WPI_TalonFX rightLead = new WPI_TalonFX(BACK_RIGHT);

  private MotorControllerGroup leftGroup = new MotorControllerGroup(leftFollower, leftLead);
  private MotorControllerGroup rightGroup = new MotorControllerGroup(rightFollower, rightLead);

  private final DifferentialDrive drive = new DifferentialDrive(leftGroup, rightGroup);

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
   * An example method querying a boolean state of the subsystem (for example, a digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */
  public boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    return false;
  }

  public void drive(XboxController controller){
    drive.arcadeDrive(controller.getLeftY(), controller.getRightX());
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
