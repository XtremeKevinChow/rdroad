using System;
using System.Collections.Generic;
using System.Web.UI.WebControls;
using Magic.ERP.Core;
using Magic.ERP;
using Magic.Framework.ORM;
using Magic.Web.UI;

public partial class Basis_TransTypeDefWHArea : System.Web.UI.Page
{
    private string TransTypeCode
    {
        get
        {
            return WebUtil.Param("type");
        }
    }
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            using (ISession session = new Session())
            {
                this.BindArea(session);
                this.QueryAndBindData(session);
            }
        }
    }

    private void BindArea(ISession session)
    {
        IList<WHArea> areas = session.CreateObjectQuery(@"
select 1 from WHArea where AreaCode not in(
    select AreaCode from WHAreaCfg where AreaCfgCode=?transTypeCode
)
    and Status=?status and IsReservedArea=?isReserved and IsTransArea=?transArea
order by AreaCode")
            .Attach(typeof(WHArea)).Attach(typeof(WHAreaCfg))
            .SetValue("?transTypeCode", this.TransTypeCode, EntityManager.GetPropMapping(typeof(WHAreaCfg), "AreaCfgCode").DbTypeInfo)
            .SetValue("?status", WHStatus.Enable, "Status")
            .SetValue("?isReserved", false, "IsReservedArea")
            .SetValue("?transArea", true, "IsTransArea")
            .List<WHArea>();
        this.drpArea.Items.Clear();
        foreach (WHArea area in areas)
            this.drpArea.Items.Add(new ListItem(area.AreaCode, area.AreaCode));
    }
    private void QueryAndBindData(ISession session)
    {
        this.repeaterControl.DataSource = session.CreateObjectQuery(@"
select 1 from WHArea where AreaCode in (select AreaCode from WHAreaCfg where AreaCfgCode=?transTypeCode)
order by AreaCode")
            .Attach(typeof(WHArea)).Attach(typeof(WHAreaCfg))
            .SetValue("?transTypeCode", this.TransTypeCode, EntityManager.GetPropMapping(typeof(WHAreaCfg), "AreaCfgCode").DbTypeInfo)
            .List<WHArea>();
        this.repeaterControl.DataBind();
    }

    protected void cmdDelete_Click(object sender, EventArgs e)
    {
        LinkButton cmd = sender as LinkButton;
        if (cmd == null) return;
        string area = cmd.Attributes["area"];
        if (string.IsNullOrEmpty(area) || area.Trim().Length <= 0) return;
        using (ISession session = new Session())
        {
            WHAreaCfg cfg = WHAreaCfg.Retrieve(session, this.TransTypeCode, area);
            if (cfg != null)
            {
                cfg.Delete(session);

                this.BindArea(session);
                this.QueryAndBindData(session);
            }
        }
    }

    protected void MagicItemCommand(object sender, MagicItemEventArgs e)
    {
        if (e.CommandName == "Add")
        {
            if (!string.IsNullOrEmpty(this.drpArea.SelectedValue))
            {
                using (ISession session = new Session())
                {
                    WHAreaCfg cfg = new WHAreaCfg();
                    cfg.AreaCfgCode = this.TransTypeCode;
                    cfg.AreaCode = this.drpArea.SelectedValue;
                    cfg.RequireAllChild = false;
                    cfg.Create(session);

                    this.BindArea(session);
                    this.QueryAndBindData(session);
                }
            }
        }
    }
}