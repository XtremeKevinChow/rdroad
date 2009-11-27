//*******************************************
// ** Description:  Data Access Object for TaxDef
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
	/// Data Access Object for TaxDef
	/// 税率定义
	/// </summary>
	[Table("BAS_TAX_DEF")]
	public partial class  TaxDef : IEntity
	{
		#region Private Fields
		private int _taxID;
		private string _taxText;
		private decimal _taxValue;
		private int _taxIndex;
		#endregion

		#region Public Properties
		[Column(Name = "TAX_ID", DbType = StdDbType.Int32, IsPrimary = true)]
		public int TaxID
		{
			get { return this._taxID; }
			set { this._taxID = value; }
		}

		///<summary>
		/// 税率文本描述
		///</summary>
		[Column(Name = "TAX_TEXT", DbType = StdDbType.UnicodeString, Length = 10)]
		public string TaxText
		{
			get { return this._taxText; }
			set { this._taxText = value; }
		}

		///<summary>
		/// 税率值，0.17表示17%的税率
		///</summary>
		[Column(Name = "TAX_VAL", DbType = StdDbType.Number, Precision = 5, Scale = 3)]
		public decimal TaxValue
		{
			get { return this._taxValue; }
			set { this._taxValue = value; }
		}

		///<summary>
		/// 排列顺序
		///</summary>
		[Column(Name = "TAX_INDX", DbType = StdDbType.Int32)]
		public int TaxIndex
		{
			get { return this._taxIndex; }
			set { this._taxIndex = value; }
		}

		#endregion

		#region Entity Methods
		public TaxDef()
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
		public static TaxDef Retrieve(ISession session, int taxID)
		{
			return EntityManager.Retrieve<TaxDef>(session, taxID);
		}
		public static bool Delete(ISession session, int taxID)
		{
			return EntityManager.Delete<TaxDef>(session, taxID);
		}
		#endregion
	}
}
