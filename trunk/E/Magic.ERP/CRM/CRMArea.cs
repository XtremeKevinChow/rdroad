//*******************************************
// ** Description:  Data Access Object for CRMArea
// ** Author     :  Code generator
// ** Created    :   2008-8-31 11:55:00
// ** Modified   :
//*******************************************

namespace Magic.ERP.CRM
{
	using System;
	using Magic.Framework.Data;
	using Magic.Framework.ORM;
	using Magic.Framework.ORM.Mapping;

	/// <summary>
	/// Data Access Object for CRMArea
	/// </summary>
	[Table("S_AREA")]
	public partial class  CRMArea : IEntity
	{
		#region Private Fields
		private string _areaCode;
		private string _areaName;
		private string _parentCode;
		private bool _isRoot;
		private int _zone;
		private bool _isExpress;
		#endregion

		#region Public Properties
		[Column(Name = "AREACODE", DbType = StdDbType.AnsiString, Length = 20, IsPrimary = true)]
		public string AreaCode
		{
			get { return this._areaCode; }
			set { this._areaCode = value; }
		}

		[Column(Name = "AREANAME", DbType = StdDbType.AnsiString, Length = 50)]
		public string AreaName
		{
			get { return this._areaName; }
			set { this._areaName = value; }
		}

		[Column(Name = "PARENTAREACODE", DbType = StdDbType.AnsiString, Length = 20)]
		public string ParentCode
		{
			get { return this._parentCode; }
			set { this._parentCode = value; }
		}

		[Column(Name = "ROOT", DbType = StdDbType.Bool)]
		public bool IsRoot
		{
			get { return this._isRoot; }
			set { this._isRoot = value; }
		}

		[Column(Name = "ZONE", DbType = StdDbType.Int32)]
		public int Zone
		{
			get { return this._zone; }
			set { this._zone = value; }
		}

		[Column(Name = "IS_EXPRESS", DbType = StdDbType.Bool)]
		public bool IsExpress
		{
			get { return this._isExpress; }
			set { this._isExpress = value; }
		}

		#endregion

		#region Entity Methods
		public CRMArea()
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
		public static CRMArea Retrieve(ISession session, string areaCode)
		{
			return EntityManager.Retrieve<CRMArea>(session, areaCode);
		}
		public static bool Delete(ISession session, string areaCode)
		{
			return EntityManager.Delete<CRMArea>(session, areaCode);
		}
		#endregion
	}
}
