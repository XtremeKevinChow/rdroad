//*******************************************
// ** Description:  Data Access Object for NotificationSubscriber
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
    /// Data Access Object for NotificationSubscriber
    /// 消息通知的订阅用户
    /// </summary>
    [Table("BC_NOTIFY_SUBER")]
    internal partial class NotificationSubscriber : IEntity
    {
        #region Private Fields
        private int _catID;
        private string _userID;
        private string _userName;
        private string _postCode;
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
        /// 订阅用户唯一标识
        ///</summary>
        [Column(Name = "USR_ID", DbType = StdDbType.AnsiString, Length = 30, IsPrimary = true)]
        public string UserID
        {
            get { return this._userID; }
            set { this._userID = value; }
        }

        ///<summary>
        /// 用户姓名
        ///</summary>
        [Column(Name = "USR_NAME", DbType = StdDbType.UnicodeString, Length = 30)]
        public string UserName
        {
            get { return this._userName; }
            set { this._userName = value; }
        }

        ///<summary>
        /// 邮件地址，或者手机号码，等等
        ///</summary>
        [Column(Name = "POST_CODE", DbType = StdDbType.AnsiString, Length = 50)]
        public string PostCode
        {
            get { return this._postCode; }
            set { this._postCode = value; }
        }

        #endregion

        #region Entity Methods
        public NotificationSubscriber()
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
        public static NotificationSubscriber Retrieve(ISession session, int catID, string userID)
        {
            return EntityManager.Retrieve<NotificationSubscriber>(session, new string[] { "CatID", "UserID" }, new object[] { catID, userID });
        }
        public static bool Delete(ISession session, int catID, string userID)
        {
            return EntityManager.Delete<NotificationSubscriber>(session, new string[] { "CatID", "UserID" }, new object[] { catID, userID }) > 0;
        }
        #endregion
    }
}
