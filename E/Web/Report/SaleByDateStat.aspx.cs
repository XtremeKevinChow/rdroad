using System;
using System.Collections.Generic;
using System.Data;
using System.Web.UI;
using Magic.ERP.Report;
using Magic.Framework.ORM;
using Magic.Framework.Utils;
using Magic.Web.UI;

public partial class Report_SaleByDateStat : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            this.txtDateFrom.Text = "2008-09-01";
            this.txtDateTo.Text = DateTime.Now.ToString("yyyy-MM-dd");

            using (ISession session = new Session())
            {
                this.QueryAndBindData(session, 1, this.magicPagerMain.PageSize, true);
            }
        }
        else this.frameDownload.Attributes["src"] = "about:blank;";
    }

    private void QueryAndBindData(ISession session, int pageIndex, int pageSize, bool fetchRecordCount)
    {
        DateTime startDate = Cast.DateTime(this.txtDateFrom.Text, new DateTime(1900, 1, 1));
        DateTime endDate = Cast.DateTime(this.txtDateTo.Text, new DateTime(1900, 1, 1));

        int count = 0;
        this.repeater.DataSource = Report.SaleByDateStat(session, startDate, endDate, pageIndex, pageSize, fetchRecordCount, ref count);
        this.repeater.DataBind();
        if (fetchRecordCount)
            this.magicPagerMain.RecordCount = this.magicPagerSub.RecordCount = count;
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
            DateTime startDate = Cast.DateTime(this.txtDateFrom.Text, new DateTime(1900, 1, 1));
            DateTime endDate = Cast.DateTime(this.txtDateTo.Text, new DateTime(1900, 1, 1));

            int count = 0;
            DataSet ds = Report.SaleByDateStat(session, startDate, endDate, -1, 0, false, ref count);
            string fileName = DownloadUtil.DownloadXls("Sale_ByDate_" + DateTime.Now.ToString("yyMMdd") + ".xls", "RPT_SALE_BYDATE_",
                new List<DownloadFormat>()
                    {
                        new DownloadFormat(DataType.Text, "日期", "OrdDate"),
                        new DownloadFormat(DataType.Number, "订单数", "OrdCount"),
                        new DownloadFormat(DataType.Number, "金额(元)", "OrdAmt"),
                        new DownloadFormat(DataType.Number, "销售件数合计", "LineCount")
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