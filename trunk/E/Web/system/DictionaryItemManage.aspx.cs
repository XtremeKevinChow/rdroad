
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

public partial class DictionaryItemManagePage : System.Web.UI.Page
{  
	log4net.ILog logger = log4net.LogManager.GetLogger(typeof(DictionaryItem));
    ISession _session = null;
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            //Init Controls Data before do query
			 EnumUtil.BindEnumData2ListControl<DictionaryItemType>(rdlItemType, true); 
			//restore query from last time
             using (_session = new Session())
             {
                 RestoreLastQuery();
             }
        }
    }
   
   
    #region Buttons,Command, PageChange Event Handler
    
	//Pager's PageChange event handler
    protected void MagicPager_PageChanged(object src, Magic.Web.UI.PageChangedEventArgs e)
    {
        using (_session = new Session())
        {
            QueryAndBindData(e.NewPageIndex, e.Pager.PageSize, false);
        }
    }
	
	//QueryButton Click Handler
    protected void btnQuery_Click(object sender, ImageClickEventArgs e)
    {
        using (_session = new Session())
        {
            QueryAndBindData(1, magicPagerMain.PageSize, true);
        }
    }

    //MagicToolbar's MagicItem OnClick Event Handler, it's for both the top one and the bottom one.
    protected void MagicItemCommand(object sender,MagicItemEventArgs e)
    {
		try
		{ 
			if (e.CommandName == "Delete")
			{ // do delete 	          
				  using(_session = new Session())
				  {       
						foreach (RepeaterItem item in this.rptDictionaryItem.Items)
						{
							HtmlInputCheckBox chk = item.FindControl("checkbox") as HtmlInputCheckBox;
							if (chk != null && chk.Checked)
							{
								string itemCode = Cast.String(chk.Attributes["itemCode"]);
								 
								DictionaryItem.Delete(_session  ,itemCode  );
							}               
						}
		           
						QueryAndBindData(1,this.magicPagerMain.PageSize, true);
		           
				   }
			  } 
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
		    logger.Info("删除DictionaryItem", ex);
            WebUtil.ShowError(this, ex);
		}
        
    }

    protected void rptDictionaryItem_ItemDataBound(object sender, RepeaterItemEventArgs e)
    {
        //
       // <a href="javascript:Edit('<%# Eval("ItemCode")%>',true)">新增子项</a>
        RepeaterItem item = e.Item;
        DictionaryItem group = item.DataItem as DictionaryItem;
       
        if (group != null && group.ItemType == DictionaryItemType.Group)
        {
            Panel panel = item.FindControl("OperationPanel") as Panel;
            if (panel != null)
            {
                HtmlAnchor ank = new HtmlAnchor();
                ank.HRef = string.Format("javascript:Edit('{0}',true)",group.ItemCode);
                ank.InnerText = "新增子项";
                panel.Controls.Add(ank);
            }
            Repeater rptSubItems = item.FindControl("rptSubItems") as Repeater;
            
            if (rptSubItems != null)
            {
                IList<DictionaryItem> subItems = DictionaryItem.GetDictionaryItemsByGroup(_session, group.ItemCode);
                if (subItems != null && subItems.Count > 0)
                {
                    rptSubItems.DataSource = subItems;
                    rptSubItems.DataBind();
                }
            }


        }
    }

    protected void OnCommandHandler(object sender, CommandEventArgs e)
    {
        using (_session = new Session())
        {
            if (e.CommandName == "DeleteSubItem")
            {
                string itemCode = e.CommandArgument.ToString();
                DictionaryItem item = DictionaryItem.Retrieve(_session, itemCode);
                if (item != null)
                {
                    if (item.Delete(_session))
                    {
                        QueryAndBindData(this.magicPagerMain.CurrentPageIndex, this.magicPagerMain.PageSize, true);
                    }
                }

                WebUtil.ShowMsg(this, "删除成功！");
            }
        }
        WebUtil.ShowMsg(this, e.CommandName);
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
            helper.SetValue(this.txtItemCode);        
            helper.SetValue(this.txtGroupCode);        
            helper.SetValue(this.txtName);        
            helper.SetValue(this.rdlItemType);        
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
        helper.GetValue(this.txtItemCode);        
        helper.GetValue(this.txtGroupCode);        
        helper.GetValue(this.txtName);        
        helper.GetValue(this.rdlItemType);        
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
        string itemCode = txtItemCode.Text.Trim();
        string groupCode = txtGroupCode.Text.Trim();
        string name = txtName.Text.Trim();
        string itemType = rdlItemType.SelectedValue;
		
        IList<DictionaryItem> dataDictionaryItem = null;
        int recordCount = 0;
      //  using (_session = new Session())
        {
            EntityQuery query = _session.CreateEntityQuery<DictionaryItem>();

           	if(!String.IsNullOrEmpty(itemCode))
			{
				query.And(Exp.Like("ItemCode", "%" + itemCode+ "%")); 
			}
            if (!String.IsNullOrEmpty(groupCode))
            {
                query.And(Exp.Eq("GroupCode", groupCode));
            }
            else
            {
                query.And(Exp.Eq("GroupCode",DictionaryItem.Root.ItemCode));
            }
           	if(!String.IsNullOrEmpty(name))
			{
				query.And(Exp.Like("Name", "%" + name+ "%")); 
			}
           	if(!String.IsNullOrEmpty(itemType))
			{
				query.And(Exp.Eq("ItemType", itemType)); 
			}
			
            query.SetPage(pageIndex, pageSize);
			
             dataDictionaryItem = query.List<DictionaryItem>();

            if (fetchRecordCount)
                recordCount = query.Count();            
        }
        this.rptDictionaryItem.DataSource = dataDictionaryItem ;
        this.rptDictionaryItem.DataBind();

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
