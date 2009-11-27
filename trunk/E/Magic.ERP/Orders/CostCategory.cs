//*******************************************
// ** Description:  Data Access Object for CostCategory
// ** Author     :  Code generator
// ** Created    :   2008-10-6 15:23:31
// ** Modified   :
//*******************************************

namespace Magic.ERP.Orders
{
	using System;
	using Magic.Framework.Data;
	using Magic.Framework.ORM;
	using Magic.Framework.ORM.Mapping;

	/// <summary>
	/// Data Access Object for CostCategory
	/// 成本类型
	/// </summary>
	[Table("COST_CAT")]
	public partial class  CostCategory : IEntity
	{
		#region Private Fields
		private int _categoryID;
		private string _text;
		#endregion

		#region Public Properties
		[Column(Name = "COST_CAT_ID", DbType = StdDbType.Int32, IsPrimary = true)]
		public int CategoryID
		{
			get { return this._categoryID; }
			set { this._categoryID = value; }
		}

		[Column(Name = "CAT_TEXT", DbType = StdDbType.UnicodeString, Length = 50)]
		public string Text
		{
			get { return this._text; }
			set { this._text = value; }
		}

		#endregion

		#region Entity Methods
		public CostCategory()
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
		public static CostCategory Retrieve(ISession session, int categoryID)
		{
			return EntityManager.Retrieve<CostCategory>(session, categoryID);
		}
		public static bool Delete(ISession session, int categoryID)
		{
			return EntityManager.Delete<CostCategory>(session, categoryID);
		}
		#endregion
	}
}
