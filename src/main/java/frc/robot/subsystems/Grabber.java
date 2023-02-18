// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.GrabConstants.*;

public class Grabber extends SubsystemBase {
  private final Solenoid solenoidGrab = new Solenoid (PneumaticsModuleType.REVPH, SOLENOID_GRAB);
  private final Solenoid solenoidTilt = new Solenoid (PneumaticsModuleType.REVPH, SOLENOID_TILT);
  /** Creates a new Grabber. */
  public Grabber() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  public void toggleGrab() {
    solenoidGrab.toggle();
  }
  public void grab(){
    solenoidGrab.set(GRAB);
  }
  public void release(){
    solenoidGrab.set(!GRAB);
  }
  public void toggleTilt() {
    solenoidTilt.toggle();
  }
  public void tiltDown() {
    solenoidTilt.set(TILT);
  }
  public void tiltUp(){
    solenoidTilt.set(!TILT);
  }}
