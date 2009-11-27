using System;
using System.Collections.Generic;
using System.Data;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using Magic.Framework.Utils;
using Magic.Framework.ORM;
using Magic.ERP;
using Magic.ERP.Core;
using Magic.ERP.Orders;
using Magic.Basis;
using Magic.Web.UI;

public partial class Inventory_AssistItemInLine : System.Web.UI.Page
{
    private ISession _session = null;
    private IList<WHArea> _areas = null;
    private IList<WHArea> GetAreas()
    {
        if (this._session == null && this._areas == null) return new List<WHArea>();
        if (this._areas == null)
        {
            this._areas = ERPUtil.GetWHArea(this._session, StockInHead.ORD_TYPE_ASSIST_IN, null);
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
                break;
            case StockInStatus.Release:
                this.cmdEdit1.Visible = false;
                this.cmdEdit2.Visible = false;
                this.cmdClose1.Visible = false;
                this.cmdClose2.Visible = false;
                break;
            case StockInStatus.Open:
                this.cmdEdit1.Visible = false;
                this.cmdEdit2.Visible = false;
                this.cmdClose1.Visible = true;
                this.cmdClose2.Visible = true;
                break;
            case StockInStatus.Close:
                this.cmdEdit1.Visible = false;
                this.cmdEdit2.Visible = false;
                this.cmdClose1.Visible = false;
                this.cmdClose2.Visible = false;
                break;
        }
    }

    private void QueryAndBindData(ISession session, StockInHead head)
    {
        this.repeatControl.DataSource = session.CreateObjectQuery(@"
select l.LineNumber as LineNumber,s.BarCode as BarCode,m.ItemCode as ItemCode,m.ItemName as ItemName
    ,l.AreaCode as AreaCode,l.SectionCode as SectionCode,l.Quantity as Quantity,l.Price as Price
from StockInLine l
inner join ItemSpec s on l.SKUID=s.SKUID
inner join ItemMaster m on m.ItemID=s.ItemID
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

        DropDownList drp = e.Item.FindControl("drpArea") as DropDownList;
        if (this.GetAreas() != null)
        {
            drp.Items.Clear();
            drp.Items.Add(new ListItem("", ""));
            foreach (WHArea area in this.GetAreas())
                drp.Items.Add(new ListItem(area.AreaCode, area.AreaCode));
            string selectedArea = Cast.String(drv["AreaCode"]).Trim();
            if (!string.IsNullOrEmpty(selectedArea))
                drp.SelectedValue = selectedArea;
        }
        if (this._head!=null && this._head.Status != StockInStatus.New)
            WebUtil.DisableControl(drp);

        TextBox txt;
        txt = e.Item.FindControl("txtSection") as TextBox;
        txt.Text = Cast.String(drv["SectionCode"]);
        if (this._head != null && this._head.Status != StockInStatus.New)
            WebUtil.DisableControl(txt);
        txt = e.Item.FindControl("txtQualifiedQty") as TextBox;
        txt.Text = RenderUtil.FormatNumber(drv["Quantity"], "#0.##");
        if (this._head != null && this._head.Status != StockInStatus.New)
        {
            WebUtil.DisableControl(txt);
            HtmlInputCheckBox chk = e.Item.FindControl("checkbox") as HtmlInputCheckBox;
            chk.Visible = false;
        }
        txt = e.Item.FindControl("txtPrice") as TextBox;
        txt.Text = RenderUtil.FormatNumber(drv["Price"], "#0.##");
        if (this._head != null && this._head.Status != StockInStatus.New)
        {
            WebUtil.DisableControl(txt);
        }
    }

    protected void MagicItemCommand(object sender, MagicItemEventArgs e)
    {
        if (e.CommandName == "Save")
        {
            #region 保存
            IList<StockInLine> linesToSave = new List<StockInLine>();
            foreach (RepeaterItem item in this.repeatControl.Items)
            {
                HtmlInputCheckBox chk = item.FindControl("checkbox") as HtmlInputCheckBox;
                DropDownList drp = item.FindControl("drpArea") as DropDownList;
                TextBox text;
                StockInLine line = new StockInLine();
                line.OrderNumber = this.OrderNumber;
                line.LineNumber = chk.Value.Trim();
                line.AreaCode = drp.SelectedValue;
                text = item.FindControl("txtSection") as TextBox;
                line.SectionCode = text.Text.Trim();
                text = item.FindControl("txtQualifiedQty") as TextBox;
                line.Quantity = Cast.Decimal(text.Text.Trim());
                text = item.FindControl("txtPrice") as TextBox;
                line.Price = Cast.Decimal(text.Text.Trim());

                linesToSave.Add(line);
            }

            using (ISession session = new Session())
            {
                try
                {
                    //检查
                    StockInHead head = StockInHead.Retrieve(session, this.OrderNumber);
                    if (head == null) return;
                    head.UpdateLines(session, linesToSave);
                    WebUtil.ShowMsg(this, "保存成功");
                }
                catch (Exception er)
                {
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
                    session.BeginTransaction();
                    StockInHead head = StockInHead.Retrieve(session, this.OrderNumber);
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
                    WebUtil.ShowMsg(this, "辅料入库单" + head.OrderNumber + "已经完成");
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
    }

    protected void cmdAddLines_Click(object sender, EventArgs e)
    {
        string[] linesArray = this.txtSkus.Value.Trim().Trim(';').Split(';');
        if (linesArray == null || linesArray.Length <= 0) return;

        using (ISession session = new Session())
        {
            StockInHead head = StockInHead.Retrieve(session, this.OrderNumber);
            if (head == null) return;
            try
            {
                session.BeginTransaction();
                head.AddLines(session, linesArray);
                session.Commit();
                this.QueryAndBindData(session, head);
            }
            catch (Exception er)
            {
                session.Rollback();
                WebUtil.ShowError(this, er);
            }
        }
    }
}