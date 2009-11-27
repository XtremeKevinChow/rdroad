namespace Magic.ERP.Orders
{
	using System;
    using System.Collections.Generic;
	using Magic.Framework.Data;
	using Magic.Framework.ORM;
	using Magic.Framework.ORM.Mapping;

    public partial class StockInLine : IWHTransLine, IWHExactItem
	{
        string IWHTransLine.TransTypeCode
        {
            get
            {
                return null;
            }
        }
        decimal IWHTransLine.QualifiedQty
        {
            get
            {
                return this._quantity;
            }
        }
        decimal IWHTransLine.UnQualifiedQty
        {
            get
            {
                return 0M;
            }
        }
        string IWHTransLine.CurrencyCode
        {
            get
            {
                return "RMB";
            }
        }
        string IWHTransLine.RefOrderLine
        {
            get
            {
                return null;
            }
        }
        string IWHTransLine.OriginalOrderLine
        {
            get
            {
                return null;
            }
        }
        string IWHTransLine.LotNumber
        {
            get
            {
                return null;
            }
        }
        decimal IWHTransLine.TaxValue { get { return 0M; } }
        IList<IWHLockItem> IWHTransLine.GetLockItem(ISession session)
        {
            return null;
        }
	}
}