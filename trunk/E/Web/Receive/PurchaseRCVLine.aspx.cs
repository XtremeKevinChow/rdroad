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

public partial class Receive_PurchaseRCVLine : System.Web.UI.Page
{
    private static log4net.ILog log = WebUtil.Logger(typeof(Receive_PurchaseRCVLine));

    private ISession _session;
    private IList<WHArea> _areas = null;
    private IList<WHArea> GetAreas()
    {
        if (this._session == null && this._areas == null) return new List<WHArea>();
        if (this._areas == null)
        {
            this._areas = ERPUtil.GetWHArea(this._session, RCVHead.ORD_TYPE_PUR, null);
        }
        return this._areas;
    }
    private RCVHead _head = null;

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
            this.txtRCVNumber.Value = this.OrderNumber;
            if (log.IsDebugEnabled)
            {
                log.Debug("PageLoad - rcv line: ordNum=" + this.OrderNumber + " return=" + WebUtil.Param("return"));
                log.Debug("url for scan to return: " + "PurchaseRCVLine.aspx?ordNum=" + this.OrderNumber + "&return=" + WebUtil.escape(WebUtil.Param("return")));
            }
            this.txtReturnUrl.Value = "PurchaseRCVLine.aspx?ordNum=" + this.OrderNumber + "&return=" + WebUtil.escape(WebUtil.Param("return"));
            this.cmdReturn1["Return"].NavigateUrl = this.cmdReturn2["Return"].NavigateUrl = WebUtil.Param("return");
            using (ISession session = new Session())
            {
                RCVHead head = RCVHead.Retrieve(session, this.OrderNumber);
                this.txtStatus.Value = head.Status.ToString();
                this.SetView(head);
                if (!string.IsNullOrEmpty(head.RefOrderNumber) && head.RefOrderNumber.Trim().Length > 0)
                {
                    this.txtPONumber.Value = head.RefOrderNumber;
                }
                this.QueryAndBindData(session, head);
            }
        }
    }

    private void SetView(RCVHead head)
    {
        switch (head.Status)
        {
            case ReceiveStatus.New:
                this.cmdEdit1.Visible = true;
                this.cmdEdit2.Visible = true;
                this.cmdClose1.Visible = false;
                this.cmdClose2.Visible = false;
                this.cmdPrint1.Visible = false;
                this.cmdPrint2.Visible = false;
                break;
            case ReceiveStatus.Release:
                this.cmdEdit1.Visible = false;
                this.cmdEdit2.Visible = false;
                this.cmdClose1.Visible = false;
                this.cmdClose2.Visible = false;
                this.cmdPrint1.Visible = true;
                this.cmdPrint2.Visible = true;
                break;
            case ReceiveStatus.Open:
                this.cmdEdit1.Visible = false;
                this.cmdEdit2.Visible = false;
                this.cmdClose1.Visible = true;
                this.cmdClose2.Visible = true;
                this.cmdPrint1.Visible = true;
                this.cmdPrint2.Visible = true;
                break;
            case ReceiveStatus.Close:
                this.cmdEdit1.Visible = false;
                this.cmdEdit2.Visible = false;
                this.cmdClose1.Visible = false;
                this.cmdClose2.Visible = false;
                this.cmdPrint1.Visible = true;
                this.cmdPrint2.Visible = true;
                break;
        }
    }

    private void QueryAndBindData(ISession session, RCVHead head)
    {
        this.repeatControl.DataSource = session.CreateObjectQuery(@"
select l.LineNumber as LineNumber,l.RefOrderLine as RefOrderLine
    ,s.BarCode as BarCode,m.ItemCode as ItemCode,m.ItemName as ItemName,s.ColorCode as ColorCode,color.ColorText as ColorText,s.SizeCode as SizeCode
    ,l.AreaCode as AreaCode,l.SectionCode as SectionCode,l.RCVTotalQty as RCVTotalQty,l.QualifiedQty as QualifiedQty
from RCVLine l
inner join ItemSpec s on l.SKUID=s.SKUID
inner join ItemMaster m on m.ItemID=s.ItemID
left join ItemColor color on color.ColorCode=s.ColorCode
where l.OrderNumber=?ordNum
order by l.LineNumber")
            .Attach(typeof(RCVLine)).Attach(typeof(ItemSpec)).Attach(typeof(ItemMaster)).Attach(typeof(ItemColor))
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
            foreach (WHArea area in this.GetAreas())
                drp.Items.Add(new ListItem(area.AreaCode, area.AreaCode));
            string selectedArea = Cast.String(drv["AreaCode"]).Trim();
            if (!string.IsNullOrEmpty(selectedArea))
                drp.SelectedValue = selectedArea;
        }
        //if (this._head.Status != ReceiveStatus.New)
        WebUtil.DisableControl(drp);

        TextBox txt;
        txt = e.Item.FindControl("txtSection") as TextBox;
        txt.Text = Cast.String(drv["SectionCode"]);
        //if (this._head.Status != ReceiveStatus.New)
        WebUtil.DisableControl(txt);
        txt = e.Item.FindControl("txtQualifiedQty") as TextBox;
        txt.Text = RenderUtil.FormatNumber(drv["QualifiedQty"], "#0.##");
        if (this._head.Status != ReceiveStatus.New)
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
            //这个功能也暂时废弃不用，因为采用扫描入库方式，并且需要控制货架、库位容量
            IList<RCVLine> linesToSave = new List<RCVLine>();
            foreach (RepeaterItem item in this.repeatControl.Items)
            {
                HtmlInputCheckBox chk = item.FindControl("checkbox") as HtmlInputCheckBox;
                TextBox text;
                RCVLine line = new RCVLine();
                line.OrderNumber = this.OrderNumber;
                line.LineNumber = chk.Value.Trim();
                text = item.FindControl("txtQualifiedQty") as TextBox;
                line.QualifiedQty = Cast.Decimal(text.Text.Trim());
                line.RCVTotalQty = line.QualifiedQty;

                linesToSave.Add(line);
            }

            using (ISession session = new Session())
            {
                try
                {
                    //检查
                    RCVHead head = RCVHead.Retrieve(session, this.OrderNumber);
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
                IList<RCVLine> linesToDelete = new List<RCVLine>();
                foreach (RepeaterItem item in this.repeatControl.Items)
                {
                    HtmlInputCheckBox chk = item.FindControl("checkbox") as HtmlInputCheckBox;
                    if (chk.Checked)
                    {
                        RCVLine line = RCVLine.Retrieve(session, this.OrderNumber, chk.Value.Trim());
                        if (line != null)
                            linesToDelete.Add(line);
                    }
                }

                try
                {
                    //检查
                    RCVHead head = RCVHead.Retrieve(session, this.OrderNumber);
                    if (head == null) return;
                    session.BeginTransaction();
                    head.DeleteLines(session, linesToDelete);
                    session.Commit();
                    this.QueryAndBindData(session, head);
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
                    RCVHead head = RCVHead.Retrieve(session, this.OrderNumber);
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
                    RCVHead head = RCVHead.Retrieve(session, this.OrderNumber);
                    session.BeginTransaction();
                    head.Close(session, true);
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
    }

    //弹出选择框，手工选择采购订单明细进行收货的方式
    //这种方式暂时废弃不用
    protected void cmdAddLines_Click(object sender, EventArgs e)
    {
        string[] linesArray = this.txtPOLines.Value.Trim().Trim(';').Split(';');
        if (linesArray == null || linesArray.Length <= 0) return;

        using (ISession session = new Session())
        {
            RCVHead head = RCVHead.Retrieve(session, this.OrderNumber);
            try
            {
                session.BeginTransaction();
                foreach (string s in linesArray)
                {
                    POLine poLine = null;
                    if (!string.IsNullOrEmpty(s) && s.Trim().Length > 0)
                        poLine = POLine.Retrieve(session, head.RefOrderNumber, s);
                    if (poLine != null)
                        head.AddLine(session, poLine);
                }
                head.Update(session, "CurrentLineNumber");
                session.Commit();
            }
            catch (Exception er)
            {
                session.Rollback();
                WebUtil.ShowError(this, er);
            }
            this.QueryAndBindData(session, head);
        }
    }
}