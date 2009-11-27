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
        //��ҳ�¼�
        this.magicPagerSub.PageSize = this.magicPagerMain.PageSize; //���ҳ����2����ҳ�ؼ��������д����һ��(�ؼ�bug)
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
                WebUtil.ShowMsg(this, "ȡ��ʧ��,�������Ա��ϵ", "ȡ��ʧ��");
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
                WebUtil.ShowMsg(this, "����ʧ��,�������Ա��ϵ", "����ʧ��");
                return;
            }
        }
    }

    private void RestoreLastQuery(ISession session)
    {
        //����Ǵ�����ҳ�淵�ظ�ҳ�棬�ָ�����ֵ
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
        //����ǰ��ѯ�����յ�url�У������µ�ҳ�棬���ص�ʱ���ٻ�ԭ��Щ����ֵ
        //helper�������÷�
        //1. helper.GetValue(�ؼ���); �Ѿ���װ�õĿؼ�����ʹ�����ַ�������GetReturnUrl()��RestoreLastQuery()�з���1
        //2. helper.Push(������, ����ֵ); û�з�װ�Ŀؼ�����һЩ����Ķ���������ʹ�����ַ�������GetReturnUrl()��RestoreLastQuery()�з���2
        QueryHelper helper = new QueryHelper(this);
        //helper.GetValue(this.txtUserName); ����_����1
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
        helper.Push("Status", strStatus); //����_����2
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
            logger.Info("��ѯ������ʧ��", ex);
            WebUtil.ShowMsg(this, "����δ������쳣,��ˢ��ҳ�����²�����������ϵϵͳ����Ա");
        }
    }
}