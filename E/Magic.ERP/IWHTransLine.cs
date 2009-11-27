namespace Magic.ERP
{
    using System.Collections.Generic;
    using Magic.Framework.ORM;

    /// <summary>
    /// 库存交易单据的行项目
    /// </summary>
    public interface IWHTransLine
    {
        IList<IWHLockItem> GetLockItem(ISession session);

        /// <summary>
        /// 单据行号
        /// </summary>
        string LineNumber { get; }

        /// <summary>
        /// 交易类型
        /// </summary>
        string TransTypeCode { get; }

        /// <summary>
        /// 产品明细ID
        /// </summary>
        int SKUID { get; }

        /// <summary>
        /// 合格数量
        /// </summary>
        decimal QualifiedQty { get; }

        /// <summary>
        /// 不合格数量
        /// </summary>
        decimal UnQualifiedQty { get; }

        /// <summary>
        /// 交易单位
        /// </summary>
        int UnitID { get; }

        /// <summary>
        /// 交易价格
        /// </summary>
        decimal Price { get; }

        /// <summary>
        /// 交易币别
        /// </summary>
        string CurrencyCode { get; }

        /// <summary>
        /// 交易仓储地
        /// </summary>
        string LocationCode { get; }

        /// <summary>
        /// 交易仓库存储区域
        /// </summary>
        string AreaCode { get; }

        /// <summary>
        /// 交易货架
        /// </summary>
        string SectionCode { get; }

        /// <summary>
        /// 引用单据行号
        /// </summary>
        string RefOrderLine { get; }

        /// <summary>
        /// 原始单据行号
        /// </summary>
        string OriginalOrderLine { get; }

        /// <summary>
        /// 启用批序号控制时的批号
        /// </summary>
        string LotNumber { get; }

        decimal TaxValue { get; }
    }

    public interface IWHTransTransferLine : IWHTransLine
    {
        int FromStockID { get; }
        string ToLocation { get; }
        string ToArea { get; }
        string ToSection { get; }
    }
}