
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
using Magic.Sys;
using Magic.Security;

public partial class UserGroupManagePage : System.Web.UI.Page
{  

    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            //Init Controls Data before do query
            //
            EnumUtil.BindEnumData2ListControl<UserGroupType>(this.cklGroupType,true);
            //restore query from last time
            RestoreLastQuery();
        }
    }
   
   
    #region Buttons,Command, PageChange Event Handler
    
	//Pager's PageChange event handler
    protected void MagicPager_PageChanged(object src, Magic.Web.UI.PageChangedEventArgs e)
    {
        QueryAndBindData(e.NewPageIndex, e.Pager.PageSize,false);
    }
	
	//QueryButton Click Handler
    protected void btnQuery_Click(object sender, ImageClickEventArgs e)
    {
        QueryAndBindData(1, magicPagerMain.PageSize, true);
    }

    //MagicToolbar's MagicItem OnClick Event Handler, it's for both the top one and the bottom one.
    protected void MagicItemCommand(object sender,MagicItemEventArgs e)
    {
        if (e.CommandName == "Delete")
        { // do delete 
            //Get Selected Items
            //IList<T> pks = new List<T>();
            foreach (RepeaterItem item in this.rptUserGroup.Items)
            {
                HtmlInputCheckBox chk = item.FindControl("checkbox") as HtmlInputCheckBox;
                if (chk != null)
                {
                    if (chk.Checked)
                    {
                        //Selected
                    }
                }               
            }
           //Do your logic
        }
    }
    #endregion
    
    #region Private Methods
    /// <summary>
    /// If user navigate back from the detail edit page,
    /// we must restore the query including:conditions and results  which the user made before he jump to other page.
    /// </summary>
    private void RestoreLastQuery()
    {
        QueryHelper helper = new QueryHelper(this);

        //check page has query parameter, if existed then load query data ,or do nothing
        if (helper.HasQueryParameter())
        {
            helper.SetValue(this.txtName);        
            helper.SetValue(this.txtDescription);        
            helper.SetValue(this.cklGroupType);        
            helper.SetValue(this.txtModifyTime);        
            // Pager's size and index
            int pageSize = Cast.Int(helper.Pop("ps"), this.magicPagerMain.PageSize);
            int pageIndex = Cast.Int(helper.Pop("pi"), 1);
            
            //DoQuery
            QueryAndBindData(pageIndex, pageSize, true);
        }
        //else do nothing
    }

    /// <summary>
    /// Collect current query conditions and pager's size and current index.
    /// as return url
    /// </summary>
    /// <returns></returns>
    private string GetReturnUrl()
    {
        QueryHelper helper = new QueryHelper(this);
        helper.GetValue(this.txtName);        
        helper.GetValue(this.txtDescription);        
        helper.GetValue(this.cklGroupType);        
        helper.GetValue(this.txtModifyTime);        
        
        //Pager's Size and Index
        helper.Push("ps", this.magicPagerMain.PageSize);
        helper.Push("pi", this.magicPagerMain.CurrentPageIndex);
        return helper.OutputReturnUrl();
        
    }

	/// <summary>
	/// Get query conditions from page , and query data from db, then bind the query result to Repeater
	/// </summary>
    private void QueryAndBindData(int pageIndex, int pageSize, bool fetchRecordCount)
    {
        string name = txtName.Text.Trim();
        string description = txtDescription.Text.Trim();
        IList<string> groupType = WebUtil.GetSelectedValues(cklGroupType);
        string modifyTime = txtModifyTime.Text.Trim();
		
        IList<UserGroup> dataUserGroup = null;
        
		int recordCount = 0;

        using (Session session = new Session())
        {
            EntityQuery query = session.CreateEntityQuery<UserGroup>();

           	if(!String.IsNullOrEmpty(name))
			{
				query.And(Exp.Like("Name", "%" + name+ "%")); 
			}
           	if(!String.IsNullOrEmpty(description))
			{
				query.And(Exp.Like("Description", "%" + description+ "%")); 
			}
           	if(groupType != null && groupType.Count > 0)
			{
				query.And(Exp.In("GroupType", groupType)); 
			}
           	if(!String.IsNullOrEmpty(modifyTime))
			{
				query.And(Exp.Eq("ModifyTime", modifyTime)); 
			}
			
            query.SetPage(pageIndex, pageSize);
			
             dataUserGroup = query.List<UserGroup>();

            if (fetchRecordCount)
                recordCount = query.Count();            
        }
        this.rptUserGroup.DataSource = dataUserGroup ;
        this.rptUserGroup.DataBind();

        if (fetchRecordCount)
        {
            this.magicPagerMain.RecordCount = this.magicPagerSub.RecordCount = recordCount;
        }

        WebUtil.SetMagicPager(magicPagerMain, pageSize, pageIndex);
        WebUtil.SetMagicPager(magicPagerSub, pageSize, pageIndex);


        //After a query, save current query 
        this.hidReturnUrl.Value = GetReturnUrl();
    }
	
	#endregion
}
