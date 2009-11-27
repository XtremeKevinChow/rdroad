using System;

/// <summary>
/// Summary description for DiagnoseTimeUtil
/// </summary>
public class DiagnoseTimeUtil
{
    static DiagnoseTimeUtil()
    {
      
       
    }

    public static void GetNextHospitalWorkDate(DateTime currentDate, out DateTime beginDate, out DateTime endDate)
    {
        beginDate = currentDate.AddDays(1);
        endDate = currentDate.AddDays(1);
        if (currentDate.DayOfWeek == DayOfWeek.Friday)
        {
            beginDate = currentDate.AddDays(1);
            endDate = currentDate.AddDays(3);
        }
        else if (currentDate.DayOfWeek == DayOfWeek.Saturday)
        {
            beginDate = currentDate.AddDays(1);
            endDate = currentDate.AddDays(2);
        }       
        else
        {
            beginDate = currentDate.AddDays(1);
            endDate = currentDate.AddDays(1);
        }
    
    }
}
