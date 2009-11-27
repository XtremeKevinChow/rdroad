namespace Magic.ERP.Core
{
    using System.Collections.Generic;
    using Magic.Framework.ORM;
    using Magic.Framework.ORM.Query;

    public partial class WHAreaCfg
	{
        public static IList<WHAreaCfg> Retrieve(ISession session, string cfgCode)
        {
            return session.CreateEntityQuery<WHAreaCfg>()
                .Where(Exp.Eq("AreaCfgCode", cfgCode))
                .List<WHAreaCfg>();
        }
	}
}