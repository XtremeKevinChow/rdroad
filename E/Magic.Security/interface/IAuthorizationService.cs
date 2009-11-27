using System;
using System.Collections.Generic;
using System.Text;

namespace Magic.Security
{
    /// <summary>
    /// 权限认证服务接口
    /// </summary>
    public interface IAuthorizationService
    {
        /// <summary>
        /// 
        /// </summary>
        /// <param name="urser"></param>
        /// <param name="operation"></param>
        /// <param name="criteria"></param>
        void AddPermissionsToQuery(UserBase urser, string operation, Magic.Framework.ORM.IEntityQuery criteria);
        /// <summary>
        /// 
        /// </summary>
        /// <param name="urser"></param>
        /// <param name="operation"></param>
        /// <returns></returns>
        bool IsAllowed(UserBase urser, string operation);
        /// <summary>
        /// 
        /// </summary>
        /// <param name="user"></param>
        /// <param name="operation"></param>
        /// <param name="key"></param>
        /// <returns></returns>
        bool IsAllowed(UserBase user, string operation, IEntitySecurityKey key);
        /// <summary>
        /// 
        /// </summary>
        /// <param name="urser"></param>
        /// <param name="operation"></param>
        /// <returns></returns>
        AuthorizationInformation GetAuthorizationInformation(UserBase urser, string operation);
        /// <summary>
        /// 
        /// </summary>
        /// <param name="user"></param>
        /// <returns></returns>
        IList<IUserGroup> GetUserGroupsForUser(UserBase user);        
    }
}
