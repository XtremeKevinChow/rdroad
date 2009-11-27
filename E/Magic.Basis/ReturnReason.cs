//*******************************************
// ** Description:  Data Access Object for ReturnReason
// ** Author     :  Code generator
// ** Created    :   2008-6-30 20:22:21
// ** Modified   :
//*******************************************

namespace Magic.Basis
{
	using System;
    using System.Collections.Generic;
	using Magic.Framework.Data;
	using Magic.Framework.ORM;
	using Magic.Framework.ORM.Mapping;

	/// <summary>
	/// Data Access Object for ReturnReason
	/// 退货原因
	/// </summary>
	[Table("BAS_RET_RSON")]
	public partial class  ReturnReason : IEntity
	{
		#region Private Fields
		private int _reasonID;
		private string _reasonText;
		#endregion

		#region Public Properties
        [Column(Name = "RSON_ID", DbType = StdDbType.Int32, IsPrimary = true, IsSequence = true, SequenceName = "SEQ_BAS_RET_RSON")]
		public int ReasonID
		{
			get { return this._reasonID; }
			set { this._reasonID = value; }
		}

		///<summary>
		/// 退货原因文本描述
		///</summary>
		[Column(Name = "RSON_TEXT", DbType = StdDbType.UnicodeString, Length = 50)]
		public string ReasonText
		{
			get { return this._reasonText; }
			set { this._reasonText = value; }
		}

		#endregion

		#region Entity Methods
		public ReturnReason()
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
		public static ReturnReason Retrieve(ISession session, int reasonID)
		{
			return EntityManager.Retrieve<ReturnReason>(session, reasonID);
		}
		public static bool Delete(ISession session, int reasonID)
		{
			return EntityManager.Delete<ReturnReason>(session, reasonID);
		}

        public static IList<ReturnReason> EffectiveList(ISession session)
        {
            return session.CreateEntityQuery<ReturnReason>()
                .OrderBy("ReasonText")
                .List<ReturnReason>();
        }
		#endregion
	}
}
