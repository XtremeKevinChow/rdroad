namespace Magic.Notification
{
    /// <summary>
    /// ��Ϣ״̬
    /// </summary>
    public enum NotificationStatus
    {
        Cancel = 0,
        /// <summary>
        /// δ����
        /// </summary>
        New = 10,
        /// <summary>
        /// �����У����ŷ���ʱ����Ҫ��һ��ʱ��֮����ܲ�ѯ�����ͽ���ɹ����
        /// </summary>
        Open = 15,
        /// <summary>
        /// �������
        /// </summary>
        Close = 20,
    }

    /// <summary>
    /// ��Ϣ�Ĵ�����
    /// </summary>
    public enum NotificationResultStatus
    {
        /// <summary>
        /// δ����
        /// </summary>
        New = 0,
        /// <summary>
        /// ȫ���������ͣ�ʧ��
        /// </summary>
        AllError = 10,
        /// <summary>
        /// ���ִ������ͣ�ʧ��
        /// </summary>
        PartError = 20,
        /// <summary>
        /// ȫ���������ͣ��ɹ�
        /// </summary>
        AllSuccess = 30,
    }

    /// <summary>
    /// ��Ϣ������
    /// </summary>
    public enum NotificationType
    {
        /// <summary>
        /// ϵͳ��Ϣ
        /// </summary>
        Sys = 1,
        /// <summary>
        /// �ʼ���Ϣ
        /// </summary>
        Mail = 2,
        /// <summary>
        /// ������Ϣ
        /// </summary>
        SMS = 3,
    }

    /// <summary>
    /// ��Ϣ����ֵ����������
    /// </summary>
    public enum NotificationDataType
    {
        /// <summary>
        /// NotificationParam���ϣ���������һ�����ֵ�ļ��ϣ�
        /// </summary>
        ListOfNotificationParam = 10,
        /// <summary>
        /// �ַ�������
        /// </summary>
        String = 20,
        /// <summary>
        /// ��������
        /// </summary>
        Int = 30,
        /// <summary>
        /// ��������
        /// </summary>
        Decimal = 40,
        /// <summary>
        /// ����ʱ������
        /// </summary>
        DateTime = 50,
    }
}