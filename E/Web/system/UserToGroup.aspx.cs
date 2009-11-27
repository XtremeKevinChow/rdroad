using System;
using System.Collections;
using System.Collections.Generic;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using Magic.Sys;
using Magic.Security;
using Magic.Framework.ORM;

public partial class system_UserToGroup : System.Web.UI.Page
{
    log4net.ILog logger = log4net.LogManager.GetLogger(typeof(UserGroup));

    private int _userId = -1;
    private Session _session = null;
    protected void Page_Load(object sender, EventArgs e)
    {
        //设置返回页面的URL地址
        if (Request["return"] != null) this.toolbarbottom["Return"].NavigateUrl = Request["return"];
        this._userId = WebUtil.ParamInt("uid", -1);
        this.txtUserId.Value = this._userId.ToString();
        if (!IsPostBack)
        {
            if (this._userId > 0)
                using (_session = new Session())
                {
                    LoadUserToGroup();
                }
        }
    }

    void LoadUserToGroup()
    {
        User user = Magic.Sys.User.Retrieve(_session, _userId);
        if (user != null)
        {
            this.lblUserName.Text = user.FullName;

            AuthorizationRepository repository = new AuthorizationRepository(_session);
            IList<IUserGroup> usr2grps = repository.GetGroupsForUser(user);
            this.rptUserToGroup.DataSource = usr2grps;
            this.rptUserToGroup.DataBind();

            if (usr2grps == null || usr2grps.Count <= 0)
                this.rptAllGroup.DataSource = repository.GetAllGroups();
            else
                this.rptAllGroup.DataSource = repository.GetAssigningGroupsForUser(user);
            this.rptAllGroup.DataBind();
        }

    }
    protected void btnAddToUser_Click(object sender, EventArgs e)
    {
        IList<int> groupIds = new List<int>();
        foreach (RepeaterItem item in this.rptAllGroup.Items)
        {
            HtmlInputCheckBox chk = item.FindControl("checkbox") as HtmlInputCheckBox;
            if (chk != null && chk.Checked)
                groupIds.Add(int.Parse(chk.Value));
        }
        if (groupIds.Count > 0)
        {
            int[] arrayIds = new int[groupIds.Count];
            groupIds.CopyTo(arrayIds, 0);
            using (_session = new Session())
            {
                UserBase user = Magic.Sys.User.Retrieve(_session, this._userId);
                try
                {
                    AuthorizationRepository repository = new AuthorizationRepository(_session);
                    repository.AddUsersToGroups(new UserBase[] { user }, arrayIds);
                    LoadUserToGroup();
                }
                catch (UnauthorizedException ex)
                {
                    WebUtil.ShowMsg(this, ex.Message, "警告");
                }
                catch (ApplicationException ex)
                {
                    WebUtil.ShowMsg(this, ex.Message, "提示");
                }
                catch (Exception ex)
                {
                    logger.Info("AddUsersToGroups", ex);
                    WebUtil.ShowError(this, ex);
                }
            }
        }
    }

    protected void btnRemoveFromUser_Click(object sender, EventArgs e)
    {
        IList<int> groupIds = new List<int>();
        foreach (RepeaterItem item in this.rptUserToGroup.Items)
        {
            HtmlInputCheckBox chk = item.FindControl("checkbox") as HtmlInputCheckBox;
            if (chk != null && chk.Checked)
                groupIds.Add(Magic.Framework.Utils.Cast.Int(chk.Value, 0));
        }
        if (groupIds.Count > 0)
        {
            int[] arrayIds = new int[groupIds.Count];
            groupIds.CopyTo(arrayIds, 0);
            using (_session = new Session())
            {
                UserBase user = Magic.Sys.User.Retrieve(_session, this._userId);

                try
                {
                    AuthorizationRepository repository = new AuthorizationRepository(_session);
                    repository.RemoveUsersFromGroups(new UserBase[] { user }, arrayIds);
                    LoadUserToGroup();
                }
                catch (UnauthorizedException ex)
                {
                    WebUtil.ShowMsg(this, ex.Message, "警告");
                }
                catch (ApplicationException ex)
                {
                    WebUtil.ShowMsg(this, ex.Message, "提示");
                }
                catch (Exception ex)
                {
                    logger.Info("AddUsersToGroups", ex);
                    WebUtil.ShowError(this, ex);
                }
            }
        }
    }
}
