//*******************************************
// ** Description:  Data Access Object for TransTypeDef
// ** Author     :  Code generator
// ** Created    :   2008-7-4 23:31:56
// ** Modified   :
//*******************************************

namespace Magic.ERP.Core
{
    using Magic.Framework.Data;
	using Magic.Framework.ORM;
	using Magic.Framework.ORM.Mapping;
    using Magic.ERP;
    using Magic.Framework.Utils;

	/// <summary>
	/// Data Access Object for TransTypeDef
	/// 交易类型定义，目前定义的交易类型：
	/// 101: 采购收货   入合格仓
	/// 102: 冲销101
	/// 103: 采购收货   入待检仓
	/// 104: 冲销103
	/// 
	/// 111: 会员退货收货   合格部分 收货入合格仓
	/// 112: 冲销111
	/// 113: 会员退货收货   不合格部分 收货入系统保留仓
	/// 114: 冲销113
	/// 115: 会员换货收货   合格部分 收货入合格仓
	/// 116: 冲销115
	/// 117: 会员换货收货   不合格部分 收货入系统保留仓
	/// 118: 冲销117
	/// 
	/// 131: 物流退货收货   合格部分 收货入合格仓
	/// 132: 冲销131
	/// 133: 物流退货收货   不合格部分 收货入系统保留仓
	/// 134: 冲销133
	/// 
	/// 201: 销售发货
	/// 202: 冲销201
	/// 203: 会员换货发货
	/// 204: 冲销203
	/// 205: 退货拒收发货
	/// 206:
	/// 207: 换货拒收发货
	/// 208:
	/// 
	/// 301: 库存盘点
	/// 302: 冲销301
	/// 303: 库存调整
	/// 304:
	/// </summary>
	[Table("INV_TRANS_TYPE_DEF")]
	public partial class  TransTypeDef : IEntity
	{
		#region Private Fields
		private string _transTypeCode;
        private TransTypePriceSource _priceSourceType;
		private bool _isCostTrans;
        private TransProperty _transProperty;
		private string _transDefText;
		private string _transDefDesc;
		#endregion

		#region Public Properties
		///<summary>
		/// 交易代码
		///</summary>
		[Column(Name = "TRANS_TYPE_CODE", DbType = StdDbType.AnsiChar, Length = 3, IsPrimary = true)]
		public string TransTypeCode
		{
			get { return this._transTypeCode; }
			set { this._transTypeCode = value; }
		}

		///<summary>
		/// 价格来源类型（计算成本时使用）
		/// 1: 从引用单据获取
		/// 2: 从关联交易获取
		/// 3: 取移动平均价
		/// 4: 根据Area的设置获取
		///</summary>
		[Column(Name = "PRICE_SRC_TYPE", DbType = StdDbType.Int32)]
        public TransTypePriceSource PriceSourceType
		{
			get { return this._priceSourceType; }
			set { this._priceSourceType = value; }
		}

		///<summary>
		/// 该交易类型是否需要计算成本
		///</summary>
		[Column(Name = "IS_COST_TRANS", DbType = StdDbType.Bool)]
		public bool IsCostTrans
		{
			get { return this._isCostTrans; }
			set { this._isCostTrans = value; }
		}

        [Column(Name = "TRANS_PROP", DbType = StdDbType.Int32)]
        public TransProperty TransProperty
        {
            get { return this._transProperty; }
            set { this._transProperty = value; }
        }

		///<summary>
		/// 交易类型主要描述
		///</summary>
		[Column(Name = "TRANS_TYPE_TEXT", DbType = StdDbType.UnicodeString, Length = 12)]
		public string TransDefText
		{
			get { return this._transDefText; }
			set { this._transDefText = value; }
		}

		///<summary>
		/// 交易类型附加描述
		///</summary>
		[Column(Name = "TRANS_TYPE_DEF_DESC", DbType = StdDbType.UnicodeString, Length = 40)]
		public string TransDefDesc
		{
			get { return this._transDefDesc; }
			set { this._transDefDesc = value; }
		}
		#endregion

		#region Entity Methods
        public TransTypeDef()
        {
        }

        public bool Create(ISession session)
        {
            if (EntityManager.Create(session, this))
            {
                ERPUtil.Cache.Set(this);
                return true;
            }
            return false;
        }
        public bool Update(ISession session)
        {
            if (EntityManager.Update(session, this))
            {
                ERPUtil.Cache.Set(this);
                return true;
            }
            return false;
        }
        public bool Update(ISession session, params string[] propertyNames2Update)
        {
            if (EntityManager.Update(session, this, propertyNames2Update))
            {
                ERPUtil.Cache.Set(this);
                return true;
            }
            return false;
        }
        public static TransTypeDef Retrieve(ISession session, string transTypeCode)
        {
            return ERPUtil.Cache.Get<TransTypeDef>(transTypeCode);
        }
		#endregion
	}
}