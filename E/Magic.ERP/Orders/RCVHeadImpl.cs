namespace Magic.ERP.Orders
{
    using System;
    using System.Data;
    using System.Collections.Generic;
    using Magic.Framework.ORM;
    using Magic.Framework.ORM.Query;
    using Magic.Framework.Data;
    using Magic.ERP.Core;
    using Magic.Framework;
    using Magic.Framework.Utils;

    public partial class RCVHead : IApprovable, IWHTransHead
    {
        private static log4net.ILog log = log4net.LogManager.GetLogger(typeof(RCVHead));

        public const string ORD_TYPE_PUR = "RC1";
        public const string ORD_TYPE_RTN = "RC2";
        public const string ORD_TYPE_EXCHG = "RC3";

        bool IWHTransHead.PreLockStock
        {
            get { return false; }
        }

        /// <summary>
        /// ��һ���к���
        /// </summary>
        /// <returns></returns>
        public virtual string NextLineNumber()
        {
            int number = Magic.Framework.Utils.Cast.Int(this.CurrentLineNumber);
            //number = number % 10 == 0 ? number + 10 : ((number + 9) / 10 * 10);
            number++;
            string result = number.ToString().PadLeft(4, '0');
            this.CurrentLineNumber = result;
            return result;
        }

        /// <summary>
        /// ǩ��ʱ�Ĵ���
        /// </summary>
        /// <param name="session"></param>
        void IApprovable.OnApprove(ISession session)
        {
            if (this._approveResult == ApproveStatus.Approve)
            {
                //ǩ��ͨ��������Ϊ������״̬��Ȼ���Թر��ջ���
                this._status = ReceiveStatus.Open;  //����״̬
                this.Update(session, "Status");
            }
            else
            {
                //���أ����Ļ��½�״̬
                this._status = ReceiveStatus.New;
                this.Update(session, "Status");
            }
        }

        /// <summary>
        /// ǩ����ɺ�Ĵ���
        /// </summary>
        /// <param name="session"></param>
        void IApprovable.PostApprove(ISession session)
        {
            //TODO: �Ƿ�ʹ�����ÿ��������Ϊ
            RCVHead.Close(this); //ʹ���µ�session��ɹرղ���������رղ��������쳣ʱ���ǩ�˴����޷����
        }

        /// <summary>
        /// ����ִ����ϵĻص�����
        /// </summary>
        /// <param name="session"></param>
        void IWHTransHead.AfterTransaction(ISession session)
        {
        }

        /// <summary>
        /// ȡ������ϸ
        /// </summary>
        /// <param name="session"></param>
        /// <returns></returns>
        IList<IWHTransLine> IWHTransHead.GetLines(ISession session)
        {
            IList<RCVLine> lines = session.CreateEntityQuery<RCVLine>()
                .Where(Exp.Eq("OrderNumber", this._orderNumber))
                .OrderBy("SKUID").OrderBy("LineNumber")
                .List<RCVLine>();
            IList<IWHTransLine> result = new List<IWHTransLine>(lines.Count);
            foreach (RCVLine rcv in lines)
                result.Add(rcv);
            return result;
        }

        /// <summary>
        /// ɾ���ջ���������ϸ
        /// </summary>
        /// <param name="session"></param>
        /// <returns></returns>
        public int DeleteLines(ISession session)
        {
            //�ɹ��ջ���ɾ��ʱ��Ҫ�ָ��ɹ�������ϸ�������ֶ�ֵ
            IList<RCVLine> lines = session.CreateEntityQuery<RCVLine>()
                .Where(Exp.Eq("OrderNumber", this.OrderNumber))
                .List<RCVLine>();
            return this.DeleteLines(session, lines);
        }
        /// <summary>
        /// ɾ���ջ���ָ����ϸ
        /// </summary>
        /// <param name="session"></param>
        /// <param name="lienesToDelete"></param>
        /// <returns></returns>
        public int DeleteLines(ISession session, IList<RCVLine> lienesToDelete)
        {
            if (lienesToDelete == null || lienesToDelete.Count <= 0) return 0;
            if (this._status != ReceiveStatus.New)
                throw new Exception("�ջ��������½�״̬���޷�ɾ����ϸ");

            int count = 0;
            foreach (RCVLine ltd in lienesToDelete)
            {
                if (this._orderTypeCode == RCVHead.ORD_TYPE_PUR)
                {   //�ɹ��ջ���ɾ����ϸʱ��Ҫ�ָ��ɹ������������ֶ�ֵ
                    POLine poLine = null;
                    if (!string.IsNullOrEmpty(this._refOrderNumber) && this._refOrderNumber.Trim().Length > 0
                        && !string.IsNullOrEmpty(ltd.RefOrderLine) && ltd.RefOrderLine.Trim().Length > 0)
                        poLine = POLine.Retrieve(session, this._refOrderNumber, ltd.RefOrderLine);
                    if (poLine != null) poLine.UnfinishedReceiveQtyChange(session, -ltd.QualifiedQty);
                }
                ltd.Delete(session);

                count++;
            }

            return count;
        }
        /// <summary>
        /// ����ջ���ϸ������ָ���ɹ����������п��ջ���ϸ
        /// </summary>
        /// <param name="session"></param>
        /// <param name="poNumber"></param>
        /// <returns></returns>
        public int AddLinesFromRefOrder(ISession session)
        {
            int count = 0;
            switch (this._orderTypeCode)
            {
                case RCVHead.ORD_TYPE_PUR:
                    foreach (POLine poLine in POHead.ReceivableLines(session, this._refOrderNumber))
                        if (this.AddLine(session, poLine)) count++;
                    break;
            }
            return count;
        }
        /// <summary>
        /// ����ջ���ϸ������POLine��Ϣ
        /// </summary>
        /// <param name="session"></param>
        /// <param name="poLine"></param>
        /// <returns></returns>
        public bool AddLine(ISession session, POLine poLine)
        {
            if (poLine.ReceivableQty() <= 0M) return false;
            if (!poLine.UnfinishedReceiveQtyChange(session, poLine.ReceivableQty())) return false;

            RCVLine line = new RCVLine();
            line.OrderNumber = this.OrderNumber;
            line.LineNumber = this.NextLineNumber();
            line.TransTypeCode = " ";
            line.LocationCode = this.LocationCode;
            line.SKUID = poLine.SKUID;
            line.UnitID = poLine.UnitID;
            line.RefQty = poLine.ReceivableQty();
            line.RCVTotalQty = poLine.ReceivableQty();
            line.QualifiedQty = poLine.ReceivableQty();
            line.UnQualifiedQty = 0M;
            line.RefOrderLine = line.OriginalOrderLine = poLine.LineNumber;
            line.TaxValue = 0M;  // poLine.TaxValue; ϵͳĬ�Ͻ���˰��������˰�ֿۣ����Խ���˰������Ϊ0��������ֹ�ȷ�����Եֿ۵Ľ���˰��
            line.Price = poLine.Price;
            return line.Create(session);
        }
        public SimpleJson AddLine(ISession session, string poLineNumber, string areaCode, string sectionCode, decimal qty)
        {
            //���
            if (string.IsNullOrEmpty(poLineNumber) || poLineNumber.Trim().Length <= 0)
                return new SimpleJson().HandleError("PO��Ϊ��");
            if (string.IsNullOrEmpty(areaCode) || areaCode.Trim().Length <= 0)
                return new SimpleJson().HandleError("��λΪ��");
            if (!string.IsNullOrEmpty(sectionCode) && sectionCode.Trim().Length > 0)
            {
                WHSection section = WHSection.Retrieve(session, areaCode, sectionCode);
                if (session == null)
                    return new SimpleJson()
                        .HandleError("��λ" + areaCode.Trim().ToUpper() + "�еĻ���" + sectionCode.Trim().ToUpper() + "������");
            }
            if (qty <= 0) return new SimpleJson().HandleError("�ջ�����" + qty.ToString() + "С��0");
            POLine poLine = POLine.Retrieve(session, this.RefOrderNumber, poLineNumber);
            if (poLine == null) return new SimpleJson().HandleError("PO " + this.RefOrderNumber + "�в�����" + poLineNumber + "����");
            if (poLine.ReceivableQty() <= 0M)
                return new SimpleJson().HandleError("����" + this.RefOrderNumber + "��" + poLineNumber + "���ջ�����Ϊ0");
            if (!poLine.UnfinishedReceiveQtyChange(session, qty))
                return new SimpleJson().HandleError("�޷����¶���" + this.RefOrderNumber + "��" + poLineNumber + "�Ĵ��������");

            RCVLine line = new RCVLine();
            line.OrderNumber = this.OrderNumber;
            line.LineNumber = this.NextLineNumber();
            line.TransTypeCode = " ";
            line.LocationCode = this.LocationCode;
            line.AreaCode = areaCode.Trim().ToUpper();
            line.SectionCode = string.IsNullOrEmpty(sectionCode) ? " " : sectionCode.Trim().ToUpper();
            line.SKUID = poLine.SKUID;
            line.UnitID = poLine.UnitID;
            line.RefQty = poLine.ReceivableQty();
            line.RCVTotalQty = qty;
            line.QualifiedQty = qty;
            line.UnQualifiedQty = 0M;
            line.RefOrderLine = line.OriginalOrderLine = poLine.LineNumber;
            line.TaxValue = 0M; // poLine.TaxValue;  ϵͳĬ�Ͻ���˰��������˰�ֿۣ����Խ���˰������Ϊ0��������ֹ�ȷ�����Եֿ۵Ľ���˰��
            line.Price = poLine.Price;
            line.Create(session);

            this.Update(session, "CurrentLineNumber");

            return new SimpleJson();
        }
        public int UpdateLines(ISession session, IList<RCVLine> linesValue)
        {
            if (linesValue == null || linesValue.Count <= 0) return 0;

            #region ���
            IList<RCVLine> lines = new List<RCVLine>(linesValue.Count);
            bool error = false, errorHead = false;
            System.Text.StringBuilder builder = new System.Text.StringBuilder();
            bool hasSection = false;
            foreach (RCVLine lv in linesValue)
            {
                errorHead = false;
                //�ջ������Ƿ���Ч
                if (lv.RCVTotalQty <= 0M)
                {
                    error = true;
                    if (!errorHead) builder.Append("�к�").Append(lv.LineNumber).Append(": ");
                    errorHead = true;
                    builder.Append("�ջ�����С��0; ");
                    continue;
                }
                RCVLine l = RCVLine.Retrieve(session, lv.OrderNumber, lv.LineNumber);
                //���Բ���д���ܣ��������д�ˣ����������Ч��
                WHSection section = null;
                hasSection = false;
                //��λ�������������
                WHArea area = WHArea.Retrieve(session, l.AreaCode);
                if (!string.IsNullOrEmpty(l.SectionCode) && l.SectionCode.Trim().Length > 0)
                {
                    hasSection = true;
                    section = WHSection.Retrieve(session, l.AreaCode, l.SectionCode);
                }
                decimal capacityFree = 0M;
                //ȡ��λ����������
                if (hasSection) capacityFree = section.SectionCapacity;
                else capacityFree = area.AreaCapacity;
                //��λ�����ۼ���ǰ�Ѵ����
                if (hasSection)
                    capacityFree = capacityFree - Cast.Decimal(session.CreateObjectQuery(@"
select sum(StockQty) from StockDetail 
where AreaCode=?area and SectionCode=?section")
                        .Attach(typeof(StockDetail))
                        .SetValue("?area", area.AreaCode, "AreaCode")
                        .SetValue("?section", section.SectionCode, "SectionCode")
                        .Scalar(), 0M);
                else
                    capacityFree = capacityFree - Cast.Decimal(session.CreateObjectQuery(@"select sum(StockQty) from StockDetail where AreaCode=?area")
                        .Attach(typeof(StockDetail))
                        .SetValue("?area", area.AreaCode, "AreaCode")
                        .Scalar(), 0M);
                //ʣ�������ͱ���������Ƚ�
                if (capacityFree < lv.QualifiedQty)
                {
                    builder.Append("�к�").Append(lv.LineNumber).Append(": ");
                    error = true;
                    builder.Append("�����").Append(lv.QualifiedQty).Append("����ʣ������").Append(capacityFree);
                }
                if (!error) lines.Add(l);
            }
            if (error)
                throw new Exception(builder.ToString());
            #endregion

            int count = 0;
            DbSession dbsession = session.DbSession as DbSession;
            session.BeginTransaction();
            for (int i = 0; i < lines.Count; i++)
            {
                RCVLine line = lines[i];
                POLine poLine = null;
                if (!string.IsNullOrEmpty(this._refOrderNumber) && this._refOrderNumber.Trim().Length > 0
                    && !string.IsNullOrEmpty(line.RefOrderLine) && line.RefOrderLine.Trim().Length > 0)
                    poLine = POLine.Retrieve(session, this._refOrderNumber, line.RefOrderLine);
                if (poLine != null)
                {
                    IDbCommand cmd = dbsession.CreateStoredProcCommand("F_PUR_RCV_TOLERANCE_RATIO", new object[] { 0, poLine.OrderNumber });
                    dbsession.ExecuteNonQuery(cmd);
                    IDbDataParameter p = cmd.Parameters[0] as IDbDataParameter;
                    decimal ration = Cast.Decimal(p.Value);
                    if (poLine.PurchaseQty * (1 + ration) - poLine.ReceiveQty - poLine.UnfinishedReceiveQty - linesValue[i].QualifiedQty + line.QualifiedQty < 0)
                    {
                        error = true;
                        builder.Append("�к�").Append(line.LineNumber).Append("�ջ�����").Append(linesValue[i].QualifiedQty)
                            .Append("����PO�����ջ�����").Append(Math.Floor(poLine.PurchaseQty * (1 + ration)) - poLine.ReceiveQty - poLine.UnfinishedReceiveQty + line.QualifiedQty);
                        break;
                    }
                    poLine.UnfinishedReceiveQtyChange(session, linesValue[i].QualifiedQty - line.QualifiedQty);
                }
                line.RCVTotalQty = linesValue[i].RCVTotalQty;
                line.QualifiedQty = linesValue[i].QualifiedQty;
                line.Update(session, "RCVTotalQty", "QualifiedQty");

                count++;
            }
            if (error)
            {
                session.Rollback();
                throw new Exception(builder.ToString());
            }
            else
                session.Commit();

            return count;
        }
        public void Release(ISession session)
        {
            #region ���
            if (this.Status != ReceiveStatus.New)
                throw new Exception(string.Format("�ջ���{0}�����½�״̬���޷�ִ�з�������", this.OrderNumber));
            int count = session.CreateEntityQuery<RCVLine>()
                .Where(Exp.Eq("OrderNumber", this.OrderNumber))
                .Count();
            if (count <= 0) throw new Exception(string.Format("�ջ���{0}û����ϸ�����ܷ���", this.OrderNumber));
            IList<RCVLine> lines = session.CreateEntityQuery<RCVLine>()
                .Where(Exp.Eq("OrderNumber", this._orderNumber))
                .List<RCVLine>();
            bool error = false, errorHead = false;
            System.Text.StringBuilder builder = new System.Text.StringBuilder();
            foreach (RCVLine line in lines)
            {
                errorHead = false;
                if (string.IsNullOrEmpty(line.LocationCode) || line.LocationCode.Trim().Length <= 0)
                {
                    error = true;
                    if (!errorHead) builder.Append("�к�").Append(line.LineNumber).Append(":");
                    builder.Append("�ִ���Ϊ��; ");
                    errorHead = true;
                }
                if (string.IsNullOrEmpty(line.AreaCode) || line.AreaCode.Trim().Length <= 0)
                {
                    error = true;
                    if (!errorHead) builder.Append("�к�").Append(line.LineNumber).Append(":");
                    builder.Append("�������Ϊ��; ");
                    errorHead = true;
                }
                if (line.RCVTotalQty <= 0M)
                {
                    error = true;
                    if (!errorHead) builder.Append("�к�").Append(line.LineNumber).Append(":");
                    builder.Append("�ջ�������Ч; ");
                    errorHead = true;
                }
                if (line.QualifiedQty <= 0M)
                {
                    error = true;
                    if (!errorHead) builder.Append("�к�").Append(line.LineNumber).Append(":");
                    builder.Append("�ϸ�������Ч; ");
                    errorHead = true;
                }
            }
            if (error)
                throw new Exception(builder.ToString());
            #endregion

            this.Status = ReceiveStatus.Release;
            this.ApproveResult = ApproveStatus.UnApprove;
            this.Update(session, "Status", "ApproveResult");
            ERPUtil.ApproveThis(session, this); //��ǩ����
        }
        public static void Close(RCVHead head)
        {
            try
            {
                using (ISession session = new Session())
                {
                    try
                    {
                        session.BeginTransaction();
                        head.Close(session, false);
                        session.Commit();
                    }
                    catch (Exception er1)
                    {
                        session.Rollback();
                        log.Error(string.Format("�ջ���{0}ǩ����ɣ��Զ��ر�ʱ�����쳣", head.OrderNumber), er1);
                        return;
                    }
                }
            }
            catch (Exception er)
            {
                log.Error(string.Format("�ջ���{0}ǩ����ɣ��Զ��ر�ʱ�����쳣���޷��������ݿ�", head.OrderNumber), er);
                return;
            }
        }
        public void Close(ISession session, bool throwException)
        {
            if (this._status != ReceiveStatus.Open)
            {
                log.ErrorFormat("�ջ���{0}���Ǵ�����״̬���޷�ִ�йرղ���", this._orderNumber);
                if (throwException)
                    throw new Exception(string.Format("�ջ���{0}���Ǵ�����״̬���޷�ִ�йرղ���", this._orderNumber));
                return;
            }

            //ǩ����ɺ�����������������Խ��ջ����Զ��رգ����������������쳣��ǩ�˴���������ɣ���Ҫ�ֹ����ر��������
            //ǩ����ɵĹرն������¿�һ��session����ɣ�ȷ���ر�ʱ���쳣����Ӱ��ǩ�˲����Ľ������ֹ��ر�ʱ�ɽ��濪session��ִ���������
            try
            {
                //��潻��
                ERPUtil.CommitWHTrans(session, this);
                //����PO�е������ֶ�ֵ
                if (this._orderTypeCode == RCVHead.ORD_TYPE_PUR
                    && !string.IsNullOrEmpty(this._refOrderNumber) && this._refOrderNumber.Trim().Length > 0)
                {
                    IList<RCVLine> rcvLines = session.CreateEntityQuery<RCVLine>()
                        .Where(Exp.Eq("OrderNumber", this._orderNumber))
                        .OrderBy("LineNumber")
                        .List<RCVLine>();
                    foreach (RCVLine rcv in rcvLines)
                    {
                        if (!string.IsNullOrEmpty(rcv.RefOrderLine) && rcv.RefOrderLine.Trim().Length > 0)
                        {
                            POLine poLine = POLine.Retrieve(session, this._refOrderNumber, rcv.RefOrderLine);
                            if (poLine != null)
                                poLine.ReceiveFinish(session, rcv.RCVTotalQty, rcv.QualifiedQty);
                        }
                    }
                }
                //���±���״̬
                this._status = ReceiveStatus.Close;
                this.Update(session, "Status");
            }
            catch (Exception er)
            {
                log.Error(string.Format("�ջ���{0}�ر�ʱ�����쳣", this._orderNumber), er);
                if (throwException) throw er;
            }
        }

        public static RCVHead CreatePurchaseRCV(ISession session, int userId, string poNumber, string note)
        {
            RCVHead head = new RCVHead();
            head._refOrderType = head._originalOrderType = POHead.ORDER_TYPE;
            head._refOrderNumber = head._orginalOrderNumber = poNumber.Trim().ToUpper();
            #region ���
            //��ʱ������ɹ��ջ����������òɹ������ķ�ʽ
            if (string.IsNullOrEmpty(head._refOrderNumber))
                throw new Exception("�ɹ�����Ϊ��");
            POHead po = POHead.Retrieve(session, head._refOrderNumber);
            if (po == null)
                throw new Exception(string.Format("�ɹ�����{0}������", head._refOrderNumber));
            if (po.Status != POStatus.Release)
                throw new Exception(string.Format("�ɹ�����{0}���Ƿ���״̬�������Խ����ջ���ҵ", head._refOrderNumber));
            if (po.ApproveResult != ApproveStatus.Approve)
                throw new Exception(string.Format("�ɹ�����{0}��û�����ǩ�ˣ������Խ����ջ���ҵ", head._refOrderNumber));
            #endregion
            head._orderTypeCode = RCVHead.ORD_TYPE_PUR;
            head._orderNumber = ERPUtil.NextOrderNumber(head._orderTypeCode);
            head._objectID = po.VendorID;
            head._locationCode = po.LocationCode;
            head._createUser = userId;
            head._note = note.Trim();
            EntityManager.Create(session, head);

            return head;
        }
    }
}