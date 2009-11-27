using System;
using System.Web.UI.WebControls;
using Magic.Framework.Utils;
using Magic.Framework.ORM;
using Magic.Web.UI;
using Magic.Security;
using Magic.Sys;

public partial class system_UserEdit : System.Web.UI.Page
{
    Mode _actionMode = Mode.Undefined;
    ISession _session = null;
    log4net.ILog logger = log4net.LogManager.GetLogger(typeof(User));

    protected void Page_Load(object sender, EventArgs e)
    {
        //返回用户列表页面的URL（包含了用户列表页面的查询条件）
        if (Request["return"] != null) this.toolbarbottom["Return"].NavigateUrl = Request["return"];
        if (!IsPostBack)
        {
            using (_session = new Session())
            {
                //有些情况下客户的组织结构没有什么要求，不需要选择组织结构，因此使用默认值
                if (Org.UseDefaultOrg(_session)) this.trOrg.Visible = false;

                InitializeDropDownList();
                _actionMode = WebUtil.GetActionMode(this);
                if (_actionMode == Mode.Edit) RetrieveUserData(_session); //如果时编辑用户，则加载显示用户资料
            }

            //不允许编辑帐号，但新增的时候需要输入
            if (this.IsAddNew())
            {
                this.txtUserName.ReadOnly = false;
                this.txtUserName.CssClass = "input";
            }
            else
            {
                this.txtUserName.ReadOnly = true;
                this.txtUserName.CssClass = "input readonly";
            }
        }
    }

    private void InitializeDropDownList()
    {
        this.ddlUserType.Items.Clear();
        this.ddlUserType.Items.Add(new ListItem("内部用户", OrgType.Own.ToString()));

        this.rblStatus.Items.Clear();
        ListItem item = new ListItem();
        item.Value = UserStatus.Enabled.ToString();
        item.Text = Magic.Sys.User.UserStatusText(UserStatus.Enabled);
        if (IsAddNew()) item.Selected = true;
        this.rblStatus.Items.Add(item);
        item = new ListItem();
        item.Value = UserStatus.Disabled.ToString();
        item.Text = Magic.Sys.User.UserStatusText(UserStatus.Disabled);
        this.rblStatus.Items.Add(item);
    }

    private void RetrieveUserData(ISession session)
    {
        int userId = WebUtil.ParamInt("id", -1);
        if (userId > 0)
        {
            User user = Magic.Sys.User.Retrieve(session, userId);
            if (user != null)
            {
                this.txtUserId.Value = user.UserId.ToString();
                this.txtPassword.Text = user.Password;
                this.txtUserName.Text = user.UserName;
                this.txtFullName.Text = user.FullName;
                this.txtEmail.Text = user.Email;
                this.ddlUserType.SelectedValue = user.UserType.ToString();
                this.rblStatus.SelectedValue = user.Status.ToString();
                this.txtExt.Text = user.Ext;
                this.txtLastLogonTime.Text = RenderUtil.FormatDatetime(user.LastLogonTime);
                this.txtOrgId.Text = user.OrgId.ToString();
                Org org = Org.Retrieve(session, user.OrgId); //加载组织的描述
                if (org != null) txtOrgDesc.Text = org.OrgName;
            }
        }
    }

    protected void MagicItemCommand(object sender, MagicItemEventArgs e)
    {
        if (e.CommandName == "Save")
        {
            SaveUser4Page();
        }
    }

    private void SaveUser4Page()
    {
        using (Session session = new Session())
        {
            try
            {
                User user = new User();
                user.UserName = this.txtUserName.Text.Trim();
                user.Password = this.txtPassword.Text;
                user.Email = txtEmail.Text.Trim();
                user.Ext = txtExt.Text.Trim();
                user.FullName = txtFullName.Text.Trim();
                if(Org.UseDefaultOrg(session))
                    user.OrgId = Org.DefaultOrg(session);
                else
                    user.OrgId = Cast.Int(this.txtOrgId.Text, -1);
                user.Status = Cast.Enum<UserStatus>(this.rblStatus.SelectedValue);
                user.UserType = Cast.Enum<OrgType>(this.ddlUserType.SelectedValue);
                user.ModifyBy = SecuritySession.CurrentUser.UserId;
                user.ModifyTime = DateTime.Now;

                if (IsAddNew())
                {
                    //检查帐号是否重复
                    int existsUserCount = session.CreateEntityQuery<Magic.Sys.User>()
                        .Where(Magic.Framework.ORM.Query.Exp.Eq("UserName", user.UserName))
                        .Count();
                    if (existsUserCount > 0)
                    {
                        WebUtil.ShowMsg(this, string.Format("用户帐号{0}已经存在", user.UserName), "错误");
                        return; //这里的return语句，using会保证session释放
                    }
                    //UserId是自增长主键，新增时不要设置它的值，新增完毕后框架会自动设置好这个值
                    user.CreateBy = SecuritySession.CurrentUser.UserId;
                    user.CreateTime = DateTime.Now;
                    user.Create(session);
                }
                else
                {
                    user.UserId = Cast.Int(this.txtUserId.Value, -1); //UserId是主键，更新时需要设置
                    user.Update(session, "Email", "Ext", "FullName", "OrgId", "Password", "Status", "UserType", "ModifyBy", "ModifyTime");
                }
                this.txtUserId.Value = user.UserId.ToString(); //如果是新增用户，新增完毕后将用户ID设置到以页面隐藏控件（新增后UserId已经被赋值了）
                WebUtil.ShowMsg(this, "用户保存成功", "操作成功");
            }
            catch (Exception ex)
            {
                logger.Info("保存User", ex);
                WebUtil.ShowMsg(this, "发生未处理的异常,请刷新页面重新操作，或者联系系统管理员");
            }
        }
    }

    private bool IsAddNew()
    {
        return Cast.Int(this.txtUserId.Value.Trim(), -1) <= 0;
    }
}