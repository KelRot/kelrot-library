package frc.robot.subsystems;

import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.Seconds;

import java.util.HashMap;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.AddressableLEDBufferView;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.LedConstants;

public class Led extends SubsystemBase{ //a Java inheritance example

    private AddressableLED m_led;
    private AddressableLEDBuffer m_buffer;
    private AddressableLEDBufferView m_group1, m_group2, m_group3;
    private HashMap<Integer, LEDPattern> m_patternList;

    public Led() {
        m_led = new AddressableLED(LedConstants.kLedPort); //every variable you might change later (ports, length etc.) should be added to Constants.java*
        m_buffer = new AddressableLEDBuffer(LedConstants.kLedLength); // * this way every port and important variable is in one place.
        m_led.setLength(LedConstants.kLedLength); //setting the length takes a lot of load so do it only one time when possible
        m_led.start();
        
        //Create led groups (views)
        m_group1 = m_buffer.createView(0, 19); //in a 60 led strip, first LED is index 0 and the last led is index 59
        m_group2 = m_buffer.createView(20, 39);
        m_group3 = m_buffer.createView(40, 59);


        LEDPattern defaultPattern = LEDPattern.solid(Color.kBlack); //off/all black pattern
        m_patternList = new HashMap<Integer, LEDPattern>(); //For this version of grouping we need to store previous patterns for each group
        m_patternList.put(1, defaultPattern);
        m_patternList.put(2, defaultPattern);
        m_patternList.put(3, defaultPattern);
        //I decided to use Integers for the keys as it is much more straight-forward, in future uses the keys can be String's for more complicated group identification needs.

        setDefaultCommand(runPattern(defaultPattern).withName("Off")); //set all leds to off/black on start
    }

    public void setSolidColor(Color color, Integer[] groupIDs){
        LEDPattern solidColorPattern = LEDPattern.solid(color);
        for (int i = 0; i < groupIDs.length; i++) { //update the latest pattern for every LED group given
            m_patternList.replace(groupIDs[i], solidColorPattern); //I love hashmap it is just so cool
        }
        runPattern();
    }

    public void setBlinkColor(Color color, double interval, Integer[] groupIDs){
        LEDPattern base = LEDPattern.solid(color);
        LEDPattern blinkPattern = base.blink(Seconds.of(interval)); //synchronised blink, wpilib also has support for asynchronised blink
        for (int i = 0; i < groupIDs.length; i++) {
            m_patternList.replace(groupIDs[i], blinkPattern);
        }
        runPattern();
    }

    public void rainbow(Integer[] groupIDs){
        LEDPattern rainbow = LEDPattern.rainbow(255, 128); //all hues at maximum saturation and *half* brightness
        LEDPattern scrollingRainbow = rainbow.scrollAtAbsoluteSpeed(MetersPerSecond.of(1), LedConstants.kLedSpacing); //moves/scrolls the effect at a speed of 1 meter per second
        for (int i = 0; i < groupIDs.length; i++) {
            m_patternList.replace(groupIDs[i], scrollingRainbow);
        }
        runPattern();
    }

    public void turnOff(){
        Integer[] groupIDs = {1,2,3}; // TRY: if we can apply to the buffer to use the whole led strip or not, if not remove 2nd runPattern function
        setSolidColor(Color.kBlack, groupIDs); //instead of creating another pattern and using runPattern etc., it is usually better to use already existing functions
    }

    public Command runPattern(){  //A command is used as it doesn't allow actions to run simultaneously, for this usage it is crucial, because we need to stop the previous patterns and start the new ones.
        return run(() -> { //there might be a way to write this cleaner
            m_patternList.get(1).applyTo(m_group1);
            m_patternList.get(2).applyTo(m_group2);
            m_patternList.get(3).applyTo(m_group3);
        });
    }

    public Command runPattern(LEDPattern pattern) { //might get removed later.
        return run(() -> {
            pattern.applyTo(m_buffer);
        });
    }

    @Override
    public void periodic() { //update the data every 20ms
        m_led.setData(m_buffer);
    }
  
    @Override
    public void simulationPeriodic() {
    }
}
