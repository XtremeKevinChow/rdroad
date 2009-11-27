using System;
using System.Collections.Generic;
using System.Text;

namespace Magic.Security
{
    /// <summary>
    /// 基于数据权限控制的Key
    /// </summary>
    public interface IEntitySecurityKey
    {
        /// <summary>
        /// 值
        /// </summary>
        string Value { get; }
        /// <summary>
        /// 父Key
        /// </summary>
        IEntitySecurityKey ParentKey { get; }
    }
}
