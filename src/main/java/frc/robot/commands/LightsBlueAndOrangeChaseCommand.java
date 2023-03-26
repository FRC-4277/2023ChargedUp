// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Lights;
import static frc.robot.Constants.Lights.*;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class LightsBlueAndOrangeChaseCommand extends InstantCommand {
  private int counter;
  private Lights lights;

  public LightsBlueAndOrangeChaseCommand(Lights lights) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(lights);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("LIGHTS BLUE AND ORANGE CHASE");
    counter = 0;
  }

  @Override
  public void execute() {
      counter++;
      if (counter == Integer.MAX_VALUE) {
        counter = 0;
      }
      int chaseOffset = (int) (counter / CHASE_COMMAND_FREQUENCY);
      lights.setOrangeAndBlueChasing(chaseOffset);
  }

}