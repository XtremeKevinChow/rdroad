//*******************************************
// ** Description:  Data Access Object for CRMSNLine
// ** Author     :  Code generator
// ** Created    :   2008-7-30 23:36:25
// ** Modified   :
//*******************************************

namespace Magic.ERP.Orders
{
	using System;
	using Magic.Framework.Data;
	using Magic.Framework.ORM;
	using Magic.Framework.ORM.Mapping;

	/// <summary>
	/// Data Access Object for CRMSNLine
	/// </summary>
	[Table("ORD_SHIPPINGNOTICE_LINES")]
	public partial class  CRMSNLine : IEntity
	{
		#region Private Fields
		private int _iD;
		private int _sNID;
		private int _saleOrderLine;
		private decimal _quantity;
		private decimal _price;
        private CRMSNLineStatus _status;
		private string _comments;
		private int _operatorID;
		private int _sellType;
		private int _memberAwardID;
		private int _sKUID;
		#endregion

		#region Public Properties
		[Column(Name = "ID", DbType = StdDbType.Int32, IsPrimary = true)]
		public int ID
		{
			get { return this._iD; }
			set { this._iD = value; }
		}

		[Column(Name = "SN_ID", DbType = StdDbType.Int32)]
		public int SNID
		{
			get { return this._sNID; }
			set { this._sNID = value; }
		}

		[Column(Name = "REF_ORDER_LINE_ID", DbType = StdDbType.Int32)]
		public int SaleOrderLine
		{
			get { return this._saleOrderLine; }
			set { this._saleOrderLine = value; }
		}

		[Column(Name = "QUANTITY", DbType = StdDbType.Number, Precision = 8, Scale = 2)]
		public decimal Quantity
		{
			get { return this._quantity; }
			set { this._quantity = value; }
		}

		[Column(Name = "PRICE", DbType = StdDbType.Number, Precision = 8, Scale = 2)]
		public decimal Price
		{
			get { return this._price; }
			set { this._price = value; }
		}

		[Column(Name = "STATUS", DbType = StdDbType.Int32)]
        public CRMSNLineStatus Status
		{
			get { return this._status; }
			set { this._status = value; }
		}

		[Column(Name = "COMMENTS", DbType = StdDbType.AnsiString, Length = 200)]
		public string Comments
		{
			get { return this._comments; }
			set { this._comments = value; }
		}

		[Column(Name = "OPERATOR_ID", DbType = StdDbType.Int32)]
		public int OperatorID
		{
			get { return this._operatorID; }
			set { this._operatorID = value; }
		}

		[Column(Name = "SELL_TYPE", DbType = StdDbType.Int32)]
		public int SellType
		{
			get { return this._sellType; }
			set { this._sellType = value; }
		}

		[Column(Name = "MBR_AWARD_ID", DbType = StdDbType.Int32)]
		public int MemberAwardID
		{
			get { return this._memberAwardID; }
			set { this._memberAwardID = value; }
		}

		[Column(Name = "SKU_ID", DbType = StdDbType.Int32)]
		public int SKUID
		{
			get { return this._sKUID; }
			set { this._sKUID = value; }
		}
		#endregion

		#region Entity Methods
		public CRMSNLine()
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
		public static CRMSNLine Retrieve(ISession session, int iD)
		{
			return EntityManager.Retrieve<CRMSNLine>(session, iD);
		}
		public static bool Delete(ISession session, int iD)
		{
			return EntityManager.Delete<CRMSNLine>(session, iD);
		}
		#endregion
	}
}
