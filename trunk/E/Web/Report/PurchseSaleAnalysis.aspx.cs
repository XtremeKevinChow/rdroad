using System;
using System.Collections.Generic;
using System.Data;
using System.Web.UI;
using Magic.Framework.Data;
using Magic.Framework.ORM;
using Magic.Framework.Utils;
using Magic.Web.UI;

public partial class Report_PurchseSaleAnalysis : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            this.txtDateFrom.Text = DateTime.Now.AddDays(-1).ToString("yyyy-MM-dd");
            this.txtDateTo.Text = DateTime.Now.ToString("yyyy-MM-dd");
            using (ISession session = new Session())
            {
                this.QueryAndBindData(session, 1, this.magicPagerMain.PageSize, true);
            }
        }
        else this.frameDownload.Attributes["src"] = "about:blank;";
    }

    private void QueryAndBindData(ISession session, int pageIndex, int pageSize, bool fetch)
    {
        int count = 0;
        DataSet ds = null;

        DateTime start = Cast.DateTime(this.txtDateFrom.Text, DateTime.Now.AddMonths(-1));
        DateTime end = Cast.DateTime(this.txtDateTo.Text, DateTime.Now);
        this.txtDateFrom.Text = start.ToString("yyyy-MM-dd");
        this.txtDateTo.Text = end.ToString("yyyy-MM-dd");
        string itemCode = "%" + this.txtItemCode.Text.Trim() + "%";
        string itemName = "%" + this.txtItemName.Text.Trim() + "%";
        //string color = "%" + this.txtColor.Text.Trim() + "%";
        //string size = "%" + this.txtSize.Text.Trim() + "%";
        string color = "%%";
        string size = "%%";
        int all = this.chkAll.Checked ? 1 : 0;

        try
        {
            session.BeginTransaction();
            DbSession db = session.DbSession as DbSession;
            IDbCommand cmd = db.CreateStoredProcCommand("p_rpt_pur_sale_analysis", new object[] { start, end, all, itemCode, itemName, color, size });
            db.ExecuteNonQuery(cmd);

            if (fetch)
                count = Cast.Int(db.ExecuteScalar(@"
Select Count(1) From(
   Select Distinct sku.itm_code
   From cus_temp_4_pur_sale_analysis a
   Inner Join prd_item_sku sku On a.sku_id=sku.sku_id
) t"));

            int startIndex = (pageIndex - 1) * pageSize + 1, endIndex = pageIndex * pageSize;
            string sql = string.Format(@"
Select * From(
    Select t.*,Rownum As rn From(
        Select it.itm_code ItemCode,it.itm_name ItemName
           ,Sum(a.begin_qty) begin_qty,Sum(a.begin_amt) begin_amt
           ,Sum(a.pur_qty) pur_qty,Sum(a.pur_amt) pur_amt
           ,Sum(a.pur_rtn_qty) pur_rtn_qty,Sum(a.pur_rtn_amt) pur_rtn_amt
           ,Sum(a.sale_qty) sale_qty,Sum(a.sale_amt) sale_amt
           ,Sum(a.sale_rtn_qty) sale_rtn_qty,Sum(a.sale_rtn_amt) sale_rtn_amt
           ,Sum(a.chk_qty+a.adj_qty+a.lend_qty+a.lend_rtn_qty+a.used_qty+a.oth_in_qty+a.scrap_qty) other_qty
           ,Sum(nvl(a.end_qty,0)) end_qty,Sum(nvl(a.end_amt,0)) end_amt
           ,Sum(nvl(sto.use_qty,0)) sto_qty
        From cus_temp_4_pur_sale_analysis a
        Left Join inv_stock_sum sto On sto.sku_id=a.sku_id
        Inner Join prd_item_sku sku On a.sku_id=sku.sku_id
        Inner Join prd_item it On it.itm_id=sku.itm_id
        Group By it.itm_code,it.itm_name
        Order By it.itm_code
    )t
)t2 Where t2.rn>={0} And t2.rn<={1}", startIndex, endIndex);
            cmd = db.CreateSqlStringCommand(sql);
            ds = db.ExecuteDataSet(cmd);

            session.Commit();
        }
        catch(Exception er)
        {
            session.Rollback();
            WebUtil.ShowError(this, er);
        }

        this.repeater.DataSource = ds;
        this.repeater.DataBind();
        if (fetch) this.magicPagerMain.RecordCount = this.magicPagerSub.RecordCount = count;
        WebUtil.SetMagicPager(magicPagerMain, pageSize, pageIndex);
        WebUtil.SetMagicPager(magicPagerSub, pageSize, pageIndex);
    }

    protected void btnQuery_Click(object sender, ImageClickEventArgs e)
    {
        using (ISession session = new Session())
        {
            this.QueryAndBindData(session, 1, this.magicPagerMain.PageSize, true);
        }
    }
    protected void MagicItemCommand(object sender, MagicItemEventArgs e)
    {
        if (e.CommandName != "Download")
            return;

        using (ISession session = new Session())
        {
            DataSet ds = null;

            DateTime start = Cast.DateTime(this.txtDateFrom.Text, DateTime.Now.AddMonths(-1));
            DateTime end = Cast.DateTime(this.txtDateTo.Text, DateTime.Now);
            this.txtDateFrom.Text = start.ToString("yyyy-MM-dd");
            this.txtDateTo.Text = end.ToString("yyyy-MM-dd");
            string itemCode = "%" + this.txtItemCode.Text.Trim() + "%";
            string itemName = "%" + this.txtItemName.Text.Trim() + "%";
            //string color = "%" + this.txtColor.Text.Trim() + "%";
            //string size = "%" + this.txtSize.Text.Trim() + "%";
            string color = "%%";
            string size = "%%";
            int all = this.chkAll.Checked ? 1 : 0;

            try
            {
                session.BeginTransaction();
                DbSession db = session.DbSession as DbSession;
                IDbCommand cmd = db.CreateStoredProcCommand("p_rpt_pur_sale_analysis", new object[] { start, end, all, itemCode, itemName, color, size });
                db.ExecuteNonQuery(cmd);

                string sql = @"
Select it.itm_code ItemCode,it.itm_name ItemName
   ,Sum(a.begin_qty) begin_qty,Sum(a.begin_amt) begin_amt
   ,Sum(a.pur_qty) pur_qty,Sum(a.pur_amt) pur_amt
   ,Sum(a.pur_rtn_qty) pur_rtn_qty,Sum(a.pur_rtn_amt) pur_rtn_amt
   ,Sum(a.sale_qty) sale_qty,Sum(a.sale_amt) sale_amt
   ,Sum(a.sale_rtn_qty) sale_rtn_qty,Sum(a.sale_rtn_amt) sale_rtn_amt
   ,Sum(a.chk_qty+a.adj_qty+a.lend_qty+a.lend_rtn_qty+a.used_qty+a.oth_in_qty+a.scrap_qty) other_qty
   ,Sum(nvl(a.end_qty,0)) end_qty,Sum(nvl(a.end_amt,0)) end_amt
   ,Sum(nvl(sto.use_qty,0)) sto_qty
From cus_temp_4_pur_sale_analysis a
Left Join inv_stock_sum sto On sto.sku_id=a.sku_id
Inner Join prd_item_sku sku On a.sku_id=sku.sku_id
Inner Join prd_item it On it.itm_id=sku.itm_id
Group By it.itm_code,it.itm_name
Order By it.itm_code";
                cmd = db.CreateSqlStringCommand(sql);
                ds = db.ExecuteDataSet(cmd);

                session.Commit();
            }
            catch (Exception er)
            {
                session.Rollback();
                WebUtil.ShowError(this, er);
            }

            string fileName = DownloadUtil.DownloadXls("Purchase_Sale_Analysis_" + DateTime.Now.ToString("yyMMdd") + ".xls"
                , "PUR_SALE_ANALYSIS", Server.MapPath("/Template/RPT_Pur_Sale_Analysis_.xls"), null, 3
                , new List<DownloadFormat>()
                    {
                        new DownloadFormat(DataType.Text, "", "ItemCode"),
                        new DownloadFormat(DataType.Text, "", "ItemName"),
                        new DownloadFormat(DataType.Number, "", "sto_qty"),
                        new DownloadFormat(DataType.Number, "", "begin_qty"),
                        new DownloadFormat(DataType.Number, "", "begin_amt"),
                        new DownloadFormat(DataType.Number, "", "pur_qty"),
                        new DownloadFormat(DataType.Number, "", "pur_amt"),
                        new DownloadFormat(DataType.Number, "", "pur_rtn_qty"),
                        new DownloadFormat(DataType.Number, "", "pur_rtn_amt"),
                        new DownloadFormat(DataType.Number, "", "sale_qty"),
                        new DownloadFormat(DataType.Number, "", "sale_amt"),
                        new DownloadFormat(DataType.Number, "", "sale_rtn_qty"),
                        new DownloadFormat(DataType.Number, "", "sale_rtn_amt"),
                        new DownloadFormat(DataType.Number, "", "other_qty"),
                        new DownloadFormat(DataType.Number, "", "end_qty"),
                        new DownloadFormat(DataType.Number, "", "end_amt")
                    }, ds);
            this.frameDownload.Attributes["src"] = fileName;
        }
    }
    protected void MagicPager_PageChanged(object src, Magic.Web.UI.PageChangedEventArgs e)
    {
        this.magicPagerSub.PageSize = this.magicPagerMain.PageSize;
        using (ISession session = new Session())
        {
            this.QueryAndBindData(session, e.NewPageIndex, e.Pager.PageSize, false);
        }
    }
}