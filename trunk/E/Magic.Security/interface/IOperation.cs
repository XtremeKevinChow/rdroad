using System;
using System.Collections.Generic;
using System.Text;

namespace Magic.Security
{
    /// <summary>
    /// 操作的接口定义
    /// </summary>
    public interface IOperation
    {
        /// <summary>
        /// 名称
        /// </summary>
        string Name { get; set; }
        /// <summary>
        /// 描述
        /// </summary>
        string Description { get; set; }
        /// <summary>
        /// 类型
        /// </summary>
        OperationType Type { get; set; }

        /// <summary>
        /// 入口
        /// </summary>
        string Entry { get; set; }
    }
}
