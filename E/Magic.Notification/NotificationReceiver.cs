//*******************************************
// ** Description:  Data Access Object for NotificationReceiver
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
    /// Data Access Object for NotificationReceiver
    /// ��Ϣ֪ͨ�Ľ�����
    /// </summary>
    [Table("BC_NOTIFY_RCVER")]
    public partial class NotificationReceiver
    {
        #region Private Fields
        private int _receiverID;
        private int _notifyID;
        private NotificationResultStatus _resultStatus;
        private string _userID;
        private string _userName;
        private string _postTo;
        private DateTime _finishTime;
        #endregion

        #region Public Properties
        [Column(Name = "NTF_RCV_ID", DbType = StdDbType.Int32, IsPrimary = true, IsSequence = true, SequenceName = "SEQ_BC_NOTIFY_RCVER")]
        public int ReceiverID
        {
            get { return this._receiverID; }
            set { this._receiverID = value; }
        }

        [Column(Name = "NTF_ID", DbType = StdDbType.Int32)]
        public int NotifyID
        {
            get { return this._notifyID; }
            set { this._notifyID = value; }
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
        /// �û�ID�������ǲ�ͬϵͳ���û�ID�����߻�ԱID���ȵ�
        ///</summary>
        [Column(Name = "USR_ID", DbType = StdDbType.AnsiString, Length = 30)]
        public string UserID
        {
            get { return this._userID; }
            set { this._userID = value; }
        }

        ///<summary>
        /// �û�����
        ///</summary>
        [Column(Name = "USR_NAME", DbType = StdDbType.UnicodeString, Length = 30)]
        public string UserName
        {
            get { return this._userName; }
            set { this._userName = value; }
        }

        ///<summary>
        /// �ʼ���ַ�������ֻ����룬�ȵ�
        ///</summary>
        [Column(Name = "POST_TO", DbType = StdDbType.AnsiString, Length = 50)]
        public string PostTo
        {
            get { return this._postTo; }
            set { this._postTo = value; }
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
        public NotificationReceiver()
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
        internal static NotificationReceiver Retrieve(ISession session, int receiverID)
        {
            return EntityManager.Retrieve<NotificationReceiver>(session, receiverID);
        }
        internal static bool Delete(ISession session, int receiverID)
        {
            return EntityManager.Delete<NotificationReceiver>(session, receiverID);
        }
        #endregion
    }
}
