<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*,java.text.SimpleDateFormat"%>
<%@page import="java.io.*,java.sql.*,java.util.Date"%>
<%@ page import="com.magic.crm.user.entity.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<%
User user=new User();
user = (User)session.getAttribute("user");
String clubid=request.getParameter("clubid");
String id=request.getParameter("id");
String create_date=request.getParameter("create_date");

SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
  Date d1 = null;
  Date d2 = null;
  d1=formatter.parse(create_date);
  d2=formatter.parse("2005-12-25"); 
 Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      Statement stmt = null;
      String condition="";


try{
	conn = DBManager.getConnection();
	
	if(clubid.equals("2")){

		if(d1.before(d2)){//2005-12-25ǰע��Ļ�Ա�����ֲ�ʱ���ܵõ���Ʒ,Ҫ����Ʒ�ݴ�����һ����Ʒ
		     String check_sql="select * from mbr_club where club_id=2 and member_id="+id;
		            pstmt = conn.prepareStatement(check_sql);
			    rs=pstmt.executeQuery();
			    if(!rs.next()){  		            
				String sQuery = "INSERT INTO MBR_GET_AWARD( ID,member_id,item_id,price,status,";
				sQuery+=" OPERATOR_ID,type,ORDER_REQUIRE,clubid)values(seq_mbr_get_award_id.nextval,?,?,?,?,?,?,?,?)"; 
				pstmt = conn.prepareStatement(sQuery);
									        
				pstmt.setInt(1,Integer.parseInt(id));
				pstmt.setInt(2,118007);
				pstmt.setDouble(3,0);
				pstmt.setInt(4,0);
				pstmt.setInt(5,Integer.parseInt(user.getId()));
				pstmt.setInt(6,7);
				pstmt.setDouble(7,15);
				pstmt.setInt(8,Integer.parseInt(clubid));
				pstmt.execute();
			    }
		     
		}
    		String del_sql="delete from mbr_club where member_id=?";
		pstmt = conn.prepareStatement(del_sql);
		pstmt.setInt(1, Integer.parseInt(id));	
		pstmt.execute();
					
		String in_club="insert into mbr_club(member_id,club_id)values(?,?)";
		pstmt = conn.prepareStatement(in_club);
		pstmt.setInt(1, Integer.parseInt(id));	
		pstmt.setInt(2, Integer.parseInt(clubid));	
		pstmt.execute();			
				
					
	}


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
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {}				
			 try {
				 conn.close();
			 	} catch(SQLException sqe) {}
	
}
//response.sendRedirect("gift_add.jsp");
%>
<a href="javascript:history.back(-1)">������ϣ�����</a>
</body>
</html>

