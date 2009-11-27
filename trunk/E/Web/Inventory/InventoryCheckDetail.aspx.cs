using System;
using System.Collections.Generic;
using System.Data;
using System.Web.UI.WebControls;
using Magic.Framework.Utils;
using Magic.Framework.ORM;
using Magic.ERP;
using Magic.ERP.Core;
using Magic.ERP.Orders;
using Magic.Basis;
using Magic.Web.UI;
using Magic.Framework.ORM.Query;

public partial class Inventory_InventoryCheckDetail : System.Web.UI.Page
{
    private static log4net.ILog log = WebUtil.Logger(typeof(Inventory_InventoryCheckDetail));
    private INVCheckHead _head = null;

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
            this.txtOrdNumber.Value = this.OrderNumber;
            WebUtil.SetMagicPager(this.magicPagerMain, this.magicPagerMain.PageSize, 1);
            WebUtil.SetMagicPager(this.magicPagerSub, this.magicPagerSub.PageSize, 1);

            using (ISession session = new Session())
            {
                this.LoadArea(session);
                INVCheckHead head = INVCheckHead.Retrieve(session, this.OrderNumber);
                this.SetView(head);
                this.RestoreLastQuery(session, head);
            }
        }
        else this.frameDownload.Attributes["src"] = "about:blank;";
    }
    private void LoadArea(ISession session)
    {
        this.drpArea.Items.Clear();
        this.drpArea.Items.Add(new ListItem("　", ""));
        IList<WHArea> areas = session.CreateObjectQuery(@"
select 1 from WHArea where AreaCode in (select AreaCode from INVCheckWh where OrderNumber=?ordNum)
order by AreaCode")
            .Attach(typeof(WHArea)).Attach(typeof(INVCheckWh))
            .SetValue("?ordNum", this.OrderNumber, EntityManager.GetPropMapping(typeof(INVCheckWh), "OrderNumber").DbTypeInfo)
            .List<WHArea>();
        foreach (WHArea area in areas)
            this.drpArea.Items.Add(new ListItem(area.AreaCode, area.AreaCode));
    }
    private void SetView(INVCheckHead head)
    {
        this.cmdReturn1["Return"].NavigateUrl = WebUtil.Param("return");
        this.cmdReturn2["Return"].NavigateUrl = WebUtil.Param("return");
        WebUtil.EnableControl(this.drpViewType);
        if (!IsPostBack)
        {
            this.drpViewType.ClearSelection();
            this.drpViewType.SelectedValue = "1";
        }
        if (head == null) return;
        #region 操作按钮
        switch (head.Status)
        {
            case INVCheckStatus.Confirm:
                this.cmdEdit1.Visible = true;
                this.cmdEdit2.Visible = true;
                this.cmdClose1.Visible = false;
                this.cmdClose2.Visible = false;
                if (head.CheckType == INVCheckType.Implicit)
                    WebUtil.DisableControl(this.drpViewType);
                break;
            case INVCheckStatus.Release:
                this.cmdEdit1.Visible = false;
                this.cmdEdit2.Visible = false;
                if (head.ApproveResult == ApproveStatus.Approve)
                {
                    this.cmdClose1.Visible = true;
                    this.cmdClose2.Visible = true;
                }
                else
                {
                    this.cmdClose1.Visible = false;
                    this.cmdClose2.Visible = false;
                }
                if (!IsPostBack)
                {
                    this.drpViewType.ClearSelection();
                    this.drpViewType.SelectedValue = "2";
                }
                break;
            case INVCheckStatus.Close:
                this.cmdEdit1.Visible = false;
                this.cmdEdit2.Visible = false;
                this.cmdClose1.Visible = false;
                this.cmdClose2.Visible = false;
                if (!IsPostBack)
                {
                    this.drpViewType.ClearSelection();
                    this.drpViewType.SelectedValue = "2";
                }
                break;
        }
        #endregion
    }
    private void QueryAndBindData(ISession session, INVCheckHead head, int pageSize, int pageIndex, bool fetchCount)
    {
        Magic.Framework.ORM.Query.ObjectQuery query = session.CreateObjectQuery(string.Format(@"
select l.LineNumber as LineNumber
    ,s.BarCode as BarCode,m.ItemCode as ItemCode,m.ItemName as ItemName
    ,s.ColorCode as ColorCode,color.ColorText as ColorText,s.SizeCode as SizeCode
    ,l.AreaCode as AreaCode,l.SectionCode as SectionCode,l.BeforeQty as BeforeQty,l.CurrentQty as CurrentQty
from INVCheckLine l
inner join ItemSpec s on l.SKUID=s.SKUID
inner join ItemMaster m on m.ItemID=s.ItemID
left join ItemColor color on color.ColorCode=s.ColorCode
where l.OrderNumber=?ordNum {0}
order by l.LineNumber", this.drpViewType.SelectedValue == "2" ? "and l.BeforeQty<>l.CurrentQty" : ""))
            .Attach(typeof(INVCheckLine)).Attach(typeof(ItemSpec)).Attach(typeof(ItemMaster)).Attach(typeof(ItemColor))
            .SetValue("?ordNum", this.OrderNumber, "l.OrderNumber")
            .SetPage(pageIndex, pageSize);
        if (this.drpArea.SelectedValue.Trim().Length > 0) query.And(Exp.Eq("l.AreaCode", this.drpArea.SelectedValue.Trim()));
        if (this.txtSKU.Text.Trim().Length > 0) query.And(Exp.Like("s.BarCode", "%" + this.txtSKU.Text.Trim() + "%"));
        if (this.txtItemCode.Text.Trim().Length > 0) query.And(Exp.Like("m.ItemCode", "%" + this.txtItemCode.Text.Trim() + "%"));
        if (this.txtName.Text.Trim().Length > 0) query.And(Exp.Like("m.ItemName", "%" + this.txtName.Text.Trim() + "%"));
        if (this.txtColor.Text.Trim().Length > 0) query.And(Exp.Like("s.ColorCode", "%" + this.txtColor.Text.Trim().ToUpper() + "%"));
        if (this.txtSize.Text.Trim().Length > 0) query.And(Exp.Like("s.SizeCode", "%" + this.txtSize.Text.Trim().ToUpper() + "%"));

        this.repeatControl.DataSource = query.DataSet();
        this._head = INVCheckHead.Retrieve(session, this.OrderNumber);
        this.repeatControl.DataBind();
        this._head = null;

        if (fetchCount)
            this.magicPagerMain.RecordCount = this.magicPagerSub.RecordCount = query.Count();
        WebUtil.SetMagicPager(magicPagerMain, pageSize, pageIndex);
        WebUtil.SetMagicPager(magicPagerSub, pageSize, pageIndex);
        this.txtReturnToThisUrl.Value = this.GetReturnUrl();
    }
    protected void repeatControl_ItemDataBound(object sender, RepeaterItemEventArgs e)
    {
        DataRowView drv = e.Item.DataItem as DataRowView;
        if (drv == null) return;

        TextBox txt = e.Item.FindControl("txtQty") as TextBox;
        txt.Text = RenderUtil.FormatNumber(drv["CurrentQty"], "#0.##");
        if (this._head.Status != INVCheckStatus.Confirm)
            WebUtil.DisableControl(txt);

        Label lbl = e.Item.FindControl("lblBefQty") as Label;
        if (this._head.CheckType == INVCheckType.Implicit && (this._head.Status == INVCheckStatus.Confirm))
            lbl.Text = "-";
        else
            lbl.Text = RenderUtil.FormatNumber(drv["BeforeQty"], "#0.##");
    }

    private void RestoreLastQuery(ISession session, INVCheckHead head)
    {
        QueryHelper helper = new QueryHelper(this);
        if (helper.HasQueryParameter())
        {
            this.drpViewType.SelectedValue = helper.Pop("vt");
            this.drpArea.SelectedValue = helper.Pop("area");
            this.txtSection.Text= helper.Pop("sec");
            this.txtSKU.Text = helper.Pop("sku");
            this.txtItemCode.Text = helper.Pop("itmc");
            this.txtName.Text = helper.Pop("itmn");
            this.txtColor.Text = helper.Pop("color");
            this.txtSize.Text = helper.Pop("size");
        }
        int pageSize = Cast.Int(helper.Pop("ps"), this.magicPagerMain.PageSize);
        int pageIndex = Cast.Int(helper.Pop("pi"), 1);
        this.QueryAndBindData(session, head, pageSize, pageIndex, true);
    }
    private string GetReturnUrl()
    {
        QueryHelper helper = new QueryHelper(this);
        helper.Push("vt", this.drpViewType.SelectedValue);
        helper.Push("area", this.drpArea.SelectedValue);
        helper.Push("sec", this.txtSection.Text.Trim());
        helper.Push("sku", this.txtSKU.Text.Trim());
        helper.Push("itmc", this.txtItemCode.Text.Trim());
        helper.Push("itmn", this.txtName.Text.Trim());
        helper.Push("color", this.txtColor.Text.Trim());
        helper.Push("size", this.txtSize.Text.Trim());
        helper.Push("ps", this.magicPagerMain.PageSize);
        helper.Push("pi", this.magicPagerMain.CurrentPageIndex);

        helper.Push("ordNum", this.OrderNumber);
        helper.Push("return", WebUtil.Param("return"));
        if (log.IsDebugEnabled)
            log.DebugFormat("url 4 returnning to sto chk line: {0}", helper.OutputReturnUrl());
        return helper.OutputReturnUrl();
    }

    protected void MagicItemCommand(object sender, MagicItemEventArgs e)
    {
        try
        {
            if (e.CommandName == "Download")
            {
                #region 下载
                DataSet ds = null;
                INVCheckHead head = null;
                using (ISession session = new Session())
                {
                    head = INVCheckHead.Retrieve(session, this.OrderNumber);
                    Magic.Framework.ORM.Query.ObjectQuery query = session.CreateObjectQuery(string.Format(@"
select l.LineNumber as LineNumber,l.AreaCode as AreaCode,l.SectionCode as SectionCode
    ,s.BarCode as SKU,m.ItemCode as ItemCode
    ,s.ColorCode as ColorCode,color.ColorText as ColorText,s.SizeCode as SizeCode
    ,{0} as BeforeQty,l.CurrentQty as CurrentQty,m.ItemName as ItemName
from INVCheckLine l
inner join ItemSpec s on l.SKUID=s.SKUID
inner join ItemMaster m on m.ItemID=s.ItemID
left join ItemColor color on color.ColorCode=s.ColorCode
where l.OrderNumber=?ordNum {1}
order by l.LineNumber"
                            , head.CheckType == INVCheckType.Implicit && head.Status == INVCheckStatus.Confirm ? "'-'" : "l.BeforeQty"
                            , this.drpViewType.SelectedValue == "2" ? "and l.BeforeQty<>l.CurrentQty" : ""))
                        .Attach(typeof(INVCheckLine)).Attach(typeof(ItemSpec)).Attach(typeof(ItemMaster))
                        .Attach(typeof(ItemColor)).Attach(typeof(ItemSize))
                        .SetValue("?ordNum", this.OrderNumber, "l.OrderNumber");
                    if (this.drpArea.SelectedValue.Trim().Length > 0) query.And(Exp.Eq("l.AreaCode", this.drpArea.SelectedValue.Trim()));
                    if (this.txtSKU.Text.Trim().Length > 0) query.And(Exp.Like("s.BarCode", "%" + this.txtSKU.Text.Trim() + "%"));
                    if (this.txtItemCode.Text.Trim().Length > 0) query.And(Exp.Like("m.ItemCode", "%" + this.txtItemCode.Text.Trim() + "%"));
                    if (this.txtName.Text.Trim().Length > 0) query.And(Exp.Like("m.ItemName", "%" + this.txtName.Text.Trim() + "%"));
                    if (this.txtColor.Text.Trim().Length > 0) query.And(Exp.Like("s.ColorCode", "%" + this.txtColor.Text.Trim().ToUpper() + "%"));
                    if (this.txtSize.Text.Trim().Length > 0) query.And(Exp.Like("s.SizeCode", "%" + this.txtSize.Text.Trim().ToUpper() + "%"));
                    ds = query.DataSet();
                }
                if (ds == null)
                {
                    WebUtil.ShowError(this, "没有数据下载或者下载出错了");
                    return;
                }

                string fileName = DownloadUtil.DownloadXls("StockCheck_" + DateTime.Now.ToString("yyMMdd") + ".xls", "CK",
                    new List<DownloadFormat>()
                    {
                        new DownloadFormat(DataType.NumberText, "行号", "LineNumber"),
                        new DownloadFormat(DataType.Text, "库位", "AreaCode"),
                        new DownloadFormat(DataType.NumberText, "货架", "SectionCode"),
                        new DownloadFormat(DataType.NumberText, "SKU", "SKU"),
                        new DownloadFormat(DataType.NumberText, "货号", "ItemCode"),
                        new DownloadFormat(DataType.Text, "颜色", "ColorCode", "ColorText"),
                        new DownloadFormat(DataType.Text, "尺码", "SizeCode"),
                        new DownloadFormat((head.CheckType == INVCheckType.Implicit && head.Status == INVCheckStatus.Confirm ? DataType.Text : DataType.Number), "系统数量", "BeforeQty"),
                        new DownloadFormat(DataType.Number, "盘点数量", "CurrentQty"),
                        new DownloadFormat(DataType.Text, "商品名称", "ItemName")
                    }, ds);
                this.frameDownload.Attributes["src"] = fileName;
                #endregion
            }
            else if (e.CommandName == "Save")
            {
                #region 保存
                using (ISession session = new Session())
                {
                    try
                    {
                        session.BeginTransaction();

                        foreach (RepeaterItem item in this.repeatControl.Items)
                        {
                            TextBox text = item.FindControl("txtQty") as TextBox;
                            if (text == null) continue;
                            string lineNum = text.Attributes["lineNumber"];
                            if (string.IsNullOrEmpty(lineNum) || lineNum.Trim().Length <= 0) continue;
                            INVCheckLine line = INVCheckLine.Retrieve(session, this.OrderNumber, lineNum);
                            if (line != null)
                            {
                                line.CurrentQty = Cast.Decimal(text.Text, line.CurrentQty);
                                line.Update(session, "CurrentQty");
                            }
                        }

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
            else if (e.CommandName == "Release")
            {
                #region 发布
                using (ISession session = new Session())
                {
                    try
                    {
                        INVCheckHead head = INVCheckHead.Retrieve(session, this.OrderNumber);
                        session.BeginTransaction();
                        head.Release(session);
                        session.Commit();
                        WebUtil.ShowMsg(this, "发布成功");
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
                        INVCheckHead head = INVCheckHead.Retrieve(session, this.OrderNumber);
                        session.BeginTransaction();
                        head.Close(session);
                        session.Commit();
                        WebUtil.ShowMsg(this, "关闭成功，本次盘点任务已经完成");
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
        catch (Exception err)
        {
            WebUtil.ShowError(this, err);
        }
    }
    protected void MagicPager_PageChanged(object src, Magic.Web.UI.PageChangedEventArgs e)
    {
        this.magicPagerSub.PageSize = this.magicPagerMain.PageSize;
        using (ISession session = new Session())
        {
            this.QueryAndBindData(session, null, this.magicPagerMain.PageSize, e.NewPageIndex, false);
        }
    }
    protected void btnQuery_Click(object sender, System.Web.UI.ImageClickEventArgs e)
    {
        using (ISession session = new Session())
        {
            this.QueryAndBindData(session, null, this.magicPagerMain.PageSize, 1, true);
        }
    }
}