package com.magic.app;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
import com.magic.utils.*;
import java.sql.*;
/**
 * 产生人员信息详细界面
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */

public class PersonDetail extends BaseServlet 
{
   
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setCharacterEncoding("GBK");
         int doc_id=0;
        super.service(request, response);
         if(!assertSession(request))
        {
           response.sendRedirect("/relogin.html");
           return;
        }
        HttpSession session=request.getSession();
           SessionInfo sessionInfo = new SessionInfo(
            ((Integer)session.getAttribute("login_company_id")).intValue(), 
            ((Integer)session.getAttribute("login_operator_id")).intValue(), 
            (java.util.HashMap)session.getAttribute("powermap"),
            request.getParameterMap());
            
            

        try{
            doc_id=Integer.parseInt(request.getParameter("doc_id"));
        }catch(Exception e){
            System.out.println(e);
            message("未完成",e.getMessage(),request, response);
            return;
        }
    
        DocType doc=new DocType(DocType.ORG_PERSON);
        HTMLView hv=new HTMLView();
        hv.setSessionInfo(sessionInfo);
        hv.setWidth(750);
        hv.setStyle(HTMLView.REPORT_STYLE);
        hv.setSubject("人员信息");
        hv.addContent("<BR>");
        hv.addDetailViewEx(DocType.ORG_PERSON,doc_id);
        hv.addContent("<BR>");
        hv.addButton("增加","../app/viewnew?doc_type=20004");
        hv.addButton("删除","../app/ctrdelete?doc_type=20004&doc_id="+doc_id);
        hv.addButton("更改","../app/viewupdate?doc_type=20004&doc_id="+doc_id);
        hv.addButton("密码重置","../app/viewupdate?doc_type=20008&doc_id="+doc_id);
        hv.addButton("角色设置","../person_role.jsp?doc_type=20004&doc_id="+doc_id);
        hv.addButton("返回", "history.go(-1)");
        hv.addButtons();
        hv.addSubject("用户角色");
        String s="<table width=\"750.0\" border=0 cellspacing=1 cellpadding=5  align=center >\n";
        s=s+"<th scope=\"col\" width=\"30%\" class=OraTableColumnHeader >角色名称</th>";
        s=s+"<th scope=\"col\" width=\"70%\" class=OraTableColumnHeader >角色描述</th></tr>";
        
        DBLink dblink=new DBLink();
        Statement stmt=null;
        ResultSet rs=null;
        try
      	{
            stmt=dblink.createStatement();rs=stmt.executeQuery("select * from vw_org_person_role where person_id="+doc_id);
            while(rs.next())
            {
            s=s+"<td class=OraTableCellText noWrap align=middle >"+rs.getString("name")+"</td>\n";
            s=s+"<td class=OraTableCellText noWrap align=middle >"+StringUtil.cEmpty(rs.getString("description"))+"</td></tr>\n";
            }
        }
		catch(Exception e)
        {
            e.printStackTrace();
        }finally{
            try{
                if (rs != null ) rs.close();
                if (stmt != null ) stmt.close();
                if(dblink!=null) dblink.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
        }
		s=s+"</table>";
        hv.addContent(s);
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter(); 
       
        out.println(hv.getHTML());
        out.close();  
    }
}