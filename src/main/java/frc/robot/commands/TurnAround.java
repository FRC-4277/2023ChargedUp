// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class TurnAround extends CommandBase {
  private final DriveTrain driveTrain;
  private final double requestedDegrees;
  private final double speed;
  /** Creates a new TurnAround. */
  public TurnAround(DriveTrain driveTrain, double requestedDegrees, double speed) {
    this.driveTrain = driveTrain;
    this.requestedDegrees = requestedDegrees;
    this.speed = speed ;
    addRequirements(driveTrain);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println(" Start turn around");
    driveTrain.zeroGyro();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    driveTrain.turnToAngle(speed, requestedDegrees);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    driveTrain.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    driveTrain.reportToShuffleboard(null);
    double currentAngle = driveTrain.getAngle();
    boolean shouldStop = Math.abs(currentAngle) > requestedDegrees ;

    System.out.println(" Current angle " + currentAngle);
    System.out.println(" Should stop driving " + shouldStop);
    return shouldStop;
  }
}
