//*******************************************
// ** Author     :  Code Generator
// ** Created    :   2008-02-19 19:04:41
// ** Modified   :
//*******************************************

using System;
using System.Collections.Generic;
using Magic.Framework.Data;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Mapping;
using Magic.Framework.ORM.Query;

namespace Magic.Sys
{
	
	/// <summary>
	///  用户组内的用户
	/// </summary>
    [Table("SYS_USER_TO_GROUP")]
	public partial class  UserToGroup : IEntity
	{
		#region Private Fields
		
		private int _groupId;
		private int _userId;
		private int _operateBy;
		private DateTime _createTime;
		private string _note;
		
		#endregion

		#region Class Constructor
		/// <summary>
		/// default constructor for 用户组内的用户
		/// </summary>
		public UserToGroup()
		{
		}
		#endregion

		#region Public Properties
		
		/// <summary>
		/// 用户组ID
		/// </summary>
		[Column(DbType=StdDbType.Int32, Nullable=false,IsPrimary=true,Name="UGP_ID")]
		public virtual int GroupId
		{
			get { return this._groupId; }
			set { this._groupId = value; }
		}
		
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
		/// 操作人
		/// </summary>
		[Column(DbType=StdDbType.Int32, Nullable=false,Name="CREATE_BY")]
		public virtual int OperateBy
		{
			get { return this._operateBy; }
			set { this._operateBy = value; }
		}
		
		/// <summary>
		/// 添加时间
		/// </summary>
		[Column(DbType=StdDbType.DateTime, Nullable=true,Name="CREATE_TIME")]
		public virtual DateTime CreateTime
		{
			get { return this._createTime; }
			set { this._createTime = value; }
		}
		
		/// <summary>
		/// 备注
		/// </summary>
		[Column(DbType=StdDbType.UnicodeString, Length=250, Nullable=true,Name="UGP_NOTE")]
		public virtual string Note
		{
			get { return this._note; }
			set { this._note = value; }
		}
		
		#endregion

		#region IEntity Members
		/// <summary>
		/// Create new 用户组内的用户 entity
		/// <returns></returns>
		/// </summary>
		public bool Create(ISession session)
		{
			return EntityManager.Create(session,this);
		}

		/// <summary>
		/// Update 用户组内的用户 entity's whole properties
		/// <returns></returns>
		/// </summary>
		public bool Update(ISession session)
		{
			return EntityManager.Update(session,this);
		}

		/// <summary>
		///Update 用户组内的用户 entity's given properties
		/// <returns></returns>
		/// </summary>
		public bool Update(ISession session, params string[] propertyNames2Update)
		{
			return EntityManager.Update(session,this,propertyNames2Update);
		}

		/// <summary>
		///Delete this 用户组内的用户 entity
		/// <returns></returns>
		/// </summary>
		public bool Delete(ISession session)
		{
			return EntityManager.Delete(session,this);
		}
		
		#endregion
		
		#region Class Static Members

		/// <summary>
		///Retrive a 用户组内的用户 Entity from DB
		/// <returns></returns>
		/// </summary>
		public static UserToGroup Retrieve(ISession session  ,int groupId   ,int userId  )
		{
			return EntityManager.Retrieve<UserToGroup>(session, new string[]{"GroupId" ,"UserId"},  new object[]{groupId ,userId});
		}

		 /// <summary>
        /// Delete by Primary key
        /// </summary>
        /// <returns></returns>
        public static int Delete(ISession session  ,int groupId   ,int userId  )
        {
           return EntityManager.Delete<UserToGroup>(session, new string[]{"GroupId" ,"UserId"},  new object[]{groupId ,userId});
        }
		#endregion

	}
}