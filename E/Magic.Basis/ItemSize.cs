//*******************************************
// ** Description:  Data Access Object for ItemSize
// ** Author     :  Code generator
// ** Created    :   2008-7-7 20:32:34
// ** Modified   :
//*******************************************

namespace Magic.Basis
{
    using System;
    using Magic.Framework.Data;
    using Magic.Framework.ORM;
    using Magic.Framework.ORM.Mapping;

    /// <summary>
    /// Data Access Object for ItemSize
    /// </summary>
    [Table("PRD_ITEM_SIZE")]
    public partial class ItemSize : IEntity
    {
        #region Private Fields
        private string _sizeCode;
        private int _categoryID;
        private string _sizeText;
        private string _description;
        #endregion

        #region Public Properties
        [Column(Name = "CODE", DbType = StdDbType.AnsiString, Length = 6, IsPrimary = true)]
        public string SizeCode
        {
            get { return this._sizeCode; }
            set { this._sizeCode = value; }
        }

        [Column(Name = "TYPE_ID", DbType = StdDbType.Int32, IsPrimary = true)]
        public int CategoryID
        {
            get { return this._categoryID; }
            set { this._categoryID = value; }
        }

        [Column(Name = "NAME", DbType = StdDbType.AnsiString, Length = 20)]
        public string SizeText
        {
            get { return this._sizeText; }
            set { this._sizeText = value; }
        }

        [Column(Name = "DESCRIPTION", DbType = StdDbType.AnsiString, Length = 100)]
        public string Description
        {
            get { return this._description; }
            set { this._description = value; }
        }
        #endregion

        #region Entity Methods
        public ItemSize()
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
        public static ItemSize Retrieve(ISession session, string sizeCode, int catId)
        {
            return EntityManager.Retrieve<ItemSize>(session, new string[] { "SizeCode", "CategoryID" }, new object[] { sizeCode, catId });
        }
        public static bool Delete(ISession session, string sizeCode, int catId)
        {
            return EntityManager.Delete<ItemSize>(session, new string[] { "SizeCode", "CategoryID" }, new object[] { sizeCode, catId }) > 0;
        }
        #endregion
    }
}
