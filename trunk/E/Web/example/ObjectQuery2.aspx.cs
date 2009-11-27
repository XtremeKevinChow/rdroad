using System;
using System.Data;
using System.Collections.Generic;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Query;
using Magic.Sys;

public partial class ObjectQuery2 : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        using (ISession session = new Magic.Framework.ORM.Session())
        {
            //测试1：对ON条件，能够根据别名（t）找到子查询，尝试从子查询中判断CreateBy是否是一个属性名字。
            //测试结果应当为true，这个属性需要替换为自段名
            string oql = @"
select * from User a inner join
(select distinct CreateBy from Org) t
on t.CreateBy=a.UserId
";
            ObjectQuery query = session.CreateObjectQuery(oql);
            IList<User> users = query.Attach(typeof(Org), "Org").Attach(typeof(User), "User").List<User>();
            /*预期执行的SQL
select a.User_ID,a.User_Name,a.Password,a.Full_Name,a.User_Type,a.Email,a.Gender,a.Employee_No
    ,a.Ext,a.Mobile,a.Home_Phone,a.Home_Address,a.Birthday,a.Note,a.Status,a.Org_ID
    ,a.Last_Logon_Time,a.Create_Time,a.Create_By,a.Modify_By,a.Modify_Time 
from SYS_User a 
inner join (select distinct Create_By from SYS_Org) t on t.Create_By=a.User_ID
             */

            //测试2：这次子查询里面将CreateBy取了一个别名，所以ON条件中的CreateBy不再需要替换
            oql = @"
select * from User a inner join
(select distinct CreateBy as CreateBy from Org) t
on t.CreateBy=a.UserId
";
            users = session.CreateObjectQuery(oql)
                .Attach(typeof(Org), "Org").Attach(typeof(User), "User")
                .List<User>();
            /*预期执行的SQL
select a.User_ID,a.User_Name,a.Password,a.Full_Name,a.User_Type,a.Email,a.Gender,a.Employee_No
    ,a.Ext,a.Mobile,a.Home_Phone,a.Home_Address,a.Birthday,a.Note,a.Status,a.Org_ID
    ,a.Last_Logon_Time,a.Create_Time,a.Create_By,a.Modify_By,a.Modify_Time 
from SYS_User a 
inner join (select distinct Create_By as CreateBy from SYS_Org) t on t.CreateBy=a.User_ID
             */

            //测试3：子查询里面情况稍微复杂一点，这将影响列名决策的执行流。
            //因为测试2中，子查询只有一个table，列名决策遇到这种情况时，如果列名在子查询里面没有定义别名，它就可以肯定是来自这个表的自段或实体的属性
            oql = @"
select OrgCode as OrgCode from Org a, User u 
where len(a.OrgCode)=? and a.CreateBy=u.UserId and u.UserId=189
";
            DataSet ds = session.CreateObjectQuery(oql)
                .Attach(typeof(Org), "Org").Attach(typeof(User), "User")
                .SetValue(0, 2, "OrgId")
                .DataSet();
            /*预期执行的SQL
select a.User_ID,a.User_Name,a.Password,a.Full_Name,a.User_Type,a.Email,a.Gender,a.Employee_No
  ,a.Ext,a.Mobile,a.Home_Phone,a.Home_Address,a.Birthday,a.Note,a.Status,a.Org_ID
  ,a.Last_Logon_Time,a.Create_Time,a.Create_By,a.Modify_By,a.Modify_Time 
from SYS_User a 
inner join (
  select c.*,p.Org_Name as ParentName 
  from SYS_Org p,SYS_Org as c 
  where c.Parent_ID=p.Org_ID
) t on t.Create_By=a.User_ID 
where t.ParentName like @_p0 order by t.Org_Code
             */
            foreach (DataRow row in ds.Tables[0].Rows)
            {
                this.Response.Write(row["OrgCode"]);
                this.Response.Write("<br />");
            }

            oql = @"select * from Org where len(OrgCode)=2";
            object obj = session.CreateObjectQuery(oql)
                .Attach(typeof(Org), "Org")
                .Scalar();
            this.Response.Write(obj);

            //this.rpUser.DataSource = users;
            //this.rpUser.DataBind();
        }
    }
}
