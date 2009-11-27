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
	/// 商品明细表（SKU表）
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
        /// 颜色
        /// </summary>
		[Column(Name = "COLOR_CODE", DbType = StdDbType.AnsiString, Length = 6)]
		public string ColorCode
		{
			get { return this._colorCode; }
			set { this._colorCode = value; }
		}

        /// <summary>
        /// 尺码
        /// </summary>
		[Column(Name = "SIZE_CODE", DbType = StdDbType.AnsiString, Length = 6)]
		public string SizeCode
		{
			get { return this._sizeCode; }
			set { this._sizeCode = value; }
		}

        /// <summary>
        /// 是否启用在途库存（虚拟库存）
        /// </summary>
		[Column(Name = "ENABLE_OS", DbType = StdDbType.Bool)]
		public bool EnableOS
		{
			get { return this._enableOS; }
			set { this._enableOS = value; }
		}

        /// <summary>
        /// 虚拟库存数量
        /// </summary>
		[Column(Name = "OS_QTY", DbType = StdDbType.Int32)]
		public int OSQty
		{
			get { return this._oSQty; }
			set { this._oSQty = value; }
		}

        /// <summary>
        /// 虚拟库存预计到货日期
        /// </summary>
		[Column(Name = "ARRIVE_DATE", DbType = StdDbType.DateTime)]
		public DateTime OSArriveDate
		{
			get { return this._oSArriveDate; }
			set { this._oSArriveDate = value; }
		}

		///<summary>
		/// 采购提前期（单位天，从下采购订单开始多少天可以到货）
		/// 本来应该不同供应商采购提前期可能不同，这里简化处理
		///</summary>
		[Column(Name = "PUR_LEAD_TIME", DbType = StdDbType.Int32)]
		public int PurLeadTime
		{
			get { return this._purLeadTime; }
			set { this._purLeadTime = value; }
		}

		///<summary>
		/// 上下架状态
		/// 0: 下架
		/// 1: 上架
		///</summary>
		[Column(Name = "ITM_STATUS", DbType = StdDbType.Int32)]
        public ItemSpecStatus Status
		{
			get { return this._status; }
			set { this._status = value; }
		}

		///<summary>
		/// 最好是第一次上架的时间（即产品发布时间）
		/// 以后再次上下架时不要再更新
		///</summary>
		[Column(Name = "ITM_PUB_DATE", DbType = StdDbType.DateTime)]
		public DateTime PubDate
		{
			get { return this._pubDate; }
			set { this._pubDate = value; }
		}

		///<summary>
		/// 保留，目前并不一定会实现这个逻辑
		///</summary>
		[Column(Name = "ENABLE_COST", DbType = StdDbType.Bool)]
		public bool EnableCost
		{
			get { return this._enableCost; }
			set { this._enableCost = value; }
		}

		///<summary>
		/// 移动平均成本，保留，目前并不一定会实现这个逻辑，即使实现也可能只是简单的平均采购成本
		///</summary>
		[Column(Name = "AVG_MOVE_COST", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal AvgMoveCost
		{
			get { return this._avgMoveCost; }
			set { this._avgMoveCost = value; }
		}

        ///<summary>
        /// 设计缺陷导致的冗余，其值与AvgMoveCost完全一样，CRM用ItemCost，ERP用AvgMoveCost
        ///</summary>
        [Column(Name = "ITM_COST", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
        public decimal ItemCost
        {
            get { return this._itemCost; }
            set { this._itemCost = value; }
        }

        /// <summary>
        /// 条码
        /// </summary>
		[Column(Name = "ITM_BARCODE", DbType = StdDbType.AnsiString, Length = 20)]
		public string BarCode
		{
			get { return this._barCode; }
			set { this._barCode = value; }
		}

        /// <summary>
        /// 基本单位
        /// </summary>
		[Column(Name = "ITM_UNIT", DbType = StdDbType.Int32)]
		public int BasicUnit
		{
			get { return this._basicUnit; }
			set { this._basicUnit = value; }
		}

        /// <summary>
        /// 单个订单最大定购数量
        /// </summary>
        [Column(Name = "MAX_COUNT", DbType = StdDbType.Int32)]
		public int MaxSaleNum
		{
			get { return this._maxSaleNum; }
			set { this._maxSaleNum = value; }
		}

        /// <summary>
        /// 市场价
        /// </summary>
		[Column(Name = "STANDARD_PRICE", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal StandardPrice
		{
			get { return this._standardPrice; }
			set { this._standardPrice = value; }
		}

        ///<summary>
        /// 正常销售价
        ///</summary>
        [Column(Name = "SALE_PRICE", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
        public decimal SalePrice
        {
            get { return this._salePrice; }
            set { this._salePrice = value; }
        }

        ///<summary>
        /// VIP会员价
        ///</summary>
        [Column(Name = "VIP_PRICE", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
        public decimal VipPrice
        {
            get { return this._vipPrice; }
            set { this._vipPrice = value; }
        }

        ///<summary>
        /// 促销价
        ///</summary>
        [Column(Name = "WEB_PRICE", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
        public decimal WebPrice
        {
            get { return this._webPrice; }
            set { this._webPrice = value; }
        }

        /// <summary>
        /// 最后更新人
        /// </summary>
		[Column(Name = "OPERATOR_ID", DbType = StdDbType.Int32)]
		public int Operator
		{
			get { return this._operator; }
			set { this._operator = value; }
		}

        /// <summary>
        /// 最后更新时间
        /// </summary>
        [Column(Name = "OPERATE_TIME", DbType = StdDbType.DateTime)]
		public DateTime OperateTime
		{
			get { return this._operateTime; }
			set { this._operateTime = value; }
		}

        ///<summary>
        /// 是否为最后销售商品
        ///</summary>
        [Column(Name = "IS_LAST_SELL", DbType = StdDbType.Bool)]
        public bool IsLastSell
        {
            get { return this._isLastSell; }
            set { this._isLastSell = value; }
        }

        private decimal _stoMax;
        ///<summary>
        /// 最大库存
        ///</summary>
        [Column(Name = "STO_MAX", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
        public decimal StoMax
        {
            get { return this._stoMax; }
            set { this._stoMax = value; }
        }

        private decimal _stoSafe;
        ///<summary>
        /// 安全库存
        ///</summary>
        [Column(Name = "STO_SAFE", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
        public decimal StoSafe
        {
            get { return this._stoSafe; }
            set { this._stoSafe = value; }
        }

        private decimal _stoMin;
        ///<summary>
        /// 最大库存
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