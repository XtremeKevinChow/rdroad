using System;
using System.Collections.Generic;
using System.Data;
using System.Web.UI;
using Magic.ERP.Report;
using Magic.Framework.ORM;
using Magic.Framework.Utils;
using Magic.Web.UI;

public partial class Report_SaleByItemCode : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            this.txtDateFrom.Text = DateTime.Now.ToString("yyyy-MM-dd");

            using (ISession session = new Session())
            {
                this.RestoreLastQuery(session);
            }
        }
        else this.frameDownload.Attributes["src"] = "about:blank;";
    }

    private void QueryAndBindData(ISession session, int pageIndex, int pageSize, bool fetchRecordCount)
    {
        DateTime startDate = Cast.DateTime(this.txtDateFrom.Text, new DateTime(1900, 1, 1));

        int count = 0;
        this.repeater.DataSource = Report.SaleByItemCode(session, startDate, pageIndex, pageSize, fetchRecordCount, ref count);
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
            this.txtDateFrom.Text = helper.Pop("df");
        this.QueryAndBindData(session, Cast.Int(helper.Pop("pi"), 1), Cast.Int(helper.Pop("ps"), this.magicPagerMain.PageSize), true);
    }
    private string GetReturnUrl()
    {
        QueryHelper helper = new QueryHelper(this);
        helper.Push("df", this.txtDateFrom.Text);
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
            DateTime startDate = Cast.DateTime(this.txtDateFrom.Text, new DateTime(1900, 1, 1));

            int count = 0;
            DataSet ds = Report.SaleByItemCode(session, startDate, -1, 0, false, ref count);
            string fileName = DownloadUtil.DownloadXls("Sale_ByItem_" + DateTime.Now.ToString("yyMMdd") + ".xls", "RPT_SALE_BYITM_",
                new List<DownloadFormat>()
                    {
                        new DownloadFormat(DataType.NumberText, "货号", "ItemCode"),
                        new DownloadFormat(DataType.Text, "名称", "ItemName"),
                        new DownloadFormat(DataType.Number, "销售数量", "SaleQty"),
                        new DownloadFormat(DataType.Number, "销售金额", "SaleAmt"),
                        new DownloadFormat(DataType.Number, "退回数量", "RtnQty"),
                        new DownloadFormat(DataType.Number, "退回金额", "RtnAmt")
                    }, ds);
            this.frameDownload.Attributes["src"] = fileName;
        }
        WebUtil.DownloadHack(this, "txtDateFrom", null);
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