//*******************************************
// ** Author     :  Code Generator
// ** Created    :   2008-05-28 09:08:28
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
	///  用户的Dashlet
	/// </summary>
    [Table("SYS_USER_DASHLET")]
	public partial class  UserDashlet : IEntity
	{
		#region Private Fields
		
		private int _userId;
		private int _dashpageId;
		private int _dashletId;
		private DashStatus _status;
		
		#endregion

		#region Class Constructor
		/// <summary>
		/// default constructor for 用户的Dashlet
		/// </summary>
		public UserDashlet()
		{
		}
		#endregion

		#region Public Properties
		
		/// <summary>
		/// 用户ID
		/// </summary>
		[Column(DbType=StdDbType.Int32, Nullable=false,IsPrimary=true,Name="USR_ID")]
		public virtual int UserId
		{
			get { return this._userId; }
			set { this._userId = value; }
		}
		
		/// <summary>
		/// DashPage_ID
		/// </summary>
		[Column(DbType=StdDbType.Int32, Nullable=false,IsPrimary=true,Name="DP_ID")]
		public virtual int DashpageId
		{
			get { return this._dashpageId; }
			set { this._dashpageId = value; }
		}
		
		/// <summary>
		/// Dashlet ID
		/// </summary>
		[Column(DbType=StdDbType.Int32, Nullable=false,IsPrimary=true,Name="DL_ID")]
		public virtual int DashletId
		{
			get { return this._dashletId; }
			set { this._dashletId = value; }
		}
		
		/// <summary>
		/// 状态
		/// </summary>
		[Column(DbType=StdDbType.Int16, Nullable=true,Name="UDL_STATUS")]
		public virtual DashStatus Status
		{
			get { return this._status; }
			set { this._status = value; }
		}
		
		#endregion

		#region IEntity Members
		/// <summary>
		/// Create new 用户的Dashlet entity
		/// <returns></returns>
		/// </summary>
		public bool Create(ISession session)
		{
			return EntityManager.Create(session,this);
		}

		/// <summary>
		/// Update 用户的Dashlet entity's whole properties
		/// <returns></returns>
		/// </summary>
		public bool Update(ISession session)
		{
			return EntityManager.Update(session,this);
		}

		/// <summary>
		///Update 用户的Dashlet entity's given properties
		/// <returns></returns>
		/// </summary>
		public bool Update(ISession session, params string[] propertyNames2Update)
		{
			return EntityManager.Update(session,this,propertyNames2Update);
		}

		/// <summary>
		///Delete this 用户的Dashlet entity
		/// <returns></returns>
		/// </summary>
		public bool Delete(ISession session)
		{
			return EntityManager.Delete(session,this);
		}
		
		#endregion
		
		#region Class Static Members

		/// <summary>
		///Retrive a 用户的Dashlet Entity from DB
		/// <returns></returns>
		/// </summary>
		public static UserDashlet Retrieve(ISession session  ,int userId   ,int dashpageId   ,int dashletId  )
		{
			return EntityManager.Retrieve<UserDashlet>(session, new string[]{"UserId" ,"DashpageId" ,"DashletId"},  new object[]{userId ,dashpageId ,dashletId});
		}

		 /// <summary>
        /// Delete by Primary key
        /// </summary>
        /// <returns></returns>
        public static int Delete(ISession session  ,int userId   ,int dashpageId   ,int dashletId  )
        {
           return EntityManager.Delete<UserDashlet>(session, new string[]{"UserId" ,"DashpageId" ,"DashletId"},  new object[]{userId ,dashpageId ,dashletId});
        }
       
         /// <summary>
        /// 是否存在指定条件的用户的Dashlet
        /// </summary>
        /// <param name="session"></param>
        /// <param name="propertyNames"></param>
        /// <param name="propertyValues"></param>
        /// <returns></returns>
        public static bool Exists(ISession session, string[] propertyNames, object[] propertyValues)
        {
            if (propertyNames != null && propertyValues != null && propertyNames.Length>0 && propertyValues.Length == propertyNames.Length)
            {
                EntityQuery qry = session.CreateEntityQuery<UserDashlet>();
                for (int i = 0; i < propertyNames.Length; i++)
                {
                    qry.And(Exp.Eq(propertyNames[i], propertyValues[i]));
                }
                return qry.Count() > 0;
                    
            }
            return false;
        } 
       
        /// <summary>
        ///  Map a DataRow to a UserDashlet Entity.
        /// </summary>
        /// <returns></returns>
       public static UserDashlet Row2Entity(System.Data.DataRow row)
       {
            if(row == null) return null;

            UserDashlet entity = new UserDashlet();
                        
		    entity._userId= Cast.Int(row["USR_ID"]);
		    entity._dashpageId= Cast.Int(row["DP_ID"]);
		    entity._dashletId= Cast.Int(row["DL_ID"]);
		    entity._status= Cast.Enum<DashStatus>(row["UDL_STATUS"]);
		   
		   return entity;
       }
       
        /// <summary>
        ///  Map a DataTable's Rows to a List of UserDashlet Entity.
        /// </summary>
        /// <returns></returns>
       public static IList<UserDashlet> Row2Entity(System.Data.DataTable dt)
       {
            IList<UserDashlet> list = null;
            if(dt != null && dt.Rows.Count>0)
           {
                 list = new List<UserDashlet>(dt.Rows.Count);
                 foreach(System.Data.DataRow row in dt.Rows)
                 {
                    UserDashlet  entity = Row2Entity(row);
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