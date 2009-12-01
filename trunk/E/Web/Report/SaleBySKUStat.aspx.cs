using System;
using System.Collections.Generic;
using System.Data;
using System.Web.UI;
using Magic.ERP.Report;
using Magic.Framework.ORM;
using Magic.Framework.Utils;
using Magic.Web.UI;

public partial class Report_SaleBySKUStat : System.Web.UI.Page
{
    public string ToPercent(object val)
    {
        decimal decValue = Cast.Decimal(val);
        if (decValue == 0M) return "&nbsp;";
        if (decValue == 999999M) return "--";
        return decValue.ToString("#0.#0") + "%";
    }
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
        this.repeater.DataSource = Report.SaleBySKUStat(session, startDate, endDate, this.txtItemCode.Text, Cast.Enum<Report_SaleByCode_OrderBy>(this.drpSort.SelectedValue), this.chkIncludeNoSale.Checked, pageIndex, pageSize, fetchRecordCount, ref count);
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
            DataSet ds = Report.SaleBySKUStat(session, startDate, endDate, this.txtItemCode.Text, Cast.Enum<Report_SaleByCode_OrderBy>(this.drpSort.SelectedValue), this.chkIncludeNoSale.Checked, -1, 0, false, ref count);
            string fileName = DownloadUtil.DownloadXls("Sale_BySKU_" + DateTime.Now.ToString("yyMMdd") + ".xls", "RPT_SALE_BYSKU_",
                new List<DownloadFormat>()
                    {
                        new DownloadFormat(DataType.NumberText, "货号", "ItemCode"),
                        new DownloadFormat(DataType.Text, "名称", "ItemName"),
                        new DownloadFormat(DataType.Text, "颜色", "ColorCode", "ColorText"),
                        new DownloadFormat(DataType.Text, "尺码", "SizeCode"),
                        new DownloadFormat(DataType.Number, "销售量", "SaleQty"),
                        new DownloadFormat(DataType.Number, "销售金额", "SaleAmt"),
                        new DownloadFormat(DataType.Number, "总采购", "PurQty"),
                        new DownloadFormat(DataType.Number, "销售率(%)", "SaleRate"),
                        new DownloadFormat(DataType.Number, "换货量", "ExcgQty"),
                        new DownloadFormat(DataType.Number, "换货率(%)", "ExcgRate"),
                        new DownloadFormat(DataType.Number, "退货量", "RtnQty"),
                        new DownloadFormat(DataType.Number, "退货率(%)", "RtnRate"),
                        new DownloadFormat(DataType.Number, "现有库存", "StoQty")
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