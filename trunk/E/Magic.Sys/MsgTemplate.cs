//*******************************************
// ** Author     :  Code Generator
// ** Created    :   2008-03-15 15:49:06
// ** Modified   :
//*******************************************

using System;
using System.Collections.Generic;
using Magic.Framework.Data;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Mapping;
using Magic.Framework.ORM.Query;
using Magic.Framework.Utils;
using Magic.Security;

namespace Magic.Sys
{

	
	
	/// <summary>
	///  消息模板
	/// </summary>
    [Table("SYS_MSG_TEMPLATE")]
	public partial class  MsgTemplate : IEntity
	{       
        
		#region Private Fields
		
		private string _tmplCode;
		private string _name;
		private int _msgTypeId;
		private MessageAccessibility _accessibility;
		private int _expires;
		private string _source;
		private string _viewEntry;
		private string _titleFormat;
		private string _contentFormat;
		private int _navId;
		private string _responseEntry;
		private DateTime _createTime;
		private DateTime _modifyTime;
		private int _createBy;
		private int _modifyBy;
		
		#endregion

		#region Class Constructor
		/// <summary>
		/// default constructor for 消息模板
		/// </summary>
		public MsgTemplate()
		{
		}
		#endregion

		#region Public Properties
		
		/// <summary>
		/// 消息模板代码
		/// </summary>
		[Column(DbType=StdDbType.AnsiString, Length=30, Nullable=false,IsPrimary=true,Name="MSG_TMPL_CODE")]
		public virtual string TmplCode
		{
			get { return this._tmplCode; }
			set { this._tmplCode = value; }
		}
		
		/// <summary>
		/// 消息模板名称
		/// </summary>
		[Column(DbType=StdDbType.UnicodeString, Length=50, Nullable=true,Name="MSG_TMPL_NAME")]
		public virtual string Name
		{
			get { return this._name; }
			set { this._name = value; }
		}
		
		/// <summary>
		/// 消息类型ID
		/// </summary>
		[Column(DbType=StdDbType.Int32, Nullable=false,Name="MSG_TYPE_ID")]
		public virtual int MsgTypeId
		{
			get { return this._msgTypeId; }
			set { this._msgTypeId = value; }
		}
		
		/// <summary>
		/// 消息的可访问性
		/// </summary>
		[Column(DbType=StdDbType.Int16, Nullable=true,Name="MSG_TMPL_ACCESS")]
		public virtual MessageAccessibility Accessibility
		{
			get { return this._accessibility; }
			set { this._accessibility = value; }
		}
		
		/// <summary>
		/// 过期期限(秒)
		/// </summary>
		[Column(DbType=StdDbType.Int32, Nullable=true,Name="EXPIRE_TIME")]
		public virtual int Expires
		{
			get { return this._expires; }
			set { this._expires = value; }
		}
		
		/// <summary>
		/// 消息的来源
		/// </summary>
		[Column(DbType=StdDbType.UnicodeString, Length=50, Nullable=true,Name="MSG_TMPL_SRC")]
		public virtual string Source
		{
			get { return this._source; }
			set { this._source = value; }
		}
		
		/// <summary>
		/// 消息查看地址
		/// </summary>
		[Column(DbType=StdDbType.UnicodeString, Length=255, Nullable=true,Name="ENTRY_URL")]
		public virtual string ViewEntry
		{
			get { return this._viewEntry; }
			set { this._viewEntry = value; }
		}
		
		/// <summary>
		/// 消息标题格式
		/// </summary>
		[Column(DbType=StdDbType.UnicodeString, Length=500, Nullable=true,Name="TITLE_FORMAT")]
		public virtual string TitleFormat
		{
			get { return this._titleFormat; }
			set { this._titleFormat = value; }
		}
		
		/// <summary>
		/// 消息内容格式
		/// </summary>
		[Column(DbType=StdDbType.UnicodeString, Length=1500, Nullable=true,Name="CONTENT_FORMAT")]
		public virtual string ContentFormat
		{
			get { return this._contentFormat; }
			set { this._contentFormat = value; }
		}
		
		/// <summary>
		/// 导航结点标识
		/// </summary>
		[Column(DbType=StdDbType.Int32, Nullable=true,Name="MSG_OPT")]
		public virtual int NavId
		{
			get { return this._navId; }
			set { this._navId = value; }
		}
		
		/// <summary>
		/// 反应的快捷方式
		/// </summary>
		[Column(DbType=StdDbType.UnicodeString, Length=255, Nullable=true,Name="REPLY_URL")]
		public virtual string ResponseEntry
		{
			get { return this._responseEntry; }
			set { this._responseEntry = value; }
		}
		
		/// <summary>
		/// 创建时间
		/// </summary>
		[Column(DbType=StdDbType.DateTime, Nullable=true,Name="CREATE_TIME")]
		public virtual DateTime CreateTime
		{
			get { return this._createTime; }
			set { this._createTime = value; }
		}
		
		/// <summary>
		/// 修改时间
		/// </summary>
		[Column(DbType=StdDbType.DateTime, Nullable=false,Name="MODIFY_TIME")]
		public virtual DateTime ModifyTime
		{
			get { return this._modifyTime; }
			set { this._modifyTime = value; }
		}
		
		/// <summary>
		/// 创建人
		/// </summary>
		[Column(DbType=StdDbType.Int32, Nullable=true,Name="CREATE_BY")]
		public virtual int CreateBy
		{
			get { return this._createBy; }
			set { this._createBy = value; }
		}
		
		/// <summary>
		/// 修改人
		/// </summary>
		[Column(DbType=StdDbType.Int32, Nullable=true,Name="MODIFY_BY")]
		public virtual int ModifyBy
		{
			get { return this._modifyBy; }
			set { this._modifyBy = value; }
		}
		
		#endregion

		#region IEntity Members
		/// <summary>
		/// Create new 消息模板 entity
		/// <returns></returns>
		/// </summary>
		public bool Create(ISession session)
		{
			return EntityManager.Create(session,this);
		}

		/// <summary>
		/// Update 消息模板 entity's whole properties
		/// <returns></returns>
		/// </summary>
		public bool Update(ISession session)
		{
			return EntityManager.Update(session,this);
		}

		/// <summary>
		///Update 消息模板 entity's given properties
		/// <returns></returns>
		/// </summary>
		public bool Update(ISession session, params string[] propertyNames2Update)
		{
			return EntityManager.Update(session,this,propertyNames2Update);
		}

		/// <summary>
		///Delete this 消息模板 entity
		/// <returns></returns>
		/// </summary>
		public bool Delete(ISession session)
		{
			return EntityManager.Delete(session,this);
		}
		
		#endregion
		
		#region Class Static Members

		/// <summary>
		///Retrive a 消息模板 Entity from DB
		/// <returns></returns>
		/// </summary>
		public static MsgTemplate Retrieve(ISession session  ,string tmplCode  )
		{
			return EntityManager.Retrieve<MsgTemplate>(session,tmplCode);
		}

		 /// <summary>
        /// Delete by Primary key
        /// </summary>
        /// <returns></returns>
        public static bool Delete(ISession session  ,string tmplCode  )
        {
           return EntityManager.Delete<MsgTemplate>(session,tmplCode);
        }
       
        /// <summary>
        ///  Map a DataRow to a MsgTemplate Entity.
        /// </summary>
        /// <returns></returns>
       public static MsgTemplate Row2Entity(System.Data.DataRow row)
       {
            if(row == null) return null;

            MsgTemplate entity = new MsgTemplate();
                        
		    entity._tmplCode= Cast.String(row["MSG_TMPL_CODE"]);
		    entity._name= Cast.String(row["MSG_TMPL_NAME"]);
		    entity._msgTypeId= Cast.Int(row["MSG_TYPE_ID"]);
		    entity._accessibility= Cast.Enum<MessageAccessibility>(row["MSG_TMPL_ACCESS"]);
		    entity._expires= Cast.Int(row["EXPIRE_TIME"]);
		    entity._source= Cast.String(row["MSG_TMPL_SRC"]);
		    entity._viewEntry= Cast.String(row["ENTRY_URL"]);
		    entity._titleFormat= Cast.String(row["TITLE_FORMAT"]);
		    entity._contentFormat= Cast.String(row["CONTENT_FORMAT"]);
		    entity._navId= Cast.Int(row["MSG_OPT"]);
		    entity._responseEntry= Cast.String(row["REPLY_URL"]);
		    entity._createTime= Cast.DateTime(row["CREATE_TIME"]);
		    entity._modifyTime= Cast.DateTime(row["MODIFY_TIME"]);
		    entity._createBy= Cast.Int(row["CREATE_BY"]);
		    entity._modifyBy= Cast.Int(row["MODIFY_BY"]);
		   
		   return entity;
       }
       
        /// <summary>
        ///  Map a DataTable's Rows to a List of MsgTemplate Entity.
        /// </summary>
        /// <returns></returns>
       public static IList<MsgTemplate> Row2Entity(System.Data.DataTable dt)
       {
            IList<MsgTemplate> list = null;
            if(dt != null && dt.Rows.Count>0)
           {
                 list = new List<MsgTemplate>(dt.Rows.Count);
                 foreach(System.Data.DataRow row in dt.Rows)
                 {
                    MsgTemplate  entity = Row2Entity(row);
                    if(entity != null)
                    {
                        list.Add(entity);
                    }
                 }
           }  
          return list; 
       }
		#endregion

	}
}