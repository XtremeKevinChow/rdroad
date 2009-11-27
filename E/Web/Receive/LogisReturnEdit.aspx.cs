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
using Magic.Basis;
using Magic.Framework.Utils;

public partial class Receive_LogisReturnEdit : System.Web.UI.Page
{
    private void showInfo(ISession session, ReturnHead head)
    {
        User user;
        if (head != null)
        {
            this.txtOrderNumber.Text = head.OrderNumber;
            this.drpLocation.SelectedValue = head.LocationCode;
            this.txtSNNumber.Text = head.RefOrderNumber;
            this.drpReason.SelectedValue = head.ReasonID.ToString();
            this.chkIsMalicious.Checked = head.IsMalicious;
            this.chkHasTransported.Checked = head.HasTransported;
            this.txtNote.Text = head.Note;
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
    private void setView(ReturnHead head)
    {
        if (!this.IsNew && head != null && head.Status != ReturnStatus.New)
        {
            WebUtil.DisableControl(this.txtNote);
            WebUtil.DisableControl(this.drpLocation);
            WebUtil.DisableControl(this.txtSNNumber);
            WebUtil.DisableControl(this.drpReason);
            WebUtil.DisableControl(this.chkIsMalicious);
            this.cmdEdit.Visible = false;
        }
        this.cmdReturn["Return"].NavigateUrl = this.ReturnUrl;
    }
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            this.txtAction.Value = WebUtil.Param("mode");
            this.txtId.Value = WebUtil.Param("ordNumber");

            ReturnHead head = null;
            using (ISession session = new Session())
            {
                IList<WHLocation> locations = WHLocation.EffectiveList(session);
                this.drpLocation.Items.Clear();
                foreach (WHLocation loc in locations)
                    this.drpLocation.Items.Add(new ListItem(loc.Name, loc.LocationCode));
                IList<ReturnReason> resons = ReturnReason.EffectiveList(session);
                this.drpReason.Items.Clear();
                this.drpReason.Items.Add(new ListItem("　", "0"));
                foreach (ReturnReason rs in resons)
                    this.drpReason.Items.Add(new ListItem(rs.ReasonText, rs.ReasonID.ToString()));

                if (!this.IsNew)
                {
                    head = ReturnHead.Retrieve(session, this.OrderNumber);
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
                ReturnHead head = null;
                if (this.IsNew)
                {
                    head = new ReturnHead();
                    try
                    {
                        head.LogisReturn(session, this.drpLocation.SelectedValue, this.txtSNNumber.Text, Cast.Int(this.drpReason.SelectedValue, 0), this.chkIsMalicious.Checked, this.chkHasTransported.Checked, this.txtNote.Text.Trim(), SecuritySession.CurrentUser.UserId);
                        head.OrderNumber = ERPUtil.NextOrderNumber(head.OrderTypeCode);
                        session.BeginTransaction();
                        head.Create(session);
                        session.Commit();

                        this.Response.Redirect("LogisReturnScan.aspx?ordNum=" + head.OrderNumber + "&return=" + WebUtil.escape("LogisReturnLine.aspx?ordNum=" + head.OrderNumber + "&return=" + WebUtil.escape(this.ReturnUrl)));
                    }
                    catch (Exception er)
                    {
                        session.Rollback();
                        WebUtil.ShowError(this, er);
                    }

                    return;
                }

                try
                {
                    head = ReturnHead.Retrieve(session, this.OrderNumber);
                    head.LogisReturn(session, this.drpLocation.SelectedValue, this.txtSNNumber.Text, Cast.Int(this.drpReason.SelectedValue, 0), this.chkIsMalicious.Checked, this.chkHasTransported.Checked, this.txtNote.Text.Trim(), SecuritySession.CurrentUser.UserId);
                    session.BeginTransaction();
                    head.Update(session, "Note", "LocationCode", "ReasonID", "ReasonText", "IsMalicious", "RefOrderID", "RefOrderNumber", "OrginalOrderNumber", "LogisticsName", "LogisticsID", "MemberName", "MemberID", "HasTransported");
                    session.Commit();
                    WebUtil.ShowMsg(this, "保存成功");
                }
                catch (Exception er2)
                {
                    session.Rollback();
                    WebUtil.ShowError(this, er2);
                }
            }
        }
    }
}