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
    m_led.setSolidColor(Color.kFirstBlue, new int[] {1,2}); //The library Color includes many color presets, but it is also possible to use RGB values to create "Color"s

    configureBindings();
  }

  private void configureBindings() {
  }

  public Command getAutonomousCommand() {
    return null;
  }
}
