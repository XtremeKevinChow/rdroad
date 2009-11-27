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
	/// 系统消息通知主记录
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
		/// 消息通知分类ID
		///</summary>
		[Column(Name = "NTF_CAT_ID", DbType = StdDbType.Int32)]
		public int CatID
		{
			get { return this._catID; }
			set { this._catID = value; }
		}

		///<summary>
		/// 消息状态:
		/// New, 10: 新建
		/// Close, 20: 已处理
		/// Cancel, 0: 取消
		///</summary>
		[Column(Name = "NTF_STATUS", DbType = StdDbType.Int32)]
        public NotificationStatus Status
		{
			get { return this._status; }
			set { this._status = value; }
		}

		///<summary>
		/// 处理结果状态
		/// 0: 未处理
		/// 10: 全部处理失败（发送失败）
		/// 20: 部分处理失败
		/// 30: 全部处理成功（发送成功）
		///</summary>
		[Column(Name = "RSLT_STATUS", DbType = StdDbType.Int32)]
        public NotificationResultStatus ResultStatus
		{
			get { return this._resultStatus; }
			set { this._resultStatus = value; }
		}

		///<summary>
		/// 引用计数器。对于邮件、短信，表示尝试发送次数；对于系统消息，可用于表示订阅用户阅读次数
		///</summary>
		[Column(Name = "REF_COUNT", DbType = StdDbType.Int32)]
		public int RefCount
		{
			get { return this._refCount; }
			set { this._refCount = value; }
		}

		///<summary>
		/// 触发该消息的主对象标识，通过这个标识可以查询到是哪个事件对象发出的消息请求
		/// 例如会员注册消息，可以记录会员号；订单下单消息可以记录订单号；发货通知可以记录订单号或发货单号，等等
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
		/// 对于系统消息，为第一次阅读时间；对于邮件、短信，为成功发送时间
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
