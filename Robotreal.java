// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
//import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.*;


/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {
//  private VictorSPX ClimberLeftSparkMax;
//  private VictorSPX ClimberRightSparkMax;
//  private final PWMSparkMax m_leftDrive = new PWMSparkMax(0);
//  private final PWMSparkMax m_rightDrive = new PWMSparkMax(1);
  WPI_VictorSPX RightFront = new WPI_VictorSPX(1);
  WPI_VictorSPX RightRear = new WPI_VictorSPX(2);
  WPI_VictorSPX LeftFront = new WPI_VictorSPX(3);
  WPI_VictorSPX LeftRear = new WPI_VictorSPX(4);

  DifferentialDrive RobotDrive = new DifferentialDrive(LeftFront, RightFront);

//  private final DifferentialDrive m_robotDrive = new DifferentialDrive(m_leftDriveFront, m_rightDriveFront);
  
  private final XboxController DriveController = new XboxController(0);
  private final Timer m_timer = new Timer();

  public Robot() {
    SendableRegistry.addChild(RobotDrive, LeftFront);
    SendableRegistry.addChild(RobotDrive, RightFront);
  }

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    RightFront.setInverted(true);
    LeftRear.follow(LeftFront);
    RightRear.follow(RightFront);
  }

  /** This function is run once each time the robot enters autonomous mode. */
  @Override
  public void autonomousInit() {
    m_timer.restart();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    // Drive for 2 seconds
    if (m_timer.get() < 2.0) {
      // Drive forwards half speed, make sure to turn input squaring off
      RobotDrive.arcadeDrive(0.4, 0.0, false);
    }
    else if (m_timer.get() > 2.0 && m_timer.get() < 4.0) {
      // Drive forwards half speed, make sure to turn input squaring off
      RobotDrive.arcadeDrive(0.0, 0.5, false);
    }
    else if (m_timer.get() > 4.0 && m_timer.get() < 6.0) {
      // Drive forwards half speed, make sure to turn input squaring off
      RobotDrive.arcadeDrive(0.0, -0.5         , false);
    }
    else {
      RobotDrive.stopMotor(); // stop robot
    }
  }

  /** This function is called once each time the robot enters teleoperated mode. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during teleoperated mode. */
  @Override
  public void teleopPeriodic() {
    RobotDrive.arcadeDrive(-DriveController.getLeftY(), -DriveController.getRightX());
  }

  /** This function is called once each time the robot enters test mode. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
