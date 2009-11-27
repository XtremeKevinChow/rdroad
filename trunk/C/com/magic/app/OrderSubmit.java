package com.magic.app;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
import com.magic.utils.*;
import java.sql.*;
/**
 * ����ȡ�����޸�Servelet
 * @author magic
 * @Vender �Ϻ��������ϵͳ���޹�˾ (magic Software System Co.,ltd)
 */
public class OrderSubmit extends BaseServlet {
  public void init(ServletConfig config) throws ServletException{
    super.init(config);
  }
  public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
   
        request.setCharacterEncoding("GBK");
        super.service(request, response);
        if(!assertSession(request)){
          response.sendRedirect("/relogin.html");
          return;
        }
        HttpSession session=request.getSession();
        SessionInfo sessionInfo = new SessionInfo(
          ((Integer)session.getAttribute("login_company_id")).intValue(), 
          ((Integer)session.getAttribute("login_operator_id")).intValue(), 
          (java.util.HashMap)session.getAttribute("powermap"),
          request.getParameterMap());
     
     int doc_type = 0;
	 int doc_id = 0;
	 int parent_doc_id = 0 ;
	 int item_id = 0;
	   String action = null;
	   String sp = null;
    try{
      doc_type = Integer.parseInt(request.getParameter("doc_type"));
	  doc_id = Integer.parseInt(request.getParameter("doc_id"));
	  action =request.getParameter("action"); 
    }catch(Exception e){
      message("δ���",e.getMessage(),request, response);
      return;
    }
     
     //ȡ�������� 
     if(action.equals("cancels")){
         cancelOrderLine(doc_id,request,response,sessionInfo);
     }
     //ȡ������
     if(action.equals("ordercancel")){
        cancelOrder(doc_id,request,response,sessionInfo);
     }
     //�����޸�
     if(action.equals("order")){
        changeOrder(doc_id,request,response,sessionInfo);
     }
     
     //�����Ա�޸�
     if(action.equals("orgorder")){
         changeOrgOrder(doc_id,request,response,sessionInfo);
     }
    
     //�����Ա����ȡ��
     if(action.equals("orgordercancel")){       
         cancelOrgOrder(doc_id,request,response,sessionInfo);
     }
     
     //ȡ�������޸�
     if(action.equals("cancelupdate") || action.equals("canceledit")){
        cancelChangeOrder(doc_id,request,response,sessionInfo,action);
        }
  }
  /**
   * ȡ��������
   */
  void cancelOrderLine(int orderLineId, HttpServletRequest request, HttpServletResponse response,SessionInfo sessionInfo)
  {
       DBLink dblink=new DBLink();
       Statement stmt=null;
       ResultSet rs=null;
       CallableStatement cstmt = null;
       int parent_doc_id=0;
       int item_id=0;
       int doc_id=orderLineId;
       try{
            parent_doc_id = Integer.parseInt(request.getParameter("parent_doc_id"));
			item_id = Integer.parseInt(request.getParameter("item_id"));          
        }catch(Exception ex){
            message("δ���","û����Ч������",request, response);
            return;            
        }
		
        if(item_id == 100000 || item_id == 100001 || item_id==100002 || item_id==100003)
        {
			message("δ���","������ȡ��!",request, response);
            return;
		}
        
        try
        {
            String sp = "{?=call transaction.f_order_line_update(?,?)}";
            cstmt=dblink.prepareCall(sp); 
            cstmt.setInt(2,doc_id); 
            cstmt.setInt(3,sessionInfo.getOperatorID()); 
            cstmt.registerOutParameter(1,java.sql.Types.INTEGER);
            cstmt.execute();
            int re=cstmt.getInt(1);
            if(re<0)  throw new  KException(re);
           
            stmt=dblink.createStatement();
            
            rs=stmt.executeQuery("select nvl(mbr_award_id,0)  mbr_award_id from  ord_lines where id="+ doc_id);
            if(rs.next()){
              if(rs.getInt("mbr_award_id")>0){
                dblink.executeUpdate("update mbr_get_award set status=-10 where id="+rs.getInt("mbr_award_id"));; 
              }
            }
			success("���","��ɶ�����ȡ������.<br><a href=\"../app/viewdetail?doc_type=4000&doc_id="+parent_doc_id+"\">����.</a>",request, response);
			return;
        }catch(KException ke)
        {
            message("δ���","�������:"+ke.getErrorCode()+"<br>��������:"+ke.getMessage(),request, response);
        }catch(Exception e)
        {
            message("δ���","����:"+e.getMessage(),request,response);
        }finally
        {
            try
            {
                if(rs!=null) rs.close();
                if(stmt!=null) stmt.close();
                if(cstmt!=null) cstmt.close();
                dblink.close();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * ȡ������
     * @param orderId
     * @param request
     * @param response
     * @param sessionInfo
     */
    void cancelOrder(int orderId, HttpServletRequest request, HttpServletResponse response,SessionInfo sessionInfo)
    {
        DBLink dblink=new DBLink();
        CallableStatement cstmt = null;
        int doc_id=orderId;
        try
        {
            String sp = "{?=call transaction.f_order_cancel(?,?,?,?)}";
            cstmt = dblink.prepareCall(sp); 
            cstmt.setInt(2,doc_id); 
			cstmt.setInt(3,sessionInfo.getOperatorID()); 
            cstmt.setString(4,"�ֹ�ȡ������"); 
            cstmt.setInt(5,0); 
            cstmt.registerOutParameter(1,java.sql.Types.INTEGER);
            cstmt.execute();
            int re=cstmt.getShort(1);
            if(re<0) throw new KException(re);
			success("���","��ɶ���ȡ������.<br><a href=\"../app/viewdetail?doc_type=4000&doc_id="+doc_id+"\">����.</a>",request, response);
        }catch(KException ke)
        {
            message("δ���","�������:"+ke.getErrorCode()+"<br>��������:"+ke.getMessage(),request, response);
        }catch(Exception e)
        {
            message("δ���","����:"+e.getMessage(),request,response);
        }finally{
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
    /**
     * ��ʼ���Ķ���
     * @param orderId
     * @param request
     * @param response
     * @param sessionInfo
     */
    void changeOrder(int orderId, HttpServletRequest request, HttpServletResponse response,SessionInfo sessionInfo)
    {
        DBLink dblink=new DBLink();
        Statement stmt=null;
        ResultSet rs=null;
        CallableStatement cstmt = null;  
        int doc_id=orderId;
        int category_id = 0;
        try{
        
            String sql="select status,order_category from ord_headers where id="+doc_id ;
            stmt=dblink.createStatement();
            rs=stmt.executeQuery(sql);
            rs.next();
            category_id = rs.getInt("order_category");
            if(category_id!=0){
              category_id = 1;
            }
            if(rs.getInt("status")<0 || rs.getInt("status")>=30){
                message("δ���","ȡ�����ѷ����Ķ��������޸�",request, response);
                return;
            }
            
            sql="update ord_headers set old_status=status,status = -6 where id="+doc_id +" and status>=0 and status<30";
            dblink.executeUpdate(sql); 
        
            //������Ϣ���빺�ﳵ
            String sp = "{?=call transaction.f_order_to_cart(?,?)}";
            cstmt = dblink.prepareCall(sp);
            cstmt.setInt(2,doc_id);
            cstmt.setString(3,(String)request.getSession().getAttribute("session_id"));
            cstmt.registerOutParameter(1,java.sql.Types.INTEGER);
            cstmt.execute();
            int re=cstmt.getInt(1);
            if(re<0) throw new KException(re);
            
            stmt=dblink.createStatement();
            rs=stmt.executeQuery("select pr_type,buyer_id from ord_headers where id="+doc_id);
            if(rs.next()){
                response.sendRedirect("../crmjsp/member_order_prod_add.jsp?old_doc_id="+doc_id+"&doc_type="+DocType.ORDER+"&card_id="+rs.getInt("buyer_id")+"&pr_from="+rs.getInt("pr_type")+"&catagory_order="+category_id);
            } else {
                message("δ���","�����޸�ʧ��.<br><a href=\"../app/viewdetail?doc_type=4000&doc_id="+doc_id+"\">����.</a>",request, response);             
            }
        }catch(KException ke)
        {
            message("δ���","�������:"+ke.getErrorCode()+"<br>��������:"+ke.getMessage(),request, response);
        }catch(Exception e)
        {
            message("δ���","����:"+e.getMessage(),request,response);
        }finally{
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
    
    /**
     * ��ʼ�������嶨��
     * @param orderId
     * @param request
     * @param response
     * @param sessionInfo
     */
    void changeOrgOrder(int orderId, HttpServletRequest request, HttpServletResponse response,SessionInfo sessionInfo)
    {    
        DBLink dblink=new DBLink();
        Statement stmt=null;
        ResultSet rs=null;
        CallableStatement cstmt = null;
        int doc_id=orderId;
        
        try{
            String sql="select status from ord_headers where id="+doc_id ;
            stmt=dblink.createStatement();
            rs=stmt.executeQuery(sql);
            rs.next();
            if(rs.getInt("status")<0 || rs.getInt("status")>=30){
                message("δ���","ȡ�����ѷ����Ķ��������޸�",request, response);
                return;
            }
            
            sql="update ord_headers set old_status=status,status = -6 where id="+doc_id +" and status>=0 and status<30";
            dblink.executeUpdate(sql); 
            
            String sp = "{?=call transaction.f_order_to_cart(?,?)}";
     
            cstmt = dblink.prepareCall(sp);
            cstmt.setInt(2,doc_id);
            cstmt.setString(3,(String)request.getSession().getAttribute("session_id"));
            cstmt.registerOutParameter(1,java.sql.Types.INTEGER);
            cstmt.execute();
            int re=cstmt.getInt(1);
            if(re<0) throw new KException(re);
            
            rs=stmt.executeQuery("select pr_type,buyer_id from ord_headers where id="+doc_id);
            if(rs.next()){    
                response.sendRedirect("../crmjsp/member_group_order_add.jsp?old_doc_id="+doc_id+"&doc_type="+DocType.GROUP_ORDER+"&organization_id="+rs.getInt("buyer_id")+"&pr_from="+rs.getInt("pr_type"));         
            } else {
                message("δ���","�����޸�ʧ��.<br><a href=\"../app/viewdetail?doc_type=4001&doc_id="+doc_id+"\">����.</a>",request, response);
            } 
        }
        catch(KException ke)
        {
            message("δ���","�������:"+ke.getErrorCode()+"<br>��������:"+ke.getMessage(),request, response);
        }catch(Exception e)
        {
            message("δ���","����:"+e.getMessage(),request,response);
        }finally{
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
    
    /**
     * ���嶨��ȡ��
     * @param orderId
     * @param request
     * @param response
     * @param sessionInfo
     */
    void cancelOrgOrder(int orderId, HttpServletRequest request, HttpServletResponse response,SessionInfo sessionInfo)
    {
        DBLink dblink=new DBLink();
        CallableStatement cstmt = null;
        int doc_id=orderId;
        try{
            String sp = "{?=call transaction.F_ORG_ORDER_CANCEL(?,?,?,?)}";
            cstmt = dblink.prepareCall(sp); 
			      cstmt.setInt(2,doc_id); 
			      cstmt.setInt(3,sessionInfo.getOperatorID()); 
			      cstmt.setString(4,"�ֹ�ȡ������"); 
            cstmt.setInt(5,0); 
            cstmt.registerOutParameter(1,java.sql.Types.INTEGER);
            cstmt.execute();
            int re = cstmt.getInt(1);
            
            if(re<0) throw new KException(re);
            success("���","��ɶ���ȡ������.<br><a href=\"../app/viewdetail?doc_type=4100&doc_id="+doc_id+"\">����.</a>",request, response);               
        }catch(KException ke) {
            message("δ���","�������:"+ke.getErrorCode()+"<br>��������:"+ke.getMessage(),request, response);
        }catch(Exception e){
             message("δ���","����:"+e.getMessage(),request,response);
        }finally{
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
    /**
     * ȡ���޸Ķ���
     */
     void cancelChangeOrder(int orderId, HttpServletRequest request, HttpServletResponse response,SessionInfo sessionInfo,String action)
     {
        DBLink dblink=new DBLink();
        Statement stmt=null;
        ResultSet rs=null;
        CallableStatement cstmt = null;
        int doc_id=orderId;
        
        try
        {
            String sql="select status from ord_headers where id="+doc_id ;
            stmt=dblink.createStatement();
            rs=stmt.executeQuery(sql);
            rs.next(); 
            if(rs.getInt("status")!=-6){
              message("δ���","����״̬�����޸�״̬������ȡ���޸ġ�",request, response);
              return;          
            }
            sql="update ord_headers set status = old_status where id="+doc_id;
            dblink.executeUpdate(sql);
        
            if(action.equals("cancelupdate")){
                success("���","���ȡ���޸Ĳ���.<br><a href=\"../app/viewdetail?doc_type=4000&doc_id="+doc_id+"\">����.</a>",request, response);
            }
            if(action.equals("canceledit")){
                success("���","���ȡ���޸Ĳ���.<br><a href=\"../app/viewdetail?doc_type=4100&doc_id="+doc_id+"\">����.</a>",request, response);       
            }
        }catch(Exception e)
        {
             message("δ���","����:"+e.getMessage(),request,response);
        }finally{
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