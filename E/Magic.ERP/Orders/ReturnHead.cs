//*******************************************
// ** Description:  Data Access Object for ReturnHead
// ** Author     :  Code generator
// ** Created    :   2008-8-25 20:29:49
// ** Modified   :
//*******************************************

namespace Magic.ERP.Orders
{
	using System;
	using Magic.Framework.Data;
	using Magic.Framework.ORM;
	using Magic.Framework.ORM.Mapping;

	/// <summary>
	/// Data Access Object for ReturnHead
	/// ��Ա�˻�������
	/// </summary>
	[Table("ORD_RTN_HEAD")]
	public partial class  ReturnHead : IEntity
	{
		#region Private Fields
		private string _orderNumber;
		private string _locationCode;
		private string _orderTypeCode;
		private int _memberID;
		private string _memberName;
        private int _logisticsID;
        private string _logisticsName;
		private ReturnStatus _status;
		private bool _isAutoMatch;
		private bool _isMalicious;
        private bool _hasScaned;
        private bool _hasTransported;
		private int _refOrderID;
		private string _refOrderNumber;
		private string _orginalOrderNumber;
		private int _reasonID;
		private string _reasonText;
		private ApproveStatus _approveResult;
		private int _approveUser;
		private DateTime _approveTime;
		private string _approveNote;
		private int _createUser;
		private DateTime _createTime;
		private string _note;
		private string _currentLineNumber;
        private string _csUser;
		#endregion

		#region Public Properties
		[Column(Name = "ORD_NUM", DbType = StdDbType.AnsiString, Length = 16, IsPrimary = true)]
		public string OrderNumber
		{
			get { return this._orderNumber; }
			set { this._orderNumber = value; }
		}

		[Column(Name = "LOC_CODE", DbType = StdDbType.AnsiString, Length = 8)]
		public string LocationCode
		{
			get { return this._locationCode; }
			set { this._locationCode = value; }
		}

		///<summary>
		/// �������ʹ���
		/// ��Ա�˻�: RC2�������˻�: RC4���ڲ��˻�: RC5
		/// ��Ա����: RC3
		///</summary>
		[Column(Name = "ORD_TYPE_CODE", DbType = StdDbType.AnsiChar, Length = 3)]
		public string OrderTypeCode
		{
			get { return this._orderTypeCode; }
			set { this._orderTypeCode = value; }
		}

		///<summary>
		/// ��ԱID
		///</summary>
		[Column(Name = "MBR_ID", DbType = StdDbType.Int32)]
		public int MemberID
		{
			get { return this._memberID; }
			set { this._memberID = value; }
		}

		///<summary>
		/// ��Ա����
		///</summary>
		[Column(Name = "MBR_NAME", DbType = StdDbType.UnicodeString, Length = 20)]
		public string MemberName
		{
			get { return this._memberName; }
			set { this._memberName = value; }
		}

        ///<summary>
        /// ������˾ID
        ///</summary>
        [Column(Name = "LOGIS_ID", DbType = StdDbType.Int32)]
        public int LogisticsID
        {
            get { return this._logisticsID; }
            set { this._logisticsID = value; }
        }

        ///<summary>
        /// ������˾����
        ///</summary>
        [Column(Name = "LOGIS_NAME", DbType = StdDbType.UnicodeString, Length = 20)]
        public string LogisticsName
        {
            get { return this._logisticsName; }
            set { this._logisticsName = value; }
        }

		[Column(Name = "ORD_STATUS", DbType = StdDbType.Int32)]
        public ReturnStatus Status
		{
			get { return this._status; }
			set { this._status = value; }
		}

		///<summary>
		/// �˻���ϸ�����Ӧ����������ϸ
		/// ɨ���˻���Ʒʱ�����ͬһ����Ʒ�ڷ������д���2��������ϸ��Ŀ��������Ա�ʾ��ϵͳ�Զ�ƥ�䷢����ϸ���������û��ֹ�ѡ��
		///</summary>
		[Column(Name = "IS_AUTO_MATCH", DbType = StdDbType.Bool)]
		public bool IsAutoMatch
		{
			get { return this._isAutoMatch; }
			set { this._isAutoMatch = value; }
		}

		///<summary>
		/// �Ƿ�����˻�
		///</summary>
		[Column(Name = "IS_MAL", DbType = StdDbType.Bool)]
		public bool IsMalicious
		{
			get { return this._isMalicious; }
			set { this._isMalicious = value; }
		}

        ///<summary>
        /// �Ƿ��Ѿ�ɨ����˻���Ʒ�����Ի����˻���������
        ///</summary>
        [Column(Name = "HAS_SCANED", DbType = StdDbType.Bool)]
        public bool HasScaned
        {
            get { return this._hasScaned; }
            set { this._hasScaned = value; }
        }

        ///<summary>
        /// ������˾�Ƿ��з��ˣ�û�з��˲���Ҫ�����˷ѣ��������乫˾�������õ���˾����Ŀ�ĵ��޷����ͣ�
        ///</summary>
        [Column(Name = "HAS_TRANSED", DbType = StdDbType.Bool)]
        public bool HasTransported
        {
            get { return this._hasTransported; }
            set { this._hasTransported = value; }
        }

		///<summary>
		/// ������ID
		///</summary>
		[Column(Name = "REF_ORD_ID", DbType = StdDbType.Int32)]
		public int RefOrderID
		{
			get { return this._refOrderID; }
			set { this._refOrderID = value; }
		}

		///<summary>
		/// ���õ��ݺ��루���������룩
		///</summary>
		[Column(Name = "REF_ORD_NUM", DbType = StdDbType.AnsiString, Length = 16)]
		public string RefOrderNumber
		{
			get { return this._refOrderNumber; }
			set { this._refOrderNumber = value; }
		}

		///<summary>
		/// ԭʼ���ݺ��루�������룩
		///</summary>
		[Column(Name = "ORG_ORD_NUM", DbType = StdDbType.AnsiString, Length = 16)]
		public string OrginalOrderNumber
		{
			get { return this._orginalOrderNumber; }
			set { this._orginalOrderNumber = value; }
		}

        private string _exchangeOrder;
        ///<summary>
        /// �����˻�ʱ��¼������������
        ///</summary>
        [Column(Name = "EXCHANGE_ORDER", DbType = StdDbType.AnsiString, Length = 16)]
        public string ExchangeOrder
        {
            get { return this._exchangeOrder; }
            set { this._exchangeOrder = value; }
        }

		///<summary>
		/// �˻�ԭ��ID
		///</summary>
		[Column(Name = "RSON_ID", DbType = StdDbType.Int32)]
		public int ReasonID
		{
			get { return this._reasonID; }
			set { this._reasonID = value; }
		}

		///<summary>
		/// �˻�ԭ������
		///</summary>
		[Column(Name = "RSON_TEXT", DbType = StdDbType.UnicodeString, Length = 50)]
		public string ReasonText
		{
			get { return this._reasonText; }
			set { this._reasonText = value; }
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
		/// �к���0010, 0020, 0030�����ķ�ʽ���룬CurrentLineNumber�洢��Ϊ��ǰ����к�ֵ�������һ���к�ʱ�����ֵת��Ϊ������10��ת��Ϊ4λ����
		///</summary>
		[Column(Name = "CUR_LINE_NUM", DbType = StdDbType.AnsiString, Length = 10)]
		public string CurrentLineNumber
		{
			get { return this._currentLineNumber; }
			set { this._currentLineNumber = value; }
		}

        ///<summary>
        /// �ͷ���Ա
        ///</summary>
        [Column(Name = "CS_USER", DbType = StdDbType.UnicodeString, Length = 30)]
        public string CSUser
        {
            get { return this._csUser; }
            set { this._csUser = value; }
        }
		#endregion

		#region Entity Methods
		public ReturnHead()
		{
            this.Status = ReturnStatus.New;
            this.ApproveResult = ApproveStatus.UnApprove;
            this.ApproveUser = 0;
            this.ApproveTime = new DateTime(1900, 1, 1);
            this.ApproveNote = " ";
            this.CreateUser = 0;
            this.CreateTime = DateTime.Now;
            this.IsAutoMatch = false;
            this.HasTransported = true;

            this.MemberID = 0;
            this.MemberName = " ";
            this.LogisticsID = 0;
            this.LogisticsName = " ";
            this._currentLineNumber = "0000";
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
            if (this.Status != ReturnStatus.New) throw new Exception("�˻��������½�״̬���޷�ɾ��");
            if (this.OrderTypeCode == ReturnHead.ORDER_TYPE_EXCHANGE_RTN)
                throw new Exception("����ɾ�������˻���"); ;
			return EntityManager.Delete(session, this);
		}
		public static ReturnHead Retrieve(ISession session, string orderNumber)
		{
			return EntityManager.Retrieve<ReturnHead>(session, orderNumber);
		}
		public static bool Delete(ISession session, string orderNumber)
		{
            if (string.IsNullOrEmpty(orderNumber) || orderNumber.Trim().Length <= 0) return false;
            ReturnHead head = Retrieve(session, orderNumber);
            if (head == null) return false;
            return head.Delete(session);
		}
		#endregion
	}
}