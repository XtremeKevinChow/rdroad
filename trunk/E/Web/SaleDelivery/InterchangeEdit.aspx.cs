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

public partial class SaleDelivery_InterchangeEdit : System.Web.UI.Page
{
    private void showInfo(ISession session, ICHead head)
    {
        User user;
        if (head != null)
        {
            this.txtOrderNumber.Text = head.OrderNumber;
            this.txtNote.Text = head.Note;
            this.drpLogistic.SelectedValue = head.LogisticCompID.ToString();
            this.txtLogisticsUser.Text = head.LogisticUser;
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
    private void setView(ICHead head)
    {
        if (this.IsNew)
        {
            this.cmdDetail.Visible = false;
            this.cmdScanLine.Visible = false;
        }
        else
        {
            if (head != null && head.Status != InterchangeStatus.New)
            {
                WebUtil.DisableControl(this.txtNote);
                WebUtil.DisableControl(this.drpLogistic);
                WebUtil.DisableControl(this.txtLogisticsUser);
                this.cmdEdit.Visible = false;
                this.cmdScanLine.Visible = false;
            }
            else if (head != null)
            {
                this.cmdScanLine.Visible = true;
                this.cmdScanLine["ScanLine"].NavigateUrl = "InterchangeLineScan.aspx?ordNumber=" + this.OrderNumber + "&return=" + Microsoft.JScript.GlobalObject.escape(this.ReturnUrl);
            }
            this.cmdDetail.Visible = true;
            this.cmdDetail["Detail"].NavigateUrl = "InterchangeLine.aspx?ordNum=" + this.OrderNumber + "&return=" + Microsoft.JScript.GlobalObject.escape(this.ReturnUrl);
        }
        this.cmdReturn["Return"].NavigateUrl = this.ReturnUrl;
    }
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            this.txtAction.Value = WebUtil.Param("mode");
            this.txtId.Value = WebUtil.Param("ordNumber");

            ICHead head = null;
            using (ISession session = new Session())
            {
                IList<Logistics> companies = session.CreateEntityQuery<Logistics>()
                    .Where(Magic.Framework.ORM.Query.Exp.Eq("Status", LogisticsStatus.Enable))
                    .OrderBy("ShortName")
                    .List<Logistics>();
                this.drpLogistic.Items.Clear();
                foreach (Logistics lg in companies)
                    this.drpLogistic.Items.Add(new ListItem(lg.ShortName, lg.LogisticCompID.ToString()));

                if (!this.IsNew)
                {
                    head = ICHead.Retrieve(session, this.OrderNumber);
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
                ICHead head = null;
                bool created = false;
                if (this.IsNew)
                {
                    head = new ICHead();
                    head.OrderTypeCode = ICHead.ORDER_TYPE;
                    head.Status = InterchangeStatus.New;
                    head.ApproveResult = ApproveStatus.UnApprove;
                    head.ApproveUser = 0;
                    head.ApproveTime = new DateTime(1900, 1, 1);
                    head.CreateUser = SecuritySession.CurrentUser.UserId;
                    head.CreateTime = DateTime.Now;
                    head.CompanyUser = head.CreateUser;
                    head.InterchangeTime = DateTime.Now;
                    head.LogisticUser = this.txtLogisticsUser.Text.Trim();
                    head.Note = this.txtNote.Text.Trim();
                    head.LogisticCompID = Magic.Framework.Utils.Cast.Int(this.drpLogistic.SelectedValue);
                    try
                    {
                        session.BeginTransaction();
                        head.OrderNumber = ERPUtil.NextOrderNumber(head.OrderTypeCode);
                        head.Create(session);
                        head.AutoGenerateDetail(session);
                        session.Commit();
                        created = true;
                        //this.txtAction.Value = "edit";
                        //this.txtId.Value = head.OrderNumber;
                        //this.showInfo(session, head);
                        //this.setView(head);
                        //WebUtil.ShowMsg(this, string.Format("交接单{0}创建成功", head.OrderNumber));
                    }
                    catch (Exception er)
                    {
                        session.Rollback();
                        WebUtil.ShowError(this, er);
                    }
                }
                if (created)
                {
                    this.Response.Redirect("InterchangeLine.aspx?ordNum=" + head.OrderNumber + "&return=" + WebUtil.escape(WebUtil.Param("return")));
                }

                head = ICHead.Retrieve(session, this.OrderNumber);
                head.Note = this.txtNote.Text.Trim();
                head.LogisticCompID = Magic.Framework.Utils.Cast.Int(this.drpLogistic.SelectedValue);
                head.LogisticUser = this.txtLogisticsUser.Text.Trim();
                head.Update(session, "Note", "LogisticCompID", "LogisticUser", "BoxNum");
                WebUtil.ShowMsg(this, "保存成功");
            }
        }
    }
}