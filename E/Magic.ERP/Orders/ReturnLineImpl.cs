namespace Magic.ERP.Orders
{
    using System.Collections.Generic;
    using Magic.Framework.ORM;
    using Magic.Framework.ORM.Query;

    public partial class  ReturnLine : IWHTransLine
	{
        public static ReturnLine Retrieve(ISession session, string orderNumber, int shippingDetailID)
        {
            IList<ReturnLine> lines = session.CreateEntityQuery<ReturnLine>()
                .Where(Exp.Eq("OrderNumber", orderNumber) & Exp.Eq("RefOrderLineID", shippingDetailID))
                .List<ReturnLine>();
            return lines == null || lines.Count <= 0 ? null : lines[0];
        }

        decimal IWHTransLine.TaxValue
        {
            get
            {
                return 0M;
            }
        }
        string IWHTransLine.LotNumber
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
                return this.RefOrderLineID.ToString();
            }
        }
        string IWHTransLine.RefOrderLine
        {
            get
            {
                return this.RefOrderLineID.ToString();
            }
        }
        string IWHTransLine.LocationCode
        {
            get
            {
                return this.LocationCode;
            }
        }
        string IWHTransLine.CurrencyCode
        {
            get
            {
                return "RMB";
            }
        }
        decimal IWHTransLine.Price
        {
            get
            {
                return this._price;
            }
        }
        int IWHTransLine.UnitID
        {
            get
            {
                return 0;
            }
        }
        decimal IWHTransLine.UnQualifiedQty
        {
            get
            {
                return this.DeliverQuantity;
            }
        }
        decimal IWHTransLine.QualifiedQty
        {
            get
            {
                return this.Quantity;
            }
        }
        IList<IWHLockItem> IWHTransLine.GetLockItem(ISession session)
        {
            return null;
        }
	}
}