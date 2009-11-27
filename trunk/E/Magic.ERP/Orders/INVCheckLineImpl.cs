namespace Magic.ERP.Orders
{
    using System.Collections.Generic;
    using Magic.Framework.ORM;

    public partial class INVCheckLine : IWHTransLine, IWHExactItem
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
            get { return this._currentQty - this._beforeQty; }
        }

        /// <summary>
        /// ϵͳ����
        /// </summary>
        decimal IWHTransLine.UnQualifiedQty
        {
            get { return this._beforeQty; }
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

        public virtual decimal Price
        {
            get
            {
                return 0M;
            }
        }
        decimal IWHTransLine.TaxValue { get { return 0M; } }
        IList<IWHLockItem> IWHTransLine.GetLockItem(ISession session)
        {
            return null;
        }
    }
}