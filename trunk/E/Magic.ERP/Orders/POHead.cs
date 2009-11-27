//*******************************************
// ** Description:  Data Access Object for POHead
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
	/// Data Access Object for POHead
	/// 采购订单主档 PO - Purchase Order
	/// 单据类型为：PO0（ORD_TYPE_DEF中定义）
	/// </summary>
	[Table("ORD_PUR_HEAD")]
	public partial class  POHead : IEntity
	{
		#region Private Fields
		private string _orderNumber;
		private int _companyID;
		private string _locationCode;
		private string _purchGroupCode;
		private int _vendorID;
        private DateTime _defaultPlanDate;
        private POStatus _status;
		private decimal _taxInclusiveAmt;
		private decimal _taxAmt;
		private decimal _taxExclusiveAmt;
		private string _shippingAddress;
		private string _tradeTerms;
		private string _paymentTerms;
		private int _createUser;
		private DateTime _createTime;
        private ApproveStatus _approveResult;
		private int _approveUser;
		private DateTime _approveTime;
		private string _approveNote;
		private string _currentLineNumber;
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
		/// 公司ID，值暂时设置为1
		///</summary>
		[Column(Name = "COMP_ID", DbType = StdDbType.Int32)]
		public int CompanyID
		{
			get { return this._companyID; }
			set { this._companyID = value; }
		}

		///<summary>
		/// 仓库
		///</summary>
		[Column(Name = "LOC_CODE", DbType = StdDbType.AnsiString, Length = 8)]
		public string LocationCode
		{
			get { return this._locationCode; }
			set { this._locationCode = value; }
		}

		///<summary>
		/// 采购组
		///</summary>
		[Column(Name = "PUR_GP_CODE", DbType = StdDbType.AnsiString, Length = 3)]
		public string PurchGroupCode
		{
			get { return this._purchGroupCode; }
			set { this._purchGroupCode = value; }
		}

		///<summary>
		/// 供应商ID
		///</summary>
		[Column(Name = "VEN_ID", DbType = StdDbType.Int32)]
		public int VendorID
		{
			get { return this._vendorID; }
			set { this._vendorID = value; }
		}

        [Column(Name = "DEF_PLAN_DATE", DbType = StdDbType.DateTime)]
        public DateTime DefaultPlanDate
        {
            get { return this._defaultPlanDate; }
            set { this._defaultPlanDate = value; }
        }

		///<summary>
		/// 1: 新建 New
		/// 2: 发布 Release（发布时送签）
		/// 3: 已完成 Close（收货时如果收货数量等于采购数量，会自动Close这个采购订单；否则需要采购员手工Close）
		///</summary>
		[Column(Name = "ORD_STATUS", DbType = StdDbType.Int32)]
        public POStatus Status
		{
			get { return this._status; }
			set { this._status = value; }
		}

        ///<summary>
        /// 含税总金额，明细项目中的含税额汇总
        ///</summary>
        [Column(Name = "TAX_IN_AMT", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
        public decimal TaxInclusiveAmt
        {
            get { return this._taxInclusiveAmt; }
            set { this._taxInclusiveAmt = value; }
        }

		///<summary>
		/// 税额，明细项目中的税额汇总
		///</summary>
		[Column(Name = "TAX_AMT", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal TaxAmt
		{
			get { return this._taxAmt; }
			set { this._taxAmt = value; }
		}

		///<summary>
		/// 不含税总金额，明细项目中的不含税额汇总
		///</summary>
		[Column(Name = "TAX_EX_AMT", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal TaxExclusiveAmt
		{
			get { return this._taxExclusiveAmt; }
			set { this._taxExclusiveAmt = value; }
		}

		///<summary>
		/// 送货地址，选择了LocationCode之后，如果送货地址为空，则自动抓取WHLocation的Address属性值
		///</summary>
		[Column(Name = "SHIP_ADDR", DbType = StdDbType.UnicodeString, Length = 70)]
		public string ShippingAddress
		{
			get { return this._shippingAddress; }
			set { this._shippingAddress = value; }
		}

		///<summary>
		/// 贸易条款，保留，不用维护这个属性
		///</summary>
		[Column(Name = "TRADE_TERMS", DbType = StdDbType.AnsiString, Length = 15)]
		public string TradeTerms
		{
			get { return this._tradeTerms; }
			set { this._tradeTerms = value; }
		}

		///<summary>
		/// 付款条款，保留，不用维护这个属性
		///</summary>
		[Column(Name = "PAY_TERMS", DbType = StdDbType.AnsiString, Length = 12)]
		public string PaymentTerms
		{
			get { return this._paymentTerms; }
			set { this._paymentTerms = value; }
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
		[Column(Name = "CUR_LINE_NUM", DbType = StdDbType.AnsiString, Length = 10)]
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

		#endregion

		#region Entity Methods
		public POHead()
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
		public static POHead Retrieve(ISession session, string orderNumber)
		{
			return EntityManager.Retrieve<POHead>(session, orderNumber);
		}
		public static bool Delete(ISession session, string orderNumber)
		{
			return EntityManager.Delete<POHead>(session, orderNumber);
		}
		#endregion
	}
}