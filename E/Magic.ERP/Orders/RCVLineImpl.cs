namespace Magic.ERP.Orders
{
    using Magic.Framework.ORM;
    using System.Collections.Generic;

    public partial class RCVLine : IWHTransLine
    {
        /// <summary>
        /// 币别
        /// </summary>
        string IWHTransLine.CurrencyCode
        {
            get { return "RMB"; }
        }

        /// <summary>
        /// 入系统保留库时用批序号控制进行区分标记
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