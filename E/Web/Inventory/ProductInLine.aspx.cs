using System;
using System.Collections.Generic;
using System.Data;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using Magic.Basis;
using Magic.ERP;
using Magic.ERP.Core;
using Magic.ERP.Orders;
using Magic.Framework.ORM;
using Magic.Framework.Utils;
using Magic.Web.UI;

public partial class Inventory_ProductInLine : System.Web.UI.Page
{
    private ISession _session = null;
    private IList<WHArea> _areas = null;
    private IList<WHArea> GetAreas()
    {
        if (this._session == null && this._areas == null) return new List<WHArea>();
        if (this._areas == null)
        {
            this._areas = ERPUtil.GetWHArea(this._session, StockInHead.ORD_TYPE_PRD_IN, null);
        }
        return this._areas;
    }
    private StockInHead _head = null;

    private string OrderNumber
    {
        get
        {
            return WebUtil.Param("ordNum").Trim();
        }
    }
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            this.txtOrderNumber.Value = this.OrderNumber;
            this.cmdReturn1["Return"].NavigateUrl = this.cmdReturn2["Return"].NavigateUrl = WebUtil.Param("return");
            using (ISession session = new Session())
            {
                OrderTypeDef typeDef = OrderTypeDef.Retrieve(session, StockInHead.ORD_TYPE_PRD_IN);
                if (typeDef != null)
                    this.hidViewUrl.Value = typeDef.ViewURL;

                StockInHead head = StockInHead.Retrieve(session, this.OrderNumber);
                this.SetView(head);
                this.QueryAndBindData(session, head);
            }
        }
    }

    private void SetView(StockInHead head)
    {
        switch (head.Status)
        {
            case StockInStatus.New:
                this.cmdEdit1.Visible = true;
                this.cmdEdit2.Visible = true;
                this.cmdClose1.Visible = false;
                this.cmdClose2.Visible = false;
                this.divAddItem.Visible = true;
                break;
            case StockInStatus.Confirm:
            case StockInStatus.Release:
                this.cmdEdit1.Visible = false;
                this.cmdEdit2.Visible = false;
                this.cmdClose1.Visible = false;
                this.cmdClose2.Visible = false;
                this.divAddItem.Visible = false;
                break;
            case StockInStatus.Open:
                this.cmdEdit1.Visible = false;
                this.cmdEdit2.Visible = false;
                this.cmdClose1.Visible = true;
                this.cmdClose2.Visible = true;
                this.divAddItem.Visible = false;
                break;
            case StockInStatus.Close:
                this.cmdEdit1.Visible = false;
                this.cmdEdit2.Visible = false;
                this.cmdClose1.Visible = false;
                this.cmdClose2.Visible = false;
                this.divAddItem.Visible = false;
                break;
            default:
                this.divAddItem.Visible = false;
                break;
        }
    }

    private void QueryAndBindData(ISession session, StockInHead head)
    {
        this.repeatControl.DataSource = session.CreateObjectQuery(@"
select l.LineNumber as LineNumber,s.BarCode as BarCode,m.ItemCode as ItemCode,m.ItemName as ItemName
    ,s.ColorCode as ColorCode,s.SizeCode as SizeCode,color.ColorText as ColorText
    ,l.AreaCode as AreaCode,l.SectionCode as SectionCode,l.Quantity as Quantity,l.Price as Price
from StockInLine l
inner join ItemSpec s on l.SKUID=s.SKUID
inner join ItemMaster m on m.ItemID=s.ItemID
left join ItemColor color on color.ColorCode=s.ColorCode
where l.OrderNumber=?ordNum
order by l.LineNumber")
            .Attach(typeof(StockInLine)).Attach(typeof(ItemSpec)).Attach(typeof(ItemMaster)).Attach(typeof(ItemColor))
            .SetValue("?ordNum", this.OrderNumber, "l.OrderNumber")
            .DataSet();
        this._session = session;
        this._head = head;
        this.repeatControl.DataBind();
        this._head = null;
        this._session = null;
    }
    protected void repeatControl_ItemDataBound(object sender, RepeaterItemEventArgs e)
    {
        DataRowView drv = e.Item.DataItem as DataRowView;
        if (drv == null) return;

        TextBox txt;
        txt = e.Item.FindControl("txtQty") as TextBox;
        txt.Text = RenderUtil.FormatNumber(drv["Quantity"], "#0.##");
        if (this._head != null && this._head.Status != StockInStatus.New)
        {
            WebUtil.DisableControl(txt);
            HtmlInputCheckBox chk = e.Item.FindControl("checkbox") as HtmlInputCheckBox;
            chk.Visible = false;
        }
        txt = e.Item.FindControl("txtSec") as TextBox;
        txt.Text = Cast.String(drv["SectionCode"]);
        if (this._head != null && this._head.Status != StockInStatus.New)
            WebUtil.DisableControl(txt);
        DropDownList drp = e.Item.FindControl("drpArea") as DropDownList;
        drp.Items.Clear();
        drp.Items.Add(new ListItem("　", ""));
        foreach (WHArea area in this.GetAreas())
        {
            ListItem li = new ListItem(area.AreaCode, area.AreaCode);
            if (area.AreaCode.Trim() == Cast.String(drv["AreaCode"]).Trim())
                li.Selected = true;
            drp.Items.Add(li);
        }
        if (this._head != null && this._head.Status != StockInStatus.New)
            WebUtil.DisableControl(drp);
    }

    protected void MagicItemCommand(object sender, MagicItemEventArgs e)
    {
        if (e.CommandName == "Save")
        {
            #region 保存
            using (ISession session = new Session())
            {
                IList<StockInLine> linesToSave = new List<StockInLine>();
                System.Text.StringBuilder error = new System.Text.StringBuilder();
                foreach (RepeaterItem item in this.repeatControl.Items)
                {
                    HtmlInputCheckBox chk = item.FindControl("checkbox") as HtmlInputCheckBox;
                    TextBox text;
                    StockInLine line = StockInLine.Retrieve(session, this.OrderNumber, chk.Value);
                    text = item.FindControl("txtQty") as TextBox;
                    line.Quantity = Cast.Decimal(text.Text.Trim());
                    text = item.FindControl("txtSec") as TextBox;
                    line.SectionCode = text.Text.Trim();
                    DropDownList drp = item.FindControl("drpArea") as DropDownList;
                    line.AreaCode = drp.SelectedValue;

                    if (string.IsNullOrEmpty(line.AreaCode) || line.AreaCode.Trim().Length <= 0)
                        error.Append(chk.Attributes["sku"]).Append("未选择库位;");
                    else if (!string.IsNullOrEmpty(line.SectionCode) && line.SectionCode.Trim().Length > 0)
                    {
                        WHSection section = WHSection.Retrieve(session, line.AreaCode, line.SectionCode);
                        if (section == null) error.Append(chk.Attributes["sku"])
                            .Append("库位").Append(line.AreaCode).Append("中不存在货架").Append(line.SectionCode).Append(";");
                    }

                    linesToSave.Add(line);
                }
                if (error.Length > 0)
                {
                    WebUtil.ShowError(this, error.ToString());
                    return;
                }

                try
                {
                    //检查
                    StockInHead head = StockInHead.Retrieve(session, this.OrderNumber);
                    if (head == null) return;
                    session.BeginTransaction();
                    head.CreateOrUpdateLines(session, linesToSave);
                    session.Commit();
                    WebUtil.ShowMsg(this, "保存成功");
                }
                catch (Exception er)
                {
                    session.Rollback();
                    WebUtil.ShowError(this, er);
                }
            }
            #endregion
        }
        else if (e.CommandName == "Delete")
        {
            #region 删除
            using (ISession session = new Session())
            {
                try
                {
                    session.BeginTransaction();
                    foreach (RepeaterItem item in this.repeatControl.Items)
                    {
                        HtmlInputCheckBox chk = item.FindControl("checkbox") as HtmlInputCheckBox;
                        if (chk.Checked)
                        {
                            StockInLine line = StockInLine.Retrieve(session, this.OrderNumber, chk.Value.Trim());
                            if (line != null)
                                line.Delete(session);
                        }
                    }
                    session.Commit();
                    this.QueryAndBindData(session, null);
                    WebUtil.ShowMsg(this, "选择的明细已经删除");
                }
                catch (Exception er)
                {
                    session.Rollback();
                    WebUtil.ShowError(this, er);
                }
            }
            #endregion
        }
        else if (e.CommandName == "Release")
        {
            #region 发布
            using (ISession session = new Session())
            {
                try
                {
                    StockInHead head = StockInHead.Retrieve(session, this.OrderNumber);
                    session.BeginTransaction();
                    head.Release(session);
                    session.Commit();
                    WebUtil.ShowMsg(this, "发布成功");
                    this.QueryAndBindData(session, head);
                    this.SetView(head);
                }
                catch (Exception er)
                {
                    session.Rollback();
                    WebUtil.ShowError(this, er);
                }
            }
            #endregion
        }
        else if (e.CommandName == "Close")
        {
            #region 关闭
            using (ISession session = new Session())
            {
                try
                {
                    StockInHead head = StockInHead.Retrieve(session, this.OrderNumber);
                    if (head == null) return;
                    session.BeginTransaction();
                    head.Close(session);
                    session.Commit();
                    WebUtil.ShowMsg(this, "产品入库单" + head.OrderNumber + "已经完成");
                    this.QueryAndBindData(session, head);
                    this.SetView(head);
                }
                catch (Exception er)
                {
                    session.Rollback();
                    WebUtil.ShowError(this, er);
                }
            }
            #endregion
        }
        else if (e.CommandName == "Add")
        {
            #region 添加明细
            using (ISession session = new Session())
            {
                ItemSpec sku = ItemSpec.Retrieve(session, this.txtSku.Text.Trim().ToUpper());
                if (sku == null)
                {
                    WebUtil.ShowError(this, "SKU: "+this.txtSku.Text.Trim()+"不存在");
                    return;
                }
                StockInHead head = StockInHead.Retrieve(session, this.OrderNumber);
                StockInLine line = new StockInLine();
                line.OrderNumber = this.OrderNumber;
                line.LocationCode = head.LocationCode;
                line.LineNumber = head.NextLineNumber();
                line.AreaCode = "";
                line.SectionCode = "";
                line.Price = sku.AvgMoveCost;
                line.Quantity = 0M;
                line.RefQuantity = 0M;
                line.SKUID = sku.SKUID;
                line.StockDetailID = 0;
                line.UnitID = 0;

                bool isError = false;
                try
                {
                    session.BeginTransaction();
                    line.Create(session);
                    head.Update(session, "CurrentLineNumber");
                    session.Commit();
                }
                catch (Exception er)
                {
                    session.Rollback();
                    WebUtil.ShowError(this, er);
                    isError = true;
                }

                if (!isError)
                {
                    this.txtSku.Text = "";
                    this.QueryAndBindData(session, head);
                    this.SetView(head);
                }
            }
            #endregion
        }
    }
}