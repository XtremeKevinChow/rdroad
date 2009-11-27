using System;
using System.Collections.Generic;
using System.Text;

namespace Magic.Security
{
    /// <summary>
    /// 权限认证的信息
    /// </summary>
    public class AuthorizationInformation
    {
        /// <summary>
        /// 权限认证信息
        /// </summary>
        public AuthorizationInformation()
        {
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="msg"></param>
        public AuthorizationInformation(string msg)
        {
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="operation"></param>
        public AuthorizationInformation(IOperation operation)
        {
        }
        /// <summary>
        /// 
        /// </summary>
        public string Message
        {
            get { return ""; }
        }
    }
}
