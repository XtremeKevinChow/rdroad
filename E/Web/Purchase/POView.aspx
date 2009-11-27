<%@ Page Language="C#" AutoEventWireup="true" %>
<%@ Import Namespace="System.Data" %>
<%@ Import Namespace="System.Collections.Generic" %>
<%@ Import Namespace="Magic.Basis" %>
<%@ Import Namespace="Magic.Framework.Utils" %>
<%@ Import Namespace="Magic.Framework.ORM" %>
<%@ Import Namespace="Magic.Framework.ORM.Query" %>
<%@ Import Namespace="Magic.ERP.Orders" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>采购订单查询</title>
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
    string OrderNumber = WebUtil.Param("ordNum");
    string LineNumber = WebUtil.Param("lineNum");
    if (!string.IsNullOrEmpty(OrderNumber))
    {
        POHead head = POHead.Retrieve(session, OrderNumber);
        if (head != null)
        {
            Magic.Sys.User createUser = null;
            if (head.CreateUser > 0) createUser = Magic.Sys.User.Retrieve(session, head.CreateUser);
            Magic.Sys.User approveUser = null;
            if (head.ApproveResult != Magic.ERP.ApproveStatus.UnApprove && head.ApproveUser > 0)
                approveUser = Magic.Sys.User.Retrieve(session, head.ApproveUser);
            Vendor vendor = null;
            if (head.VendorID > 0) vendor = Vendor.Retrieve(session, head.VendorID);
%>
    <div>
        <h4 style="margin:0;">采购订单<%= head.OrderNumber%></h4>
        <table class="queryArea" cellpadding="0" cellspacing="0" style=" margin-bottom:8px;width:95%;">
            <tr>
                <td class="label" style="width:70px;">状态：</td>
                <td style="width:75px;background-color:#fff;"><%= POHead.POStatusText(head.Status) %></td>
                <td class="label" style="width:70px;">采购组：</td>
                <td style="width:75px;background-color:#fff;"><%= head.PurchGroupCode %></td>
                <td class="label" style="width:80px;">创建者：</td>
                <td style="width:105px;background-color:#fff;"><%= createUser == null ? "" : createUser.FullName%></td>
                <td class="label" style="width:70px;">创建时间：</td>
                <td style="background-color:#fff;"><%= RenderUtil.FormatDatetime(head.CreateTime) %></td>
            </tr>
            <tr>
                <td class="label">签核结果：</td>
                <td style="background-color:#fff;"><%= POHead.ApproveStatusText(head.ApproveResult) %></td>
                <td class="label">签核人：</td>
                <td style="background-color:#fff;"><%= approveUser == null ? "" : approveUser.FullName%></td>
                <td class="label">签核时间：</td>
                <td style="background-color:#fff;"><%= head.ApproveResult== Magic.ERP.ApproveStatus.UnApprove?"": RenderUtil.FormatDatetime(head.ApproveTime) %></td>
                <td class="label">签核备注：</td>
                <td style="background-color:#fff;"><%= head.ApproveResult== Magic.ERP.ApproveStatus.UnApprove?"": head.ApproveNote%></td>
             </tr>
            <tr>
                <td class="label">含税总额：</td>
                <td style="background-color:#fff;"><%= RenderUtil.FormatNumber(head.TaxInclusiveAmt, "#0.#0", "0") %></td>
                <td class="label">税额：</td>
                <td style="background-color:#fff;"><%= RenderUtil.FormatNumber(head.TaxAmt, "#0.#0", "0")%></td>
                <td class="label">不含税总额：</td>
                <td style="background-color:#fff;"><%= RenderUtil.FormatNumber(head.TaxExclusiveAmt, "#0.#0", "0")%></td>
                <td class="label">供应商：</td>
                <td style="background-color:#fff;"><%=vendor==null?"":vendor.ShortName%></td>
             </tr>
            <tr>
                <td class="label">仓库：</td>
                <td style="background-color:#fff;"><%= head.LocationCode %></td>
                <td class="label">送货地址：</td>
                <td colspan="5" style="background-color:#fff;"><%= head.ShippingAddress %></td>
            </tr>
        </table>
        <table class="datalist2" cellpadding="0" cellspacing="0" style="width:95%;">
                <tr>
                    <td class="h nw">行号</td>
                    <td class="h nw">状态</td>
                    <td class="h nw">货号</td>
                    <td class="h nw">商品名称</td>
                    <td class="h nw">颜色</td>
                    <td class="h nw">尺码</td>
                    <td class="h nw">需求日期</td>
                    <td class="h nw">采购数量</td>
                    <td class="h nw">单价</td>
                    <td class="h nw">含税额</td>
                    <td class="h nw">税率</td>
                    <td class="h nw">税额</td>
                    <td class="h nw">不含税额</td>
                    <td class="h nw">收货日期</td>
                    <td class="h nw">收货数量</td>
                </tr>
<%
    DataSet ds = session.CreateObjectQuery(@"
select m.ItemCode as ItemCode,m.ItemName as ItemName,s.ColorCode as ColorCode,color.ColorText as ColorText,s.SizeCode as SizeCode
    ,p.LineNumber as LineNumber,p.LineStatus as LineStatus
    ,p.PlanDate as PlanDate,p.PurchaseQty as PurchaseQty
    ,p.Price as Price,p.TaxInclusiveAmt as TaxInclusiveAmt,t.TaxText as TaxText,p.TaxAmt as TaxAmt,p.TaxExlusiveAmt as TaxExlusiveAmt
    ,p.ActualDate as ActualDate,p.IQCQty as IQCQty
from POLine p
inner join ItemSpec s on p.SKUID=s.SKUID
inner join ItemMaster m on s.ItemID=m.ItemID
left join TaxDef t on t.TaxID=p.TaxID
left join ItemColor color on color.ColorCode=s.ColorCode
WHERE p.OrderNumber=?
order by p.LineNumber")
          .Attach(typeof(POLine)).Attach(typeof(ItemSpec)).Attach(typeof(ItemMaster)).Attach(typeof(TaxDef)).Attach(typeof(ItemColor))
          .SetValue(0, OrderNumber, "p.OrderNumber")
          .DataSet();
    string color = "", statusColor = "";
    foreach (DataRow row in ds.Tables[0].Rows)
    {
        color="";
        statusColor="";
        if (!string.IsNullOrEmpty(LineNumber) && Cast.String(row["LineNumber"]) == LineNumber)
        {
            color = "red";
        }
        else
        {
            Magic.ERP.POLineStatus status = Cast.Enum<Magic.ERP.POLineStatus>(row["LineStatus"]);
            switch (status)
            {
                case Magic.ERP.POLineStatus.Open:
                    statusColor = "blue";
                    break;
                case Magic.ERP.POLineStatus.Cancel:
                    statusColor = "gray";
                    break;
            }
        }
%>
                <tr style='color:<%= color %>'>
                    <td class="w" align="center"><%=row["LineNumber"]%></td>
                    <td class="w" style='color:<%=statusColor %>;' align="center">
                        <%=POLine.POLineStatusText(Cast.Enum<Magic.ERP.POLineStatus>(row["LineStatus"]))%>
                    </td>
                    <td class="w" ><%=row["ItemCode"]%></td>
                    <td class="w"><%=row["ItemName"]%></td>
                    <td class="w"><%=row["ColorCode"]%>&nbsp;<%=row["ColorText"]%></td>
                    <td class="w"><%=row["SizeCode"]%></td>
                    <td class="w"><%=RenderUtil.FormatDate(row["PlanDate"])%></td>
                    <td class="w" style="text-align:right; padding-right:4px;">
                        <%=RenderUtil.FormatNumber(row["PurchaseQty"], "#0.##")%>
                    </td>
                    <td class="w" style="text-align:right; padding-right:4px;">
                        <%=RenderUtil.FormatNumber(row["Price"], "#0.#0", "0")%>
                    </td>
                    <td class="w" style="text-align:right; padding-right:4px;">
                        <%=RenderUtil.FormatNumber(row["TaxInclusiveAmt"], "#0.#0", "0")%>
                    </td>
                    <td class="w" style="text-align:right;"><%=row["TaxText"]%></td>
                    <td class="w" style="text-align:right; padding-right:4px;">
                        <%=RenderUtil.FormatNumber(row["TaxAmt"], "#0.#0", "0")%>
                    </td>
                    <td class="w" style="text-align:right; padding-right:4px;">
                        <%=RenderUtil.FormatNumber(row["TaxExlusiveAmt"], "#0.#0", "0")%>
                    </td>
                    <td class="w"><%=RenderUtil.FormatDate(row["ActualDate"])%></td>
                    <td class="w" style="text-align:right; padding-right:4px;">
                        <%=RenderUtil.FormatNumber(row["IQCQty"], "#0.##")%>
                    </td>
                </tr>
<% 
    }
%>
        </table>
    <table border="0" cellpadding="0" cellspacing="0" id="cmdPrint" style="width:95%;"><tr>
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