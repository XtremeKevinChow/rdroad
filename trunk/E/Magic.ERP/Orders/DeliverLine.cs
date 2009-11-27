//*******************************************
// ** Description:  Data Access Object for DeliverLine
// ** Author     :  Code generator
// ** Created    :   2008-6-30 20:22:45
// ** Modified   :
//*******************************************

namespace Magic.ERP.Orders
{
    using Magic.Framework.Data;
	using Magic.Framework.ORM;
	using Magic.Framework.ORM.Mapping;

	/// <summary>
    /// Data Access Object for DeliverLine
	/// 发货单明细
	/// </summary>
	[Table("ORD_DLV_LINE")]
	public partial class  DeliverLine : IEntity
	{
		#region Private Fields
		private string _orderNumber;
		private string _lineNumber;
		private int _sKUID;
		private decimal _shipQty;
		private decimal _price;
		private string _refOrderLine;
        private string _originalOrderLine;
		private string _locationCode;
		private string _areaCode;
		private string _sectionCode;
		private int _saleType;
		private int _unitID;
		#endregion

		#region Public Properties
		[Column(Name = "ORD_NUM", DbType = StdDbType.AnsiString, Length = 16, IsPrimary = true)]
		public string OrderNumber
		{
			get { return this._orderNumber; }
			set { this._orderNumber = value; }
		}

		[Column(Name = "LINE_NUM", DbType = StdDbType.AnsiChar, Length = 4, IsPrimary = true)]
		public string LineNumber
		{
			get { return this._lineNumber; }
			set { this._lineNumber = value; }
		}

		///<summary>
		/// 产品明细ID（SKU 表）
		///</summary>
		[Column(Name = "SKU_ID", DbType = StdDbType.Int32)]
		public int SKUID
		{
			get { return this._sKUID; }
			set { this._sKUID = value; }
		}

		///<summary>
		/// 发货数量，从订单带过来
		///</summary>
		[Column(Name = "SHIP_QTY", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal ShipQty
		{
			get { return this._shipQty; }
			set { this._shipQty = value; }
		}

		///<summary>
		/// 价格，从销售订单带过来
		///</summary>
        [Column(Name = "DLV_PRICE", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal Price
		{
			get { return this._price; }
			set { this._price = value; }
		}

        ///<summary>
        /// 引用单据行号，对销售发货该属性为销售订单行号，对换货发货该属性为换货收货单行号
        ///</summary>
		[Column(Name = "REF_ODR_LINE", DbType = StdDbType.AnsiString, Length = 4)]
		public string RefOrderLine
		{
			get { return this._refOrderLine; }
			set { this._refOrderLine = value; }
		}

        ///<summary>
        /// 原始单据行号，对销售发货和换货发货，该属性均为销售订单行号
        ///</summary>
        [Column(Name = "ORG_ODR_LINE", DbType = StdDbType.AnsiChar, Length = 4)]
        public string OriginalOrderLine
        {
            get { return this._originalOrderLine; }
            set { this._originalOrderLine = value; }
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

		///<summary>
		/// 订单销售类型，从订单带过来
		///</summary>
		[Column(Name = "SALE_TYPE", DbType = StdDbType.Int32)]
		public int SaleType
		{
			get { return this._saleType; }
			set { this._saleType = value; }
		}

		[Column(Name = "UNIT_ID", DbType = StdDbType.Int32)]
		public int UnitID
		{
			get { return this._unitID; }
			set { this._unitID = value; }
		}

		#endregion

		#region Entity Methods
        public DeliverLine()
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
		#endregion
	}
}
