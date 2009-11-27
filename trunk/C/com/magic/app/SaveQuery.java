package com.magic.app;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
import com.magic.utils.*;
import java.sql.*;
/**
 * 高级查询保存界面
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */

public class SaveQuery extends BaseServlet 
{
   
    
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

        request.setCharacterEncoding("GBK");
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
            
            
        int doc_type =0;
        DocType doc;
        String data_source ;
				String sp = "";
        try{
            doc_type=Integer.parseInt(request.getParameter("doc_type"));
            doc=new DocType(doc_type);
        }catch(Exception e)
        {
            System.out.println(e);
            message("未完成",e.getMessage(),request, response);
            return;
        }

        HTMLView hv=new HTMLView();
        hv.setSessionInfo(sessionInfo);
        hv.setDocType(doc_type);
  
				String searchStr="";
        try
        {
           searchStr=hv.getSearchStr();
        }catch(KException e)
        {
            System.out.println(e);
            message("未完成",e.getMessage(),request, response);
            return;
        }
         
		switch(doc_type){

		case 10100:

			sp="{?=call config.f_bas_saved_queries_add(?,?,?,?,?,?)}";
			if(StringUtil.cEmpty(request.getParameter("name")).trim().equals("") || request.getParameter("is_public")==null){
                 message("未完成","必要的字段没有输入",request, response);
                 return;
            }		   
                        
			int para_index=1;						
        DBLink dblink=new DBLink();
        Statement stmt=null;
        ResultSet rs=null;
        CallableStatement cstmt =null;
        try{
            cstmt=dblink.prepareCall(sp); 
			para_index++;
			cstmt.setInt(para_index,Integer.parseInt(request.getParameter("parent_doc_type"))); 
            System.out.println("参数名称:要保存的Doc_type");
            System.out.println("参数值："+Integer.parseInt(request.getParameter("parent_doc_type")));
            System.out.println("参数类型:INTEGER");
            System.out.println("");
            
            para_index++;
            cstmt.setString(para_index,StringUtil.cEmpty(request.getParameter("searchStr"))); 
            System.out.println("参数名称:SQL语句");
            System.out.println("参数值："+StringUtil.cEmpty(request.getParameter("searchStr")));
            System.out.println("参数类型:STRING");
            System.out.println("");

            para_index++;
            cstmt.setString(para_index,StringUtil.cEmpty(request.getParameter("name"))); 
            System.out.println("参数名称:查询名称");
            System.out.println("参数值："+StringUtil.cEmpty(request.getParameter("name")));
            System.out.println("参数类型:STRING");
            System.out.println("");
            para_index++;
            cstmt.setInt(para_index,Integer.parseInt(request.getParameter("is_public"))); 
            System.out.println("参数名称:是否公开");
            System.out.println("参数值："+Integer.parseInt(request.getParameter("is_public")));
            System.out.println("参数类型:INTEGER");
            //  System.out.println("------"+getIntParameter("is_public"));
            
            para_index++;
            cstmt.setInt(para_index,sessionInfo.getOperatorID()); 
            System.out.println("参数名称:操作员ID");
            System.out.println("参数值："+sessionInfo.getOperatorID());
            System.out.println("参数类型:INTEGER");
            System.out.println("");

            para_index++;
            cstmt.setInt(para_index,sessionInfo.getCompanyID()); 
            System.out.println("参数名称:俱乐部ID");
            System.out.println("参数值："+sessionInfo.getCompanyID());
            System.out.println("参数类型:INTEGER");

            cstmt.registerOutParameter(1,java.sql.Types.INTEGER);
            cstmt.execute();
            int re=cstmt.getShort(1);
                          
            if(re<0){
               KException ke=new KException(re);
               message("未完成","错误代码:"+ke.getErrorCode()+"<br>错误描述:"+ke.getMessage(),request, response);
            } else 
            {
                            success("完成","完成记录增加操作.<br>[<a href=\"javascript:javascript:document.location.href='../app/viewquery?doc_type="+Integer.parseInt(request.getParameter("parent_doc_type"))+"';\">返回.</a>]",request, response);
                              return;
            }  
		} catch(Exception e){
			System.out.println(""+e.getMessage());
		}finally
        {
             try
             {
                if(rs!=null) rs.close();
                if(stmt!=null) stmt.close();
                dblink.close();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
		 break;

		default:
             hv=new HTMLView();
             hv.setSessionInfo(sessionInfo);
             hv.setWidth(750);
              
             hv.setSubject("增加&nbsp;"+doc.getDocName() + "高级查询收藏");
       
			       String s = "<table width=750.0 border=0 cellspacing=1 cellpadding=5>";

						 s = s + "<form name=\"fm_add\" method=\"post\" action=\"../app/savequery\">";
						 s = s + "<input type=\"hidden\" name=\"doc_type\" value=\"10100\">";
						 s = s + "<input type=\"hidden\" name=\"parent_doc_type\" value=\""+ doc_type+"\">";
						 s = s + "<input type=\"hidden\" name=\"searchStr\" value=\" "+ searchStr+"\">";
						 s = s + "<tr>";
						 s = s + "<td width=\"20%\" align=\"right\" ><font color=red>*</font>&nbsp;名称</td>";
						 s = s + "<td align=\"left\" width=\"40%\" nowarp> <input name=\"name\" value=\"\"></td><td width=\"40%\" class=OraTipText align=\"left\"></td></tr>";
						 s = s + "<tr><td width=\"20%\" align=\"right\" ><font color=red>*</font>&nbsp;是否公用</td><td align=\"left\" width=\"40%\" nowarp>";
						 s = s + " <input type='radio' name=\"is_public\" value=\"1\">是&nbsp;<input type='radio' name=\"is_public\" value=\"0\">否&nbsp;</td>";
						 s = s + "<td width=\"40%\" class=OraTipText align=\"left\"></td></tr><tr><td colspan=3>";
						 s = s + "<table width=\"750.0\" border=0 cellspacing=0 cellpadding=0 background=\"../crmjsp/images/headerlinepixel_onwhite.gif\" HEIGHT=1 WIDTH=1>";
						 s = s + "<tr background=\"../crmjsp/images/headerlinepixel_onwhite.gif\" HEIGHT=1 WIDTH=1>";
						 s = s + "<td  height=1 width=\"750.0\" background=\"../crmjsp/images/headerlinepixel_onwhite.gif\" HEIGHT=1 WIDTH=1><img src=\"../crmjsp/images/headerlinepixel_onwhite.gif\" HEIGHT=1 WIDTH=1></td>";
						 s = s + "</tr></table></td></tr><tr><td align=\"right\" colspan=3>";
						 s = s + "<input type=\"button\" class=\"button2\" value=\"提交\" onClick=\"submit();\">&nbsp;<input type=\"button\" class=\"button2\" value=\"取消\" onClick=\"history.back();\"></tr></form>";
						 s = s + "</table>";
						 hv.addContent(s);

						 //hv.addNewView(10100,doc_type);
              
             response.setContentType(CONTENT_TYPE);
             PrintWriter out = response.getWriter(); 
            
             out.println(hv.getHTML());
             out.close();  
						 break;
				 }
				//如果没有输入显示提示
        //if(searchStr.trim().equals("1=1") && StringUtil.cEmpty(request.getParameter("first")).equals("1")){   
				//} else {   //输入查询条件则转到新增收藏的界面

       //显示界面
		}
}