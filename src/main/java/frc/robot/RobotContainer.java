// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.LedConstants;
import frc.robot.subsystems.LedSubsystem;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
public class RobotContainer {
  private final LedSubsystem m_led = new LedSubsystem(LedConstants.kledGroups);

  public RobotContainer() {
    m_led.setBlinkColor(Color.kRed, 3, new int[]{0,1,2});
    configureBindings();
  }

  private void configureBindings() {
  }

  public LedSubsystem getLedSubsystem() {
    return m_led;
  }

  public Command getAutonomousCommand() {
    return null;
  }
}
