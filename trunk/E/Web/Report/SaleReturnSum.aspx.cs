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

public partial class Report_SaleReturnSum : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            this.txtDateFrom.Text = DateTime.Now.AddDays(-1).ToString("yyyy-MM-dd");
            this.txtDateTo.Text = DateTime.Now.ToString("yyyy-MM-dd");

            this.drpPeriod.Items.Clear();
            this.drpPeriod.Items.Add(new ListItem("　", "0"));
            this.drpReturnType.Items.Clear();
            this.drpReturnType.Items.Add(new ListItem("物流退货", "1"));
            this.drpReturnType.Items.Add(new ListItem("会员退货", "2"));
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

        this.repeater.DataSource = Report.SaleReturnSum(session, startDate, endDate, Cast.Int(this.drpReturnType.SelectedValue));
        this.repeater.DataBind();
        this.hidReturnUrl.Value = this.GetReturnUrl(startDate, endDate);
    }
    private void RestoreLastQuery(ISession session)
    {
        QueryHelper helper = new QueryHelper(this);
        if (helper.HasQueryParameter())
        {
            this.txtDateFrom.Text = helper.Pop("df");
            this.txtDateTo.Text = helper.Pop("dt");
            this.drpPeriod.SelectedValue = helper.Pop("pd");
            this.drpReturnType.SelectedValue = helper.Pop("rt");
        }
        this.QueryAndBindData(session);
    }
    private string GetReturnUrl(DateTime start, DateTime end)
    {
        QueryHelper helper = new QueryHelper(this);
        helper.Push("pd", this.drpPeriod.SelectedValue);
        helper.Push("df", this.txtDateFrom.Text.Trim());
        helper.Push("dt", this.txtDateTo.Text.Trim());
        helper.Push("rt", this.drpReturnType.SelectedValue);
        System.Text.StringBuilder url = new System.Text.StringBuilder();
        string rtType ="";
        if(this.drpReturnType.SelectedValue=="1") rtType="RC4";
        else if(this.drpReturnType.SelectedValue=="2") rtType="RC2";
        url.Append("SaleReturnQuery.aspx?mode=fix&flag=1&df=").Append(start.ToString("yyyy-MM-dd"))
            .Append("&dt=").Append(end.ToString("yyyy-MM-dd"))
            .Append("&rttype=").Append(rtType)
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

            DataSet ds = Report.SaleReturnSum(session, startDate, endDate, Cast.Int(this.drpReturnType.SelectedValue));
            string fileName = DownloadUtil.DownloadXls("Sale_Return_Sum_" + DateTime.Now.ToString("yyMMdd") + ".xls", "RPT_SALE_RTN_SUM_",
                new List<DownloadFormat>()
                    {
                        new DownloadFormat(DataType.Text, "物流公司", "LogisName"),
                        new DownloadFormat(DataType.Number, "销售收入", "SaleAmt"),
                        new DownloadFormat(DataType.Number, "发送费", "TransportAmt"),
                        new DownloadFormat(DataType.Number, "包装费", "PackageAmt"),
                        new DownloadFormat(DataType.Number, "礼券抵扣", "CouponsAmt"),
                        new DownloadFormat(DataType.Number, "销售折扣", "DiscountAmt"),
                        new DownloadFormat(DataType.Number, "退款合计", "OrderAmt"),
                        new DownloadFormat(DataType.Number, "退回礼金", "EMoneyAmt"),
                        new DownloadFormat(DataType.Number, "退回帐户", "AccountReceivable"),
                        new DownloadFormat(DataType.Number, "POS机收款", "PosReceivable"),
                        new DownloadFormat(DataType.Number, "物流应收款", "LogisReceivable")
                    }, ds);
            this.frameDownload.Attributes["src"] = fileName;
        }
        WebUtil.DownloadHack(this, "txtDateFrom", "txtDateTo");
    }
}