using System;
using System.Collections.Generic;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data;
using Magic.Framework.Data;
using Magic.Framework.Utils;
using Magic.Framework.ORM;
using Magic.Basis;
using Magic.ERP.Report;

public partial class Report_MbrAccountSum : System.Web.UI.Page
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

        if (periodId > 0)
        {
            DbSession dbsession = session.DbSession as DbSession;
            IDbCommand cmd = dbsession.CreateSqlStringCommand("select sum(begin_amt) as begin_amt,sum(end_amt) as end_amt from fi_rpt_cash_account_balance where pd_id=" + periodId.ToString());
            DataSet ds = dbsession.ExecuteDataSet(cmd);
            if (ds.Tables[0].Rows.Count > 0)
            {
                this.lblBegin.Text = RenderUtil.FormatNumber(Cast.Decimal(ds.Tables[0].Rows[0]["begin_amt"]), "#,##0.#0", "0.00");
                this.lblEnd.Text = RenderUtil.FormatNumber(Cast.Decimal(ds.Tables[0].Rows[0]["end_amt"]), "#,##0.#0", "0.00");
            }
        }

        this.repeater.DataSource = Report.MbrAccountSum(session, startDate, endDate);
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
        return helper.OutputReturnUrl();
    }
    protected void btnQuery_Click(object sender, ImageClickEventArgs e)
    {
        using (ISession session = new Session())
        {
            this.QueryAndBindData(session);
        }
    }
}