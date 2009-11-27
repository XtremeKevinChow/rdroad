using System;
using System.Collections.Generic;
using System.Text;

namespace Magic.Security
{
    /// <summary>
    /// 用户组类型
    /// </summary>
    public enum UserGroupType
    {
        /// <summary>
        /// 管理员级
        /// </summary>
        Administrative = 0,
        /// <summary>
        /// 系统级
        /// </summary>
        System = 1,
        /// <summary>
        /// 用户级
        /// </summary>
        Users = 2
    }
}
