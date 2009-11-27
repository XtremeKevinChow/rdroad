namespace Magic.ERP
{
    using System;
    using Magic.Framework.ORM;

    /// <summary>
    /// ��ǩ�˵ĵ���
    /// </summary>
    public interface IApprovable : IEntity
    {
        /// <summary>
        /// ��������
        /// </summary>
        string OrderTypeCode { get; }

        /// <summary>
        /// ���ݺ���
        /// </summary>
        string OrderNumber { get; }

        /// <summary>
        /// ǩ�˽��
        /// </summary>
        ApproveStatus ApproveResult { get; set; }

        /// <summary>
        /// ǩ����
        /// </summary>
        int ApproveUser { get; set; }

        /// <summary>
        /// ǩ��ʱ��
        /// </summary>
        DateTime ApproveTime { get; set; }

        /// <summary>
        /// ǩ��˵��
        /// </summary>
        string ApproveNote { get; set; }

        /// <summary>
        /// <para>ǩ����ɺ�Ļص�����</para>
        /// <para>���sessionλ��ǩ����ɴ��������֮�⣬ǩ�˵������Ѿ��ɹ��ύ��ʧ�ܻع���</para>
        /// <para>����ⵥ�ݣ���ǩ����ɺ���ʹ��һ���µ�session���г���⽻�ף��رձ�ǩ�˵ĵ��ݣ���Щ����Ӧ�÷�����������У�������ǩ�˵�session��������</para>
        /// </summary>
        /// <param name="session">���session��ֻ���ģ��������������������ݣ�ֻ�ܲ�ѯ����</param>
        void PostApprove(ISession session);

        /// <summary>
        /// <para>ǩ�˹����еĻص�����</para>
        /// <para>���session����ǩ����ɴ��������֮�У���ǩ�˵��ݵ�״̬���µȣ�Ӧ�÷�����������д���</para>
        /// </summary>
        /// <param name="session"></param>
        void OnApprove(ISession session);

        /// <summary>
        /// ���ݴ�����
        /// </summary>
        int CreateUser { get; }

        /// <summary>
        /// ���ݴ���ʱ��
        /// </summary>
        DateTime CreateTime { get; }
    }
}