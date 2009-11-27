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
	/// ��Ϣ֪ͨ�ķ���
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
		/// ��Ϣ֪ͨ����ID
		///</summary>
		[Column(Name = "NTF_CAT_ID", DbType = StdDbType.Int32, IsPrimary = true)]
		public int CatID
		{
			get { return this._catID; }
			set { this._catID = value; }
		}

		///<summary>
		/// ��Ϣ����
		/// Sys, 1:  ϵͳ��Ϣ
		/// Mail, 2: �ʼ���Ϣ
		/// SMS, 3: ������Ϣ
		///</summary>
		[Column(Name = "NTF_TYPE", DbType = StdDbType.Int32)]
        public NotificationType Type
		{
			get { return this._type; }
			set { this._type = value; }
		}

		///<summary>
		/// �������Ƿ�������Ϣ֪ͨ����
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
		/// ģ���ļ����֣����������Ϣ�������ϵľ���·��
		///</summary>
		[Column(Name = "TEMPLATE_FILE", DbType = StdDbType.UnicodeString, Length = 200)]
		public string TemplateFile
		{
			get { return this._templateFile; }
			set { this._templateFile = value; }
		}

		///<summary>
		/// ��Ϣ֪ͨ��������ϵͳģ��: CRM, ERP, eShop
		///</summary>
		[Column(Name = "MDL_CODE", DbType = StdDbType.AnsiString, Length = 30)]
		public string ModuleCode
		{
			get { return this._moduleCode; }
			set { this._moduleCode = value; }
		}

		///<summary>
		/// ��Ϣ֪ͨ���͵������ı�
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
