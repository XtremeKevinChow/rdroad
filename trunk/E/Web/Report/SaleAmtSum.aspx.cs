using System;
using System.Collections.Generic;
using System.Data;
using System.Web.UI;
using System.Web.UI.WebControls;
using Magic.Basis;
using Magic.ERP.Report;
using Magic.Framework.ORM;
using Magic.Framework.Utils;
using Magic.Web.UI;

public partial class Report_SaleAmtSum : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            this.txtDateFrom.Text = DateTime.Now.AddDays(-1).ToString("yyyy-MM-dd");
            this.txtDateTo.Text = DateTime.Now.ToString("yyyy-MM-dd");

            this.drpPeriod.Items.Clear();
            this.drpPeriod.Items.Add(new ListItem("　", "0"));
            using (ISession session = new Session())
            {
                IList<INVPeriod> periods = INVPeriod.ClosedPeriods(session);
                foreach (INVPeriod p in periods)
                    this.drpPeriod.Items.Add(new ListItem(p.PeriodCode, p.PeriodID.ToString()));
                if (this.drpPeriod.Items.Count > 1)
                    this.drpPeriod.SelectedIndex = 1;
                this.RestoreLastQuery(session);
            }
        }
        else this.frameDownload.Attributes["src"] = "about:blank;";
    }

    private void QueryAndBindData(ISession session)
    {
        DateTime defaultDate = new DateTime(1900, 1, 1);
        DateTime startDate = Cast.DateTime(this.txtDateFrom.Text, defaultDate);
        DateTime endDate = Cast.DateTime(this.txtDateTo.Text, defaultDate);
        //如果选择了库存期间，使用库存期间的起始日期
        int periodId = Cast.Int(this.drpPeriod.SelectedValue, 0);
        if (periodId > 0)
        {
            INVPeriod period = INVPeriod.Retrieve(session, periodId);
            if (period != null)
            {
                startDate = period.StartingDate;
                endDate = period.EndDate;
            }
        }
        if (startDate <= defaultDate || endDate <= defaultDate)
        {
            WebUtil.ShowError(this, "请选择时间范围或库存期间");
            return;
        }

        this.repeater.DataSource = Report.SaleAmtSum(session, startDate, endDate);
        this.repeater.DataBind();
        this.hidReturnUrl.Value = this.GetReturnUrl();
    }
    private void RestoreLastQuery(ISession session)
    {
        QueryHelper helper = new QueryHelper(this);
        if (helper.HasQueryParameter())
        {
            this.txtDateFrom.Text = helper.Pop("df");
            this.txtDateTo.Text = helper.Pop("dt");
            this.drpPeriod.SelectedValue = helper.Pop("pd");
        }
        this.QueryAndBindData(session);
    }
    private string GetReturnUrl()
    {
        QueryHelper helper = new QueryHelper(this);
        helper.Push("pd", this.drpPeriod.SelectedValue);
        helper.Push("df", this.txtDateFrom.Text.Trim());
        helper.Push("dt", this.txtDateTo.Text.Trim());
        System.Text.StringBuilder url = new System.Text.StringBuilder();
        url.Append("SaleAmtDetail.aspx?mode=fix&df=").Append(this.txtDateFrom.Text.Trim())
            .Append("&dt=").Append(this.txtDateTo.Text.Trim())
            .Append("&pd=").Append(this.drpPeriod.SelectedValue)
            .Append("&return=").Append(WebUtil.escape(helper.OutputReturnUrl()));
        return url.ToString();
    }
    protected void btnQuery_Click(object sender, ImageClickEventArgs e)
    {
        using (ISession session = new Session())
        {
            this.QueryAndBindData(session);
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
            //如果选择了库存期间，使用库存期间的起始日期
            int periodId = Cast.Int(this.drpPeriod.SelectedValue, 0);
            if (periodId > 0)
            {
                INVPeriod period = INVPeriod.Retrieve(session, periodId);
                if (period != null)
                {
                    startDate = period.StartingDate;
                    endDate = period.EndDate;
                }
            }
            if (startDate <= defaultDate || endDate <= defaultDate)
            {
                WebUtil.ShowError(this, "请选择时间范围或库存期间");
                return;
            }

            DataSet ds = Report.SaleAmtSum(session, startDate, endDate);
            string fileName = DownloadUtil.DownloadXls("Sale_Sum_" + DateTime.Now.ToString("yyMMdd") + ".xls", "RPT_SALE_AMT_SUM_",
                new List<DownloadFormat>()
                    {
                        new DownloadFormat(DataType.Text, "物流公司", "LogisName"),
                        new DownloadFormat(DataType.Number, "销售收入", "SaleAmt"),
                        new DownloadFormat(DataType.Number, "发送费", "TransportAmt"),
                        new DownloadFormat(DataType.Number, "包装费", "PackageAmt"),
                        new DownloadFormat(DataType.Number, "礼券抵扣", "CouponsAmt"),
                        new DownloadFormat(DataType.Number, "销售折扣", "DiscountAmt"),
                        new DownloadFormat(DataType.Number, "订单金额合计", "OrderAmt"),
                        new DownloadFormat(DataType.Number, "礼金支付", "EMoneyAmt"),
                        new DownloadFormat(DataType.Number, "帐户支付", "AccountReceivable"),
                        new DownloadFormat(DataType.Number, "POS机收款", "PosReceivable"),
                        new DownloadFormat(DataType.Number, "物流应收款", "LogisReceivable")
                    }, ds);
            this.frameDownload.Attributes["src"] = fileName;
        }
        WebUtil.DownloadHack(this, "txtDateFrom", "txtDateTo");
    }
}