//*******************************************
// ** Description:  Data Access Object for StockBalanceDetail
// ** Author     :  Code generator
// ** Created    :   2008-10-7 0:25:48
// ** Modified   :
//*******************************************

namespace Magic.ERP.Report
{
	using System;
	using Magic.Framework.Data;
	using Magic.Framework.ORM;
	using Magic.Framework.ORM.Mapping;

	/// <summary>
	/// Data Access Object for StockBalanceDetail
	/// </summary>
	[Table("INV_STOCK_BALANCE_DTL")]
	public partial class  StockBalanceDetail : IEntity
	{
		#region Private Fields
		private int _periodID;
		private int _sKUID;
		private decimal _avgMoveCost;
		private decimal _beginQty;
		private decimal _beginAmt;
		private decimal _saleQty;
		private decimal _saleAmt;
		private decimal _saleReturnQty;
		private decimal _saleReturnAmt;
		private decimal _purQty;
		private decimal _purAmt;
		private decimal _purCost;
		private decimal _purReturnQty;
		private decimal _purReturnAmt;
		private decimal _checkQty;
		private decimal _checkAmt;
		private decimal _adjustQty;
		private decimal _adjustAmt;
		private decimal _lendQty;
		private decimal _lendRtnQty;
		private decimal _usedQty;
		private decimal _usedAmt;
        private decimal _otherInQty;
        private decimal _otherInAmt;
		private decimal _endQty;
		private decimal _endAmt;
		#endregion

		#region Public Properties
		[Column(Name = "PD_ID", DbType = StdDbType.Int32, IsPrimary = true)]
		public int PeriodID
		{
			get { return this._periodID; }
			set { this._periodID = value; }
		}

		[Column(Name = "SKU_ID", DbType = StdDbType.Int32, IsPrimary = true)]
		public int SKUID
		{
			get { return this._sKUID; }
			set { this._sKUID = value; }
		}

		///<summary>
		/// 期末的移动平均成本
		///</summary>
		[Column(Name = "AVG_MOV_COST", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal AvgMoveCost
		{
			get { return this._avgMoveCost; }
			set { this._avgMoveCost = value; }
		}

		///<summary>
		/// 期初数量
		///</summary>
		[Column(Name = "BEGIN_QTY", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal BeginQty
		{
			get { return this._beginQty; }
			set { this._beginQty = value; }
		}

		///<summary>
		/// 期初金额
		///</summary>
		[Column(Name = "BEGIN_AMT", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal BeginAmt
		{
			get { return this._beginAmt; }
			set { this._beginAmt = value; }
		}

		///<summary>
		/// 销售数量
		///</summary>
		[Column(Name = "SALE_QTY", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal SaleQty
		{
			get { return this._saleQty; }
			set { this._saleQty = value; }
		}

		///<summary>
		/// 销售金额
		///</summary>
		[Column(Name = "SALE_AMT", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal SaleAmt
		{
			get { return this._saleAmt; }
			set { this._saleAmt = value; }
		}

		///<summary>
		/// 销售退回数量
		///</summary>
		[Column(Name = "SALE_RTN_QTY", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal SaleReturnQty
		{
			get { return this._saleReturnQty; }
			set { this._saleReturnQty = value; }
		}

		///<summary>
		/// 销售退回金额
		///</summary>
		[Column(Name = "SALE_RTN_AMT", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal SaleReturnAmt
		{
			get { return this._saleReturnAmt; }
			set { this._saleReturnAmt = value; }
		}

		///<summary>
		/// 采购数量
		///</summary>
		[Column(Name = "PUR_QTY", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal PurQty
		{
			get { return this._purQty; }
			set { this._purQty = value; }
		}

		///<summary>
		/// 采购金额
		///</summary>
		[Column(Name = "PUR_AMT", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal PurAmt
		{
			get { return this._purAmt; }
			set { this._purAmt = value; }
		}

		///<summary>
		/// 采购成本
		///</summary>
		[Column(Name = "PUR_COST", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal PurCost
		{
			get { return this._purCost; }
			set { this._purCost = value; }
		}

		///<summary>
		/// 采购退回数量
		///</summary>
		[Column(Name = "PUR_RTN_QTY", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal PurReturnQty
		{
			get { return this._purReturnQty; }
			set { this._purReturnQty = value; }
		}

		///<summary>
		/// 采购退回金额
		///</summary>
		[Column(Name = "PUR_RTN_AMT", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal PurReturnAmt
		{
			get { return this._purReturnAmt; }
			set { this._purReturnAmt = value; }
		}

		///<summary>
		/// 库存盘点损益数量
		///</summary>
		[Column(Name = "CHK_QTY", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal CheckQty
		{
			get { return this._checkQty; }
			set { this._checkQty = value; }
		}

		///<summary>
		/// 库存盘点损益金额
		///</summary>
		[Column(Name = "CHK_AMT", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal CheckAmt
		{
			get { return this._checkAmt; }
			set { this._checkAmt = value; }
		}

		///<summary>
		/// 库存调整损益数量
		///</summary>
		[Column(Name = "ADJ_QTY", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal AdjustQty
		{
			get { return this._adjustQty; }
			set { this._adjustQty = value; }
		}

		///<summary>
		/// 库存调整损益金额
		///</summary>
		[Column(Name = "ADJ_AMT", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal AdjustAmt
		{
			get { return this._adjustAmt; }
			set { this._adjustAmt = value; }
		}

		///<summary>
		/// 借出数量
		///</summary>
		[Column(Name = "LEND_QTY", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal LendQty
		{
			get { return this._lendQty; }
			set { this._lendQty = value; }
		}

		[Column(Name = "LEND_RTN_QTY", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal LendRtnQty
		{
			get { return this._lendRtnQty; }
			set { this._lendRtnQty = value; }
		}

		///<summary>
		/// 库存领用数量
		///</summary>
		[Column(Name = "USED_QTY", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal UsedQty
		{
			get { return this._usedQty; }
			set { this._usedQty = value; }
		}

		///<summary>
		/// 库存领用金额
		///</summary>
		[Column(Name = "USED_AMT", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal UsedAmt
		{
			get { return this._usedAmt; }
			set { this._usedAmt = value; }
		}

        ///<summary>
        /// 其它入库数量（产品入库）
        ///</summary>
        [Column(Name = "OTH_IN_QTY", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
        public decimal OtherInQty
        {
            get { return this._otherInQty; }
            set { this._otherInQty = value; }
        }

        ///<summary>
        /// 其它入库金额（产品入库）
        ///</summary>
        [Column(Name = "OTH_IN_AMT", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
        public decimal OtherInAmt
        {
            get { return this._otherInQty; }
            set { this._otherInQty = value; }
        }

		///<summary>
		/// 期末结余量
		///</summary>
		[Column(Name = "END_QTY", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal EndQty
		{
			get { return this._endQty; }
			set { this._endQty = value; }
		}

		///<summary>
		/// 期末结余金额
		///</summary>
		[Column(Name = "END_AMT", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal EndAmt
		{
			get { return this._endAmt; }
			set { this._endAmt = value; }
		}

        private decimal _scrapQty;
        ///<summary>
        /// 报废数量
        ///</summary>
        [Column(Name = "SCRAP_QTY", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
        public decimal ScrapQty
        {
            get { return this._scrapQty; }
            set { this._scrapQty = value; }
        }
        private decimal _scrapAmt;
        ///<summary>
        /// 报废金额
        ///</summary>
        [Column(Name = "SCRAP_AMT", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
        public decimal ScrapAmt
        {
            get { return this._scrapAmt; }
            set { this._scrapAmt = value; }
        }
		#endregion

		#region Entity Methods
		public StockBalanceDetail()
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
		public static StockBalanceDetail Retrieve(ISession session, int periodID, int sKUID)
		{
			return EntityManager.Retrieve<StockBalanceDetail>(session, new string[]{ "PeriodID", "SKUID" },  new object[]{ periodID, sKUID });
		}
		public static bool Delete(ISession session, int periodID, int sKUID)
		{
			return EntityManager.Delete<StockBalanceDetail>(session, new string[]{ "PeriodID", "SKUID" },  new object[]{ periodID, sKUID })>0;
		}
		#endregion
	}
}
