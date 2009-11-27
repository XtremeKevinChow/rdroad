using System;
using System.Collections;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using Magic.Framework.ORM;
using Magic.Basis;
using Magic.Framework.Utils;
using Magic.Framework.ORM.Query;
using System.Collections.Generic;

public partial class numTwo_ASSItemManage : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            //读取数据库，加载页面
            using (ISession session = new Session())
            {
                LoadInfo(session);
            }
        }
    }

    private void LoadInfo(ISession session)
    {
        //分页
        int pageIndex = 1;
        int pageSize = this.mpage.PageSize;

        QueryHelper help = new QueryHelper(this);
        if (help.HasQueryParameter())
        {
            help.SetValue(this.txtItemCode);
            help.SetValue(this.txtItemName);
            help.SetValue(this.txtItemSpec);
            help.SetValue(this.chkItemStatus);
            pageIndex = Cast.Int(help.Pop("pi"), this.mpage.CurrentPageIndex);
            pageSize = Cast.Int(help.Pop("ps"), this.mpage.PageSize);
        }
        QueryAndBindDate(session, pageIndex, pageSize, true);

    }

    public string StatusText(object obj)
    {
        ItemSpecStatus status = Cast.Enum<ItemSpecStatus>(obj);
        switch (status)
        {
            case ItemSpecStatus.Enable: return "有效";
            case ItemSpecStatus.Disable: return "停用";
        }
        return "";
    }

    private void QueryAndBindDate(ISession session, int pageIndex, int pageSize, bool allCount)
    {
        //取出查询条件值
        string itemCode = this.txtItemCode.Text.Trim();
        string itemName = this.txtItemName.Text.Trim();
        string itemSpec = this.txtItemSpec.Text.Trim();
        //取出状态值
        IList<ItemSpecStatus> status = new List<ItemSpecStatus>();
        foreach (ListItem item in this.chkItemStatus.Items)
            if (item.Selected) { status.Add(Cast.Enum<ItemSpecStatus>(item.Value)); }

        ObjectQuery query = session.CreateObjectQuery(@"
select m.ItemID as ItemID, m.ItemCode as ItemCode, m.ItemName as ItemName, 
    m.ItemDesc as ItemDesc, i.SKUID as SKUID, i.Status as Status
from ItemMaster m 
inner join ItemSpec i on m.ItemID = i.ItemID 
order by m.ItemCode")
            .Attach(typeof(ItemMaster)).Attach(typeof(ItemSpec))
            .Where(Exp.In("i.Status", status))
            .Where(Exp.Eq("m.ItemType", ItemType.AssistantItem))
            .SetPage(pageIndex, pageSize);
        //附加查询条件
        if (!string.IsNullOrEmpty(itemCode)) query.And(Exp.Like("m.ItemCode", "%" + itemCode.ToUpper() + "%"));
        if (!string.IsNullOrEmpty(itemName)) query.And(Exp.Like("m.ItemName", "%" + itemName + "%"));
        if (!string.IsNullOrEmpty(itemSpec)) query.And(Exp.Like("m.itemSpec", "%" + itemSpec + "%"));
        //绑定结果
        this.rInfo.DataSource = query.DataSet();
        this.rInfo.DataBind();
        if (allCount)
            this.mpage.RecordCount = this.mPageSub.RecordCount = query.Count();
        WebUtil.SetMagicPager(this.mpage, pageSize, pageIndex);
        WebUtil.SetMagicPager(this.mPageSub, pageSize, pageIndex);
        //取URL
        this.hidReturnUrl.Value = GetReturnUrl();
    }

    private string GetReturnUrl()
    {
        QueryHelper help = new QueryHelper(this);
        help.GetValue(this.txtItemCode);
        help.GetValue(this.txtItemName);
        help.GetValue(this.txtItemSpec);
        help.GetValue(this.chkItemStatus);

        help.Push("ps", this.mpage.PageSize);
        help.Push("pi", this.mpage.CurrentPageIndex);
        return help.OutputReturnUrl();
    }

    protected void mpage_PageChanged(object src, Magic.Web.UI.PageChangedEventArgs e)
    {
        this.mPageSub.PageSize = this.mpage.PageSize;//如果页面有2个翻页控件，则必须写上这一句(控件bug)
        int pageIndex = e.NewPageIndex;
        using (ISession session = new Session())
        {
            QueryAndBindDate(session, pageIndex, this.mpage.PageSize, false);
        }
    }

    protected void btnSearch_Click(object sender, EventArgs e)
    {
        using (ISession session = new Session())
        {
            QueryAndBindDate(session, 1, this.mpage.PageSize, true);
        }
    }

    protected void mboor_ItemCommand(object sender, Magic.Web.UI.MagicItemEventArgs args)
    {
        if (args.CommandName == "delete")
        {
            using (ISession session = new Session())
            {
                session.BeginTransaction();
                try
                {
                    bool deleted = false;
                    foreach (RepeaterItem item in this.rInfo.Items)
                    {
                        HtmlInputCheckBox chk = item.FindControl("chkItem") as HtmlInputCheckBox;
                        if (chk != null && chk.Checked && Cast.Int(chk.Value) > 0)
                        {
                            Magic.Basis.ItemSpec spec = ItemSpec.Retrieve(session, Cast.Int(chk.Value));
                            ItemMaster master = ItemMaster.Retrieve(session, spec.ItemID);
                            if (master.ItemType == ItemType.AssistantItem)
                            {
                                if (spec != null) spec.Delete(session);
                                if (master != null) master.Delete(session);
                                deleted = true;
                            }
                        }
                    }
                    session.Commit();
                    if (deleted) this.QueryAndBindDate(session, this.mpage.CurrentPageIndex, this.mpage.PageSize, true);
                    WebUtil.ShowMsg(this, "选择的辅料已经被删除", "操作成功");
                }
                catch (Exception err)
                {
                    session.Rollback();
                    WebUtil.ShowError(this, err);
                }
                this.QueryAndBindDate(session, this.mpage.CurrentPageIndex, this.mpage.PageSize, true);
            }
        }
    }
}
