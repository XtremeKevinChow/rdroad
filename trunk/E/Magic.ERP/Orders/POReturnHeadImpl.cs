namespace Magic.ERP.Orders
{
    using System;
    using System.Text;
    using System.Data;
    using Magic.ERP.Core;
    using Magic.Framework.Data;
    using Magic.Framework.ORM;
    using Magic.Framework.Utils;
    using Magic.Framework.ORM.Query;
    using Magic.Framework.ORM.Mapping;

    public partial class POReturnHead : IApprovable
    {
        private static log4net.ILog log = log4net.LogManager.GetLogger(typeof(POReturnHead));

        public const string ORD_TYPE_CODE = "PRT";
        void IApprovable.OnApprove(ISession session)
        {
            if (this.ApproveResult == ApproveStatus.Approve)
            {
                this.Status = POReturnStatus.Open;
                this.Update(session, "Status");
            }

            if (this.ApproveResult == ApproveStatus.Reject)
            {
                this.Status = POReturnStatus.New;
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
                        session.BeginTransaction();
                        this.Close(session);
                        session.Commit();
                    }
                    catch (Exception er)
                    {
                        newSession.Rollback();
                        log.Error(string.Format("采购退货单{0}签核完成，自动Close时发生异常", this.OrderNumber), er);
                    }
                }
            }
        }
        public string OrderTypeCode
        {
            get
            {
                return ORD_TYPE_CODE;
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

        public void Update(ISession session, int vendorId, string locationCode, string note)
        {
            if (this.Status != POReturnStatus.New)
                throw new Exception("单据不是新建状态，不能更新");
            if (this.VendorID != vendorId)
            {
                int count = session.CreateEntityQuery<POReturnLine>()
                    .Where(Exp.Eq("OrderNumber", this.OrderNumber))
                    .Count();
                if (count > 0)
                    throw new Exception("采购退货单已经添加了其它供应商的退货明细，不可以修改供应商");
            }
            this.VendorID = vendorId;
            this.LocationCode = locationCode;
            this.Note = note;
            this.Update(session, "VendorID", "LocationCode", "Note");
        }
        public void Release(ISession session)
        {
            if (this.Status != POReturnStatus.New)
                throw new Exception("退货单不是新建状态，无法发布");
            int count = session.CreateEntityQuery<POReturnLine>()
                .Where(Exp.Eq("OrderNumber", this.OrderNumber))
                .Count();
            if (count <= 0)
                throw new Exception("退货单没有退货明细，无法发布");
            this.Status = POReturnStatus.Release;
            this.Update(session, "Status");
            ERPUtil.ApproveThis(session, this);
        }
        public void Close(ISession session)
        {
            if (this.Status != POReturnStatus.Open)
                throw new Exception("退货单不是Open状态，无法关闭");
            DbSession dbsession = session.DbSession as DbSession;
            dbsession.ExecuteNonQueryForProc("f_po_rtn_close", new object[] { this.OrderNumber, 0 });

            this.Status = POReturnStatus.Close;
            this.Update(session, "Status");
        }
        public DataSet QueryNewLine(ISession session, DateTime start, DateTime end, string po, string barCode, int pageIndex, int pageSize, bool fetch, ref int count)
        {
            DbSession dbsession = session.DbSession as DbSession;
            StringBuilder sql = new StringBuilder();
            IDbCommand cmd = dbsession.CreateSqlStringCommand("");

            sql.Append(@"
Select pol.Ord_Num As PONumber,pol.line_num As POLine
       ,sku.itm_barcode As BarCode,i.itm_code As ItemCode,i.itm_name As ItemName,sku.color_code As ColorCode,sku.size_code As SizeCode
       ,color.Name As ColorText
       ,pol.rcv_qty As Qty,Rownum As rowindex
       ,sku.sku_id as SKUID
From ord_pur_line pol
Inner Join ord_pur_head poh On pol.Ord_Num=poh.ord_num
Inner Join prd_item_sku sku On sku.sku_id=pol.sku_id
Inner Join prd_item i On i.itm_code=sku.itm_code
Left Join prd_item_color color On color.code=sku.color_code
Where poh.ven_id=:venId And poh.loc_code=:locCode And pol.rcv_qty>0 
    and poh.ord_status in (2,3) and poh.aprv_rslt=1 and pol.line_status in (1,3)");
            dbsession.AddParameter(cmd, ":venId", DbTypeInfo.Int32(), this.VendorID);
            dbsession.AddParameter(cmd, ":locCode", DbTypeInfo.AnsiString(8), this.LocationCode);

            if (start > new DateTime(1900, 1, 1))
            {
                sql.Append(" And poh.create_time>=:startDate");
                dbsession.AddParameter(cmd, ":startDate", DbTypeInfo.Date(), start);
            }
            if (end > new DateTime(1900, 1, 1))
            {
                sql.Append(" And poh.create_time<:endDate");
                dbsession.AddParameter(cmd, ":endDate", DbTypeInfo.Date(), end.AddDays(1));
            }
            if (!string.IsNullOrEmpty(po) && po.Trim().Length > 0)
            {
                sql.Append(" And poh.ord_num=:po");
                dbsession.AddParameter(cmd, ":po", DbTypeInfo.AnsiString(16), po.Trim().ToUpper());
            }
            if (!string.IsNullOrEmpty(barCode) && barCode.Trim().Length > 0)
            {
                sql.Append(" And sku.itm_barcode Like :barCode");
                dbsession.AddParameter(cmd, ":barCode", DbTypeInfo.AnsiString(20), "%" + barCode.Trim().ToUpper() + "%");
            }

            if (fetch)
            {
                cmd.CommandText = "select count(*) from (" + sql.ToString() + ")t";
                count = Magic.Framework.Utils.Cast.Int(dbsession.ExecuteScalar(cmd));
            }

            int startIndex = (pageIndex - 1) * pageSize + 1, endIndex = pageIndex * pageSize;
            sql.Append(" order by poh.ord_num desc,pol.line_num)t1 where t1.rowindex>=").Append(startIndex)
                .Append(" and t1.rowindex<=").Append(endIndex);

            cmd.CommandText = "select * from(" + sql.ToString();
            return dbsession.ExecuteDataSet(cmd);
        }
        public void AddLine(ISession session, string po, string poLine, string area, string section, decimal qty)
        {
            if (this.Status != POReturnStatus.New)
                throw new Exception("采购退货单不是新建状态，无法添加明细");
            POLine line = POLine.Retrieve(session, po, poLine);
            if (line == null)
                throw new Exception(po + "行" + poLine + "不存在");
            if (line.ReceiveQty < qty)
                throw new Exception(po + "行" + poLine + "退货数量" + qty.ToString() + "大于收货数量" + line.ReceiveQty.ToString());
            StockDetail sto = StockDetail.Retrieve(session, line.SKUID, this.LocationCode, area, section);
            if (sto == null)
                throw new Exception(po + "行" + poLine + "库存明细(" + area + "," + section + ")不存在");
            if (sto.StockQty < qty)
                throw new Exception(po + "行" + poLine + "退货数量" + qty.ToString() + "大于库存量" + sto.StockQty.ToString());

            DbSession dbsession = session.DbSession as DbSession;
            IDbCommand cmd = dbsession.CreateSqlStringCommand("Select Sum(a.rtn_qty) From ord_pur_rtn_line a Where a.po_num=:po And a.po_line=:poline");
            dbsession.AddParameter(cmd, ":po", DbTypeInfo.AnsiString(16), po);
            dbsession.AddParameter(cmd, ":poline", DbTypeInfo.AnsiString(4), poLine);
            decimal returnedQty = Cast.Decimal(dbsession.ExecuteScalar(cmd));
            if (returnedQty + qty > line.ReceiveQty)
                throw new Exception(po + "行" + poLine + "，已退货数量" + returnedQty.ToString() + "本次退货数量" + qty.ToString() + "大于收货数量" + line.ReceiveQty.ToString());

            POReturnLine rtnLine = new POReturnLine();
            rtnLine.OrderNumber = this.OrderNumber;
            rtnLine.LineNumber = this.NextLineNumber();
            rtnLine.PONumber = po;
            rtnLine.POLine = poLine;
            rtnLine.SKUID = line.SKUID;
            rtnLine.Price = line.Price;
            rtnLine.Quantity = qty;
            rtnLine.TaxValue = line.TaxValue;
            rtnLine.StockDetailID = sto.StockDetailID;
            rtnLine.Create(session);
        }
    }
}
