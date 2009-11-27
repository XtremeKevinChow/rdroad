using System;
using System.Collections.Generic;
using System.Data;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using Magic.ERP;
using Magic.Basis;
using Magic.ERP.Core;
using Magic.ERP.Orders;
using Magic.Framework.ORM;
using Magic.Framework.Utils;
using Magic.Web.UI;

public partial class Inventory_AssistItemOutNewLine : System.Web.UI.Page
{
    private static log4net.ILog log = WebUtil.Logger(typeof(Inventory_AssistItemOutNewLine));

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
            log.DebugFormat("PageLoad - to add assist item req line: ordNum={0}, return={1}", this.OrderNumber, WebUtil.Param("return"));
            this.toolbarTop["Return"].NavigateUrl = this.toolbarBottom["Return"].NavigateUrl = WebUtil.Param("return");
            using (ISession session = new Session())
            {
                StockInHead head = StockInHead.Retrieve(session, this.OrderNumber);
                this.drpArea.Items.Clear();
                this.drpArea.Items.Add(new ListItem("", ""));
                IList<WHArea> areas = ERPUtil.GetWHArea(session, StockInHead.ORD_TYPE_ASSIST_OUT, null, head.LocationCode);
                foreach (WHArea area in areas)
                    this.drpArea.Items.Add(new ListItem(area.AreaCode, area.AreaCode));
                this.QueryAndBindData(session, 1, this.magicPagerMain.PageSize, true);
            }
        }
    }

    protected void MagicPager_PageChanged(object src, Magic.Web.UI.PageChangedEventArgs e)
    {
        this.magicPagerSub.PageSize = this.magicPagerMain.PageSize;
        using (ISession session = new Session())
        {
            this.QueryAndBindData(session, e.NewPageIndex, e.Pager.PageSize, false);
        }
    }

    protected void btnQuery_Click(object sender, ImageClickEventArgs e)
    {
        using (ISession session = new Session())
        {
            this.QueryAndBindData(session, 1, magicPagerMain.PageSize, true);
        }
    }

    private void QueryAndBindData(ISession session, int pageIndex, int pageSize, bool fetchRecordCount)
    {
        int count = 0;
        StockInHead head = StockInHead.Retrieve(session, this.OrderNumber);
        this.repeatControl.DataSource = StockInHead.QueryLine(session, true, this.OrderNumber, fetchRecordCount, out count
            ,head.LocationCode, "", this.txtItemCode.Text, this.txtItemName.Text, "", "", this.drpArea.SelectedValue, this.txtSection.Text
            , pageIndex, pageSize);

        this.repeatControl.DataBind();
        if (fetchRecordCount)
            this.magicPagerMain.RecordCount = this.magicPagerSub.RecordCount = count;
        WebUtil.SetMagicPager(magicPagerMain, pageSize, pageIndex);
        WebUtil.SetMagicPager(magicPagerSub, pageSize, pageIndex);
    }

    protected void MagicItemCommand(object sender, MagicItemEventArgs e)
    {
        if (e.CommandName != "Save") return;

        using (ISession session = new Session())
        {
            try
            {
                IList<StockInLine> lines = new List<StockInLine>();
                StockInHead head = StockInHead.Retrieve(session, this.OrderNumber);
                foreach (RepeaterItem item in this.repeatControl.Items)
                {
                    TextBox text = item.FindControl("txtQty") as TextBox;
                    decimal qty = Cast.Decimal(text.Text, 0M);
                    if (qty <= 0M) continue;

                    HtmlInputCheckBox chk = item.FindControl("checkbox") as HtmlInputCheckBox;
                    StockInLine line = new StockInLine();
                    line.OrderNumber = this.OrderNumber;
                    line.StockDetailID = Cast.Int(chk.Value);
                    line.LineNumber = Cast.String(chk.Attributes["line"]);
                    line.Quantity = qty;
                    line.Price = 0M;
                    lines.Add(line);
                }
                if (lines.Count <= 0)
                {
                    log.Debug("Save - assist item req line: no lines need to be saved");
                    return;
                }

                session.BeginTransaction();
                head.CreateOrUpdateLines(session, lines);
                session.Commit();

                log.DebugFormat("Save - assist item req line: {0} lines were created or updated", lines.Count);
                WebUtil.ShowMsg(this, "辅料领用明细已经保存");
                this.QueryAndBindData(session, this.magicPagerMain.CurrentPageIndex, this.magicPagerMain.PageSize, true);
            }
            catch (Exception er)
            {
                log.Error("Save - to add assist item req line: ", er);
                session.Rollback();
                WebUtil.ShowError(this, er);
            }
        }
    }
    protected void repeatControl_ItemDataBound(object sender, RepeaterItemEventArgs e)
    {
        DataRowView drv = e.Item.DataItem as DataRowView;
        if (drv == null) return;

        Label lbl = e.Item.FindControl("lblFreeQty") as Label;
        lbl.Text = RenderUtil.FormatNumber(Cast.Decimal(drv["StockQty"]) - Cast.Decimal(drv["FrozenQty"]), "#0.##");
        TextBox txt = e.Item.FindControl("txtQty") as TextBox;
        txt.Text = RenderUtil.FormatNumber(drv["Qty"], "#0.##");
    }
}