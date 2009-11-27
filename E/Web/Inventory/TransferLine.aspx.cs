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
using Magic.ERP.Core;
using Magic.ERP.Orders;
using Magic.Basis;
using Magic.Web.UI;

public partial class Inventory_TransferLine : System.Web.UI.Page
{
    private static log4net.ILog log = WebUtil.Logger(typeof(Inventory_TransferLine));

    private ISession _session;
    private WHTransferHead _head = null;

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
            this.txtUrlToThisPage.Value = "TransferLine.aspx?ordNum=" + this.OrderNumber + "&return=" + WebUtil.escape(WebUtil.Param("return"));
            this.cmdReturn1["Return"].NavigateUrl = this.cmdReturn2["Return"].NavigateUrl = WebUtil.Param("return");
            using (ISession session = new Session())
            {
                WHTransferHead head = WHTransferHead.Retrieve(session, this.OrderNumber);
                log.DebugFormat("PageLoad - 移库明细: ordNum={0}, status={1}", head.OrderNumber, head.Status.ToString());
                this.SetView(head);
                this.QueryAndBindData(session, head);
            }
        }
    }

    private void SetView(WHTransferHead head)
    {
        switch (head.Status)
        {
            case WHTransferStatus.New:
                this.cmdEdit1.Visible = true;
                this.cmdEdit2.Visible = true;
                this.cmdClose1.Visible = false;
                this.cmdClose2.Visible = false;
                break;
            case WHTransferStatus.Release:
                this.cmdEdit1.Visible = false;
                this.cmdEdit2.Visible = false;
                this.cmdClose1.Visible = false;
                this.cmdClose2.Visible = false;
                break;
            case WHTransferStatus.Open:
                this.cmdEdit1.Visible = false;
                this.cmdEdit2.Visible = false;
                this.cmdClose1.Visible = true;
                this.cmdClose2.Visible = true;
                break;
            case WHTransferStatus.Close:
                this.cmdEdit1.Visible = false;
                this.cmdEdit2.Visible = false;
                this.cmdClose1.Visible = false;
                this.cmdClose2.Visible = false;
                break;
        }
    }

    private void QueryAndBindData(ISession session, WHTransferHead head)
    {
        this.repeatControl.DataSource = session.CreateObjectQuery(@"
select l.LineNumber as LineNumber,sku.BarCode as BarCode,i.ItemCode as ItemCode,i.ItemName as ItemName
    ,sku.ColorCode as ColorCode,color.ColorText as ColorText,sku.SizeCode as SizeCode
    ,l.FromArea as FromArea,l.FromSection as FromSection,l.ToArea as ToArea,l.ToSection as ToSection
    ,l.MoveQty as MoveQty
from WHTransferLine l
inner join ItemSpec sku on l.SKUID=sku.SKUID
inner join ItemMaster i on i.ItemID=sku.ItemID
left join ItemColor color on color.ColorCode=sku.ColorCode
where l.OrderNumber=?ordNum
order by l.LineNumber")
            .Attach(typeof(WHTransferLine))
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
                            log.DebugFormat("Delete - 移库明细: ordNum={0}, lineNum={1}", this.OrderNumber, chk.Value);
                            WHTransferLine.Delete(session, this.OrderNumber, chk.Value);
                            deleted = true;
                        }
                    }
                    session.Commit();
                    this.QueryAndBindData(session, WHTransferHead.Retrieve(session, this.OrderNumber));
                    WebUtil.ShowMsg(this, "选择的明细已经删除");
                }
                catch (Exception er)
                {
                    session.Rollback();
                    log.Error("Error - 删除移库明细: ", er);
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
                    WHTransferHead head = WHTransferHead.Retrieve(session, this.OrderNumber);
                    log.DebugFormat("Release - 移库单: {0}", this.OrderNumber);
                    head.Release(session);
                    session.Commit();
                    WebUtil.ShowMsg(this, "发布成功");
                    this.QueryAndBindData(session, head);
                    this.SetView(head);
                }
                catch (Exception er)
                {
                    session.Rollback();
                    log.Error("Error - 移库单: ", er);
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
                    WHTransferHead head = WHTransferHead.Retrieve(session, this.OrderNumber);
                    session.BeginTransaction();
                    log.DebugFormat("Close - 移库单: {0}", this.OrderNumber);
                    head.Close(session);
                    session.Commit();
                    WebUtil.ShowMsg(this, string.Format("移库单{0}已经关闭", this.OrderNumber));
                    this.QueryAndBindData(session, head);
                    this.SetView(head);
                }
                catch (Exception er)
                {
                    session.Rollback();
                    log.Error("Error - 移库单: ", er);
                    WebUtil.ShowError(this, er);
                }
            }
            #endregion
        }
    }
}