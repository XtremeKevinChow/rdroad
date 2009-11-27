//*******************************************
// ** Description:  Data Access Object for PurchaseGroup
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
	/// Data Access Object for PurchaseGroup
	/// ²É¹º×é
	/// </summary>
	[Table("BAS_PUR_GRP")]
	public partial class  PurchaseGroup : IEntity
	{
		#region Private Fields
		private string _purchGroupCode;
		private string _purchGroupText;
		#endregion

		#region Public Properties
		[Column(Name = "PUR_GP_CODE", DbType = StdDbType.AnsiString, Length = 3, IsPrimary = true)]
		public string PurchGroupCode
		{
			get { return this._purchGroupCode; }
			set { this._purchGroupCode = value; }
		}

		[Column(Name = "PUR_GP_TEXT", DbType = StdDbType.UnicodeString, Length = 20)]
		public string PurchGroupText
		{
			get { return this._purchGroupText; }
			set { this._purchGroupText = value; }
		}

		#endregion

		#region Entity Methods
		public PurchaseGroup()
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
		public static PurchaseGroup Retrieve(ISession session, string purchGroupCode)
		{
			return EntityManager.Retrieve<PurchaseGroup>(session, purchGroupCode);
		}
		public static bool Delete(ISession session, string purchGroupCode)
		{
			return EntityManager.Delete<PurchaseGroup>(session, purchGroupCode);
		}
		#endregion
	}
}
