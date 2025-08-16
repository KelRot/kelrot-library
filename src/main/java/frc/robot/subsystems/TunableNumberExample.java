package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.kelrotlib.utils.TunableNumber;

public class TunableNumberExample extends SubsystemBase {
  
  private final TunableNumber newnumber = new TunableNumber("sa", 0);
  
  private final TunableNumber newnumber2 = new TunableNumber("test2", 0, value -> {
    System.out.println("Tunable Number is changed, new number is" + value);

  // Usage is not like motor.setVoltage(newnumber)
  // You should motor.setVoltage(newnumber.get())
  });
  /** Creates a new ExampleSubsystem. */
  public TunableNumberExample() {
    
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
