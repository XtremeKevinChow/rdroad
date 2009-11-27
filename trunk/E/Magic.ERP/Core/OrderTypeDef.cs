//*******************************************
// ** Description:  Data Access Object for OrderTypeDef
// ** Author     :  Code generator
// ** Created    :   2008-6-30 20:23:03
// ** Modified   :
//*******************************************

namespace Magic.ERP.Core
{
    using Magic.Framework.Data;
	using Magic.Framework.ORM;
	using Magic.Framework.ORM.Mapping;
    using Magic.ERP;

    /// <summary>
    /// Data Access Object for OrderTypeDef
    /// �������Ͷ��壬Ŀǰ����ĵ������ͣ�
    /// PO0: ��ͨ�ɹ�����
    /// RC1: �ɹ��ջ���
    /// RC2: ��Ա�˻��ջ���
    /// RC3: ��Ա�����ջ���
    /// RC4: �����˻��ջ���
    /// SD0: ���۷�����
    /// DL2: ����������
    /// DL3: ��Ա�˻����շ�����
    /// IC0: ���ӵ�
    /// SO0: ���۶���
    /// SC0: ����̵�
    /// SA0: ������
    /// MV0: �ƿⵥ
    /// </summary>
	[Table("ORD_TYPE_DEF")]
	public partial class  OrderTypeDef : IEntity
	{
		#region Private Fields
		private string _orderTypeCode;
		private string _typeText;
        private bool _supportApprove;
		private bool _needApprove;
		private string _viewURL;
		private int _ruleDefineID;
        private int _transStepCount;
		#endregion

		#region Public Properties
		///<summary>
		/// �������ʹ���
		///</summary>
		[Column(Name = "ORD_TYPE_CODE", DbType = StdDbType.AnsiChar, Length = 3, IsPrimary = true)]
		public string OrderTypeCode
		{
			get { return this._orderTypeCode; }
			set { this._orderTypeCode = value; }
		}

		///<summary>
		/// ������������
		///</summary>
		[Column(Name = "ORD_TYPE_TEXT", DbType = StdDbType.UnicodeString, Length = 20)]
		public string TypeText
		{
			get { return this._typeText; }
			set { this._typeText = value; }
		}

        ///<summary>
        /// �õ����Ƿ�֧��ǩ��
        ///</summary>
        [Column(Name = "CAN_APRV", DbType = StdDbType.Bool)]
        public bool SupportApprove
        {
            get { return this._supportApprove; }
            set { this._supportApprove = value; }
        }

		///<summary>
		/// �����͵ĵ����Ƿ���Ҫǩ��
		///</summary>
		[Column(Name = "NEED_APRV", DbType = StdDbType.Bool)]
		public bool NeedApprove
		{
			get { return this._needApprove; }
			set { this._needApprove = value; }
		}

		///<summary>
		/// �����ַ����һ��req_num������ֵΪ���ݺ��룬��Ӧ��ҳ�渺����ʾ������ݵ���ϸ��Ϣ
		///</summary>
		[Column(Name = "VIEW_URL", DbType = StdDbType.AnsiString, Length = 70)]
		public string ViewURL
		{
			get { return this._viewURL; }
			set { this._viewURL = value; }
		}

		///<summary>
		/// ���ݺ������ɹ���
		///</summary>
		[Column(Name = "RULE_DEF_ID", DbType = StdDbType.Int32)]
		public int RuleDefineID
		{
			get { return this._ruleDefineID; }
			set { this._ruleDefineID = value; }
		}

        [Column(Name = "TRANS_STEP_CNT", DbType = StdDbType.Int32, Precision = 2)]
        public int TransStepCount
        {
            get { return this._transStepCount; }
            set { this._transStepCount = value; }
        }
		#endregion

		#region Entity Methods
		public OrderTypeDef()
		{
		}

		public bool Create(ISession session)
		{
            if (EntityManager.Create(session, this))
            {
                ERPUtil.Cache.Set(this);
                return true;
            }
            return false;
		}
		public bool Update(ISession session)
		{
            if (EntityManager.Update(session, this))
            {
                ERPUtil.Cache.Set(this);
                return true;
            }
            return false;
		}
		public bool Update(ISession session, params string[] propertyNames2Update)
		{
            if (EntityManager.Update(session, this, propertyNames2Update))
            {
                ERPUtil.Cache.Set(this);
                return true;
            }
            return false;
		}
		public bool Delete(ISession session)
		{
            if (EntityManager.Delete(session, this))
            {
                ERPUtil.Cache.Remove(this);
                return true;
            }
            return false;
		}
		public static OrderTypeDef Retrieve(ISession session, string orderTypeCode)
		{
            return ERPUtil.Cache.Get<OrderTypeDef>(orderTypeCode);
		}
		#endregion
	}
}
