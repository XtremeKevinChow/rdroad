<%@ Page Language="C#" AutoEventWireup="true" %>
<%@ Import Namespace="System.Data" %>
<%@ Import Namespace="System.Collections.Generic" %>
<%@ Import Namespace="Magic.Basis" %>
<%@ Import Namespace="Magic.Framework.Utils" %>
<%@ Import Namespace="Magic.Framework.ORM" %>
<%@ Import Namespace="Magic.Framework.ORM.Query" %>
<%@ Import Namespace="Magic.ERP.Orders" %>
<%@ Import Namespace="Magic.ERP.Core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>采购收货查询</title>
    <link href="../CSS/queryPage.css" rel="Stylesheet" type="text/css" />
</head>
<body>
    <form id="form1" runat="server">
<%
using (ISession session = new Session())
{
    string orderNumber = WebUtil.Param("ordNum");
    string lineNumber = WebUtil.Param("lineNum");
    if (!string.IsNullOrEmpty(orderNumber))
    {
        RCVHead head = RCVHead.Retrieve(session, orderNumber);
        if (head != null)
        {
            Magic.Sys.User createUser = null;
            if (head.CreateUser > 0) createUser = Magic.Sys.User.Retrieve(session, head.CreateUser);
            Magic.Sys.User approveUser = null;
            if (head.ApproveResult != Magic.ERP.ApproveStatus.UnApprove && head.ApproveUser > 0)
                approveUser = Magic.Sys.User.Retrieve(session, head.ApproveUser);
            OrderStatusDef statusDef = OrderStatusDef.Retrieve(session, head.OrderTypeCode, (int)head.Status);
%>
    <div>
        <h4 style="margin:0;">收货单<%= head.OrderNumber %></h4>
        <table class="queryArea" cellpadding="0" cellspacing="0" style=" margin-bottom:8px;">
            <tr>
                <td class="label" style="width:70px;">状态：</td>
                <td style="width:75px;background-color:#fff;"><%= statusDef == null ? "" : statusDef.StatusText %></td>
                <td class="label" style="width:70px;">创建者：</td>
                <td style="width:75px;background-color:#fff;"><%= createUser==null ? "" : createUser.FullName %></td>
                <td class="label" style="width:80px;">创建时间：</td>
                <td style="width:105px;background-color:#fff;"><%= RenderUtil.FormatDatetime(head.CreateTime) %></td>
                <td class="label" style="width:70px;">备注：</td>
                <td style="background-color:#fff;"><%= head.Note %></td>
            </tr>
            <tr>
                <td class="label">签核结果：</td>
                <td style="background-color:#fff;"><%= POHead.ApproveStatusText(head.ApproveResult) %></td>
                <td class="label">签核人：</td>
                <td style="background-color:#fff;"><%= approveUser == null ? "" : approveUser.FullName%></td>
                <td class="label">签核时间：</td>
                <td style="background-color:#fff;"><%= head.ApproveResult == Magic.ERP.ApproveStatus.UnApprove?"": RenderUtil.FormatDatetime(head.ApproveTime) %></td>
                <td class="label">签核备注：</td>
                <td style="background-color:#fff;"><%= head.ApproveResult == Magic.ERP.ApproveStatus.UnApprove?"": head.ApproveNote%></td>
             </tr>
        </table>
        <table class="datalist2" id="data_list_table" cellpadding="0" cellspacing="0">
                <tr>
                    <td class="h nw">行号</td>
                    <td class="h nw">采购单行号</td>
                    <td class="h nw">货号</td>
                    <td class="h nw">商品名称</td>
                    <td class="h nw">颜色</td>
                    <td class="h nw">尺码</td>
                    <td class="h nw">库位</td>
                    <td class="h nw">货架</td>
                    <td class="h nw">收货数量</td>
                </tr>
<%
    DataSet ds = session.CreateObjectQuery(@"
select m.ItemCode as ItemCode,m.ItemName as ItemName,s.ColorCode as ColorCode,s.SizeCode as SizeCode
    ,l.LineNumber as LineNumber,l.RefOrderLine as RefOrderLine
    ,l.AreaCode as AreaCode,l.SectionCode as SectionCode,l.QualifiedQty as QualifiedQty
from RCVLine l
inner join ItemSpec s on l.SKUID=s.SKUID
inner join ItemMaster m on s.ItemID=m.ItemID
WHERE l.OrderNumber=?
order by l.LineNumber")
          .Attach(typeof(RCVLine)).Attach(typeof(ItemSpec)).Attach(typeof(ItemMaster))
          .SetValue(0, orderNumber, "l.OrderNumber")
          .DataSet();
    string color = "";
    foreach (DataRow row in ds.Tables[0].Rows)
    {
        color="";
        if (!string.IsNullOrEmpty(lineNumber) && Cast.String(row["LineNumber"]) == lineNumber)
            color = "red";
%>
                <tr style='color:<%= color %>'>
                    <td clss="nw" style="width: 40px;" align="center"><%=row["LineNumber"]%></td>
                    <td clss="nw" style='width: 40px;' align="center"><%=row["RefOrderLine"]%></td>
                    <td clss="nw" style="width: 70px;"><%=row["ItemCode"]%></td>
                    <td clss="nw"><%=row["ItemName"]%></td>
                    <td clss="nw" style="width: 50px;"><%=row["ColorCode"]%></td>
                    <td clss="nw" style="width: 50px;"><%=row["SizeCode"]%></td>
                    <td clss="nw" style="width: 60px;text-align:right;">
                        <%=row["AreaCode"]%>
                    </td>
                    <td clss="nw" style="width: 60px;text-align:right;">
                        <%=row["SectionCode"]%>
                    </td>
                    <td clss="nw" style="width: 80px;text-align:right;">
                        <%=RenderUtil.FormatNumber(row["QualifiedQty"], "#0.##")%>
                    </td>
                </tr>
<% 
    }
%>
        </table>
    </div>
<%
        }
    }
}
%>
    </form>
</body>
</html>