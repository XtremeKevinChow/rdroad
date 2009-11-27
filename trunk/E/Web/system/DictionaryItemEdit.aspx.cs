
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

public partial class DictionaryItemEditPage : System.Web.UI.Page
{  
    Mode _actionMode = Mode.Undefined;
	ISession _session = null;
	log4net.ILog logger = log4net.LogManager.GetLogger(typeof(DictionaryItem));
    protected void Page_Load(object sender, EventArgs e)
    {
        _actionMode = WebUtil.GetActionMode(this);
        if (!IsPostBack)
        {
            //Init Data
            //Init Controls Data before do query
            if (_actionMode == Mode.New)
            {
                EnumUtil.BindEnumData2ListControl<DictionaryItemType>(rdlItemType, false, DictionaryItemType.Group);
                this.rdlItemType.SelectedValue = DictionaryItemType.Group.ToString();
            }
            if (_actionMode == Mode.AddChild || _actionMode == Mode.New)
            {
                EnumUtil.BindEnumData2ListControl<DictionaryItemType>(rdlItemType, false, DictionaryItemType.Boolean, DictionaryItemType.Numric, DictionaryItemType.String);
                this.rdlItemType.SelectedValue = DictionaryItemType.String.ToString();
            }
                 

            using (_session = new Session())
            {                               
                this.trGroupCode.Visible = false;
                if (_actionMode == Mode.Edit)
                {
                    LoadData();
                }
                else if(_actionMode == Mode.AddChild)
                {
                    AddChild();
                }
                logger.Info("操作类型" + _actionMode.ToString());
            }
        }
		 if (Request["return"] != null)
        {
			this.toolbarup["Return"].NavigateUrl = Request["return"]; 
            this.toolbarbottom["Return"].NavigateUrl = Request["return"];
        }
    }

    private void AddChild()
    {
        string groupCode = WebUtil.Param("groupCode");
        if (!string.IsNullOrEmpty(groupCode))
        {
            DictionaryItem item = DictionaryItem.Retrieve(_session, groupCode);
            if (item == null)
            {
                WebUtil.ShowMsg(this, "传入的组代码找不到");
                this.txtStringValue.Disabled = true;
                return;
            }

            this.trGroupCode.Visible = true;
            this.txtGroupCode.Text = groupCode;
            this.txtGroupName.Text = item.Name;           
        }
    }
   
	#region LoadData
	private void LoadData()
	{
		hidItemCode.Value = WebUtil.Param("itemCode");
		string itemCode = WebUtil.Param("itemCode");
       
        DictionaryItem dictionaryItem = null;           
        dictionaryItem = DictionaryItem.Retrieve(_session, itemCode  );
      
        if (dictionaryItem != null)
        {
            txtCode.Text = hidItemCode.Value;
            txtCode.Enabled = false;
			txtGroupCode.Text = dictionaryItem.GroupCode;
			txtName.Text = dictionaryItem.Name;
            this.rdlItemType.Items.Clear();
            EnumUtil.BindEnumData2ListControl<DictionaryItemType>(this.rdlItemType, false, dictionaryItem.ItemType);
			rdlItemType.SelectedValue = dictionaryItem.ItemType.ToString();
            txtNumberValue.Text = Cast.String(dictionaryItem.NumberValue, "0");
			txtStringValue.Value = dictionaryItem.StringValue;
            ddlBoolValue.SelectedValue = dictionaryItem.BoolValue.ToString();
			txtNote.Value = dictionaryItem.Note;
			
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
        DictionaryItem dictionaryItem = new  DictionaryItem();
		bool flag = true;   
        try
        {
            if (_actionMode == Mode.AddChild)
            {
                dictionaryItem.GroupCode = txtGroupCode.Text.Trim();
            }
            else if (_actionMode == Mode.New)
            {
                dictionaryItem.GroupCode = DictionaryItem.Root.ItemCode;
            }
           
            dictionaryItem.ItemCode = txtCode.Text.Trim();
            dictionaryItem.Name = txtName.Text.Trim();
				dictionaryItem.ItemType = Cast.Enum<DictionaryItemType>(rdlItemType.SelectedValue);
                switch (dictionaryItem.ItemType)
                {
                    case DictionaryItemType.Boolean:
                        dictionaryItem.BoolValue = Cast.Bool(this.ddlBoolValue.SelectedValue);
                        break;
                    case DictionaryItemType.Numric:
                        dictionaryItem.NumberValue = Cast.Decimal(this.txtNumberValue.Text);
                        break;
                    case DictionaryItemType.String:
                        if (this.txtStringValue.Value.Trim().Length > 1000)
                        {
                            dictionaryItem.StringValue = this.txtStringValue.Value.Trim().Substring(0, 1000);
                        }
                        else
                            dictionaryItem.StringValue = this.txtStringValue.Value;
                        break;
                    default:
                        break;
                }
				dictionaryItem.Note = txtNote.Value.Trim();
            using (_session = new Session())
            {			
                if (IsAddNew())
                {
                    if (DictionaryItem.Exists(_session, new string[] { "ItemCode" }, new object[] { dictionaryItem.ItemCode }))
                    {
                        throw new ApplicationException(string.Format("代码:{0}已经存在；如果是子项，建议将组代码作为前缀", dictionaryItem.ItemCode));
                    }
                    flag = dictionaryItem.Create(_session);
                }
                else
                {			
                   dictionaryItem.ItemCode = (this.hidItemCode.Value);
                   flag = dictionaryItem.Update(_session,"ItemCode","Name","BoolValue","NumberValue","StringValue");
                }
            }
            this.hidItemCode.Value = dictionaryItem.ItemCode.ToString();
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
		    logger.Info("保存DictionaryItem", ex);
            WebUtil.ShowMsg(this, "发生未处理的异常,请刷新页面重新操作，或者联系系统管理员");
		}
    }
	
	 private bool IsAddNew()
    {         
         if( hidItemCode.Value.Trim().Length==0)
                return true;
        return false;
    }
	#endregion
}
