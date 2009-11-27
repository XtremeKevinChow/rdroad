using System;
using System.Collections.Generic;
using Magic.Web.UI;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Query;
using Magic.Framework.Utils;
using Magic.Security;
using Magic.Basis;
using Magic.ERP;
using Magic.ERP.Orders;
using Magic.ERP.Core;
using Magic.Sys;

public partial class Receive_PurchaseRCVEdit : System.Web.UI.Page
{
    private void showInfo(ISession session, RCVHead head)
    {
        User user;
        if (head != null)
        {
            this.txtOrderNumber.Text = head.OrderNumber;
            this.txtPONumber.Value = head.RefOrderNumber;
            if (head.ObjectID > 0)
            {
                Vendor ven = Vendor.Retrieve(session, head.ObjectID);
                if (ven != null) this.lblVendor.Text = ven.ShortName;
            }
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
    private void setView(RCVHead head)
    {
        if (this.IsNew)
            this.cmdDetail.Visible = false;
        else
        {
            WebUtil.DisableControl(this.txtPONumber);
            this.cmdSelectPO.Visible = false;
            this.trAutoCreate.Visible = false;

            this.cmdDetail.Visible = true;
            this.cmdDetail["Detail"].NavigateUrl = "PurchaseRCVLine.aspx?ordNum=" + this.OrderNumber + "&return=" + Microsoft.JScript.GlobalObject.escape(this.ReturnUrl);
            if (head != null && head.Status != ReceiveStatus.New)
            {
                this.cmdEdit.Visible = false;
                WebUtil.DisableControl(this.txtNote);
            }
        }
        this.trAutoCreate.Visible = false;
        this.cmdReturn["Return"].NavigateUrl = this.ReturnUrl;
    }
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            this.txtAction.Value = WebUtil.Param("mode");
            this.txtId.Value = WebUtil.Param("ordNumber");

            RCVHead head = null;
            using (ISession session = new Session())
            {
                if (!this.IsNew)
                {
                    head = RCVHead.Retrieve(session, this.OrderNumber);
                    this.showInfo(session, head);
                }
                else
                {
                    //this.rdoAutoCreateLines.Checked = true;
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
                RCVHead head = null;
                if (this.IsNew)
                {
                    int lineCount = 0;
                    try
                    {
                        session.BeginTransaction();
                        head = RCVHead.CreatePurchaseRCV(session, SecuritySession.CurrentUser.UserId, this.txtPONumber.Value, this.txtNote.Text);
                        if (this.rdoAutoCreateLines.Checked)
                            lineCount = head.AddLinesFromRefOrder(session);
                        session.Commit();

                        this.txtAction.Value = "edit";
                        this.txtId.Value = head.OrderNumber;
                        this.showInfo(session, head);
                        this.setView(head);
                        WebUtil.ShowMsg(this, string.Format("收货单{0}创建成功{1}", head.OrderNumber, this.rdoAutoCreateLines.Checked ? "，自动创建了" + lineCount.ToString() + "个收货明细" : ""));
                    }
                    catch (Exception er)
                    {
                        session.Rollback();
                        WebUtil.ShowError(this, er);
                    }

                    return;
                }

                head = RCVHead.Retrieve(session, this.OrderNumber);
                head.Note = this.txtNote.Text.Trim();
                head.Update(session, "Note");
                WebUtil.ShowMsg(this, "保存成功");
            }
        }
    }
}