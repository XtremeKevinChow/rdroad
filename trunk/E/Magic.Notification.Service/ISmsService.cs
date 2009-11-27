using System;
using System.Collections.Generic;
using System.Text;

namespace Magic.Notification.Service
{
    /// <summary>
    /// SMS短信服务的接口
    /// </summary>
    public interface ISmsService
    {
        /// <summary>
        /// 发送短信
        /// </summary>
        /// <param name="UserId"></param>
        /// <param name="Password"></param>
        /// <param name="Msg"></param>
        /// <param name="Destnumbers"></param>
        /// <returns></returns>
        object SendMessage(string spAccountId, string spPassword, string Msg, string destinationPhone);

        /// <summary>
        /// 根据返回值判断是否成功，true：成功，false：失败，failMessage 表示错误信息
        /// </summary>
        /// <param name="sendMessageResult"></param>
        /// <param name="failMessage"></param>
        /// <returns></returns>
        bool ParseSendResult(object sendMessageResult, out string errorCode, out string failMessage);

    }
}
