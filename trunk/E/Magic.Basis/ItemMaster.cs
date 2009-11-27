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
    /// ��Ʒ�������ű�
    /// </summary>
    [Table("PRD_ITEM")]
    public partial class ItemMaster : IEntity
    {
        #region Private Fields
        private int _itemID;
        private ItemType _itemType;
        private string _itemCode;  //��ʾ
        private string _itemName;  //��ʾ
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
        private string _itemDesc;  //��ʾ
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
        /// ������99ԭ����item type�в��죺
        /// 0: ���ϣ������װ���ϣ��������ۣ�����¼�ɱ���
        /// 1: ��ͨ��Ʒ  
        /// 2: ϵ����Ʒ   
        /// 3: ��װ��Ʒ
        /// ��ϵ����Ʒ����װ��Ʒ�Ĳ���˵����
        /// һ��ϵ����Ʒ����װ��Ʒ���ڲ�Ʒ�������ϸ���зֱ���һ����¼��1��1�Ĺ�ϵ��Ȼ��ϵ�С���װ��Ʒ������и���Ӧ��ϵ����¼ϵ�С���װ�а������ء��ڿ㡢ȹ�ӵ���������Ϣ
        /// ��Ϊ�۸���ϵ�ǻ���SKU�ģ���������ϵ�С���װ��Ʒ�ĸ��ּ۸�ֻ��Ҫʹ����ϸ����������¼�����Ϳ��ԣ��߼�������ͨ��Ʒ����һ�¡���������������SKU���У���������۸�Ҳ��ͬһ������
        /// ��������ͨ��Ʒ����Ʒ��������ϸ���еļ�¼��1�Զ�Ĺ�ϵ
        /// ϵ�С���װ��Ʒ������Ҫ�ֱ����һ����������
        /// ��ϵ����Ʒ����װ��Ʒ���뵽���ﳵ������֮��Ӧ�������ͨ��Ʒ��SKU���ڹ��ﳵ��������ϸ�м�¼�������ͺ�ϵ�С���װ��ID
        /// �ӹ��ﳵ��������ɾ������ϵ�С���װ����Ʒʱ���̳ǵ��߼�Ϊ������ǿ�ѡ��װ�����еĿ�ѡ��Ʒ����ֻɾ�������Ʒ������ϵ�С���װ����Ʒȫ��ɾ����CRM���򲻱�ʹ�øù�����Ϊ����CRM����ɾ����������һЩ�쳣��������ɿͷ���Ա���账��
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
        /// ��Ҫ���html���룬������Ϊclob����
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
        /// ����
        /// </summary>
        [Column(Name = "ITM_FABRIC", DbType = StdDbType.AnsiString, Length = 50)]
        public string ItemMaterial
        {
            get { return this._itemMaterial; }
            set { this._itemMaterial = value; }
        }

        /// <summary>
        /// ����
        /// </summary>
        [Column(Name = "ITM_LINING", DbType = StdDbType.AnsiString, Length = 50)]
        public string ItemLining
        {
            get { return this._itemLining; }
            set { this._itemLining = value; }
        }

        /// <summary>
        /// ����
        /// </summary>
        [Column(Name = "ITM_BOTTOM", DbType = StdDbType.AnsiString, Length = 50)]
        public string ItemBottom
        {
            get { return this._itemBottom; }
            set { this._itemBottom = value; }
        }

        /// <summary>
        /// ����
        /// </summary>
        [Column(Name = "ITM_SIDE", DbType = StdDbType.AnsiString, Length = 50)]
        public string ItemSide
        {
            get { return this._itemSide; }
            set { this._itemSide = value; }
        }

        /// <summary>
        /// ��������
        /// </summary>
        [Column(Name = "ITM_OTHER", DbType = StdDbType.AnsiString, Length = 50)]
        public string ItemOther
        {
            get { return this._itemOther; }
            set { this._itemOther = value; }
        }

        ///<summary>
        /// ��Ա�����������������
        ///</summary>
        [Column(Name = "MAX_COUNT", DbType = StdDbType.Int32)]
        public int MaxSaleNum
        {
            get { return this._maxCount; }
            set { this._maxCount = value; }
        }

        ///<summary>
        /// �Ƿ�������;���
        ///</summary>
        [Column(Name = "ENABLE_OS", DbType = StdDbType.Bool)]
        public bool EnableOVS
        {
            get { return this._enableOVS; }
            set { this._enableOVS = value; }
        }

        ///<summary>
        /// ���OVS����
        ///</summary>
        [Column(Name = "OS_QTY", DbType = StdDbType.Int32)]
        public int OVSQty
        {
            get { return this._ovsQty; }
            set { this._ovsQty = value; }
        }

        ///<summary>
        /// ��λ
        ///</summary>
        [Column(Name = "ITM_UNIT", DbType = StdDbType.Int32)]
        public int Unit
        {
            get { return this._unit; }
            set { this._unit = value; }
        }

        ///<summary>
        /// ����г�����SKU���ϵ��г������ʹ�ã�����
        ///</summary>
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
        /// �������ۼ�
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

        ///<summary>
        /// ���ϵ��ֶ�
        ///</summary>
        [Column(Name = "ITM_COST", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
        public decimal ItemCost
        {
            get { return this._itemCost; }
            set { this._itemCost = value; }
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

        ///<summary>
        /// ���ϵ��ֶ�
        ///</summary>
        [Column(Name = "MAIN_CATEGORY", DbType = StdDbType.Int32)]
        public int MainCategory
        {
            get { return this._mainCategory; }
            set { this._mainCategory = value; }
        }

        /// <summary>
        /// ���ϵ��ֶ�
        /// </summary>
        [Column(Name = "ITM_BARCODE", DbType = StdDbType.AnsiString, Length = 13)]
        public string BarCode
        {
            get { return this._barCode; }
            set { this._barCode = value; }
        }

        /// <summary>
        /// ���۱�ǣ�ΪYʱ����Ʒ�����ۣ�ΪNʱ��������
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