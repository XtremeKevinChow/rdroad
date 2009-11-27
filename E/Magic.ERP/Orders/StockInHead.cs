//*******************************************
// ** Description:  Data Access Object for StockInHead
// ** Author     :  Code generator
// ** Created    :   2008-7-26 16:48:01
// ** Modified   :
//*******************************************

namespace Magic.ERP.Orders
{
    using System;
    using Magic.Framework.Data;
    using Magic.Framework.ORM;
    using Magic.Framework.ORM.Mapping;

    /// <summary>
    /// Data Access Object for StockInHead
    /// </summary>
    [Table("ORD_STO_IN_HEAD")]
    public partial class StockInHead : IEntity
    {
        #region Private Fields
        private string _orderNumber;
        private string _orderTypeCode;
        private StockInStatus _status;
        private string _locationCode;
        private ApproveStatus _approveResult;
        private int _approveUser;
        private DateTime _approveTime;
        private string _approveNote;
        private int _createUser;
        private DateTime _createTime;
        private string _responsible;
        private string _currentLineNumber;
        private string _note;
        private bool _preLock;
        private string _refOrdNum;
        #endregion

        #region Public Properties
        [Column(Name = "ORD_NUM", DbType = StdDbType.AnsiString, Length = 16, IsPrimary = true)]
        public string OrderNumber
        {
            get { return this._orderNumber; }
            set { this._orderNumber = value; }
        }

        ///<summary>
        /// 单据类型代码
        /// RC
        ///</summary>
        [Column(Name = "ORD_TYPE_CODE", DbType = StdDbType.AnsiChar, Length = 3)]
        public string OrderTypeCode
        {
            get { return this._orderTypeCode; }
            set { this._orderTypeCode = value; }
        }

        ///<summary>
        /// 1. New 新建
        /// 2. Release 发布
        /// 3. Open 
        /// 4. Close 关闭
        ///</summary>
        [Column(Name = "ORD_STATUS", DbType = StdDbType.Int32)]
        public StockInStatus Status
        {
            get { return this._status; }
            set { this._status = value; }
        }

        [Column(Name = "LOC_CODE", DbType = StdDbType.AnsiString, Length = 8)]
        public string LocationCode
        {
            get { return this._locationCode; }
            set { this._locationCode = value; }
        }

        ///<summary>
        /// 0: 未签核
        /// 1: 签核通过
        /// -1: 签核驳回
        ///</summary>
        [Column(Name = "APRV_RSLT", DbType = StdDbType.Int32, Precision = 1)]
        public ApproveStatus ApproveResult
        {
            get { return this._approveResult; }
            set { this._approveResult = value; }
        }

        ///<summary>
        /// 签核用户，签核完毕后该字段会自动设置，不需要维护
        ///</summary>
        [Column(Name = "APRV_USR", DbType = StdDbType.Int32)]
        public int ApproveUser
        {
            get { return this._approveUser; }
            set { this._approveUser = value; }
        }

        ///<summary>
        /// 签核时间，签核完毕后该字段会自动设置，不需要维护
        ///</summary>
        [Column(Name = "APRV_TIME", DbType = StdDbType.DateTime)]
        public DateTime ApproveTime
        {
            get { return this._approveTime; }
            set { this._approveTime = value; }
        }

        [Column(Name = "APRV_NOTE", DbType = StdDbType.UnicodeString, Length = 40)]
        public string ApproveNote
        {
            get { return this._approveNote; }
            set { this._approveNote = value; }
        }

        [Column(Name = "CREATE_USR", DbType = StdDbType.Int32)]
        public int CreateUser
        {
            get { return this._createUser; }
            set { this._createUser = value; }
        }

        [Column(Name = "CREATE_TIME", DbType = StdDbType.DateTime)]
        public DateTime CreateTime
        {
            get { return this._createTime; }
            set { this._createTime = value; }
        }

        ///<summary>
        /// 责任人
        ///</summary>
        [Column(Name = "RSPON_USR", DbType = StdDbType.UnicodeString, Length = 20)]
        public string Responsible
        {
            get { return this._responsible; }
            set { this._responsible = value; }
        }

        ///<summary>
        /// 行号以0010, 0020, 0030这样的方式编码，CurrentLineNumber存储的为当前最大行号值，添加下一个行号时将这个值转换为整数加10再转换为4位数字
        ///</summary>
        [Column(Name = "CUR_LINE_NUM", DbType = StdDbType.AnsiString, Length = 10)]
        public string CurrentLineNumber
        {
            get { return this._currentLineNumber; }
            set { this._currentLineNumber = value; }
        }

        [Column(Name = "ORD_NOTE", DbType = StdDbType.UnicodeString, Length = 50)]
        public string Note
        {
            get { return this._note; }
            set { this._note = value; }
        }

        public bool PreLockStock
        {
            get { return false; }
            set { }
        }

        [Column(Name = "REF_ORD_NUM", DbType = StdDbType.AnsiString, Length=18)]
        public string RefOrdNum
        {
            get { return this._refOrdNum; }
            set { this._refOrdNum = value; }
        }
        #endregion

        #region Entity Methods
        public StockInHead()
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
            if (this.Status != StockInStatus.New)
                throw new Exception("单据不是新建状态，无法删除");
            EntityManager.Delete(session, this);
            session.CreateEntityQuery<StockInLine>()
                .Where(Magic.Framework.ORM.Query.Exp.Eq("OrderNumber", this.OrderNumber))
                .Delete();
            return true;
        }
        public static StockInHead Retrieve(ISession session, string orderNumber)
        {
            return EntityManager.Retrieve<StockInHead>(session, orderNumber);
        }
        public static bool Delete(ISession session, string orderNumber)
        {
            StockInHead head = StockInHead.Retrieve(session, orderNumber);
            if (head == null) return false;
            return head.Delete(session);
        }
        #endregion
    }
}
