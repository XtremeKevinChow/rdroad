
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

public partial class MessageEditPage : System.Web.UI.Page
{  
    Mode _actionMode = Mode.Undefined;
	ISession _session = null;
	log4net.ILog logger = log4net.LogManager.GetLogger(typeof(Message));
    protected void Page_Load(object sender, EventArgs e)
    {        
        if (!IsPostBack)
        {
            //Init Data
            //Init Controls Data before do query
			 EnumUtil.BindEnumData2ListControl<MessageAccessibility>(rdlAccessibility, false);
             EnumUtil.BindEnumData2ListControl<MessageStatus>(rdlStatus, false);

            using (_session = new Session())
            {
                this.ddlMsgTypeId.Items.Add("");
                EnumUtil.BindEntity2ListControl<MessageType>(_session, this.ddlMsgTypeId, "TypeName", "MsgTypeId");
               
                this.txtCreateTime.Text = RenderUtil.FormatDatetime(DateTime.Now);
                this.txtSendTime.Text = this.txtCreateTime.Text;
                this.txtCreateBy.Text = SecuritySession.CurrentUser.UserName;
                
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
		txtMessageId.Value = WebUtil.Param("messageId");
		int messageId = Cast.Int(WebUtil.Param("messageId"));
       
        Message message = null;           
        message = Message.Retrieve(_session, messageId  );
      
        if (message != null)
        {
            ddlMsgTypeId.SelectedValue = message.MsgTypeId.ToString();
            ChangeMsgTypeId(_session);

			ddlTmplCode.SelectedValue = message.TmplCode;
			
			txtTitle.Text = message.Title;
			txtContent.Value = message.Content;
			rdlAccessibility.SelectedValue = message.Accessibility.ToString();
			txtCreateTime.Text = RenderUtil.FormatDatetime(message.CreateTime);
			txtViewEntry.Text = message.ViewEntry;
			txtSendTime.Text = RenderUtil.FormatDatetime(message.SendTime);
			txtExpireTime.Text = RenderUtil.FormatDatetime(message.ExpireTime);
			txtCreateBy.Text = message.CreateBy;
			txtLastResponseTime.Text = RenderUtil.FormatDatetime(message.LastResponseTime);
			rdlStatus.SelectedValue = message.Status.ToString();
			txtResponseEntry.Text = message.ResponseEntry;
			txtSource.Text = message.Source;
			txtDestination.Text = message.Destination;

            this.ddlMsgTypeId.Enabled = false;
            this.ddlTmplCode.Enabled = false;
            this.txtContent.Disabled = true;
            this.txtTitle.Enabled = false;
            this.rdlAccessibility.Enabled = false;
            this.rdlStatus.Enabled = false;
            this.txtCreateBy.Enabled = false;
            this.txtCreateTime.Enabled = false;
            this.txtDestination.Enabled = false;
            this.txtExpireTime.Enabled = false;
            this.txtLastResponseTime.Enabled = false;
            this.txtSendTime.Enabled = false;
            this.txtSource.Enabled = false;
            this.toolbarup.Items["Save"].Enabled = false;
            this.toolbarbottom.Items["Save"].Enabled = false;
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
        Message message = new  Message();
		bool flag = true;   
        try
        {
				message.TmplCode = Cast.String(ddlTmplCode.SelectedValue);
				message.MsgTypeId = Cast.Int(ddlMsgTypeId.SelectedValue);
				message.Title = Cast.String(txtTitle.Text.Trim());
				message.Content = Cast.String(txtContent.Value.Trim());
				message.Accessibility = Cast.Enum<MessageAccessibility>(rdlAccessibility.SelectedValue);
				message.CreateTime = Cast.DateTime(txtCreateTime.Text.Trim());
				message.ViewEntry = Cast.String(txtViewEntry.Text.Trim());
				message.SendTime = Cast.DateTime(txtSendTime.Text.Trim());
				message.ExpireTime = Cast.DateTime(txtExpireTime.Text.Trim());
				message.CreateBy = Cast.String(txtCreateBy.Text.Trim());
				message.LastResponseTime = Cast.DateTime(txtLastResponseTime.Text.Trim());
				message.Status = Cast.Enum<MessageStatus>(rdlStatus.SelectedValue);
				message.ResponseEntry = Cast.String(txtResponseEntry.Text.Trim());
				message.Source = Cast.String(txtSource.Text.Trim());
				message.Destination = Cast.String(txtDestination.Text.Trim());
            using (_session = new Session())
            {			
                if (IsAddNew())
                {
                    flag = message.Create(_session);
                }
                else
                {			
                   message.MessageId = int.Parse(this.txtMessageId.Value);
                   flag = message.Update(_session,  "TmplCode",  "MsgTypeId",  "Title",  "Content",  "Accessibility",  "CreateTime",  "ViewEntry",  "SendTime",  "ExpireTime",  "CreateBy",  "LastResponseTime",  "Status",  "ResponseEntry",  "Source",  "Destination");
                }
            }
            this.txtMessageId.Value = message.MessageId.ToString();
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
		    logger.Info("保存Message", ex);
            WebUtil.ShowMsg(this, "发生未处理的异常,请刷新页面重新操作，或者联系系统管理员");
		}
    }
	
	 private bool IsAddNew()
    {         
         if( txtMessageId.Value.Trim().Length==0)
                return true;
        return false;
    }
	#endregion
     protected void ddlMsgTypeId_SelectedIndexChanged(object sender, EventArgs e)
     {
         using (_session = new Session())
         {
             ChangeMsgTypeId(_session);
         }
     }

     private void ChangeMsgTypeId(ISession session)
     {
         this.ddlTmplCode.Items.Clear();
         int msgtypeId = -1;
         if (int.TryParse(this.ddlMsgTypeId.SelectedValue, out msgtypeId))
         {
         
                 EnumUtil.BindEntity2ListControl<MsgTemplate>(session, this.ddlTmplCode, "Name", "TmplCode", Exp.Eq("MsgTypeId", msgtypeId));
             
         }
     }
}
