namespace Magic.ERP.Report
{
    using System;
    using System.Data;
    using Magic.Framework.ORM;
    using Magic.Framework.ORM.Query;
    using Magic.ERP.Core;
    using Magic.ERP.Orders;
    using Magic.Basis;
    using Magic.Sys;
    using Magic.Framework.Utils;
    using Magic.Framework.Data;

    public enum Report_SaleByCode_OrderBy
    {
        /// <summary>
        /// 按货号排序
        /// </summary>
        ByCode = 1,
        /// <summary>
        /// 按销量排序
        /// </summary>
        BySaleQty = 2,
        /// <summary>
        /// 按销售金额
        /// </summary>
        BySaleAmt = 3
    }

    public class Report
    {
        #region 财务报表
        private const string UnInterchangedDesc = "未交接，没有物流公司";
        //物流公司发货统计
        public static DataSet LogisticDeliverySum(ISession session, DateTime start, DateTime end)
        {
            DateTime dtStart = Cast.DateTime(start.ToString("yyyy-MM-dd")), dtEnd = Cast.DateTime(end.ToString("yyyy-MM-dd"));
            DbSession dbsession = session.DbSession as DbSession;
            IDbCommand command = dbsession.CreateSqlStringCommand(@"
select t2.LogisticsID as LogisId,lg.lc_short_name as LogisName
  ,t2.OrderCount,t2.PackageCount,t2.AgentAmt,t2.RtnOrdCount,t2.RtnPkCount,t2.RtnAgentAmt
from(
  select t1.LogisticsID,sum(t1.OrderCount) as OrderCount,sum(t1.PackageCount) as PackageCount,sum(t1.AgentAmt) as AgentAmt
      ,sum(t1.RtnOrdCount) as RtnOrdCount,sum(t1.RtnPkCount) as RtnPkCount,sum(t1.RtnAgentAmt) as RtnAgentAmt
  from(
      select a.Logis_Id as LogisticsID,1 as OrderCount,sn1.package_count as PackageCount,-a.logis_receivable as AgentAmt
          ,0 as RtnOrdCount,0 as RtnPkCount,0 as RtnAgentAmt
      from fi_rpt_sale a
      inner join ord_shippingnotices sn1 on sn1.barcode=a.sn_number
      where a.sale_date>=:df1 and a.sale_date<:dt1
      Union All
      select a.Logis_Id As LogisticsID,0 as OrderCount,0 as PackageCount,0 as AgentAmt
          ,1 as RtnOrdCount,sn2.package_count as RtnPkCount,a.Logis_Receivable as RtnAgentAmt
      from fi_rpt_sale_return a
      inner join ord_shippingnotices sn2 on sn2.barcode=a.sn_number
      where a.rt_date>=:df2 and a.rt_date<:dt2 And a.rt_type=1
  ) t1
  group by t1.LogisticsID
) t2
left join bas_lc_comp lg on lg.lc_id=t2.LogisticsID
order by t2.LogisticsID");
            dbsession.AddParameter(command, ":df1", DbTypeInfo.DateTime(), dtStart);
            dbsession.AddParameter(command, ":dt1", DbTypeInfo.DateTime(), dtEnd.AddDays(1));
            dbsession.AddParameter(command, ":df2", DbTypeInfo.DateTime(), dtStart);
            dbsession.AddParameter(command, ":dt2", DbTypeInfo.DateTime(), dtEnd.AddDays(1));

            DataSet ds = dbsession.ExecuteDataSet(command);
            ds.Tables[0].Columns.Add(new DataColumn("DiffAmt", typeof(decimal)));
            if (ds.Tables[0].Rows.Count > 0)
            {
                DataRow sumRow = ds.Tables[0].NewRow();
                sumRow["LogisId"] = 0;
                sumRow["LogisName"] = "**合计**";
                sumRow["OrderCount"] = 0;
                sumRow["PackageCount"] = 0;
                sumRow["AgentAmt"] = 0M;
                sumRow["RtnOrdCount"] = 0;
                sumRow["RtnPkCount"] = 0;
                sumRow["RtnAgentAmt"] = 0M;
                sumRow["DiffAmt"] = 0M;
                foreach (DataRow row in ds.Tables[0].Rows)
                {
                    row["DiffAmt"] = Cast.Decimal(row["AgentAmt"]) - Cast.Decimal(row["RtnAgentAmt"]);

                    sumRow["OrderCount"] = Cast.Int(sumRow["OrderCount"]) + Cast.Int(row["OrderCount"]);
                    sumRow["PackageCount"] = Cast.Int(sumRow["PackageCount"]) + Cast.Int(row["PackageCount"]);
                    sumRow["AgentAmt"] = Cast.Decimal(sumRow["AgentAmt"]) + Cast.Decimal(row["AgentAmt"]);
                    sumRow["RtnOrdCount"] = Cast.Int(sumRow["RtnOrdCount"]) + Cast.Int(row["RtnOrdCount"]);
                    sumRow["RtnPkCount"] = Cast.Int(sumRow["RtnPkCount"]) + Cast.Int(row["RtnPkCount"]);
                    sumRow["RtnAgentAmt"] = Cast.Decimal(sumRow["RtnAgentAmt"]) + Cast.Decimal(row["RtnAgentAmt"]);
                    sumRow["DiffAmt"] = Cast.Decimal(sumRow["DiffAmt"]) + Cast.Decimal(row["DiffAmt"]);
                    if (Cast.Int(row["LogisId"]) <= 0)
                    {
                        row["LogisId"] = -99;
                        row["LogisName"] = UnInterchangedDesc;
                    }
                }
                ds.Tables[0].Rows.Add(sumRow);
            }
            return ds;
        }

        //出入库统计－汇总
        public static DataSet StockBalanceSum(ISession session, int periodId)
        {
            ObjectQuery query = session.CreateObjectQuery(@"
select b.CategoryID as CategoryID,b.CatName as CatName,a.BeginQty as BeginQty,a.BeginAmt as BeginAmt
    ,a.PurQty as PurQty,a.PurAmt as PurAmt,a.PurReturnQty as PurReturnQty,a.PurReturnAmt as PurReturnAmt
    ,a.SaleQty as SaleQty,a.SaleAmt as SaleAmt,a.SaleReturnQty as SaleReturnQty,a.SaleReturnAmt as SaleReturnAmt
    ,a.CheckQty as CheckQty,a.CheckAmt as CheckAmt,a.AdjustQty as AdjustQty,a.AdjustAmt as AdjustAmt
    ,a.UsedQty as UsedQty,a.UsedAmt as UsedAmt,a.OtherInQty as OtherInQty,a.OtherInAmt as OtherInAmt
    ,a.EndQty as EndQty,a.EndAmt as EndAmt,0 as DiffAmt
    ,a.ScrapQty as ScrapQty,a.ScrapAmt as ScrapAmt
from StockBalanceSum a
inner join ItemCategory b on a.CategoryID=b.CategoryID
where a.PeriodID=?pdid
order by b.CatCode")
                .Attach(typeof(StockBalanceSum)).Attach(typeof(ItemCategory))
                .SetValue("?pdid", periodId, "a.PeriodID");
            DataSet ds = query.DataSet();
            if (ds.Tables[0].Rows.Count > 0)
            {
                DataRow sumRow = ds.Tables[0].NewRow();
                sumRow["CatName"] = "**合计**";
                sumRow["BeginQty"] = 0M;
                sumRow["BeginAmt"] = 0M;
                sumRow["PurQty"] = 0M;
                sumRow["PurAmt"] = 0M;
                sumRow["PurReturnQty"] = 0M;
                sumRow["PurReturnAmt"] = 0M;
                sumRow["SaleQty"] = 0M;
                sumRow["SaleAmt"] = 0M;
                sumRow["SaleReturnQty"] = 0M;
                sumRow["SaleReturnAmt"] = 0M;
                sumRow["CheckQty"] = 0M;
                sumRow["CheckAmt"] = 0M;
                sumRow["AdjustQty"] = 0M;
                sumRow["AdjustAmt"] = 0M;
                sumRow["UsedQty"] = 0M;
                sumRow["UsedAmt"] = 0M;
                sumRow["OtherInQty"] = 0M;
                sumRow["OtherInAmt"] = 0M;
                sumRow["EndQty"] = 0M;
                sumRow["EndAmt"] = 0M;
                sumRow["DiffAmt"] = 0M;
                sumRow["ScrapQty"] = 0M;
                sumRow["ScrapAmt"] = 0M;
                foreach (DataRow row in ds.Tables[0].Rows)
                {
                    sumRow["BeginQty"] = Cast.Decimal(sumRow["BeginQty"]) + Cast.Decimal(row["BeginQty"]);
                    sumRow["BeginAmt"] = Cast.Decimal(sumRow["BeginAmt"]) + Cast.Decimal(row["BeginAmt"]);
                    sumRow["PurQty"] = Cast.Decimal(sumRow["PurQty"]) + Cast.Decimal(row["PurQty"]);
                    sumRow["PurAmt"] = Cast.Decimal(sumRow["PurAmt"]) + Cast.Decimal(row["PurAmt"]);
                    sumRow["PurReturnQty"] = Cast.Decimal(sumRow["PurReturnQty"]) + Cast.Decimal(row["PurReturnQty"]);
                    sumRow["PurReturnAmt"] = Cast.Decimal(sumRow["PurReturnAmt"]) + Cast.Decimal(row["PurReturnAmt"]);
                    sumRow["SaleQty"] = Cast.Decimal(sumRow["SaleQty"]) + Cast.Decimal(row["SaleQty"]);
                    sumRow["SaleAmt"] = Cast.Decimal(sumRow["SaleAmt"]) + Cast.Decimal(row["SaleAmt"]);
                    sumRow["SaleReturnQty"] = Cast.Decimal(sumRow["SaleReturnQty"]) + Cast.Decimal(row["SaleReturnQty"]);
                    sumRow["SaleReturnAmt"] = Cast.Decimal(sumRow["SaleReturnAmt"]) + Cast.Decimal(row["SaleReturnAmt"]);
                    sumRow["CheckQty"] = Cast.Decimal(sumRow["CheckQty"]) + Cast.Decimal(row["CheckQty"]);
                    sumRow["CheckAmt"] = Cast.Decimal(sumRow["CheckAmt"]) + Cast.Decimal(row["CheckAmt"]);
                    sumRow["AdjustQty"] = Cast.Decimal(sumRow["AdjustQty"]) + Cast.Decimal(row["AdjustQty"]);
                    sumRow["AdjustAmt"] = Cast.Decimal(sumRow["AdjustAmt"]) + Cast.Decimal(row["AdjustAmt"]);
                    sumRow["UsedQty"] = Cast.Decimal(sumRow["UsedQty"]) + Cast.Decimal(row["UsedQty"]);
                    sumRow["UsedAmt"] = Cast.Decimal(sumRow["UsedAmt"]) + Cast.Decimal(row["UsedAmt"]);
                    sumRow["OtherInQty"] = Cast.Decimal(sumRow["OtherInQty"]) + Cast.Decimal(row["OtherInQty"]);
                    sumRow["OtherInAmt"] = Cast.Decimal(sumRow["OtherInAmt"]) + Cast.Decimal(row["OtherInAmt"]);
                    sumRow["EndQty"] = Cast.Decimal(sumRow["EndQty"]) + Cast.Decimal(row["EndQty"]);
                    sumRow["EndAmt"] = Cast.Decimal(sumRow["EndAmt"]) + Cast.Decimal(row["EndAmt"]);
                    sumRow["ScrapQty"] = Cast.Decimal(sumRow["ScrapQty"]) + Cast.Decimal(row["ScrapQty"]);
                    sumRow["ScrapAmt"] = Cast.Decimal(sumRow["ScrapAmt"]) + Cast.Decimal(row["ScrapAmt"]);

                    row["DiffAmt"] = -(Cast.Decimal(row["BeginAmt"]) - Cast.Decimal(row["SaleAmt"]) + Cast.Decimal(row["SaleReturnAmt"])
                        + Cast.Decimal(row["PurAmt"]) + Cast.Decimal(row["PurReturnAmt"])
                        + Cast.Decimal(row["CheckAmt"]) + Cast.Decimal(row["AdjustAmt"])
                        + Cast.Decimal(row["UsedAmt"]) + Cast.Decimal(row["OtherInAmt"]) + Cast.Decimal(row["ScrapAmt"])
                        - Cast.Decimal(row["EndAmt"]));
                    sumRow["DiffAmt"] = Cast.Decimal(sumRow["DiffAmt"]) + Cast.Decimal(row["DiffAmt"]);
                }
                ds.Tables[0].Rows.Add(sumRow);
            }
            return ds;
        }
        //出入库统计 - 明细
        public static DataSet StockBalanceDetail(ISession session, int periodId, int catId, string itemCode, string itemName, string colorCode, string sizeCode, int pageIndex, int pageSize, bool fetch, ref int count)
        {
            ObjectQuery query = session.CreateObjectQuery(@"
select b.CatName as CatName,spec.ItemCode as ItemCode,mst.ItemName as ItemName
    ,spec.ColorCode as ColorCode,color.ColorText as ColorText,spec.SizeCode as SizeCode
    ,a.BeginQty as BeginQty,a.BeginAmt as BeginAmt
    ,a.PurQty as PurQty,a.PurAmt as PurAmt,a.PurReturnQty as PurReturnQty,a.PurReturnAmt as PurReturnAmt
    ,a.SaleQty as SaleQty,a.SaleAmt as SaleAmt,a.SaleReturnQty as SaleReturnQty,a.SaleReturnAmt as SaleReturnAmt
    ,a.CheckQty as CheckQty,a.CheckAmt as CheckAmt,a.AdjustQty as AdjustQty,a.AdjustAmt as AdjustAmt
    ,a.UsedQty as UsedQty,a.UsedAmt as UsedAmt,a.OtherInQty as OtherInQty,a.OtherInAmt as OtherInAmt
    ,a.EndQty as EndQty,a.EndAmt as EndAmt,0 as DiffAmt
    ,a.ScrapQty as ScrapQty,a.ScrapAmt as ScrapAmt
from StockBalanceDetail a
inner join ItemSpec spec on spec.SKUID=a.SKUID
inner join ItemMaster mst on mst.ItemID=spec.ItemID
inner join ItemCategory b on mst.CategoryID=b.CategoryID
left join ItemColor color on color.ColorCode=spec.ColorCode
where a.PeriodID=?pdid
order by b.CatCode,spec.ItemCode,spec.ColorCode,spec.SizeCode")
                .Attach(typeof(StockBalanceDetail))
                .Attach(typeof(ItemCategory)).Attach(typeof(ItemSpec)).Attach(typeof(ItemMaster)).Attach(typeof(ItemColor))
                .SetValue("?pdid", periodId, "a.PeriodID");
            if (pageIndex > 0) query.SetPage(pageIndex, pageSize);
            if (catId > 0) query.And(Exp.Eq("b.CategoryID", catId));
            if (!string.IsNullOrEmpty(itemCode) && itemCode.Trim().Length > 0)
                query.And(Exp.Like("spec.ItemCode", "%" + itemCode.Trim().ToUpper() + "%"));
            if (!string.IsNullOrEmpty(itemName) && itemName.Trim().Length > 0)
                query.And(Exp.Like("mst.ItemName", "%" + itemName.Trim().ToUpper() + "%"));
            if (!string.IsNullOrEmpty(colorCode) && colorCode.Trim().Length > 0)
                query.And(Exp.Like("spec.ColorCode", "%" + colorCode.Trim().ToUpper() + "%"));
            if (!string.IsNullOrEmpty(sizeCode) && sizeCode.Trim().Length > 0)
                query.And(Exp.Like("spec.SizeCode", "%" + sizeCode.Trim().ToUpper() + "%"));

            if (fetch) count = query.Count();
            DataSet ds = query.DataSet();

            foreach (DataRow row in ds.Tables[0].Rows)
                row["DiffAmt"] = -(Cast.Decimal(row["BeginAmt"]) - Cast.Decimal(row["SaleAmt"]) + Cast.Decimal(row["SaleReturnAmt"])
                    + Cast.Decimal(row["PurAmt"]) + Cast.Decimal(row["PurReturnAmt"])
                    + Cast.Decimal(row["CheckAmt"]) + Cast.Decimal(row["AdjustAmt"])
                    + Cast.Decimal(row["UsedAmt"]) + Cast.Decimal(row["OtherInAmt"]) + Cast.Decimal(row["ScrapAmt"])
                    - Cast.Decimal(row["EndAmt"]));

            return ds;
        }

        //采购收货凭证－主档
        public static DataSet PurchaseReceiveSum(ISession session, int vendorId, string rcvNumber, string poNumber, DateTime start, DateTime end, int pageSize, int pageIndex, bool fetch, ref int count)
        {
            if (end <= new DateTime(1900, 1, 1)) end = DateTime.Now;
            int intStart = Cast.Int(start.ToString("yyyyMMdd")), intEnd = Cast.Int(end.ToString("yyyyMMdd"));
            int startIndex = (pageIndex - 1) * pageSize + 1, endIndex = pageIndex * pageSize;

            DbSession dbsession = session.DbSession as DbSession;
            IDbCommand command = dbsession.CreateSqlStringCommand("");
            System.Text.StringBuilder sql = new System.Text.StringBuilder();
            sql.Append(@"
    from (
        Select th.commit_date,th.COMMIT_TIME,th.TRANS_ORD_NUM
               ,Sum(tl.trans_qty*tl.trans_price) As TaxInAmt,Round(Sum(tl.trans_qty*tl.trans_price/(1+tl.tax_val)),2) As TaxExAmt
        From INV_TRANS_HEAD th
        Inner Join INV_TRANS_LINE tl On th.trans_num=tl.trans_num
        Where (th.COMMIT_DATE>=:startDate) AND (th.COMMIT_DATE<=:endDate) And th.TRANS_ORD_TYPE='RC1'");
            dbsession.AddParameter(command, ":startDate", EntityManager.GetPropMapping(typeof(WHTransHead), "CommitDate").DbTypeInfo, intStart);
            dbsession.AddParameter(command, ":endDate", EntityManager.GetPropMapping(typeof(WHTransHead), "CommitDate").DbTypeInfo, intEnd);
            if (!string.IsNullOrEmpty(rcvNumber) && rcvNumber.Trim().Length > 0)
            {
                sql.Append(" And th.trans_ord_num=:rcvNumber");
                dbsession.AddParameter(command, ":rcvNumber", EntityManager.GetPropMapping(typeof(WHTransHead), "TransOrderNumber").DbTypeInfo, rcvNumber.Trim().ToUpper());
            }
            if (!string.IsNullOrEmpty(poNumber) && poNumber.Trim().Length > 0)
            {
                sql.Append(" And th.Ref_Ord_Num=:poNumber");
                dbsession.AddParameter(command, ":poNumber", EntityManager.GetPropMapping(typeof(WHTransHead), "RefOrderNumber").DbTypeInfo, poNumber.Trim().ToUpper());
            }
            sql.Append(@" Group By th.commit_date,th.COMMIT_TIME,th.TRANS_ORD_NUM
    )t
    inner join ORD_RCV_HEAD h on h.ORD_NUM=t.TRANS_ORD_NUM
    left join BAS_VEN_COMP v on h.OBJ_ID=v.VEN_ID");
            if (vendorId > 0)
            {
                sql.Append(" Where h.obj_id=:venId");
                dbsession.AddParameter(command, ":venId", EntityManager.GetPropMapping(typeof(RCVHead), "ObjectID").DbTypeInfo, vendorId);
            }

            if (fetch)
            {
                command.CommandText = "select count(1) " + sql.ToString();
                count = Cast.Int(dbsession.ExecuteScalar(command));
            }

            sql.Append(" order by t.COMMIT_DATE,t.COMMIT_TIME) mf_t1) mf_t2 WHERE mf_r>=")
                .Append(startIndex).Append(" AND mf_r<=").Append(endIndex);
            command.CommandText = @"SELECT * FROM(SELECT mf_t1.*,Rownum AS mf_r FROM(
    select to_date(t.commit_date,'YYYYMMDD') As TransDate,h.ORD_NUM as OrderNumber,h.REF_ORD_NUM as PONumber
           ,t.TaxInAmt,t.TaxExAmt,t.TaxInAmt-t.TaxExAmt As TaxAmt
           ,v.VEN_FULL_NAME VendorName " + sql.ToString();
            return dbsession.ExecuteDataSet(command);

            //            ObjectQuery query = session.CreateObjectQuery(@"
            //select h.OrderNumber as OrderNumber,h.RefOrderNumber as PONumber,v.FullName as VendorName
            //    ,h.Status as Status,sd.StatusText as StatusText,h.ApproveResult as ApproveResult,u.FullName as UserName,h.CreateTime as CreateTime
            //from WHTransHead th
            //inner join RCVHead h on h.OrderNumber=th.TransOrderNumber
            //left join Vendor v on h.ObjectID=v.VendorID
            //left join User u on h.CreateUser=u.UserId
            //left join OrderStatusDef sd on sd.OrderTypeCode=h.OrderTypeCode and sd.StatusValue=h.Status
            //where th.TransOrderType=?ordType
            //order by th.CommitDate,th.CommitTime")
            //                .Attach(typeof(WHTransHead)).Attach(typeof(RCVHead))
            //                .Attach(typeof(Vendor)).Attach(typeof(User)).Attach(typeof(OrderStatusDef))
            //                .SetValue("?ordType", RCVHead.ORD_TYPE_PUR, "th.TransOrderType")
            //                .SetPage(pageIndex, pageSize);
            //            if (vendorId > 0) query.And(Exp.Eq("h.ObjectID", vendorId));
            //            if (!string.IsNullOrEmpty(rcvNumber) && rcvNumber.Trim().Length > 0)
            //                query.And(Exp.Eq("th.TransOrderNumber", rcvNumber.Trim().ToUpper()));
            //            if (!string.IsNullOrEmpty(poNumber) && poNumber.Trim().Length > 0)
            //                query.And(Exp.Eq("th.RefOrderNumber", poNumber.Trim().ToUpper()));
            //            if (start > new DateTime(1900, 1, 1))
            //                query.And(Exp.Ge("th.CommitDate", Cast.Int(start.ToString("yyyyMMdd"))));
            //            if (end > new DateTime(1900, 1, 1))
            //                query.And(Exp.Le("th.CommitDate", Cast.Int(end.ToString("yyyyMMdd"))));
            //            if (fetch) count = query.Count();
            //            return query.DataSet();
        }
        //采购收货凭证－明细
        public static DataSet PurchaseReceiveDetail(ISession session, int vendorId, string rcvNumber, string poNumber, DateTime start, DateTime end)
        {
            ObjectQuery query = session.CreateObjectQuery(@"
select h.OrderNumber as RCVNumber,h.RefOrderNumber as PONumber
    ,l.LineNumber as RCVLine,l.RefOrderLine as POLine,m.ItemCode as ItemCode,m.ItemName as ItemName
    ,i.BarCode as BarCode,i.ColorCode as ColorCode,color.ColorText as ColorText,i.SizeCode as SizeCode
    ,l.QualifiedQty as RCVQty,l.Price as Price,l.TaxValue as TaxValue
from WHTransHead th
inner join RCVHead h on h.OrderNumber=th.TransOrderNumber
inner join RCVLine l on h.OrderNumber=l.OrderNumber
inner join ItemSpec i on l.SKUID=i.SKUID
inner join ItemMaster m on m.ItemID=i.ItemID
left join ItemColor color on color.ColorCode=i.ColorCode
where th.TransOrderType=?ordType
order by th.CommitDate,th.CommitTime,l.OrderNumber,l.LineNumber")
                .Attach(typeof(WHTransHead)).Attach(typeof(RCVHead)).Attach(typeof(RCVLine))
                .Attach(typeof(ItemSpec)).Attach(typeof(ItemMaster)).Attach(typeof(ItemColor))
                .SetValue("?ordType", RCVHead.ORD_TYPE_PUR, "th.RefOrderType");
            if (vendorId > 0) query.And(Exp.Eq("h.ObjectID", vendorId));
            if (!string.IsNullOrEmpty(rcvNumber) && rcvNumber.Trim().Length > 0)
                query.And(Exp.Eq("th.TransOrderType", rcvNumber.Trim().ToUpper()));
            if (!string.IsNullOrEmpty(poNumber) && poNumber.Trim().Length > 0)
                query.And(Exp.Eq("th.RefOrderNumber", poNumber.Trim().ToUpper()));
            if (start > new DateTime(1900, 1, 1))
                query.And(Exp.Ge("th.CommitDate", Cast.Int(start.ToString("yyyyMMdd"))));
            if (end > new DateTime(1900, 1, 1))
                query.And(Exp.Le("th.CommitDate", Cast.Int(end.ToString("yyyyMMdd"))));
            DataSet ds = query.DataSet();

            //计算每个明细的金额，每个收货单添加一个汇总记录
            string rcvnum = "", prevnum = "";
            decimal totalAmt = 0M, amt = 0M;
            ds.Tables[0].Columns.Add("RCVAmt", typeof(decimal));
            int rowsCount = ds.Tables[0].Rows.Count;
            int index = 0;
            while (index < rowsCount)
            {
                DataRow row = ds.Tables[0].Rows[index];
                rcvnum = Cast.String(row["RCVNumber"]).Trim();
                amt = Cast.Decimal(row["RCVQty"]) * Cast.Decimal(row["Price"]) / (1 + Cast.Decimal(row["TaxValue"]));
                row["RCVAmt"] = amt;

                if (rcvnum != prevnum && prevnum.Length > 0)
                {
                    //加汇总记录
                    DataRow summaryRow = ds.Tables[0].NewRow();
                    summaryRow["RCVNumber"] = prevnum;
                    summaryRow["RCVAmt"] = totalAmt;
                    summaryRow["PONumber"] = "**合计**";
                    totalAmt = 0M;
                    ds.Tables[0].Rows.InsertAt(summaryRow, index++);
                    rowsCount++;
                }

                totalAmt += amt;
                index++;
                prevnum = rcvnum;
            }
            if (ds.Tables[0].Rows.Count > 0)
            {
                DataRow lastRow = ds.Tables[0].NewRow();
                lastRow["RCVNumber"] = prevnum;
                lastRow["RCVAmt"] = totalAmt;
                lastRow["PONumber"] = "**合计**";
                ds.Tables[0].Rows.InsertAt(lastRow, index);
            }

            return ds;
        }

        //销售统计－按物流公司汇总
        public static DataSet SaleAmtSum(ISession session, DateTime start, DateTime end)
        {
            DateTime dtStart = Cast.DateTime(start.ToString("yyyy-MM-dd")), dtEnd = Cast.DateTime(end.ToString("yyyy-MM-dd"));
            DbSession dbsession = session.DbSession as DbSession;
            IDbCommand command = dbsession.CreateSqlStringCommand(@"
select a.Logis_Id As LogisID,b.lc_short_name As LogisName
       ,sum(a.sale_amt) As SaleAmt,sum(a.transport_amt) As TransportAmt,sum(a.package_amt) As PackageAmt,sum(a.coupons_amt) As CouponsAmt,sum(a.discount_amt) as DiscountAmt
       ,sum(a.sale_amt+a.transport_amt+a.package_amt+a.coupons_amt+a.discount_amt) As OrderAmt
       ,-sum(a.account_receivable) As AccountReceivable,-sum(a.pos_receivable) As PosReceivable,-sum(a.Logis_Receivable) As LogisReceivable
       ,-sum(nvl(a.emoney_amt,0)) as EMoneyAmt
from fi_rpt_sale a
Left Join bas_lc_comp b On a.Logis_Id=b.lc_id
where a.sale_date>=:df1 and a.sale_date<:dt1
Group By a.Logis_Id,b.lc_short_name");
            dbsession.AddParameter(command, ":df1", DbTypeInfo.DateTime(), dtStart);
            dbsession.AddParameter(command, ":dt1", DbTypeInfo.DateTime(), dtEnd.AddDays(1));
            DataSet ds = dbsession.ExecuteDataSet(command);
            if (ds.Tables[0].Rows.Count > 0)
            {
                DataRow sumRow = ds.Tables[0].NewRow();
                sumRow["LogisID"] = 0;
                sumRow["LogisName"] = "**合计**";
                sumRow["SaleAmt"] = 0M;
                sumRow["TransportAmt"] = 0M;
                sumRow["PackageAmt"] = 0M;
                sumRow["CouponsAmt"] = 0M;
                sumRow["DiscountAmt"] = 0M;
                sumRow["OrderAmt"] = 0M;
                sumRow["AccountReceivable"] = 0M;
                sumRow["PosReceivable"] = 0M;
                sumRow["LogisReceivable"] = 0M;
                sumRow["EMoneyAmt"] = 0M;
                foreach (DataRow row in ds.Tables[0].Rows)
                {
                    sumRow["SaleAmt"] = Cast.Decimal(sumRow["SaleAmt"]) + Cast.Decimal(row["SaleAmt"]);
                    sumRow["TransportAmt"] = Cast.Decimal(sumRow["TransportAmt"]) + Cast.Decimal(row["TransportAmt"]);
                    sumRow["PackageAmt"] = Cast.Decimal(sumRow["PackageAmt"]) + Cast.Decimal(row["PackageAmt"]);
                    sumRow["CouponsAmt"] = Cast.Decimal(sumRow["CouponsAmt"]) + Cast.Decimal(row["CouponsAmt"]);
                    sumRow["DiscountAmt"] = Cast.Decimal(sumRow["DiscountAmt"]) + Cast.Decimal(row["DiscountAmt"]);
                    sumRow["OrderAmt"] = Cast.Decimal(sumRow["OrderAmt"]) + Cast.Decimal(row["OrderAmt"]);
                    sumRow["AccountReceivable"] = Cast.Decimal(sumRow["AccountReceivable"]) + Cast.Decimal(row["AccountReceivable"]);
                    sumRow["PosReceivable"] = Cast.Decimal(sumRow["PosReceivable"]) + Cast.Decimal(row["PosReceivable"]);
                    sumRow["LogisReceivable"] = Cast.Decimal(sumRow["LogisReceivable"]) + Cast.Decimal(row["LogisReceivable"]);
                    sumRow["EMoneyAmt"] = Cast.Decimal(sumRow["EMoneyAmt"]) + Cast.Decimal(row["EMoneyAmt"]);

                    if (Cast.Int(row["LogisID"]) <= 0)
                    {
                        row["LogisID"] = -99;
                        row["LogisName"] = UnInterchangedDesc;
                    }
                }
                ds.Tables[0].Rows.Add(sumRow);
            }
            return ds;
        }
        //销售明细
        public static DataSet SaleAmt(ISession session, DateTime start, DateTime end, int logisId, string snNumber, string soNumber, string shippingNumber, int pageIndex, int pageSize, bool fetch, ref int count)
        {
            DateTime dtStart = Cast.DateTime(start.ToString("yyyy-MM-dd")), dtEnd = Cast.DateTime(end.ToString("yyyy-MM-dd"));
            DbSession dbsession = session.DbSession as DbSession;
            IDbCommand cmd = dbsession.CreateSqlStringCommand("");

            System.Text.StringBuilder sql = new System.Text.StringBuilder();
            sql.Append(@"
from fi_rpt_sale a
inner join ord_shippingnotices sn on sn.barcode=a.sn_number
where a.sale_date>=:startDate And a.sale_date<:endDate");
            dbsession.AddParameter(cmd, ":startDate", DbTypeInfo.DateTime(), dtStart);
            dbsession.AddParameter(cmd, ":endDate", DbTypeInfo.DateTime(), dtEnd.AddDays(1));
            if (logisId == -99)
                sql.Append(" And (a.logis_id<=0 Or a.logis_id Is Null)");
            else if (logisId > 0)
                sql.Append(" And a.logis_id=").Append(logisId);

            if (!string.IsNullOrEmpty(snNumber) && snNumber.Trim().Length > 0)
            {
                sql.Append(" And a.sn_number=:snNumber");
                dbsession.AddParameter(cmd, ":snNumber", DbTypeInfo.AnsiString(20), snNumber.Trim().ToUpper());
            }
            if (!string.IsNullOrEmpty(soNumber) && soNumber.Trim().Length > 0)
            {
                sql.Append(" And a.so_number=:soNumber");
                dbsession.AddParameter(cmd, ":soNumber", DbTypeInfo.AnsiString(20), soNumber.Trim().ToUpper());
            }
            if (!string.IsNullOrEmpty(shippingNumber) && shippingNumber.Trim().Length > 0)
            {
                sql.Append(" And sn.shipping_number=:shippingNumber");
                dbsession.AddParameter(cmd, ":shippingNumber", DbTypeInfo.AnsiString(20), shippingNumber.Trim().ToUpper());
            }

            if (fetch)
            {
                cmd.CommandText = "select count(1) " + sql.ToString();
                count = Cast.Int(dbsession.ExecuteScalar(cmd));
            }

            sql.Append(" order by a.sale_date,a.sn_number");
            System.Text.StringBuilder sql2 = new System.Text.StringBuilder();
            sql2.Append("Select t.*,Case When rt.Id Is Not Null Then 0 Else t.LogisReceivable End As ActualReceivable From(");
            if (pageIndex <= 0)
            {
                sql2.Append(@"
select a.sale_date as TransDate,a.sn_number as SNNumber,a.so_number as SONumber,sn.shipping_number As ShippingNumber
    ,a.Cost_Amt As CostAmt,a.sale_amt As SaleAmt,a.transport_amt As TransportAmt,a.package_amt As PackageAmt,a.coupons_amt As CouponsAmt,a.discount_amt as DiscountAmt
    ,-a.account_receivable As AccountReceivable,-a.pos_receivable As PosReceivable,-a.Logis_Receivable As LogisReceivable,-a.emoney_amt as EMoneyAmt")
                    .Append(" ")
                    .Append(sql.ToString());
            }
            else
            {
                int startIndex = (pageIndex - 1) * pageSize + 1, endIndex = pageIndex * pageSize;
                sql2.Append("SELECT * FROM(SELECT mf_t1.*,Rownum AS mf_r FROM(")
                    .Append(@"
select a.sale_date as TransDate,a.sn_number as SNNumber,a.so_number as SONumber,sn.shipping_number As ShippingNumber
    ,a.Cost_Amt As CostAmt,a.sale_amt As SaleAmt,a.transport_amt As TransportAmt,a.package_amt As PackageAmt,a.coupons_amt As CouponsAmt,a.discount_amt as DiscountAmt
    ,-a.account_receivable As AccountReceivable,-a.pos_receivable As PosReceivable,-a.Logis_Receivable As LogisReceivable,-a.emoney_amt as EMoneyAmt")
                    .Append(sql.ToString())
                    .Append(") mf_t1) mf_t2 WHERE mf_r>=")
                    .Append(startIndex)
                    .Append(" AND mf_r<=")
                    .Append(endIndex);
            }
            sql2.Append(@")t 
Left Join fi_rpt_sale_return rt On t.SNNumber=rt.sn_number And rt.rt_type=1 
Order By t.TransDate,t.SNNumber");

            cmd.CommandText = sql2.ToString();
            return dbsession.ExecuteDataSet(cmd);
        }

        //退货统计－按物流公司汇总
        public static DataSet SaleReturnSum(ISession session, DateTime start, DateTime end, int returnType)
        {
            DateTime dtStart = Cast.DateTime(start.ToString("yyyy-MM-dd")), dtEnd = Cast.DateTime(end.ToString("yyyy-MM-dd"));
            DbSession dbsession = session.DbSession as DbSession;
            IDbCommand command = dbsession.CreateSqlStringCommand(string.Format(@"
select a.Logis_Id As LogisID,b.lc_short_name As LogisName
       ,sum(a.sale_amt) As SaleAmt,sum(a.transport_amt) As TransportAmt,sum(a.package_amt) As PackageAmt,sum(a.coupons_amt) As CouponsAmt,sum(a.discount_amt) As DiscountAmt
       ,sum(a.sale_amt+a.transport_amt+a.package_amt+a.coupons_amt+a.discount_amt) As OrderAmt
       ,sum(a.account_receivable) As AccountReceivable,-sum(a.pos_receivable) As PosReceivable,-sum(a.Logis_Receivable) As LogisReceivable
       ,sum(nvl(a.emoney_amt,0)) as EMoneyAmt
from fi_rpt_sale_return a
Left Join bas_lc_comp b On a.Logis_Id=b.lc_id
where a.rt_date>=:df1 and a.rt_date<:dt1 {0}
Group By a.Logis_Id,b.lc_short_name", returnType == 1 ? "and a.rt_type=1" : "and a.rt_type in(2,3)"));
            dbsession.AddParameter(command, ":df1", DbTypeInfo.DateTime(), dtStart);
            dbsession.AddParameter(command, ":dt1", DbTypeInfo.DateTime(), dtEnd.AddDays(1));
            DataSet ds = dbsession.ExecuteDataSet(command);
            if (ds.Tables[0].Rows.Count > 0)
            {
                DataRow sumRow = ds.Tables[0].NewRow();
                sumRow["LogisID"] = 0;
                sumRow["LogisName"] = "**合计**";
                sumRow["SaleAmt"] = 0M;
                sumRow["TransportAmt"] = 0M;
                sumRow["PackageAmt"] = 0M;
                sumRow["CouponsAmt"] = 0M;
                sumRow["DiscountAmt"] = 0M;
                sumRow["OrderAmt"] = 0M;
                sumRow["AccountReceivable"] = 0M;
                sumRow["PosReceivable"] = 0M;
                sumRow["LogisReceivable"] = 0M;
                sumRow["EMoneyAmt"] = 0M;
                foreach (DataRow row in ds.Tables[0].Rows)
                {
                    sumRow["SaleAmt"] = Cast.Decimal(sumRow["SaleAmt"]) + Cast.Decimal(row["SaleAmt"]);
                    sumRow["TransportAmt"] = Cast.Decimal(sumRow["TransportAmt"]) + Cast.Decimal(row["TransportAmt"]);
                    sumRow["PackageAmt"] = Cast.Decimal(sumRow["PackageAmt"]) + Cast.Decimal(row["PackageAmt"]);
                    sumRow["CouponsAmt"] = Cast.Decimal(sumRow["CouponsAmt"]) + Cast.Decimal(row["CouponsAmt"]);
                    sumRow["DiscountAmt"] = Cast.Decimal(sumRow["DiscountAmt"]) + Cast.Decimal(row["DiscountAmt"]);
                    sumRow["OrderAmt"] = Cast.Decimal(sumRow["OrderAmt"]) + Cast.Decimal(row["OrderAmt"]);
                    sumRow["AccountReceivable"] = Cast.Decimal(sumRow["AccountReceivable"]) + Cast.Decimal(row["AccountReceivable"]);
                    sumRow["PosReceivable"] = Cast.Decimal(sumRow["PosReceivable"]) + Cast.Decimal(row["PosReceivable"]);
                    sumRow["LogisReceivable"] = Cast.Decimal(sumRow["LogisReceivable"]) + Cast.Decimal(row["LogisReceivable"]);
                    sumRow["EMoneyAmt"] = Cast.Decimal(sumRow["EMoneyAmt"]) + Cast.Decimal(row["EMoneyAmt"]);

                    if (Cast.Int(row["LogisID"]) <= 0)
                    {
                        row["LogisID"] = -99;
                        row["LogisName"] = UnInterchangedDesc;
                    }
                }
                ds.Tables[0].Rows.Add(sumRow);
            }
            return ds;
        }
        //退货明细－按退货订单
        public static DataSet SaleReturn(ISession session, DateTime start, DateTime end, string returnType, int logisId, string rtNumber, string snNumber, string soNumber, int pageIndex, int pageSize, bool fetch, ref int count)
        {
            if (end <= new DateTime(1900, 1, 1)) end = DateTime.Now;
            DateTime dtStart = Cast.DateTime(start.ToString("yyyy-MM-dd")), dtEnd = Cast.DateTime(end.ToString("yyyy-MM-dd"));
            int startIndex = (pageIndex - 1) * pageSize + 1, endIndex = pageIndex * pageSize;

            DbSession dbsession = session.DbSession as DbSession;
            IDbCommand command = dbsession.CreateSqlStringCommand("");
            System.Text.StringBuilder sql = new System.Text.StringBuilder();
            sql.Append(@"
Select to_char(a.rt_date,'YYYY-MM-DD') As inner_date
       ,a.rt_number as RTNumber,a.sn_number As SNNumber,a.so_number As SONumber,sn.shipping_number as ShippingNumber
       ,Case When a.rt_type=1 Then '物流退货' When a.rt_type=2 Then '会员退货' Else ' ' End as OrderType
       ,a.sale_amt As SaleAmt,a.transport_amt As TransportAmt,a.package_amt As PackageAmt,a.coupons_amt As CouponsAmt,a.discount_amt as DiscountAmt
       ,(a.sale_amt+a.transport_amt+a.package_amt+a.coupons_amt+a.discount_amt) As ReturnAmt
       ,a.account_receivable As AccountReceivable,-a.pos_receivable As Posreceivable,-a.Logis_Receivable As LogisReceivable,nvl(a.emoney_amt,0) as EMoneyAmt
       ,lg.lc_short_name As LogisName
from fi_rpt_sale_return a 
left join ord_shippingnotices sn on a.sn_number=sn.doc_number
left join BAS_LC_COMP lg on lg.LC_ID=a.Logis_Id
where a.rt_date>=:startDate and a.rt_date<=:endDate");
            dbsession.AddParameter(command, ":startDate", DbTypeInfo.DateTime(), dtStart);
            dbsession.AddParameter(command, ":endDate", DbTypeInfo.DateTime(), dtEnd);
            if (!string.IsNullOrEmpty(returnType))
                sql.Append(" and a.rt_type=").Append(returnType == "RC4" ? "1" : returnType == "RC2" ? "2" : "0");
            if (logisId > 0)
            {
                sql.Append(" and a.Logis_Id=:logisId");
                dbsession.AddParameter(command, ":logisId", DbTypeInfo.Int32(), logisId);
            }
            if (!string.IsNullOrEmpty(rtNumber) && rtNumber.Trim().Length > 0)
            {
                sql.Append(" and a.rt_number like :rtNumber");
                dbsession.AddParameter(command, ":rtNumber", DbTypeInfo.AnsiString(16), "%" + rtNumber.Trim().ToUpper() + "%");
            }
            if (!string.IsNullOrEmpty(snNumber) && snNumber.Trim().Length > 0)
            {
                sql.Append(" and a.sn_number like :snNumber");
                dbsession.AddParameter(command, ":snNumber", DbTypeInfo.AnsiString(20), "%" + snNumber.Trim().ToUpper() + "%");
            }
            if (!string.IsNullOrEmpty(soNumber) && soNumber.Trim().Length > 0)
            {
                sql.Append(" and a.so_number like :soNumber");
                dbsession.AddParameter(command, ":soNumber", DbTypeInfo.AnsiString(20), "%" + soNumber.Trim().ToUpper() + "%");
            }

            if (fetch)
            {
                command.CommandText = "select count(1) from(" + sql.ToString() + ")t";
                count = Cast.Int(dbsession.ExecuteScalar(command));
            }
            sql.Append(" order by a.rt_date,a.rt_number");
            string sqlString = "";
            if (pageIndex > 0)
            {
                sql.Append(") mf_t1) mf_t2 WHERE mf_r>=").Append(startIndex).Append(" AND mf_r<=").Append(endIndex);
                sqlString = "SELECT * FROM(SELECT mf_t1.*,Rownum AS mf_r FROM(" + sql.ToString();
            }
            else
                sqlString = sql.ToString();
            command.CommandText = sqlString;
            return dbsession.ExecuteDataSet(command);
        }

        //帐户变动汇总
        public static DataSet MbrAccountSum(ISession session, DateTime start, DateTime end)
        {
            DbSession dbsession = session.DbSession as DbSession;
            IDbCommand command = dbsession.CreateSqlStringCommand(@"
Select a.flush_type as FlushID,a.pay_method as PaymentID,d.Name As FlushType,c.Name As PaymentType,sum(a.money_update) As TotalAmt
From mbr_money_history a
Left Join s_payment_method c On c.Id=a.pay_method
Left Join magic.s_flushbalance_source d On a.flush_type=d.Id
Where a.Modify_Date>=:dStart And a.Modify_Date<=:dEnd and a.money_update<>0
Group By a.flush_type,a.pay_method,d.Name,c.Name");
            dbsession.AddParameter(command, ":dStart", DbTypeInfo.Date(), start);
            dbsession.AddParameter(command, ":dEnd", DbTypeInfo.Date(), end.AddDays(1));

            return dbsession.ExecuteDataSet(command);
        }
        //帐户变动明细
        public static DataSet MbrAccountDetail(ISession session, DateTime start, DateTime end, int flushType, int paymentType, string ordNum, string mbrNum, string mbrName, string userName, int pageIndex, int pageSize, bool fetch, ref int count)
        {
            DbSession dbsession = session.DbSession as DbSession;
            System.Text.StringBuilder sql = new System.Text.StringBuilder();
            sql.Append(@"
Select a.Modify_Date As FlushDate,d.Name As FlushType,c.Name As PaymentType
    ,m.Name As MbrName,m.card_id as MbrNum,u.Name As UserName
    ,a.credence As OrderNumber,a.money_update As FlushAmt,a.comments
From mbr_money_history a
Left Join magic.s_event_type b On a.event_type=b.Id
Left Join s_payment_method c On c.Id=a.pay_method
Left Join magic.s_flushbalance_source d On a.flush_type=d.Id
Left Join mbr_members m On a.member_id=m.Id
Left Join magic.org_persons u On u.Id=a.operator_id
Where a.Modify_Date>=:dStart And a.Modify_Date<=:dEnd and a.money_update<>0");

            IDbCommand command = dbsession.CreateSqlStringCommand("");
            dbsession.AddParameter(command, ":dStart", DbTypeInfo.Date(), start);
            dbsession.AddParameter(command, ":dEnd", DbTypeInfo.Date(), end.AddDays(1));

            if (flushType > 0) sql.Append(" and a.flush_type=").Append(flushType);
            if (paymentType > 0) sql.Append(" and a.pay_method=").Append(paymentType);
            if (!string.IsNullOrEmpty(ordNum) && ordNum.Trim().Length > 0)
            {
                sql.Append(" and (a.credence=:ordNum1 or a.comments like :ordNum2)");
                dbsession.AddParameter(command, ":ordNum1", DbTypeInfo.AnsiString(20), ordNum.Trim().ToUpper());
                dbsession.AddParameter(command, ":ordNum2", DbTypeInfo.AnsiString(20), "%" + ordNum.Trim().ToUpper() + "%");
            }
            if (!string.IsNullOrEmpty(mbrNum) && mbrNum.Trim().Length > 0)
            {
                sql.Append(" and m.card_id=:mbrNum");
                dbsession.AddParameter(command, ":mbrNum", DbTypeInfo.AnsiString(10), mbrNum.Trim());
            }
            if (!string.IsNullOrEmpty(mbrName) && mbrName.Trim().Length > 0)
            {
                sql.Append(" and m.name like :mbrName");
                dbsession.AddParameter(command, ":mbrName", DbTypeInfo.AnsiString(40), "%" + mbrName.Trim() + "%");
            }
            if (!string.IsNullOrEmpty(userName) && userName.Trim().Length > 0)
            {
                sql.Append(" and u.name like :userName");
                dbsession.AddParameter(command, ":userName", DbTypeInfo.AnsiString(40), "%" + userName.Trim() + "%");
            }

            if (fetch)
            {
                command.CommandText = "select count(1) from (" + sql.ToString() + ")t";
                count = Cast.Int(dbsession.ExecuteScalar(command));
            }

            sql.Append(" Order By a.modify_date");
            if (pageIndex <= 0) //不分页
                command.CommandText = sql.ToString();
            else //分页
            {
                int startIndex = (pageIndex - 1) * pageSize + 1, endIndex = pageIndex * pageSize;
                sql.Append(") mf_t1) mf_t2 WHERE mf_r>=").Append(startIndex).Append(" AND mf_r<=").Append(endIndex);
                command.CommandText = "SELECT * FROM(SELECT mf_t1.*,Rownum AS mf_r FROM(" + sql.ToString();
            }

            return dbsession.ExecuteDataSet(command);
        }

        //礼品销售
        public static DataSet GiftSale(ISession session, DateTime start, DateTime end, int pageIndex, int pageSize, bool fetch, ref int count)
        {
            DbSession dbsession = session.DbSession as DbSession;
            System.Text.StringBuilder sql = new System.Text.StringBuilder();
            sql.Append(@"
Select sku.itm_barcode As BarCode,sku.itm_code As ItemCode,i.itm_name As ItemName
       ,sku.color_code As ColorCode,color.Name As ColorText,sku.size_code As SizeCode
       ,-Sum(l.trans_qty) As Qty
       ,-Sum(l.avg_mov_cost*l.trans_qty) As CostAmt
       ,Round(Sum(l.avg_mov_cost*l.trans_qty)/Sum(l.trans_qty),2) As AvgPrice
From inv_trans_line l
Inner Join prd_item_sku sku On sku.sku_id=l.sku_id
Inner Join prd_item i On sku.itm_code=i.itm_code
Left Join prd_item_color color On color.code=sku.color_code
Where l.trans_date>=:startDate And l.trans_date<=:endDate And l.trans_type_code='201' And l.trans_price=0
Group By sku.itm_barcode,sku.itm_code,i.itm_name,sku.color_code,color.Name,sku.size_code");

            IDbCommand command = dbsession.CreateSqlStringCommand("");
            dbsession.AddParameter(command, ":startDate", DbTypeInfo.Int32(), Cast.Int(start.ToString("yyyyMMdd")));
            dbsession.AddParameter(command, ":endDate", DbTypeInfo.Int32(), Cast.Int(end.ToString("yyyyMMdd")));

            if (fetch)
            {
                command.CommandText = "select count(1) from (" + sql.ToString() + ")t";
                count = Cast.Int(dbsession.ExecuteScalar(command));
            }

            sql.Append(" Order By sku.itm_code,sku.color_code,sku.size_code");
            if (pageIndex <= 0) //不分页
                command.CommandText = sql.ToString();
            else //分页
            {
                int startIndex = (pageIndex - 1) * pageSize + 1, endIndex = pageIndex * pageSize;
                sql.Append(") mf_t1) mf_t2 WHERE mf_r>=").Append(startIndex).Append(" AND mf_r<=").Append(endIndex);
                command.CommandText = "SELECT * FROM(SELECT mf_t1.*,Rownum AS mf_r FROM(" + sql.ToString();
            }

            return dbsession.ExecuteDataSet(command);
        }
        #endregion

        #region 其它报表（财务无关）
        //库存－销售情况
        public static DataSet StockSaleDiff(ISession session, int sort, bool onlyStockLack, int pageIndex, int pageSize, bool fetch, ref int count)
        {
            DbSession dbsession = session.DbSession as DbSession;
            System.Text.StringBuilder sql = new System.Text.StringBuilder();
            sql.Append(@"
Select sku.itm_barcode As SKU,i.itm_code As ItemCode,i.itm_name As ItemName,sku.color_code As ColorCode,color.Name As ColorText,sku.size_code As SizeCode
       ,t1.OrdQty,t1.TempQty,nvl(sto.use_qty,0) As StoQty
       ,Case When nvl(sto.use_qty,0)-t1.OrdQty-t1.TempQty<0 Then nvl(sto.use_qty,0)-t1.OrdQty-t1.TempQty Else 0 End As LackQty
       ,pr.Qty as PeddingQty
From(
    Select sku_id,Sum(OrdQty) As OrdQty,Sum(TempQty) As TempQty
    From(
        Select ol.sku_id,ol.quantity As OrdQty,0 As TempQty
        From ord_headers oh
        Inner Join ord_lines ol On ol.Order_Id=oh.Id
        Where oh.status In (30/*配货中*/,20/*缺货等待*/,15/*缺款等待*/,101,21/*预售等待*/,25/*等待打印*/,0,-6)
              And ol.status Not In (-20/*缺货取消*/,-10/*取消*/)
        Union All
         Select sku_id,0 As OrdQty,quantity As TempQty From mbr_get_award 
         Where Type=12 And status=0 And last_date<=Sysdate
     )t2 Group By sku_id
)t1
Left Join inv_stock_sum sto On t1.sku_id=sto.sku_id
Inner Join prd_item_sku sku On sku.sku_id=t1.sku_id
Inner Join prd_item i On i.itm_code=sku.itm_code
Left Join prd_item_color color On color.code=sku.color_code
left join cus_pendding_rcv pr on pr.SKU_ID=sku.sku_id");

            if (onlyStockLack)
                sql.Append(" Where nvl(sto.use_qty,0)-t1.OrdQty-t1.TempQty<0");
            IDbCommand command = dbsession.CreateSqlStringCommand("");

            if (fetch)
            {
                command.CommandText = "select count(1) from (" + sql.ToString() + ")t";
                count = Cast.Int(dbsession.ExecuteScalar(command));
            }

            switch (sort)
            {
                case 1: sql.Append(" Order By sku.itm_code,sku.color_code,sku.size_code"); break;
                case 2: sql.Append(" Order By t1.OrdQty desc,sku.itm_barcode"); break;
                case 3: sql.Append(" Order By sto.use_qty asc,sku.itm_barcode"); break;
                case 4: sql.Append(" Order By nvl(sto.use_qty,0)-t1.OrdQty-t1.TempQty asc,sku.itm_barcode"); break;
            }
            if (pageIndex <= 0) //不分页
                command.CommandText = sql.ToString();
            else //分页
            {
                int startIndex = (pageIndex - 1) * pageSize + 1, endIndex = pageIndex * pageSize;
                sql.Append(") mf_t1) mf_t2 WHERE mf_r>=").Append(startIndex).Append(" AND mf_r<=").Append(endIndex);
                command.CommandText = "SELECT * FROM(SELECT mf_t1.*,Rownum AS mf_r FROM(" + sql.ToString();
            }

            return dbsession.ExecuteDataSet(command);
        }
        //按货号汇总的销售
        //    这个报表应该已经废弃不用了
        public static DataSet SaleByItemCode(ISession session, DateTime date, int pageIndex, int pageSize, bool fetch, ref int count)
        {
            DbSession dbsession = session.DbSession as DbSession;
            System.Text.StringBuilder sql = new System.Text.StringBuilder();
            sql.Append(@"
Select i.itm_code as ItemCode,i.itm_name as ItemName,t3.SaleQty,t3.SaleAmt,t3.RtnQty,t3.RtnAmt
From(
    Select sku.itm_code,Sum(t2.SaleQty) As SaleQty,Sum(t2.SaleAmt) As SaleAmt,Sum(t2.RtnQty) As RtnQty,Sum(t2.RtnAmt) As RtnAmt
    From(
        Select t1.sku_id,Sum(t1.SaleQty) As SaleQty,Sum(t1.SaleAmt) As SaleAmt,Sum(t1.RtnQty) As RtnQty,Sum(t1.RtnAmt) As RtnAmt
        From(
            Select tl.sku_id,-tl.trans_qty As SaleQty,-tl.trans_qty*tl.trans_price As SaleAmt,0 As RtnQty,0 As RtnAmt
            From inv_trans_line tl
            Where tl.trans_type_code In('201'/*,'203'*/) And tl.trans_date<=:d1
            Union All
            Select tl.sku_id,0 As SaleQty,0 As SaleAmt,tl.trans_qty As RtnQty,tl.trans_qty*tl.trans_price As RtnAmt
            From inv_trans_line tl
            Where tl.trans_type_code In(/*'115',*/'131','111') And tl.trans_date<=:d2
        )t1 Group By t1.sku_id
    )t2 Inner Join prd_item_sku sku On t2.sku_id=sku.sku_id
    Group By sku.itm_code
)t3 Inner Join prd_item i On t3.itm_code=i.itm_code");

            IDbCommand command = dbsession.CreateSqlStringCommand("");
            dbsession.AddParameter(command, ":d1", DbTypeInfo.Int32(), Cast.Int(date.ToString("yyyyMMdd")));
            dbsession.AddParameter(command, ":d2", DbTypeInfo.Int32(), Cast.Int(date.ToString("yyyyMMdd")));

            if (fetch)
            {
                command.CommandText = "select count(1) from (" + sql.ToString() + ")t";
                count = Cast.Int(dbsession.ExecuteScalar(command));
            }

            sql.Append(" Order By i.itm_code");
            if (pageIndex <= 0) //不分页
                command.CommandText = sql.ToString();
            else //分页
            {
                int startIndex = (pageIndex - 1) * pageSize + 1, endIndex = pageIndex * pageSize;
                sql.Append(") mf_t1) mf_t2 WHERE mf_r>=").Append(startIndex).Append(" AND mf_r<=").Append(endIndex);
                command.CommandText = "SELECT * FROM(SELECT mf_t1.*,Rownum AS mf_r FROM(" + sql.ToString();
            }

            return dbsession.ExecuteDataSet(command);
        }
        //按sku销售情况（销售、销售率、换货率、退货率）
        public static DataSet SaleBySKUStat(ISession session, DateTime dateFrom, DateTime dateTo, string itemCode, Report_SaleByCode_OrderBy order, bool all, int pageIndex, int pageSize, bool fetch, ref int count)
        {
            DbSession dbsession = session.DbSession as DbSession;
            System.Text.StringBuilder sql = new System.Text.StringBuilder();
            sql.Append(string.Format(@"
Select /*+ index(sku pk_prd_item_sku) */
       i.itm_code As ItemCode,i.itm_name As ItemName,sku.color_code As ColorCode,color.Name As ColorText,sku.size_code As SizeCode
       ,t1.SaleQty-nvl(t2.RtnQty,0) as SaleQty,t1.SaleAmt-nvl(t2.RtnAmt,0) as SaleAmt,nvl(t3.PurQty,0) As PurQty
       ,Case When nvl(t3.PurQty,0)=0 Then 999999 Else Round((t1.SaleQty-nvl(t2.RtnQty,0))/nvl(t3.PurQty,0)*100,2) End As SaleRate
       ,t1.ExcgQty,Case When (t1.SaleQty-nvl(t2.RtnQty,0))=0 Then 999999 Else Round(t1.ExcgQty/(t1.SaleQty-nvl(t2.RtnQty,0))*100,2) End As ExcgRate
       ,nvl(t2.RtnQty,0) As RtnQty
       ,Case When (t1.SaleQty-nvl(t2.RtnQty,0))=0 Then 999999 Else Round(nvl(t2.RtnQty,0)/(t1.SaleQty-nvl(t2.RtnQty,0))*100,2) End As RtnRate
       ,nvl(sto.use_qty,0) As StoQty  
From( --指定期间内的销售量、换货量 .
     {0} Select /*+ index(o ind_ord_header_relese_date) index(l ind_ord_lines) */ l.sku_id As SKU
             ,Sum(Case When l.status=22 Then 0 /*换货，被换的商品不算销售*/ Else l.quantity End) As SaleQty
             ,Sum((Case When l.status=22 Then 0 Else l.quantity End)*l.price) As SaleAmt
             ,Sum(Case When l.status=22 Then l.quantity Else 0 End) As ExcgQty
      From ord_headers o
      Inner Join ord_lines l On o.Id=l.order_id
      Where o.status In (0/*新创建*/,-6/*修改中*/,15/*缺款等待*/,20/*缺货等待*/,21/*预售等待*/
              ,25/*等待打印*/,30/*配货中*/,100/*已发货*/,-8/*订单退货*/,99/*部分退货*/)
          And l.status Not In(49/*调整前，退货调整时调整前的明细排除掉*/,-20/*缺货取消*/,-10/*取消*/)
          And o.release_date>=:dateStart And o.release_date<:dateEnd
          And l.sku_id Not In(Select /*+ ordered use_nl(a b) index(a pk_cus_item_exclude_from_sale) index(b ix_prd_item_sku_3) */ b.sku_id From CUS_ITEM_EXCLUDE_FROM_SALE a inner join prd_item_sku b on a.item_code=b.itm_code) --排除礼品 . 
          And ((l.sell_type=-77 And l.Set_Code Not In(Select item_code From CUS_ITEM_EXCLUDE_FROM_SALE)) Or l.sell_type<>-77)
          And o.buyer_id Not In(Select member_id From CUS_INSIDE_MEMBER) --排除内部员工 .
      Group By l.sku_id {1}
)t1 Inner Join prd_item_sku sku On sku.sku_id=t1.SKU
Inner Join prd_item i On i.itm_code=sku.itm_code
Left Join prd_item_color color On color.Code=sku.color_code
Left Join ( --指定期间内的退货量，从ERP库房交易表获取（订单上按状态获取比较麻烦，因为存在调整前、调整后的问题，并且如果购买2件而只退1件回来，从订单上无法知道只退了1件） .
      Select tl.sku_id,Sum(tl.trans_qty) As RtnQty,sum(tl.trans_qty*tl.trans_price) as RtnAmt
      From inv_trans_line tl 
      Where tl.trans_date>=:intStart And tl.trans_date<=:intEnd And tl.trans_type_code In ('111','131')
      Group By tl.sku_id
)t2 On t1.sku=t2.sku_id
Left Join inv_stock_sum sto On sto.sku_id=t1.sku
Left Join ( --总采购入库量 .
     Select tl.sku_id,Sum(tl.trans_qty) As PurQty
     From inv_trans_line tl Where tl.trans_type_code In ('101'/*采购收货*/,'151'/*采购退货*/)
     Group By tl.sku_id
)t3 On t3.sku_id=t1.sku
Where 1=1", !all ? "" : @"
select SKU,sum(SaleQty) SaleQty,sum(SaleAmt) SaleAmt,sum(ExcgQty) ExcgQty from(
", !all ? "" : @"
Union
Select sto.sku_id As SKU,0 As SaleQty,0 As SaleAmt,0 As ExcgQty
From inv_stock_sum sto
Where sto.use_qty>0 And sto.sku_id Not In (Select /*+ ordered use_nl(a b) index(a pk_cus_item_exclude_from_sale) index(b ix_prd_item_sku_3) */ b.sku_id From CUS_ITEM_EXCLUDE_FROM_SALE a inner join prd_item_sku b on a.item_code=b.itm_code) --排除礼品 .
)ttt group by SKU"));

            IDbCommand command = dbsession.CreateSqlStringCommand("");
            dbsession.AddParameter(command, ":dateStart", DbTypeInfo.Date(), dateFrom);
            dbsession.AddParameter(command, ":dateEnd", DbTypeInfo.Date(), dateTo.AddDays(1));
            dbsession.AddParameter(command, ":intStart", DbTypeInfo.Int32(), Cast.Int(dateFrom.ToString("yyyyMMdd")));
            dbsession.AddParameter(command, ":intEnd", DbTypeInfo.Int32(), Cast.Int(dateTo.ToString("yyyyMMdd")));
            if (!string.IsNullOrEmpty(itemCode) && itemCode.Trim().Length > 0)
            {
                sql.Append(" And i.itm_code=:itemCode");
                dbsession.AddParameter(command, ":itemCode", DbTypeInfo.AnsiString(18), itemCode.Trim());
            }

            //取汇总信息
            DataRow row = null;
            command.CommandText = string.Format(@"
select sum(SaleQty) SaleQty,sum(SaleAmt) SaleAmt,sum(PurQty) PurQty
    ,sum(ExcgQty) ExcgQty,sum(RtnQty) RtnQty,sum(StoQty) StoQty,count(1) cnt
from({0})t", sql.ToString());
            DataSet ds = dbsession.ExecuteDataSet(command);
            if (ds.Tables[0].Rows.Count > 0)
            {
                row = ds.Tables[0].Rows[0];
                count = Cast.Int(row["cnt"]);
            }
            else count = 0;

            if (order == Report_SaleByCode_OrderBy.BySaleAmt)
                sql.Append(" Order By t1.SaleAmt desc,i.itm_code,sku.color_code,sku.size_code");
            else if (order == Report_SaleByCode_OrderBy.BySaleQty)
                sql.Append(" Order By t1.SaleQty desc,i.itm_code,sku.color_code,sku.size_code");
            else
                sql.Append(" Order By i.itm_code,sku.color_code,sku.size_code");

            if (pageIndex <= 0) //不分页
                command.CommandText = sql.ToString();
            else //分页
            {
                int startIndex = (pageIndex - 1) * pageSize + 1, endIndex = pageIndex * pageSize;
                sql.Append(") mf_t1) mf_t2 WHERE mf_r>=").Append(startIndex).Append(" AND mf_r<=").Append(endIndex);
                command.CommandText = "SELECT * FROM(SELECT mf_t1.*,Rownum AS mf_r FROM(" + sql.ToString();
            }
            ds = dbsession.ExecuteDataSet(command);

            //将汇总信息添加到结果中
            DataRow totalRow = ds.Tables[0].NewRow();
            totalRow["ItemCode"] = "";
            totalRow["ColorText"] = "";
            totalRow["ColorCode"] = "";
            totalRow["SizeCode"] = "";
            totalRow["ItemName"] = "<span style='color:red'><strong>合计</strong></span>";
            if (row != null)
            {
                decimal saleQty = Cast.Decimal(row["SaleQty"]);
                decimal purQty = Cast.Decimal(row["PurQty"]);
                decimal excgQty = Cast.Decimal(row["ExcgQty"]);
                decimal rtnQty = Cast.Decimal(row["RtnQty"]);
                totalRow["SaleQty"] = saleQty;
                totalRow["SaleAmt"] = row["SaleAmt"];
                totalRow["PurQty"] = purQty;

                if (purQty == 0) totalRow["SaleRate"] = 999999M;
                else totalRow["SaleRate"] = saleQty / purQty * 100;

                totalRow["ExcgQty"] = excgQty;
                totalRow["RtnQty"] = rtnQty;
                totalRow["StoQty"] = row["StoQty"];

                if (saleQty == 0)
                {
                    totalRow["ExcgRate"] = 999999M;
                    totalRow["RtnRate"] = 999999M;
                }
                else
                {
                    totalRow["ExcgRate"] = excgQty / saleQty * 100;
                    totalRow["RtnRate"] = rtnQty / saleQty * 100;
                }
            }
            ds.Tables[0].Rows.InsertAt(totalRow, 0);

            //不知道为什么销售没有，却有退货的情况发生，结果就是销售量变成负数了
            //这里处理一下
            foreach (DataRow r in ds.Tables[0].Rows)
            {
                decimal saleQty = Cast.Decimal(r["SaleQty"]);
                if (saleQty >= 0M) continue;
                r["SaleQty"] = DBNull.Value;
                r["SaleAmt"] = DBNull.Value;
                r["SaleRate"] = 999999M;
                r["ExcgRate"] = 999999M;
                r["RtnRate"] = 999999M;
            }

            return ds;
        }
        //按货号销售情况（销售、销售率、换货率、退货率）
        public static DataSet SaleByItemCodeStat(ISession session, DateTime dateFrom, DateTime dateTo, string itemCode, Report_SaleByCode_OrderBy order, bool all, int pageIndex, int pageSize, bool fetch, ref int count)
        {
            DbSession dbsession = session.DbSession as DbSession;
            System.Text.StringBuilder sql = new System.Text.StringBuilder();
            sql.Append(string.Format(@"
Select t4.ItemCode,i.itm_name As ItemName
       ,t4.SaleQty,t4.SaleAmt,t4.PurQty,Case When t4.PurQty=0 Then 999999 Else Round(t4.SaleQty/t4.PurQty*100,2) End As SaleRate
       ,t4.ExcgQty,Case When t4.SaleQty=0 Then 999999 Else Round(t4.ExcgQty/t4.SaleQty*100,2) End As ExcgRate
       ,t4.RtnQty,Case When t4.SaleQty=0 Then 999999 Else Round(t4.RtnQty/t4.SaleQty*100,2) End As RtnRate
       ,t5.StoQty
From(
      Select /*+ index(sku pk_prd_item_sku) */
             sku.itm_code As ItemCode
             ,Sum(t1.SaleQty-nvl(t2.RtnQty,0)) As SaleQty,Sum(t1.SaleAmt-nvl(t2.RtnAmt,0)) As SaleAmt,Sum(nvl(t3.PurQty,0)) As PurQty,Sum(t1.ExcgQty) As ExcgQty
             ,Sum(nvl(t2.RtnQty,0)) As RtnQty
      From( --指定期间内的销售量、换货量 .
           {0} Select /*+ index(o ind_ord_header_relese_date) index(l ind_ord_lines) */ l.sku_id As SKU
                   ,Sum(Case When l.status=22 Then 0 /*换货，被换的商品不算销售*/ Else l.quantity End) As SaleQty
                   ,Sum((Case When l.status=22 Then 0 Else l.quantity End)*l.price) As SaleAmt
                   ,Sum(Case When l.status=22 Then l.quantity Else 0 End) As ExcgQty
            From ord_headers o
            Inner Join ord_lines l On o.Id=l.order_id
            Where o.status In (0/*新创建*/,-6/*修改中*/,15/*缺款等待*/,20/*缺货等待*/,21/*预售等待*/
                    ,25/*等待打印*/,30/*配货中*/,100/*已发货*/,-8/*订单退货*/,99/*部分退货*/)
                And l.status Not In(49/*调整前，退货调整时调整前的明细排除掉*/,-20/*缺货取消*/,-10/*取消*/)
                And o.release_date>=:dateStart And o.release_date<:dateEnd
                And l.sku_id Not In(Select /*+ ordered use_nl(a b) index(a pk_cus_item_exclude_from_sale) index(b ix_prd_item_sku_3) */ b.sku_id From CUS_ITEM_EXCLUDE_FROM_SALE a inner join prd_item_sku b on a.item_code=b.itm_code) --排除礼品 . 
                And ((l.sell_type=-77 And l.Set_Code Not In(Select item_code From CUS_ITEM_EXCLUDE_FROM_SALE)) Or l.sell_type<>-77)
                And o.buyer_id Not In(Select member_id From CUS_INSIDE_MEMBER) --排除内部员工 .
            Group By l.sku_id {1}
      )t1 Inner Join prd_item_sku sku On sku.sku_id=t1.SKU
      Left Join ( --指定期间内的退货量，从ERP库房交易表获取（订单上按状态获取比较麻烦，因为存在调整前、调整后的问题，并且如果购买2件而只退1件回来，从订单上无法知道只退了1件） .
            Select tl.sku_id,Sum(tl.trans_qty) As RtnQty,sum(tl.trans_qty*tl.trans_price) as RtnAmt
            From inv_trans_line tl 
            Where tl.trans_date>=:intStart And tl.trans_date<=:intEnd And tl.trans_type_code In ('111','131')
            Group By tl.sku_id
      )t2 On t1.sku=t2.sku_id
      Left Join ( --总采购入库量 .
           Select tl.sku_id,Sum(tl.trans_qty) As PurQty
           From inv_trans_line tl Where tl.trans_type_code In ('101'/*采购收货*/,'151'/*采购退货*/)
           Group By tl.sku_id
      )t3 On t3.sku_id=t1.sku
      Group By sku.itm_code
)t4 Inner Join prd_item i On i.itm_code=t4.ItemCode
Inner Join (
      Select sku.itm_code,Sum(sto.use_qty) As StoQty
      From inv_stock_sum sto
      Inner Join prd_item_sku sku On sto.sku_id=sku.sku_id
      Group By sku.itm_code
)t5 On t5.itm_code=t4.ItemCode
Where 1=1", !all ? "" : @"
select SKU,sum(SaleQty) SaleQty,sum(SaleAmt) SaleAmt,sum(ExcgQty) ExcgQty from(
", !all ? "" : @"
Union
Select sto.sku_id As SKU,0 As SaleQty,0 As SaleAmt,0 As ExcgQty
From inv_stock_sum sto
Where sto.use_qty>0 And sto.sku_id Not In (Select /*+ ordered use_nl(a b) index(a pk_cus_item_exclude_from_sale) index(b ix_prd_item_sku_3) */ b.sku_id From CUS_ITEM_EXCLUDE_FROM_SALE a inner join prd_item_sku b on a.item_code=b.itm_code) --排除礼品 .
)ttt group by SKU"));

            IDbCommand command = dbsession.CreateSqlStringCommand("");
            dbsession.AddParameter(command, ":dateStart", DbTypeInfo.Date(), dateFrom);
            dbsession.AddParameter(command, ":dateEnd", DbTypeInfo.Date(), dateTo.AddDays(1));
            dbsession.AddParameter(command, ":intStart", DbTypeInfo.Int32(), Cast.Int(dateFrom.ToString("yyyyMMdd")));
            dbsession.AddParameter(command, ":intEnd", DbTypeInfo.Int32(), Cast.Int(dateTo.ToString("yyyyMMdd")));
            if (!string.IsNullOrEmpty(itemCode) && itemCode.Trim().Length > 0)
            {
                sql.Append(" And i.itm_code=:itemCode");
                dbsession.AddParameter(command, ":itemCode", DbTypeInfo.AnsiString(18), itemCode.Trim());
            }

            //取汇总信息
            DataRow row = null;
            command.CommandText = string.Format(@"
select sum(SaleQty) SaleQty,sum(SaleAmt) SaleAmt,sum(PurQty) PurQty
    ,sum(ExcgQty) ExcgQty,sum(RtnQty) RtnQty,sum(StoQty) StoQty,count(1) as cnt
from({0})t", sql.ToString());
            DataSet ds = dbsession.ExecuteDataSet(command);
            if (ds.Tables[0].Rows.Count > 0)
            {
                row = ds.Tables[0].Rows[0];
                count = Cast.Int(row["cnt"]);
            }
            else count = 0;

            if (order == Report_SaleByCode_OrderBy.BySaleAmt)
                sql.Append(" Order By t4.SaleAmt desc,t4.ItemCode");
            else if (order == Report_SaleByCode_OrderBy.BySaleQty)
                sql.Append(" Order By t4.SaleQty desc,t4.ItemCode");
            else
                sql.Append(" Order By t4.ItemCode");
            if (pageIndex <= 0) //不分页
                command.CommandText = sql.ToString();
            else //分页
            {
                int startIndex = (pageIndex - 1) * pageSize + 1, endIndex = pageIndex * pageSize;
                sql.Append(") mf_t1) mf_t2 WHERE mf_r>=").Append(startIndex).Append(" AND mf_r<=").Append(endIndex);
                command.CommandText = "SELECT * FROM(SELECT mf_t1.*,Rownum AS mf_r FROM(" + sql.ToString();
            }

            ds = dbsession.ExecuteDataSet(command);

            //将汇总信息添加到结果中
            DataRow totalRow = ds.Tables[0].NewRow();
            totalRow["ItemCode"] = "";
            totalRow["ItemName"] = "<span style='color:red'><strong>合计</strong></span>";
            if (row != null)
            {
                decimal saleQty = Cast.Decimal(row["SaleQty"]);
                decimal purQty = Cast.Decimal(row["PurQty"]);
                decimal excgQty = Cast.Decimal(row["ExcgQty"]);
                decimal rtnQty = Cast.Decimal(row["RtnQty"]);
                totalRow["SaleQty"] = saleQty;
                totalRow["SaleAmt"] = row["SaleAmt"];
                totalRow["PurQty"] = purQty;

                if (purQty == 0) totalRow["SaleRate"] = 999999M;
                else totalRow["SaleRate"] = saleQty / purQty * 100;

                totalRow["ExcgQty"] = excgQty;
                totalRow["RtnQty"] = rtnQty;
                totalRow["StoQty"] = row["StoQty"];

                if (saleQty == 0)
                {
                    totalRow["ExcgRate"] = 999999M;
                    totalRow["RtnRate"] = 999999M;
                }
                else
                {
                    totalRow["ExcgRate"] = excgQty / saleQty * 100;
                    totalRow["RtnRate"] = rtnQty / saleQty * 100;
                }
            }
            ds.Tables[0].Rows.InsertAt(totalRow, 0);

            //不知道为什么销售没有，却有退货的情况发生，结果就是销售量变成负数了
            //这里处理一下
            foreach (DataRow r in ds.Tables[0].Rows)
            {
                decimal saleQty = Cast.Decimal(r["SaleQty"]);
                if (saleQty >= 0M) continue;
                r["SaleQty"] = DBNull.Value;
                r["SaleAmt"] = DBNull.Value;
                r["SaleRate"] = 999999M;
                r["ExcgRate"] = 999999M;
                r["RtnRate"] = 999999M;
            }

            return ds;
        }
        //按产品类别销售情况（销售、销售率、换货率、退货率）
        public static DataSet SaleByCategoryStat(ISession session, DateTime dateFrom, DateTime dateTo, int pageIndex, int pageSize, bool fetch, ref int count)
        {
            DbSession dbsession = session.DbSession as DbSession;
            System.Text.StringBuilder sql = new System.Text.StringBuilder();
            sql.Append(@"
Select cat.catalog_name As CatName
       ,t4.SaleQty,t4.SaleAmt,t4.PurQty,Case When t4.PurQty=0 Then 999999 Else Round(t4.SaleQty/t4.PurQty*100,2) End As SaleRate
       ,t4.ExcgQty,Case When t4.SaleQty=0 Then 999999 Else Round(t4.ExcgQty/t4.SaleQty*100,2) End As ExcgRate
       ,t4.RtnQty,Case When t4.SaleQty=0 Then 999999 Else Round(t4.RtnQty/t4.SaleQty*100,2) End As RtnRate
From(
      Select /*+ index(sku pk_prd_item_sku) */
             i.category_id
             ,Sum(t1.SaleQty) As SaleQty,Sum(t1.SaleAmt) As SaleAmt,Sum(nvl(t3.PurQty,0)) As PurQty,Sum(t1.ExcgQty) As ExcgQty
             ,Sum(nvl(t2.RtnQty,0)) As RtnQty
      From( --指定期间内的销售量、换货量 .
            Select /*+ index(o ind_ord_header_relese_date) index(l ind_ord_lines) */ l.sku_id As SKU
                   ,Sum(Case When l.status=22 Then 0 /*换货，被换的商品不算销售*/ Else l.quantity End) As SaleQty
                   ,Sum((Case When l.status=22 Then 0 Else l.quantity End)*l.price) As SaleAmt
                   ,Sum(Case When l.status=22 Then l.quantity Else 0 End) As ExcgQty
            From ord_headers o
            Inner Join ord_lines l On o.Id=l.order_id
            Where o.status In (0/*新创建*/,-6/*修改中*/,15/*缺款等待*/,20/*缺货等待*/,21/*预售等待*/
                    ,25/*等待打印*/,30/*配货中*/,100/*已发货*/,-8/*订单退货*/,99/*部分退货*/)
                And l.status Not In(49/*调整前，退货调整时调整前的明细排除掉*/,-20/*缺货取消*/,-10/*取消*/)
                And o.release_date>=:dateStart And o.release_date<:dateEnd
                And l.sku_id Not In(Select /*+ ordered use_nl(a b) index(a pk_cus_item_exclude_from_sale) index(b ix_prd_item_sku_3) */ b.sku_id From CUS_ITEM_EXCLUDE_FROM_SALE a inner join prd_item_sku b on a.item_code=b.itm_code) --排除礼品 . 
                And ((l.sell_type=-77 And l.Set_Code Not In(Select item_code From CUS_ITEM_EXCLUDE_FROM_SALE)) Or l.sell_type<>-77)
                And o.buyer_id Not In(Select member_id From CUS_INSIDE_MEMBER) --排除内部员工 .
            Group By l.sku_id
      )t1 Inner Join prd_item_sku sku On sku.sku_id=t1.SKU
      Inner Join prd_item i On i.itm_code=sku.itm_code
      Left Join ( --指定期间内的退货量，从ERP库房交易表获取（订单上按状态获取比较麻烦，因为存在调整前、调整后的问题，并且如果购买2件而只退1件回来，从订单上无法知道只退了1件） .
            Select tl.sku_id,Sum(tl.trans_qty) As RtnQty
            From inv_trans_line tl 
            Where tl.trans_date>=:intStart And tl.trans_date<=:intEnd And tl.trans_type_code In ('111','131')
            Group By tl.sku_id
      )t2 On t1.sku=t2.sku_id
      Left Join ( --总采购入库量 .
           Select tl.sku_id,Sum(tl.trans_qty) As PurQty
           From inv_trans_line tl Where tl.trans_type_code In ('101'/*采购收货*/,'151'/*采购退货*/)
           Group By tl.sku_id
      )t3 On t3.sku_id=t1.sku
      Group By i.category_id
)t4 Inner Join prd_item_category cat On cat.catalog_id=t4.category_id
Where 1=1");

            IDbCommand command = dbsession.CreateSqlStringCommand("");
            dbsession.AddParameter(command, ":dateStart", DbTypeInfo.Date(), dateFrom);
            dbsession.AddParameter(command, ":dateEnd", DbTypeInfo.Date(), dateTo.AddDays(1));
            dbsession.AddParameter(command, ":intStart", DbTypeInfo.Int32(), Cast.Int(dateFrom.ToString("yyyyMMdd")));
            dbsession.AddParameter(command, ":intEnd", DbTypeInfo.Int32(), Cast.Int(dateTo.ToString("yyyyMMdd")));

            if (fetch)
            {
                command.CommandText = "select count(1) from (" + sql.ToString() + ")t";
                count = Cast.Int(dbsession.ExecuteScalar(command));
            }

            sql.Append(" Order By t4.SaleQty desc,t4.category_id");
            if (pageIndex <= 0) //不分页
                command.CommandText = sql.ToString();
            else //分页
            {
                int startIndex = (pageIndex - 1) * pageSize + 1, endIndex = pageIndex * pageSize;
                sql.Append(") mf_t1) mf_t2 WHERE mf_r>=").Append(startIndex).Append(" AND mf_r<=").Append(endIndex);
                command.CommandText = "SELECT * FROM(SELECT mf_t1.*,Rownum AS mf_r FROM(" + sql.ToString();
            }

            return dbsession.ExecuteDataSet(command);
        }
        //日销售情况统计
        public static DataSet SaleByDateStat(ISession session, DateTime dateFrom, DateTime dateTo, int pageIndex, int pageSize, bool fetch, ref int count)
        {
            DbSession dbsession = session.DbSession as DbSession;
            System.Text.StringBuilder sql = new System.Text.StringBuilder();
            sql.Append(@"
Select t1.OrdDate,Count(t1.OrdDate) As OrdCount,Sum(t1.OrdAmt) As OrdAmt,Sum(t1.LineCount) As LineCount
From (
      Select o.so_number As OrdNum,to_char(o.release_date,'YYYY-MM-DD') As OrdDate
             ,o.goods_fee+o.delivery_fee+o.package_fee+o.append_fee+o.discount_fee As OrdAmt
             ,(Select Count(l.Id) From ord_lines l Where l.order_id=o.Id) As LineCount
      From ord_headers o
      Where o.status In (0/*新创建*/,-6/*修改中*/,15/*缺款等待*/,20/*缺货等待*/,21/*预售等待*/
                    ,25/*等待打印*/,30/*配货中*/,100/*已发货*/,-8/*订单退货*/,99/*部分退货*/)
             And o.order_type<>20 /*排除换货订单，否则这部分销售被重复统计*/
             And o.buyer_id<>9178221 And o.Goods_Fee>0 /*排除礼品单独发货(例如只有5块运费)的订单*/
             And o.release_date>=:dateStart And o.release_date<:dateEnd
)t1
Group By t1.OrdDate
Order By t1.OrdDate");

            IDbCommand command = dbsession.CreateSqlStringCommand("");
            dbsession.AddParameter(command, ":dateStart", DbTypeInfo.Date(), dateFrom);
            dbsession.AddParameter(command, ":dateEnd", DbTypeInfo.Date(), dateTo.AddDays(1));

            if (fetch)
            {
                command.CommandText = "select count(1) from (" + sql.ToString() + ")t";
                count = Cast.Int(dbsession.ExecuteScalar(command));
            }

            if (pageIndex <= 0) //不分页
                command.CommandText = sql.ToString();
            else //分页
            {
                int startIndex = (pageIndex - 1) * pageSize + 1, endIndex = pageIndex * pageSize;
                sql.Append(") mf_t1) mf_t2 WHERE mf_r>=").Append(startIndex).Append(" AND mf_r<=").Append(endIndex);
                command.CommandText = "SELECT * FROM(SELECT mf_t1.*,Rownum AS mf_r FROM(" + sql.ToString();
            }

            return dbsession.ExecuteDataSet(command);
        }
        //套装销售
        public static DataSet SaleBySetCodeStat(ISession session, DateTime dateFrom, DateTime dateTo, string setCode, int pageIndex, int pageSize, bool fetch, ref int count)
        {
            //调用存储过程获取套装销售数据
            //存储过程执行完毕后，销售数据在临时表CUS_TEMP_4_SETSALE中
            DbSession dbsession = session.DbSession as DbSession;
            IDbCommand cmd1 = null, cmd2 = null;
            DataSet result = null;
            cmd1 = dbsession.CreateStoredProcCommand("p_rpt_set_sale", new object[] { dateFrom, dateTo.AddDays(1), setCode, pageIndex, pageSize, fetch ? 1 : 0, 0 });
            dbsession.ExecuteNonQuery(cmd1);

            if (fetch)
            {
                IDbDataParameter param = cmd1.Parameters[cmd1.Parameters.Count - 1] as IDbDataParameter;
                count = Cast.Int(param.Value);
            }

            //从临时表取报表数据
            //？现在的退货率：(全部退货套数+部分退货套数)/总销售套数
            cmd2 = dbsession.CreateSqlStringCommand(@"
Select i.itm_code As ItemCode,i.itm_name As ItemName,a.sale_qty As SaleQty,a.sale_amt As SaleAmt
       ,a.rtn_qty As RtnQty, a.part_rtn_qty As PartRtnQty
       ,Case When a.sale_qty=0 Then 999999 Else round((a.rtn_qty+a.part_rtn_qty)/a.sale_qty*100,2) End As RtnRate
       ,a.excg_qty As ExcgQty,Case When a.sale_qty=0 Then 999999 Else round(a.excg_qty/a.sale_qty*100, 2) End As ExcgRate
From CUS_TEMP_4_SETSALE a
Inner Join prd_item i On i.itm_code=a.set_code
Order By a.sale_qty desc,i.itm_code");
            result = dbsession.ExecuteDataSet(cmd2);

            return result;
        }
        //库存预警，目前逻辑：
        //    只有prd_item_sku.sto_method大于0的才启用库存预警功能；
        //    sto_max, sto_safe, sto_min的设置方面，大于0就算做有效设置，有效的设置应该确保sto_max>sto_safe>sto_min
        //WarnType: 1大于最大库存；2小于安全库存；3小于最小库存
        public static DataSet StockWarning(ISession session, int pageIndex, int pageSize, bool fetch, ref int count)
        {
            DbSession dbsession = session.DbSession as DbSession;
            System.Text.StringBuilder sql = new System.Text.StringBuilder();
            sql.Append(@"
Select sku.itm_barcode As SKU,i.itm_code As ItemCode,i.itm_name As ItemName
       ,sku.color_code As ColorCode,color.Name As ColorText,sku.Size_Code As SizeCode
       ,Case When nvl(sku.sto_max,0)>0 And sto.use_qty>nvl(sku.sto_max,0) Then 1
                 When nvl(sku.sto_min,0)>0 And sto.use_qty<nvl(sku.sto_min,0) Then 3
                 When nvl(sku.sto_safe,0)>0 And sto.use_qty<nvl(sku.sto_safe,0) Then 2
                 Else 0 End As WarnType
       ,sku.sto_max As MaxQty,sto.use_qty As StoQty,sku.sto_safe As SafeQty,sku.sto_min As MinQty
From prd_item_sku sku
Inner Join inv_stock_sum sto On sku.sku_id=sto.sku_id
Inner Join prd_item i On i.itm_code=sku.itm_code
Left Join prd_item_color color On color.code=sku.color_code
Where sku.sto_method>0 And (
      (nvl(sku.sto_max,0)>0 And sto.use_qty>nvl(sku.sto_max,0)) Or 
      (nvl(sku.sto_safe,0)>0 And sto.use_qty<nvl(sku.sto_safe,0)) Or 
      (nvl(sku.sto_min,0)>0 And sto.use_qty<nvl(sku.sto_min,0))
)");

            IDbCommand command = dbsession.CreateSqlStringCommand("");

            if (fetch)
            {
                command.CommandText = "select count(1) from (" + sql.ToString() + ")t";
                count = Cast.Int(dbsession.ExecuteScalar(command));
            }

            sql.Append(" Order By i.itm_code,sku.color_code,sku.size_code");
            if (pageIndex <= 0) //不分页
                command.CommandText = sql.ToString();
            else //分页
            {
                int startIndex = (pageIndex - 1) * pageSize + 1, endIndex = pageIndex * pageSize;
                sql.Append(") mf_t1) mf_t2 WHERE mf_r>=").Append(startIndex).Append(" AND mf_r<=").Append(endIndex);
                command.CommandText = "SELECT * FROM(SELECT mf_t1.*,Rownum AS mf_r FROM(" + sql.ToString();
            }

            return dbsession.ExecuteDataSet(command);
        }

        //按付款方式统计
        public static DataSet SaleByPaymentMethod(ISession session, DateTime fromDate, DateTime toDate)
        {
            DbSession dbsession = session.DbSession as DbSession;
            IDbCommand command = dbsession.CreateSqlStringCommand("");
            System.Text.StringBuilder sql = new System.Text.StringBuilder();
            sql.Append(@"
Select o.payment_method As PayID,pay.Name As PayName
       ,Count(o.Id) As TotalCount,Sum(o.goods_fee+o.append_fee+o.delivery_fee+o.package_fee+o.discount_fee) As TotalAmt
       ,0.01 As CountPercent, 0.01 As AmtPercent, ' ' As CountText, ' ' As AmtText
From ord_headers o
Left Join s_payment_method pay On pay.Id=o.payment_method
Where o.status In(100,99) And (o.Order_Type=20 Or (o.Order_Type<>20 And o.goods_fee>0))");
            if (fromDate > new DateTime(1900, 1, 1))
            {
                sql.Append(" And o.create_date>=:fromDate");
                dbsession.AddParameter(command, ":fromDate", DbTypeInfo.Date(), fromDate);
            }
            if (toDate > new DateTime(1900, 1, 1))
            {
                sql.Append(" And o.create_date<=:toDate");
                dbsession.AddParameter(command, ":toDate", DbTypeInfo.Date(), toDate.AddDays(1));
            }
            sql.Append(" Group By o.payment_method,pay.Name");
            command.CommandText = sql.ToString();
            DataSet ds = dbsession.ExecuteDataSet(command);

            decimal totalCount = 0M, totalAmt = 0M, temp;
            foreach (DataRow row in ds.Tables[0].Rows)
            {
                totalCount += Cast.Decimal(row["TotalCount"]);
                totalAmt += Cast.Decimal(row["TotalAmt"]);
            }
            string text = "";
            foreach (DataRow row in ds.Tables[0].Rows)
            {
                temp = totalCount == 0M ? 0M : Cast.Decimal(row["TotalCount"]) / totalCount * 100;
                text = temp == 0M ? "" : temp.ToString("#0.#0") + "%";
                row["CountText"] = text;

                temp = totalAmt == 0M ? 0M : Cast.Decimal(row["TotalAmt"]) / totalAmt * 100;
                text = temp == 0M ? "" : temp.ToString("#0.#0") + "%";
                row["AmtText"] = text;
            }

            return ds;
        }
        //按发送方式统计
        public static DataSet SaleByDeliveryType(ISession session, DateTime fromDate, DateTime toDate)
        {
            DbSession dbsession = session.DbSession as DbSession;
            IDbCommand command = dbsession.CreateSqlStringCommand("");
            System.Text.StringBuilder sql = new System.Text.StringBuilder();
            sql.Append(@"
Select o.delivery_type As DlvID,dt.Name As DlvName
       ,Count(o.Id) As TotalCount,Sum(o.goods_fee+o.append_fee+o.delivery_fee+o.package_fee+o.discount_fee) As TotalAmt
       ,0.01 As CountPercent, 0.01 As AmtPercent, ' ' As CountText, ' ' As AmtText
From ord_headers o
Left Join s_delivery_type dt On dt.Id=o.delivery_type
Where o.status In(100,99) And (o.Order_Type=20 Or (o.Order_Type<>20 And o.goods_fee>0))");
            if (fromDate > new DateTime(1900, 1, 1))
            {
                sql.Append(" And o.create_date>=:fromDate");
                dbsession.AddParameter(command, ":fromDate", DbTypeInfo.Date(), fromDate);
            }
            if (toDate > new DateTime(1900, 1, 1))
            {
                sql.Append(" And o.create_date<=:toDate");
                dbsession.AddParameter(command, ":toDate", DbTypeInfo.Date(), toDate.AddDays(1));
            }
            sql.Append(" Group By o.delivery_type,dt.Name");
            command.CommandText = sql.ToString();
            DataSet ds = dbsession.ExecuteDataSet(command);

            decimal totalCount = 0M, totalAmt = 0M, temp;
            foreach (DataRow row in ds.Tables[0].Rows)
            {
                totalCount += Cast.Decimal(row["TotalCount"]);
                totalAmt += Cast.Decimal(row["TotalAmt"]);
            }
            string text = "";
            foreach (DataRow row in ds.Tables[0].Rows)
            {
                temp = totalCount == 0M ? 0M : Cast.Decimal(row["TotalCount"]) / totalCount * 100;
                text = temp == 0M ? "" : temp.ToString("#0.#0") + "%";
                row["CountText"] = text;

                temp = totalAmt == 0M ? 0M : Cast.Decimal(row["TotalAmt"]) / totalAmt * 100;
                text = temp == 0M ? "" : temp.ToString("#0.#0") + "%";
                row["AmtText"] = text;
            }

            return ds;
        }
        #endregion
    }
}