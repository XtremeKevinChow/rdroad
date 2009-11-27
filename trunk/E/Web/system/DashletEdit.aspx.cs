
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

public partial class DashletEditPage : System.Web.UI.Page
{  
    Mode _actionMode = Mode.Undefined;
	ISession _session = null;
	log4net.ILog logger = log4net.LogManager.GetLogger(typeof(Dashlet));
    protected void Page_Load(object sender, EventArgs e)
    {        
        if (!IsPostBack)
        {
            //Init Data
            EnumUtil.BindDictionaryItems2ListControl(this.ddlCategory, "Dashlet_Category");
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
		hidDashletId.Value = WebUtil.Param("dashletId");
		int dashletId = Cast.Int(WebUtil.Param("dashletId"));
       
        Dashlet dashlet = null;           
        dashlet = Dashlet.Retrieve(_session, dashletId  );
      
        if (dashlet != null)
        {
			ddlCategory.SelectedValue = dashlet.Category;
			txtTitle.Text = dashlet.Title;
			txtLink.Text = dashlet.Link;
			txtIcon.Text = dashlet.Icon;
			txtDescription.Text = dashlet.Description;
			txtInstanceMethod.Text = dashlet.InstanceMethod;
			txtInstanceParameter.Text = dashlet.InstanceParameter;
			
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
        Dashlet dashlet = new  Dashlet();
		bool flag = true;   
        try
        {
				dashlet.Category = ddlCategory.SelectedValue;
				dashlet.Title = txtTitle.Text.Trim();
				dashlet.Link = txtLink.Text.Trim();
                dashlet.Icon = txtIcon.Attributes["key"];
				dashlet.Description = txtDescription.Text.Trim();
				dashlet.InstanceMethod = txtInstanceMethod.Text.Trim();
				dashlet.InstanceParameter = txtInstanceParameter.Text.Trim();
            using (_session = new Session())
            {			
                if (IsAddNew())
                {
                    flag = dashlet.Create(_session);
                }
                else
                {			
                   dashlet.DashletId = int.Parse(this.hidDashletId.Value);
                   flag = dashlet.Update(_session,  "Category",  "Title",  "Link",  "Icon",  "Description",  "InstanceMethod",  "InstanceParameter");
                }
            }
            this.hidDashletId.Value = dashlet.DashletId.ToString();
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
		    logger.Info("保存Dashlet", ex);
            WebUtil.ShowMsg(this, "发生未处理的异常,请刷新页面重新操作，或者联系系统管理员");
		}
    }
	
	 private bool IsAddNew()
    {         
         if( hidDashletId.Value.Trim().Length==0)
                return true;
        return false;
    }
	#endregion
}
