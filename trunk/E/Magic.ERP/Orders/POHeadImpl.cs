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
        /// 下一个行号码
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
        /// 签核时的处理
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
        /// 订单单据类型
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
                case POStatus.New: return "新建";
                case POStatus.Release: return "发布";
                case POStatus.Close: return "已完成";
            }
            return "";
        }

        public static string ApproveStatusText(ApproveStatus statusValue)
        {
            switch (statusValue)
            {
                case ApproveStatus.Reject: return "拒绝";
                case ApproveStatus.UnApprove: return "未签核";
                case ApproveStatus.Approve: return "已签核";
            }
            return "";
        }
        public void UpdateLines(ISession session, IList<POLine> linesToSave)
        {
        }
        public void Release(ISession session)
        {
            if (this.Status != POStatus.New)
                throw new Exception(string.Format("采购订单{0}不是新建状态，无法执行发布操作", this.OrderNumber));
            IList<POLine> lines = session.CreateEntityQuery<POLine>()
                .Where(Exp.Eq("OrderNumber", this.OrderNumber))
                .List<POLine>();
            if (lines == null || lines.Count <= 0) throw new Exception(string.Format("采购订单{0}没有明细，不能发布", this.OrderNumber));
            System.Text.StringBuilder builder = new System.Text.StringBuilder();
            foreach (POLine line in lines)
                if (line.PurchaseQty <= 0M || line.Price <= 0M)
                    builder.Append("行").Append(line.LineNumber).Append("无效的数量或价格, ");
            if (builder.Length > 0)
                throw new Exception(builder.ToString());
            this.Status = POStatus.Release;
            this.ApproveResult = ApproveStatus.UnApprove;
            this.Update(session, "Status", "ApproveResult");
            ERPUtil.ApproveThis(session, this); //送签单据
        }
        public void Close(ISession session)
        {
            if (this.Status != POStatus.Release || this.ApproveResult != ApproveStatus.Approve)
                throw new Exception(string.Format("采购订单{0}没有发布或者签核，无法执行关闭操作", this.OrderNumber));
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