using System;
using System.Collections.Generic;
using System.Text;
using System.Reflection;
using System.Runtime.Remoting.Contexts;


namespace Magic.Security
{
    /// <summary>
    /// 指定类需要进行权限检查
    /// </summary>
    [AttributeUsage(AttributeTargets.Class)]
    public class PermissionCheckAttribute:System.Runtime.Remoting.Contexts.ContextAttribute
    {
        /// <summary>
        /// 
        /// </summary>
        public PermissionCheckAttribute()
            : base("PermissionCheckAttribute")
        {
        }
        /// <summary>
        /// 
        /// </summary>
        /// <param name="ctorMsg"></param>
        public override void GetPropertiesForNewContext(System.Runtime.Remoting.Activation.IConstructionCallMessage ctorMsg)
        {
            ctorMsg.ContextProperties.Add(new PermissionCheckProperty());
        }
    }
}
