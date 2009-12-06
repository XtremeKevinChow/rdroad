<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="com.magic.crm.product.entity.ProductType"%>
<%
java.util.ArrayList data = ( java.util.ArrayList )request.getAttribute("list");
int len = 0;
if (data != null) {
	len = data.size();
}
%>
<HTML><HEAD><TITLE>产品类型树</TITLE>
<base target="rtop">
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="javascript" src="../script/dTree.js"></script>


<SCRIPT LANGUAGE="JavaScript">
<!--

function myPopupWin(nodeid)
{
	
	document.forms[0].id.value=nodeid;
	modify_f();

}

/**
function myPopupWin(nodeid)
{
	var lcdept,lsStr;
	document.forms[0].id.value=nodeid;
	//psTarget_Frame = lsTaget;
	document.all("oper_win").style.display="";
	document.all("oper_win").style.position="absolute";
	
	document.all("oper_win").style.pixelLeft=document.all("treebody").offsetLeft+document.activeElement.offsetLeft+10;
	
	document.all("oper_win").style.pixelTop=document.all("treebody").offsetTop+document.activeElement.offsetTop+10;
	
	//window.event.cancelBubble = true;
	
	

}
*/
function add_f() {
	oper_win.style.display = "none";
	document.forms[0].action = "productType.do?type=addInit";
	//document.parentWindow.parent.document.operation.src="productType.do?type=addInit";
	document.forms[0].target="operation";
	document.forms[0].submit();
	
	
}
function modify_f() {

	if(document.forms[0].id.value=="0") {
		alert("最上级不能编辑");
		return;
	}
	oper_win.style.display = "none";
	document.forms[0].action = "productType.do?type=modInit";
	document.forms[0].target = "operation";
	document.forms[0].submit();
	
}

function delete_f() {
	oper_win.style.display = "none";
	document.forms[0].action = "productType.do?type=delete";
	document.forms[0].target = "operation";
	document.forms[0].submit();
}

function query_f() {
	oper_win.style.display = "none";
	document.forms[0].action = "productType.do?type=queryProductByCategory";
	document.forms[0].target = "operation";
	document.forms[0].submit();
}

//-->
</SCRIPT>



</HEAD>
<BODY id="treebody" leftmargin="0" topmargin="0" background="/common/images/bg2.gif" marginwidth="0" marginheight="0">
<html:form action="productType.do?type=addInit" method="POST" >
<div id="treeContainer" width="50%"></div>
<script language="javascript">

var d=new Date();
var tree=new dTree();
tree.add(1,0,"根节点","javascript:myPopupWin(0)","_self");
<%

for(int i = 0; i < len; i ++) {
	
	ProductType tree = (ProductType)data.get(i);
	String parentID =null;
	if(tree.getParentType()==0) {
		parentID = "1";
	}else{
		parentID = String.valueOf(tree.getParentType());
	}
	
	if(tree.getID() == 0) {
		
		continue;
	}
%>	

tree.add('<%=tree.getID()%>','<%=parentID%>',"<%=tree.getName()%>(<%=tree.getCatalogCode()%>)","javascript:myPopupWin(<%=tree.getID()%>)","_self");
<%
}
%>


tree.write(document.getElementById("treeContainer"));
//window.status="加载"+tree.all.length+"个节点"+((new Date())-d)+"ms.";

</script>


<table width="100" border=1 bordercolorlight="f5f5f5" bordercolordark="999999"style="display:none" id="oper_win" cellspacing="0">
  <tr> 
    <td id="property_entry" onMouseOver="this.className='on'" onMouseOut="this.className='off'" onclick="modify_f()" style="cursor:hand" align="center" class="off"  class="off">编辑分类</td>
  </tr>
  <tr> 
    <td id="content_entry" onMouseOver="this.className='on'" onMouseOut="this.className='off'" onclick="add_f()" style="cursor:hand" align="center" class="off"  class="off">新增下级分类</td>
  </tr>
  <tr> 
    <td id="delete_entry" onMouseOver="this.className='on'" onMouseOut="this.className='off'" onclick="delete_f()" style="cursor:hand" align="center" class="off"  class="off">删除分类产品</td>
  </tr>
  <tr id="sub_entry_tr"> 
    <td id="sub_entry" onMouseOver="this.className='on'" onMouseOut="this.className='off'" onclick="query_f()" style="cursor:hand" align="center" class="off"  class="off">浏览分类产品</td>
  </tr>
</table>


	<input type="hidden" name="id">
   


</BODY>
</HTML>
</html:form>
