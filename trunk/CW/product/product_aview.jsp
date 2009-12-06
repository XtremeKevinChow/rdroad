<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="com.magic.crm.common.CommonPageUtil"%>
<%@ page import="com.magic.crm.product.Constants"%>
<%@ page import="com.magic.crm.product.entity.Product"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
    response.setHeader("expires","0");
    response.setHeader("Cache-Control", "no-store"); //http1.1
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache"); //http1.0
%>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">

<link rel="stylesheet" href="../css/style.css" type="text/css">
</head>
<body  text="#000000" leftmargin="0" topmargin="0" >
<br>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">��Ʒ����</font><font color="838383"> 
      		-&gt; </font><font color="838383">��Ʒ��ϸ��Ϣ</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>

<html:form action="/productView.do" method="post" onsubmit="return checkInput();">
  
<table width="90%" border=0  align="center" cellpadding=3 cellspacing=1 bgcolor="#99CCFF" >
  <tr> 
    <td width="20%" align="right"  class="OraTableRowHeader" noWrap >&nbsp;����</td>
    <td width="30%" align="left" bgcolor="#ffffff">
    	<bean:write name="<%=Constants.PRODUCT_FORM%>" property="itemCode" filter="false"/> 
    </td>
    <td width="20%" align="right"  class="OraTableRowHeader" noWrap  >&nbsp;����/����</td>
    <td width="30%" align="left" bgcolor="#ffffff">
    	<bean:write name="<%=Constants.PRODUCT_FORM%>" property="author" filter="false"/>  </td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;��Ʒ����</td>
    <td align="left" bgcolor="#ffffff">
    	<bean:write name="<%=Constants.PRODUCT_FORM%>" property="name" filter="false"/>  </td>
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;��Ʒ��λ</td>
    <td align="left" bgcolor="#ffffff">
		<logic:equal name="<%=Constants.PRODUCT_FORM%>" property="unit" value="0">��</logic:equal>     
		<logic:equal name="<%=Constants.PRODUCT_FORM%>" property="unit" value="1">��</logic:equal>
		<logic:equal name="<%=Constants.PRODUCT_FORM%>" property="unit" value="2">��</logic:equal>    
		<logic:equal name="<%=Constants.PRODUCT_FORM%>" property="unit" value="3">��</logic:equal>    
		<logic:equal name="<%=Constants.PRODUCT_FORM%>" property="unit" value="4">��</logic:equal>    
		<logic:equal name="<%=Constants.PRODUCT_FORM%>" property="unit" value="5">��</logic:equal> 		

    	
    </td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;��Ʒ����</td>
    <td align="left" bgcolor="#ffffff">
		<logic:equal name="<%=Constants.PRODUCT_FORM%>" property="itemType" value="1">ͼ��</logic:equal>
		<logic:equal name="<%=Constants.PRODUCT_FORM%>" property="itemType" value="2">Ӱ��</logic:equal>     
		<logic:equal name="<%=Constants.PRODUCT_FORM%>" property="itemType" value="3">����</logic:equal>
		<logic:equal name="<%=Constants.PRODUCT_FORM%>" property="itemType" value="4">��Ϸ/���</logic:equal>    
		<logic:equal name="<%=Constants.PRODUCT_FORM%>" property="itemType" value="5">��Ʒ</logic:equal>
		<logic:equal name="<%=Constants.PRODUCT_FORM%>" property="itemType" value="6">����</logic:equal>      

    </td>
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;��Ʒ���</td>
    <td align="left" bgcolor="#ffffff">
    	<bean:write name="<%=Constants.PRODUCT_FORM%>" property="catalogName" filter="false"/>
      </td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;��Ʒ����</td>
    <td align="left" bgcolor="#ffffff">
    	<bean:write name="<%=Constants.PRODUCT_FORM%>" property="standardPrice" filter="false" format="#.00"/></td>
    <td align="right"  class="OraTableRowHeader" noWrap  ><font color="#000000">��վ��</font></td>
    <td align="left" bgcolor="#ffffff">
    	<bean:write name="<%=Constants.PRODUCT_FORM%>" property="webPrice" filter="false" format="#.00"/></td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;������</td>
    <td align="left" bgcolor="#ffffff">
    	<bean:write name="<%=Constants.PRODUCT_FORM%>" property="silverPrice" filter="false" format="#.00"/>
    </td>
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;�𿨼�</td>
    <td align="left" bgcolor="#ffffff">
    	<bean:write name="<%=Constants.PRODUCT_FORM%>" property="godenPrice" filter="false" format="#.00"/>
    </td>
  </tr>
    <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;�׽𿨼�</td>
    <td align="left" bgcolor="#ffffff">
    	<bean:write name="<%=Constants.PRODUCT_FORM%>" property="platina_Price" filter="false" format="#.00"/>
    </td>
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;�����������</td>
    <td align="left" bgcolor="#ffffff">
    	<bean:write name="<%=Constants.PRODUCT_FORM%>" property="maxsalenum" filter="false" format="#"/>
    </td>    
  </tr>  
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;�ۿ�</td>
    <td align="left" bgcolor="#ffffff">
    	<bean:write name="<%=Constants.PRODUCT_FORM%>" property="discount" filter="false" format="#.00"/></td>
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;˰��</td>
    <td align="left" bgcolor="#ffffff">
    			<logic:equal name="<%=Constants.PRODUCT_FORM%>" property="tax" value="1">17%</logic:equal>
		<logic:equal name="<%=Constants.PRODUCT_FORM%>" property="tax" value="2">13%</logic:equal>     
		<logic:equal name="<%=Constants.PRODUCT_FORM%>" property="tax" value="3">6%</logic:equal>
		<logic:equal name="<%=Constants.PRODUCT_FORM%>" property="tax" value="4">10%</logic:equal>    
		<logic:equal name="<%=Constants.PRODUCT_FORM%>" property="tax" value="0">0%</logic:equal>
    	 </td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;��˰��</td>
    <td align="left" bgcolor="#ffffff">
    	<bean:write name="<%=Constants.PRODUCT_FORM%>" property="unpurchasingCost" filter="false" format="#.00"/></td>
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;����˰��</td>
    <td align="left" bgcolor="#ffffff">
    	<bean:write name="<%=Constants.PRODUCT_FORM%>" property="purchasingCost" filter="false" format="#.00"/>		
    	 </td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;��Ʒ����</td>
    <td align="left" bgcolor="#ffffff">
    	<logic:match name="<%=Constants.PRODUCT_FORM%>" property="peculiarity" value="0">��װ</logic:match>
    	<logic:match name="<%=Constants.PRODUCT_FORM%>" property="peculiarity" value="1">��װ</logic:match>
    </td>
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;�ɹ��༭</td>
    <td align="left" bgcolor="#ffffff">
    	<bean:write name="<%=Constants.PRODUCT_FORM%>" property="productOwnerName" filter="false"/>
    </td>
     
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;��Ʒ��Ӧ��</td>
    <td align="left" bgcolor="#ffffff">
    	<bean:write name="<%=Constants.PRODUCT_FORM%>" property="supplierName" filter="false"/>
    </td>
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;���䷽ʽ</td>
    <td align="left" bgcolor="#ffffff">
    	<logic:match name="<%=Constants.PRODUCT_FORM%>" property="deliveryType" value="1">����</logic:match>
    	<logic:match name="<%=Constants.PRODUCT_FORM%>" property="deliveryType" value="2">������</logic:match>
    	<logic:match name="<%=Constants.PRODUCT_FORM%>" property="deliveryType" value="3">��Ӧ���ͻ�</logic:match>
    </td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;������</td>
    <td align="left" bgcolor="#ffffff">
    	<bean:write name="<%=Constants.PRODUCT_FORM%>" property="title" filter="false"/>
    </td>
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;������</td>
    <td align="left" bgcolor="#ffffff">
    	<bean:write name="<%=Constants.PRODUCT_FORM%>" property="publishingHouse" filter="false" format="#"/>
      </td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;CIP��</td>
    <td align="left" bgcolor="#ffffff">
    	<bean:write name="<%=Constants.PRODUCT_FORM%>" property="icpCode" filter="false"/></td>
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;ISBN</td>
    <td align="left" bgcolor="#ffffff">
    	<bean:write name="<%=Constants.PRODUCT_FORM%>" property="isbn" filter="false"/> </td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;������</td>
    <td align="left" bgcolor="#ffffff">
    	<bean:write name="<%=Constants.PRODUCT_FORM%>" property="barCode" filter="false"/> </td>
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;�˻���</td>
    <td align="left" bgcolor="#ffffff">
    	<bean:write name="<%=Constants.PRODUCT_FORM%>" property="returnRate" filter="false"  format="#.00"/> 
    </td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;���ʹ��״̬</td>
    <td align="left" bgcolor="#ffffff">
    	<logic:match name="<%=Constants.PRODUCT_FORM%>" property="userStatus" value="1">YY</logic:match>
    	<logic:match name="<%=Constants.PRODUCT_FORM%>" property="userStatus" value="2">YN</logic:match>
    	<logic:match name="<%=Constants.PRODUCT_FORM%>" property="userStatus" value="3">NY</logic:match>
    	<logic:match name="<%=Constants.PRODUCT_FORM%>" property="userStatus" value="4">NN</logic:match>
    	</td>
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;�������</td>
    <td align="left" bgcolor="#ffffff">
    	<bean:write name="<%=Constants.PRODUCT_FORM%>" property="replaceItemID" filter="false" format="#"/>
     </td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;��ͬ����</td>
    <td align="left" bgcolor="#ffffff">
    	<logic:match name="<%=Constants.PRODUCT_FORM%>" property="contractTitle" value="0">��Ӧ��</logic:match>
    	<logic:match name="<%=Constants.PRODUCT_FORM%>" property="contractTitle" value="1">������</logic:match>
    </td>
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;�Ƿ����꼴ֹ</td>
    <td align="left" bgcolor="#ffffff">
    	<logic:match name="<%=Constants.PRODUCT_FORM%>" property="isLastSel" value="0">��</logic:match>
    	<logic:match name="<%=Constants.PRODUCT_FORM%>" property="isLastSel" value="1">��</logic:match>
    </td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;����ԱID</td>
    <td align="left" bgcolor="#ffffff">
    	<bean:write name="<%=Constants.PRODUCT_FORM%>" property="operatorID" filter="false"  format="#"/>
    </td>
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;����ʱ��</td>
    <td align="left" bgcolor="#ffffff">
    	<bean:write name="<%=Constants.PRODUCT_FORM%>" property="operateTime" filter="false" format="yyyy-MM-dd"/>
    </td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;��ע</td>
    <td align="left" bgcolor="#ffffff"colspan="3">&nbsp;<bean:write name="<%=Constants.PRODUCT_FORM%>" property="comments" filter="false"/></textarea> 
    </td>
    
  </tr>
  
</table>
</html:form>
<p align="center"><input name = "submitButton" id="submitButton" type="button" class="button2" value=" �ر� " onclick="javascript:self.close();"> </p>
</body>
</html>

