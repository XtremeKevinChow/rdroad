<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager,com.magic.crm.user.entity.*"%>
<%
String pid=request.getParameter("pid");
String id=request.getParameter("id");
Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      Statement stmt = null;
      String condition="";

String item_code="";
String c_price="";
int s_type=0;
try{
		 conn = DBManager.getConnection();
		 String sql="";	
		 sql="select b.item_code,a.* from  PRD_PRICELIST_LINES a,prd_items b where a.item_id=b.item_id and a.id ="+id;			 
			 
		 pstmt=conn.prepareStatement(sql);
		 rs=pstmt.executeQuery();
		 if(rs.next()){ 
	         	item_code=rs.getString("item_code");
	         	c_price=rs.getString("common_price");
	         	s_type=rs.getInt("sell_type");
	         	
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
%>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../crmjsp/go_top.js"></script>
<script language="javascript" src="../script/default.js"></script>
<script language="javascript">
function getProduct(){
	openWin("../product/productQuery.do?actn=selectProduct&ifproduct=1&isreport=1","2005",700,400);
}
function f_checkData() {
        if(document.form1.itemCode.value==""){
           alert("����д����");
           document.form1.itemCode.select();	
           return false;
        }
        if(document.form1.sell_type.value==""){
           alert("����д���۷�ʽ");
           document.form1.sell_type.select();	
           return false;
        }        
	if(isNaN(document.form1.common_price.value)||document.form1.common_price.value==""){
	alert('�����۸�ֻ��Ϊ����!');
	document.form1.common_price.select();
	return false;
	}
	             	    
		
 	document.form1.input.disabled = true;
}
</script>

</head>
<body bgcolor="#FFFFFF" text="#000000">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="21">&nbsp;</td>
    <td> <font color="#838383"><b>��ǰλ��</b> : �г����� -&gt; �޸���ļ���Ʒ</font></td>
  </tr>
</table>
<br>
<form name="form1" action="msc_active_gift_update_ok.jsp" method="post" onsubmit="return f_checkData();">
 <table width="95%" border=0 cellspacing=1 cellpadding=1  align="center" >
  
 <tr> 
    <td class="OraTableRowHeader" noWrap  >����<font color=red>*</font></td>
    <td><%=item_code%>
    <!--
<input type="hidden" id="itemID" name="itemID" value=""><input id="itemCode" name="itemCode" value="<%=item_code%>" readonly>
				<a href="javascript:getProduct();">
     				<img src="../images/icon_lookup.gif" name ="findcate" width="22" height="23" border=0 align="top">
     			</a>
    -->
    </td>
 </tr>  

 <tr> 
    <td class="OraTableRowHeader" noWrap >���۷�ʽ<font color=red>*</font></td>
    <td>
	<select name="sell_type" > 
	<option value="" >��ѡ��...</option>
	<option value="3" <%if(s_type==3){%>selected<%}%> >��������</option>
	<option value="7" <%if(s_type==7){%>selected<%}%> >ע������</option>
	<option value="2" <%if(s_type==2){%>selected<%}%> >��������</option>
	<option value="0" <%if(s_type==0){%>selected<%}%> >��������</option>
	<option value="14" <%if(s_type==14){%>selected<%}%> >����</option>
	<option value="1" <%if(s_type==1){%>selected<%}%> >�ֿ�����</option>
	<option value="6" <%if(s_type==6){%>selected<%}%> >���ֻ���</option>
	<option value="10" <%if(s_type==10){%>selected<%}%> >�ιο�</option>
	<option value="5" <%if(s_type==5){%>selected<%}%> >��������Ʒ</option>
	<option value="13" <%if(s_type==13){%>selected<%}%> >�˹�����Ʒ</option>
	<option value="9" <%if(s_type==9){%>selected<%}%> >���ϻ��Ʒ</option>
	<option value="11" <%if(s_type==11){%>selected<%}%> >��͸����</option>
	<option value="4" <%if(s_type==4){%>selected<%}%> >��Ʒ��Ʒ</option>
	<option value="8" <%if(s_type==8){%>selected<%}%> >��Ա��</option>
	<option value="12" <%if(s_type==12){%>selected<%}%> >ת����Ʒ</option>
</select>      
    </td>
 </tr>

  <tr> 
    <td class="OraTableRowHeader" noWrap  >������<font color=red>*</font></td>
    <td><input name="common_price" value="<%=c_price%>"></td>
 </tr> 
  <tr align="center" valign="middle"> 
    <td height="42" colspan=2> 
      <input  type="submit" name="input" class="button2" value=" �� �� " > 
      
  </tr> 
</table>
<input type="hidden" name="pid" value="<%=pid%>">
<input type="hidden" name="id" value="<%=id%>">
<input type="hidden" name="itemCode" value="<%=item_code%>">
</form>



</body>
</html>

