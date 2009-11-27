//*******************************************
// ** Description:  Data Access Object for PendingReceipt
// ** Author     :  Code generator
// ** Created    :   2009-3-28 10:39:38
// ** Modified   :
//*******************************************

namespace Magic.ERP.Orders
{
	using System;
	using Magic.Framework.Data;
	using Magic.Framework.ORM;
	using Magic.Framework.ORM.Mapping;

	/// <summary>
	/// </summary>
	[Table("CUS_PENDDING_RCV")]
	public partial class  PendingReceipt : IEntity
	{
		#region Private Fields
		private int _sKU_ID;
		private decimal _qty;
		#endregion

		#region Public Properties
		[Column(Name = "SKU_ID", DbType = StdDbType.Int32, IsPrimary = true)]
		public int SKU_ID
		{
			get { return this._sKU_ID; }
			set { this._sKU_ID = value; }
		}

		[Column(Name = "QTY", DbType = StdDbType.Number, Precision = 10)]
		public decimal Qty
		{
			get { return this._qty; }
			set { this._qty = value; }
		}

		#endregion

		#region Entity Methods
		public PendingReceipt()
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
		public static PendingReceipt Retrieve(ISession session, int sKU_ID)
		{
			return EntityManager.Retrieve<PendingReceipt>(session, sKU_ID);
		}
		public static bool Delete(ISession session, int sKU_ID)
		{
			return EntityManager.Delete<PendingReceipt>(session, sKU_ID);
		}

		public override int GetHashCode()
		{
			return this._sKU_ID.GetHashCode();
		}
		public override bool Equals(object obj)
		{
			if (obj == null) return false;
			PendingReceipt tempObj = obj as PendingReceipt;
			if (tempObj == null) return false;
			return this._sKU_ID == tempObj._sKU_ID;
		}
		#endregion
	}
}
