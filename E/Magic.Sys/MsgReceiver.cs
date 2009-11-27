//*******************************************
// ** Author     :  Code Generator
// ** Created    :   2008-03-14 21:19:26
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
	///  消息接收者
	/// </summary>
    [Table("SYS_MSG_RECEIVER")]
	public partial class  MsgReceiver : IEntity
	{
		#region Private Fields
		
		private int _receiverId;
		private int _messageId;
		private int _userId;
		private bool _subscriberType;
		private DateTime _sendTime;
		private DateTime _receiveTime;
		private DateTime _expireTime;
        private MessageReadStatus _readStatus;
		private string _note;
		
		#endregion

		#region Class Constructor
		/// <summary>
		/// default constructor for 消息接收者
		/// </summary>
		public MsgReceiver()
		{
		}
		#endregion

		#region Public Properties
		
		/// <summary>
		/// 消息接收标识ID
		/// </summary>
		[Column(DbType=StdDbType.Int32, Nullable=false,IsSequence=true, SequenceName="SEQ_SYS_Msg_Receiver",IsPrimary=true,Name="MSG_RCV_ID")]
		public virtual int ReceiverId
		{
			get { return this._receiverId; }
			set { this._receiverId = value; }
		}
		
		/// <summary>
		/// 消息ID
		/// </summary>
		[Column(DbType=StdDbType.Int32, Nullable=false,Name="MSG_ID")]
		public virtual int MessageId
		{
			get { return this._messageId; }
			set { this._messageId = value; }
		}
		
		/// <summary>
		/// 用户ID
		/// </summary>
		[Column(DbType=StdDbType.Int32, Nullable=false,Name="USR_ID")]
		public virtual int UserId
		{
			get { return this._userId; }
			set { this._userId = value; }
		}
		
		/// <summary>
		/// 订阅用户类型
		/// </summary>
		[Column(DbType=StdDbType.Bool, Nullable=false,Name="MSG_RCV_TYPE")]
		public virtual bool SubscriberType
		{
			get { return this._subscriberType; }
			set { this._subscriberType = value; }
		}
		
		/// <summary>
		/// 发送时间
		/// </summary>
		[Column(DbType=StdDbType.DateTime, Nullable=true,Name="MSG_RCV_SEND_TIME")]
		public virtual DateTime SendTime
		{
			get { return this._sendTime; }
			set { this._sendTime = value; }
		}
		
		/// <summary>
		/// 接收时间
		/// </summary>
		[Column(DbType=StdDbType.DateTime, Nullable=true,Name="MSG_RCV_TIME")]
		public virtual DateTime ReceiveTime
		{
			get { return this._receiveTime; }
			set { this._receiveTime = value; }
		}
		
		/// <summary>
		/// 过期时间
		/// </summary>
		[Column(DbType=StdDbType.DateTime, Nullable=true,Name="MSG_RCV_EXPIRE_TIME")]
		public virtual DateTime ExpireTime
		{
			get { return this._expireTime; }
			set { this._expireTime = value; }
		}
		
		/// <summary>
		/// 阅读状态
		/// </summary>
		[Column(DbType=StdDbType.Int16, Nullable=true,Name="MSG_RCV_STATUS")]
        public virtual MessageReadStatus ReadStatus
		{
			get { return this._readStatus; }
			set { this._readStatus = value; }
		}
		
		/// <summary>
		/// 备注
		/// </summary>
		[Column(DbType=StdDbType.UnicodeString, Length=250, Nullable=true,Name="MSG_RCV_NOTE")]
		public virtual string Note
		{
			get { return this._note; }
			set { this._note = value; }
		}
		
		#endregion

		#region IEntity Members
		/// <summary>
		/// Create new 消息接收者 entity
		/// <returns></returns>
		/// </summary>
		public bool Create(ISession session)
		{
			return EntityManager.Create(session,this);
		}

		/// <summary>
		/// Update 消息接收者 entity's whole properties
		/// <returns></returns>
		/// </summary>
		public bool Update(ISession session)
		{
			return EntityManager.Update(session,this);
		}

		/// <summary>
		///Update 消息接收者 entity's given properties
		/// <returns></returns>
		/// </summary>
		public bool Update(ISession session, params string[] propertyNames2Update)
		{
			return EntityManager.Update(session,this,propertyNames2Update);
		}

		/// <summary>
		///Delete this 消息接收者 entity
		/// <returns></returns>
		/// </summary>
		public bool Delete(ISession session)
		{
			return EntityManager.Delete(session,this);
		}
		
		#endregion
		
		#region Class Static Members

		/// <summary>
		///Retrive a 消息接收者 Entity from DB
		/// <returns></returns>
		/// </summary>
		public static MsgReceiver Retrieve(ISession session  ,int receiverId  )
		{
			return EntityManager.Retrieve<MsgReceiver>(session,receiverId);
		}

		 /// <summary>
        /// Delete by Primary key
        /// </summary>
        /// <returns></returns>
        public static bool Delete(ISession session  ,int receiverId  )
        {
           return EntityManager.Delete<MsgReceiver>(session,receiverId);
        }
       
        /// <summary>
        ///  Map a DataRow to a MsgReceiver Entity.
        /// </summary>
        /// <returns></returns>
       public static MsgReceiver Row2Entity(System.Data.DataRow row)
       {
            if(row == null) return null;

            MsgReceiver entity = new MsgReceiver();
                        
		    entity._receiverId= Cast.Int(row["MSG_RCV_ID"]);
		    entity._messageId= Cast.Int(row["MSG_ID"]);
		    entity._userId= Cast.Int(row["USR_ID"]);
		    entity._subscriberType= Cast.Bool(row["MSG_RCV_TYPE"]);
		    entity._sendTime= Cast.DateTime(row["MSG_RCV_SEND_TIME"]);
		    entity._receiveTime= Cast.DateTime(row["MSG_RCV_TIME"]);
		    entity._expireTime= Cast.DateTime(row["MSG_RCV_EXPIRE_TIME"]);
		    entity._readStatus= Cast.Enum<MessageReadStatus>(row["MSG_RCV_STATUS"]);
		    entity._note= Cast.String(row["MSG_RCV_NOTE"]);
		   
		   return entity;
       }
       
        /// <summary>
        ///  Map a DataTable's Rows to a List of MsgReceiver Entity.
        /// </summary>
        /// <returns></returns>
       public static IList<MsgReceiver> Row2Entity(System.Data.DataTable dt)
       {
            IList<MsgReceiver> list = null;
            if(dt != null && dt.Rows.Count>0)
           {
                 list = new List<MsgReceiver>(dt.Rows.Count);
                 foreach(System.Data.DataRow row in dt.Rows)
                 {
                    MsgReceiver  entity = Row2Entity(row);
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