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

public partial class Report_StockBalanceSum : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            this.drpPeriod.Items.Clear();
            using (ISession session = new Session())
            {
                IList<INVPeriod> periods = INVPeriod.ClosedPeriods(session);
                foreach (INVPeriod p in periods)
                    this.drpPeriod.Items.Add(new ListItem(p.PeriodCode, p.PeriodID.ToString()));
                this.QueryAndBindData(session);
            }
        }
        else this.frameDownload.Attributes["src"] = "about:blank;";
    }

    private void QueryAndBindData(ISession session)
    {
        int periodId = Cast.Int(this.drpPeriod.SelectedValue, 0);
        if (periodId <= 0) return;

        this.repeater.DataSource = Report.StockBalanceSum(session, periodId);
        this.repeater.DataBind();
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
            int periodId = Cast.Int(this.drpPeriod.SelectedValue, 0);
            if (periodId <= 0) return;

            DataSet ds = Report.StockBalanceSum(session, periodId);
            string fileName = DownloadUtil.DownloadXls("Inv_Balance_Sum_" + DateTime.Now.ToString("yyMMdd") + ".xls"
                , "RPT_INV_SUM", Server.MapPath("/Template/RPT_Stock_Balance_Download.xls"), null, 3
                , new List<DownloadFormat>()
                    {
                        new DownloadFormat(DataType.Text, "", "CatName"),
                        new DownloadFormat(DataType.Number, "", "BeginQty"),
                        new DownloadFormat(DataType.Number, "", "BeginAmt"),
                        new DownloadFormat(DataType.Number, "", "PurQty"),
                        new DownloadFormat(DataType.Number, "", "PurAmt"),
                        new DownloadFormat(DataType.Number, "", "PurReturnQty"),
                        new DownloadFormat(DataType.Number, "", "PurReturnAmt"),
                        new DownloadFormat(DataType.Number, "", "SaleQty"),
                        new DownloadFormat(DataType.Number, "", "SaleAmt"),
                        new DownloadFormat(DataType.Number, "", "SaleReturnQty"),
                        new DownloadFormat(DataType.Number, "", "SaleReturnAmt"),
                        new DownloadFormat(DataType.Number, "", "UsedQty"),
                        new DownloadFormat(DataType.Number, "", "UsedAmt"),
                        new DownloadFormat(DataType.Number, "", "OtherInQty"),
                        new DownloadFormat(DataType.Number, "", "OtherInAmt"),
                        new DownloadFormat(DataType.Number, "", "CheckQty"),
                        new DownloadFormat(DataType.Number, "", "CheckAmt"),
                        new DownloadFormat(DataType.Number, "", "AdjustQty"),
                        new DownloadFormat(DataType.Number, "", "AdjustAmt"),
                        new DownloadFormat(DataType.Number, "", "ScrapQty"),
                        new DownloadFormat(DataType.Number, "", "ScrapAmt"),
                        new DownloadFormat(DataType.Number, "", "DiffAmt"),
                        new DownloadFormat(DataType.Number, "", "EndQty"),
                        new DownloadFormat(DataType.Number, "", "EndAmt")
                    }, ds);
            this.frameDownload.Attributes["src"] = fileName;
        }
    }
}
