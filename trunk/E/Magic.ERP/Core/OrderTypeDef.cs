//*******************************************
// ** Description:  Data Access Object for OrderTypeDef
// ** Author     :  Code generator
// ** Created    :   2008-6-30 20:23:03
// ** Modified   :
//*******************************************

namespace Magic.ERP.Core
{
    using Magic.Framework.Data;
	using Magic.Framework.ORM;
	using Magic.Framework.ORM.Mapping;
    using Magic.ERP;

    /// <summary>
    /// Data Access Object for OrderTypeDef
    /// 单据类型定义，目前定义的单据类型：
    /// PO0: 普通采购订单
    /// RC1: 采购收货单
    /// RC2: 会员退货收货单
    /// RC3: 会员换货收货单
    /// RC4: 物流退货收货单
    /// SD0: 销售发货单
    /// DL2: 换货发货单
    /// DL3: 会员退货拒收发货单
    /// IC0: 交接单
    /// SO0: 销售订单
    /// SC0: 库存盘点
    /// SA0: 库存调整
    /// MV0: 移库单
    /// </summary>
	[Table("ORD_TYPE_DEF")]
	public partial class  OrderTypeDef : IEntity
	{
		#region Private Fields
		private string _orderTypeCode;
		private string _typeText;
        private bool _supportApprove;
		private bool _needApprove;
		private string _viewURL;
		private int _ruleDefineID;
        private int _transStepCount;
		#endregion

		#region Public Properties
		///<summary>
		/// 单据类型代码
		///</summary>
		[Column(Name = "ORD_TYPE_CODE", DbType = StdDbType.AnsiChar, Length = 3, IsPrimary = true)]
		public string OrderTypeCode
		{
			get { return this._orderTypeCode; }
			set { this._orderTypeCode = value; }
		}

		///<summary>
		/// 单据类型描述
		///</summary>
		[Column(Name = "ORD_TYPE_TEXT", DbType = StdDbType.UnicodeString, Length = 20)]
		public string TypeText
		{
			get { return this._typeText; }
			set { this._typeText = value; }
		}

        ///<summary>
        /// 该单据是否支持签核
        ///</summary>
        [Column(Name = "CAN_APRV", DbType = StdDbType.Bool)]
        public bool SupportApprove
        {
            get { return this._supportApprove; }
            set { this._supportApprove = value; }
        }

		///<summary>
		/// 该类型的单据是否需要签核
		///</summary>
		[Column(Name = "NEED_APRV", DbType = StdDbType.Bool)]
		public bool NeedApprove
		{
			get { return this._needApprove; }
			set { this._needApprove = value; }
		}

		///<summary>
		/// 这个地址接受一个req_num参数，值为单据号码，对应的页面负责显示这个单据的详细信息
		///</summary>
		[Column(Name = "VIEW_URL", DbType = StdDbType.AnsiString, Length = 70)]
		public string ViewURL
		{
			get { return this._viewURL; }
			set { this._viewURL = value; }
		}

		///<summary>
		/// 单据号码生成规则
		///</summary>
		[Column(Name = "RULE_DEF_ID", DbType = StdDbType.Int32)]
		public int RuleDefineID
		{
			get { return this._ruleDefineID; }
			set { this._ruleDefineID = value; }
		}

        [Column(Name = "TRANS_STEP_CNT", DbType = StdDbType.Int32, Precision = 2)]
        public int TransStepCount
        {
            get { return this._transStepCount; }
            set { this._transStepCount = value; }
        }
		#endregion

		#region Entity Methods
		public OrderTypeDef()
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
		public bool Delete(ISession session)
		{
            if (EntityManager.Delete(session, this))
            {
                ERPUtil.Cache.Remove(this);
                return true;
            }
            return false;
		}
		public static OrderTypeDef Retrieve(ISession session, string orderTypeCode)
		{
            return ERPUtil.Cache.Get<OrderTypeDef>(orderTypeCode);
		}
		#endregion
	}
}
