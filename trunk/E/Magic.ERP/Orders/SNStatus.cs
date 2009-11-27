//*******************************************
// ** Description:  Data Access Object for SNStatus
// ** Author     :  Code generator
// ** Created    :   2008-8-7 0:39:46
// ** Modified   :
//*******************************************

namespace Magic.ERP.Orders
{
	using System;
	using Magic.Framework.Data;
	using Magic.Framework.ORM;
	using Magic.Framework.ORM.Mapping;

	/// <summary>
	/// Data Access Object for SNStatus
	/// </summary>
    [Table("S_SHIPPINGNOTICE_STATUS")]
	public partial class  SNStatus : IEntity
	{
		#region Private Fields
		private int _iD;
		private string _name;
		#endregion

		#region Public Properties
		[Column(Name = "ID", DbType = StdDbType.Int32, IsPrimary = true)]
		public int ID
		{
			get { return this._iD; }
			set { this._iD = value; }
		}

		[Column(Name = "NAME", DbType = StdDbType.AnsiString, Length = 20)]
		public string Name
		{
			get { return this._name; }
			set { this._name = value; }
		}

		#endregion

		#region Entity Methods
		public SNStatus()
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
		public static SNStatus Retrieve(ISession session, int iD)
		{
			return EntityManager.Retrieve<SNStatus>(session, iD);
		}
		public static bool Delete(ISession session, int iD)
		{
			return EntityManager.Delete<SNStatus>(session, iD);
		}
		#endregion
	}
}
