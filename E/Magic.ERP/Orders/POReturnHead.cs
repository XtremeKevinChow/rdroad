//*******************************************
// ** Description:  Data Access Object for POReturnHead
// ** Author     :  Code generator
// ** Created    :   2008-12-19 2:01:45
// ** Modified   :
//*******************************************

namespace Magic.ERP.Orders
{
	using System;
	using Magic.Framework.Data;
	using Magic.Framework.ORM;
    using Magic.Framework.ORM.Query;
	using Magic.Framework.ORM.Mapping;

	/// <summary>
	/// Data Access Object for POReturnHead
	/// </summary>
	[Table("ORD_PUR_RTN_HEAD")]
	public partial class  POReturnHead : IEntity
	{
		#region Private Fields
		private string _orderNumber;
		private string _locationCode;
		private int _vendorID;
		private POReturnStatus _status;
		private int _createUser;
		private DateTime _createTime;
		private int _approveUser;
		private DateTime _approveTime;
        private ApproveStatus _approveResult;
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
		/// 仓库
		///</summary>
		[Column(Name = "LOC_CODE", DbType = StdDbType.AnsiString, Length = 8)]
		public string LocationCode
		{
			get { return this._locationCode; }
			set { this._locationCode = value; }
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

		///<summary>
		/// 1: 新建 New
		/// 2: 发布 Release（发布时送签）
		/// 3: 已完成 Close（收货时如果收货数量等于采购数量，会自动Close这个采购订单；否则需要采购员手工Close）
		///</summary>
		[Column(Name = "ORD_STATUS", DbType = StdDbType.Int32)]
        public POReturnStatus Status
		{
			get { return this._status; }
			set { this._status = value; }
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

		#endregion

		#region Entity Methods
		public POReturnHead()
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
            session.CreateEntityQuery<POReturnLine>()
                .Where(Exp.Eq("OrderNumber", this.OrderNumber))
                .Delete();
			return EntityManager.Delete(session, this);
		}
		public static POReturnHead Retrieve(ISession session, string orderNumber)
		{
			return EntityManager.Retrieve<POReturnHead>(session, orderNumber);
		}
		public static bool Delete(ISession session, string orderNumber)
		{
            session.CreateEntityQuery<POReturnLine>()
                .Where(Exp.Eq("OrderNumber", orderNumber))
                .Delete();
			return EntityManager.Delete<POReturnHead>(session, orderNumber);
		}
		#endregion
	}
}
