using System;
using System.Collections;
using System.Collections.Generic;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using Magic.Framework.Utils;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Query;
using Magic.Web.UI;
using Magic.Sys;
using Magic.ERP.Orders;
using Magic.ERP;
using Magic.ERP.Core;

public partial class ExchangeDeliverManage : System.Web.UI.Page
{
    log4net.ILog logger = log4net.LogManager.GetLogger(typeof(User));

    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            this.txtBeginDate.Text = DateTime.Now.AddDays(-3).ToString("yyyy-MM-dd");
            this.txtEndDate.Text = DateTime.Now.ToString("yyyy-MM-dd");

            using (ISession session = new Session())
            {
                RestoreLastQuery(session);
            }
        }
    }

    protected void MagicPager_PageChanged(object src, Magic.Web.UI.PageChangedEventArgs e)
    {
        //翻页事件
        this.magicPagerSub.PageSize = this.magicPagerMain.PageSize; //如果页面有2个翻页控件，则必须写上这一句(控件bug)
        using (ISession session = new Session())
        {
            QueryAndBindData(session, e.NewPageIndex, e.Pager.PageSize, false);
        }
    }

    protected void btnQuery_Click(object sender, ImageClickEventArgs e)
    {
        using (ISession session = new Session())
        {
            QueryAndBindData(session, 1, this.magicPagerMain.PageSize, true);
        }
    }

    protected void MagicItemCommand(object sender, MagicItemEventArgs e)
    {
        if (e.CommandName == "Delete")
        {
            try
            {
                using (ISession _session = new Session())
                {
                    List<Magic.ERP.Orders.SDHead> List = new List<Magic.ERP.Orders.SDHead>();
                    for (int i = 0; i < this.rptSDHead.Items.Count; i++)
                    {
                        System.Web.UI.HtmlControls.HtmlInputCheckBox objCheckBox = this.rptSDHead.Items[i].FindControl("checkbox") as System.Web.UI.HtmlControls.HtmlInputCheckBox;
                        if (objCheckBox.Checked)
                        {
                            Magic.ERP.Orders.SDHead objHead = Magic.ERP.Orders.SDHead.Retrieve(_session, objCheckBox.Attributes["value"].Trim());
                            if (objHead.Status == Magic.ERP.DeliverStatus.New)
                            {
                                List.Add(objHead);
                            }
                        }
                    }

                    _session.BeginTransaction();
                    try
                    {
                        for (int i = 0; i < List.Count; i++)
                        {
                            _session.CreateEntityQuery<DeliverLine>().Where(Exp.Eq("OrderNumber", List[i].OrderNumber)).Delete();
                            List[i].Delete(_session);
                        }
                        _session.Commit();
                    }
                    catch (Exception ex)
                    {
                        _session.Rollback();
                        throw ex;
                    }

                    QueryAndBindData(_session, 1, this.magicPagerMain.PageSize, true);
                }
            }
            catch (Exception ex)
            {
                WebUtil.ShowMsg(this, "取消失败,请与管理员联系", "取消失败");
                return;
            }
        }

        if (e.CommandName == "Approve")
        {
            try
            {
                using (ISession _session = new Session())
                {
                    List<Magic.ERP.Orders.SDHead> List = new List<Magic.ERP.Orders.SDHead>();
                    for (int i = 0; i < this.rptSDHead.Items.Count; i++)
                    {
                        System.Web.UI.HtmlControls.HtmlInputCheckBox objCheckBox = this.rptSDHead.Items[i].FindControl("checkbox") as System.Web.UI.HtmlControls.HtmlInputCheckBox;
                        if (objCheckBox.Checked)
                        {
                            Magic.ERP.Orders.SDHead objHead = Magic.ERP.Orders.SDHead.Retrieve(_session, objCheckBox.Attributes["value"].Trim());
                            if (objHead.Status == Magic.ERP.DeliverStatus.New)
                            {
                                List.Add(objHead);
                            }
                        }
                    }

                    _session.BeginTransaction();
                    try
                    {
                        for (int i = 0; i < List.Count; i++)
                        {
                            Magic.ERP.ERPUtil.ApproveThis(_session, List[i]);
                            List[i].Status = Magic.ERP.DeliverStatus.Release;
                            List[i].Update(_session, "Status");
                        }
                        _session.Commit();
                    }
                    catch (Exception ex)
                    {
                        _session.Rollback();
                        throw ex;
                    }

                    QueryAndBindData(_session, 1, this.magicPagerMain.PageSize, true);
                }
            }
            catch (Exception ex)
            {
                WebUtil.ShowMsg(this, "发布失败,请与管理员联系", "发布失败");
                return;
            }
        }
    }

    private void RestoreLastQuery(ISession session)
    {
        //如果是从其它页面返回该页面，恢复条件值
        QueryHelper helper = new QueryHelper(this);
        if (helper.HasQueryParameter())
        {
            helper.SetValue(this.txtSDNumber);
            helper.SetValue(this.txtSONumber);
            helper.SetValue(this.txtICNumber);
            helper.SetValue(this.txtUser);
            helper.SetValue(this.txtBeginDate);
            helper.SetValue(this.txtEndDate);

            string strStatus = helper.Pop("Status") ;
            for (int i = 0; i < this.cklStatus.Items.Count; i++)
            {
                this.cklStatus.Items[i].Selected = false;
            }
            if (strStatus.Trim().Length > 0)
            {
                string[] lstStatus = strStatus.Split(',');
                for (int i = 0; i < lstStatus.Length; i++)
                {
                    ListItem Item = this.cklStatus.Items.FindByValue(lstStatus[i]);
                    Item.Selected = true;
                }
            }

            int pageSize = Cast.Int(helper.Pop("ps"), this.magicPagerMain.PageSize);
            int pageIndex = Cast.Int(helper.Pop("pi"), 1);

            QueryAndBindData(session, pageIndex, pageSize, true);
        }
    }

    private string GetReturnUrl()
    {
        //将当前查询条件拚到url中，带到新的页面，返回的时候再还原这些条件值
        //helper的两种用法
        //1. helper.GetValue(控件名); 已经封装好的控件可以使用这种方法，见GetReturnUrl()和RestoreLastQuery()中方法1
        //2. helper.Push(参数名, 参数值); 没有封装的控件，或一些特殊的东西，可以使用这种方法，见GetReturnUrl()和RestoreLastQuery()中方法2
        QueryHelper helper = new QueryHelper(this);
        //helper.GetValue(this.txtUserName); 传参_方法1
        string strStatus = "";
        for (int i = 0; i < this.cklStatus.Items.Count; i++)
        {
            if (this.cklStatus.Items[i].Selected)
            {
                if (strStatus.Trim().Length == 0)
                {
                    strStatus = this.cklStatus.Items[i].Value;
                }
                else
                {
                    strStatus += "," + this.cklStatus.Items[i].Value;
                }
            }
        }
        helper.Push("Status", strStatus); //传参_方法2
        helper.GetValue(this.txtEndDate);
        helper.GetValue(this.txtBeginDate);
        helper.GetValue(this.txtICNumber);
        helper.GetValue(this.txtSDNumber);
        helper.GetValue(this.txtSONumber);
        helper.GetValue(this.txtUser);
        helper.Push("ps", this.magicPagerMain.PageSize);
        helper.Push("pi", this.magicPagerMain.CurrentPageIndex);
        return helper.OutputReturnUrl();
    }

    private void QueryAndBindData(ISession session, int pageIndex, int pageSize, bool fetchRecordCount)
    {
        try
        {
            string strSONumber = this.txtSONumber.Text;
            string strSDNumber = this.txtSDNumber.Text;
            string strICNumber = this.txtICNumber.Text;
            string strUser = this.txtUser.Text;
            DateTime dtBegin = this.txtBeginDate.Text.Trim().Length == 0 ? DateTime.Parse("1900-1-1") : DateTime.Parse(this.txtBeginDate.Text);
            DateTime dtEnd = this.txtEndDate.Text.Trim().Length == 0 ? DateTime.Parse("2900-1-1") : DateTime.Parse(this.txtEndDate.Text);
            List<int> lstStatus = new List<int>();
            for (int i = 0; i < this.cklStatus.Items.Count; i++)
            {
                if (this.cklStatus.Items[i].Selected)
                {
                    lstStatus.Add(int.Parse(this.cklStatus.Items[i].Value.Trim()));
                }
            }

            ObjectQuery query = session.CreateObjectQuery(@"
SELECT A.OrderNumber AS SDNumber,A.RefOrderNumber AS ERNumber,A.DeliverDate AS DeliverDate,B.OrderNumber AS ICNumber,C.StatusText AS StatusText,
    D.FullName AS UserName,A.CreateTime AS CreateDate
FROM SDHead A
LEFT JOIN ICLine B ON A.OrderNumber=B.RefOrderNumber
LEFT JOIN OrderStatusDef C ON A.Status=C.StatusValue AND C.OrderTypeCode=A.OrderTypeCode
LEFT JOIN User D ON A.CreateUser=D.UserId
WHERE A.OrderTypeCode=? AND A.OrderNumber LIKE ? AND A.RefOrderNumber LIKE ? AND D.FullName LIKE ? AND A.CreateTime>=? AND A.CreateTime<=?
")
            .Attach(typeof(Magic.ERP.Orders.SDHead))
            .Attach(typeof(Magic.ERP.Orders.ICLine))
            .Attach(typeof(Magic.ERP.Core.OrderStatusDef))
            .Attach(typeof(Magic.Sys.User))
            .SetValue(0,Magic.ERP.Orders.EDHead.ORDER_TYPE,"A.OrderTypeCode")
            .SetValue(1, "%" + strSDNumber + "%", "A.OrderNumber")
            .SetValue(2, "%" + strSONumber + "%", "A.RefOrderNumber")
            .SetValue(3, "%" + strUser + "%", "D.FullName")
            .SetValue(4, dtBegin, "A.CreateTime")
            .SetValue(5, dtEnd, "A.CreateTime")
            .And(Exp.In("A.Status", lstStatus.ToArray()))
            .SetPage(pageIndex,pageSize);
            if (strICNumber.Trim().Length > 0)
            {
                query = query.And(Exp.Like("B.OrderNumber", "%" + strICNumber + "%"));
            }

            this.rptSDHead.DataSource = query.DataSet().Tables[0];
            this.rptSDHead.DataBind();

            if (fetchRecordCount)
            {
                this.magicPagerMain.RecordCount = query.Count();
                this.magicPagerSub.RecordCount = query.Count();
            }

            WebUtil.SetMagicPager(magicPagerMain, pageSize, pageIndex);
            WebUtil.SetMagicPager(magicPagerSub, pageSize, pageIndex);
            this.hidReturnUrl.Value = GetReturnUrl();
        }
        catch (Exception ex)
        {
            logger.Info("查询发货单失败", ex);
            WebUtil.ShowMsg(this, "发生未处理的异常,请刷新页面重新操作，或者联系系统管理员");
        }
    }
}