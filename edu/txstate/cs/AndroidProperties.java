package edu.txstate.cs;

public class AndroidProperties
{
    // Based on information at https://source.android.com/devices/tech/power/device

    // Very restricted. Requires specific hardware
    // BATTERY_PROPERTY_ENERGY_COUNTER   Remaining energy in nanowatt-hours

    // Generally available.
    // BATTERY_PROPERTY_CHARGE_COUNTER   Remaining battery capacity in microampere-hours
    // BATTERY_PROPERTY_CURRENT_NOW      Instantaneous battery current in microamperes

    // Pulled from https://developer.android.com/reference/android/os/BatteryManager

    public static final float BATTERY_PROPERTY_STATIC_VOLTAGE = 3.7f;

    public static final int BATTERY_PROPERTY_CHARGE_COUNTER = 1;
    public static final int BATTERY_PROPERTY_CURRENT_NOW    = 2;
    public static final int BATTERY_PROPERTY_ENERGY_COUNTER = 5;

    public static final int BATTERY_PROPERTY_STATUS = 6;
    public static final int BATTERY_STATUS_DISCHARGING = 3;
}
