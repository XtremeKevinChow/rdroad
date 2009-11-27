using System;
using System.Web.UI.WebControls;
using Magic.Framework.Utils;
using Magic.Framework.ORM;
using Magic.Web.UI;
using Magic.ERP.Core;
using Magic.ERP;

public partial class Basis_OrderNumberRuleDefEdit : System.Web.UI.Page
{
    private bool IsNew
    {
        get
        {
            return this.RuleDefId <= 0;
        }
    }
    private int RuleDefId
    {
        get
        {
            return Cast.Int(this.txtRuleId.Value, 0);
        }
    }

    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            this.txtRuleId.Value = WebUtil.Param("defId");

            this.drpTimeStampPrecision.Items.Clear();
            this.drpTimeStampPrecision.Items.Add(new ListItem("", ""));
            this.drpTimeStampPrecision.Items.Add(new ListItem("日", OrderRuleDefPrecision.Date.ToString()));
            this.drpTimeStampPrecision.Items.Add(new ListItem("月", OrderRuleDefPrecision.Month.ToString()));
            this.drpTimeStampPrecision.Items.Add(new ListItem("年", OrderRuleDefPrecision.Year.ToString()));

            if (!this.IsNew)
            {
                using (ISession session = new Session())
                {
                    OrderRuleDef def = OrderRuleDef.Retrieve(session, this.RuleDefId);
                    if (def != null)
                    {
                        this.txtText.Text = def.RuleDefineText;
                        if (def.UsePrefix)
                        {
                            this.drpUsePrefix.SelectedValue = "1";
                            this.txtPrefix.Text = def.PrefixValue;
                        }
                        if (def.UseTimeStamp)
                        {
                            this.drpUseTimeStamp.SelectedValue = "1";
                            if (def.TimeStampPrecision == OrderRuleDefPrecision.Date || def.TimeStampPrecision == OrderRuleDefPrecision.Month || def.TimeStampPrecision == OrderRuleDefPrecision.Year)
                                this.drpTimeStampPrecision.SelectedValue = def.TimeStampPrecision.ToString();
                            this.txtTimeStampPattern.Text = def.TimeStampPattern;
                            this.lblPrevTimeStampVal.Text = def.PrevTimeStampValue.ToString("yyyy-MM-dd");
                        }
                        this.txtSerialLen.Text = def.SerialLength.ToString();
                        this.lblPrevSerialVal.Text = def.PrevSerialValue.ToString();
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
                OrderRuleDef def = null;
                if (this.IsNew) def = new OrderRuleDef();
                else def = OrderRuleDef.Retrieve(session, this.RuleDefId);
                def.RuleDefineText = this.txtText.Text.Trim();
                def.UsePrefix = this.drpUsePrefix.SelectedValue == "1" ? true : false;
                if (def.UsePrefix) def.PrefixValue = this.txtPrefix.Text.Trim();
                def.UseTimeStamp = this.drpUseTimeStamp.SelectedValue == "1" ? true : false;
                if (def.UseTimeStamp)
                {
                    def.TimeStampPrecision = Cast.Enum<OrderRuleDefPrecision>(this.drpTimeStampPrecision.SelectedValue, def.TimeStampPrecision);
                    def.TimeStampPattern = this.txtTimeStampPattern.Text.Trim();
                }
                def.SerialLength = Cast.Int(this.txtSerialLen.Text.Trim(), def.SerialLength);

                if (this.IsNew) def.Create(session);
                else def.Update(session, "RuleDefineText", "UsePrefix", "PrefixValue", "UseTimeStamp", "TimeStampPrecision", "TimeStampPattern", "SerialLength");
                this.Response.Redirect("OrderNumberRuleDefManager.aspx");
            }
        }
    }
}