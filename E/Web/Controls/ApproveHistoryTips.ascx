<%@ Control Language="C#" AutoEventWireup="false" %>
<%@ Import Namespace="System.Collections.Generic" %>
<%@ Import Namespace="System.Data" %>
<%@ Import Namespace="Magic.ERP" %>
<%@ Import Namespace="Magic.ERP.Core" %>
<%@ Import Namespace="Magic.ERP.Orders" %>
<%@ Import Namespace="Magic.Sys" %>
<%@ Import Namespace="Magic.Framework.Utils" %>
<%@ Import Namespace="Magic.Framework.ORM" %>
<%@ Import Namespace="Magic.Framework.ORM.Query" %>
<table cellpadding="0" cellspacing="0" style="width: 100%;font-weight:600;color:#0000FF;">
    <tr>
        <td style="width:70px;">单据号码</td>
        <td style="text-align:left;"><%= WebUtil.Param("ordNum").Trim()%></td>
    </tr>
</table>
<table id="dataArea" cellpadding="0" cellspacing="0" style="width: 100%; height: 100%;">
    <tr>
        <th>签核人</th>
        <th>签核结果</th>
        <th>签核时间</th>
        <th>签核备注</th>
    </tr>
    <%
        string ordNumber = WebUtil.Param("ordNum").Trim();		
        using (ISession session = new Session())
        {
            DataSet ds = session.CreateObjectQuery(@"
select u.FullName as UserName,ar.HasFinished as HasFinished,ar.ApproveResult as ApproveResult,ar.ApproveTime as ApproveTime,ar.ApproveNote as ApproveNote
from OrderApproveItem ai
inner join OrderApproveResult ar on ai.ApproveID=ar.ApproveID
inner join User u on u.UserId=ar.ApproveUser
where ai.OrderNumber=?ordNumber
order by ai.ApproveID,ar.StepIndex")
                .Attach(typeof(OrderApproveItem)).Attach(typeof(OrderApproveResult)).Attach(typeof(User))
                .SetValue("?ordNumber", ordNumber, "ai.OrderNumber")
                .DataSet();

            foreach (DataRow row in ds.Tables[0].Rows)
            {
    %>
    <tr>
        <td style="width:70px;text-align:left;"><%= RenderUtil.FormatString(row["UserName"])%></td>
		<td style="width:60px;text-align:left;"><%= !Cast.Bool(row["HasFinished"]) ? "待签核" : Cast.Bool(row["ApproveResult"]) ? "通过" : "驳回" %></td>
		<td style="width:110px;text-align:left;"><%= !Cast.Bool(row["HasFinished"]) ? "&nbsp;" : RenderUtil.FormatDatetime(row["ApproveTime"])%></td>
		<td style="text-align:left;"><%= !Cast.Bool(row["HasFinished"]) ? "&nbsp;" : RenderUtil.FormatString(row["ApproveNote"]).Replace("\r\n", "&nbsp;&nbsp;")%></td>
    </tr>
    <%
        }
        }
    %>
</table>