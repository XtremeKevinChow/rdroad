using System;
using System.Collections.Generic;
using System.Text;

namespace Magic.Security
{
    /// <summary>
    /// 权限类型
    /// </summary>
    public enum OperationType
    {
        /// <summary>
        /// 系统
        /// </summary>
        System = 100,
        /// <summary>
        /// 模组
        /// </summary>
        Module = 0,
        /// <summary>
        /// 功能
        /// </summary>
        Feature = 1,
        /// <summary>
        /// 操作实体
        /// </summary>
        Entity = 2,
        /// <summary>
        /// 操作字段
        /// </summary>
        Field = 4,

        /// <summary>
        /// 报表
        /// </summary>
        Report =8
    }
}
