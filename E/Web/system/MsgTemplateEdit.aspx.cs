
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

public partial class MsgTemplateEditPage : System.Web.UI.Page
{  
    Mode _actionMode = Mode.Undefined;
	ISession _session = null;
	log4net.ILog logger = log4net.LogManager.GetLogger(typeof(MsgTemplate));
    protected void Page_Load(object sender, EventArgs e)
    {        
        if (!IsPostBack)
        {
            //Init Data
            EnumUtil.BindEnumData2ListControl<MessageAccessibility>(rdlAccessibility, false);
            EnumUtil.BindEntity2ListControl<MessageType>(this.ddlMsgType, "TypeName", "MsgTypeId");
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
		txtTmplCode.Text = WebUtil.Param("tmplCode");
        hidTmplCode.Value = WebUtil.Param("tmplCode");

		string tmplCode = Cast.String(WebUtil.Param("tmplCode"));
       
        MsgTemplate msgTemplate = null;           
        msgTemplate = MsgTemplate.Retrieve(_session, tmplCode  );
      
        if (msgTemplate != null)
        {

			txtName.Text = msgTemplate.Name;
			ddlMsgType.SelectedValue = msgTemplate.MsgTypeId.ToString();
			rdlAccessibility.SelectedValue = msgTemplate.Accessibility.ToString();
			txtExpires.Text = msgTemplate.Expires.ToString();
			txtSource.Text = msgTemplate.Source;
			txtViewEntry.Text = msgTemplate.ViewEntry;
			txtTitleFormat.Value = msgTemplate.TitleFormat;
			txtContentFormat.Value = msgTemplate.ContentFormat;            
			txtNavId.Value = msgTemplate.NavId.ToString();
            //Navigator nav = Navigator.Retrieve(_session, msgTemplate.NavId);
            //if (nav != null)
            //    txtNavName.Text = nav.Name;

			txtResponseEntry.Text = msgTemplate.ResponseEntry;
			
        }
        txtTmplCode.ReadOnly = true;
        
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
        MsgTemplate msgTemplate = new  MsgTemplate();
		bool flag = true;   
        try
        {
				msgTemplate.Name = Cast.String(txtName.Text.Trim());
				msgTemplate.MsgTypeId = Cast.Int(ddlMsgType.SelectedValue.Trim());
				msgTemplate.Accessibility = Cast.Enum<MessageAccessibility>(rdlAccessibility.SelectedValue);
				msgTemplate.Expires = Cast.Int(txtExpires.Text.Trim());
				msgTemplate.Source = Cast.String(txtSource.Text.Trim());
				msgTemplate.ViewEntry = Cast.String(txtViewEntry.Text.Trim());
				msgTemplate.TitleFormat = Cast.String(txtTitleFormat.Value.Trim());
				msgTemplate.ContentFormat = Cast.String(txtContentFormat.Value.Trim());
				msgTemplate.NavId = Cast.Int(txtNavId.Value.Trim());
				msgTemplate.ResponseEntry = Cast.String(txtResponseEntry.Text.Trim());
                msgTemplate.ModifyBy = SecuritySession.CurrentUser.UserId;
                msgTemplate.ModifyTime = DateTime.Now;
                msgTemplate.TmplCode = this.txtTmplCode.Text.Trim();
            using (_session = new Session())
            {			
                if (IsAddNew())
                {
                    msgTemplate.CreateBy = msgTemplate.ModifyBy;
                    msgTemplate.CreateTime = msgTemplate.ModifyTime;
                    flag = msgTemplate.Create(_session);
                }
                else
                {			
                   
                   flag = msgTemplate.Update(_session,  "Name",  "MsgType",  "Accessibility",  "Expires",  "Source",  "ViewEntry",  "TitleFormat",  "ContentFormat",  "NavId",  "ResponseEntry");
                }
            }
            this.hidTmplCode.Value = msgTemplate.TmplCode.Trim();
            this.txtTmplCode.Text = msgTemplate.TmplCode.Trim();
            this.txtTmplCode.ReadOnly = true;
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
		    logger.Info("保存消息模板", ex);
            WebUtil.ShowMsg(this, "发生未处理的异常,请刷新页面重新操作，或者联系系统管理员");
		}
    }
	
	 private bool IsAddNew()
    {         
         if( hidTmplCode.Value .Trim().Length==0)
                return true;
        return false;
    }
	#endregion
}
