//*******************************************
// ** Description:  Data Access Object for WHTransferLine
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
	/// Data Access Object for WHTransferLine
	/// </summary>
	[Table("ORD_MOV_LINE")]
	public partial class  WHTransferLine : IEntity
	{
		#region Private Fields
		private string _orderNumber;
		private string _lineNumber;
		private int _sKUID;
        private int _unitID;
        private int _fromStockID;
		private string _fromLocation;
		private string _fromArea;
		private string _fromSection;
		private string _toLocation;
		private string _toArea;
		private string _toSection;
		private decimal _moveQty;
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

        [Column(Name = "STO_ID_FROM", DbType = StdDbType.Int32)]
        public int FromStockID
        {
            get { return this._fromStockID; }
            set { this._fromStockID = value; }
        }

		[Column(Name = "LOC_FROM", DbType = StdDbType.AnsiString, Length = 8)]
		public string FromLocation
		{
			get { return this._fromLocation; }
			set { this._fromLocation = value; }
		}

		[Column(Name = "AREA_FROM", DbType = StdDbType.AnsiString, Length = 8)]
		public string FromArea
		{
			get { return this._fromArea; }
			set { this._fromArea = value; }
		}

		[Column(Name = "SEC_FROM", DbType = StdDbType.AnsiString, Length = 10)]
		public string FromSection
		{
			get { return this._fromSection; }
			set { this._fromSection = value; }
		}

		[Column(Name = "LOC_TO", DbType = StdDbType.AnsiString, Length = 8)]
		public string ToLocation
		{
			get { return this._toLocation; }
			set { this._toLocation = value; }
		}

		[Column(Name = "AREA_TO", DbType = StdDbType.AnsiString, Length = 8)]
		public string ToArea
		{
			get { return this._toArea; }
			set { this._toArea = value; }
		}

		[Column(Name = "SEC_TO", DbType = StdDbType.AnsiString, Length = 10)]
		public string ToSection
		{
			get { return this._toSection; }
			set { this._toSection = value; }
		}

		///<summary>
		/// 移库数量
		///</summary>
		[Column(Name = "MOV_QTY", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal MoveQty
		{
			get { return this._moveQty; }
			set { this._moveQty = value; }
		}

		#endregion

		#region Entity Methods
		public WHTransferLine()
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
		public static WHTransferLine Retrieve(ISession session, string orderNumber, string lineNumber)
		{
			return EntityManager.Retrieve<WHTransferLine>(session, new string[]{ "OrderNumber", "LineNumber" },  new object[]{ orderNumber, lineNumber });
		}
		public static bool Delete(ISession session, string orderNumber, string lineNumber)
		{
			return EntityManager.Delete<WHTransferLine>(session, new string[]{ "OrderNumber", "LineNumber" },  new object[]{ orderNumber, lineNumber })>0;
		}
		#endregion
	}
}
