// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commandGroups;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.BalanceOnStationCommand;
import frc.robot.commands.DriveForwardOverChargeStationCommand;
import frc.robot.commands.DriveOnChargeStationCommand;
import frc.robot.commands.TurnAround;
import frc.robot.subsystems.DriveTrain;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class DriveOverForwardAndBackOnBalance extends SequentialCommandGroup {

  /** Creates a new ScoreAndDrive. */
  public DriveOverForwardAndBackOnBalance(DriveTrain driveTrain, Joystick joystick) {
    addCommands(
      new DriveForwardOverChargeStationCommand(driveTrain),
      new TurnAround(driveTrain, 140, 0.5),
      new DriveOnChargeStationCommand(driveTrain),
      new BalanceOnStationCommand(driveTrain, joystick)
    );
  }
}
