<%@ page contentType="text/html;charset=GBK" import="com.magic.crm.util.*"%>
<%@page import="com.magic.crm.member.entity.*"%>
<%@page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.sql.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager,com.magic.utils.Arith"%>
<%
	
	String card_id=request.getParameter("card_id");
				 card_id=(card_id==null)?"":card_id.trim();
	String fstatus=request.getParameter("status");
				 fstatus=(fstatus==null)?"N":fstatus.trim();
	
	String tag=request.getParameter("tag");
					 tag=(tag==null)?"":tag.trim();		

      Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      String condition="";
      String sql="";
%>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<SCRIPT LANGUAGE="JavaScript">
<!--


function query_f() {

if (document.form.card_id.value == "")
	{
		alert("��Ա�Ų���Ϊ��");
		document.form.card_id.focus();
		return;
	}

	document.form.submit();
}
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" >

<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td width="210%"><nobr>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">�ʻ�����</font><font color="838383"> 
      		-&gt; </font><font color="838383">��Ա���ʱ���</font><font color="838383"> </nobr>
      	</td>
      	<td align="right">
      		&nbsp;
      	</td>
   </tr>
</table>

<form name="form" method="POST" action="postage.jsp">		
		<table width="98%" align="center" border=0 cellspacing=1 cellpadding=5  class="OraTableRowHeader" noWrap >
			<tr height="22">
				<td width="80">��Ա�ţ�</td>
				<td  bgcolor="#FFFFFF"  >
					<input type="hidden" name="tag" value="1">
					<input type="text" size=10 name="card_id" value="<%=card_id%>" >
					<input type="radio" name="status" value='N' checked='true' <%if(fstatus.equals("N")){ %> checked <%}%>>δ���� 
					<input type="radio" name="status" value='Y' <%if(fstatus.equals("Y")){ %> checked <%}%> >�ѱ���
					&nbsp;&nbsp;
					<input type="button" name="btn_query" value=" ��ѯ " onclick="query_f();">	</td>
			</tr>			
	</table>
</form>
<%
if(tag.equals("1")){
%>
<form name="tempForm1" method="POST" action="postage_ok.jsp">		
	<table width="98%" align="center" border=0 cellspacing=1 cellpadding=5 >
		<tr height="22">
			<td class="OraTableRowHeader" noWrap  noWrap align=center width="6%" ><b>������</b></td>
			<td class="OraTableRowHeader" noWrap  noWrap align=center width="8%" ><b>������</b></td>
			<td class="OraTableRowHeader" noWrap  noWrap align=center width="8%" ><b>����</b></td>
			<td class="OraTableRowHeader" noWrap  noWrap align=center width="8%" ><b>��׼����</b></td>
			<td class="OraTableRowHeader" noWrap  noWrap align=center width="8%" ><b>����ԭ��</b></td>
			<td class="OraTableRowHeader" noWrap  noWrap align=center width="8%" ><b>�����</b></td>
			<td class="OraTableRowHeader" noWrap  noWrap align=center width="8%" ><b>�������</b></td>
			<td class="OraTableRowHeader" noWrap  noWrap align=center width="8%" ><b>����</b></td>
			<td class="OraTableRowHeader" noWrap  noWrap align=center width="16%" ><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��ע&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b></td>
			<td class="OraTableRowHeader" noWrap  noWrap align=center  width="6%"><b>����</b></td>
		</tr>
<%
		try{
		 conn = DBManager.getConnection();
			sql=" select a.*,c.rr_desc from jxc.sto_return_postage a inner join mbr_members b ";
			sql+=" on a.mbrid=b.id ";
			sql+=" inner join jxc.return_reasons c on c.rr_no=a.rr_no ";
			sql+=" where b.card_id=? and a.status = ?"	;
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,card_id);
			pstmt.setString(2,fstatus);
			//out.println(sql+card_id+"***"+fstatus);
			rs=pstmt.executeQuery();	

			while(rs.next()){	
			 String rp_id=rs.getString("rp_id");
			 String return_type=rs.getString("return_type");
			 String sn_code=rs.getString("sn_code");
			 String sn_id=rs.getString("sn_id");
			 String postage=rs.getString("postage");
			 String post_num=rs.getString("post_num");
			 String mbrid=rs.getString("mbrid");
			 String bz=rs.getString("bz");
			        bz=(bz==null)?"":bz;
			 String create_date=rs.getString("create_date");
			 String status=rs.getString("status");
			 String apply_date=rs.getString("apply_date");
			        apply_date=(apply_date==null)?"":apply_date.substring(1,10);
			        
			 String apply_operator=rs.getString("apply_operator");
			        apply_operator=(apply_operator==null)?"":apply_operator;
			 
			 String res_no=rs.getString("res_no");
			 String rr_no=rs.getString("rr_no");
			 String rr_desc=rs.getString("rr_desc");
			 String fact_postage=rs.getString("fact_postage"); 
       double money=Double.parseDouble(fact_postage);			 
%>

			<tr height="22">
				<td  class=OraTableCellText><a href="../order/snView.do?sn_id=<%=sn_id%>" ><%=sn_code%></a></td>
				<td  class=OraTableCellText ><%=post_num%></td>
				<td class=OraTableCellText align="right"><%=postage%></td>
				<td  class=OraTableCellText align="right">
				<%if(status.equals("N")){%>
					<input type="text" size=6 name="fact_postage" value="<%=fact_postage%>"></text>
				<%}else{%>
				<font color="red"><%=fact_postage%></font>
				<%}%>				
				
				</td>				
				
				<td  class=OraTableCellText><%=rr_desc%></td>
				<td class=OraTableCellText ><%=apply_operator%></td>
				<td  class=OraTableCellText ><%=apply_date%></td>				
				<td class=OraTableCellText >
				<%
				if(return_type.equals("0")){
				   out.println("�����˻�");
				}
				if(return_type.equals("1")){
				   out.println("����");
				}
				if(return_type.equals("2")){
				   out.println("ȫ��");
				}								
				%>
				</td>
				<td  class=OraTableCellText ><%=bz%>&nbsp;</td>
				<td  align="center"  class=OraTableCellText>
				<%if(status.equals("N")){%>
					<input type="submit" name="btn_query" value=" ȷ�� " >	
				<%}else{%>
				<font color="red"><nobr>�ѱ���</nobr></font>
				<%}%>
				  <input type="hidden" name="sn_code" value="<%=sn_code%>">
				  <input type="hidden" name="res_no" value="<%=res_no%>">
				  <input type="hidden" name="mbrid" value="<%=mbrid%>">
				  <input type="hidden" name="card_id" value="<%=card_id%>">
				  <input type="hidden" name="rp_id" value="<%=rp_id%>">
				</td>				
			</tr>	
			<%
}//while
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
		 
}//tag
%>	
		</table>
	</form>


</body>
</html>
