
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
using Magic.Framework.Data;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Query;
using Magic.Web.UI;
using Magic.Framework.Debug;
using Magic.Framework.Utils;
using Magic.Security;
using Magic.Sys;

public partial class UserGroupEditPage : System.Web.UI.Page
{  
    Mode _actionMode = Mode.Undefined;
	ISession _session = null;
	log4net.ILog logger = log4net.LogManager.GetLogger(typeof(UserGroup));
    protected void Page_Load(object sender, EventArgs e)
    {        
        if (!IsPostBack)
        {
            //Init Data
            EnumUtil.BindEnumData2ListControl<UserGroupType>(this.rblGroupType, false);
            using (_session = new Session())
            {                
                _actionMode = WebUtil.GetActionMode(this);
                if (_actionMode == Mode.Edit)
                {
                    LoadData();
                }
            }
        }
		 if (Request["return"] != null)
        {
            this.toolbarbottom["Return"].NavigateUrl = Request["return"];
        }
    }
   
	#region LoadData
	private void LoadData()
	{
		int groupId = -1;
        if (int.TryParse(Request["id"], out groupId))
        {
            UserGroup userGroup = null;
            userGroup = UserGroup.Retrieve(_session, groupId);

            if (userGroup != null)
            {
                txtName.Text = userGroup.Name;
                txtDescription.Text = userGroup.Description;
                rblGroupType.SelectedValue = userGroup.GroupType.ToString();
                txtGroupLevel.Text = userGroup.GroupLevel.ToString();
            }

            this.txtGroupId.Value = Request["id"];
        }
	}
	#endregion
   
    #region Buttons,Command Event Handler
    
	//MagicToolbar's MagicItem OnClick Event Handler, it's for both the top one and the bottom one.
    protected void MagicItemCommand(object sender, MagicItemEventArgs e)
    {
        if (e.CommandName == "Save")
        {
           SaveData();           
        }
    }
    #endregion
    
    #region Private Methods
	//Save Data
   	private void SaveData()
    {
        UserGroup userGroup = new  UserGroup();
		bool flag = true;   
        try
        {
            userGroup.ParentId = UserGroup.Root.GroupId;
			userGroup.Name = txtName.Text.Trim();
			userGroup.Description = txtDescription.Text.Trim();
			userGroup.GroupType = (UserGroupType)Enum.Parse(typeof(UserGroupType),rblGroupType.SelectedValue);
            userGroup.GroupLevel = (short)(UserGroup.Root.GroupLevel + 1);
            userGroup.ModifyBy = SecuritySession.CurrentUser.UserId;
            userGroup.ModifyTime = DateTime.Now;
            using (_session = new Session())
            {			
                if (IsAddNew())
                {
                    userGroup.CreateTime = userGroup.ModifyTime;
                    userGroup.CreateBy = SecuritySession.CurrentUser.UserId;
                    flag = userGroup.Create(_session);
                }
                else
                {			
                   userGroup.GroupId = int.Parse(this.txtGroupId.Value);
                   flag = userGroup.Update(_session,  "ParentId",  "Name",  "Description",  "GroupType",  "GroupLevel","ModifyBy","ModifyTime");
                }
            }
            this.txtGroupId.Value = userGroup.GroupId.ToString();

            if (flag)
                WebUtil.ShowMsg(this, "操作成功", "提示");
            else
                WebUtil.ShowMsg(this, "操作失败", "提示");  
        }
        catch(UnauthorizedException ex)
        {
            WebUtil.ShowMsg(this,ex.Message,"警告");
        }
		catch(ApplicationException ex)
		{
			WebUtil.ShowMsg(this,ex.Message,"提示");
		}
		catch(Exception ex)
		{
		    logger.Info("保存UserGroup", ex);
            WebUtil.ShowMsg(this, "发生未处理的异常,请刷新页面重新操作，或者联系系统管理员");
		}
    }
	
	 private bool IsAddNew()
    {         
         if( txtGroupId.Value.Trim().Length==0)
                return true;
        return false;
    }
	#endregion
}
