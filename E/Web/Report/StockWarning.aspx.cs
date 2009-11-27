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

public partial class Report_StockWarning : System.Web.UI.Page
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
        this.repeater.DataSource = Report.StockWarning(session, pageIndex, pageSize, fetchRecordCount, ref count);
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
            int count=0;
            DataSet ds = Report.StockWarning(session, -1, 0, false, ref count);
            string fileName = DownloadUtil.DownloadXls("Stock_Warn_" + DateTime.Now.ToString("yyMMdd") + ".xls", "RPT_STO_WARN_",
                new List<DownloadFormat>()
                    {
                        new DownloadFormat(DataType.NumberText, "SKU", "SKU"),
                        new DownloadFormat(DataType.NumberText, "货号", "ItemCode"),
                        new DownloadFormat(DataType.Text, "名称", "ItemName"),
                        new DownloadFormat(DataType.NumberText, "颜色", "ColorCode", "ColorText"),
                        new DownloadFormat(DataType.Text, "尺码", "SizeCode"),
                        new DownloadFormat(DataType.Number, "Max.", "MaxQty"),
                        new DownloadFormat(DataType.Number, "库存量", "StoQty"),
                        new DownloadFormat(DataType.Number, "Safe.", "SafeQty"),
                        new DownloadFormat(DataType.Number, "Min.", "MinQty")
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
    public string ClassName(object warnType, int position)
    {
        int wt = Cast.Int(warnType, 0);
        //warn type:  1: 超过max   2: 小于安全库存   3: 小于最小库存
        //position: 1 最大库存, 2 库存量, 3 安全库存, 4 最小库存
        if (wt == 1 && (position == 1 || position == 2)) return "#CCCC00";
        if (wt == 2 && (position == 2 || position == 3)) return "#FFDD99";
        if (wt == 3 && (position == 2 || position == 4)) return "#FF3300";
        return "";
    }
    public string GetTitle(object warnType, int position)
    {
        int wt = Cast.Int(warnType, 0);
        switch (wt)
        {
            case 1: if (position == 1 || position == 2) return "超过最大库存量Max."; return "";
            case 2: if (position == 2 || position == 3) return "小于安全库存量Safe."; return "";
            case 3: if (position == 2 || position == 4) return "小于最小库存量Min."; return "";
            default: return "";
        }
    }
}