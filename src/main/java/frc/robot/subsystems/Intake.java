// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.GrabConstants.*;


/** Add your docs here. */
public class Intake extends SubsystemBase {
    private final WPI_TalonSRX top = new WPI_TalonSRX(TOP);
    private final WPI_TalonSRX bottom = new WPI_TalonSRX(BOTTOM);

 /** Creates a new Grabber. */
 public Intake() {
  top.setNeutralMode(NeutralMode.Brake);
  bottom.setNeutralMode(NeutralMode.Brake);
 }

 @Override
 public void periodic() {
   // This method will be called once per scheduler run
 }
 public void in() {
   move(1);
 }
 public void out(){
   move(-1);
 }
 public void move(int direction){
   top.set(1.0 * -direction);
   bottom.set(1.0 * direction);
 }
 public void toggle(){
  
 }
 public void stop(){ 
  top.set(0);
  bottom.set(0);
 };
}