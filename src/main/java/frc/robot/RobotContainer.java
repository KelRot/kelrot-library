// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import frc.kelrotlib.utils.TunableNumber;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.LedSubsystem;
import frc.robot.subsystems.TunableNumberExample;
public class RobotContainer {

  private final LedSubsystem leds = new LedSubsystem();
  private final TunableNumberExample example = new TunableNumberExample();
  public RobotContainer() {
    configureBindings();
    leds.setSolidColor(Color.kFirstBlue, new Integer[] {1});
  }

  private void configureBindings() {
  }

  public Command getAutonomousCommand() {
    return null;
  }
}
