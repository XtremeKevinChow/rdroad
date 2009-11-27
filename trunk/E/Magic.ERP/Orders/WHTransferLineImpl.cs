namespace Magic.ERP.Orders
{
    using System.Collections.Generic;
    using Magic.Framework.ORM;

    //还有问题：怎样用接口解决来源库房与目标库房
    public partial class WHTransferLine : IWHTransTransferLine
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
            get { return this._moveQty; }
        }

        /// <summary>
        /// 系统数量
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