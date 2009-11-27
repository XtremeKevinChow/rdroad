//*******************************************
// ** Description:  Data Access Object for INVPeriod
// ** Author     :  Code generator
// ** Created    :   2008-9-25 10:39:05
// ** Modified   :
//*******************************************

namespace Magic.Basis
{
	using System;
	using Magic.Framework.Data;
	using Magic.Framework.ORM;
	using Magic.Framework.ORM.Mapping;

	/// <summary>
	/// Data Access Object for INVPeriod
	/// ����ڼ�
	/// </summary>
	[Table("INV_PERIOD")]
	public partial class  INVPeriod : IEntity
	{
		#region Private Fields
		private int _periodID;
		private string _periodCode;
        private INVPeriodType _type;
		private int _year;
		private int _index;
		private DateTime _startingDate;
		private DateTime _endDate;
		private INVPeriodStatus _status;
        private bool _balanceFinished;
		#endregion

		#region Public Properties
		[Column(Name = "PD_ID", DbType = StdDbType.Int32, IsPrimary = true, IsSequence = true, SequenceName = "SEQ_INV_PERIOD")]
		public int PeriodID
		{
			get { return this._periodID; }
			set { this._periodID = value; }
		}

		///<summary>
		/// ����ڼ����
		///</summary>
		[Column(Name = "PD_CODE", DbType = StdDbType.AnsiString, Length = 8)]
		public string PeriodCode
		{
			get { return this._periodCode; }
			set { this._periodCode = value; }
		}

		///<summary>
		/// ����ڼ�����
		/// Quarter: �����Ȼ��ֿ���ڼ�
		/// Month: ���·ݻ���
		/// Manual: �ֹ�����
		///</summary>
		[Column(Name = "PD_TYPE", DbType = StdDbType.AnsiString, Length = 10)]
		public INVPeriodType Type
		{
			get { return this._type; }
			set { this._type = value; }
		}

		///<summary>
		/// ���
		///</summary>
		[Column(Name = "PD_YEAR", DbType = StdDbType.Int32)]
		public int Year
		{
			get { return this._year; }
			set { this._year = value; }
		}

		///<summary>
		/// ����ڼ���ܰ���ÿ�¡�ÿ���ȵȻ��֣�Index������������ʾ�ڼ����·ݡ��ڼ������ȵ�
		///</summary>
		[Column(Name = "PD_INDEX", DbType = StdDbType.Int32)]
		public int Index
		{
			get { return this._index; }
			set { this._index = value; }
		}

		///<summary>
		/// ��ʼ����
		///</summary>
		[Column(Name = "START_DATE", DbType = StdDbType.DateTime)]
		public DateTime StartingDate
		{
			get { return this._startingDate; }
			set { this._startingDate = value; }
		}

		///<summary>
		/// ��������
		///</summary>
		[Column(Name = "END_DATE", DbType = StdDbType.DateTime)]
		public DateTime EndDate
		{
			get { return this._endDate; }
			set { this._endDate = value; }
		}

		///<summary>
		/// ����ڼ�״̬:
		/// New: 10
		/// Open: 20
		/// Close: 30
		///</summary>
		[Column(Name = "PD_STATUS", DbType = StdDbType.Int32)]
		public INVPeriodStatus Status
		{
			get { return this._status; }
			set { this._status = value; }
		}

        ///<summary>
        /// �Ƿ��Ѿ�ִ�й��½ᴦ��
        ///</summary>
        [Column(Name = "BLNC_FINISH", DbType = StdDbType.Bool)]
        public bool BalanceFinished
        {
            get { return this._balanceFinished; }
            set { this._balanceFinished = value; }
        }

		#endregion

		#region Entity Methods
		public INVPeriod()
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
		public static INVPeriod Retrieve(ISession session, int periodID)
		{
			return EntityManager.Retrieve<INVPeriod>(session, periodID);
		}
		public static bool Delete(ISession session, int periodID)
		{
			return EntityManager.Delete<INVPeriod>(session, periodID);
		}
		#endregion
	}
}
