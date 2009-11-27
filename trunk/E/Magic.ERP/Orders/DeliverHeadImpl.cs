namespace Magic.ERP.Orders
{
    using System;
    using System.Collections.Generic;
    using Magic.Framework.ORM;
    using Magic.Framework.ORM.Query;

    public partial class DeliverHead : IApprovable, IWHTransHead
    {
        /// <summary>
        /// ��һ���к���
        /// </summary>
        /// <returns></returns>
        public virtual string NextLineNumber()
        {
            int number = Magic.Framework.Utils.Cast.Int(this.CurrentLineNumber);
            number = number % 10 == 0 ? number + 10 : ((number + 9) / 10 * 10);
            string result = number.ToString().PadLeft(4, '0');
            this.CurrentLineNumber = result;
            return result;
        }

        /// <summary>
        /// ǩ�����
        /// </summary>
        /// <param name="session"></param>
        public virtual void OnApprove(ISession session)
        {
            if (this.ApproveResult == ApproveStatus.Approve)
            {
                this.Status = DeliverStatus.Open;
                this.Update(session);

                //using (ISession _session = new Session())
                //{
                //    _session.BeginTransaction();
                //    try
                //    {
                //        ERPUtil.CommitWHTrans(_session, this);
                //        _session.Commit();
                //    }
                //    catch (Exception ex)
                //    {
                //        _session.Rollback();
                //    }
                //}
            }

            if (this.ApproveResult == ApproveStatus.Reject)
            {
                this.Status = DeliverStatus.New;
                this.Update(session);
            }
        }

        /// <summary>
        /// ǩ�����
        /// </summary>
        /// <param name="session"></param>
        public virtual void PostApprove(ISession session)
        {
            ERPUtil.CommitWHTrans(session, this);  
        }

        /// <summary>
        /// ���õ������ͣ�ֱ�Ӵ���ĳ�����׵ĵ������ͣ�
        /// �����Ա�˻������õ�������Ϊ����������
        /// </summary>
        public virtual string RefOrderType
        {
            get
            {
                throw new Exception("not implemented");
            }
        }

        /// <summary>
        /// ԭʼ��������
        /// �����Ա�˻���ԭʼ��������Ϊ��Ա���������۶���������
        /// </summary>
        public virtual string OriginalOrderType
        {
            get { return "SO0"; }
        }

        /// <summary>
        /// ����ִ����ϵĻص�����
        /// </summary>
        /// <param name="session"></param>
        public virtual void AfterTransaction(ISession session)
        {
            this.Status = DeliverStatus.Close;
            this.DeliverDate = System.DateTime.Now;
            this.Update(session, "Status", "DeliverDate");
        }

        /// <summary>
        /// ȡ������ϸ
        /// </summary>
        /// <param name="session"></param>
        /// <returns></returns>
        public virtual IList<IWHTransLine> GetLines(ISession session)
        {
            IList<DeliverLine> lines = session.CreateEntityQuery<DeliverLine>()
                .Where(Exp.Eq("OrderNumber", this.OrderNumber))
                .OrderBy("SKUID").OrderBy("LineNumber")
                .List<DeliverLine>();
            IList<IWHTransLine> result = new List<IWHTransLine>(lines.Count);
            foreach (DeliverLine line in lines)
                result.Add(line);
            return result;
        }
    }
}