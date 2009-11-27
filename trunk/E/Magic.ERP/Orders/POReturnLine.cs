//*******************************************
// ** Description:  Data Access Object for POReturnLine
// ** Author     :  Code generator
// ** Created    :   2008-12-19 2:01:45
// ** Modified   :
//*******************************************

namespace Magic.ERP.Orders
{
	using System;
	using Magic.Framework.Data;
	using Magic.Framework.ORM;
	using Magic.Framework.ORM.Mapping;

	/// <summary>
	/// Data Access Object for POReturnLine
	/// </summary>
	[Table("ORD_PUR_RTN_LINE")]
	public partial class  POReturnLine : IEntity
	{
		#region Private Fields
		private string _orderNumber;
		private string _lineNumber;
		private string _pONumber;
		private string _pOLine;
		private int _sKUID;
		private decimal _price;
		private decimal _quantity;
		private decimal _taxValue;
		private int _stockDetailID;
		#endregion

		#region Public Properties
		[Column(Name = "ORD_NUM", DbType = StdDbType.AnsiString, Length = 16, IsPrimary = true)]
		public string OrderNumber
		{
			get { return this._orderNumber; }
			set { this._orderNumber = value; }
		}

		[Column(Name = "LINE_NUM", DbType = StdDbType.AnsiString, Length = 4, IsPrimary = true)]
		public string LineNumber
		{
			get { return this._lineNumber; }
			set { this._lineNumber = value; }
		}

		[Column(Name = "PO_NUM", DbType = StdDbType.AnsiString, Length = 16)]
		public string PONumber
		{
			get { return this._pONumber; }
			set { this._pONumber = value; }
		}

		[Column(Name = "PO_LINE", DbType = StdDbType.AnsiString, Length = 4)]
		public string POLine
		{
			get { return this._pOLine; }
			set { this._pOLine = value; }
		}

		[Column(Name = "SKU_ID", DbType = StdDbType.Int32)]
		public int SKUID
		{
			get { return this._sKUID; }
			set { this._sKUID = value; }
		}

		///<summary>
		/// 采购价格
		///</summary>
		[Column(Name = "PUR_PRICE", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal Price
		{
			get { return this._price; }
			set { this._price = value; }
		}

		///<summary>
		/// 采购数量
		///</summary>
		[Column(Name = "RTN_QTY", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal Quantity
		{
			get { return this._quantity; }
			set { this._quantity = value; }
		}

		///<summary>
		/// 税率，例如0.17表示17%的税率
		///</summary>
		[Column(Name = "TAX_VAL", DbType = StdDbType.Number, Precision = 5, Scale = 3)]
		public decimal TaxValue
		{
			get { return this._taxValue; }
			set { this._taxValue = value; }
		}

		[Column(Name = "STO_DTL_ID", DbType = StdDbType.Int32)]
		public int StockDetailID
		{
			get { return this._stockDetailID; }
			set { this._stockDetailID = value; }
		}

		#endregion

		#region Entity Methods
		public POReturnLine()
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
		public static POReturnLine Retrieve(ISession session, string orderNumber, string lineNumber)
		{
			return EntityManager.Retrieve<POReturnLine>(session, new string[]{ "OrderNumber", "LineNumber" },  new object[]{ orderNumber, lineNumber });
		}
		public static bool Delete(ISession session, string orderNumber, string lineNumber)
		{
			return EntityManager.Delete<POReturnLine>(session, new string[]{ "OrderNumber", "LineNumber" },  new object[]{ orderNumber, lineNumber })>0;
		}
		#endregion
	}
}
