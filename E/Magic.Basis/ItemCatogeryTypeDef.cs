//*******************************************
// ** Description:  Data Access Object for ItemCatogeryTypeDef
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
	/// Data Access Object for ItemCatogeryTypeDef
	/// </summary>
	[Table("PRD_ITEM_CAT_TYPE")]
	public partial class  ItemCatogeryTypeDef : IEntity
	{
		#region Private Fields
		private int _catTypeID;
		private string _catTypeText;
		#endregion

		#region Public Properties
        [Column(Name = "CAT_TYPE_ID", DbType = StdDbType.Int32, IsPrimary = true, IsSequence = true, SequenceName = "SEQ_PRD_ITEM_CAT_TYPE")]
		public int CatTypeID
		{
			get { return this._catTypeID; }
			set { this._catTypeID = value; }
		}

		[Column(Name = "CAT_TYPE_TEXT", DbType = StdDbType.UnicodeString, Length = 20)]
		public string CatTypeText
		{
			get { return this._catTypeText; }
			set { this._catTypeText = value; }
		}

		#endregion

		#region Entity Methods
		public ItemCatogeryTypeDef()
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
		public static ItemCatogeryTypeDef Retrieve(ISession session, int catTypeID)
		{
			return EntityManager.Retrieve<ItemCatogeryTypeDef>(session, catTypeID);
		}
		public static bool Delete(ISession session, int catTypeID)
		{
			return EntityManager.Delete<ItemCatogeryTypeDef>(session, catTypeID);
		}
		#endregion
	}
}
