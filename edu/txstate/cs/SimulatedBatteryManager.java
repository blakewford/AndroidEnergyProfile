package edu.txstate.cs;

public class SimulatedBatteryManager
{
    public long getLongProperty(int property)
    {
        long value = 0L;
        switch(property)
        {
            case AndroidProperties.BATTERY_PROPERTY_STATUS:
                value = AndroidProperties.BATTERY_STATUS_DISCHARGING;
                break;
            case AndroidProperties.BATTERY_PROPERTY_CURRENT_NOW:
                value = -200 * 1000; // microamperes
                break;
        }

        return value;
    }
}
