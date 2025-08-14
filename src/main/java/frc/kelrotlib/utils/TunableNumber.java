//hellooo
package frc.kelrotlib.utils;

import edu.wpi.first.networktables.*;
import dev.doglog.DogLog;

import java.util.EnumSet;
import java.util.function.Consumer;

public class TunableNumber {
    private final NetworkTableEntry entry;
    private double lastValue;
    private NetworkTableInstance inst = NetworkTableInstance.getDefault();


        /**
     * @param key          
     * @param defaultValue 
     * @param onChange     
     */
    public TunableNumber(String key, double defaultValue, Consumer<Double> onChange) {
        this.lastValue = defaultValue;
        entry = NetworkTableInstance.getDefault()
                .getTable("SmartDashboard")
                .getEntry("/Tuning" + "/" + key);

        entry.setDouble(defaultValue);

inst.addListener(
        entry,
        EnumSet.of(NetworkTableEvent.Kind.kValueAll),
        event -> {
        double newValue = entry.getDouble(lastValue);
          if(this.lastValue != newValue) {
            DogLog.log("/Tuning" + "/" + key, entry.getDouble(lastValue));
            System.out.println(newValue);
            this.lastValue = newValue;
            onChange.accept(lastValue);
          }
        });
    }

    /** 
     * @param key          
     * @param defaultValue 
     * @param onChange     
     */
    public TunableNumber(String key, double defaultValue) {
        this.lastValue = defaultValue;
        entry = NetworkTableInstance.getDefault()
                .getTable("SmartDashboard")
                .getEntry("/Tuning" + "/" + key);

        entry.setDouble(defaultValue);
    
        inst.addListener(
        entry,
        EnumSet.of(NetworkTableEvent.Kind.kValueAll),
        event -> {
        double newValue = entry.getDouble(lastValue);
          if(this.lastValue != newValue) {
            DogLog.log("/Tuning" + "/" + key, entry.getDouble(lastValue));
            System.out.println(newValue);
            this.lastValue = newValue;
          }
        });

    }

    /** Şu an dashboard’daki güncel değeri döner */
    public double get() {
        return entry.getDouble(lastValue);
    }
}
