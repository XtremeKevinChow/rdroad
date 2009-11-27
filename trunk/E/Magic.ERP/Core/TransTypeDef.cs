//*******************************************
// ** Description:  Data Access Object for TransTypeDef
// ** Author     :  Code generator
// ** Created    :   2008-7-4 23:31:56
// ** Modified   :
//*******************************************

namespace Magic.ERP.Core
{
    using Magic.Framework.Data;
	using Magic.Framework.ORM;
	using Magic.Framework.ORM.Mapping;
    using Magic.ERP;
    using Magic.Framework.Utils;

	/// <summary>
	/// Data Access Object for TransTypeDef
	/// �������Ͷ��壬Ŀǰ����Ľ������ͣ�
	/// 101: �ɹ��ջ�   ��ϸ��
	/// 102: ����101
	/// 103: �ɹ��ջ�   ������
	/// 104: ����103
	/// 
	/// 111: ��Ա�˻��ջ�   �ϸ񲿷� �ջ���ϸ��
	/// 112: ����111
	/// 113: ��Ա�˻��ջ�   ���ϸ񲿷� �ջ���ϵͳ������
	/// 114: ����113
	/// 115: ��Ա�����ջ�   �ϸ񲿷� �ջ���ϸ��
	/// 116: ����115
	/// 117: ��Ա�����ջ�   ���ϸ񲿷� �ջ���ϵͳ������
	/// 118: ����117
	/// 
	/// 131: �����˻��ջ�   �ϸ񲿷� �ջ���ϸ��
	/// 132: ����131
	/// 133: �����˻��ջ�   ���ϸ񲿷� �ջ���ϵͳ������
	/// 134: ����133
	/// 
	/// 201: ���۷���
	/// 202: ����201
	/// 203: ��Ա��������
	/// 204: ����203
	/// 205: �˻����շ���
	/// 206:
	/// 207: �������շ���
	/// 208:
	/// 
	/// 301: ����̵�
	/// 302: ����301
	/// 303: ������
	/// 304:
	/// </summary>
	[Table("INV_TRANS_TYPE_DEF")]
	public partial class  TransTypeDef : IEntity
	{
		#region Private Fields
		private string _transTypeCode;
        private TransTypePriceSource _priceSourceType;
		private bool _isCostTrans;
        private TransProperty _transProperty;
		private string _transDefText;
		private string _transDefDesc;
		#endregion

		#region Public Properties
		///<summary>
		/// ���״���
		///</summary>
		[Column(Name = "TRANS_TYPE_CODE", DbType = StdDbType.AnsiChar, Length = 3, IsPrimary = true)]
		public string TransTypeCode
		{
			get { return this._transTypeCode; }
			set { this._transTypeCode = value; }
		}

		///<summary>
		/// �۸���Դ���ͣ�����ɱ�ʱʹ�ã�
		/// 1: �����õ��ݻ�ȡ
		/// 2: �ӹ������׻�ȡ
		/// 3: ȡ�ƶ�ƽ����
		/// 4: ����Area�����û�ȡ
		///</summary>
		[Column(Name = "PRICE_SRC_TYPE", DbType = StdDbType.Int32)]
        public TransTypePriceSource PriceSourceType
		{
			get { return this._priceSourceType; }
			set { this._priceSourceType = value; }
		}

		///<summary>
		/// �ý��������Ƿ���Ҫ����ɱ�
		///</summary>
		[Column(Name = "IS_COST_TRANS", DbType = StdDbType.Bool)]
		public bool IsCostTrans
		{
			get { return this._isCostTrans; }
			set { this._isCostTrans = value; }
		}

        [Column(Name = "TRANS_PROP", DbType = StdDbType.Int32)]
        public TransProperty TransProperty
        {
            get { return this._transProperty; }
            set { this._transProperty = value; }
        }

		///<summary>
		/// ����������Ҫ����
		///</summary>
		[Column(Name = "TRANS_TYPE_TEXT", DbType = StdDbType.UnicodeString, Length = 12)]
		public string TransDefText
		{
			get { return this._transDefText; }
			set { this._transDefText = value; }
		}

		///<summary>
		/// �������͸�������
		///</summary>
		[Column(Name = "TRANS_TYPE_DEF_DESC", DbType = StdDbType.UnicodeString, Length = 40)]
		public string TransDefDesc
		{
			get { return this._transDefDesc; }
			set { this._transDefDesc = value; }
		}
		#endregion

		#region Entity Methods
        public TransTypeDef()
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
        public static TransTypeDef Retrieve(ISession session, string transTypeCode)
        {
            return ERPUtil.Cache.Get<TransTypeDef>(transTypeCode);
        }
		#endregion
	}
}