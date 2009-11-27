//*******************************************
// ** Description:  Data Access Object for NotificationLog
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
	/// Data Access Object for NotificationLog
	/// ��Ϣ֪ͨ����־��Ϣ���ʼ���������Ϣ����¼������־
	/// </summary>
	[Table("BC_NOTIFY_LOG")]
    internal partial class NotificationLog : IEntity
	{
		#region Private Fields
		private int _logID;
		private int _notifyID;
		private int _receiverID;
		private string _resultCode;
		private string _errorText;
		private DateTime _logTime;
		#endregion

		#region Public Properties
		[Column(Name = "NTF_LOG_ID", DbType = StdDbType.Int32, IsPrimary = true, IsSequence = true, SequenceName = "SEQ_BC_NOTIFY_LOG")]
		public int LogID
		{
			get { return this._logID; }
			set { this._logID = value; }
		}

		[Column(Name = "NTF_ID", DbType = StdDbType.Int32)]
		public int NotifyID
		{
			get { return this._notifyID; }
			set { this._notifyID = value; }
		}

		[Column(Name = "NTF_RCV_ID", DbType = StdDbType.Int32)]
		public int ReceiverID
		{
			get { return this._receiverID; }
			set { this._receiverID = value; }
		}

		///<summary>
		/// ���Žӿڷ��ص�״̬�룻�ʼ����ͽ��״̬�롣�ȵ�
		///</summary>
		[Column(Name = "RSLT_CODE", DbType = StdDbType.AnsiString, Length = 10)]
		public string ResultCode
		{
			get { return this._resultCode; }
			set { this._resultCode = value; }
		}

		///<summary>
		/// ��������쳣������쳣��Ϣ
		///</summary>
		[Column(Name = "ErrorText", DbType = StdDbType.UnicodeString, Length = 2000)]
		public string ErrorText
		{
			get { return this._errorText; }
			set { this._errorText = value; }
		}

		[Column(Name = "LOG_TIME", DbType = StdDbType.DateTime)]
		public DateTime LogTime
		{
			get { return this._logTime; }
			set { this._logTime = value; }
		}

		#endregion

		#region Entity Methods
        public NotificationLog()
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
		public static NotificationLog Retrieve(ISession session, int logID)
		{
			return EntityManager.Retrieve<NotificationLog>(session, logID);
		}
		public static bool Delete(ISession session, int logID)
		{
			return EntityManager.Delete<NotificationLog>(session, logID);
		}
		#endregion
	}
}
