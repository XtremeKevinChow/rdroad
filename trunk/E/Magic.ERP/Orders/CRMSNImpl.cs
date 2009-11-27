namespace Magic.ERP.Orders
{
    using System;
    using System.Data;
    using System.Collections.Generic;
    using Magic.Basis;
    using Magic.Framework.Data;
    using Magic.Framework.ORM;
    using Magic.Framework.ORM.Query;
    using Magic.Framework.ORM.Mapping;
    using Magic.Framework;
    using Magic.Framework.Utils;
    using Magic.Sys;
    using Magic.ERP.Core;

    public partial class CRMSN : IWHTransHead
    {
        log4net.ILog log = log4net.LogManager.GetLogger(typeof(CRMSN));
        private IList<CRMSNLine> _lines;
        private string _orderTypeCode;

        public const string ORDER_TYPE_CODE_SD = "SD0";
        public const string ORDER_TYPE_CODE_ED = "DL2";

        bool IWHTransHead.PreLockStock
        {
            get { return true; }
        }

        public static SimpleJson DeliverOrder4Check(ISession session, string orderNumber)
        {
            CRMSN shippingNotice = CRMSN.Retrieve(session, orderNumber);
            if (shippingNotice == null) return new SimpleJson().HandleError(string.Format("������{0}������", orderNumber));
            if (shippingNotice.Status != CRMSNStatus.Distributing)
                return new SimpleJson().HandleError(string.Format("������{0}�����Ѵ�ӡ״̬���޷����к˻���ҵ", shippingNotice.OrderNumber));
            SimpleJson json = shippingNotice.ToJSon(session);
            //�Ƿ���Ҫ���˵�����Ҫ�˻��ķ�����ϸ��
            DataSet ds = session.CreateObjectQuery(@"
select s.BarCode as SKU,m.ItemCode as ItemCode,m.ItemName as ItemName
    ,s.ColorCode as ColorCode,color.ColorText as ColorText,s.SizeCode as SizeCode
    ,sum(l.Quantity) as Quantity
from CRMSNLine l
inner join ItemSpec s on l.SKUID=s.SKUID
inner join ItemMaster m on m.ItemID=s.ItemID
left join ItemColor color on color.ColorCode=s.ColorCode
group by s.BarCode,m.ItemCode,m.ItemName,s.ColorCode,color.ColorText,s.SizeCode
order by s.BarCode")
                .Attach(typeof(CRMSNLine)).Attach(typeof(ItemSpec)).Attach(typeof(ItemMaster))
                .Attach(typeof(ItemColor))
                .Where(Exp.Eq("l.SNID", shippingNotice.ID))
                .DataSet();
            IList<SimpleJson> lineJson = new List<SimpleJson>(ds.Tables[0].Rows.Count);
            foreach (DataRow row in ds.Tables[0].Rows)
                lineJson.Add(new SimpleJson()
                    .Add("sku", row["SKU"])
                    .Add("itemCode", row["ItemCode"])
                    .Add("color", Cast.String(row["ColorCode"]) + " " + Cast.String(row["ColorText"]))
                    .Add("size", row["SizeCode"])
                    .Add("itemName", row["ItemName"])
                    .Add("qty", row["Quantity"]));
            json.Add("lines", lineJson);
            return json;
        }
        public static SimpleJson DeliverOrder4Package(ISession session, string orderNumber)
        {
            CRMSN shippingNotice = CRMSN.Retrieve(session, orderNumber);
            if (shippingNotice == null) return new SimpleJson().HandleError(string.Format("������{0}������", orderNumber));
            if (shippingNotice.Status != CRMSNStatus.Checked)
                throw new Exception("������" + shippingNotice.OrderNumber + "û�к˻������Ѿ���װ��ϣ��޷�ִ�а�װ��ҵ");
            return shippingNotice.ToJSon(session);
        }
        public SimpleJson ToJSon(ISession session)
        {
            SimpleJson json = new SimpleJson()
                .Add("orderNumber", this.OrderNumber)
                .Add("saleOrder", this.SaleOrderNumber)
                .Add("memberId", this.MemberID);
            string memberName = " ";
            if (this.MemberID > 0)
            {
                Magic.Basis.Member member = Magic.Basis.Member.Retrieve(session, this.MemberID);
                if (member != null) memberName = member.Name;
            }
            json.Add("member", memberName);
            json.Add("contact", this.Contact)
                .Add("phone", this.Phone)
                .Add("mobile", this.Mobile)
                .Add("address", this.Address)
                .Add("district", this.Province + " " + this.City)
                .Add("shippingNumber", this.ShippingNumber)
                .Add("isInvoice", this.IsInvoice)
                .Add("invoice", Cast.String(this.InvoiceNumber, "").Trim())
                .Add("packageWeight", this.PackageWeight)
                .Add("logistics", this.LogisticsID)
                .Add("packageCount", this.PackageCount <= 0 ? 1 : this.PackageCount);
            string paymentMethod = " ";
            if (this.PaymentMethod > 0)
            {
                CRM.PaymentMethod pm = CRM.PaymentMethod.Retrieve(session, this.PaymentMethod);
                if (pm != null)
                    paymentMethod = pm.Name;
            }
            json.Add("paymentMethod", paymentMethod);
            string deliverType = " ";
            if (this.DeliveryType > 0)
            {
                CRM.DeliverType dt = CRM.DeliverType.Retrieve(session, this.DeliveryType);
                if (dt != null) deliverType = dt.Name;
            }
            json.Add("deliverType", deliverType);
            string logisName = " ";
            if (this.LogisticsID > 0)
            {
                Logistics logis = Logistics.Retrieve(session, this.LogisticsID);
                if (logis != null) logisName = logis.ShortName;
            }
            json.Add("logisticsName", logisName);
            string packageType = " ";
            if (this.PackageType > 0)
            {
                CRM.PackageType pt = CRM.PackageType.Retrieve(session, this.PackageType);
                if (pt != null) packageType = pt.Name;
            }
            json.Add("packageType", packageType)
                .Add("note", string.IsNullOrEmpty(this.Remark) ? " " : this.Remark.Replace("</br>", " "));
            json.Add("packageUser", string.IsNullOrEmpty(this.PackagePerson) ? " " : this.PackagePerson);
            return json;
        }

        public static CRMSN Retrieve(ISession session, string orderNumber)
        {
            if (string.IsNullOrEmpty(orderNumber) || orderNumber.Length <= 0) return null;
            IList<CRMSN> heads = session.CreateEntityQuery<CRMSN>()
                .Where(Exp.Eq("OrderNumber", orderNumber))
                .List<CRMSN>();
            return heads == null || heads.Count <= 0 ? null : heads[0];
        }

        public static void CheckFinish(ISession session, string orderNumber)
        {
            if (string.IsNullOrEmpty(orderNumber) || orderNumber.Trim().Length <= 0)
                throw new Exception("����������Ϊ�գ��޷�ִ�иò���");
            CRMSN head = CRMSN.Retrieve(session, orderNumber);
            if (head == null) throw new Exception("������" + orderNumber + "������");
            if (head.Status != CRMSNStatus.Distributing) return;
            head.Status = CRMSNStatus.Checked;
            User user = User.Retrieve(session, Security.SecuritySession.CurrentUser.UserId);
            head.CheckPerson = user == null ? " " : user.FullName;
            head.CheckDate = DateTime.Now;
            head.Update(session, "Status", "CheckPerson", "CheckDate");
            if (head._lines == null || head._lines.Count <= 0)
                head._lines = session.CreateEntityQuery<CRMSNLine>()
                    .Where(Exp.Eq("SNID", head.ID))
                    .OrderBy("SKUID").OrderBy("ID")
                    .List<CRMSNLine>();
            foreach (CRMSNLine line in head._lines)
            {
                line.Status = CRMSNLineStatus.Checked;
                line.Update(session, "Status");
            }
            head.WHTransactionDo(session);
        }
        public static void PackageSave(ISession session, string orderNumber, decimal packageWeight, string shippingNumber, int logisticId, string invoiceNumber, int packageCount)
        {
            CRMSN head = CRMSN.Retrieve(session, orderNumber);
            if (head == null) throw new Exception("������" + orderNumber + "������");
            if (head.Status != CRMSNStatus.Checked)
                throw new Exception("������" + orderNumber + "û�к˻������Ѿ���װ��ϣ��޷�ִ�а�װ��ҵ");
            head.PackageWeight = packageWeight;
            head.ShippingNumber = shippingNumber;
            head.LogisticsID = logisticId;
            head.InvoiceNumber = invoiceNumber;
            head.PackageCount = packageCount;
            head.Update(session, "PackageWeight", "ShippingNumber", "LogisticsID", "InvoiceNumber", "PackageCount");
        }
        public static void PackageFinish(ISession session, string orderNumber)
        {
            CRMSN head = CRMSN.Retrieve(session, orderNumber);
            if (head == null) throw new Exception("������" + orderNumber + "������");
            if (head.Status != CRMSNStatus.Checked)
                throw new Exception("������" + orderNumber + "û�к˻������Ѿ���װ��ϣ��޷�ִ�а�װ��ҵ");
            head.Status = CRMSNStatus.Packaged;
            head.PackageDate = DateTime.Now;
            head.PackagePerson = Security.SecuritySession.CurrentUser == null ? " " : Security.SecuritySession.CurrentUser.FullName;
            head.Update(session, "Status", "PackageDate", "PackagePerson");
        }
        public void WHTransactionDo(ISession session)
        {
            SOHead saleOrder = SOHead.Retrieve(session, this._saleOrderID);
            if (saleOrder.OrderType == SaleOrderType.Exchange)
                this._orderTypeCode = ORDER_TYPE_CODE_ED;
            else
                this._orderTypeCode = ORDER_TYPE_CODE_SD;
            ERPUtil.CommitWHTrans(session, this);
        }

        string IWHTransHead.Note
        {
            get { return this._comments; }
        }
        string IWHTransHead.OrderTypeCode
        {
            get { return this._orderTypeCode; }
        }
        string IWHTransHead.OriginalOrderType
        {
            get { return "SO0"; }
        }
        string IWHTransHead.RefOrderType
        {
            get { return "SO0"; }
        }
        string IWHTransHead.OrginalOrderNumber
        {
            get { return this.SaleOrderNumber; }
        }
        string IWHTransHead.RefOrderNumber
        {
            get { return this.SaleOrderNumber; }
        }
        IList<IWHTransLine> IWHTransHead.GetLines(ISession session)
        {
            if (this._lines == null || this._lines.Count <= 0)
                this._lines = session.CreateEntityQuery<CRMSNLine>()
                    .Where(Exp.Eq("SNID", this.ID))
                    .OrderBy("SKUID").OrderBy("ID")
                    .List<CRMSNLine>();
            IList<IWHTransLine> whLines = new List<IWHTransLine>(this._lines.Count);
            foreach (CRMSNLine line in this._lines)
                whLines.Add(line);
            return whLines;
        }
        void IWHTransHead.AfterTransaction(ISession session)
        {
            //����CRM�洢����
            DbSession dbSession = session.DbSession as DbSession;
            IDbCommand command = dbSession.CreateStoredProcCommand("ORDERS.P_SHIPPINGNOTICE_COMPLETE", new object[] { this.ID, 0 });
            dbSession.ExecuteNonQuery(command);
            //�洢������
            IDataParameter param = command.Parameters[command.Parameters.Count - 1] as IDataParameter;
            int result = Cast.Int(param.Value);
            if (result < 0)
                throw new Exception("��������ɵĴ������쳣���쳣��Ϣ: " + result.ToString());
            if (this._lines == null || this._lines.Count <= 0) return;
            //�ָ�CRM��������Ļ��ܿ����������ȡ��CRM�Լ���ָ�����Ļ��ܿ������
            IDbCommand command2 = dbSession.CreateSqlStringCommand("UPDATE INV_STOCK_SUM SET FROZEN_QTY=FROZEN_QTY-:qty WHERE SKU_ID=:sku");
            foreach (CRMSNLine line in this._lines)
            {
                //�ָ�CRM��������Ļ��ܿ����
                command2.Parameters.Clear();
                dbSession.AddParameter(command2, ":qty", EntityManager.GetPropMapping(typeof(StockSummary), "FrozenQty").DbTypeInfo, line.Quantity);
                dbSession.AddParameter(command2, ":sku", EntityManager.GetPropMapping(typeof(StockDetail), "SKUID").DbTypeInfo, line.SKUID);
                dbSession.ExecuteNonQuery(command2);
            }
            //���Ͷ���֪ͨ
            Magic.Notification.Service.BuildService.OrderDeliverSms(session, this.ID);
            if (this._orderTypeCode == ORDER_TYPE_CODE_ED) return; //Ŀǰ�������������˻�ͳ�� 
            //��������ͳ������
            command = dbSession.CreateStoredProcCommand("p_rpt_fi_sale", new object[] { this.OrderNumber, 0 });
            dbSession.ExecuteNonQuery(command);
            param = command.Parameters[command.Parameters.Count - 1] as IDataParameter;
            result = Cast.Int(param.Value);
            switch (result)
            {
                case -1001:
                    throw new Exception("���µĿ���ڼ�û����������������ϵϵͳά����Ա");
                case -1002:
                    command = dbSession.CreateSqlStringCommand("select * from fi_rpt_sale where sn_number='" + this.OrderNumber + "'");
                    DataSet ds = dbSession.ExecuteDataSet(command);
                    throw new Exception("����ͳ�����ݴ�������ϵϵͳά����Ա" + "<br />" +
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
        public void InterchangeFinish(ISession session)
        {
            this._status = CRMSNStatus.Interchanged;
            this.Update(session, "Status");
            if (this._lines == null || this._lines.Count <= 0)
                this._lines = session.CreateEntityQuery<CRMSNLine>()
                    .Where(Exp.Eq("SNID", this.ID))
                    .OrderBy("SKUID").OrderBy("ID")
                    .List<CRMSNLine>();
            foreach (CRMSNLine line in this._lines)
            {
                line.Status = CRMSNLineStatus.Interchanged;
                line.Update(session, "Status");
            }
            //���ӵ���ɺ󣬸�������ͳ����������ݵ�������idֵ
            DbSession dbsession = session.DbSession as DbSession;
            IDbCommand cmd = dbsession.CreateStoredProcCommand("p_rpt_fi_sale", new object[] { this.OrderNumber, 0 });
            dbsession.ExecuteNonQuery(cmd);
        }
        public static IList<CRMSN> InterchangableList(ISession session, int logisId)
        {
            return session.CreateObjectQuery(@"
select 1
from CRMSN sn
where sn.LogisticsID=?logisId and sn.Status=?snStatus and sn.OrderNumber not in(
    select distinct il.RefOrderNumber
    from ICHead ih
    inner join ICLine il on ih.OrderNumber=il.OrderNumber
    where ih.Status in (?new, ?release, ?open))")
                .Attach(typeof(CRMSN)).Attach(typeof(ICHead)).Attach(typeof(ICLine))
                .OrderBy("sn.OrderNumber")
                .SetValue("?snStatus", CRMSNStatus.Packaged, "sn.Status")
                .SetValue("?logisId", logisId, "sn.LogisticsID")
                .SetValue("?new", InterchangeStatus.New, EntityManager.GetPropMapping(typeof(ICHead), "Status").DbTypeInfo)
                .SetValue("?release", InterchangeStatus.Release, EntityManager.GetPropMapping(typeof(ICHead), "Status").DbTypeInfo)
                .SetValue("?open", InterchangeStatus.Open, EntityManager.GetPropMapping(typeof(ICHead), "Status").DbTypeInfo)
                .List<CRMSN>();
        }
    }
}