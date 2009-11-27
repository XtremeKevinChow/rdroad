namespace Magic.ERP.Orders
{
    using System.Collections.Generic;
    using Magic.Framework.ORM;

    //�������⣺�����ýӿڽ����Դ�ⷿ��Ŀ��ⷿ
    public partial class WHTransferLine : IWHTransTransferLine
    {
        /// <summary>
        /// �Է��������ֶ�û��
        /// </summary>
        string IWHTransLine.TransTypeCode
        {
            get { return null; }
        }

        /// <summary>
        /// ʵ������
        /// </summary>
        decimal IWHTransLine.QualifiedQty
        {
            get { return this._moveQty; }
        }

        /// <summary>
        /// ϵͳ����
        /// </summary>
        decimal IWHTransLine.UnQualifiedQty
        {
            get { return 0M; }
        }

        /// <summary>
        /// �ұ�
        /// </summary>
        string IWHTransLine.CurrencyCode
        {
            get { return "RMB"; }
        }

        /// <summary>
        /// �Է��������ֶ�û��
        /// </summary>
        string IWHTransLine.LotNumber
        {
            get { return null; }
        }

        string IWHTransLine.RefOrderLine
        {
            get
            {
                return " ";
            }
        }
        string IWHTransLine.OriginalOrderLine
        {
            get
            {
                return " ";
            }
        }

        decimal IWHTransLine.Price
        {
            get
            {
                return 0M;
            }
        }

        string IWHTransLine.LocationCode
        {
            get
            {
                return this._fromLocation;
            }
        }
        string IWHTransLine.AreaCode
        {
            get
            {
                return this._fromArea;
            }
        }
        string IWHTransLine.SectionCode
        {
            get
            {
                return this._fromSection;
            }
        }
        decimal IWHTransLine.TaxValue { get { return 0M; } }
        IList<IWHLockItem> IWHTransLine.GetLockItem(ISession session)
        {
            return null;
        }

        string IWHTransTransferLine.ToLocation
        {
            get { return this._toLocation; }
        }
        string IWHTransTransferLine.ToArea
        {
            get { return this._toArea; }
        }
        string IWHTransTransferLine.ToSection
        {
            get { return this._toSection; }
        }
    }
}