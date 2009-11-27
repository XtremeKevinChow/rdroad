using System;
using System.Data;
using System.Collections.Generic;
using System.Text;
using Magic.Framework.Data;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Mapping;
using Magic.Framework.ORM.Query;
using Magic.Security;
using Magic.Framework.Utils;


namespace Magic.Sys
{
    [PermissionCheck]
    public class AuthorizationRepository : ContextBoundObject, IAuthorizationRepository
    {
        Session _session = null;
        
        public AuthorizationRepository(Session session)
        {
            _session = session;
        }

        #region IAuthorizationRepository Members
        /// <summary>
        /// 删除指定的用户
        /// </summary>
        /// <param name="user"></param>
        [Operation("/Administration/User/Remove", "删除用户")]
        public void RemoveUser(UserBase user)
        {
            //如果User是IEntity，则调用其本身的Delete方法，否则使用EntityManager删除它。
            if (user is IEntity)
            {
                IEntity userEntity = (IEntity)user;
                userEntity.Delete(this._session);
            }
            else
            {
                EntityManager.Delete(this._session, user.GetType(), user.UserId);
            }

        }

        /// <summary>
        /// 添加用户到用户组
        /// </summary>
        /// <param name="users"></param>
        /// <param name="userGroups"></param> 
        [Operation("/Administration/UserGroup/AddUser", "添加用户")]
        bool IAuthorizationRepository.AddUsersToGroups(UserBase[] users, IUserGroup[] userGroups)
        {
            if (ArrayIsNullOrEmpty(users) || ArrayIsNullOrEmpty(userGroups))
            {
                throw new ArgumentNullException("users,groups", "Users or UserGroups can not be null or empty!");
            }
            bool flag = false;
            foreach (UserGroup grp in userGroups)
            {
                flag = this.AddUsersToGroup(users, grp);
            }
            return flag;
        }

        /// <summary>
        /// 添加用户到用户组
        /// </summary>
        /// <param name="users"></param>
        /// <param name="groupIds"></param>
        /// <returns></returns>
        [Operation("/Administration/UserGroup/AddUser", "添加用户")]
        public bool AddUsersToGroups(UserBase[] users, int[] groupIds)
        {
            if (ArrayIsNullOrEmpty(users) || ArrayIsNullOrEmpty(groupIds))
            {
                throw new ArgumentNullException("users,groupIds", "Users or UserGroups can not be null or empty!");
            }
            bool flag = false;
            _session.BeginTransaction();
            try
            {
                foreach (int gid in groupIds)
                {
                    UserGroup grp = UserGroup.Retrieve(_session, gid);

                    flag = AddUsersToGroup(users, grp);
                    _session.Commit();

                }
            }
            catch
            {
                _session.Rollback();
                throw;
            }
            return flag;
        }



        public bool AddUsersToGroups(int[] userIds, UserGroup[] groups)
        {
            if (ArrayIsNullOrEmpty(userIds) || ArrayIsNullOrEmpty(groups))
            {
                throw new ArgumentNullException("userIds,groupIds", "Users or UserGroups can not be null or empty!");
            }
            bool flag = false;

            _session.BeginTransaction();
            try
            {
                foreach (int uid in userIds)
                {
                    User user = User.Retrieve(_session, uid);
                    foreach (UserGroup grp in groups)
                    {
                        if (!grp.UserIsInGroup(user, _session))
                        {
                            grp.AddUser(user, _session);
                        }
                    }
                }
                _session.Commit();
            }
            catch
            {
                _session.Rollback();
                throw;
            }

            return flag;
        }




        /// <summary>
        /// 创建UserGroup
        /// </summary>
        /// <param name="userGroup"></param>
        [Operation("/Administration/UserGroup/Create", "创建用户组")]
        public bool CreateUserGroup(IUserGroup userGroup)
        {
            if (userGroup != null)
            {
                return EntityManager.Create(_session, userGroup);
            }
            return false;
        }

     
        bool IAuthorizationRepository.DeleteUserGroup(IUserGroup userGroup, bool throwOnPopulatedGroup)
        {
           return DeleteUserGroup((UserGroup)userGroup, throwOnPopulatedGroup);
        }

        /// <summary>
        /// 删除UserGroup
        /// </summary>
        /// <param name="userGroup"></param>
        /// <param name="throwOnPopulatedGroup"></param>
        /// <returns></returns>
        [Operation("/Administration/UserGroup/Delete", "删除用户组")]
        public bool DeleteUserGroup(UserGroup userGroup, bool throwOnPopulatedGroup)
        {
            bool flag = true;
            if (userGroup != null)
            {
                IList<User> users = userGroup.FindUsersInGroup(_session);
                flag = !(users != null && users.Count > 0);
                if (flag)
                {
                    IList<UserGroup> grps = userGroup.FindChildGroups(this._session);
                    flag = !(grps != null && grps.Count > 0);
                }
                if (flag)
                {
                    flag = userGroup.Delete(_session);
                }
            }
            return flag;
        }

        /// <summary>
        /// 查找Group中的所有用户
        /// </summary>
        /// <param name="group"></param>
        /// <param name="usernameToMathc"></param>
        /// <returns></returns>
        public IList<UserBase> FindUsersInGroup(IUserGroup group, string usernameToMathc)
        {
               if (group == null)
                throw new ArgumentNullException("group");
              // string sql = @"select * from sys_user u inner join sys_user_to_group ug on u.user_id=ug.user_id where group_id=@Group_ID";
               string oql = @"select u.* from User u inner join UserToGroup g on u.UserId=g.UserId";
            UserGroup urgrp = (UserGroup)group;

            return _session.CreateObjectQuery(oql)
                .Attach(typeof(User))
                .Attach(typeof(UserToGroup))
                .Attach(typeof(UserBase))
                .And(Exp.Eq("g.GroupId",urgrp.GroupId))
                .List<UserBase>();

            
        }

        

        /// <summary>
        /// 获取可以分配给用户组的用户
        /// </summary>
        /// <param name="groupId"></param>
        /// <param name="userNameToMatch"></param>
        /// <returns></returns>
        public DataTable GetAssigningUserForGroup(int groupId, string userNameToMatch)
        {
            //string sql = @"select u.User_Id as Id, u.User_Name as UserName,u.Full_Name as FullName,u.Email as Email from SYS_User u  where u.user_id not in (select user_id from SYS_User_To_Group where Group_ID=@GROUP_ID) AND u.User_Name like @NameMatch";

            string oql = @"select u.UserId as UserId, u.UserName as UserName,u.FullName as FullName,u.Email as Email from User u  where u.UserId not in (select UserId from UserToGroup where GroupId=@GroupId) ";
            ObjectQuery query = _session.CreateObjectQuery(oql).Attach(typeof(User)).Attach(typeof(UserToGroup));
            query.SetValue("@GroupId", groupId, DbTypeInfo.Int32());

            string nameMatch = "%";
            if (!string.IsNullOrEmpty(userNameToMatch))
            {
                nameMatch = userNameToMatch + "%";
                query.And(Exp.Like("u.UserName", nameMatch));
                query.And(Exp.Like("u.FullName", nameMatch));
            }
            return query.DataSet().Tables[0];
            
        }


        /// <summary>
        /// 获取所有用户组
        /// </summary>
        /// <returns></returns>
        IList<IUserGroup> IAuthorizationRepository.GetAllGroups()
        {
            IList<UserGroup> grps = GetAllGroups();
            if (grps != null && grps.Count > 0)
            {
                IList<IUserGroup> igrps = new List<IUserGroup>(grps.Count);
                foreach (UserGroup grp in grps)
                {
                    igrps.Add(grp);
                }
                return igrps;
            }
            return null;
        }

        /// <summary>
        /// 获取所有用户组
        /// </summary>
        /// <returns></returns>
        [Operation("/Administration/UserGroup/ListAll", "列表所有用户组")]
        public IList<UserGroup> GetAllGroups()
        {
            EntityQuery qry = _session.CreateEntityQuery<UserGroup>();
            return qry.List<UserGroup>();
        }

        /// <summary>
        /// 获取指定用户所在的用户组
        /// </summary>
        /// <param name="user"></param>
        /// <returns></returns>
        public IList<IUserGroup> GetGroupsForUser(UserBase user)
        {
            string oql = @"select g.* from UserGroup g inner join UserToGroup ug on g.GroupId=ug.GroupId
                                where UserId=@UserId";

            IList<IUserGroup> grps = new List<IUserGroup>();
            ObjectQuery query = _session.CreateObjectQuery(oql).Attach(typeof(UserGroup)).Attach(typeof(UserToGroup));
            query.SetValue("@UserId", user.UserId, DbTypeInfo.Int32());
            IList<UserGroup> userGroups = query.List<UserGroup>();
            foreach (UserGroup ug in userGroups)
                grps.Add(ug);
            return grps;
        }

        /// <summary>
        /// 判断用户是否在用户组
        /// </summary>
        /// <param name="user"></param>
        /// <param name="group"></param>
        /// <returns></returns>
        public bool IsUserInGroup(UserBase user, IUserGroup group)
        {
            if (group == null)
                throw new ArgumentNullException("group");
            if (user == null)
                throw new ArgumentNullException("user");

            return IsUserInGroup(user.UserId, ((UserGroup)group).GroupId);
        }

        /// <summary>
        /// 判断用户是否在用户组
        /// </summary>
        /// <param name="userId"></param>
        /// <param name="groupId"></param>
        /// <returns></returns>
        public bool IsUserInGroup(int userId, int groupId)
        {
            string oql = @"select 1 from UserToGroup ";

            return
                _session.CreateObjectQuery(oql)
                .Attach(typeof(UserToGroup))
                .And(Exp.Eq("UserId", userId))
                .And(Exp.Eq("GroupId", groupId))
                .Count()>0;
        }

        /// <summary>
        /// 从用户组中删除用户
        /// </summary>
        /// <param name="users"></param>
        /// <param name="groups"></param>
        [Operation("/Administration/UserGroup/RemoveUser", "移出用户")]
        void IAuthorizationRepository.RemoveUsersFromGroups(UserBase[] users, IUserGroup[] userGroups)
        {
            if (ArrayIsNullOrEmpty(users) || ArrayIsNullOrEmpty(userGroups))
            {
                throw new ArgumentNullException("users,groups", "Users or UserGroups can not be null or empty!");
            }

            foreach (IUserGroup grp in userGroups)
            {
                if (!grp.RemoveUserFromGroup(users, this._session))
                {
                    throw new ApplicationException(string.Format("从用户组{0}中移出用户失败！", grp.Name));
                }
            }
        }

        /// <summary>
        /// 从用户组中删除用户
        /// </summary>
        /// <param name="users"></param>
        /// <param name="groups"></param>
        [Operation("/Administration/UserGroup/RemoveUser", "移出用户")]
        public void RemoveUsersFromGroups(UserBase[] users, int[] groupIds)
        {
            if (ArrayIsNullOrEmpty(users) || ArrayIsNullOrEmpty(groupIds))
            {
                throw new ArgumentNullException("users,groupIds", "Users or UserGroups can not be null or empty!");
            }
            this._session.BeginTransaction();
            try
            {
                foreach (int gid in groupIds)
                {
                    UserGroup group = UserGroup.Retrieve(_session, gid);
                    if (!group.RemoveUserFromGroup(users, this._session))
                    {
                        throw new ApplicationException(string.Format("从用户组{0}中移出用户失败！", group.Name));
                    }
                }
                _session.Commit();
            }
            catch
            {
                this._session.Rollback();
                throw;
            }
        }

        /// <summary>
        /// 从用户组中删除用户
        /// </summary>
        /// <param name="userIds"></param>
        /// <param name="groupIds"></param>
        [Operation("/Administration/UserGroup/RemoveUser", "移出用户")]
        public void RemoveUsersFromGroups(int[] userIds, int[] groupIds)
        {
            _session.BeginTransaction();
            try
            {
                foreach (int gid in groupIds)
                {
                    foreach (int uid in userIds)
                    {
                        //if (IsUserInGroup(uid, gid))
                        {
                            RemoveUserFromGroup(_session, gid, uid);
                        }
                    }
                }
                _session.Commit();
            }
            catch
            {
                _session.Rollback();
                throw;
            }
        }

        /// <summary>
        /// 用户组是否存在
        /// </summary>
        /// <param name="groupName"></param>
        /// <returns></returns>
        public bool UserGroupExists(string groupName)
        {

            {
                EntityQuery qry = _session.CreateEntityQuery<UserGroup>();
                qry.Where(Exp.Eq("Name", groupName));
                return qry.Count() > 0;
            }
        }

        public IUserGroup FindGroupByName(string userGroupName)
        {

            {
                EntityQuery qry = _session.CreateEntityQuery<UserGroup>();
                qry.Where(Exp.Eq("Name", userGroupName));
                IList<UserGroup> list = qry.List<UserGroup>();
                if (list.Count > 0)
                    return list[0];
                else
                    return null;
            }
        }

        /// <summary>
        /// 创建子用户组
        /// </summary>
        /// <param name="parentGroupName"></param>
        /// <param name="childGroup"></param>
        /// <returns></returns>
        public bool CreateChildGroup(string parentGroupName, IUserGroup childGroup)
        {
            UserGroup parent = FindGroupByName(parentGroupName) as UserGroup;

            return CreateChildGroup(parent, childGroup);
        }

        /// <summary>
        /// 创建子用户组
        /// </summary>
        /// <param name="parentGroup"></param>
        /// <param name="childGroup"></param>
        /// <returns></returns>
        public bool CreateChildGroup(IUserGroup parentGroup, IUserGroup childGroup)
        {
            UserGroup child = childGroup as UserGroup;
            UserGroup parent = parentGroup as UserGroup;
            if (child != null && parent != null)
            {
                child.ParentId = parent.GroupId;
                child.GroupLevel = (short)(parent.GroupLevel + (short)1);
                child.CreateBy = SecuritySession.CurrentUser.UserId;
                child.CreateTime = DateTime.Now;
                child.ModifyBy = child.CreateBy;
                child.ModifyTime = child.CreateTime;
                child.GroupType = parent.GroupType;

                return child.Create(this._session);
            }

            return false;
        }

        /// <summary>
        /// 获取当前用户在指定用户组上的所有祖先用户组
        /// </summary>
        /// <param name="user"></param>
        /// <param name="group"></param>
        /// <returns></returns>
        public IList<IUserGroup> GetAncestryAssociation(UserBase user, IUserGroup group)
        {
            return null;
        }

        public bool CreateOperation(IOperation operation)
        {
            throw new NotImplementedException();
        }

        public IOperation GetOperationByName(string operationName)
        {
            throw new NotImplementedException();
        }

        public IList<IOperation> GetOffspringOperations(string operationName)
        {
            throw new NotImplementedException();
        }

        public bool CreateChildOperation(string parentOperationName, IOperation operation)
        {
            throw new NotImplementedException();
        }

        bool IAuthorizationRepository.RemoveOperation(string operationName)
        {
            Operation op = Operation.Retrieve(_session, operationName);
            return RemoveOperation(op);
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="operation"></param>
        /// <returns></returns>
        public bool RemoveOperation(IOperation operation)
        {
            if (operation != null)
            {
                Operation op = (Operation)operation;

                if (Operation.HasChildren(_session, op.OperationId))
                {
                    throw new ApplicationException("当前操作有子操作，请先删除子操作");
                }

                return EntityManager.Delete(_session, typeof(Operation), op.OperationId);
            }
            return true;
        }

        #endregion

        private bool ArrayIsNullOrEmpty(Array array)
        {
            return array == null || array.Length == 0;
        }

        /// <summary>
        /// 获取可以分配给用户的用户组
        /// </summary>
        /// <param name="user"></param>
        /// <returns></returns>
        public IList<UserGroup> GetAssigningGroupsForUser(UserBase user)
        {
            IList<UserGroup> grps = new List<UserGroup>();
            //string sql = @"select * from sys_usergroup g where g.group_id not in (select group_id from sys_user_to_group where user_id=@USER_ID)";
            string oql = "select g.* from UserGroup g where g.GroupId not in(select GroupId from UserToGroup where UserId=@UserId)";

            return _session
                .CreateObjectQuery(oql)
                .Attach(typeof(UserGroup))
                .Attach(typeof(UserToGroup))
                .SetValue("@UserId", user.UserId, DbTypeInfo.Int32())
                .List<UserGroup>();
            
        }


        private bool AddUsersToGroup(UserBase[] users, UserGroup grp)
        {
            bool flag = false;

            if (grp != null)
            {
                foreach (UserBase user in users)
                {
                    if (!grp.UserIsInGroup(user, this._session))
                    {
                        if (user != null)
                        {
                            flag = grp.AddUser(user, this._session);
                        }
                    }
                }
            }
            return flag;
        }
        private bool AddUserToGroup(ISession session, int userId, int groupId)
        {
            UserToGroup utg = new UserToGroup();
            utg.CreateTime = DateTime.Now;
            utg.GroupId = groupId;
            utg.OperateBy = SecuritySession.CurrentUser.UserId;
            utg.UserId = userId;

            return utg.Create(session);

        }

        private bool RemoveUserFromGroup(ISession session, int groupId, int userId)
        {
            return UserToGroup.Delete(session, groupId, userId) > 0;
        }


        public DataTable GetAssignedPermssions(PermissionType type, int privilegerId)
        {
            string oql = @"select 
                            PermissionId as PermissionId,OperationId as OperationId, Type, GroupId as GroupId,UserId as UserId,IsAllow as IsAllow,CreateBy as CreateBy, CreateTime as CreateTime 
                            from Permission";
            ObjectQuery query=  _session.CreateObjectQuery(oql)
                 .Attach(typeof(Permission))
                 .And(Exp.Eq("Type", PermissionType.OnUserGroup));

            if (type == PermissionType.OnUser)
            {
                query.And(Exp.Eq("UserId",privilegerId));
            }
            else if (type == PermissionType.OnUserGroup)
            {
              query.And(Exp.Eq("GroupId",privilegerId));
            }
           
            return
               
                 query.DataSet().Tables[0];
           
     
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="groupIds"></param>
        /// <returns></returns>
        public DataTable GetAssignedPermissions(int[] groupIds)
        {
            if (groupIds == null || groupIds.Length == 0)
                throw new ArgumentNullException("groupIds");
            
            string oql = @"select 
                            PermissionId as PermissionId,OperationId as OperationId, Type, GroupID as GroupId,UserID as UserId,IsAllow as IsAllow,CreateBy as CreateBy, CreateTime as CreateTime 
                            from Permission";
           return 
               _session.CreateObjectQuery(oql)
                .Attach(typeof(Permission))
                .And(Exp.Eq("Type", PermissionType.OnUserGroup))
                .DataSet().Tables[0];

        }

        /// <summary>
        /// 分配权限
        /// </summary>
        /// <param name="type"></param>
        /// <param name="priviledgerIds"></param>
        /// <param name="operationIds"></param>
        /// <returns></returns>
        [Operation("/Administration/Permission/Assign","分配权限",OperationType.Feature)]
        public bool AssignPermissions(PermissionType type, int[] priviledgerIds, int[] operationIds)
        {

            if (priviledgerIds == null || priviledgerIds.Length == 0)
            {
                throw new ArgumentNullException("privilegerIds");
            }
            if (operationIds == null || operationIds.Length == 0)
            {
                throw new ArgumentNullException("operationIds");
            }
            bool flag = true;
            foreach (int privildegerId in priviledgerIds)
            {
                foreach (int opId in operationIds)
                {
                    if (!ExistsPermission(type, privildegerId, opId))
                    {
                        Permission prm = new Permission();
                        if (type == PermissionType.OnUserGroup)
                        {
                            prm.GroupId = privildegerId;
                        }
                        else if (type == PermissionType.OnUser)
                        {
                            prm.UserId = privildegerId;
                        }
                        prm.OperationId = opId;
                        prm.IsAllow = true;
                        prm.Type = type;
                        prm.CreateBy = SecuritySession.CurrentUser.UserId;
                        prm.CreateTime = DateTime.Now;
                        if(!prm.Create(_session))
                            flag = false;
                    }
                }
            }
            return flag;
        }

        /// <summary>
        /// 移除权限
        /// </summary>
        /// <param name="type"></param>
        /// <param name="privilegerIds"></param>
        /// <param name="operationIds"></param>
        /// <returns></returns>
        [Operation("/Administration/Permission/Remove","移除权限",OperationType.Feature)]
        public bool RemovePermission(PermissionType type, int[] privilegerIds, int[] operationIds)
        {
            if (privilegerIds == null || privilegerIds.Length == 0)
            {
                throw new ArgumentNullException("privilegerIds");
            }
            if (operationIds == null || operationIds.Length == 0)
            {
                throw new ArgumentNullException("operationIds");
            }
            bool flag = true;
            foreach (int privildegerId in privilegerIds)
            {
                foreach (int opId in operationIds)
                {
                  
                Permission prm = Permission.Retrieve(_session, type, privildegerId, opId);
                if (prm != null)
                    prm.Delete(_session);
                 
                }
            }
            return flag;
        }

        /// <summary>
        /// 判断权限是否存在
        /// </summary>
        /// <param name="type"></param>
        /// <param name="privilegerId"></param>
        /// <param name="operationId"></param>
        /// <returns></returns>
        public bool ExistsPermission(PermissionType type, int privilegerId, int operationId)
        {

            string oql = @"select 1 from Permission p inner join Operation o on p.OperationId=o.OperationId ";
            ObjectQuery query = _session
                .CreateObjectQuery(oql)
                .Attach(typeof(Permission))
                .Attach(typeof(Operation))
                .And(Exp.Eq("o.OperationId", operationId))
                .And(Exp.Eq("p.Type",type));

            if (type == PermissionType.OnUser)
            {
                query.And(Exp.Eq("p.UserId", privilegerId));
            }
            else if (type == PermissionType.OnUserGroup)
            {
                query.And(Exp.Eq("p.GroupId", privilegerId));
            }
            else
            {
                return false;
            }

            return query.Count() > 0;
        }

        /// <summary>
        /// 判断权限是否存在
        /// </summary>
        /// <param name="type"></param>
        /// <param name="privilegerId"></param>
        /// <param name="operationName"></param>
        /// <returns></returns>
        public bool ExistsPermission(PermissionType type, int privilegerId, string operationName)
        {
            string oql = @"select 1 from Permission p inner join Operation o on p.OperationId=o.OperationId ";
            ObjectQuery query = _session
                .CreateObjectQuery(oql)
                .Attach(typeof(Permission))
                .Attach(typeof(Operation))
                .And(Exp.Eq("o.Name", operationName))
                .And(Exp.Eq("p.Type", type));
         
            if (type == PermissionType.OnUser)
            {
                query.And(Exp.Eq("p.UserId", privilegerId));
            }
            else if (type == PermissionType.OnUserGroup)
            {
                query.And(Exp.Eq("p.GroupId", privilegerId));
            }
            else
            {
                return false;
            }

            return query.Count() > 0;
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="groupIds"></param>
        /// <param name="operationName"></param>
        /// <returns></returns>
        public bool ExistsPermission(int[] groupIds, string operationName)
        {
            if (groupIds == null )
                throw new ArgumentNullException("groupIds");
            if(groupIds.Length==0)
                return false;
            //string sql = @"select count(1) from sys_permission p join sys_operation o on p.operation_id=o.operation_id where o.name=@OP_NAME and p.Type=@TYPE";
            string oql = @"select 1 from Permission p inner join Operation o on p.OperationId=o.OperationId ";
            return _session
                .CreateObjectQuery(oql)
                .Attach(typeof(Permission))
                .Attach(typeof(Operation))
                .And(Exp.Eq("o.Name", operationName))
                .And(Exp.Eq("p.Type", PermissionType.OnUserGroup))
                .And(Exp.In("p.GroupId", groupIds))
                .Count() > 0;
            
        }
    }
}
