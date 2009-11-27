namespace Magic.ERP.Orders
{
    using System;
    using System.Data;
    using System.Text;
    using System.Collections.Generic;
    using Magic.Framework.ORM;
    using Magic.Framework.ORM.Query;
    using Magic.ERP.Core;
    using Magic.Framework.Utils;

    public partial class INVCheckHead 
    {
        public static void ConfirmInvCheckOrder(ISession _session,string InvCheckNumber)
        {
            string strSQL = @"
SELECT SUM(A.StockQty) AS StockQty,A.LocationCode AS LocationCode,A.AreaCode AS AreaCode,A.SectionCode AS SectionCode,
        B.SKUID AS SKUID,B.StandardPrice AS Price,B.BasicUnit AS Unit
FROM StockDetail A
LEFT JOIN ItemSpec B ON A.SKUID=B.SKUID
WHERE A.AreaCode In (SELECT AreaCode FROM INVCheckWh WHERE OrderNumber=?)
GROUP BY A.LocationCode,A.AreaCode,A.SectionCode,
        B.SKUID,B.StandardPrice,B.BasicUnit";

            DataSet ds = _session.CreateObjectQuery(strSQL)
             .Attach(typeof(StockDetail))
             .Attach(typeof(Magic.Basis.ItemSpec))
             .Attach(typeof(Magic.Basis.ItemMaster))
             .Attach(typeof(INVCheckWh))
             .SetValue(0, InvCheckNumber, EntityManager.GetEntityMapping(typeof(INVCheckWh)).GetPropertyMapping("OrderNumber").DbTypeInfo)
             .DataSet();

            INVCheckHead objHead = new INVCheckHead();
            objHead.OrderNumber = InvCheckNumber;
            objHead.Status = INVCheckStatus.Confirm;
            objHead.Update(_session, "Status");

            for (int i = 0; i < ds.Tables[0].Rows.Count; i++)
            {
               INVCheckLine objItem = new INVCheckLine();
               objItem.AreaCode = Cast.String(ds.Tables[0].Rows[i]["AreaCode"]);
               objItem.BeforeQty = Cast.Decimal(ds.Tables[0].Rows[i]["StockQty"], 0M);
               objItem.CurrentQty = objItem.BeforeQty;
               objItem.LineNumber = objHead.NextLineNumber();
               objItem.LocationCode = Cast.String(ds.Tables[0].Rows[i]["LocationCode"]);
               objItem.OrderNumber = InvCheckNumber;
               objItem.SectionCode = Cast.String(ds.Tables[0].Rows[i]["SectionCode"]);
               objItem.SKUID = Cast.Int(ds.Tables[0].Rows[i]["SKUID"], 0);
               objItem.Create(_session);
            }
        }

        public static void DeleteInvCheckOrder(ISession _session, string InvCheckNumber)
        {
            if (_session.CreateEntityQuery<INVCheckHead>().Where(Exp.Eq("OrderNumber", InvCheckNumber)).And(Exp.Eq("Status", 1)).List<INVCheckHead>().Count == 0)
            {
                throw new Exception("库存盘点单不存在或已经确认!");
            }

            _session.CreateEntityQuery<INVCheckHead>().Where(Exp.Eq("OrderNumber", InvCheckNumber)).Delete();
            _session.CreateEntityQuery<INVCheckLine>().Where(Exp.Eq("OrderNumber", InvCheckNumber)).Delete();
            _session.CreateEntityQuery<INVCheckWh>().Where(Exp.Eq("OrderNumber", InvCheckNumber)).Delete();
        }
    }
}
