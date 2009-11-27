using System;
using System.Web.UI.WebControls;
using System.Web.UI.HtmlControls;
using Magic.Web.UI;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Query;
using Magic.ERP.Core;

public partial class Approve_ApproveDef : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            using (ISession session = new Session())
            {
                this.QueryAndBindData(session);
            }
        }
    }

    private void QueryAndBindData(ISession session)
    {
        this.repeatControl.DataSource = session.CreateEntityQuery<OrderTypeDef>()
            .Where(Exp.Eq("SupportApprove", true))
            .OrderBy("OrderTypeCode")
            .List<OrderTypeDef>();
        this.repeatControl.DataBind();
    }
    protected void repeatControl_ItemDataBound(object sender, RepeaterItemEventArgs e)
    {
        OrderTypeDef ordType = e.Item.DataItem as OrderTypeDef;
        if (ordType == null) return;

        DropDownList drp = e.Item.FindControl("drpNeedApprove") as DropDownList;
        drp.SelectedValue = (ordType.NeedApprove ? "1" : "0");
        HtmlAnchor cmd = e.Item.FindControl("cmdAddUser") as HtmlAnchor;
        if (ordType.NeedApprove)
        {
            cmd.Visible = true;
            cmd.HRef = "ApproveDefUser.aspx?type=" + ordType.OrderTypeCode;
        }
        else cmd.Visible = false;
    }
    protected void MagicItemCommand(object sender, MagicItemEventArgs e)
    {
        if (e.CommandName != "Save")
        {
            WebUtil.ShowError(this, "无效的命令");
            return;
        }

        bool saved = false;
        using (ISession session = new Session())
        {
            try
            {
                HtmlInputText input;
                DropDownList drp;
                string typeCode;
                OrderTypeDef typeDef;

                session.BeginTransaction();
                foreach (RepeaterItem item in this.repeatControl.Items)
                {
                    input = item.FindControl("txtTypeCode") as HtmlInputText;
                    typeCode = input.Value;
                    typeDef = OrderTypeDef.Retrieve(session, typeCode);
                    if (typeDef != null)
                    {
                        drp = item.FindControl("drpNeedApprove") as DropDownList;
                        typeDef.NeedApprove = drp.SelectedValue == "1" ? true : false;
                        typeDef.Update(session, "NeedApprove");
                        saved = true;
                    }
                }
                session.Commit();
                if (saved)
                {
                    WebUtil.ShowMsg(this, "所做的修改已经保存成功", "保存成功");
                    this.QueryAndBindData(session);
                }
            }
            catch (Exception er)
            {
                session.Rollback();
                WebUtil.ShowError(this, er);
            }
        }
    }
}