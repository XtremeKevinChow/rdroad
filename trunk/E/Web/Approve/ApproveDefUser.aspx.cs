using System;
using System.Collections.Generic;
using System.Data;
using System.Web.UI.WebControls;
using System.Web.UI.HtmlControls;
using Magic.Web.UI;
using Magic.Sys;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Query;
using Magic.ERP.Core;

public partial class Approve_ApproveDefUser : System.Web.UI.Page
{
    private string OrderTypeCode
    {
        get
        {
            return WebUtil.Param("type").Trim();
        }
    }

    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            this.txtOrdType.Value = this.OrderTypeCode;
            using (ISession session = new Session())
            {
                this.QueryAndBindData(session);
            }
        }
    }

    private void QueryAndBindData(ISession session)
    {
        this.repeatControl.DataSource = session.CreateObjectQuery(@"
select ad.StepIndex as StepIndex,ad.UserSequnce as UserSequnce,
    u.UserId as UserID,u.FullName as UserName,u.Email as Email,u.Ext as Ext,u.Mobile as Mobile
from OrderApproveDef ad
inner join User u on ad.UserID=u.UserId
where ad.OrderTypeCode=?typeCode
order by ad.StepIndex")
            .Attach(typeof(OrderApproveDef)).Attach(typeof(User))
            .SetValue("?typeCode", this.OrderTypeCode, "ad.OrderTypeCode")
            .DataSet();
        this.repeatControl.DataBind();
    }
    protected void repeatControl_ItemDataBound(object sender, RepeaterItemEventArgs e)
    {
        //DataRowView drv = e.Item.DataItem as DataRowView;
        //if (drv == null) return;

        //HtmlInputText txt = e.Item.FindControl("txtSeq") as HtmlInputText;

    }
    protected void MagicItemCommand(object sender, MagicItemEventArgs e)
    {
        if (e.CommandName != "Save" && e.CommandName != "Delete")
        {
            WebUtil.ShowError(this, "无效的命令");
            return;
        }
        if (this.repeatControl.Items.Count <= 0) return;

        IList<OrderApproveDef> defines = new List<OrderApproveDef>();
        foreach (RepeaterItem item in this.repeatControl.Items)
        {
            HtmlInputCheckBox chk = item.FindControl("checkbox") as HtmlInputCheckBox;
            if (e.CommandName == "Delete" && chk.Checked) continue;
            HtmlInputText txt = item.FindControl("txtSeq") as HtmlInputText;
            int userId = Magic.Framework.Utils.Cast.Int(chk.Value, -1);
            if (userId <= 0)
            {
                WebUtil.ShowError(this, "无法读取签核用户资料，请和管理员联系");
                return;
            }

            OrderApproveDef def = new OrderApproveDef();
            def.OrderTypeCode = this.OrderTypeCode;
            def.UserID = userId;
            def.UserSequnce = Magic.Framework.Utils.Cast.Int(txt.Value);
            defines.Add(def);
        }

        using (ISession session = new Session())
        {
            try
            {
                session.BeginTransaction();
                session.CreateEntityQuery<OrderApproveDef>()
                    .Where(Exp.Eq("OrderTypeCode", this.OrderTypeCode))
                    .Delete();
                this.SortDefines(defines);
                for (int i = 1; i <= defines.Count; i++)
                {
                    defines[i-1].StepIndex = i;
                    defines[i-1].Create(session);
                }
                session.Commit();
                if (e.CommandName == "Save")
                    WebUtil.ShowMsg(this, "签核顺序保存成功");
                else
                    WebUtil.ShowMsg(this, "选择的签核人员已经删除");
                this.QueryAndBindData(session);
            }
            catch (Exception er)
            {
                session.Rollback();
                WebUtil.ShowError(this, er);
            }
        }
    }
    protected void cmdAddUser_Click(object sender, EventArgs e)
    {
        string[] userIds = this.txtUserToAdd.Value.Trim().Trim(';').Split(';');
        if (userIds != null && userIds.Length > 0)
        {
            using (ISession session = new Session())
            {
                DataSet ds = session.CreateObjectQuery("select max(UserSequnce) as UserSequnce,max(StepIndex) as StepIndex from OrderApproveDef where OrderTypeCode=?typeCode")
                    .Attach(typeof(OrderApproveDef))
                    .SetValue("?typeCode", this.OrderTypeCode, "OrderTypeCode")
                    .DataSet();
                int maxSeq = ds.Tables[0].Rows.Count <= 0 ? 0 : Magic.Framework.Utils.Cast.Int(ds.Tables[0].Rows[0]["UserSequnce"], 0);
                int maxIndex = ds.Tables[0].Rows.Count <= 0 ? 0 : Magic.Framework.Utils.Cast.Int(ds.Tables[0].Rows[0]["StepIndex"], 0);

                try
                {
                    session.BeginTransaction();
                    for (int i = 0; i < userIds.Length; i++)
                    {
                        int userId = Magic.Framework.Utils.Cast.Int(userIds[i], 0);
                        if (userId <= 0) continue;
                        OrderApproveDef def = new OrderApproveDef();
                        def.OrderTypeCode = this.OrderTypeCode;
                        def.UserID = userId;
                        def.UserSequnce = (++maxSeq);
                        def.StepIndex = (++maxIndex);
                        def.Create(session);
                    }
                    session.Commit();
                    WebUtil.ShowMsg(this, "签核用户添加成功");
                    this.QueryAndBindData(session);
                }
                catch (Exception er)
                {
                    session.Rollback();
                    WebUtil.ShowError(this, er);
                }
            }
        }
    }

    private void SortDefines(IList<OrderApproveDef> defines)
    {
        if (defines == null || defines.Count <= 1) return;
        bool exchanged = true;
        while (exchanged)
        {
            exchanged = false;
            for (int i = 1; i < defines.Count; i++)
            {
                if (defines[i - 1].UserSequnce > defines[i].UserSequnce)
                {
                    OrderApproveDef t = defines[i - 1];
                    defines[i - 1] = defines[i];
                    defines[i] = t;
                    exchanged = true;
                }
            }
        }
    }
}