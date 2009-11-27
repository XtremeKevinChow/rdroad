//*******************************************
// ** Description:  Data Access Object for DeliverHead
// ** Author     :  Code generator
// ** Created    :   2008-7-9 1:21:51
// ** Modified   :
//*******************************************

namespace Magic.ERP.Orders
{
    using System;
    using Magic.Framework.Data;
    using Magic.Framework.ORM;
    using Magic.Framework.ORM.Mapping;

    /// <summary>
    /// Data Access Object for DeliverHead
    /// 发货单主档
    /// </summary>
    [Table("ORD_DLV_HEAD")]
    public partial class DeliverHead : IEntity
    {
        #region Private Fields
        private string _orderNumber;
        private string _orderTypeCode;
        private int _customerID;
        private DeliverStatus _status;
        private decimal _agentAmt;
        private string _refOrderNumber;
        private string _orginalOrderNumber;
        private int _createUser;
        private DateTime _createTime;
        private ApproveStatus _approveResult;
        private int _approveUser;
        private DateTime _approveTime;
        private string _approveNote;
        private string _currentLineNumber;
        private string _note;
        private string _greetingWord;
        private int _packageType;
        private bool _interchanged;
        private string _interchangeNumber;
        private DateTime _deliverDate;
        #endregion

        #region Public Properties
        [Column(Name = "ORD_NUM", DbType = StdDbType.AnsiString, Length = 16, IsPrimary = true)]
        public string OrderNumber
        {
            get { return this._orderNumber; }
            set { this._orderNumber = value; }
        }

        ///<summary>
        /// 单据类型代码
        ///</summary>
        [Column(Name = "ORD_TYPE_CODE", DbType = StdDbType.AnsiChar, Length = 3)]
        public string OrderTypeCode
        {
            get { return this._orderTypeCode; }
            set { this._orderTypeCode = value; }
        }

        [Column(Name = "CUS_ID", DbType = StdDbType.Int32)]
        public int CustomerID
        {
            get { return this._customerID; }
            set { this._customerID = value; }
        }

        ///<summary>
        /// 1: 新增
        /// 2: 发布（送签）
        /// 3: 已完成（签核通过后）
        ///</summary>
        [Column(Name = "ORD_STATUS", DbType = StdDbType.Int32)]
        public DeliverStatus Status
        {
            get { return this._status; }
            set { this._status = value; }
        }

        ///<summary>
        /// 代收款总金额
        ///</summary>
        [Column(Name = "AGENT_AMT", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
        public decimal AgentAmt
        {
            get { return this._agentAmt; }
            set { this._agentAmt = value; }
        }

        ///<summary>
        /// 引用单据号码：对于销售发货该属性为销售订单号码，对换货发货该属性为换货收货单号码
        ///</summary>
        [Column(Name = "REF_ORD_NUM", DbType = StdDbType.AnsiString, Length = 16)]
        public string RefOrderNumber
        {
            get { return this._refOrderNumber; }
            set { this._refOrderNumber = value; }
        }

        ///<summary>
        /// 原始单据号码：对销售发货和换货发货该属性均为销售订单号码
        ///</summary>
        [Column(Name = "ORG_ORD_NUM", DbType = StdDbType.AnsiString, Length = 16)]
        public string OrginalOrderNumber
        {
            get { return this._orginalOrderNumber; }
            set { this._orginalOrderNumber = value; }
        }

        [Column(Name = "CREATE_USR", DbType = StdDbType.Int32)]
        public int CreateUser
        {
            get { return this._createUser; }
            set { this._createUser = value; }
        }

        [Column(Name = "CREATE_TIME", DbType = StdDbType.DateTime)]
        public DateTime CreateTime
        {
            get { return this._createTime; }
            set { this._createTime = value; }
        }

        ///<summary>
        /// 0: 未签核
        /// 1: 签核通过
        /// -1: 签核驳回
        ///</summary>
        [Column(Name = "APRV_RSLT", DbType = StdDbType.Int32, Precision = 1)]
        public ApproveStatus ApproveResult
        {
            get { return this._approveResult; }
            set { this._approveResult = value; }
        }

        ///<summary>
        /// 签核用户，签核完毕后该字段会自动设置，不需要维护
        ///</summary>
        [Column(Name = "APRV_USR", DbType = StdDbType.Int32)]
        public int ApproveUser
        {
            get { return this._approveUser; }
            set { this._approveUser = value; }
        }

        ///<summary>
        /// 签核时间，签核完毕后该字段会自动设置，不需要维护
        ///</summary>
        [Column(Name = "APRV_TIME", DbType = StdDbType.DateTime)]
        public DateTime ApproveTime
        {
            get { return this._approveTime; }
            set { this._approveTime = value; }
        }

        [Column(Name = "APRV_NOTE", DbType = StdDbType.UnicodeString, Length = 40)]
        public string ApproveNote
        {
            get { return this._approveNote; }
            set { this._approveNote = value; }
        }

        ///<summary>
        /// 行号以0010, 0020, 0030这样的方式编码，CurrentLineNumber存储的为当前最大行号值，添加下一个行号时将这个值转换为整数加10再转换为4位数字
        ///</summary>
        [Column(Name = "CUR_LINE_NUM", DbType = StdDbType.AnsiString, Length = 4)]
        public string CurrentLineNumber
        {
            get { return this._currentLineNumber; }
            set { this._currentLineNumber = value; }
        }

        [Column(Name = "ORD_NOTE", DbType = StdDbType.UnicodeString, Length = 50)]
        public string Note
        {
            get { return this._note; }
            set { this._note = value; }
        }

        ///<summary>
        /// 订单贺卡的祝福语
        ///</summary>
        [Column(Name = "GRT_WORD", DbType = StdDbType.AnsiString, Length = 100)]
        public string GreetingWord
        {
            get { return this._greetingWord; }
            set { this._greetingWord = value; }
        }

        ///<summary>
        /// 礼品包装、普通包装等包装方式
        ///</summary>
        [Column(Name = "PACK_TYPE", DbType = StdDbType.Int32)]
        public int PackageType
        {
            get { return this._packageType; }
            set { this._packageType = value; }
        }

        ///<summary>
        /// 是否已经关联到交接单
        ///</summary>
        [Column(Name = "FINISH_INT_CHG", DbType = StdDbType.Bool)]
        public bool Interchanged
        {
            get { return this._interchanged; }
            set { this._interchanged = value; }
        }

        ///<summary>
        /// 关联的交接单号码
        ///</summary>
        [Column(Name = "ORD_IC_NUM", DbType = StdDbType.AnsiString, Length = 16)]
        public string InterchangeNumber
        {
            get { return this._interchangeNumber; }
            set { this._interchangeNumber = value; }
        }

        ///<summary>
        /// 发货日期
        ///</summary>
        [Column(Name = "DLV_DATE", DbType = StdDbType.DateTime)]
        public DateTime DeliverDate
        {
            get { return this._deliverDate; }
            set { this._deliverDate = value; }
        }

        #endregion

        #region Entity Methods
        public DeliverHead()
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
        #endregion
    }
}