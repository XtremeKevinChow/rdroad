//*******************************************
// ** Description:  Data Access Object for ItemColor
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
	/// Data Access Object for ItemColor
	/// </summary>
	[Table("PRD_ITEM_COLOR")]
	public partial class  ItemColor : IEntity
	{
		#region Private Fields
		private string _colorCode;
		private string _colorText;
        private string _description;
		#endregion

		#region Public Properties
        [Column(Name = "CODE", DbType = StdDbType.AnsiString, Length = 6, IsPrimary = true)]
		public string ColorCode
		{
			get { return this._colorCode; }
			set { this._colorCode = value; }
		}

        [Column(Name = "NAME", DbType = StdDbType.AnsiString, Length = 20)]
		public string ColorText
		{
			get { return this._colorText; }
			set { this._colorText = value; }
		}

        [Column(Name = "DESCRIPTION", DbType = StdDbType.AnsiString, Length = 100)]
        public string Description
        {
            get { return this._description; }
            set { this._description = value; }
        }
		#endregion

		#region Entity Methods
		public ItemColor()
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
		public static ItemColor Retrieve(ISession session, string colorCode)
		{
			return EntityManager.Retrieve<ItemColor>(session, colorCode);
		}
		public static bool Delete(ISession session, string colorCode)
		{
			return EntityManager.Delete<ItemColor>(session, colorCode);
		}
		#endregion
	}
}
