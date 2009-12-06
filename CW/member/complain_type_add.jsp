<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.sql.*"%>
<%@page import="java.text.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager,com.magic.utils.Arith"%>

<%
DecimalFormat myformat = new DecimalFormat("#");
      String tag=request.getParameter("tag");
      tag=(tag==null)?"":tag;
      String kind=request.getParameter("kind");
      kind=(kind==null)?"0":kind;
      String class_type=request.getParameter("class_type");
      class_type=(class_type==null)?"0":class_type;      
      String dept_id=request.getParameter("dept_id");
      dept_id=(dept_id==null)?"":dept_id;   
      String parent_id=request.getParameter("parent_id");
      parent_id=(parent_id==null)?"0":parent_id;       
      String type_name=request.getParameter("type_name");
      type_name=(type_name==null)?"":type_name;       
      Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      String condition="";
      String sql="";
      
		try{
		 conn = DBManager.getConnection();      

               
		sql="select SEQ_COMPLAINT_type_ID.Nextval from dual "; 
		pstmt=conn.prepareStatement(sql);
		rs=pstmt.executeQuery();	
		int cmpt_id=0;
		if(rs.next()){
		cmpt_id=rs.getInt(1);
		}               
		conn.setAutoCommit(false);
	       	sql=" insert into complaint_type (CMPT_TYPE_ID,PARENT_ID,CMPT_TYPE_NAME,TYPE,STATUS)values("+cmpt_id+", ";
		sql+=""+parent_id+",'"+type_name+"',"+kind+",0)";
		pstmt=conn.prepareStatement(sql);
		pstmt.executeUpdate();
		if(kind.equals("0")){//只有是投诉才会新增部门ID
			if(dept_id.equals("")){//新增小类时，系统自动新增部门ID
				sql="select dept_id from DEPT_COMPLAINT where cmpt_type_id="+parent_id; 
				pstmt=conn.prepareStatement(sql);
				rs=pstmt.executeQuery();	
				if(rs.next()){
				dept_id=rs.getString(1);
				}		
			
			}
	
		       	sql=" insert into DEPT_COMPLAINT (dept_id,CMPT_TYPE_ID)values("+dept_id+","+cmpt_id+")";
			pstmt=conn.prepareStatement(sql);
			pstmt.executeUpdate();	
			
		}
		conn.commit();	
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
 try {
	 conn.close();
 	} catch(SQLException sqe) {}

}

%>

<script>
    self.location="complain_type_set.jsp";
</script>