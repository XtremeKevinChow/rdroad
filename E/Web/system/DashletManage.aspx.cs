
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

public partial class DashletManagePage : System.Web.UI.Page
{  
	log4net.ILog logger = log4net.LogManager.GetLogger(typeof(Dashlet));
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            //Init Controls Data before do query
            EnumUtil.BindDictionaryItems2ListControl(this.ddlCategory, "Dashlet_Category");
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
		try
		{ 
			if (e.CommandName == "Delete")
			{ // do delete 	          
				  using(ISession session = new Session())
				  {       
						foreach (RepeaterItem item in this.rptDashlet.Items)
						{
							HtmlInputCheckBox chk = item.FindControl("checkbox") as HtmlInputCheckBox;
							if (chk != null && chk.Checked)
							{
								int dashletId = Cast.Int(chk.Attributes["dashletId"]);
								 
								Dashlet.Delete(session  ,dashletId  );
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
		    logger.Info("删除Dashlet", ex);
            WebUtil.ShowMsg(this, "发生未处理的异常,请刷新页面重新操作，或者联系系统管理员");
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
            helper.SetValue(this.ddlCategory);        
            helper.SetValue(this.txtTitle);        
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
        helper.GetValue(this.ddlCategory);        
        helper.GetValue(this.txtTitle);        
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
        string category = ddlCategory.SelectedValue;
        string title = txtTitle.Text.Trim();
		
        IList<Dashlet> dataDashlet = null;
        int recordCount = 0;
        using (Session session = new Session())
        {
            EntityQuery query = session.CreateEntityQuery<Dashlet>();

           	if(!String.IsNullOrEmpty(category))
			{
				query.And(Exp.Like("Category", "%" + category+ "%")); 
			}
           	if(!String.IsNullOrEmpty(title))
			{
				query.And(Exp.Like("Title", "%" + title+ "%")); 
			}
			
            query.SetPage(pageIndex, pageSize);
			
             dataDashlet = query.List<Dashlet>();

            if (fetchRecordCount)
                recordCount = query.Count();            
        }
        this.rptDashlet.DataSource = dataDashlet ;
        this.rptDashlet.DataBind();

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
