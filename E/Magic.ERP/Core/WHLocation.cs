//*******************************************
// ** Description:  Data Access Object for WHLocation
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
	/// Data Access Object for WHLocation
	/// 仓储地点
	/// </summary>
	[Table("INV_WH_LOCATION")]
	public partial class  WHLocation : IEntity
	{
		#region Private Fields
		private string _locationCode;
		private int _companyID;
		private string _name;
        private WHStatus _status;
		private string _text;
		private string _address;
		private string _zipCode;
		private string _contact;
		private string _phone;
		private string _faxNumber;
		#endregion

		#region Public Properties
		[Column(Name = "LOC_CODE", DbType = StdDbType.AnsiString, Length = 8, IsPrimary = true)]
		public string LocationCode
		{
			get { return this._locationCode; }
			set { this._locationCode = value; }
		}

		[Column(Name = "COMPANY_ID", DbType = StdDbType.Int32)]
		public int CompanyID
		{
			get { return this._companyID; }
			set { this._companyID = value; }
		}

		[Column(Name = "LOC_NAME", DbType = StdDbType.UnicodeString, Length = 25)]
		public string Name
		{
			get { return this._name; }
			set { this._name = value; }
		}

		///<summary>
		/// 0: 删除
		/// 1: 无效、禁用
		/// 2: 有效
		///</summary>
		[Column(Name = "LOC_STATUS", DbType = StdDbType.Int32, Precision = 1)]
        public WHStatus Status
		{
			get { return this._status; }
			set { this._status = value; }
		}

		[Column(Name = "LOC_TEXT", DbType = StdDbType.UnicodeString, Length = 50)]
		public string Text
		{
			get { return this._text; }
			set { this._text = value; }
		}

		[Column(Name = "LOC_ADDR", DbType = StdDbType.UnicodeString, Length = 70)]
		public string Address
		{
			get { return this._address; }
			set { this._address = value; }
		}

		[Column(Name = "LOC_ZIPCODE", DbType = StdDbType.AnsiString, Length = 10)]
		public string ZipCode
		{
			get { return this._zipCode; }
			set { this._zipCode = value; }
		}

		[Column(Name = "LOC_CONTACT", DbType = StdDbType.UnicodeString, Length = 25)]
		public string Contact
		{
			get { return this._contact; }
			set { this._contact = value; }
		}

		[Column(Name = "LOC_PHONE", DbType = StdDbType.AnsiString, Length = 20)]
		public string Phone
		{
			get { return this._phone; }
			set { this._phone = value; }
		}

		[Column(Name = "FAX_NUM", DbType = StdDbType.AnsiString, Length = 16)]
		public string FaxNumber
		{
			get { return this._faxNumber; }
			set { this._faxNumber = value; }
		}

		#endregion

		#region Entity Methods
		public WHLocation()
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
		public static WHLocation Retrieve(ISession session, string locationCode)
		{
			return EntityManager.Retrieve<WHLocation>(session, locationCode);
		}
		public static bool Delete(ISession session, string locationCode)
		{
			return EntityManager.Delete<WHLocation>(session, locationCode);
		}
		#endregion
	}
}
