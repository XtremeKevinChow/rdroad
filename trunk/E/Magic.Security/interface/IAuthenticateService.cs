using System;
using System.Collections.Generic;
using System.Text;

namespace Magic.Security
{
    /// <summary>
    /// 用户验证服务接口
    /// </summary>
    public interface IAuthenticateService
    {
        /// <summary>
        /// 认证用户
        /// </summary>
        /// <param name="username"></param>
        /// <param name="password"></param>
        /// <returns></returns>
        bool AuthenticateUser(string username, string password);
        /// <summary>
        /// 登出系统
        /// </summary>
        void SignOut();
        /// <summary>
        /// 获取当前用户
        /// </summary>
        /// <returns></returns>
        UserBase GetCurrentUser();
        /// <summary>
        /// 当前用户是否已登陆
        /// </summary>
        /// <returns></returns>
        bool Authenticated();

    }
}
