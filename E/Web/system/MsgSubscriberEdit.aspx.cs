
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

public partial class MsgSubscriberEditPage : System.Web.UI.Page
{  
    Mode _actionMode = Mode.Undefined;
	ISession _session = null;
	log4net.ILog logger = log4net.LogManager.GetLogger(typeof(MsgSubscriber));
    protected void Page_Load(object sender, EventArgs e)
    {        
        if (!IsPostBack)
        {
            //Init Data
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
		txtSubscriberId.Value = WebUtil.Param("subscriberId");
		int subscriberId = Cast.Int(WebUtil.Param("subscriberId"));
       
        MsgSubscriber msgSubscriber = null;           
        msgSubscriber = MsgSubscriber.Retrieve(_session, subscriberId  );
      
        if (msgSubscriber != null)
        {
			txtTmplCode.Text = msgSubscriber.TmplCode;
			txtUserId.Text = msgSubscriber.UserId.ToString();
			txtGroupId.Text = msgSubscriber.GroupId.ToString();
			txtIsGroup.Text = msgSubscriber.IsGroup.ToString();
			txtSubscribeTime.Text = RenderUtil.FormatDatetime(msgSubscriber.SubscribeTime);
			
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
        MsgSubscriber msgSubscriber = new  MsgSubscriber();
		bool flag = true;   
        try
        {
				msgSubscriber.TmplCode = Cast.String(txtTmplCode.Text.Trim());
				msgSubscriber.UserId = Cast.Int(txtUserId.Text.Trim());
				msgSubscriber.GroupId = Cast.Int(txtGroupId.Text.Trim());
				msgSubscriber.IsGroup = Cast.Bool(txtIsGroup.Text.Trim());
				msgSubscriber.SubscribeTime = Cast.DateTime(txtSubscribeTime.Text.Trim());
            using (_session = new Session())
            {			
                if (IsAddNew())
                {
                    flag = msgSubscriber.Create(_session);
                }
                else
                {			
                   msgSubscriber.SubscriberId = int.Parse(this.txtSubscriberId.Value);
                   flag = msgSubscriber.Update(_session,  "TmplCode",  "UserId",  "GroupId",  "IsGroup",  "SubscribeTime");
                }
            }
            this.txtSubscriberId.Value = msgSubscriber.SubscriberId.ToString();
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
		    logger.Info("保存MsgSubscriber", ex);
            WebUtil.ShowMsg(this, "发生未处理的异常,请刷新页面重新操作，或者联系系统管理员");
		}
    }
	
	 private bool IsAddNew()
    {         
         if( txtSubscriberId.Value.Trim().Length==0)
                return true;
        return false;
    }
	#endregion
}
