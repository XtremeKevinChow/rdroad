using System;
using System.Web.UI.WebControls;
using Magic.Framework.Utils;
using Magic.Framework.ORM;
using Magic.Web.UI;
using Magic.ERP.Core;
using Magic.ERP;

public partial class Basis_TransTypeDefEdit : System.Web.UI.Page
{
    private bool IsNew
    {
        get
        {
            return this.txtIsNew.Value.Trim() == "1";
        }
    }
    private string TransTypeCode
    {
        get
        {
            return this.txtCode.Text.Trim();
        }
    }

    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            this.txtCode.Text = WebUtil.Param("type");
            if (this.txtCode.Text.Trim().Length <= 0) this.txtIsNew.Value = "1";

            this.drpPriceSource.Items.Clear();
            this.drpPriceSource.Items.Add(new ListItem(TransTypeDef.PriceSourceTypeText(TransTypePriceSource.NoPrice), TransTypePriceSource.NoPrice.ToString()));
            this.drpPriceSource.Items.Add(new ListItem(TransTypeDef.PriceSourceTypeText(TransTypePriceSource.FromSourceOrder), TransTypePriceSource.FromSourceOrder.ToString()));
            this.drpPriceSource.Items.Add(new ListItem(TransTypeDef.PriceSourceTypeText(TransTypePriceSource.FromMovAvgCost), TransTypePriceSource.FromMovAvgCost.ToString()));
            this.drpPriceSource.Items.Add(new ListItem(TransTypeDef.PriceSourceTypeText(TransTypePriceSource.FromAreaCfg), TransTypePriceSource.FromAreaCfg.ToString()));

            this.drpIsCostTrans.Items.Clear();
            this.drpIsCostTrans.Items.Add(new ListItem("否", "0"));
            this.drpIsCostTrans.Items.Add(new ListItem("是", "1"));

            this.drpTransProp.Items.Clear();
            this.drpTransProp.Items.Add(new ListItem("入", TransProperty.In.ToString()));
            this.drpTransProp.Items.Add(new ListItem("出", TransProperty.Out.ToString()));

            if (!this.IsNew)
            {
                WebUtil.DisableControl(this.txtCode);
                using (ISession session = new Session())
                {
                    TransTypeDef def = TransTypeDef.Retrieve(session, this.TransTypeCode);
                    this.txtText.Text = def.TransDefText;
                    this.txtDesc.Text = def.TransDefDesc;
                    this.drpIsCostTrans.SelectedValue = def.IsCostTrans ? "1" : "0";
                    this.drpPriceSource.SelectedValue = def.PriceSourceType.ToString();
                    this.drpTransProp.SelectedValue = def.TransProperty.ToString();
                }
            }
        }
    }

    protected void MagicItemCommand(object sender, MagicItemEventArgs e)
    {
        if (e.CommandName == "Save")
        {
            TransTypeDef def = new TransTypeDef();
            def.TransTypeCode = this.TransTypeCode;
            def.TransDefText = this.txtText.Text.Trim();
            def.TransDefDesc = this.txtDesc.Text.Trim();
            def.IsCostTrans = this.drpIsCostTrans.SelectedValue == "1" ? true : false;
            def.PriceSourceType = Cast.Enum<TransTypePriceSource>(this.drpPriceSource.SelectedValue);
            def.TransProperty = Cast.Enum<TransProperty>(this.drpTransProp.SelectedValue);
            using(ISession session = new Session())
            {
                if (this.IsNew)
                {
                    def.Create(session);
                    WebUtil.DisableControl(this.txtCode);
                    this.txtIsNew.Value = "";
                }
                else def.Update(session);
                this.Response.Redirect("TransTypeDefManager.aspx");
            }
        }
    }
}