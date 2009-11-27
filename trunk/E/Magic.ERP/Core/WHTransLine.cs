//*******************************************
// ** Description:  Data Access Object for WHTransLine
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
	/// Data Access Object for WHTransLine
	/// ��潻�ױ�
	/// </summary>
	[Table("INV_TRANS_LINE")]
	public partial class  WHTransLine : IEntity
	{
		#region Private Fields
		private int _transNumber;
		private int _lineNumber;
		private int _sKUID;
		private string _transTypeCode;
		private string _locationCode;
		private string _areaCode;
		private string _sectionCode;
		private int _transDate;
		private int _unitID;
		private decimal _beforeTransQty;
		private decimal _transQty;
		private string _currencyCode;
		private decimal _transPrice;
		private bool _hasLotControl;
		private string _lotNumber;
		private string _refOrderType;
		private string _refOrderNumber;
		private string _refOrderLine;
		private string _originalOrderType;
		private string _originalOrderNumber;
		private string _originalOrderLine;
		private int _transTime;
		private string _transReason;
        private decimal _taxValue;
        private decimal _avgMoveCost;
        private int _stockDetailID;
		#endregion

		#region Public Properties
		[Column(Name = "TRANS_NUM", DbType = StdDbType.Int32, IsPrimary = true)]
		public int TransNumber
		{
			get { return this._transNumber; }
			set { this._transNumber = value; }
		}

		[Column(Name = "LINE_NUM", DbType = StdDbType.Int32, Precision = 5, IsPrimary = true)]
		public int LineNumber
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
		/// ���״���
		///</summary>
		[Column(Name = "TRANS_TYPE_CODE", DbType = StdDbType.AnsiChar, Length = 3)]
		public string TransTypeCode
		{
			get { return this._transTypeCode; }
			set { this._transTypeCode = value; }
		}

		///<summary>
		/// �ִ��ص�
		///</summary>
		[Column(Name = "LOC_CODE", DbType = StdDbType.AnsiString, Length = 8)]
		public string LocationCode
		{
			get { return this._locationCode; }
			set { this._locationCode = value; }
		}

		///<summary>
		/// �ֿ�����
		///</summary>
		[Column(Name = "AREA_CODE", DbType = StdDbType.AnsiString, Length = 8)]
		public string AreaCode
		{
			get { return this._areaCode; }
			set { this._areaCode = value; }
		}

		///<summary>
		/// ����
		///</summary>
		[Column(Name = "SEC_CODE", DbType = StdDbType.AnsiString, Length = 10)]
		public string SectionCode
		{
			get { return this._sectionCode; }
			set { this._sectionCode = value; }
		}

		///<summary>
		/// �������ڣ�8λ������ʾ
		///</summary>
		[Column(Name = "TRANS_DATE", DbType = StdDbType.Int32, Precision = 8)]
		public int TransDate
		{
			get { return this._transDate; }
			set { this._transDate = value; }
		}

		[Column(Name = "UNIT_ID", DbType = StdDbType.Int32)]
		public int UnitID
		{
			get { return this._unitID; }
			set { this._unitID = value; }
		}

		[Column(Name = "BEF_TRANS_QTY", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal BeforeTransQty
		{
			get { return this._beforeTransQty; }
			set { this._beforeTransQty = value; }
		}

		///<summary>
		/// ��������
		///</summary>
		[Column(Name = "TRANS_QTY", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal TransQty
		{
			get { return this._transQty; }
			set { this._transQty = value; }
		}

		///<summary>
		/// ���ױұ�
		///</summary>
		[Column(Name = "CUR_CODE", DbType = StdDbType.AnsiString, Length = 4)]
		public string CurrencyCode
		{
			get { return this._currencyCode; }
			set { this._currencyCode = value; }
		}

		///<summary>
		/// ���׼۸�
		///</summary>
		[Column(Name = "TRANS_PRICE", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal TransPrice
		{
			get { return this._transPrice; }
			set { this._transPrice = value; }
		}

		[Column(Name = "HAS_LOT", DbType = StdDbType.Bool)]
		public bool HasLotControl
		{
			get { return this._hasLotControl; }
			set { this._hasLotControl = value; }
		}

		[Column(Name = "LOT_NUM", DbType = StdDbType.AnsiString, Length = 24)]
		public string LotNumber
		{
			get { return this._lotNumber; }
			set { this._lotNumber = value; }
		}

		[Column(Name = "REF_ORD_TYPE", DbType = StdDbType.AnsiChar, Length = 3)]
		public string RefOrderType
		{
			get { return this._refOrderType; }
			set { this._refOrderType = value; }
		}

		///<summary>
		/// ���õ��ݺ���
		///</summary>
		[Column(Name = "REF_ORD_NUM", DbType = StdDbType.AnsiString, Length = 16)]
		public string RefOrderNumber
		{
			get { return this._refOrderNumber; }
			set { this._refOrderNumber = value; }
		}

		///<summary>
		/// ���õ����к�
		///</summary>
		[Column(Name = "REF_ODR_LINE", DbType = StdDbType.AnsiString, Length = 10)]
		public string RefOrderLine
		{
			get { return this._refOrderLine; }
			set { this._refOrderLine = value; }
		}

		[Column(Name = "ORG_ODR_TYPE", DbType = StdDbType.AnsiChar, Length = 3)]
		public string OriginalOrderType
		{
			get { return this._originalOrderType; }
			set { this._originalOrderType = value; }
		}

		///<summary>
		/// ԭʼ���ݺ���
		///</summary>
		[Column(Name = "ORG_ORD_NUM", DbType = StdDbType.AnsiString, Length = 16)]
		public string OriginalOrderNumber
		{
			get { return this._originalOrderNumber; }
			set { this._originalOrderNumber = value; }
		}

		///<summary>
		/// ԭʼ�����к�
		///</summary>
        [Column(Name = "ORG_ODR_LINE", DbType = StdDbType.AnsiString, Length = 10)]
		public string OriginalOrderLine
		{
			get { return this._originalOrderLine; }
			set { this._originalOrderLine = value; }
		}

		///<summary>
		/// ����ʱ�䣬4λ������ʾ
		///</summary>
		[Column(Name = "TRANS_TIME", DbType = StdDbType.Int32, Precision = 4)]
		public int TransTime
		{
			get { return this._transTime; }
			set { this._transTime = value; }
		}

		[Column(Name = "TRANS_RSON", DbType = StdDbType.UnicodeString, Length = 80)]
		public string TransReason
		{
			get { return this._transReason; }
			set { this._transReason = value; }
		}

        ///<summary>
        /// ˰��
        ///</summary>
        [Column(Name = "TAX_VAL", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
        public decimal TaxValue
        {
            get { return this._taxValue; }
            set { this._taxValue = value; }
        }

        ///<summary>
        /// ����ʹ�õ��ĸ������ϸ
        ///</summary>
        [Column(Name = "STO_DTL_ID", DbType = StdDbType.Int32)]
        public int StockDetailID
        {
            get { return this._stockDetailID; }
            set { this._stockDetailID = value; }
        }

        ///<summary>
        /// ����ʱ���ƶ�ƽ���ۣ�����ǰ��
        ///</summary>
        [Column(Name = "AVG_MOV_COST", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
        public decimal AvgMoveCost
        {
            get { return this._avgMoveCost; }
            set { this._avgMoveCost = value; }
        }

        private bool _isScrap;
        ///<summary>
        /// �Ƿ��Ʒ��
        ///</summary>
        [Column(Name = "IS_SCRAP", DbType = StdDbType.Bool)]
        public bool IsScrap
        {
            get { return this._isScrap; }
            set { this._isScrap = value; }
        }

        private bool _isQC;
        /// <summary>
        /// ��潻��ִ�й�����ʹ�ø����ԣ�����ֵ������Ҳ����Ҫ���浽���ݿ���
        /// </summary>
        internal bool IsQC
        {
            get { return this._isQC; }
            set { this._isQC = value; }
        }
		#endregion

		#region Entity Methods
		public WHTransLine()
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
		public static WHTransLine Retrieve(ISession session, int transNumber, int lineNumber)
		{
			return EntityManager.Retrieve<WHTransLine>(session, new string[]{ "TransNumber", "LineNumber" },  new object[]{ transNumber, lineNumber });
		}
		public static bool Delete(ISession session, int transNumber, int lineNumber)
		{
			return EntityManager.Delete<WHTransLine>(session, new string[]{ "TransNumber", "LineNumber" },  new object[]{ transNumber, lineNumber })>0;
		}
		#endregion
	}
}
