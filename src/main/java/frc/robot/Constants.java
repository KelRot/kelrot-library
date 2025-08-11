// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.Meters;
import edu.wpi.first.units.measure.Distance;
public final class Constants {
  public static class LedConstants {
    public static final int kLedPort = 6; //PWM port on RoborIO
    public static final int kLedLength = 60; //led count
    public static final Distance kLedSpacing = Meters.of(1/ 60.0); // density of 120 LEDs per meter
  }
  
}
