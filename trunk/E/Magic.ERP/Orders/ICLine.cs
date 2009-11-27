//*******************************************
// ** Description:  Data Access Object for ICLine
// ** Author     :  Code generator
// ** Created    :   2008-6-30 20:22:44
// ** Modified   :
//*******************************************

namespace Magic.ERP.Orders
{
	using System;
	using Magic.Framework.Data;
	using Magic.Framework.ORM;
	using Magic.Framework.ORM.Mapping;

	/// <summary>
	/// Data Access Object for ICLine
	/// 交接单明细
	/// </summary>
	[Table("ORD_IC_LINE")]
	public partial class  ICLine : IEntity
	{
		#region Private Fields
		private string _orderNumber;
		private string _lineNumber;
		private string _orderTypeCode;
		private string _refOrderNumber;
		#endregion

		#region Public Properties
		[Column(Name = "ORD_NUM", DbType = StdDbType.AnsiString, Length = 16, IsPrimary = true)]
		public string OrderNumber
		{
			get { return this._orderNumber; }
			set { this._orderNumber = value; }
		}

        [Column(Name = "LINE_NUM", DbType = StdDbType.AnsiString, Length = 10, IsPrimary = true)]
		public string LineNumber
		{
			get { return this._lineNumber; }
			set { this._lineNumber = value; }
		}

		///<summary>
		/// 单据类型代码
		///</summary>
		[Column(Name = "ORD_TYPE_CODE", DbType = StdDbType.AnsiChar, Length = 3)]
		public string OrderTypeCode
		{
			get { return this._orderTypeCode; }
			set { this._orderTypeCode = value; }
		}

		///<summary>
		/// 销售订单发货单单号，或者拒收的退货、换货发货单单号
		///</summary>
		[Column(Name = "REF_ORDER_NUM", DbType = StdDbType.AnsiString, Length = 16)]
		public string RefOrderNumber
		{
			get { return this._refOrderNumber; }
			set { this._refOrderNumber = value; }
		}

		#endregion

		#region Entity Methods
		public ICLine()
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
		public static ICLine Retrieve(ISession session, string orderNumber, string lineNumber)
		{
			return EntityManager.Retrieve<ICLine>(session, new string[]{ "OrderNumber", "LineNumber" },  new object[]{ orderNumber, lineNumber });
		}
		public static bool Delete(ISession session, string orderNumber, string lineNumber)
		{
			return EntityManager.Delete<ICLine>(session, new string[]{ "OrderNumber", "LineNumber" },  new object[]{ orderNumber, lineNumber })>0;
		}
		#endregion
	}
}
