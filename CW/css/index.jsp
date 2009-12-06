<%
//参数tel来自Call Center的配置文件
String tel = request.getParameter("tel");
tel = (tel == null) ? "" : tel;
String isquery = request.getParameter("isquery");
isquery = (isquery == null) ? "0" : isquery;
        String LogID=request.getParameter("LogID");
        LogID=(LogID==null)?"":LogID;

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>佰明会员关系管理系统</TITLE>
<META content="text/html; charset=gb2312" http-equiv=Content-Type>
<META content="MSHTML 5.00.3315.2870" name=GENERATOR>
</HEAD>
<FRAMESET name="mainframe" frameSpacing=0 rows=43,*,22 style="BORDER-BOTTOM: 0px; BORDER-LEFT: 0px; BORDER-RIGHT: 0px; BORDER-TOP: 0px;border-color:lightblue" cols="*"> 
  <FRAME frameBorder=no marginHeight=1 marginWidth=1 name=toolframe noResize scrolling=no src="top.jsp?LogID=<%=LogID%>">
  <FRAMESET name="framemenu" border=1 cols=160,16,* frameborder="1" FRAMESPACING="0" TOPMARGIN="0" LEFTMARGIN="1" MARGINHEIGHT="0" MARGINWIDTH="0" bordercolor="#D4D0C8"> 
    <FRAME name="menutool" scrolling="auto" src="menu/sel.htm" bordercolor="lightblue">
    <FRAME name="control" frameBorder=no noresize scrolling="no" src="menu/control.htm" bordercolor="lightblue">
    <FRAME name="main" scrolling=yes src="member/query.do?TELEPHONE=<%=tel%>&isquery=<%=isquery%>" bordercolor="#FFFFFF">
  </FRAMESET>
  <frame src="menu/end.jsp" scrolling="NO" noresize frameborder="NO" bordercolor="#D4D0C8">
</FRAMESET>
<noframes> 
</noframes> 

</HTML>
