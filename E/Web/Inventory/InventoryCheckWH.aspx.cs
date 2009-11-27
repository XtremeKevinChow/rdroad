using System;
using System.Collections.Generic;
using System.Web.UI.WebControls;
using Magic.ERP.Core;
using Magic.ERP.Orders;
using Magic.ERP;
using Magic.Framework.ORM;
using Magic.Web.UI;

public partial class Inventory_InventoryCheckWH : System.Web.UI.Page
{
    private INVCheckHead _head;

    private string OrderNumber
    {
        get
        {
            return WebUtil.Param("ordNum");
        }
    }
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            using (ISession session = new Session())
            {
                INVCheckHead head = INVCheckHead.Retrieve(session, this.OrderNumber);
                this.SetView(head);
                if (head.Status == INVCheckStatus.New)
                    this.BindArea(session, head);
                this.QueryAndBindData(session, head);
            }
        }
    }
    private void SetView(INVCheckHead head)
    {
        if (head == null || head.Status != INVCheckStatus.New)
        {
            this.sectionAdd.Visible = false;
            this.sectionAlert.Visible = false;
            this.cmdConfirm.Visible = false;
            if (head != null)
            {
                this.cmdViewDetail.Visible = true;
                this.cmdViewDetail["Detail"].NavigateUrl = "InventoryCheckDetail.aspx?ordNum=" + head.OrderNumber + "&return=" + Microsoft.JScript.GlobalObject.escape(WebUtil.Param("return"));
            }
            else this.cmdViewDetail.Visible = false;
        }
        else this.cmdViewDetail.Visible = false;
        this.cmdReturn["Return"].NavigateUrl = WebUtil.Param("return");
    }
    private void BindArea(ISession session, INVCheckHead head)
    {
        IList<WHArea> areas = session.CreateObjectQuery(@"
select 1 from WHArea where AreaCode not in(
    select distinct a.AreaCode 
    from INVCheckWh a 
    inner join INVCheckHead b on a.OrderNumber=b.OrderNumber 
    where b.Status=?s1
)
    and Status=?status and IsReservedArea=?isReserved and IsTransArea=?transArea and LocationCode=?loc and IsLocked=?islock
order by AreaCode")
            .Attach(typeof(WHArea)).Attach(typeof(INVCheckWh)).Attach(typeof(INVCheckHead))
            .SetValue("?s1", INVCheckStatus.New, EntityManager.GetPropMapping(typeof(INVCheckHead), "Status").DbTypeInfo)
            .SetValue("?status", WHStatus.Enable, "Status")
            .SetValue("?isReserved", false, "IsReservedArea")
            .SetValue("?transArea", true, "IsTransArea")
            .SetValue("?loc", head.LocationCode, "LocationCode")
            .SetValue("?islock", false, "IsLocked")
            .List<WHArea>();
        this.drpArea.Items.Clear();
        foreach (WHArea area in areas)
            this.drpArea.Items.Add(new ListItem(area.AreaCode, area.AreaCode));
    }
    private void QueryAndBindData(ISession session, INVCheckHead head)
    {
        this.repeaterControl.DataSource = session.CreateObjectQuery(@"
select 1 from WHArea where AreaCode in (select AreaCode from INVCheckWh where OrderNumber=?ordNum)
order by AreaCode")
            .Attach(typeof(WHArea)).Attach(typeof(INVCheckWh))
            .SetValue("?ordNum", this.OrderNumber, EntityManager.GetPropMapping(typeof(INVCheckWh), "OrderNumber").DbTypeInfo)
            .List<WHArea>();
        this._head = head;
        this.repeaterControl.DataBind();
        this._head = null;
    }
    protected void cmdDelete_Click(object sender, EventArgs e)
    {
        LinkButton cmd = sender as LinkButton;
        if (cmd == null) return;
        string area = cmd.Attributes["area"];
        if (string.IsNullOrEmpty(area) || area.Trim().Length <= 0) return;
        try
        {
            using (ISession session = new Session())
            {
                INVCheckHead head = INVCheckHead.Retrieve(session, this.OrderNumber);
                try
                {
                    session.BeginTransaction();
                    head.RemoveArea(session, area);
                    session.Commit();

                    this.BindArea(session, head);
                    this.QueryAndBindData(session, head);
                }
                catch (Exception err)
                {
                    session.Rollback();
                    WebUtil.ShowError(this, err);
                    return;
                }
            }
        }
        catch (Exception er)
        {
            WebUtil.ShowError(this, er);
        }
    }
    protected void MagicItemCommand(object sender, MagicItemEventArgs e)
    {
        try
        {
            if (e.CommandName == "Add" && !string.IsNullOrEmpty(this.drpArea.SelectedValue))
            {
                #region 添加库位
                using (ISession session = new Session())
                {
                    INVCheckHead head = INVCheckHead.Retrieve(session, this.OrderNumber);
                    try
                    {
                        session.BeginTransaction();
                        head.AddArea(session, this.drpArea.SelectedValue);
                        session.Commit();

                        this.BindArea(session, head);
                        this.QueryAndBindData(session, head);
                    }
                    catch (Exception er1)
                    {
                        session.Rollback();
                        WebUtil.ShowError(this, er1);
                        return;
                    }
                }
                #endregion
            }
            else if (e.CommandName == "Confirm")
            {
                #region 确定开始盘点作业
                bool success = false;
                using (ISession session = new Session())
                {
                    INVCheckHead head = INVCheckHead.Retrieve(session, this.OrderNumber);
                    try
                    {
                        session.BeginTransaction();
                        head.ConfirmCheckOrder(session);
                        session.Commit();
                        success = true;
                    }
                    catch (Exception er1)
                    {
                        session.Rollback();
                        WebUtil.ShowError(this, er1);
                        return;
                    }
                }
                #endregion
                if (success)
                    this.Response.Redirect("InventoryCheckDetail.aspx?ordNum="+this.OrderNumber+"&return="+WebUtil.escape(WebUtil.Param("return")));
            }
        }
        catch (Exception er)
        {
            WebUtil.ShowError(this, er);
        }
    }
    protected void repeaterControl_ItemDataBound(object sender, RepeaterItemEventArgs e)
    {
        LinkButton cmd = e.Item.FindControl("cmdDelete") as LinkButton;
        if (this._head.Status != INVCheckStatus.New)
            cmd.Visible = false;
    }
}