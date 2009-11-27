//*******************************************
// ** Description:  Data Access Object for ItemMaster
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
    /// Data Access Object for ItemMaster
    /// 商品主表（货号表）
    /// </summary>
    [Table("PRD_ITEM")]
    public partial class ItemMaster : IEntity
    {
        #region Private Fields
        private int _itemID;
        private ItemType _itemType;
        private string _itemCode;  //显示
        private string _itemName;  //显示
        private string _itemNameEN;
        private int _categoryID;
        private string _itemOrigin;
        private string _itemMaterial;
        private string _itemLining;
        private string _itemTitle;
        private string _itemBottom;
        private string _itemSide;
        private string _itemOther;
        private int _maxCount;
        private bool _enableOVS;
        private int _ovsQty;
        private int _unit;
        private string _itemDesc;  //显示
        private decimal _standardPrice;
        private decimal _salePrice;
        private decimal _vipPrice;
        private bool _isLastSell;
        private int _mainCategory;
        private string _barCode;
        private decimal _itemCost;
        private string _saleFlag;
        private decimal _webPrice;
        #endregion

        #region Public Properties
        [Column(Name = "ITM_ID", DbType = StdDbType.Int32, IsPrimary = true, IsSequence = true, SequenceName = "SEQ_PRD_ITEM_ID")]
        public int ItemID
        {
            get { return this._itemID; }
            set { this._itemID = value; }
        }

        ///<summary>
        /// 可能与99原来的item type有差异：
        /// 0: 辅料（例如包装材料，不会销售，仅记录成本）
        /// 1: 普通商品  
        /// 2: 系列商品   
        /// 3: 套装商品
        /// 对系列商品、套装商品的补充说明：
        /// 一种系列商品或套装商品，在产品主表和明细表中分别建立一条记录，1对1的关系，然后系列、套装商品与货号有个对应关系表，记录系列、套装中包含文胸、内裤、裙子等这样的信息
        /// 因为价格体系是基于SKU的，这样对于系列、套装商品的各种价格，只需要使用明细表中那条记录关联就可以，逻辑上与普通商品处理一致。如果促销可以针对SKU进行，则处理上与价格也是同一个道理
        /// 而对于普通商品，产品主表与明细表中的记录是1对多的关系
        /// 系列、套装商品可能需要分别分配一种销售类型
        /// 将系列商品、套装商品加入到购物车、订单之后，应当拆成普通商品的SKU，在购物车、订单明细中记录销售类型和系列、套装的ID
        /// 从购物车、订单中删除属于系列、套装的商品时，商城的逻辑为：如果是可选套装类型中的可选商品，则只删除这个商品；否则将系列、套装的商品全部删除。CRM中则不必使用该规则，因为到了CRM中再删除基本都是一些异常情况，交由客服人员按需处理
        ///</summary>
        [Column(Name = "ITM_TYPE", DbType = StdDbType.Number, Precision = 1)]
        public ItemType ItemType
        {
            get { return this._itemType; }
            set { this._itemType = value; }
        }

        [Column(Name = "ITM_CODE", DbType = StdDbType.AnsiString, Length = 18)]
        public string ItemCode
        {
            get { return this._itemCode; }
            set { this._itemCode = value; }
        }

        [Column(Name = "ITM_NAME", DbType = StdDbType.AnsiString, Length = 40)]
        public string ItemName
        {
            get { return this._itemName; }
            set { this._itemName = value; }
        }

        [Column(Name = "ITM_NAME_EN", DbType = StdDbType.AnsiString, Length = 40)]
        public string ItemNameEN
        {
            get { return this._itemNameEN; }
            set { this._itemNameEN = value; }
        }

        ///<summary>
        /// 需要存放html代码，所以设为clob类型
        ///</summary>
        [Column(Name = "ITM_DESC", DbType = StdDbType.AnsiString, Length = 1000)]
        public string ItemDesc
        {
            get { return this._itemDesc; }
            set { this._itemDesc = value; }
        }

        [Column(Name = "ITM_TITLE", DbType = StdDbType.AnsiString, Length = 50)]
        public string ItemTitle
        {
            get { return this._itemTitle; }
            set { this._itemTitle = value; }
        }

        [Column(Name = "ITM_ORIGIN", DbType = StdDbType.AnsiString, Length = 15)]
        public string ItemOrigin
        {
            get { return this._itemOrigin; }
            set { this._itemOrigin = value; }
        }

        [Column(Name = "CATEGORY_ID", DbType = StdDbType.Int32)]
        public int CategoryID
        {
            get { return this._categoryID; }
            set { this._categoryID = value; }
        }

        /// <summary>
        /// 面料
        /// </summary>
        [Column(Name = "ITM_FABRIC", DbType = StdDbType.AnsiString, Length = 50)]
        public string ItemMaterial
        {
            get { return this._itemMaterial; }
            set { this._itemMaterial = value; }
        }

        /// <summary>
        /// 里料
        /// </summary>
        [Column(Name = "ITM_LINING", DbType = StdDbType.AnsiString, Length = 50)]
        public string ItemLining
        {
            get { return this._itemLining; }
            set { this._itemLining = value; }
        }

        /// <summary>
        /// 底浪
        /// </summary>
        [Column(Name = "ITM_BOTTOM", DbType = StdDbType.AnsiString, Length = 50)]
        public string ItemBottom
        {
            get { return this._itemBottom; }
            set { this._itemBottom = value; }
        }

        /// <summary>
        /// 侧翼
        /// </summary>
        [Column(Name = "ITM_SIDE", DbType = StdDbType.AnsiString, Length = 50)]
        public string ItemSide
        {
            get { return this._itemSide; }
            set { this._itemSide = value; }
        }

        /// <summary>
        /// 其它面料
        /// </summary>
        [Column(Name = "ITM_OTHER", DbType = StdDbType.AnsiString, Length = 50)]
        public string ItemOther
        {
            get { return this._itemOther; }
            set { this._itemOther = value; }
        }

        ///<summary>
        /// 会员单个订单最大购买数量
        ///</summary>
        [Column(Name = "MAX_COUNT", DbType = StdDbType.Int32)]
        public int MaxSaleNum
        {
            get { return this._maxCount; }
            set { this._maxCount = value; }
        }

        ///<summary>
        /// 是否启用在途库存
        ///</summary>
        [Column(Name = "ENABLE_OS", DbType = StdDbType.Bool)]
        public bool EnableOVS
        {
            get { return this._enableOVS; }
            set { this._enableOVS = value; }
        }

        ///<summary>
        /// 最大OVS数量
        ///</summary>
        [Column(Name = "OS_QTY", DbType = StdDbType.Int32)]
        public int OVSQty
        {
            get { return this._ovsQty; }
            set { this._ovsQty = value; }
        }

        ///<summary>
        /// 单位
        ///</summary>
        [Column(Name = "ITM_UNIT", DbType = StdDbType.Int32)]
        public int Unit
        {
            get { return this._unit; }
            set { this._unit = value; }
        }

        ///<summary>
        /// 这个市场价与SKU表上的市场价如何使用，待定
        ///</summary>
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
        /// 正常销售价
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

        ///<summary>
        /// 作废的字段
        ///</summary>
        [Column(Name = "ITM_COST", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
        public decimal ItemCost
        {
            get { return this._itemCost; }
            set { this._itemCost = value; }
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

        ///<summary>
        /// 作废的字段
        ///</summary>
        [Column(Name = "MAIN_CATEGORY", DbType = StdDbType.Int32)]
        public int MainCategory
        {
            get { return this._mainCategory; }
            set { this._mainCategory = value; }
        }

        /// <summary>
        /// 作废的字段
        /// </summary>
        [Column(Name = "ITM_BARCODE", DbType = StdDbType.AnsiString, Length = 13)]
        public string BarCode
        {
            get { return this._barCode; }
            set { this._barCode = value; }
        }

        /// <summary>
        /// 销售标记，为Y时该商品可销售，为N时不可销售
        /// </summary>
        [Column(Name = "SALEFLAG", DbType = StdDbType.AnsiString, Length = 1)]
        public string SaleFlag
        {
            get { return this._saleFlag; }
            set { this._saleFlag = value; }
        }
        #endregion

        #region Entity Methods
        public ItemMaster()
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
        public static ItemMaster Retrieve(ISession session, int itemID)
        {
            return EntityManager.Retrieve<ItemMaster>(session, itemID);
        }
        public static bool Delete(ISession session, int itemID)
        {
            return EntityManager.Delete<ItemMaster>(session, itemID);
        }
        #endregion
    }
}