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
using Magic.Sys;

public partial class UserManagePage : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            //所有使用ISession的地方，必须象下面使用using语句，确保ISession释放；
            //如果不使用using语句，则必须手工确保任何情况下，最后都调用了ISession.Dispose()方法手工释放
            using (ISession session = new Session())
            {
                RestoreLastQuery(session);
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

    protected void MagicItemCommand(object sender, MagicItemEventArgs e)
    {
        if (e.CommandName == "Delete")
        {
            //MagicToolBar的事件
            bool deleted = false;
            using (ISession session = new Session())
            {
                session.BeginTransaction();
                try
                {
                    foreach (RepeaterItem item in this.rptUser.Items)
                    {
                        HtmlInputCheckBox chk = item.FindControl("checkbox") as HtmlInputCheckBox;
                        if (chk != null && chk.Checked && Cast.Int(chk.Value) > 0)
                        {
                            Magic.Sys.User user = Magic.Sys.User.Retrieve(session, Cast.Int(chk.Value));
                            if (user != null)
                            {
                                //逻辑删除，仅将状态update成UserStatus.Deleted
                                user.Status = UserStatus.Deleted;
                                user.ModifyBy = Magic.Security.SecuritySession.CurrentUser.UserId;
                                user.ModifyTime = DateTime.Now;
                                //Update方法有2个版本，
                                //1. 只传ISession参数，将更新实体的所有属性
                                //2. 传一个属性名称的字符串数组，只更新指定的字段
                                user.Update(session, "Status", "ModifyBy", "ModifyTime");
                                deleted = true;
                            }
                        }
                    }
                    session.Commit();
                    if (deleted)
                        this.QueryAndBindData(session, this.magicPagerMain.CurrentPageIndex, this.magicPagerMain.PageSize, true);
                    WebUtil.ShowMsg(this, "选择的用户已经被删除", "操作成功");
                }
                catch (Exception err)
                {
                    session.Rollback();
                    WebUtil.ShowError(this, err);
                }
            }
        }
    }

    private void RestoreLastQuery(ISession session)
    {
        int pageSize = 20;
        int pageIndex = 1;
        //如果是从其它页面返回该页面，恢复条件值
        QueryHelper helper = new QueryHelper(this);
        if (helper.HasQueryParameter())
        {
            //helper.SetValue(this.txtUserName); //传参_方法1
            this.txtUserName.Text = helper.Pop("un"); //传参_方法2
            helper.SetValue(this.txtFullName);
            helper.SetValue(this.cklStatus);
            pageSize = Cast.Int(helper.Pop("ps"), this.magicPagerMain.PageSize);
            pageIndex = Cast.Int(helper.Pop("pi"), 1);
        }
        QueryAndBindData(session, pageIndex, pageSize, true);
    }

    private string GetReturnUrl()
    {
        //将当前查询条件拚到url中，带到新的页面，返回的时候再还原这些条件值
        //helper的两种用法
        //1. helper.GetValue(控件名); 已经封装好的控件可以使用这种方法，见GetReturnUrl()和RestoreLastQuery()中方法1
        //2. helper.Push(参数名, 参数值); 没有封装的控件，或一些特殊的东西，可以使用这种方法，见GetReturnUrl()和RestoreLastQuery()中方法2
        QueryHelper helper = new QueryHelper(this);
        //helper.GetValue(this.txtUserName); 传参_方法1
        helper.Push("un", this.txtUserName.Text.Trim()); //传参_方法2
        helper.GetValue(this.txtFullName);
        helper.GetValue(this.cklStatus);
        helper.Push("ps", this.magicPagerMain.PageSize);
        helper.Push("pi", this.magicPagerMain.CurrentPageIndex);
        return helper.OutputReturnUrl();
    }

    private void QueryAndBindData(ISession session, int pageIndex, int pageSize, bool fetchRecordCount)
    {
        string userName = txtUserName.Text.Trim();
        string fullName = txtFullName.Text.Trim();
        IList<UserStatus> status = new List<UserStatus>();
        foreach (ListItem item in cklStatus.Items)
            if (item.Selected) status.Add(Cast.Enum<UserStatus>(item.Value));
        //定义OQL语句，语法为基本的SQL语法，select出来的字段都使用as定义一个别名，在.aspx文件中使用别名绑定；否则select出来的字段为数据库字段名
        ObjectQuery query = session.CreateObjectQuery(@"
select u.UserId as UserId,o.OrgName as OrgName,u.UserType as UserType
    ,u.UserName as UserName,u.FullName as FullName,u.Ext as Ext,u.LastLogonTime as LastLogonTime,u.Status as UserStatus
    ,u.ModifyTime as ModifyTime,u2.FullName as ModifyBy
from User u
inner join Org o on u.OrgId=o.OrgId
left join User u2 on u.ModifyBy=u2.UserId
where u.Status<>?
order by u.FullName
")
            .Attach(typeof(Magic.Sys.User)).Attach(typeof(Org)) //OQL里面用到的所有对象都要调用Attach方法关联
            //为OQL语句中的变量赋值，按位置索引的变量OQL中使用?表示，设置值时用0开始的索引访问；
            //命名的变量OQL中使用@或者:前缀，设置值时使用变量名称(包含@或者:符号)
            .SetValue(0, UserStatus.Deleted, "u.Status") //注意：有关联多个对象时，最好定义别名，OQL对属性相关的操作都使用"别名.属性名"访问
            .And(Exp.In("u.Status", status)) //为OQL追加条件
            .SetPage(pageIndex, pageSize); //设置分页属性
        //为OQL追加条件
        //注意：有关联多个对象时，最好定义别名，OQL对属性相关的操作都使用"别名.属性名"访问
        if (!string.IsNullOrEmpty(userName)) query.And(Exp.Like("u.UserName", "%" + userName + "%"));
        if (!string.IsNullOrEmpty(fullName)) query.And(Exp.Like("u.FullName", "%" + fullName + "%"));

        this.rptUser.DataSource = query.DataSet();
        this.rptUser.DataBind();
        if (fetchRecordCount)
            this.magicPagerMain.RecordCount = this.magicPagerSub.RecordCount = query.Count();
        WebUtil.SetMagicPager(magicPagerMain, pageSize, pageIndex);
        WebUtil.SetMagicPager(magicPagerSub, pageSize, pageIndex);
        //拚装出返回该页面时的地址，设置到界面隐藏控件。界面用JavaScript进入编辑、新增、用户组等页面时，会把返回地址传过去
        this.hidReturnUrl.Value = GetReturnUrl();
    }
}