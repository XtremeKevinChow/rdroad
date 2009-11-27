//*******************************************
// ** Description:  Data Access Object for INVCheckWh
// ** Author     :  Code generator
// ** Created    :   2008-7-13 21:22:51
// ** Modified   :
//*******************************************

namespace Magic.ERP.Orders
{
	using System;
	using Magic.Framework.Data;
	using Magic.Framework.ORM;
	using Magic.Framework.ORM.Mapping;

	/// <summary>
	/// Data Access Object for INVCheckWh
	/// </summary>
	[Table("ORD_INV_CHK_WH")]
	public partial class  INVCheckWh : IEntity
	{
		#region Private Fields
		private string _orderNumber;
		private string _areaCode;
		#endregion

		#region Public Properties
		[Column(Name = "ORD_NUM", DbType = StdDbType.AnsiString, Length = 16, IsPrimary = true)]
		public string OrderNumber
		{
			get { return this._orderNumber; }
			set { this._orderNumber = value; }
		}

		[Column(Name = "AREA_CODE", DbType = StdDbType.AnsiString, Length = 8, IsPrimary = true)]
		public string AreaCode
		{
			get { return this._areaCode; }
			set { this._areaCode = value; }
		}

		#endregion

		#region Entity Methods
		public INVCheckWh()
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
		public static INVCheckWh Retrieve(ISession session, string orderNumber, string areaCode)
		{
			return EntityManager.Retrieve<INVCheckWh>(session, new string[]{ "OrderNumber", "AreaCode" },  new object[]{ orderNumber, areaCode });
		}
		public static bool Delete(ISession session, string orderNumber, string areaCode)
		{
			return EntityManager.Delete<INVCheckWh>(session, new string[]{ "OrderNumber", "AreaCode" },  new object[]{ orderNumber, areaCode })>0;
		}
		#endregion
	}
}
