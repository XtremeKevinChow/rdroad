//*******************************************
// ** Description:  Data Access Object for LogisticArea
// ** Author     :  Code generator
// ** Created    :   2008-8-31 10:27:26
// ** Modified   :
//*******************************************

namespace Magic.Basis
{
    using System;
    using Magic.Framework.Data;
    using Magic.Framework.ORM;
    using Magic.Framework.ORM.Mapping;

    /// <summary>
    /// Data Access Object for LogisticArea
    /// </summary>
    [Table("BAS_LOGIS_AREA")]
    public partial class LogisticArea : IEntity
    {
        #region Private Fields
        private int _iD;
        private int _logisticCompID;
        private string _pID;
        private string _pName;
        private string _cID;
        private string _cName;
        private string _dID;
        private string _dName;
        private string _serviceArea;
        private string _unavailabelArea;
        private string _note;
        private bool _hasError;
        private string _errorMessage;
        #endregion

        #region Public Properties
        [Column(Name = "LCA_ID", DbType = StdDbType.Int32, IsPrimary = true, IsSequence = true, SequenceName = "SEQ_BAS_LOGIS_AREA")]
        public int ID
        {
            get { return this._iD; }
            set { this._iD = value; }
        }

        [Column(Name = "LC_ID", DbType = StdDbType.Int32)]
        public int LogisticCompID
        {
            get { return this._logisticCompID; }
            set { this._logisticCompID = value; }
        }

        ///<summary>
        /// Province ID
        ///</summary>
        [Column(Name = "P_ID", DbType = StdDbType.AnsiString, Length = 20)]
        public string PID
        {
            get { return this._pID; }
            set { this._pID = value; }
        }

        ///<summary>
        /// Province Name
        ///</summary>
        [Column(Name = "P_NAME", DbType = StdDbType.UnicodeString, Length = 20)]
        public string PName
        {
            get { return this._pName; }
            set { this._pName = value; }
        }

        ///<summary>
        /// City ID
        ///</summary>
        [Column(Name = "C_ID", DbType = StdDbType.AnsiString, Length = 20)]
        public string CID
        {
            get { return this._cID; }
            set { this._cID = value; }
        }

        [Column(Name = "C_NAME", DbType = StdDbType.UnicodeString, Length = 20)]
        public string CName
        {
            get { return this._cName; }
            set { this._cName = value; }
        }

        ///<summary>
        /// District ID
        ///</summary>
        [Column(Name = "D_ID", DbType = StdDbType.AnsiString, Length = 20)]
        public string DID
        {
            get { return this._dID; }
            set { this._dID = value; }
        }

        ///<summary>
        /// District Name
        ///</summary>
        [Column(Name = "D_NAME", DbType = StdDbType.UnicodeString, Length = 20)]
        public string DName
        {
            get { return this._dName; }
            set { this._dName = value; }
        }

        ///<summary>
        /// 可服务区域备注
        ///</summary>
        [Column(Name = "SVC_AREA", DbType = StdDbType.UnicodeString, Length = 300)]
        public string ServiceArea
        {
            get { return this._serviceArea; }
            set { this._serviceArea = value; }
        }

        ///<summary>
        /// 不可服务区域备注
        ///</summary>
        [Column(Name = "UN_SVC_AREA", DbType = StdDbType.UnicodeString, Length = 300)]
        public string UnavailabelArea
        {
            get { return this._unavailabelArea; }
            set { this._unavailabelArea = value; }
        }

        ///<summary>
        /// 额外备注
        ///</summary>
        [Column(Name = "LCA_NOTE", DbType = StdDbType.UnicodeString, Length = 300)]
        public string Note
        {
            get { return this._note; }
            set { this._note = value; }
        }

        ///<summary>
        /// 数据导入时是否发生错误
        ///</summary>
        [Column(Name = "HAS_ERR", DbType = StdDbType.Bool)]
        public bool HasError
        {
            get { return this._hasError; }
            set { this._hasError = value; }
        }

        ///<summary>
        /// 数据导入时的错误描述
        ///</summary>
        [Column(Name = "ERR_MSG", DbType = StdDbType.UnicodeString, Length = 100)]
        public string ErrorMessage
        {
            get { return this._errorMessage; }
            set { this._errorMessage = value; }
        }
        #endregion

        #region Entity Methods
        public LogisticArea()
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
        public static LogisticArea Retrieve(ISession session, int iD)
        {
            return EntityManager.Retrieve<LogisticArea>(session, iD);
        }
        public static bool Delete(ISession session, int iD)
        {
            return EntityManager.Delete<LogisticArea>(session, iD);
        }
        #endregion
    }
}
