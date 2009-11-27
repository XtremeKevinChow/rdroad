using System;
using System.Collections.Generic;
using System.Data;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using Magic.ERP;
using Magic.ERP.Core;
using Magic.ERP.Orders;
using Magic.Framework.ORM;
using Magic.Framework.Utils;
using Magic.Web.UI;

public partial class Purchase_POReturnNewLine : System.Web.UI.Page
{
    private ISession _session = null;
    private IList<WHArea> _areas = null;
    private IList<WHArea> GetAreas()
    {
        if (this._session == null && this._areas == null) return new List<WHArea>();
        if (this._areas == null)
        {
            this._areas = ERPUtil.GetWHArea(this._session, POReturnHead.ORD_TYPE_CODE, null);
        }
        return this._areas;
    }
    private static log4net.ILog log = WebUtil.Logger(typeof(Purchase_POReturnNewLine));

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
            this.txtDateFrom.Text = DateTime.Now.AddMonths(-2).ToString("yyyy-MM-dd");
            this.txtDateTo.Text = DateTime.Now.ToString("yyyy-MM-dd");
            log.DebugFormat("PageLoad - to add prd req line: ordNum={0}, return={1}", this.OrderNumber, WebUtil.Param("return"));
            this.toolbarTop["Return"].NavigateUrl = this.toolbarBottom["Return"].NavigateUrl = WebUtil.Param("return");
            using (ISession session = new Session())
            {
                POReturnHead head = POReturnHead.Retrieve(session, this.OrderNumber);
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
            this.QueryAndBindData(session, 1, this.magicPagerMain.PageSize, true);
        }
    }

    private void QueryAndBindData(ISession session, int pageIndex, int pageSize, bool fetchRecordCount)
    {
        POReturnHead head = POReturnHead.Retrieve(session, this.OrderNumber);
        int count = 0;
        this.repeatControl.DataSource = head.QueryNewLine(session, Cast.DateTime(this.txtDateFrom.Text), Cast.DateTime(this.txtDateTo.Text), this.txtPO.Text, this.txtSku.Text, pageIndex, pageSize, fetchRecordCount, ref count);
        this._session = session;
        this.repeatControl.DataBind();
        if (fetchRecordCount)
            this.magicPagerMain.RecordCount = this.magicPagerSub.RecordCount = count;
        WebUtil.SetMagicPager(magicPagerMain, pageSize, pageIndex);
        WebUtil.SetMagicPager(magicPagerSub, pageSize, pageIndex);
        this._session = null;
    }

    protected void MagicItemCommand(object sender, MagicItemEventArgs e)
    {
        if (e.CommandName != "Save") return;

        using (ISession session = new Session())
        {
            try
            {
                POReturnHead head = POReturnHead.Retrieve(session, this.OrderNumber);

                session.BeginTransaction();
                foreach (RepeaterItem item in this.repeatControl.Items)
                {
                    TextBox text = item.FindControl("txtQty") as TextBox;
                    decimal qty = Cast.Decimal(text.Text, 0M);
                    if (qty <= 0M) continue;

                    HtmlInputCheckBox chk = item.FindControl("checkbox") as HtmlInputCheckBox;
                    text = item.FindControl("txtSec") as TextBox;
                    DropDownList drp = item.FindControl("drpArea") as DropDownList;
                    if (string.IsNullOrEmpty(drp.SelectedValue))
                        throw new Exception(chk.Attributes["po"] + "行" + chk.Attributes["poline"] + "没有选择库位");

                    head.AddLine(session, chk.Attributes["po"], chk.Attributes["poline"], drp.SelectedValue.Trim().ToUpper(), text.Text.Trim().ToUpper(), qty);
                }
                head.Update(session, "CurrentLineNumber");               
                session.Commit();

                WebUtil.ShowMsg(this, "指定的退货信息已经保存");
                this.QueryAndBindData(session, this.magicPagerMain.CurrentPageIndex, this.magicPagerMain.PageSize, true);
            }
            catch (Exception er)
            {
                log.Error("Save - to add purchase return line: ", er);
                session.Rollback();
                WebUtil.ShowError(this, er);
            }
        }
    }
    protected void repeatControl_ItemDataBound(object sender, RepeaterItemEventArgs e)
    {
        DataRowView drv = e.Item.DataItem as DataRowView;
        if (drv == null) return;

        DropDownList drp = e.Item.FindControl("drpArea") as DropDownList;
        drp.Items.Clear();
        drp.Items.Add(new ListItem("", ""));
        foreach (WHArea area in this.GetAreas())
            drp.Items.Add(new ListItem(area.AreaCode, area.AreaCode));
    }
}