//*******************************************
// ** Author     :  Code Generator
// ** Created    :   2008-03-15 15:48:57
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
	///  系统消息
	/// </summary>
    [Table("SYS_MESSAGE")]
	public partial class  Message : IEntity
	{
		#region Private Fields
		
		private int _messageId;
		private string _tmplCode;
		private int _msgTypeId;
		private string _title;
		private string _content;
		private MessageAccessibility _accessibility;
		private DateTime _createTime;
		private string _viewEntry;
		private DateTime _sendTime;
		private DateTime _expireTime;
		private string _createBy;
		private DateTime _lastResponseTime;
		private MessageStatus _status;
		private string _responseEntry;
		private string _source;
		private string _destination;
		
		#endregion

		#region Class Constructor
		/// <summary>
		/// default constructor for 系统消息
		/// </summary>
		public Message()
		{
		}
		#endregion

		#region Public Properties
		
		/// <summary>
		/// 消息ID
		/// </summary>
		[Column(DbType=StdDbType.Int32, Nullable=false,IsSequence=true, SequenceName="SEQ_SYS_Message",IsPrimary=true,Name="MSG_ID")]
		public virtual int MessageId
		{
			get { return this._messageId; }
			set { this._messageId = value; }
		}
		
		/// <summary>
		/// 消息模板代码
		/// </summary>
		[Column(DbType=StdDbType.AnsiString, Length=30, Nullable=false,Name="MSG_TMPL_CODE")]
		public virtual string TmplCode
		{
			get { return this._tmplCode; }
			set { this._tmplCode = value; }
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
		/// 标题
		/// </summary>
		[Column(DbType=StdDbType.UnicodeString, Length=250, Nullable=true,Name="MSG_TITLE")]
		public virtual string Title
		{
			get { return this._title; }
			set { this._title = value; }
		}
		
		/// <summary>
		/// 内容
		/// </summary>
		[Column(DbType=StdDbType.UnicodeString, Length=1000, Nullable=true,Name="MSG_CONTENT")]
		public virtual string Content
		{
			get { return this._content; }
			set { this._content = value; }
		}
		
		/// <summary>
		/// 可访问性
		/// </summary>
		[Column(DbType=StdDbType.Int16, Nullable=true,Name="MSG_ACCESS")]
		public virtual MessageAccessibility Accessibility
		{
			get { return this._accessibility; }
			set { this._accessibility = value; }
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
		/// 查看地址
		/// </summary>
		[Column(DbType=StdDbType.UnicodeString, Length=255, Nullable=true,Name="MSG_URL")]
		public virtual string ViewEntry
		{
			get { return this._viewEntry; }
			set { this._viewEntry = value; }
		}
		
		/// <summary>
		/// 发送时间
		/// </summary>
		[Column(DbType=StdDbType.DateTime, Nullable=true,Name="MSG_SEND_TIME")]
		public virtual DateTime SendTime
		{
			get { return this._sendTime; }
			set { this._sendTime = value; }
		}
		
		/// <summary>
		/// 过期时间
		/// </summary>
		[Column(DbType=StdDbType.DateTime, Nullable=true,Name="MSG_EXPIRE_TIME")]
		public virtual DateTime ExpireTime
		{
			get { return this._expireTime; }
			set { this._expireTime = value; }
		}
		
		/// <summary>
		/// 创建人
		/// </summary>
		[Column(DbType=StdDbType.UnicodeString, Length=20, Nullable=false,Name="CREATE_BY")]
		public virtual string CreateBy
		{
			get { return this._createBy; }
			set { this._createBy = value; }
		}
		
		/// <summary>
		/// 最近响应时间
		/// </summary>
		[Column(DbType=StdDbType.DateTime, Nullable=true,Name="LAST_RESPONSE")]
		public virtual DateTime LastResponseTime
		{
			get { return this._lastResponseTime; }
			set { this._lastResponseTime = value; }
		}
		
		/// <summary>
		/// 消息状态
		/// </summary>
		[Column(DbType=StdDbType.Int16, Nullable=true,Name="MSG_STATUS")]
		public virtual MessageStatus Status
		{
			get { return this._status; }
			set { this._status = value; }
		}
		
		/// <summary>
		/// 反应的快捷地址
		/// </summary>
		[Column(DbType=StdDbType.UnicodeString, Length=255, Nullable=true,Name="RESPONSE_URL")]
		public virtual string ResponseEntry
		{
			get { return this._responseEntry; }
			set { this._responseEntry = value; }
		}
		
		/// <summary>
		/// 消息来源
		/// </summary>
		[Column(DbType=StdDbType.UnicodeString, Length=50, Nullable=true,Name="MSG_SOURCE")]
		public virtual string Source
		{
			get { return this._source; }
			set { this._source = value; }
		}
		
		/// <summary>
		/// 消息目标
		/// </summary>
		[Column(DbType=StdDbType.UnicodeString, Length=50, Nullable=true,Name="MSG_TARGET")]
		public virtual string Destination
		{
			get { return this._destination; }
			set { this._destination = value; }
		}
		
		#endregion

		#region IEntity Members
		/// <summary>
		/// Create new 系统消息 entity
		/// <returns></returns>
		/// </summary>
		public bool Create(ISession session)
		{
			return EntityManager.Create(session,this);
		}

		/// <summary>
		/// Update 系统消息 entity's whole properties
		/// <returns></returns>
		/// </summary>
		public bool Update(ISession session)
		{
			return EntityManager.Update(session,this);
		}

		/// <summary>
		///Update 系统消息 entity's given properties
		/// <returns></returns>
		/// </summary>
		public bool Update(ISession session, params string[] propertyNames2Update)
		{
			return EntityManager.Update(session,this,propertyNames2Update);
		}

		/// <summary>
		///Delete this 系统消息 entity
		/// <returns></returns>
		/// </summary>
		public bool Delete(ISession session)
		{
			return EntityManager.Delete(session,this);
		}
		
		#endregion
		
		#region Class Static Members

		/// <summary>
		///Retrive a 系统消息 Entity from DB
		/// <returns></returns>
		/// </summary>
		public static Message Retrieve(ISession session  ,int messageId  )
		{
			return EntityManager.Retrieve<Message>(session,messageId);
		}

		 /// <summary>
        /// Delete by Primary key
        /// </summary>
        /// <returns></returns>
        public static bool Delete(ISession session  ,int messageId  )
        {
           return EntityManager.Delete<Message>(session,messageId);
        }
       
        /// <summary>
        ///  Map a DataRow to a Message Entity.
        /// </summary>
        /// <returns></returns>
       public static Message Row2Entity(System.Data.DataRow row)
       {
            if(row == null) return null;

            Message entity = new Message();
                        
		    entity._messageId= Cast.Int(row["MSG_ID"]);
		    entity._tmplCode= Cast.String(row["MSG_TMPL_CODE"]);
		    entity._msgTypeId= Cast.Int(row["MSG_TYPE_ID"]);
		    entity._title= Cast.String(row["MSG_TITLE"]);
		    entity._content= Cast.String(row["MSG_CONTENT"]);
		    entity._accessibility= Cast.Enum<MessageAccessibility>(row["MSG_ACCESS"]);
		    entity._createTime= Cast.DateTime(row["CREATE_TIME"]);
		    entity._viewEntry= Cast.String(row["MSG_URL"]);
		    entity._sendTime= Cast.DateTime(row["MSG_SEND_TIME"]);
		    entity._expireTime= Cast.DateTime(row["MSG_EXPIRE_TIME"]);
		    entity._createBy= Cast.String(row["CREATE_BY"]);
		    entity._lastResponseTime= Cast.DateTime(row["LAST_RESPONSE"]);
		    entity._status= Cast.Enum<MessageStatus>(row["MSG_STATUS"]);
		    entity._responseEntry= Cast.String(row["RESPONSE_URL"]);
		    entity._source= Cast.String(row["MSG_SOURCE"]);
		    entity._destination= Cast.String(row["MSG_TARGET"]);
		   
		   return entity;
       }
       
        /// <summary>
        ///  Map a DataTable's Rows to a List of Message Entity.
        /// </summary>
        /// <returns></returns>
       public static IList<Message> Row2Entity(System.Data.DataTable dt)
       {
            IList<Message> list = null;
            if(dt != null && dt.Rows.Count>0)
           {
                 list = new List<Message>(dt.Rows.Count);
                 foreach(System.Data.DataRow row in dt.Rows)
                 {
                    Message  entity = Row2Entity(row);
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