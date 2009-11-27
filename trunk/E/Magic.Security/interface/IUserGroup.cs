using System;
using System.Collections.Generic;
using System.Text;
using Magic.Framework.ORM;

namespace Magic.Security
{
    /// <summary>
    /// 用户组的接口定义
    /// </summary>
    public interface IUserGroup:IEntity
    {
        
        /// <summary>
        /// 名称
        /// </summary>
        string Name { get; set; }

        /// <summary>
        /// 描述
        /// </summary>
        string Description{get;set;}

        /// <summary>
        /// 类型
        /// </summary>
        UserGroupType GroupType { get; set; }

        /// <summary>
        /// 父群组
        /// </summary>
        IUserGroup ParentGroup { get; }

        /// <summary>
        /// 添加用户
        /// </summary>
        /// <returns></returns>
        bool AddUser(UserBase user, ISession session);

        /// <summary>
        /// 用户组内的用户
        /// </summary>
        /// <returns></returns>
        IList<UserBase> FindUsersInGroup(ISession session);

        /// <summary>
        /// 返回所有子孙用户组
        /// </summary>
        /// <returns></returns>
        IList<IUserGroup> FindOffspringGroups(ISession session);

        /// <summary>
        /// 返回所有子用户组
        /// </summary>
        /// <returns></returns>
        IList<IUserGroup> FindChildGroups(ISession session);

        /// <summary>
        /// 判断用户是否在用户组内
        /// </summary>
        /// <returns></returns>
        bool UserIsInGroup(UserBase user, ISession session);

        /// <summary>
        /// 查找所有祖先用户组
        /// </summary>
        /// <returns></returns>
        IList<IUserGroup> FindAncestryGroup(ISession session);

        /// <summary>
        /// 从用户组内移出用户
        /// </summary>
        /// <returns></returns>
        bool RemoveUserFromGroup(IList<UserBase> users, ISession session);

        
    }
}
