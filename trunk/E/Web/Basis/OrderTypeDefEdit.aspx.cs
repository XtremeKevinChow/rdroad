using System;
using System.Collections.Generic;
using System.Web.UI.WebControls;
using Magic.Framework.Utils;
using Magic.Framework.ORM;
using Magic.Web.UI;
using Magic.ERP.Core;
using Magic.ERP;

public partial class Basis_OrderTypeDefEdit : System.Web.UI.Page
{
    private bool IsNew
    {
        get
        {
            return this.txtIsNew.Value == "1";
        }
    }
    private string TypeCode
    {
        get
        {
            return this.txtTypeCode.Text.Trim().ToUpper();
        }
    }

    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            this.txtTypeCode.Text = WebUtil.Param("type");
            if (this.txtTypeCode.Text.Trim().Length <= 0)
            {
                this.txtIsNew.Value = "1";
                this.trNeedAppr.Visible = false;
            }
            else
            {
                this.txtIsNew.Value = "";
                WebUtil.DisableControl(this.txtTypeCode);
            }
            //发货（销售发货和换货发货）由CRM生成，单据编码规则在ERP中不会起作用
            if (this.TypeCode == "SD0" || this.TypeCode == "DL2")
                this.trRule.Visible = false;

            using (ISession session = new Session())
            {
                IList<OrderRuleDef> ruleDefines = session.CreateEntityQuery<OrderRuleDef>()
                    .OrderBy("RuleDefineID")
                    .List<OrderRuleDef>();
                this.drpRule.Items.Clear();
                this.drpRule.Items.Add(new ListItem("", "0"));
                foreach (OrderRuleDef rule in ruleDefines)
                    this.drpRule.Items.Add(new ListItem(string.IsNullOrEmpty(rule.RuleDefineText) ? "　" : rule.RuleDefineText, rule.RuleDefineID.ToString()));

                if (!this.IsNew)
                {
                    OrderTypeDef def = OrderTypeDef.Retrieve(session, this.TypeCode);
                    if (def != null)
                    {
                        this.txtText.Text = def.TypeText;
                        this.txtUrl.Text = def.ViewURL;
                        if (!def.SupportApprove)
                            this.trNeedAppr.Visible = false;
                        else
                            this.drpNeedAppr.SelectedValue = def.NeedApprove ? "1" : "0";
                        this.lblSupportAppr.Text = RenderUtil.FormatBool(def.SupportApprove, "是", "否");
                        if (def.RuleDefineID > 0)
                            this.drpRule.SelectedValue = def.RuleDefineID.ToString();
                    }
                }
            }
        }
    }

    protected void MagicItemCommand(object sender, MagicItemEventArgs e)
    {
        if (e.CommandName == "Save")
        {
            using (ISession session = new Session())
            {
                OrderTypeDef def = null;
                if (this.IsNew)
                {
                    def = new OrderTypeDef();
                    def.OrderTypeCode = this.TypeCode;
                    def.SupportApprove = false;
                    def.NeedApprove = false;
                    def.TransStepCount = 0;
                }
                else def = OrderTypeDef.Retrieve(session, this.TypeCode);
                def.TypeText = this.txtText.Text.Trim();
                def.ViewURL = this.txtUrl.Text.Trim();
                if (this.TypeCode != "SD0" && this.TypeCode != "DL2")
                    def.RuleDefineID = Cast.Int(this.drpRule.SelectedValue, def.RuleDefineID);
                if (def.SupportApprove)
                    def.NeedApprove = this.drpNeedAppr.SelectedValue == "0" ? false : true;

                if (this.IsNew) def.Create(session);
                else def.Update(session, "TypeText", "ViewURL", "RuleDefineID", "NeedApprove");
                this.Response.Redirect("OrderTypeDefManager.aspx");
            }
        }
    }
}