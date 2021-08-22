import java.lang.*;

import edu.txstate.cs.*;

class Test
{
    public static void main(String[] args)
    {
        AndroidEnergyProfiler.Start(new SimulatedBatteryManager());
        try{ Thread.sleep(30000); } catch(Exception e){}

        AndroidEnergyProfiler.ProfileResult result = AndroidEnergyProfiler.Stop();
        if(result.Validity == AndroidEnergyProfiler.ResultStatus.VALID)
        {
            System.out.println("Result is " + result.Validity + ", system used " + result.EnergyUsed + "J over " + result.ElapsedSeconds + " seconds.");
        }
        else
        {
            System.out.println("Invalid Result");
        }
    }
}
