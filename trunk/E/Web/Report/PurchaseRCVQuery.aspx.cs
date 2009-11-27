using System;
using System.Collections.Generic;
using System.Data;
using System.Web.UI;
using System.Web.UI.WebControls;
using Magic.Framework.Utils;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Query;
using Magic.ERP.Report;
using Magic.Basis;
using Magic.Web.UI;

public partial class Report_PurchaseRCVQueryLine : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            this.txtDateFrom.Text = DateTime.Now.AddDays(-3).ToString("yyyy-MM-dd");
            this.txtDateTo.Text = DateTime.Now.ToString("yyyy-MM-dd");

            using (ISession session = new Session())
            {
                this.LoadVendor(session);
                this.RestoreLastQuery(session);
            }
        }
        else this.frameDownload.Attributes["src"] = "about:blank;";
    }
    private void LoadVendor(ISession session)
    {
        this.drpVendor.Items.Clear();
        IList<Vendor> vendors = session.CreateEntityQuery<Vendor>()
            .Where(Exp.Eq("Status", VendorStatus.Enable))
            .OrderBy("ShortName")
            .List<Vendor>();
        this.drpVendor.Items.Add(new ListItem("　", ""));
        foreach (Vendor ven in vendors)
            this.drpVendor.Items.Add(new ListItem(ven.ShortName, ven.VendorID.ToString()));
    }

    protected void MagicPager_PageChanged(object src, Magic.Web.UI.PageChangedEventArgs e)
    {
        this.magicPagerSub.PageSize = this.magicPagerMain.PageSize;
        using (ISession session = new Session())
        {
            this.QueryAndBindData(session, e.NewPageIndex, e.Pager.PageSize, false);
        }
    }

    protected void btnQuery_Click(object sender, ImageClickEventArgs e)
    {
        using (ISession session = new Session())
        {
            this.QueryAndBindData(session, 1, magicPagerMain.PageSize, true);
        }
    }

    private void QueryAndBindData(ISession session, int pageIndex, int pageSize, bool fetchRecordCount)
    {
        DateTime fromDate = Cast.DateTime(this.txtDateFrom.Text.Trim(), new DateTime(1900, 1, 1));
        DateTime toDate = Cast.DateTime(this.txtDateTo.Text.Trim(), new DateTime(1900, 1, 1));
        if (fromDate == new DateTime(1900, 1, 1))
        {
            WebUtil.ShowError(this, "请输入开始时间");
            return;
        }
        int vendorId = Cast.Int(this.drpVendor.SelectedValue, 0);
        int count = 0;
        DataSet ds = Report.PurchaseReceiveSum(session, vendorId, this.txtRcvNumber.Text, this.txtPONumber.Text
            , fromDate, toDate, pageSize, pageIndex, fetchRecordCount, ref count);

        this.repeatControl.DataSource = ds;
        this.repeatControl.DataBind();
        if (fetchRecordCount)
            this.magicPagerMain.RecordCount = this.magicPagerSub.RecordCount = count;
        WebUtil.SetMagicPager(magicPagerMain, pageSize, pageIndex);
        WebUtil.SetMagicPager(magicPagerSub, pageSize, pageIndex);
        this.hidReturnUrl.Value = this.GetReturnUrl();
    }

    private void RestoreLastQuery(ISession session)
    {
        QueryHelper helper = new QueryHelper(this);
        if (helper.HasQueryParameter())
        {
            this.drpVendor.SelectedValue = helper.Pop("ven");
            this.txtRcvNumber.Text = helper.Pop("onum");
            this.txtDateFrom.Text = helper.Pop("df");
            this.txtDateTo.Text = helper.Pop("dt");
            this.txtPONumber.Text = helper.Pop("po");
        }
        this.QueryAndBindData(session, Cast.Int(helper.Pop("pi"), 1), Cast.Int(helper.Pop("ps"), this.magicPagerMain.PageSize), true);
    }

    private string GetReturnUrl()
    {
        QueryHelper helper = new QueryHelper(this);
        helper.Push("ven", this.drpVendor.SelectedValue);
        helper.Push("onum", this.txtRcvNumber.Text.Trim());
        helper.Push("df", this.txtDateFrom.Text.Trim());
        helper.Push("dt", this.txtDateTo.Text.Trim());
        helper.Push("po", this.txtPONumber.Text.Trim());
        helper.Push("pi", this.magicPagerMain.CurrentPageIndex);
        helper.Push("ps", this.magicPagerMain.PageSize);
        return helper.OutputReturnUrl();
    }

    protected void MagicItemCommand(object sender, MagicItemEventArgs e)
    {
        if (e.CommandName != "Download") return;

        using (ISession session = new Session())
        {
            try
            {
                //获取数据
                DateTime fromDate = Cast.DateTime(this.txtDateFrom.Text.Trim(), new DateTime(1900, 1, 1));
                DateTime toDate = Cast.DateTime(this.txtDateTo.Text.Trim(), new DateTime(1900, 1, 1));
                if (fromDate == new DateTime(1900, 1, 1))
                {
                    WebUtil.ShowError(this, "请输入开始时间");
                    return;
                }
                DataSet ds = Report.PurchaseReceiveDetail(session, Cast.Int(this.drpVendor.SelectedValue)
                    , this.txtRcvNumber.Text, this.txtPONumber.Text, fromDate, toDate);

                //下载到Excel
                string fileName = DownloadUtil.DownloadXls("RCV_Detail_" + DateTime.Now.ToString("yyMMdd") + ".xls", "RPT_RCV_DTL",
                    new List<DownloadFormat>()
                    {
                        new DownloadFormat(DataType.Text, "收货单", "RCVNumber"),
                        new DownloadFormat(DataType.Text, "采购单", "PONumber"),
                        new DownloadFormat(DataType.NumberText, "行号", "RCVLine"),
                        new DownloadFormat(DataType.NumberText, "PO行号", "POLine"),
                        new DownloadFormat(DataType.NumberText, "SKU", "BarCode"),
                        new DownloadFormat(DataType.NumberText, "货号", "ItemCode"),
                        new DownloadFormat(DataType.Text, "商品名称", "ItemName"),
                        new DownloadFormat(DataType.Text, "颜色", "ColorCode", "ColorText"),
                        new DownloadFormat(DataType.Text, "尺码", "SizeCode"),
                        new DownloadFormat(DataType.Number, "收货数量", "RCVQty"),
                        new DownloadFormat(DataType.Number, "单价", "Price"),
                        new DownloadFormat(DataType.Number, "金额", "RCVAmt")
                    }, ds);
                this.frameDownload.Attributes["src"] = fileName;
                WebUtil.DownloadHack(this, "txtDateFrom", "txtDateTo");
            }
            catch (Exception er)
            {
                WebUtil.ShowError(this, er);
            }
        }
    }
}