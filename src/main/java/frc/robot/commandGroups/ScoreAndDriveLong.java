// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.DriveAutoForwardTimedCommand;
import frc.robot.commands.GrabRelease;
import frc.robot.commands.TilterTiltDown;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Grabber;
import static frc.robot.Constants.AutoConstants.LONG_SPEED;;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ScoreAndDriveLong extends SequentialCommandGroup {
  private final Grabber grabber;
  private final DriveTrain driveTrain;
  /** Creates a new ScoreAndDrive. */
  public ScoreAndDriveLong(Grabber grabber, DriveTrain driveTrain) {
    this.addRequirements(grabber, driveTrain);
    this.grabber = grabber;
    this.driveTrain = driveTrain;

    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new TilterTiltDown(grabber),
      new GrabRelease(grabber),
      new DriveAutoForwardTimedCommand(driveTrain, LONG_SPEED)
    );
  }
}
