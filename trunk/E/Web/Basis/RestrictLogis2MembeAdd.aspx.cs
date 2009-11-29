using System;
using System.Collections.Generic;
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
            if (Request["return"] != null)
                this.toolbarTop["Return"].NavigateUrl = this.toolbarBottom["Return"].NavigateUrl = Request["return"];
            using (ISession session = new Session())
            {
                this.drpLogis.Items.Clear();
                this.drpLogis.Items.Add(new ListItem("", ""));
                IList<Logistics> logis = Logistics.GetEffectiveLogistics(session);
                foreach (Logistics l in logis)
                    this.drpLogis.Items.Add(new ListItem(l.ShortName, l.LogisticCompID.ToString()));
            }
        }
    }

    void QueryAndBindData(ISession session, int pageIndex, int pageSize, bool fetchRecordCount)
    {
        string name = this.txtMemberName.Text.Trim();
        string number = this.txtMemberNumber.Text.Trim();

        EntityQuery query = session.CreateEntityQuery<Member>()
            .Where(Exp.Like("Name", "%" + name + "%"))
            .And(Exp.Like("MemberNumber", "%" + number + "%"))
            .SetPage(pageIndex, pageSize)
            .OrderBy("MemberNumber");
        this.rptVendor.DataSource = query.List<Member>();
        this.rptVendor.DataBind();
        if (fetchRecordCount)
            this.magicPagerMain.RecordCount = this.magicPagerSub.RecordCount = query.Count();
        WebUtil.SetMagicPager(magicPagerMain, pageSize, pageIndex);
        WebUtil.SetMagicPager(magicPagerSub, pageSize, pageIndex);
    }

    protected void MagicItemCommand(object sender, MagicItemEventArgs e)
    {
        if (e.CommandName == "Add")
        {
            int logisId = Cast.Int(this.drpLogis.SelectedValue, 0);
            if (logisId <= 0)
            {
                WebUtil.ShowMsg(this, "请选择物流公司");
                return;
            }
            bool added = false;
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
                            RestrictLogis2Member r = new RestrictLogis2Member();
                            r.LogisId = logisId;
                            r.MemberId = Cast.Int(chk.Value);
                            r.Create(session);
                            added = true;
                        }
                    }
                    session.Commit();
                }
                catch (Exception ex)
                {
                    session.Rollback();
                    WebUtil.ShowError(this, ex);
                }

                if (added)
                    this.Response.Redirect(Request["return"]);
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
