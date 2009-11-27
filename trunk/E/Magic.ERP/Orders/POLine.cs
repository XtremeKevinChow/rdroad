//*******************************************
// ** Description:  Data Access Object for POLine
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
	/// Data Access Object for POLine
	/// �ɹ�������ϸ
	/// </summary>
	[Table("ORD_PUR_LINE")]
    public partial class POLine : IEntity
	{
		#region Private Fields
		private string _orderNumber;
		private string _lineNumber;
        private POLineStatus _lineStatus;
		private int _sKUID;
		private decimal _purchaseQty;
		private decimal _price;
		private decimal _taxInclusiveAmt;
		private int _taxID;
		private decimal _taxValue;
		private decimal _taxAmt;
		private decimal _taxExlusiveAmt;
		private DateTime _planDate;
		private DateTime _actualDate;
		private decimal _receiveQty;
        private decimal _unfinishedReceiveQty;
		private decimal _iQCQty;
		private int _modifyUser;
		private DateTime _modifyTime;
		private int _unitID;
		#endregion

		#region Public Properties
		[Column(Name = "ORD_NUM", DbType = StdDbType.AnsiString, Length = 16, IsPrimary = true)]
		public string OrderNumber
		{
			get { return this._orderNumber; }
			set { this._orderNumber = value; }
		}

		[Column(Name = "LINE_NUM", DbType = StdDbType.AnsiString, Length = 10, IsPrimary = true)]
		public string LineNumber
		{
			get { return this._lineNumber; }
			set { this._lineNumber = value; }
		}

		///<summary>
		/// 1: ��Ч Open�����һ��LineʱĬ��״̬ΪOpen��
		/// 2: ȡ�� Cancel�������Ѿ�ǩ��֮�����ȡ��ĳ���У�ǰ�����ջ�����Ϊ0��
		/// 3: ����� Close
		///</summary>
		[Column(Name = "LINE_STATUS", DbType = StdDbType.Int32)]
        public POLineStatus LineStatus
		{
			get { return this._lineStatus; }
			set { this._lineStatus = value; }
		}

		[Column(Name = "SKU_ID", DbType = StdDbType.Int32)]
		public int SKUID
		{
			get { return this._sKUID; }
			set { this._sKUID = value; }
		}

		///<summary>
		/// �ɹ�����
		///</summary>
		[Column(Name = "PUR_QTY", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal PurchaseQty
		{
			get { return this._purchaseQty; }
			set { this._purchaseQty = value; }
		}

		///<summary>
		/// �ɹ��۸�
		///</summary>
		[Column(Name = "PUR_PRICE", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal Price
		{
			get { return this._price; }
			set { this._price = value; }
		}

		///<summary>
		/// ��˰���
		///</summary>
		[Column(Name = "TAX_IN_AMT", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal TaxInclusiveAmt
		{
			get { return this._taxInclusiveAmt; }
			set { this._taxInclusiveAmt = value; }
		}

		///<summary>
		/// ˰�ʱ�ID
		///</summary>
		[Column(Name = "TAX_ID", DbType = StdDbType.Int32)]
		public int TaxID
		{
			get { return this._taxID; }
			set { this._taxID = value; }
		}

		///<summary>
		/// ˰�ʣ�����0.17��ʾ17%��˰��
		///</summary>
		[Column(Name = "TAX_VAL", DbType = StdDbType.Number, Precision = 5, Scale = 3)]
		public decimal TaxValue
		{
			get { return this._taxValue; }
			set { this._taxValue = value; }
		}

		///<summary>
		/// ˰��
		///</summary>
		[Column(Name = "TAX_AMT", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal TaxAmt
		{
			get { return this._taxAmt; }
			set { this._taxAmt = value; }
		}

		///<summary>
		/// ����˰���
		///</summary>
		[Column(Name = "TAX_EX_AMT", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal TaxExlusiveAmt
		{
			get { return this._taxExlusiveAmt; }
			set { this._taxExlusiveAmt = value; }
		}

		///<summary>
		/// ��Ӧ���ͻ�����
		///</summary>
		[Column(Name = "PLAN_RCV_DATE", DbType = StdDbType.DateTime)]
		public DateTime PlanDate
		{
			get { return this._planDate; }
			set { this._planDate = value; }
		}

		///<summary>
		/// ʵ���ջ�ʱ�䣬�ջ�ʱ�Զ�����
		///</summary>
		[Column(Name = "ACT_RCV_DATE", DbType = StdDbType.DateTime)]
		public DateTime ActualDate
		{
			get { return this._actualDate; }
			set { this._actualDate = value; }
		}

		///<summary>
		/// �ջ��������ջ�ʱ�Զ�����
		///</summary>
		[Column(Name = "RCV_QTY", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal ReceiveQty
		{
			get { return this._receiveQty; }
			set { this._receiveQty = value; }
		}

        ///<summary>
        /// �Ѿ������ջ��������ջ���ҵ��û����ɵ�����
        /// �����ֶΣ��������ܵĴ�ʩ�����ⴴ���ջ���ʱ�ĸ���sql������ѯ
        ///</summary>
        [Column(Name = "TEMP_RCV_QTY", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
        public decimal UnfinishedReceiveQty
        {
            get { return this._unfinishedReceiveQty; }
            set { this._unfinishedReceiveQty = value; }
        }

		///<summary>
		/// �ϸ��������ջ�ʱ�Զ�����
		///</summary>
		[Column(Name = "IQC_QTY", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal IQCQty
		{
			get { return this._iQCQty; }
			set { this._iQCQty = value; }
		}

		[Column(Name = "MODIFY_USR", DbType = StdDbType.Int32)]
		public int ModifyUser
		{
			get { return this._modifyUser; }
			set { this._modifyUser = value; }
		}

		[Column(Name = "MODIFY_TIME", DbType = StdDbType.DateTime)]
		public DateTime ModifyTime
		{
			get { return this._modifyTime; }
			set { this._modifyTime = value; }
		}

		[Column(Name = "UNIT_ID", DbType = StdDbType.Int32)]
		public int UnitID
		{
			get { return this._unitID; }
			set { this._unitID = value; }
		}

		#endregion

		#region Entity Methods
		public POLine()
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
		public static POLine Retrieve(ISession session, string orderNumber, string lineNumber)
		{
			return EntityManager.Retrieve<POLine>(session, new string[]{ "OrderNumber", "LineNumber" },  new object[]{ orderNumber, lineNumber });
		}
		public static bool Delete(ISession session, string orderNumber, string lineNumber)
		{
			return EntityManager.Delete<POLine>(session, new string[]{ "OrderNumber", "LineNumber" },  new object[]{ orderNumber, lineNumber })>0;
		}
		#endregion
	}
}
