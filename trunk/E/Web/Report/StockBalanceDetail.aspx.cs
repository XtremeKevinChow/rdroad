using System;
using System.Collections.Generic;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data;
using Magic.Framework.Utils;
using Magic.Framework.ORM;
using Magic.Basis;
using Magic.ERP.Report;
using Magic.Web.UI;

public partial class Report_StockBalanceDetail : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            this.drpPeriod.Items.Clear();
            this.drpItemCat.Items.Clear();
            this.drpItemCat.Items.Add(new ListItem("　", "0"));

            using (ISession session = new Session())
            {
                IList<INVPeriod> periods = INVPeriod.ClosedPeriods(session);
                foreach (INVPeriod p in periods)
                    this.drpPeriod.Items.Add(new ListItem(p.PeriodCode, p.PeriodID.ToString()));
                IList<ItemCategory> cats = session.CreateEntityQuery<ItemCategory>()
                    .List<ItemCategory>();
                foreach (ItemCategory c in cats)
                    this.drpItemCat.Items.Add(new ListItem(c.CatName, c.CategoryID.ToString()));

                if (WebUtil.Param("mode").Trim().ToLower() == "fix")
                {
                    this.cmdReturn1.Visible = true;
                    this.cmdReturn2.Visible = true;
                    this.cmdReturn1["Return"].NavigateUrl = "StockBalanceSum.aspx?pd=" + WebUtil.Param("pd");
                    this.cmdReturn2["Return"].NavigateUrl = "StockBalanceSum.aspx?pd=" + WebUtil.Param("pd");
                    this.drpItemCat.SelectedValue = WebUtil.Param("cat");
                    this.drpPeriod.SelectedValue = WebUtil.Param("pd");
                }
                else
                {
                    this.cmdReturn1.Visible = false;
                    this.cmdReturn2.Visible = false;
                }

                this.QueryAndBindData(session, 1, this.magicPagerMain.PageSize, true);
            }
        }
        else this.frameDownload.Attributes["src"] = "about:blank;";
    }

    private void QueryAndBindData(ISession session, int pageIndex, int pageSize, bool fetch)
    {
        int periodId = Cast.Int(this.drpPeriod.SelectedValue, 0);
        if (periodId <= 0) return;

        int count = 0;
        this.repeater.DataSource = Report.StockBalanceDetail(session, periodId, Cast.Int(this.drpItemCat.SelectedValue), this.txtItemCode.Text, this.txtItemName.Text, this.txtColor.Text, this.txtSize.Text, pageIndex, pageSize, fetch, ref count);
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
            DateTime defaultDate = new DateTime(1900, 1, 1);
            int periodId = Cast.Int(this.drpPeriod.SelectedValue, 0);
            if (periodId <= 0) return;

            int count = 0;
            DataSet ds = Report.StockBalanceDetail(session, periodId, Cast.Int(this.drpItemCat.SelectedValue), this.txtItemCode.Text, this.txtItemName.Text, this.txtColor.Text, this.txtSize.Text, -1, 0, false, ref count);
            string fileName = DownloadUtil.DownloadXls("Inv_Balance_Detail_" + DateTime.Now.ToString("yyMMdd") + ".xls"
                , "RPT_INV_DTL", Server.MapPath("/Template/RPT_Stock_Balance_Detail_Download.xls"), null, 3
                , new List<DownloadFormat>()
                    {
                        new DownloadFormat(DataType.Text, "", "CatName"),
                        new DownloadFormat(DataType.Text, "", "ItemCode"),
                        new DownloadFormat(DataType.Text, "", "ItemName"),
                        new DownloadFormat(DataType.Text, "", "ColorCode", "ColorText"),
                        new DownloadFormat(DataType.Text, "", "SizeCode"),
                        new DownloadFormat(DataType.Number, "", "BeginQty"),
                        new DownloadFormat(DataType.Number, "", "BeginAmt"),
                        new DownloadFormat(DataType.Number, "", "PurQty"),
                        new DownloadFormat(DataType.Number, "", "PurAmt"),
                        new DownloadFormat(DataType.Number, "", "PurReturnQty"),
                        new DownloadFormat(DataType.Number, "", "PurReturnAmt"),
                        new DownloadFormat(DataType.Number, "", "SaleQty"),
                        new DownloadFormat(DataType.Number, "", "SaleAmt"),
                        new DownloadFormat(DataType.Number, "", "SaleReturnQty"),
                        new DownloadFormat(DataType.Number, "", "SaleReturnAmt"),
                        new DownloadFormat(DataType.Number, "", "UsedQty"),
                        new DownloadFormat(DataType.Number, "", "UsedAmt"),
                        new DownloadFormat(DataType.Number, "", "OtherInQty"),
                        new DownloadFormat(DataType.Number, "", "OtherInAmt"),
                        new DownloadFormat(DataType.Number, "", "CheckQty"),
                        new DownloadFormat(DataType.Number, "", "CheckAmt"),
                        new DownloadFormat(DataType.Number, "", "AdjustQty"),
                        new DownloadFormat(DataType.Number, "", "AdjustAmt"),
                        new DownloadFormat(DataType.Number, "", "ScrapQty"),
                        new DownloadFormat(DataType.Number, "", "ScrapAmt"),
                        new DownloadFormat(DataType.Number, "", "DiffAmt"),
                        new DownloadFormat(DataType.Number, "", "EndQty"),
                        new DownloadFormat(DataType.Number, "", "EndAmt")
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