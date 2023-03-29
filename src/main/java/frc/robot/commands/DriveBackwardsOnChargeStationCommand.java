// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DriveTrain;

public class DriveBackwardsOnChargeStationCommand extends SequentialCommandGroup {

  // List commands here sequentially
  public DriveBackwardsOnChargeStationCommand(DriveTrain driveTrain) { // List commands here sequentially
    addCommands(new DriveDistance(driveTrain, -1, 70, 0.6));
  }

}