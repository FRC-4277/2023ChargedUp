// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class DriveDistanceAndDirection extends CommandBase {
  /** Creates a new DriveDistanceAndDirection. */
  private final DriveTrain driveTrain;
  private double initialDistance;
  private double distance;
  private int direction;
  private double percent;
  private double percentPower;
  public DriveDistanceAndDirection(DriveTrain driveTrain, int direction, double distance, double percent) {
    this.driveTrain = driveTrain;
    this.direction = direction;
    this.distance = distance;
    this.percent = percent;
    addRequirements(driveTrain);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    initialDistance = driveTrain.getRightSelectedSensorPosition();
    driveTrain.driveDistance(direction, percent);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    driveTrain.stop();
  }
  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    System.out.println(" Start Drive Distance" + initialDistance + distance);
    System.out.println(" start DriveDistance direction" + driveTrain.getRightSelectedSensorPosition());
    return driveTrain.getRightSelectedSensorPosition() >= initialDistance + distance;
  }
}
