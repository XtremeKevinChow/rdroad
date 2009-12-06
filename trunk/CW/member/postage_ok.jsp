<%@ page contentType="text/html;charset=GBK" import="com.magic.crm.util.*"%>
<%@page import="com.magic.crm.member.entity.*,com.magic.crm.user.entity.*"%>
<%@page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.sql.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager,com.magic.utils.Arith"%>
<%

User user=new User();
user = (User)session.getAttribute("user");

			String card_id=request.getParameter("card_id");
						 card_id=(card_id==null)?"":card_id.trim();
						 
			String rp_id=request.getParameter("rp_id");		
			String sn_code=request.getParameter("sn_code");
						 sn_code=(sn_code==null)?"":sn_code.trim();
			String res_no=request.getParameter("res_no");
						 res_no=(res_no==null)?"":res_no.trim();		
			String mbrid=request.getParameter("mbrid");
	 		String fact_postage=request.getParameter("fact_postage");
						 fact_postage=(fact_postage==null)?"0":fact_postage.trim();								 
		   double money=Double.parseDouble(fact_postage);
	    String comments="发货单 ："+sn_code+"退货入库单："+res_no;
		   	 
      Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      PreparedStatement pstmt1=null;
      String condition="";
      String sql="";
      CallableStatement cstmt = null;
      String sp = null;
      int re = 0;  

		try{
		 conn = DBManager.getConnection();
		  conn.setAutoCommit(false);
	sp = "{?=call member.f_member_add_money(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		cstmt = conn.prepareCall(sp);
		cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
		cstmt.setString(2, "");
		cstmt.setString(3, card_id);
		cstmt.setDouble(4, money);
		cstmt.setInt(5, 12);
		cstmt.setString(6, "");
		cstmt.setInt(7, Integer.parseInt(user.getId()));
		cstmt.setString(8, "邮资报销");
		cstmt.setInt(9, 0);
		cstmt.setInt(10, 0);
		cstmt.setInt(11, 0);
		cstmt.setString(12, "");
		cstmt.setInt(13, 0);
		cstmt.setInt(14, 14);       
 	  
	cstmt.execute();
	re = cstmt.getInt(1); 
			  /*
			sql="UPDATE mbr_members SET deposit="+money+"+deposit WHERE card_id = '"+card_id+"'";
      pstmt = conn.prepareStatement(sql);
		  pstmt.execute();	
		  		
      String sql1="INSERT INTO mbr_money_history ";
      sql1+="(ID, member_id,money_update,deposit, event_type, pay_method, comments)";
      sql1+="VALUES (seq_mbr_money_history.NEXTVAL,"+mbrid+", "+money+",";
      sql1+= "(select deposit from mbr_members where id="+mbrid+" ),2020, 12,'"+comments+"')";
    
			pstmt1=conn.prepareStatement(sql1);
			pstmt1.execute();		
			*/
			sql="UPDATE jxc.sto_return_postage SET fact_postage="+money+",apply_operator='"+user.getNAME()+"',status='Y',apply_date=sysdate WHERE rp_id = "+rp_id;
      pstmt = conn.prepareStatement(sql);
		  pstmt.execute();			
			 
			 conn.commit();

		} catch(Exception se) {

			se.printStackTrace();
	
		 } finally {
	
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {}
			if (pstmt1 != null)
				try {
					pstmt1.close();
				} catch (Exception e) {}				
			 try {
				 conn.close();
			 	} catch(SQLException sqe) {}
	
		 }
	
		 response.sendRedirect("postage.jsp?tag=1&card_id="+card_id);

%>	

