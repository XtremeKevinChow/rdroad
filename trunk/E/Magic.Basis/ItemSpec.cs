//*******************************************
// ** Description:  Data Access Object for ItemSpec
// ** Author     :  Code generator
// ** Created    :   2008-6-30 20:22:21
// ** Modified   :
//*******************************************

namespace Magic.Basis
{
	using System;
	using Magic.Framework.Data;
	using Magic.Framework.ORM;
	using Magic.Framework.ORM.Mapping;

	/// <summary>
	/// Data Access Object for ItemSpec
	/// ��Ʒ��ϸ��SKU��
	/// </summary>
	[Table("PRD_ITEM_SKU")]
	public partial class  ItemSpec : IEntity
	{
		#region Private Fields
		private int _sKUID;
		private int _itemID;
        private string _itemCode;
		private string _colorCode;
		private string _sizeCode;
		private bool _enableOS;
		private int _oSQty;
		private DateTime _oSArriveDate;
		private int _purLeadTime;
        private ItemSpecStatus _status;
		private DateTime _pubDate;
		private bool _enableCost;
		private decimal _avgMoveCost;
        private decimal _itemCost;
		private string _barCode;
		private int _basicUnit;
		private int _maxSaleNum;
		private decimal _standardPrice;
        private decimal _salePrice;
        private decimal _vipPrice;
        private decimal _webPrice;
		private int _operator;
		private DateTime _operateTime;
        private bool _isLastSell;
		#endregion

		#region Public Properties
        [Column(Name = "SKU_ID", DbType = StdDbType.Int32, IsPrimary = true, IsSequence = true, SequenceName = "SEQ_PRD_ITEM_SKU_ID")]
		public int SKUID
		{
			get { return this._sKUID; }
			set { this._sKUID = value; }
		}

		[Column(Name = "ITM_ID", DbType = StdDbType.Int32)]
		public int ItemID
		{
			get { return this._itemID; }
			set { this._itemID = value; }
		}

        [Column(Name = "ITM_CODE", DbType = StdDbType.AnsiString, Length = 18)]
        public string ItemCode
        {
            get { return this._itemCode; }
            set { this._itemCode = value; }
        }

        /// <summary>
        /// ��ɫ
        /// </summary>
		[Column(Name = "COLOR_CODE", DbType = StdDbType.AnsiString, Length = 6)]
		public string ColorCode
		{
			get { return this._colorCode; }
			set { this._colorCode = value; }
		}

        /// <summary>
        /// ����
        /// </summary>
		[Column(Name = "SIZE_CODE", DbType = StdDbType.AnsiString, Length = 6)]
		public string SizeCode
		{
			get { return this._sizeCode; }
			set { this._sizeCode = value; }
		}

        /// <summary>
        /// �Ƿ�������;��棨�����棩
        /// </summary>
		[Column(Name = "ENABLE_OS", DbType = StdDbType.Bool)]
		public bool EnableOS
		{
			get { return this._enableOS; }
			set { this._enableOS = value; }
		}

        /// <summary>
        /// ����������
        /// </summary>
		[Column(Name = "OS_QTY", DbType = StdDbType.Int32)]
		public int OSQty
		{
			get { return this._oSQty; }
			set { this._oSQty = value; }
		}

        /// <summary>
        /// ������Ԥ�Ƶ�������
        /// </summary>
		[Column(Name = "ARRIVE_DATE", DbType = StdDbType.DateTime)]
		public DateTime OSArriveDate
		{
			get { return this._oSArriveDate; }
			set { this._oSArriveDate = value; }
		}

		///<summary>
		/// �ɹ���ǰ�ڣ���λ�죬���²ɹ�������ʼ��������Ե�����
		/// ����Ӧ�ò�ͬ��Ӧ�̲ɹ���ǰ�ڿ��ܲ�ͬ������򻯴���
		///</summary>
		[Column(Name = "PUR_LEAD_TIME", DbType = StdDbType.Int32)]
		public int PurLeadTime
		{
			get { return this._purLeadTime; }
			set { this._purLeadTime = value; }
		}

		///<summary>
		/// ���¼�״̬
		/// 0: �¼�
		/// 1: �ϼ�
		///</summary>
		[Column(Name = "ITM_STATUS", DbType = StdDbType.Int32)]
        public ItemSpecStatus Status
		{
			get { return this._status; }
			set { this._status = value; }
		}

		///<summary>
		/// ����ǵ�һ���ϼܵ�ʱ�䣨����Ʒ����ʱ�䣩
		/// �Ժ��ٴ����¼�ʱ��Ҫ�ٸ���
		///</summary>
		[Column(Name = "ITM_PUB_DATE", DbType = StdDbType.DateTime)]
		public DateTime PubDate
		{
			get { return this._pubDate; }
			set { this._pubDate = value; }
		}

		///<summary>
		/// ������Ŀǰ����һ����ʵ������߼�
		///</summary>
		[Column(Name = "ENABLE_COST", DbType = StdDbType.Bool)]
		public bool EnableCost
		{
			get { return this._enableCost; }
			set { this._enableCost = value; }
		}

		///<summary>
		/// �ƶ�ƽ���ɱ���������Ŀǰ����һ����ʵ������߼�����ʹʵ��Ҳ����ֻ�Ǽ򵥵�ƽ���ɹ��ɱ�
		///</summary>
		[Column(Name = "AVG_MOVE_COST", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal AvgMoveCost
		{
			get { return this._avgMoveCost; }
			set { this._avgMoveCost = value; }
		}

        ///<summary>
        /// ���ȱ�ݵ��µ����࣬��ֵ��AvgMoveCost��ȫһ����CRM��ItemCost��ERP��AvgMoveCost
        ///</summary>
        [Column(Name = "ITM_COST", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
        public decimal ItemCost
        {
            get { return this._itemCost; }
            set { this._itemCost = value; }
        }

        /// <summary>
        /// ����
        /// </summary>
		[Column(Name = "ITM_BARCODE", DbType = StdDbType.AnsiString, Length = 20)]
		public string BarCode
		{
			get { return this._barCode; }
			set { this._barCode = value; }
		}

        /// <summary>
        /// ������λ
        /// </summary>
		[Column(Name = "ITM_UNIT", DbType = StdDbType.Int32)]
		public int BasicUnit
		{
			get { return this._basicUnit; }
			set { this._basicUnit = value; }
		}

        /// <summary>
        /// ����������󶨹�����
        /// </summary>
        [Column(Name = "MAX_COUNT", DbType = StdDbType.Int32)]
		public int MaxSaleNum
		{
			get { return this._maxSaleNum; }
			set { this._maxSaleNum = value; }
		}

        /// <summary>
        /// �г���
        /// </summary>
		[Column(Name = "STANDARD_PRICE", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal StandardPrice
		{
			get { return this._standardPrice; }
			set { this._standardPrice = value; }
		}

        ///<summary>
        /// �������ۼ�
        ///</summary>
        [Column(Name = "SALE_PRICE", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
        public decimal SalePrice
        {
            get { return this._salePrice; }
            set { this._salePrice = value; }
        }

        ///<summary>
        /// VIP��Ա��
        ///</summary>
        [Column(Name = "VIP_PRICE", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
        public decimal VipPrice
        {
            get { return this._vipPrice; }
            set { this._vipPrice = value; }
        }

        ///<summary>
        /// ������
        ///</summary>
        [Column(Name = "WEB_PRICE", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
        public decimal WebPrice
        {
            get { return this._webPrice; }
            set { this._webPrice = value; }
        }

        /// <summary>
        /// ��������
        /// </summary>
		[Column(Name = "OPERATOR_ID", DbType = StdDbType.Int32)]
		public int Operator
		{
			get { return this._operator; }
			set { this._operator = value; }
		}

        /// <summary>
        /// ������ʱ��
        /// </summary>
        [Column(Name = "OPERATE_TIME", DbType = StdDbType.DateTime)]
		public DateTime OperateTime
		{
			get { return this._operateTime; }
			set { this._operateTime = value; }
		}

        ///<summary>
        /// �Ƿ�Ϊ���������Ʒ
        ///</summary>
        [Column(Name = "IS_LAST_SELL", DbType = StdDbType.Bool)]
        public bool IsLastSell
        {
            get { return this._isLastSell; }
            set { this._isLastSell = value; }
        }

        private decimal _stoMax;
        ///<summary>
        /// �����
        ///</summary>
        [Column(Name = "STO_MAX", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
        public decimal StoMax
        {
            get { return this._stoMax; }
            set { this._stoMax = value; }
        }

        private decimal _stoSafe;
        ///<summary>
        /// ��ȫ���
        ///</summary>
        [Column(Name = "STO_SAFE", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
        public decimal StoSafe
        {
            get { return this._stoSafe; }
            set { this._stoSafe = value; }
        }

        private decimal _stoMin;
        ///<summary>
        /// �����
        ///</summary>
        [Column(Name = "STO_MIN", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
        public decimal StoMin
        {
            get { return this._stoMin; }
            set { this._stoMin = value; }
        }

        private int _stoMethod;
        [Column(Name = "STO_METHOD", DbType = StdDbType.Int32)]
        public int StoMethod
        {
            get
            {
                return this._stoMethod;
            }
            set
            {
                this._stoMethod = value;
            }
        }
		#endregion

		#region Entity Methods
		public ItemSpec()
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
		public static ItemSpec Retrieve(ISession session, int sKUID)
		{
			return EntityManager.Retrieve<ItemSpec>(session, sKUID);
		}
		public static bool Delete(ISession session, int sKUID)
		{
			return EntityManager.Delete<ItemSpec>(session, sKUID);
		}
		#endregion
	}
}