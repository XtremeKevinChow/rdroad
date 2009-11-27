namespace Magic.ERP.Orders
{
    public partial class DeliverLine : IWHTransLine
	{
        /// <summary>
        /// �Է��������ֶ�û��
        /// </summary>
        string IWHTransLine.TransTypeCode
        {
            get { return null; }
        }

        /// <summary>
        /// ��������
        /// </summary>
        decimal IWHTransLine.QualifiedQty
        {
            get { return this.ShipQty; }
        }

        /// <summary>
        /// �Է��������ֶ�û��
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

        decimal IWHTransLine.TaxValue { get { return 0M; } }
	}
}