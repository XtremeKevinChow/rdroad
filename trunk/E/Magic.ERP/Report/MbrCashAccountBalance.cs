//*******************************************
// ** Description:  Data Access Object for MbrCashAccountBalance
// ** Author     :  Code generator
// ** Created    :   2008-10-6 23:32:59
// ** Modified   :
//*******************************************

namespace Magic.ERP.Report
{
	using System;
	using Magic.Framework.Data;
	using Magic.Framework.ORM;
	using Magic.Framework.ORM.Mapping;

	/// <summary>
	/// Data Access Object for MbrCashAccountBalance
	/// </summary>
	[Table("fi_rpt_cash_account_balance")]
	public partial class  MbrCashAccountBalance : IEntity
	{
		#region Private Fields
		private int _periodID;
		private int _memberId;
		private decimal _beginAmt;
		private decimal _endAmt;
		#endregion

		#region Public Properties
		[Column(Name = "PD_ID", DbType = StdDbType.Int32, IsPrimary = true)]
		public int PeriodID
		{
			get { return this._periodID; }
			set { this._periodID = value; }
		}

		[Column(Name = "member_id", DbType = StdDbType.Int32, IsPrimary = true)]
		public int MemberID
		{
			get { return this._memberId; }
			set { this._memberId = value; }
		}

		///<summary>
		/// ÆÚ³õÓà¶î
		///</summary>
		[Column(Name = "begin_amt", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal BeginAmt
		{
			get { return this._beginAmt; }
			set { this._beginAmt = value; }
		}

		///<summary>
		/// ÆÚÄ©Óà¶î
		///</summary>
		[Column(Name = "end_amt", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal EndAmt
		{
			get { return this._endAmt; }
			set { this._endAmt = value; }
		}
		#endregion

		#region Entity Methods
        public MbrCashAccountBalance()
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
		public static StockBalanceSum Retrieve(ISession session, int periodID, int memberId)
		{
            return EntityManager.Retrieve<StockBalanceSum>(session, new string[] { "PeriodID", "MemberID" }, new object[] { periodID, memberId });
		}
        public static bool Delete(ISession session, int periodID, int memberId)
		{
            return EntityManager.Delete<StockBalanceSum>(session, new string[] { "PeriodID", "MemberID" }, new object[] { periodID, memberId }) > 0;
		}
		#endregion
	}
}
