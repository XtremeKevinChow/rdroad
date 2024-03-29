using System;
using System.Data;
using System.Web.UI;
using System.Web.UI.WebControls;
using Magic.Framework.Utils;
using Magic.Framework.ORM;
using Magic.Framework.Data;

public partial class SaleDelivery_CSLogQuery : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            this.txtDateFrom.Text = DateTime.Now.AddDays(-1).ToString("yyyy-MM-dd");
            this.txtDateTo.Text = DateTime.Now.ToString("yyyy-MM-dd");

            using (ISession session = new Session())
            {
                DbSession dbsession = session.DbSession as DbSession;
                this.LoadType(dbsession);
                this.LoadUser(dbsession);

                this.QueryAndBindData(session, 1, this.magicPagerMain.PageSize, true);
            }
        }
    }
    private void LoadType(DbSession session)
    {
        this.drpMainType.Items.Clear();
        this.drpMainType.Items.Add(new ListItem("　", "0"));
        IDbCommand cmd = session.CreateSqlStringCommand(@"
Select cmpt_type_id As Main_Id,cmpt_type_name As Main_Text
From magic.complaint_type 
Where cmpt_type_id In(select distinct main_id from ext_cs_log_main_type)");
        DataSet ds = session.ExecuteDataSet(cmd);
        foreach (DataRow row in ds.Tables[0].Rows)
            this.drpMainType.Items.Add(new ListItem(Cast.String(row["Main_Text"]), Cast.String(row["Main_Id"])));
    }
    private void LoadSubType(DbSession session)
    {
        this.drpSubType.Items.Clear();
        this.drpSubType.Items.Add(new ListItem("　", "0"));
        int mainId = Cast.Int(this.drpMainType.SelectedValue);
        if (mainId <= 0) return;
        IDbCommand cmd = session.CreateSqlStringCommand(string.Format(@"
Select cmpt_type_id As Sub_Id,cmpt_type_name As Sub_Text
From magic.complaint_type 
Where cmpt_type_id In(select sub_id from ext_cs_log_main_type Where main_id={0})", mainId));
        DataSet ds = session.ExecuteDataSet(cmd);
        foreach (DataRow row in ds.Tables[0].Rows)
            this.drpSubType.Items.Add(new ListItem(Cast.String(row["Sub_Text"]), Cast.String(row["Sub_Id"])));
    }
    private void LoadUser(DbSession session)
    {
        this.drpUser.Items.Clear();
        this.drpUser.Items.Add(new ListItem("　", "0"));
        IDbCommand cmd = session.CreateSqlStringCommand(@"
Select Id,Name From magic.org_persons Where Id In(
       Select Distinct Id From magic.crm_userrole Where roleid In (6,53,3,5)
)");
        DataSet ds = session.ExecuteDataSet(cmd);
        foreach (DataRow row in ds.Tables[0].Rows)
            this.drpUser.Items.Add(new ListItem(Cast.String(row["Name"]), Cast.String(row["Id"])));
    }
    protected void MagicPager_PageChanged(object src, Magic.Web.UI.PageChangedEventArgs e)
    {
        this.magicPagerSub.PageSize = this.magicPagerMain.PageSize;
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
    private void QueryAndBindData(ISession session, int pageIndex, int pageSize, bool fetchRecordCount)
    {
        DbSession dbsession = session.DbSession as DbSession;
        IDbCommand cmd = dbsession.CreateSqlStringCommand("");
        System.Text.StringBuilder sql = new System.Text.StringBuilder();

        sql.Append(@"
select Case When c.type=1 Then '咨询' Else '投诉' End As Type
       ,c.cmpt_type_name As MainType,b.cmpt_type_name As SubType
       ,u.Name As CreateUser,a.mbr_id As MbrId,m.card_id As MbrNum,m.Name As MbrName,a.Create_Date
       ,a.cmpt_content As Content
from magic.mbr_complaint a
Inner Join magic.complaint_type b On a.cmpt_type_id=b.cmpt_type_id
Inner Join magic.complaint_type c On b.parent_id=c.cmpt_type_id
Inner Join magic.mbr_members m On m.Id=a.mbr_id
Left Join magic.org_persons u On u.Id=a.creator
Where b.cmpt_type_id in(Select sub_id From ext_cs_log_main_type)");
        int mainType = Cast.Int(this.drpMainType.SelectedValue);
        if (mainType > 0)
            sql.Append(" And c.cmpt_type_id=").Append(mainType);
        int subType = Cast.Int(this.drpSubType.SelectedValue);
        if (subType > 0)
            sql.Append(" And b.cmpt_type_id=").Append(subType);
        int userId = Cast.Int(this.drpUser.SelectedValue);
        if (userId > 0)
            sql.Append(" And a.creator=").Append(userId);
        if (!string.IsNullOrEmpty(this.txtMbrNum.Text) && this.txtMbrNum.Text.Trim().Length > 0)
        {
            sql.Append(" And m.card_id Like :mbrNum");
            dbsession.AddParameter(cmd, ":mbrNum", DbTypeInfo.AnsiString(10), "%" + this.txtMbrNum.Text.Trim() + "%");
        }
        if (!string.IsNullOrEmpty(this.txtMbrName.Text) && this.txtMbrName.Text.Trim().Length > 0)
        {
            sql.Append(" And And m.Name Like :mbrName");
            dbsession.AddParameter(cmd, ":mbrName", DbTypeInfo.AnsiString(40), "%" + this.txtMbrName.Text.Trim() + "%");
        }
        DateTime dateFrom = Cast.DateTime(this.txtDateFrom.Text), dateTo = Cast.DateTime(this.txtDateTo.Text);
        if (dateFrom > new DateTime(1900, 1, 1))
        {
            sql.Append(" And a.create_date>=:startDate");
            dbsession.AddParameter(cmd, ":startDate", DbTypeInfo.DateTime(), dateFrom);
        }
        if (dateTo > new DateTime(1900, 1, 1))
        {
            sql.Append(" And a.create_date<=:endDate");
            dbsession.AddParameter(cmd, ":endDate", DbTypeInfo.DateTime(), dateTo.AddDays(1));
        }

        if (fetchRecordCount)
        {
            cmd.CommandText = "select count(1) from(" + sql.ToString() + ")t";
            int count = Cast.Int(dbsession.ExecuteScalar(cmd));
            this.magicPagerMain.RecordCount = this.magicPagerSub.RecordCount = count;
        }

        sql.Append(" Order By a.create_date Desc) mf_t1) mf_t2 WHERE mf_r>=")
            .Append((pageIndex - 1) * pageSize + 1)
            .Append(" AND mf_r<=").Append(pageIndex * pageSize);
        cmd.CommandText = "SELECT * FROM(SELECT mf_t1.*,Rownum AS mf_r FROM(" + sql.ToString();
        this.repeatControl.DataSource = dbsession.ExecuteDataSet(cmd);
        this.repeatControl.DataBind();
        WebUtil.SetMagicPager(magicPagerMain, pageSize, pageIndex);
        WebUtil.SetMagicPager(magicPagerSub, pageSize, pageIndex);
    }
    protected void drpMainType_SelectedIndexChanged(object sender, EventArgs e)
    {
        using (ISession session = new Session())
        {
            DbSession dbsession = session.DbSession as DbSession;
            this.LoadSubType(dbsession);
        }
    }
}