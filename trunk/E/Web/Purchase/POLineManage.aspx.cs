using System;
using System.Collections.Generic;
using System.Data;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using Magic.Framework.Utils;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Query;
using Magic.Web.UI;
using Magic.Basis;
using Magic.ERP;
using Magic.ERP.Orders;
using Magic.Security;

public partial class Purchase_POLineManage : System.Web.UI.Page
{
    ISession _session = null;
    log4net.ILog logger = log4net.LogManager.GetLogger(typeof(POLine));

    private string OrderNumber
    {
        get
        {
            return WebUtil.Param("OrderNum").Trim();
        }
    }

    protected void Page_Load(object sender, EventArgs e)
    {
        if (Request["return"] != null) this.cmdReturn1["Return"].NavigateUrl = this.cmdReturn2["Return"].NavigateUrl = Request["return"];
        if (!IsPostBack)
        {
            using (_session = new Session())
            {
                POHead head = POHead.Retrieve(_session, this.OrderNumber);
                if (head != null)
                    BindPOLine(_session, head);
                this.SetView(_session, head);
                if (head.Status == POStatus.New)
                    this.SetQuickAdd(_session, head);
            }
        }
    }

    private void SetView(ISession session, POHead head)
    {
        POStatus status = head == null ? POStatus.Close : head.Status;
        this.hidStatus.Value = status.ToString();
        //按钮屏蔽判断  新建 1  发布 2 已完成3
        //新增 cmdSelectItem  保存cmdSave1 取消cmdCancel1 返回cmdReturn1
        switch (status)
        {
            case POStatus.New:
                this.cmdSelectItem1.Visible = true;
                this.cmdSelectItem2.Visible = true;
                this.cmdSave1.Visible = true;
                this.cmdSave2.Visible = true;
                this.cmdDelete1.Visible = true;
                this.cmdDelete2.Visible = true;
                this.cmdCancel1.Visible = false;
                this.cmdCancel2.Visible = false;
                this.cmdRelease1.Visible = true;
                this.cmdRelease2.Visible = true;
                this.cmdClose1.Visible = false;
                this.cmdClose2.Visible = false;
                this.divQuickAdd1.Visible = true;
                this.divQuickAdd2.Visible = true;
                break;
            case POStatus.Release:
                this.cmdSelectItem1.Visible = false;
                this.cmdSelectItem2.Visible = false;
                this.cmdSave1.Visible = false;
                this.cmdSave2.Visible = false;
                this.cmdDelete1.Visible = false;
                this.cmdDelete2.Visible = false;
                this.cmdRelease1.Visible = false;
                this.cmdRelease2.Visible = false;
                this.cmdCancel1.Visible = true;
                this.cmdCancel2.Visible = true;
                if (head.ApproveResult == ApproveStatus.UnApprove)
                {
                    this.cmdClose1.Visible = false;
                    this.cmdClose2.Visible = false;
                }
                else
                {
                    this.cmdClose1.Visible = true;
                    this.cmdClose2.Visible = true;
                }
                this.divQuickAdd1.Visible = false;
                this.divQuickAdd2.Visible = false;
                this.SetReadonly();
                break;
            case POStatus.Close:
                this.cmdSelectItem1.Visible = false;
                this.cmdSelectItem2.Visible = false;
                this.cmdSave1.Visible = false;
                this.cmdSave2.Visible = false;
                this.cmdDelete1.Visible = false;
                this.cmdDelete2.Visible = false;
                this.cmdRelease1.Visible = false;
                this.cmdRelease2.Visible = false;
                this.cmdCancel1.Visible = false;
                this.cmdCancel2.Visible = false;
                this.cmdClose1.Visible = false;
                this.cmdClose2.Visible = false;
                this.divQuickAdd1.Visible = false;
                this.divQuickAdd2.Visible = false;
                this.SetReadonly();
                break;
        }
    }
    private void SetQuickAdd(ISession session, POHead head)
    {
        this.txtDemandDate.Value = head.DefaultPlanDate.ToString("yyyy-MM-dd");
    }

    private void BindPOLine(ISession session, POHead poHead)
    {
        //显示POHead内容
        if (poHead != null)
        {
            this.LabOrderNumber.Text = poHead.OrderNumber;
            //供应商
            Vendor vendor = Vendor.Retrieve(session, poHead.VendorID);
            if (vendor != null)
                this.LabVendorID.Text = vendor.ShortName;
            //采购组
            this.LabPurchGroupCode.Text = poHead.PurchGroupCode;
            this.LabTaxInclusiveAmt.Text = RenderUtil.FormatNumber(poHead.TaxInclusiveAmt, "#0.#0");
        }

        //POLine.SKUID=ItemSpec.SKUID  ItemSpec.ItemID=ItemMaster.ItemID
        //货号ItemMaster.ItemCode  商品名称ItemMaster.ItemName 
        //颜色ItemSpec.ColorCode  尺码  ItemSpec.SizeCode
        ObjectQuery query = session.CreateObjectQuery(@"
select m.ItemCode as ItemCode,m.ItemName as ItemName,s.ColorCode as ColorCode,s.SizeCode as SizeCode
    ,p.LineNumber as LineNumber,p.LineStatus as LineStatus,p.PurchaseQty as PurchaseQty,p.PlanDate as PlanDate,p.Price as Price,p.OrderNumber as OrderNumber
    ,p.TaxInclusiveAmt as TaxInclusiveAmt,p.TaxValue as TaxValue,p.TaxAmt as TaxAmt,p.TaxID as TaxID
from POLine p
inner join ItemSpec s on p.SKUID=s.SKUID
inner join ItemMaster m on s.ItemID=m.ItemID
WHERE p.OrderNumber=?
order by p.LineNumber")
            .Attach(typeof(POLine)).Attach(typeof(ItemSpec)).Attach(typeof(ItemMaster))
            .SetValue(0, this.OrderNumber, "p.OrderNumber");
        DataSet ds = query.DataSet();
        this.rptPL.DataSource = ds;
        this.rptPL.DataBind();

        int number = 0;
        foreach (DataRow row in ds.Tables[0].Rows)
            number += Cast.Int(row["PurchaseQty"]);
        this.lblTotalQty.Text = number.ToString();
    }

    private void SetReadonly()
    {
        //屏幕文本框
        foreach (RepeaterItem item in this.rptPL.Items)
        {
            HtmlInputText txtPurchaseQty = item.FindControl("txtPurchaseQty") as HtmlInputText;
            WebUtil.DisableControl(txtPurchaseQty);
            HtmlInputText txtPlanDate = item.FindControl("txtPlanDate") as HtmlInputText;
            WebUtil.DisableControl(txtPlanDate);
            HtmlInputText txtPrice = item.FindControl("txtPrice") as HtmlInputText;
            WebUtil.DisableControl(txtPrice);
        }
    }

    protected void MagicItemCommand(object sender, MagicItemEventArgs e)
    {
        if (e.CommandName == "Save")
        {
            #region 保存
            //txtPurchaseQty txtPlanDate txtPrice
            bool updated = false;
            using (_session = new Session())
            {
                POHead head = POHead.Retrieve(_session, this.OrderNumber);
                if (head == null || head.Status != POStatus.New) return;

                _session.BeginTransaction();
                try
                {
                    foreach (RepeaterItem item in this.rptPL.Items)
                    {
                        HtmlInputCheckBox chk = item.FindControl("checkbox") as HtmlInputCheckBox;
                        if (!string.IsNullOrEmpty(chk.Value))
                        {
                            HtmlInputText txtPurchaseQty = item.FindControl("txtPurchaseQty") as HtmlInputText;
                            HtmlInputText txtPlanDate = item.FindControl("txtPlanDate") as HtmlInputText;
                            HtmlInputText txtPrice = item.FindControl("txtPrice") as HtmlInputText;
                            POLine poLine = POLine.Retrieve(_session, this.OrderNumber, chk.Value);
                            if (poLine == null || poLine.LineStatus != POLineStatus.Open) continue;

                            poLine.PurchaseQty = Cast.Decimal(txtPurchaseQty.Value, poLine.PurchaseQty);
                            poLine.PlanDate = Cast.DateTime(txtPlanDate.Value, poLine.PlanDate);
                            poLine.Price = Cast.Decimal(txtPrice.Value, poLine.Price);
                            poLine.TaxID = 0;
                            poLine.TaxValue = 0M;
                            //含税额（含税采购成本）  TaxInclusiveAmt 含税额 = 数量*单价
                            poLine.TaxInclusiveAmt = poLine.PurchaseQty * poLine.Price;
                            //不含税额（采购成本） TaxExlusiveAmt 含税额-税额
                            poLine.TaxExlusiveAmt = 0M; // poLine.TaxInclusiveAmt / (1 + poLine.TaxValue);
                            //税额    TaxAmt  税额 = 不含税额*税率
                            poLine.TaxAmt = 0M; //poLine.TaxExlusiveAmt * poLine.TaxValue;
                            poLine.Update(_session, "PurchaseQty", "TaxID", "TaxValue", "PlanDate", "Price", "TaxInclusiveAmt");
                            updated = true;
                        }
                    }
                    //更新统计信息
                    if (updated)
                        UpdatePOLineAndPoHead(_session, head);
                    _session.Commit();
                    if (updated)
                    {
                        BindPOLine(_session, head);
                        WebUtil.ShowMsg(this, "采购订单明细保存成功", "操作成功");
                    }
                }
                catch (Exception ex)
                {
                    _session.Rollback();
                    WebUtil.ShowError(this, ex);
                }
            }
            #endregion
        }
        else if (e.CommandName == "Cancel")
        {
            #region 取消明细
            bool updated = false;
            using (_session = new Session())
            {
                POHead head = POHead.Retrieve(_session, this.OrderNumber);
                if (head.Status != POStatus.Release)
                    return;

                _session.BeginTransaction();
                try
                {
                    foreach (RepeaterItem item in this.rptPL.Items)
                    {
                        HtmlInputCheckBox chk = item.FindControl("checkbox") as HtmlInputCheckBox;
                        if (chk != null && chk.Checked && !string.IsNullOrEmpty(chk.Value))
                        {
                            POLine poLine = POLine.Retrieve(_session, this.OrderNumber, chk.Value);
                            if (poLine == null || poLine.LineStatus != POLineStatus.Open) continue;
                            poLine.LineStatus = POLineStatus.Cancel;
                            poLine.ModifyUser = Magic.Security.SecuritySession.CurrentUser.UserId;
                            poLine.ModifyTime = DateTime.Now;
                            poLine.Update(_session, "LineStatus", "ModifyUser", "ModifyTime");
                            updated = true;
                        }
                    }
                    //再次统计 POLine
                    if (updated)
                        UpdatePOLineAndPoHead(_session, head);
                    _session.Commit();

                    if (updated)
                    {
                        BindPOLine(_session, head);
                        WebUtil.ShowMsg(this, "选择的明细已经取消", "操作成功");
                    }
                }
                catch (Exception ex)
                {
                    _session.Rollback();
                    WebUtil.ShowError(this, ex);
                }
            }
            #endregion
        }
        else if (e.CommandName == "Delete")
        {
            #region 删除明细
            bool deleted = false;
            using (_session = new Session())
            {
                POHead head = POHead.Retrieve(_session, this.OrderNumber);
                if (head == null || head.Status != POStatus.New) return;

                _session.BeginTransaction();
                try
                {
                    foreach (RepeaterItem item in this.rptPL.Items)
                    {
                        HtmlInputCheckBox chk = item.FindControl("checkbox") as HtmlInputCheckBox;
                        if (chk != null && chk.Checked && !string.IsNullOrEmpty(chk.Value))
                        {
                            POLine line = POLine.Retrieve(_session, this.OrderNumber, chk.Value);
                            if (line.LineStatus != POLineStatus.Open) continue;
                            line.Delete(_session);
                            deleted = true;
                        }
                    }
                    //再次统计 POLine
                    if (deleted)
                        UpdatePOLineAndPoHead(_session, head);
                    _session.Commit();

                    if (deleted)
                    {
                        BindPOLine(_session, head);
                        WebUtil.ShowMsg(this, "选择的明细已经删除", "操作成功");
                    }
                }
                catch (Exception ex)
                {
                    _session.Rollback();
                    WebUtil.ShowError(this, ex);
                }
            }
            #endregion
        }
        else if (e.CommandName == "Release" || e.CommandName == "Close")
        {
            #region 发布，关闭
            using (_session = new Session())
            {
                try
                {
                    POHead head = POHead.Retrieve(_session, this.OrderNumber);
                    _session.BeginTransaction();
                    if (e.CommandName == "Release")
                        head.Release(_session);
                    else
                        head.Close(_session);
                    _session.Commit();

                    BindPOLine(_session, head);
                    this.SetView(_session, head);
                    WebUtil.ShowMsg(this, "订单已经" + (e.CommandName == "Release" ? "发布" : "关闭"), "操作成功");
                }
                catch (Exception er)
                {
                    _session.Rollback();
                    WebUtil.ShowError(this, er);
                }
            }
            #endregion
        }
        else if (e.CommandName == "QuickAdd")
        {
            #region 快速添加
            using (_session = new Session())
            {
                //检查
                string itemCode = this.txtItemCode.Value.Trim();
                string color = this.txtColorCode.Value.Trim().ToUpper();
                string size = this.txtSizeCode.Value.Trim().ToUpper();
                decimal qty = Cast.Decimal(this.txtPurchaseQty.Value.Trim(), 0M);
                decimal price = Cast.Decimal(this.txtPrice.Value.Trim(), 0M);
                DateTime date = Cast.DateTime(this.txtDemandDate.Value.Trim(), new DateTime(1900, 1, 1));

                if (qty <= 0M)
                {
                    this.txtAlertMsg.InnerText = string.Format("采购数量{0}不是有效的数字", this.txtPurchaseQty.Value.Trim());
                    return;
                }
                if (price <= 0M)
                {
                    this.txtAlertMsg.InnerText = string.Format("单价{0}不是有效的数字", this.txtPrice.Value.Trim());
                    return;
                }
                if (date <= new DateTime(1900, 1, 1))
                {
                    this.txtAlertMsg.InnerText = string.Format("无效的需求日期{0}", this.txtDemandDate.Value.Trim());
                    return;
                }

                IList<ItemMaster> masters = _session.CreateEntityQuery<ItemMaster>()
                    .Where(Exp.Eq("ItemCode", itemCode)).List<ItemMaster>();
                if (masters == null || masters.Count <= 0)
                {
                    this.txtAlertMsg.InnerText = string.Format("货号{0}不存在", itemCode);
                    return;
                }
                ItemColor objColor = ItemColor.Retrieve(_session, color);
                if (objColor == null)
                {
                    this.txtAlertMsg.InnerText = string.Format("颜色代码{0}不存在", color);
                    return;
                }
                IList<ItemSpec> skus = _session.CreateEntityQuery<ItemSpec>()
                    .Where(Exp.Eq("ItemID", masters[0].ItemID) & Exp.Eq("ColorCode", objColor.ColorCode) & Exp.Eq("SizeCode", size))
                    .List<ItemSpec>();
                if (skus == null || skus.Count <= 0)
                {
                    this.txtAlertMsg.InnerText = string.Format("不存在货号:{0} 颜色:{1} 尺码:{2}的SKU", itemCode, color, size);
                    return;
                }
                ItemSize objSize = ItemSize.Retrieve(_session, size, masters[0].CategoryID);
                if (objSize == null)
                {
                    this.txtAlertMsg.InnerText = string.Format("尺码{0}不存在", size);
                    return;
                }

                //添加操作
                POHead head = POHead.Retrieve(_session, this.OrderNumber);
                POLine line = new POLine();
                line.OrderNumber = this.OrderNumber;
                line.LineNumber = head.NextLineNumber();
                line.LineStatus = POLineStatus.Open;
                line.SKUID = skus[0].SKUID;
                line.PurchaseQty = qty;
                line.Price = price;
                line.TaxID = 0;
                line.TaxValue = 0M;
                line.TaxInclusiveAmt = line.PurchaseQty * line.Price;
                line.TaxExlusiveAmt = 0M; // line.TaxInclusiveAmt / (1 + line.TaxValue);
                line.TaxAmt = 0M; //line.TaxExlusiveAmt * line.TaxValue;
                line.PlanDate = date;
                line.ActualDate = new DateTime(1900, 1, 1);
                line.ReceiveQty = 0M;
                line.IQCQty = 0M;
                line.UnfinishedReceiveQty = 0M;
                line.ModifyUser = SecuritySession.CurrentUser.UserId;
                line.ModifyTime = DateTime.Now;
                line.UnitID = 0;

                try
                {
                    _session.BeginTransaction();
                    line.Create(_session);
                    head.Update(_session, "CurrentLineNumber");
                    this.UpdatePOLineAndPoHead(_session, head);
                    _session.Commit();
                    BindPOLine(_session, head);
                    this.txtAlertMsg.InnerText = "添加成功，订单行号为" + line.LineNumber;
                    this.txtPurchaseQty.Value = "";
                }
                catch (Exception er)
                {
                    _session.Rollback();
                    WebUtil.ShowError(this, er);
                }
            }
            #endregion
        }
    }

    private void UpdatePOLineAndPoHead(ISession session, POHead head)
    {
        //POLine 统计
        IList<POLine> lines = session.CreateEntityQuery<POLine>()
            .Where(Exp.Eq("OrderNumber", this.OrderNumber))
            .And(Exp.NEq("LineStatus", POLineStatus.Cancel))
            .List<POLine>();
        decimal totalTaxInclusiveAmt = 0M; //, totalTaxAmt = 0M, totalTaxExlusiveAmt = 0M;
        foreach (POLine line in lines)
        {
            totalTaxInclusiveAmt += line.TaxInclusiveAmt;
            //totalTaxAmt += line.TaxAmt;
            //totalTaxExlusiveAmt += line.TaxExlusiveAmt;
        }

        //更新主表　TaxInclusiveAmt　TaxAmt　TaxExlusiveAmt
        head.TaxInclusiveAmt = totalTaxInclusiveAmt;
        //head.TaxAmt = totalTaxAmt;
        //head.TaxExclusiveAmt = totalTaxExlusiveAmt;
        head.Update(session, "TaxInclusiveAmt");
    }

    protected void cmdProduct_Click(object sender, EventArgs e)
    {
        if (this.txtSkuId.Value.Trim().Length <= 0) return;

        bool added = false;
        using (_session = new Session())
        {
            string[] idArray = this.txtSkuId.Value.Trim().Trim(';').Split(';');
            try
            {
                POHead head = POHead.Retrieve(_session, this.OrderNumber);
                if (head == null || head.Status != POStatus.New) return;

                _session.BeginTransaction();
                foreach (string s in idArray)
                {
                    POLine poLine = new POLine();
                    poLine.OrderNumber = this.OrderNumber;
                    poLine.SKUID = Cast.Int(s, 0);
                    if (poLine.SKUID <= 0) continue;
                    poLine.LineNumber = head.NextLineNumber();
                    poLine.LineStatus = POLineStatus.Open;
                    poLine.PurchaseQty = 0M;
                    poLine.Price = 0M;
                    //Vendor vendor = null;
                    //if (head.VendorID > 0) vendor = Vendor.Retrieve(_session, head.VendorID);
                    //if (vendor != null)
                    //{
                    //    poLine.TaxID = vendor.TaxID;
                    //    poLine.TaxValue = vendor.Tax;
                    //}
                    //else
                    //{
                    //    poLine.TaxID = 0;
                    //    poLine.TaxValue = 0M;
                    //}
                    poLine.TaxID = 0;
                    poLine.TaxValue = 0M;
                    poLine.TaxInclusiveAmt = 0M;
                    poLine.TaxAmt = 0M;
                    poLine.TaxExlusiveAmt = 0M;
                    poLine.PlanDate = head.DefaultPlanDate;
                    poLine.ActualDate = new DateTime(1900, 1, 1);
                    poLine.ReceiveQty = 0M;
                    poLine.IQCQty = 0M;
                    poLine.UnfinishedReceiveQty = 0M;
                    poLine.ModifyUser = SecuritySession.CurrentUser.UserId;
                    poLine.ModifyTime = DateTime.Now;
                    poLine.UnitID = 0;

                    poLine.Create(_session);
                    added = true;
                }
                head.Update(_session, "CurrentLineNumber");
                _session.Commit();
                if (added)
                {
                    BindPOLine(_session, head);
                    WebUtil.ShowMsg(this, "订单明细保存成功", "操作成功");
                }
            }
            catch (Exception ex)
            {
                _session.Rollback();
                logger.Info("保存POLine", ex);
                WebUtil.ShowError(this, "发生未处理的异常,请刷新页面重新操作，或者联系系统管理员");
            }
        }
    }
    protected void rptPL_ItemDataBound(object sender, RepeaterItemEventArgs e)
    {
        DataRowView drv = e.Item.DataItem as DataRowView;
        if (drv == null) return;

        POLineStatus status = Cast.Enum<POLineStatus>(drv["LineStatus"]);
        Label lblStatus = e.Item.FindControl("lblLineStatus") as Label;
        lblStatus.Text = POLine.POLineStatusText(status);
        HtmlInputCheckBox chk;
        switch (status)
        {
            case POLineStatus.Open:
                lblStatus.ForeColor = System.Drawing.Color.Blue;
                break;
            case POLineStatus.Close:
                lblStatus.ForeColor = System.Drawing.Color.Black;
                chk = e.Item.FindControl("checkbox") as HtmlInputCheckBox;
                chk.Visible = false;
                break;
            case POLineStatus.Cancel:
                lblStatus.ForeColor = System.Drawing.Color.Gray;
                break;
        }
    }
}