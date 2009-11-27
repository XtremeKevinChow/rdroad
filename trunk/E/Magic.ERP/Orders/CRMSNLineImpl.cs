namespace Magic.ERP.Orders
{
	using System;
    using System.Collections.Generic;
	using Magic.Framework.Data;
	using Magic.Framework.ORM;
	using Magic.Framework.ORM.Mapping;
    using Magic.Framework;
    using Magic.Framework.ORM.Query;

    //TODO: 发货单必须记录: Location, Area, Section
	public partial class  CRMSNLine : IWHTransLine
	{
        string IWHTransLine.CurrencyCode
        {
            get { return "RMB"; }
        }
        string IWHTransLine.LineNumber
        {
            get { return this.ID.ToString(); }
        }
        string IWHTransLine.LocationCode
        {
            get { return null; }
        }
        string IWHTransLine.AreaCode
        {
            get { return null; }
        }
        string IWHTransLine.SectionCode
        {
            get { return null; }
        }
        string IWHTransLine.LotNumber
        {
            get { return " "; }
        }
        string IWHTransLine.OriginalOrderLine
        {
            get { return this.SaleOrderLine.ToString(); }
        }
        decimal IWHTransLine.UnQualifiedQty
        {
            get { return this.Quantity; }
        }
        decimal IWHTransLine.QualifiedQty
        {
            get { return 0M; }
        }
        string IWHTransLine.RefOrderLine
        {
            get { return this.SaleOrderLine.ToString(); }
        }
        string IWHTransLine.TransTypeCode
        {
            get { return null; }
        }
        int IWHTransLine.UnitID
        {
            get { return 0; }
        }
        decimal IWHTransLine.TaxValue { get { return 0M; } }
        IList<IWHLockItem> IWHTransLine.GetLockItem(ISession session)
        {
            IList<CRMSNLineINV> lockItems = session.CreateEntityQuery<CRMSNLineINV>()
                .Where(Exp.Eq("OwnerID", this.ID))
                .OrderBy("StockDetailID")
                .List<CRMSNLineINV>();
            IList<IWHLockItem> result = new List<IWHLockItem>(lockItems.Count);
            foreach (CRMSNLineINV line in lockItems)
            {
                line.Owner = this;
                result.Add(line);
            }
            return result;
        }
	}
}