//*******************************************
// ** Description:  Data Access Object for RCVHead
// ** Author     :  Code generator
// ** Created    :   2008-6-30 20:22:44
// ** Modified   :
//*******************************************

namespace Magic.ERP.Orders
{
	using System;
	using Magic.Framework.Data;
	using Magic.Framework.ORM;
	using Magic.Framework.ORM.Mapping;

	/// <summary>
	/// Data Access Object for RCVHead
	/// 收货单 RCV - Receive
	/// 包括采购收货、会员退货收货、会员换货收货（不包含物流公司退货）
	/// 采购收货单据类型为：RC1（ORD_TYPE_DEF表中定义）
	/// 会员退货收货单据类型为：RC2
	/// 会员换货收货单据类型为：RC3
	/// </summary>
	[Table("ORD_RCV_HEAD")]
	public partial class  RCVHead : IEntity
	{
		#region Private Fields
		private string _orderNumber;
		private string _orderTypeCode;
        private string _locationCode;
		private int _objectID;
		private string _refOrderType;
		private string _refOrderNumber;
		private string _originalOrderType;
		private string _orginalOrderNumber;
        private ReceiveStatus _status;
        private ApproveStatus _approveResult;
		private int _approveUser;
		private DateTime _approveTime;
		private string _approveNote;
		private int _createUser;
		private DateTime _createTime;
		private string _currentLineNumber;
		private int _reasonID;
		private string _note;
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

		[Column(Name = "OBJ_ID", DbType = StdDbType.Int32)]
		public int ObjectID
		{
			get { return this._objectID; }
			set { this._objectID = value; }
		}

        [Column(Name = "LOC_CODE", DbType = StdDbType.AnsiString, Length = 8)]
        public string LocationCode
        {
            get { return this._locationCode; }
            set { this._locationCode = value; }
        }

		///<summary>
		/// 引用单据类型，
		/// 如果为采购订单收货，引用单据类型为PO0（引用采购订单）
		/// 如果为会员退货收货，引用单据类型为SD0（销售发货单）
		/// 如果为会员换货收货，引用单据类型为SD0（销售发货单）
		///</summary>
		[Column(Name = "REF_ORD_TYPE", DbType = StdDbType.AnsiChar, Length = 3)]
		public string RefOrderType
		{
			get { return this._refOrderType; }
			set { this._refOrderType = value; }
		}

		///<summary>
		/// 引用单据号码
		/// 如果为采购订单收货，引用单据号码为采购订单号
		/// 如果为会员换货或者退货，引用单据号码为销售发货单号
		///</summary>
		[Column(Name = "REF_ORD_NUM", DbType = StdDbType.AnsiString, Length = 16)]
		public string RefOrderNumber
		{
			get { return this._refOrderNumber; }
			set { this._refOrderNumber = value; }
		}

		///<summary>
		/// 原始单据类型，
		/// 如果为采购订单收货，原始单据类型为PO0（采购订单）
		/// 如果为会员退货收货，原始单据类型为SD0（销售订单）
		/// 如果为会员换货收货，原始单据类型为SD0（销售订单）
		///</summary>
		[Column(Name = "ORG_ODR_TYPE", DbType = StdDbType.AnsiChar, Length = 3)]
		public string OriginalOrderType
		{
			get { return this._originalOrderType; }
			set { this._originalOrderType = value; }
		}

		///<summary>
		/// 原始单据号码
		/// 如果为采购订单收货，原始单据号码为采购订单号码
		/// 如果为会员换货或者退货，原始单据号码为销售订单号码
		///</summary>
		[Column(Name = "ORG_ORD_NUM", DbType = StdDbType.AnsiString, Length = 16)]
		public string OrginalOrderNumber
		{
			get { return this._orginalOrderNumber; }
			set { this._orginalOrderNumber = value; }
		}

		[Column(Name = "ORD_STATUS", DbType = StdDbType.Int32)]
        public ReceiveStatus Status
		{
			get { return this._status; }
			set { this._status = value; }
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
		/// 行号以0010, 0020, 0030这样的方式编码，CurrentLineNumber存储的为当前最大行号值，添加下一个行号时将这个值转换为整数加10再转换为4位数字
		///</summary>
		[Column(Name = "CUR_LINE_NUM", DbType = StdDbType.AnsiString, Length = 10)]
		public string CurrentLineNumber
		{
			get { return this._currentLineNumber; }
			set { this._currentLineNumber = value; }
		}

		[Column(Name = "RSON_ID", DbType = StdDbType.Int32)]
		public int ReasonID
		{
			get { return this._reasonID; }
			set { this._reasonID = value; }
		}

		[Column(Name = "ORD_NOTE", DbType = StdDbType.UnicodeString, Length = 50)]
		public string Note
		{
			get { return this._note; }
			set { this._note = value; }
		}

		#endregion

		#region Entity Methods
		public RCVHead()
		{
            this.Status = ReceiveStatus.New;
            this.ApproveResult = ApproveStatus.UnApprove;
            this.ApproveTime = new DateTime(1900, 1, 1);
            this.ApproveUser = 0;
            this.ApproveNote = " ";
            this.CreateUser = 0;
            this.CreateTime = DateTime.Now;
            this.CurrentLineNumber = "0000";
            this.ReasonID = 0;
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
			this.DeleteLines(session);
            EntityManager.Delete(session, this);
            return true;
		}
		public static RCVHead Retrieve(ISession session, string orderNumber)
		{
			return EntityManager.Retrieve<RCVHead>(session, orderNumber);
		}
		public static bool Delete(ISession session, string orderNumber)
		{
            RCVHead head = RCVHead.Retrieve(session, orderNumber);
            return head.Delete(session);
		}
		#endregion
	}
}