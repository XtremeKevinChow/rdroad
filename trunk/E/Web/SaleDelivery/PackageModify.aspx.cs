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
using Magic.Web.UI;

public partial class SaleDelivery_PackageModify : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        this.lblInfo.InnerText = "";
        if (!IsPostBack)
        {
            using (ISession session = new Session())
            {
                IList<Logistics> logis = session.CreateEntityQuery<Logistics>().OrderBy("ShortName").List<Logistics>();
                this.drpLogis.Items.Clear();
                this.drpLogis.Items.Add(new ListItem(" ", "0"));
                foreach (Logistics l in logis)
                    this.drpLogis.Items.Add(new ListItem(l.ShortName, l.LogisticCompID.ToString()));

                IList<ExcelTemplate> templates = ExcelTemplate.GetEnabledList(session);
                this.drpTemplate.Items.Clear();
                foreach (ExcelTemplate t in templates)
                    this.drpTemplate.Items.Add(new ListItem(t.TemplateName, t.TemplateID.ToString()));
            }
        }
    }

    protected void MagicItemCommand(object sender, MagicItemEventArgs e)
    {
        if (e.CommandName != "Confirm" && e.CommandName != "Save") return;
        string snnumber = this.txtSNNumber.Text.Trim();
        if (snnumber.Length <= 0)
        {
            this.lblInfo.InnerText = "发货单号码为空";
            return;
        }

        using (ISession session = new Session())
        {
            CRMSN sn = CRMSN.Retrieve(session, snnumber);
            if (sn == null)
            {
                this.lblInfo.InnerText = "发货单" + snnumber + "不存在";
                return;
            }
            if (sn.Status != CRMSNStatus.Checked && sn.Status != CRMSNStatus.Packaged)
            {
                this.lblInfo.InnerText = "发货单" + (sn.Status == CRMSNStatus.Interchanged ? "已经完成交接" : "状态为" + sn.Status.ToString()) + "，不可以进行调整";
                return;
            }
            ICHead ic = ICHead.Query(session, sn.OrderNumber);
            if (ic != null)
            {
                this.lblInfo.InnerText = sn.OrderNumber + "已经被加入到交接单" + ic.OrderNumber + "中，请先从交接单中删除该发货单后再进行修改";
                return;
            }

            this.hidSnNumber.Value = snnumber;
            if (e.CommandName == "Confirm")
            {
                this.snView.SNNumber = sn.OrderNumber;
                if (sn.LogisticsID > 0)
                    this.drpLogis.SelectedValue = sn.LogisticsID.ToString();
                else
                    this.drpLogis.SelectedValue = "0";
                this.txtInvoice.Value = sn.InvoiceNumber;
                this.txtPackageCount.Value = sn.PackageCount.ToString();
                this.txtPackageWeight.Value = RenderUtil.FormatNumber(sn.PackageWeight, "##0.#0");
                this.txtShippingNumber.Value = sn.ShippingNumber;
            }
            else if (e.CommandName == "Save")
            {
                if (Cast.Int(this.drpLogis.SelectedValue, 0) <= 0)
                {
                    this.lblInfo.InnerText = "请选择物流公司";
                    return;
                }
                sn.ShippingNumber = this.txtShippingNumber.Value.Trim();
                sn.InvoiceNumber = this.txtInvoice.Value.Trim();
                sn.PackageWeight = Cast.Decimal(this.txtPackageWeight.Value, sn.PackageWeight);
                sn.PackageCount = Cast.Int(this.txtPackageCount.Value, sn.PackageCount);
                sn.LogisticsID = Cast.Int(this.drpLogis.SelectedValue, 0);
                sn.Update(session, "ShippingNumber", "InvoiceNumber", "PackageWeight", "PackageCount", "LogisticsID");
                this.lblInfo.InnerText = "发货单" + sn.OrderNumber + "包装信息修改成功";
                this.txtSNNumber.Text = "";
                this.snView.SNNumber = "";
                this.txtInvoice.Value = "";
                this.txtPackageCount.Value = "";
                this.txtPackageWeight.Value = "";
                this.txtShippingNumber.Value = "";
            }
        }
    }
}