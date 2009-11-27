//*******************************************
// ** Description:  Data Access Object for ExcelTemplate
// ** Author     :  Code generator
// ** Created    :   2008-8-14 15:12:15
// ** Modified   :
//*******************************************

namespace Magic.Basis
{
	using System;
    using System.Collections.Generic;
	using Magic.Framework.Data;
	using Magic.Framework.ORM;
	using Magic.Framework.ORM.Mapping;

    public enum ExcelTemplateStatus
    {
        Enable = 1,
        Disable = 2,
        Delete = -1,
    }

	/// <summary>
	/// Data Access Object for ExcelTemplate
	/// </summary>
	[Table("EXCEL_TEMPLATE")]
	public partial class  ExcelTemplate : IEntity
	{
		#region Private Fields
		private int _templateID;
        private ExcelTemplateStatus _status;
		private string _templateName;
		private string _fileVirtualPath;
		#endregion

		#region Public Properties
		[Column(Name = "ET_ID", DbType = StdDbType.Int32, IsPrimary = true, IsSequence = true, SequenceName = "SEQ_EXCEL_TEMPLATE")]
		public int TemplateID
		{
			get { return this._templateID; }
			set { this._templateID = value; }
		}

		[Column(Name = "STATUS", DbType = StdDbType.Int32)]
        public ExcelTemplateStatus Status
		{
			get { return this._status; }
			set { this._status = value; }
		}

		[Column(Name = "ET_NAME", DbType = StdDbType.UnicodeString, Length = 20)]
		public string TemplateName
		{
			get { return this._templateName; }
			set { this._templateName = value; }
		}

		[Column(Name = "FILE_V_PATH", DbType = StdDbType.AnsiString, Length = 150)]
		public string FileVirtualPath
		{
			get { return this._fileVirtualPath; }
			set { this._fileVirtualPath = value; }
		}

		#endregion

		#region Entity Methods
		public ExcelTemplate()
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
            this.Status = ExcelTemplateStatus.Delete;
            return this.Update(session, "Status");
		}
		public static ExcelTemplate Retrieve(ISession session, int templateID)
		{
			return EntityManager.Retrieve<ExcelTemplate>(session, templateID);
		}
		public static bool Delete(ISession session, int templateID)
		{
            ExcelTemplate t = ExcelTemplate.Retrieve(session, templateID);
            if (t == null) return false;
            return t.Delete(session);
		}

        public static IList<ExcelTemplate> GetEnabledList(ISession session)
        {
            return session.CreateEntityQuery<ExcelTemplate>()
                .Where(Magic.Framework.ORM.Query.Exp.Eq("Status", ExcelTemplateStatus.Enable))
                .OrderBy("TemplateName")
                .List<ExcelTemplate>();
        }
        public static IList<ExcelTemplate> ListAll(ISession session)
        {
            return session.CreateEntityQuery<ExcelTemplate>()
                .Where(Magic.Framework.ORM.Query.Exp.NEq("Status", ExcelTemplateStatus.Delete))
                .OrderBy("TemplateName")
                .List<ExcelTemplate>();
        }
		#endregion
	}
}
