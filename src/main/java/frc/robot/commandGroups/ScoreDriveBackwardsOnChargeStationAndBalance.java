// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commandGroups;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.BalanceOnStationCommand;
import frc.robot.commands.DriveBackwardsOnChargeStationCommand;
import frc.robot.commands.DriveShiftCommand;
import frc.robot.commands.OuttakeTimed;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intake;

public class ScoreDriveBackwardsOnChargeStationAndBalance extends SequentialCommandGroup {
  /** Creates a new DriveBackwardsOnChargeStationAndBalance. */
  public ScoreDriveBackwardsOnChargeStationAndBalance(DriveTrain driveTrain, Intake intake, Joystick joystick) {
    addCommands(
      //new DriveShiftCommand(driveTrain),
      new OuttakeTimed(intake, 1),
      new DriveBackwardsOnChargeStationCommand(driveTrain),
      new BalanceOnStationCommand(driveTrain, joystick)
    );
  }
}
