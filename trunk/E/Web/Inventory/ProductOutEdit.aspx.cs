using System;
using System.Collections.Generic;
using System.Web.UI.WebControls;
using Magic.ERP;
using Magic.ERP.Core;
using Magic.ERP.Orders;
using Magic.Framework.ORM;
using Magic.Security;
using Magic.Sys;
using Magic.Web.UI;

public partial class Inventory_ProductOutEdit : System.Web.UI.Page
{
    private void showInfo(ISession session, StockInHead head)
    {
        User user;
        if (head != null)
        {
            this.txtOrderNumber.Text = head.OrderNumber;
            this.txtNote.Text = head.Note;
            this.drpLocation.SelectedValue = head.LocationCode;
            this.txtResponsible.Text = head.Responsible;
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
        if (this.IsNew)
            this.cmdDetail.Visible = false;
        else
        {
            if (head != null && head.Status != StockInStatus.New)
            {
                WebUtil.DisableControl(this.txtNote);
                WebUtil.DisableControl(this.drpLocation);
                WebUtil.DisableControl(this.txtResponsible);
                this.cmdEdit.Visible = false;
            }
            this.cmdDetail.Visible = true;
            this.cmdDetail["Detail"].NavigateUrl = "ProductOutLine.aspx?ordNum=" + this.OrderNumber + "&return=" + WebUtil.escape(this.ReturnUrl);
        }
        this.cmdReturn["Return"].NavigateUrl = this.ReturnUrl;
    }
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            this.txtAction.Value = WebUtil.Param("mode");
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

                if (!this.IsNew)
                {
                    head = StockInHead.Retrieve(session, this.OrderNumber);
                    this.showInfo(session, head);
                }
            }
            this.setView(head);
        }
    }
    private bool IsNew
    {
        get
        {
            return this.txtAction.Value.Trim() == "new" && this.txtId.Value.Trim().Length <= 0;
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
    protected void MagicItemCommand(object sender, MagicItemEventArgs e)
    {
        if (e.CommandName == "Save")
        {
            using (ISession session = new Session())
            {
                StockInHead head = null;
                if (this.IsNew)
                {
                    head = new StockInHead();
                    head.OrderTypeCode = StockInHead.ORD_TYPE_PRD_OUT;
                    head.Status = StockInStatus.New;
                    head.ApproveResult = ApproveStatus.UnApprove;
                    head.ApproveUser = 0;
                    head.ApproveTime = new DateTime(1900, 1, 1);
                    head.CreateUser = SecuritySession.CurrentUser.UserId;
                    head.CreateTime = DateTime.Now;
                    head.Note = this.txtNote.Text.Trim();
                    head.Responsible = this.txtResponsible.Text.Trim();
                    head.LocationCode = this.drpLocation.SelectedValue;
                    try
                    {
                        session.BeginTransaction();
                        head.OrderNumber = ERPUtil.NextOrderNumber(head.OrderTypeCode);
                        head.Create(session);
                        session.Commit();
                    }
                    catch (Exception er)
                    {
                        session.Rollback();
                        WebUtil.ShowError(this, er);
                    }

                    this.Response.Redirect("ProductOutNewLine.aspx?ordNum=" + head.OrderNumber + "&return=" + WebUtil.escape("ProductOutLine.aspx?ordNum=" + head.OrderNumber + "&return=" + WebUtil.escape(WebUtil.Param("return"))));
                    return;
                }

                head = StockInHead.Retrieve(session, this.OrderNumber);
                head.Responsible = this.txtResponsible.Text.Trim();
                head.Note = this.txtNote.Text.Trim();
                head.LocationCode = this.drpLocation.SelectedValue;
                head.Update(session, "Note", "LocationCode");
                this.Response.Redirect("ProductOutLine.aspx?ordNum=" + head.OrderNumber + "&return=" + WebUtil.escape(WebUtil.Param("return")));
            }
        }
    }
}