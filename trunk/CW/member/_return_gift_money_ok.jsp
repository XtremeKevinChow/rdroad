<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.user.entity.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<%@ page import="com.magic.crm.member.entity.Member"%>
<%@ page import="com.magic.crm.user.entity.User"%>
<%@ page import="com.magic.crm.util.CallCenterHander"%>
<%
//查询条件
String ref_no=request.getParameter("ref_no");
ref_no = ChangeCoding.unescape(ref_no);
String member_name=request.getParameter("member_name");
member_name = ChangeCoding.unescape(member_name);


int id = Integer.parseInt(request.getParameter("id"));
double money = Double.parseDouble(request.getParameter("money"));

String ref_id = request.getParameter("ref_id");

User user = new User();
user = (User) session.getAttribute("user");
CallCenterHander hander = new CallCenterHander(request
							.getSession());

Member member = (Member) hander.getServicedMember();

int memberId = -1;
if (member != null) {
	memberId = member.getID();
}

Connection conn=null;
      
CallableStatement cstmt = null;
PreparedStatement pstmt = null;
ResultSet rs = null;
int ret = -1;  
boolean flg = false;
// member_id, amt, operator_id, ref_id
try{
		conn = DBManager.getConnection();

		//判断此汇款单是否已经送过礼金
		if (ref_id != null && ref_id.trim().length() > 0) {
			pstmt = conn.prepareStatement("select 1 from mbr_money_history where credence = ? and pay_method = 63");
			pstmt.setString(1, ref_id);
			rs = pstmt.executeQuery();
			if (rs.next()) { //此汇款单已经送过礼品
				flg = true;
			}
		}
		if (flg) {
			response.sendRedirect("_money_list.jsp?msg="+ret+"&ref_no="+ref_no+"&member_name="+member_name);
			return;
		}
		
		cstmt = conn.prepareCall("{? = call member.f_member_return_amt(?, ?, ?, ?)}");
	    cstmt.setInt(2, memberId);
		// 操作人员
		cstmt.setDouble(3, money);
		cstmt.setInt(4, Integer.parseInt(user.getId()));
		cstmt.setString(5, ref_id);
		cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
	
		cstmt.execute();

		
		ret = cstmt.getInt(1);           		                
	                 
		


} catch(Exception se) {

			se.printStackTrace();
	
		 } finally {		
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {}
			if (cstmt != null)
				try {
					cstmt.close();
				} catch (Exception e) {}
			if (conn != null)
			 try {
				 conn.close();
			 	} catch(SQLException sqe) {}
	
}
response.sendRedirect("_money_list.jsp?msg="+ret);
%>

