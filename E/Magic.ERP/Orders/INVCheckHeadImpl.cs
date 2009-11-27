namespace Magic.ERP.Orders
{
    using System;
    using System.Data;
    using System.Collections.Generic;
    using Magic.Framework.Data;
    using Magic.Framework.ORM;
    using Magic.Framework.ORM.Query;
    using Magic.ERP.Core;
    using Magic.Basis;
    using Magic.Framework.Utils;

    public partial class INVCheckHead : IApprovable, IWHTransHead
    {
        private log4net.ILog log = log4net.LogManager.GetLogger(typeof(INVCheckHead));

        public const string ORDER_TYPE_CHK = "SC0";
        public const string ORDER_TYPE_ADJ = "SA0";

        bool IWHTransHead.PreLockStock
        {
            get { return false; }
        }

        public INVCheckHead(string location, INVCheckType type, string note, int createUser)
        {
            this._orderNumber = ERPUtil.NextOrderNumber(ORDER_TYPE_CHK);
            this._orderTypeCode = ORDER_TYPE_CHK;
            this._locationCode = location;
            this._checkType = type;
            this._note = note;
            this._createUser = createUser;
            this._createTime = DateTime.Now;
            this._approveResult = ApproveStatus.UnApprove;
            this._approveUser = 0;
            this._approveTime = new DateTime(1900, 1, 1);
            this._currentLineNumber = "0000";
            this._status = INVCheckStatus.New;
        }

        /// <summary>
        /// 下一个行号码
        /// </summary>
        /// <returns></returns>
        public virtual string NextLineNumber()
        {
            int number = Magic.Framework.Utils.Cast.Int(this.CurrentLineNumber);
            number = number % 10 == 0 ? number + 10 : ((number + 9) / 10 * 10);
            string result = number.ToString().PadLeft(4, '0');
            this.CurrentLineNumber = result;
            return result;
        }

        /// <summary>
        /// 签核完成
        /// </summary>
        /// <param name="session"></param>
        public virtual void OnApprove(ISession session)
        {
            if (this.ApproveResult == ApproveStatus.Reject)
            {
                if (this._orderTypeCode == INVCheckHead.ORDER_TYPE_CHK)
                    this.Status = INVCheckStatus.Confirm;
                else
                    this._status = INVCheckStatus.New;
                this.Update(session, "Status");
            }
        }

        /// <summary>
        /// 签核完成
        /// </summary>
        /// <param name="session"></param>
        public virtual void PostApprove(ISession session)
        {
            try
            {
                session.BeginTransaction();
                this.Close(session);
                session.Commit();
            }
            catch (Exception er)
            {
                log.Error((this._orderTypeCode == ORDER_TYPE_CHK ? "sto chk" : "sto adj") + this.OrderNumber + " was approved, error occurs while trying to close this order automatically", er);
                session.Rollback();
            }
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
            //解除盘点库位的锁定
            if (this._orderTypeCode == ORDER_TYPE_CHK)
            {
                IList<WHArea> areas = session.CreateObjectQuery(@"
select 1 from WHArea where AreaCode in(
    select AreaCode from INVCheckWh where OrderNumber=?ordNumber
)")
                    .Attach(typeof(WHArea)).Attach(typeof(INVCheckWh))
                    .SetValue("?ordNumber", this._orderNumber, EntityManager.GetPropMapping(typeof(INVCheckWh), "OrderNumber").DbTypeInfo)
                    .List<WHArea>();
                foreach (WHArea area in areas)
                {
                    area.IsLocked = false;
                    area.Update(session, "IsLocked");
                }
            }
        }
        /// <summary>
        /// 取交易明细
        /// </summary>
        /// <param name="session"></param>
        /// <returns></returns>
        public virtual IList<IWHTransLine> GetLines(ISession session)
        {
            //只有数量不相等的盘点明细参与库房交易
            IList<INVCheckLine> lines = session.CreateObjectQuery(@"
select 1 from INVCheckLine where BeforeQty<>CurrentQty and OrderNumber=?ordNum")
                .Attach(typeof(INVCheckLine))
                .SetValue("?ordNum", this._orderNumber, "OrderNumber")
                .OrderBy("SKUID").OrderBy("LineNumber")
                .List<INVCheckLine>();
            IList<IWHTransLine> result = new List<IWHTransLine>(lines.Count);
            foreach (INVCheckLine line in lines)
                result.Add(line);
            return result;
        }

        public void RemoveArea(ISession session, string area)
        {
            if (this._status != INVCheckStatus.New)
                throw new Exception("单据不是新建状态，无法执行该操作");
            if (string.IsNullOrEmpty(area) || area.Trim().Length <= 0) return;
            INVCheckWh.Delete(session, this.OrderNumber, area);
        }
        public void AddArea(ISession session, string area)
        {
            if (this._status != INVCheckStatus.New)
                throw new Exception("单据不是新建状态，无法执行该操作");
            if (string.IsNullOrEmpty(area) || area.Trim().Length <= 0) return;
            INVCheckWh wh = new INVCheckWh();
            wh.OrderNumber = this._orderNumber;
            wh.AreaCode = area;
            wh.Create(session);
        }
        public void ConfirmCheckOrder(ISession session)
        {
            if (this._status != INVCheckStatus.New)
                throw new Exception("单据不是新建状态，无法执行该操作");
            if (session.CreateEntityQuery<INVCheckWh>()
                .Where(Exp.Eq("OrderNumber", this._orderNumber))
                .Count() <= 0)
                throw new Exception("您还没有选择要盘点的库位");

            #region 锁定盘点库位
            IList<WHArea> areas = session.CreateObjectQuery(@"
select 1 from WHArea where AreaCode in(
    select AreaCode from INVCheckWh where OrderNumber=?ordNumber
)")
                .Attach(typeof(WHArea)).Attach(typeof(INVCheckWh))
                .SetValue("?ordNumber", this._orderNumber, EntityManager.GetPropMapping(typeof(INVCheckWh), "OrderNumber").DbTypeInfo)
                .List<WHArea>();
            foreach (WHArea area in areas)
            {
                area.IsLocked = true;
                area.Update(session, "IsLocked");
            }
            #endregion

            #region 盘点单明细
            //TODO: 
            //1. 明细记录数无法超过9999
            //2. 物料定义废弃料状态，盘点时不处理这些废弃料
            DbSession dbSession = session.DbSession as DbSession;
            Type type = typeof(INVCheckLine), stoType = typeof(StockDetail);
            System.Text.StringBuilder sql = new System.Text.StringBuilder();
            sql.Append("INSERT INTO ")
                .Append(EntityManager.GetEntityMapping(type).TableName)
                .Append("(")
                    .Append(EntityManager.GetPropMapping(type, "OrderNumber").ColumnName).Append(",")
                    .Append(EntityManager.GetPropMapping(type, "LineNumber").ColumnName).Append(",")
                    .Append(EntityManager.GetPropMapping(type, "SKUID").ColumnName).Append(",")
                    .Append(EntityManager.GetPropMapping(type, "UnitID").ColumnName).Append(",")
                    .Append(EntityManager.GetPropMapping(type, "LocationCode").ColumnName).Append(",")
                    .Append(EntityManager.GetPropMapping(type, "AreaCode").ColumnName).Append(",")
                    .Append(EntityManager.GetPropMapping(type, "SectionCode").ColumnName).Append(",")
                    .Append(EntityManager.GetPropMapping(type, "BeforeQty").ColumnName).Append(",")
                    .Append(EntityManager.GetPropMapping(type, "CurrentQty").ColumnName).Append(",")
                    .Append(EntityManager.GetPropMapping(type, "StockDetailID").ColumnName)
                .Append(") SELECT '")
                .Append(this._orderNumber).Append("' AS ORDNUM,")
                .Append("TRIM(TO_CHAR(ROWNUM,'0000')) AS LINENUM,")
                .Append("T.SKU,0 AS UNIT,T.LOC_C,T.AREA_C,T.SEC_C,T.BEF_QTY,0 AS CUR_QTY,STODID ")
                .Append("FROM(SELECT ")
                        .Append(EntityManager.GetPropMapping(stoType, "SKUID").ColumnName).Append(" AS SKU,")
                        .Append(EntityManager.GetPropMapping(stoType, "LocationCode").ColumnName).Append(" AS LOC_C,")
                        .Append(EntityManager.GetPropMapping(stoType, "AreaCode").ColumnName).Append(" AS AREA_C,")
                        .Append(EntityManager.GetPropMapping(stoType, "SectionCode").ColumnName).Append(" AS SEC_C,")
                        .Append(EntityManager.GetPropMapping(stoType, "StockQty").ColumnName).Append(" AS BEF_QTY,")
                        .Append(EntityManager.GetPropMapping(stoType, "StockDetailID").ColumnName).Append(" AS STODID ")
                    .Append("FROM ").Append(EntityManager.GetEntityMapping(stoType).TableName).Append(" ")
                    .Append("WHERE ")
                        .Append(EntityManager.GetPropMapping(stoType, "LocationCode").ColumnName).Append("='").Append(this._locationCode).Append("'")
                        .Append(" AND ").Append(EntityManager.GetPropMapping(stoType, "AreaCode").ColumnName).Append(" IN(")
                            .Append("SELECT ").Append(EntityManager.GetPropMapping(typeof(INVCheckWh), "AreaCode").ColumnName).Append(" ")
                            .Append("FROM ").Append(EntityManager.GetEntityMapping(typeof(INVCheckWh)).TableName).Append(" ")
                            .Append("WHERE ").Append(EntityManager.GetPropMapping(typeof(INVCheckWh), "OrderNumber").ColumnName).Append("='").Append(this._orderNumber).Append("'")
                        .Append(") ")
                    .Append("ORDER BY ")
                        .Append(EntityManager.GetPropMapping(stoType, "AreaCode").ColumnName).Append(",")
                        .Append(EntityManager.GetPropMapping(stoType, "SectionCode").ColumnName).Append(",")
                        .Append(EntityManager.GetPropMapping(stoType, "SKUID").ColumnName)
                .Append(")T");
            dbSession.ExecuteNonQuery(sql.ToString());
            #endregion

            this._status = INVCheckStatus.Confirm;
            this.Update(session, "Status");
        }
        public void Release(ISession session)
        {
            #region 检查
            if (this.OrderTypeCode == ORDER_TYPE_CHK && this._status != INVCheckStatus.Confirm)
                throw new Exception("状态为确认的盘点单才可以执行发布操作");
            if (this.OrderTypeCode == ORDER_TYPE_ADJ && this._status != INVCheckStatus.New)
                throw new Exception("新建状态的库存调整单才可以执行发布操作");
            if (session.CreateEntityQuery<INVCheckLine>()
                .Where(Exp.Eq("OrderNumber", this._orderNumber))
                .SetLastOffset(1)
                .Count() <= 0)
                throw new Exception("没有明细，无法执行发布操作");
            #endregion

            this._status = INVCheckStatus.Release;
            this._approveResult = ApproveStatus.UnApprove;
            this.Update(session, "Status", "ApproveResult");
            ERPUtil.ApproveThis(session, this);
        }
        public void Close(ISession session)
        {
            if (this._status != INVCheckStatus.Release || this._approveResult != ApproveStatus.Approve)
                throw new Exception("只有发布后的盘点单能够执行关闭操作");
            ERPUtil.CommitWHTrans(session, this);
            this._status = INVCheckStatus.Close;
            this.Update(session, "Status");
        }

        public static DataSet Query(ISession session, bool requireCount, out int count, string location, string sku, string itemCode, string itemName, string color, string size, string area, string section, int pageIndex, int pageSize)
        {
            ObjectQuery query = session.CreateObjectQuery(@"
select sku.BarCode as BarCode,i.ItemCode as ItemCode,i.ItemName as ItemName
    ,sku.ColorCode as ColorCode,color.ColorText as ColorText,sku.SizeCode as SizeCode
    ,sto.AreaCode as AreaCode,sto.SectionCode as SectionCode,sto.StockQty as StockQty,sto.FrozenQty as FrozenQty
    ,sto.StockDetailID as StockDetailID
from StockDetail sto
inner join ItemSpec sku on sku.SKUID=sto.SKUID
inner join ItemMaster i on i.ItemID=sku.ItemID
left join ItemColor color on color.ColorCode=sku.ColorCode
where sto.LocationCode=?loc
order by i.ItemCode,sku.ColorCode,sku.SizeCode,sto.AreaCode,sto.SectionCode")
                .Attach(typeof(StockDetail))
                .Attach(typeof(ItemSpec)).Attach(typeof(ItemMaster)).Attach(typeof(ItemColor))
                .SetValue("?loc", location, "sto.LocationCode")
                .SetPage(pageIndex, pageSize);

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
        public INVCheckLine NewLineAdj(ISession session, int stoDetailId, decimal adjQty)
        {
            StockDetail sto = StockDetail.Retrieve(session, stoDetailId);
            if (sto == null)
            {
                log.ErrorFormat("add stock adj line for {0}, stock detail id {1} not exists", this.OrderNumber, stoDetailId);
                throw new Exception("选择的库存项不存在");
            }

            INVCheckLine line = new INVCheckLine();
            line.OrderNumber = this.OrderNumber;
            line.StockDetailID = stoDetailId;
            line.LocationCode = sto.LocationCode;
            line.AreaCode = sto.AreaCode;
            line.SectionCode = sto.SectionCode;
            line.BeforeQty = sto.StockQty;
            line.CurrentQty = adjQty;
            line.SKUID = sto.SKUID;
            line.UnitID = 0;

            return line;
        }
        /// <summary>
        /// 保存库存盘点明细，lines中每个对象必须设置好LineNumber、CurrentQty这2个属性
        /// </summary>
        /// <param name="session"></param>
        /// <param name="orderNumber"></param>
        /// <param name="lines"></param>
        public void UpdateLines(ISession session, IList<INVCheckLine> lines)
        {
            if (lines == null || lines.Count <= 0) return;
            foreach (INVCheckLine line in lines)
            {
                INVCheckLine lineToSave = INVCheckLine.Retrieve(session, this.OrderNumber, line.LineNumber);
                if (lineToSave == null) throw new Exception("库存盘点单" + this.OrderNumber + "中不存在明细项目" + line.LineNumber);
                lineToSave.CurrentQty = line.CurrentQty;
                lineToSave.Update(session, "CurrentQty");
            }
        }
        public void ClearCheckQty(ISession session)
        {
            session.CreateEntityQuery<INVCheckLine>()
                .Where(Exp.Eq("OrderNumber", this.OrderNumber))
                .Update(new string[] { "CurrentQty" }, new object[] { 0M });
        }
        /// <summary>
        /// 库存调整单添加或更新明细
        /// <para>以StockDetailID判断INVCheckLine明细对象是否已经存在，不存在则新增，存在则更新</para>
        /// <para>如果是新增，lines中对象的各个属性必须都已经设置好；如果是更新，只更新CurrentQty属性</para>
        /// </summary>
        /// <param name="session"></param>
        /// <param name="lines"></param>
        public void CreateOrUpdateLines(ISession session, IList<INVCheckLine> lines)
        {
            if (this.Status != INVCheckStatus.New && this.Status != INVCheckStatus.Confirm)
            {
                log.WarnFormat("status of sto {0} {1} is {2}, attemping to update lines is denied", this.OrderTypeCode == ORDER_TYPE_CHK ? "chk" : "adj", this.OrderNumber, this.Status.ToString());
                throw new Exception("不可以更新");
            }
            bool needUpdateHead = false;
            foreach (INVCheckLine line in lines)
            {
                INVCheckLine temp = this.GetLine(session, line.StockDetailID);
                if (temp != null)
                {
                    temp.CurrentQty = line.CurrentQty;
                    temp.Update(session, "CurrentQty");
                }
                else
                {
                    line.LineNumber = this.NextLineNumber();
                    needUpdateHead = true;
                    line.Create(session);
                }
            }
            if (needUpdateHead) this.Update(session, "CurrentLineNumber");
        }
        private INVCheckLine GetLine(ISession session, int stodid)
        {
            if (stodid <= 0) return null;
            IList<INVCheckLine> lines = session.CreateEntityQuery<INVCheckLine>()
                .Where(Exp.Eq("OrderNumber", this.OrderNumber) & Exp.Eq("StockDetailID", stodid))
                .SetLastOffset(1)
                .List<INVCheckLine>();
            return lines == null || lines.Count <= 0 ? null : lines[0];
        }
    }
}