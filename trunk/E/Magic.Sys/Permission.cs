//*******************************************
// ** Author     :  Code Generator
// ** Created    :   2008-02-25 10:07:30
// ** Modified   :
//*******************************************

using System;
using System.Collections.Generic;
using Magic.Framework.Data;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Mapping;
using Magic.Framework.ORM.Query;
using Magic.Security;

namespace Magic.Sys
{
	/// <summary>
	///  权限许可
	/// </summary>
    [Table("SYS_PERMISSION")]
	public partial class  Permission : IEntity
	{
		#region Private Fields
		
		private int _permissionId;
		private int _operationId;
		private PermissionType _type;
		private int _userId;
		private int _groupId;
		private bool _isAllow;
		private DateTime _createTime;
		private int _createBy;
        private Operation _operation = null;
        		
		#endregion

		#region Class Constructor
		/// <summary>
		/// default constructor for 权限许可
		/// </summary>
		public Permission()
		{
		}
		#endregion

		#region Public Properties
		
		/// <summary>
		/// 许可ID
		/// </summary>
        [Column(DbType = StdDbType.Int32, Nullable = false, IsSequence = true, SequenceName = "SEQ_SYS_PERMISSION", IsPrimary = true, Name = "PERM_ID")]
		public virtual int PermissionId
		{
			get { return this._permissionId; }
			set { this._permissionId = value; }
		}
		
		/// <summary>
		/// 操作ID
		/// </summary>
		[Column(DbType=StdDbType.Int32, Nullable=false,Name="OPT_ID")]
		public virtual int OperationId
		{
			get { return this._operationId; }
			set { this._operationId = value; }
		}
		
		/// <summary>
		/// 权限类型
		/// </summary>
		[Column(DbType=StdDbType.Int16, Nullable=true,Name="PERM_TYPE")]
		public virtual PermissionType Type
		{
			get { return this._type; }
			set { this._type = value; }
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
		/// 用户组ID
		/// </summary>
		[Column(DbType=StdDbType.Int32, Nullable=false,Name="UGP_ID")]
		public virtual int GroupId
		{
			get { return this._groupId; }
			set { this._groupId = value; }
		}
		
		/// <summary>
		/// 允许/禁止
		/// </summary>
		[Column(DbType=StdDbType.Bool, Nullable=false,Name="PERM_ALLOW")]
		public virtual bool IsAllow
		{
			get { return this._isAllow; }
			set { this._isAllow = value; }
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
		/// 创建人
		/// </summary>
		[Column(DbType=StdDbType.Int32, Nullable=false,Name="CREATE_BY")]
		public virtual int CreateBy
		{
			get { return this._createBy; }
			set { this._createBy = value; }
		}

        /// <summary>
        /// 操作
        /// </summary>
        public Operation Operation
        {
            get { return _operation; }
            set 
            {
                if (value != null)
                {
                    if (value.OperationId == _operationId)
                        _operation = value;
                    else
                        throw new ArgumentException("the OperationId of assigning Operation value is not equal to the Permssion's OperationId");
                }
            }
        }
		#endregion

		#region IEntity Members
		/// <summary>
		/// Create new 权限许可 entity
		/// <returns></returns>
		/// </summary>
		public bool Create(ISession session)
		{
			return EntityManager.Create(session,this);
		}

		/// <summary>
		/// Update 权限许可 entity's whole properties
		/// <returns></returns>
		/// </summary>
		public bool Update(ISession session)
		{
			return EntityManager.Update(session,this);
		}

		/// <summary>
		///Update 权限许可 entity's given properties
		/// <returns></returns>
		/// </summary>
		public bool Update(ISession session, params string[] propertyNames2Update)
		{
			return EntityManager.Update(session,this,propertyNames2Update);
		}

		/// <summary>
		///Delete this 权限许可 entity
		/// <returns></returns>
		/// </summary>
		public bool Delete(ISession session)
		{
			return EntityManager.Delete(session,this);
		}
		
		#endregion
		
		#region Class Static Members

		/// <summary>
		///Retrive a 权限许可 Entity from DB
		/// <returns></returns>
		/// </summary>
		public static Permission Retrieve(ISession session  ,int permissionId  )
		{
			return EntityManager.Retrieve<Permission>(session,permissionId);
		}

        /// <summary>
        /// 
        /// </summary>
        /// <param name="session"></param>
        /// <param name="PERM_TYPE"></param>
        /// <param name="privilegerId"></param>
        /// <param name="operationId"></param>
        /// <returns></returns>
        public static Permission Retrieve(ISession session, PermissionType type, int privilegerId, int operationId)
        {
            EntityQuery qry = session.CreateEntityQuery<Permission>();
            qry.And(Exp.Eq("Type", type));
            qry.And(Exp.Eq("OperationId", operationId));
            if (type == PermissionType.OnUser)
            {
                qry.And(Exp.Eq("UserId", privilegerId));
            }
            else if (type == PermissionType.OnUserGroup)
            {
                qry.And(Exp.Eq("GroupId", privilegerId));
            }
            else
            {
                return null;
            }
            IList<Permission> list = qry.List<Permission>();
            if (list != null && list.Count > 0)
                return list[0];
            return null;
                
        }
		 /// <summary>
        /// Delete by Primary key
        /// </summary>
        /// <returns></returns>
        public static bool Delete(ISession session  ,int permissionId  )
        {
           return EntityManager.Delete<Permission>(session,permissionId);
        }
       
        /// <summary>
        ///  Map a DataRow to a Permission Entity.
        /// </summary>
        /// <returns></returns>
       public static Permission Row2Entity(System.Data.DataRow row)
       {
            if(row == null) return null;

            Permission entity = new Permission();
                        
		    if(!row.IsNull("PERM_ID"))
		    	    entity._permissionId= (int)(row["PERM_ID"]);
		    if(!row.IsNull("OPT_ID"))
		    	    entity._operationId= (int)(row["OPT_ID"]);
		    if(!row.IsNull("PERM_TYPE"))
		    	    entity._type= (PermissionType)Enum.Parse(typeof(PermissionType),(row["PERM_TYPE"]).ToString());
		    if(!row.IsNull("USR_ID"))
		    	    entity._userId= (int)(row["USR_ID"]);
		    if(!row.IsNull("UGP_ID"))
		    	    entity._groupId= (int)(row["UGP_ID"]);
		    if(!row.IsNull("PERM_ALLOW"))
		    	    entity._isAllow= (bool)(row["PERM_ALLOW"]);
		    if(!row.IsNull("CREATE_TIME"))
		    	    entity._createTime= (DateTime)(row["CREATE_TIME"]);
		    if(!row.IsNull("CREATE_BY"))
		    	    entity._createBy= (int)(row["CREATE_BY"]);
		   
		   return entity;
       }
		#endregion


        #region
        /// <summary>
        /// 获取赋给权限主体的操作
        /// </summary>
        /// <param name="session"></param>
        /// <param name="PERM_TYPE"></param>
        /// <param name="privilegerId"></param>
        /// <returns></returns>
       public static IList<Operation> GetPrevilegedOperations(ISession session, PermissionType prmType, int privilegerId, OperationType opType)
       {
           string oql = "select o.* from Operation o inner join Permission p on o.OperationId=p.OperationId";
         ObjectQuery qry =  session.CreateObjectQuery(oql)
               .Attach(typeof(Operation))
               .Attach(typeof(Permission));
                 if (prmType == PermissionType.OnUser)
                {
                    qry.And(Exp.Eq("p.UserId", privilegerId));
                }
                 else if (prmType == PermissionType.OnUserGroup)
                {
                    qry.And(Exp.Eq("p.GroupId", privilegerId));
                }
                 qry.And(Exp.Eq("o.Type", opType));
                 qry.And(Exp.Eq("o.Status", OperationStatus.Active));
                 return qry.List<Operation>();
       }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="session"></param>
        /// <param name="prmType"></param>
        /// <param name="privilegerId"></param>
        /// <param name="opType"></param>
        /// <param name="parentOperationId"></param>
        /// <returns></returns>
       public static IList<Operation> GetPrevilegedChildOperations(ISession session, PermissionType prmType, int privilegerId, OperationType opType, int parentOperationId)
       {
           string oql = "select o.* from Operation o inner join Permission p on o.OperationId=p.OperationId";
           ObjectQuery qry = session.CreateObjectQuery(oql)
                 .Attach(typeof(Operation))
                 .Attach(typeof(Permission));
           if (prmType == PermissionType.OnUser)
           {
               qry.And(Exp.Eq("p.UserId", privilegerId));
           }
           else if (prmType == PermissionType.OnUserGroup)
           {
               qry.And(Exp.Eq("p.GroupId", privilegerId));
           }
           qry.And(Exp.Eq("o.Type", opType));
           qry.And(Exp.Eq("o.Status", OperationStatus.Active));
           qry.And(Exp.Eq("o.ParentId", parentOperationId));
           return qry.List<Operation>();
       }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="session"></param>
        /// <param name="userId"></param>
        /// <param name="opType"></param>
        /// <param name="isOnGroup"></param>
        /// <returns></returns>
       public static IList<Operation> GetPrevilegedUserGroupOperations(ISession session, int userId, OperationType opType, bool isOnGroup)
       {
           string oql = @"select o.* from Operation o inner join Permission p on o.OperationId=p.OperationId 
                            inner join UserToGroup ug on p.GroupId=ug.GroupId";
           ObjectQuery qry = session.CreateObjectQuery(oql)
                 .Attach(typeof(Operation))
                 .Attach(typeof(Permission))
                 .Attach(typeof(UserToGroup))
                 .OrderBy("o.SeqNo");
           qry.And(Exp.Eq("p.Type", PermissionType.OnUserGroup));
           qry.And(Exp.Eq("ug.UserId", userId));
           qry.And(Exp.Eq("o.Type", opType));
           qry.And(Exp.Eq("o.Status", OperationStatus.Active));
         
           return qry.List<Operation>();
       }

       /// <summary>
       /// 
       /// </summary>
       /// <param name="session"></param>
       /// <param name="prmType"></param>
       /// <param name="privilegerId"></param>
       /// <param name="opType"></param>
       /// <param name="parentOperationId"></param>
       /// <returns></returns>
       public static IList<Operation> GetPrevilegedUserGroupChildOperations(ISession session,  int userId, OperationType opType, int parentOperationId)
       {
           string oql = @"select o.* from Operation o inner join Permission p on o.OperationId=p.OperationId
                                 inner join UserToGroup ug on p.GroupId=ug.GroupId
                                ";
           ObjectQuery qry = session.CreateObjectQuery(oql)
                 .Attach(typeof(Operation))
                 .Attach(typeof(Permission))
                   .Attach(typeof(UserToGroup))
                   .OrderBy("o.SeqNo");

           qry.And(Exp.Eq("p.Type", PermissionType.OnUserGroup));
           qry.And(Exp.Eq("ug.UserId", userId));
           qry.And(Exp.Eq("o.Type", opType));
           qry.And(Exp.Eq("o.Status", OperationStatus.Active));
           qry.And(Exp.Eq("o.ParentId", parentOperationId));
           return qry.List<Operation>();
       }
        #endregion
    }
}