namespace Magic.ERP
{
    using System;
    using System.Data;
    using System.Collections.Generic;
    using Magic.Framework.ORM;
    using Magic.Framework.ORM.Query;
    using Magic.Framework.Utils;
    using Magic.Framework.Data;
    using Magic.ERP.Core;
    using Magic.ERP.Orders;
    using Magic.Basis;

    public class ERPUtil
    {
        private static log4net.ILog log = log4net.LogManager.GetLogger(typeof(ERPUtil));
        private static CacheManager _cacheManager = null;
        private const string DEFAULT_TIME_PATTERN = "yyMMdd";
        private const int DEFAULT_SERIA_LEN = 4;
        public static bool HasInitialized = false;

        private static IDictionary<string, string> _enumText = new Dictionary<string, string>();

        static ERPUtil()
        {
            _enumText.Add("ApproveStatus.Approve", "通过");
            _enumText.Add("ApproveStatus.Reject", "驳回");
            _enumText.Add("ApproveStatus.UnApprove", "未签核");
            _enumText.Add("INVCheckType.Explicit", "明盘");
            _enumText.Add("INVCheckType.Implicit", "暗盘");
        }

        internal static IApprovable GetApproveItem(ISession session, string orderType, string orderNumber)
        {
            switch (orderType)
            {
                case POHead.ORDER_TYPE: //采购订单
                    return POHead.Retrieve(session, orderNumber);
                case RCVHead.ORD_TYPE_PUR:       //采购收货
                    return RCVHead.Retrieve(session, orderNumber);
                case POReturnHead.ORD_TYPE_CODE: //采购退货
                    return POReturnHead.Retrieve(session, orderNumber);
                case ICHead.ORDER_TYPE: //交接单
                    return ICHead.Retrieve(session, orderNumber);
                case INVCheckHead.ORDER_TYPE_ADJ: //库存调整单
                case INVCheckHead.ORDER_TYPE_CHK: //库房盘点
                    return INVCheckHead.Retrieve(session, orderNumber);
                case StockInHead.ORD_TYPE_ASSIST_IN:       //辅料入库
                case StockInHead.ORD_TYPE_ASSIST_OUT:    //辅料领用
                case StockInHead.ORD_TYPE_PRD_IN:            //产品入库
                case StockInHead.ORD_TYPE_PRD_OUT:         //产品领用
                    return StockInHead.Retrieve(session, orderNumber);
                case ReturnHead.ORDER_TYPE_MBR_RTN: //会员退货
                case ReturnHead.ORDER_TYPE_LOGISTICS_RTN: //物流退货
                case ReturnHead.ORDER_TYPE_INNER_RTN: //内部退货
                case ReturnHead.ORDER_TYPE_EXCHANGE_RTN: //会员换货
                    return ReturnHead.Retrieve(session, orderNumber);
                case WHTransferHead.ORDER_TYPE_NORMAL: //移库单
                    return WHTransferHead.Retrieve(session, orderNumber);
            }
            throw new Exception(string.Format("Order type {0} is not a registered approvable item", orderType));
        }

        public static void Initialize(ISession session)
        {
            _cacheManager = new CacheManager(session);
            HasInitialized = true;
        }

        public static CacheManager Cache
        {
            get
            {
                return _cacheManager;
            }
        }

        /// <summary>
        /// 返回单个单据状态的文本描述
        /// </summary>
        /// <param name="session"></param>
        /// <param name="orderTypeCode">单据类型</param>
        /// <param name="status">单据状态，可以是整数值、枚举值</param>
        /// <returns></returns>
        public static string StatusText(ISession session, string orderTypeCode, object status)
        {
            if (status == null) return "";
            OrderStatusDef statusDef = OrderStatusDef.Retrieve(session, orderTypeCode, Cast.Int(status));
            if (statusDef == null) return "";
            return statusDef.StatusText;
        }
        /// <summary>
        /// 返回单据状态的列表
        /// </summary>
        /// <param name="session"></param>
        /// <param name="orderTypeCode">单据类型</param>
        /// <returns></returns>
        public static IList<OrderStatusDef> StatusText(ISession session, string orderTypeCode)
        {
            return session.CreateEntityQuery<OrderStatusDef>()
                .Where(Exp.Eq("OrderTypeCode", orderTypeCode))
                .OrderBy("StatusIndex")
                .List<OrderStatusDef>();
        }

        /// <summary>
        /// 送签单据
        /// </summary>
        /// <param name="session"></param>
        /// <param name="order"></param>
        public static void ApproveThis(ISession session, IApprovable order)
        {
            log.DebugFormat("submit order {0} for approve", order.OrderNumber);
            OrderTypeDef typeDef = OrderTypeDef.Retrieve(session, order.OrderTypeCode);
            log.DebugFormat("OrderTypeDef {0} retrieved", order.OrderTypeCode);
            //不需要签核
            if (!typeDef.NeedApprove)
            {
                log.DebugFormat("order type {0} don't need approval", order.OrderTypeCode);
                order.ApproveResult = ApproveStatus.Approve;
                order.Update(session, "ApproveResult");
                order.OnApprove(session);
                session.PostCommit += order.PostApprove;
                return;
            }

            //检查该单据是否已经有未完成的签核项，避免重复生成
            int count = session.CreateEntityQuery<OrderApproveItem>()
                .Where(Exp.Eq("OrderTypeCode", order.OrderTypeCode)
                    & Exp.Eq("OrderNumber", order.OrderNumber)
                    & Exp.Eq("Status", ApproveStatus.UnApprove))
                .Count();
            if (count > 0)
            {
                log.DebugFormat("order {0} has {1} unfinished approve items, maybe it's already in approving state", order.OrderNumber, count);
                throw new Exception(typeDef.TypeText + order.OrderNumber + "已经在签核中");
            }

            IList<OrderApproveDef> appDefines = session.CreateEntityQuery<OrderApproveDef>()
                .Where(Exp.Eq("OrderTypeCode", order.OrderTypeCode))
                .OrderBy("StepIndex")
                .List<OrderApproveDef>();
            if (appDefines == null || appDefines.Count <= 0)
            {
                log.DebugFormat("no OrderApproveDef exists for order type {0}", order.OrderTypeCode);
                throw new Exception(string.Format("Approve defines error for order type {0}", order.OrderTypeCode));
            }
            else
                log.DebugFormat("{0} OrderApproveDef retrieved", appDefines.Count);

            //生成签核项
            OrderApproveItem appItem = new OrderApproveItem();
            appItem.OrderTypeCode = order.OrderTypeCode;
            appItem.OrderNumber = order.OrderNumber;
            appItem.Status = ApproveStatus.UnApprove;
            appItem.ApproveTime = new DateTime(1900, 1, 1);
            appItem.SubmitUser = Magic.Security.SecuritySession.CurrentUser.UserId;
            appItem.SubmitTime = DateTime.Now;
            appItem.CreateUser = order.CreateUser;
            appItem.CreateTime = order.CreateTime;
            appItem.Create(session);
            log.DebugFormat("OrderApproveItem {0} created", appItem.ApproveID);

            for (int i = 0; i < appDefines.Count; i++)
            {
                OrderApproveResult appResult = new OrderApproveResult();
                appResult.ApproveID = appItem.ApproveID;
                appResult.StepIndex = appDefines[i].StepIndex;
                appResult.ApproveUser = appDefines[i].UserID;
                appResult.HasFinished = false;
                if (i == 0) appResult.ActiveItem = true;
                else appResult.ActiveItem = false;
                appResult.ApproveResult = false;
                appResult.ApproveTime = new DateTime(1900, 1, 1);
                appResult.ApproveNote = " ";
                appResult.Create(session);
                log.DebugFormat("OrderApproveResult {0} created", appResult.ApproveResultID);
            }
        }

        /// <summary>
        /// <para>开始库存交易</para>
        /// <returns>执行成功返回true，否则将抛出异常</returns>
        /// </summary>
        /// <remarks>
        /// 1. 采购退货没有使用这个方式
        /// </remarks>
        public static bool CommitWHTrans(ISession session, IWHTransHead transHead)
        {
            if (transHead == null) throw new Exception("the order committed for wh transaction is null");
            if (string.IsNullOrEmpty(transHead.OrderTypeCode) || transHead.OrderTypeCode.Trim().Length <= 0)
                throw new Exception(string.Format("the order {0}'s OrderTypeCode is null or empty", transHead.OrderNumber));
            OrderTypeDef orderTypeDef = OrderTypeDef.Retrieve(session, transHead.OrderTypeCode);
            if (orderTypeDef == null) throw new Exception(string.Format("OrderTypeDef {0} not exists", transHead.OrderTypeCode));
            if (orderTypeDef.TransStepCount <= 0)
                throw new Exception("transaction steps (TransStepCount) invalidate");

            int commitDate = Cast.Int(DateTime.Now.ToString("yyyyMMdd")), commitTime = Cast.Int(DateTime.Now.ToString("HHmm"));
            int lineNumber = 1;

            //交易主档
            log.DebugFormat("to build transaction head for {0}...", transHead.OrderNumber);
            WHTransHead head = new WHTransHead();
            #region 创建主档
            head.TransOrderType = transHead.OrderTypeCode;
            head.TransOrderNumber = transHead.OrderNumber;
            head.RefOrderType = transHead.RefOrderType;
            head.RefOrderNumber = transHead.RefOrderNumber;
            head.OriginalOrderType = transHead.OriginalOrderType;
            head.OriginalOrderNumber = transHead.OrginalOrderNumber;
            head.CommitUser = transHead.CreateUser;
            head.CommitDate = commitDate;
            head.CommitTime = commitTime;
            head.TransDesc = transHead.Note;
            #endregion

            //交易明细
            IList<WHTransLine> lines = new List<WHTransLine>();
            IList<IWHTransLine> transLines = transHead.GetLines(session);
            if (transLines == null || transLines.Count <= 0)
            {
                transHead.AfterTransaction(session);
                log.Warn(orderTypeDef.TypeText + transHead.OrderNumber + " has no lines, the transaction finished");
                return true;
            }
            #region 创建明细
            log.DebugFormat("to build transaction lines for {0}...", transHead.OrderNumber);
            foreach (IWHTransLine transLine in transLines)
            {
                for (int i = 0; i < orderTypeDef.TransStepCount; i++)
                {
                    log.DebugFormat("line:{0}, trans step:{1}", transLine.LineNumber, i + 1);
                    OrderTransDef orderTransDef = OrderTransDef.Retrieve(session, orderTypeDef.OrderTypeCode, i + 1);
                    if (orderTransDef == null)
                        throw new Exception(string.Format("  transaction step {0} for order type {1} not exists", i + 1, transHead.OrderTypeCode));
                    IList<WHTransLine> tempTransLine = new List<WHTransLine>();
                    if (transHead.PreLockStock)
                    {
                        //销售发货、换货发货，生成发货单的时候已经为每个发货明细分配好明细库位、货架、出库数量
                        //  （可能一个发货明细从多个库位、货架出货，具体的分配明细在CRMSNLineINV中）
                        //并且冻结明细库存。因此发货出库时除了出库操作外，需要恢复冻结的明细库存量
                        IList<IWHLockItem> lockItems = transLine.GetLockItem(session);
                        if (lockItems == null || lockItems.Count <= 0)
                        {
                            log.DebugFormat("{0}{1} use pre-locked detail stock items", orderTypeDef.TypeText, transHead.OrderNumber);
                            throw new Exception("no pre-locked detail stock items found");
                        }
                        foreach (IWHLockItem li in lockItems)
                        {
                            log.DebugFormat("pre-locked item: trans type={0}, id={1}, ownerid={2}, area={3}, section={4}, qty={5}", transLine.TransTypeCode, li.StockDetailID, li.OwnerID, li.AreaCode, li.SectionCode, li.Quantity);
                            TransItemWrapper wrapper = new TransItemWrapper(
                                transLine.SKUID, transLine.UnitID, transLine.CurrencyCode, transLine.LineNumber, transLine.OriginalOrderLine,
                                transLine.TaxValue, transLine.TransTypeCode, li.Quantity, transLine.UnQualifiedQty,
                                transLine.Price, li.LocationCode, li.AreaCode, li.SectionCode, li.StockDetailID);
                            tempTransLine.Add(BuildTransLine(session, wrapper, transHead, commitDate, commitTime, ref lineNumber, orderTransDef));
                        }
                    }
                    else if (transLine is IWHTransTransferLine)
                    {
                        //移库操作，从A移库到B，对应会配置2个交易明细
                        TransTypeDef tranTypeDef = TransTypeDef.Retrieve(session, orderTransDef.TransTypeCode);
                        if (tranTypeDef.TransProperty == TransProperty.Out) //移库操作，出库部分
                        {
                            IWHTransTransferLine transferLine = transLine as IWHTransTransferLine;
                            log.DebugFormat("transfer order line - Out: trans type={0}, sku={1}, price={2}, qty={3}, area={4}, section={5}, stoid={6}", transLine.TransTypeCode, transLine.SKUID, transLine.Price, transLine.QualifiedQty, transLine.AreaCode, transLine.SectionCode, transferLine.FromStockID);
                            TransItemWrapper wrapper = new TransItemWrapper(
                                transLine.SKUID, transLine.UnitID, transLine.CurrencyCode, transLine.LineNumber, transLine.OriginalOrderLine,
                                transLine.TaxValue, transLine.TransTypeCode, transLine.QualifiedQty, transLine.UnQualifiedQty,
                                transLine.Price, transLine.LocationCode, transLine.AreaCode, transLine.SectionCode, transferLine.FromStockID);
                            tempTransLine.Add(BuildTransLine(session, wrapper, transHead, commitDate, commitTime, ref lineNumber, orderTransDef));
                        }
                        else if (tranTypeDef.TransProperty == TransProperty.In) //移库操作，入库部分
                        {
                            IWHTransTransferLine transferLine = transLine as IWHTransTransferLine;
                            log.DebugFormat("transfer order line - In: trans type={0}, sku={1}, price={2}, qty={3}, area={4}, section={5}", transLine.TransTypeCode, transLine.SKUID, transLine.Price, transLine.QualifiedQty, transferLine.ToArea, transferLine.ToSection);
                            TransItemWrapper wrapper = new TransItemWrapper(
                                transLine.SKUID, transLine.UnitID, transLine.CurrencyCode, transLine.LineNumber, transLine.OriginalOrderLine,
                                transLine.TaxValue, transLine.TransTypeCode, transLine.QualifiedQty, transLine.UnQualifiedQty,
                                transLine.Price, transferLine.ToLocation, transferLine.ToArea, transferLine.ToSection, 0);
                            tempTransLine.Add(BuildTransLine(session, wrapper, transHead, commitDate, commitTime, ref lineNumber, orderTransDef));
                        }
                        else
                            throw new Exception("Transfer Order Type does not support TransProperty " + tranTypeDef.TransProperty.ToString());
                    }
                    else //其它出入库操作
                    {
                        int stoid = 0;
                        if (transLine is IWHExactItem)
                            stoid = (transLine as IWHExactItem).StockDetailID;
                        log.DebugFormat("trans order line: trans type={0}, sku={1}, price={2}, qty={3}, area={4}, section={5}", transLine.TransTypeCode, transLine.SKUID, transLine.Price, transLine.QualifiedQty, transLine.AreaCode, transLine.SectionCode);
                        TransItemWrapper wrapper = new TransItemWrapper(
                            transLine.SKUID, transLine.UnitID, transLine.CurrencyCode, transLine.LineNumber, transLine.OriginalOrderLine,
                            transLine.TaxValue, transLine.TransTypeCode, transLine.QualifiedQty, transLine.UnQualifiedQty,
                            transLine.Price, transLine.LocationCode, transLine.AreaCode, transLine.SectionCode, stoid);
                        tempTransLine.Add(BuildTransLine(session, wrapper, transHead, commitDate, commitTime, ref lineNumber, orderTransDef));
                    }
                    foreach (WHTransLine line in tempTransLine)
                        lines.Add(line);
                }
            }
            #endregion

            #region 更新库存表, 计算移动平均成本
            //update ... set STOCK_QTY=STOCK_QTY+:qty，避免并发影响
            //事务一个一个串行排队，应该不会造成库存量不准
            log.DebugFormat("to update stock qty for {0}...", transHead.OrderNumber);
            DbSession dbSession = session.DbSession as DbSession;
            IDbCommand commandUpdate1 = dbSession.CreateSqlStringCommand("update INV_STOCK_SUM set USE_QTY=USE_QTY+:qty,LAST_UPDATE=sysdate where SKU_ID=:skuId");
            IDbCommand commandInsert1 = dbSession.CreateSqlStringCommand("insert into INV_STOCK_SUM(SKU_ID,USE_QTY,FROZEN_QTY,LAST_UPDATE) values(:skuId,:qty,0,sysdate)");
            IDbCommand commondUpdate2 = dbSession.CreateSqlStringCommand("update INV_STOCK_DETAIL set STOCK_QTY=STOCK_QTY+:qty,FROZEN_QTY=FROZEN_QTY+:fzqty where STOCK_DTL_ID=:dtlId");
            IDbCommand cmdCheck = dbSession.CreateSqlStringCommand("select STOCK_QTY from INV_STOCK_DETAIL where STOCK_DTL_ID=:dtlId");
            foreach (WHTransLine l in lines)
            {
                //计算移动平均成本的逻辑
                TransTypeDef transDef = TransTypeDef.Retrieve(session, l.TransTypeCode);
                if (transDef.IsCostTrans) //成本交易才计算移动平均价
                {
                    //采购成本计算说明：
                    //小规模纳税人，采购税率（进项税）不可以抵扣，即没有退税政策，不管税率多少都必须算作采购成本，所以单位采购成本即为采购价格
                    //一般纳税人有退税政策，不过涉及的因素比较多，比如对方是什么性质的企业，所开的发票是否可以退税等。企业规模增长，成为一般纳税人后，
                    //采购收货由财务手工确定是否可以抵扣，抵扣的税率为多少
                    //一般采购成本需要考虑的其它费用包括：运杂费、运输途中的损耗、国外贸易的进口关税。采购差旅费一般记入管理费用
                    //系统实现：
                    //流程: 采购下达采购订单=>仓库收货检验=>财务确定是否可抵扣、抵扣税率=>系统执行入库、计算商品移动平均成本
                    //1. 系统默认是不可以抵扣的，采购收货的抵扣税率设置为0
                    //2. 如果可以抵扣，财务选择收货抵扣税率，这种情况下采购成本计算公式为: (采购数量*采购单价)/(1+抵扣税率)
                    //3. 如果一张采购单分多次送货、收货，财务先与供应商沟通所开发票是否可以退税抵扣，供应商在采购订单全部送货完毕后开发票，
                    //    所开发票必须与先前确定的发票类型、金额保持一致，否则财务将发票退回供应商重开
                    //    只有最后一次收货之后供应商才会开发票，所以开票校验之类的动作必须能对应多次收货
                    //4. 对于国内贸易运杂费等其它因素可以不用考虑到采购成本中（因为一般金额比较小，财务可以手工记入相应费用分录中）；但对于国际贸易则必须考虑运费、进口关税之类
                    if (l.TaxValue < 0) l.TaxValue = 0;
                    decimal costAmt = l.TransQty * l.TransPrice / (1 + l.TaxValue);
                    DataSet ds = session.CreateObjectQuery(@"
select a.AvgMoveCost as AvgMoveCost,b.StockQty as StockQty
from ItemSpec a
left join StockSummary b on a.SKUID=b.SKUID
where a.SKUID=?sku")
                        .Attach(typeof(ItemSpec)).Attach(typeof(StockSummary))
                        .SetValue("?sku", l.SKUID, "a.SKUID")
                        .DataSet();
                    decimal prevCost = Cast.Decimal(ds.Tables[0].Rows[0]["AvgMoveCost"], 0M), currentStock = Cast.Decimal(ds.Tables[0].Rows[0]["StockQty"], 0M);
                    decimal currentCost = (prevCost * currentStock + costAmt) / (l.TransQty + currentStock);
                    log.DebugFormat("new avg mov cost: {0}. old cost:{1}, old stock:{2}, trans amt:{3}, trans qty:{4}", currentCost, prevCost, currentStock, costAmt, l.TransQty);
                    ItemSpec spec = new ItemSpec();
                    spec.SKUID = l.SKUID;
                    spec.AvgMoveCost = currentCost;
                    spec.ItemCost = currentCost;
                    spec.Update(session, "AvgMoveCost", "ItemCost");
                }

                //质检仓的出入库事件，不更新汇总库存表的库存量
                if (!l.IsScrap && !l.IsQC)
                {
                    if (session.CreateEntityQuery<StockSummary>().Where(Exp.Eq("SKUID", l.SKUID)).Count() > 0)
                    {
                        log.DebugFormat("to update summary stock, sku:{0}, qty:{1}", l.SKUID, l.TransQty);
                        commandUpdate1.Parameters.Clear();
                        dbSession.AddParameter(commandUpdate1, ":qty", EntityManager.GetEntityMapping(typeof(StockSummary)).GetPropertyMapping("StockQty").DbTypeInfo, l.TransQty);
                        dbSession.AddParameter(commandUpdate1, ":skuId", EntityManager.GetEntityMapping(typeof(StockSummary)).GetPropertyMapping("SKUID").DbTypeInfo, l.SKUID);
                        dbSession.ExecuteNonQuery(commandUpdate1);
                    }
                    else
                    {
                        log.DebugFormat("to create summary stock, sku:{0}, qty:{1}", l.SKUID, l.TransQty);
                        commandInsert1.Parameters.Clear();
                        dbSession.AddParameter(commandInsert1, ":skuId", EntityManager.GetEntityMapping(typeof(StockSummary)).GetPropertyMapping("SKUID").DbTypeInfo, l.SKUID);
                        dbSession.AddParameter(commandInsert1, ":qty", EntityManager.GetEntityMapping(typeof(StockSummary)).GetPropertyMapping("StockQty").DbTypeInfo, l.TransQty);
                        dbSession.ExecuteNonQuery(commandInsert1);
                    }
                }

                bool update = false;
                decimal qty = l.TransQty, fzqty = 0M;
                if (transHead.PreLockStock)
                {
                    update = true;
                    fzqty = l.TransQty;
                    StockDetail stockDetail = StockDetail.Retrieve(session, l.StockDetailID);
                    if (stockDetail != null) l.BeforeTransQty = stockDetail.StockQty;
                    else
                    {
                        log.ErrorFormat("the locked sto dtl {0} for order {1}, line {2} not exists", l.StockDetailID, transHead.RefOrderNumber, l.RefOrderLine);
                        throw new Exception("被冻结的库存明细" + l.StockDetailID.ToString() + "不存在");
                    }
                }
                else if (l.StockDetailID > 0)
                {
                    StockDetail stockDetail = StockDetail.Retrieve(session, l.StockDetailID);
                    if (stockDetail != null)
                    {
                        update = true;
                        l.BeforeTransQty = stockDetail.StockQty;
                    }
                    else
                    {
                        log.ErrorFormat("sto dtl {0} for order {1}, line {2} not exists", l.StockDetailID, transHead.RefOrderNumber, l.RefOrderLine);
                        throw new Exception("库存明细" + l.StockDetailID.ToString() + "不存在");
                    }
                }
                else
                {
                    EntityQuery query = session.CreateEntityQuery<StockDetail>()
                        .Where(Exp.Eq("SKUID", l.SKUID) & Exp.Eq("LocationCode", l.LocationCode) & Exp.Eq("AreaCode", l.AreaCode))
                        .And(Exp.Eq("SectionCode", string.IsNullOrEmpty(l.SectionCode) || l.SectionCode.Trim().Length <= 0 ? " " : l.SectionCode))
                        .SetLastOffset(1);
                    if (l.HasLotControl && !string.IsNullOrEmpty(l.LotNumber) && l.LotNumber.Trim().Length > 0)
                        query.And(Exp.Eq("LotNumber", l.LotNumber));
                    IList<StockDetail> stockDetails = query.List<StockDetail>();
                    if (stockDetails != null && stockDetails.Count > 0)
                    {
                        update = true;
                        l.StockDetailID = stockDetails[0].StockDetailID;
                        l.BeforeTransQty = stockDetails[0].StockQty;
                    }
                }

                if (update)
                {
                    log.DebugFormat("to update detail stock, sku:{0}, old qty:{1}, trans qty:{2}", l.SKUID, l.BeforeTransQty, l.TransQty);
                    commondUpdate2.Parameters.Clear();
                    dbSession.AddParameter(commondUpdate2, ":qty", EntityManager.GetEntityMapping(typeof(StockDetail)).GetPropertyMapping("StockQty").DbTypeInfo, qty);
                    dbSession.AddParameter(commondUpdate2, ":fzqty", EntityManager.GetEntityMapping(typeof(StockDetail)).GetPropertyMapping("FrozenQty").DbTypeInfo, fzqty);
                    dbSession.AddParameter(commondUpdate2, ":dtlId", EntityManager.GetEntityMapping(typeof(StockDetail)).GetPropertyMapping("StockDetailID").DbTypeInfo, l.StockDetailID);
                    dbSession.ExecuteNonQuery(commondUpdate2);
                }
                else
                {
                    log.DebugFormat("to create detail stock, sku{0}, trans qty:{1}", l.SKUID, l.TransQty);
                    l.BeforeTransQty = 0M;
                    StockDetail sto = new StockDetail();
                    sto.SKUID = l.SKUID;
                    sto.LocationCode = l.LocationCode;
                    sto.AreaCode = l.AreaCode;
                    sto.SectionCode = string.IsNullOrEmpty(l.SectionCode) || l.SectionCode.Trim().Length <= 0 ? " " : l.SectionCode;
                    sto.LotNumber = string.IsNullOrEmpty(l.LotNumber) || l.LotNumber.Trim().Length <= 0 ? " " : l.LotNumber;
                    sto.StockQty = l.TransQty;
                    sto.Create(session);
                    l.StockDetailID = sto.StockDetailID;
                }
                cmdCheck.Parameters.Clear();
                dbSession.AddParameter(cmdCheck, ":dtlId", DbTypeInfo.Int32(), l.StockDetailID);
                decimal newStoQty = Cast.Decimal(dbSession.ExecuteScalar(cmdCheck));
                if (newStoQty < 0M)
                    throw new Exception(string.Format("库存明细{0}将变成负数！不允许负库存！", l.StockDetailID));
            }
            #endregion

            #region 插入交易记录
            //insert记录，不会存在并发冲突
            head.Create(session);
            log.DebugFormat("wh trans head created: {0}", head.TransNumber);
            foreach (WHTransLine l in lines)
            {
                l.TransNumber = head.TransNumber;
                l.Create(session);
            }
            log.DebugFormat("{0} wh trans lines created", lines.Count);
            transHead.AfterTransaction(session);
            #endregion

            return true;
        }

        private class TransItemWrapper
        {
            public TransItemWrapper(int skuId, int unitId, string currency, string lineNum, string orgLine, decimal tax, string transType, decimal qty, decimal unqty, decimal price, string loc, string area, string sec, int stoid)
            {
                this.SKUID = skuId;
                this.UnitID = unitId;
                this.CurrencyCode = currency;
                this.LineNumber = lineNum;
                this.OriginalOrderLine = orgLine;
                this.TaxValue = tax;
                this.TransTypeCode = transType;
                this.QualifiedQty = qty;
                this.UnQualifiedQty = unqty;
                this.Price = price;
                this.LocationCode = loc;
                this.AreaCode = area;
                this.SectionCode = sec;
                this.StockDetailID = stoid;
            }

            public int SKUID = 0;
            public int UnitID = 0;
            public string CurrencyCode = null;
            public string LineNumber = null;
            public string OriginalOrderLine = null;
            public decimal TaxValue = 0M;
            public string TransTypeCode = null;
            public decimal QualifiedQty = 0M;
            public decimal UnQualifiedQty = 0M;
            public decimal Price = 0M;
            public string LocationCode = null;
            public string AreaCode = null;
            public string SectionCode = null;
            public int StockDetailID = 0;
        }

        private static WHTransLine BuildTransLine(ISession session, TransItemWrapper wrapper, IWHTransHead transHead, int commitDate, int commitTime, ref int lineNumber, OrderTransDef orderTransDef)
        {
            WHArea area = null;

            WHTransLine line = new WHTransLine();
            line.LineNumber = lineNumber++;
            line.SKUID = wrapper.SKUID;
            line.TransDate = commitDate;
            line.TransTime = commitTime;
            line.UnitID = wrapper.UnitID;
            line.CurrencyCode = wrapper.CurrencyCode;
            line.RefOrderType = transHead.OrderTypeCode;
            line.RefOrderNumber = transHead.OrderNumber;
            line.RefOrderLine = wrapper.LineNumber;
            line.OriginalOrderType = transHead.OriginalOrderType;
            line.OriginalOrderNumber = transHead.OrginalOrderNumber;
            line.OriginalOrderLine = wrapper.OriginalOrderLine;
            line.TaxValue = wrapper.TaxValue;
            line.StockDetailID = wrapper.StockDetailID;
            ItemSpec itemSpec = ItemSpec.Retrieve(session, line.SKUID);
            line.AvgMoveCost = itemSpec.AvgMoveCost;

            //TODO: 目前只支持的配置实现方式有限

            //交易类型
            if (orderTransDef.StepType == TransStepType.Single)
            {
                if (orderTransDef.TransTypeFrom == TransTypeFrom.ConfigValue) line.TransTypeCode = orderTransDef.TransTypeCode;
                else if (orderTransDef.TransTypeFrom == TransTypeFrom.InterfaceValue) line.TransTypeCode = wrapper.TransTypeCode;
                else
                    throw new Exception(string.Format("WHTransaction: TransTypeFrom {0} not supported", (int)orderTransDef.TransTypeFrom));
            }
            else if (orderTransDef.StepType == TransStepType.MultiSelect)
            {
                if (orderTransDef.TransTypeFrom == TransTypeFrom.InterfaceValue) line.TransTypeCode = wrapper.TransTypeCode;
                else
                    throw new Exception(string.Format("WHTransaction: TransTypeFrom {0} not supported by TransStepType.MultiSelect", (int)orderTransDef.TransTypeFrom));
            }
            //交易数量
            if (orderTransDef.TransQtyFrom == TransQtyFrom.QualifiedQty) line.TransQty = wrapper.QualifiedQty;
            else if (orderTransDef.TransQtyFrom == TransQtyFrom.UnQualifiedQty) line.TransQty = wrapper.UnQualifiedQty;
            else
                throw new Exception(string.Format("WHTransaction: TransQtyFrom {0} not supported", (int)orderTransDef.TransQtyFrom));
            //交易仓库信息。LocationCode, AreaCode必须有，SectionCode可以有可以没有
            if (orderTransDef.TransLocationFrom == TransLocationFrom.ConfigValue)
            {
                //从（交易－仓库配置）中取库房区域、货架信息
                //不会从（产品－默认存储位置）读取，因为出入库单据如果用到（产品－默认存储位置），需要在界面提供选择，因此仓库获取类型变为TransLocationFrom.InterfaceValue
                IList<WHAreaCfg> areaCfg = WHAreaCfg.Retrieve(session, line.TransTypeCode);
                if (areaCfg == null || areaCfg.Count <= 0)
                    throw new Exception(string.Format("WHTransaction: area cfg does not exists for transaction type {0}", line.TransTypeCode));
                area = WHArea.Retrieve(session, areaCfg[0].AreaCode);
                if (area == null) throw new Exception(string.Format("WHTransaction: area {0} does not exist", areaCfg[0].AreaCode));
                line.LocationCode = area.LocationCode;
                line.AreaCode = area.AreaCode;
            }
            else if (orderTransDef.TransLocationFrom == TransLocationFrom.InterfaceValue)
            {
                if (string.IsNullOrEmpty(wrapper.LocationCode) || wrapper.LocationCode.Trim().Length <= 0
                    || string.IsNullOrEmpty(wrapper.AreaCode) || wrapper.AreaCode.Trim().Length <= 0)
                    throw new Exception(string.Format("WHTransaction: LocationCode or AreaCode is empty in transaction order line {0}", wrapper.LineNumber));
                line.LocationCode = wrapper.LocationCode;
                line.AreaCode = wrapper.AreaCode;
                line.SectionCode = wrapper.SectionCode;
            }
            else
                throw new Exception(string.Format("WHTransaction: TransLocationFrom {0} not supported", (int)orderTransDef.TransLocationFrom));

            area = WHArea.Retrieve(session, line.AreaCode);
            if (area == null) throw new Exception(string.Format("WHTransaction: invalidate AreaCode {0}", line.AreaCode));
            //如果库位被锁定（例如正在进行盘点作业）则不允许交易
            if (area.IsLocked && orderTransDef.TransTypeCode != "301") //盘点的交易类型为301
                throw new Exception("库位" + area.AreaCode + "-" + area.Name + "正在盘点，无法执行出入库操作");
            line.IsScrap = area.IsScrap;
            line.IsQC = area.IsQC;

            TransTypeDef transTypeDef = TransTypeDef.Retrieve(session, orderTransDef.TransTypeCode);
            if (transTypeDef == null)
                throw new Exception(string.Format("WHTransaction: invalidate transaction type code {0}", orderTransDef.TransTypeCode));
            line.TransQty = line.TransQty * ((int)transTypeDef.TransProperty);
            //交易描述
            line.TransReason = transTypeDef.TransDefText;
            if (!string.IsNullOrEmpty(transTypeDef.TransDefDesc) && transTypeDef.TransDefDesc.Trim().Length > 0)
                line.TransReason = line.TransReason + " - " + transTypeDef.TransDefDesc;
            //交易价格
            if (transTypeDef.PriceSourceType == TransTypePriceSource.FromSourceOrder)
                line.TransPrice = wrapper.Price;
            else if (transTypeDef.PriceSourceType == TransTypePriceSource.FromMovAvgCost)
            {
                line.TransPrice = itemSpec.AvgMoveCost;
            }
            else if (transTypeDef.PriceSourceType == TransTypePriceSource.FromAreaCfg)
            {
                if (!area.UseFixCost || area.CostFixValue < 0M)
                    throw new Exception(string.Format("WHTransaction: TransTypePriceSource is FromAreaCfg, but it does not exists in AreaCode {0}", area.AreaCode));
                line.TransPrice = area.CostFixValue;
            }
            line.TransPrice = line.TransPrice * area.CostTransRate - area.FixedComsumeValue;
            if (line.TransPrice < 0M) line.TransPrice = 0M;

            //if ((line.TransTypeCode == "101" || line.TransTypeCode == "301") && itemSpec.EnableOS)
            //{
            //    itemSpec.EnableOS = false;
            //    itemSpec.Update(session, "EnableOS");
            //}

            log.DebugFormat("trans line built: trans type:{0}, qty={1}, area={2}, section={3}, price={4}", line.TransTypeCode, line.TransQty, line.AreaCode, line.SectionCode, line.TransPrice);
            return line;
        }

        /// <summary>
        /// 生成一个新的单据号码
        /// </summary>
        /// <param name="session"></param>
        /// <param name="orderTypeCode"></param>
        /// <returns></returns>
        public static string NextOrderNumber(string orderTypeCode)
        {
            string newNumber = "";
            //用独立的连接来做，因为单据生成需要使用事务，如果出错，回滚事务不会影响其它方面
            using (ISession session = new Session())
            {
                OrderTypeDef typeDefine = OrderTypeDef.Retrieve(session, orderTypeCode);
                if (typeDefine == null)
                    throw new Exception("Can not generate new order number, order type " + orderTypeCode + " not defined");
                if (typeDefine.RuleDefineID <= 0)
                    throw new Exception("Can not generate new order number, rule of order type " + orderTypeCode + " not defined");
                OrderRuleDef ruleDefine = OrderRuleDef.Retrieve(session, typeDefine.RuleDefineID);
                if (ruleDefine == null)
                    throw new Exception("Can not generate new order number, rule of order type " + orderTypeCode + " not defined");

                //开始生成新的单据号码
                try
                {
                    DateTime timeStamp = DateTime.Now;
                    string pattern = "";
                    DateTime prevTimeStamp = ruleDefine.PrevTimeStampValue;
                    int prevSerialNumber = ruleDefine.PrevSerialValue;

                    //确保用一个事务将当前OrderRuleDef记录锁住（TODO: 数据库锁 vs 内存锁，哪个更好？）
                    session.BeginTransaction();
                    ruleDefine.Version = DateTime.Now;
                    EntityManager.Update(session, ruleDefine, "Version"); //锁住OrderRuleDef记录
                    ruleDefine = EntityManager.Retrieve<OrderRuleDef>(session, ruleDefine.RuleDefineID);

                    //单据前缀
                    System.Text.StringBuilder builder = new System.Text.StringBuilder();
                    if (ruleDefine.UsePrefix && !string.IsNullOrEmpty(ruleDefine.PrefixValue) && ruleDefine.PrefixValue.Trim().Length > 0)
                        builder.Append(ruleDefine.PrefixValue.Trim());

                    #region 单据时间戳
                    if (ruleDefine.UseTimeStamp)
                    {
                        if (!string.IsNullOrEmpty(ruleDefine.TimeStampPattern) && ruleDefine.TimeStampPattern.Trim().Length > 0)
                            pattern = ruleDefine.TimeStampPattern.Trim();
                        else
                            pattern = DEFAULT_TIME_PATTERN;
                        switch (ruleDefine.TimeStampPrecision)
                        {
                            case OrderRuleDefPrecision.Year:
                                timeStamp = Cast.DateTime(timeStamp.Year.ToString() + "-01-01");
                                if (ruleDefine.PrevTimeStampValue.Year != timeStamp.Year)
                                    prevSerialNumber = 0;
                                break;
                            case OrderRuleDefPrecision.Month:
                                timeStamp = Cast.DateTime(timeStamp.ToString("yyyy-MM-01"));
                                if (ruleDefine.PrevTimeStampValue.Year != timeStamp.Year || ruleDefine.PrevTimeStampValue.Month != timeStamp.Month)
                                    prevSerialNumber = 0;
                                break;
                            case OrderRuleDefPrecision.Date:
                                timeStamp = Cast.DateTime(timeStamp.ToString("yyyy-MM-dd"));
                                if (ruleDefine.PrevTimeStampValue.Year != timeStamp.Year || ruleDefine.PrevTimeStampValue.Month != timeStamp.Month
                                    || ruleDefine.PrevTimeStampValue.Day != timeStamp.Day)
                                    prevSerialNumber = 0;
                                break;
                            case OrderRuleDefPrecision.Hour:
                                timeStamp = Cast.DateTime(timeStamp.ToString("yyyy-MM-dd HH:01"));
                                if (ruleDefine.PrevTimeStampValue.Year != timeStamp.Year || ruleDefine.PrevTimeStampValue.Month != timeStamp.Month
                                    || ruleDefine.PrevTimeStampValue.Day != timeStamp.Day || ruleDefine.PrevTimeStampValue.Hour != timeStamp.Hour)
                                    prevSerialNumber = 0;
                                break;
                            default: break;
                        }
                        builder.Append(timeStamp.ToString(pattern));
                    }
                    #endregion

                    //单据序列号
                    prevSerialNumber++;
                    builder.Append(prevSerialNumber.ToString().PadLeft(ruleDefine.SerialLength <= 0 ? DEFAULT_SERIA_LEN : ruleDefine.SerialLength, '0'));

                    //更新单据规则
                    ruleDefine.PrevTimeStampValue = timeStamp;
                    ruleDefine.PrevSerialValue = prevSerialNumber;
                    ruleDefine.Update(session, "PrevTimeStampValue", "PrevSerialValue");

                    session.Commit();

                    newNumber = builder.ToString();
                }
                catch (Exception er)
                {
                    session.Rollback();
                    throw new Exception("Can not generate new order number: " + orderTypeCode, er);
                }
            }

            return newNumber;
        }

        /// <summary>
        /// <para>获取仓库区域列表</para>
        /// <para>单据只有一个交易步骤时，可以只传入<paramref name="orderTypeCode"/>获取仓库区域列表</para>
        /// <para>如果单据有多个交易步骤需要选择库房区域，必须先在界面让用户选择具体的交易类型，然后根据交易类型代码<paramref name="transTypeCode"/>获取可以选择的库房区域</para>
        /// </summary>
        /// <param name="session"></param>
        /// <param name="orderTypeCode">单据类型代码</param>
        /// <param name="transTypeCode">交易类型代码</param>
        /// <returns>仓库区域列表</returns>
        public static IList<WHArea> GetWHArea(ISession session, string orderTypeCode, string transTypeCode)
        {
            return ERPUtil.GetWHArea(session, orderTypeCode, transTypeCode, null);
        }
        /// <summary>
        /// <para>获取仓库区域列表</para>
        /// <para>单据只有一个交易步骤时，可以只传入<paramref name="orderTypeCode"/>获取仓库区域列表</para>
        /// <para>如果单据有多个交易步骤需要选择库房区域，必须先在界面让用户选择具体的交易类型，然后根据交易类型代码<paramref name="transTypeCode"/>获取可以选择的库房区域</para>
        /// </summary>
        /// <param name="session"></param>
        /// <param name="orderTypeCode">单据类型代码</param>
        /// <param name="transTypeCode">交易类型代码</param>
        /// <param name="transTypeCode">仓储地代码，可以不用给</param>
        /// <returns>仓库区域列表</returns>
        public static IList<WHArea> GetWHArea(ISession session, string orderTypeCode, string transTypeCode, string locationCode)
        {
            if (!string.IsNullOrEmpty(transTypeCode) && transTypeCode.Trim().Length > 0)
                return ERPUtil.GetWHAreaByTransType(session, transTypeCode, locationCode);
            if (string.IsNullOrEmpty(orderTypeCode) || orderTypeCode.Trim().Length <= 0)
                throw new Exception("Empty parameters while retrieving WHArea list");
            IList<OrderTransDef> transDefs = session.CreateEntityQuery<OrderTransDef>()
                .Where(
                    Exp.Eq("OrderTypeCode", orderTypeCode)
                    & Exp.Eq("StepType", TransStepType.Single) //为MultiSelect时，界面让用户选择交易类型，根据交易类型再取库房区域
                    & Exp.Eq("TransLocationFrom", TransLocationFrom.InterfaceValue) //为ConfigValue时，一般配置中只出现一个库房区域，不需要选择
                ).List<OrderTransDef>();
            if (transDefs == null || transDefs.Count != 1)
                throw new Exception(string.Format("Order type {0} has mutiple transaction steps, the trans type code is required in this case", orderTypeCode));
            if (string.IsNullOrEmpty(transDefs[0].TransTypeCode) || transDefs[0].TransTypeCode.Trim().Length <= 0)
                throw new Exception(string.Format("Trans type code for order type {0} is empty", orderTypeCode));
            return ERPUtil.GetWHAreaByTransType(session, transDefs[0].TransTypeCode, locationCode);
        }
        private static IList<WHArea> GetWHAreaByTransType(ISession session, string transTypeCode, string locationCode)
        {
            ObjectQuery query = session.CreateObjectQuery(@"
select a.*
from WHArea a
inner join WHAreaCfg c on c.AreaCfgCode=@cfgCode and c.AreaCode=a.AreaCode
where a.IsReservedArea=@isReserved and a.IsTransArea=@isTrans and a.Status=@status
order by a.LocationCode,a.AreaCode
")
                .Attach(typeof(WHArea)).Attach(typeof(WHAreaCfg))
                .SetValue("@cfgCode", transTypeCode, "c.AreaCfgCode")
                .SetValue("@isReserved", false, "a.IsReservedArea") //系统内置的库房区域不取
                .SetValue("@isTrans", true, "a.IsTransArea") //只取可交易的库房区域
                .SetValue("@status", WHStatus.Enable, "a.Status") //只取有效状态的库房区域
                ;
            if (!string.IsNullOrEmpty(locationCode) && locationCode.Trim().Length > 0)
                query.And(Exp.Eq("a.LocationCode", locationCode.Trim()));
            return query.List<WHArea>();
        }

        public static string EnumText<T>(T enumValue)
        {
            string value = "";
            _enumText.TryGetValue(typeof(T).Name + "." + enumValue.ToString(), out value);
            return value;
        }
    }
}