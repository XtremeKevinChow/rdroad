using System;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using Magic.Basis;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Query;
using Magic.Framework.Utils;
using Magic.Web.UI;

public partial class Basis_RestrictLogis2MemberManager : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            using (ISession session = new Session())
            {
                RestoreLastQuery(session);
            }
        }
    }

    void RestoreLastQuery(ISession session)
    {
        int pageSize = 20;
        int pageIndex = 1;
        QueryHelper helper = new QueryHelper(this);
        if (helper.HasQueryParameter())
        {
            helper.SetValue(this.txtLogis);
            helper.SetValue(this.txtMemberName);
            helper.SetValue(this.txtMemberNumber);
            pageSize = Cast.Int(helper.Pop("ps"), this.magicPagerMain.PageSize);
            pageIndex = Cast.Int(helper.Pop("pi"), 1);
        }
        QueryAndBindData(session, pageIndex, pageSize, true);
    }

    string GetReturnUrl()
    {
        QueryHelper helper = new QueryHelper(this);
        helper.GetValue(this.txtLogis); //传参_方法1
        helper.GetValue(this.txtMemberName); //传参_方法2
        helper.GetValue(this.txtMemberNumber);
        helper.Push("ps", this.magicPagerMain.PageSize);
        helper.Push("pi", this.magicPagerMain.CurrentPageIndex);
        return helper.OutputReturnUrl();
    }

    void QueryAndBindData(ISession session, int pageIndex, int pageSize, bool fetchRecordCount)
    {
        string name = this.txtMemberName.Text.Trim();
        string number = this.txtMemberNumber.Text.Trim();
        string logis = this.txtLogis.Text.Trim();

        //定义OQL
        ObjectQuery query = session.CreateObjectQuery(@"
select m.MemberNumber as MemberNumber,m.Name as MemberName,l.ShortName as LogisName,lm.MemberId as MemberID,lm.LogisId as LogisId
from RestrictLogis2Member lm
inner join Member m on lm.MemberId=m.MemberID
inner join Logistics l on l.LogisticCompID=lm.LogisId
order by m.MemberNumber, l.ShortName")
            .Attach(typeof(RestrictLogis2Member)).Attach(typeof(Member)).Attach(typeof(Logistics))
            .SetPage(pageIndex, pageSize);
        //追加多个条件
        if (!string.IsNullOrEmpty(name)) query.And(Exp.Like("m.Name", "%" + name + "%"));
        if (!string.IsNullOrEmpty(number)) query.And(Exp.Like("m.MemberNumber", "%" + number + "%"));
        if (!string.IsNullOrEmpty(logis)) query.And(Exp.Like("l.ShortName", "%" + logis + "%"));
        this.rptVendor.DataSource = query.DataSet();
        this.rptVendor.DataBind();
        if (fetchRecordCount)
            this.magicPagerMain.RecordCount = this.magicPagerSub.RecordCount = query.Count();
        WebUtil.SetMagicPager(magicPagerMain, pageSize, pageIndex);
        WebUtil.SetMagicPager(magicPagerSub, pageSize, pageIndex);
        this.hidReturnUrl.Value = GetReturnUrl();

    }

    protected void MagicItemCommand(object sender, MagicItemEventArgs e)
    {
        if (e.CommandName == "Delete")
        {
            bool deleted = false;
            using (ISession session = new Session())
            {
                session.BeginTransaction();
                try
                {
                    foreach (RepeaterItem item in this.rptVendor.Items)
                    {
                        HtmlInputCheckBox chk = item.FindControl("checkbox") as HtmlInputCheckBox;
                        if (chk != null && chk.Checked)
                        {
                            int mid = Cast.Int(chk.Attributes["mid"]), lid = Cast.Int(chk.Attributes["lid"]);
                            deleted = deleted || RestrictLogis2Member.Delete(session, lid, mid) > 0;
                        }
                    }
                    session.Commit();
                    if (deleted)
                        QueryAndBindData(session, magicPagerMain.CurrentPageIndex, magicPagerMain.PageSize, true);
                }
                catch (Exception ex)
                {
                    session.Rollback();
                    WebUtil.ShowError(this, ex);
                }
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
}
