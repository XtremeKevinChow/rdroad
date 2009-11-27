using System;
using System.Collections.Generic;
using System.Web.UI.WebControls;
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

public partial class Inventory_StockAdjustmentEdit : System.Web.UI.Page
{
    private static log4net.ILog log = WebUtil.Logger(typeof(Inventory_StockAdjustmentEdit));

    private void showInfo(ISession session, INVCheckHead head)
    {
        User user;
        if (head != null)
        {
            this.txtOrderNumber.Text = head.OrderNumber;
            this.txtNote.Text = head.Note;
            if (!string.IsNullOrEmpty(head.LocationCode))
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
    private void setView(INVCheckHead head)
    {
        if (this.IsNew)
            this.cmdDetail.Visible = false;
        else
        {
            this.cmdDetail.Visible = true;
            this.cmdDetail["Detail"].NavigateUrl = "StockAdjustmentLine.aspx?ordNum=" + this.OrderNumber + "&return=" + Microsoft.JScript.GlobalObject.escape(this.ReturnUrl);
            if (head != null && head.Status != INVCheckStatus.New)
                this.cmdEdit.Visible = false;
        }
        this.cmdReturn["Return"].NavigateUrl = this.ReturnUrl;

        if (head != null && head.Status != INVCheckStatus.New)
        {
            WebUtil.DisableControl(this.drpLocation);
            WebUtil.DisableControl(this.txtNote);
        }
    }
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            this.txtAction.Value = WebUtil.Param("mode");
            this.txtId.Value = WebUtil.Param("ordNumber");

            this.setView(null);

            using (ISession session = new Session())
            {
                IList<WHLocation> locations = WHLocation.EffectiveList(session);
                this.drpLocation.Items.Clear();
                foreach (WHLocation loc in locations)
                    this.drpLocation.Items.Add(new ListItem(loc.Name, loc.LocationCode));

                if (!this.IsNew)
                {
                    INVCheckHead head = INVCheckHead.Retrieve(session, this.OrderNumber);
                    this.setView(head);
                    this.showInfo(session, head);
                    log.DebugFormat("PageLoad - {0}库存调整: ordNum={1}, return={2}", head.Status == INVCheckStatus.New ? "编辑" : "查看", this.OrderNumber, this.ReturnUrl);
                }
                else
                    log.Debug("PageLoad－新增库存调整单: return=" + WebUtil.Param("return"));
            }
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
                INVCheckHead head = null;
                if (this.IsNew)
                {
                    head = new INVCheckHead();
                    head.OrderNumber = ERPUtil.NextOrderNumber(INVCheckHead.ORDER_TYPE_ADJ);
                    head.Note = this.txtNote.Text.Trim();
                    head.LocationCode = this.drpLocation.SelectedValue;
                    head.Status = INVCheckStatus.New;
                    head.OrderTypeCode = INVCheckHead.ORDER_TYPE_ADJ;
                    head.CreateTime = DateTime.Now;
                    head.CreateUser = SecuritySession.CurrentUser.UserId;

                    head.Create(session);
                    log.DebugFormat("Create - 库存调整单: {0}", head.OrderNumber);

                    System.Text.StringBuilder url = new System.Text.StringBuilder();
                    url.Append("StockAdjustmentNewLine.aspx?ordNum=").Append(head.OrderNumber);
                    url.Append("&return=");
                    //保存后跳转到添加明细页面
                    //下面的返回url设置用途：
                    //1. 从添加明细页面点击返回按钮，将跳转到明细维护界面
                    //2. 明细维护界面再点击返回按钮，跳转到调整单主档维护页面，并且保留下从调整单主档维护页面进入新增页面时的查询条件
                    url.Append(WebUtil.escape("StockAdjustmentLine.aspx?ordNum=" + head.OrderNumber + "&return=" + WebUtil.escape(WebUtil.Param("return"))));
                    if (log.IsDebugEnabled)
                    {
                        log.DebugFormat("Redirect - to添加调整明细: url={0}", url.ToString());
                        log.DebugFormat("返回调整明细页地址: url={0}", "StockAdjustmentLine.aspx?ordNum=" + head.OrderNumber + "&return=" + WebUtil.escape(WebUtil.Param("return")));
                    }
                    this.Response.Redirect(url.ToString());
                }

                head = INVCheckHead.Retrieve(session, this.OrderNumber);
                if (head.Status != INVCheckStatus.New)
                {
                    log.WarnFormat("Warn - 保存库存调整单: 库存调整单{0}状态为{1}，无法保存", head.OrderNumber, head.Status.ToString());
                    WebUtil.ShowMsg(this, "只有状态为新建的库存调整单可以修改保存!");
                    return;
                }
                head.Note = this.txtNote.Text.Trim();
                head.LocationCode = this.drpLocation.SelectedValue;
                head.Update(session, "Note", "LocationCode");
                log.DebugFormat("Update - 库存调整单: {0}", head.OrderNumber);
                WebUtil.ShowMsg(this, "保存成功");
            }
        }
    }
}