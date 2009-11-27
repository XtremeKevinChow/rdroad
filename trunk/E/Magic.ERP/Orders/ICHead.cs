//*******************************************
// ** Description:  Data Access Object for ICHead
// ** Author     :  Code generator
// ** Created    :   2008-6-30 20:22:44
// ** Modified   :
//*******************************************

namespace Magic.ERP.Orders
{
	using System;
	using Magic.Framework.Data;
	using Magic.Framework.ORM;
	using Magic.Framework.ORM.Mapping;

	/// <summary>
	/// Data Access Object for ICHead
	/// ���ӵ����� IC - Interchange
	/// ��������Ϊ��RC4��ORD_TYPE_DEF���ж��壩
	/// </summary>
	[Table("ORD_IC_HEAD")]
	public partial class  ICHead : IEntity
	{
		#region Private Fields
		private string _orderNumber;
        private InterchangeStatus _status;
		private int _logisticCompID;
		private string _logisticUser;
		private int _companyUser;
		private DateTime _interchangeTime;
		private string _note;
        private ApproveStatus _approveResult;
        private int _approveUser;
        private DateTime _approveTime;
        private string _approveNote;
        private int _createUser;
        private DateTime _createTime;
        private string _orderTypeCode;
        private string _currentLineNumber;
		#endregion

		#region Public Properties
		[Column(Name = "ORD_NUM", DbType = StdDbType.AnsiString, Length = 16, IsPrimary = true)]
		public string OrderNumber
		{
			get { return this._orderNumber; }
			set { this._orderNumber = value; }
		}

		///<summary>
		/// 1: ����
		/// 2: ��������ǩ��
		/// 3: ����ɣ�ǩ��ͨ����
		///</summary>
		[Column(Name = "ORD_STATUS", DbType = StdDbType.Int32)]
        public InterchangeStatus Status
		{
			get { return this._status; }
			set { this._status = value; }
		}

		///<summary>
		/// ������˾ID
		///</summary>
		[Column(Name = "LC_ID", DbType = StdDbType.Int32)]
		public int LogisticCompID
		{
			get { return this._logisticCompID; }
			set { this._logisticCompID = value; }
		}

		///<summary>
		/// ������˾����������
		///</summary>
		[Column(Name = "LC_USR", DbType = StdDbType.UnicodeString, Length = 12)]
		public string LogisticUser
		{
			get { return this._logisticUser; }
			set { this._logisticUser = value; }
		}

		///<summary>
		/// �ֿ⽻���û�ID���������ݵ��û���
		///</summary>
		[Column(Name = "CMP_USR", DbType = StdDbType.Int32)]
		public int CompanyUser
		{
			get { return this._companyUser; }
			set { this._companyUser = value; }
		}

		///<summary>
		/// ����ʱ��
		///</summary>
		[Column(Name = "IC_TIME", DbType = StdDbType.DateTime)]
		public DateTime InterchangeTime
		{
			get { return this._interchangeTime; }
			set { this._interchangeTime = value; }
		}

		///<summary>
		/// ��ע
		///</summary>
		[Column(Name = "ORD_NOTE", DbType = StdDbType.UnicodeString, Length = 50)]
		public string Note
		{
			get { return this._note; }
			set { this._note = value; }
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

        [Column(Name = "ORD_TYPE_CODE", DbType = StdDbType.AnsiChar, Length = 3)]
        public string OrderTypeCode
        {
            get { return this._orderTypeCode; }
            set { this._orderTypeCode = value; }
        }

        ///<summary>
        /// �к���0010, 0020, 0030�����ķ�ʽ���룬CurrentLineNumber�洢��Ϊ��ǰ����к�ֵ�������һ���к�ʱ�����ֵת��Ϊ������10��ת��Ϊ4λ����
        ///</summary>
        [Column(Name = "CUR_LINE_NUM", DbType = StdDbType.AnsiString, Length = 10)]
        public string CurrentLineNumber
        {
            get { return this._currentLineNumber; }
            set { this._currentLineNumber = value; }
        }
		#endregion

		#region Entity Methods
		public ICHead()
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
            session.CreateEntityQuery<ICLine>()
                .Where(Magic.Framework.ORM.Query.Exp.Eq("OrderNumber", this._orderNumber))
                .Delete();
			EntityManager.Delete(session, this);
            return true;
		}
		public static ICHead Retrieve(ISession session, string orderNumber)
		{
			return EntityManager.Retrieve<ICHead>(session, orderNumber);
		}
		public static bool Delete(ISession session, string orderNumber)
		{
            ICHead head = ICHead.Retrieve(session, orderNumber);
            if (head == null) return false;
            return head.Delete(session);
		}
		#endregion
	}
}
