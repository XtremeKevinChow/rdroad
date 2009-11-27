namespace Magic.ERP.Orders
{
    using System;
    using System.Collections.Generic;
    using Magic.Framework.Data;
    using Magic.Framework.ORM;
    using Magic.Framework.ORM.Query;
    using Magic.Framework.ORM.Mapping;

    public partial class POHead : IApprovable
    {
        public const string ORDER_TYPE = "PO0";

        /// <summary>
        /// ��һ���к���
        /// </summary>
        /// <returns></returns>
        public virtual string NextLineNumber()
        {
            int number = Magic.Framework.Utils.Cast.Int(this.CurrentLineNumber);
            //number = number % 10 == 0 ? number + 10 : ((number + 9) / 10 * 10);
            string result = (number+1).ToString().PadLeft(4, '0');
            this.CurrentLineNumber = result;
            return result;
        }

        /// <summary>
        /// ǩ��ʱ�Ĵ���
        /// </summary>
        /// <param name="session"></param>
        void IApprovable.OnApprove(ISession session)
        {
            if (this.ApproveResult == ApproveStatus.Reject)
            {
                this.Status = POStatus.New;
                this.Update(session, "Status");
            }
        }
        void IApprovable.PostApprove(ISession session)
        {
        }
        /// <summary>
        /// ������������
        /// </summary>
        public string OrderTypeCode
        {
            get
            {
                return ORDER_TYPE;
            }
        }

        public static string POStatusText(POStatus statusValue)
        {
            switch (statusValue)
            {
                case POStatus.New: return "�½�";
                case POStatus.Release: return "����";
                case POStatus.Close: return "�����";
            }
            return "";
        }

        public static string ApproveStatusText(ApproveStatus statusValue)
        {
            switch (statusValue)
            {
                case ApproveStatus.Reject: return "�ܾ�";
                case ApproveStatus.UnApprove: return "δǩ��";
                case ApproveStatus.Approve: return "��ǩ��";
            }
            return "";
        }
        public void UpdateLines(ISession session, IList<POLine> linesToSave)
        {
        }
        public void Release(ISession session)
        {
            if (this.Status != POStatus.New)
                throw new Exception(string.Format("�ɹ�����{0}�����½�״̬���޷�ִ�з�������", this.OrderNumber));
            IList<POLine> lines = session.CreateEntityQuery<POLine>()
                .Where(Exp.Eq("OrderNumber", this.OrderNumber))
                .List<POLine>();
            if (lines == null || lines.Count <= 0) throw new Exception(string.Format("�ɹ�����{0}û����ϸ�����ܷ���", this.OrderNumber));
            System.Text.StringBuilder builder = new System.Text.StringBuilder();
            foreach (POLine line in lines)
                if (line.PurchaseQty <= 0M || line.Price <= 0M)
                    builder.Append("��").Append(line.LineNumber).Append("��Ч��������۸�, ");
            if (builder.Length > 0)
                throw new Exception(builder.ToString());
            this.Status = POStatus.Release;
            this.ApproveResult = ApproveStatus.UnApprove;
            this.Update(session, "Status", "ApproveResult");
            ERPUtil.ApproveThis(session, this); //��ǩ����
        }
        public void Close(ISession session)
        {
            if (this.Status != POStatus.Release || this.ApproveResult != ApproveStatus.Approve)
                throw new Exception(string.Format("�ɹ�����{0}û�з�������ǩ�ˣ��޷�ִ�йرղ���", this.OrderNumber));
            this.Status = POStatus.Close;
            this.Update(session, "Status");
            IList<POLine> lines = session.CreateEntityQuery<POLine>()
                .Where(Exp.Eq("OrderNumber", this.OrderNumber))
                .And(Exp.Eq("LineStatus", POLineStatus.Open))
                .List<POLine>();
            foreach (POLine line in lines)
            {
                line.LineStatus = POLineStatus.Close;
                line.Update(session, "LineStatus");
            }
        }
        public static IList<POLine> ReceivableLines(ISession session, string orderNumber)
        {
            return session.CreateObjectQuery(@"
select 1
from POLine l
where l.OrderNumber=?orderNumber and l.LineStatus=?lineStatus
    and l.PurchaseQty-l.ReceiveQty-l.UnfinishedReceiveQty>0
order by l.LineNumber")
                .Attach(typeof(POLine))
                .SetValue("?orderNumber", orderNumber, "l.OrderNumber")
                .SetValue("?lineStatus", POLineStatus.Open, "l.LineStatus")
                .List<POLine>();
        }
        public static IList<POLine> PipelineInvQuery(ISession session, int skuId)
        {
            return session.CreateObjectQuery(@"
select 1
from POHead h
inner join POLine l on h.OrderNumber=l.OrderNumber
where h.Status=?release and l.LineStatus=?open and l.SKUID=?skuid
order by l.PlanDate desc")
                .Attach(typeof(POHead)).Attach(typeof(POLine))
                .SetValue("?release", POStatus.Release, "h.Status")
                .SetValue("?open", POLineStatus.Open, "l.LineStatus")
                .SetValue("?skuid", skuId, "l.SKUID")
                .List<POLine>();
        }
    }
}