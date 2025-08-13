package frc.robot.subsystems;

import frc.kelrotlib.leds.Led;

public class LedSubsystem extends Led {
  /** Creates a new ExampleSubsystem. */
  public LedSubsystem() {
    createGroup(0,29, 1);
    createGroup(30,59,2);
  }

}
