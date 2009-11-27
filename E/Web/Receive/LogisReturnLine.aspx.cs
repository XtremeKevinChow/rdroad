using System;
using System.Collections.Generic;
using System.Data;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using Magic.Framework.Utils;
using Magic.Framework.ORM;
using Magic.ERP;
using Magic.ERP.CRM;
using Magic.ERP.Core;
using Magic.ERP.Orders;
using Magic.Basis;
using Magic.Web.UI;

public partial class Receive_LogisReturnLine : System.Web.UI.Page
{
    private ISession _session = null;
    private IList<WHArea> _areas = null;
    private IList<WHArea> GetAreas()
    {
        if (this._session == null && this._areas == null) return new List<WHArea>();
        if (this._areas == null)
        {
            this._areas = ERPUtil.GetWHArea(this._session, ReturnHead.ORDER_TYPE_MBR_RTN, null);
        }
        return this._areas;
    }
    private ReturnHead _head = null;
    private string ReturnUrl
    {
        get
        {
            string returnUrl = WebUtil.Param("return");
            if (string.IsNullOrEmpty(returnUrl) || returnUrl.Trim().Length <= 0)
                returnUrl = "LogisReturnManage.aspx";
            return returnUrl;
        }
    }
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
            this.txtReturnUrl.Value = "LogisReturnLine.aspx?ordNum=" + this.OrderNumber + "&return=" + WebUtil.escape(this.ReturnUrl);
            this.cmdReturn1["Return"].NavigateUrl = this.cmdReturn2["Return"].NavigateUrl = this.ReturnUrl;
            using (ISession session = new Session())
            {
                ReturnHead head = ReturnHead.Retrieve(session, this.OrderNumber);
                this.snView.SNNumber = head.RefOrderNumber;
                this.SetView(head);
                this.QueryAndBindData(session, head);
            }
        }
    }

    private void SetView(ReturnHead head)
    {
        switch (head.Status)
        {
            case ReturnStatus.New:
                this.cmdEdit1.Visible = true;
                this.cmdEdit2.Visible = true;
                this.cmdClose1.Visible = false;
                this.cmdClose2.Visible = false;
                break;
            case ReturnStatus.Release:
                this.cmdEdit1.Visible = false;
                this.cmdEdit2.Visible = false;
                this.cmdClose1.Visible = false;
                this.cmdClose2.Visible = false;
                break;
            case ReturnStatus.Open:
                this.cmdEdit1.Visible = false;
                this.cmdEdit2.Visible = false;
                this.cmdClose1.Visible = true;
                this.cmdClose2.Visible = true;
                break;
            case ReturnStatus.Close:
                this.cmdEdit1.Visible = false;
                this.cmdEdit2.Visible = false;
                this.cmdClose1.Visible = false;
                this.cmdClose2.Visible = false;
                break;
        }
    }

    private void QueryAndBindData(ISession session, ReturnHead head)
    {
        this.repeatControl.DataSource = session.CreateObjectQuery(@"
select rtl.LineNumber as LineNumber
    ,s.BarCode as BarCode,m.ItemCode as ItemCode,m.ItemName as ItemName,s.ColorCode as ColorCode,color.ColorText as ColorText,s.SizeCode as SizeCode
    ,st.Name as SaleType,snl.Price as Price,snl.Quantity as ShippingQty
    ,rtl.Quantity as ReturnQty,rtl.AreaCode as AreaCode,rtl.SectionCode as SectionCode
from ReturnLine rtl
inner join CRMSNLine snl on rtl.RefOrderLineID=snl.ID
inner join ItemSpec s on s.SKUID=rtl.SKUID
inner join ItemMaster m on m.ItemID=s.ItemID
left join ItemColor color on color.ColorCode=s.ColorCode
left join CRMSaleType st on st.ID=snl.SellType
where rtl.OrderNumber=?ordNum
order by rtl.LineNumber")
            .Attach(typeof(ReturnLine)).Attach(typeof(ItemSpec)).Attach(typeof(ItemMaster)).Attach(typeof(ItemColor))
            .Attach(typeof(CRMSNLine)).Attach(typeof(CRMSaleType))
            .SetValue("?ordNum", this.OrderNumber, "rtl.OrderNumber")
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
        if (this.GetAreas() != null && this.GetAreas().Count > 0)
        {
            drp.Items.Clear();
            drp.Items.Add(new ListItem("", ""));
            foreach (WHArea area in this.GetAreas())
                drp.Items.Add(new ListItem(area.AreaCode, area.AreaCode));
            string selectedArea = Cast.String(drv["AreaCode"]).Trim();
            if (!string.IsNullOrEmpty(selectedArea))
                drp.SelectedValue = selectedArea;
        }
        if (this._head != null && this._head.Status != ReturnStatus.New)
            WebUtil.DisableControl(drp);

        TextBox txt;
        txt = e.Item.FindControl("txtSection") as TextBox;
        txt.Text = Cast.String(drv["SectionCode"]);
        if (this._head != null && this._head.Status != ReturnStatus.New)
            WebUtil.DisableControl(txt);
        if (this._head != null && this._head.Status != ReturnStatus.New)
        {
            HtmlInputCheckBox chk = e.Item.FindControl("checkbox") as HtmlInputCheckBox;
            chk.Visible = false;
        }
    }

    protected void MagicItemCommand(object sender, MagicItemEventArgs e)
    {
        if (e.CommandName == "Save")
        {
            #region 保存
            IList<ReturnLine> linesToSave = new List<ReturnLine>();
            foreach (RepeaterItem item in this.repeatControl.Items)
            {
                HtmlInputCheckBox chk = item.FindControl("checkbox") as HtmlInputCheckBox;
                DropDownList drp = item.FindControl("drpArea") as DropDownList;
                TextBox text;
                ReturnLine line = new ReturnLine();
                line.OrderNumber = this.OrderNumber;
                line.LineNumber = chk.Value.Trim();
                line.AreaCode = drp.SelectedValue;
                text = item.FindControl("txtSection") as TextBox;
                line.SectionCode = text.Text.Trim();

                linesToSave.Add(line);
            }

            using (ISession session = new Session())
            {
                try
                {
                    //检查
                    ReturnHead head = ReturnHead.Retrieve(session, this.OrderNumber);
                    if (head == null) return;
                    session.BeginTransaction();
                    head.UpdateLines(session, linesToSave);
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
                    session.BeginTransaction();
                    ReturnHead head = ReturnHead.Retrieve(session, this.OrderNumber);
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
                    ReturnHead head = ReturnHead.Retrieve(session, this.OrderNumber);
                    if (head == null) return;
                    session.BeginTransaction();
                    head.Close(session);
                    session.Commit();
                    WebUtil.ShowMsg(this, "退货单" + head.OrderNumber + "已经完成");
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