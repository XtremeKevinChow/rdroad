//*******************************************
// ** Description:  Data Access Object for StockDetail
// ** Author     :  Code generator
// ** Created    :   2008-7-6 13:41:05
// ** Modified   :
//*******************************************

namespace Magic.ERP.Core
{
	using System;
    using System.Collections.Generic;
	using Magic.Framework.Data;
	using Magic.Framework.ORM;
    using Magic.Framework.ORM.Query;
	using Magic.Framework.ORM.Mapping;

	/// <summary>
	/// Data Access Object for StockDetail
	/// </summary>
	[Table("INV_STOCK_DETAIL")]
	public partial class  StockDetail : IEntity
	{
		#region Private Fields
		private int _stockDetailID;
		private int _sKUID;
		private string _locationCode;
		private string _areaCode;
		private string _sectionCode;
		private string _lotNumber;
		private decimal _stockQty;
		private decimal _frozenQty;
		#endregion

		#region Public Properties
		[Column(Name = "STOCK_DTL_ID", DbType = StdDbType.Int32, IsPrimary = true, IsSequence = true, SequenceName = "SEQ_INV_STOCK_DETAIL")]
		public int StockDetailID
		{
			get { return this._stockDetailID; }
			set { this._stockDetailID = value; }
		}

		[Column(Name = "SKU_ID", DbType = StdDbType.Int32)]
		public int SKUID
		{
			get { return this._sKUID; }
			set { this._sKUID = value; }
		}

		[Column(Name = "LOC_CODE", DbType = StdDbType.AnsiString, Length = 8)]
		public string LocationCode
		{
			get { return this._locationCode; }
			set { this._locationCode = value; }
		}

		[Column(Name = "AREA_CODE", DbType = StdDbType.AnsiString, Length = 8)]
		public string AreaCode
		{
			get { return this._areaCode; }
			set { this._areaCode = value; }
		}

		[Column(Name = "SEC_CODE", DbType = StdDbType.AnsiString, Length = 10)]
		public string SectionCode
		{
			get { return this._sectionCode; }
			set { this._sectionCode = value; }
		}

		[Column(Name = "LOT_NUM", DbType = StdDbType.AnsiString, Length = 24)]
		public string LotNumber
		{
			get { return this._lotNumber; }
			set { this._lotNumber = value; }
		}

		[Column(Name = "STOCK_QTY", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
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

		#endregion

		#region Entity Methods
		public StockDetail()
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
		public static StockDetail Retrieve(ISession session, int stockDetailID)
		{
			return EntityManager.Retrieve<StockDetail>(session, stockDetailID);
		}
        public static StockDetail Retrieve(ISession session, int skuId, string locationCode, string areaCode, string sectionCode)
        {
            if (skuId <= 0) return null;
            if (string.IsNullOrEmpty(locationCode) || locationCode.Trim().Length <= 0) return null;
            if (string.IsNullOrEmpty(areaCode) || areaCode.Trim().Length <= 0) return null;
            EntityQuery query = session.CreateEntityQuery<StockDetail>()
                .Where(Exp.Eq("SKUID", skuId) & Exp.Eq("LocationCode", locationCode) & Exp.Eq("AreaCode", areaCode))
                .OrderBy("StockQty", Order.Desc);
            if (!string.IsNullOrEmpty(sectionCode) && sectionCode.Trim().Length > 0)
                query.And(Exp.Eq("SectionCode", sectionCode.Trim()));
            IList<StockDetail> details = query.List<StockDetail>();
            return details == null || details.Count <= 0 ? null : details[0];
        }
		public static bool Delete(ISession session, int stockDetailID)
		{
			return EntityManager.Delete<StockDetail>(session, stockDetailID);
		}
		#endregion
	}
}