namespace Magic.ERP.Core
{
    using System.Collections.Generic;
    using Magic.Framework.ORM;
    using Magic.Framework.ORM.Query;

    public partial class OrderStatusDef
	{
        public static OrderStatusDef Retrieve(ISession session, string orderTypeCode, int statusValue)
        {
            IList<OrderStatusDef> lists = session.CreateEntityQuery<OrderStatusDef>()
                .Where(Exp.Eq("OrderTypeCode", orderTypeCode) & Exp.Eq("StatusValue", statusValue))
                .List<OrderStatusDef>();
            if (lists == null || lists.Count <= 0) return null;
            return lists[0];
        }
	}
}