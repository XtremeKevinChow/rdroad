//*******************************************
// ** Description:  Data Access Object for ReturnHead
// ** Author     :  Code generator
// ** Created    :   2008-8-25 20:29:49
// ** Modified   :
//*******************************************

namespace Magic.ERP.Orders
{
    using System;
    using System.Data;
    using System.Text;
    using System.Collections.Generic;
    using Magic.Framework.ORM;
    using Magic.Framework.Data;
    using Magic.Framework.Utils;
    using Magic.ERP.Core;
    using Magic.Framework.ORM.Query;
    using Magic.Basis;

    public partial class ReturnHead : IApprovable, IWHTransHead
    {
        private static log4net.ILog log = log4net.LogManager.GetLogger(typeof(ReturnHead));

        public const string ORDER_TYPE_MBR_RTN = "RC2";
        public const string ORDER_TYPE_LOGISTICS_RTN = "RC4";
        public const string ORDER_TYPE_INNER_RTN = "RC5";
        public const string ORDER_TYPE_EXCHANGE_RTN = "RC3";
        private IList<ReturnLine> _lines = null; //该属性只有在做库房交易过程才被赋值，仅内部使用

        bool IWHTransHead.PreLockStock
        {
            get { return false; }
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
                this._status = ReturnStatus.Open;
                this.Update(session, "Status");
                //还是用同一个session来关闭
                this.Close(session);
            }
            else
            {
                //驳回，更改回新建状态
                this._status = ReturnStatus.New;
                this.Update(session, "Status");
            }
        }
        /// <summary>
        /// 签核完成后的处理
        /// </summary>
        /// <param name="session"></param>
        void IApprovable.PostApprove(ISession session)
        {
        }

        string IWHTransHead.OriginalOrderType
        {
            get
            {
                return "SO0";
            }
        }
        string IWHTransHead.RefOrderType
        {
            get
            {
                return CRMSN.ORDER_TYPE_CODE_SD;
            }
        }
        IList<IWHTransLine> IWHTransHead.GetLines(ISession session)
        {
            IList<ReturnLine> lines = session.CreateEntityQuery<ReturnLine>()
                .Where(Exp.Eq("OrderNumber", this.OrderNumber))
                .OrderBy("SKUID").OrderBy("RefOrderLineID")
                .List<ReturnLine>();
            this._lines = lines;
            IList<IWHTransLine> whLines = new List<IWHTransLine>(lines.Count);
            foreach (ReturnLine line in lines)
            {
                if (this.OrderTypeCode != ReturnHead.ORDER_TYPE_EXCHANGE_RTN)
                    line.LocationCode = this.LocationCode;
                else
                {
                    WHArea area = WHArea.Retrieve(session, line.AreaCode);
                    line.LocationCode = area.LocationCode;
                }
                whLines.Add(line);
            }
            return whLines;
        }
        void IWHTransHead.AfterTransaction(ISession session)
        {
            DbSession dbSession = session.DbSession as DbSession;
            IDbCommand command = null;
            //CRM接口需要的退货类型
            int returnType = 0;
            switch (this.OrderTypeCode)
            {
                case ORDER_TYPE_MBR_RTN: returnType = 0; break; //会员退货
                case ORDER_TYPE_LOGISTICS_RTN: returnType = 1; break; //物流退货
                case ORDER_TYPE_INNER_RTN: returnType = 2; break; //内部退货
                case ORDER_TYPE_EXCHANGE_RTN: //换货退货，不需要调用CRM存储过程，只更新换货订单状态
                    {
                        SOHead saleOrder = SOHead.Query(session, this.ExchangeOrder);
                        if (saleOrder == null) return;
                        //saleOrder.UpdateStatus(session, 25).UpdateLineStatus(session, 30);
                        command = dbSession.CreateStoredProcCommand("f_order_change_confirm", new object[] { 0, saleOrder.ID });
                        dbSession.ExecuteNonQuery(command);
                        int r = Cast.Int((command.Parameters[0] as IDataParameter).Value);
                        if (r == 0 || r == -2) return;
                        else throw new Exception("更新换货订单状态时出错，f_order_change_confirm返回" + r.ToString());
                    };
                default: throw new Exception("无效的退货类型");
            }
            //判断是否全退
            bool isReturnAll = false;
            int snLineCount = session.CreateEntityQuery<CRMSNLine>()
                .Where(Exp.Eq("SNID", this.RefOrderID))
                .Count();
            isReturnAll = snLineCount == this._lines.Count; //第一步，发货单明细与退货明细数量不相等，则一定不是全退
            StringBuilder snLineIds = new StringBuilder();
            for (int i = 0; i < this._lines.Count; i++)
            {
                if (this._lines[i].Quantity != this._lines[i].DeliverQuantity) isReturnAll = false;
                if (i != 0) snLineIds.Append(",");
                snLineIds.Append(this._lines[i].RefOrderLineID.ToString()).Append("-").Append(this._lines[i].Quantity.ToString());
            }
            //检查：物流退货、内部退货必须是全退
            if (!isReturnAll && (this.OrderTypeCode == ORDER_TYPE_LOGISTICS_RTN || this.OrderTypeCode == ORDER_TYPE_INNER_RTN))
                throw new Exception("物流退货、内部退货必须是全退");

            //调用CRM存储过程
            if (isReturnAll)
            {
                //全退: ORDERS.P_RETURN_SHIPPINGNOTICES
                //        v_shipping_id number, --CRM发货单ID
                //        v_vouch varchar2, --退货单号码
                //        v_comments VARCHAR2, --退货备注信息
                //        v_operator_id NUMBER, --退货人，ERP与CRM用户不同，现在没有对应起来
                //        v_return_order_type INTEGER, --退货类型: 0会员退货，1物流退货，2内部退货
                //        v_is_bad INTEGER, --是否恶意退货
                //        v_return OUT NUMBER --返回值
                command = dbSession.CreateStoredProcCommand("ORDERS.P_RETURN_SHIPPINGNOTICES", new object[] { this.RefOrderID, this.OrderNumber, 0, returnType, this.IsMalicious ? 1 : 0, 0 });
            }
            else
            {
                //部分退货，目前只支持针对部分发货明细全退
                //2008-11-03: 部分退货可以指定退货数量了
                //  : ORDERS.P_PARTLY_RETURN2
                //        v_shipping_id number, --CRM发货单ID
                //        v_lines VARCHAR2, --发货单明细，格式为 明细ID1,明细ID2,明细ID3...
                //        v_vouch varchar2, --退货单号码
                //        v_return OUT NUMBER --返回值
                command = dbSession.CreateStoredProcCommand("ORDERS.p_partly_return", new object[] { this.RefOrderID, snLineIds.ToString(), this.OrderNumber, 0 });
            }

            dbSession.ExecuteNonQuery(command);
            //存储过程中
            IDataParameter param = command.Parameters[command.Parameters.Count - 1] as IDataParameter;
            int result = Cast.Int(param.Value);
            if (result < 0)
                throw new Exception("退货时发生异常，异常信息: " + result.ToString());

            //退货完成后添加退货统计信息
            command = dbSession.CreateStoredProcCommand("p_rpt_fi_sale_return", new object[] { this.OrderNumber, 0, 0 });
            dbSession.ExecuteNonQuery(command);
            param = command.Parameters[1] as IDataParameter;
            result = Cast.Int(param.Value);
            //退货后出现不正常现象，原因是因为礼品、套装等优惠的幅度太大，退货后礼品和解套的套装都按原价计算，因此可能出现会员退货之后还需要再支付一定金额
            //解决方法：检查会员当前帐户余额是否足够（不能出现负数），出现负数时（例如退货后帐户余额变成-127）提示仓库人员，并且回滚事务
            //              仓库人员通知客服和财务，客服与会员进行确认，财务在会员帐户上手工充值（充值127），再由仓库执行退货操作
            //              上面这些事情做完之后，退货后会员帐户余额变成0
            decimal returnAmt = Cast.Decimal((command.Parameters[2] as IDataParameter).Value);
            if (returnAmt < 0)
            {
                //如果退货金额小于0，查会员帐户余额是否出现小于0的情况
                command = dbSession.CreateSqlStringCommand(@"
Select m.deposit
From mbr_members m
Inner Join ord_headers o On o.buyer_id=m.Id
Where o.so_number=:sonum");
                dbSession.AddParameter(command, ":sonum", DbTypeInfo.AnsiString(16), this.OrginalOrderNumber);
                decimal accountAmt = Cast.Decimal(dbSession.ExecuteScalar(command));
                if (accountAmt < 0) throw new Exception("该退货单退货后导致会员帐户出现负数，请联系客服人员进行处理（退货金额为" + returnAmt.ToString("#0.#0") + "，退货后帐户余额为" + accountAmt.ToString("#0.#0") + "）");
            }
            switch (result)
            {
                case -1001:
                    throw new Exception("本月的库存期间没有正常开启，请联系系统维护人员");
                case -1002:
                    command = dbSession.CreateSqlStringCommand("select * from fi_rpt_sale_return where rt_number='" + this.OrderNumber + "'");
                    DataSet ds = dbSession.ExecuteDataSet(command);
                    throw new Exception("退货统计数据错误，请联系系统维护人员" + "<br />" +
                        "销售:" + Cast.Decimal(ds.Tables[0].Rows[0]["sale_amt"]).ToString() + ", " +
                        "发送费:" + Cast.Decimal(ds.Tables[0].Rows[0]["transport_amt"]).ToString() + ", " +
                        "包装费:" + Cast.Decimal(ds.Tables[0].Rows[0]["package_amt"]).ToString() + ", " +
                        "礼券:" + Cast.Decimal(ds.Tables[0].Rows[0]["coupons_amt"]).ToString() + ", " +
                        "折扣:" + Cast.Decimal(ds.Tables[0].Rows[0]["discount_amt"]).ToString() + ", " +
                        "礼金:" + Cast.Decimal(ds.Tables[0].Rows[0]["emoney_amt"]).ToString() + ", " +
                        "帐户:" + Cast.Decimal(ds.Tables[0].Rows[0]["account_receivable"]).ToString() + ", " +
                        "POS机:" + Cast.Decimal(ds.Tables[0].Rows[0]["pos_receivable"]).ToString() + ", " +
                        "物流应收:" + Cast.Decimal(ds.Tables[0].Rows[0]["logis_receivable"]).ToString());
                default: break;
            }
        }

        private Logistics GetSNLogistics(ISession session, string sn)
        {
            IList<Logistics> r = session.CreateObjectQuery(@"
select 1
from CRMSN sn
inner join ICLine icl On icl.RefOrderNumber=sn.OrderNumber
inner join ICHead ich on ich.OrderNumber=icl.OrderNumber
inner join Logistics lg on lg.LogisticCompID=ich.LogisticCompID")
                .Attach(typeof(CRMSN)).Attach(typeof(ICLine)).Attach(typeof(ICHead)).Attach(typeof(Logistics))
                .Where(Exp.Eq("sn.OrderNumber", sn))
                .List<Logistics>();

            return r.Count > 0 ? r[0] : null;
        }
        /// <summary>
        /// 会员退货单设置方法，只是设置实体属性，不会update数据库
        /// </summary>
        /// <param name="session"></param>
        /// <param name="locationCode">库位</param>
        /// <param name="snNumber">发货单号码</param>
        /// <param name="reasonId">退货原因</param>
        /// <param name="isMalicious">是否恶意退货</param>
        /// <param name="note">备注</param>
        /// <param name="createUser"></param>
        public void MemberReturn(ISession session, string locationCode, string snNumber, int reasonId, bool isMalicious, string note, int createUser)
        {
            if (string.IsNullOrEmpty(snNumber) || snNumber.Trim().Length <= 0)
                throw new Exception("必须填写发货单号码");
            CRMSN sn = CRMSN.Retrieve(session, snNumber.Trim());
            if (sn == null)
                throw new Exception("发货单" + snNumber + "不存在");
            //if (sn.Status == CRMSNStatus.Return)
            //    throw new Exception("发货单" + sn.OrderNumber + "已经退货");
            //if (sn.Status == CRMSNStatus.PartExchange)
            //    throw new Exception("发货单" + sn.OrderNumber + "已经换货");
            if (sn.Status != CRMSNStatus.Interchanged)
                throw new Exception("发货单" + snNumber + "未完成，无法退货");
            if (!string.IsNullOrEmpty(this.OrderNumber) && this.OrderNumber.Trim().Length > 0
                && !string.IsNullOrEmpty(this.RefOrderNumber) && this.RefOrderNumber.Trim().Length > 0
                && this.RefOrderNumber != snNumber)
            {
                //发货单号码改变，检查是否已经有明细存在，如果已经有明细了则不允许删除
                if (session.CreateEntityQuery<ReturnLine>().Where(Exp.Eq("OrderNumber", this.OrderNumber)).Count() > 0)
                    throw new Exception("退货单" + this.OrderNumber + "已经存在退货明细，无法再改变发货单号码");
            }
            Logistics lg = this.GetSNLogistics(session, snNumber);
            if (lg != null && !lg.CanReturn)
                throw new Exception("系统设置了物流公司" + lg.ShortName + "发货的订单不允许退货，请联系系统管理员");

            this.RefOrderNumber = sn.OrderNumber;
            this.RefOrderID = sn.ID;
            this.OrginalOrderNumber = sn.SaleOrderNumber;
            this.MemberID = sn.MemberID;
            if (sn.MemberID > 0)
            {
                Magic.Basis.Member member = Magic.Basis.Member.Retrieve(session, sn.MemberID);
                if (member != null) this.MemberName = member.Name;
            }
            this.LogisticsID = sn.LogisticsID;
            if (this.LogisticsID > 0)
            {
                Magic.Basis.Logistics logis = Magic.Basis.Logistics.Retrieve(session, this.LogisticsID);
                this.LogisticsName = logis.ShortName;
            }
            this.LocationCode = locationCode;
            this.IsMalicious = isMalicious;
            this.Note = note;
            this.ReasonID = reasonId;
            if (reasonId > 0)
            {
                Magic.Basis.ReturnReason reason = Magic.Basis.ReturnReason.Retrieve(session, reasonId);
                if (reason != null) this.ReasonText = reason.ReasonText;
            }

            this.OrderTypeCode = ORDER_TYPE_MBR_RTN;
            this.CreateUser = createUser;
            this.IsAutoMatch = false;
        }
        public void LogisReturn(ISession session, string locationCode, string snNumber, int reasonId, bool isMalicious, bool hasTransported, string note, int createUser)
        {
            if (string.IsNullOrEmpty(snNumber) || snNumber.Trim().Length <= 0)
                throw new Exception("必须填写发货单号码");
            CRMSN sn = CRMSN.Retrieve(session, snNumber.Trim());
            if (sn == null)
                throw new Exception("发货单" + snNumber + "不存在");
            if (sn.Status == CRMSNStatus.Return)
                throw new Exception("发货单" + sn.OrderNumber + "已经退货");
            if (sn.Status == CRMSNStatus.PartExchange)
                throw new Exception("发货单" + sn.OrderNumber + "已经换货");
            if (sn.Status != CRMSNStatus.Interchanged)
                throw new Exception("发货单" + snNumber + "未完成，无法退货");
            if (!string.IsNullOrEmpty(this.OrderNumber) && this.OrderNumber.Trim().Length > 0
                && !string.IsNullOrEmpty(this.RefOrderNumber) && this.RefOrderNumber.Trim().Length > 0
                && this.RefOrderNumber != snNumber)
            {
                //发货单号码改变，检查是否已经有明细存在，如果已经有明细了则不允许删除
                if (session.CreateEntityQuery<ReturnLine>().Where(Exp.Eq("OrderNumber", this.OrderNumber)).Count() > 0)
                    throw new Exception("退货单" + this.OrderNumber + "已经存在退货明细，无法再改变发货单号码");
            }

            this.RefOrderNumber = sn.OrderNumber;
            this.RefOrderID = sn.ID;
            this.OrginalOrderNumber = sn.SaleOrderNumber;
            this.MemberID = sn.MemberID;
            if (sn.MemberID > 0)
            {
                Magic.Basis.Member member = Magic.Basis.Member.Retrieve(session, sn.MemberID);
                if (member != null) this.MemberName = member.Name;
            }
            this.LogisticsID = sn.LogisticsID;
            if (this.LogisticsID > 0)
            {
                Magic.Basis.Logistics logis = Magic.Basis.Logistics.Retrieve(session, this.LogisticsID);
                this.LogisticsName = logis.ShortName;
            }
            this.LocationCode = locationCode;
            this.IsMalicious = isMalicious;
            this.HasTransported = hasTransported;
            this.Note = note;
            this.ReasonID = reasonId;
            if (reasonId > 0)
            {
                Magic.Basis.ReturnReason reason = Magic.Basis.ReturnReason.Retrieve(session, reasonId);
                if (reason != null) this.ReasonText = reason.ReasonText;
            }

            this.OrderTypeCode = ORDER_TYPE_LOGISTICS_RTN;
            this.CreateUser = createUser;
            this.IsAutoMatch = true;
        }
        /// <summary>
        /// 更新会员换货退货单，只是设置实体属性，不会update数据库
        /// </summary>
        /// <param name="session"></param>
        /// <param name="locationCode">库位</param>
        /// <param name="reasonId">退货原因</param>
        /// <param name="note">备注</param>
        /// <param name="createUser"></param>
        public void ExchangeReturn(ISession session, string locationCode, int reasonId, string note, int createUser)
        {
            this.LocationCode = locationCode;
            this.Note = note;
            this.ReasonID = reasonId;
            if (reasonId > 0)
            {
                Magic.Basis.ReturnReason reason = Magic.Basis.ReturnReason.Retrieve(session, reasonId);
                if (reason != null) this.ReasonText = reason.ReasonText;
            }
        }
        /// <summary>
        /// 下一个行号码
        /// </summary>
        /// <returns></returns>
        public virtual string NextLineNumber()
        {
            int number = Magic.Framework.Utils.Cast.Int(this.CurrentLineNumber);
            //number = number % 10 == 0 ? number + 10 : ((number + 9) / 10 * 10);
            string result = (number + 1).ToString().PadLeft(4, '0');
            this.CurrentLineNumber = result;
            return result;
        }
        public void UpdateOrCreateLines(ISession session, IList<ReturnLine> toSave)
        {
            //确保换货退货不会执行该方法
            if (this.OrderTypeCode == ORDER_TYPE_EXCHANGE_RTN) return;

            log.DebugFormat("to create or save rtn lines, count:{0}", toSave == null ? 0 : toSave.Count);
            if (toSave == null || toSave.Count <= 0) return;
            if (this.Status != ReturnStatus.New)
                throw new Exception("退货单不是新建状态，无法更新");
            //物流退货、内部退货必须全退，不支持部分退货
            if (this.OrderTypeCode == ORDER_TYPE_LOGISTICS_RTN || this.OrderTypeCode == ORDER_TYPE_INNER_RTN)
            {
                int snLineCount = session.CreateEntityQuery<CRMSNLine>().Where(Exp.Eq("SNID", this.RefOrderID)).Count();
                if (snLineCount != toSave.Count)
                    throw new Exception("发货单" + this.RefOrderNumber + "明细(" + snLineCount.ToString() + "项)与退货明细(" + toSave.Count.ToString() + "项)不一致");
            }
            bool updateHeadLineNum = false;
            foreach (ReturnLine line in toSave)
            {
                if (line.RefOrderLineID <= 0 || line.Quantity <= 0M)
                {
                    if (this.OrderTypeCode == ORDER_TYPE_LOGISTICS_RTN || this.OrderTypeCode == ORDER_TYPE_INNER_RTN)
                        throw new Exception("退货明细或者数量无效");
                    continue;
                }
                bool checkCreate = true;
                if (this.OrderTypeCode == ORDER_TYPE_MBR_RTN)
                {
                    //会员退货才可以分多次扫描退货明细
                    ReturnLine existsLine = ReturnLine.Retrieve(session, this.OrderNumber, line.RefOrderLineID);
                    if (existsLine != null)
                    {
                        log.DebugFormat("rtn line({0}) exists, rtn qty:{1}", line.RefOrderLineID, line.Quantity);
                        existsLine.Quantity += line.Quantity;
                        existsLine.Update(session, "Quantity");
                        checkCreate = false;
                    }
                }
                if (checkCreate)
                {
                    CRMSNLine snline = CRMSNLine.Retrieve(session, line.RefOrderLineID);
                    if (snline == null)
                        throw new Exception("发货单明细" + line.RefOrderLineID.ToString() + "不存在");
                    //物流退货、内部退货必须全退
                    if ((this.OrderTypeCode == ORDER_TYPE_LOGISTICS_RTN || this.OrderTypeCode == ORDER_TYPE_INNER_RTN)
                        && line.Quantity != snline.Quantity)
                        throw new Exception("退货数量不等于发货数量");
                    ReturnLine existsLine = new ReturnLine();
                    existsLine.OrderNumber = this.OrderNumber;
                    existsLine.LineNumber = this.NextLineNumber();
                    updateHeadLineNum = true;
                    existsLine.RefOrderLineID = snline.ID;
                    existsLine.SKUID = snline.SKUID;
                    existsLine.TransTypeCode = " ";
                    existsLine.Quantity = line.Quantity;
                    existsLine.DeliverQuantity = snline.Quantity;
                    existsLine.Price = snline.Price;
                    IList<WHArea> areas = ERPUtil.GetWHArea(session, this.OrderTypeCode, null, this.LocationCode);
                    if (areas.Count == 1)
                        existsLine.AreaCode = areas[0].AreaCode;
                    log.DebugFormat("to create rtn line, qty:{0}", line.Quantity);
                    existsLine.Create(session);
                }
            }
            if (updateHeadLineNum) this.Update(session, "CurrentLineNumber");
        }
        public void UpdateLines(ISession session, IList<ReturnLine> toSave)
        {
            if (toSave == null || toSave.Count <= 0) return;
            if (this.Status != ReturnStatus.New)
                throw new Exception("退货单不是新建状态，无法更新");
            this.CheckWHSettings(session, toSave);
            //保存
            foreach (ReturnLine line in toSave)
                line.Update(session, "AreaCode", "SectionCode");
        }
        private void CheckWHSettings(ISession session, IList<ReturnLine> toCheck)
        {
            StringBuilder errmsg = new StringBuilder();
            //检查
            foreach (ReturnLine line in toCheck)
            {
                if (string.IsNullOrEmpty(line.AreaCode) || line.AreaCode.Trim().Length <= 0)
                {
                    errmsg.Append("行").Append(line.LineNumber).Append("库位为空; ");
                    continue;
                }
                if (!string.IsNullOrEmpty(line.SectionCode) && line.SectionCode.Trim().Length > 0)
                {
                    WHSection section = WHSection.Retrieve(session, line.AreaCode, line.SectionCode);
                    if (section == null)
                    {
                        errmsg.Append("行").Append(line.LineNumber)
                            .Append("货架").Append(line.SectionCode).Append("(").Append(line.AreaCode).Append(")不存在; ");
                        continue;
                    }
                }
            }
            if (errmsg.Length > 0)
                throw new Exception(errmsg.ToString());
        }
        public void Release(ISession session)
        {
            if (this.Status != ReturnStatus.New)
                throw new Exception("退货单不是新建状态，无法执行发布操作");
            //换货退货单，必须扫描退货商品，扫描数量必须等于退货商品数量
            if (this.OrderTypeCode == ORDER_TYPE_EXCHANGE_RTN && !this.HasScaned)
                throw new Exception("您还没有扫描退货商品");
            IList<ReturnLine> lines = session.CreateEntityQuery<ReturnLine>()
                .Where(Exp.Eq("OrderNumber", this.OrderNumber))
                .List<ReturnLine>();
            if (lines.Count <= 0 && this.OrderTypeCode != ReturnHead.ORDER_TYPE_EXCHANGE_RTN)
                throw new Exception("没有退货明细，无法发布");
            this.CheckWHSettings(session, lines);
            this.Status = ReturnStatus.Release;
            this.Update(session, "Status");
            ERPUtil.ApproveThis(session, this);
        }
        public void Close(ISession session)
        {
            if (this.Status != ReturnStatus.Open)
                throw new Exception("退货单不是待入库状态，无法执行该操作");
            this.Status = ReturnStatus.Close;
            this.Update(session, "Status");
            ERPUtil.CommitWHTrans(session, this);
        }
        public static IList<ReturnHead> QueryBySNNumber(ISession session, string snNumber)
        {
            if (string.IsNullOrEmpty(snNumber) || snNumber.Trim().Length <= 0) return null;
            return session.CreateEntityQuery<ReturnHead>()
                .Where(Exp.Eq("RefOrderNumber", snNumber.Trim().ToUpper()))
                .List<ReturnHead>();
        }
        public void ExchangeReturnScaned(ISession session)
        {
            this.HasScaned = true;
            this.Update(session, "HasScaned");
        }
    }
}