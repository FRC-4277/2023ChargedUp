// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.PlungerConstants.*;

public class Plunger extends SubsystemBase {
  private final Solenoid solenoid = new Solenoid (PneumaticsModuleType.CTREPCM, SOLENOID_PLUNGER);
  /** Creates a new Grabber. */
  public Plunger() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  public void togglePlunger() {
    solenoid.toggle();
  }
  }
