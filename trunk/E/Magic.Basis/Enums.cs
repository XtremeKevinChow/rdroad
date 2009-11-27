//*******************************************
// ** Description:  Data Access Object for Currency
// ** Author     :  Code generator
// ** Created    :   2008-6-30 20:22:21
// ** Modified   :
//*******************************************

namespace Magic.Basis
{
    /// <summary>
    /// 供应商状态
    /// </summary>
    public enum VendorStatus
    {
        /// <summary>
        /// 已删除
        /// </summary>
        Delete = 0,
        /// <summary>
        /// 禁用
        /// </summary>
        Disable = 1,
        /// <summary>
        /// 启用
        /// </summary>
        Enable = 2,
    }

    /// <summary>
    /// 物流商状态
    /// </summary>
    public enum LogisticsStatus
    {
        /// <summary>
        /// 已删除
        /// </summary>
        Delete = 0,
        /// <summary>
        /// 禁用
        /// </summary>
        Disable = 1,
        /// <summary>
        /// 启用
        /// </summary>
        Enable = 2,
    }

    /// <summary>
    /// 商品SKU状态（上下架状态）
    /// </summary>
    public enum ItemSpecStatus
    {
        /// <summary>
        /// 下架
        /// </summary>
        Disable = 0,
        /// <summary>
        /// 上架
        /// </summary>
        Enable = 1,
    }

    /// <summary>
    /// 商品类型
    /// </summary>
    public enum ItemType
    {
        /// <summary>
        /// 辅料
        /// </summary>
        AssistantItem = 0,
        /// <summary>
        /// 普通商品
        /// </summary>
        NormalItem = 1,
        /// <summary>
        /// 系列商品
        /// </summary>
        SerialItem = 2,
        /// <summary>
        /// 套装商品
        /// </summary>
        SetItem = 3,
    }

    /// <summary>
    /// 库存期间类型
    /// </summary>
    public enum INVPeriodType
    {
        /// <summary>
        /// 按季度划分
        /// </summary>
        Quarter = 10,
        /// <summary>
        /// 按月份划分
        /// </summary>
        Month = 20,
        /// <summary>
        /// 手工划分
        /// </summary>
        Manual = 30,
    }

    /// <summary>
    /// 库存期间状态
    /// </summary>
    public enum INVPeriodStatus
    {
        /// <summary>
        /// 初始状态
        /// </summary>
        New = 10,
        /// <summary>
        /// 开帐状态
        /// </summary>
        Open =20,
        /// <summary>
        /// 关闭状态
        /// </summary>
        Close = 30,
    }

    public enum StockWarnMethod
    {
        None = 0,
    }
}
