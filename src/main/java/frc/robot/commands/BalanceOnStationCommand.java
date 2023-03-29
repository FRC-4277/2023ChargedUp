// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrain;

public class BalanceOnStationCommand extends CommandBase {
  private final DriveTrain driveTrain;
  private final Joystick joystick;
  private double howFarOffAmI;
  private double currentAngle;
  private double drivePower;
  /** Creates a new BalanceOnStationCommand. */
  public BalanceOnStationCommand(DriveTrain driveTrain, Joystick joystick) {
    addRequirements(driveTrain);
    this.driveTrain = driveTrain;
    this.joystick = joystick;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //driveTrain.zeroGyro();
    System.out.println("Start Autobalance ");

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
     // Uncomment the line below this to simulate the gyroscope axis with a controller joystick
     //Double currentAngle = -1 * joystick.getX() * 45;
    //
    this.currentAngle = driveTrain.getPitch();

    howFarOffAmI = Constants.Balance.BALANCE_GOAL_DEGREES - currentAngle;
    drivePower = -Math.min(Constants.Balance.BALANCED_DRIVE_KP * howFarOffAmI, 1);

    // Our robot needed an extra push to drive up in reverse, probably due to weight imbalances
    if (drivePower < 0) {
      drivePower *= Constants.Balance.BALANCE_BACKWARD_MULTIPLIER;
    }

    // Limit the max power
    if (Math.abs(drivePower) > 0.4) {
      drivePower = Math.copySign(0.4, drivePower);
    }

    driveTrain.driveForBalance(drivePower, drivePower);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    driveTrain.reportToShuffleboard(null);
    boolean shouldFinish = Math.abs(howFarOffAmI) < Constants.Balance.BALANCE_ANGLE_TRESHOLD_DEGREES;
      // Debugging Print Statments
      System.out.println("Current Angle: " + currentAngle);
      System.out.println("Error " + howFarOffAmI);
      System.out.println("Drive Power: " + drivePower);
      System.out.println("Should finish " + shouldFinish );
    return shouldFinish; 
    // End the command when we are within the specified threshold of being 'flat' (gyroscope pitch of 0 degrees)
  }
}
