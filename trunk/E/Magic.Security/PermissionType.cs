using System;
using System.Collections.Generic;
using System.Text;

namespace Magic.Security
{
    /// <summary>
    /// 权限许可类型
    /// </summary>
    public enum PermissionType
    {
        /// <summary>
        /// 针对用户的访问权限
        /// </summary>
        OnUser = 1,
        /// <summary>
        /// 针对用户组的访问权限
        /// </summary>
        OnUserGroup = 2,
        /// <summary>
        /// 针对数据权限
        /// </summary>
        OnEntitySecurityKey = 4,        
    }
}
