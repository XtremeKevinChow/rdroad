//*******************************************
// ** Description:  Data Access Object for NotificationParam
// ** Author     :  Code generator
// ** Created    :   2008-9-22 10:55:05
// ** Modified   :
//*******************************************

namespace Magic.Notification
{
	using System;
	using Magic.Framework.Data;
	using Magic.Framework.ORM;
	using Magic.Framework.ORM.Mapping;

	/// <summary>
	/// Data Access Object for NotificationParam
	/// ��Ϣ֪ͨ�Ĳ�����֧�ֲ㼶�ṹ
	/// ���綩������֪ͨ��
	/// 1. ParamName: SaleOrderNumber, ParamValue: 0809102314 ���������룩
	/// 2. ParamName: ShippingNumber, ParamValue: 902145 ����ݹ�˾�˵����룩
	/// 3. ParamName: LogisticCompany, ParamValue: Բͨ���
	/// 4. ParamName: TotalAmt, ParamValue: 462.00 �������ܽ�
	/// 5. ParamName: DueAmt, ParamValue: 262.00 ��ʣ��Ӧ����
	/// 6. ParamName: Lines, ParamValue: 
	/// </summary>
	[Table("BC_NOTIFY_PARAM")]
	public partial class  NotificationParam
	{
		#region Private Fields
		private int _paramID;
		private int _notifyID;
		private int _serialIndex;
		private int _subIndex;
		private int _parentID;
        private NotificationDataType _dataType;
		private string _paramName;
		private string _paramValue;
		#endregion

		#region Public Properties
		[Column(Name = "NTF_PARAM_ID", DbType = StdDbType.Int32, IsPrimary = true, IsSequence = true, SequenceName = "SEQ_BC_NOTIFY_PARAM")]
		public int ParamID
		{
			get { return this._paramID; }
			set { this._paramID = value; }
		}

		[Column(Name = "NTF_ID", DbType = StdDbType.Int32)]
		public int NotifyID
		{
			get { return this._notifyID; }
			set { this._notifyID = value; }
		}

		///<summary>
		/// ����ϵ��ID������һ�������ж����ϸ��ÿ����ϸ�ж������
		/// ��һ����ϸ��SerialIndexΪ1���ڶ�����ϸ��SerialIndexΪ2...
		///</summary>
		[Column(Name = "SER_INDX", DbType = StdDbType.Int32)]
		public int SerialIndex
		{
			get { return this._serialIndex; }
			set { this._serialIndex = value; }
		}

		///<summary>
		/// ����˳������һ�������ж����ϸ��ÿ����ϸ�ж������
		/// ÿ����ϸ�ĵ�һ������SubIndexΪ1���ڶ�������SubIndexΪ2...
		///</summary>
		[Column(Name = "SUB_INDEX", DbType = StdDbType.Int32)]
		public int SubIndex
		{
			get { return this._subIndex; }
			set { this._subIndex = value; }
		}

		[Column(Name = "PARENT_ID", DbType = StdDbType.Int32)]
		public int ParentID
		{
			get { return this._parentID; }
			set { this._parentID = value; }
		}

		///<summary>
		/// ����ֵ����������
		/// 10. ����ֵ�ļ��ϣ�list of NotificationParam��
		/// 20: �ַ���
		/// 30: ����
		/// 40: Decimal
		/// 50: ����ʱ��
		///</summary>
		[Column(Name = "DATA_TYPE", DbType = StdDbType.Int32)]
        public NotificationDataType DataType
		{
			get { return this._dataType; }
			set { this._dataType = value; }
		}

		[Column(Name = "PARAM_NAME", DbType = StdDbType.AnsiString, Length = 25)]
		public string ParamName
		{
			get { return this._paramName; }
			set { this._paramName = value; }
		}

		[Column(Name = "PARAM_VALUE", DbType = StdDbType.UnicodeString, Length = 50)]
		public string ParamValue
		{
			get { return this._paramValue; }
			set { this._paramValue = value; }
		}

		#endregion

		#region Entity Methods
        public NotificationParam()
        {
        }

        internal bool Create(ISession session)
		{
			return EntityManager.Create(session, this);
		}
        internal bool Update(ISession session)
		{
			return EntityManager.Update(session, this);
		}
        internal bool Update(ISession session, params string[] propertyNames2Update)
		{
			return EntityManager.Update(session, this, propertyNames2Update);
		}
        internal bool Delete(ISession session)
		{
			return EntityManager.Delete(session, this);
		}
        internal static NotificationParam Retrieve(ISession session, int paramID)
		{
			return EntityManager.Retrieve<NotificationParam>(session, paramID);
		}
        internal static bool Delete(ISession session, int paramID)
		{
			return EntityManager.Delete<NotificationParam>(session, paramID);
		}
		#endregion
	}
}
