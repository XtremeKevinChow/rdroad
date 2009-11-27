using System;
using System.Collections;
using System.Configuration;
using System.Data;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using Magic.Sys;
using Magic.Framework.ORM;
using Magic.Framework.Utils;

public partial class UserInfoView : System.Web.UI.Page
{
    private int UserId
    {
        get
        {
            int userId = WebUtil.ParamInt("id", 0);
            if(userId<=0)
                userId=Magic.Security.SecuritySession.CurrentUser == null ? 0 : Magic.Security.SecuritySession.CurrentUser.UserId;
            return userId;
        }
    }
    protected void Page_Load(object sender, EventArgs e)
    {
        Magic.Sys.User user = null;
        using (ISession session = new Session())
        {
            user = Magic.Sys.User.Retrieve(session, this.UserId);
        }
        if (user != null)
        {
            this.txtUserName.Value = user.UserName;
            this.txtFullName.Value = user.FullName;
            this.txtLoginTime.Value = RenderUtil.FormatDatetime(user.LastLogonTime);
            this.txtCreateTime.Value = RenderUtil.FormatDatetime(user.CreateTime);
        }
        else
        {
            WebUtil.ShowMsg(this, "请重新登陆");
        }
    }

    protected void txtSubmit_Click(object sender, EventArgs e)
    {
        using (ISession session = new Session())
        {
            Magic.Sys.User user = Magic.Sys.User.Retrieve(session, this.UserId);
            if (user == null)
            {
                WebUtil.ShowError(this, "请重新登陆");
                return;
            }
            if (this.txtOldPwd.Value != user.Password)
            {
                WebUtil.ShowError(this, "原密码不正确");
                return;
            }
            if (this.txtNewPwd.Value != this.txtNewPwd2.Value)
            {
                WebUtil.ShowError(this, "2次输入的密码不正确");
                return;
            }
            user.Password = this.txtNewPwd.Value;
            user.Update(session, "Password");
            WebUtil.ShowMsg(this, "密码修改成功");
        }
    }
}
