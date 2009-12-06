<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<%
String oldCardId=request.getParameter("oldCardId");
String newCardId=request.getParameter("newCardId");
if (oldCardId == null) {
	out.println("�Ͽ��Ų��ܿ�!");
	return;
}
if (newCardId == null) {
	out.println("�¿��Ų��ܿ�!");
	return;
}
if (oldCardId.equals(newCardId)) {
	out.println("���Ͽ��Ų���һ��!");
	return;
}
if (oldCardId.length() != 8 || newCardId.length() != 8) {
	out.println("���ű�����8λ��!");
	return;
}
try {
	Integer.parseInt(oldCardId);
	Integer.parseInt(newCardId);
}catch(Exception e) {
	
	out.println("���ű�����8λ����!");
	return;
}

Connection conn = null;
ResultSet rs = null;
ResultSet rs2 = null;
PreparedStatement pstmt = null;
PreparedStatement pstmt2 = null;
PreparedStatement pstmt3 = null;

try{
	conn = DBManager.getConnection();
	conn.setAutoCommit(false);
	String sql = "select card_id, old_card_code from mbr_members where card_id = ? and is_organization = 0 ";
	pstmt3 = conn.prepareStatement(sql);
	pstmt3.setString(1, newCardId);
	rs2  = pstmt3.executeQuery();
	if (rs2.next()) {
		out.println("�¿����Ѿ����ڣ���ȷ���¿���!");
		return;
	}
	
	pstmt = conn.prepareStatement(sql);
	pstmt.setString(1, oldCardId);
	rs = pstmt.executeQuery();
	if (rs.next()) {
		sql = "update mbr_members set old_card_code = ? , card_id = ? where card_id = ? ";
		pstmt2 = conn.prepareStatement(sql);
		pstmt2.setString(1, oldCardId);
		pstmt2.setString(2, newCardId);
		pstmt2.setString(3, oldCardId);
		int i = pstmt2.executeUpdate();
		if (i == 0) {
			out.println("δ���³ɹ��������ǻ�Ա�Ų�����!");
			conn.rollback();
			return;
		} else if ( i == 1) {
			out.println("���³ɹ�!");
			conn.commit();
			return;
		} else {
			out.println("���¼�¼����2�����Ϸ�!");
			conn.rollback();
			return;
		}
	} else {
		out.println("�ɿ��Ų�����!");
		conn.rollback();
		return;
	}
    


} catch(Exception se) {
		conn.rollback();
		se.printStackTrace();
	
} finally {
	if (rs != null)
		try {
			rs.close();
		} catch (Exception e) {
		}

	if (rs2 != null)
		try {
			rs2.close();
		} catch (Exception e) {}
	if (pstmt != null)
		try {
			pstmt.close();
		} catch (Exception e) {}
	if (pstmt2 != null)
		try {
			pstmt2.close();
		} catch (Exception e) {}
	if (pstmt3 != null)
		try {
			pstmt3.close();
		}catch(Exception e) {}
	if (conn != null)
		try {
			conn.close();
		} catch(SQLException sqe) {}
}

%>

