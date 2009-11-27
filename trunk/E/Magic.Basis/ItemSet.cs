//*******************************************
// ** Description:  Data Access Object for ItemSet
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
	/// Data Access Object for ItemSet
	/// 系列套装
	/// </summary>
	[Table("PRD_ITEM_SET")]
	public partial class  ItemSet : IEntity
	{
		#region Private Fields
		private int _setID;
		private int _itemID;
		private bool _isOptional;
		#endregion

		#region Public Properties
		[Column(Name = "SET_ID", DbType = StdDbType.Int32, IsPrimary = true)]
		public int SetID
		{
			get { return this._setID; }
			set { this._setID = value; }
		}

		[Column(Name = "ITM_ID", DbType = StdDbType.Int32, IsPrimary = true)]
		public int ItemID
		{
			get { return this._itemID; }
			set { this._itemID = value; }
		}

		///<summary>
		/// 这个字段只对于可选套装有效
		/// 0: 必须购买的商品
		/// 1: 可选是否购买的商品
		///</summary>
		[Column(Name = "IS_OPTIONAL", DbType = StdDbType.Bool)]
		public bool IsOptional
		{
			get { return this._isOptional; }
			set { this._isOptional = value; }
		}

		#endregion

		#region Entity Methods
		public ItemSet()
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
		public static ItemSet Retrieve(ISession session, int setID, int itemID)
		{
			return EntityManager.Retrieve<ItemSet>(session, new string[]{ "SetID", "ItemID" },  new object[]{ setID, itemID });
		}
		public static bool Delete(ISession session, int setID, int itemID)
		{
			return EntityManager.Delete<ItemSet>(session, new string[]{ "SetID", "ItemID" },  new object[]{ setID, itemID })>0;
		}
		#endregion
	}
}
