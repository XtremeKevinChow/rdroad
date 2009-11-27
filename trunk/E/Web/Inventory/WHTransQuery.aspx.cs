using System;
using System.Data;
using System.Web.UI;
using System.Web.UI.WebControls;
using Magic.Framework.Utils;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Query;
using Magic.Basis;
using Magic.ERP;
using Magic.ERP.Core;

public partial class Inventory_WHTransQuery : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            this.txtDateFrom.Text = DateTime.Now.AddDays(-7).ToString("yyyy-MM-dd");
            this.txtDateTo.Text = DateTime.Now.ToString("yyyy-MM-dd");
            WebUtil.SetMagicPager(magicPagerMain, magicPagerMain.PageSize, 1);
            WebUtil.SetMagicPager(magicPagerSub, magicPagerSub.PageSize, 1);
            using (ISession session = new Session())
            {
                InitDrpDown(session);
                //this.QueryAndBindData(session, 1, this.magicPagerMain.PageSize, true);
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

        //TransTypeDef
        ObjectQuery query = session.CreateObjectQuery(@"select t.TransDefText as TransDefText,t.TransTypeCode as TransTypeCode from TransTypeDef t ").Attach(typeof(TransTypeDef));
        this.drpTransTypeDef.DataTextField = "TransDefText";
        this.drpTransTypeDef.DataValueField = "TransTypeCode";
        this.drpTransTypeDef.DataSource = query.DataSet();
        this.drpTransTypeDef.DataBind();
        this.drpTransTypeDef.Items.Add(new ListItem("　", ""));
        this.drpTransTypeDef.Items[this.drpTransTypeDef.Items.Count - 1].Selected = true;
    }

    void QueryAndBindData(ISession session, int pageIndex, int pageSize, bool fetchRecordCount)
    {
        string itemCode = this.txtItemCode.Text.Trim();
        string itemName = this.txtItemName.Text.Trim();
        string colorCode = this.txtColorCode.Text.Trim();
        string sizeCode = this.txtSizeCode.Text.Trim();
        string area = this.drpArea.SelectedValue;
        string section = this.txtSection.Text.Trim();
        string transType = this.drpTransTypeDef.SelectedValue;
        string refOrderNumber = this.txtRefOrderNumber.Text.Trim();
        string originNumber = this.txtOriginalOrderNumber.Text.Trim();
        DateTime start = Cast.DateTime(this.txtDateFrom.Text, new DateTime(1900, 1, 1));
        DateTime end = Cast.DateTime(this.txtDateTo.Text, new DateTime(1900, 1, 1));

        ObjectQuery query = session.CreateObjectQuery(@"
select m.ItemCode as ItemCode,m.ItemName as ItemName
    ,spec.ColorCode as ColorCode,color.ColorText as ColorText,spec.SizeCode as SizeCode
    ,line.AreaCode as WName,section.SectionCode as SectionCode
    ,line.TransQty as TransQty,line.RefOrderNumber as RefOrderNumber,line.OriginalOrderNumber as OriginalOrderNumber
    ,line.TransDate as TransDate,line.TransTime as TransTime
    ,typedef.TransDefText as TransDefText
from WHTransLine line
inner join ItemSpec spec on line.SKUID=spec.SKUID
inner join ItemMaster m on m.ItemID=spec.ItemID
inner join WHArea warea on warea.AreaCode=line.AreaCode
left join WHSection section on section.AreaCode=line.AreaCode and section.SectionCode=line.SectionCode
left join ItemColor color on color.ColorCode=spec.ColorCode
inner join TransTypeDef typedef on line.TransTypeCode=typedef.TransTypeCode
order by line.TransDate desc,line.TransTime desc
")
            .Attach(typeof(WHTransLine)).Attach(typeof(TransTypeDef))
            .Attach(typeof(ItemMaster)).Attach(typeof(ItemSpec)).Attach(typeof(ItemColor))
            .Attach(typeof(WHLocation)).Attach(typeof(WHSection)).Attach(typeof(WHArea))
            .SetPage(pageIndex, pageSize);

        //追加条件
        if (!string.IsNullOrEmpty(itemCode)) query.And(Exp.Like("m.ItemCode", "%" + itemCode + "%"));
        if (!string.IsNullOrEmpty(itemName)) query.And(Exp.Like("m.ItemName", "%" + itemName + "%"));
        if (!string.IsNullOrEmpty(colorCode)) query.And(Exp.Like("spec.ColorCode", colorCode.ToUpper() + "%"));
        if (!string.IsNullOrEmpty(sizeCode)) query.And(Exp.Like("spec.SizeCode", sizeCode.ToUpper() + "%"));
        if (!string.IsNullOrEmpty(area)) query.And(Exp.Eq("line.AreaCode", area.ToUpper()));
        if (!string.IsNullOrEmpty(section)) query.And(Exp.Like("line.SectionCode", "%" + section.ToUpper() + "%"));
        if (!string.IsNullOrEmpty(transType)) query.And(Exp.Eq("line.TransTypeCode", transType));
        if (!string.IsNullOrEmpty(refOrderNumber)) query.And(Exp.Like("line.RefOrderNumber", refOrderNumber.ToUpper()));
        if (!string.IsNullOrEmpty(originNumber)) query.And(Exp.Like("line.OriginalOrderNumber", originNumber.ToUpper()));
        if (start > new DateTime(1900, 1, 1)) query.And(Exp.Ge("line.TransDate", Cast.Int(start.ToString("yyyyMMdd"))));
        if (end > new DateTime(1900, 1, 1)) query.And(Exp.Le("line.TransDate", Cast.Int(end.AddDays(1).ToString("yyyyMMdd"))));

        this.rptPO.DataSource = query.DataSet();
        this.rptPO.DataBind();
        if (fetchRecordCount)
            this.magicPagerMain.RecordCount = this.magicPagerSub.RecordCount = query.Count();
        WebUtil.SetMagicPager(magicPagerMain, pageSize, pageIndex);
        WebUtil.SetMagicPager(magicPagerSub, pageSize, pageIndex);
    }

    protected void MagicPager_PageChanged(object src, Magic.Web.UI.PageChangedEventArgs e)
    {
        //翻页事件
        this.magicPagerSub.PageSize = this.magicPagerMain.PageSize; //如果页面有2个翻页控件，则必须写上这一句(控件bug)
        using (ISession session = new Session())
        {
            this.QueryAndBindData(session, e.NewPageIndex, e.Pager.PageSize, false);
        }
    }

    protected void btnQuery_Click(object sender, ImageClickEventArgs e)
    {
        using (ISession session = new Session())
        {
            this.QueryAndBindData(session, 1, magicPagerMain.PageSize, true);
        }
    }
    protected void rptPO_ItemDataBound(object sender, RepeaterItemEventArgs e)
    {
        DataRowView drv = e.Item.DataItem as DataRowView;
        if (drv == null) return;
        Label label = e.Item.FindControl("lblTime") as Label;
        string date = Cast.String(drv["TransDate"]);
        string time = Cast.String(drv["TransTime"]).PadLeft(4, '0');
        System.Text.StringBuilder builder = new System.Text.StringBuilder();
        builder.Append(date.Substring(0, 4)).Append("-").Append(date.Substring(4, 2)).Append("-").Append(date.Substring(6, 2))
            .Append(" ")
            .Append(time.Substring(0, 2)).Append(":").Append(time.Substring(2, 2));
        label.Text = builder.ToString();
    }
}