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
<TITLE> 上海佰明会员关系管理系统</TITLE>
<META content="text/html; charset=gb2312" http-equiv=Content-Type>
<META content="MSHTML 5.00.3315.2870" name=GENERATOR>
</HEAD>

<script type="text/javascript">
 window.NavigateListeners = new Array("topFrame");
   function FunctionNavigate(url,funcID,sender)
   {         
        window.mainFrame.location.href = url;  
        if(typeof(window.NavigateListeners) != "undefined" && window.NavigateListeners != null)
        {
            for(i=0;i<window.NavigateListeners.length;i++)
            {
                var frameName = window.NavigateListeners[i];
                if(frameName != sender)
                {
                    window[frameName].FunctionNavigate(url,funcID,sender);
                }
            }
        } 
   }
   
  
</script>
<style type="text/css">body {
	BACKGROUND: #fff; MARGIN: 0px
}
TABLE {
	BORDER-RIGHT: medium none; PADDING-RIGHT: 0px; BORDER-TOP: medium none; PADDING-LEFT: 0px; PADDING-BOTTOM: 0px; MARGIN: 0px; BORDER-LEFT: medium none; PADDING-TOP: 0px; BORDER-BOTTOM: medium none
}
TD {
	BORDER-RIGHT: medium none; PADDING-RIGHT: 0px; BORDER-TOP: medium none; PADDING-LEFT: 0px; PADDING-BOTTOM: 0px; MARGIN: 0px; BORDER-LEFT: medium none; PADDING-TOP: 0px; BORDER-BOTTOM: medium none
}
IMG {
	BORDER-RIGHT: medium none; PADDING-RIGHT: 0px; BORDER-TOP: medium none; PADDING-LEFT: 0px; PADDING-BOTTOM: 0px; MARGIN: 0px; BORDER-LEFT: medium none; PADDING-TOP: 0px; BORDER-BOTTOM: medium none
}
FORM {
	BORDER-RIGHT: medium none; PADDING-RIGHT: 0px; BORDER-TOP: medium none; PADDING-LEFT: 0px; PADDING-BOTTOM: 0px; MARGIN: 0px; BORDER-LEFT: medium none; PADDING-TOP: 0px; BORDER-BOTTOM: medium none
}
TD {
	FONT-SIZE: 12px
}
P {
	FONT-SIZE: 12px
}
LI {
	FONT-SIZE: 12px
}
SELECT {
	FONT-SIZE: 12px
}
INPUT {
	FONT-SIZE: 12px
}
TEXTAREA {
	FONT-SIZE: 12px
}
</style>

<!--
<FRAMESET name="mainframe" frameSpacing=0 rows=43,*,22 style="BORDER-BOTTOM: 0px; BORDER-LEFT: 0px; BORDER-RIGHT: 0px; BORDER-TOP: 0px;border-color:lightblue" cols="*"> 
  <FRAME frameBorder=no marginHeight=1 marginWidth=1 name=toolframe noResize scrolling=no src="top.jsp?LogID=<%=LogID%>">
  <FRAMESET id="framemenu" name="framemenu" border=1 cols=160,16,* frameborder="1" FRAMESPACING="0" TOPMARGIN="0" LEFTMARGIN="1" MARGINHEIGHT="0" MARGINWIDTH="0" bordercolor="#D4D0C8"> 
    <FRAME name="menutool" scrolling="auto" src="menu/sel.htm" bordercolor="lightblue">
    <FRAME name="control" frameBorder=no noresize scrolling="no" src="menu/control.htm" bordercolor="lightblue">
    <FRAME name="main" scrolling=yes src="member/query.do?TELEPHONE=<%=tel%>&isquery=<%=isquery%>" bordercolor="#FFFFFF">
  </FRAMESET>
  <frame src="menu/end.jsp" scrolling="NO" noresize frameborder="NO" bordercolor="#D4D0C8">
</FRAMESET>
-->
<FRAMESET rows="128,*" frameBorder="no" cols="*" name="global">
	<FRAME id="topFrame" title="topFrame" name="topFrame" src="nav_top.jsp" frameBorder=no noResize scrolling=no>
	<FRAME id="mainFrame" title="mainFrame" name="mainFrame" src="member/query.do">
</FRAMESET>
<noframes> 
</noframes> 

</HTML>
