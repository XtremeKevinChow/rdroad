//*******************************************
// ** Description:  Data Access Object for ReturnLine
// ** Author     :  Code generator
// ** Created    :   2008-8-25 20:29:49
// ** Modified   :
//*******************************************

namespace Magic.ERP.Orders
{
	using System;
	using Magic.Framework.Data;
	using Magic.Framework.ORM;
	using Magic.Framework.ORM.Mapping;

	/// <summary>
	/// Data Access Object for ReturnLine
	/// </summary>
	[Table("ORD_RTN_LINE")]
	public partial class  ReturnLine : IEntity
	{
		#region Private Fields
		private string _orderNumber;
		private string _lineNumber;
		private int _sKUID;
		private string _transTypeCode;
		private int _refOrderLineID;
		private decimal _deliverQuantity;
		private decimal _quantity;
        private decimal _price;
        private string _locationCode;
		private string _areaCode;
		private string _sectionCode;
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

		///<summary>
		/// 交易代码
		///</summary>
		[Column(Name = "TRANS_TYPE_CODE", DbType = StdDbType.AnsiChar, Length = 3)]
		public string TransTypeCode
		{
			get { return this._transTypeCode; }
			set { this._transTypeCode = value; }
		}

		///<summary>
		/// 发货单明细ID
		///</summary>
		[Column(Name = "REF_ORD_LINE_ID", DbType = StdDbType.Int32)]
		public int RefOrderLineID
		{
			get { return this._refOrderLineID; }
			set { this._refOrderLineID = value; }
		}

		///<summary>
		/// 发货数量
		///</summary>
		[Column(Name = "DLV_QTY", DbType = StdDbType.Number, Precision = 8, Scale = 2)]
		public decimal DeliverQuantity
		{
			get { return this._deliverQuantity; }
			set { this._deliverQuantity = value; }
		}

		///<summary>
		/// 退货数量
		///</summary>
		[Column(Name = "RTN_QTY", DbType = StdDbType.Number, Precision = 8, Scale = 2)]
		public decimal Quantity
		{
			get { return this._quantity; }
			set { this._quantity = value; }
		}

        ///<summary>
        /// 退货价格
        ///</summary>
        [Column(Name = "RTN_PRICE", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
        public decimal Price
        {
            get { return this._price; }
            set { this._price = value; }
        }

        /// <summary>
        /// 该属性不保存到数据库，只在库房交易时该属性才会有值
        /// </summary>
        public string LocationCode
        {
            get
            {
                return this._locationCode;
            }
            set
            {
                this._locationCode = value;
            }
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

		#endregion

		#region Entity Methods
		public ReturnLine()
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
		public static ReturnLine Retrieve(ISession session, string orderNumber, string lineNumber)
		{
			return EntityManager.Retrieve<ReturnLine>(session, new string[]{ "OrderNumber", "LineNumber" },  new object[]{ orderNumber, lineNumber });
		}
		public static bool Delete(ISession session, string orderNumber, string lineNumber)
		{
			return EntityManager.Delete<ReturnLine>(session, new string[]{ "OrderNumber", "LineNumber" },  new object[]{ orderNumber, lineNumber })>0;
		}
		#endregion
	}
}
