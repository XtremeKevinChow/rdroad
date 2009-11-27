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

public partial class UserManagePage : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            //����ʹ��ISession�ĵط�������������ʹ��using��䣬ȷ��ISession�ͷţ�
            //�����ʹ��using��䣬������ֹ�ȷ���κ�����£���󶼵�����ISession.Dispose()�����ֹ��ͷ�
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
            QueryAndBindData(session, 1, magicPagerMain.PageSize, true);
        }
    }

    protected void MagicItemCommand(object sender, MagicItemEventArgs e)
    {
        if (e.CommandName == "Delete")
        {
            //MagicToolBar���¼�
            bool deleted = false;
            using (ISession session = new Session())
            {
                session.BeginTransaction();
                try
                {
                    foreach (RepeaterItem item in this.rptUser.Items)
                    {
                        HtmlInputCheckBox chk = item.FindControl("checkbox") as HtmlInputCheckBox;
                        if (chk != null && chk.Checked && Cast.Int(chk.Value) > 0)
                        {
                            Magic.Sys.User user = Magic.Sys.User.Retrieve(session, Cast.Int(chk.Value));
                            if (user != null)
                            {
                                //�߼�ɾ��������״̬update��UserStatus.Deleted
                                user.Status = UserStatus.Deleted;
                                user.ModifyBy = Magic.Security.SecuritySession.CurrentUser.UserId;
                                user.ModifyTime = DateTime.Now;
                                //Update������2���汾��
                                //1. ֻ��ISession������������ʵ�����������
                                //2. ��һ���������Ƶ��ַ������飬ֻ����ָ�����ֶ�
                                user.Update(session, "Status", "ModifyBy", "ModifyTime");
                                deleted = true;
                            }
                        }
                    }
                    session.Commit();
                    if (deleted)
                        this.QueryAndBindData(session, this.magicPagerMain.CurrentPageIndex, this.magicPagerMain.PageSize, true);
                    WebUtil.ShowMsg(this, "ѡ����û��Ѿ���ɾ��", "�����ɹ�");
                }
                catch (Exception err)
                {
                    session.Rollback();
                    WebUtil.ShowError(this, err);
                }
            }
        }
    }

    private void RestoreLastQuery(ISession session)
    {
        int pageSize = 20;
        int pageIndex = 1;
        //����Ǵ�����ҳ�淵�ظ�ҳ�棬�ָ�����ֵ
        QueryHelper helper = new QueryHelper(this);
        if (helper.HasQueryParameter())
        {
            //helper.SetValue(this.txtUserName); //����_����1
            this.txtUserName.Text = helper.Pop("un"); //����_����2
            helper.SetValue(this.txtFullName);
            helper.SetValue(this.cklStatus);
            pageSize = Cast.Int(helper.Pop("ps"), this.magicPagerMain.PageSize);
            pageIndex = Cast.Int(helper.Pop("pi"), 1);
        }
        QueryAndBindData(session, pageIndex, pageSize, true);
    }

    private string GetReturnUrl()
    {
        //����ǰ��ѯ�����յ�url�У������µ�ҳ�棬���ص�ʱ���ٻ�ԭ��Щ����ֵ
        //helper�������÷�
        //1. helper.GetValue(�ؼ���); �Ѿ���װ�õĿؼ�����ʹ�����ַ�������GetReturnUrl()��RestoreLastQuery()�з���1
        //2. helper.Push(������, ����ֵ); û�з�װ�Ŀؼ�����һЩ����Ķ���������ʹ�����ַ�������GetReturnUrl()��RestoreLastQuery()�з���2
        QueryHelper helper = new QueryHelper(this);
        //helper.GetValue(this.txtUserName); ����_����1
        helper.Push("un", this.txtUserName.Text.Trim()); //����_����2
        helper.GetValue(this.txtFullName);
        helper.GetValue(this.cklStatus);
        helper.Push("ps", this.magicPagerMain.PageSize);
        helper.Push("pi", this.magicPagerMain.CurrentPageIndex);
        return helper.OutputReturnUrl();
    }

    private void QueryAndBindData(ISession session, int pageIndex, int pageSize, bool fetchRecordCount)
    {
        string userName = txtUserName.Text.Trim();
        string fullName = txtFullName.Text.Trim();
        IList<UserStatus> status = new List<UserStatus>();
        foreach (ListItem item in cklStatus.Items)
            if (item.Selected) status.Add(Cast.Enum<UserStatus>(item.Value));
        //����OQL��䣬�﷨Ϊ������SQL�﷨��select�������ֶζ�ʹ��as����һ����������.aspx�ļ���ʹ�ñ����󶨣�����select�������ֶ�Ϊ���ݿ��ֶ���
        ObjectQuery query = session.CreateObjectQuery(@"
select u.UserId as UserId,o.OrgName as OrgName,u.UserType as UserType
    ,u.UserName as UserName,u.FullName as FullName,u.Ext as Ext,u.LastLogonTime as LastLogonTime,u.Status as UserStatus
    ,u.ModifyTime as ModifyTime,u2.FullName as ModifyBy
from User u
inner join Org o on u.OrgId=o.OrgId
left join User u2 on u.ModifyBy=u2.UserId
where u.Status<>?
order by u.FullName
")
            .Attach(typeof(Magic.Sys.User)).Attach(typeof(Org)) //OQL�����õ������ж���Ҫ����Attach��������
            //ΪOQL����еı�����ֵ����λ�������ı���OQL��ʹ��?��ʾ������ֵʱ��0��ʼ���������ʣ�
            //�����ı���OQL��ʹ��@����:ǰ׺������ֵʱʹ�ñ�������(����@����:����)
            .SetValue(0, UserStatus.Deleted, "u.Status") //ע�⣺�й����������ʱ����ö��������OQL��������صĲ�����ʹ��"����.������"����
            .And(Exp.In("u.Status", status)) //ΪOQL׷������
            .SetPage(pageIndex, pageSize); //���÷�ҳ����
        //ΪOQL׷������
        //ע�⣺�й����������ʱ����ö��������OQL��������صĲ�����ʹ��"����.������"����
        if (!string.IsNullOrEmpty(userName)) query.And(Exp.Like("u.UserName", "%" + userName + "%"));
        if (!string.IsNullOrEmpty(fullName)) query.And(Exp.Like("u.FullName", "%" + fullName + "%"));

        this.rptUser.DataSource = query.DataSet();
        this.rptUser.DataBind();
        if (fetchRecordCount)
            this.magicPagerMain.RecordCount = this.magicPagerSub.RecordCount = query.Count();
        WebUtil.SetMagicPager(magicPagerMain, pageSize, pageIndex);
        WebUtil.SetMagicPager(magicPagerSub, pageSize, pageIndex);
        //��װ�����ظ�ҳ��ʱ�ĵ�ַ�����õ��������ؿؼ���������JavaScript����༭���������û����ҳ��ʱ����ѷ��ص�ַ����ȥ
        this.hidReturnUrl.Value = GetReturnUrl();
    }
}