package com.magic.app;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
import com.magic.utils.*;
import java.sql.*;
/**
 * Ŀ¼����ҳ����������ơ�ɾ������������ֹ
 * @author magic
 * @Vender �Ϻ��������ϵͳ���޹�˾ (magic Software System Co.,ltd)
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
             message("δ���",e.getMessage(),request, response);
             return;
        }


        
		if(sub_method.equals("copy")){
			list_id = request.getParameter("pricelist_id");
			if(list_id ==null || list_id.equals("")){
                message("δ���","û��ѡ��Ҫ���Ƶļ�Ŀ��",request, response);
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
            //System.out.println("��������:ID");
            //System.out.println("����ֵ��"+Integer.parseInt(request.getParameter("pricelist_id")));
            //System.out.println("��������:INTEGER");

			para_index++;
			cstmt.setInt(para_index,Integer.parseInt(request.getParameter("parent_doc_id"))); 
            //System.out.println("��������:���ĵ�ID");
            //System.out.println("����ֵ��"+Integer.parseInt(request.getParameter("parent_doc_id")));
            //System.out.println("��������:INTEGER");

			para_index++;
            cstmt.setInt(para_index,sessionInfo.getOperatorID()); 
            //System.out.println("��������:����ԱID");
            //System.out.println("����ֵ��"+sessionInfo.getOperatorID());
            //System.out.println("��������:INTEGER");

        } 
        else {
			para_index++;
			cstmt.setInt(para_index,Integer.parseInt(request.getParameter("card_id"))); 
            //System.out.println("��������:���ĵ�ID");
            //System.out.println("����ֵ��"+Integer.parseInt(request.getParameter("card_id")));
            //System.out.println("��������:INTEGER");
		}

		if(sub_method.equals("delete")){
			para_index++;
            cstmt.setInt(para_index,sessionInfo.getOperatorID()); 
            //System.out.println("��������:����ԱID");
            //System.out.println("����ֵ��"+sessionInfo.getOperatorID());
            //System.out.println("��������:INTEGER");
		}
					
        cstmt.registerOutParameter(1,java.sql.Types.INTEGER);
        cstmt.execute();
        int re=cstmt.getShort(1);
        if(re<0)
        {
            KException ke=new KException(re);
            message("δ���","�������:"+ke.getErrorCode()+"<br>��������:"+ke.getMessage(),request, response);
        }
        else
        {
			//����
			if(sub_method.equals("copy") && doc_type==1510){
				success("���","��ɼ�Ŀ���Ƽ�¼����.<br><a href=\"../app/pricelistdetail?doc_type=1510&doc_id="+Integer.parseInt(request.getParameter("parent_doc_id"))+"\">����.</a>",request, response);
				return;
			}

			if(sub_method.equals("release") && doc_type==1510){
				success("���","��ɼ�Ŀ��������.<br><a href=\"../app/pricelistdetail?doc_type=1510&doc_id="+Integer.parseInt(request.getParameter("card_id"))+"\">����.</a>",request, response);
				return;
			}

			if(sub_method.equals("delete") && doc_type==1510){
				 success("���","��ɼ�Ŀ��ɾ������.<br><a href=\"../app/pricelistdetail?doc_type=1510&doc_id="+Integer.parseInt(request.getParameter("card_id"))+"\">����.</a>",request, response);
				return;
			}

			if(sub_method.equals("pause") && doc_type==1510){
				success("���","��ɼ�Ŀ����ֹ����.<br><a href=\"../app/pricelistdetail?doc_type=1510&doc_id="+Integer.parseInt(request.getParameter("card_id"))+"\">����.</a>",request, response);
				return;
			}

							 //��ҳ
			if(sub_method.equals("copy") && doc_type==1660){
				success("���","��ɼ�Ŀ���Ƽ�¼����.<br><a href=\"../app/pricelistdetail?doc_type=1660&doc_id="+Integer.parseInt(request.getParameter("parent_doc_id"))+"\">����.</a>",request, response);
				return;
			}

			if(sub_method.equals("release") && doc_type==1660){
				success("���","��ɼ�Ŀ��������.<br><a href=\"../app/pricelistdetail?doc_type=1660&doc_id="+Integer.parseInt(request.getParameter("card_id"))+"\">����.</a>",request, response);
				 return;
			}

             if(sub_method.equals("delete") && doc_type==1660){
                 success("���","��ɼ�Ŀ��ɾ������.<br><a href=\"../app/pricelistdetail?doc_type=1660&doc_id="+Integer.parseInt(request.getParameter("card_id"))+"\">����.</a>",request, response);
                 return;
             }
    
             if(sub_method.equals("pause") && doc_type==1660){
                 success("���","��ɼ�Ŀ����ֹ����.<br><a href=\"../app/pricelistdetail?doc_type=1660&doc_id="+Integer.parseInt(request.getParameter("card_id"))+"\">����.</a>",request, response);
                 return;
             }
    
             //Ŀ¼
             if(sub_method.equals("copy") && doc_type==1680){
                 success("���","���Ŀ¼���Ƽ�¼����.<br><a href=\"../app/catalogdetail?doc_type=1680&doc_id="+Integer.parseInt(request.getParameter("parent_doc_id"))+"\">����.</a>",request, response);
                 return;
             }
    
             if(sub_method.equals("release") && doc_type==1680){
                 success("���","���Ŀ¼��������.<br><a href=\"../app/catalogdetail?doc_type=1680&doc_id="+Integer.parseInt(request.getParameter("card_id"))+"\">����.</a>",request, response);
                 return;
             }
    
             if(sub_method.equals("delete") && doc_type==1680){
                 success("���","���Ŀ¼��ɾ������.<br><a href=\"../app/catalogdetail?doc_type=1680&doc_id="+Integer.parseInt(request.getParameter("card_id"))+"\">����.</a>",request, response);
                 return;
             }
    
             if(sub_method.equals("pause") && doc_type==1680){
                 success("���","���Ŀ¼����ֹ����.<br><a href=\"../app/catalogdetail?doc_type=1680&doc_id="+Integer.parseInt(request.getParameter("card_id"))+"\">����.</a>",request, response);
                 return;
             }

            success("���","���������¼����.<br><a href=\"../app/viewconfig?doc_type="+doc_type+"\">����.</a>",request, response);
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