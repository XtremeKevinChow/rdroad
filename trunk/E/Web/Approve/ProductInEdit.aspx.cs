using System;
using System.Collections.Generic;
using System.Web.UI.WebControls;
using Magic.Web.UI;
using Magic.Framework.ORM;
using Magic.Security;
using Magic.ERP;
using Magic.ERP.Orders;
using Magic.ERP.Core;
using Magic.Sys;

public partial class Approve_ProductInEdit : System.Web.UI.Page
{
    private void showInfo(ISession session, StockInHead head)
    {
        User user;
        if (head != null)
        {
            this.txtOrderNumber.Text = head.OrderNumber;
            this.txtNote.Text = head.Note;
            this.drpLocation.SelectedValue = head.LocationCode;
            OrderStatusDef statusDef = OrderStatusDef.Retrieve(session, head.OrderTypeCode, (int)head.Status);
            if (statusDef != null) this.lblStatus.Text = statusDef.StatusText;
            if (head.CreateUser > 0)
            {
                user = Magic.Sys.User.Retrieve(session, head.CreateUser);
                if (user != null) this.lblUser.Text = user.FullName;
            }
            this.lblCreateTime.Text = RenderUtil.FormatDatetime(head.CreateTime);
            this.lblApproveResult.Text = ERPUtil.EnumText<ApproveStatus>(head.ApproveResult);
            switch (head.ApproveResult)
            {
                case ApproveStatus.Reject: this.lblApproveResult.ForeColor = System.Drawing.Color.Red; break;
                case ApproveStatus.Approve: this.lblApproveResult.ForeColor = System.Drawing.Color.Blue; break;
            }
            if (head.ApproveResult == ApproveStatus.Approve || head.ApproveResult == ApproveStatus.Reject)
            {
                if (head.ApproveUser > 0)
                {
                    user = Magic.Sys.User.Retrieve(session, head.ApproveUser);
                    if (user != null) this.lblApproveUser.Text = user.FullName;
                }
                this.lblApproveTime.Text = RenderUtil.FormatDatetime(head.ApproveTime);
            }
            this.txtApproveNote.Text = head.ApproveNote;
        }
    }
    private void setView(StockInHead head)
    {
        WebUtil.DisableControl(this.txtNote);
        WebUtil.DisableControl(this.drpLocation);
        this.cmdDetail.Visible = true;
        this.cmdDetail["Detail"].NavigateUrl = "ProductInLine.aspx?ordNum=" + this.OrderNumber + "&return=" + WebUtil.escape(this.ReturnUrl);
        this.cmdReturn["Return"].NavigateUrl = this.ReturnUrl;
    }
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            this.txtId.Value = WebUtil.Param("ordNumber");

            StockInHead head = null;
            using (ISession session = new Session())
            {
                IList<WHLocation> locations = session.CreateEntityQuery<WHLocation>()
                    .Where(Magic.Framework.ORM.Query.Exp.Eq("Status", WHStatus.Enable))
                    .OrderBy("LocationCode")
                    .List<WHLocation>();
                this.drpLocation.Items.Clear();
                foreach (WHLocation loc in locations)
                    this.drpLocation.Items.Add(new ListItem(loc.Name, loc.LocationCode));

                head = StockInHead.Retrieve(session, this.OrderNumber);
                this.showInfo(session, head);
            }
            this.setView(head);
        }
    }
    private string OrderNumber
    {
        get
        {
            return this.txtId.Value.Trim();
        }
    }
    private string ReturnUrl
    {
        get
        {
            return WebUtil.Param("return");
        }
    }
}