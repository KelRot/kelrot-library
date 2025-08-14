// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.subsystems.Led;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
public class RobotContainer {
  private final Led m_led = new Led();

  public RobotContainer() {
    m_led.createGroup(0, 29, 1); //indexing: in a 60 led strip, first LED is index 0 and the last led is index 59
    m_led.createGroup(30, 59, 2);

    m_led.setSolidColor(Color.kFirstBlue, new Integer[] {1,2}); //The library Color includes many color presets, but it is also possible to use RGB values to create "Color"s

    configureBindings();
  }

  private void configureBindings() {
  }

  public Command getAutonomousCommand() {
    return null;
  }
}
