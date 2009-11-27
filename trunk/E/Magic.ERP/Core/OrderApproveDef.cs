//*******************************************
// ** Description:  Data Access Object for OrderApproveDef
// ** Author     :  Code generator
// ** Created    :   2008-7-15 0:35:27
// ** Modified   :
//*******************************************

namespace Magic.ERP.Core
{
	using System;
	using Magic.Framework.Data;
	using Magic.Framework.ORM;
	using Magic.Framework.ORM.Mapping;

	/// <summary>
	/// Data Access Object for OrderApproveDef
	/// 单据签核流程定义
	/// </summary>
	[Table("ORD_APRV_DEF")]
	public partial class  OrderApproveDef : IEntity
	{
		#region Private Fields
		private string _orderTypeCode;
		private int _stepIndex;
		private int _userID;
		private int _userSequnce;
		#endregion

		#region Public Properties
		///<summary>
		/// 单据类型代码
		///</summary>
		[Column(Name = "ORD_TYPE_CODE", DbType = StdDbType.AnsiChar, Length = 3, IsPrimary = true)]
		public string OrderTypeCode
		{
			get { return this._orderTypeCode; }
			set { this._orderTypeCode = value; }
		}

		[Column(Name = "STEP_INDX", DbType = StdDbType.Int32, IsPrimary = true)]
		public int StepIndex
		{
			get { return this._stepIndex; }
			set { this._stepIndex = value; }
		}

		[Column(Name = "USR_ID", DbType = StdDbType.Int32, IsPrimary = true)]
		public int UserID
		{
			get { return this._userID; }
			set { this._userID = value; }
		}

		[Column(Name = "USR_SEQ", DbType = StdDbType.Int32)]
		public int UserSequnce
		{
			get { return this._userSequnce; }
			set { this._userSequnce = value; }
		}

		#endregion

		#region Entity Methods
		public OrderApproveDef()
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
		public static OrderApproveDef Retrieve(ISession session, string orderTypeCode, int stepIndex, int userID)
		{
			return EntityManager.Retrieve<OrderApproveDef>(session, new string[]{ "OrderTypeCode", "StepIndex", "UserID" },  new object[]{ orderTypeCode, stepIndex, userID });
		}
		public static bool Delete(ISession session, string orderTypeCode, int stepIndex, int userID)
		{
			return EntityManager.Delete<OrderApproveDef>(session, new string[]{ "OrderTypeCode", "StepIndex", "UserID" },  new object[]{ orderTypeCode, stepIndex, userID })>0;
		}
		#endregion
	}
}
