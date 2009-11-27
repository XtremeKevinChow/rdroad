namespace Magic.ERP.Orders
{
    using System.Collections.Generic;
    using Magic.Framework.ORM;

    public partial class INVCheckLine : IWHTransLine, IWHExactItem
    {
        /// <summary>
        /// 对发货单该字段没用
        /// </summary>
        string IWHTransLine.TransTypeCode
        {
            get { return null; }
        }

        /// <summary>
        /// 实际数量
        /// </summary>
        decimal IWHTransLine.QualifiedQty
        {
            get { return this._currentQty - this._beforeQty; }
        }

        /// <summary>
        /// 系统数量
        /// </summary>
        decimal IWHTransLine.UnQualifiedQty
        {
            get { return this._beforeQty; }
        }

        /// <summary>
        /// 币别
        /// </summary>
        string IWHTransLine.CurrencyCode
        {
            get { return "RMB"; }
        }

        /// <summary>
        /// 对发货单该字段没用
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