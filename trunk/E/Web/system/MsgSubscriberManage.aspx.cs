
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

public partial class MsgSubscriberManagePage : System.Web.UI.Page
{
    Mode _actionMode = Mode.Undefined;
	log4net.ILog logger = log4net.LogManager.GetLogger(typeof(MsgSubscriber));
    ISession _session = null;
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            //Init Controls Data before do query
			//restore query from last time
            _actionMode = WebUtil.GetActionMode(this);

			
            using (_session = new Session())
            {
                if (_actionMode == Mode.Edit)
                {
                    LoadData();
                }             
            }
          
        }
        if (Request["return"] != null)
        {
            this.btnReturn.NavigateUrl = Request["return"];            
        }
    }

    private void LoadData()
    {
        try
        {
            string tmplCode = WebUtil.Param("tmplcode");
            if (string.IsNullOrEmpty(tmplCode))
                throw new ApplicationException("没有传递消息模板的代码");
            MsgTemplate tmpl = MsgTemplate.Retrieve(_session, tmplCode);
            if (tmpl == null)
                throw new ApplicationException(string.Format("模板代码:{0}的消息模板为空", tmplCode));

            this.txtTmplCode.Text = tmpl.TmplCode.Trim();
            this.txtTmplName.Text = tmpl.Name.Trim();
            this.txtMsgTypeId.Value = tmpl.MsgTypeId.ToString();
            this.txtMsgTypeName.Text = MessageType.Retrieve(_session, tmpl.MsgTypeId).TypeName;

            QueryAndBindData(this.rptSubscribeGroup, this.magicPagerGroup, true, 1, this.magicPagerGroup.PageSize, true);
            QueryAndBindData(this.rptSubscribeUser, this.magicPagerUser, false, 1, this.magicPagerUser.PageSize, true);

        }
        catch (UnauthorizedException ex)
        {
            WebUtil.ShowMsg(this, ex.Message, "警告");
        }
        catch (ApplicationException ex)
        {
            WebUtil.ShowMsg(this, ex.Message, "提示");
        }
        catch (Exception ex)
        {
            logger.Info("查询MsgSubscriber", ex);
            WebUtil.ShowMsg(this, "发生未处理的异常,请刷新页面重新操作，或者联系系统管理员");
        }
    }
   
   
    #region Buttons,Command, PageChange Event Handler
    
	//Pager's PageChange event handler
    protected void MagicPagerUser_PageChanged(object src, Magic.Web.UI.PageChangedEventArgs e)
    {
        QueryAndBindData(this.rptSubscribeUser, this.magicPagerUser,false, e.NewPageIndex, e.Pager.PageSize,false);
    }

    protected void MagicPagerGroup_PageChanged(object src, Magic.Web.UI.PageChangedEventArgs e)
    {
        QueryAndBindData(this.rptSubscribeGroup, this.magicPagerGroup,true,e.NewPageIndex, e.Pager.PageSize, false);
    }


    //MagicToolbar's MagicItem OnClick Event Handler, it's for both the top one and the bottom one.
    protected void MagicItemCommand(object sender,MagicItemEventArgs e)
    {
		try
		{
            Repeater rpt = null;
            MagicPager pager = null;
            bool isGroup = false; ;
			if (e.CommandName == "DeleteUser")
			{ // do delete 	          
                rpt = this.rptSubscribeUser;
                pager = this.magicPagerUser;
                isGroup = false;
			  }
            else if (e.CommandName == "DeleteGroup")
            {
                rpt = this.rptSubscribeGroup;
                pager = this.magicPagerGroup;
                isGroup = true;
            }
            if (rpt != null)
            {
                using (_session = new Session())
                {
                    foreach (RepeaterItem item in rpt.Items)
                    {
                        HtmlInputCheckBox chk = item.FindControl("checkbox") as HtmlInputCheckBox;
                        if (chk != null && chk.Checked)
                        {
                            int subscriberId = Cast.Int(chk.Attributes["subscriberId"]);

                            MsgSubscriber.Delete(_session, subscriberId);
                        }
                    }

                    QueryAndBindData(rpt,pager, isGroup,1, pager.PageSize, true);

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
		    logger.Info("删除MsgSubscriber", ex);
            WebUtil.ShowMsg(this, "发生未处理的异常,请刷新页面重新操作，或者联系系统管理员");
		}
        
    }
    #endregion
    
    #region Private Methods

	/// <summary>
	/// Get query conditions from page , and query data from db, then bind the query result to Repeater
	/// </summary>
    private void QueryAndBindData(Repeater rpt, MagicPager pager, bool isGroup, int pageIndex, int pageSize, bool fetchRecordCount)
    {
        string tmplCode = txtTmplCode.Text.Trim();
        DataTable dtMsgBuscriber = null; 
               
        int recordCount = 0;
        string oql = "";
        if (!isGroup)
            oql = @"select s.SubscriberId as SubscriberId,
                                      s.TmplCode as TmplCode,
                                       s.UserId as UserId,
                                        u.UserName as UserName,
                                        u.FullName as FullName,
                                        s.SubscribeTime as SubscribeTime
                            from MsgSubscriber s inner join User u on s.UserId=u.UserId and s.IsGroup=0";
        else
            oql = @" select s.SubscriberId as SubscriberId,
                                    s.TmplCode as TmplCode,
                                       s.UserId as UserId,
                                        s.GroupId as GroupId,
                                        g.Name as GroupName,
                                        g.Description as Description,
                                s.SubscribeTime as SubscribeTime
                            from MsgSubscriber s inner join UserGroup g on s.GroupId=g.GroupId and s.IsGroup=1";

        using (Session session = new Session())
        {
            ObjectQuery query = session.CreateObjectQuery(oql);

            query.Attach(typeof(MsgSubscriber));
            if (isGroup)
                query.Attach(typeof(UserGroup));
            else
                query.Attach(typeof(User));

            query.And(Exp.Eq("s.TmplCode", tmplCode));
	
            
            query.SetPage(pageIndex, pageSize);

            dtMsgBuscriber = query.DataSet().Tables[0];

            if (fetchRecordCount)
                recordCount = query.Count();            
        }

        rpt.DataSource = dtMsgBuscriber;
        rpt.DataBind();

        

        if (fetchRecordCount)
        {
           pager.RecordCount = recordCount;
        }
   
        WebUtil.SetMagicPager(pager, pageSize, pageIndex);
        
    }
	
	#endregion
    protected void btnAddUserGroup_Click(object sender, EventArgs e)
    {
        int groupId = -1;
        if (int.TryParse(this.txtUserGroupId.Value, out groupId))
        {
            using (_session = new Session())
            {
                MsgSubscriber subscriber = new MsgSubscriber();
                subscriber.GroupId = groupId;
                subscriber.IsGroup = true;
                subscriber.SubscribeTime = DateTime.Now;
                subscriber.TmplCode = this.txtTmplCode.Text.Trim();
                if (!MsgSubscriber.Exists(_session, new string[] { "TmplCode", "GroupId" }, new object[] { subscriber.TmplCode, subscriber.GroupId }))
                {
                    subscriber.Create(_session);
                    QueryAndBindData(this.rptSubscribeGroup, this.magicPagerGroup, true, 1, magicPagerGroup.PageSize, true);
                }
                else
                    WebUtil.ShowMsg(this, "该用户组已经订阅");

                
            }
        }
    }

    protected void btnAddUser_Click(object sender, EventArgs e)
    {
        int userId = -1;
        if (int.TryParse(this.txtUserId.Value, out userId))
        {
            using (_session = new Session())
            {
                MsgSubscriber subscriber = new MsgSubscriber();
                subscriber.UserId = userId;
                subscriber.IsGroup = false;
                subscriber.SubscribeTime = DateTime.Now;
                subscriber.TmplCode = this.txtTmplCode.Text.Trim();
                if (!MsgSubscriber.Exists(_session, new string[] { "TmplCode", "UserId" }, new object[] { subscriber.TmplCode, subscriber.UserId }))
                {
                    subscriber.Create(_session);
                    QueryAndBindData(this.rptSubscribeUser, this.magicPagerUser, true, 1, magicPagerUser.PageSize, true);
                }
                else
                    WebUtil.ShowMsg(this, "该用户已经订阅");


            }
        }
    }
}
