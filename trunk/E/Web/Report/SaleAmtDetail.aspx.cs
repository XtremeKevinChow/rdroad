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

public partial class Report_SaleAmtDetail : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            this.cmdReturn1.Visible = false;
            this.cmdReturn2.Visible = false;

            this.txtDateFrom.Text = DateTime.Now.AddDays(-1).ToString("yyyy-MM-dd");
            this.txtDateTo.Text = DateTime.Now.ToString("yyyy-MM-dd");

            this.drpPeriod.Items.Clear();
            this.drpPeriod.Items.Add(new ListItem("　", "0"));
            this.drpLogis.Items.Clear();
            this.drpLogis.Items.Add(new ListItem("　", "0"));
            using (ISession session = new Session())
            {
                IList<INVPeriod> periods = INVPeriod.ClosedPeriods(session);
                foreach (INVPeriod p in periods)
                    this.drpPeriod.Items.Add(new ListItem(p.PeriodCode, p.PeriodID.ToString()));
                if (this.drpPeriod.Items.Count > 1)
                    this.drpPeriod.SelectedIndex = 1;
                IList<Logistics> logis = session.CreateEntityQuery<Logistics>()
                    .OrderBy("ShortName")
                    .List<Logistics>();
                foreach (Logistics lg in logis)
                    this.drpLogis.Items.Add(new ListItem(lg.ShortName, lg.LogisticCompID.ToString()));

                string mode = WebUtil.Param("mode");
                if (mode.Trim().ToLower() == "fix")
                {
                    //从销售款统计导航过来
                    this.cmdReturn1.Visible = true;
                    this.cmdReturn2.Visible = true;
                    this.cmdReturn1["Return"].NavigateUrl = this.cmdReturn2["Return"].NavigateUrl = WebUtil.Param("return");

                    this.drpPeriod.SelectedValue = WebUtil.ParamInt("pd", 0).ToString();
                    int logisId = WebUtil.ParamInt("lg", 0);
                    if (logisId < 0) logisId = 0;
                    this.drpLogis.SelectedValue = logisId.ToString();
                    DateTime dt;
                    dt = Cast.DateTime(WebUtil.Param("df"), new DateTime(1900, 1, 1));
                    if (dt > new DateTime(1900, 1, 1))
                        this.txtDateFrom.Text = dt.ToString("yyyy-MM-dd");
                    dt = Cast.DateTime(WebUtil.Param("dt"), new DateTime(1900, 1, 1));
                    if (dt > new DateTime(1900, 1, 1))
                        this.txtDateTo.Text = dt.ToString("yyyy-MM-dd");
                }

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
        int logisId = WebUtil.ParamInt("lg", 0);
        if (logisId != -99)
            logisId = Cast.Int(this.drpLogis.SelectedValue);

        int count = 0;
        this.repeater.DataSource = Report.SaleAmt(session, startDate, endDate, logisId
            , this.txtSNNumber.Text, this.txtSONumber.Text, this.txtShippingNumber.Text
            , pageIndex, pageSize, fetchRecordCount, ref count);
        this.repeater.DataBind();
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
            this.txtDateFrom.Text = helper.Pop("df");
            this.txtDateTo.Text = helper.Pop("dt");
            this.drpPeriod.SelectedValue = helper.Pop("pd");
            int logisId = Cast.Int(helper.Pop("lg"));
            if (logisId < 0) logisId = 0;
            this.drpLogis.SelectedValue = logisId.ToString();
            this.txtSNNumber.Text = helper.Pop("sn");
            this.txtSONumber.Text = helper.Pop("so");
        }
        this.QueryAndBindData(session, Cast.Int(helper.Pop("pi"), 1), Cast.Int(helper.Pop("ps"), this.magicPagerMain.PageSize), true);
    }
    private string GetReturnUrl()
    {
        QueryHelper helper = new QueryHelper(this);
        helper.Push("mode", WebUtil.Param("mode"));
        helper.Push("df", this.txtDateFrom.Text);
        helper.Push("dt", this.txtDateTo.Text);
        int logisId = WebUtil.ParamInt("lg", 0);
        if (logisId != -99)
            logisId = Cast.Int(this.drpLogis.SelectedValue);
        helper.Push("lg", logisId);
        helper.Push("pd", this.drpPeriod.SelectedValue);
        helper.Push("sn", this.txtSNNumber.Text);
        helper.Push("so", this.txtSONumber.Text);
        helper.Push("pi", this.magicPagerMain.CurrentPageIndex);
        helper.Push("ps", this.magicPagerMain.PageSize);
        helper.Push("return", WebUtil.escape(WebUtil.Param("return")));

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
            int logisId = WebUtil.ParamInt("lg", 0);
            if (logisId != -99)
                logisId = Cast.Int(this.drpLogis.SelectedValue);

            int count = 0;
            DataSet ds = Report.SaleAmt(session, startDate, endDate, logisId
                , this.txtSNNumber.Text, this.txtSONumber.Text, this.txtShippingNumber.Text
                , -1, 0, false, ref count);
            string fileName = DownloadUtil.DownloadXls("Sale_" + DateTime.Now.ToString("yyMMdd") + ".xls", "RPT_SALE_AMT_DTL_",
                new List<DownloadFormat>()
                    {
                        new DownloadFormat(DataType.Date, "发货日期", "TransDate"),
                        new DownloadFormat(DataType.NumberText, "发货单", "SNNumber"),
                        new DownloadFormat(DataType.NumberText, "订单", "SONumber"),
                        new DownloadFormat(DataType.NumberText, "运单号", "ShippingNumber"),
                        new DownloadFormat(DataType.Number, "成本金额", "CostAmt"),
                        new DownloadFormat(DataType.Number, "销售收入", "SaleAmt"),
                        new DownloadFormat(DataType.Number, "发送费", "TransportAmt"),
                        new DownloadFormat(DataType.Number, "包装费", "PackageAmt"),
                        new DownloadFormat(DataType.Number, "礼券抵扣", "CouponsAmt"),
                        new DownloadFormat(DataType.Number, "销售折扣", "DiscountAmt"),
                        new DownloadFormat(DataType.Number, "礼金支付", "EMoneyAmt"),
                        new DownloadFormat(DataType.Number, "帐户支付", "AccountReceivable"),
                        new DownloadFormat(DataType.Number, "POS机收款", "PosReceivable"),
                        new DownloadFormat(DataType.Number, "物流应收款", "LogisReceivable"),
                        new DownloadFormat(DataType.Number, "实际应收款", "ActualReceivable")
                    }, ds);
            this.frameDownload.Attributes["src"] = fileName;
            WebUtil.DownloadHack(this, "txtDateFrom", "txtDateTo");
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