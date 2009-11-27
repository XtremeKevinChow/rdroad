//*******************************************
// ** Description:  Data Access Object for WHTransferHead
// ** Author     :  Code generator
// ** Created    :   2008-7-13 21:22:51
// ** Modified   :
//*******************************************

namespace Magic.ERP.Orders
{
    using System;
    using Magic.Framework.Data;
    using Magic.Framework.ORM;
    using Magic.Framework.ORM.Mapping;

    /// <summary>
    /// Data Access Object for WHTransferHead
    /// </summary>
    [Table("ORD_MOV_HEAD")]
    public partial class WHTransferHead : IEntity
    {
        #region Private Fields
        private string _orderNumber;
        private string _orderTypeCode;
        private WHTransferStatus _status;
        private ApproveStatus _approveResult;
        private int _approveUser;
        private DateTime _approveTime;
        private string _approveNote;
        private int _createUser;
        private DateTime _createTime;
        private string _currentLineNumber;
        private string _note;
        private string _fromLocation;
        private string _toLocation;
        #endregion

        #region Public Properties
        [Column(Name = "ORD_NUM", DbType = StdDbType.AnsiString, Length = 16, IsPrimary = true)]
        public string OrderNumber
        {
            get { return this._orderNumber; }
            set { this._orderNumber = value; }
        }

        ///<summary>
        /// MV0: 待检仓移库入合格仓（这种不做）
        /// MV1: 合格仓之间移库
        /// MV2: 报废移库
        ///</summary>
        [Column(Name = "ORD_TYPE_CODE", DbType = StdDbType.AnsiChar, Length = 3)]
        public string OrderTypeCode
        {
            get { return this._orderTypeCode; }
            set { this._orderTypeCode = value; }
        }

        ///<summary>
        /// 1. New 新增状态
        ///     可以修改单据主档资料，修改添加、修改、删除明细
        /// 2. Release 发布（发布时送签处理）
        /// 3. Close 完成
        ///     签核通过，状态变为Close；签核驳回，状态回到New
        ///</summary>
        [Column(Name = "ORD_STATUS", DbType = StdDbType.Int32)]
        public WHTransferStatus Status
        {
            get { return this._status; }
            set { this._status = value; }
        }

        ///<summary>
        /// 0: UnApprove 未签核
        /// 1: Approve 通过
        /// -1: Reject 驳回
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

        [Column(Name = "FROM_LOC", DbType = StdDbType.AnsiString, Length = 8)]
        public string FromLocation
        {
            get { return this._fromLocation; }
            set { this._fromLocation = value; }
        }

        [Column(Name = "TO_LOC", DbType = StdDbType.AnsiString, Length = 8)]
        public string ToLocation
        {
            get { return this._toLocation; }
            set { this._toLocation = value; }
        }
        #endregion

        #region Entity Methods
        public WHTransferHead()
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
            if (this.Status != WHTransferStatus.New)
                throw new Exception("移库单不是新建状态，无法删除");
            int count = session.CreateEntityQuery<WHTransferLine>()
                .Where(Magic.Framework.ORM.Query.Exp.Eq("OrderNumber", this.OrderNumber))
                .Delete();
            log.DebugFormat("transfer order {0}: {1} lines deleted", this.OrderNumber, count);
            EntityManager.Delete(session, this);
            log.DebugFormat("transfer order {0}: head deleted", this.OrderNumber);
            return true;
        }
        public static WHTransferHead Retrieve(ISession session, string orderNumber)
        {
            return EntityManager.Retrieve<WHTransferHead>(session, orderNumber);
        }
        public static bool Delete(ISession session, string orderNumber)
        {
            WHTransferHead head = WHTransferHead.Retrieve(session, orderNumber);
            if (head == null) return false;
            return head.Delete(session);
        }
        #endregion
    }
}
