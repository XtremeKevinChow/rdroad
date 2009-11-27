package com.magic.app;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
import com.magic.utils.*;
import java.sql.*;
/**
 * 订单取消、修改Servelet
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
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
      message("未完成",e.getMessage(),request, response);
      return;
    }
     
     //取消定单行 
     if(action.equals("cancels")){
         cancelOrderLine(doc_id,request,response,sessionInfo);
     }
     //取消定单
     if(action.equals("ordercancel")){
        cancelOrder(doc_id,request,response,sessionInfo);
     }
     //定单修改
     if(action.equals("order")){
        changeOrder(doc_id,request,response,sessionInfo);
     }
     
     //团体会员修改
     if(action.equals("orgorder")){
         changeOrgOrder(doc_id,request,response,sessionInfo);
     }
    
     //团体会员订单取消
     if(action.equals("orgordercancel")){       
         cancelOrgOrder(doc_id,request,response,sessionInfo);
     }
     
     //取消定单修改
     if(action.equals("cancelupdate") || action.equals("canceledit")){
        cancelChangeOrder(doc_id,request,response,sessionInfo,action);
        }
  }
  /**
   * 取消定单行
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
            message("未完成","没有有效的输入",request, response);
            return;            
        }
		
        if(item_id == 100000 || item_id == 100001 || item_id==100002 || item_id==100003)
        {
			message("未完成","卡不能取消!",request, response);
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
			success("完成","完成订单行取消操作.<br><a href=\"../app/viewdetail?doc_type=4000&doc_id="+parent_doc_id+"\">返回.</a>",request, response);
			return;
        }catch(KException ke)
        {
            message("未完成","错误代码:"+ke.getErrorCode()+"<br>错误描述:"+ke.getMessage(),request, response);
        }catch(Exception e)
        {
            message("未完成","描述:"+e.getMessage(),request,response);
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
     * 取消定单
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
            cstmt.setString(4,"手工取消订单"); 
            cstmt.setInt(5,0); 
            cstmt.registerOutParameter(1,java.sql.Types.INTEGER);
            cstmt.execute();
            int re=cstmt.getShort(1);
            if(re<0) throw new KException(re);
			success("完成","完成订单取消操作.<br><a href=\"../app/viewdetail?doc_type=4000&doc_id="+doc_id+"\">返回.</a>",request, response);
        }catch(KException ke)
        {
            message("未完成","错误代码:"+ke.getErrorCode()+"<br>错误描述:"+ke.getMessage(),request, response);
        }catch(Exception e)
        {
            message("未完成","描述:"+e.getMessage(),request,response);
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
     * 开始更改定单
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
                message("未完成","取消或已发货的订单不能修改",request, response);
                return;
            }
            
            sql="update ord_headers set old_status=status,status = -6 where id="+doc_id +" and status>=0 and status<30";
            dblink.executeUpdate(sql); 
        
            //订单信息加入购物车
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
                message("未完成","订单修改失败.<br><a href=\"../app/viewdetail?doc_type=4000&doc_id="+doc_id+"\">返回.</a>",request, response);             
            }
        }catch(KException ke)
        {
            message("未完成","错误代码:"+ke.getErrorCode()+"<br>错误描述:"+ke.getMessage(),request, response);
        }catch(Exception e)
        {
            message("未完成","描述:"+e.getMessage(),request,response);
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
     * 开始更改团体定单
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
                message("未完成","取消或已发货的订单不能修改",request, response);
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
                message("未完成","订单修改失败.<br><a href=\"../app/viewdetail?doc_type=4001&doc_id="+doc_id+"\">返回.</a>",request, response);
            } 
        }
        catch(KException ke)
        {
            message("未完成","错误代码:"+ke.getErrorCode()+"<br>错误描述:"+ke.getMessage(),request, response);
        }catch(Exception e)
        {
            message("未完成","描述:"+e.getMessage(),request,response);
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
     * 团体定单取消
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
			      cstmt.setString(4,"手工取消订单"); 
            cstmt.setInt(5,0); 
            cstmt.registerOutParameter(1,java.sql.Types.INTEGER);
            cstmt.execute();
            int re = cstmt.getInt(1);
            
            if(re<0) throw new KException(re);
            success("完成","完成订单取消操作.<br><a href=\"../app/viewdetail?doc_type=4100&doc_id="+doc_id+"\">返回.</a>",request, response);               
        }catch(KException ke) {
            message("未完成","错误代码:"+ke.getErrorCode()+"<br>错误描述:"+ke.getMessage(),request, response);
        }catch(Exception e){
             message("未完成","描述:"+e.getMessage(),request,response);
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
     * 取消修改定单
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
              message("未完成","订单状态不是修改状态，不能取消修改。",request, response);
              return;          
            }
            sql="update ord_headers set status = old_status where id="+doc_id;
            dblink.executeUpdate(sql);
        
            if(action.equals("cancelupdate")){
                success("完成","完成取消修改操作.<br><a href=\"../app/viewdetail?doc_type=4000&doc_id="+doc_id+"\">返回.</a>",request, response);
            }
            if(action.equals("canceledit")){
                success("完成","完成取消修改操作.<br><a href=\"../app/viewdetail?doc_type=4100&doc_id="+doc_id+"\">返回.</a>",request, response);       
            }
        }catch(Exception e)
        {
             message("未完成","描述:"+e.getMessage(),request,response);
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