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
    private HashMap<Integer, AddressableLEDBufferView> m_groupList;
    private HashMap<Integer, LEDPattern> m_patternList;
    private LEDPattern k_defaultPattern = LEDPattern.solid(Color.kBlack);

    public Led() {
        m_led = new AddressableLED(LedConstants.kLedPort); //every variable you might change later (ports, length etc.) should be added to Constants.java*
        m_buffer = new AddressableLEDBuffer(LedConstants.kLedLength); // * this way every port and important variable is in one place.
        m_led.setLength(LedConstants.kLedLength); //setting the length takes a lot of load so do it only one time when possible
        m_led.start();
        
        m_groupList = new HashMap<Integer, AddressableLEDBufferView>(); //I decided to use Integers for the keys as it is much more straight-forward, in future uses the keys can be String's for more complicated group identification needs.
        m_patternList = new HashMap<Integer, LEDPattern>(); //For this version of grouping we need to store previous patterns for each group

        setDefaultCommand(runPattern(k_defaultPattern).withName("Off")); //set all leds to off/black on start
    }

    public void createGroup(int startingIndex, int endingIndex, Integer groupID) { //infinite amount of groups creatable, without complicating the code
        AddressableLEDBufferView group = m_buffer.createView(startingIndex, endingIndex); //create new group(view)
        m_groupList.put(groupID, group);
        m_patternList.put(groupID, k_defaultPattern); //initially set the defaultPattern, so the runPattern command doesn't break
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
        return run(() -> {
            for (Integer i : m_groupList.keySet()) { //parsing through every key in the groupList HashMap
                m_patternList.get(i).applyTo(m_groupList.get(i)); //getting every setted pattern and applying it to the ID'd LED Group
            }
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
