using System;
using System.Collections.Generic;
using System.Text;

namespace Magic.Security
{
    /// <summary>
    /// 运行时的权限会话
    /// </summary>
    public sealed class SecuritySession
    {       
        private static IAuthenticateService _authenticateService = null;
        private static IAuthorizationService _azService = null;

        private static string _loginUrl = string.Empty;
        private static string _defaultUrl = string.Empty;

        private SecuritySession()
        {
        }

        /// <summary>
        /// 构造一个权限认证Session，需要提供一个认证服务对象
        /// </summary>
        static SecuritySession()
        {
            IAuthenticateService authenticateService = SecurityServiceProvider.GetAuthenticateService();
            _authenticateService = authenticateService;

            IAuthorizationService azService = SecurityServiceProvider.GetAuthorizationService();
            _azService = azService;
        }
        
        #region ISecuritySession Members
        /// <summary>
        /// 向查询添加权限控制
        /// </summary>
        /// <param name="operation"></param>
        /// <param name="criteria"></param>
        public static void AddPermissionsToQuery(string operation, Magic.Framework.ORM.IEntityQuery criteria)
        {
            _azService.AddPermissionsToQuery(CurrentUser, operation, criteria);
        }

        /// <summary>
        /// 向查询添加权限控制
        /// </summary>
        /// <param name="urser"></param>
        /// <param name="operation"></param>
        /// <param name="criteria"></param>
        public static void AddPermissionsToQuery(UserBase urser, string operation, Magic.Framework.ORM.IEntityQuery criteria)
        {
            _azService.AddPermissionsToQuery(urser, operation, criteria);
        }


        /// <summary>
        /// 是否允许操作
        /// </summary>
        /// <param name="operation"></param>
        /// <returns></returns>
        public static bool IsAllowed(string operation)
        {
            return _azService.IsAllowed(CurrentUser, operation);
        }

        /// <summary>
        ///  是否允许操作
        /// </summary>
        /// <param name="user"></param>
        /// <param name="operation"></param>
        /// <returns></returns>
        public static bool IsAllowed(UserBase user, string operation)
        {
            return _azService.IsAllowed(user, operation);
        }

        /// <summary>
        /// 是否允许操作某个指定的对象
        /// </summary>
        /// <param name="operation"></param>
        /// <param name="key"></param>
        /// <returns></returns>
        public static bool IsAllowed(string operation, IEntitySecurityKey key)
        {
            return _azService.IsAllowed(CurrentUser, operation, key);
        }

        /// <summary>
        /// 用户是否有操作某个指定对象的权限
        /// </summary>
        /// <param name="user"></param>
        /// <param name="operation"></param>
        /// <param name="key"></param>
        /// <returns></returns>
        public static bool IsAllowed(UserBase user, string operation, IEntitySecurityKey key)
        {
            return _azService.IsAllowed(user, operation, key);
        }

        /// <summary>
        /// 当前用户
        /// </summary>
        public static UserBase CurrentUser
        {
            get 
            {
                return _authenticateService.GetCurrentUser();
            }
        }

        /// <summary>
        /// 验证当前用户，并登入系统
        /// </summary>
        /// <param name="userName"></param>
        /// <param name="password"></param>
        public static bool Authenticate(string userName, string password)
        {
            return _authenticateService.AuthenticateUser(userName, password);
        }

        /// <summary>
        /// 判断当前用户是否已经被验证
        /// </summary>
        /// <returns></returns>
        public static bool Authenticated()
        {
            return _authenticateService.Authenticated();
        }

        /// <summary>
        /// 当前用户登出系统
        /// </summary>
        public static void SignOut()
        {
            _authenticateService.SignOut();
        }

        /// <summary>
        /// 获取当前用户所属的用户组
        /// </summary>
        /// <returns></returns>
        public static IList<IUserGroup> GetGroupsForUser()
        {
            return _azService.GetUserGroupsForUser(CurrentUser);
        }
        
      
        #endregion
    }
}
