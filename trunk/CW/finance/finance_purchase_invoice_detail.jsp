<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.sql.*,com.magic.utils.Arith"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--

//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000">

<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">财务管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">采购发票详情</font><font color="838383"> 
      	</td>
   </tr>
</table>

<br>
<table id="main" width="95%" align="center" cellspacing="1" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >  
  <tr height="26"> 

    <td>发票号：</td>
	<td bgcolor="#FFFFFF">
		<bean:write name="finPurchaseInvoiceForm" property="factAPCode"/>
    </td>
    <td>发票类型：</td>
	<td bgcolor="#FFFFFF">
		<logic:equal name="finPurchaseInvoiceForm" property="apType" value="1">蓝票</logic:equal>
		<logic:equal name="finPurchaseInvoiceForm" property="apType" value="2">红票</logic:equal>

    </td>	
  </tr>
  <tr height="26"> 
	<td>供应商代码：</td><td bgcolor="#FFFFFF"><bean:write name="finPurchaseInvoiceForm" property="proNO"/></td>
	<td>供应商名称：</td><td bgcolor="#FFFFFF"><bean:write name="finPurchaseInvoiceForm" property="proName"/></td>
	</tr>
	<tr height="26"> 
	<td>制单人：</td><td bgcolor="#FFFFFF"><bean:write name="finPurchaseInvoiceForm" property="creator"/></td>
	<td>单据日期：</td><td bgcolor="#FFFFFF"><bean:write name="finPurchaseInvoiceForm" property="createDate"/></td>
  </tr>
  	<tr height="26"> 
	<td>审核人：</td><td bgcolor="#FFFFFF"><bean:write name="finPurchaseInvoiceForm" property="checkPerson"/></td>
	<td>审核日期：</td><td bgcolor="#FFFFFF"><bean:write name="finPurchaseInvoiceForm" property="checkDate"/></td>
  </tr>
  	<tr height="26"> 
	<td>记帐人：</td><td bgcolor="#FFFFFF"><bean:write name="finPurchaseInvoiceForm" property="tallier"/></td>
	<td>记帐日期：</td><td bgcolor="#FFFFFF"><bean:write name="finPurchaseInvoiceForm" property="tallyDate"/></td>
  </tr>  
   	<tr height="26"> 
	<td>金额：</td><td bgcolor="#FFFFFF"><bean:write name="finPurchaseInvoiceForm" property="amt"/></td>
	<td>发票日期：</td><td bgcolor="#FFFFFF"><bean:write name="finPurchaseInvoiceForm" property="invoiceDate"/></td>
  </tr>  
  <tr height="26" > 

	<td>单据状态：</td>
	<td bgcolor="#FFFFFF" colspan="3" >
		<logic:equal name="finPurchaseInvoiceForm" property="status" value="1">新建</logic:equal>
		<logic:equal name="finPurchaseInvoiceForm" property="status" value="2">审核</logic:equal>
		<logic:equal name="finPurchaseInvoiceForm" property="status" value="3">记帐</logic:equal>
	</td>

</table>
<br>
<bean:define id="list" name="finPurchaseInvoiceForm" property="invoiceDetail"/>
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5>
	<tr>
		<th width="55"  class="OraTableRowHeader" noWrap  noWrap align=middle>产品代码</th>
		<th width="150"  class="OraTableRowHeader" noWrap  noWrap align=middle>产品名称</th>
		<th width="55"  class="OraTableRowHeader" noWrap  noWrap align=middle>本次数量</th>
		<th width="55"  class="OraTableRowHeader" noWrap  noWrap align=middle>预算单价</th>
		<th width="55"  class="OraTableRowHeader" noWrap  noWrap align=middle>预算成本</th>
		<th width="55"  class="OraTableRowHeader" noWrap  noWrap align=middle>记帐单价</th>
		<th width="55"  class="OraTableRowHeader" noWrap  noWrap align=middle>记帐成本</th>
		<th width="30"  class="OraTableRowHeader" noWrap  noWrap align=middle>税率</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>税额</th>
		<th width="55"  class="OraTableRowHeader" noWrap  noWrap align=middle>价税合计</th>
		<th width="55"  class="OraTableRowHeader" noWrap  noWrap align=middle>成本差异</th>

	</tr>
<!--
	<logic:iterate id="list" name="list">
	<tr>
		<td class=OraTableCellText noWrap align=middle ><bean:write name="list" property="itemCode"/></td>
		<td class=OraTableCellText noWrap align=left ><bean:write name="list" property="itemName"/></td>
		<td class=OraTableCellText noWrap align=right ><bean:write name="list" property="qty" format="#0"/></td>
		<td class=OraTableCellText noWrap align=right ><bean:write name="list" property="purPrice" format="#0.00"/></td>
    <td class=OraTableCellText noWrap align=right ><bean:write name="list" property="budget_Cost" format="#0.00"/></td>
    
		<td class=OraTableCellText noWrap align=right ><bean:write name="list" property="apPrice" format="#0.00"/></td>
		<td class=OraTableCellText noWrap align=right ><bean:write name="list" property="amt" format="#0.00"/></td>
		<td class=OraTableCellText noWrap align=right ><bean:write name="list" property="tax" format="#0.00"/></td>
		<td class=OraTableCellText noWrap align=right ><bean:write name="list" property="taxAmt" format="#0.00"/></td>
		<td class=OraTableCellText noWrap align=right ><bean:write name="list" property="totalAmt" format="#0.00"/></td>
		<td class=OraTableCellText noWrap align=right >-<bean:write name="list" property="disAmt" format="#0.00"/></td>
	</tr>
	</logic:iterate>
	<tr>
		<td class=OraTableCellText colspan="5" align=left ><B>合计：</B></td>
		<td class=OraTableCellText colspan="1" align=right ></td>
		<td class=OraTableCellText colspan="1" align=right ><bean:write name="finPurchaseInvoiceForm" property="amtAll" format="#0.00"/></td>
		<td class=OraTableCellText colspan="1" align=right ></td>
		<td class=OraTableCellText colspan="1" align=right ><bean:write name="finPurchaseInvoiceForm" property="taxAmtAll" format="#0.00"/></td>
		<td class=OraTableCellText colspan="1" align=right ><bean:write name="finPurchaseInvoiceForm" property="amt" format="#0.00"/></td>
		<td class=OraTableCellText colspan="1" align=right >-<bean:write name="finPurchaseInvoiceForm" property="disAmtAll" format="#0.00"/></td>
	</tr>
	-->
<%
      Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      String condition="";
      String sql="";

			String apID=request.getParameter("apID");
		try{
		 conn = DBManager.getConnection();

			            sql = "select distinct b.item_type "
			                    + "from fin_ap_dtl a inner join prd_items b on a.item_id = b.item_id "
			                    + "where a.ap_id ="+apID;		 
				        pstmt = conn.prepareStatement(sql);
			          ResultSet rs1 = pstmt.executeQuery();
			        double final_purPrice_all=0;
		        	double final_apPrice_all=0;
			        double final_taxAmtAll_all=0;			          
							double final_amt_all=0;		
							double final_disAmt_all=0;	
							while(rs1.next()){ 
							String item_type=rs1.getString("item_type");
%>
	<tr>
	  <td class=OraTableCellText noWrap align=left colspan="11">
	  <%
	    if(item_type.equals("1")){
	       out.println("图书");
	    }
	    if(item_type.equals("2")){
	       out.println("影视");
	    }
	    if(item_type.equals("3")){
	       out.println("音乐");
	    }
	    if(item_type.equals("4")){
	       out.println("游戏/软件");
	    }
	    if(item_type.equals("5")){
	       out.println("礼品");
	    }
	    if(item_type.equals("6")){
	       out.println("其他");
	    }
    	    	    	    	    	    
	  %>
	  </td>
	</tr>


				    <%
			            sql = "select a.*, b.item_code, b.name as item_name, (a. qty + c.use_qty) as use_qty "
			                    + "from fin_ap_dtl a inner join prd_items b on a.item_id = b.item_id "
			                    + "inner join fin_ps_dtl c on a.ps_dtl_id = c.ps_dtl_id "
			                    + "where a.ap_id ="+apID+" and b.item_type="+item_type;		 
				        pstmt = conn.prepareStatement(sql);
			          rs = pstmt.executeQuery();
			        double purPrice_all=0;
		        	double apPrice_all=0;
			        double taxAmtAll_all=0;
			        double amt_all=0;
			        double disAmt_all=0;			          
							while(rs.next()){ 
			        String item_code=rs.getString("item_code");
			        String item_name=rs.getString("item_name");
			        int qty=rs.getInt("qty");
			        double purPrice=rs.getDouble("pur_Price");
			        double apPrice=rs.getDouble("ap_Price");
			        double amt=rs.getDouble("amt");
			        double tax=rs.getDouble("tax");
			        
			        double totalAmt=rs.getDouble("total_Amt");
			        double disAmt=rs.getDouble("dis_Amt");
			        //double taxAmt=Arith.round(tax*amt,2);
					double taxAmt=Arith.round(totalAmt-amt,2);
			        
			        purPrice_all=purPrice_all+purPrice*qty;
			        apPrice_all=apPrice_all+amt;
			        taxAmtAll_all=taxAmtAll_all+taxAmt;
			        amt_all=amt_all+totalAmt;
			        disAmt_all=disAmt_all+disAmt;
					//预算成本
					double purAmt = Arith.round(purPrice*qty, 2);
			        	    
				    %>
				      <tr>
								<td width="" class=OraTableCellText noWrap align=left ><%=item_code%></td>
								<td width="" class=OraTableCellText noWrap align=left ><%=item_name%></td>
								<td width="" class=OraTableCellText noWrap align=right ><%=qty%></td>
								<td width="" class=OraTableCellText noWrap align=right ><%=purPrice%></td>
						    <td width="" class=OraTableCellText noWrap align=right ><%=purAmt%></td>
						    
								<td width="" class=OraTableCellText noWrap align=right ><%=apPrice%></td>
								<td width="" class=OraTableCellText noWrap align=right ><%=amt%></td>
								<td width="" class=OraTableCellText noWrap align=right ><%=tax%></td>
								<td width="" class=OraTableCellText noWrap align=right ><%=taxAmt%></td>
								<td width="" class=OraTableCellText noWrap align=right ><%=totalAmt%></td>
								<td width="" class=OraTableCellText noWrap align=right ><%=-disAmt%></td>	      
				      </tr>
				      <%
				      }

			        final_purPrice_all=final_purPrice_all+purPrice_all;	
			        final_apPrice_all	=final_apPrice_all+apPrice_all;	
			        final_taxAmtAll_all	=final_taxAmtAll_all+taxAmtAll_all;      
							final_amt_all	=	final_amt_all+amt_all;
							final_disAmt_all=final_disAmt_all+disAmt_all;				      
				      %>
						<tr>
							<td class=OraTableCellText colspan="4" align=left ><B>小计：</B></td>
							<td class=OraTableCellText colspan="1" align=right ><%=Arith.round(purPrice_all,2)%></td>
							<td class=OraTableCellText colspan="1" align=right ></td>
							<td class=OraTableCellText colspan="1" align=right ><%=Arith.round(apPrice_all,2)%></td>
							<td class=OraTableCellText colspan="1" align=right ></td>
							<td class=OraTableCellText colspan="1" align=right ><%=Arith.round(taxAmtAll_all,2)%></td>
							<td class=OraTableCellText colspan="1" align=right ><%=Arith.round(amt_all,2)%></td>
							<td class=OraTableCellText colspan="1" align=right ><%=-Arith.round(disAmt_all,2)%></td>
						</tr>

<%		
  }//while item_type
%>
						<tr>
							<td class=OraTableCellText colspan="4" align=left ><B>总计：</B></td>
							<td class=OraTableCellText colspan="1" align=right ><%=Arith.round(final_purPrice_all,2)%></td>
							<td class=OraTableCellText colspan="1" align=right ></td>
							<td class=OraTableCellText colspan="1" align=right ><%=Arith.round(final_apPrice_all,2)%></td>
							<td class=OraTableCellText colspan="1" align=right ></td>
							<td class=OraTableCellText colspan="1" align=right ><%=Arith.round(final_taxAmtAll_all,2)%></td>
							<td class=OraTableCellText colspan="1" align=right ><%=Arith.round(final_amt_all,2)%></td>
							<td class=OraTableCellText colspan="1" align=right ><%=-Arith.round(final_disAmt_all,2)%></td>
						</tr>
<%
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
</table>
<table  align="center">
	<tr>
		<td   align="center"><input type="button" value=" 返回 " onclick="history.go(-1)"></td>
		<td   align="center">&nbsp;<a href="finance_purchase_invoice_detail_excel.jsp?apID=<%=apID%>" target=_blank >生成EXCEL</a></td>
	</tr>
</table>

</body>
</html>
