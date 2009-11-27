<%@ Page Language="C#" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>上海佰明ERP管理系统</title>
    <script type="text/javascript">
   function FunctionNavigate(url,funcID,sender)
   {         
        window.mainFrame.location.href = url;  
   }
    </script>
</head>
<frameset rows="86,*" frameborder="no" cols="*" id="global" name="global">
	<FRAME id="topFrame" title="topFrame" name="topFrame" src="nav_top.aspx" frameBorder=no noResize scrolling=no>
    <FRAME id="mainFrame" title="mainFrame" name="mainFrame" src="">
</frameset>
</html>