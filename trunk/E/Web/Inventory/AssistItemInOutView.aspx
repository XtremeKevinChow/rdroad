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
    <title>单据详细资料</title>
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
string lineNumber = WebUtil.Param("lineNum");
if (!string.IsNullOrEmpty(orderNumber) && orderNumber.Trim().Length > 0)
{
    using (ISession session = new Session())
    {
        StockInHead head = StockInHead.Retrieve(session, orderNumber);
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
        <h4 style="margin:0;">
            <%=head.OrderTypeCode == StockInHead.ORD_TYPE_ASSIST_IN?"辅料入库单":"辅料领用单"%>
            <%= head.OrderNumber%>
        </h4>
        <table class="queryArea" cellpadding="0" cellspacing="0" style=" margin-bottom:8px;">
            <tr>
                <td class="label" style="width:70px;">状态：</td>
                <td style="width:75px;background-color:#fff;"><%= statusDef == null ? "" : statusDef.StatusText%></td>
                <td class="label" style="width:70px;">创建者：</td>
                <td style="width:75px;background-color:#fff;"><%= createUser == null ? "" : createUser.FullName%></td>
                <td class="label" style="width:80px;">创建时间：</td>
                <td style="width:105px;background-color:#fff;"><%= RenderUtil.FormatDatetime(head.CreateTime)%></td>
                <td class="label" style="width:70px;">备注：</td>
                <td style="background-color:#fff;"><%= head.Note%></td>
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
                    <td class="h" style='width: 100px;'>SKU</td>
                    <td class="h" style='width: 100px;'>货号</td>
                    <td class="h" style="">商品名称</td>
                    <td class="h" style='width: 60px;'>库位</td>
                    <td class="h" style='width: 90px;'>货架</td>
                    <td class="h" style='width: 80px;'>数量</td>
                    <td class="h" style='width: 80px;'>价格</td>
                </tr>
<%
    DataSet ds = session.CreateObjectQuery(@"
select s.BarCode as BarCode,m.ItemCode as ItemCode,m.ItemName as ItemName
    ,l.LineNumber as LineNumber,l.AreaCode as AreaCode,l.SectionCode as SectionCode,l.Quantity as Quantity,l.Price as Price
from StockInLine l
inner join ItemSpec s on l.SKUID=s.SKUID
inner join ItemMaster m on s.ItemID=m.ItemID
WHERE l.OrderNumber=?
order by l.LineNumber")
          .Attach(typeof(StockInLine)).Attach(typeof(ItemSpec)).Attach(typeof(ItemMaster))
          .SetValue(0, orderNumber, "l.OrderNumber")
          .DataSet();
    string color = "";
    foreach (DataRow row in ds.Tables[0].Rows)
    {
        color = "";
        if (!string.IsNullOrEmpty(lineNumber) && Cast.String(row["LineNumber"]) == lineNumber)
            color = "red";
%>
                <tr style='color:<%= color %>'>
                    <td style="width: 40px;" align="center"><%=row["LineNumber"]%></td>
                    <td style='width: 100px;'><%=row["BarCode"]%></td>
                    <td style='width: 100px;'><%=row["ItemCode"]%></td>
                    <td><%=row["ItemName"]%></td>
                    <td style="width: 60px;"><%=RenderUtil.FormatString(row["AreaCode"])%></td>
                    <td style="width: 90px;"><%=RenderUtil.FormatString(row["SectionCode"])%>&nbsp;</td>
                    <td style="width: 80px;text-align:right;"><%=RenderUtil.FormatNumber(row["Quantity"], "#0.##")%></td>
                    <td style="width: 80px;text-align:right;"><%=RenderUtil.FormatNumber(row["Price"], "#0.##")%></td>
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