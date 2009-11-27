using System;
using System.Data;
using System.Collections.Generic;
using System.Web.UI.WebControls;
using Magic.Framework.Utils;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Query;
using Magic.Web.UI;
using Magic.Security;
using Magic.Sys;
using Magic.ERP.Core;
using Magic.ERP.Orders;
using Magic.ERP;

public partial class Inventory_InventoryCheckEdit : System.Web.UI.Page
{
    log4net.ILog logger = log4net.LogManager.GetLogger(typeof(Inventory_InventoryCheckEdit));
    private bool IsNew
    {
        get
        {
            return this.txtInvCheckNumber.Text.Trim().Length <= 0;
        }
    }
    private string OrderNumber
    {
        get
        {
            return this.txtInvCheckNumber.Text.Trim();
        }
    }

    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            this.txtInvCheckNumber.Text = WebUtil.Param("ordNum");
            this.cmdReturn["Return"].NavigateUrl = WebUtil.Param("return");

            this.drpCheckType.Items.Clear();
            this.drpCheckType.Items.Add(new ListItem(ERPUtil.EnumText<INVCheckType>(INVCheckType.Explicit), INVCheckType.Explicit.ToString()));
            this.drpCheckType.Items.Add(new ListItem(ERPUtil.EnumText<INVCheckType>(INVCheckType.Implicit), INVCheckType.Implicit.ToString()));

            INVCheckHead head = null;
            using (ISession session = new Session())
            {
                this.LoadLocation(session);
                if (!this.IsNew)
                {
                    head = INVCheckHead.Retrieve(session, this.OrderNumber);
                    this.LoadLocation(session);
                    this.SetView(session, head);
                }
            }
        }
    }
    private void LoadLocation(ISession session)
    {
        if (this.IsNew)
        {
            IList<WHLocation> locations = session.CreateEntityQuery<WHLocation>()
                .Where(Exp.Eq("Status", WHStatus.Enable))
                .OrderBy("LocationCode")
                .List<WHLocation>();
            this.drpLocation.Items.Clear();
            foreach (WHLocation loc in locations)
                this.drpLocation.Items.Add(new ListItem(loc.Name, loc.LocationCode));
        }
    }
    private void SetView(ISession session, INVCheckHead head)
    {
        if (!this.IsNew) WebUtil.DisableControl(this.drpLocation);
        if (head != null)
        {
            if (!string.IsNullOrEmpty(head.LocationCode) && head.LocationCode.Trim().Length > 0)
            {
                WHLocation location = WHLocation.Retrieve(session, head.LocationCode);
                this.drpLocation.Items.Clear();
                this.drpLocation.Items.Add(new ListItem(location.Name, location.LocationCode));
            }
            this.drpCheckType.SelectedValue = head.CheckType.ToString();
            this.txtMemo.Text = head.Note;
            OrderStatusDef statusDef = OrderStatusDef.Retrieve(session, INVCheckHead.ORDER_TYPE_CHK, (int)head.Status);
            if (statusDef != null) this.lblStatus.Text = statusDef.StatusText;
            Magic.Sys.User user = null;
            if (head.CreateUser > 0)
            {
                user = Magic.Sys.User.Retrieve(session, head.CreateUser);
                if (user != null) this.lblUser.Text = user.FullName;
            }
            this.lblCreateTime.Text = RenderUtil.FormatDatetime(head.CreateTime);
            this.lblApproveResult.Text = ERPUtil.EnumText<ApproveStatus>(head.ApproveResult);
            if (head.ApproveUser > 0)
            {
                user = Magic.Sys.User.Retrieve(session, head.ApproveUser);
                if (user != null) this.lblApproveUser.Text = user.FullName;
            }
            this.lblApproveTime.Text = RenderUtil.FormatDatetime(head.ApproveTime);
            this.txtApproveNote.Text = head.ApproveNote;

            if (head.Status != INVCheckStatus.New)
            {
                WebUtil.DisableControl(this.txtMemo);
                WebUtil.DisableControl(this.drpCheckType);
                this.cmdSave.Visible = false;
            }
        }
    }
    protected void MagicItemCommand(object sender, MagicItemEventArgs e)
    {
        if (e.CommandName == "Save")
        {
            try
            {
                using (ISession session = new Session())
                {
                    try
                    {
                        session.BeginTransaction();
                        INVCheckHead objHead = null;
                        if (this.IsNew)
                        {
                            objHead = new INVCheckHead(this.drpLocation.SelectedValue, Cast.Enum<INVCheckType>(this.drpCheckType.SelectedValue), this.txtMemo.Text.Trim(), SecuritySession.CurrentUser.UserId);
                            objHead.Create(session);
                        }
                        else
                        {
                            objHead = INVCheckHead.Retrieve(session, this.OrderNumber);
                            objHead.Note = this.txtMemo.Text.Trim();
                            objHead.CheckType = Cast.Enum<INVCheckType>(this.drpCheckType.SelectedValue);
                            objHead.Update(session, "CheckType", "Note");
                        }
                        session.Commit();
                        this.Response.Redirect("InventoryCheckWH.aspx?ordNum=" + objHead.OrderNumber + "&return=" + Microsoft.JScript.GlobalObject.escape(WebUtil.Param("return")));
                    }
                    catch (Exception ex)
                    {
                        session.Rollback();
                        throw ex;
                    }
                }
            }
            catch (Exception ex)
            {
                logger.Info("保存库存盘点单", ex);
                WebUtil.ShowMsg(this, "发生未处理的异常,请刷新页面重新操作，或者联系系统管理员");
            }
        }
    }
}