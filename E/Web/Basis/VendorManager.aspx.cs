using System;
using System.Collections;
using System.Collections.Generic;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using Magic.Framework.Utils;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Query;
using Magic.Web.UI;
using Magic.Basis;

public partial class system_Vendor : System.Web.UI.Page
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
            helper.SetValue(this.txtShortName);
            helper.SetValue(this.txtFullName);
            helper.SetValue(this.cklStatus);
            pageSize = Cast.Int(helper.Pop("ps"), this.magicPagerMain.PageSize);
            pageIndex = Cast.Int(helper.Pop("pi"), 1);
        }
        QueryAndBindData(session, pageIndex, pageSize, true);
    }

    string GetReturnUrl()
    {
        QueryHelper helper = new QueryHelper(this);
        helper.GetValue(this.txtShortName); //传参_方法1
        helper.GetValue(this.txtFullName); //传参_方法2
        helper.GetValue(this.cklStatus);
        helper.Push("ps", this.magicPagerMain.PageSize);
        helper.Push("pi", this.magicPagerMain.CurrentPageIndex);
        return helper.OutputReturnUrl();
    }

    void QueryAndBindData(ISession session, int pageIndex, int pageSize, bool fetchRecordCount)
    {
        string shortName = this.txtShortName.Text;
        string fullName = this.txtFullName.Text;
        IList<VendorStatus> status = new List<VendorStatus>();
        foreach (ListItem item in cklStatus.Items)
            if (item.Selected) status.Add(Cast.Enum<VendorStatus>(item.Value));
        //定义OQL
        ObjectQuery query = session.CreateObjectQuery(@"
select v.VendorID as VendorID,v.Status as Status,v.ShortName as ShortName,v.FullName as FullName,v.Address as Address,v.Contact as Contact,v.ZipCode as ZipCode,v.Phone as Phone,v.Fax as Fax,v.BankAccount as BankAccount,v.TaxID as TaxID,v.Tax as Tax 
    ,td.TaxText as TaxText
from Vendor v
left join TaxDef td on v.TaxID=td.TaxID
order by v.FullName")
            .Attach(typeof(Vendor)).Attach(typeof(TaxDef))
            .And(Exp.In("v.Status", status)).SetPage(pageIndex,pageSize);
        //追加多个条件
        if (!string.IsNullOrEmpty(shortName)) query.And(Exp.Like("v.ShortName", "%" + shortName + "%"));
        if (!string.IsNullOrEmpty(fullName)) query.And(Exp.Like("v.FullName", "%" + fullName + "%"));
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
                        if (chk != null && chk.Checked && Cast.Int(chk.Value) > 0)
                        {
                            Magic.Basis.Vendor vendor = Magic.Basis.Vendor.Retrieve(session, Cast.Int(chk.Value));
                            if (vendor != null)
                            {
                                //逻辑删除，仅将状态update成UserStatus.Deleted
                                vendor.Status = VendorStatus.Delete;
                                vendor.Update(session, "Status");
                                deleted = true;
                            }
                        }
                    }
                    session.Commit();
                    if (deleted)
                    {
                        RestoreLastQuery(session);
                        WebUtil.ShowMsg(this, "选择的供应商已经被删除", "操作成功");
                    }
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
