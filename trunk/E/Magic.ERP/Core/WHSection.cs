//*******************************************
// ** Description:  Data Access Object for WHSection
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
	/// Data Access Object for WHSection
	/// 货架
	/// </summary>
	[Table("INV_WH_SECTION")]
	public partial class  WHSection : IEntity
	{
		#region Private Fields
		private string _areaCode;
		private string _sectionCode;
        private WHStatus _status;
		private decimal _sectionCapacity;
		private string _text;
		#endregion

		#region Public Properties
		[Column(Name = "AREA_CODE", DbType = StdDbType.AnsiString, Length = 8, IsPrimary = true)]
		public string AreaCode
		{
			get { return this._areaCode; }
			set { this._areaCode = value; }
		}

		[Column(Name = "SEC_CODE", DbType = StdDbType.AnsiString, Length = 10, IsPrimary = true)]
		public string SectionCode
		{
			get { return this._sectionCode; }
			set { this._sectionCode = value; }
		}

		///<summary>
		/// 0: 删除
		/// 1: 无效、禁用
		/// 2: 有效
		///</summary>
		[Column(Name = "SEC_STATUS", DbType = StdDbType.Int32, Precision = 1)]
        public WHStatus Status
		{
			get { return this._status; }
			set { this._status = value; }
		}

		///<summary>
		/// 货架容量
		///</summary>
		[Column(Name = "SEC_CAPACITY", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal SectionCapacity
		{
			get { return this._sectionCapacity; }
			set { this._sectionCapacity = value; }
		}

		[Column(Name = "SEC_TEXT", DbType = StdDbType.UnicodeString, Length = 40)]
		public string Text
		{
			get { return this._text; }
			set { this._text = value; }
		}

		#endregion

		#region Entity Methods
		public WHSection()
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
		public static WHSection Retrieve(ISession session, string areaCode, string sectionCode)
		{
			return EntityManager.Retrieve<WHSection>(session, new string[]{ "AreaCode", "SectionCode" },  new object[]{ areaCode, sectionCode });
		}
		public static bool Delete(ISession session, string areaCode, string sectionCode)
		{
			return EntityManager.Delete<WHSection>(session, new string[]{ "AreaCode", "SectionCode" },  new object[]{ areaCode, sectionCode })>0;
		}
		#endregion
	}
}
