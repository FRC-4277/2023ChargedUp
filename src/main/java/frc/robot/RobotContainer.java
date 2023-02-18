// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commandGroups.ScoreAndDriveShort;
import frc.robot.commands.DriveAutoForwardTimedCommand;
import frc.robot.commands.DriveManualCommand;
import frc.robot.commands.DriveShiftCommand;
import frc.robot.commands.GrabCommand;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Grabber;

import static frc.robot.Constants.Controllers.*;

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */


public class RobotContainer {
  //Shuffleboard
  private final ShuffleboardTab autonomousTab = Shuffleboard.getTab("Autonomous");
  private SendableChooser autoChooser;
  

  // Subsystems
  private final DriveTrain driveTrain = new DriveTrain();
  private final Grabber grabber = new Grabber();

  //Controllers
  //private final XboxController controller =


      //new XboxController(XBOX_CONTROLLER);
  private final XboxController controller =
      new XboxController (XBOX_CONTROLLER);
  private final Joystick joystick = 
      new Joystick (LOGITECH_JOYSTICK);

    // Pneumatics
  private final Compressor compressor = new Compressor(PneumaticsModuleType.CTREPCM);

  //Commands
  private final DriveManualCommand driveManualCommand = new DriveManualCommand(driveTrain, joystick, controller);
  private final DriveShiftCommand driveShiftCommand = new DriveShiftCommand(driveTrain);
  private final GrabCommand grabCommand = new GrabCommand(grabber);
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
    //Set default commands for subsystems
    driveTrain.setDefaultCommand(driveManualCommand);
    //set up Shuffleboard
    setupAutonomousTab();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    //new Trigger(driveTrain::exampleCondition)
       // .onTrue(new DriveManualCommand(driveTrain, controller));
      
       // Xbox Controller
       new JoystickButton(controller, Button.kX.value)
       .onTrue(new InstantCommand(() -> driveTrain.toggleShift()));

       new JoystickButton(controller, Button.kB.value)
       .onTrue(new InstantCommand(() -> grabber.toggleGrab()));

       new JoystickButton(controller, Button.kY.value)
       .onTrue(new InstantCommand(() -> grabber.toggleTilt()));
       
       
       //Joystick buttons
       new JoystickButton(joystick, Button.kX.value)
      .onTrue(new InstantCommand(() -> driveTrain.toggleShift()));

      new JoystickButton(joystick, Button.kLeftStick.value)
      .onTrue(new InstantCommand (() -> grabber.toggleGrab()));
       
      new JoystickButton(joystick, Button.kY.value)
      .onTrue(new InstantCommand (() -> grabber.toggleTilt()));
      


    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
    //controller.().whileTrue(driveTrain.exampleMethodCommand());
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return Commands.print( "No autonomous command configured.");
  }
  private void setupAutonomousTab(){
    autoChooser = new SendableChooser<>();
    SendableRegistry.setName(autoChooser, "Autonomous Command");
    autoChooser.setDefaultOption("Nothing", null);
    autoChooser.addOption("Drive Forward", new DriveAutoForwardTimedCommand(driveTrain, 2));
    autoChooser.addOption("Score And Drive Short", new ScoreAndDriveShort(grabber, driveTrain));
    autonomousTab.add(autoChooser)
      .withPosition(0, 0)
      .withSize(2, 1);
  }

}

