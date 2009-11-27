using System;
using System.Data;
using System.Collections.Generic;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Query;
using Magic.Sys;

public partial class oq : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        string oql = @"
select 
    a.OrgId, a.OrgCode, --测试：Property，使用Entity别名。期望结果：别名保留，Property替换为Column
    OrgName, --测试：Property字。期望结果：Property替换为Column
    --测试：CASE语句。期望结果：正确解析，并将里面的Property替换为Column
    case a.OrgType when 1 then '组织' when 2 then '合作企业' else '不明物' end as Type,
    u.User_Name as CreateBy, --测试：Column，使用Table别名，Column定义了别名。期望结果：全部保留，不做转换
    a.CreateDate, a.ModifyBy, a.ModifyDate --测试：Property
from Org a --测试：Entity，定义了别名。期望结果：Entity替换为Table。
inner join SYS_User u --测试：Table，定义了别名。期望结果：全部保留
    --测试：Column与Property关联。期望结果：Column保留，Property替换为Column。    
    --注意：SELECT列表中有一个别名叫做CreateBy，
    --所以：a.CreateBy表示Entity Org的属性，需要替换为自段名。如果使用u.User_ID=CreateBy，则CreateBy为别名，这种情况不应当替换
    on u.User_ID=a.CreateBy 
where 
    a.OrgType=:ot --测试：命名参数
    and u.user_id=? --测试：位置参数
";
        using (ISession session = new Magic.Framework.ORM.Session())
        {
            ObjectQuery query = session.CreateObjectQuery(oql);
            query.Attach(typeof(Org), "Org")
                .SetValue(":ot", OrgType.Own, "OrgType")
                .SetValue(0, 189, "CreateBy")
                .SetFirstOffset(4)
                .SetLastOffset(7);

            DataSet ds = query.DataSet();  //1
            /*执行的语句
WITH q AS(
  select a.Org_ID,a.Org_Code,Org_Name
    ,case a.Org_Type when 1 then '组织' when 2 then '合作企业' else '不明物' end as Type
    ,u.User_Name as CreateBy,a.Create_Date,a.Modify_By,a.Modify_Date
    ,ROW_NUMBER() over(ORDER BY CURRENT_TIMESTAMP) AS _mm_row 
  from SYS_Org a 
  inner join SYS_User u on u.User_ID=a.Create_By 
  where a.Org_Type=@_p0 and u.user_id=@_p1
) 
SELECT * FROM q WHERE _mm_row>=@_mm_first AND _mm_row<=@_mm_last
             */

            IList<Org> orgs = query.List<Org>();  //2
            /*执行的语句
WITH q AS(
  select a.Org_ID,a.Parent_ID,a.Org_Code,a.Org_Name,a.Org_Type,a.Is_Virtual
    ,a.Is_Root,a.Org_Seq,a.Del_Flag,a.Description,a.Manager,a.Create_Date
    ,a.Create_By,a.Modify_Date,a.Modify_By
    ,ROW_NUMBER() over(ORDER BY CURRENT_TIMESTAMP) AS _mm_row 
  from SYS_Org a inner join SYS_User u on u.User_ID=a.Create_By 
  where a.Org_Type=@_p0 and u.user_id=@_p1
) 
SELECT * FROM q WHERE _mm_row>=@_mm_first AND _mm_row<=@_mm_last
             */

            IList<User> users = query.List<User>(); //3
            /*执行的语句
WITH q AS(
  select u.User_ID,u.User_Name,u.Password,u.Full_Name,u.User_Type
    ,u.Email,u.Gender,u.Employee_No,u.Ext,u.Mobile,u.Home_Phone
    ,u.Home_Address,u.Birthday,u.Note,u.Status,u.Org_ID,u.Last_Logon_Time
    ,u.Create_Time,u.Create_By,u.Modify_By,u.Modify_Time
    ,ROW_NUMBER() over(ORDER BY CURRENT_TIMESTAMP) AS _mm_row 
  from SYS_Org a inner join SYS_User u on u.User_ID=a.Create_By 
  where a.Org_Type=@_p0 and u.user_id=@_p1
) 
SELECT * FROM q WHERE _mm_row>=@_mm_first AND _mm_row<=@_mm_last 
             */

            this.rpDs.DataSource = ds;
            this.rpDs.DataBind();
            this.rpOrg.DataSource = orgs;
            this.rpOrg.DataBind();
            this.rpUser.DataSource = users;
            this.rpUser.DataBind();
        }
    }
}
