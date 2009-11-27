//*******************************************
// ** Description:  Data Access Object for OrderApproveItem
// ** Author     :  Code generator
// ** Created    :   2008-6-30 20:23:03
// ** Modified   :
//*******************************************

namespace Magic.ERP.Core
{
	using System;
	using Magic.Framework.Data;
	using Magic.Framework.ORM;
	using Magic.Framework.ORM.Mapping;

	/// <summary>
	/// Data Access Object for OrderApproveItem
	/// 要签核的单据
	/// </summary>
	[Table("ORD_APRV_ITM")]
	public partial class  OrderApproveItem : IEntity
	{
		#region Private Fields
		private int _approveID;
		private string _orderTypeCode;
		private string _orderNumber;
        private ApproveStatus _status;
        private DateTime _approveTime;
		private int _submitUser;
		private DateTime _submitTime;
		private int _createUser;
		private DateTime _createTime;
		#endregion

		#region Public Properties
        [Column(Name = "APRV_ID", DbType = StdDbType.Int32, IsPrimary = true, IsSequence = true, SequenceName = "SEQ_ORD_APRV_ITM")]
		public int ApproveID
		{
			get { return this._approveID; }
			set { this._approveID = value; }
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

		[Column(Name = "ORD_NUM", DbType = StdDbType.AnsiString, Length = 16)]
		public string OrderNumber
		{
			get { return this._orderNumber; }
			set { this._orderNumber = value; }
		}

		///<summary>
		/// 1: 待签核
		/// 2: 签核中
		/// 3: 已完成
		///</summary>
		[Column(Name = "APRV_STATUS", DbType = StdDbType.Int32)]
        public ApproveStatus Status
		{
			get { return this._status; }
			set { this._status = value; }
		}

        ///<summary>
        /// 签核时间
        ///</summary>
        [Column(Name = "APRV_TIME", DbType = StdDbType.DateTime)]
        public DateTime ApproveTime
        {
            get { return this._approveTime; }
            set { this._approveTime = value; }
        }

		[Column(Name = "SUBMIT_USR", DbType = StdDbType.Int32)]
		public int SubmitUser
		{
			get { return this._submitUser; }
			set { this._submitUser = value; }
		}

		[Column(Name = "SUBMIT_TIME", DbType = StdDbType.DateTime)]
		public DateTime SubmitTime
		{
			get { return this._submitTime; }
			set { this._submitTime = value; }
		}

		[Column(Name = "ORD_CREATE_USR", DbType = StdDbType.Int32)]
		public int CreateUser
		{
			get { return this._createUser; }
			set { this._createUser = value; }
		}

		[Column(Name = "ORD_CREATE_TIME", DbType = StdDbType.DateTime)]
		public DateTime CreateTime
		{
			get { return this._createTime; }
			set { this._createTime = value; }
		}

		#endregion

		#region Entity Methods
		public OrderApproveItem()
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
		public static OrderApproveItem Retrieve(ISession session, int approveID)
		{
			return EntityManager.Retrieve<OrderApproveItem>(session, approveID);
		}
		public static bool Delete(ISession session, int approveID)
		{
			return EntityManager.Delete<OrderApproveItem>(session, approveID);
		}
		#endregion
	}
}
