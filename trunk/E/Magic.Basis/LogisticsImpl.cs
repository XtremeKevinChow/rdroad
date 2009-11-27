namespace Magic.Basis
{
    using System.Collections.Generic;
    using Magic.Framework.Utils;
    using Magic.Framework.ORM;
    using Magic.Framework.ORM.Query;

    public partial class Logistics
	{
        public static string LogisticsStatusText(LogisticsStatus statusValue)
        {
            switch (statusValue)
            {
                case LogisticsStatus.Delete: return "ɾ��";
                case LogisticsStatus.Enable: return "����";
                case LogisticsStatus.Disable: return "����";
            }
            return "";
        }
        public static string LogisticsStatusText(object statusValue)
        {
            return LogisticsStatusText(Cast.Enum<LogisticsStatus>(statusValue));
        }
        public static IList<Logistics> GetEffectiveLogistics(ISession session)
        {
            return session.CreateEntityQuery<Logistics>()
                .Where(Exp.Eq("Status", LogisticsStatus.Enable))
                .OrderBy("ShortName")
                .List<Logistics>();
        }
	}
}
