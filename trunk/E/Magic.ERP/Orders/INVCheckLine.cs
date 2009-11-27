//*******************************************
// ** Description:  Data Access Object for INVCheckLine
// ** Author     :  Code generator
// ** Created    :   2008-7-13 21:22:51
// ** Modified   :
//*******************************************

namespace Magic.ERP.Orders
{
	using System;
	using Magic.Framework.Data;
	using Magic.Framework.ORM;
	using Magic.Framework.ORM.Mapping;

	/// <summary>
	/// Data Access Object for INVCheckLine
	/// </summary>
	[Table("ORD_INV_CHK_LINE")]
	public partial class  INVCheckLine : IEntity
	{
		#region Private Fields
		private string _orderNumber;
		private string _lineNumber;
		private int _sKUID;
        private int _unitID;
		private string _locationCode;
		private string _areaCode;
		private string _sectionCode;
		private decimal _beforeQty;
		private decimal _currentQty;
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

		///<summary>
		/// 产品明细ID（SKU 表）
		///</summary>
		[Column(Name = "SKU_ID", DbType = StdDbType.Int32)]
		public int SKUID
		{
			get { return this._sKUID; }
			set { this._sKUID = value; }
		}

        [Column(Name = "UNIT_ID", DbType = StdDbType.Int32)]
        public int UnitID
        {
            get { return this._unitID; }
            set { this._unitID = value; }
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
		/// 调整之前的数量
		///</summary>
		[Column(Name = "PREV_QTY", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal BeforeQty
		{
			get { return this._beforeQty; }
			set { this._beforeQty = value; }
		}

		///<summary>
		/// 当前实际数量
		///</summary>
		[Column(Name = "CUR_QTY", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal CurrentQty
		{
			get { return this._currentQty; }
			set { this._currentQty = value; }
		}

        [Column(Name = "STO_DTL_ID", DbType = StdDbType.Int32)]
        public int StockDetailID
        {
            get { return this._stockDetailID; }
            set { this._stockDetailID = value; }
        }
		#endregion

		#region Entity Methods
		public INVCheckLine()
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
		public static INVCheckLine Retrieve(ISession session, string orderNumber, string lineNumber)
		{
			return EntityManager.Retrieve<INVCheckLine>(session, new string[]{ "OrderNumber", "LineNumber" },  new object[]{ orderNumber, lineNumber });
		}
		public static bool Delete(ISession session, string orderNumber, string lineNumber)
		{
			return EntityManager.Delete<INVCheckLine>(session, new string[]{ "OrderNumber", "LineNumber" },  new object[]{ orderNumber, lineNumber })>0;
		}
		#endregion
	}
}
