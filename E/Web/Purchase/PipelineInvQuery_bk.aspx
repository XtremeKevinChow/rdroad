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
    <title>在途库存（在途采购量）查询</title>
    <link href="/CSS/queryPage.css" rel="Stylesheet" type="text/css" />
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
        <h4 style="margin:0;">在途库存（在途采购量）查询</h4>
        <table class="queryArea" cellpadding="0" cellspacing="0" style=" margin-bottom:8px;">
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
        </table>
        <table class="datalist" id="data_list_table" cellpadding="0" cellspacing="0">
            <thead>
                <tr>
                    <th>行号</th>
                    <th>状态</th>
                    <th>货号</th>
                    <th>商品名称</th>
                    <th>颜色</th>
                    <th>尺码</th>
                    <th>需求日期</th>
                    <th>采购数量</th>
                    <th>单价</th>
                    <th>含税额</th>
                    <th>税率</th>
                    <th>税额</th>
                    <th>不含税额</th>
                    <th>收货日期</th>
                    <th>收货数量</th>
                </tr>
            </thead>
            <tbody>
<%
    DataSet ds = session.CreateObjectQuery(@"
select m.ItemCode as ItemCode,m.ItemName as ItemName,s.ColorCode as ColorCode,s.SizeCode as SizeCode
    ,p.LineNumber as LineNumber,p.LineStatus as LineStatus
    ,p.PlanDate as PlanDate,p.PurchaseQty as PurchaseQty
    ,p.Price as Price,p.TaxInclusiveAmt as TaxInclusiveAmt,t.TaxText as TaxText,p.TaxAmt as TaxAmt,p.TaxExlusiveAmt as TaxExlusiveAmt
    ,p.ActualDate as ActualDate,p.IQCQty as IQCQty
from POLine p
inner join ItemSpec s on p.SKUID=s.SKUID
inner join ItemMaster m on s.ItemID=m.ItemID
left join TaxDef t on t.TaxID=p.TaxID
WHERE p.OrderNumber=?
order by p.LineNumber")
          .Attach(typeof(POLine)).Attach(typeof(ItemSpec)).Attach(typeof(ItemMaster)).Attach(typeof(TaxDef))
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
                    <td style="width: 35px;" align="center"><%=row["LineNumber"]%></td>
                    <td style='width: 40px;color:<%=statusColor %>;' align="center">
                        <%=POLine.POLineStatusText(Cast.Enum<Magic.ERP.POLineStatus>(row["LineStatus"]))%>
                    </td>
                    <td style="width: 70px;"><%=row["ItemCode"]%></td>
                    <td><%=row["ItemName"]%></td>
                    <td style="width: 40px;"><%=row["ColorCode"]%></td>
                    <td style="width: 40px;"><%=row["SizeCode"]%></td>
                    <td style="width: 70px;"><%=RenderUtil.FormatDate(row["PlanDate"])%></td>
                    <td style="width: 50px; text-align:right;">
                        <%=RenderUtil.FormatNumber(row["PurchaseQty"], "#0.##")%>
                    </td>
                    <td style="width: 45px; text-align:right;">
                        <%=RenderUtil.FormatNumber(row["Price"], "#0.#0", "0")%>
                    </td>
                    <td style="width: 70px; text-align:right;">
                        <%=RenderUtil.FormatNumber(row["TaxInclusiveAmt"], "#0.#0", "0")%>
                    </td>
                    <td style="width: 40px; text-align:right;"><%=row["TaxText"]%></td>
                    <td style="width: 60px; text-align:right;">
                        <%=RenderUtil.FormatNumber(row["TaxAmt"], "#0.#0", "0")%>
                    </td>
                    <td style="width: 70px; text-align:right;">
                        <%=RenderUtil.FormatNumber(row["TaxExlusiveAmt"], "#0.#0", "0")%>
                    </td>
                    <td style="width: 70px;"><%=RenderUtil.FormatDate(row["ActualDate"])%></td>
                    <td style="width: 50px; text-align:right;">
                        <%=RenderUtil.FormatNumber(row["IQCQty"], "#0.##")%>
                    </td>
                </tr>
<% 
    }
%>
            </tbody>
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