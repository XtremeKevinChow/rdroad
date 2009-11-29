//*******************************************
// ** Description:  Data Access Object for ReturnHead
// ** Author     :  Code generator
// ** Created    :   2008-8-25 20:29:49
// ** Modified   :
//*******************************************

namespace Magic.ERP.Orders
{
    using System;
    using System.Data;
    using System.Text;
    using System.Collections.Generic;
    using Magic.Framework.ORM;
    using Magic.Framework.Data;
    using Magic.Framework.Utils;
    using Magic.ERP.Core;
    using Magic.Framework.ORM.Query;
    using Magic.Basis;

    public partial class ReturnHead : IApprovable, IWHTransHead
    {
        private static log4net.ILog log = log4net.LogManager.GetLogger(typeof(ReturnHead));

        public const string ORDER_TYPE_MBR_RTN = "RC2";
        public const string ORDER_TYPE_LOGISTICS_RTN = "RC4";
        public const string ORDER_TYPE_INNER_RTN = "RC5";
        public const string ORDER_TYPE_EXCHANGE_RTN = "RC3";
        private IList<ReturnLine> _lines = null; //������ֻ�������ⷿ���׹��̲ű���ֵ�����ڲ�ʹ��

        bool IWHTransHead.PreLockStock
        {
            get { return false; }
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
                this._status = ReturnStatus.Open;
                this.Update(session, "Status");
                //������ͬһ��session���ر�
                this.Close(session);
            }
            else
            {
                //���أ����Ļ��½�״̬
                this._status = ReturnStatus.New;
                this.Update(session, "Status");
            }
        }
        /// <summary>
        /// ǩ����ɺ�Ĵ���
        /// </summary>
        /// <param name="session"></param>
        void IApprovable.PostApprove(ISession session)
        {
        }

        string IWHTransHead.OriginalOrderType
        {
            get
            {
                return "SO0";
            }
        }
        string IWHTransHead.RefOrderType
        {
            get
            {
                return CRMSN.ORDER_TYPE_CODE_SD;
            }
        }
        IList<IWHTransLine> IWHTransHead.GetLines(ISession session)
        {
            IList<ReturnLine> lines = session.CreateEntityQuery<ReturnLine>()
                .Where(Exp.Eq("OrderNumber", this.OrderNumber))
                .OrderBy("SKUID").OrderBy("RefOrderLineID")
                .List<ReturnLine>();
            this._lines = lines;
            IList<IWHTransLine> whLines = new List<IWHTransLine>(lines.Count);
            foreach (ReturnLine line in lines)
            {
                if (this.OrderTypeCode != ReturnHead.ORDER_TYPE_EXCHANGE_RTN)
                    line.LocationCode = this.LocationCode;
                else
                {
                    WHArea area = WHArea.Retrieve(session, line.AreaCode);
                    line.LocationCode = area.LocationCode;
                }
                whLines.Add(line);
            }
            return whLines;
        }
        void IWHTransHead.AfterTransaction(ISession session)
        {
            DbSession dbSession = session.DbSession as DbSession;
            IDbCommand command = null;
            //CRM�ӿ���Ҫ���˻�����
            int returnType = 0;
            switch (this.OrderTypeCode)
            {
                case ORDER_TYPE_MBR_RTN: returnType = 0; break; //��Ա�˻�
                case ORDER_TYPE_LOGISTICS_RTN: returnType = 1; break; //�����˻�
                case ORDER_TYPE_INNER_RTN: returnType = 2; break; //�ڲ��˻�
                case ORDER_TYPE_EXCHANGE_RTN: //�����˻�������Ҫ����CRM�洢���̣�ֻ���»�������״̬
                    {
                        SOHead saleOrder = SOHead.Query(session, this.ExchangeOrder);
                        if (saleOrder == null) return;
                        //saleOrder.UpdateStatus(session, 25).UpdateLineStatus(session, 30);
                        command = dbSession.CreateStoredProcCommand("f_order_change_confirm", new object[] { 0, saleOrder.ID });
                        dbSession.ExecuteNonQuery(command);
                        int r = Cast.Int((command.Parameters[0] as IDataParameter).Value);
                        if (r == 0 || r == -2) return;
                        else throw new Exception("���»�������״̬ʱ����f_order_change_confirm����" + r.ToString());
                    };
                default: throw new Exception("��Ч���˻�����");
            }
            //�ж��Ƿ�ȫ��
            bool isReturnAll = false;
            int snLineCount = session.CreateEntityQuery<CRMSNLine>()
                .Where(Exp.Eq("SNID", this.RefOrderID))
                .Count();
            isReturnAll = snLineCount == this._lines.Count; //��һ������������ϸ���˻���ϸ��������ȣ���һ������ȫ��
            StringBuilder snLineIds = new StringBuilder();
            for (int i = 0; i < this._lines.Count; i++)
            {
                if (this._lines[i].Quantity != this._lines[i].DeliverQuantity) isReturnAll = false;
                if (i != 0) snLineIds.Append(",");
                snLineIds.Append(this._lines[i].RefOrderLineID.ToString()).Append("-").Append(this._lines[i].Quantity.ToString());
            }
            //��飺�����˻����ڲ��˻�������ȫ��
            if (!isReturnAll && (this.OrderTypeCode == ORDER_TYPE_LOGISTICS_RTN || this.OrderTypeCode == ORDER_TYPE_INNER_RTN))
                throw new Exception("�����˻����ڲ��˻�������ȫ��");

            //����CRM�洢����
            if (isReturnAll)
            {
                //ȫ��: ORDERS.P_RETURN_SHIPPINGNOTICES
                //        v_shipping_id number, --CRM������ID
                //        v_vouch varchar2, --�˻�������
                //        v_comments VARCHAR2, --�˻���ע��Ϣ
                //        v_operator_id NUMBER, --�˻��ˣ�ERP��CRM�û���ͬ������û�ж�Ӧ����
                //        v_return_order_type INTEGER, --�˻�����: 0��Ա�˻���1�����˻���2�ڲ��˻�
                //        v_is_bad INTEGER, --�Ƿ�����˻�
                //        v_return OUT NUMBER --����ֵ
                command = dbSession.CreateStoredProcCommand("ORDERS.P_RETURN_SHIPPINGNOTICES", new object[] { this.RefOrderID, this.OrderNumber, 0, returnType, this.IsMalicious ? 1 : 0, 0 });
            }
            else
            {
                //�����˻���Ŀǰֻ֧����Բ��ַ�����ϸȫ��
                //2008-11-03: �����˻�����ָ���˻�������
                //  : ORDERS.P_PARTLY_RETURN2
                //        v_shipping_id number, --CRM������ID
                //        v_lines VARCHAR2, --��������ϸ����ʽΪ ��ϸID1,��ϸID2,��ϸID3...
                //        v_vouch varchar2, --�˻�������
                //        v_return OUT NUMBER --����ֵ
                command = dbSession.CreateStoredProcCommand("ORDERS.p_partly_return", new object[] { this.RefOrderID, snLineIds.ToString(), this.OrderNumber, 0 });
            }

            dbSession.ExecuteNonQuery(command);
            //�洢������
            IDataParameter param = command.Parameters[command.Parameters.Count - 1] as IDataParameter;
            int result = Cast.Int(param.Value);
            if (result < 0)
                throw new Exception("�˻�ʱ�����쳣���쳣��Ϣ: " + result.ToString());

            //�˻���ɺ�����˻�ͳ����Ϣ
            command = dbSession.CreateStoredProcCommand("p_rpt_fi_sale_return", new object[] { this.OrderNumber, 0, 0 });
            dbSession.ExecuteNonQuery(command);
            param = command.Parameters[1] as IDataParameter;
            result = Cast.Int(param.Value);
            //�˻�����ֲ���������ԭ������Ϊ��Ʒ����װ���Żݵķ���̫���˻�����Ʒ�ͽ��׵���װ����ԭ�ۼ��㣬��˿��ܳ��ֻ�Ա�˻�֮����Ҫ��֧��һ�����
            //�������������Ա��ǰ�ʻ�����Ƿ��㹻�����ܳ��ָ����������ָ���ʱ�������˻����ʻ������-127����ʾ�ֿ���Ա�����һع�����
            //              �ֿ���Ա֪ͨ�ͷ��Ͳ��񣬿ͷ����Ա����ȷ�ϣ������ڻ�Ա�ʻ����ֹ���ֵ����ֵ127�������ɲֿ�ִ���˻�����
            //              ������Щ��������֮���˻����Ա�ʻ������0
            decimal returnAmt = Cast.Decimal((command.Parameters[2] as IDataParameter).Value);
            if (returnAmt < 0)
            {
                //����˻����С��0�����Ա�ʻ�����Ƿ����С��0�����
                command = dbSession.CreateSqlStringCommand(@"
Select m.deposit
From mbr_members m
Inner Join ord_headers o On o.buyer_id=m.Id
Where o.so_number=:sonum");
                dbSession.AddParameter(command, ":sonum", DbTypeInfo.AnsiString(16), this.OrginalOrderNumber);
                decimal accountAmt = Cast.Decimal(dbSession.ExecuteScalar(command));
                if (accountAmt < 0) throw new Exception("���˻����˻����»�Ա�ʻ����ָ���������ϵ�ͷ���Ա���д����˻����Ϊ" + returnAmt.ToString("#0.#0") + "���˻����ʻ����Ϊ" + accountAmt.ToString("#0.#0") + "��");
            }
            switch (result)
            {
                case -1001:
                    throw new Exception("���µĿ���ڼ�û����������������ϵϵͳά����Ա");
                case -1002:
                    command = dbSession.CreateSqlStringCommand("select * from fi_rpt_sale_return where rt_number='" + this.OrderNumber + "'");
                    DataSet ds = dbSession.ExecuteDataSet(command);
                    throw new Exception("�˻�ͳ�����ݴ�������ϵϵͳά����Ա" + "<br />" +
                        "����:" + Cast.Decimal(ds.Tables[0].Rows[0]["sale_amt"]).ToString() + ", " +
                        "���ͷ�:" + Cast.Decimal(ds.Tables[0].Rows[0]["transport_amt"]).ToString() + ", " +
                        "��װ��:" + Cast.Decimal(ds.Tables[0].Rows[0]["package_amt"]).ToString() + ", " +
                        "��ȯ:" + Cast.Decimal(ds.Tables[0].Rows[0]["coupons_amt"]).ToString() + ", " +
                        "�ۿ�:" + Cast.Decimal(ds.Tables[0].Rows[0]["discount_amt"]).ToString() + ", " +
                        "���:" + Cast.Decimal(ds.Tables[0].Rows[0]["emoney_amt"]).ToString() + ", " +
                        "�ʻ�:" + Cast.Decimal(ds.Tables[0].Rows[0]["account_receivable"]).ToString() + ", " +
                        "POS��:" + Cast.Decimal(ds.Tables[0].Rows[0]["pos_receivable"]).ToString() + ", " +
                        "����Ӧ��:" + Cast.Decimal(ds.Tables[0].Rows[0]["logis_receivable"]).ToString());
                default: break;
            }
        }

        private Logistics GetSNLogistics(ISession session, string sn)
        {
            IList<Logistics> r = session.CreateObjectQuery(@"
select 1
from CRMSN sn
inner join ICLine icl On icl.RefOrderNumber=sn.OrderNumber
inner join ICHead ich on ich.OrderNumber=icl.OrderNumber
inner join Logistics lg on lg.LogisticCompID=ich.LogisticCompID")
                .Attach(typeof(CRMSN)).Attach(typeof(ICLine)).Attach(typeof(ICHead)).Attach(typeof(Logistics))
                .Where(Exp.Eq("sn.OrderNumber", sn))
                .List<Logistics>();

            return r.Count > 0 ? r[0] : null;
        }
        /// <summary>
        /// ��Ա�˻������÷�����ֻ������ʵ�����ԣ�����update���ݿ�
        /// </summary>
        /// <param name="session"></param>
        /// <param name="locationCode">��λ</param>
        /// <param name="snNumber">����������</param>
        /// <param name="reasonId">�˻�ԭ��</param>
        /// <param name="isMalicious">�Ƿ�����˻�</param>
        /// <param name="note">��ע</param>
        /// <param name="createUser"></param>
        public void MemberReturn(ISession session, string locationCode, string snNumber, int reasonId, bool isMalicious, string note, int createUser)
        {
            if (string.IsNullOrEmpty(snNumber) || snNumber.Trim().Length <= 0)
                throw new Exception("������д����������");
            CRMSN sn = CRMSN.Retrieve(session, snNumber.Trim());
            if (sn == null)
                throw new Exception("������" + snNumber + "������");
            //if (sn.Status == CRMSNStatus.Return)
            //    throw new Exception("������" + sn.OrderNumber + "�Ѿ��˻�");
            //if (sn.Status == CRMSNStatus.PartExchange)
            //    throw new Exception("������" + sn.OrderNumber + "�Ѿ�����");
            if (sn.Status != CRMSNStatus.Interchanged)
                throw new Exception("������" + snNumber + "δ��ɣ��޷��˻�");
            if (!string.IsNullOrEmpty(this.OrderNumber) && this.OrderNumber.Trim().Length > 0
                && !string.IsNullOrEmpty(this.RefOrderNumber) && this.RefOrderNumber.Trim().Length > 0
                && this.RefOrderNumber != snNumber)
            {
                //����������ı䣬����Ƿ��Ѿ�����ϸ���ڣ�����Ѿ�����ϸ��������ɾ��
                if (session.CreateEntityQuery<ReturnLine>().Where(Exp.Eq("OrderNumber", this.OrderNumber)).Count() > 0)
                    throw new Exception("�˻���" + this.OrderNumber + "�Ѿ������˻���ϸ���޷��ٸı䷢��������");
            }
            Logistics lg = this.GetSNLogistics(session, snNumber);
            if (lg != null && !lg.CanReturn)
                throw new Exception("ϵͳ������������˾" + lg.ShortName + "�����Ķ����������˻�������ϵϵͳ����Ա");

            this.RefOrderNumber = sn.OrderNumber;
            this.RefOrderID = sn.ID;
            this.OrginalOrderNumber = sn.SaleOrderNumber;
            this.MemberID = sn.MemberID;
            if (sn.MemberID > 0)
            {
                Magic.Basis.Member member = Magic.Basis.Member.Retrieve(session, sn.MemberID);
                if (member != null) this.MemberName = member.Name;
            }
            this.LogisticsID = sn.LogisticsID;
            if (this.LogisticsID > 0)
            {
                Magic.Basis.Logistics logis = Magic.Basis.Logistics.Retrieve(session, this.LogisticsID);
                this.LogisticsName = logis.ShortName;
            }
            this.LocationCode = locationCode;
            this.IsMalicious = isMalicious;
            this.Note = note;
            this.ReasonID = reasonId;
            if (reasonId > 0)
            {
                Magic.Basis.ReturnReason reason = Magic.Basis.ReturnReason.Retrieve(session, reasonId);
                if (reason != null) this.ReasonText = reason.ReasonText;
            }

            this.OrderTypeCode = ORDER_TYPE_MBR_RTN;
            this.CreateUser = createUser;
            this.IsAutoMatch = false;
        }
        public void LogisReturn(ISession session, string locationCode, string snNumber, int reasonId, bool isMalicious, bool hasTransported, string note, int createUser)
        {
            if (string.IsNullOrEmpty(snNumber) || snNumber.Trim().Length <= 0)
                throw new Exception("������д����������");
            CRMSN sn = CRMSN.Retrieve(session, snNumber.Trim());
            if (sn == null)
                throw new Exception("������" + snNumber + "������");
            if (sn.Status == CRMSNStatus.Return)
                throw new Exception("������" + sn.OrderNumber + "�Ѿ��˻�");
            if (sn.Status == CRMSNStatus.PartExchange)
                throw new Exception("������" + sn.OrderNumber + "�Ѿ�����");
            if (sn.Status != CRMSNStatus.Interchanged)
                throw new Exception("������" + snNumber + "δ��ɣ��޷��˻�");
            if (!string.IsNullOrEmpty(this.OrderNumber) && this.OrderNumber.Trim().Length > 0
                && !string.IsNullOrEmpty(this.RefOrderNumber) && this.RefOrderNumber.Trim().Length > 0
                && this.RefOrderNumber != snNumber)
            {
                //����������ı䣬����Ƿ��Ѿ�����ϸ���ڣ�����Ѿ�����ϸ��������ɾ��
                if (session.CreateEntityQuery<ReturnLine>().Where(Exp.Eq("OrderNumber", this.OrderNumber)).Count() > 0)
                    throw new Exception("�˻���" + this.OrderNumber + "�Ѿ������˻���ϸ���޷��ٸı䷢��������");
            }

            this.RefOrderNumber = sn.OrderNumber;
            this.RefOrderID = sn.ID;
            this.OrginalOrderNumber = sn.SaleOrderNumber;
            this.MemberID = sn.MemberID;
            if (sn.MemberID > 0)
            {
                Magic.Basis.Member member = Magic.Basis.Member.Retrieve(session, sn.MemberID);
                if (member != null) this.MemberName = member.Name;
            }
            this.LogisticsID = sn.LogisticsID;
            if (this.LogisticsID > 0)
            {
                Magic.Basis.Logistics logis = Magic.Basis.Logistics.Retrieve(session, this.LogisticsID);
                this.LogisticsName = logis.ShortName;
            }
            this.LocationCode = locationCode;
            this.IsMalicious = isMalicious;
            this.HasTransported = hasTransported;
            this.Note = note;
            this.ReasonID = reasonId;
            if (reasonId > 0)
            {
                Magic.Basis.ReturnReason reason = Magic.Basis.ReturnReason.Retrieve(session, reasonId);
                if (reason != null) this.ReasonText = reason.ReasonText;
            }

            this.OrderTypeCode = ORDER_TYPE_LOGISTICS_RTN;
            this.CreateUser = createUser;
            this.IsAutoMatch = true;
        }
        /// <summary>
        /// ���»�Ա�����˻�����ֻ������ʵ�����ԣ�����update���ݿ�
        /// </summary>
        /// <param name="session"></param>
        /// <param name="locationCode">��λ</param>
        /// <param name="reasonId">�˻�ԭ��</param>
        /// <param name="note">��ע</param>
        /// <param name="createUser"></param>
        public void ExchangeReturn(ISession session, string locationCode, int reasonId, string note, int createUser)
        {
            this.LocationCode = locationCode;
            this.Note = note;
            this.ReasonID = reasonId;
            if (reasonId > 0)
            {
                Magic.Basis.ReturnReason reason = Magic.Basis.ReturnReason.Retrieve(session, reasonId);
                if (reason != null) this.ReasonText = reason.ReasonText;
            }
        }
        /// <summary>
        /// ��һ���к���
        /// </summary>
        /// <returns></returns>
        public virtual string NextLineNumber()
        {
            int number = Magic.Framework.Utils.Cast.Int(this.CurrentLineNumber);
            //number = number % 10 == 0 ? number + 10 : ((number + 9) / 10 * 10);
            string result = (number + 1).ToString().PadLeft(4, '0');
            this.CurrentLineNumber = result;
            return result;
        }
        public void UpdateOrCreateLines(ISession session, IList<ReturnLine> toSave)
        {
            //ȷ�������˻�����ִ�и÷���
            if (this.OrderTypeCode == ORDER_TYPE_EXCHANGE_RTN) return;

            log.DebugFormat("to create or save rtn lines, count:{0}", toSave == null ? 0 : toSave.Count);
            if (toSave == null || toSave.Count <= 0) return;
            if (this.Status != ReturnStatus.New)
                throw new Exception("�˻��������½�״̬���޷�����");
            //�����˻����ڲ��˻�����ȫ�ˣ���֧�ֲ����˻�
            if (this.OrderTypeCode == ORDER_TYPE_LOGISTICS_RTN || this.OrderTypeCode == ORDER_TYPE_INNER_RTN)
            {
                int snLineCount = session.CreateEntityQuery<CRMSNLine>().Where(Exp.Eq("SNID", this.RefOrderID)).Count();
                if (snLineCount != toSave.Count)
                    throw new Exception("������" + this.RefOrderNumber + "��ϸ(" + snLineCount.ToString() + "��)���˻���ϸ(" + toSave.Count.ToString() + "��)��һ��");
            }
            bool updateHeadLineNum = false;
            foreach (ReturnLine line in toSave)
            {
                if (line.RefOrderLineID <= 0 || line.Quantity <= 0M)
                {
                    if (this.OrderTypeCode == ORDER_TYPE_LOGISTICS_RTN || this.OrderTypeCode == ORDER_TYPE_INNER_RTN)
                        throw new Exception("�˻���ϸ����������Ч");
                    continue;
                }
                bool checkCreate = true;
                if (this.OrderTypeCode == ORDER_TYPE_MBR_RTN)
                {
                    //��Ա�˻��ſ��Էֶ��ɨ���˻���ϸ
                    ReturnLine existsLine = ReturnLine.Retrieve(session, this.OrderNumber, line.RefOrderLineID);
                    if (existsLine != null)
                    {
                        log.DebugFormat("rtn line({0}) exists, rtn qty:{1}", line.RefOrderLineID, line.Quantity);
                        existsLine.Quantity += line.Quantity;
                        existsLine.Update(session, "Quantity");
                        checkCreate = false;
                    }
                }
                if (checkCreate)
                {
                    CRMSNLine snline = CRMSNLine.Retrieve(session, line.RefOrderLineID);
                    if (snline == null)
                        throw new Exception("��������ϸ" + line.RefOrderLineID.ToString() + "������");
                    //�����˻����ڲ��˻�����ȫ��
                    if ((this.OrderTypeCode == ORDER_TYPE_LOGISTICS_RTN || this.OrderTypeCode == ORDER_TYPE_INNER_RTN)
                        && line.Quantity != snline.Quantity)
                        throw new Exception("�˻����������ڷ�������");
                    ReturnLine existsLine = new ReturnLine();
                    existsLine.OrderNumber = this.OrderNumber;
                    existsLine.LineNumber = this.NextLineNumber();
                    updateHeadLineNum = true;
                    existsLine.RefOrderLineID = snline.ID;
                    existsLine.SKUID = snline.SKUID;
                    existsLine.TransTypeCode = " ";
                    existsLine.Quantity = line.Quantity;
                    existsLine.DeliverQuantity = snline.Quantity;
                    existsLine.Price = snline.Price;
                    IList<WHArea> areas = ERPUtil.GetWHArea(session, this.OrderTypeCode, null, this.LocationCode);
                    if (areas.Count == 1)
                        existsLine.AreaCode = areas[0].AreaCode;
                    log.DebugFormat("to create rtn line, qty:{0}", line.Quantity);
                    existsLine.Create(session);
                }
            }
            if (updateHeadLineNum) this.Update(session, "CurrentLineNumber");
        }
        public void UpdateLines(ISession session, IList<ReturnLine> toSave)
        {
            if (toSave == null || toSave.Count <= 0) return;
            if (this.Status != ReturnStatus.New)
                throw new Exception("�˻��������½�״̬���޷�����");
            this.CheckWHSettings(session, toSave);
            //����
            foreach (ReturnLine line in toSave)
                line.Update(session, "AreaCode", "SectionCode");
        }
        private void CheckWHSettings(ISession session, IList<ReturnLine> toCheck)
        {
            StringBuilder errmsg = new StringBuilder();
            //���
            foreach (ReturnLine line in toCheck)
            {
                if (string.IsNullOrEmpty(line.AreaCode) || line.AreaCode.Trim().Length <= 0)
                {
                    errmsg.Append("��").Append(line.LineNumber).Append("��λΪ��; ");
                    continue;
                }
                if (!string.IsNullOrEmpty(line.SectionCode) && line.SectionCode.Trim().Length > 0)
                {
                    WHSection section = WHSection.Retrieve(session, line.AreaCode, line.SectionCode);
                    if (section == null)
                    {
                        errmsg.Append("��").Append(line.LineNumber)
                            .Append("����").Append(line.SectionCode).Append("(").Append(line.AreaCode).Append(")������; ");
                        continue;
                    }
                }
            }
            if (errmsg.Length > 0)
                throw new Exception(errmsg.ToString());
        }
        public void Release(ISession session)
        {
            if (this.Status != ReturnStatus.New)
                throw new Exception("�˻��������½�״̬���޷�ִ�з�������");
            //�����˻���������ɨ���˻���Ʒ��ɨ��������������˻���Ʒ����
            if (this.OrderTypeCode == ORDER_TYPE_EXCHANGE_RTN && !this.HasScaned)
                throw new Exception("����û��ɨ���˻���Ʒ");
            IList<ReturnLine> lines = session.CreateEntityQuery<ReturnLine>()
                .Where(Exp.Eq("OrderNumber", this.OrderNumber))
                .List<ReturnLine>();
            if (lines.Count <= 0 && this.OrderTypeCode != ReturnHead.ORDER_TYPE_EXCHANGE_RTN)
                throw new Exception("û���˻���ϸ���޷�����");
            this.CheckWHSettings(session, lines);
            this.Status = ReturnStatus.Release;
            this.Update(session, "Status");
            ERPUtil.ApproveThis(session, this);
        }
        public void Close(ISession session)
        {
            if (this.Status != ReturnStatus.Open)
                throw new Exception("�˻������Ǵ����״̬���޷�ִ�иò���");
            this.Status = ReturnStatus.Close;
            this.Update(session, "Status");
            ERPUtil.CommitWHTrans(session, this);
        }
        public static IList<ReturnHead> QueryBySNNumber(ISession session, string snNumber)
        {
            if (string.IsNullOrEmpty(snNumber) || snNumber.Trim().Length <= 0) return null;
            return session.CreateEntityQuery<ReturnHead>()
                .Where(Exp.Eq("RefOrderNumber", snNumber.Trim().ToUpper()))
                .List<ReturnHead>();
        }
        public void ExchangeReturnScaned(ISession session)
        {
            this.HasScaned = true;
            this.Update(session, "HasScaned");
        }
    }
}