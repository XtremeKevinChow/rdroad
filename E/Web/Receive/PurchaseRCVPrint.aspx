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
    <title>打印采购收货单</title>
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
        RCVHead head = RCVHead.Retrieve(session, orderNumber);
        Vendor vendor = null;
        if (head != null)
        {
            Magic.Sys.User createUser = null;
            if (head.CreateUser > 0) createUser = Magic.Sys.User.Retrieve(session, head.CreateUser);
            Magic.Sys.User approveUser = null;
            if (head.ApproveResult != Magic.ERP.ApproveStatus.UnApprove && head.ApproveUser > 0)
                approveUser = Magic.Sys.User.Retrieve(session, head.ApproveUser);
            OrderStatusDef statusDef = OrderStatusDef.Retrieve(session, head.OrderTypeCode, (int)head.Status);
            if (head.ObjectID > 0)
                vendor = Vendor.Retrieve(session, head.ObjectID);
%>
    <div>
        <h5 style="margin:0;">收货单<%= head.OrderNumber %></h5>
        <table class="queryArea" cellpadding="0" cellspacing="0" style=" margin-bottom:8px;">
            <tr>
                <td class="label" style="width:70px;">创建者：</td>
                <td style="width:100px;background-color:#fff;"><%= createUser==null ? "" : createUser.FullName %></td>
                <td class="label" style="width:80px;">创建时间：</td>
                <td style="width:105px;background-color:#fff;"><%= RenderUtil.FormatDatetime(head.CreateTime) %></td>
                <td class="label" style="width:70px;">备注：</td>
                <td style="background-color:#fff;"><%= head.Note %></td>
            </tr>
            <tr>
                <td class="label">采购订单：</td>
                <td style="background-color:#fff;"><%= head.RefOrderNumber%></td>
                <td class="label" style="width:80px;">供应商：</td>
                <td colspan="3" style="background-color:#fff;"><%= vendor==null?"":vendor.ShortName%></td>
            </tr>
        </table>
        <table class="datalist2" cellpadding="0" cellspacing="0">
                <tr>
                    <td style="width: 40px;" class="h">行号</td>
                    <td style='width: 50px;' class="h">PO行号</td>
                    <td style="width: 70px;" class="h">货号</td>
                    <td class="h">商品名称</td>
                    <td style="width: 90px;" class="h">颜色</td>
                    <td style="width: 50px;" class="h">尺码</td>
                    <td style="width: 60px;" class="h">收货数量</td>
                    <td style="width: 60px;" class="h">单价</td>
                    <td style="width: 80px;" class="h">金额</td>
                </tr>
<%
    DataSet ds = session.CreateObjectQuery(@"
select m.ItemCode as ItemCode,m.ItemName as ItemName,s.ColorCode as ColorCode,color.ColorText as ColorText,s.SizeCode as SizeCode
    ,l.LineNumber as LineNumber,l.RefOrderLine as RefOrderLine
    ,l.QualifiedQty as QualifiedQty,l.Price as Price,l.TaxValue as TaxValue
from RCVLine l
inner join ItemSpec s on l.SKUID=s.SKUID
inner join ItemMaster m on s.ItemID=m.ItemID
left join ItemColor color on s.ColorCode=color.ColorCode
WHERE l.OrderNumber=?
order by l.LineNumber")
          .Attach(typeof(RCVLine)).Attach(typeof(ItemSpec)).Attach(typeof(ItemMaster)).Attach(typeof(ItemColor))
          .SetValue(0, orderNumber, "l.OrderNumber")
          .DataSet();
    decimal totalAmt = 0M, amt=0M;
    foreach (DataRow row in ds.Tables[0].Rows)
    {
        amt = Cast.Decimal(row["QualifiedQty"]) * Cast.Decimal(row["Price"]) / (1 + Cast.Decimal(row["TaxValue"]));
        totalAmt += amt;
%>
                <tr>
                    <td align="center"><%=row["LineNumber"]%></td>
                    <td align="center"><%=row["RefOrderLine"]%></td>
                    <td><%=row["ItemCode"]%></td>
                    <td><%=row["ItemName"]%></td>
                    <td><%=row["ColorCode"]%>&nbsp;<%=row["ColorText"]%></td>
                    <td><%=row["SizeCode"]%></td>
                    <td style="text-align:right; padding-right:4px;">
                        <%=RenderUtil.FormatNumber(row["QualifiedQty"], "##")%>
                    </td>
                    <td style="text-align:right; padding-right:4px;">
                        <%=RenderUtil.FormatNumber(row["Price"], "#0.#0")%>
                    </td>
                    <td style="text-align:right; padding-right:4px;">
                        <%=RenderUtil.FormatNumber(amt, "#0.#0")%>
                    </td>
                </tr>
<% 
    }
%>
            <tr>
                <td colspan="8" style="text-align:right;">合计:&nbsp;&nbsp;</td>
                <td style="text-align:right;"><%= RenderUtil.FormatNumber(totalAmt, "#0.#0") %>&nbsp;</td>
            </tr>
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