//*******************************************
// ** Author     :  Code Generator
// ** Created    :   2008-03-15 16:46:48
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
	///  消息类型
	/// </summary>
    [Table("SYS_MESSAGE_TYPE")]
	public partial class  MessageType : IEntity
	{
		#region Private Fields
		
		private int _msgTypeId;
		private string _typeName;
		
		#endregion

		#region Class Constructor
		/// <summary>
		/// default constructor for 消息类型
		/// </summary>
		public MessageType()
		{
		}
		#endregion

		#region Public Properties
		
		/// <summary>
		/// 消息类型ID
		/// </summary>
		[Column(DbType=StdDbType.Int32, Nullable=false,IsSequence=true,SequenceName="SEQ_SYS_Message_Type",IsPrimary=true,Name="MSG_TYPE_ID")]
		public virtual int MsgTypeId
		{
			get { return this._msgTypeId; }
			set { this._msgTypeId = value; }
		}
		
		/// <summary>
		/// 消息类型名称
		/// </summary>
		[Column(DbType=StdDbType.UnicodeString, Length=20, Nullable=true,Name="MSG_TYPE_NAME")]
		public virtual string TypeName
		{
			get { return this._typeName; }
			set { this._typeName = value; }
		}
		
		#endregion

		#region IEntity Members
		/// <summary>
		/// Create new 消息类型 entity
		/// <returns></returns>
		/// </summary>
		public bool Create(ISession session)
		{
			return EntityManager.Create(session,this);
		}

		/// <summary>
		/// Update 消息类型 entity's whole properties
		/// <returns></returns>
		/// </summary>
		public bool Update(ISession session)
		{
			return EntityManager.Update(session,this);
		}

		/// <summary>
		///Update 消息类型 entity's given properties
		/// <returns></returns>
		/// </summary>
		public bool Update(ISession session, params string[] propertyNames2Update)
		{
			return EntityManager.Update(session,this,propertyNames2Update);
		}

		/// <summary>
		///Delete this 消息类型 entity
		/// <returns></returns>
		/// </summary>
		public bool Delete(ISession session)
		{
			return EntityManager.Delete(session,this);
		}
		
		#endregion
		
		#region Class Static Members

		/// <summary>
		///Retrive a 消息类型 Entity from DB
		/// <returns></returns>
		/// </summary>
		public static MessageType Retrieve(ISession session  ,int msgTypeId  )
		{
			return EntityManager.Retrieve<MessageType>(session,msgTypeId);
		}

		 /// <summary>
        /// Delete by Primary key
        /// </summary>
        /// <returns></returns>
        public static bool Delete(ISession session  ,int msgTypeId  )
        {
           return EntityManager.Delete<MessageType>(session,msgTypeId);
        }
       
        /// <summary>
        ///  Map a DataRow to a MessageType Entity.
        /// </summary>
        /// <returns></returns>
       public static MessageType Row2Entity(System.Data.DataRow row)
       {
            if(row == null) return null;

            MessageType entity = new MessageType();
                        
		    entity._msgTypeId= Cast.Int(row["MSG_TYPE_ID"]);
		    entity._typeName= Cast.String(row["MSG_TYPE_NAME"]);
		   
		   return entity;
       }
       
        /// <summary>
        ///  Map a DataTable's Rows to a List of MessageType Entity.
        /// </summary>
        /// <returns></returns>
       public static IList<MessageType> Row2Entity(System.Data.DataTable dt)
       {
            IList<MessageType> list = null;
            if(dt != null && dt.Rows.Count>0)
           {
                 list = new List<MessageType>(dt.Rows.Count);
                 foreach(System.Data.DataRow row in dt.Rows)
                 {
                    MessageType  entity = Row2Entity(row);
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