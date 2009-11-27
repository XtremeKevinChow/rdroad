
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

public partial class OpLogEditPage : System.Web.UI.Page
{  
    Mode _actionMode = Mode.Undefined;
	ISession _session = null;
	log4net.ILog logger = log4net.LogManager.GetLogger(typeof(OpLog));
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
		hidLogId.Value = WebUtil.Param("logId");
		int logId = Cast.Int(WebUtil.Param("logId"));
       
        OpLog opLog = null;           
        opLog = OpLog.Retrieve(_session, logId  );
      
        if (opLog != null)
        {
			ddlOpObjectType.SelectedValue = opLog.OpObjectType;
			txtOpObjectId.Text = opLog.OpObjectId;
			txtOpContent.Text = opLog.OpContent;
			ddlOpType.SelectedValue = opLog.OpType;
			txtOpTime.Text = RenderUtil.FormatDatetime(opLog.OpTime);
			txtOperatorName.Text = opLog.OperatorName;
			ddlOperatorType.SelectedValue = opLog.OperatorType.ToString();
			txtIP.Text = opLog.IP;
			
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
        OpLog opLog = new  OpLog();
		bool flag = true;   
        try
        {
				opLog.OpObjectType = ddlOpObjectType.SelectedValue;
				opLog.OpObjectId = txtOpObjectId.Text.Trim();
				opLog.OpContent = txtOpContent.Text.Trim();
				opLog.OpType = ddlOpType.SelectedValue;
				opLog.OpTime = Cast.DateTime(txtOpTime.Text.Trim());
				opLog.OperatorName = txtOperatorName.Text.Trim();
				opLog.OperatorType = Cast.Int16(ddlOperatorType.SelectedValue);
				opLog.IP = txtIP.Text.Trim();
            using (_session = new Session())
            {			
                if (IsAddNew())
                {
                    flag = opLog.Create(_session);
                }
                else
                {			
                   opLog.LogId = int.Parse(this.hidLogId.Value);
                   flag = opLog.Update(_session,  "OpObjectType",  "OpObjectId",  "OpContent",  "OpType",  "OpTime",  "OperatorName",  "OperatorType",  "IP");
                }
            }
            this.hidLogId.Value = opLog.LogId.ToString();
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
		    logger.Info("保存OpLog", ex);
            WebUtil.ShowMsg(this, "发生未处理的异常,请刷新页面重新操作，或者联系系统管理员");
		}
    }
	
	 private bool IsAddNew()
    {         
         if( hidLogId.Value.Trim().Length==0)
                return true;
        return false;
    }
	#endregion
}
