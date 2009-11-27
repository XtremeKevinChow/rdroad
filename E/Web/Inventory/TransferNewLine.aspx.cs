using System;
using System.Collections.Generic;
using System.Data;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using Magic.Framework.Utils;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Query;
using Magic.Sys;
using Magic.ERP;
using Magic.Basis;
using Magic.ERP.Core;
using Magic.ERP.Orders;
using Magic.Web.UI;

public partial class Inventory_TransferNewLine : System.Web.UI.Page
{
    private static log4net.ILog log = WebUtil.Logger(typeof(Inventory_TransferNewLine));

    private IList<WHArea> _area;
    private WHTransferHead _head;
    private ISession _session;
    private IList<WHArea> GetArea(ISession session)
    {
        if (session == null) return new List<WHArea>(0);
        if (this._area == null)
            this._area = ERPUtil.GetWHArea(session, WHTransferHead.ORDER_TYPE_NORMAL, "403", this._head.ToLocation);
        return this._area;
    }
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
            log.DebugFormat("PageLoad - 添加移库明细: ordNum={0}, return={1}", this.OrderNumber, WebUtil.Param("return"));
            this.toolbarTop["Return"].NavigateUrl = this.toolbarBottom["Return"].NavigateUrl = WebUtil.Param("return");
            using (ISession session = new Session())
            {
                WHTransferHead head = WHTransferHead.Retrieve(session, this.OrderNumber);
                this.drpArea.Items.Clear();
                this.drpArea.Items.Add(new ListItem("", ""));
                IList<WHArea> areas = ERPUtil.GetWHArea(session, WHTransferHead.ORDER_TYPE_NORMAL, "401", head.FromLocation);
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
        this._head = WHTransferHead.Retrieve(session, this.OrderNumber);
        this.repeatControl.DataSource = WHTransferHead.Query(session, fetchRecordCount, out count, this._head.FromLocation
            , this.txtSku.Text, this.txtItemCode.Text, this.txtItemName.Text, this.txtColor.Text, this.txtSize.Text, this.drpArea.SelectedValue, this.txtSection.Text
            , pageIndex, pageSize);
        this._session = session;

        this.repeatControl.DataBind();
        this._session = null;
        if (fetchRecordCount)
            this.magicPagerMain.RecordCount = this.magicPagerSub.RecordCount = count;
        WebUtil.SetMagicPager(magicPagerMain, pageSize, pageIndex);
        WebUtil.SetMagicPager(magicPagerSub, pageSize, pageIndex);
    }
    protected void repeatControl_ItemDataBound(object sender, RepeaterItemEventArgs e)
    {
        DataRowView drv = e.Item.DataItem as DataRowView;
        if (drv == null) return;

        DropDownList drp = e.Item.FindControl("drpToArea") as DropDownList;
        drp.Items.Clear();
        if (this.GetArea(this._session) != null)
            foreach (WHArea area in this.GetArea(this._session))
                drp.Items.Add(new ListItem(area.AreaCode, area.AreaCode));
        HtmlInputCheckBox chk = e.Item.FindControl("checkbox") as HtmlInputCheckBox;
        if (Cast.Decimal(drv["TransferQty"]) <= 0)
            chk.Visible = false;
    }

    protected void MagicItemCommand(object sender, MagicItemEventArgs e)
    {
        if (e.CommandName != "Save") return;

        using (ISession session = new Session())
        {
            try
            {
                IList<WHTransferLine> lines = new List<WHTransferLine>();
                WHTransferHead head = WHTransferHead.Retrieve(session, this.OrderNumber);
                foreach (RepeaterItem item in this.repeatControl.Items)
                {
                    TextBox text = item.FindControl("txtMoveQty") as TextBox;
                    decimal qty = Cast.Decimal(text.Text, 0M);
                    if (qty <= 0M) continue;

                    WHTransferLine line = new WHTransferLine();
                    line.MoveQty = qty;
                    HtmlInputCheckBox chk = item.FindControl("checkbox") as HtmlInputCheckBox;
                    line.FromStockID = Cast.Int(chk.Value);
                    HtmlInputText input = item.FindControl("hidTransferQty") as HtmlInputText;
                    qty = Cast.Decimal(input.Value, 0M);
                    if (line.MoveQty > qty)
                    {
                        WebUtil.ShowError(this, "移库量必须小于或等于可移库量");
                        return;
                    }
                    DropDownList drp = item.FindControl("drpToArea") as DropDownList;
                    line.ToArea = drp.SelectedValue;
                    text = item.FindControl("txtToSection") as TextBox;
                    line.ToSection = text.Text.Trim();
                    lines.Add(line);
                }
                if (lines.Count <= 0)
                {
                    log.Debug("Save - 移库明细: 没有填写移库数量，未添加明细");
                    return;
                }

                session.BeginTransaction();
                head.AddLines(session, lines);
                session.Commit();

                log.DebugFormat("Save - 移库明细: 总共添加了{0}个移库明细", lines.Count);
                WebUtil.ShowMsg(this, "已经创建移库明细");
                this.QueryAndBindData(session, this.magicPagerMain.CurrentPageIndex, this.magicPagerMain.PageSize, true);
            }
            catch (Exception er)
            {
                log.Error("Save - 移库明细: ", er);
                session.Rollback();
                WebUtil.ShowError(this, er);
            }
        }
    }
}