//*******************************************
// ** Description:  Data Access Object for StockInLine
// ** Author     :  Code generator
// ** Created    :   2008-7-26 16:59:15
// ** Modified   :
//*******************************************

namespace Magic.ERP.Orders
{
	using System;
	using Magic.Framework.Data;
	using Magic.Framework.ORM;
	using Magic.Framework.ORM.Mapping;

	/// <summary>
	/// Data Access Object for StockInLine
	/// </summary>
	[Table("ORD_STO_IN_LINE")]
	public partial class  StockInLine : IEntity
	{
		#region Private Fields
		private string _orderNumber;
		private string _lineNumber;
		private int _sKUID;
		private string _locationCode;
		private string _areaCode;
		private string _sectionCode;
		private int _unitID;
        private decimal _refQuantity;
		private decimal _quantity;
		private decimal _price;
        private int _stockDetailID;
		#endregion

		#region Public Properties
		[Column(Name = "ORD_NUM", DbType = StdDbType.AnsiString, Length = 16, IsPrimary = true)]
		public string OrderNumber
		{
			get { return this._orderNumber; }
			set { this._orderNumber = value; }
		}

		[Column(Name = "LINE_NUM", DbType = StdDbType.AnsiString, Length = 10, IsPrimary = true)]
		public string LineNumber
		{
			get { return this._lineNumber; }
			set { this._lineNumber = value; }
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

		[Column(Name = "SEC_CODE", DbType = StdDbType.AnsiString, Length = 10)]
		public string SectionCode
		{
			get { return this._sectionCode; }
			set { this._sectionCode = value; }
		}

		[Column(Name = "UNIT_ID", DbType = StdDbType.Int32)]
		public int UnitID
		{
			get { return this._unitID; }
			set { this._unitID = value; }
		}

        ///<summary>
        /// 引用数量
        ///</summary>
        [Column(Name = "REF_QTY", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
        public decimal RefQuantity
        {
            get { return this._refQuantity; }
            set { this._refQuantity = value; }
        }

		///<summary>
		/// 合格数量
		///</summary>
		[Column(Name = "TRANS_QTY", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal Quantity
		{
			get { return this._quantity; }
			set { this._quantity = value; }
		}

		///<summary>
		/// 采购订单收货，价格为采购价格
		/// 会员退货、换货，价格不需要设置
		///</summary>
		[Column(Name = "TRANS_PRICE", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal Price
		{
			get { return this._price; }
			set { this._price = value; }
		}

        [Column(Name = "STO_ID", DbType = StdDbType.Int32)]
        public int StockDetailID
        {
            get { return this._stockDetailID; }
            set { this._stockDetailID = value; }
        }
		#endregion

		#region Entity Methods
		public StockInLine()
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
		public static StockInLine Retrieve(ISession session, string orderNumber, string lineNumber)
		{
			return EntityManager.Retrieve<StockInLine>(session, new string[]{ "OrderNumber", "LineNumber" },  new object[]{ orderNumber, lineNumber });
		}
		public static bool Delete(ISession session, string orderNumber, string lineNumber)
		{
			return EntityManager.Delete<StockInLine>(session, new string[]{ "OrderNumber", "LineNumber" },  new object[]{ orderNumber, lineNumber })>0;
		}
		#endregion
	}
}
