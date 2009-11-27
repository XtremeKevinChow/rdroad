namespace Magic.Notification
{
    /// <summary>
    /// 消息状态
    /// </summary>
    public enum NotificationStatus
    {
        Cancel = 0,
        /// <summary>
        /// 未处理
        /// </summary>
        New = 10,
        /// <summary>
        /// 处理中（短信发送时，需要隔一段时间之后才能查询到发送结果成功与否）
        /// </summary>
        Open = 15,
        /// <summary>
        /// 处理完成
        /// </summary>
        Close = 20,
    }

    /// <summary>
    /// 消息的处理结果
    /// </summary>
    public enum NotificationResultStatus
    {
        /// <summary>
        /// 未处理
        /// </summary>
        New = 0,
        /// <summary>
        /// 全部处理（发送）失败
        /// </summary>
        AllError = 10,
        /// <summary>
        /// 部分处理（发送）失败
        /// </summary>
        PartError = 20,
        /// <summary>
        /// 全部处理（发送）成功
        /// </summary>
        AllSuccess = 30,
    }

    /// <summary>
    /// 消息的类型
    /// </summary>
    public enum NotificationType
    {
        /// <summary>
        /// 系统消息
        /// </summary>
        Sys = 1,
        /// <summary>
        /// 邮件消息
        /// </summary>
        Mail = 2,
        /// <summary>
        /// 短信消息
        /// </summary>
        SMS = 3,
    }

    /// <summary>
    /// 消息参数值的数据类型
    /// </summary>
    public enum NotificationDataType
    {
        /// <summary>
        /// NotificationParam集合（即包含下一层参数值的集合）
        /// </summary>
        ListOfNotificationParam = 10,
        /// <summary>
        /// 字符串类型
        /// </summary>
        String = 20,
        /// <summary>
        /// 整数类型
        /// </summary>
        Int = 30,
        /// <summary>
        /// 浮点类型
        /// </summary>
        Decimal = 40,
        /// <summary>
        /// 日期时间类型
        /// </summary>
        DateTime = 50,
    }
}