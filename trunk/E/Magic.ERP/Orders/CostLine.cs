//*******************************************
// ** Description:  Data Access Object for CostLine
// ** Author     :  Code generator
// ** Created    :   2008-10-6 15:23:31
// ** Modified   :
//*******************************************

namespace Magic.ERP.Orders
{
	using System;
	using Magic.Framework.Data;
	using Magic.Framework.ORM;
	using Magic.Framework.ORM.Mapping;

	/// <summary>
	/// Data Access Object for CostLine
	/// </summary>
	[Table("COST_LINE")]
	public partial class  CostLine : IEntity
	{
		#region Private Fields
		private int _costID;
		private int _categoryID;
		private string _costObject;
		private decimal _costAmt;
		private string _note;
		#endregion

		#region Public Properties
		[Column(Name = "COST_ID", DbType = StdDbType.Int32, IsPrimary = true, IsSequence = true, SequenceName = "SEQ_COST_LINE")]
		public int CostID
		{
			get { return this._costID; }
			set { this._costID = value; }
		}

		[Column(Name = "COST_CAT_ID", DbType = StdDbType.Int32)]
		public int CategoryID
		{
			get { return this._categoryID; }
			set { this._categoryID = value; }
		}

		///<summary>
		/// 成本对象，例如发货单ID或者号码等
		///</summary>
		[Column(Name = "COST_OBJ", DbType = StdDbType.AnsiString, Length = 20)]
		public string CostObject
		{
			get { return this._costObject; }
			set { this._costObject = value; }
		}

		///<summary>
		/// 成本费用金额
		///</summary>
		[Column(Name = "COST_AMT", DbType = StdDbType.Number, Precision = 12, Scale = 3)]
		public decimal CostAmt
		{
			get { return this._costAmt; }
			set { this._costAmt = value; }
		}

		///<summary>
		/// 备注信息
		///</summary>
		[Column(Name = "COST_NOTE", DbType = StdDbType.UnicodeString, Length = 30)]
		public string Note
		{
			get { return this._note; }
			set { this._note = value; }
		}

		#endregion

		#region Entity Methods
		public CostLine()
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
		public static CostLine Retrieve(ISession session, int costID)
		{
			return EntityManager.Retrieve<CostLine>(session, costID);
		}
		public static bool Delete(ISession session, int costID)
		{
			return EntityManager.Delete<CostLine>(session, costID);
		}
		#endregion
	}
}
