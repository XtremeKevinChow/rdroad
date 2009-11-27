using System;
using System.Collections.Generic;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using Magic.Framework.ORM;
using Magic.ERP;
using Magic.ERP.Core;
using Magic.ERP.Orders;
using Magic.Basis;
using Magic.Web.UI;

public partial class Inventory_StockAdjustmentLine : System.Web.UI.Page
{
    private static log4net.ILog log = WebUtil.Logger(typeof(Inventory_StockAdjustmentLine));

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
            this.txtUrlToThisPage.Value = "StockAdjustmentLine.aspx?ordNum=" + this.OrderNumber + "&return=" + WebUtil.escape(WebUtil.Param("return"));
            this.cmdReturn1["Return"].NavigateUrl = this.cmdReturn2["Return"].NavigateUrl = WebUtil.Param("return");
            using (ISession session = new Session())
            {
                INVCheckHead head = INVCheckHead.Retrieve(session, this.OrderNumber);
                log.DebugFormat("PageLoad - sto adj line: ordNum={0}, status={1}", head.OrderNumber, head.Status.ToString());
                this.SetView(head);
                this.QueryAndBindData(session, head);
            }
        }
    }

    private void SetView(INVCheckHead head)
    {
        switch (head.Status)
        {
            case INVCheckStatus.New:
                this.cmdEdit1.Visible = true;
                this.cmdEdit2.Visible = true;
                this.cmdClose1.Visible = false;
                this.cmdClose2.Visible = false;
                break;
            case INVCheckStatus.Release:
                this.cmdEdit1.Visible = false;
                this.cmdEdit2.Visible = false;
                this.cmdClose1.Visible = true;
                this.cmdClose2.Visible = true;
                break;
            case INVCheckStatus.Close:
                this.cmdEdit1.Visible = false;
                this.cmdEdit2.Visible = false;
                this.cmdClose1.Visible = false;
                this.cmdClose2.Visible = false;
                break;
        }
    }

    private void QueryAndBindData(ISession session, INVCheckHead head)
    {
        this.repeatControl.DataSource = session.CreateObjectQuery(@"
select l.LineNumber as LineNumber,sku.BarCode as BarCode,i.ItemCode as ItemCode,i.ItemName as ItemName
    ,sku.ColorCode as ColorCode,color.ColorText as ColorText,sku.SizeCode as SizeCode
    ,l.AreaCode as AreaCode,l.SectionCode as SectionCode,l.BeforeQty as StockQty,l.CurrentQty as AdjQty
from INVCheckLine l
inner join ItemSpec sku on l.SKUID=sku.SKUID
inner join ItemMaster i on i.ItemID=sku.ItemID
left join ItemColor color on color.ColorCode=sku.ColorCode
where l.OrderNumber=?ordNum
order by l.LineNumber")
            .Attach(typeof(INVCheckLine))
            .Attach(typeof(ItemSpec)).Attach(typeof(ItemMaster)).Attach(typeof(ItemColor))
            .SetValue("?ordNum", this.OrderNumber, "l.OrderNumber")
            .DataSet();
        this.repeatControl.DataBind();
    }

    protected void MagicItemCommand(object sender, MagicItemEventArgs e)
    {
        if (e.CommandName == "Delete")
        {
            #region 删除
            using (ISession session = new Session())
            {
                try
                {
                    bool deleted = false;
                    session.BeginTransaction();
                    foreach (RepeaterItem item in this.repeatControl.Items)
                    {
                        HtmlInputCheckBox chk = item.FindControl("checkbox") as HtmlInputCheckBox;
                        if (chk.Checked)
                        {
                            log.DebugFormat("Delete - sto adj line: ordNum={0}, lineNum={1}", this.OrderNumber, chk.Value);
                            INVCheckLine.Delete(session, this.OrderNumber, chk.Value);
                            deleted = true;
                        }
                    }
                    session.Commit();
                    if (deleted)
                    {
                        this.QueryAndBindData(session, INVCheckHead.Retrieve(session, this.OrderNumber));
                        WebUtil.ShowMsg(this, "选择的明细已经删除");
                    }
                }
                catch (Exception er)
                {
                    session.Rollback();
                    log.Error("Error - delete sto adj line: ", er);
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
                    log.DebugFormat("Release - sto adj: {0}", this.OrderNumber);
                    head.Release(session);
                    session.Commit();
                    WebUtil.ShowMsg(this, "发布成功");
                    this.QueryAndBindData(session, head);
                    this.SetView(head);
                }
                catch (Exception er)
                {
                    session.Rollback();
                    log.Error("Error - release sto adj: ", er);
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
                    log.DebugFormat("Close - sto adj: {0}", this.OrderNumber);
                    head.Close(session);
                    session.Commit();
                    WebUtil.ShowMsg(this, string.Format("库存调整单{0}已经关闭", this.OrderNumber));
                    this.QueryAndBindData(session, head);
                    this.SetView(head);
                }
                catch (Exception er)
                {
                    session.Rollback();
                    log.Error("Error - close sto adj: ", er);
                    WebUtil.ShowError(this, er);
                }
            }
            #endregion
        }
    }
}