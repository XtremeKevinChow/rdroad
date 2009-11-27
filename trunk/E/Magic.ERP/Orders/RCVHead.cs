//*******************************************
// ** Description:  Data Access Object for RCVHead
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
	/// Data Access Object for RCVHead
	/// �ջ��� RCV - Receive
	/// �����ɹ��ջ�����Ա�˻��ջ�����Ա�����ջ���������������˾�˻���
	/// �ɹ��ջ���������Ϊ��RC1��ORD_TYPE_DEF���ж��壩
	/// ��Ա�˻��ջ���������Ϊ��RC2
	/// ��Ա�����ջ���������Ϊ��RC3
	/// </summary>
	[Table("ORD_RCV_HEAD")]
	public partial class  RCVHead : IEntity
	{
		#region Private Fields
		private string _orderNumber;
		private string _orderTypeCode;
        private string _locationCode;
		private int _objectID;
		private string _refOrderType;
		private string _refOrderNumber;
		private string _originalOrderType;
		private string _orginalOrderNumber;
        private ReceiveStatus _status;
        private ApproveStatus _approveResult;
		private int _approveUser;
		private DateTime _approveTime;
		private string _approveNote;
		private int _createUser;
		private DateTime _createTime;
		private string _currentLineNumber;
		private int _reasonID;
		private string _note;
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

		[Column(Name = "OBJ_ID", DbType = StdDbType.Int32)]
		public int ObjectID
		{
			get { return this._objectID; }
			set { this._objectID = value; }
		}

        [Column(Name = "LOC_CODE", DbType = StdDbType.AnsiString, Length = 8)]
        public string LocationCode
        {
            get { return this._locationCode; }
            set { this._locationCode = value; }
        }

		///<summary>
		/// ���õ������ͣ�
		/// ���Ϊ�ɹ������ջ������õ�������ΪPO0�����òɹ�������
		/// ���Ϊ��Ա�˻��ջ������õ�������ΪSD0�����۷�������
		/// ���Ϊ��Ա�����ջ������õ�������ΪSD0�����۷�������
		///</summary>
		[Column(Name = "REF_ORD_TYPE", DbType = StdDbType.AnsiChar, Length = 3)]
		public string RefOrderType
		{
			get { return this._refOrderType; }
			set { this._refOrderType = value; }
		}

		///<summary>
		/// ���õ��ݺ���
		/// ���Ϊ�ɹ������ջ������õ��ݺ���Ϊ�ɹ�������
		/// ���Ϊ��Ա���������˻������õ��ݺ���Ϊ���۷�������
		///</summary>
		[Column(Name = "REF_ORD_NUM", DbType = StdDbType.AnsiString, Length = 16)]
		public string RefOrderNumber
		{
			get { return this._refOrderNumber; }
			set { this._refOrderNumber = value; }
		}

		///<summary>
		/// ԭʼ�������ͣ�
		/// ���Ϊ�ɹ������ջ���ԭʼ��������ΪPO0���ɹ�������
		/// ���Ϊ��Ա�˻��ջ���ԭʼ��������ΪSD0�����۶�����
		/// ���Ϊ��Ա�����ջ���ԭʼ��������ΪSD0�����۶�����
		///</summary>
		[Column(Name = "ORG_ODR_TYPE", DbType = StdDbType.AnsiChar, Length = 3)]
		public string OriginalOrderType
		{
			get { return this._originalOrderType; }
			set { this._originalOrderType = value; }
		}

		///<summary>
		/// ԭʼ���ݺ���
		/// ���Ϊ�ɹ������ջ���ԭʼ���ݺ���Ϊ�ɹ���������
		/// ���Ϊ��Ա���������˻���ԭʼ���ݺ���Ϊ���۶�������
		///</summary>
		[Column(Name = "ORG_ORD_NUM", DbType = StdDbType.AnsiString, Length = 16)]
		public string OrginalOrderNumber
		{
			get { return this._orginalOrderNumber; }
			set { this._orginalOrderNumber = value; }
		}

		[Column(Name = "ORD_STATUS", DbType = StdDbType.Int32)]
        public ReceiveStatus Status
		{
			get { return this._status; }
			set { this._status = value; }
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
		/// �к���0010, 0020, 0030�����ķ�ʽ���룬CurrentLineNumber�洢��Ϊ��ǰ����к�ֵ�������һ���к�ʱ�����ֵת��Ϊ������10��ת��Ϊ4λ����
		///</summary>
		[Column(Name = "CUR_LINE_NUM", DbType = StdDbType.AnsiString, Length = 10)]
		public string CurrentLineNumber
		{
			get { return this._currentLineNumber; }
			set { this._currentLineNumber = value; }
		}

		[Column(Name = "RSON_ID", DbType = StdDbType.Int32)]
		public int ReasonID
		{
			get { return this._reasonID; }
			set { this._reasonID = value; }
		}

		[Column(Name = "ORD_NOTE", DbType = StdDbType.UnicodeString, Length = 50)]
		public string Note
		{
			get { return this._note; }
			set { this._note = value; }
		}

		#endregion

		#region Entity Methods
		public RCVHead()
		{
            this.Status = ReceiveStatus.New;
            this.ApproveResult = ApproveStatus.UnApprove;
            this.ApproveTime = new DateTime(1900, 1, 1);
            this.ApproveUser = 0;
            this.ApproveNote = " ";
            this.CreateUser = 0;
            this.CreateTime = DateTime.Now;
            this.CurrentLineNumber = "0000";
            this.ReasonID = 0;
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
			this.DeleteLines(session);
            EntityManager.Delete(session, this);
            return true;
		}
		public static RCVHead Retrieve(ISession session, string orderNumber)
		{
			return EntityManager.Retrieve<RCVHead>(session, orderNumber);
		}
		public static bool Delete(ISession session, string orderNumber)
		{
            RCVHead head = RCVHead.Retrieve(session, orderNumber);
            return head.Delete(session);
		}
		#endregion
	}
}