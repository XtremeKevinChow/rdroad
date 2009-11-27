//*******************************************
// ** Author     :  Code Generator
// ** Created    :   2008-03-14 21:19:23
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
	///  消息订阅用户
	/// </summary>
    [Table("SYS_MSG_SUBSCRIBER")]
	public partial class  MsgSubscriber : IEntity
	{
		#region Private Fields
		
		private int _subscriberId;
		private string _tmplCode;
		private int _userId;
		private int _groupId;
		private bool _isGroup;
		private DateTime _subscribeTime;
		
		#endregion

		#region Class Constructor
		/// <summary>
		/// default constructor for 消息订阅用户
		/// </summary>
		public MsgSubscriber()
		{
		}
		#endregion

		#region Public Properties
		
		/// <summary>
		/// 订阅ID
		/// </summary>
        [Column(DbType = StdDbType.Int32, Nullable = false, IsSequence = true, SequenceName = "SEQ_SYS_MSG_SUBSCRIBER", IsPrimary = true, Name = "MSG_SUB_ID")]
		public virtual int SubscriberId
		{
			get { return this._subscriberId; }
			set { this._subscriberId = value; }
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
		/// 用户ID
		/// </summary>
		[Column(DbType=StdDbType.Int32, Nullable=true,Name="USR_ID")]
		public virtual int UserId
		{
			get { return this._userId; }
			set { this._userId = value; }
		}
		
		/// <summary>
		/// 用户组ID
		/// </summary>
		[Column(DbType=StdDbType.Int32, Nullable=true,Name="UGP_ID")]
		public virtual int GroupId
		{
			get { return this._groupId; }
			set { this._groupId = value; }
		}
		
		/// <summary>
		/// 是否用户组
		/// </summary>
		[Column(DbType=StdDbType.Bool, Nullable=false,Name="IS_GROUP")]
		public virtual bool IsGroup
		{
			get { return this._isGroup; }
			set { this._isGroup = value; }
		}
		
		/// <summary>
		/// 订阅时间
		/// </summary>
		[Column(DbType=StdDbType.DateTime, Nullable=true,Name="MSG_SUB_TIME")]
		public virtual DateTime SubscribeTime
		{
			get { return this._subscribeTime; }
			set { this._subscribeTime = value; }
		}
		
		#endregion

		#region IEntity Members
		/// <summary>
		/// Create new 消息订阅用户 entity
		/// <returns></returns>
		/// </summary>
		public bool Create(ISession session)
		{
			return EntityManager.Create(session,this);
		}

		/// <summary>
		/// Update 消息订阅用户 entity's whole properties
		/// <returns></returns>
		/// </summary>
		public bool Update(ISession session)
		{
			return EntityManager.Update(session,this);
		}

		/// <summary>
		///Update 消息订阅用户 entity's given properties
		/// <returns></returns>
		/// </summary>
		public bool Update(ISession session, params string[] propertyNames2Update)
		{
			return EntityManager.Update(session,this,propertyNames2Update);
		}

		/// <summary>
		///Delete this 消息订阅用户 entity
		/// <returns></returns>
		/// </summary>
		public bool Delete(ISession session)
		{
			return EntityManager.Delete(session,this);
		}
		
		#endregion
		
		#region Class Static Members

		/// <summary>
		///Retrive a 消息订阅用户 Entity from DB
		/// <returns></returns>
		/// </summary>
		public static MsgSubscriber Retrieve(ISession session  ,int subscriberId  )
		{
			return EntityManager.Retrieve<MsgSubscriber>(session,subscriberId);
		}

		 /// <summary>
        /// Delete by Primary key
        /// </summary>
        /// <returns></returns>
        public static bool Delete(ISession session  ,int subscriberId  )
        {
           return EntityManager.Delete<MsgSubscriber>(session,subscriberId);
        }
       
        /// <summary>
        ///  Map a DataRow to a MsgSubscriber Entity.
        /// </summary>
        /// <returns></returns>
       public static MsgSubscriber Row2Entity(System.Data.DataRow row)
       {
            if(row == null) return null;

            MsgSubscriber entity = new MsgSubscriber();
                        
		    entity._subscriberId= Cast.Int(row["MSG_SUB_ID"]);
		    entity._tmplCode= Cast.String(row["MSG_TMPL_CODE"]);
		    entity._userId= Cast.Int(row["USR_ID"]);
		    entity._groupId= Cast.Int(row["UGP_ID"]);
		    entity._isGroup= Cast.Bool(row["IS_GROUP"]);
		    entity._subscribeTime= Cast.DateTime(row["MSG_SUB_TIME"]);
		   
		   return entity;
       }
       
        /// <summary>
        ///  Map a DataTable's Rows to a List of MsgSubscriber Entity.
        /// </summary>
        /// <returns></returns>
       public static IList<MsgSubscriber> Row2Entity(System.Data.DataTable dt)
       {
            IList<MsgSubscriber> list = null;
            if(dt != null && dt.Rows.Count>0)
           {
                 list = new List<MsgSubscriber>(dt.Rows.Count);
                 foreach(System.Data.DataRow row in dt.Rows)
                 {
                    MsgSubscriber  entity = Row2Entity(row);
                    if(entity != null)
                    {
                        list.Add(entity);
                    }
                 }
           }  
          return list; 
       }

        /// <summary>
        /// 是否存在指定条件的实体
        /// </summary>
        /// <param name="session"></param>
        /// <param name="conditonPropertyNames"></param>
        /// <param name="conditionPropertyValues"></param>
        /// <returns></returns>
       public static bool Exists(ISession session, string[] conditonPropertyNames, object[] conditionPropertyValues)
       {
           EntityQuery query = session.CreateEntityQuery<MsgSubscriber>();
           if (conditionPropertyValues != null && conditonPropertyNames != null && conditionPropertyValues.Length == conditonPropertyNames.Length)
           {
               for (int i = 0; i < conditionPropertyValues.Length; i++)
               {
                   query.And(Exp.Eq(conditonPropertyNames[i], conditionPropertyValues[i]));
               }
           }

           return query.Count() > 0;
       }
		#endregion

        #region public methods
        /// <summary>
        /// 获取某个消息模板需要发送消息的最终用户
        /// </summary>
        /// <param name="session"></param>
        /// <param name="tmplCode"></param>
        /// <returns></returns>
       public static IList<User> GetSendingUser4Subscriber(ISession session, string tmplCode)
       {
           IList<UserGroup> subsGroups = 
               session.CreateObjectQuery(@"select g.* from MsgSubscriber s inner join UserGroup g on s.GroupId=g.GroupId  and s.IsGroup=1")
               .Attach(typeof(MsgSubscriber))
               .Attach(typeof(UserGroup))
               .And(Exp.Eq("s.TmplCode",tmplCode))
               .List<UserGroup>();
           List<User> userList = new List<User>();
           if (subsGroups != null && subsGroups.Count > 0)
           {
              
               foreach (UserGroup group in subsGroups)
               {
                   IList<User> users = group.FindUsersInGroup(session);
                   if(users != null)
                        userList.AddRange(users);
               }
           }

           IList<User> subsUsers =
               session.CreateObjectQuery(@"select u.* from MsgSubscriber s inner join User u on s.UserId=u.UserId and s.IsGroup=0")
               .Attach(typeof(MsgSubscriber))
               .Attach(typeof(User))
               .And(Exp.Eq("s.TmplCode",tmplCode))
               .List<User>();
           if (subsUsers != null && subsUsers.Count > 0)
           {
               userList.AddRange(subsUsers);
           }
           return userList;
       }
        #endregion
    }
}