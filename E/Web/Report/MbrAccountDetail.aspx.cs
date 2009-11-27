using System;
using System.Collections.Generic;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data;
using Magic.Framework.Utils;
using Magic.Framework.ORM;
using Magic.Basis;
using Magic.ERP.CRM;
using Magic.ERP.Report;
using Magic.Web.UI;

public partial class Report_MbrAccountDetail : System.Web.UI.Page
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
            this.drpFlush.Items.Clear();
            this.drpFlush.Items.Add(new ListItem("　", "0"));
            this.drpPayment.Items.Clear();
            this.drpPayment.Items.Add(new ListItem("　", "0"));
            using (ISession session = new Session())
            {
                IList<INVPeriod> periods = INVPeriod.ClosedPeriods(session);
                foreach (INVPeriod p in periods)
                    this.drpPeriod.Items.Add(new ListItem(p.PeriodCode, p.PeriodID.ToString()));
                if (this.drpPeriod.Items.Count > 1)
                    this.drpPeriod.SelectedIndex = 1;
                IList<FlushType> flush = FlushType.EffectiveList(session);
                foreach (FlushType f in flush)
                    this.drpFlush.Items.Add(new ListItem(f.Name, f.ID.ToString()));
                IList<PaymentMethod> payment = PaymentMethod.EffectiveList(session);
                foreach (PaymentMethod p in payment)
                    this.drpPayment.Items.Add(new ListItem(p.Name, p.ID.ToString()));

                string mode = WebUtil.Param("mode");
                if (mode.Trim().ToLower() == "fix")
                {
                    //从帐户变动汇总导航过来
                    this.cmdReturn1.Visible = true;
                    this.cmdReturn2.Visible = true;
                    this.cmdReturn1["Return"].NavigateUrl = this.cmdReturn2["Return"].NavigateUrl = WebUtil.Param("return");

                    this.drpPeriod.SelectedValue = WebUtil.ParamInt("pd", 0).ToString();
                    this.drpFlush.SelectedValue = WebUtil.ParamInt("flush", 0).ToString();
                    this.drpPayment.SelectedValue = WebUtil.ParamInt("payment", 0).ToString();
                    DateTime dt;
                    dt = Cast.DateTime(WebUtil.Param("df"), new DateTime(1900, 1, 1));
                    if (dt > new DateTime(1900, 1, 1))
                        this.txtDateFrom.Text = dt.ToString("yyyy-MM-dd");
                    dt = Cast.DateTime(WebUtil.Param("dt"), new DateTime(1900, 1, 1));
                    if (dt > new DateTime(1900, 1, 1))
                        this.txtDateTo.Text = dt.ToString("yyyy-MM-dd");
                    this.txtMbrID.Text = WebUtil.Param("mbr");
                }

                this.QueryAndBindData(session, 1, this.magicPagerMain.PageSize, true);
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

        int count = 0;
        this.repeater.DataSource = Report.MbrAccountDetail(session, startDate, endDate
            , Cast.Int(this.drpFlush.SelectedValue), Cast.Int(this.drpPayment.SelectedValue)
            , this.txtOrder.Text, this.txtMbrID.Text, this.txtMbrName.Text, this.txtUser.Text
            , pageIndex, pageSize, fetchRecordCount, ref count);
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

            int count = 0;
            DataSet ds = Report.MbrAccountDetail(session, startDate, endDate
                , Cast.Int(this.drpFlush.SelectedValue), Cast.Int(this.drpPayment.SelectedValue)
                , this.txtOrder.Text, this.txtMbrID.Text, this.txtMbrName.Text, this.txtUser.Text
                , -1, 0, false, ref count);
            string fileName = DownloadUtil.DownloadXls("Member_Account_Detail_" + DateTime.Now.ToString("yyMMdd") + ".xls", "RPT_MBR_ACC_",
                new List<DownloadFormat>()
                    {
                        new DownloadFormat(DataType.Date, "日期", "FlushDate"),
                        new DownloadFormat(DataType.Text, "变动原因", "FlushType"),
                        new DownloadFormat(DataType.Text, "支付方式", "PaymentType"),
                        new DownloadFormat(DataType.NumberText, "会员号", "MbrNum"),
                        new DownloadFormat(DataType.Text, "姓名", "MbrName"),
                        new DownloadFormat(DataType.NumberText, "凭证号", "OrderNumber"),
                        new DownloadFormat(DataType.Number, "变动金额", "FlushAmt"),
                        new DownloadFormat(DataType.Text, "操作人", "UserName"),
                        new DownloadFormat(DataType.Text, "操作备注", "comments")
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