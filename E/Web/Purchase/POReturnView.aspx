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
    <title>采购退货单</title>
    <link href="../CSS/queryPage.css" rel="Stylesheet" type="text/css" />
        <style type="text/css">
            .detaillist
            {
            	border-left:solid 1px #000;
            	border-top:solid 1px #000;
            }
            .detaillist tr td
            {
            	text-indent:3px;
            	border-right:solid 1px #000;
            	border-bottom:solid 1px #000;
            }
        </style>
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
string orderNumber = WebUtil.Param("ordNum");
if (!string.IsNullOrEmpty(orderNumber) && orderNumber.Trim().Length > 0)
{
    using (ISession session = new Session())
    {
        POReturnHead head = POReturnHead.Retrieve(session, orderNumber);
        if (head != null)
        {
            Magic.Sys.User createUser = null;
            if (head.CreateUser > 0) createUser = Magic.Sys.User.Retrieve(session, head.CreateUser);
            Magic.Sys.User approveUser = null;
            if (head.ApproveResult != Magic.ERP.ApproveStatus.UnApprove && head.ApproveUser > 0)
                approveUser = Magic.Sys.User.Retrieve(session, head.ApproveUser);
            OrderStatusDef statusDef = OrderStatusDef.Retrieve(session, head.OrderTypeCode, (int)head.Status);
            Vendor vendor = Vendor.Retrieve(session, head.VendorID);
%>
    <div>
        <h4 style="margin:0;">采购退货单<%= head.OrderNumber%></h4>
        <table class="queryArea" cellpadding="0" cellspacing="0" style=" margin-bottom:8px;">
            <tr>
                <td class="label" style="width:70px;">状态：</td>
                <td style="width:75px;background-color:#fff;"><%= statusDef == null ? "" : statusDef.StatusText%></td>
                <td class="label" style="width:70px;">创建者：</td>
                <td style="width:75px;background-color:#fff;"><%= createUser == null ? "" : createUser.FullName%></td>
                <td class="label" style="width:80px;">创建时间：</td>
                <td style="width:105px;background-color:#fff;"><%= RenderUtil.FormatDatetime(head.CreateTime)%></td>
                <td class="label" style="width:70px;">供应商：</td>
                <td style="background-color:#fff;"><%= vendor==null?"&nbsp;":vendor.ShortName %>&nbsp;</td>
            </tr>
            <tr>
                <td class="label">签核结果：</td>
                <td style="background-color:#fff;"><%= POHead.ApproveStatusText(head.ApproveResult)%></td>
                <td class="label">签核人：</td>
                <td style="background-color:#fff;"><%= approveUser == null ? "" : approveUser.FullName%></td>
                <td class="label">签核时间：</td>
                <td style="background-color:#fff;"><%= head.ApproveResult == Magic.ERP.ApproveStatus.UnApprove ? "" : RenderUtil.FormatDatetime(head.ApproveTime)%></td>
                <td class="label">签核备注：</td>
                <td style="background-color:#fff;"><%= head.ApproveResult == Magic.ERP.ApproveStatus.UnApprove ? "" : head.ApproveNote%></td>
             </tr>
        </table>
        <table class="datalist2" cellpadding="0" cellspacing="0">
                <tr>
                    <td class="h" style="width: 40px;">行号</td>
                    <td class="h" style='width: 100px;'>PO</td>
                    <td class="h" style='width: 40px;'>PO行</td>
                    <td class="h" style='width: 100px;'>SKU</td>
                    <td class="h" style='width: 70px;'>货号</td>
                    <td class="h" style='width: 90px;'>颜色</td>
                    <td class="h" style='width: 40px;'>尺码</td>
                    <td class="h" style="">商品名称</td>
                    <td class="h" style='width: 40px;'>库位</td>
                    <td class="h" style='width: 80px;'>货架</td>
                    <td class="h" style='width: 50px;'>数量</td>
                    <td class="h" style='width: 50px;'>价格</td>
                    <td class="h" style='width: 50px;'>总金额</td>
                </tr>
<%
    DataSet ds = session.CreateObjectQuery(@"
select l.LineNumber as LineNumber,l.PONumber as PONumber,l.POLine as POLine
    ,s.BarCode as BarCode,m.ItemCode as ItemCode,m.ItemName as ItemName,s.ColorCode as ColorCode,s.SizeCode as SizeCode
    ,sto.AreaCode as AreaCode,sto.SectionCode as SectionCode,l.Quantity as Quantity,l.Price as Price
    ,color.ColorText as ColorText
from POReturnLine l
inner join StockDetail sto on sto.StockDetailID=l.StockDetailID
inner join ItemSpec s on l.SKUID=s.SKUID
inner join ItemMaster m on s.ItemID=m.ItemID
left join ItemColor color on color.ColorCode=s.ColorCode
WHERE l.OrderNumber=?
order by l.LineNumber")
          .Attach(typeof(POReturnLine)).Attach(typeof(ItemSpec)).Attach(typeof(ItemMaster)).Attach(typeof(ItemColor))
          .Attach(typeof(StockDetail))
          .SetValue(0, orderNumber, "l.OrderNumber")
          .DataSet();
    decimal totalAmt = 0M;
    foreach (DataRow row in ds.Tables[0].Rows)
    {
        decimal lineAmt = Cast.Decimal(row["Quantity"]) * Cast.Decimal(row["Price"]);
        totalAmt += lineAmt;
%>
                <tr>
                    <td align="center"><%=row["LineNumber"]%></td>
                    <td><%=row["PONumber"]%></td>
                    <td><%=row["POLine"]%></td>
                    <td><%=row["BarCode"]%></td>
                    <td><%=row["ItemCode"]%></td>
                    <td><%=row["ColorCode"]%>&nbsp;<%=row["ColorText"]%></td>
                    <td><%=row["SizeCode"]%></td>
                    <td><%=row["ItemName"]%></td>
                    <td><%=RenderUtil.FormatString(row["AreaCode"])%></td>
                    <td><%=RenderUtil.FormatString(row["SectionCode"])%>&nbsp;</td>
                    <td style="text-align:right;padding-right:4px;"><%=RenderUtil.FormatNumber(row["Quantity"], "#0.##")%></td>
                    <td style="text-align:right;padding-right:4px;"><%=RenderUtil.FormatNumber(row["Price"], "#0.##")%></td>
                    <td style="text-align:right;padding-right:4px;"><%=RenderUtil.FormatNumber(lineAmt, "#0.##")%></td>
                </tr>
<% 
    }
%>
            <tr>
                <td colspan="12">汇总金额</td>
                <td style="text-align:right;padding-right:4px;"><%=RenderUtil.FormatNumber(totalAmt, "#0.##")%></td>
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