using System;
using System.Data;
using System.Web.UI;
using System.Web.UI.WebControls;
using Magic.Basis;
using Magic.ERP.Core;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Query;
using Magic.Framework.Utils;
using Magic.Web.UI;

public partial class Inventory_StockWarningMgt : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            using (ISession session = new Session())
            {
                this.QueryAndBindData(session, 1, this.magicPagerMain.PageSize, true);
            }
        }
    }

    private void QueryAndBindData(ISession session, int pageIndex, int pageSize, bool fetchRecordCount)
    {
        ObjectQuery query = session.CreateObjectQuery(@"
select s.SKUID as SKUID,i.ItemCode as ItemCode,i.ItemName as ItemName,s.ColorCode as ColorCode,c.ColorText as ColorText,s.SizeCode as SizeCode
    ,s.StoMethod as StoMethod,s.StoMax as StoMax,s.StoSafe as StoSafe,s.StoMin as StoMin,sto.StockQty as StoQty
from ItemSpec s
inner join ItemMaster i on s.ItemCode=i.ItemCode
inner join StockSummary sto on sto.SKUID=s.SKUID
left join ItemColor c on c.ColorCode=s.ColorCode")
            .Attach(typeof(ItemMaster)).Attach(typeof(ItemSpec)).Attach(typeof(ItemColor))
            .Attach(typeof(StockSummary))
            .SetPage(pageIndex, pageSize);

        if (!string.IsNullOrEmpty(this.txtItemCode.Text))
            query.And(Exp.Like("s.ItemCode", "%" + this.txtItemCode.Text.Trim().ToUpper() + "%"));
        if (!string.IsNullOrEmpty(this.txtItemName.Text))
            query.And(Exp.Like("i.ItemName", "%" + this.txtItemName.Text.Trim().ToUpper() + "%"));
        if (!string.IsNullOrEmpty(this.txtColor.Text))
            query.And(Exp.Eq("s.ColorCode", this.txtColor.Text.Trim().ToUpper()));
        if (!string.IsNullOrEmpty(this.txtSize.Text))
            query.And(Exp.Eq("s.SizeCode", this.txtSize.Text.Trim().ToUpper()));

        this.repeater.DataSource = query.DataSet();
        this.repeater.DataBind();
        if (fetchRecordCount)
            this.magicPagerMain.RecordCount = this.magicPagerSub.RecordCount = query.Count();
        WebUtil.SetMagicPager(magicPagerMain, pageSize, pageIndex);
        WebUtil.SetMagicPager(magicPagerSub, pageSize, pageIndex);
    }
    protected void btnQuery_Click(object sender, ImageClickEventArgs e)
    {
        using (ISession session = new Session())
        {
            this.QueryAndBindData(session, 1, this.magicPagerMain.PageSize, true);
        }
    }
    protected void MagicItemCommand(object sender, MagicItemEventArgs e)
    {
        if (e.CommandName != "Save")
            return;

        using (ISession session = new Session())
        {
            try
            {
                session.BeginTransaction();
                foreach (RepeaterItem ri in this.repeater.Items)
                {
                    HiddenField hidSkuId = ri.FindControl("hidSkuId") as HiddenField;
                    int skuId = Cast.Int(hidSkuId.Value);
                    CheckBox chkMethod = ri.FindControl("chkMethod") as CheckBox;
                    TextBox txt;
                    txt = ri.FindControl("txtMax") as TextBox;
                    decimal max = Cast.Decimal(txt.Text);
                    txt = ri.FindControl("txtSafe") as TextBox;
                    decimal safe = Cast.Decimal(txt.Text);
                    txt = ri.FindControl("txtMin") as TextBox;
                    decimal min = Cast.Decimal(txt.Text);

                    ItemSpec sku = ItemSpec.Retrieve(session, skuId);
                    if (sku != null && ((sku.StoMethod > 0) != chkMethod.Checked || sku.StoMax != max || sku.StoMin != min || sku.StoSafe != safe))
                    {
                        sku.StoMethod = chkMethod.Checked ? 1 : 0;
                        sku.StoMax = max;
                        sku.StoMin = min;
                        sku.StoSafe = safe;
                        sku.Update(session, "StoMethod", "StoMax", "StoMin", "StoSafe");
                    }
                }
                session.Commit();
                WebUtil.ShowMsg(this, "保存成功");
            }
            catch (Exception er)
            {
                session.Rollback();
                WebUtil.ShowError(this, er.Message);
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
    protected void repeater_ItemDataBound(object sender, System.Web.UI.WebControls.RepeaterItemEventArgs e)
    {
        DataRowView drv = e.Item.DataItem as DataRowView;
        if (drv == null) return;

        HiddenField hidSkuId = e.Item.FindControl("hidSkuId") as HiddenField;
        if (hidSkuId == null) return;
        hidSkuId.Value = Cast.Int(drv["SKUID"]).ToString();

        CheckBox chkMethod = e.Item.FindControl("chkMethod") as CheckBox;
        if (chkMethod != null) chkMethod.Checked = Cast.Int(drv["StoMethod"]) > 0;

        TextBox txt;
        txt = e.Item.FindControl("txtMax") as TextBox;
        if (txt != null) txt.Text = Cast.Decimal(drv["StoMax"]).ToString("#.##");
        txt = e.Item.FindControl("txtSafe") as TextBox;
        if (txt != null) txt.Text = Cast.Decimal(drv["StoSafe"]).ToString("#.##");
        txt = e.Item.FindControl("txtMin") as TextBox;
        if (txt != null) txt.Text = Cast.Decimal(drv["StoMin"]).ToString("#.##");
    }
}