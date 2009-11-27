namespace Magic.ERP.Core
{
    using System.Collections.Generic;
    using Magic.Framework.ORM;
    using Magic.Framework.ORM.Query;

    public partial class OrderTransDef
	{
        public static OrderTransDef Retrieve(ISession session, string orderTypeCode, int stepIndex)
        {
            IList<OrderTransDef> lists = session.CreateEntityQuery<OrderTransDef>()
                .Where(Exp.Eq("OrderTypeCode", orderTypeCode) & Exp.Eq("StepIndex", stepIndex))
                .List<OrderTransDef>();
            if (lists == null || lists.Count <= 0) return null;
            return lists[0];
        }
	}
}