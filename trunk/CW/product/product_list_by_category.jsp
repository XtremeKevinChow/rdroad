<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="com.magic.crm.common.CommonPageUtil"%>
<%@ page import="com.magic.crm.product.Constants"%>
<%@ page import="com.magic.crm.product.entity.Product"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%

String typeID = (String)request.getAttribute("typeid");

typeID = (typeID == null) ? "" : typeID;


String qty = (String)request.getAttribute("qty");
qty = (qty == null) ? "" : qty;
%>

<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript" src="../script/sortTable.js"></script>
<script language="javascript">
function checkInput(){
	var form = document.forms[0];
	return true;
}


function viewProduct(itemID){
	openWin("productView.do?itemID="+itemID,"wins",720,480);
}

function productopen(itemID) { ��
	openWin("productModifyItem.do?actn=modify&itemID="+itemID,"wins",720,480);��
} 
function productopen2(itemID) { ��

	openWin("productViewItem.do?actn=view&item_id="+itemID,"wins",720,480);��
} 

</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="loadSort(DataTable);">

<%@ include file = "../common/page.jsp" %>
<%
String actn = request.getParameter("actn");
%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">�ͻ�����</font><font color="838383"> 
      		-&gt; </font><font color="838383">��Ʒ��ѯ</font><font color="838383"> 
      	</td>
   </tr> 
</table>
<html:form action="/productType.do?type=queryProductByCategory" method="post">
<input type="hidden" name="ID" value="<%=typeID%>">
<input type="hidden" name="qty" value="<%=qty%>">
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


	<table width="95%" align="center" border=0 cellspacing=1 cellpadding=2 id="DataTable">

    <tr height="26"> 
      <td width="14%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >����</td>
      <td width="32%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >��Ʒ����</td>
      <td width="11%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >��Ʒ����</td>
	  <td width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >���ÿ��</td>
      <td width="9%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >����</td>
      <td width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >������</td>
      <td width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >�𿨼�</td>
      <td width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >���ϼ�</td>
       
    </tr>
    
    <bean:define id="modelList" name="pageModel" property="modelList"/>
    <logic:iterate id="product" name="modelList">
    <tr style="cursor:hand" id ="<bean:write name="product" property="itemCode" filter="false"/>##<bean:write name="product" property="itemCode" filter="true"/>"> 
      <td noWrap align=middle >
      	<%if (actn !=null && actn.equals("selectProduct")){%>
      		<bean:write name="product" property="itemCode" filter="true"/>
      	<%}else {%>
      	
      	<logic:equal name="product" property="isSet" value="1">
      	<!--
		      	<a href="../product/productViewItem.do?actn=view&item_id=<bean:write name='product' property='itemID' filter='true'/>" target=_blank>
		      		<bean:write name="product" property="itemCode" filter="true"/> 
		      	</a>      
		      	-->
	      	<a href="javascript:productopen2('<bean:write name="product" property="itemID" filter="true"/>')">
	      		<bean:write name="product" property="itemCode" filter="true"/> 
	      	</a> 			      		
      	</logic:equal>  
      	
      	<logic:equal name="product" property="isSet" value="0">
	      	<a href="javascript:viewProduct('<bean:write name="product" property="itemID" filter="true"/>')">
	      		<bean:write name="product" property="itemCode" filter="true"/>
	      	</a>   	
      	</logic:equal>       	    	
      	<%}%>
      </td>
      <td class=OraTableCellText noWrap align="left">
      	<%if (actn !=null && actn.equals("selectProduct")){%>
      		<bean:write name="product" property="name" filter="false"/>
      	<%}else{%>
      	<logic:equal name="product" property="isSet" value="1">

	      	<a href="javascript:productopen2('<bean:write name="product" property="itemID" filter="true"/>')">
	      		<bean:write name="product" property="name" filter="false"/>  
	      	</a> 		      	     	
      	</logic:equal>
      	
      	<logic:equal name="product" property="isSet" value="0">
	      	<a href="javascript:viewProduct('<bean:write name="product" property="itemID" filter="true"/>')">
	      		<bean:write name="product" property="name" filter="false"/> 
	      	</a>   	
      	</logic:equal>      	

      	<%}%>

      </td>
      <td class=OraTableCellText noWrap align=left >
		<logic:equal name="product" property="type" value="1">ͼ��</logic:equal>
		<logic:equal name="product" property="type" value="2">Ӱ��</logic:equal>     
		<logic:equal name="product" property="type" value="3">����</logic:equal>
		<logic:equal name="product" property="type" value="4">��Ϸ/���</logic:equal>    
		<logic:equal name="product" property="type" value="5">��Ʒ</logic:equal>
		<logic:equal name="product" property="type" value="6">����</logic:equal>    				
	 
      </td>
	  <td class=OraTableCellText noWrap align=right ><bean:write name="product" property="availQty" filter="false" format="0"/></td>
      <td class=OraTableCellText noWrap align=right ><bean:write name="product" property="standardPrice" filter="false" format="0.00"/></td>
      <td class=OraTableCellText noWrap align=right ><bean:write name="product" property="silverPrice" filter="false" format="0.00"/></td>
      <td class=OraTableCellText noWrap align=right ><bean:write name="product" property="godenPrice" filter="false" format="0.00"/></td>
      <td class=OraTableCellText noWrap align=right ><bean:write name="product" property="webPrice" filter="false" format=".00"/></td>
      	
      	
      	

    </tr>
    </logic:iterate>
    
  </table>
<%
}
%>
</html:form>

</body>
</html>
