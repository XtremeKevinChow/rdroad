namespace Magic.ERP
{
    /// <summary>
    /// 单据的签核状态、签核结果
    /// </summary>
    public enum ApproveStatus
    {
        /// <summary>
        /// 驳回
        /// </summary>
        Reject = -1,
        /// <summary>
        /// 未签核
        /// </summary>
        UnApprove = 0,
        /// <summary>
        /// 通过
        /// </summary>
        Approve = 1,
    }

    /// <summary>
    /// 发货单状态
    /// </summary>
    public enum DeliverStatus
    {
        /// <summary>
        /// 取消
        /// </summary>
        Cancel = 0,
        /// <summary>
        /// 新增
        /// </summary>
        New = 1,
        /// <summary>
        /// 发布（送签）
        /// </summary>
        Release = 2,
        /// <summary>
        /// 检货中
        /// </summary>
        Open = 3,
        /// <summary>
        /// 已完成
        /// </summary>
        Close = 4,
    }

    /// <summary>
    /// 交接单状态
    /// </summary>
    public enum InterchangeStatus
    {
        /// <summary>
        /// 新建
        /// </summary>
        New = 1,
        /// <summary>
        /// 发布
        /// </summary>
        Release = 2,
        Open = 3,
        /// <summary>
        /// 已完成
        /// </summary>
        Close = 4,
    }

    /// <summary>
    /// 收货单状态
    /// </summary>
    public enum ReceiveStatus
    {
        /// <summary>
        /// 新建
        /// </summary>
        New = 1,
        /// <summary>
        /// 发布
        /// </summary>
        Release = 2,
        /// <summary>
        /// 
        /// </summary>
        Open = 3,
        /// <summary>
        /// 已完成
        /// </summary>
        Close = 4,
    }

    /// <summary>
    /// 采购订单状态
    /// </summary>
    public enum POStatus
    {
        /// <summary>
        /// 新建
        /// </summary>
        New = 1,
        /// <summary>
        /// 发布
        /// </summary>
        Release = 2,
        /// <summary>
        /// 已完成
        /// </summary>
        Close = 3,
    }

    /// <summary>
    /// 采购订单行状态
    /// </summary>
    public enum POLineStatus
    {
        /// <summary>
        /// 有效状态
        /// </summary>
        Open = 1,
        /// <summary>
        /// 取消
        /// </summary>
        Cancel = 2,
        /// <summary>
        /// 已完成
        /// </summary>
        Close = 3,
    }

    /// <summary>
    /// 库存盘点、调整单状态
    /// </summary>
    public enum INVCheckStatus
    {
        /// <summary>
        /// 新建
        /// </summary>
        New = 1,
        /// <summary>
        /// 确认
        /// </summary>
        Confirm = 2,
        /// <summary>
        /// 发布
        /// </summary>
        Release = 3,
        /// <summary>
        /// 完成
        /// </summary>
        Close = 4,
    }

    /// <summary>
    /// 移库单状态
    /// </summary>
    public enum WHTransferStatus
    {
        /// <summary>
        /// 新建
        /// </summary>
        New = 1,
        /// <summary>
        /// 发布
        /// </summary>
        Release = 2,
        /// <summary>
        /// 待移库
        /// </summary>
        Open = 3,
        /// <summary>
        /// 完成
        /// </summary>
        Close = 4,
    }

    /// <summary>
    /// 交易步骤的类型
    /// </summary>
    public enum TransStepType
    {
        /// <summary>
        /// 一个步骤中只包含单个交易
        /// </summary>
        Single = 1,
        /// <summary>
        /// 一个步骤中包含多个交易类型，单据中不同的行项目可能使用不同的交易类型进行交易
        /// </summary>
        MultiSelect = 2,
    }

    /// <summary>
    /// 从哪里获取交易类型代码
    /// </summary>
    public enum TransTypeFrom
    {
        /// <summary>
        /// 从配置中获取
        /// </summary>
        ConfigValue = 1,
        /// <summary>
        /// 从接口中获取
        /// </summary>
        InterfaceValue = 2,
    }

    /// <summary>
    /// 从哪里读取交易数量
    /// </summary>
    public enum TransQtyFrom
    {
        /// <summary>
        /// 取合格数量
        /// </summary>
        QualifiedQty = 1,
        /// <summary>
        /// 取不合格数量
        /// </summary>
        UnQualifiedQty = 2,
    }

    public enum TransProperty
    {
        /// <summary>
        /// 入库
        /// </summary>
        In = 1,
        /// <summary>
        /// 出库
        /// </summary>
        Out = -1,
    }

    /// <summary>
    /// 从哪里获取交易仓位、货架信息
    /// </summary>
    public enum TransLocationFrom
    {
        /// <summary>
        /// 从交易－仓库对照表中读取
        /// </summary>
        ConfigValue = 1,
        /// <summary>
        /// 从接口读取
        /// </summary>
        InterfaceValue = 2,
    }

    /// <summary>
    /// 单据时间戳精度类型
    /// </summary>
    public enum OrderRuleDefPrecision
    {
        Year = 1,
        Month = 2,
        Date = 3,
        Hour = 4,
        Minute = 5,
    }

    /// <summary>
    /// 仓储地、仓库区域、货架的状态
    /// </summary>
    public enum WHStatus
    {
        /// <summary>
        /// 删除
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
    /// 交易价格来源类型
    /// </summary>
    public enum TransTypePriceSource
    {
        /// <summary>
        /// 不记录价格
        /// </summary>
        NoPrice = 0,
        /// <summary>
        /// 从来源单据获取
        /// </summary>
        FromSourceOrder = 1,
        /// <summary>
        /// 取移动平均价
        /// </summary>
        FromMovAvgCost = 3,
        /// <summary>
        /// 从库房区域的配置中获取
        /// </summary>
        FromAreaCfg = 4,
    }

    public enum StockInStatus
    {
        New = 1,
        Release = 2,
        Open = 3,
        Close = 4,
        /// <summary>
        /// 只有产品入库才有这个状态。状态流程：New->Release->Confirm->送签处理->Open->Close
        /// </summary>
        Confirm = 5,
    }

    /// <summary>
    /// 盘点方式
    /// </summary>
    public enum INVCheckType
    {
        /// <summary>
        /// 明盘
        /// </summary>
        Explicit = 1,
        /// <summary>
        /// 暗盘
        /// </summary>
        Implicit = 2,
    }

    public enum CRMSNStatus
    {
        /// <summary>
        /// 退货
        /// </summary>
        Return = -5,
        /// <summary>
        /// 取消
        /// </summary>
        Cancel = -1,
        /// <summary>
        /// 新建
        /// </summary>
        New = 0,
        /// <summary>
        /// 已打印，配送中
        /// </summary>
        Distributing = 10,
        /// <summary>
        /// 上架完成，即拣货完毕，配货区上架完成
        /// </summary>
        PickedOut = 15,
        /// <summary>
        /// 核货完成 
        /// </summary>
        Checked = 20,
        /// <summary>
        /// 包装完成
        /// </summary>
        Packaged = 30,
        /// <summary>
        /// 部分换货
        /// </summary>
        PartExchange = 39,
        /// <summary>
        /// 交接完成
        /// </summary>
        Interchanged = 40,
    }
    public enum CRMSNLineStatus
    {
        /// <summary>
        /// 退货
        /// </summary>
        Return = -5,
        /// <summary>
        /// 取消
        /// </summary>
        Cancel = -1,
        /// <summary>
        /// 正常
        /// </summary>
        Natural = 0,
        /// <summary>
        /// 已打印，配送中
        /// </summary>
        Distributing = 10,
        /// <summary>
        /// 核货完成 
        /// </summary>
        Checked = 20,
        /// <summary>
        /// 包装完成
        /// </summary>
        Packaged = 30,
        /// <summary>
        /// 交接完成
        /// </summary>
        Interchanged = 40,
        /// <summary>
        /// 换货
        /// </summary>
        Exchange = 60,
        /// <summary>
        /// 补货
        /// </summary>
        Replenishment = 70,
    }

    /// <summary>
    /// 退货单状态
    /// </summary>
    public enum ReturnStatus
    {
        /// <summary>
        /// 
        /// </summary>
        New = 1,
        Release = 2,
        Open = 3,
        Close = 4,
    }

    /// <summary>
    /// 销售订单类型
    /// </summary>
    public enum SaleOrderType
    {
        /// <summary>
        /// 标准订单
        /// </summary>
        Standard = 10,
        /// <summary>
        /// 退货单
        /// </summary>
        Return = 40,
        /// <summary>
        /// 团购订单
        /// </summary>
        MajorCustomer = 15,
        /// <summary>
        /// 预售订单
        /// </summary>
        PreSale = 5,
        /// <summary>
        /// 换货订单
        /// </summary>
        Exchange = 20,
    }

    /// <summary>
    /// 采购退货状态
    /// </summary>
    public enum POReturnStatus
    {
        /// <summary>
        /// 新建
        /// </summary>
        New = 1,
        /// <summary>
        /// 发布
        /// </summary>
        Release = 2,
        /// <summary>
        /// 待出库
        /// </summary>
        Open = 3,
        /// <summary>
        /// 完成
        /// </summary>
        Close = 4,
    }
}