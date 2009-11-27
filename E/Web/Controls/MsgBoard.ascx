<%@ Control Language="C#" %>
<%@ Import Namespace="System.Data" %>
<%@ Import Namespace="System.Collections.Generic" %>
<%@ Import Namespace="Magic.Sys" %>
<script runat="server">
   private DataTable _dataSource = null;
   public DataTable DataSource
    {
        get
        {
            return _dataSource;
        }
        set
        {            
            _dataSource = value;
        }
    }
 
</script>
<%
    if (_dataSource != null)
    {

     %>
<ul class="leftul">  
    <% foreach (DataRow row in _dataSource.Rows)
       {%>    
     <li><a href="javascript:PublishNavigate('<% = row["ViewEntry"] %>','');" title='<%= row["Title"] %>'><%= RenderUtil.AnsiSubstring(string.Format("[{0}]{1}",row["TypeName"],row["Title"]),25) %> </a><img src="images/ico_delete.gif" style="cursor:pointer" onclick='DeleteMsg(<%= row["ReceiverId"] %>)' alt="已阅读" title="已阅读" /></li>
    <%} %>
 </ul>
<% } %>