<%@ Page Language="C#" AutoEventWireup="true" %>
<%@ Import Namespace="System.Data" %>
<%@ Import Namespace="System.Collections.Generic" %>
<%@ Import Namespace="Magic.Basis" %>
<%@ Import Namespace="Magic.Framework.Utils" %>
<%@ Import Namespace="Magic.Framework.ORM" %>
<%@ Import Namespace="Magic.Framework.Data" %>
<%@ Import Namespace="Magic.Framework.ORM.Query" %>
<%@ Import Namespace="Magic.ERP.Orders" %>
<%@ Import Namespace="Magic.ERP.Core" %>
<%@ Import Namespace="Magic.ERP.CRM" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>打印退货单</title>
    <link href="../CSS/queryPage.css" rel="Stylesheet" type="text/css" />
        <script type="text/javascript">
            function onprint() {
                document.getElementById("cmdPrint").style["display"] = "none";
                window.print();
                document.getElementById("cmdPrint").style["display"] = "";
            }
        </script>
</head>
<body>
    <form id="form1" runat="server">
<%
using (ISession session = new Session())
{
    string orderNumber = WebUtil.Param("ordNum");
    if (!string.IsNullOrEmpty(orderNumber))
    {
        ReturnHead head = ReturnHead.Retrieve(session, orderNumber);
        if (head != null)
        {
            Magic.Sys.User createUser = null;
            if (head.CreateUser > 0) createUser = Magic.Sys.User.Retrieve(session, head.CreateUser);
%>
    <div>
        <h5 style="margin:0;">退货单<%= head.OrderNumber %></h5>
        <table class="queryArea" cellpadding="0" cellspacing="0" style=" margin-bottom:8px;">
            <tr>
                <td class="label" style="width:68px;">创建者：</td>
                <td style="width:100px;background-color:#fff;"><%= createUser==null ? "" : createUser.FullName %></td>
                <td class="label" style="width:68px;">创建时间：</td>
                <td style="width:105px;background-color:#fff;"><%= RenderUtil.FormatDatetime(head.CreateTime) %></td>
                <td class="label" style="width:68px;">备注：</td>
                <td colspan="3" style="background-color:#fff;"><%= head.Note %></td>
            </tr>
            <tr>
                <td class="label">发货单号：</td>
                <td style="background-color:#fff;"><%= head.RefOrderNumber%></td>
                <td class="label">订单号：</td>
                <td style="background-color:#fff;"><%= head.OrginalOrderNumber%></td>
                <td class="label">会员姓名：</td>
                <td style="background-color:#fff; width:60px;"><%= head.MemberName%></td>
                <td class="label" style="width:70px;">物流公司：</td>
                <td style="background-color:#fff;"><%= head.LogisticsName%></td>
            </tr>
            <tr>
                <td class="label">退货原因：</td>
                <td colspan="8" style="background-color:#fff;"><%= head.ReasonText%></td>
            </tr>
        </table>
        <table class="datalist2" cellpadding="0" cellspacing="0">
                <tr>
                <td class="h" style="width:40px;">行号</td>
                <td class="h" style="width:85px;">货号</td>
                <td class="h">商品名称</td>
                <td class="h" style="width:110px;">颜色</td>
                <td class="h" style="width:50px;">尺码</td>
                <td class="h" style="width:60px;">销售方式</td>
                <td class="h" style="width:60px;">退货数量</td>
                <td class="h" style="width:50px;">单价</td>
                </tr>
<%
    DataSet ds = session.CreateObjectQuery(@"
select rtl.LineNumber as LineNumber,s.BarCode as BarCode,m.ItemCode as ItemCode,m.ItemName as ItemName
    ,s.ColorCode as ColorCode,color.ColorText as ColorText,s.SizeCode as SizeCode
    ,st.Name as SaleType,snl.Price as Price,snl.Quantity as ShippingQty,rtl.Quantity as ReturnQty
from ReturnHead rth
inner join CRMSNLine snl on rth.RefOrderID=snl.SNID
inner join ItemSpec s on s.SKUID=snl.SKUID
inner join ItemMaster m on m.ItemID=s.ItemID
inner join ReturnLine rtl on rtl.OrderNumber=rth.OrderNumber and rtl.RefOrderLineID=snl.ID
left join ItemColor color on color.ColorCode=s.ColorCode
left join CRMSaleType st on st.ID=snl.SellType
where rth.OrderNumber=?ordNum
order by rtl.LineNumber")
                    .Attach(typeof(ReturnHead)).Attach(typeof(ReturnLine))
                    .Attach(typeof(ItemSpec)).Attach(typeof(ItemMaster)).Attach(typeof(ItemColor))
                    .Attach(typeof(CRMSNLine)).Attach(typeof(CRMSaleType))
                    .SetValue("?ordNum", orderNumber, "rtl.OrderNumber")
                    .DataSet();
    foreach (DataRow row in ds.Tables[0].Rows)
    {
%>
                <tr>
                    <td align="center"><%=row["LineNumber"]%></td>
                    <td><%=row["ItemCode"]%></td>
                    <td><%=row["ItemName"]%></td>
                    <td><%=row["ColorCode"]%>&nbsp;<%=row["ColorText"]%></td>
                    <td><%=row["SizeCode"]%></td>
                    <td><%=row["SaleType"]%></td>
                    <td style="text-align:right;padding-right:4px;">
                        <%=RenderUtil.FormatNumber(row["ReturnQty"], "#0.##")%>
                    </td>
                    <td style="text-align:right;padding-right:4px;">
                        <%=RenderUtil.FormatNumber(row["Price"], "#0.#0")%>
                    </td>
                </tr>
<% 
    }
%>
        </table>
        
    <table border="0" cellpadding="0" cellspacing="0" id="cmdPrint"><tr>
        <td style="width:40%;"></td>
        <td><input type="button" value="打印" onclick="onprint();" />&nbsp;&nbsp;&nbsp;&nbsp;
        <input type="button" value="关闭" onclick="window.close();" /></td>
        <td style="width:40%;"></td>
    </tr></table>
    </div>
<%
        }
    }
}
%>
    </form>
</body>
</html>