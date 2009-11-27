<%@ Page Language="C#" %>
<%@ Import Namespace="Magic.Security" %>
<script runat="server">
    protected void Page_Load()
    {
        SecuritySession.SignOut();      
    }
</script>

