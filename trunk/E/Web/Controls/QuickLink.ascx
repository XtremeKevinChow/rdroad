<%@ Control Language="C#" ClassName="QuickLink" %>
<%@ Import Namespace="System.Collections.Generic" %>
<%@ Import Namespace="Magic.Sys" %>
<%@ Import Namespace="Magic.Framework.ORM" %>
<%@ Import Namespace="Magic.Framework.ORM.Query" %>

<script runat="server">

</script>

<%
    int funcId = WebUtil.ParamInt("funcId", -1);
    IList<Operation> list = null;
    if (funcId > -1)
    {
        using (ISession session = new Session())
        {
            list = session.CreateEntityQuery<Operation>()
                .And(Exp.Eq("ParentId", funcId)).List<Operation>();
        }
    }
%>
<%if (list != null && list.Count > 0)
  {%>
<div class='linkContent'>
    <% foreach (Operation nav in list)
       {%>
    <a href="javascript:PublishNavigate('<%= nav.Entry %>')">
        <img width='24' height='20' src='images/left_icon1.gif' title='<%= nav.Description %>' border='0'
            align='absmiddle' /><%= nav.Description %></a><br />
    <%} %>
</div>
<%} %>