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

public partial class Report_Stock_Sale_Diff : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            using (ISession session = new Session())
            {
                this.QueryAndBindData(session, 1, this.magicPagerMain.PageSize, true);
            }
        }
        else this.frameDownload.Attributes["src"] = "about:blank;";
    }

    private void QueryAndBindData(ISession session, int pageIndex, int pageSize, bool fetchRecordCount)
    {
        int count = 0;
        this.repeater.DataSource = Report.StockSaleDiff(session, Cast.Int(this.drpSort.SelectedValue), this.chkOnlyStoLack.Checked, pageIndex, pageSize, fetchRecordCount, ref count);
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
            int count = 0;
            DataSet ds = Report.StockSaleDiff(session, Cast.Int(this.drpSort.SelectedValue), this.chkOnlyStoLack.Checked, -1, 0, false, ref count);
            string fileName = DownloadUtil.DownloadXls("Stock_Sale_Diff_" + DateTime.Now.ToString("yyMMdd") + ".xls", "RPT_STO_SALE_DIFF_",
                new List<DownloadFormat>()
                    {
                        new DownloadFormat(DataType.NumberText, "SKU", "SKU"),
                        new DownloadFormat(DataType.NumberText, "货号", "ItemCode"),
                        new DownloadFormat(DataType.Text, "名称", "ItemName"),
                        new DownloadFormat(DataType.NumberText, "颜色", "ColorCode", "ColorText"),
                        new DownloadFormat(DataType.NumberText, "尺码", "SizeCode"),
                        new DownloadFormat(DataType.Number, "当前定购数量", "OrdQty"),
                        new DownloadFormat(DataType.Number, "暂存架待发数量", "TempQty"),
                        new DownloadFormat(DataType.Number, "库存数量", "StoQty"),
                        new DownloadFormat(DataType.Number, "缺货数量", "LackQty")
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