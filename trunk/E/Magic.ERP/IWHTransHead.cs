namespace Magic.ERP
{
    using System;
    using System.Collections.Generic;
    using Magic.Framework.ORM;

    /// <summary>
    /// ��Ҫ���п�潻�׵ĵ���
    /// </summary>
    public interface IWHTransHead
    {
        /// <summary>
        /// Ԥ�������˿��
        /// </summary>
        bool PreLockStock
        {
            get;
        }

        /// <summary>
        /// ��������
        /// </summary>
        string OrderTypeCode { get; }

        /// <summary>
        /// ���ݺ���
        /// </summary>
        string OrderNumber { get; }

        /// <summary>
        /// ���õ������ͣ�ֱ�Ӵ���ĳ�����׵ĵ������ͣ�
        /// �����Ա�˻������õ�������Ϊ����������
        /// </summary>
        string RefOrderType { get; }

        /// <summary>
        /// ���õ��ݺ��루ֱ�Ӵ���ĳ�����׵ĵ��ݺ��룩
        /// �����Ա�˻������õ��ݺ���Ϊ����������
        /// </summary>
        string RefOrderNumber { get; }

        /// <summary>
        /// ԭʼ��������
        /// �����Ա�˻���ԭʼ��������Ϊ��Ա���������۶���������
        /// </summary>
        string OriginalOrderType { get; }

        /// <summary>
        /// ԭʼ���ݺ���
        /// �����Ա�˻���ԭʼ���ݺ���Ϊ��Ա���������۶���������
        /// </summary>
        string OrginalOrderNumber { get; }

        /// <summary>
        /// ���ݴ�����
        /// </summary>
        int CreateUser { get; }

        /// <summary>
        /// ���ݴ���ʱ��
        /// </summary>
        DateTime CreateTime { get; }

        /// <summary>
        /// ����ִ����ϵĻص�����
        /// </summary>
        /// <param name="session"></param>
        void AfterTransaction(ISession session);

        /// <summary>
        /// 
        /// </summary>
        string Note { get; }

        /// <summary>
        /// ��ȡ������ϸ
        /// </summary>
        /// <returns></returns>
        IList<IWHTransLine> GetLines(ISession session);
    }
}