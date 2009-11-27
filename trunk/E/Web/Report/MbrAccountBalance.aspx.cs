using System;
using System.Collections.Generic;
using System.Data;
using System.Web.UI;
using System.Web.UI.WebControls;
using Magic.Basis;
using Magic.ERP.Report;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Query;
using Magic.Framework.Utils;
using Magic.Web.UI;

public partial class Report_MbrAccountBalance : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            this.cmdReturn1["Return"].NavigateUrl = this.cmdReturn2["Return"].NavigateUrl = WebUtil.Param("return");
            this.hidReturnUrl.Value = WebUtil.Param("return");
            this.drpPeriod.Items.Clear();
            this.txtMbrID.Text = WebUtil.Param("mnum");
            this.txtMbrName.Text = WebUtil.Param("mname");
            using (ISession session = new Session())
            {
                IList<INVPeriod> periods = INVPeriod.ClosedPeriods(session);
                foreach (INVPeriod p in periods)
                    this.drpPeriod.Items.Add(new ListItem(p.PeriodCode, p.PeriodID.ToString()));
                this.drpPeriod.SelectedValue = WebUtil.Param("pd");
                this.QueryAndBindData(session, 1, this.magicPagerMain.PageSize, true);
            }
        }
        else this.frameDownload.Attributes["src"] = "about:blank;";
    }

    private void QueryAndBindData(ISession session, int pageIndex, int pageSize, bool fetchRecordCount)
    {
        int periodId = Cast.Int(this.drpPeriod.SelectedValue, 0);
        ObjectQuery query = session.CreateObjectQuery(@"
select m.MemberNumber as MemberNumber,m.Name as MemberName,ca.BeginAmt as BeginAmt,ca.EndAmt as EndAmt
from MbrCashAccountBalance ca
left join Member m on m.MemberID=ca.MemberID
order by m.MemberNumber")
            .Attach(typeof(MbrCashAccountBalance)).Attach(typeof(Member))
            .SetPage(pageIndex, pageSize)
            .Where(Exp.Eq("ca.PeriodID", periodId));
        if (!string.IsNullOrEmpty(this.txtMbrID.Text) && this.txtMbrID.Text.Trim().Length > 0)
            query.And(Exp.Eq("m.MemberNumber", this.txtMbrID.Text.Trim()));
        if (!string.IsNullOrEmpty(this.txtMbrName.Text) && this.txtMbrName.Text.Trim().Length > 0)
            query.And(Exp.Like("m.Name", "%" + this.txtMbrName.Text.Trim() + "%"));
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
        if (e.CommandName != "Download")
            return;

        using (ISession session = new Session())
        {
            int periodId = Cast.Int(this.drpPeriod.SelectedValue, 0);
            ObjectQuery query = session.CreateObjectQuery(@"
select m.MemberNumber as MemberNumber,m.Name as MemberName,ca.BeginAmt as BeginAmt,ca.EndAmt as EndAmt
from MbrCashAccountBalance ca
left join Member m on m.MemberID=ca.MemberID
order by m.MemberNumber")
                .Attach(typeof(MbrCashAccountBalance)).Attach(typeof(Member))
                .Where(Exp.Eq("ca.PeriodID", periodId));
            if (!string.IsNullOrEmpty(this.txtMbrID.Text) && this.txtMbrID.Text.Trim().Length > 0)
                query.And(Exp.Eq("m.MemberNumber", this.txtMbrID.Text.Trim()));
            if (!string.IsNullOrEmpty(this.txtMbrName.Text) && this.txtMbrName.Text.Trim().Length > 0)
                query.And(Exp.Like("m.Name", "%" + this.txtMbrName.Text.Trim() + "%"));
            DataSet ds = query.DataSet();

            string fileName = DownloadUtil.DownloadXls("Member_Account_Balance_" + DateTime.Now.ToString("yyMMdd") + ".xls", "RPT_MBR_ACC_",
                new List<DownloadFormat>()
                    {
                        new DownloadFormat(DataType.NumberText, "会员号", "MemberNumber"),
                        new DownloadFormat(DataType.Text, "会员姓名", "MemberName"),
                        new DownloadFormat(DataType.Number, "期初余额", "BeginAmt"),
                        new DownloadFormat(DataType.Number, "期末余额", "EndAmt")
                    }, ds);
            this.frameDownload.Attributes["src"] = fileName;
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
}