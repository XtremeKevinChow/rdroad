//*******************************************
// ** Description:  Data Access Object for StockPeriodBalance
// ** Author     :  Code generator
// ** Created    :   2008-9-25 10:39:05
// ** Modified   :
//*******************************************

namespace Magic.Basis
{
	using System;
	using Magic.Framework.Data;
	using Magic.Framework.ORM;
	using Magic.Framework.ORM.Mapping;

	/// <summary>
	/// Data Access Object for StockPeriodBalance
	/// 期间库存结余历史
	/// </summary>
	[Table("INV_STOCK_BALANCE")]
	public partial class  StockPeriodBalance : IEntity
	{
		#region Private Fields
		private int _balanceID;
		private int _periodID;
		private int _sKUID;
		private string _locationCode;
		private string _areaCode;
		private string _sectionCode;
		private string _lotNumber;
		private decimal _stockQty;
		#endregion

		#region Public Properties
		[Column(Name = "BLNC_ID", DbType = StdDbType.Int32, IsPrimary = true, IsSequence = true, SequenceName = "SEQ_INV_STOCK_BALANCE")]
		public int BalanceID
		{
			get { return this._balanceID; }
			set { this._balanceID = value; }
		}

		///<summary>
		/// 库存期间ID
		///</summary>
		[Column(Name = "PD_ID", DbType = StdDbType.Int32)]
		public int PeriodID
		{
			get { return this._periodID; }
			set { this._periodID = value; }
		}

		[Column(Name = "SKU_ID", DbType = StdDbType.Int32)]
		public int SKUID
		{
			get { return this._sKUID; }
			set { this._sKUID = value; }
		}

		[Column(Name = "LOC_CODE", DbType = StdDbType.AnsiString, Length = 8)]
		public string LocationCode
		{
			get { return this._locationCode; }
			set { this._locationCode = value; }
		}

		[Column(Name = "AREA_CODE", DbType = StdDbType.AnsiString, Length = 8)]
		public string AreaCode
		{
			get { return this._areaCode; }
			set { this._areaCode = value; }
		}

		[Column(Name = "SEC_CODE", DbType = StdDbType.AnsiString, Length = 8)]
		public string SectionCode
		{
			get { return this._sectionCode; }
			set { this._sectionCode = value; }
		}

		[Column(Name = "LOT_NUM", DbType = StdDbType.AnsiString, Length = 24)]
		public string LotNumber
		{
			get { return this._lotNumber; }
			set { this._lotNumber = value; }
		}

		[Column(Name = "STOCK_QTY", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal StockQty
		{
			get { return this._stockQty; }
			set { this._stockQty = value; }
		}

		#endregion

		#region Entity Methods
		public StockPeriodBalance()
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
		public static StockPeriodBalance Retrieve(ISession session, int balanceID)
		{
			return EntityManager.Retrieve<StockPeriodBalance>(session, balanceID);
		}
		public static bool Delete(ISession session, int balanceID)
		{
			return EntityManager.Delete<StockPeriodBalance>(session, balanceID);
		}
		#endregion
	}
}
