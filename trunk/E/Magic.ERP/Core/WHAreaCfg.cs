//*******************************************
// ** Description:  Data Access Object for WHAreaCfg
// ** Author     :  Code generator
// ** Created    :   2008-7-1 6:35:20
// ** Modified   :
//*******************************************

namespace Magic.ERP.Core
{
	using System;
	using Magic.Framework.Data;
	using Magic.Framework.ORM;
	using Magic.Framework.ORM.Mapping;

	/// <summary>
	/// Data Access Object for WHAreaCfg
	/// </summary>
	[Table("INV_WH_USE_CFG")]
	public partial class  WHAreaCfg : IEntity
	{
		#region Private Fields
		private string _areaCfgCode;
		private string _areaCode;
		private bool _requireAllChild;
		#endregion

		#region Public Properties
		///<summary>
		/// 如果是库房交易相关的仓库配置，配置代码为交易代码
		/// 其它的仓库配置，配置代码自定义，但需要保证不能与交易类型代码冲突
		/// 为了保证不冲突，所有交易类型均为3位数字，其它仓库使用配置均以字符A+2位数字表示
		///</summary>
		[Column(Name = "WH_CFG_CODE", DbType = StdDbType.AnsiChar, Length = 3, IsPrimary = true)]
		public string AreaCfgCode
		{
			get { return this._areaCfgCode; }
			set { this._areaCfgCode = value; }
		}

		[Column(Name = "AREA_CODE", DbType = StdDbType.AnsiString, Length = 8, IsPrimary = true)]
		public string AreaCode
		{
			get { return this._areaCode; }
			set { this._areaCode = value; }
		}

		///<summary>
		/// 是否需要所有下一级的交易仓库(IsTransArea为true)
		///</summary>
		[Column(Name = "REQ_ALL_CHILD", DbType = StdDbType.Bool)]
		public bool RequireAllChild
		{
			get { return this._requireAllChild; }
			set { this._requireAllChild = value; }
		}

		#endregion

		#region Entity Methods
		public WHAreaCfg()
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
		public static WHAreaCfg Retrieve(ISession session, string areaCfgCode, string areaCode)
		{
			return EntityManager.Retrieve<WHAreaCfg>(session, new string[]{ "AreaCfgCode", "AreaCode" },  new object[]{ areaCfgCode, areaCode });
		}
		public static bool Delete(ISession session, string areaCfgCode, string areaCode)
		{
			return EntityManager.Delete<WHAreaCfg>(session, new string[]{ "AreaCfgCode", "AreaCode" },  new object[]{ areaCfgCode, areaCode })>0;
		}
		#endregion
	}
}