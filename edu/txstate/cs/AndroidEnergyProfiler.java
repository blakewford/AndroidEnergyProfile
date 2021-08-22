package edu.txstate.cs;

import java.io.*;
import java.lang.Thread;
import java.lang.reflect.*;

public class AndroidEnergyProfiler
{
    public enum ResultStatus
    {
        UNKNOWN,
        VALID,
        INVALID
    }

    public class ProfileResult
    {
        public float EnergyUsed = 0f;
        public long ElapsedSeconds = 0L;
        public ResultStatus Validity = ResultStatus.UNKNOWN;
    }

    private class ProfilerThread extends Thread
    {
        private long mStart = 0L;
        private long mAmpIterator = 0L;
        private float mAmpAccumulator = 0L;
        private Object mBatteryProperties = null;
        private ProfileResult mResult = new ProfileResult();

        private long mPace = 0L;
        private volatile boolean mCancelled = false;

        ProfilerThread(long pace, Object properties)
        {
            mPace = pace;
            mBatteryProperties = properties;
        }

        public void loop()
        {
            Class cls = null;
            try{ cls = Class.forName("android.os.BatteryManager"); } catch(Exception e){}
            if(cls == null)
            {
                try{ cls = Class.forName("edu.txstate.cs.SimulatedBatteryManager"); } catch(Exception e){}
            }

            try
            {
                Method getLongProperty = cls.getMethod("getLongProperty", int.class);
                long value = (long)getLongProperty.invoke(mBatteryProperties, AndroidProperties.BATTERY_PROPERTY_STATUS);
                if(value == AndroidProperties.BATTERY_STATUS_DISCHARGING && mResult.Validity == ResultStatus.UNKNOWN)
                {
                    mResult.Validity = ResultStatus.VALID;
                }

                if(value != AndroidProperties.BATTERY_STATUS_DISCHARGING)
                {
                    mResult.Validity = ResultStatus.INVALID;
                }
                mAmpAccumulator += ((long)getLongProperty.invoke(mBatteryProperties, AndroidProperties.BATTERY_PROPERTY_CURRENT_NOW)/(1000f*1000f)); // to watts
                mAmpIterator++;
            }
            catch(Exception e)
            {
                System.out.println(e.toString());
            }
        }

        public void run()
        {
            mStart = System.currentTimeMillis();
            while(!mCancelled)
            {
                loop();
                try{ sleep(mPace); }catch(Exception e){}
            }

            mResult.ElapsedSeconds = (System.currentTimeMillis() - mStart)/1000; // seconds

            float averageAmps = mAmpAccumulator / mAmpIterator;
            float watts = averageAmps * AndroidProperties.BATTERY_PROPERTY_STATIC_VOLTAGE;
            mResult.EnergyUsed = Math.abs(watts * mResult.ElapsedSeconds);
        }

        public void cancel()
        {
            mCancelled = true;
        }

        public ProfileResult getResult()
        {
            return mResult;
        }
    }

    private ProfilerThread mThread = null;
    private void Initialize(Object properties)
    {
        mThread = new ProfilerThread(1000L, properties); // milliseconds, could be parameterized
        mThread.start();
    }

    private void Kill()
    {
        mThread.cancel();
        try{ mThread.join(); }catch(Exception e){}
    }

    private ProfileResult getResult()
    {
        return mThread.getResult();
    }

    private static AndroidEnergyProfiler mProfiler = null;

    public static void Start(Object properties)
    {
        if(mProfiler != null)
        {
            Stop();
        }

        mProfiler = new AndroidEnergyProfiler();
        mProfiler.Initialize(properties);
    }

    public static ProfileResult Stop()
    {
        if(mProfiler == null) return null;
        mProfiler.Kill();

        return mProfiler.getResult();
    }
}
