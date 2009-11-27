using System;
using System.Collections.Generic;
using System.Web.UI;
using System.Web.UI.WebControls;
using Magic.Framework.Utils;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Query;
using Magic.ERP;
using Magic.ERP.Orders;
using Magic.Basis;

public partial class SaleDelivery_DeliveryQuery : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            this.txtDateFrom.Text = DateTime.Now.AddMonths(-1).ToString("yyyy-MM-dd");
            this.txtDateTo.Text = DateTime.Now.ToString("yyyy-MM-dd");
            this.chkDistributing.Checked = true;
            this.chkChecked.Checked = true;
            this.chkPackaged.Checked = true;

            using (ISession session = new Session())
            {
                SNStatus status = SNStatus.Retrieve(session, (int)CRMSNStatus.Distributing);
                if (status != null) this.chkDistributing.Text = status.Name;
                status = SNStatus.Retrieve(session, (int)CRMSNStatus.Checked);
                if (status != null) this.chkChecked.Text = status.Name;
                status = SNStatus.Retrieve(session, (int)CRMSNStatus.Packaged);
                if (status != null) this.chkPackaged.Text = status.Name;
                status = SNStatus.Retrieve(session, (int)CRMSNStatus.Interchanged);
                if (status != null) this.chkInterchanged.Text = status.Name;
                status = SNStatus.Retrieve(session, (int)CRMSNStatus.Return);
                if (status != null) this.chkReturn.Text = status.Name;
                status = SNStatus.Retrieve(session, (int)CRMSNStatus.PartExchange);
                if (status != null) this.chkPartReturn.Text = status.Name;

                this.drpLogis.Items.Clear();
                this.drpLogis.Items.Add(new ListItem("¡¡", "0"));
                IList<Logistics> logis = Logistics.GetEffectiveLogistics(session);
                foreach (Logistics l in logis)
                    this.drpLogis.Items.Add(new ListItem(l.ShortName, l.LogisticCompID.ToString()));

                WebUtil.SetMagicPager(magicPagerMain, this.magicPagerMain.PageSize, 1);
                WebUtil.SetMagicPager(magicPagerSub, this.magicPagerMain.PageSize, 1);
                this.RestoreLastQuery(session);
            }
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

    protected void btnQuery_Click(object sender, ImageClickEventArgs e)
    {
        using (ISession session = new Session())
        {
            this.QueryAndBindData(session, 1, magicPagerMain.PageSize, true);
        }
    }

    public string FormatInvoiceStatus(object val)
    {
        string s = Cast.String(val).Trim();
        return s.Length > 0 ? "ÒÑ¿ªÆ±" : "&nbsp;";
    }
    public string DistrictString(object pro, object city)
    {
        return Cast.String(pro).Trim() + "&nbsp;" + Cast.String(city).Trim();
    }

    private void QueryAndBindData(ISession session, int pageIndex, int pageSize, bool fetchRecordCount)
    {
        ObjectQuery query = session.CreateObjectQuery(@"
select sn.OrderNumber as OrderNumber,sn.SaleOrderNumber as SaleOrderNumber,sn.InvoiceNumber as InvoiceNumber,sn.ShippingNumber as ShippingNumber
    ,st.Name as StatusText,l.ShortName as LogisName
    ,sn.CreateTime as CreateTime,m.Name as MemberName,sn.Contact as Contact,sn.Province as Province,sn.City as City
    ,sn.PackageDate as PackageDate,so.ReleaseDate as OrdReleaseDate,sn.PrintDate as PrintDate
from CRMSN sn
inner join SOHead so on sn.SaleOrderID=so.ID
left join Member m on sn.MemberID=m.MemberID
left join SNStatus st on st.ID=sn.Status
left join Logistics l on l.LogisticCompID=sn.LogisticsID
order by sn.CreateTime")
            .Attach(typeof(CRMSN)).Attach(typeof(Member)).Attach(typeof(SNStatus)).Attach(typeof(Logistics))
            .Attach(typeof(SOHead))
            .Where(Exp.In("sn.Status", CRMSNStatus.Distributing, CRMSNStatus.Checked, CRMSNStatus.Packaged, CRMSNStatus.Interchanged, CRMSNStatus.Return, CRMSNStatus.PartExchange))
            .SetPage(pageIndex, pageSize);
        if (this.txtSDNumber.Text.Trim().Length > 0)
            query.And(Exp.Like("sn.OrderNumber", "%" + this.txtSDNumber.Text.Trim() + "%"));
        if (this.txtSONumber.Text.Trim().Length > 0)
            query.And(Exp.Like("sn.SaleOrderNumber", "%" + this.txtSONumber.Text.Trim() + "%"));
        if (this.txtInvoice.Text.Trim().Length > 0)
            query.And(Exp.Like("sn.InvoiceNumber", "%" + this.txtInvoice.Text.Trim() + "%"));
        if (this.txtShippingNumber.Text.Trim().Length > 0)
            query.And(Exp.Like("sn.ShippingNumber", "%" + this.txtShippingNumber.Text.Trim() + "%"));
        int logisId = Cast.Int(this.drpLogis.SelectedValue);
        if (logisId > 0)
            query.And(Exp.Eq("sn.LogisticsID", logisId));
        DateTime fromDate = Cast.DateTime(this.txtDateFrom.Text.Trim(), new DateTime(1900, 1, 1));
        DateTime toDate = Cast.DateTime(this.txtDateTo.Text.Trim(), new DateTime(1900, 1, 1));
        if (fromDate > new DateTime(1900, 1, 1)) query.And(Exp.Ge("sn.CreateTime", fromDate));
        if (toDate > new DateTime(1900, 1, 1)) query.And(Exp.Le("sn.CreateTime", toDate.AddDays(1)));
        IList<CRMSNStatus> status = new List<CRMSNStatus>();
        if (this.chkDistributing.Checked) status.Add(CRMSNStatus.Distributing);
        if (this.chkChecked.Checked) status.Add(CRMSNStatus.Checked);
        if (this.chkPackaged.Checked) status.Add(CRMSNStatus.Packaged);
        if (this.chkInterchanged.Checked) status.Add(CRMSNStatus.Interchanged);
        if (this.chkReturn.Checked) status.Add(CRMSNStatus.Return);
        if (this.chkPartReturn.Checked) status.Add(CRMSNStatus.PartExchange);
        query.And(Exp.In("sn.Status", status));
        if (this.drpInvoice.SelectedValue == "0")
            query.And(Exp.Eq("sn.InvoiceNumber", " "));
        else if (this.drpInvoice.SelectedValue == "1")
            query.And(Exp.NEq("sn.InvoiceNumber", " "));

        this.repeatControl.DataSource = query.DataSet();
        this.repeatControl.DataBind();
        if (fetchRecordCount)
            this.magicPagerMain.RecordCount = this.magicPagerSub.RecordCount = query.Count();
        WebUtil.SetMagicPager(magicPagerMain, pageSize, pageIndex);
        WebUtil.SetMagicPager(magicPagerSub, pageSize, pageIndex);
        this.hidReturnUrl.Value = this.GetReturnUrl();
    }

    private void RestoreLastQuery(ISession session)
    {
        QueryHelper helper = new QueryHelper(this);
        if (helper.HasQueryParameter())
        {
            this.txtSDNumber.Text = helper.Pop("sdnum");
            this.txtSONumber.Text = helper.Pop("sonum");
            this.txtShippingNumber.Text = helper.Pop("snnum");
            this.txtInvoice.Text = helper.Pop("invoice");
            this.drpLogis.SelectedValue = helper.Pop("lg");
            this.txtDateFrom.Text = helper.Pop("df");
            this.txtDateTo.Text = helper.Pop("dt");
            if (helper.Pop("distribute") == "1") this.chkDistributing.Checked = true;
            else this.chkDistributing.Checked = false;
            if (helper.Pop("check") == "1") this.chkChecked.Checked = true;
            else this.chkChecked.Checked = false;
            if (helper.Pop("pack") == "1") this.chkPackaged.Checked = true;
            else this.chkPackaged.Checked = false;
            if (helper.Pop("inter") == "1") this.chkInterchanged.Checked = true;
            else this.chkInterchanged.Checked = false;
            if (helper.Pop("rtn") == "1") this.chkReturn.Checked = true;
            else this.chkReturn.Checked = false;
            if (helper.Pop("prtn") == "1") this.chkPartReturn.Checked = true;
            else this.chkPartReturn.Checked = false;
        }
        this.QueryAndBindData(session, Cast.Int(helper.Pop("pi"), 1), Cast.Int(helper.Pop("ps"), this.magicPagerMain.PageSize), true);
    }

    private string GetReturnUrl()
    {
        QueryHelper helper = new QueryHelper(this);
        helper.Push("sdnum", this.txtSDNumber.Text.Trim());
        helper.Push("sonum", this.txtSONumber.Text.Trim());
        helper.Push("snnum", this.txtShippingNumber.Text.Trim());
        helper.Push("invoice", this.txtInvoice.Text.Trim());
        helper.Push("lg", this.drpLogis.SelectedValue);
        helper.Push("df", this.txtDateFrom.Text.Trim());
        helper.Push("dt", this.txtDateTo.Text.Trim());
        if (this.chkDistributing.Checked) helper.Push("distribute", "1");
        if (this.chkChecked.Checked) helper.Push("check", "1");
        if (this.chkPackaged.Checked) helper.Push("pack", "1");
        if (this.chkInterchanged.Checked) helper.Push("inter", "1");
        if (this.chkReturn.Checked) helper.Push("rtn", "1");
        if (this.chkPartReturn.Checked) helper.Push("prtn", "1");
        helper.Push("pi", this.magicPagerMain.CurrentPageIndex);
        helper.Push("ps", this.magicPagerMain.PageSize);
        return helper.OutputReturnUrl();
    }
}