namespace Magic.ERP.Orders
{
    using System;
    using System.Data;
    using System.Collections.Generic;
    using Magic.Framework.ORM;
    using Magic.Framework.ORM.Query;
    using Magic.Framework.Data;
    using Magic.ERP.Core;
    using Magic.Framework;
    using Magic.Framework.Utils;

    public partial class RCVHead : IApprovable, IWHTransHead
    {
        private static log4net.ILog log = log4net.LogManager.GetLogger(typeof(RCVHead));

        public const string ORD_TYPE_PUR = "RC1";
        public const string ORD_TYPE_RTN = "RC2";
        public const string ORD_TYPE_EXCHG = "RC3";

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
            number++;
            string result = number.ToString().PadLeft(4, '0');
            this.CurrentLineNumber = result;
            return result;
        }

        /// <summary>
        /// 签核时的处理
        /// </summary>
        /// <param name="session"></param>
        void IApprovable.OnApprove(ISession session)
        {
            if (this._approveResult == ApproveStatus.Approve)
            {
                //签核通过，更新为待发货状态，然后尝试关闭收货单
                this._status = ReceiveStatus.Open;  //更新状态
                this.Update(session, "Status");
            }
            else
            {
                //驳回，更改回新建状态
                this._status = ReceiveStatus.New;
                this.Update(session, "Status");
            }
        }

        /// <summary>
        /// 签核完成后的处理
        /// </summary>
        /// <param name="session"></param>
        void IApprovable.PostApprove(ISession session)
        {
            //TODO: 是否使用配置控制这个行为
            RCVHead.Close(this); //使用新的session完成关闭操作，避免关闭操作发生异常时造成签核处理无法完成
        }

        /// <summary>
        /// 交易执行完毕的回调函数
        /// </summary>
        /// <param name="session"></param>
        void IWHTransHead.AfterTransaction(ISession session)
        {
        }

        /// <summary>
        /// 取交易明细
        /// </summary>
        /// <param name="session"></param>
        /// <returns></returns>
        IList<IWHTransLine> IWHTransHead.GetLines(ISession session)
        {
            IList<RCVLine> lines = session.CreateEntityQuery<RCVLine>()
                .Where(Exp.Eq("OrderNumber", this._orderNumber))
                .OrderBy("SKUID").OrderBy("LineNumber")
                .List<RCVLine>();
            IList<IWHTransLine> result = new List<IWHTransLine>(lines.Count);
            foreach (RCVLine rcv in lines)
                result.Add(rcv);
            return result;
        }

        /// <summary>
        /// 删除收货单所有明细
        /// </summary>
        /// <param name="session"></param>
        /// <returns></returns>
        public int DeleteLines(ISession session)
        {
            //采购收货，删除时需要恢复采购订单明细的冗余字段值
            IList<RCVLine> lines = session.CreateEntityQuery<RCVLine>()
                .Where(Exp.Eq("OrderNumber", this.OrderNumber))
                .List<RCVLine>();
            return this.DeleteLines(session, lines);
        }
        /// <summary>
        /// 删除收货单指定明细
        /// </summary>
        /// <param name="session"></param>
        /// <param name="lienesToDelete"></param>
        /// <returns></returns>
        public int DeleteLines(ISession session, IList<RCVLine> lienesToDelete)
        {
            if (lienesToDelete == null || lienesToDelete.Count <= 0) return 0;
            if (this._status != ReceiveStatus.New)
                throw new Exception("收货单不是新建状态，无法删除明细");

            int count = 0;
            foreach (RCVLine ltd in lienesToDelete)
            {
                if (this._orderTypeCode == RCVHead.ORD_TYPE_PUR)
                {   //采购收货，删除明细时需要恢复采购订单的冗余字段值
                    POLine poLine = null;
                    if (!string.IsNullOrEmpty(this._refOrderNumber) && this._refOrderNumber.Trim().Length > 0
                        && !string.IsNullOrEmpty(ltd.RefOrderLine) && ltd.RefOrderLine.Trim().Length > 0)
                        poLine = POLine.Retrieve(session, this._refOrderNumber, ltd.RefOrderLine);
                    if (poLine != null) poLine.UnfinishedReceiveQtyChange(session, -ltd.QualifiedQty);
                }
                ltd.Delete(session);

                count++;
            }

            return count;
        }
        /// <summary>
        /// 添加收货明细，引用指定采购订单的所有可收货明细
        /// </summary>
        /// <param name="session"></param>
        /// <param name="poNumber"></param>
        /// <returns></returns>
        public int AddLinesFromRefOrder(ISession session)
        {
            int count = 0;
            switch (this._orderTypeCode)
            {
                case RCVHead.ORD_TYPE_PUR:
                    foreach (POLine poLine in POHead.ReceivableLines(session, this._refOrderNumber))
                        if (this.AddLine(session, poLine)) count++;
                    break;
            }
            return count;
        }
        /// <summary>
        /// 添加收货明细，引用POLine信息
        /// </summary>
        /// <param name="session"></param>
        /// <param name="poLine"></param>
        /// <returns></returns>
        public bool AddLine(ISession session, POLine poLine)
        {
            if (poLine.ReceivableQty() <= 0M) return false;
            if (!poLine.UnfinishedReceiveQtyChange(session, poLine.ReceivableQty())) return false;

            RCVLine line = new RCVLine();
            line.OrderNumber = this.OrderNumber;
            line.LineNumber = this.NextLineNumber();
            line.TransTypeCode = " ";
            line.LocationCode = this.LocationCode;
            line.SKUID = poLine.SKUID;
            line.UnitID = poLine.UnitID;
            line.RefQty = poLine.ReceivableQty();
            line.RCVTotalQty = poLine.ReceivableQty();
            line.QualifiedQty = poLine.ReceivableQty();
            line.UnQualifiedQty = 0M;
            line.RefOrderLine = line.OriginalOrderLine = poLine.LineNumber;
            line.TaxValue = 0M;  // poLine.TaxValue; 系统默认进项税不可以退税抵扣，所以交易税率设置为0，需财务手工确定可以抵扣的进项税率
            line.Price = poLine.Price;
            return line.Create(session);
        }
        public SimpleJson AddLine(ISession session, string poLineNumber, string areaCode, string sectionCode, decimal qty)
        {
            //检查
            if (string.IsNullOrEmpty(poLineNumber) || poLineNumber.Trim().Length <= 0)
                return new SimpleJson().HandleError("PO行为空");
            if (string.IsNullOrEmpty(areaCode) || areaCode.Trim().Length <= 0)
                return new SimpleJson().HandleError("库位为空");
            if (!string.IsNullOrEmpty(sectionCode) && sectionCode.Trim().Length > 0)
            {
                WHSection section = WHSection.Retrieve(session, areaCode, sectionCode);
                if (session == null)
                    return new SimpleJson()
                        .HandleError("库位" + areaCode.Trim().ToUpper() + "中的货架" + sectionCode.Trim().ToUpper() + "不存在");
            }
            if (qty <= 0) return new SimpleJson().HandleError("收货数量" + qty.ToString() + "小于0");
            POLine poLine = POLine.Retrieve(session, this.RefOrderNumber, poLineNumber);
            if (poLine == null) return new SimpleJson().HandleError("PO " + this.RefOrderNumber + "中不存在" + poLineNumber + "的行");
            if (poLine.ReceivableQty() <= 0M)
                return new SimpleJson().HandleError("订单" + this.RefOrderNumber + "行" + poLineNumber + "可收货数量为0");
            if (!poLine.UnfinishedReceiveQtyChange(session, qty))
                return new SimpleJson().HandleError("无法更新订单" + this.RefOrderNumber + "行" + poLineNumber + "的待入库数量");

            RCVLine line = new RCVLine();
            line.OrderNumber = this.OrderNumber;
            line.LineNumber = this.NextLineNumber();
            line.TransTypeCode = " ";
            line.LocationCode = this.LocationCode;
            line.AreaCode = areaCode.Trim().ToUpper();
            line.SectionCode = string.IsNullOrEmpty(sectionCode) ? " " : sectionCode.Trim().ToUpper();
            line.SKUID = poLine.SKUID;
            line.UnitID = poLine.UnitID;
            line.RefQty = poLine.ReceivableQty();
            line.RCVTotalQty = qty;
            line.QualifiedQty = qty;
            line.UnQualifiedQty = 0M;
            line.RefOrderLine = line.OriginalOrderLine = poLine.LineNumber;
            line.TaxValue = 0M; // poLine.TaxValue;  系统默认进项税不可以退税抵扣，所以交易税率设置为0，需财务手工确定可以抵扣的进项税率
            line.Price = poLine.Price;
            line.Create(session);

            this.Update(session, "CurrentLineNumber");

            return new SimpleJson();
        }
        public int UpdateLines(ISession session, IList<RCVLine> linesValue)
        {
            if (linesValue == null || linesValue.Count <= 0) return 0;

            #region 检查
            IList<RCVLine> lines = new List<RCVLine>(linesValue.Count);
            bool error = false, errorHead = false;
            System.Text.StringBuilder builder = new System.Text.StringBuilder();
            bool hasSection = false;
            foreach (RCVLine lv in linesValue)
            {
                errorHead = false;
                //收货数量是否有效
                if (lv.RCVTotalQty <= 0M)
                {
                    error = true;
                    if (!errorHead) builder.Append("行号").Append(lv.LineNumber).Append(": ");
                    errorHead = true;
                    builder.Append("收货数量小于0; ");
                    continue;
                }
                RCVLine l = RCVLine.Retrieve(session, lv.OrderNumber, lv.LineNumber);
                //可以不填写货架，但如果填写了，则检查货架有效性
                WHSection section = null;
                hasSection = false;
                //库位、货架容量检查
                WHArea area = WHArea.Retrieve(session, l.AreaCode);
                if (!string.IsNullOrEmpty(l.SectionCode) && l.SectionCode.Trim().Length > 0)
                {
                    hasSection = true;
                    section = WHSection.Retrieve(session, l.AreaCode, l.SectionCode);
                }
                decimal capacityFree = 0M;
                //取库位、货架容量
                if (hasSection) capacityFree = section.SectionCapacity;
                else capacityFree = area.AreaCapacity;
                //库位容量扣减当前已存放量
                if (hasSection)
                    capacityFree = capacityFree - Cast.Decimal(session.CreateObjectQuery(@"
select sum(StockQty) from StockDetail 
where AreaCode=?area and SectionCode=?section")
                        .Attach(typeof(StockDetail))
                        .SetValue("?area", area.AreaCode, "AreaCode")
                        .SetValue("?section", section.SectionCode, "SectionCode")
                        .Scalar(), 0M);
                else
                    capacityFree = capacityFree - Cast.Decimal(session.CreateObjectQuery(@"select sum(StockQty) from StockDetail where AreaCode=?area")
                        .Attach(typeof(StockDetail))
                        .SetValue("?area", area.AreaCode, "AreaCode")
                        .Scalar(), 0M);
                //剩余容量和本次入库量比较
                if (capacityFree < lv.QualifiedQty)
                {
                    builder.Append("行号").Append(lv.LineNumber).Append(": ");
                    error = true;
                    builder.Append("入库量").Append(lv.QualifiedQty).Append("大于剩余容量").Append(capacityFree);
                }
                if (!error) lines.Add(l);
            }
            if (error)
                throw new Exception(builder.ToString());
            #endregion

            int count = 0;
            DbSession dbsession = session.DbSession as DbSession;
            session.BeginTransaction();
            for (int i = 0; i < lines.Count; i++)
            {
                RCVLine line = lines[i];
                POLine poLine = null;
                if (!string.IsNullOrEmpty(this._refOrderNumber) && this._refOrderNumber.Trim().Length > 0
                    && !string.IsNullOrEmpty(line.RefOrderLine) && line.RefOrderLine.Trim().Length > 0)
                    poLine = POLine.Retrieve(session, this._refOrderNumber, line.RefOrderLine);
                if (poLine != null)
                {
                    IDbCommand cmd = dbsession.CreateStoredProcCommand("F_PUR_RCV_TOLERANCE_RATIO", new object[] { 0, poLine.OrderNumber });
                    dbsession.ExecuteNonQuery(cmd);
                    IDbDataParameter p = cmd.Parameters[0] as IDbDataParameter;
                    decimal ration = Cast.Decimal(p.Value);
                    if (poLine.PurchaseQty * (1 + ration) - poLine.ReceiveQty - poLine.UnfinishedReceiveQty - linesValue[i].QualifiedQty + line.QualifiedQty < 0)
                    {
                        error = true;
                        builder.Append("行号").Append(line.LineNumber).Append("收货数量").Append(linesValue[i].QualifiedQty)
                            .Append("超过PO最大可收货数量").Append(Math.Floor(poLine.PurchaseQty * (1 + ration)) - poLine.ReceiveQty - poLine.UnfinishedReceiveQty + line.QualifiedQty);
                        break;
                    }
                    poLine.UnfinishedReceiveQtyChange(session, linesValue[i].QualifiedQty - line.QualifiedQty);
                }
                line.RCVTotalQty = linesValue[i].RCVTotalQty;
                line.QualifiedQty = linesValue[i].QualifiedQty;
                line.Update(session, "RCVTotalQty", "QualifiedQty");

                count++;
            }
            if (error)
            {
                session.Rollback();
                throw new Exception(builder.ToString());
            }
            else
                session.Commit();

            return count;
        }
        public void Release(ISession session)
        {
            #region 检查
            if (this.Status != ReceiveStatus.New)
                throw new Exception(string.Format("收货单{0}不是新建状态，无法执行发布操作", this.OrderNumber));
            int count = session.CreateEntityQuery<RCVLine>()
                .Where(Exp.Eq("OrderNumber", this.OrderNumber))
                .Count();
            if (count <= 0) throw new Exception(string.Format("收货单{0}没有明细，不能发布", this.OrderNumber));
            IList<RCVLine> lines = session.CreateEntityQuery<RCVLine>()
                .Where(Exp.Eq("OrderNumber", this._orderNumber))
                .List<RCVLine>();
            bool error = false, errorHead = false;
            System.Text.StringBuilder builder = new System.Text.StringBuilder();
            foreach (RCVLine line in lines)
            {
                errorHead = false;
                if (string.IsNullOrEmpty(line.LocationCode) || line.LocationCode.Trim().Length <= 0)
                {
                    error = true;
                    if (!errorHead) builder.Append("行号").Append(line.LineNumber).Append(":");
                    builder.Append("仓储地为空; ");
                    errorHead = true;
                }
                if (string.IsNullOrEmpty(line.AreaCode) || line.AreaCode.Trim().Length <= 0)
                {
                    error = true;
                    if (!errorHead) builder.Append("行号").Append(line.LineNumber).Append(":");
                    builder.Append("入库区域为空; ");
                    errorHead = true;
                }
                if (line.RCVTotalQty <= 0M)
                {
                    error = true;
                    if (!errorHead) builder.Append("行号").Append(line.LineNumber).Append(":");
                    builder.Append("收货数量无效; ");
                    errorHead = true;
                }
                if (line.QualifiedQty <= 0M)
                {
                    error = true;
                    if (!errorHead) builder.Append("行号").Append(line.LineNumber).Append(":");
                    builder.Append("合格数量无效; ");
                    errorHead = true;
                }
            }
            if (error)
                throw new Exception(builder.ToString());
            #endregion

            this.Status = ReceiveStatus.Release;
            this.ApproveResult = ApproveStatus.UnApprove;
            this.Update(session, "Status", "ApproveResult");
            ERPUtil.ApproveThis(session, this); //送签单据
        }
        public static void Close(RCVHead head)
        {
            try
            {
                using (ISession session = new Session())
                {
                    try
                    {
                        session.BeginTransaction();
                        head.Close(session, false);
                        session.Commit();
                    }
                    catch (Exception er1)
                    {
                        session.Rollback();
                        log.Error(string.Format("收货单{0}签核完成，自动关闭时发生异常", head.OrderNumber), er1);
                        return;
                    }
                }
            }
            catch (Exception er)
            {
                log.Error(string.Format("收货单{0}签核完成，自动关闭时发生异常，无法连接数据库", head.OrderNumber), er);
                return;
            }
        }
        public void Close(ISession session, bool throwException)
        {
            if (this._status != ReceiveStatus.Open)
            {
                log.ErrorFormat("收货单{0}不是待发货状态，无法执行关闭操作", this._orderNumber);
                if (throwException)
                    throw new Exception(string.Format("收货单{0}不是待发货状态，无法执行关闭操作", this._orderNumber));
                return;
            }

            //签核完成后会调用这个方法，尝试将收货单自动关闭，如果这个方法发生异常，签核处理正常完成，需要手工来关闭这个单据
            //签核完成的关闭动作将新开一个session来完成，确保关闭时的异常不会影响签核操作的结束；手工关闭时由界面开session来执行这个方法
            try
            {
                //库存交易
                ERPUtil.CommitWHTrans(session, this);
                //更新PO行的冗余字段值
                if (this._orderTypeCode == RCVHead.ORD_TYPE_PUR
                    && !string.IsNullOrEmpty(this._refOrderNumber) && this._refOrderNumber.Trim().Length > 0)
                {
                    IList<RCVLine> rcvLines = session.CreateEntityQuery<RCVLine>()
                        .Where(Exp.Eq("OrderNumber", this._orderNumber))
                        .OrderBy("LineNumber")
                        .List<RCVLine>();
                    foreach (RCVLine rcv in rcvLines)
                    {
                        if (!string.IsNullOrEmpty(rcv.RefOrderLine) && rcv.RefOrderLine.Trim().Length > 0)
                        {
                            POLine poLine = POLine.Retrieve(session, this._refOrderNumber, rcv.RefOrderLine);
                            if (poLine != null)
                                poLine.ReceiveFinish(session, rcv.RCVTotalQty, rcv.QualifiedQty);
                        }
                    }
                }
                //更新本身状态
                this._status = ReceiveStatus.Close;
                this.Update(session, "Status");
            }
            catch (Exception er)
            {
                log.Error(string.Format("收货单{0}关闭时发生异常", this._orderNumber), er);
                if (throwException) throw er;
            }
        }

        public static RCVHead CreatePurchaseRCV(ISession session, int userId, string poNumber, string note)
        {
            RCVHead head = new RCVHead();
            head._refOrderType = head._originalOrderType = POHead.ORDER_TYPE;
            head._refOrderNumber = head._orginalOrderNumber = poNumber.Trim().ToUpper();
            #region 检查
            //暂时不允许采购收货，但不引用采购订单的方式
            if (string.IsNullOrEmpty(head._refOrderNumber))
                throw new Exception("采购订单为空");
            POHead po = POHead.Retrieve(session, head._refOrderNumber);
            if (po == null)
                throw new Exception(string.Format("采购订单{0}不存在", head._refOrderNumber));
            if (po.Status != POStatus.Release)
                throw new Exception(string.Format("采购订单{0}不是发布状态，不可以进行收货作业", head._refOrderNumber));
            if (po.ApproveResult != ApproveStatus.Approve)
                throw new Exception(string.Format("采购订单{0}还没有完成签核，不可以进行收货作业", head._refOrderNumber));
            #endregion
            head._orderTypeCode = RCVHead.ORD_TYPE_PUR;
            head._orderNumber = ERPUtil.NextOrderNumber(head._orderTypeCode);
            head._objectID = po.VendorID;
            head._locationCode = po.LocationCode;
            head._createUser = userId;
            head._note = note.Trim();
            EntityManager.Create(session, head);

            return head;
        }
    }
}