<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="com.magic.crm.common.CommonPageUtil"%>
<%@ page import="com.magic.crm.product.Constants"%>
<%@ page import="com.magic.crm.product.entity.Product"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
String isreport=request.getParameter("isreport");
       isreport=(isreport==null)?"":isreport;
%>

<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="javascript">
function checkInput(){
	var form = document.forms[0];
	return true;
}

function selectItem(){

	var selectedid = getSelectedItem();
	
	if(selectedid !="") {
	      <%
	      if(isreport.equals("1")){
	      %>
	      //alert(selectedid.substring(0,selectedid.indexOf("##"));
		opener.document.forms[0].item_code.value=selectedid.substring(0,selectedid.indexOf("##"));
		<%}else{%>
		var temp = selectedid.split("##");
//alert(temp);
		opener.getOpenwinValue(temp);
	      <%}%>
		self.close();
	}
	
}
function setfocus(){
	document.forms[0].qry_item_code.focus();
	return true;
}

function viewProduct(itemID){
	openWin("productView.do?itemID="+itemID,"wins",720,480);
}
function updatestock(itemID){
	openWin("../product/product_update_stock.jsp?itemID="+itemID,"wins",720,480);
}
function productopen(itemID) { ��
	openWin("productModifyItem.do?actn=modify&itemID="+itemID,"wins",720,480);��
} 
function productopen2(itemID) { ��

	openWin("productViewItem.do?actn=view&item_id="+itemID,"wins",720,480);��
} 

function modifyProduct(itemID, item_type){
    if (item_type=="2"|| item_type=="3") {
        document.forms[0].action="product2Set.do?type=updateinit&item_code="+itemID;
    } else {
        document.forms[0].action="productShow.do?itemcode="+itemID;
    }
	document.forms[0].submit();
}

function viewsku(itemID){
    document.forms[0].action="productSKU.do?type=list&item_code="+itemID;
    document.forms[0].submit();
}

function deleteProduct(itemID, item_type) {
    if (item_type=="2"|| item_type=="3") {
        if (!confirm("��ȷ��Ҫɾ���ò�Ʒ�𣿸ò�Ʒ��Ӧsku��ͬʱ��ɾ��")){
		    return false;
	    }
	    document.forms[0].action="product2Set.do?type=delete&set_item_code="+itemID;
        document.forms[0].submit();
        
    } else {
    
        if (!confirm("��ȷ��Ҫɾ���ò�Ʒ�𣿸ò�Ʒ��Ӧsku��ͬʱ��ɾ��")){
		    return false;
	    }
	    document.forms[0].action="product2.do?type=delete&itemcode="+itemID;
        document.forms[0].submit();
    }
}

</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="return setfocus();">

<%@ include file = "../common/page.jsp" %>
<%
String actn = request.getParameter("actn");
%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">��Ʒ����</font><font color="838383"> 
      		-&gt; </font><font color="838383">���Ų�ѯ</font><font color="838383"> 
      	</td>
   </tr> 
</table>
<html:form action="/productQuery.do" method="post" onsubmit="return checkInput();">
  <table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
    <tr>
		
      <td width="95%">���ţ� 
        <html:text property="qry_item_code" size="12"/>
        ��Ʒ���� 
        <html:text property="qry_item_name" size="16"/>
        &nbsp; 
                <html:select property="qry_item_type" >
                  <html:option value="">--����--</html:option>
                  <html:option value="1">��ͨ��Ʒ</html:option>
				          <html:option value="2">ϵ����Ʒ</html:option>
				          <html:option value="3">��װ��Ʒ</html:option>
				          <html:option value="0">����</html:option>
				</html:select>
				
				<html:select property="qry_item_category" >
				<html:option value="0">--����--</html:option>
        <html:optionsCollection   property="cates" value="id" label="name"/> 
				</html:select>
        
		
		<input name="query" type="submit" class="button5" value=" ��ѯ ">
		<input name="actn" type="hidden" value="<%=actn%>">
		<input name="isreport" type="hidden" value="<%=isreport%>">
		</td>
	</tr>
</table>

<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	<tr>
		<td><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>
<!--��Ʒ��ѯ�б�begin-->
<%
if (request.getMethod().equals("POST")){
%>
<!--��ҳ����-->
<bean:define id="pageModel" name="<%=Constants.PAGE_MODEL%>" scope="request" type="CommonPageUtil"/>
<%
    //ȡ���ܼ�¼����ҳ����
    int totalNum = 0;
    int curPage = 0;
    int totalPage = 0;
    
    totalNum = pageModel.getRecordCount();
	curPage = pageModel.getPageNo();
	totalPage = pageModel.getTotalPage();
	
%>
<%=turnPagePattern(totalNum,totalPage,curPage)%>
<%=turnPageScript("listFrm")%>
<%if (actn !=null && actn.equals("selectProduct")){%>
  	<table width="95%" align="center" onclick="changeItem()" ondblclick="selectItem()" border=0 cellspacing=1 cellpadding=2 >
<%}else{%>
	<table width="95%" align="center" border=0 cellspacing=1 cellpadding=2 >
<%}%>
    <tr height="26"> 
      <th width="8%"  class="OraTableRowHeader" noWrap  align=middle  >����</th>
      <th width="12%"  class="OraTableRowHeader" noWrap align=middle  >��Ʒ����</th>
      <th width="8%"  class="OraTableRowHeader" noWrap align=middle  >��Ʒ����</th>
      <th width="8%"  class="OraTableRowHeader" noWrap  align=middle  >��Ʒ����</th>
      <th width="8%"  class="OraTableRowHeader" noWrap  align=middle  >�г���</th>
      <!--<th width="8%"  class="OraTableRowHeader" noWrap  align=middle  >�ɱ�</th>-->
      <th width="8%"  class="OraTableRowHeader" noWrap  align=middle  >��Ա��</th>
      <th width="8%"  class="OraTableRowHeader" noWrap   align=middle  >VIP��</th>
	    <th width="8%"  class="OraTableRowHeader" noWrap  align=middle  >����</th>
	  
      <th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >����</th>
      
    </tr>
    
    <% int i=0; %>
    <bean:define id="modelList" name="pageModel" property="modelList"/>
    <logic:iterate id="product" name="modelList">

    <tr style="cursor:hand" id ="<bean:write name="product" property="item_code" filter="true"/>"
		 <% if(i%2==1) { %>class=OraTableCellText<% } %>
     > 
      
      <td noWrap align=middle >
      <a href="javascript:modifyProduct('<bean:write name="product" property="item_code"/>','<bean:write name="product" property="item_type" filter="false"/>');"><bean:write name="product" property="item_code" /></a></td>
      <td  noWrap align=right ><bean:write name="product" property="item_name" filter="false" /></td>
      <td  noWrap align=right ><bean:write name="product" property="item_type_name" filter="false" /></td>
      <td  noWrap align=right ><bean:write name="product" property="item_category_name" filter="false" /></td>
      <td  noWrap align=right ><bean:write name="product" property="standard_price" filter="false" format="0.00"/></td>
      <!--<td  noWrap align=right ><bean:write name="product" property="item_cost" filter="false" format="0.00"/></td>-->
      <td  noWrap align=right ><bean:write name="product" property="sale_price" filter="false" format="0.00"/></td>
      <td  noWrap align=right ><bean:write name="product" property="vip_price" filter="false" format=".00"/></td>
      <td  noWrap align=right ><bean:write name="product" property="max_count" filter="false" /></td>

     
      <td  noWrap align=center >
	  	<input type="button" name="sku" class="button5" value="�鿴SKU" onclick="javascript:viewsku('<bean:write name="product" property="item_code"/>');">
	  	
	  	<input type="button" name="del" class="button5" value="ɾ��" onclick="javascript:deleteProduct('<bean:write name="product" property="item_code" />','<bean:write name="product" property="item_type"/>');">
	  </td>
		    	
    </tr>
    <% i++; %>
    </logic:iterate>
    
  </table>
<%
}
%>
</html:form>

</body>
</html>
