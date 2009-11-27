using System;
using System.Collections.Generic;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.HtmlControls;
using Magic.Framework.Utils;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Query;
using Magic.Web.UI;
using Magic.ERP;
using Magic.ERP.Orders;

public partial class Inventory_InventoryCheckManage : System.Web.UI.Page
{
    log4net.ILog logger = log4net.LogManager.GetLogger(typeof(Inventory_InventoryCheckManage));

    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            this.txtBeginDate.Text = DateTime.Now.AddMonths(-1).ToString("yyyy-MM-dd");
            this.txtEndDate.Text = DateTime.Now.ToString("yyyy-MM-dd");

            WebUtil.SetMagicPager(magicPagerMain, magicPagerMain.PageSize, 1);
            WebUtil.SetMagicPager(magicPagerSub, magicPagerMain.PageSize, 1);
            this.hidReturnUrl.Value = this.GetReturnUrl();

            using (ISession session = new Session())
            {
                RestoreLastQuery(session);
            }
        }
    }

    protected void MagicPager_PageChanged(object src, Magic.Web.UI.PageChangedEventArgs e)
    {
        //翻页事件
        this.magicPagerSub.PageSize = this.magicPagerMain.PageSize; //如果页面有2个翻页控件，则必须写上这一句(控件bug)
        using (ISession session = new Session())
        {
            QueryAndBindData(session, e.NewPageIndex, e.Pager.PageSize, false);
        }
    }

    protected void btnQuery_Click(object sender, ImageClickEventArgs e)
    {
        using (ISession session = new Session())
        {
            QueryAndBindData(session, 1, this.magicPagerMain.PageSize, true);
        }
    }

    protected void MagicItemCommand(object sender, MagicItemEventArgs e)
    {
        if (e.CommandName == "Delete")
        {
            try
            {
                using (ISession session = new Session())
                {
                    session.BeginTransaction();
                    try
                    {
                        for (int i = 0; i < this.rptSDHead.Items.Count; i++)
                        {
                            System.Web.UI.HtmlControls.HtmlInputCheckBox objCheckBox = this.rptSDHead.Items[i].FindControl("checkbox") as System.Web.UI.HtmlControls.HtmlInputCheckBox;
                            if (objCheckBox.Checked)
                                INVCheckHead.Delete(session, objCheckBox.Attributes["value"].Trim());
                        }
                        session.Commit();
                        QueryAndBindData(session, 1, this.magicPagerMain.PageSize, true);
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
                WebUtil.ShowError(this, "删除失败,请与管理员联系!\r\n失败信息:" + ex.Message);
                return;
            }
        }
    }

    private void RestoreLastQuery(ISession session)
    {
        QueryHelper helper = new QueryHelper(this);
        if (helper.HasQueryParameter())
        {
            helper.SetValue(this.txtBeginDate);
            helper.SetValue(this.txtEndDate);
            helper.SetValue(this.txtInvCheckNumbere);

            string strStatus = helper.Pop("Status");
            for (int i = 0; i < this.cklStatus.Items.Count; i++)
                this.cklStatus.Items[i].Selected = false;
            if (strStatus.Trim().Length > 0)
            {
                string[] lstStatus = strStatus.Split(',');
                for (int i = 0; i < lstStatus.Length; i++)
                {
                    ListItem Item = this.cklStatus.Items.FindByValue(lstStatus[i]);
                    Item.Selected = true;
                }
            }
        }
        int pageSize = Cast.Int(helper.Pop("ps"), this.magicPagerMain.PageSize);
        int pageIndex = Cast.Int(helper.Pop("pi"), 1);
        this.QueryAndBindData(session, pageIndex, pageSize, true);
    }

    private string GetReturnUrl()
    {
        QueryHelper helper = new QueryHelper(this);
        string strStatus = "";
        for (int i = 0; i < this.cklStatus.Items.Count; i++)
        {
            if (this.cklStatus.Items[i].Selected)
            {
                if (strStatus.Trim().Length == 0)
                    strStatus = this.cklStatus.Items[i].Value;
                else
                    strStatus += "," + this.cklStatus.Items[i].Value;
            }
        }
        helper.Push("Status", strStatus); //传参_方法2
        helper.GetValue(this.txtBeginDate);
        helper.GetValue(this.txtEndDate);
        helper.GetValue(this.txtInvCheckNumbere);
        helper.Push("ps", this.magicPagerMain.PageSize);
        helper.Push("pi", this.magicPagerMain.CurrentPageIndex);
        return helper.OutputReturnUrl();
    }

    private void QueryAndBindData(ISession session, int pageIndex, int pageSize, bool fetchRecordCount)
    {
        try
        {
            string strInvCheckNumber = this.txtInvCheckNumbere.Text.Trim().ToUpper();
            DateTime dtBegin = this.txtBeginDate.Text.Trim().Length == 0 ? DateTime.Parse("1900-1-1") : DateTime.Parse(this.txtBeginDate.Text);
            DateTime dtEnd = this.txtEndDate.Text.Trim().Length == 0 ? DateTime.Parse("2900-1-1") : DateTime.Parse(this.txtEndDate.Text);
            IList<INVCheckStatus> lstStatus = new List<INVCheckStatus>();
            for (int i = 0; i < this.cklStatus.Items.Count; i++)
            {
                if (this.cklStatus.Items[i].Selected)
                    lstStatus.Add(Cast.Enum<INVCheckStatus>(Cast.Int(this.cklStatus.Items[i].Value.Trim())));
            }

            ObjectQuery query = session.CreateObjectQuery(@"
SELECT A.OrderNumber AS OrderNumber,loc.Name as LocName,A.Note AS Note,C.StatusText AS StatusText,A.ApproveResult as ApproveResult
    ,D.FullName AS UserName,A.CreateTime AS CreateDate,A.Status AS Status
FROM INVCheckHead A
LEFT JOIN OrderStatusDef C ON A.Status=C.StatusValue AND C.OrderTypeCode=A.OrderTypeCode
LEFT JOIN User D ON A.CreateUser=D.UserId
left join WHLocation loc on loc.LocationCode=A.LocationCode
WHERE A.OrderTypeCode=?
order by A.OrderNumber desc")
            .Attach(typeof(INVCheckHead)).Attach(typeof(Magic.ERP.Core.WHLocation))
            .Attach(typeof(Magic.ERP.Core.OrderStatusDef))
            .Attach(typeof(Magic.Sys.User))
            .SetValue(0, INVCheckHead.ORDER_TYPE_CHK, "A.OrderTypeCode")
            .And(Exp.In("A.Status", lstStatus))
            .SetPage(pageIndex, pageSize);

            if (!string.IsNullOrEmpty(strInvCheckNumber)) query.And(Exp.Like("A.OrderNumber", "%" + strInvCheckNumber + "%"));
            if (dtBegin > new DateTime(1900, 1, 1)) query.And(Exp.Ge("A.CreateTime", dtBegin));
            if (dtEnd > new DateTime(1900, 1, 1)) query.And(Exp.Le("A.CreateTime", dtEnd.AddDays(1)));

            this.rptSDHead.DataSource = query.DataSet();
            this.rptSDHead.DataBind();

            if (fetchRecordCount)
                this.magicPagerMain.RecordCount = this.magicPagerSub.RecordCount = query.Count();

            WebUtil.SetMagicPager(magicPagerMain, pageSize, pageIndex);
            WebUtil.SetMagicPager(magicPagerSub, pageSize, pageIndex);

            this.hidReturnUrl.Value = GetReturnUrl();
        }
        catch (Exception ex)
        {
            logger.Info("查询库房盘点单失败", ex);
            WebUtil.ShowError(this, ex);
        }
    }
    protected void rptSDHead_ItemDataBound(object sender, RepeaterItemEventArgs e)
    {
        System.Data.DataRowView drv = e.Item.DataItem as System.Data.DataRowView;
        if (drv == null) return;
        INVCheckStatus status = Cast.Enum<INVCheckStatus>(drv["Status"]);
        if (status != INVCheckStatus.New)
        {
            HtmlInputCheckBox chk = e.Item.FindControl("checkbox") as HtmlInputCheckBox;
            if (chk == null) return;
            chk.Visible = false;
        }
        HtmlAnchor cmd = e.Item.FindControl("cmdDetail") as HtmlAnchor;
        if (status == INVCheckStatus.New)
            cmd.Visible = false;
        else
            cmd.HRef = "javascript:ondetail('" + Cast.String(drv["OrderNumber"]) + "');";
        Label lblApprStatus = e.Item.FindControl("lblApprStatus") as Label;
        ApproveStatus apprStatus = Cast.Enum<ApproveStatus>(drv["ApproveResult"]);
        lblApprStatus.Text = ERPUtil.EnumText<ApproveStatus>(apprStatus);
        switch (apprStatus)
        {
            case ApproveStatus.UnApprove:
                lblApprStatus.Text="";
                break;
            case ApproveStatus.Approve:
                lblApprStatus.ForeColor = System.Drawing.Color.Blue;
                break;
            case ApproveStatus.Reject:
                lblApprStatus.ForeColor = System.Drawing.Color.Red;
                break;
        }
    }
}