using System;
using System.Collections.Generic;
using System.Text;
using System.Diagnostics;
using System.Reflection;
using System.Runtime.Remoting.Messaging;
using System.Runtime.Remoting.Contexts;
using System.Runtime.Remoting.Activation;

namespace Magic.Security
{
    /// <summary>
    /// 权限检查使用的上下文属性(ContextProperty)
    /// </summary>
    public class PermissionCheckProperty:IContextProperty,IContributeObjectSink
    {
        #region IContributeObjectSink Members
        /// <summary>
        /// 获取用于权限检查使用的消息钩子
        /// </summary>
        /// <param name="obj"></param>
        /// <param name="nextSink"></param>
        /// <returns></returns>
        public IMessageSink GetObjectSink(MarshalByRefObject obj, IMessageSink nextSink)
        {
            return new SecurityAspect(nextSink);
        }

        #endregion

        #region IContextProperty Members
        /// <summary>
        /// 未实现
        /// </summary>
        /// <param name="newContext"></param>
        public void Freeze(Context newContext)
        {
            
        }
        /// <summary>
        /// return true
        /// </summary>
        /// <param name="newCtx"></param>
        /// <returns></returns>
        public bool IsNewContextOK(Context newCtx)
        {
            return true;
        }

        /// <summary>
        /// 名称
        /// </summary>
        public string Name
        {
            get { return "PermissionCheckAttribute"; }
        }

        #endregion
    }
}
