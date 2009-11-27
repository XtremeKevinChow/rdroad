//*******************************************
// ** Description:  Data Access Object for ReturnHead
// ** Author     :  Code generator
// ** Created    :   2008-8-25 20:29:49
// ** Modified   :
//*******************************************

namespace Magic.ERP.Orders
{
	using System;
	using Magic.Framework.Data;
	using Magic.Framework.ORM;
	using Magic.Framework.ORM.Mapping;

	/// <summary>
	/// Data Access Object for ReturnHead
	/// 会员退货单主档
	/// </summary>
	[Table("ORD_RTN_HEAD")]
	public partial class  ReturnHead : IEntity
	{
		#region Private Fields
		private string _orderNumber;
		private string _locationCode;
		private string _orderTypeCode;
		private int _memberID;
		private string _memberName;
        private int _logisticsID;
        private string _logisticsName;
		private ReturnStatus _status;
		private bool _isAutoMatch;
		private bool _isMalicious;
        private bool _hasScaned;
        private bool _hasTransported;
		private int _refOrderID;
		private string _refOrderNumber;
		private string _orginalOrderNumber;
		private int _reasonID;
		private string _reasonText;
		private ApproveStatus _approveResult;
		private int _approveUser;
		private DateTime _approveTime;
		private string _approveNote;
		private int _createUser;
		private DateTime _createTime;
		private string _note;
		private string _currentLineNumber;
        private string _csUser;
		#endregion

		#region Public Properties
		[Column(Name = "ORD_NUM", DbType = StdDbType.AnsiString, Length = 16, IsPrimary = true)]
		public string OrderNumber
		{
			get { return this._orderNumber; }
			set { this._orderNumber = value; }
		}

		[Column(Name = "LOC_CODE", DbType = StdDbType.AnsiString, Length = 8)]
		public string LocationCode
		{
			get { return this._locationCode; }
			set { this._locationCode = value; }
		}

		///<summary>
		/// 单据类型代码
		/// 会员退货: RC2；物流退货: RC4；内部退货: RC5
		/// 会员换货: RC3
		///</summary>
		[Column(Name = "ORD_TYPE_CODE", DbType = StdDbType.AnsiChar, Length = 3)]
		public string OrderTypeCode
		{
			get { return this._orderTypeCode; }
			set { this._orderTypeCode = value; }
		}

		///<summary>
		/// 会员ID
		///</summary>
		[Column(Name = "MBR_ID", DbType = StdDbType.Int32)]
		public int MemberID
		{
			get { return this._memberID; }
			set { this._memberID = value; }
		}

		///<summary>
		/// 会员名称
		///</summary>
		[Column(Name = "MBR_NAME", DbType = StdDbType.UnicodeString, Length = 20)]
		public string MemberName
		{
			get { return this._memberName; }
			set { this._memberName = value; }
		}

        ///<summary>
        /// 物流公司ID
        ///</summary>
        [Column(Name = "LOGIS_ID", DbType = StdDbType.Int32)]
        public int LogisticsID
        {
            get { return this._logisticsID; }
            set { this._logisticsID = value; }
        }

        ///<summary>
        /// 物流公司名称
        ///</summary>
        [Column(Name = "LOGIS_NAME", DbType = StdDbType.UnicodeString, Length = 20)]
        public string LogisticsName
        {
            get { return this._logisticsName; }
            set { this._logisticsName = value; }
        }

		[Column(Name = "ORD_STATUS", DbType = StdDbType.Int32)]
        public ReturnStatus Status
		{
			get { return this._status; }
			set { this._status = value; }
		}

		///<summary>
		/// 退货明细必须对应到发货单明细
		/// 扫描退货商品时，如果同一个商品在发货单中存在2个以上明细项目，这个属性表示由系统自动匹配发货明细，还是由用户手工选择
		///</summary>
		[Column(Name = "IS_AUTO_MATCH", DbType = StdDbType.Bool)]
		public bool IsAutoMatch
		{
			get { return this._isAutoMatch; }
			set { this._isAutoMatch = value; }
		}

		///<summary>
		/// 是否恶意退货
		///</summary>
		[Column(Name = "IS_MAL", DbType = StdDbType.Bool)]
		public bool IsMalicious
		{
			get { return this._isMalicious; }
			set { this._isMalicious = value; }
		}

        ///<summary>
        /// 是否已经扫描过退货商品，仅对换货退货类型有用
        ///</summary>
        [Column(Name = "HAS_SCANED", DbType = StdDbType.Bool)]
        public bool HasScaned
        {
            get { return this._hasScaned; }
            set { this._hasScaned = value; }
        }

        ///<summary>
        /// 物流公司是否有发运（没有发运不需要计算运费，例如运输公司将包裹拿到公司后发现目的地无法配送）
        ///</summary>
        [Column(Name = "HAS_TRANSED", DbType = StdDbType.Bool)]
        public bool HasTransported
        {
            get { return this._hasTransported; }
            set { this._hasTransported = value; }
        }

		///<summary>
		/// 发货单ID
		///</summary>
		[Column(Name = "REF_ORD_ID", DbType = StdDbType.Int32)]
		public int RefOrderID
		{
			get { return this._refOrderID; }
			set { this._refOrderID = value; }
		}

		///<summary>
		/// 引用单据号码（发货单号码）
		///</summary>
		[Column(Name = "REF_ORD_NUM", DbType = StdDbType.AnsiString, Length = 16)]
		public string RefOrderNumber
		{
			get { return this._refOrderNumber; }
			set { this._refOrderNumber = value; }
		}

		///<summary>
		/// 原始单据号码（订单号码）
		///</summary>
		[Column(Name = "ORG_ORD_NUM", DbType = StdDbType.AnsiString, Length = 16)]
		public string OrginalOrderNumber
		{
			get { return this._orginalOrderNumber; }
			set { this._orginalOrderNumber = value; }
		}

        private string _exchangeOrder;
        ///<summary>
        /// 换货退货时记录换货订单号码
        ///</summary>
        [Column(Name = "EXCHANGE_ORDER", DbType = StdDbType.AnsiString, Length = 16)]
        public string ExchangeOrder
        {
            get { return this._exchangeOrder; }
            set { this._exchangeOrder = value; }
        }

		///<summary>
		/// 退货原因ID
		///</summary>
		[Column(Name = "RSON_ID", DbType = StdDbType.Int32)]
		public int ReasonID
		{
			get { return this._reasonID; }
			set { this._reasonID = value; }
		}

		///<summary>
		/// 退货原因描述
		///</summary>
		[Column(Name = "RSON_TEXT", DbType = StdDbType.UnicodeString, Length = 50)]
		public string ReasonText
		{
			get { return this._reasonText; }
			set { this._reasonText = value; }
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
		/// 备注
		///</summary>
		[Column(Name = "ORD_NOTE", DbType = StdDbType.UnicodeString, Length = 50)]
		public string Note
		{
			get { return this._note; }
			set { this._note = value; }
		}

		///<summary>
		/// 行号以0010, 0020, 0030这样的方式编码，CurrentLineNumber存储的为当前最大行号值，添加下一个行号时将这个值转换为整数加10再转换为4位数字
		///</summary>
		[Column(Name = "CUR_LINE_NUM", DbType = StdDbType.AnsiString, Length = 10)]
		public string CurrentLineNumber
		{
			get { return this._currentLineNumber; }
			set { this._currentLineNumber = value; }
		}

        ///<summary>
        /// 客服人员
        ///</summary>
        [Column(Name = "CS_USER", DbType = StdDbType.UnicodeString, Length = 30)]
        public string CSUser
        {
            get { return this._csUser; }
            set { this._csUser = value; }
        }
		#endregion

		#region Entity Methods
		public ReturnHead()
		{
            this.Status = ReturnStatus.New;
            this.ApproveResult = ApproveStatus.UnApprove;
            this.ApproveUser = 0;
            this.ApproveTime = new DateTime(1900, 1, 1);
            this.ApproveNote = " ";
            this.CreateUser = 0;
            this.CreateTime = DateTime.Now;
            this.IsAutoMatch = false;
            this.HasTransported = true;

            this.MemberID = 0;
            this.MemberName = " ";
            this.LogisticsID = 0;
            this.LogisticsName = " ";
            this._currentLineNumber = "0000";
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
            if (this.Status != ReturnStatus.New) throw new Exception("退货单不是新建状态，无法删除");
            if (this.OrderTypeCode == ReturnHead.ORDER_TYPE_EXCHANGE_RTN)
                throw new Exception("不能删除换货退货单"); ;
			return EntityManager.Delete(session, this);
		}
		public static ReturnHead Retrieve(ISession session, string orderNumber)
		{
			return EntityManager.Retrieve<ReturnHead>(session, orderNumber);
		}
		public static bool Delete(ISession session, string orderNumber)
		{
            if (string.IsNullOrEmpty(orderNumber) || orderNumber.Trim().Length <= 0) return false;
            ReturnHead head = Retrieve(session, orderNumber);
            if (head == null) return false;
            return head.Delete(session);
		}
		#endregion
	}
}