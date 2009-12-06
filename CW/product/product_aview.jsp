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
<title>佰明会员关系管理系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">

<link rel="stylesheet" href="../css/style.css" type="text/css">
</head>
<body  text="#000000" leftmargin="0" topmargin="0" >
<br>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">产品管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">产品详细信息</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>

<html:form action="/productView.do" method="post" onsubmit="return checkInput();">
  
<table width="90%" border=0  align="center" cellpadding=3 cellspacing=1 bgcolor="#99CCFF" >
  <tr> 
    <td width="20%" align="right"  class="OraTableRowHeader" noWrap >&nbsp;货号</td>
    <td width="30%" align="left" bgcolor="#ffffff">
    	<bean:write name="<%=Constants.PRODUCT_FORM%>" property="itemCode" filter="false"/> 
    </td>
    <td width="20%" align="right"  class="OraTableRowHeader" noWrap  >&nbsp;作者/译者</td>
    <td width="30%" align="left" bgcolor="#ffffff">
    	<bean:write name="<%=Constants.PRODUCT_FORM%>" property="author" filter="false"/>  </td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;产品名称</td>
    <td align="left" bgcolor="#ffffff">
    	<bean:write name="<%=Constants.PRODUCT_FORM%>" property="name" filter="false"/>  </td>
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;产品单位</td>
    <td align="left" bgcolor="#ffffff">
		<logic:equal name="<%=Constants.PRODUCT_FORM%>" property="unit" value="0">本</logic:equal>     
		<logic:equal name="<%=Constants.PRODUCT_FORM%>" property="unit" value="1">包</logic:equal>
		<logic:equal name="<%=Constants.PRODUCT_FORM%>" property="unit" value="2">套</logic:equal>    
		<logic:equal name="<%=Constants.PRODUCT_FORM%>" property="unit" value="3">件</logic:equal>    
		<logic:equal name="<%=Constants.PRODUCT_FORM%>" property="unit" value="4">盒</logic:equal>    
		<logic:equal name="<%=Constants.PRODUCT_FORM%>" property="unit" value="5">把</logic:equal> 		

    	
    </td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;产品类型</td>
    <td align="left" bgcolor="#ffffff">
		<logic:equal name="<%=Constants.PRODUCT_FORM%>" property="itemType" value="1">图书</logic:equal>
		<logic:equal name="<%=Constants.PRODUCT_FORM%>" property="itemType" value="2">影视</logic:equal>     
		<logic:equal name="<%=Constants.PRODUCT_FORM%>" property="itemType" value="3">音乐</logic:equal>
		<logic:equal name="<%=Constants.PRODUCT_FORM%>" property="itemType" value="4">游戏/软件</logic:equal>    
		<logic:equal name="<%=Constants.PRODUCT_FORM%>" property="itemType" value="5">礼品</logic:equal>
		<logic:equal name="<%=Constants.PRODUCT_FORM%>" property="itemType" value="6">其他</logic:equal>      

    </td>
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;产品类别</td>
    <td align="left" bgcolor="#ffffff">
    	<bean:write name="<%=Constants.PRODUCT_FORM%>" property="catalogName" filter="false"/>
      </td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;产品定价</td>
    <td align="left" bgcolor="#ffffff">
    	<bean:write name="<%=Constants.PRODUCT_FORM%>" property="standardPrice" filter="false" format="#.00"/></td>
    <td align="right"  class="OraTableRowHeader" noWrap  ><font color="#000000">网站价</font></td>
    <td align="left" bgcolor="#ffffff">
    	<bean:write name="<%=Constants.PRODUCT_FORM%>" property="webPrice" filter="false" format="#.00"/></td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;银卡价</td>
    <td align="left" bgcolor="#ffffff">
    	<bean:write name="<%=Constants.PRODUCT_FORM%>" property="silverPrice" filter="false" format="#.00"/>
    </td>
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;金卡价</td>
    <td align="left" bgcolor="#ffffff">
    	<bean:write name="<%=Constants.PRODUCT_FORM%>" property="godenPrice" filter="false" format="#.00"/>
    </td>
  </tr>
    <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;白金卡价</td>
    <td align="left" bgcolor="#ffffff">
    	<bean:write name="<%=Constants.PRODUCT_FORM%>" property="platina_Price" filter="false" format="#.00"/>
    </td>
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;最大销售数量</td>
    <td align="left" bgcolor="#ffffff">
    	<bean:write name="<%=Constants.PRODUCT_FORM%>" property="maxsalenum" filter="false" format="#"/>
    </td>    
  </tr>  
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;折扣</td>
    <td align="left" bgcolor="#ffffff">
    	<bean:write name="<%=Constants.PRODUCT_FORM%>" property="discount" filter="false" format="#.00"/></td>
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;税率</td>
    <td align="left" bgcolor="#ffffff">
    			<logic:equal name="<%=Constants.PRODUCT_FORM%>" property="tax" value="1">17%</logic:equal>
		<logic:equal name="<%=Constants.PRODUCT_FORM%>" property="tax" value="2">13%</logic:equal>     
		<logic:equal name="<%=Constants.PRODUCT_FORM%>" property="tax" value="3">6%</logic:equal>
		<logic:equal name="<%=Constants.PRODUCT_FORM%>" property="tax" value="4">10%</logic:equal>    
		<logic:equal name="<%=Constants.PRODUCT_FORM%>" property="tax" value="0">0%</logic:equal>
    	 </td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;含税价</td>
    <td align="left" bgcolor="#ffffff">
    	<bean:write name="<%=Constants.PRODUCT_FORM%>" property="unpurchasingCost" filter="false" format="#.00"/></td>
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;不含税价</td>
    <td align="left" bgcolor="#ffffff">
    	<bean:write name="<%=Constants.PRODUCT_FORM%>" property="purchasingCost" filter="false" format="#.00"/>		
    	 </td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;产品特性</td>
    <td align="left" bgcolor="#ffffff">
    	<logic:match name="<%=Constants.PRODUCT_FORM%>" property="peculiarity" value="0">简装</logic:match>
    	<logic:match name="<%=Constants.PRODUCT_FORM%>" property="peculiarity" value="1">金装</logic:match>
    </td>
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;采购编辑</td>
    <td align="left" bgcolor="#ffffff">
    	<bean:write name="<%=Constants.PRODUCT_FORM%>" property="productOwnerName" filter="false"/>
    </td>
     
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;产品供应商</td>
    <td align="left" bgcolor="#ffffff">
    	<bean:write name="<%=Constants.PRODUCT_FORM%>" property="supplierName" filter="false"/>
    </td>
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;运输方式</td>
    <td align="left" bgcolor="#ffffff">
    	<logic:match name="<%=Constants.PRODUCT_FORM%>" property="deliveryType" value="1">自提</logic:match>
    	<logic:match name="<%=Constants.PRODUCT_FORM%>" property="deliveryType" value="2">代理发送</logic:match>
    	<logic:match name="<%=Constants.PRODUCT_FORM%>" property="deliveryType" value="3">供应商送货</logic:match>
    </td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;副标题</td>
    <td align="left" bgcolor="#ffffff">
    	<bean:write name="<%=Constants.PRODUCT_FORM%>" property="title" filter="false"/>
    </td>
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;出版社</td>
    <td align="left" bgcolor="#ffffff">
    	<bean:write name="<%=Constants.PRODUCT_FORM%>" property="publishingHouse" filter="false" format="#"/>
      </td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;CIP号</td>
    <td align="left" bgcolor="#ffffff">
    	<bean:write name="<%=Constants.PRODUCT_FORM%>" property="icpCode" filter="false"/></td>
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;ISBN</td>
    <td align="left" bgcolor="#ffffff">
    	<bean:write name="<%=Constants.PRODUCT_FORM%>" property="isbn" filter="false"/> </td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;条形码</td>
    <td align="left" bgcolor="#ffffff">
    	<bean:write name="<%=Constants.PRODUCT_FORM%>" property="barCode" filter="false"/> </td>
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;退换率</td>
    <td align="left" bgcolor="#ffffff">
    	<bean:write name="<%=Constants.PRODUCT_FORM%>" property="returnRate" filter="false"  format="#.00"/> 
    </td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;库存使用状态</td>
    <td align="left" bgcolor="#ffffff">
    	<logic:match name="<%=Constants.PRODUCT_FORM%>" property="userStatus" value="1">YY</logic:match>
    	<logic:match name="<%=Constants.PRODUCT_FORM%>" property="userStatus" value="2">YN</logic:match>
    	<logic:match name="<%=Constants.PRODUCT_FORM%>" property="userStatus" value="3">NY</logic:match>
    	<logic:match name="<%=Constants.PRODUCT_FORM%>" property="userStatus" value="4">NN</logic:match>
    	</td>
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;替代货号</td>
    <td align="left" bgcolor="#ffffff">
    	<bean:write name="<%=Constants.PRODUCT_FORM%>" property="replaceItemID" filter="false" format="#"/>
     </td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;合同标题</td>
    <td align="left" bgcolor="#ffffff">
    	<logic:match name="<%=Constants.PRODUCT_FORM%>" property="contractTitle" value="0">供应商</logic:match>
    	<logic:match name="<%=Constants.PRODUCT_FORM%>" property="contractTitle" value="1">出版社</logic:match>
    </td>
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;是否售完即止</td>
    <td align="left" bgcolor="#ffffff">
    	<logic:match name="<%=Constants.PRODUCT_FORM%>" property="isLastSel" value="0">否</logic:match>
    	<logic:match name="<%=Constants.PRODUCT_FORM%>" property="isLastSel" value="1">是</logic:match>
    </td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;操作员ID</td>
    <td align="left" bgcolor="#ffffff">
    	<bean:write name="<%=Constants.PRODUCT_FORM%>" property="operatorID" filter="false"  format="#"/>
    </td>
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;操作时间</td>
    <td align="left" bgcolor="#ffffff">
    	<bean:write name="<%=Constants.PRODUCT_FORM%>" property="operateTime" filter="false" format="yyyy-MM-dd"/>
    </td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;备注</td>
    <td align="left" bgcolor="#ffffff"colspan="3">&nbsp;<bean:write name="<%=Constants.PRODUCT_FORM%>" property="comments" filter="false"/></textarea> 
    </td>
    
  </tr>
  
</table>
</html:form>
<p align="center"><input name = "submitButton" id="submitButton" type="button" class="button2" value=" 关闭 " onclick="javascript:self.close();"> </p>
</body>
</html>

