//*******************************************
// ** Description:  Data Access Object for NotificationCategory
// ** Author     :  Code generator
// ** Created    :   2008-9-22 10:55:05
// ** Modified   :
//*******************************************

namespace Magic.Notification
{
	using System;
	using Magic.Framework.Data;
	using Magic.Framework.ORM;
	using Magic.Framework.ORM.Mapping;

	/// <summary>
	/// Data Access Object for NotificationCategory
	/// 消息通知的分类
	/// </summary>
	[Table("BC_NOTIFY_CAT")]
    public partial class NotificationCategory : IEntity
	{
		#region Private Fields
		private int _catID;
        private NotificationType _type;
		private bool _isEnabled;
		private string _titleTemplate;
		private string _templateFile;
		private string _moduleCode;
		private string _categoryText;
		#endregion

		#region Public Properties
		///<summary>
		/// 消息通知分类ID
		///</summary>
		[Column(Name = "NTF_CAT_ID", DbType = StdDbType.Int32, IsPrimary = true)]
		public int CatID
		{
			get { return this._catID; }
			set { this._catID = value; }
		}

		///<summary>
		/// 消息类型
		/// Sys, 1:  系统消息
		/// Mail, 2: 邮件消息
		/// SMS, 3: 短信消息
		///</summary>
		[Column(Name = "NTF_TYPE", DbType = StdDbType.Int32)]
        public NotificationType Type
		{
			get { return this._type; }
			set { this._type = value; }
		}

		///<summary>
		/// 该类型是否启用消息通知功能
		///</summary>
		[Column(Name = "IS_ENABLED", DbType = StdDbType.Bool)]
		public bool IsEnabled
		{
			get { return this._isEnabled; }
			set { this._isEnabled = value; }
		}

		[Column(Name = "TITLE_TMPL", DbType = StdDbType.UnicodeString, Length = 60)]
		public string TitleTemplate
		{
			get { return this._titleTemplate; }
			set { this._titleTemplate = value; }
		}

		///<summary>
		/// 模板文件名字，必须包含消息服务器上的绝对路径
		///</summary>
		[Column(Name = "TEMPLATE_FILE", DbType = StdDbType.UnicodeString, Length = 200)]
		public string TemplateFile
		{
			get { return this._templateFile; }
			set { this._templateFile = value; }
		}

		///<summary>
		/// 消息通知类型所属系统模块: CRM, ERP, eShop
		///</summary>
		[Column(Name = "MDL_CODE", DbType = StdDbType.AnsiString, Length = 30)]
		public string ModuleCode
		{
			get { return this._moduleCode; }
			set { this._moduleCode = value; }
		}

		///<summary>
		/// 消息通知类型的描述文本
		///</summary>
		[Column(Name = "CAT_TEXT", DbType = StdDbType.UnicodeString, Length = 30)]
		public string CategoryText
		{
			get { return this._categoryText; }
			set { this._categoryText = value; }
		}

		#endregion

		#region Entity Methods
        public NotificationCategory()
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
		public static NotificationCategory Retrieve(ISession session, int catID)
		{
			return EntityManager.Retrieve<NotificationCategory>(session, catID);
		}
		public static bool Delete(ISession session, int catID)
		{
			return EntityManager.Delete<NotificationCategory>(session, catID);
		}
		#endregion
	}
}
