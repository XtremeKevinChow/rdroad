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

public partial class Report_SaleReturnQuery : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            this.txtDateFrom.Text = DateTime.Now.AddDays(-3).ToString("yyyy-MM-dd");
            this.txtDateTo.Text = DateTime.Now.ToString("yyyy-MM-dd");
            this.drpReturnType.Items.Clear();
            this.drpReturnType.Items.Add(new ListItem("��", ""));
            this.drpReturnType.Items.Add(new ListItem("��Ա�˻�", "RC2"));
            this.drpReturnType.Items.Add(new ListItem("�����˻�", "RC4"));

            if (WebUtil.Param("mode") == "fix")
            {
                this.cmdReturn1.Visible = true;
                this.cmdReturn2.Visible = true;
                this.cmdReturn1["Return"].NavigateUrl = WebUtil.Param("return");
                this.cmdReturn2["Return"].NavigateUrl = WebUtil.Param("return");
            }
            else
            {
                this.cmdReturn1.Visible = false;
                this.cmdReturn2.Visible = false;
            }

            using (ISession session = new Session())
            {
                this.LoadLogistics(session);
                this.RestoreLastQuery(session);
            }
        }
        else this.frameDownload.Attributes["src"] = "about:blank;";
    }
    private void LoadLogistics(ISession session)
    {
        this.drpLogis.Items.Clear();
        IList<Logistics> logis = session.CreateEntityQuery<Logistics>()
                .OrderBy("ShortName")
                .List<Logistics>();
        this.drpLogis.Items.Add(new ListItem("��", ""));
        foreach (Logistics lg in logis)
            this.drpLogis.Items.Add(new ListItem(lg.ShortName, lg.LogisticCompID.ToString()));
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
            WebUtil.ShowError(this, "�����뿪ʼʱ��");
            return;
        }
        int logisId = Cast.Int(this.drpLogis.SelectedValue, 0);
        int count = 0;
        DataSet ds = Report.SaleReturn(session, fromDate, toDate, this.drpReturnType.SelectedValue
            , logisId, this.txtRTNumber.Text, this.txtSNNumber.Text, this.txtSONumber.Text
            , pageIndex, pageSize, fetchRecordCount, ref count);

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
            this.drpLogis.SelectedValue = helper.Pop("lg");
            this.drpReturnType.SelectedValue = helper.Pop("rttype");
            this.txtRTNumber.Text = helper.Pop("rt");
            this.txtSNNumber.Text = helper.Pop("sn");
            this.txtSONumber.Text = helper.Pop("so");
            this.txtDateFrom.Text = helper.Pop("df");
            this.txtDateTo.Text = helper.Pop("dt");
        }
        this.QueryAndBindData(session, Cast.Int(helper.Pop("pi"), 1), Cast.Int(helper.Pop("ps"), this.magicPagerMain.PageSize), true);
    }

    private string GetReturnUrl()
    {
        QueryHelper helper = new QueryHelper(this);
        helper.Push("lg", this.drpLogis.SelectedValue);
        helper.Push("rttype", this.drpReturnType.SelectedValue);
        helper.Push("df", this.txtDateFrom.Text.Trim());
        helper.Push("dt", this.txtDateTo.Text.Trim());
        helper.Push("rt", this.txtRTNumber.Text.Trim());
        helper.Push("sn", this.txtSNNumber.Text.Trim());
        helper.Push("so", this.txtSONumber.Text.Trim());
        helper.Push("pi", this.magicPagerMain.CurrentPageIndex);
        helper.Push("ps", this.magicPagerMain.PageSize);
        helper.Push("return", WebUtil.escape(WebUtil.Param("return")));
        return helper.OutputReturnUrl();
    }

    protected void MagicItemCommand(object sender, MagicItemEventArgs e)
    {
        if (e.CommandName != "Download") return;

        using (ISession session = new Session())
        {
            try
            {
                DateTime fromDate = Cast.DateTime(this.txtDateFrom.Text.Trim(), new DateTime(1900, 1, 1));
                DateTime toDate = Cast.DateTime(this.txtDateTo.Text.Trim(), new DateTime(1900, 1, 1));
                if (fromDate == new DateTime(1900, 1, 1))
                {
                    WebUtil.ShowError(this, "�����뿪ʼʱ��");
                    return;
                }
                int logisId = Cast.Int(this.drpLogis.SelectedValue, 0);
                int count = 0;
                DataSet ds = Report.SaleReturn(session, fromDate, toDate, this.drpReturnType.SelectedValue
                    , logisId, this.txtRTNumber.Text, this.txtSNNumber.Text, this.txtSONumber.Text
                    , -1, 0, false, ref count);

                //���ص�Excel
                string fileName = DownloadUtil.DownloadXls("Sale_Return_" + DateTime.Now.ToString("yyMMdd") + ".xls", "RPT_RTN_SUM",
                    new List<DownloadFormat>()
                    {
                        new DownloadFormat(DataType.Text, "�˻�����", "inner_date"),
                        new DownloadFormat(DataType.Text, "�˻�����", "RTNumber"),
                        new DownloadFormat(DataType.NumberText, "��������", "SNNumber"),
                        new DownloadFormat(DataType.Text, "������", "SONumber"),
                        new DownloadFormat(DataType.Text, "�˵���", "ShippingNumber"),
                        new DownloadFormat(DataType.Text, "�˻�����", "OrderType"),
                        new DownloadFormat(DataType.Text, "������˾", "LogisName"),
                        new DownloadFormat(DataType.Number, "��������", "SaleAmt"),
                        new DownloadFormat(DataType.Number, "���ͷ�", "TransportAmt"),
                        new DownloadFormat(DataType.Number, "��װ��", "PackageAmt"),
                        new DownloadFormat(DataType.Number, "��ȯ�ֿ�", "CouponsAmt"),
                        new DownloadFormat(DataType.Number, "�����ۿ�", "DiscountAmt"),
                        new DownloadFormat(DataType.Number, "�˿�ϼ�", "ReturnAmt"),
                        new DownloadFormat(DataType.Number, "�˻����", "EMoneyAmt"),
                        new DownloadFormat(DataType.Number, "�˻��ʻ�", "AccountReceivable"),
                        new DownloadFormat(DataType.Number, "POS���տ�", "Posreceivable"),
                        new DownloadFormat(DataType.Number, "����Ӧ�տ�", "LogisReceivable")
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