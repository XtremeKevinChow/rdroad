//*******************************************
// ** Description:  Data Access Object for OrderTransDefOptions
// ** Author     :  Code generator
// ** Created    :   2008-7-1 3:29:10
// ** Modified   :
//*******************************************

namespace Magic.ERP.Core
{
	using System;
	using Magic.Framework.Data;
	using Magic.Framework.ORM;
	using Magic.Framework.ORM.Mapping;

	/// <summary>
	/// Data Access Object for OrderTransDefOptions
	/// </summary>
	[Table("ORD_TRANS_DEF_OPT")]
	public partial class  OrderTransDefOptions : IEntity
	{
		#region Private Fields
		private int _transDefID;
		private string _transTypeCode;
		private string _alias;
		#endregion

		#region Public Properties
		[Column(Name = "TRANS_DEF_ID", DbType = StdDbType.Int32, IsPrimary = true)]
		public int TransDefID
		{
			get { return this._transDefID; }
			set { this._transDefID = value; }
		}

		///<summary>
		/// ½»Ò×´úÂë
		///</summary>
		[Column(Name = "TRANS_TYPE_CODE", DbType = StdDbType.AnsiChar, Length = 3, IsPrimary = true)]
		public string TransTypeCode
		{
			get { return this._transTypeCode; }
			set { this._transTypeCode = value; }
		}

		[Column(Name = "TRANS_DEF_ALIAS", DbType = StdDbType.UnicodeString, Length = 20)]
		public string Alias
		{
			get { return this._alias; }
			set { this._alias = value; }
		}

		#endregion

		#region Entity Methods
		public OrderTransDefOptions()
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
		public static OrderTransDefOptions Retrieve(ISession session, int transDefID, string transTypeCode)
		{
			return EntityManager.Retrieve<OrderTransDefOptions>(session, new string[]{ "TransDefID", "TransTypeCode" },  new object[]{ transDefID, transTypeCode });
		}
		public static bool Delete(ISession session, int transDefID, string transTypeCode)
		{
			return EntityManager.Delete<OrderTransDefOptions>(session, new string[]{ "TransDefID", "TransTypeCode" },  new object[]{ transDefID, transTypeCode })>0;
		}
		#endregion
	}
}
