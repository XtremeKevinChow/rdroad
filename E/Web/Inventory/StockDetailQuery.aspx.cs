using System;
using System.Web.UI;
using System.Web.UI.WebControls;
using Magic.Framework.Utils;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Query;
using Magic.Basis;
using Magic.ERP;
using Magic.ERP.Core;

public partial class Inventory_StockDetailQuery : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            using (ISession session = new Session())
            {
                InitDrpDown(session);
                WebUtil.SetMagicPager(magicPagerMain, magicPagerMain.PageSize, 1);
                WebUtil.SetMagicPager(magicPagerSub, magicPagerSub.PageSize, 1);
            }
        }
    }

    void InitDrpDown(ISession session)
    {
        this.drpArea.DataTextField = "AreaCode";
        this.drpArea.DataValueField = "AreaCode";
        this.drpArea.DataSource = session.CreateEntityQuery<WHArea>()
            .Where(Exp.Eq("Status", WHStatus.Enable) & Exp.Eq("IsReservedArea", false) & Exp.Eq("IsTransArea", true))
            .List<WHArea>();
        this.drpArea.DataBind();
        this.drpArea.Items.Add(new ListItem("　", ""));
        this.drpArea.Items[this.drpArea.Items.Count - 1].Selected = true;
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

    void QueryAndBindData(ISession session, int pageIndex, int pageSize, bool fetchRecordCount)
    {
        string itemCode = this.txtItemCode.Text.Trim();
        string itemName = this.txtItemName.Text.Trim();
        string colorCode = this.txtColorCode.Text.Trim().ToUpper();
        string sizeCode = this.txtSizeCode.Text.Trim().ToUpper();
        string sku = this.txtSku.Text.Trim().ToUpper();
        string area = this.drpArea.SelectedValue;
        string section = this.txtSection.Text.Trim();
        ObjectQuery query = session.CreateObjectQuery(@"
select spec.BarCode as BarCode,m.ItemCode as ItemCode,m.ItemName as ItemName
    ,spec.ColorCode as ColorCode,color.ColorText as ColorText,spec.SizeCode as SizeCode
    ,detail.StockQty as StockQty,detail.FrozenQty as FrozenQty,detail.SectionCode as SectionCode,detail.AreaCode as WHAName
from StockDetail detail
inner join ItemSpec spec on detail.SKUID = spec.SKUID
inner join ItemMaster m on m.ItemID = spec.ItemID
inner join WHArea wha on detail.AreaCode = wha.AreaCode
left join WHSection whs on detail.SectionCode = whs.SectionCode and detail.AreaCode=whs.AreaCode
left join ItemColor color on spec.ColorCode=color.ColorCode
order by m.ItemCode,spec.ColorCode,spec.SizeCode
")
            .Attach(typeof(StockDetail)).Attach(typeof(ItemMaster)).Attach(typeof(ItemSpec))
            .Attach(typeof(WHSection)).Attach(typeof(WHArea))
            .Attach(typeof(ItemColor))
            .SetPage(pageIndex, pageSize);

        //追加条件
        if (!string.IsNullOrEmpty(itemCode)) query.And(Exp.Like("m.ItemCode", "%" + itemCode + "%"));
        if (!string.IsNullOrEmpty(itemName)) query.And(Exp.Like("m.ItemName", "%" + itemName + "%"));
        if (!string.IsNullOrEmpty(colorCode)) query.And(Exp.Like("spec.ColorCode", "%"+ colorCode.ToUpper() + "%"));
        if (!string.IsNullOrEmpty(sku)) query.And(Exp.Like("spec.BarCode", "%"+ sku.ToUpper() + "%"));
        if (!string.IsNullOrEmpty(sizeCode)) query.And(Exp.Like("spec.SizeCode", "%"+ sizeCode.ToUpper() + "%"));
        if (!string.IsNullOrEmpty(area)) query.And(Exp.Eq("detail.AreaCode", area.ToUpper()));
        if (!string.IsNullOrEmpty(section)) query.And(Exp.Like("detail.SectionCode", "%" + section.ToUpper() + "%"));
        this.rptPO.DataSource = query.DataSet();
        this.rptPO.DataBind();
        if (fetchRecordCount)
            this.magicPagerMain.RecordCount = this.magicPagerSub.RecordCount = query.Count();
        WebUtil.SetMagicPager(magicPagerMain, pageSize, pageIndex);
        WebUtil.SetMagicPager(magicPagerSub, pageSize, pageIndex);
    }

    protected void btnQuery_Click(object sender, ImageClickEventArgs e)
    {
        using (ISession session = new Session())
        {
            QueryAndBindData(session, 1, magicPagerMain.PageSize, true);
        }
    }
}
