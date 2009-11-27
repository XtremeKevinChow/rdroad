//*******************************************
// ** Description:  Data Access Object for Member
// ** Author     :  Code generator
// ** Created    :   2008-7-7 20:32:34
// ** Modified   :
//*******************************************

namespace Magic.Basis
{
	using System;
	using Magic.Framework.Data;
	using Magic.Framework.ORM;
	using Magic.Framework.ORM.Mapping;

	/// <summary>
	/// Data Access Object for Member
	/// </summary>
	[Table("MBR_MEMBERS")]
	public partial class  Member : IEntity
	{
		#region Private Fields
		private int _memberID;
		private string _name;
        private string _memberNumber;
        private string _mobile;
        private string _familyPhone;
        private string _companyPhone;
		#endregion

		#region Public Properties
		[Column(Name = "ID", DbType = StdDbType.Int32, IsPrimary = true, IsSequence = true, SequenceName = "SEQ_MBR_MEMBERS")]
		public int MemberID
		{
			get { return this._memberID; }
			set { this._memberID = value; }
		}

		[Column(Name = "NAME", DbType = StdDbType.AnsiString, Length = 30)]
		public string Name
		{
			get { return this._name; }
			set { this._name = value; }
		}

        [Column(Name = "CARD_ID", DbType = StdDbType.AnsiString, Length = 10)]
        public string MemberNumber
        {
            get { return this._memberNumber; }
            set { this._memberNumber = value; }
        }

        [Column(Name = "TELEPHONE", DbType = StdDbType.AnsiString, Length = 50)]
        public string Mobile
        {
            get { return this._mobile; }
            set { this._mobile = value; }
        }

        [Column(Name = "COMPANY_PHONE", DbType = StdDbType.AnsiString, Length = 50)]
        public string CompanyPhone
        {
            get { return this._companyPhone; }
            set { this._companyPhone = value; }
        }

        [Column(Name = "FAMILY_PHONE", DbType = StdDbType.AnsiString, Length = 50)]
        public string FamilyPhone
        {
            get { return this._familyPhone; }
            set { this._familyPhone = value; }
        }
		#endregion

		#region Entity Methods
		public Member()
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
		public static Member Retrieve(ISession session, int memberID)
		{
			return EntityManager.Retrieve<Member>(session, memberID);
		}
		public static bool Delete(ISession session, int memberID)
		{
			return EntityManager.Delete<Member>(session, memberID);
		}
		#endregion
	}
}
