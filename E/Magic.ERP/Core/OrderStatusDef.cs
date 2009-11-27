//*******************************************
// ** Description:  Data Access Object for OrderStatusDef
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
	/// Data Access Object for OrderStatusDef
	/// 单据状态描述文本
	/// </summary>
	[Table("ORD_STATUS_DEF")]
	public partial class  OrderStatusDef : IEntity
	{
		#region Private Fields
		private int _statusID;
		private string _orderTypeCode;
		private int _statusValue;
		private string _statusText;
		private int _statusIndex;
		#endregion

		#region Public Properties
        [Column(Name = "STATUS_ID", DbType = StdDbType.Int32, IsPrimary = true, IsSequence = true, SequenceName = "SEQ_ORD_STATUS_DEF")]
		public int StatusID
		{
			get { return this._statusID; }
			set { this._statusID = value; }
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

		[Column(Name = "STATUS_VAL", DbType = StdDbType.Int32)]
		public int StatusValue
		{
			get { return this._statusValue; }
			set { this._statusValue = value; }
		}

		[Column(Name = "STATUS_TEXT", DbType = StdDbType.UnicodeString, Length = 10)]
		public string StatusText
		{
			get { return this._statusText; }
			set { this._statusText = value; }
		}

		[Column(Name = "STATUS_INDX", DbType = StdDbType.Int32)]
		public int StatusIndex
		{
			get { return this._statusIndex; }
			set { this._statusIndex = value; }
		}

		#endregion

		#region Entity Methods
		public OrderStatusDef()
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
		public static OrderStatusDef Retrieve(ISession session, int statusID)
		{
			return EntityManager.Retrieve<OrderStatusDef>(session, statusID);
		}
		public static bool Delete(ISession session, int statusID)
		{
			return EntityManager.Delete<OrderStatusDef>(session, statusID);
		}
		#endregion
	}
}
