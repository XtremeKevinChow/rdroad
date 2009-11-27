namespace Magic.ERP.Orders
{
    using System;
    using System.Data;
    using System.Collections.Generic;
    using Magic.Framework.ORM;
    using Magic.Framework.ORM.Query;
    using Magic.Basis;
    using Magic.ERP.Core;
    using Magic.Framework.Utils;

    public partial class WHTransferHead : IApprovable, IWHTransHead
    {
        public static log4net.ILog log = log4net.LogManager.GetLogger(typeof(WHTransferHead));

        //合格品正常移库
        public const string ORDER_TYPE_NORMAL = "MV1";

        bool IWHTransHead.PreLockStock
        {
            get { return false; }
        }

        /// <summary>
        /// 下一个行号码
        /// </summary>
        /// <returns></returns>
        public virtual string NextLineNumber()
        {
            int number = Magic.Framework.Utils.Cast.Int(this.CurrentLineNumber);
            //number = number % 10 == 0 ? number + 10 : ((number + 9) / 10 * 10);
            string result = (++number).ToString().PadLeft(4, '0');
            this.CurrentLineNumber = result;
            return result;
        }

        /// <summary>
        /// 签核处理
        /// </summary>
        /// <param name="session"></param>
        public virtual void OnApprove(ISession session)
        {
            if (this._approveResult == ApproveStatus.Approve)
            {
                //签核通过，更新为待发货状态，然后尝试关闭收货单
                log.DebugFormat("order {0} is approved, status -> {1}", this.OrderNumber, WHTransferStatus.Open.ToString());
                this._status = WHTransferStatus.Open;
                this.Update(session, "Status");
                //还是用同一个session来关闭
                log.DebugFormat("try to close {0} after approve", this.OrderNumber);
                this.Close(session);
            }
            else
            {
                //驳回，更改回新建状态
                log.DebugFormat("order {0} is rejected, status -> {1}", this.OrderNumber, WHTransferStatus.New.ToString());
                this._status = WHTransferStatus.New;
                this.Update(session, "Status");
            }
        }
        /// <summary>
        /// 签核完成之后
        /// </summary>
        /// <param name="session"></param>
        public virtual void PostApprove(ISession session)
        {
        }

        /// <summary>
        /// 引用单据类型（直接触发某个交易的单据类型）
        /// 例如会员退货，引用单据类型为发货单类型
        /// </summary>
        public virtual string RefOrderType
        {
            get
            {
                return " ";
            }
        }

        public virtual string RefOrderNumber
        {
            get
            {
                return " ";
            }
        }

        /// <summary>
        /// 原始单据类型
        /// 例如会员退货，原始单据类型为会员订单（销售订单）类型
        /// </summary>
        public virtual string OriginalOrderType
        {
            get { return " "; }
        }

        public virtual string OrginalOrderNumber
        {
            get
            {
                return " ";
            }
        }

        /// <summary>
        /// 交易执行完毕的回调函数
        /// </summary>
        /// <param name="session"></param>
        public virtual void AfterTransaction(ISession session)
        {
            this.Status = WHTransferStatus.Close;
            this.Update(session, "Status");
        }

        /// <summary>
        /// 取交易明细
        /// </summary>
        /// <param name="session"></param>
        /// <returns></returns>
        public virtual IList<IWHTransLine> GetLines(ISession session)
        {
            IList<WHTransferLine> lines = session.CreateEntityQuery<WHTransferLine>()
                .Where(Exp.Eq("OrderNumber", this.OrderNumber))
                .OrderBy("LineNumber")
                .List<WHTransferLine>();
            IList<IWHTransLine> result = new List<IWHTransLine>(lines.Count);
            foreach (WHTransferLine line in lines)
                result.Add(line);
            return result;
        }

        public static DataSet Query(ISession session, bool requireCount, out int count, string fromLocation, string sku, string itemCode, string itemName, string color, string size, string area, string section, int pageIndex, int pageSize)
        {
            ObjectQuery query = session.CreateObjectQuery(@"
select sku.BarCode as BarCode,i.ItemCode as ItemCode,i.ItemName as ItemName
    ,sku.ColorCode as ColorCode,color.ColorText as ColorText,sku.SizeCode as SizeCode
    ,sto.AreaCode as AreaCode,sto.SectionCode as SectionCode,sto.StockQty as StockQty,sto.FrozenQty as FrozenQty
    ,0 as TempQty,0 as TransferQty
    ,sto.StockDetailID as StockDetailID
from StockDetail sto
inner join ItemSpec sku on sku.SKUID=sto.SKUID
inner join ItemMaster i on i.ItemID=sku.ItemID
left join ItemColor color on color.ColorCode=sku.ColorCode
where sto.StockQty>0 and sto.LocationCode=?loc
order by i.ItemCode,sku.ColorCode,sku.SizeCode,sto.AreaCode,sto.SectionCode")
                .Attach(typeof(StockDetail))
                .Attach(typeof(ItemSpec)).Attach(typeof(ItemMaster)).Attach(typeof(ItemColor))
                .SetValue("?loc", fromLocation, "sto.LocationCode")
                .SetPage(pageIndex, pageSize);

            if (!string.IsNullOrEmpty(area))
                query.And(Exp.Eq("sto.AreaCode", area));
            if (section.Trim().Length > 0) query.And(Exp.Like("sto.SectionCode", "%" + section.Trim().ToUpper() + "%"));
            if (sku.Trim().Length > 0) query.And(Exp.Like("sku.BarCode", "%" + sku.Trim().ToUpper() + "%"));
            if (itemCode.Trim().Length > 0) query.And(Exp.Like("i.ItemCode", "%" + itemCode.Trim().ToUpper() + "%"));
            if (itemName.Trim().Length > 0) query.And(Exp.Like("i.ItemName", "%" + itemName.Trim() + "%"));
            if (color.Trim().Length > 0) query.And(Exp.Like("sku.ColorCode", "%" + color.Trim().ToUpper() + "%"));
            if (size.Trim().Length > 0) query.And(Exp.Like("sku.SizeCode", "%" + size.Trim().ToUpper() + "%"));

            //计算可移库数量
            DataSet ds = query.DataSet();
            if (ds.Tables[0].Rows.Count > 0)
            {
                IList<int> stoids = new List<int>(ds.Tables[0].Rows.Count);
                foreach (DataRow row in ds.Tables[0].Rows)
                    stoids.Add(Cast.Int(row["StockDetailID"]));
                DataSet tempDs = session.CreateObjectQuery(@"
select l.FromStockID as FromStockID,sum(l.MoveQty) as Qty
from WHTransferHead h
inner join WHTransferLine l on h.OrderNumber=l.OrderNumber
group by l.FromStockID")
                    .Attach(typeof(WHTransferHead)).Attach(typeof(WHTransferLine))
                    .Where(Exp.In("h.Status", WHTransferStatus.New, WHTransferStatus.Release, WHTransferStatus.Open) & Exp.In("l.FromStockID", stoids))
                    .DataSet();
                foreach (DataRow row in ds.Tables[0].Rows)
                {
                    DataRow[] rows = tempDs.Tables[0].Select("FromStockID=" + Cast.Int(row["StockDetailID"]).ToString());
                    if (rows != null && rows.Length > 0)
                        row["TempQty"] = Cast.Decimal(rows[0]["Qty"]);
                    row["TransferQty"] = Cast.Decimal(row["StockQty"]) - Cast.Decimal(row["FrozenQty"]) - Cast.Decimal(row["TempQty"]);
                }
            }

            count = 0;
            if (requireCount) count = query.Count();

            return ds;
        }

        public void AddLines(ISession session, IList<WHTransferLine> lines)
        {
            if (this.Status != WHTransferStatus.New)
            {
                log.WarnFormat("status of transfer order {0} is not New, lines can't be added", this.OrderNumber);
                throw new Exception(string.Format("移库单{0}不是新建状态，无法添加明细", this.OrderNumber));
            }
            //检查
            System.Text.StringBuilder builder = new System.Text.StringBuilder();
            bool msghead = false;
            ItemSpec sku = null; ;
            foreach (WHTransferLine line in lines)
            {
                msghead = false;
                sku = null;
                StockDetail sto = StockDetail.Retrieve(session, line.FromStockID);
                if (sto == null)
                {
                    log.ErrorFormat("detail stock ID {0} not exists", line.FromStockID);
                    throw new Exception(string.Format("库存明细ID {0}不存在", line.FromStockID));
                }

                line.OrderNumber = this.OrderNumber;
                line.LineNumber = this.NextLineNumber();
                line.SKUID = sto.SKUID;
                line.FromLocation = Cast.String(this.FromLocation, "").Trim().ToUpper();
                line.ToLocation = Cast.String(this.ToLocation, "").Trim().ToUpper();
                line.FromArea = Cast.String(sto.AreaCode).Trim().ToUpper();
                line.FromSection = Cast.String(sto.SectionCode).Trim().ToUpper();
                line.UnitID = 0;
                bool isAreaEmpty = false;
                if (string.IsNullOrEmpty(line.ToArea) || line.ToArea.Trim().Length <= 0)
                {
                    if (!msghead)
                    {
                        if (sku == null) sku = ItemSpec.Retrieve(session, sto.SKUID);
                        builder.Append(sku.BarCode).Append(":");
                    }
                    builder.Append("移入库位为空;");
                    isAreaEmpty = true;
                }
                else if (!string.IsNullOrEmpty(line.ToSection) && line.ToSection.Trim().Length > 0)
                {
                    WHSection section = WHSection.Retrieve(session, line.ToArea, line.ToSection);
                    if (section == null)
                    {
                        if (!msghead)
                        {
                            if (sku == null) sku = ItemSpec.Retrieve(session, sto.SKUID);
                            builder.Append(sku.BarCode).Append(":");
                        }
                        builder.Append("移入库位").Append(line.ToArea).Append("中不存在货架").Append(line.ToSection).Append(";");
                    }
                }
                if (!isAreaEmpty && line.FromArea == Cast.String(line.ToArea).Trim().ToUpper() && line.FromSection == Cast.String(line.ToSection, "").Trim().ToUpper())
                {
                    if (sku == null) sku = ItemSpec.Retrieve(session, sto.SKUID);
                    builder.Append(sku.BarCode).Append(":");
                    builder.Append("来源库位-货架与移入库位-货架一样;");
                }
            }

            if (builder.Length > 0)
            {
                log.Warn(builder.ToString());
                throw new Exception(builder.ToString());
            }

            foreach (WHTransferLine line in lines)
                line.Create(session);
            this.Update(session, "CurrentLineNumber");
        }
        public void Release(ISession session)
        {
            if (this.Status != WHTransferStatus.New)
            {
                log.WarnFormat("移库单{0}不是新建状态，无法执行发布操作", this.OrderNumber);
                throw new Exception(string.Format("移库单{0}不是新建状态，无法执行发布操作", this.OrderNumber));
            }
            IList<WHTransferLine> lines = session.CreateEntityQuery<WHTransferLine>()
                .Where(Exp.Eq("OrderNumber", this.OrderNumber))
                .List<WHTransferLine>();
            if (lines.Count <= 0)
            {
                log.ErrorFormat("移库单{0}没有明细，无法执行发布操作", this.OrderNumber);
                throw new Exception(string.Format("移库单{0}没有明细，无法执行发布操作", this.OrderNumber));
            }
            this.Status = WHTransferStatus.Release;
            this.Update(session, "Status");
            ERPUtil.ApproveThis(session, this);
        }
        public void Close(ISession session)
        {
            if (this.Status != WHTransferStatus.Open)
            {
                log.ErrorFormat("移库单{0}不是待移库状态，无法执行关闭操作", this.OrderNumber);
                throw new Exception(string.Format("移库单{0}不是待移库状态，无法执行关闭操作", this.OrderNumber));
            }
            this.Status = WHTransferStatus.Close;
            this.Update(session, "Status");
            ERPUtil.CommitWHTrans(session, this);
        }
    }
}