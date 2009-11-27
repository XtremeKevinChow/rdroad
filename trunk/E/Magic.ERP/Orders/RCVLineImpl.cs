namespace Magic.ERP.Orders
{
    using Magic.Framework.ORM;
    using System.Collections.Generic;

    public partial class RCVLine : IWHTransLine
    {
        /// <summary>
        /// �ұ�
        /// </summary>
        string IWHTransLine.CurrencyCode
        {
            get { return "RMB"; }
        }

        /// <summary>
        /// ��ϵͳ������ʱ������ſ��ƽ������ֱ��
        /// </summary>
        string IWHTransLine.LotNumber
        {
            get { return this._orderNumber.Trim()+"-"+this._lineNumber.Trim() ; }
        }

        decimal IWHTransLine.TaxValue { get { return this.TaxValue; } }
        IList<IWHLockItem> IWHTransLine.GetLockItem(ISession session)
        {
            return null;
        }
    }
}