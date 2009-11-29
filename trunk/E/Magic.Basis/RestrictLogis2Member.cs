namespace Magic.Basis
{
    using Magic.Framework.Data;
    using Magic.Framework.ORM;
    using Magic.Framework.ORM.Mapping;

    /// <summary>
    /// Data Access Object for ItemColor
    /// </summary>
    [Table("cus_restrict_logis_member")]
    public partial class RestrictLogis2Member : IEntity
    {
        #region Private Fields
        private int _logisId;
        private int _memberId;
        #endregion

        #region Public Properties
        [Column(Name = "member_id", DbType = StdDbType.Int32, IsPrimary = true)]
        public int MemberId
        {
            get { return this._memberId; }
            set { this._memberId = value; }
        }

        [Column(Name = "logis_id", DbType = StdDbType.Int32, IsPrimary = true)]
        public int LogisId
        {
            get { return this._logisId; }
            set { this._logisId = value; }
        }
        #endregion

        #region Entity Methods
        public RestrictLogis2Member()
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
        public static RestrictLogis2Member Retrieve(ISession session, int logisId, int memberId)
        {
            return EntityManager.Retrieve<RestrictLogis2Member>(session, new string[] { "MemberId", "LogisId" }, new object[] { memberId, logisId });
        }
        public static int Delete(ISession session, int logisId, int memberId)
        {
            return EntityManager.Delete<RestrictLogis2Member>(session, new string[] { "MemberId", "LogisId" }, new object[] { memberId, logisId });
        }
        #endregion
    }
}
