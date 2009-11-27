namespace Magic.ERP.Orders
{
    public partial class DeliverLine : IWHTransLine
	{
        /// <summary>
        /// 对发货单该字段没用
        /// </summary>
        string IWHTransLine.TransTypeCode
        {
            get { return null; }
        }

        /// <summary>
        /// 发货数量
        /// </summary>
        decimal IWHTransLine.QualifiedQty
        {
            get { return this.ShipQty; }
        }

        /// <summary>
        /// 对发货单该字段没用
        /// </summary>
        decimal IWHTransLine.UnQualifiedQty
        {
            get { return 0M; }
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

        decimal IWHTransLine.TaxValue { get { return 0M; } }
	}
}