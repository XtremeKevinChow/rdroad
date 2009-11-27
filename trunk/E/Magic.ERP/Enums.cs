namespace Magic.ERP
{
    /// <summary>
    /// ���ݵ�ǩ��״̬��ǩ�˽��
    /// </summary>
    public enum ApproveStatus
    {
        /// <summary>
        /// ����
        /// </summary>
        Reject = -1,
        /// <summary>
        /// δǩ��
        /// </summary>
        UnApprove = 0,
        /// <summary>
        /// ͨ��
        /// </summary>
        Approve = 1,
    }

    /// <summary>
    /// ������״̬
    /// </summary>
    public enum DeliverStatus
    {
        /// <summary>
        /// ȡ��
        /// </summary>
        Cancel = 0,
        /// <summary>
        /// ����
        /// </summary>
        New = 1,
        /// <summary>
        /// ��������ǩ��
        /// </summary>
        Release = 2,
        /// <summary>
        /// �����
        /// </summary>
        Open = 3,
        /// <summary>
        /// �����
        /// </summary>
        Close = 4,
    }

    /// <summary>
    /// ���ӵ�״̬
    /// </summary>
    public enum InterchangeStatus
    {
        /// <summary>
        /// �½�
        /// </summary>
        New = 1,
        /// <summary>
        /// ����
        /// </summary>
        Release = 2,
        Open = 3,
        /// <summary>
        /// �����
        /// </summary>
        Close = 4,
    }

    /// <summary>
    /// �ջ���״̬
    /// </summary>
    public enum ReceiveStatus
    {
        /// <summary>
        /// �½�
        /// </summary>
        New = 1,
        /// <summary>
        /// ����
        /// </summary>
        Release = 2,
        /// <summary>
        /// 
        /// </summary>
        Open = 3,
        /// <summary>
        /// �����
        /// </summary>
        Close = 4,
    }

    /// <summary>
    /// �ɹ�����״̬
    /// </summary>
    public enum POStatus
    {
        /// <summary>
        /// �½�
        /// </summary>
        New = 1,
        /// <summary>
        /// ����
        /// </summary>
        Release = 2,
        /// <summary>
        /// �����
        /// </summary>
        Close = 3,
    }

    /// <summary>
    /// �ɹ�������״̬
    /// </summary>
    public enum POLineStatus
    {
        /// <summary>
        /// ��Ч״̬
        /// </summary>
        Open = 1,
        /// <summary>
        /// ȡ��
        /// </summary>
        Cancel = 2,
        /// <summary>
        /// �����
        /// </summary>
        Close = 3,
    }

    /// <summary>
    /// ����̵㡢������״̬
    /// </summary>
    public enum INVCheckStatus
    {
        /// <summary>
        /// �½�
        /// </summary>
        New = 1,
        /// <summary>
        /// ȷ��
        /// </summary>
        Confirm = 2,
        /// <summary>
        /// ����
        /// </summary>
        Release = 3,
        /// <summary>
        /// ���
        /// </summary>
        Close = 4,
    }

    /// <summary>
    /// �ƿⵥ״̬
    /// </summary>
    public enum WHTransferStatus
    {
        /// <summary>
        /// �½�
        /// </summary>
        New = 1,
        /// <summary>
        /// ����
        /// </summary>
        Release = 2,
        /// <summary>
        /// ���ƿ�
        /// </summary>
        Open = 3,
        /// <summary>
        /// ���
        /// </summary>
        Close = 4,
    }

    /// <summary>
    /// ���ײ��������
    /// </summary>
    public enum TransStepType
    {
        /// <summary>
        /// һ��������ֻ������������
        /// </summary>
        Single = 1,
        /// <summary>
        /// һ�������а�������������ͣ������в�ͬ������Ŀ����ʹ�ò�ͬ�Ľ������ͽ��н���
        /// </summary>
        MultiSelect = 2,
    }

    /// <summary>
    /// �������ȡ�������ʹ���
    /// </summary>
    public enum TransTypeFrom
    {
        /// <summary>
        /// �������л�ȡ
        /// </summary>
        ConfigValue = 1,
        /// <summary>
        /// �ӽӿ��л�ȡ
        /// </summary>
        InterfaceValue = 2,
    }

    /// <summary>
    /// �������ȡ��������
    /// </summary>
    public enum TransQtyFrom
    {
        /// <summary>
        /// ȡ�ϸ�����
        /// </summary>
        QualifiedQty = 1,
        /// <summary>
        /// ȡ���ϸ�����
        /// </summary>
        UnQualifiedQty = 2,
    }

    public enum TransProperty
    {
        /// <summary>
        /// ���
        /// </summary>
        In = 1,
        /// <summary>
        /// ����
        /// </summary>
        Out = -1,
    }

    /// <summary>
    /// �������ȡ���ײ�λ��������Ϣ
    /// </summary>
    public enum TransLocationFrom
    {
        /// <summary>
        /// �ӽ��ף��ֿ���ձ��ж�ȡ
        /// </summary>
        ConfigValue = 1,
        /// <summary>
        /// �ӽӿڶ�ȡ
        /// </summary>
        InterfaceValue = 2,
    }

    /// <summary>
    /// ����ʱ�����������
    /// </summary>
    public enum OrderRuleDefPrecision
    {
        Year = 1,
        Month = 2,
        Date = 3,
        Hour = 4,
        Minute = 5,
    }

    /// <summary>
    /// �ִ��ء��ֿ����򡢻��ܵ�״̬
    /// </summary>
    public enum WHStatus
    {
        /// <summary>
        /// ɾ��
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
    /// ���׼۸���Դ����
    /// </summary>
    public enum TransTypePriceSource
    {
        /// <summary>
        /// ����¼�۸�
        /// </summary>
        NoPrice = 0,
        /// <summary>
        /// ����Դ���ݻ�ȡ
        /// </summary>
        FromSourceOrder = 1,
        /// <summary>
        /// ȡ�ƶ�ƽ����
        /// </summary>
        FromMovAvgCost = 3,
        /// <summary>
        /// �ӿⷿ����������л�ȡ
        /// </summary>
        FromAreaCfg = 4,
    }

    public enum StockInStatus
    {
        New = 1,
        Release = 2,
        Open = 3,
        Close = 4,
        /// <summary>
        /// ֻ�в�Ʒ���������״̬��״̬���̣�New->Release->Confirm->��ǩ����->Open->Close
        /// </summary>
        Confirm = 5,
    }

    /// <summary>
    /// �̵㷽ʽ
    /// </summary>
    public enum INVCheckType
    {
        /// <summary>
        /// ����
        /// </summary>
        Explicit = 1,
        /// <summary>
        /// ����
        /// </summary>
        Implicit = 2,
    }

    public enum CRMSNStatus
    {
        /// <summary>
        /// �˻�
        /// </summary>
        Return = -5,
        /// <summary>
        /// ȡ��
        /// </summary>
        Cancel = -1,
        /// <summary>
        /// �½�
        /// </summary>
        New = 0,
        /// <summary>
        /// �Ѵ�ӡ��������
        /// </summary>
        Distributing = 10,
        /// <summary>
        /// �ϼ���ɣ��������ϣ�������ϼ����
        /// </summary>
        PickedOut = 15,
        /// <summary>
        /// �˻���� 
        /// </summary>
        Checked = 20,
        /// <summary>
        /// ��װ���
        /// </summary>
        Packaged = 30,
        /// <summary>
        /// ���ֻ���
        /// </summary>
        PartExchange = 39,
        /// <summary>
        /// �������
        /// </summary>
        Interchanged = 40,
    }
    public enum CRMSNLineStatus
    {
        /// <summary>
        /// �˻�
        /// </summary>
        Return = -5,
        /// <summary>
        /// ȡ��
        /// </summary>
        Cancel = -1,
        /// <summary>
        /// ����
        /// </summary>
        Natural = 0,
        /// <summary>
        /// �Ѵ�ӡ��������
        /// </summary>
        Distributing = 10,
        /// <summary>
        /// �˻���� 
        /// </summary>
        Checked = 20,
        /// <summary>
        /// ��װ���
        /// </summary>
        Packaged = 30,
        /// <summary>
        /// �������
        /// </summary>
        Interchanged = 40,
        /// <summary>
        /// ����
        /// </summary>
        Exchange = 60,
        /// <summary>
        /// ����
        /// </summary>
        Replenishment = 70,
    }

    /// <summary>
    /// �˻���״̬
    /// </summary>
    public enum ReturnStatus
    {
        /// <summary>
        /// 
        /// </summary>
        New = 1,
        Release = 2,
        Open = 3,
        Close = 4,
    }

    /// <summary>
    /// ���۶�������
    /// </summary>
    public enum SaleOrderType
    {
        /// <summary>
        /// ��׼����
        /// </summary>
        Standard = 10,
        /// <summary>
        /// �˻���
        /// </summary>
        Return = 40,
        /// <summary>
        /// �Ź�����
        /// </summary>
        MajorCustomer = 15,
        /// <summary>
        /// Ԥ�۶���
        /// </summary>
        PreSale = 5,
        /// <summary>
        /// ��������
        /// </summary>
        Exchange = 20,
    }

    /// <summary>
    /// �ɹ��˻�״̬
    /// </summary>
    public enum POReturnStatus
    {
        /// <summary>
        /// �½�
        /// </summary>
        New = 1,
        /// <summary>
        /// ����
        /// </summary>
        Release = 2,
        /// <summary>
        /// ������
        /// </summary>
        Open = 3,
        /// <summary>
        /// ���
        /// </summary>
        Close = 4,
    }
}