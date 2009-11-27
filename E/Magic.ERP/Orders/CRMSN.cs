//*******************************************
// ** Description:  Data Access Object for CRMSN
// ** Author     :  Code generator
// ** Created    :   2008-7-30 23:36:25
// ** Modified   :
//*******************************************

namespace Magic.ERP.Orders
{
    using System;
    using Magic.Framework.Data;
    using Magic.Framework.ORM;
    using Magic.Framework.ORM.Mapping;

    /// <summary>
    /// Data Access Object for CRMSN
    /// </summary>
    [Table("ORD_SHIPPINGNOTICES")]
    public partial class CRMSN : IEntity
    {
        #region Private Fields
        private int _iD;
        private string _orderNumber;
        private int _saleOrderID;
        private string _saleOrderNumber;
        private CRMSNStatus _status;
        private int _memberID;
        private string _postCode;
        private string _address;
        private string _phone;
        private string _mobile;
        private string _contact;
        private string _comments;
        private string _remark;
        private string _checkPerson;
        private DateTime _checkDate;
        private decimal _packageFee;
        private decimal _goodsFee;
        private decimal _appendFee;
        private string _packagePerson;
        private DateTime _packageDate;
        private DateTime _deliveryDate;
        private int _deliveryFee;
        private int _deliveryType;
        private int _paymentMethod;
        private int _createUser;
        private DateTime _createTime;
        private string _state;
        private string _city;
        private decimal _agentAmt;
        private decimal _paidAmt;
        private bool _isInvoice;
        private string _invoiceNumber;
        private string _shippingNumber;
        private decimal _packageWeight;
        private int _logisticsId;
        private int _packageCount;
        private int _packageType;
        #endregion

        #region Public Properties
        [Column(Name = "ID", DbType = StdDbType.Int32, IsPrimary = true)]
        public int ID
        {
            get { return this._iD; }
            set { this._iD = value; }
        }

        [Column(Name = "BARCODE", DbType = StdDbType.AnsiString, Length = 200)]
        public string OrderNumber
        {
            get { return this._orderNumber; }
            set { this._orderNumber = value; }
        }

        [Column(Name = "ORDER_NUMBER", DbType = StdDbType.AnsiString, Length = 20)]
        public string SaleOrderNumber
        {
            get { return this._saleOrderNumber; }
            set { this._saleOrderNumber = value; }
        }

        [Column(Name = "REF_ORDER_ID", DbType = StdDbType.Int32)]
        public int SaleOrderID
        {
            get { return this._saleOrderID; }
            set { this._saleOrderID = value; }
        }

        [Column(Name = "STATUS", DbType = StdDbType.Int32)]
        public CRMSNStatus Status
        {
            get { return this._status; }
            set { this._status = value; }
        }

        [Column(Name = "MEMBER_ID", DbType = StdDbType.Int32)]
        public int MemberID
        {
            get { return this._memberID; }
            set { this._memberID = value; }
        }

        [Column(Name = "POSTCODE", DbType = StdDbType.AnsiChar, Length = 6)]
        public string PostCode
        {
            get { return this._postCode; }
            set { this._postCode = value; }
        }

        [Column(Name = "ADDRESS", DbType = StdDbType.AnsiString, Length = 200)]
        public string Address
        {
            get { return this._address; }
            set { this._address = value; }
        }

        [Column(Name = "PHONE", DbType = StdDbType.AnsiString, Length = 50)]
        public string Mobile
        {
            get { return this._mobile; }
            set { this._mobile = value; }
        }

        [Column(Name = "PHONE1", DbType = StdDbType.AnsiString, Length = 30)]
        public string Phone
        {
            get { return this._phone; }
            set { this._phone = value; }
        }

        [Column(Name = "CONTACT", DbType = StdDbType.AnsiString, Length = 20)]
        public string Contact
        {
            get { return this._contact; }
            set { this._contact = value; }
        }

        [Column(Name = "COMMENTS", DbType = StdDbType.AnsiString, Length = 1000)]
        public string Comments
        {
            get { return this._comments; }
            set { this._comments = value; }
        }

        [Column(Name = "REMARK", DbType = StdDbType.AnsiString, Length = 1000)]
        public string Remark
        {
            get { return this._remark; }
            set { this._remark = value; }
        }

        [Column(Name = "CHECK_PERSON", DbType = StdDbType.AnsiString, Length = 3)]
        public string CheckPerson
        {
            get { return this._checkPerson; }
            set { this._checkPerson = value; }
        }

        [Column(Name = "CHECK_DATE", DbType = StdDbType.DateTime)]
        public DateTime CheckDate
        {
            get { return this._checkDate; }
            set { this._checkDate = value; }
        }

        /// <summary>
        /// 包装费
        /// </summary>
        [Column(Name = "PACKAGE_FEE", DbType = StdDbType.Number, Precision = 8, Scale = 2)]
        public decimal PackageFee
        {
            get { return this._packageFee; }
            set { this._packageFee = value; }
        }
        /// <summary>
        /// 订单总金额
        /// </summary>
        [Column(Name = "GOODS_FEE", DbType = StdDbType.Number, Precision = 8, Scale = 2)]
        public decimal GoodsFee
        {
            get { return this._goodsFee; }
            set { this._goodsFee = value; }
        }
        /// <summary>
        /// 礼券抵扣金额
        /// </summary>
        [Column(Name = "APPEND_FEE", DbType = StdDbType.Number, Precision = 8, Scale = 2)]
        public decimal AppendFee
        {
            get { return this._appendFee; }
            set { this._appendFee = value; }
        }
        /// <summary>
        /// 运费
        /// </summary>
        [Column(Name = "DELIVERY_FEE", DbType = StdDbType.Int32)]
        public int DeliveryFee
        {
            get { return this._deliveryFee; }
            set { this._deliveryFee = value; }
        }
        /// <summary>
        /// 已支付金额（使用帐户上原有的余额，或者邮局、银行汇款支付的以及支付宝、快钱、银联、信用卡等网上支付的）
        /// </summary>
        [Column(Name = "PAYED_MONEY", DbType = StdDbType.Number, Precision = 8, Scale = 2)]
        public decimal PaidAmt
        {
            get { return this._paidAmt; }
            set { this._paidAmt = value; }
        }

        [Column(Name = "PACKAGEWEIGHT", DbType = StdDbType.Number, Precision = 10, Scale = 2)]
        public decimal PackageWeight
        {
            get { return this._packageWeight; }
            set { this._packageWeight = value; }
        }

        [Column(Name = "ACCOUNT_PAYABLE", DbType = StdDbType.Number, Precision = 8, Scale = 2)]
        public decimal AgentAmt
        {
            get { return this._agentAmt; }
            set { this._agentAmt = value; }
        }

        [Column(Name = "PACKAGE_PERSON", DbType = StdDbType.AnsiString, Length = 3)]
        public string PackagePerson
        {
            get { return this._packagePerson; }
            set { this._packagePerson = value; }
        }

        [Column(Name = "STATE", DbType = StdDbType.AnsiString, Length = 200)]
        public string Province
        {
            get { return this._state; }
            set { this._state = value; }
        }

        [Column(Name = "CITY", DbType = StdDbType.AnsiString, Length = 200)]
        public string City
        {
            get { return this._city; }
            set { this._city = value; }
        }

        [Column(Name = "PACKAGE_DATE", DbType = StdDbType.DateTime)]
        public DateTime PackageDate
        {
            get { return this._packageDate; }
            set { this._packageDate = value; }
        }

        [Column(Name = "DELIVERY_DATE", DbType = StdDbType.DateTime)]
        public DateTime DeliveryDate
        {
            get { return this._deliveryDate; }
            set { this._deliveryDate = value; }
        }

        [Column(Name = "DELIVERY_TYPE", DbType = StdDbType.Int32)]
        public int DeliveryType
        {
            get { return this._deliveryType; }
            set { this._deliveryType = value; }
        }

        [Column(Name = "CREATOR_ID", DbType = StdDbType.Int32)]
        public int CreateUser
        {
            get { return this._createUser; }
            set { this._createUser = value; }
        }

        [Column(Name = "CREATE_DATE", DbType = StdDbType.DateTime)]
        public DateTime CreateTime
        {
            get { return this._createTime; }
            set { this._createTime = value; }
        }

        [Column(Name = "INVOICE_NUMBER", DbType = StdDbType.AnsiString, Length = 40)]
        public string InvoiceNumber
        {
            get { return this._invoiceNumber; }
            set { this._invoiceNumber = value; }
        }

        [Column(Name = "IS_INVOICE", DbType = StdDbType.AnsiChar, Length = 1)]
        public bool IsInvoice
        {
            get { return this._isInvoice; }
            set { this._isInvoice = value; }
        }

        [Column(Name = "SHIPPING_NUMBER", DbType = StdDbType.AnsiString, Length = 30)]
        public string ShippingNumber
        {
            get { return this._shippingNumber; }
            set { this._shippingNumber = value; }
        }

        [Column(Name = "LOGISTIC_ID", DbType = StdDbType.Int32)]
        public int LogisticsID
        {
            get { return this._logisticsId; }
            set { this._logisticsId = value; }
        }

        [Column(Name = "PAYMENT_METHOD", DbType = StdDbType.Int32)]
        public int PaymentMethod
        {
            get { return this._paymentMethod; }
            set { this._paymentMethod = value; }
        }

        [Column(Name = "PACKAGE_COUNT", DbType = StdDbType.Int32)]
        public int PackageCount
        {
            get { return this._packageCount; }
            set { this._packageCount = value; }
        }

        /// <summary>
        /// 包装方式
        /// </summary>
        [Column(Name = "PACKAGE_CATEGORY", DbType = StdDbType.Int32)]
        public int PackageType
        {
            get { return this._packageType; }
            set { this._packageType = value; }
        }

        private DateTime _printDate;
        [Column(Name = "PRINT_DATE", DbType = StdDbType.DateTime)]
        public DateTime PrintDate
        {
            get { return this._printDate; }
            set { this._printDate = value; }
        }
        #endregion

        #region Entity Methods
        public CRMSN()
        {
        }

        public bool Create(ISession session)
        {
            return EntityManager.Create(session, this);
        }
        public bool Update(ISession session)
        {
            return EntityManager.Update(session, this);
        }
        public bool Update(ISession session, params string[] propertyNames2Update)
        {
            return EntityManager.Update(session, this, propertyNames2Update);
        }
        public bool Delete(ISession session)
        {
            return EntityManager.Delete(session, this);
        }
        public static CRMSN Retrieve(ISession session, int iD)
        {
            return EntityManager.Retrieve<CRMSN>(session, iD);
        }
        public static bool Delete(ISession session, int iD)
        {
            return EntityManager.Delete<CRMSN>(session, iD);
        }
        #endregion
    }
}