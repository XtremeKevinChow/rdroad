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
            //�Ժ���Ըĳɰ������õĹ���������
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
                throw new Exception("�޷��Կ���ڼ�" + this.PeriodCode + "����");
            INVPeriod openPeriod = GetOpenPeriod(session);
            //TODO: ��ֿ�ʱ����ڼ䲻һ���Ĵ���
            if (openPeriod != null)
                throw new Exception("��һ������ڼ�" + openPeriod.PeriodCode + "�Ѿ����ʣ�ϵͳֻ����һ�����ʵĿ���ڼ�");
            this.Status = INVPeriodStatus.Open;
            this.Update(session, "Status");
        }
        public void Close(ISession session)
        {
            if (this.Status != INVPeriodStatus.Open)
                throw new Exception("�޷��رտ���ڼ�" + this.PeriodCode);
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