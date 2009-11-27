<%@ page language="C#" ClassName="StockAdjustmentView"%>
<%@ Import Namespace="Magic.Framework.Utils" %>
<%@ Import Namespace="Magic.Framework.ORM" %>
<%@ Import Namespace="Magic.Framework.ORM.Query" %>
<%@ Import Namespace="Magic.ERP" %>
<%@ Import Namespace="Magic.ERP.Core" %>
<%@ Import Namespace="Magic.ERP.Orders" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<script runat="server">
        private string m_StatusText = "";
        private INVCheckHead m_Head = null;
        private Magic.Sys.User m_User = null;

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                string strOrderNumber = WebUtil.Param("ORDNUM");
                if ((strOrderNumber == null) || (strOrderNumber.Trim().Length == 0))
                {
                }
                else
                {
                    using (ISession _session = new Session())
                    {
                        this.m_Head = INVCheckHead.Retrieve(_session, strOrderNumber);
                        this.m_User = Magic.Sys.User.Retrieve(_session, this.m_Head.CreateUser);
                        this.m_StatusText = ERPUtil.StatusText(_session, INVCheckHead.ORDER_TYPE_ADJ, this.m_Head.Status);
                        this.LoadInvCheck_Line(_session, strOrderNumber);
                    }
                }
            }
        }

        private void LoadInvCheck_Line(ISession _session, string OrderNumber)
        {
            string type = WebUtil.Param("t").ToLower();
            if (type != "all" && type != "diff")
                type = "diff";
            ObjectQuery query = _session.CreateObjectQuery(string.Format(@"
SELECT 
    L.Name AS LocationName,L.LocationCode AS LocationCode
    ,R.Name AS AreaName,R.AreaCode AS AreaCode
    ,S.Text AS SectionName,S.SectionCode AS SectionCode
    ,B.BarCode as BarCode,C.ItemCode as ItemCode,C.ItemName AS ItemName,E.SizeText AS SizeText,D.ColorText AS ColorText
    ,A.BeforeQty AS BeforeQty,A.CurrentQty AS CurrentQty
FROM INVCheckLine A
inner JOIN ItemSpec B ON A.SKUID=B.SKUID
inner JOIN ItemMaster C ON B.ItemID=C.ItemID
LEFT JOIN ItemColor D ON B.ColorCode=D.ColorCode 
LEFT JOIN ItemSize E ON B.SizeCode=E.SizeCode
inner JOIN WHLocation L ON A.LocationCode=L.LocationCode
inner JOIN WHArea R ON A.AreaCode=R.AreaCode
LEFT JOIN WHSection S ON A.SectionCode=S.SectionCode
WHERE A.OrderNumber=? {0}
order by A.LineNumber", type == "diff" ? "and A.BeforeQty<>A.CurrentQty" : ""))
            .Attach(typeof(Magic.ERP.Orders.INVCheckLine))
            .Attach(typeof(Magic.Basis.ItemSpec))
            .Attach(typeof(Magic.Basis.ItemMaster))
            .Attach(typeof(Magic.Basis.ItemColor))
            .Attach(typeof(Magic.Basis.ItemSize))
            .Attach(typeof(Magic.ERP.Core.WHLocation))
            .Attach(typeof(Magic.ERP.Core.WHArea))
            .Attach(typeof(Magic.ERP.Core.WHSection))
            .SetValue(0, OrderNumber, "A.OrderNumber");

            this.rptSDLine.DataSource = query.DataSet();
            this.rptSDLine.DataBind();
        }
        private string DiffQtyString(object bef, object cur)
        {
            decimal befQty = Cast.Decimal(bef, 0M);
            decimal curQty = Cast.Decimal(cur, 0M);
            string s = curQty > befQty ? "盘盈&nbsp;&nbsp;" : curQty < befQty ? "盘亏&nbsp;&nbsp;" : "";
            s = s + RenderUtil.FormatNumber(curQty - befQty, "#0.##");
            return s;
        }
    </script>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>查看盘点资料</title>
        <link href="../CSS/queryPage.css" rel="Stylesheet" type="text/css" />
    <link href="../CSS/popups.css" rel="stylesheet" type="text/css" />
</head>
<body>
    <table class="datalist">
        <tr>
            <td colspan="5" style="font-weight:bold;">盘点信息</td>
        </tr>
        <tr>
            <td style="width:15%">盘点单编号</td>
            <td style="width:25%;border-bottom-color:Black;border-bottom-width:1px;"><%= m_Head == null ? "" : m_Head.OrderNumber%></td>
            <td></td>
            <td style="width:10%">状态</td>
            <td style="width:23%;border-bottom-color:Black;border-bottom-width:1px;"><%=m_StatusText%></td>

        </tr>
        <tr>
            <td>创建者</td>
            <td style="border-bottom-color:Black;border-bottom-width:1px;"><%= this.m_User == null ? "" : m_User.FullName%></td>          
            <td></td>  
            <td style="width:15%">创建时间</td>
            <td style="width:25%;border-bottom-color:Black;border-bottom-width:1px;"><%= m_Head == null ? "" : RenderUtil.FormatDatetime(m_Head.CreateTime)%></td>
        </tr>
        <tr>
            <td>备注</td>
            <td colspan="4" style="border-bottom-color:Black;border-bottom-width:1px;"><%= m_Head == null ? "" : m_Head.Note%></td>
        </tr>
    </table>
<br />

    <table class="datalist">
        <tr>
            <td colspan="10" style="font-weight:bold">盘点明细</td>
        </tr>
        <tr style="text-align:center;font-weight:bold;">
            <td style="width:70px;">仓库</td>
            <td style="width:60px;">库位</td>
            <td style="width:50px;">货架</td>
            <td style="width:80px;">货号</td>
            <td>物料名称</td>
            <td style="width:70px;">颜色</td>
            <td style="width:50px;">尺寸</td>
            <td style="width:80px;">盘点前数量</td>
            <td style="width:80px;">盘点数量</td>
            <td style="width:120px;">差异</td>
        </tr>
        <asp:Repeater ID="rptSDLine" runat="server">
            <ItemTemplate>
                <tr>
                    <td id="tdLineNumber" runat="server">
                        <%# RenderUtil.FormatString(Eval("LocationName"))%>
                    </td>
                    <td>
                        <%# RenderUtil.FormatString(Eval("AreaName"))%>
                    </td>
                    <td>
                        <%# RenderUtil.FormatString(Eval("SectionCode"))%>
                    </td>
                    <td>
                        <%# RenderUtil.FormatString(Eval("ItemCode"))%>
                    </td>
                    <td>
                        <%# RenderUtil.FormatString(Eval("ItemName"))%>
                    </td>
                    <td>
                        <%# RenderUtil.FormatString(Eval("ColorText"))%>
                    </td>
                    <td>
                        <%# RenderUtil.FormatString(Eval("SizeText"))%>
                    </td>
                    <td style="text-align:right">
                        <%# RenderUtil.FormatString(Eval("BeforeQty"))%>
                    </td>
                    <td style="text-align:right">
                        <%# RenderUtil.FormatString(Eval("CurrentQty"))%>
                    </td>
                    <td style="text-align:right">
                        <%# this.DiffQtyString(Eval("BeforeQty"), Eval("CurrentQty"))%>
                    </td>
                </tr>
            </ItemTemplate>
        </asp:Repeater>
    </table>
</body>
</html>
