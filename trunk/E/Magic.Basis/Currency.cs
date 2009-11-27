//*******************************************
// ** Description:  Data Access Object for Currency
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
	/// Data Access Object for Currency
	/// </summary>
	[Table("BAS_CURRENCY")]
	public partial class  Currency : IEntity
	{
		#region Private Fields
		private string _currencyCode;
		private string _currencyText;
		#endregion

		#region Public Properties
		[Column(Name = "CUR_CODE", DbType = StdDbType.AnsiString, Length = 4, IsPrimary = true)]
		public string CurrencyCode
		{
			get { return this._currencyCode; }
			set { this._currencyCode = value; }
		}

		[Column(Name = "CUR_TEXT", DbType = StdDbType.UnicodeString, Length = 10)]
		public string CurrencyText
		{
			get { return this._currencyText; }
			set { this._currencyText = value; }
		}

		#endregion

		#region Entity Methods
		public Currency()
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
		public static Currency Retrieve(ISession session, string currencyCode)
		{
			return EntityManager.Retrieve<Currency>(session, currencyCode);
		}
		public static bool Delete(ISession session, string currencyCode)
		{
			return EntityManager.Delete<Currency>(session, currencyCode);
		}
		#endregion
	}
}
