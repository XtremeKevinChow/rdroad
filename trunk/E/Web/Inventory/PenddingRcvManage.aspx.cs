using System;
using System.Data;
using System.Web.UI;
using System.Web.UI.WebControls;
using Magic.Basis;
using Magic.ERP.Orders;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Query;
using Magic.Framework.Utils;
using Magic.Web.UI;

public partial class Inventory_PenddingRcvManage : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            this.chkFlag.Checked = true;
            using (ISession session = new Session())
            {
                WebUtil.SetMagicPager(magicPagerMain, magicPagerMain.PageSize, 1);
                WebUtil.SetMagicPager(magicPagerSub, magicPagerSub.PageSize, 1);
            }
        }
    }
    void QueryAndBindData(ISession session, int pageIndex, int pageSize, bool fetchRecordCount)
    {
        string itemCode = this.txtItemCode.Text.Trim();
        string itemName = this.txtItemName.Text.Trim();
        string colorCode = this.txtColorCode.Text.Trim();
        string sizeCode = this.txtSizeCode.Text.Trim();
        string sku = this.txtSku.Text.Trim();

        ObjectQuery query = session.CreateObjectQuery(@"
select i.ItemCode as ItemCode,i.ItemName as ItemName,sku.ColorCode as ColorCode,sku.SizeCode as SizeCode,color.ColorText as ColorText,sku.BarCode as BarCode
    ,pr.Qty as Qty,sku.SKUID as SKUID
from ItemMaster i
inner join ItemSpec sku on i.ItemCode=sku.ItemCode
left join ItemColor color on color.ColorCode=sku.ColorCode
left join PendingReceipt pr on pr.SKU_ID=sku.SKUID
order by i.ItemCode,sku.ColorCode,sku.SizeCode")
            .Attach(typeof(ItemMaster)).Attach(typeof(ItemSpec)).Attach(typeof(ItemColor))
            .Attach(typeof(PendingReceipt))
            .SetPage(pageIndex, pageSize);
        //追加条件
        if (chkFlag.Checked) query.And(Exp.Gt("pr.Qty", 0));
        if (!string.IsNullOrEmpty(itemCode)) query.And(Exp.Like("i.ItemCode", "%" + itemCode + "%"));
        if (!string.IsNullOrEmpty(itemName)) query.And(Exp.Like("i.ItemName", "%" + itemName + "%"));
        if (!string.IsNullOrEmpty(colorCode)) query.And(Exp.Like("sku.ColorCode", "%" + colorCode.ToUpper() + "%"));
        if (!string.IsNullOrEmpty(sizeCode)) query.And(Exp.Like("sku.SizeCode", "%" + sizeCode.ToUpper() + "%"));
        if (!string.IsNullOrEmpty(sku)) query.And(Exp.Like("sku.BarCode", "%" + sku.ToUpper() + "%"));
        this.rptPO.DataSource = query.DataSet();
        this.rptPO.DataBind();
        if (fetchRecordCount)
            this.magicPagerMain.RecordCount = this.magicPagerSub.RecordCount = query.Count();
        WebUtil.SetMagicPager(magicPagerMain, pageSize, pageIndex);
        WebUtil.SetMagicPager(magicPagerSub, pageSize, pageIndex);
    }
    protected void MagicItemCommand(object sender, MagicItemEventArgs e)
    {
        using (ISession session = new Session())
        {
            try
            {
                session.BeginTransaction();
                foreach (RepeaterItem item in this.rptPO.Items)
                {
                    HiddenField hidsku = item.FindControl("hidsku") as HiddenField;
                    TextBox text = item.FindControl("txtQty") as TextBox;
                    int skuId = Cast.Int(hidsku.Value);
                    decimal qty = Cast.Decimal(text.Text);
                    if (skuId <= 0) continue;

                    PendingReceipt pr = PendingReceipt.Retrieve(session, skuId);
                    if (pr == null)
                    {
                        if (qty <= 0M) continue;
                        pr = new PendingReceipt() { SKU_ID = skuId };
                        pr.Qty = qty;
                        pr.Create(session);
                    }
                    else
                    {
                        pr.Qty = qty;
                        pr.Update(session);
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
    }
    protected void MagicPager_PageChanged(object src, Magic.Web.UI.PageChangedEventArgs e)
    {
        //翻页事件
        this.magicPagerSub.PageSize = this.magicPagerMain.PageSize; //如果页面有2个翻页控件，则必须写上这一句(控件bug)
        using (ISession session = new Session())
        {
            QueryAndBindData(session, e.NewPageIndex, e.Pager.PageSize, false);
        }
    }
    protected void btnQuery_Click(object sender, ImageClickEventArgs e)
    {
        using (ISession session = new Session())
        {
            QueryAndBindData(session, 1, magicPagerMain.PageSize, true);
        }
    }
    protected void rptPO_ItemDataBound(object sender, System.Web.UI.WebControls.RepeaterItemEventArgs e)
    {
        TextBox txt = e.Item.FindControl("txtQty") as TextBox;
        HiddenField hidsku = e.Item.FindControl("hidsku") as HiddenField;
        DataRowView drv = e.Item.DataItem as DataRowView;
        if (txt == null || drv == null) return;

        txt.Text = Cast.Int(drv["Qty"]).ToString();
        hidsku.Value = Cast.Int(drv["SKUID"]).ToString();
    }
}