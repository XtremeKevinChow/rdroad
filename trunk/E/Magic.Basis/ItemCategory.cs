//*******************************************
// ** Description:  Data Access Object for ItemCategory
// ** Author     :  Code generator
// ** Created    :   2008-6-30 20:22:21
// ** Modified   :
//*******************************************

namespace Magic.Basis
{
	using System;
    using System.Collections.Generic;
    using Magic.Framework.ORM.Query;
	using Magic.Framework.Data;
	using Magic.Framework.ORM;
	using Magic.Framework.ORM.Mapping;

	/// <summary>
	/// Data Access Object for ItemCategory
	/// </summary>
	[Table("PRD_ITEM_CATEGORY")]
	public partial class  ItemCategory : IEntity
	{
		#region Private Fields
		private int _categoryID;
		private int _parentID;
		private int _catTypeID;
		private int _companyID;
		private int _catLevel;
		private bool _isLeaf;
		private bool _isRoot;
		private string _catCode;
		private string _catName;
		private string _catText;
		#endregion

		#region Public Properties
        [Column(Name = "CATALOG_ID", DbType = StdDbType.Int32, IsPrimary = true, IsSequence = true, SequenceName = "SEQ_PRD_CATALOG_ID")]
		public int CategoryID
		{
			get { return this._categoryID; }
			set { this._categoryID = value; }
		}

		[Column(Name = "PARENT_ID", DbType = StdDbType.Int32)]
		public int ParentID
		{
			get { return this._parentID; }
			set { this._parentID = value; }
		}

		[Column(Name = "CAT_TYPE_ID", DbType = StdDbType.Int32)]
		public int CatTypeID
		{
			get { return this._catTypeID; }
			set { this._catTypeID = value; }
		}

		[Column(Name = "COMPANY_ID", DbType = StdDbType.Int32)]
		public int CompanyID
		{
			get { return this._companyID; }
			set { this._companyID = value; }
		}

        [Column(Name = "CATALOG_LEVEL", DbType = StdDbType.Int32)]
		public int CatLevel
		{
			get { return this._catLevel; }
			set { this._catLevel = value; }
		}

		[Column(Name = "IS_LEAF", DbType = StdDbType.Bool)]
		public bool IsLeaf
		{
			get { return this._isLeaf; }
			set { this._isLeaf = value; }
		}

		[Column(Name = "IS_ROOT", DbType = StdDbType.Bool)]
		public bool IsRoot
		{
			get { return this._isRoot; }
			set { this._isRoot = value; }
		}

		[Column(Name = "CATALOG_CODE", DbType = StdDbType.AnsiString, Length = 50)]
		public string CatCode
		{
			get { return this._catCode; }
			set { this._catCode = value; }
		}

        [Column(Name = "CATALOG_NAME", DbType = StdDbType.AnsiString, Length = 200)]
		public string CatName
		{
			get { return this._catName; }
			set { this._catName = value; }
		}

		[Column(Name = "DESCRIPTION", DbType = StdDbType.AnsiString, Length = 200)]
		public string CatText
		{
			get { return this._catText; }
			set { this._catText = value; }
		}

		#endregion

		#region Entity Methods
		public ItemCategory()
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
		public static ItemCategory Retrieve(ISession session, int categoryID)
		{
			return EntityManager.Retrieve<ItemCategory>(session, categoryID);
		}
        public static ItemCategory Retrieve(ISession session, string catCode)
        {
            IList<ItemCategory> cats = session.CreateEntityQuery<ItemCategory>()
                .Where(Exp.Eq("CatCode", catCode))
                .List<ItemCategory>();
            return cats == null || cats.Count <= 0 ? null : cats[0];
        }
		public static bool Delete(ISession session, int categoryID)
		{
			return EntityManager.Delete<ItemCategory>(session, categoryID);
		}
		#endregion
	}
}
