namespace Magic.Basis
{
    using System;
    using System.Collections.Generic;
    using Magic.Framework.Data;
    using Magic.Framework.ORM;
    using Magic.Framework.ORM.Query;

    public partial class INVPeriod : IEntity
    {
        public static INVPeriod GetOpenPeriod(ISession session)
        {
            IList<INVPeriod> periods = session.CreateEntityQuery<INVPeriod>()
                .Where(Exp.Eq("Status", INVPeriodStatus.Open))
                .List<INVPeriod>();
            return periods.Count > 0 ? periods[0] : null;
        }
        public static INVPeriod GetPeriod(ISession session, DateTime date)
        {
            DateTime dt = new DateTime(date.Year, date.Month, date.Day);
            IList<INVPeriod> periods = session.CreateEntityQuery<INVPeriod>()
                .Where(Exp.Ge("EndDate", dt) & Exp.Le("StartingDate", dt))
                .List<INVPeriod>();
            return periods.Count > 0 ? periods[0] : null;
        }
        public static INVPeriod CreatePeriod(ISession session, DateTime date)
        {
            DateTime nowDay = new DateTime(date.Year, date.Month, date.Day);
            //以后可以改成按照配置的规则来计算
            DateTime firstDay = new DateTime(date.Year, date.Month, 1);
            DateTime lastDay = firstDay.AddMonths(1).AddDays(-1);

            INVPeriod period = new INVPeriod();
            period.Type = INVPeriodType.Month;
            period.Status = INVPeriodStatus.New;
            period.Year = nowDay.Year;
            period.Index = nowDay.Month;
            period.PeriodCode = period.Year.ToString() + period.Index.ToString().PadLeft(2, '0');
            period.StartingDate = firstDay;
            period.EndDate = lastDay;
            period.BalanceFinished = false;
            period.Create(session);

            return period;
        }
        public void Open(ISession session)
        {
            if (this.Status != INVPeriodStatus.New)
                throw new Exception("无法对库存期间" + this.PeriodCode + "开帐");
            INVPeriod openPeriod = GetOpenPeriod(session);
            //TODO: 多仓库时库存期间不一样的处理
            if (openPeriod != null)
                throw new Exception("另一个库存期间" + openPeriod.PeriodCode + "已经开帐，系统只能有一个开帐的库存期间");
            this.Status = INVPeriodStatus.Open;
            this.Update(session, "Status");
        }
        public void Close(ISession session)
        {
            if (this.Status != INVPeriodStatus.Open)
                throw new Exception("无法关闭库存期间" + this.PeriodCode);
            this.Status = INVPeriodStatus.Close;
            this.Update(session, "Status");
        }
        public static IList<INVPeriod> ClosedPeriods(ISession session)
        {
            return session.CreateEntityQuery<INVPeriod>()
                .Where(Exp.Eq("Status", INVPeriodStatus.Close))
                .OrderBy("StartingDate", Order.Desc)
                .List<INVPeriod>();
        }
    }
}