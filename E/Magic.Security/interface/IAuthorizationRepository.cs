using System;
using System.Collections.Generic;
using System.Text;

namespace Magic.Security
{
    /// <summary>
    /// 权限认证的存储类接口
    /// </summary>
	public interface IAuthorizationRepository
    {
        #region 用户组

        /// <summary>
        /// 添加用户到群组
        /// </summary>
        bool AddUsersToGroups(UserBase[] users, IUserGroup[] userGroups);

        /// <summary>
        /// 创建用户组
        /// </summary>
        bool CreateUserGroup(IUserGroup userGroup);

        /// <summary>
        /// 根据用户组
        /// </summary>
        /// <returns></returns>
        bool DeleteUserGroup(IUserGroup userGroup, bool throwOnPopulatedGroup);

        /// <summary>
        /// 查找用户组内的用户
        /// </summary>
        /// <returns></returns>
        IList<UserBase> FindUsersInGroup(IUserGroup group, string usernameToMathc);

        /// <summary>
        /// 获取所有的用户组
        /// </summary>
        /// <returns></returns>
        IList<IUserGroup> GetAllGroups();
 
        /// <summary>
        /// 获取指定用户所属的所有用户组
        /// </summary>
        /// <returns></returns>
        IList<IUserGroup> GetGroupsForUser(UserBase user);

         /// <summary>
        /// 判断用户是否在给定的用户组类
        /// </summary>
        /// <returns></returns>
        bool IsUserInGroup(UserBase user, IUserGroup group);
                
        /// <summary>
        /// 从多个用户组内删除多个用户
        /// </summary>
        /// <param name="users"></param>
        /// <param name="groups"></param>
        void RemoveUsersFromGroups(UserBase[] users, IUserGroup[] groups);

        /// <summary>
        /// 判断指定组名称的用户组是否存在
        /// </summary>
        /// <param name="groupName"></param>
        /// <returns></returns>
        bool UserGroupExists(string groupName);

        /// <summary>
        /// 查找指定组名称的用户组
        /// </summary>
        /// <param name="userGroupName"></param>
        /// <returns></returns>
        IUserGroup FindGroupByName(string userGroupName);

        /// <summary>
        /// 创建子用户组
        /// </summary>
        /// <returns></returns>
        bool CreateChildGroup(string parentGroupName, IUserGroup childGroup);

        /// <summary>
        /// 创建子用户组
        /// </summary>
        /// <param name="parentGroup"></param>
        /// <param name="childGroup"></param>
        /// <returns></returns>
        bool CreateChildGroup(IUserGroup parentGroup, IUserGroup childGroup);

        /// <summary>
        /// 获取指定用户从指定用户组而上的所有祖先用户组
        /// </summary>
        /// <returns></returns>
        IList<IUserGroup> GetAncestryAssociation(UserBase user, IUserGroup group);
        #endregion

        #region 用户
        /// <summary>
        /// 删除用户
        /// </summary>
        /// <param name="user"></param>
        void RemoveUser(UserBase user);
        #endregion

        #region Operation
        /// <summary>
        /// 创建操作
        /// </summary>
        /// <param name="operation"></param>
        /// <returns></returns>
        bool CreateOperation(IOperation operation);
        /// <summary>
        /// 根据操作名称查找操作
        /// </summary>
        /// <param name="operationName"></param>
        /// <returns></returns>
        IOperation GetOperationByName(string operationName);
        /// <summary>
        /// 查找指定操作的所有子孙操作
        /// </summary>
        /// <param name="operationName"></param>
        /// <returns></returns>
        IList<IOperation> GetOffspringOperations(string operationName);
        /// <summary>
        /// 创建子操作
        /// </summary>
        /// <param name="parentOperationName"></param>
        /// <param name="operation"></param>
        /// <returns></returns>
        bool CreateChildOperation(string parentOperationName, IOperation operation);

        /// <summary>
        /// 删除操作
        /// </summary>
        /// <param name="operationName"></param>
        bool RemoveOperation(string operationName);

        /// <summary>
        /// 删除操作
        /// </summary>
        /// <param name="operation"></param>
        bool RemoveOperation(IOperation operation);
        #endregion
      
	}
}
