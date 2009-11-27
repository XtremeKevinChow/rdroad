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

public partial class Report_GiftSale : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            this.txtDateFrom.Text = DateTime.Now.AddDays(-1).ToString("yyyy-MM-dd");
            this.txtDateTo.Text = DateTime.Now.ToString("yyyy-MM-dd");

            using (ISession session = new Session())
            {
                this.RestoreLastQuery(session);
            }
        }
        else this.frameDownload.Attributes["src"] = "about:blank;";
    }

    private void QueryAndBindData(ISession session, int pageIndex, int pageSize, bool fetchRecordCount)
    {
        DateTime defaultDate = new DateTime(1900, 1, 1);
        DateTime startDate = Cast.DateTime(this.txtDateFrom.Text, defaultDate);
        DateTime endDate = Cast.DateTime(this.txtDateTo.Text, defaultDate);

        int count = 0;
        this.repeater.DataSource = Report.GiftSale(session, startDate, endDate, pageIndex, pageSize, fetchRecordCount, ref count);
        this.repeater.DataBind();
        if (fetchRecordCount)
            this.magicPagerMain.RecordCount = this.magicPagerSub.RecordCount = count;
        WebUtil.SetMagicPager(magicPagerMain, pageSize, pageIndex);
        WebUtil.SetMagicPager(magicPagerSub, pageSize, pageIndex);
    }
    private void RestoreLastQuery(ISession session)
    {
        QueryHelper helper = new QueryHelper(this);
        if (helper.HasQueryParameter())
        {
            this.txtDateFrom.Text = helper.Pop("df");
            this.txtDateTo.Text = helper.Pop("dt");
        }
        this.QueryAndBindData(session, Cast.Int(helper.Pop("pi"), 1), Cast.Int(helper.Pop("ps"), this.magicPagerMain.PageSize), true);
    }
    private string GetReturnUrl()
    {
        QueryHelper helper = new QueryHelper(this);
        helper.Push("df", this.txtDateFrom.Text);
        helper.Push("dt", this.txtDateTo.Text);
        return helper.OutputReturnUrl();
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
            DateTime startDate = Cast.DateTime(this.txtDateFrom.Text, defaultDate);
            DateTime endDate = Cast.DateTime(this.txtDateTo.Text, defaultDate);

            int count=0;
            DataSet ds = Report.GiftSale(session, startDate, endDate, -1, 0, false, ref count);
            string fileName = DownloadUtil.DownloadXls("Gift_Sale_" + DateTime.Now.ToString("yyMMdd") + ".xls", "RPT_GIFT_SALE_",
                new List<DownloadFormat>()
                    {
                        new DownloadFormat(DataType.NumberText, "SKU", "BarCode"),
                        new DownloadFormat(DataType.NumberText, "货号", "ItemCode"),
                        new DownloadFormat(DataType.Text, "名称", "ItemName"),
                        new DownloadFormat(DataType.NumberText, "颜色", "ColorCode", "ColorText"),
                        new DownloadFormat(DataType.Text, "尺码", "SizeCode"),
                        new DownloadFormat(DataType.Number, "数量", "Qty"),
                        new DownloadFormat(DataType.Number, "平均成本", "AvgPrice"),
                        new DownloadFormat(DataType.Number, "成本金额", "CostAmt")
                    }, ds);
            this.frameDownload.Attributes["src"] = fileName;
        }
        WebUtil.DownloadHack(this, "txtDateFrom", "txtDateTo");
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