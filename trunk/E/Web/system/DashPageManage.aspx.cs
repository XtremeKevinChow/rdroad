
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

public partial class DashPageManagePage : System.Web.UI.Page
{  
	log4net.ILog logger = log4net.LogManager.GetLogger(typeof(DashPage));
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            //Init Controls Data before do query
            EnumUtil.BindEnumData2ListControl<DashPageLayout>(this.cklLayout, true);
            EnumUtil.BindEnumData2ListControl<DashPageType>(this.rdlType, true);
            EnumUtil.BindEnumData2ListControl<DashStatus>(this.cklStatus, true);
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
						foreach (RepeaterItem item in this.rptDashPage.Items)
						{
							HtmlInputCheckBox chk = item.FindControl("checkbox") as HtmlInputCheckBox;
							if (chk != null && chk.Checked)
							{
								int dashpageId = Cast.Int(chk.Attributes["dashpageId"]);
								 
								DashPage.Delete(session  ,dashpageId  );
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
		    logger.Info("删除DashPage", ex);
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
            helper.SetValue(this.txtTitle);        
            helper.SetValue(this.cklLayout);        
            helper.SetValue(this.rdlType);        
            helper.SetValue(this.cklStatus);        
            helper.SetValue(this.txtCreateTime);        
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
        helper.GetValue(this.txtTitle);        
        helper.GetValue(this.cklLayout);        
        helper.GetValue(this.rdlType);        
        helper.GetValue(this.cklStatus);        
        helper.GetValue(this.txtCreateTime);        
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
        string title = txtTitle.Text.Trim();
        IList<string> layout = WebUtil.GetSelectedValues(cklLayout);
        string type = rdlType.SelectedValue;
        IList<string> status = WebUtil.GetSelectedValues(cklStatus);
		DateTime createTime ;
		
        IList<DashPage> dataDashPage = null;
        int recordCount = 0;
        using (Session session = new Session())
        {
            EntityQuery query = session.CreateEntityQuery<DashPage>();

           	if(!String.IsNullOrEmpty(title))
			{
				query.And(Exp.Like("Title", "%" + title+ "%")); 
			}
           	if(layout != null && layout.Count > 0)
			{
				query.And(Exp.In("Layout", layout)); 
			}
           	if(!String.IsNullOrEmpty(type))
			{
				query.And(Exp.Eq("Type", type)); 
			}
           	if(status != null && status.Count > 0)
			{
				query.And(Exp.In("Status", status)); 
			}
			if(DateTime.TryParse(txtCreateTime.Text.Trim(), out createTime))
			{
				query.And(Exp.Ge("CreateTime", createTime));
			}
			
            query.SetPage(pageIndex, pageSize);
			
             dataDashPage = query.List<DashPage>();

            if (fetchRecordCount)
                recordCount = query.Count();            
        }
        this.rptDashPage.DataSource = dataDashPage ;
        this.rptDashPage.DataBind();

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
