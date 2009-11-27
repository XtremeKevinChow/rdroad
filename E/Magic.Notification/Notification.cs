//*******************************************
// ** Description:  Data Access Object for Notification
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
	/// Data Access Object for Notification
	/// ϵͳ��Ϣ֪ͨ����¼
	/// </summary>
	[Table("BC_NOTIFY")]
	public partial class  Notification
	{
		#region Private Fields
		private int _notifyID;
		private int _catID;
        private NotificationStatus _status;
        private NotificationResultStatus _resultStatus;
		private int _refCount;
		private string _triggerID;
		private DateTime _createTime;
		private DateTime _finishTime;
		#endregion

		#region Public Properties
		[Column(Name = "NTF_ID", DbType = StdDbType.Int32, IsPrimary = true, IsSequence = true, SequenceName = "SEQ_BC_NOTIFY")]
		public int NotifyID
		{
			get { return this._notifyID; }
			set { this._notifyID = value; }
		}

		///<summary>
		/// ��Ϣ֪ͨ����ID
		///</summary>
		[Column(Name = "NTF_CAT_ID", DbType = StdDbType.Int32)]
		public int CatID
		{
			get { return this._catID; }
			set { this._catID = value; }
		}

		///<summary>
		/// ��Ϣ״̬:
		/// New, 10: �½�
		/// Close, 20: �Ѵ���
		/// Cancel, 0: ȡ��
		///</summary>
		[Column(Name = "NTF_STATUS", DbType = StdDbType.Int32)]
        public NotificationStatus Status
		{
			get { return this._status; }
			set { this._status = value; }
		}

		///<summary>
		/// ������״̬
		/// 0: δ����
		/// 10: ȫ������ʧ�ܣ�����ʧ�ܣ�
		/// 20: ���ִ���ʧ��
		/// 30: ȫ������ɹ������ͳɹ���
		///</summary>
		[Column(Name = "RSLT_STATUS", DbType = StdDbType.Int32)]
        public NotificationResultStatus ResultStatus
		{
			get { return this._resultStatus; }
			set { this._resultStatus = value; }
		}

		///<summary>
		/// ���ü������������ʼ������ţ���ʾ���Է��ʹ���������ϵͳ��Ϣ�������ڱ�ʾ�����û��Ķ�����
		///</summary>
		[Column(Name = "REF_COUNT", DbType = StdDbType.Int32)]
		public int RefCount
		{
			get { return this._refCount; }
			set { this._refCount = value; }
		}

		///<summary>
		/// ��������Ϣ���������ʶ��ͨ�������ʶ���Բ�ѯ�����ĸ��¼����󷢳�����Ϣ����
		/// �����Աע����Ϣ�����Լ�¼��Ա�ţ������µ���Ϣ���Լ�¼�����ţ�����֪ͨ���Լ�¼�����Ż򷢻����ţ��ȵ�
		///</summary>
		[Column(Name = "TRI_ID", DbType = StdDbType.AnsiString, Length = 30)]
		public string TriggerID
		{
			get { return this._triggerID; }
			set { this._triggerID = value; }
		}

		[Column(Name = "CREATE_TIME", DbType = StdDbType.DateTime)]
		public DateTime CreateTime
		{
			get { return this._createTime; }
			set { this._createTime = value; }
		}

		///<summary>
		/// ����ϵͳ��Ϣ��Ϊ��һ���Ķ�ʱ�䣻�����ʼ������ţ�Ϊ�ɹ�����ʱ��
		///</summary>
		[Column(Name = "CLOSE_TIME", DbType = StdDbType.DateTime)]
		public DateTime FinishTime
		{
			get { return this._finishTime; }
			set { this._finishTime = value; }
		}

		#endregion

		#region Entity Methods
		internal Notification()
		{
		}

        internal bool Create(ISession session)
		{
			return EntityManager.Create(session, this);
		}
        internal bool Update(ISession session)
		{
			return EntityManager.Update(session, this);
		}
        internal bool Update(ISession session, params string[] propertyNames2Update)
		{
			return EntityManager.Update(session, this, propertyNames2Update);
		}
        internal bool Delete(ISession session)
		{
			return EntityManager.Delete(session, this);
		}
        internal static Notification Retrieve(ISession session, int notifyID)
		{
			return EntityManager.Retrieve<Notification>(session, notifyID);
		}
        internal static bool Delete(ISession session, int notifyID)
		{
			return EntityManager.Delete<Notification>(session, notifyID);
		}
		#endregion
	}
}
