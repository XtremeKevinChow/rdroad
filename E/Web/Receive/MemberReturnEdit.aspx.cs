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

public partial class Receive_MemberReturnEdit : System.Web.UI.Page
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
            this.chk.Checked = head.IsMalicious;
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
            WebUtil.DisableControl(this.chk);
            this.cmdEdit.Visible = false;
        }
        else if (head != null && head.OrderTypeCode == ReturnHead.ORDER_TYPE_EXCHANGE_RTN)
        {
            //换货退货单，不允许修改发货单号码
            WebUtil.DisableControl(this.txtSNNumber);
            WebUtil.DisableControl(this.chk);
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
                this.drpLocation.Items.Add(new ListItem("　", ""));
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
    private bool IsAllChangeReturn(IList<ReturnHead> heads)
    {
        foreach (ReturnHead h in heads)
            if (h.OrderTypeCode != ReturnHead.ORDER_TYPE_EXCHANGE_RTN) return false;
        return true;
    }
    private int CountOfNewChangeReturn(IList<ReturnHead> heads)
    {
        int count = 0;
        foreach (ReturnHead h in heads)
            if (h.OrderTypeCode == ReturnHead.ORDER_TYPE_EXCHANGE_RTN && h.Status == ReturnStatus.New) count++;
        return count;
    }
    protected void MagicItemCommand(object sender, MagicItemEventArgs e)
    {
        if (e.CommandName == "Save")
        {
            using (ISession session = new Session())
            {
                ReturnHead head = null;
                #region 更新退货单的情况
                if (!this.IsNew)
                {
                    head = ReturnHead.Retrieve(session, this.txtOrderNumber.Text.Trim());
                    if (head == null)
                    {
                        WebUtil.ShowError(this, "退货单" + this.txtOrderNumber.Text.Trim() + "不存在");
                        return;
                    }
                    if (head.Status != ReturnStatus.New)
                    {
                        WebUtil.ShowError(this, "退货单" + head.OrderNumber + "不是新建状态，无法更新");
                        return;
                    }
                    try
                    {
                        head.MemberReturn(session, this.drpLocation.SelectedValue, this.txtSNNumber.Text, Cast.Int(this.drpReason.SelectedValue, 0), this.chk.Checked, this.txtNote.Text.Trim(), SecuritySession.CurrentUser.UserId);
                        session.BeginTransaction();
                        head.Update(session, "Note", "LocationCode", "ReasonID", "ReasonText", "IsMalicious", "RefOrderID", "RefOrderNumber", "OrginalOrderNumber", "LogisticsName", "LogisticsID", "MemberName", "MemberID");
                        session.Commit();
                        WebUtil.ShowMsg(this, "保存成功");
                    }
                    catch (Exception er2)
                    {
                        session.Rollback();
                        WebUtil.ShowError(this, er2);
                    }
                    return;
                }
                #endregion
                //根据发货单查询是否存在退货单，2个作用
                //1. 换货的情况下，创建换货订单同时已经创建了换货退货单，这种情况要更新一下换货退货单资料（仓库、退货原因、备注）而不用新增
                //2. 目前不允许一个订单多次退货，即发货单如果已经用于创建退货单，则不再允许创建第2个了
                IList<ReturnHead> heads = ReturnHead.QueryBySNNumber(session, this.txtSNNumber.Text.Trim());
                #region 从来没有(会员退货、物流退货、内部退货)，允许退货，以下代码创建新的会员退货单
                if (heads.Count <= 0)
                {
                    head = new ReturnHead();
                    try
                    {
                        head.MemberReturn(session, this.drpLocation.SelectedValue, this.txtSNNumber.Text, Cast.Int(this.drpReason.SelectedValue, 0), this.chk.Checked, this.txtNote.Text.Trim(), SecuritySession.CurrentUser.UserId);
                        head.OrderNumber = ERPUtil.NextOrderNumber(head.OrderTypeCode);
                        session.BeginTransaction();
                        head.Create(session);
                        session.Commit();

                        this.Response.Redirect("MemberReturnScan.aspx?ordNum=" + head.OrderNumber + "&return=" + WebUtil.escape("MemberReturnLine.aspx?ordNum=" + head.OrderNumber + "&return=" + WebUtil.escape(this.ReturnUrl)));
                    }
                    catch (Exception er)
                    {
                        session.Rollback();
                        WebUtil.ShowError(this, er);
                    }

                    return;
                }
                #endregion
                int countOfNewChangeReturn = this.CountOfNewChangeReturn(heads);
                #region 有新建状态的换货单，直接调出该换货单，更新其资料，更新完后跳转到退货扫描界面
                //如果已经部分退货过一次，下面的代码允许继续换货
                if (countOfNewChangeReturn > 0)
                {
                    foreach (ReturnHead h in heads)
                        if (h.Status == ReturnStatus.New && h.OrderTypeCode == ReturnHead.ORDER_TYPE_EXCHANGE_RTN)
                        {
                            head = h;
                            break;
                        }
                    if (head != null)
                    {
                        try
                        {
                            head.ExchangeReturn(session, this.drpLocation.SelectedValue, Cast.Int(this.drpReason.SelectedValue), this.txtNote.Text.Trim(), SecuritySession.CurrentUser.UserId);
                            session.BeginTransaction();
                            head.Update(session, "LocationCode", "ReasonID", "ReasonText", "Note", "CreateUser");
                            session.Commit();
                            this.Response.Redirect("ExchangeReturnScan.aspx?ordNum=" + head.OrderNumber + "&return=" + WebUtil.escape("MemberReturnLine.aspx?ordNum=" + head.OrderNumber + "&return=" + WebUtil.escape(WebUtil.Param("return"))));
                            return;
                        }
                        catch (Exception er5)
                        {
                            session.Rollback();
                            WebUtil.ShowError(this, er5);
                        }
                    }
                    return;
                }
                #endregion
                #region 如果有未完成的退货单，必须完成后才可以新建另外的退货
                foreach (ReturnHead h in heads)
                {
                    if (h.Status != ReturnStatus.Close)
                    {
                        WebUtil.ShowError(this, "与发货单" + h.RefOrderNumber + "相关的退货单" + h.OrderNumber + "还没有完成，无法创建另外的退货单");
                        return;
                    }
                }
                #endregion
                #region 如果没有创建过任何会员退货、物流退货、内部退货，则允许创建一个（这几种退货只能退一次）
                if (this.IsAllChangeReturn(heads))
                {
                    head = new ReturnHead();
                    try
                    {
                        head.MemberReturn(session, this.drpLocation.SelectedValue, this.txtSNNumber.Text, Cast.Int(this.drpReason.SelectedValue, 0), this.chk.Checked, this.txtNote.Text.Trim(), SecuritySession.CurrentUser.UserId);
                        head.OrderNumber = ERPUtil.NextOrderNumber(head.OrderTypeCode);
                        session.BeginTransaction();
                        head.Create(session);
                        session.Commit();

                        this.Response.Redirect("MemberReturnScan.aspx?ordNum=" + head.OrderNumber + "&return=" + WebUtil.escape("MemberReturnLine.aspx?ordNum=" + head.OrderNumber + "&return=" + WebUtil.escape(this.ReturnUrl)));
                    }
                    catch (Exception er)
                    {
                        session.Rollback();
                        WebUtil.ShowError(this, er);
                    }

                    return;
                }
                #endregion
                #region 其它情况将无法再创建会员退货单
                if (this.IsNew)
                {
                    WebUtil.ShowError(this, "同一个订单（发货单）不允许多次退货，系统无法再创建退货单");
                    return;
                }
                #endregion
            }
        }
    }
}