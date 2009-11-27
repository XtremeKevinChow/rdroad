//*******************************************
// ** Description:  Data Access Object for OrderApproveResult
// ** Author     :  Code generator
// ** Created    :   2008-7-9 21:48:08
// ** Modified   :
//*******************************************

namespace Magic.ERP.Core
{
	using System;
	using Magic.Framework.Data;
	using Magic.Framework.ORM;
	using Magic.Framework.ORM.Mapping;

	/// <summary>
	/// Data Access Object for OrderApproveResult
	/// 签核记录
	/// </summary>
	[Table("ORD_APRV_RSLT")]
	public partial class  OrderApproveResult : IEntity
	{
		#region Private Fields
		private int _approveResultID;
		private int _approveID;
		private int _stepIndex;
		private int _approveUser;
		private bool _hasFinished;
		private bool _activeItem;
		private bool _approveResult;
		private DateTime _approveTime;
		private string _approveNote;
		#endregion

		#region Public Properties
		[Column(Name = "APRV_RSLT_ID", DbType = StdDbType.Int32, IsPrimary = true, IsSequence = true, SequenceName = "SEQ_ORD_APRV_RSLT")]
		public int ApproveResultID
		{
			get { return this._approveResultID; }
			set { this._approveResultID = value; }
		}

		[Column(Name = "APRV_ID", DbType = StdDbType.Int32)]
		public int ApproveID
		{
			get { return this._approveID; }
			set { this._approveID = value; }
		}

		///<summary>
		/// 签核步骤
		///</summary>
		[Column(Name = "STEP_INDX", DbType = StdDbType.Int32)]
		public int StepIndex
		{
			get { return this._stepIndex; }
			set { this._stepIndex = value; }
		}

		///<summary>
		/// 签核人
		///</summary>
		[Column(Name = "USR_ID", DbType = StdDbType.Int32)]
		public int ApproveUser
		{
			get { return this._approveUser; }
			set { this._approveUser = value; }
		}

		///<summary>
		/// 是否已经完成签核  0: 未签核  1: 已签核
		///</summary>
		[Column(Name = "HAS_FINISHED", DbType = StdDbType.Bool)]
		public bool HasFinished
		{
			get { return this._hasFinished; }
			set { this._hasFinished = value; }
		}

		[Column(Name = "ACT_ITM", DbType = StdDbType.Bool)]
		public bool ActiveItem
		{
			get { return this._activeItem; }
			set { this._activeItem = value; }
		}

		///<summary>
		/// 签核结果  0: 拒绝  1: 通过
		///</summary>
		[Column(Name = "APRV_RSLT", DbType = StdDbType.Bool)]
		public bool ApproveResult
		{
			get { return this._approveResult; }
			set { this._approveResult = value; }
		}

		///<summary>
		/// 签核时间
		///</summary>
		[Column(Name = "APRV_TIME", DbType = StdDbType.DateTime)]
		public DateTime ApproveTime
		{
			get { return this._approveTime; }
			set { this._approveTime = value; }
		}

		///<summary>
		/// 签单人的备注信息
		///</summary>
		[Column(Name = "APRV_NOTE", DbType = StdDbType.UnicodeString, Length = 40)]
		public string ApproveNote
		{
			get { return this._approveNote; }
			set { this._approveNote = value; }
		}

		#endregion

		#region Entity Methods
		public OrderApproveResult()
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
		public static OrderApproveResult Retrieve(ISession session, int approveResultID)
		{
			return EntityManager.Retrieve<OrderApproveResult>(session, approveResultID);
		}
		public static bool Delete(ISession session, int approveResultID)
		{
			return EntityManager.Delete<OrderApproveResult>(session, approveResultID);
		}
		#endregion
	}
}
