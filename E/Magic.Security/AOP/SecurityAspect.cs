using System;
using System.Collections.Generic;
using System.Text;
using System.Diagnostics;
using System.Reflection;
using System.Runtime.Remoting.Activation;
using System.Runtime.Remoting.Contexts;
using System.Runtime.Remoting.Messaging;

namespace Magic.Security
{
    internal class SecurityAspect:IMessageSink
    {
        private IMessageSink m_Next;

        internal SecurityAspect(IMessageSink next)
        {
            m_Next = next;
        }

        #region IMessageSink Members

        public IMessageCtrl AsyncProcessMessage(IMessage msg, IMessageSink replySink)
        {
           throw new NotImplementedException("not support the Async process message!");
        }

        public IMessageSink NextSink
        {
            get { return m_Next; }
        }

        /// <summary>
        /// 同步处理消息
        /// </summary>
        /// <param name="msg"></param>
        /// <returns></returns>
        public IMessage SyncProcessMessage(IMessage msg)
        {
             Preprocess(msg);
            IMessage methodReturn = m_Next.SyncProcessMessage(msg);
            return methodReturn;
        }

        #endregion

         #region 自定义的 AOP 方法
        private void Preprocess(IMessage msg)
        {
            //只处理方法调用
            if (!(msg is IMethodMessage)) return;

            //获取方法中定义的 Task 属性，交给权限检查类去检查
            IMethodMessage call = msg as IMethodMessage;
            MethodBase mb = call.MethodBase;
            object[] attrObj = mb.GetCustomAttributes(typeof(OperationAttribute), false);
            Debug.WriteLine("current method Name : " + mb.Name);
            if (attrObj != null && attrObj.Length>0)
            {
                OperationAttribute attr = (OperationAttribute)attrObj[0];

                if (!string.IsNullOrEmpty(attr.Name))
                {
                    if (!SecuritySession.IsAllowed(attr.Name))
                        throw new UnauthorizedException(attr.Description);
                }
                Debug.WriteLine("Operation Name: " + attr.Name);
            }
        }


        #endregion
    }
}
