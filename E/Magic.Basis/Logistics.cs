//*******************************************
// ** Description:  Data Access Object for Logistics
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
	/// Data Access Object for Logistics
	/// 物流商，即配送商
	/// </summary>
	[Table("BAS_LC_COMP")]
	public partial class  Logistics : IEntity
	{
		#region Private Fields
        private int _logisticCompID;
        private LogisticsStatus _status;
		private string _shortName;
		private string _fullName;
		private string _address;
		private string _zipCode;
		private string _contact;
		private string _phone;
		private string _fax;
		private int _settlementPeriod;
		private bool _hasPledge;
		private decimal _pledgeAmount;
		private string _logisticsScope;
		private string _bankAccount;
        private bool _canReturn;
		#endregion

		#region Public Properties
        [Column(Name = "LC_ID", DbType = StdDbType.Int32, IsPrimary = true, IsSequence = true, SequenceName = "SEQ_BAS_LC_COMP")]
		public int LogisticCompID
		{
			get { return this._logisticCompID; }
			set { this._logisticCompID = value; }
		}

		///<summary>
		/// 0: 删除
		/// 1: 无效
		/// 2: 有效
		///</summary>
		[Column(Name = "LC_STATUS", DbType = StdDbType.Int32)]
        public LogisticsStatus Status
		{
			get { return this._status; }
			set { this._status = value; }
		}

		///<summary>
		/// 简称
		///</summary>
		[Column(Name = "LC_SHORT_NAME", DbType = StdDbType.UnicodeString, Length = 20)]
		public string ShortName
		{
			get { return this._shortName; }
			set { this._shortName = value; }
		}

		///<summary>
		/// 全称
		///</summary>
		[Column(Name = "LC_FULL_NAME", DbType = StdDbType.UnicodeString, Length = 40)]
		public string FullName
		{
			get { return this._fullName; }
			set { this._fullName = value; }
		}

		///<summary>
		/// 公司地址
		///</summary>
		[Column(Name = "LC_ADDR", DbType = StdDbType.UnicodeString, Length = 70)]
		public string Address
		{
			get { return this._address; }
			set { this._address = value; }
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
		/// 联系人
		///</summary>
		[Column(Name = "LC_CONTACT", DbType = StdDbType.UnicodeString, Length = 20)]
		public string Contact
		{
			get { return this._contact; }
			set { this._contact = value; }
		}

		///<summary>
		/// 联系电话
		///</summary>
		[Column(Name = "LC_PHONE", DbType = StdDbType.AnsiString, Length = 18)]
		public string Phone
		{
			get { return this._phone; }
			set { this._phone = value; }
		}

		///<summary>
		/// 传真
		///</summary>
		[Column(Name = "LC_FAX", DbType = StdDbType.AnsiString, Length = 18)]
		public string Fax
		{
			get { return this._fax; }
			set { this._fax = value; }
		}

		///<summary>
		/// 结算周期
		///</summary>
		[Column(Name = "SETTL_PERIOD", DbType = StdDbType.Int32)]
		public int SettlementPeriod
		{
			get { return this._settlementPeriod; }
			set { this._settlementPeriod = value; }
		}

		///<summary>
		/// 是否有抵押款
		///</summary>
		[Column(Name = "HAS_PLEDGE", DbType = StdDbType.Bool)]
		public bool HasPledge
		{
			get { return this._hasPledge; }
			set { this._hasPledge = value; }
		}

		///<summary>
		/// 抵押款金额
		///</summary>
		[Column(Name = "PLEDGE_AMT", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal PledgeAmount
		{
			get { return this._pledgeAmount; }
			set { this._pledgeAmount = value; }
		}

		///<summary>
		/// 配送范围
		///</summary>
		[Column(Name = "LC_SCOPE", DbType = StdDbType.UnicodeString, Length = 20)]
		public string LogisticsScope
		{
			get { return this._logisticsScope; }
			set { this._logisticsScope = value; }
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
        /// 是否可以退货
        ///</summary>
        [Column(Name = "can_return", DbType = StdDbType.Bool)]
        public bool CanReturn
        {
            get { return this._canReturn; }
            set { this._canReturn = value; }
        }
		#endregion

		#region Entity Methods
		public Logistics()
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
		public static Logistics Retrieve(ISession session, int logisticCompID)
		{
			return EntityManager.Retrieve<Logistics>(session, logisticCompID);
		}
		public static bool Delete(ISession session, int logisticCompID)
		{
			return EntityManager.Delete<Logistics>(session, logisticCompID);
		}
		#endregion
	}
}
