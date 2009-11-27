//*******************************************
// ** Description:  Data Access Object for PurchaseGroup2User
// ** Author     :  Code generator
// ** Created    :   2008-6-30 20:22:21
// ** Modified   :
//*******************************************

namespace Magic.Basis
{
	using System;
	using Magic.Framework.Data;
	using Magic.Framework.ORM;
	using Magic.Framework.ORM.Mapping;

	/// <summary>
	/// Data Access Object for PurchaseGroup2User
	/// 采购组与用户对应关系
	/// </summary>
	[Table("BAS_PUR_GRP2USR")]
	public partial class  PurchaseGroup2User : IEntity
	{
		#region Private Fields
		private string _purchGroupCode;
		private int _userID;
		#endregion

		#region Public Properties
		[Column(Name = "PUR_GP_CODE", DbType = StdDbType.AnsiString, Length = 3, IsPrimary = true)]
		public string PurchGroupCode
		{
			get { return this._purchGroupCode; }
			set { this._purchGroupCode = value; }
		}

		[Column(Name = "USR_ID", DbType = StdDbType.Int32, IsPrimary = true)]
		public int UserID
		{
			get { return this._userID; }
			set { this._userID = value; }
		}

		#endregion

		#region Entity Methods
		public PurchaseGroup2User()
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
		public static PurchaseGroup2User Retrieve(ISession session, string purchGroupCode, int userID)
		{
			return EntityManager.Retrieve<PurchaseGroup2User>(session, new string[]{ "PurchGroupCode", "UserID" },  new object[]{ purchGroupCode, userID });
		}
		public static bool Delete(ISession session, string purchGroupCode, int userID)
		{
			return EntityManager.Delete<PurchaseGroup2User>(session, new string[]{ "PurchGroupCode", "UserID" },  new object[]{ purchGroupCode, userID })>0;
		}
		#endregion
	}
}
