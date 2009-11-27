//*******************************************
// ** Description:  Data Access Object for WHTransHead
// ** Author     :  Code generator
// ** Created    :   2008-7-4 23:23:12
// ** Modified   :
//*******************************************

namespace Magic.ERP.Core
{
	using System;
	using Magic.Framework.Data;
	using Magic.Framework.ORM;
	using Magic.Framework.ORM.Mapping;

	/// <summary>
	/// Data Access Object for WHTransHead
	/// </summary>
	[Table("INV_TRANS_HEAD")]
	public partial class  WHTransHead : IEntity
	{
		#region Private Fields
		private int _transNumber;
		private string _transOrderType;
		private string _transOrderNumber;
		private string _refOrderType;
		private string _refOrderNumber;
		private string _originalOrderType;
		private string _originalOrderNumber;
		private int _commitUser;
		private int _commitDate;
		private int _commitTime;
		private string _transDesc;
		#endregion

		#region Public Properties
		[Column(Name = "TRANS_NUM", DbType = StdDbType.Int32, IsPrimary = true, IsSequence = true, SequenceName = "SEQ_INV_TRANS_HEAD")]
		public int TransNumber
		{
			get { return this._transNumber; }
			set { this._transNumber = value; }
		}

		[Column(Name = "TRANS_ORD_TYPE", DbType = StdDbType.AnsiChar, Length = 3)]
		public string TransOrderType
		{
			get { return this._transOrderType; }
			set { this._transOrderType = value; }
		}

		[Column(Name = "TRANS_ORD_NUM", DbType = StdDbType.AnsiString, Length = 16)]
		public string TransOrderNumber
		{
			get { return this._transOrderNumber; }
			set { this._transOrderNumber = value; }
		}

		[Column(Name = "REF_ORD_TYPE", DbType = StdDbType.AnsiChar, Length = 3)]
		public string RefOrderType
		{
			get { return this._refOrderType; }
			set { this._refOrderType = value; }
		}

		///<summary>
		/// 引用单据号码
		///</summary>
		[Column(Name = "REF_ORD_NUM", DbType = StdDbType.AnsiString, Length = 16)]
		public string RefOrderNumber
		{
			get { return this._refOrderNumber; }
			set { this._refOrderNumber = value; }
		}

		[Column(Name = "ORG_ODR_TYPE", DbType = StdDbType.AnsiChar, Length = 3)]
		public string OriginalOrderType
		{
			get { return this._originalOrderType; }
			set { this._originalOrderType = value; }
		}

		///<summary>
		/// 原始单据号码
		///</summary>
		[Column(Name = "ORG_ORD_NUM", DbType = StdDbType.AnsiString, Length = 16)]
		public string OriginalOrderNumber
		{
			get { return this._originalOrderNumber; }
			set { this._originalOrderNumber = value; }
		}

		[Column(Name = "COMMIT_USR", DbType = StdDbType.Int32)]
		public int CommitUser
		{
			get { return this._commitUser; }
			set { this._commitUser = value; }
		}

		[Column(Name = "COMMIT_DATE", DbType = StdDbType.Int32, Precision = 8)]
		public int CommitDate
		{
			get { return this._commitDate; }
			set { this._commitDate = value; }
		}

		[Column(Name = "COMMIT_TIME", DbType = StdDbType.Int32, Precision = 4)]
		public int CommitTime
		{
			get { return this._commitTime; }
			set { this._commitTime = value; }
		}

		[Column(Name = "TRANS_DESC", DbType = StdDbType.UnicodeString, Length = 70)]
		public string TransDesc
		{
			get { return this._transDesc; }
			set { this._transDesc = value; }
		}

		#endregion

		#region Entity Methods
		public WHTransHead()
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
		public static WHTransHead Retrieve(ISession session, int transNumber)
		{
			return EntityManager.Retrieve<WHTransHead>(session, transNumber);
		}
		public static bool Delete(ISession session, int transNumber)
		{
			return EntityManager.Delete<WHTransHead>(session, transNumber);
		}
		#endregion
	}
}
