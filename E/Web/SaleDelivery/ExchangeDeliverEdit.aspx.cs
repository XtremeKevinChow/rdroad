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

public partial class ExchangeDeliverEdit : System.Web.UI.Page
{
    ISession _session = null;
    IList<Magic.ERP.Core.WHArea> m_Areas = null;
    log4net.ILog logger = log4net.LogManager.GetLogger(typeof(User));

    protected void Page_Load(object sender, EventArgs e)
    {
        this.rptSDLine.ItemDataBound += new RepeaterItemEventHandler(rptSDLine_ItemDataBound);
        this.rptSDLine.ItemCreated += new RepeaterItemEventHandler(rptSDLine_ItemCreated);

        if (!IsPostBack)
        {
            string strSDNumber = Request.QueryString["SDNumber"];
            if ((strSDNumber == null) || (strSDNumber.Trim().Length == 0))
            {
                this.InitialPage();
            }
            else
            {
                using (_session = new Session())
                {
                    this.LoadSD_Head(_session, strSDNumber);
                    this.LoadSD_Line(_session, strSDNumber);
                }
            }
        }
        //返回用户列表页面的URL（包含了用户列表页面的查询条件）
        //if (Request["return"] != null) this.toolbarbottom["Return"].NavigateUrl = Request["return"];
        //if (!IsPostBack)
        //{
        //    using (_session = new Session())
        //    {
        //        //有些情况下客户的组织结构没有什么要求，不需要选择组织结构，因此使用默认值
        //        if (Org.UseDefaultOrg(_session)) this.trOrg.Visible = false;

        //        InitializeDropDownList();
        //        _actionMode = WebUtil.GetActionMode(this);
        //        if (_actionMode == Mode.Edit) RetrieveUserData(_session); //如果时编辑用户，则加载显示用户资料
        //    }

        //    //不允许编辑帐号，但新增的时候需要输入
        //    if (this.IsAddNew())
        //    {
        //        this.txtUserName.ReadOnly = false;
        //        this.txtUserName.CssClass = "input";
        //    }
        //    else
        //    {
        //        this.txtUserName.ReadOnly = true;
        //        this.txtUserName.CssClass = "input readonly";
        //    }
        //}
    }

    void rptSDLine_ItemCreated(object sender, RepeaterItemEventArgs e)
    {
        if ((e.Item.ItemType == ListItemType.Item) || (e.Item.ItemType == ListItemType.AlternatingItem))
        {
            if (this.m_Areas == null)
            {
                IList<Magic.ERP.Core.WHArea> Areas = Magic.ERP.ERPUtil.GetWHArea(_session, Magic.ERP.Orders.SDHead.ORDER_TYPE, null);

                this.m_Areas = Areas;
            }

            DropDownList ddlArea = e.Item.FindControl("ddlArea") as DropDownList;
            ddlArea.DataSource = this.m_Areas;
            ddlArea.DataBind();
        }
    }

    void rptSDLine_ItemDataBound(object sender, RepeaterItemEventArgs e)
    {
        if ((e.Item.ItemType == ListItemType.Item) || (e.Item.ItemType == ListItemType.AlternatingItem))
        {
            Repeater repSDLine = sender as Repeater ;
            DropDownList ddlArea = e.Item.FindControl("ddlArea") as DropDownList;
            ddlArea.SelectedValue = (repSDLine.DataSource as DataSet).Tables[0].Rows[e.Item.ItemIndex]["AreaCode"].ToString().Trim();
            ddlArea_SelectedIndexChanged(ddlArea, null);
            DropDownList ddlSection = e.Item.FindControl("ddlSection") as DropDownList;
            ddlSection.SelectedValue = (repSDLine.DataSource as DataSet).Tables[0].Rows[e.Item.ItemIndex]["SectionCode"].ToString().Trim();
        }
    }

    protected void ddlArea_SelectedIndexChanged(object sender, EventArgs e)
    {
        DropDownList ddlArea = sender as DropDownList;
        DropDownList ddlSection = ddlArea.Parent.FindControl("ddlSection") as DropDownList;

        using (_session = new Session())
        {
            ObjectQuery query = _session.CreateObjectQuery(@"
SELECT A.SectionCode AS SectionCode,A.Text AS Text 
FROM WHSection A WHERE A.AreaCode=?
")
    .Attach(typeof(Magic.ERP.Core.WHSection))
    .SetValue(0, ddlArea.SelectedValue, "A.AreaCode");

            DataTable dt = query.DataSet().Tables[0];
            ddlSection.DataSource = dt;
            ddlSection.DataBind();
            ddlSection.Items.Insert(0, new ListItem("", ""));
        }
    }

    protected void MagicItemCommand(object sender, MagicItemEventArgs e)
    {
        if (e.CommandName == "Save")
        {
            SaveSaleDeliver();
        }
        if (e.CommandName == "Return")
        {
            string strURL = "ExchangeDeliverManage.aspx" ;
            if ((Request.QueryString["return"] != null) && (Request.QueryString["return"].Trim().Length > 0))
            {
                strURL = Request.QueryString["return"].Trim();
            }
            Response.Redirect(strURL);
        }
    }

    private void SaveSaleDeliver()
    {
        try
        {
            using (_session = new Session())
            {
                if (Magic.ERP.Orders.EDHead.Retrieve(_session, this.txtSaleDeliver.Text).Status != Magic.ERP.DeliverStatus.New)
                {
                    WebUtil.ShowMsg(this, "只有状态为新建的换货发货单才可以修改保存!");
                    return;
                }
            }

            List<Magic.ERP.Orders.SDLine> List = new List<Magic.ERP.Orders.SDLine>();
            for (int i = 0; i < this.rptSDLine.Items.Count; i++)
            {
                Magic.ERP.Orders.SDLine objLine = new Magic.ERP.Orders.SDLine();
                objLine.AreaCode = (this.rptSDLine.Items[i].FindControl("ddlArea") as DropDownList).SelectedValue;
                objLine.SectionCode = (this.rptSDLine.Items[i].FindControl("ddlSection") as DropDownList).SelectedValue;
                objLine.OrderNumber = this.txtSaleDeliver.Text;
                objLine.LineNumber = (this.rptSDLine.Items[i].FindControl("tdLineNumber") as System.Web.UI.HtmlControls.HtmlTableCell).InnerText.Replace("\r\n","").Trim();
                if (objLine.AreaCode.Trim().Length == 0)
                {
                    WebUtil.ShowMsg(this, "发货单明细"+objLine.LineNumber+"的仓库区域不能为空,请重新输入!", "保存失败");
                    return;
                }
                List.Add(objLine);
            }

            using (_session = new Session())
            {
                _session.BeginTransaction() ;
                try
                {
                    for (int i = 0; i < List.Count; i++)
                    {
                        EntityManager.Update(_session, List[i], "AreaCode", "SectionCode");
                    }
                    _session.Commit();
                }
                catch (Exception ex)
                {
                    _session.Rollback();
                    throw ex;
                }
            }

            WebUtil.ShowMsg(this, "发货单保存成功", "操作成功");
        }
        catch (Exception ex)
        {
            logger.Info("保存发货单", ex);
            WebUtil.ShowMsg(this, "发生未处理的异常,请刷新页面重新操作，或者联系系统管理员");
        }
    }

    private bool IsAddNew()
    {
        //return Cast.Int(this.txtUserId.Value.Trim(), -1) <= 0;
        return false;
    }

    private void LoadSD_Head(ISession Session,string OrderNumber)
    {
        ObjectQuery query = Session.CreateObjectQuery(@"
SELECT A.*,B.OrderNumber AS ICNumber,C.StatusText AS StatusText,D.Name AS CusName,'Cus_Addr' AS CusAddress 
FROM SDHead A
LEFT JOIN ICLine B ON A.OrderNumber=B.RefOrderNumber
LEFT JOIN Member D ON A.CustomerID = D.MemberID
LEFT JOIN OrderStatusDef C ON A.Status=C.StatusValue AND C.OrderTypeCode=A.OrderTypeCode
WHERE A.OrderNumber=?
")
            .Attach(typeof(Magic.ERP.Orders.SDHead))
            .Attach(typeof(Magic.ERP.Orders.ICLine))
            .Attach(typeof(Magic.Basis.Member))
            .Attach(typeof(Magic.ERP.Core.OrderStatusDef))
            .SetValue(0, OrderNumber, "A.OrderNumber");
        DataSet ds = query.DataSet();

        if (ds.Tables[0].Rows.Count == 0)
        {
            this.InitialPage();
        }
        else
        {
            this.txtAddress.Text = ds.Tables[0].Rows[0]["CusAddress"].ToString();
            this.txtCusName.Text = ds.Tables[0].Rows[0]["CusName"].ToString();
            this.txtInterChange.Text = ds.Tables[0].Rows[0]["ICNumber"].ToString();
            this.txtMemo.Text = ds.Tables[0].Rows[0]["ORD_NOTE"].ToString();
            this.txtSaleDeliver.Text = ds.Tables[0].Rows[0]["ORD_NUM"].ToString();
            this.txtSaleOrder.Text = ds.Tables[0].Rows[0]["REF_ORD_NUM"].ToString();
            this.txtStatus.Text = ds.Tables[0].Rows[0]["StatusText"].ToString();
        }
    }

    private void LoadSD_Line(ISession Session, string OrderNumber)
    {
        ObjectQuery query = Session.CreateObjectQuery(@"
SELECT A.OrderNumber AS OrderNumber,A.LineNumber AS SDLineNumber,A.RefOrderLine AS SOLineNumber,C.ItemCode AS ItemCode,
        C.ItemName AS ItemName,E.SizeText AS SizeText,D.ColorText AS ColorText,A.ShipQty AS ShipQty,A.AreaCode AS AreaCode,A.SectionCode AS SectionCode
FROM SDLine A
LEFT JOIN ItemSpec B ON A.SKUID=B.SKUID
LEFT JOIN ItemMaster C ON B.ItemID=C.ItemID
LEFT JOIN ItemColor D ON B.ColorCode=D.ColorCode 
LEFT JOIN ItemSize E ON B.SizeCode=E.SizeCode
where A.OrderNumber=?
")
            .Attach(typeof(Magic.ERP.Orders.SDLine))
            .Attach(typeof(Magic.Basis.ItemSpec))
            .Attach(typeof(Magic.Basis.ItemMaster))
            .Attach(typeof(Magic.Basis.ItemColor))
            .Attach(typeof(Magic.Basis.ItemSize))
            .SetValue(0, OrderNumber, "A.OrderNumber");

        this.rptSDLine.DataSource = query.DataSet();
        this.rptSDLine.DataBind();
    }

    private void InitialPage()
    {
        this.txtAddress.Text = "";
        this.txtAddress.Enabled = false;
        this.txtCusName.Text = "";
        this.txtCusName.Enabled = false;
        this.txtInterChange.Text = "";
        this.txtInterChange.Enabled = false;
        this.txtMemo.Text = "";
        this.txtMemo.Enabled = false;
        this.txtSaleDeliver.Text = "";
        this.txtSaleDeliver.Enabled = false;
        this.txtSaleOrder.Text = "";
        this.txtSaleOrder.Enabled = false;
        this.txtStatus.Text = "";
        this.txtStatus.Enabled = false;
    }
}