
namespace Magic.Sys
{
    /// <summary>
    /// 消息的可访问性, derive from short.
    /// </summary>
    public enum MessageAccessibility
    {
        /// <summary>
        /// 公共
        /// </summary>
        Public = 0,
        /// <summary>
        /// 仅订阅者
        /// </summary>
        Subscriber = 1
    }

    /// <summary>
    /// 阅读状态
    /// </summary>
    public enum MessageReadStatus
    {
        /// <summary>
        /// 未读
        /// </summary>
        Unread = 0,
        /// <summary>
        /// 已读
        /// </summary>
        Read = 1,
        /// <summary>
        /// 过期
        /// </summary>
        Expired = 2
    }

    public enum MessageStatus
    {
        /// <summary>
        /// 新的消息
        /// </summary>
        New = 0,
        /// <summary>
        /// 发送
        /// </summary>
        Sent = 1,
        /// <summary>
        /// 置顶
        /// </summary>
        Elite = 2,
        /// <summary>
        /// 过期
        /// </summary>
        Expired = 3
    }

    public enum ParameterType
    {
        /// <summary>
        /// 单个值
        /// </summary>
        Value = 1,
        /// <summary>
        /// 组合
        /// </summary>
        Group = 2
    }
}