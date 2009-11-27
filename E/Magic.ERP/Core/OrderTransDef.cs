//*******************************************
// ** Description:  Data Access Object for OrderTransDef
// ** Author     :  Code generator
// ** Created    :   2008-7-1 3:29:10
// ** Modified   :
//*******************************************

namespace Magic.ERP.Core
{
	using System;
	using Magic.Framework.Data;
	using Magic.Framework.ORM;
	using Magic.Framework.ORM.Mapping;

	/// <summary>
	/// Data Access Object for OrderTransDef
	/// �������Ͷ�Ӧ�Ľ���
	/// </summary>
	[Table("ORD_TRANS_DEF")]
	public partial class  OrderTransDef : IEntity
	{
		#region Private Fields
		private int _transDefID;
		private string _orderTypeCode;
		private int _stepIndex;
        private TransStepType _stepType;
		private string _transTypeCode;
        private TransTypeFrom _transTypeFrom;
        private TransQtyFrom _transQtyFrom;
        private TransLocationFrom _transLocationFrom;
		private string _alias;
        private int _userIndex;
		#endregion

		#region Public Properties
		[Column(Name = "TRANS_DEF_ID", DbType = StdDbType.Int32, IsPrimary = true)]
		public int TransDefID
		{
			get { return this._transDefID; }
			set { this._transDefID = value; }
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

		///<summary>
		/// ���ײ���
		///</summary>
		[Column(Name = "STEP_INDX", DbType = StdDbType.Int32)]
		public int StepIndex
		{
			get { return this._stepIndex; }
			set { this._stepIndex = value; }
		}

		///<summary>
		/// 1: ��������
		/// 2: �����ѡ���ף�ִ�����е�ĳ�����ף�
		///</summary>
		[Column(Name = "STEP_TYPE", DbType = StdDbType.Int32, Precision = 1)]
        public TransStepType StepType
		{
			get { return this._stepType; }
			set { this._stepType = value; }
		}

		///<summary>
		/// ���״���
		///</summary>
		[Column(Name = "TRANS_TYPE_CODE", DbType = StdDbType.AnsiChar, Length = 3)]
		public string TransTypeCode
		{
			get { return this._transTypeCode; }
			set { this._transTypeCode = value; }
		}

		///<summary>
		/// ������ȡ��������
		/// 1: �ӱ���¼�������л�ȡ
		/// 2: �ӽӿڻ�ȡ
		///</summary>
		[Column(Name = "TRANS_TYPE_FROM", DbType = StdDbType.Int32, Precision = 1)]
        public TransTypeFrom TransTypeFrom
		{
			get { return this._transTypeFrom; }
			set { this._transTypeFrom = value; }
		}

		///<summary>
		/// ������ȡ��������
		/// 1: ȡ�ϸ�����
		/// 2: ȡ���ϸ�����
		///</summary>
		[Column(Name = "TRANS_QTY_FROM", DbType = StdDbType.Int32, Precision = 1)]
        public TransQtyFrom TransQtyFrom
		{
			get { return this._transQtyFrom; }
			set { this._transQtyFrom = value; }
		}

		///<summary>
		/// ������ȡ���״洢λ��
		/// 1: �ӽ��ף��ֿ���ձ��л�ȡ
		/// 2: �ӽӿ��л�ȡ
		///</summary>
		[Column(Name = "TRANS_LOC_FROM", DbType = StdDbType.Int32, Precision = 1)]
        public TransLocationFrom TransLocationFrom
		{
			get { return this._transLocationFrom; }
			set { this._transLocationFrom = value; }
		}

		///<summary>
		/// �ý�������������еı���
		///</summary>
		[Column(Name = "TRANS_DEF_ALIAS", DbType = StdDbType.UnicodeString, Length = 20)]
		public string Alias
		{
			get { return this._alias; }
			set { this._alias = value; }
		}

        [Column(Name = "USER_INDEX", DbType = StdDbType.Int32)]
        public int UserIndex
        {
            get { return this._userIndex; }
            set { this._userIndex = value; }
        }
		#endregion

		#region Entity Methods
		public OrderTransDef()
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
		public static OrderTransDef Retrieve(ISession session, int transDefID)
		{
			return EntityManager.Retrieve<OrderTransDef>(session, transDefID);
		}
		public static bool Delete(ISession session, int transDefID)
		{
			return EntityManager.Delete<OrderTransDef>(session, transDefID);
		}
		#endregion
	}
}
