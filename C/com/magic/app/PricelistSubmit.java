package com.magic.app;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
import com.magic.utils.*;
import java.sql.*;
/**
 * 目录、单页、促销活动复制、删除、发布、终止
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */
public class PricelistSubmit extends BaseServlet 
{
  
  public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
       
       request.setCharacterEncoding("GB2312");
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
            
            
        int doc_type=0;
        String sub_method = null;
        String list_id = null;
        String sp=null;
        super.service(request, response);
        try{
            doc_type=Integer.parseInt(request.getParameter("parent_doc_type"));
						sub_method = request.getParameter("act");

        }catch(Exception e)
        {
             System.out.println(e);
             message("未完成",e.getMessage(),request, response);
             return;
        }


        
		if(sub_method.equals("copy")){
			list_id = request.getParameter("pricelist_id");
			if(list_id ==null || list_id.equals("")){
                message("未完成","没有选择要复制的价目表",request, response);
                return;
			}
   			sp= "{?=call catalog.f_pricelist_lines_copy(?,?,?)}";
		}
        else if(sub_method.equals("release")){
           sp= "{?=call catalog.f_pricelist_release(?)}";
		} 
        else if(sub_method.equals("delete")){
           sp= "{?=call catalog.f_pricelist_delete(?,?)}";
		} 
        else if(sub_method.equals("pause")){
					 sp= "{?=call catalog.f_pricelist_pause(?)}";
		}
        
        DBLink dblink=new DBLink();
        CallableStatement cstmt=null;
        try
        {
            int para_index=1;
            cstmt = dblink.prepareCall(sp); 
            if(sub_method.equals("copy")){
            para_index++;
			cstmt.setInt(para_index,Integer.parseInt(request.getParameter("pricelist_id"))); 
            //System.out.println("参数名称:ID");
            //System.out.println("参数值："+Integer.parseInt(request.getParameter("pricelist_id")));
            //System.out.println("参数类型:INTEGER");

			para_index++;
			cstmt.setInt(para_index,Integer.parseInt(request.getParameter("parent_doc_id"))); 
            //System.out.println("参数名称:父文档ID");
            //System.out.println("参数值："+Integer.parseInt(request.getParameter("parent_doc_id")));
            //System.out.println("参数类型:INTEGER");

			para_index++;
            cstmt.setInt(para_index,sessionInfo.getOperatorID()); 
            //System.out.println("参数名称:操作员ID");
            //System.out.println("参数值："+sessionInfo.getOperatorID());
            //System.out.println("参数类型:INTEGER");

        } 
        else {
			para_index++;
			cstmt.setInt(para_index,Integer.parseInt(request.getParameter("card_id"))); 
            //System.out.println("参数名称:父文档ID");
            //System.out.println("参数值："+Integer.parseInt(request.getParameter("card_id")));
            //System.out.println("参数类型:INTEGER");
		}

		if(sub_method.equals("delete")){
			para_index++;
            cstmt.setInt(para_index,sessionInfo.getOperatorID()); 
            //System.out.println("参数名称:操作员ID");
            //System.out.println("参数值："+sessionInfo.getOperatorID());
            //System.out.println("参数类型:INTEGER");
		}
					
        cstmt.registerOutParameter(1,java.sql.Types.INTEGER);
        cstmt.execute();
        int re=cstmt.getShort(1);
        if(re<0)
        {
            KException ke=new KException(re);
            message("未完成","错误代码:"+ke.getErrorCode()+"<br>错误描述:"+ke.getMessage(),request, response);
        }
        else
        {
			//促销
			if(sub_method.equals("copy") && doc_type==1510){
				success("完成","完成价目表复制记录操作.<br><a href=\"../app/pricelistdetail?doc_type=1510&doc_id="+Integer.parseInt(request.getParameter("parent_doc_id"))+"\">返回.</a>",request, response);
				return;
			}

			if(sub_method.equals("release") && doc_type==1510){
				success("完成","完成价目表发布操作.<br><a href=\"../app/pricelistdetail?doc_type=1510&doc_id="+Integer.parseInt(request.getParameter("card_id"))+"\">返回.</a>",request, response);
				return;
			}

			if(sub_method.equals("delete") && doc_type==1510){
				 success("完成","完成价目表删除操作.<br><a href=\"../app/pricelistdetail?doc_type=1510&doc_id="+Integer.parseInt(request.getParameter("card_id"))+"\">返回.</a>",request, response);
				return;
			}

			if(sub_method.equals("pause") && doc_type==1510){
				success("完成","完成价目表中止操作.<br><a href=\"../app/pricelistdetail?doc_type=1510&doc_id="+Integer.parseInt(request.getParameter("card_id"))+"\">返回.</a>",request, response);
				return;
			}

							 //单页
			if(sub_method.equals("copy") && doc_type==1660){
				success("完成","完成价目表复制记录操作.<br><a href=\"../app/pricelistdetail?doc_type=1660&doc_id="+Integer.parseInt(request.getParameter("parent_doc_id"))+"\">返回.</a>",request, response);
				return;
			}

			if(sub_method.equals("release") && doc_type==1660){
				success("完成","完成价目表发布操作.<br><a href=\"../app/pricelistdetail?doc_type=1660&doc_id="+Integer.parseInt(request.getParameter("card_id"))+"\">返回.</a>",request, response);
				 return;
			}

             if(sub_method.equals("delete") && doc_type==1660){
                 success("完成","完成价目表删除操作.<br><a href=\"../app/pricelistdetail?doc_type=1660&doc_id="+Integer.parseInt(request.getParameter("card_id"))+"\">返回.</a>",request, response);
                 return;
             }
    
             if(sub_method.equals("pause") && doc_type==1660){
                 success("完成","完成价目表中止操作.<br><a href=\"../app/pricelistdetail?doc_type=1660&doc_id="+Integer.parseInt(request.getParameter("card_id"))+"\">返回.</a>",request, response);
                 return;
             }
    
             //目录
             if(sub_method.equals("copy") && doc_type==1680){
                 success("完成","完成目录表复制记录操作.<br><a href=\"../app/catalogdetail?doc_type=1680&doc_id="+Integer.parseInt(request.getParameter("parent_doc_id"))+"\">返回.</a>",request, response);
                 return;
             }
    
             if(sub_method.equals("release") && doc_type==1680){
                 success("完成","完成目录表发布操作.<br><a href=\"../app/catalogdetail?doc_type=1680&doc_id="+Integer.parseInt(request.getParameter("card_id"))+"\">返回.</a>",request, response);
                 return;
             }
    
             if(sub_method.equals("delete") && doc_type==1680){
                 success("完成","完成目录表删除操作.<br><a href=\"../app/catalogdetail?doc_type=1680&doc_id="+Integer.parseInt(request.getParameter("card_id"))+"\">返回.</a>",request, response);
                 return;
             }
    
             if(sub_method.equals("pause") && doc_type==1680){
                 success("完成","完成目录表中止操作.<br><a href=\"../app/catalogdetail?doc_type=1680&doc_id="+Integer.parseInt(request.getParameter("card_id"))+"\">返回.</a>",request, response);
                 return;
             }

            success("完成","完成新增记录操作.<br><a href=\"../app/viewconfig?doc_type="+doc_type+"\">返回.</a>",request, response);
        }
        }catch(Exception e)
        {
          System.out.println(e);
        }finally
        {
             try
            {
                if(cstmt!=null) cstmt.close();
                dblink.close();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }     
}