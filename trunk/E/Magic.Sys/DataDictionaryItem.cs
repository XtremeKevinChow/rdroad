using System;
using System.Collections.Generic;
using Magic.Framework.Data;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Mapping;
using Magic.Framework.ORM.Query;
using Magic.Framework.Utils;
using Magic.Security;

namespace Magic.Sys
{
	/// <summary>
	/// 项类型, derive from short.
	/// </summary>
	public enum DictionaryItemType
	{
           /// <summary>
        /// 组合
        /// </summary>
        Group =0,
        /// <summary>
        /// 数据项
        /// </summary>
        String = 1,
        /// <summary>
        /// 数值型
        /// </summary>
        Numric = 2,
        /// <summary>
        /// 布尔型
        /// </summary>
        Boolean = 3
     
	}
    /// <summary>
    ///  系统数据字典项
    /// </summary>
    [Table("SYS_DICTIONARY_ITEM")]
    public partial class DictionaryItem : IEntity
    {
        #region Private Fields

        private string _itemCode;
        private string _groupCode;
        private string _name;
        private DictionaryItemType _itemType;
        private bool _boolValue;
        private decimal _numberValue;
        private string _stringValue;
        private string _note;
        static DictionaryItem _root = null;
		
        #endregion

        #region Class Constructor
        /// <summary>
        /// default constructor for 系统数据字典项
        /// </summary>
        public DictionaryItem()
        {
        }
        #endregion

        /// <summary>
        /// 
        /// </summary>
        public static DictionaryItem Root
        {
            get
            {
                if (_root == null)
                {
                    _root = new DictionaryItem();
                    _root._groupCode = "";
                    _root._itemCode = "ROOT";
                    _root._itemType = DictionaryItemType.Group;
                    _root._name = "数据词典";
                    _root._note = "Global数据词典";
                }
                return _root;
            }
        }

        #region Public Properties

        /// <summary>
        /// 数据代码
        /// </summary>
        [Column(DbType = StdDbType.UnicodeString, Length = 30, Nullable = false, IsPrimary = true, Name = "DIC_ITEM_CODE")]
        public virtual string ItemCode
        {
            get { return this._itemCode; }
            set { this._itemCode = value; }
        }

        /// <summary>
        /// 组代码
        /// </summary>
        [Column(DbType = StdDbType.UnicodeString, Length = 30, Nullable = false, Name = "DIC_GROUP")]
        public virtual string GroupCode
        {
            get { return this._groupCode; }
            set { this._groupCode = value; }
        }

        /// <summary>
        /// 数据名称
        /// </summary>
        [Column(DbType = StdDbType.UnicodeString, Length = 50, Nullable = true, Name = "DIC_NAME")]
        public virtual string Name
        {
            get { return this._name; }
            set { this._name = value; }
        }

        /// <summary>
        /// 项类型
        /// </summary>
        [Column(DbType = StdDbType.Int16, Nullable = true, Name = "DIC_ITEM_TYPE")]
        public virtual DictionaryItemType ItemType
        {
            get { return this._itemType; }
            set { this._itemType = value; }
        }

        /// <summary>
        /// 布尔型值
        /// </summary>
        [Column(DbType = StdDbType.Bool, Nullable = false, Name = "BOOL_VALUE")]
        public virtual bool BoolValue
        {
            get { return this._boolValue; }
            set { this._boolValue = value; }
        }

        /// <summary>
        /// 数值型（18，2）
        /// </summary>
        [Column(DbType = StdDbType.Number, Precision = 18, Scale = 2, Nullable = true, Name = "NUMBER_VALUE")]
        public virtual decimal NumberValue
        {
            get { return this._numberValue; }
            set { this._numberValue = value; }
        }

        /// <summary>
        /// 字符型(Unicode字符长度1000)
        /// </summary>
        [Column(DbType = StdDbType.UnicodeString, Length = 1000, Nullable = true, Name = "STRING_VALUE")]
        public virtual string StringValue
        {
            get { return this._stringValue; }
            set { this._stringValue = value; }
        }

        /// <summary>
        /// 备注
        /// </summary>
        [Column(DbType = StdDbType.UnicodeString, Length = 250, Nullable = true, Name = "DIC_NOTE")]
        public virtual string Note
        {
            get { return this._note; }
            set { this._note = value; }
        }

        #endregion

        #region IEntity Members
        /// <summary>
        /// Create new 系统数据字典项 entity
        /// <returns></returns>
        /// </summary>
        public bool Create(ISession session)
        {
            return EntityManager.Create(session, this);
        }

        /// <summary>
        /// Update 系统数据字典项 entity's whole properties
        /// <returns></returns>
        /// </summary>
        public bool Update(ISession session)
        {
            return EntityManager.Update(session, this);
        }

        /// <summary>
        ///Update 系统数据字典项 entity's given properties
        /// <returns></returns>
        /// </summary>
        public bool Update(ISession session, params string[] propertyNames2Update)
        {
            return EntityManager.Update(session, this, propertyNames2Update);
        }

        /// <summary>
        ///Delete this 系统数据字典项 entity
        /// <returns></returns>
        /// </summary>
        public bool Delete(ISession session)
        {
            return EntityManager.Delete(session, this);
        }

        #endregion

        #region Class Static Members

        /// <summary>
        ///Retrive a 系统数据字典项 Entity from DB
        /// <returns></returns>
        /// </summary>
        public static DictionaryItem Retrieve(ISession session, string itemCode)
        {
            return EntityManager.Retrieve<DictionaryItem>(session, itemCode);
        }

        /// <summary>
        /// Delete by Primary key
        /// </summary>
        /// <returns></returns>
        public static bool Delete(ISession session, string itemCode)
        {
            return EntityManager.Delete<DictionaryItem>(session, itemCode);
        }

        /// <summary>
        /// 是否存在指定条件的系统数据字典项
        /// </summary>
        /// <param name="session"></param>
        /// <param name="propertyNames"></param>
        /// <param name="propertyValues"></param>
        /// <returns></returns>
        public static bool Exists(ISession session, string[] propertyNames, object[] propertyValues)
        {
            if (propertyNames != null && propertyValues != null && propertyNames.Length > 0 && propertyValues.Length == propertyNames.Length)
            {
                EntityQuery qry = session.CreateEntityQuery<DictionaryItem>();
                for (int i = 0; i < propertyNames.Length; i++)
                {
                    qry.And(Exp.Eq(propertyNames[i], propertyValues[i]));
                }
                return qry.Count() > 0;

            }
            return false;
        }

        /// <summary>
        ///  Map a DataRow to a DictionaryItem Entity.
        /// </summary>
        /// <returns></returns>
        public static DictionaryItem Row2Entity(System.Data.DataRow row)
        {
            if (row == null) return null;

            DictionaryItem entity = new DictionaryItem();

            entity._itemCode = Cast.String(row["DIC_ITEM_CODE"]);
            entity._groupCode = Cast.String(row["DIC_GROUP"]);
            entity._name = Cast.String(row["DIC_NAME"]);
            entity._itemType = Cast.Enum<DictionaryItemType>(row["DIC_ITEM_TYPE"]);
            entity._boolValue = Cast.Bool(row["BOOL_VALUE"]);
            entity._numberValue = Cast.Decimal(row["NUMBER_VALUE"]);
            entity._stringValue = Cast.String(row["STRING_VALUE"]);
            entity._note = Cast.String(row["DIC_NOTE"]);

            return entity;
        }

        /// <summary>
        ///  Map a DataTable's Rows to a List of DictionaryItem Entity.
        /// </summary>
        /// <returns></returns>
        public static IList<DictionaryItem> Row2Entity(System.Data.DataTable dt)
        {
            IList<DictionaryItem> list = null;
            if (dt != null && dt.Rows.Count > 0)
            {
                list = new List<DictionaryItem>(dt.Rows.Count);
                foreach (System.Data.DataRow row in dt.Rows)
                {
                    DictionaryItem entity = Row2Entity(row);
                    if (entity != null)
                    {
                        list.Add(entity);
                    }
                }
            }
            return list;
        }
        #endregion


        /// <summary>
        /// 获取组参数
        /// </summary>
        /// <param name="session"></param>
        /// <param name="groupCode"></param>
        /// <returns></returns>
        public static IList<DictionaryItem> GetDictionaryItemsByGroup(ISession session, string groupCode)
        {
            return
                session.CreateEntityQuery<DictionaryItem>()
                .And(Exp.Eq("GroupCode", groupCode))
                .List<DictionaryItem>();
        }

        /// <summary>
        /// 获取值的字符串
        /// </summary>
        public string ValueToString
        {
            get
            {
                switch (this._itemType)
                {
                    case DictionaryItemType.String:
                        return this._stringValue;
                    case DictionaryItemType.Numric:
                        return this._numberValue.ToString();
                    case DictionaryItemType.Boolean:
                        return this._boolValue.ToString();
                    case DictionaryItemType.Group:
                    default:
                        return this._name;
                       
                }
            }
        }

        /// <summary>
        /// 获取整型数
        /// </summary>
        /// <returns></returns>
        public int GetIntValue()
        {
            if (ItemType == DictionaryItemType.Numric)
            {
                return Cast.Int(this.NumberValue,0);
            }
            throw new ApplicationException("参数的数据类型不是数值型，不能获取");
        }

        /// <summary>
        /// 获取日期型数值
        /// </summary>
        /// <returns></returns>
        public DateTime GetDateTimeValue()
        {
            return Cast.DateTime(ValueToString);
        }
        

    }

    /// <summary>
    /// 数据字典
    /// </summary>
    public sealed class DataDictionary
    {
        static IDictionary<string, DictionaryItem> _dicItems = new Dictionary<string, DictionaryItem>();
        static IDictionary<string, IList<DictionaryItem>> _groupItems = new Dictionary<string, IList<DictionaryItem>>();

        /// <summary>
        /// 获取一组字典项
        /// </summary>
        /// <param name="session"></param>
        /// <param name="groupCode"></param>
        /// <returns></returns>
        public static IList<DictionaryItem> GetDictionaryItemsByGroup( string groupCode)
        {
            if (_groupItems.ContainsKey(groupCode))
            {
                return _groupItems[groupCode];
            }
            else
            {
                IList<DictionaryItem> items = null;
                using(ISession session = new Session())
                {
                   items=  DictionaryItem.GetDictionaryItemsByGroup(session, groupCode);
                }
                if (items == null || items.Count<=0)
                {
                    throw new ApplicationException(string.Format("没有设置 组代码为:{0}的数据字典子项，请确认初始化数据中包含此代码的初始化数据", groupCode));
                }
                else
                {
                    _groupItems.Add(groupCode, items);
                }              
                return items;
            }
        }

        /// <summary>
        /// 获取单个字典项
        /// </summary>
        /// <param name="itemCode"></param>
        /// <returns></returns>
        public static DictionaryItem GetDictionaryItemByCode(string itemCode)
        {
            if (_dicItems.ContainsKey(itemCode))
            {
                return _dicItems[itemCode];
            }
            else
            {
                DictionaryItem item = null;
                using (ISession session = new Session())
                {
                   item=  DictionaryItem.Retrieve(session, itemCode);
                   if (item != null)
                   {
                       _dicItems.Add(itemCode, item);
                   }
                   else
                   {
                       throw new ApplicationException(string.Format("没有设置 项代码为:{0}的数据字典项，请确认初始化数据中包含此代码的初始化数据",itemCode));
                   }
                   return item;
                }
            }
        }
    }
}
