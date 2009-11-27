//*******************************************
// ** Description:  Data Access Object for StockSummary
// ** Author     :  Code generator
// ** Created    :   2008-6-30 20:23:03
// ** Modified   :
//*******************************************

namespace Magic.ERP.Core
{
	using System;
	using Magic.Framework.Data;
	using Magic.Framework.ORM;
	using Magic.Framework.ORM.Mapping;

	/// <summary>
	/// Data Access Object for StockSummary
	/// </summary>
	[Table("INV_STOCK_SUM")]
	public partial class  StockSummary : IEntity
	{
		#region Private Fields
		private int _sKUID;
		private decimal _stockQty;
		private decimal _frozenQty;
		private DateTime _lastUpdate;
		#endregion

		#region Public Properties
		[Column(Name = "SKU_ID", DbType = StdDbType.Int32, IsPrimary = true)]
		public int SKUID
		{
			get { return this._sKUID; }
			set { this._sKUID = value; }
		}

        [Column(Name = "USE_QTY", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal StockQty
		{
			get { return this._stockQty; }
			set { this._stockQty = value; }
		}

		[Column(Name = "FROZEN_QTY", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal FrozenQty
		{
			get { return this._frozenQty; }
			set { this._frozenQty = value; }
		}

		[Column(Name = "LAST_UPDATE", DbType = StdDbType.DateTime)]
		public DateTime LastUpdate
		{
			get { return this._lastUpdate; }
			set { this._lastUpdate = value; }
		}

		#endregion

		#region Entity Methods
		public StockSummary()
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
		public static StockSummary Retrieve(ISession session, int sKUID)
		{
			return EntityManager.Retrieve<StockSummary>(session, sKUID);
		}
		public static bool Delete(ISession session, int sKUID)
		{
			return EntityManager.Delete<StockSummary>(session, sKUID);
		}
		#endregion
	}
}
