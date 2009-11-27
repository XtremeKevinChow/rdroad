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

public partial class Inventory_AssistItemOutLine : System.Web.UI.Page
{
    private ISession _session = null;
    private IList<WHArea> _areas = null;
    private IList<WHArea> GetAreas()
    {
        if (this._session == null && this._areas == null) return new List<WHArea>();
        if (this._areas == null)
        {
            this._areas = ERPUtil.GetWHArea(this._session, StockInHead.ORD_TYPE_ASSIST_OUT, null);
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
            this.txtReturnUrl.Value = "AssistItemOutLine.aspx?ordNum=" + this.OrderNumber + "&return=" + WebUtil.escape(WebUtil.Param("return"));
            using (ISession session = new Session())
            {
                OrderTypeDef typeDef = OrderTypeDef.Retrieve(session, StockInHead.ORD_TYPE_ASSIST_OUT);
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
    ,s.ColorCode as ColorCode,s.SizeCode as SizeCode
    ,l.AreaCode as AreaCode,l.SectionCode as SectionCode,l.Quantity as Qty,l.Price as Price
    ,l.StockDetailID as StockDetailID
from StockInLine l
inner join ItemSpec s on l.SKUID=s.SKUID
inner join ItemMaster m on m.ItemID=s.ItemID
where l.OrderNumber=?ordNum
order by l.LineNumber")
            .Attach(typeof(StockInLine)).Attach(typeof(ItemSpec)).Attach(typeof(ItemMaster))
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
        txt.Text = RenderUtil.FormatNumber(drv["Qty"], "#0.##");
        if (this._head != null && this._head.Status != StockInStatus.New)
        {
            WebUtil.DisableControl(txt);
            HtmlInputCheckBox chk = e.Item.FindControl("checkbox") as HtmlInputCheckBox;
            chk.Visible = false;
        }
    }

    protected void MagicItemCommand(object sender, MagicItemEventArgs e)
    {
        if (e.CommandName == "Save")
        {
            #region 保存
            using (ISession session = new Session())
            {
                IList<StockInLine> linesToSave = new List<StockInLine>();
                foreach (RepeaterItem item in this.repeatControl.Items)
                {
                    HtmlInputCheckBox chk = item.FindControl("checkbox") as HtmlInputCheckBox;
                    TextBox text;
                    StockInLine line = StockInLine.Retrieve(session, this.OrderNumber, chk.Value);
                    text = item.FindControl("txtQty") as TextBox;
                    line.Quantity = Cast.Decimal(text.Text.Trim());

                    linesToSave.Add(line);
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
                    WebUtil.ShowMsg(this, "辅料领用单" + head.OrderNumber + "已经完成");
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
}