using System;
using System.Collections.Generic;
using System.Data;
using System.Web.UI;
using Magic.ERP.Report;
using Magic.Framework.ORM;
using Magic.Framework.Utils;
using Magic.Web.UI;

public partial class Report_SaleByDelivery : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            this.txtDateFrom.Text = "2008-09-01";
            this.txtDateTo.Text = DateTime.Now.ToString("yyyy-MM-dd");
            using (ISession session = new Session())
            {
                this.QueryAndBindData(session);
            }
        }
        else this.frameDownload.Attributes["src"] = "about:blank;";
    }

    private void QueryAndBindData(ISession session)
    {
        DateTime from = Cast.DateTime(this.txtDateFrom.Text), to = Cast.DateTime(this.txtDateTo.Text);
        this.repeater.DataSource = Report.SaleByDeliveryType(session, from, to);
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
            DateTime from = Cast.DateTime(this.txtDateFrom.Text), to = Cast.DateTime(this.txtDateTo.Text);
            DataSet ds = Report.SaleByDeliveryType(session, from, to);
            string fileName = DownloadUtil.DownloadXls("Sale_By_Delivery_" + DateTime.Now.ToString("yyMMdd") + ".xls", "Sale_By_Delivery",
                new List<DownloadFormat>()
                    {
                        new DownloadFormat(DataType.Text, "发送方式", "DlvName"),
                        new DownloadFormat(DataType.Number, "订单总数量", "TotalCount"),
                        new DownloadFormat(DataType.Text, "所占比例", "CountText"),
                        new DownloadFormat(DataType.Number, "订单总金额", "TotalAmt"),
                        new DownloadFormat(DataType.Text, "所占比例", "AmtText")
                    }, ds);
            this.frameDownload.Attributes["src"] = fileName;
        }
    }
}