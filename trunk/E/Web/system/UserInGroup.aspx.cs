using System;
using System.Collections;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using Magic.Sys;
using Magic.Security;
using Magic.Framework.ORM;
using Magic.Framework.Data;
using Magic.Framework.ORM.Query;

public partial class system_UserInGroup : System.Web.UI.Page
{
    log4net.ILog logger = log4net.LogManager.GetLogger(typeof(UserGroup));

    private int _groupId = -1;
    private Session _session = null;
    protected void Page_Load(object sender, EventArgs e)
    {
        if (Request["return"] != null)
        {
            this.toolbarbottom["Return"].NavigateUrl = Request["return"];
        }
        if (!IsPostBack)
        {
            if (int.TryParse(Request["gid"], out _groupId))
            {
                this.txtGroupId.Value = _groupId.ToString();
                using (_session = new Session())
                {
                    LoadUserInGroup();
                }
            }
        }
        else
        {
            int.TryParse(this.txtGroupId.Value, out _groupId);
        }
    }

    void LoadUserInGroup()
    {
        UserGroup group = Magic.Sys.UserGroup.Retrieve(_session, _groupId);
        if (group != null)
        {
            this.txtGroupName.Text = group.Name;

            AuthorizationRepository repository = new AuthorizationRepository(_session);
            IList<UserBase> users = repository.FindUsersInGroup(group, "");
            this.rptUserInGroup.DataSource = users;
            this.rptUserInGroup.DataBind();

            DataTable dt = repository.GetAssigningUserForGroup(group.GroupId, "");
            this.rptAllGroup.DataSource = dt;
            this.rptAllGroup.DataBind();
        }

    }
    protected void btnAddToGroup_Click(object sender, EventArgs e)
    {
        IList<int> userIds = new List<int>();
        foreach (RepeaterItem item in this.rptAllGroup.Items)
        {
            HtmlInputCheckBox chk = item.FindControl("checkbox") as HtmlInputCheckBox;
            if (chk != null)
            {
                if (chk.Checked)
                {
                    userIds.Add(int.Parse(chk.Value));
                }
            }
        }

        if (userIds.Count > 0)
        {
            int[] arrayIds = new int[userIds.Count];
            userIds.CopyTo(arrayIds, 0);
            using (_session = new Session())
            {
                UserGroup group = Magic.Sys.UserGroup.Retrieve(_session, this._groupId);
                try
                {
                    AuthorizationRepository repository = new AuthorizationRepository(_session);

                    repository.AddUsersToGroups(arrayIds,new UserGroup[]{group});

                    LoadUserInGroup();
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
                    logger.Info("向用户组添加用户", ex);
                    WebUtil.ShowError(this, ex);
                }
            }
        }

    }

    protected void btnRemoveFromGroup_Click(object sender, EventArgs e)
    {
        IList<int> userIds = new List<int>();
        foreach (RepeaterItem item in this.rptUserInGroup.Items)
        {
            HtmlInputCheckBox chk = item.FindControl("checkbox") as HtmlInputCheckBox;
            if (chk != null)
            {
                if (chk.Checked)
                {
                    userIds.Add(int.Parse(chk.Value));
                }
            }
        }
        if (userIds.Count > 0)
        {
            int[] arrayIds = new int[userIds.Count];
            userIds.CopyTo(arrayIds, 0);
            using (_session = new Session())
            {         

                try
                {
                    AuthorizationRepository repository = new AuthorizationRepository(_session);

                    repository.RemoveUsersFromGroups(arrayIds, new int[] { _groupId });

                    LoadUserInGroup();
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
                    logger.Info("从用户组中移出用户", ex);
                    WebUtil.ShowError(this, ex);
                }
            }
        }

    }
}
