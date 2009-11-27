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

public partial class StockAdjustment : System.Web.UI.Page
{
    ISession _session = null;
    IList<Magic.ERP.Core.WHArea> m_Areas = null;
    log4net.ILog logger = log4net.LogManager.GetLogger(typeof(User));
    string m_OrderNumber = "";

    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            Session["InvAdjustmentItem"] = null;
            WebUtil.DisableControl(this.txtInvCheckNumber);
            WebUtil.DisableControl(this.txtUser);
            WebUtil.DisableControl(this.txtStatus);

            this.ddlLocation.SelectedIndexChanged += new EventHandler(ddlLocation_SelectedIndexChanged);
            this.ddlArea.SelectedIndexChanged += new EventHandler(ddlArea_SelectedIndexChanged);
            this.ddlSection.SelectedIndexChanged += new EventHandler(ddlSection_SelectedIndexChanged);

            if(this.tblAddItem.Style["display"]==null)
            {
                this.tblAddItem.Style.Add("display","none") ;
            }
            else
            {
                this.tblAddItem.Style["display"] = "none" ;
            }

            string strAdjustmentNumber = WebUtil.Param("AdjustmentNumber");
            m_OrderNumber = strAdjustmentNumber;

            this.InitialPage();

            if ((strAdjustmentNumber == null) || (strAdjustmentNumber.Trim().Length == 0))
            {
                ;
            }
            else
            {
                using (_session = new Session())
                {
                    this.LoadCheckOrder_Head(_session, strAdjustmentNumber);
                    this.LoadCheckOrder_Item(_session, strAdjustmentNumber);
                }
            }
        }
    }

    protected void ddlSection_SelectedIndexChanged(object sender, EventArgs e)
    {
        using (_session = new Session())
        {
            if (this.hidSkuId.Value.Trim().Length > 0)
            {
                LoadInventoryStock(_session, int.Parse(this.hidSkuId.Value), this.ddlLocation.SelectedValue.Trim(), this.ddlArea.SelectedValue.Trim(), this.ddlSection.SelectedValue);
            }
        }
    }

    private void LoadInventoryStock(ISession session,int SKUID, string LocationCode, string AreaCode, string SectionCode)
    {
        ObjectQuery query = session.CreateObjectQuery(@"
SELECT SUM(A.StockQty) AS StockQty
FROM StockDetail A
WHERE A.LocationCode=? and A.AreaCode=? AND A.SectionCode=? AND A.SKUID=?
")
                    .Attach(typeof(Magic.ERP.Core.StockDetail))
                    .Attach(typeof(Magic.ERP.Core.WHLocation))
                    .Attach(typeof(Magic.ERP.Core.WHArea))
                    .Attach(typeof(Magic.ERP.Core.WHSection))
                    .Attach(typeof(Magic.Basis.ItemSpec))
                    .Attach(typeof(Magic.Basis.ItemMaster))
                    .Attach(typeof(Magic.Basis.ItemColor))
                    .Attach(typeof(Magic.Basis.ItemSize))
                    .SetValue(0, LocationCode, "A.LocationCode")
                    .SetValue(1, AreaCode, "A.AreaCode")
                    .SetValue(2, SectionCode, "A.SectionCode")
                    .SetValue(3, SKUID, "A.SKUID");
        DataSet ds = query.DataSet();
        if (ds.Tables[0].Rows.Count == 0)
        {
            this.txtOldQuantity.Value = "0";
        }
        else
        {
            this.txtOldQuantity.Value = Cast.Decimal(ds.Tables[0].Rows[0][0]).ToString();
        }

        if (this.tblAddItem.Style["display"] == null)
        {
            this.tblAddItem.Style.Add("display", "block");
        }
        else
        {
            this.tblAddItem.Style["display"] = "block";
        }
    }

    protected void ddlArea_SelectedIndexChanged(object sender, EventArgs e)
    {
        if (this.ddlArea.SelectedValue.Trim().Length > 0)
        {
            this.ddlSection.Items.Clear();
            this.ddlSection.Items.Add(new ListItem("", ""));

            using (_session = new Session())
            {
                Magic.ERP.Core.WHRepository repository = new WHRepository(_session);
                foreach (WHSection section in repository.GetSections(this.ddlArea.SelectedValue))
                {
                    this.ddlSection.Items.Add(new ListItem(section.Text, section.SectionCode));
                }

                if (this.hidSkuId.Value.Trim().Length > 0)
                {
                    LoadInventoryStock(_session, int.Parse(this.hidSkuId.Value), this.ddlLocation.SelectedValue.Trim(), this.ddlArea.SelectedValue.Trim(), this.ddlSection.SelectedValue);
                }
            }
        }
        else
        {
            this.ddlSection.Items.Clear();
        }
    }

    protected void ddlLocation_SelectedIndexChanged(object sender, EventArgs e)
    {
        if (this.ddlLocation.SelectedValue.Trim().Length > 0)
        {
            this.ddlArea.Items.Clear();
            this.ddlArea.Items.Add(new ListItem("", ""));
            using (_session = new Session())
            {
                Magic.ERP.Core.WHRepository repository = new WHRepository(_session);
                foreach (WHArea area in repository.GetAreas(this.ddlLocation.SelectedValue.Trim()))
                {
                    this.ddlArea.Items.Add(new ListItem(area.Name, area.AreaCode));
                }

                if (this.hidSkuId.Value.Trim().Length > 0)
                {
                    LoadInventoryStock(_session, int.Parse(this.hidSkuId.Value), this.ddlLocation.SelectedValue.Trim(), this.ddlArea.SelectedValue.Trim(), this.ddlSection.SelectedValue);
                }
            }
        }
        else
        {
            this.ddlArea.Items.Clear();
        }
    }

    protected void MagicItemCommand(object sender, MagicItemEventArgs e)
    {
        if (e.CommandName == "Save")
        {
            SaveCheckOrder();
        }

        if (e.CommandName == "Close")
        {
            try
            {
                if (this.txtInvCheckNumber.Text.Trim().Length == 0)
                {
                    WebUtil.ShowMsg(this, "只有签核通过的库存调整单才可以关闭!");
                    return;
                }

                using (_session = new Session())
                {
                    Magic.ERP.Orders.INVCheckHead objhead = Magic.ERP.Orders.INVCheckHead.Retrieve(_session, this.txtInvCheckNumber.Text.Trim());
                    if ((objhead.Status == Magic.ERP.INVCheckStatus.Release) && (objhead.ApproveResult == Magic.ERP.ApproveStatus.Approve))
                    {
                        Magic.ERP.ERPUtil.CommitWHTrans(_session, objhead);
                    }
                    else
                    {
                        WebUtil.ShowMsg(this, "只有签核通过的库存调整单才可以关闭!");
                        return;
                    }
                }
            }
            catch (Exception ex)
            {
                WebUtil.ShowMsg(this,"关闭库存调整单失败,请与管理员联系!");
            }
        }

        if (e.CommandName == "Insert")
        {
            try
            {
                if (this.hidSkuId.Value.Trim().Length == 0)
                {
                    WebUtil.ShowMsg(this, "请先选择物料!");
                    return;
                }
                string strSKUID = this.hidSkuId.Value;
                string strLocation = this.ddlLocation.SelectedValue;
                string strArea = this.ddlArea.SelectedValue;
                string strSection = this.ddlSection.SelectedValue;
                int intNewQuantity = 0;
                try
                {
                    intNewQuantity = int.Parse(this.txtNewQuantity.Text);
                    if (intNewQuantity < 0)
                    {
                        WebUtil.ShowMsg(this, "物料库存数量必须为整数,请重新输入!");
                        return;
                    }
                }
                catch (Exception ex)
                {
                    WebUtil.ShowMsg(this, "物料库存数量必须为整数,请重新输入!");
                    return;
                }

                using(_session = new Session())
                {
                }

                Magic.ERP.Orders.INVCheckLine objLine = new INVCheckLine();
                objLine.AreaCode = strArea;
                objLine.BeforeQty = int.Parse(this.txtOldQuantity.Value);
                objLine.CurrentQty = intNewQuantity;
                objLine.LineNumber = "";
                objLine.LocationCode = strLocation;
                objLine.OrderNumber = this.txtInvCheckNumber.Text;
                objLine.SectionCode = strSection;
                objLine.SKUID = int.Parse(strSKUID);

                SaveItem(objLine,this.txtItemName.Value,this.txtItemSize.Value,this.txtItemColor.Value,this.ddlLocation.SelectedItem.Text,
                            this.ddlArea.SelectedItem.Text,this.ddlSection.SelectedItem.Text);

                if (this.tblAddItem.Style["display"] == null)
                {
                    this.tblAddItem.Style.Add("display", "none");
                }
                else
                {
                    this.tblAddItem.Style["display"] = "none";
                }

                this.txtItemName.Value = "";
                this.txtItemColor.Value = "";
                this.txtItemSize.Value = "";
                this.ddlLocation.SelectedIndex = 0;
                this.txtOldQuantity.Value = "0";
                this.txtNewQuantity.Text = "";
                this.ddlArea.Items.Clear();
                this.ddlSection.Items.Clear();
            }
            catch (Exception ex)
            {
                logger.Error(ex);
                WebUtil.ShowError(this,ex);
            }
        }

        if (e.CommandName == "Return")
        {
            string strURL = "StockAdjustmentManage.aspx" ;
            if ((WebUtil.Param("return") != null) && (WebUtil.Param("return").Length > 0))
            {
                strURL = WebUtil.Param("return").Trim();
            }
            Response.Redirect(strURL);
        }
    }

    private void SaveItem(INVCheckLine objLine,string ItemName,string SizeText,string ColorText,string LocationName,string AreaName,string SectionName)
    {
        DataSet dt = Session["InvAdjustmentItem"] as DataSet;

        if ((dt!=null) || (dt.Tables[0].Rows.Count== 0))
        {
            // Add`
        //    SELECT A.BeforeQty AS BeforeQty,A.CurrentQty AS CurrentQty,C.ItemCode AS ItemCode,B.SKUID AS SKUID,
        //C.ItemName AS ItemName,E.SizeText AS SizeText,D.ColorText AS ColorText,
        //L.Name AS LocationName,R.Name AS AreaName,S.Text AS SectionName,L.LocationCode AS LocationCode,
        //R.AreaCode AS AreaCode,S.SectionCode AS SectionCode
            DataRow dtRow = dt.Tables[0].NewRow();
            dtRow["CurrentQty"] = objLine.CurrentQty;
            dtRow["BeforeQty"] = objLine.BeforeQty ;
            dtRow["ItemCode"] = "";
            dtRow["SKUID"] = objLine.SKUID;
            dtRow["ItemName"] = ItemName;
            dtRow["SizeText"] = SizeText;
            dtRow["ColorText"] = ColorText;
            dtRow["LocationName"] = LocationName;
            dtRow["AreaName"] = AreaName;
            dtRow["SectionName"] = SectionName;
            dtRow["LocationCode"] = objLine.LocationCode;
            dtRow["AreaCode"] = objLine.AreaCode;
            dtRow["SectionCode"] = objLine.SectionCode;
            dt.Tables[0].Rows.Add(dtRow);
        }
        else
        {
            dt.Tables[0].Rows[0]["CurrentQty"] = objLine.CurrentQty;
        }

        Session["InvAdjustmentItem"] = dt;

        this.rptInvCheck.DataSource = dt;
        this.rptInvCheck.DataBind();
    }

    private void SaveCheckOrder()
    {
        try
        {
            DataSet ds = Session["InvAdjustmentItem"] as DataSet;

            Dictionary<int, int> lstCurrentQuantity = new Dictionary<int, int>();
            for (int i = 0; i < this.rptInvCheck.Items.Count; i++)
            {
                TextBox objBox = this.rptInvCheck.Items[i].FindControl("txtCurrentQuantity") as TextBox;
                System.Web.UI.HtmlControls.HtmlInputHidden objSKU = this.rptInvCheck.Items[i].FindControl("hidSKUID") as System.Web.UI.HtmlControls.HtmlInputHidden;
                try
                {
                    lstCurrentQuantity.Add(Cast.Int(objSKU.Value), Cast.Int(objBox.Text));
                }
                catch (Exception ex)
                {
                    WebUtil.ShowMsg(this, "调整数量非法,请重新输入!");
                    return;
                }
            }

            using (_session = new Session())
            {
                _session.BeginTransaction();
                try
                {
                    INVCheckHead objHead = INVCheckHead.Retrieve(_session, this.txtInvCheckNumber.Text);

                    bool blnInsert = false;
                    if (objHead == null)
                    {
                        blnInsert = true;
                        objHead = new INVCheckHead();
                        objHead.CreateTime = DateTime.Now;
                        objHead.CreateUser = Magic.Security.SecuritySession.CurrentUser.UserId;
                        objHead.Note = this.txtMemo.Text;
                        objHead.OrderNumber = Magic.ERP.ERPUtil.NextOrderNumber(Magic.ERP.Orders.INVCheckHead.ORDER_TYPE_ADJ);
                        objHead.OrderTypeCode = INVCheckHead.ORDER_TYPE_ADJ;
                        objHead.Status = Magic.ERP.INVCheckStatus.New;
                        objHead.Create(_session);
                    }
                    else
                    {
                        if ((objHead.Status == Magic.ERP.INVCheckStatus.Release) || (objHead.Status == Magic.ERP.INVCheckStatus.Close))
                        {
                            WebUtil.ShowMsg(this, "发布以后的盘点单不可以进行修改");
                            return;
                        }

                        objHead.Note = this.txtMemo.Text;
                        blnInsert = false;
                        _session.CreateEntityQuery<INVCheckLine>().Where(Exp.Eq("OrderNumber", objHead.OrderNumber)).Delete();
                    }
                    
                    objHead.CurrentLineNumber = "";


                    List<INVCheckLine> List = new List<INVCheckLine>();
                    for (int i = 0; i < ds.Tables[0].Rows.Count; i++)
                    {
                        INVCheckLine objLine = new INVCheckLine();
                        objLine.SKUID = Cast.Int(ds.Tables[0].Rows[i]["SKUID"]);
                        objLine.AreaCode = Cast.String(ds.Tables[0].Rows[i]["AreaCode"]);
                        objLine.BeforeQty = Cast.Decimal(ds.Tables[0].Rows[i]["BeforeQty"]);
                        objLine.CurrentQty = lstCurrentQuantity[objLine.SKUID];
                        objLine.LineNumber = objHead.NextLineNumber();
                        objLine.LocationCode = Cast.String(ds.Tables[0].Rows[i]["LocationCode"]);
                        objLine.OrderNumber = objHead.OrderNumber;
                        objLine.SectionCode = Cast.String(ds.Tables[0].Rows[i]["SectionCode"]);
                        objLine.Create(_session);
                    }

                    objHead.Update(_session, "CurrentLineNumber");

                    this.txtInvCheckNumber.Text = objHead.OrderNumber;
                    _session.Commit();
                }
                catch (Exception ex)
                {
                    _session.Rollback();
                    throw ex;
                }
            }
            WebUtil.ShowMsg(this, "库存盘点单保存成功", "操作成功");
        }
        catch (Exception ex)
        {
            logger.Info("保存库存盘点单", ex);
            WebUtil.ShowMsg(this, "发生未处理的异常,请刷新页面重新操作，或者联系系统管理员");
        }
    }

    private void LoadCheckOrder_Head(ISession Session, string OrderNumber)
    {
        ObjectQuery query = Session.CreateObjectQuery(@"
SELECT A.OrderNumber AS OrderNumber,A.Note AS Note,A.Status AS Status,C.StatusText AS StatusText,U.FullName AS UserName
FROM INVCheckHead A
LEFT JOIN User U ON A.CreateUser=U.UserId
LEFT JOIN OrderStatusDef C ON A.Status=C.StatusValue AND C.OrderTypeCode=A.OrderTypeCode
WHERE A.OrderNumber=?
")
                    .Attach(typeof(Magic.ERP.Orders.INVCheckHead))
                    .Attach(typeof(Magic.Sys.User))
                    .Attach(typeof(Magic.ERP.Core.OrderStatusDef))
                    .SetValue(0, OrderNumber, "A.OrderNumber");
        DataSet ds = query.DataSet();

        if (ds.Tables[0].Rows.Count == 0)
        {
            this.InitialPage();
        }
        else
        {
            this.txtMemo.Text = ds.Tables[0].Rows[0]["Note"].ToString();
            this.txtStatus.Text = ds.Tables[0].Rows[0]["StatusText"].ToString();
            this.txtUser.Text = ds.Tables[0].Rows[0]["UserName"].ToString();
            this.txtInvCheckNumber.Text = OrderNumber ;
        }
    }

    private void LoadCheckOrder_Item(ISession _session, string OrderNumber)
    {
        ObjectQuery query = _session.CreateObjectQuery(@"
SELECT A.BeforeQty AS BeforeQty,A.CurrentQty AS CurrentQty,C.ItemCode AS ItemCode,B.SKUID AS SKUID,
        C.ItemName AS ItemName,E.SizeText AS SizeText,D.ColorText AS ColorText,
        L.Name AS LocationName,R.Name AS AreaName,S.Text AS SectionName,L.LocationCode AS LocationCode,
        R.AreaCode AS AreaCode,S.SectionCode AS SectionCode
FROM INVCheckLine A
LEFT JOIN ItemSpec B ON A.SKUID=B.SKUID
LEFT JOIN ItemMaster C ON B.ItemID=C.ItemID
LEFT JOIN ItemColor D ON B.ColorCode=D.ColorCode 
LEFT JOIN ItemSize E ON B.SizeCode=E.SizeCode
LEFT JOIN WHLocation L ON A.LocationCode=L.LocationCode
LEFT JOIN WHArea R ON A.AreaCode=R.AreaCode
LEFT JOIN WHSection S ON A.SectionCode=S.SectionCode
WHERE A.OrderNumber=?
")
            .Attach(typeof(Magic.ERP.Orders.INVCheckLine))
            .Attach(typeof(Magic.Basis.ItemSpec))
            .Attach(typeof(Magic.Basis.ItemMaster))
            .Attach(typeof(Magic.Basis.ItemColor))
            .Attach(typeof(Magic.Basis.ItemSize))
            .Attach(typeof(Magic.ERP.Core.WHLocation))
            .Attach(typeof(Magic.ERP.Core.WHArea))
            .Attach(typeof(Magic.ERP.Core.WHSection))
            .SetValue(0, OrderNumber, "A.OrderNumber");
        DataSet ds = query.DataSet();
        Session["InvAdjustmentItem"] = ds;
        this.rptInvCheck.DataSource = ds;
        this.rptInvCheck.DataBind();
    }

    private void InitialPage()
    {
        using (_session = new Session())
        {
            this.txtMemo.Text = "";
            this.txtStatus.Text = "新建";
            this.txtUser.Text = Magic.Security.SecuritySession.CurrentUser.FullName;
            this.txtInvCheckNumber.Text = "";
            this.ddlLocation.Items.Add(new ListItem("　", ""));

            WHRepository repository = new WHRepository(_session);
            foreach (WHLocation location in repository.GetLocations())
            {
                TreeNode objNode = new TreeNode() ;
                objNode.Text = location.Name ;
                objNode.Value = location.LocationCode ;
                this.ddlLocation.Items.Add(new ListItem(location.Name, location.LocationCode));
            }

            this.LoadCheckOrder_Item(_session, "");
        }
    }
}