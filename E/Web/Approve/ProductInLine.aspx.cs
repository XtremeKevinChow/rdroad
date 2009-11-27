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

public partial class Approve_ProductInLine : System.Web.UI.Page
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
            case StockInStatus.Confirm:
                this.cmdEdit1.Visible = true;
                this.cmdEdit2.Visible = true;
                break;
            default:
                this.cmdEdit1.Visible = false;
                this.cmdEdit2.Visible = false;
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

        TextBox txt = e.Item.FindControl("txtPrice") as TextBox;
        txt.Text = RenderUtil.FormatNumber(drv["Price"], "#0.#0");
        if (this._head == null || this._head.Status != StockInStatus.Confirm)
            WebUtil.DisableControl(txt);
    }

    protected void MagicItemCommand(object sender, MagicItemEventArgs e)
    {
        using (ISession session = new Session())
        {
            try
            {
                StockInHead head = StockInHead.Retrieve(session, this.OrderNumber);
                string msg = "";
                session.BeginTransaction();
                if (e.CommandName == "Save")
                {
                    this.Save(session, head);
                    msg = "保存成功";
                }
                else if (e.CommandName == "Release")
                {
                    this.Confirm(session, head);
                    msg = "入库价格审核完成";
                }
                session.Commit();
                this.QueryAndBindData(session, head);
                this.SetView(head);
                WebUtil.ShowMsg(this, msg);
            }
            catch (Exception er)
            {
                session.Rollback();
                WebUtil.ShowError(this, er);
            }
        }
    }
    private void Save(ISession session, StockInHead head)
    {
        if (head == null) return;

        IList<StockInLine> linesToSave = new List<StockInLine>();
        foreach (RepeaterItem item in this.repeatControl.Items)
        {
            HtmlInputHidden txt = item.FindControl("hidLine") as HtmlInputHidden;
            TextBox text;
            StockInLine line = StockInLine.Retrieve(session, this.OrderNumber, txt.Value);
            text = item.FindControl("txtPrice") as TextBox;
            line.Price = Cast.Decimal(text.Text, line.Price);
            linesToSave.Add(line);
        }

        head.UpdatePrice(session, linesToSave);
    }
    private void Confirm(ISession session, StockInHead head)
    {
        if (head == null) return;
        head.Confirm(session);
    }
}