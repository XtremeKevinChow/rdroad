//*******************************************
// ** Description:  Data Access Object for DeliverHead
// ** Author     :  Code generator
// ** Created    :   2008-7-9 1:21:51
// ** Modified   :
//*******************************************

namespace Magic.ERP.Orders
{
    using System;
    using Magic.Framework.Data;
    using Magic.Framework.ORM;
    using Magic.Framework.ORM.Mapping;

    /// <summary>
    /// Data Access Object for DeliverHead
    /// ����������
    /// </summary>
    [Table("ORD_DLV_HEAD")]
    public partial class DeliverHead : IEntity
    {
        #region Private Fields
        private string _orderNumber;
        private string _orderTypeCode;
        private int _customerID;
        private DeliverStatus _status;
        private decimal _agentAmt;
        private string _refOrderNumber;
        private string _orginalOrderNumber;
        private int _createUser;
        private DateTime _createTime;
        private ApproveStatus _approveResult;
        private int _approveUser;
        private DateTime _approveTime;
        private string _approveNote;
        private string _currentLineNumber;
        private string _note;
        private string _greetingWord;
        private int _packageType;
        private bool _interchanged;
        private string _interchangeNumber;
        private DateTime _deliverDate;
        #endregion

        #region Public Properties
        [Column(Name = "ORD_NUM", DbType = StdDbType.AnsiString, Length = 16, IsPrimary = true)]
        public string OrderNumber
        {
            get { return this._orderNumber; }
            set { this._orderNumber = value; }
        }

        ///<summary>
        /// �������ʹ���
        ///</summary>
        [Column(Name = "ORD_TYPE_CODE", DbType = StdDbType.AnsiChar, Length = 3)]
        public string OrderTypeCode
        {
            get { return this._orderTypeCode; }
            set { this._orderTypeCode = value; }
        }

        [Column(Name = "CUS_ID", DbType = StdDbType.Int32)]
        public int CustomerID
        {
            get { return this._customerID; }
            set { this._customerID = value; }
        }

        ///<summary>
        /// 1: ����
        /// 2: ��������ǩ��
        /// 3: ����ɣ�ǩ��ͨ����
        ///</summary>
        [Column(Name = "ORD_STATUS", DbType = StdDbType.Int32)]
        public DeliverStatus Status
        {
            get { return this._status; }
            set { this._status = value; }
        }

        ///<summary>
        /// ���տ��ܽ��
        ///</summary>
        [Column(Name = "AGENT_AMT", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
        public decimal AgentAmt
        {
            get { return this._agentAmt; }
            set { this._agentAmt = value; }
        }

        ///<summary>
        /// ���õ��ݺ��룺�������۷���������Ϊ���۶������룬�Ի�������������Ϊ�����ջ�������
        ///</summary>
        [Column(Name = "REF_ORD_NUM", DbType = StdDbType.AnsiString, Length = 16)]
        public string RefOrderNumber
        {
            get { return this._refOrderNumber; }
            set { this._refOrderNumber = value; }
        }

        ///<summary>
        /// ԭʼ���ݺ��룺�����۷����ͻ������������Ծ�Ϊ���۶�������
        ///</summary>
        [Column(Name = "ORG_ORD_NUM", DbType = StdDbType.AnsiString, Length = 16)]
        public string OrginalOrderNumber
        {
            get { return this._orginalOrderNumber; }
            set { this._orginalOrderNumber = value; }
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
        /// 0: δǩ��
        /// 1: ǩ��ͨ��
        /// -1: ǩ�˲���
        ///</summary>
        [Column(Name = "APRV_RSLT", DbType = StdDbType.Int32, Precision = 1)]
        public ApproveStatus ApproveResult
        {
            get { return this._approveResult; }
            set { this._approveResult = value; }
        }

        ///<summary>
        /// ǩ���û���ǩ����Ϻ���ֶλ��Զ����ã�����Ҫά��
        ///</summary>
        [Column(Name = "APRV_USR", DbType = StdDbType.Int32)]
        public int ApproveUser
        {
            get { return this._approveUser; }
            set { this._approveUser = value; }
        }

        ///<summary>
        /// ǩ��ʱ�䣬ǩ����Ϻ���ֶλ��Զ����ã�����Ҫά��
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

        ///<summary>
        /// �к���0010, 0020, 0030�����ķ�ʽ���룬CurrentLineNumber�洢��Ϊ��ǰ����к�ֵ�������һ���к�ʱ�����ֵת��Ϊ������10��ת��Ϊ4λ����
        ///</summary>
        [Column(Name = "CUR_LINE_NUM", DbType = StdDbType.AnsiString, Length = 4)]
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

        ///<summary>
        /// �����ؿ���ף����
        ///</summary>
        [Column(Name = "GRT_WORD", DbType = StdDbType.AnsiString, Length = 100)]
        public string GreetingWord
        {
            get { return this._greetingWord; }
            set { this._greetingWord = value; }
        }

        ///<summary>
        /// ��Ʒ��װ����ͨ��װ�Ȱ�װ��ʽ
        ///</summary>
        [Column(Name = "PACK_TYPE", DbType = StdDbType.Int32)]
        public int PackageType
        {
            get { return this._packageType; }
            set { this._packageType = value; }
        }

        ///<summary>
        /// �Ƿ��Ѿ����������ӵ�
        ///</summary>
        [Column(Name = "FINISH_INT_CHG", DbType = StdDbType.Bool)]
        public bool Interchanged
        {
            get { return this._interchanged; }
            set { this._interchanged = value; }
        }

        ///<summary>
        /// �����Ľ��ӵ�����
        ///</summary>
        [Column(Name = "ORD_IC_NUM", DbType = StdDbType.AnsiString, Length = 16)]
        public string InterchangeNumber
        {
            get { return this._interchangeNumber; }
            set { this._interchangeNumber = value; }
        }

        ///<summary>
        /// ��������
        ///</summary>
        [Column(Name = "DLV_DATE", DbType = StdDbType.DateTime)]
        public DateTime DeliverDate
        {
            get { return this._deliverDate; }
            set { this._deliverDate = value; }
        }

        #endregion

        #region Entity Methods
        public DeliverHead()
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
        #endregion
    }
}