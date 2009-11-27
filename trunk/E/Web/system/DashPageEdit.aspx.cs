
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
using Magic.Framework.OQL;
using Magic.Web.UI;
using Magic.Framework.Debug;
using Magic.Framework.Utils;
using Magic.Security;
using Magic.Sys;

public partial class DashPageEditPage : System.Web.UI.Page
{  
    Mode _actionMode = Mode.Undefined;
	ISession _session = null;
	log4net.ILog logger = log4net.LogManager.GetLogger(typeof(DashPage));
    protected void Page_Load(object sender, EventArgs e)
    {        
        if (!IsPostBack)
        {
            //Init Data
            EnumUtil.BindEnumData2ListControl<DashPageLayout>(this.rdlLayout, false);
            EnumUtil.BindEnumData2ListControl<DashPageType>(this.rdlType, false);
            EnumUtil.BindEnumData2ListControl<DashStatus>(this.rdlStatus, false);
            //Init Controls Data before do query
            
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
			this.toolbarup["Return"].NavigateUrl = Request["return"]; 
            this.toolbarbottom["Return"].NavigateUrl = Request["return"];
        }
    }
   
	#region LoadData
	private void LoadData()
	{
		hidDashpageId.Value = WebUtil.Param("dashpageId");
		int dashpageId = Cast.Int(WebUtil.Param("dashpageId"));
       
        DashPage dashPage = null;           
        dashPage = DashPage.Retrieve(_session, dashpageId  );
      
        if (dashPage != null)
        {
			txtTitle.Text = dashPage.Title;
			rdlLayout.SelectedValue = dashPage.Layout.ToString();
			rdlType.SelectedValue = dashPage.Type.ToString();
			txtHelpLink.Text = dashPage.HelpLink;
			txtDescription.Text = dashPage.Description;
			rdlStatus.SelectedValue = dashPage.Status.ToString();
			txtCreateTime.Text = RenderUtil.FormatDatetime(dashPage.CreateTime);
            txtCreateBy.Text = Magic.Sys.User.GetUserName(dashPage.CreateBy);
			
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
        DashPage dashPage = new  DashPage();
		bool flag = true;   
        try
        {
				dashPage.Title = txtTitle.Text.Trim();
				dashPage.Layout = Cast.Enum<DashPageLayout>(rdlLayout.SelectedValue);
				dashPage.Type = Cast.Enum<DashPageType>(rdlType.SelectedValue);
				dashPage.HelpLink = txtHelpLink.Text.Trim();
				dashPage.Description = txtDescription.Text.Trim();
				dashPage.Status = Cast.Enum<DashStatus>(rdlStatus.SelectedValue);
                dashPage.CreateTime = DateTime.Now;
                dashPage.CreateBy = SecuritySession.CurrentUser.UserId;
            using (_session = new Session())
            {			
                if (IsAddNew())
                {
                    flag = dashPage.Create(_session);
                }
                else
                {			
                   dashPage.DashpageId = int.Parse(this.hidDashpageId.Value);
                   flag = dashPage.Update(_session,  "Title",  "Layout",  "Type",  "HelpLink",  "Description",  "Status",  "CreateTime",  "CreateBy");
                }
            }
            this.hidDashpageId.Value = dashPage.DashpageId.ToString();
           if(flag)
                WebUtil.ShowMsg(this,"操作成功","提示");
           else
                WebUtil.ShowMsg(this,"操作失败","提示");  
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
		    logger.Info("保存DashPage", ex);
            WebUtil.ShowMsg(this, "发生未处理的异常,请刷新页面重新操作，或者联系系统管理员");
		}
    }
	
	 private bool IsAddNew()
    {         
         if( hidDashpageId.Value.Trim().Length==0)
                return true;
        return false;
    }
	#endregion
}
