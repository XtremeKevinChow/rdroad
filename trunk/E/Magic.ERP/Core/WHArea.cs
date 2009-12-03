//*******************************************
// ** Description:  Data Access Object for WHArea
// ** Author     :  Code generator
// ** Created    :   2008-6-30 20:23:03
// ** Modified   :
//*******************************************

namespace Magic.ERP.Core
{
	using System;
	using Magic.Framework.Data;
	using Magic.Framework.ORM;
	using Magic.Framework.ORM.Mapping;

	/// <summary>
	/// Data Access Object for WHArea
	/// 仓库存储区域
	/// </summary>
	[Table("INV_WH_AREA")]
	public partial class  WHArea : IEntity
	{
		#region Private Fields
		private string _areaCode;
		private string _locationCode;
		private string _parentArea;
		private string _name;
		private string _text;
		private WHStatus _status;
		private decimal _areaCapacity;
		private bool _hasSection;
		private bool _isTransArea;
        private bool _isLocked;
		private bool _isReservedArea;
		private bool _allowDelete;
		private bool _allowChild;
		private bool _useFixCost;
		private decimal _costFixValue;
		private decimal _costTransRate;
		private decimal _fixedComsumeValue;
		#endregion

		#region Public Properties
		[Column(Name = "AREA_CODE", DbType = StdDbType.AnsiString, Length = 8, IsPrimary = true)]
		public string AreaCode
		{
			get { return this._areaCode; }
			set { this._areaCode = value; }
		}

		[Column(Name = "LOC_CODE", DbType = StdDbType.AnsiString, Length = 8)]
		public string LocationCode
		{
			get { return this._locationCode; }
			set { this._locationCode = value; }
		}

		[Column(Name = "PARENT_CODE", DbType = StdDbType.AnsiString, Length = 8)]
		public string ParentArea
		{
			get { return this._parentArea; }
			set { this._parentArea = value; }
		}

		[Column(Name = "AREA_NAME", DbType = StdDbType.UnicodeString, Length = 25)]
		public string Name
		{
			get { return this._name; }
			set { this._name = value; }
		}

		[Column(Name = "AREA_TEXT", DbType = StdDbType.UnicodeString, Length = 40)]
		public string Text
		{
			get { return this._text; }
			set { this._text = value; }
		}

		///<summary>
		/// 0: 删除
		/// 1: 无效、禁用
		/// 2: 有效
		///</summary>
		[Column(Name = "AREA_STATUS", DbType = StdDbType.Int32, Precision = 1)]
        public WHStatus Status
		{
			get { return this._status; }
			set { this._status = value; }
		}

		///<summary>
		/// 存储容量
		///</summary>
		[Column(Name = "AREA_CAPACITY", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal AreaCapacity
		{
			get { return this._areaCapacity; }
			set { this._areaCapacity = value; }
		}

		///<summary>
		/// 不设置货架，则出入库操作时不需要选择货架
		///</summary>
		[Column(Name = "HAS_SECTION", DbType = StdDbType.Bool)]
		public bool HasSection
		{
			get { return this._hasSection; }
			set { this._hasSection = value; }
		}

		///<summary>
		/// 有些Area只是区域规划中的一个大区域，本身并不实际存储货物，货物存放在它下面的子Area中，非交易Area不会出现在入库仓库列表中
		///</summary>
		[Column(Name = "IS_TRANS_AREA", DbType = StdDbType.Bool)]
		public bool IsTransArea
		{
			get { return this._isTransArea; }
			set { this._isTransArea = value; }
		}

		///<summary>
		/// 是否系统保留仓
		///</summary>
		[Column(Name = "IS_RESERV_AREA", DbType = StdDbType.Bool)]
		public bool IsReservedArea
		{
			get { return this._isReservedArea; }
			set { this._isReservedArea = value; }
		}

        ///<summary>
        /// 是否锁定该库位，仓库盘点时会锁定库位
        ///</summary>
        [Column(Name = "IS_LOCKED", DbType = StdDbType.Bool)]
        public bool IsLocked
        {
            get { return this._isLocked; }
            set { this._isLocked = value; }
        }

		///<summary>
		/// 是否允许删除
		///</summary>
		[Column(Name = "ALLOW_DEL", DbType = StdDbType.Bool)]
		public bool AllowDelete
		{
			get { return this._allowDelete; }
			set { this._allowDelete = value; }
		}

		///<summary>
		/// 是否允许添加子区域
		///</summary>
		[Column(Name = "ALLOW_CHILD", DbType = StdDbType.Bool)]
		public bool AllowChild
		{
			get { return this._allowChild; }
			set { this._allowChild = value; }
		}

		///<summary>
		/// 是否使用固定成本值
		///</summary>
		[Column(Name = "USE_FIX_COST", DbType = StdDbType.Bool)]
		public bool UseFixCost
		{
			get { return this._useFixCost; }
			set { this._useFixCost = value; }
		}

		///<summary>
		/// 固定成本值
		///</summary>
		[Column(Name = "COST_FIX_VAL", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal CostFixValue
		{
			get { return this._costFixValue; }
			set { this._costFixValue = value; }
		}

		///<summary>
		/// 例如成本计算方式为单据价格，价格为20，成本折损率为0.9，则交易后价格为20*0.9=18
		///</summary>
		[Column(Name = "COST_TRANS_RATE", DbType = StdDbType.Number, Precision = 6, Scale = 3)]
		public decimal CostTransRate
		{
			get { return this._costTransRate; }
			set { this._costTransRate = value; }
		}

		///<summary>
		/// 例如成本计算方式为单据价格，价格为20，成本折损值为4，则交易后价格为20-15=5
		///</summary>
		[Column(Name = "TRANS_COM_FIX_VAL", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal FixedComsumeValue
		{
			get { return this._fixedComsumeValue; }
			set { this._fixedComsumeValue = value; }
		}

        private bool _isScrap;
        ///<summary>
        /// 是否废品仓
        ///</summary>
        [Column(Name = "IS_SCRAP", DbType = StdDbType.Bool)]
        public bool IsScrap
        {
            get { return this._isScrap; }
            set { this._isScrap = value; }
        }

        private bool _isQC;
        ///<summary>
        /// 是否废品仓
        ///</summary>
        [Column(Name = "IS_QC", DbType = StdDbType.Bool)]
        public bool IsQC
        {
            get { return this._isQC; }
            set { this._isQC = value; }
        }

        private bool _isNonformal;
        ///<summary>
        /// 是否废品仓
        ///</summary>
        [Column(Name = "is_nonformal", DbType = StdDbType.Bool)]
        public bool IsNonFormal
        {
            get { return this._isNonformal; }
            set { this._isNonformal = value; }
        }
		#endregion

		#region Entity Methods
		public WHArea()
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
		public static WHArea Retrieve(ISession session, string areaCode)
		{
			return EntityManager.Retrieve<WHArea>(session, areaCode);
		}
		public static bool Delete(ISession session, string areaCode)
		{
			return EntityManager.Delete<WHArea>(session, areaCode);
		}
		#endregion
	}
}
