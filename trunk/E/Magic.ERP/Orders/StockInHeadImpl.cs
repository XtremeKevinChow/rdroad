namespace Magic.ERP.Orders
{
    using System;
    using System.Data;
    using System.Collections.Generic;
    using Magic.Framework.Data;
    using Magic.Framework.ORM;
    using Magic.Framework.ORM.Query;
    using Magic.Framework.ORM.Mapping;
    using Magic.ERP.Core;
    using Magic.Basis;

    public partial class StockInHead : IApprovable, IWHTransHead
    {
        private static log4net.ILog log = log4net.LogManager.GetLogger(typeof(StockInHead));
        public const string ORD_TYPE_ASSIST_IN = "AI0";
        public const string ORD_TYPE_ASSIST_OUT = "AO0";
        public const string ORD_TYPE_PRD_IN = "PIN";
        public const string ORD_TYPE_PRD_OUT = "POU";

        /// <summary>
        /// 下一个行号码
        /// </summary>
        /// <returns></returns>
        public virtual string NextLineNumber()
        {
            int number = Magic.Framework.Utils.Cast.Int(this.CurrentLineNumber);
            //number = number % 10 == 0 ? number + 10 : ((number + 9) / 10 * 10);
            number++;
            string result = number.ToString().PadLeft(4, '0');
            this.CurrentLineNumber = result;
            return result;
        }

        void IApprovable.OnApprove(ISession session)
        {
            if (this.ApproveResult == ApproveStatus.Approve)
            {
                this.Status = StockInStatus.Open;
                this.Update(session, "Status");
            }

            if (this.ApproveResult == ApproveStatus.Reject)
            {
                this.Status = StockInStatus.New;
                this.Update(session, "Status");
            }
        }
        void IApprovable.PostApprove(ISession session)
        {
            if (this.ApproveResult == ApproveStatus.Approve)
            {
                using (ISession newSession = new Session())
                {
                    try
                    {
                        newSession.BeginTransaction();
                        this.Close(session);
                        newSession.Commit();
                    }
                    catch (Exception ex)
                    {
                        newSession.Rollback();
                        log.Error(string.Format("入库单{0}签核完成，自动Close时发生异常", this.OrderNumber), ex);
                    }
                }
            }
        }

        string IWHTransHead.RefOrderType
        {
            get
            {
                return " ";
            }
        }
        string IWHTransHead.RefOrderNumber
        {
            get
            {
                return " ";
            }
        }
        string IWHTransHead.OriginalOrderType
        {
            get
            {
                return " ";
            }
        }
        string IWHTransHead.OrginalOrderNumber
        {
            get
            {
                return " ";
            }
        }
        void IWHTransHead.AfterTransaction(ISession session)
        {
            this.Status = StockInStatus.Close;
            this.Update(session, "Status");
        }
        IList<IWHTransLine> IWHTransHead.GetLines(ISession session)
        {
            IList<StockInLine> lines = session.CreateEntityQuery<StockInLine>()
                .Where(Exp.Eq("OrderNumber", this._orderNumber))
                .OrderBy("SKUID").OrderBy("LineNumber")
                .List<StockInLine>();
            IList<IWHTransLine> result = new List<IWHTransLine>(lines.Count);
            foreach (StockInLine line in lines)
                result.Add(line);
            return result;
        }

        public void Close(ISession session)
        {
            ERPUtil.CommitWHTrans(session, this);
        }
        public void UpdateLines(ISession session, IList<StockInLine> lines2Save)
        {
            if (lines2Save == null || lines2Save.Count <= 0) return;

            #region 检查
            if (this._status != StockInStatus.New)
                throw new Exception("单据不是新建状态，无法更新");

            IList<StockInLine> lines = new List<StockInLine>(lines2Save.Count);
            bool error = false, errorHead = false;
            System.Text.StringBuilder builder = new System.Text.StringBuilder();
            foreach (StockInLine item in lines2Save)
            {
                errorHead = false;
                if (item.Quantity <= 0M)
                {
                    error = true;
                    if (!errorHead) builder.Append("行号").Append(item.LineNumber).Append(": ");
                    errorHead = true;
                    builder.Append("数量无效; ");
                }
                if (this._orderTypeCode == StockInHead.ORD_TYPE_ASSIST_IN)
                {
                    if (string.IsNullOrEmpty(item.AreaCode) || item.AreaCode.Trim().Length <= 0)
                    {
                        error = true;
                        if (!errorHead) builder.Append("行号").Append(item.LineNumber).Append(": ");
                        errorHead = true;
                        builder.Append("仓库为空; ");
                    }
                    if (!string.IsNullOrEmpty(item.SectionCode) && item.SectionCode.Trim().Length > 0)
                    {
                        WHSection section = WHSection.Retrieve(session, item.AreaCode, item.SectionCode);
                        if (section == null)
                        {
                            error = true;
                            if (!errorHead) builder.Append("行号").Append(item.LineNumber).Append(": ");
                            errorHead = true;
                            builder.Append("货架{").Append(item.AreaCode).Append("-").Append(item.SectionCode).Append("}不存在; ");
                        }
                    }
                }
                else if (this._orderTypeCode == StockInHead.ORD_TYPE_ASSIST_OUT)
                {
                    if (item.Quantity > item.RefQuantity)
                    {
                        error = true;
                        if (!errorHead) builder.Append("行号").Append(item.LineNumber).Append(": ");
                        errorHead = true;
                        builder.Append("领用数量大于库存数量; ");
                    }
                }
                if (!error) lines.Add(StockInLine.Retrieve(session, item.OrderNumber, item.LineNumber));
            }
            if (error)
                throw new Exception(builder.ToString());
            #endregion

            for (int i = 0; i < lines.Count; i++)
            {
                StockInLine line = lines[i];
                if (this._orderTypeCode == StockInHead.ORD_TYPE_ASSIST_IN)
                {
                    line.AreaCode = lines2Save[i].AreaCode;
                    line.SectionCode = lines2Save[i].SectionCode;
                    line.Quantity = lines2Save[i].Quantity;
                    line.Price = lines2Save[i].Price;
                    line.Update(session, "AreaCode", "SectionCode", "Quantity", "Price");
                }
                else
                {
                    line.Quantity = lines2Save[i].Quantity;
                    line.Update(session, "Quantity");
                }
            }
        }
        public void Release(ISession session)
        {
            #region 检查
            if (this._status != StockInStatus.New)
                throw new Exception("单据不是新建状态，无法发布");
            IList<StockInLine> lines = session.CreateEntityQuery<StockInLine>()
                .Where(Exp.Eq("OrderNumber", this._orderNumber))
                .OrderBy("LineNumber")
                .List<StockInLine>();
            System.Text.StringBuilder builder = new System.Text.StringBuilder();
            if (lines.Count <= 0) builder.Append("单据").Append(this._orderNumber).Append("没有明细，无法发布");
            else
            {
                foreach (StockInLine line in lines)
                {
                    if (string.IsNullOrEmpty(line.AreaCode) || line.AreaCode.Trim().Length <= 0)
                        builder.Append("行号").Append(line.LineNumber).Append("仓库无效; ");
                    if (line.Quantity <= 0)
                        builder.Append("行号").Append(line.LineNumber).Append("数量无效; ");
                }
            }
            if (builder.Length > 0)
                throw new Exception(builder.ToString());
            #endregion

            if (this.OrderTypeCode != StockInHead.ORD_TYPE_PRD_IN)
            {
                this._status = StockInStatus.Release;
                ERPUtil.ApproveThis(session, this);
            }
            //产品入库单，发布后需要填写价格再走签核、入库处理流程
            else this._status = StockInStatus.Confirm;
            this.Update(session, "Status");
        }
        public void Confirm(ISession session)
        {
            if (this._status != StockInStatus.Confirm)
                throw new Exception("单据不是发布状态，无法审批入库价格");

            if (this.OrderTypeCode != StockInHead.ORD_TYPE_PRD_IN)
                throw new Exception(this.OrderNumber + "不是产品入库单");
            this._status = StockInStatus.Release;
            this.Update(session, "Status");
            ERPUtil.ApproveThis(session, this);
        }
        public void UpdatePrice(ISession session, IList<StockInLine> lines2Save)
        {
            if (lines2Save == null || lines2Save.Count <= 0) return;
            if (this._status != StockInStatus.Confirm)
                throw new Exception("单据不是发布状态，无法更新入库价格");

            foreach (StockInLine l in lines2Save)
                l.Update(session, "Price");
        }
        public int AddLines(ISession session, string[] skus)
        {
            StockInLine line;
            if (skus == null | skus.Length <= 0) return 0;
            int count = 0;
            if (this._orderTypeCode == ORD_TYPE_ASSIST_IN)
            {
                foreach (string s in skus)
                {
                    int skuId = Magic.Framework.Utils.Cast.Int(s, 0);
                    if (skuId <= 0) continue;
                    line = new StockInLine();
                    line.OrderNumber = this._orderNumber;
                    line.LineNumber = this.NextLineNumber();
                    line.SKUID = skuId;
                    line.UnitID = 0;
                    line.LocationCode = this._locationCode;
                    line.AreaCode = "";
                    line.SectionCode = "";
                    line.Quantity = 0M;
                    line.Price = 0M;
                    line.Create(session);
                    count++;
                }
            }
            else
            {
                foreach (string s in skus)
                {
                    string[] temp = s.Split(',');
                    if (temp == null || temp.Length != 4) continue;
                    int skuId = Magic.Framework.Utils.Cast.Int(temp[0], 0);
                    if (skuId <= 0) continue;
                    line = new StockInLine();
                    line.OrderNumber = this._orderNumber;
                    line.LineNumber = this.NextLineNumber();
                    line.SKUID = skuId;
                    line.UnitID = 0;
                    line.LocationCode = this._locationCode;
                    line.AreaCode = temp[1];
                    line.SectionCode = temp[2];
                    line.RefQuantity = Magic.Framework.Utils.Cast.Decimal(temp[3]);
                    line.Quantity = 0M;
                    line.Price = 0M;
                    line.Create(session);
                    count++;
                }
            }
            this.Update(session, "CurrentLineNumber");
            return count;
        }
        public static DataSet QueryLine(ISession session, bool isAssisItem, string ordNum, bool requireCount, out int count, string location, string sku, string itemCode, string itemName, string color, string size, string area, string section, int pageIndex, int pageSize)
        {
            ObjectQuery query = session.CreateObjectQuery(@"
select sku.BarCode as BarCode,i.ItemCode as ItemCode,i.ItemName as ItemName
    ,sku.ColorCode as ColorCode,color.ColorText as ColorText,sku.SizeCode as SizeCode
    ,sto.AreaCode as AreaCode,sto.SectionCode as SectionCode,sto.StockQty as StockQty,sto.FrozenQty as FrozenQty
    ,sto.StockDetailID as StockDetailID,l.LineNumber as LineNumber,l.Quantity as Qty
    ,case when l.LineNumber is null then sku.AvgMoveCost else l.Price end as Price
from StockDetail sto
inner join ItemSpec sku on sku.SKUID=sto.SKUID
inner join ItemMaster i on i.ItemID=sku.ItemID
left join ItemColor color on color.ColorCode=sku.ColorCode
left join StockInLine l on l.OrderNumber=?stoOrdNum and l.StockDetailID=sto.StockDetailID
where sto.LocationCode=?loc and i.ItemType=?itype
order by i.ItemCode,sku.ColorCode,sku.SizeCode,sto.AreaCode,sto.SectionCode")
                .Attach(typeof(StockDetail)).Attach(typeof(StockInLine))
                .Attach(typeof(ItemSpec)).Attach(typeof(ItemMaster)).Attach(typeof(ItemColor))
                .SetValue("?loc", location, "sto.LocationCode")
                .SetValue("?stoOrdNum", ordNum, "l.OrderNumber")
                .SetPage(pageIndex, pageSize);

            if (isAssisItem)
                query.SetValue("?itype", ItemType.AssistantItem, "i.ItemType");
            else
                query.SetValue("?itype", ItemType.NormalItem, "i.ItemType");
            if (!string.IsNullOrEmpty(area))
                query.And(Exp.Eq("sto.AreaCode", area));
            if (section.Trim().Length > 0) query.And(Exp.Like("sto.SectionCode", "%" + section.Trim().ToUpper() + "%"));
            if (sku.Trim().Length > 0) query.And(Exp.Like("sku.BarCode", "%" + sku.Trim().ToUpper() + "%"));
            if (itemCode.Trim().Length > 0) query.And(Exp.Like("i.ItemCode", "%" + itemCode.Trim().ToUpper() + "%"));
            if (itemName.Trim().Length > 0) query.And(Exp.Like("i.ItemName", "%" + itemName.Trim() + "%"));
            if (color.Trim().Length > 0) query.And(Exp.Like("sku.ColorCode", "%" + color.Trim().ToUpper() + "%"));
            if (size.Trim().Length > 0) query.And(Exp.Like("sku.SizeCode", "%" + size.Trim().ToUpper() + "%"));

            count = 0;
            if (requireCount) count = query.Count();

            return query.DataSet();
        }
        public void CreateOrUpdateLines(ISession session, IList<StockInLine> lines)
        {
            if (this.Status != StockInStatus.New)
            {
                log.WarnFormat("order status is {0}, attemping to update lines is denied", this.Status.ToString());
                throw new Exception("单据不是新建状态，不可以更新");
            }
            bool needUpdateHead = false;
            foreach (StockInLine line in lines)
            {
                StockDetail sto = null;
                if (this.OrderTypeCode == StockInHead.ORD_TYPE_PRD_IN || this.OrderTypeCode == StockInHead.ORD_TYPE_ASSIST_IN)
                {
                    sto = StockDetail.Retrieve(session, line.SKUID, line.LocationCode, line.AreaCode, line.SectionCode);
                    if (sto == null)
                    {
                        sto = new StockDetail();
                        sto.LocationCode = line.LocationCode;
                        sto.SKUID = line.SKUID;
                        sto.AreaCode = line.AreaCode;
                        sto.SectionCode = string.IsNullOrEmpty(line.SectionCode) ? " " : line.SectionCode;
                        sto.LotNumber = " ";
                        sto.StockQty = 0M;
                        sto.FrozenQty = 0M;
                        sto.Create(session);
                    }
                }
                else
                    sto = StockDetail.Retrieve(session, line.StockDetailID);
                if (sto == null)
                {
                    log.ErrorFormat("add line for {0}, stock detail id {1} not exists", this.OrderNumber, line.StockDetailID);
                    throw new Exception("库存项不存在");
                }
                if ((this.OrderTypeCode == StockInHead.ORD_TYPE_ASSIST_OUT || this.OrderTypeCode == StockInHead.ORD_TYPE_PRD_OUT)
                    && (line.Quantity > sto.StockQty - sto.FrozenQty))
                    throw new Exception("领用数量超过可使用库存数量");
                if (!string.IsNullOrEmpty(line.LineNumber) && line.LineNumber.Trim().Length > 0)
                {
                    //更新
                    line.RefQuantity = sto.StockQty;
                    line.LocationCode = sto.LocationCode;
                    line.AreaCode = sto.AreaCode;
                    line.SectionCode = sto.SectionCode;
                    line.StockDetailID = sto.StockDetailID;
                    ItemSpec sku = ItemSpec.Retrieve(session, sto.SKUID);
                    line.Price = sku.AvgMoveCost;
                    line.Update(session, "LocationCode", "AreaCode", "SectionCode", "StockDetailID", "RefQuantity", "Quantity", "Price");
                }
                else
                {
                    //创建
                    line.OrderNumber = this.OrderNumber;
                    line.LineNumber = this.NextLineNumber();
                    line.LocationCode = sto.LocationCode;
                    line.AreaCode = sto.AreaCode;
                    line.SectionCode = sto.SectionCode;
                    line.StockDetailID = sto.StockDetailID;
                    line.SKUID = sto.SKUID;
                    line.UnitID = 0;
                    line.RefQuantity = sto.StockQty;

                    ItemSpec sku = ItemSpec.Retrieve(session, sto.SKUID);
                    line.Price = sku.AvgMoveCost;

                    line.Create(session);
                    needUpdateHead = true;
                }
            }
            if (needUpdateHead) this.Update(session, "CurrentLineNumber");
        }
        //将产品领用单明细添加到产品入库单中
        public void AddPrdOutDetail2ThisOrder(ISession session)
        {
            if (this.OrderTypeCode != ORD_TYPE_PRD_IN) throw new Exception("只有产品入库单可以执行该方法");
            if (string.IsNullOrEmpty(this.RefOrdNum) || this.RefOrdNum.Trim().Length <= 0)
                return;
            StockInHead refHead = StockInHead.Retrieve(session, this.RefOrdNum);
            if (refHead == null)
                throw new Exception("");
            IList<StockInLine> refLines = session.CreateEntityQuery<StockInLine>()
                .Where(Exp.Eq("OrderNumber", this.RefOrdNum))
                .List<StockInLine>();
            if (refLines.Count <= 0) return;
            foreach (StockInLine refLine in refLines)
            {
                StockInLine line = new StockInLine();
                line.OrderNumber = this.OrderNumber;
                line.LineNumber = this.NextLineNumber();
                line.LocationCode = refLine.LocationCode;
                line.AreaCode = refLine.AreaCode;
                line.SectionCode = refLine.SectionCode;
                line.StockDetailID = refLine.StockDetailID;
                line.SKUID = refLine.SKUID;
                line.Quantity = refLine.Quantity;
                line.Price = refLine.Price;
                line.RefQuantity = 0M;
                line.UnitID = 0;
                line.Create(session);
            }
            this.Update(session, "CurrentLineNumber");
        }
    }
}