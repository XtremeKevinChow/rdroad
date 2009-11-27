namespace Magic.Basis
{
    using System;
    using System.Data;
    using Magic.Framework.Data;
    using Magic.Framework.ORM;

    public partial class StockPeriodBalance : IEntity
    {
        public static int CreatePeriodBalance(ISession session, INVPeriod openPeriod)
        {
            DbSession dbsession = session.DbSession as DbSession;
            IDbCommand command = dbsession.CreateSqlStringCommand(@"
Insert Into inv_stock_balance(blnc_id, pd_id, sku_id, loc_code, area_code, sec_code, lot_num, stock_qty)
Select seq_inv_stock_balance.nextval,:pid,sku_id,loc_code,area_code,sec_code,lot_num,stock_qty
From inv_stock_detail
Where stock_qty<>0");
            dbsession.AddParameter(command, ":pid", DbTypeInfo.Int32(), openPeriod.PeriodID);
            dbsession.ExecuteNonQuery(command);

            INVPeriod prev = INVPeriod.GetPeriod(session, openPeriod.StartingDate.AddDays(-1));
            command = dbsession.CreateStoredProcCommand("f_rpt_inv_balance", new object[] { openPeriod.PeriodID, (prev == null ? 0 : prev.PeriodID) });
            dbsession.ExecuteNonQuery(command);
            return 1;
        }
    }
}