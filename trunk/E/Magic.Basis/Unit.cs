//*******************************************
// ** Description:  Data Access Object for Unit
// ** Author     :  Code generator
// ** Created    :   2008-6-30 20:22:21
// ** Modified   :
//*******************************************

namespace Magic.Basis
{
	using System;
	using Magic.Framework.Data;
	using Magic.Framework.ORM;
	using Magic.Framework.ORM.Mapping;

	/// <summary>
	/// Data Access Object for Unit
	/// µ¥Î»
	/// </summary>
	[Table("BAS_UNIT")]
	public partial class  Unit : IEntity
	{
		#region Private Fields
		private int _unitID;
		private string _unitCode;
		private string _unitText;
		#endregion

		#region Public Properties
        [Column(Name = "UNIT_ID", DbType = StdDbType.Int32, IsPrimary = true, IsSequence = true, SequenceName = "SEQ_BAS_UNIT")]
		public int UnitID
		{
			get { return this._unitID; }
			set { this._unitID = value; }
		}

		[Column(Name = "UNIT_CODE", DbType = StdDbType.AnsiString, Length = 8)]
		public string UnitCode
		{
			get { return this._unitCode; }
			set { this._unitCode = value; }
		}

		[Column(Name = "UNIT_TEXT", DbType = StdDbType.UnicodeString, Length = 10)]
		public string UnitText
		{
			get { return this._unitText; }
			set { this._unitText = value; }
		}

		#endregion

		#region Entity Methods
		public Unit()
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
		public static Unit Retrieve(ISession session, int unitID)
		{
			return EntityManager.Retrieve<Unit>(session, unitID);
		}
		public static bool Delete(ISession session, int unitID)
		{
			return EntityManager.Delete<Unit>(session, unitID);
		}
		#endregion
	}
}
