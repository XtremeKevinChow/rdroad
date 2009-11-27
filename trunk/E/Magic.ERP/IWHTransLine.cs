namespace Magic.ERP
{
    using System.Collections.Generic;
    using Magic.Framework.ORM;

    /// <summary>
    /// ��潻�׵��ݵ�����Ŀ
    /// </summary>
    public interface IWHTransLine
    {
        IList<IWHLockItem> GetLockItem(ISession session);

        /// <summary>
        /// �����к�
        /// </summary>
        string LineNumber { get; }

        /// <summary>
        /// ��������
        /// </summary>
        string TransTypeCode { get; }

        /// <summary>
        /// ��Ʒ��ϸID
        /// </summary>
        int SKUID { get; }

        /// <summary>
        /// �ϸ�����
        /// </summary>
        decimal QualifiedQty { get; }

        /// <summary>
        /// ���ϸ�����
        /// </summary>
        decimal UnQualifiedQty { get; }

        /// <summary>
        /// ���׵�λ
        /// </summary>
        int UnitID { get; }

        /// <summary>
        /// ���׼۸�
        /// </summary>
        decimal Price { get; }

        /// <summary>
        /// ���ױұ�
        /// </summary>
        string CurrencyCode { get; }

        /// <summary>
        /// ���ײִ���
        /// </summary>
        string LocationCode { get; }

        /// <summary>
        /// ���ײֿ�洢����
        /// </summary>
        string AreaCode { get; }

        /// <summary>
        /// ���׻���
        /// </summary>
        string SectionCode { get; }

        /// <summary>
        /// ���õ����к�
        /// </summary>
        string RefOrderLine { get; }

        /// <summary>
        /// ԭʼ�����к�
        /// </summary>
        string OriginalOrderLine { get; }

        /// <summary>
        /// ��������ſ���ʱ������
        /// </summary>
        string LotNumber { get; }

        decimal TaxValue { get; }
    }

    public interface IWHTransTransferLine : IWHTransLine
    {
        int FromStockID { get; }
        string ToLocation { get; }
        string ToArea { get; }
        string ToSection { get; }
    }
}