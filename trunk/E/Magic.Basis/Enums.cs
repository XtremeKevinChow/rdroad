//*******************************************
// ** Description:  Data Access Object for Currency
// ** Author     :  Code generator
// ** Created    :   2008-6-30 20:22:21
// ** Modified   :
//*******************************************

namespace Magic.Basis
{
    /// <summary>
    /// ��Ӧ��״̬
    /// </summary>
    public enum VendorStatus
    {
        /// <summary>
        /// ��ɾ��
        /// </summary>
        Delete = 0,
        /// <summary>
        /// ����
        /// </summary>
        Disable = 1,
        /// <summary>
        /// ����
        /// </summary>
        Enable = 2,
    }

    /// <summary>
    /// ������״̬
    /// </summary>
    public enum LogisticsStatus
    {
        /// <summary>
        /// ��ɾ��
        /// </summary>
        Delete = 0,
        /// <summary>
        /// ����
        /// </summary>
        Disable = 1,
        /// <summary>
        /// ����
        /// </summary>
        Enable = 2,
    }

    /// <summary>
    /// ��ƷSKU״̬�����¼�״̬��
    /// </summary>
    public enum ItemSpecStatus
    {
        /// <summary>
        /// �¼�
        /// </summary>
        Disable = 0,
        /// <summary>
        /// �ϼ�
        /// </summary>
        Enable = 1,
    }

    /// <summary>
    /// ��Ʒ����
    /// </summary>
    public enum ItemType
    {
        /// <summary>
        /// ����
        /// </summary>
        AssistantItem = 0,
        /// <summary>
        /// ��ͨ��Ʒ
        /// </summary>
        NormalItem = 1,
        /// <summary>
        /// ϵ����Ʒ
        /// </summary>
        SerialItem = 2,
        /// <summary>
        /// ��װ��Ʒ
        /// </summary>
        SetItem = 3,
    }

    /// <summary>
    /// ����ڼ�����
    /// </summary>
    public enum INVPeriodType
    {
        /// <summary>
        /// �����Ȼ���
        /// </summary>
        Quarter = 10,
        /// <summary>
        /// ���·ݻ���
        /// </summary>
        Month = 20,
        /// <summary>
        /// �ֹ�����
        /// </summary>
        Manual = 30,
    }

    /// <summary>
    /// ����ڼ�״̬
    /// </summary>
    public enum INVPeriodStatus
    {
        /// <summary>
        /// ��ʼ״̬
        /// </summary>
        New = 10,
        /// <summary>
        /// ����״̬
        /// </summary>
        Open =20,
        /// <summary>
        /// �ر�״̬
        /// </summary>
        Close = 30,
    }

    public enum StockWarnMethod
    {
        None = 0,
    }
}
