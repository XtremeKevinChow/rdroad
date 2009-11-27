//*******************************************
// ** Description:  Data Access Object for Vendor
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
	/// Data Access Object for Vendor
	/// 供应商
	/// </summary>
	[Table("BAS_VEN_COMP")]
	public partial class  Vendor : IEntity
	{
		#region Private Fields
		private int _vendorID;
		private VendorStatus _status;
		private string _shortName;
		private string _fullName;
		private string _address;
		private string _contact;
		private string _zipCode;
		private string _phone;
		private string _fax;
		private string _bankAccount;
		private int _taxID;
		private decimal _tax;
		#endregion

		#region Public Properties
        [Column(Name = "VEN_ID", DbType = StdDbType.Int32, IsPrimary = true, IsSequence = true, SequenceName = "SEQ_BAS_VEN_COMP")]
		public int VendorID
		{
			get { return this._vendorID; }
			set { this._vendorID = value; }
		}

		///<summary>
		/// 0: 删除
		/// 1: 无效
		/// 2: 有效
		///</summary>
		[Column(Name = "VEN_STATUS", DbType = StdDbType.Int32)]
        public VendorStatus Status
		{
			get { return this._status; }
			set { this._status = value; }
		}

		///<summary>
		/// 简称
		///</summary>
		[Column(Name = "VEN_SHORT_NAME", DbType = StdDbType.UnicodeString, Length = 20)]
		public string ShortName
		{
			get { return this._shortName; }
			set { this._shortName = value; }
		}

		///<summary>
		/// 全称
		///</summary>
		[Column(Name = "VEN_FULL_NAME", DbType = StdDbType.UnicodeString, Length = 40)]
		public string FullName
		{
			get { return this._fullName; }
			set { this._fullName = value; }
		}

		///<summary>
		/// 公司地址
		///</summary>
		[Column(Name = "VEN_ADDR", DbType = StdDbType.UnicodeString, Length = 70)]
		public string Address
		{
			get { return this._address; }
			set { this._address = value; }
		}

		///<summary>
		/// 联系人
		///</summary>
		[Column(Name = "VEN_CONTACT", DbType = StdDbType.UnicodeString, Length = 20)]
		public string Contact
		{
			get { return this._contact; }
			set { this._contact = value; }
		}

		///<summary>
		/// 邮编
		///</summary>
		[Column(Name = "ZIP_CODE", DbType = StdDbType.AnsiString, Length = 10)]
		public string ZipCode
		{
			get { return this._zipCode; }
			set { this._zipCode = value; }
		}

		///<summary>
		/// 联系电话
		///</summary>
		[Column(Name = "VEN_PHONE", DbType = StdDbType.AnsiString, Length = 18)]
		public string Phone
		{
			get { return this._phone; }
			set { this._phone = value; }
		}

		///<summary>
		/// 传真
		///</summary>
		[Column(Name = "VEN_FAX", DbType = StdDbType.AnsiString, Length = 18)]
		public string Fax
		{
			get { return this._fax; }
			set { this._fax = value; }
		}

		///<summary>
		/// 银行帐号
		///</summary>
		[Column(Name = "BANK_ACNT", DbType = StdDbType.AnsiString, Length = 30)]
		public string BankAccount
		{
			get { return this._bankAccount; }
			set { this._bankAccount = value; }
		}

		///<summary>
		/// 关联税率表TaxDef的ID
		///</summary>
        [Column(Name = "TAX_ID", DbType = StdDbType.Int32)]
		public int TaxID
		{
			get { return this._taxID; }
			set { this._taxID = value; }
		}

		///<summary>
		/// 供应商的默认税率
		///</summary>
        [Column(Name = "TAX_VAL", DbType = StdDbType.Number, Precision = 4, Scale = 2)]
		public decimal Tax
		{
			get { return this._tax; }
			set { this._tax = value; }
		}

		#endregion

		#region Entity Methods
		public Vendor()
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
		public static Vendor Retrieve(ISession session, int vendorID)
		{
			return EntityManager.Retrieve<Vendor>(session, vendorID);
		}
		public static bool Delete(ISession session, int vendorID)
		{
			return EntityManager.Delete<Vendor>(session, vendorID);
		}
		#endregion
	}
}
