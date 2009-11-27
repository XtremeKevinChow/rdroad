//*******************************************
// ** Description:  Data Access Object for NotificationCodeDef
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
	/// Data Access Object for NotificationCodeDef
	/// ������Ϣ֪ͨʱ����״̬��Ķ���
	/// </summary>
	[Table("BC_NOTIFY_CODE_DEF")]
    public partial class NotificationCodeDef : IEntity
	{
		#region Private Fields
        private NotificationType _type;
		private bool _isSuccess;
		private string _code;
		private string _text;
		#endregion

		#region Public Properties
		///<summary>
		/// ��Ϣ����
		/// Sys, 1:  ϵͳ��Ϣ
		/// Mail, 2: �ʼ���Ϣ
		/// SMS, 3: ������Ϣ
		///</summary>
		[Column(Name = "NTF_TYPE", DbType = StdDbType.Int32, IsPrimary = true)]
        public virtual NotificationType Type
		{
			get { return this._type; }
			set { this._type = value; }
		}

		[Column(Name = "IS_SUCCESS", DbType = StdDbType.Bool)]
		public virtual bool IsSuccess
		{
			get { return this._isSuccess; }
			set { this._isSuccess = value; }
		}

		[Column(Name = "DEF_CODE", DbType = StdDbType.AnsiString, Length = 20)]
		public virtual string Code
		{
			get { return this._code; }
			set { this._code = value; }
		}

		[Column(Name = "DEF_TEXT", DbType = StdDbType.UnicodeString, Length = 50)]
		public virtual string Text
		{
			get { return this._text; }
			set { this._text = value; }
		}

		#endregion

		#region Entity Methods
        public NotificationCodeDef()
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
		public static NotificationCodeDef Retrieve(ISession session, int type)
		{
			return EntityManager.Retrieve<NotificationCodeDef>(session, type);
		}
		public static bool Delete(ISession session, int type)
		{
			return EntityManager.Delete<NotificationCodeDef>(session, type);
		}
		#endregion
	}
}