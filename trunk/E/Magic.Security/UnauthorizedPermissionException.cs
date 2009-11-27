using System;
using System.Collections.Generic;
using System.Text;

namespace Magic.Security
{
    /// <summary>
    /// 未授权的异常
    /// </summary>
    public class UnauthorizedException:Exception
    {
        /// <summary>
        /// 给定描述
        /// </summary>
        /// <param name="permissionDescription"></param>
        public UnauthorizedException(string permissionDescription)
            :base(string.Format("对不起，您没有[{0}]的权限！", permissionDescription))
        {
        }
        /// <summary>
        /// 默认描述为："该操作"
        /// </summary>
        public UnauthorizedException()
            : this("该操作")
        {
        }
        
    }
}
