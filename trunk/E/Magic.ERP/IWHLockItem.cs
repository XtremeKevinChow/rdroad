namespace Magic.ERP
{
    using System;
    using System.Collections.Generic;
    using Magic.Framework.ORM;

    /// <summary>
    /// 需要进行库存交易的单据
    /// </summary>
    public interface IWHLockItem
    {
        IWHTransLine Owner { get; }
        int OwnerID { get; }
        int StockDetailID { get; }
        string LocationCode { get; }
        string AreaCode { get; }
        string SectionCode { get; }
        decimal Quantity { get; }
    }

    public interface IWHExactItem
    {
        int StockDetailID { get; }
    }
}