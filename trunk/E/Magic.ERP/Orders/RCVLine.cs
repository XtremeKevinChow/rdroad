//*******************************************
// ** Description:  Data Access Object for RCVLine
// ** Author     :  Code generator
// ** Created    :   2008-6-30 20:22:45
// ** Modified   :
//*******************************************

namespace Magic.ERP.Orders
{
	using System;
	using Magic.Framework.Data;
	using Magic.Framework.ORM;
	using Magic.Framework.ORM.Mapping;

	/// <summary>
	/// Data Access Object for RCVLine
	/// 收货单明细
	/// </summary>
	[Table("ORD_RCV_LINE")]
	public partial class  RCVLine : IEntity
	{
		#region Private Fields
		private string _orderNumber;
		private string _lineNumber;
		private string _transTypeCode;
		private int _sKUID;
		private decimal _price;
		private int _unitID;
		private decimal _refQty;
		private decimal _rCVTotalQty;
		private decimal _qualifiedQty;
		private decimal _unqualifiedQty;
		private string _refOrderLine;
		private string _originalOrderLine;
		private string _locationCode;
		private string _areaCode;
		private string _sectionCode;
        private decimal _taxValue;
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

		///<summary>
		/// 交易代码
		///</summary>
		[Column(Name = "TRANS_TYPE_CODE", DbType = StdDbType.AnsiChar, Length = 3)]
		public string TransTypeCode
		{
			get { return this._transTypeCode; }
			set { this._transTypeCode = value; }
		}

		[Column(Name = "SKU_ID", DbType = StdDbType.Int32)]
		public int SKUID
		{
			get { return this._sKUID; }
			set { this._sKUID = value; }
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

		[Column(Name = "UNIT_ID", DbType = StdDbType.Int32)]
		public int UnitID
		{
			get { return this._unitID; }
			set { this._unitID = value; }
		}

		///<summary>
		/// 引用数量，采购收货，引用数量为采购订单行的未收货数量
		/// 会员退货、换货，引用数量为发货单的发货数量
		///</summary>
		[Column(Name = "REF_QTY", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal RefQty
		{
			get { return this._refQty; }
			set { this._refQty = value; }
		}

		///<summary>
		/// 总收货数量
		///</summary>
		[Column(Name = "RCV_TOTAL_QTY", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal RCVTotalQty
		{
			get { return this._rCVTotalQty; }
			set { this._rCVTotalQty = value; }
		}

		///<summary>
		/// 合格数量
		///</summary>
		[Column(Name = "QUAL_QTY", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal QualifiedQty
		{
			get { return this._qualifiedQty; }
			set { this._qualifiedQty = value; }
		}

		///<summary>
		/// 不合格数量
		///</summary>
		[Column(Name = "UNQUAL_QTY", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal UnQualifiedQty
		{
			get { return this._unqualifiedQty; }
			set { this._unqualifiedQty = value; }
		}

		///<summary>
		/// 引用单据行号
		///</summary>
		[Column(Name = "REF_ODR_LINE", DbType = StdDbType.AnsiString, Length = 10)]
		public string RefOrderLine
		{
			get { return this._refOrderLine; }
			set { this._refOrderLine = value; }
		}

		///<summary>
		/// 原始单据行号
		///</summary>
        [Column(Name = "ORG_ODR_LINE", DbType = StdDbType.AnsiString, Length = 10)]
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
        /// 税率
        ///</summary>
        [Column(Name = "TAX_VAL", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
        public decimal TaxValue
        {
            get { return this._taxValue; }
            set { this._taxValue = value; }
        }
		#endregion

		#region Entity Methods
		public RCVLine()
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
		public static RCVLine Retrieve(ISession session, string orderNumber, string lineNumber)
		{
			return EntityManager.Retrieve<RCVLine>(session, new string[]{ "OrderNumber", "LineNumber" },  new object[]{ orderNumber, lineNumber });
		}
		public static bool Delete(ISession session, string orderNumber, string lineNumber)
		{
			return EntityManager.Delete<RCVLine>(session, new string[]{ "OrderNumber", "LineNumber" },  new object[]{ orderNumber, lineNumber })>0;
		}
		#endregion
	}
}
