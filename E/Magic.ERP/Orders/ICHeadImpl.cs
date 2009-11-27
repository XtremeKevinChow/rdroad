namespace Magic.ERP.Orders
{
    using System;
    using System.Collections.Generic;
    using Magic.Framework.Data;
    using Magic.Framework.ORM;
    using Magic.Framework.ORM.Mapping;
    using Magic.Framework.ORM.Query;
    using Magic.Framework;
    using Magic.Framework.Utils;
    using Magic.Basis;

    public partial class ICHead : IApprovable
    {
        log4net.ILog log = log4net.LogManager.GetLogger(typeof(ICHead));

        public const string ORDER_TYPE = "IC0";

        public virtual string NextLineNumber()
        {
            int number = Magic.Framework.Utils.Cast.Int(this.CurrentLineNumber);
            //number = number % 10 == 0 ? number + 10 : ((number + 9) / 10 * 10);
            number++;
            string result = number.ToString().PadLeft(4, '0');
            this.CurrentLineNumber = result;
            return result;
        }

        public virtual void OnApprove(ISession session)
        {
            if (this.ApproveResult == ApproveStatus.Approve)
            {
                this.Status = InterchangeStatus.Open;
                this.Update(session);
            }

            if (this.ApproveResult == ApproveStatus.Reject)
            {
                this.Status = InterchangeStatus.New;
                this.Update(session);
            }
        }
        public virtual void PostApprove(ISession session)
        {
            try
            {
                session.BeginTransaction();
                try
                {
                    this.Close(session);
                    session.Commit();
                }
                catch (Exception er2)
                {
                    session.Rollback();
                    log.Error("���ӵ�" + this._orderNumber + "ǩ����ϣ������Զ��ر�ʱ�����쳣", er2);
                    return;
                }
            }
            catch (Exception er)
            {
                log.Error("���ӵ�" + this._orderNumber + "ǩ����ϣ������Զ��ر�ʱ�����쳣", er);
                return;
            }
        }

        public void Release(ISession session)
        {
            if (this._status != InterchangeStatus.New)
                throw new Exception("���ӵ�" + this._orderNumber + "�����½�״̬���޷�����");
            if (session.CreateEntityQuery<ICLine>()
                .Where(Exp.Eq("OrderNumber", this._orderNumber))
                .Count() <= 0)
                throw new Exception("���ӵ�û����ϸ���޷�����");
            this._status = InterchangeStatus.Release;
            this._approveResult = ApproveStatus.UnApprove;
            this.Update(session, "Status", "ApproveResult");
            ERPUtil.ApproveThis(session, this);
        }
        public void Close(ISession session)
        {
            if (this._status != InterchangeStatus.Open)
                throw new Exception("���ӵ�û��ǩ�ˣ��޷��ر�");

            IList<CRMSN> sns = session.CreateObjectQuery(@"
select 1 from CRMSN a inner join ICLine b on a.OrderNumber=b.RefOrderNumber 
where b.OrderNumber=?ordNum 
order by b.LineNumber")
                .Attach(typeof(CRMSN)).Attach(typeof(ICLine))
                .SetValue("?ordNum", this._orderNumber, "b.OrderNumber")
                .List<CRMSN>();
            foreach (CRMSN sn in sns)
                sn.InterchangeFinish(session);
            this._status = InterchangeStatus.Close;
            this.Update(session, "Status");
        }
        public void AddLines(ISession session, string[] sns)
        {
            if (sns == null || sns.Length <= 0) return;
            foreach (string s in sns)
            {
                if (string.IsNullOrEmpty(s) || s.Length <= 0) continue;
                CRMSN sn = CRMSN.Retrieve(session, s);
                if (sn == null) throw new Exception("������" + s + "������");
                this.AddLine(session, sn);
            }
        }
        public void AddLine(ISession session, CRMSN sn)
        {
            if (sn == null) return;
            if (sn.Status != CRMSNStatus.Checked && sn.Status != CRMSNStatus.Packaged)
                throw new Exception("ֻ���Ѿ��˻�����װ�ķ��������ܽ��н���");
            if (this.LogisticCompID != sn.LogisticsID)
                throw new Exception("��������װʱѡ���������˾�뽻�ӵ���������˾��ͬ");
            IList<ICHead> icHeads = session.CreateObjectQuery(@"
select 1 from ICHead h inner join ICLine l on h.OrderNumber=l.OrderNumber
where l.RefOrderNumber=?snno
")
                .Attach(typeof(ICHead)).Attach(typeof(ICLine))
                .And(Exp.In("h.Status", InterchangeStatus.New, InterchangeStatus.Release, InterchangeStatus.Open))
                .SetValue("?snno", sn.OrderNumber, "l.RefOrderNumber")
                .List<ICHead>();
            if (icHeads != null && icHeads.Count > 0)
                throw new Exception("������" + sn.OrderNumber + "�Ѿ����뵽���ӵ�" + icHeads[0].OrderNumber + "����");
            ICLine line = new ICLine();
            line.OrderNumber = this.OrderNumber;
            line.LineNumber = this.NextLineNumber();
            line.OrderTypeCode = " ";
            line.RefOrderNumber = sn.OrderNumber;
            line.Create(session);
        }
        public static SimpleJson AddLine(ISession session, string icNumber, string snNumber)
        {
            if (string.IsNullOrEmpty(icNumber) || string.IsNullOrEmpty(snNumber))
                throw new Exception("��Ч����");
            ICHead head = ICHead.Retrieve(session, icNumber);
            if (head == null) throw new Exception("���ӵ�" + icNumber + "������");
            CRMSN sn = CRMSN.Retrieve(session, snNumber);
            if (sn == null) throw new Exception("������" + snNumber + "������");
            head.AddLine(session, sn);
            return sn.ToJSon(session);
        }
        public void AutoGenerateDetail(ISession session)
        {
            if (this.Status != InterchangeStatus.New)
                throw new Exception("�����������½�״̬���޷��޸�");
            if (this.LogisticCompID <= 0)
                throw new Exception("���ӵ��ϵ�������˾��Ч");
            IList<CRMSN> sns = CRMSN.InterchangableList(session, this.LogisticCompID);
            foreach (CRMSN sn in sns)
            {
                ICLine line = new ICLine();
                line.OrderNumber = this.OrderNumber;
                line.LineNumber = this.NextLineNumber();
                line.OrderTypeCode = " ";
                line.RefOrderNumber = sn.OrderNumber;
                line.Create(session);
            }
        }
        public int TotalPackageCount(ISession session)
        {
            return Cast.Int(session.CreateObjectQuery(@"
select sum(b.PackageCount)
from ICLine a
inner join CRMSN b on a.RefOrderNumber=b.OrderNumber
where a.OrderNumber=?icNumber")
                .Attach(typeof(ICLine)).Attach(typeof(CRMSN))
                .SetValue("?icNumber", this.OrderNumber, "a.OrderNumber")
                .DataSet().Tables[0].Rows[0][0], 0);
        }
        public static ICHead Query(ISession session, string snNumber)
        {
            IList<ICLine> lines = session.CreateEntityQuery<ICLine>()
                .Where(Exp.Eq("RefOrderNumber", snNumber))
                .List<ICLine>();
            if (lines == null || lines.Count <= 0) return null;
            return ICHead.Retrieve(session, lines[0].OrderNumber);
        }
    }
}