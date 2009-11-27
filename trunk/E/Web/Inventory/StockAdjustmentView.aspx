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
                //string strOrderNumber = "SD_20080708_001";
                if ((strOrderNumber == null) || (strOrderNumber.Trim().Length == 0))
                {
                    //this.InitialPage();
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
            ObjectQuery query = _session.CreateObjectQuery(@"
SELECT A.BeforeQty AS BeforeQty,A.CurrentQty AS CurrentQty,C.ItemCode AS ItemCode,B.SKUID AS SKUID,
        C.ItemName AS ItemName,E.SizeText AS SizeText,D.ColorText AS ColorText,
        L.Name AS LocationName,R.Name AS AreaName,S.Text AS SectionName,L.LocationCode AS LocationCode,
        R.AreaCode AS AreaCode,S.SectionCode AS SectionCode
FROM INVCheckLine A
LEFT JOIN ItemSpec B ON A.SKUID=B.SKUID
LEFT JOIN ItemMaster C ON B.ItemID=C.ItemID
LEFT JOIN ItemColor D ON B.ColorCode=D.ColorCode 
LEFT JOIN ItemSize E ON B.SizeCode=E.SizeCode
LEFT JOIN WHLocation L ON A.LocationCode=L.LocationCode
LEFT JOIN WHArea R ON A.AreaCode=R.AreaCode
LEFT JOIN WHSection S ON A.SectionCode=S.SectionCode
WHERE A.OrderNumber=?
")
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
    </script>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Untitled Page</title>
        <link href="../CSS/queryPage.css" rel="Stylesheet" type="text/css" />
    <link href="../CSS/popups.css" rel="stylesheet" type="text/css" />
</head>
<body>
    <table class="datalist">
        <tr>
            <td colspan="5" style="font-weight:bold">库存盘点信息</td>
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
            <td style="width:25%;border-bottom-color:Black;border-bottom-width:1px;"><%= m_Head == null ? "" : Cast.String(m_Head.CreateTime)%></td>
        </tr>
        <tr>
            <td>备注</td>
            <td colspan="4" style="border-bottom-color:Black;border-bottom-width:1px;"><%= m_Head == null ? "" : m_Head.Note%></td>
        </tr>
    </table>
<br />

    <table class="datalist">
        <tr>
            <td colspan="9" style="font-weight:bold">销售发货单明细</td>
        </tr>
        <tr>
            <td style="width:20%;font-weight:bold">
                仓库
            </td>
            <td style="width:20%;font-weight:bold">
                区域
            </td>
            <td style="width:20%;font-weight:bold">
                物料名称
            </td>
            <td style="font-weight:bold">
                尺寸
            </td>
            <td style="font-weight:bold">
                颜色
            </td>
            <td style="font-weight:bold">
                当前数量
            </td>
            <td style="font-weight:bold">
                实际数量
            </td>
        </tr>
        <asp:Repeater ID="rptSDLine" runat="server">
            <ItemTemplate>
                <tr>
                    <td id="tdLineNumber" runat="server">
                        <%# RenderUtil.FormatString(Eval("LOCATIONNAME"))%>
                    </td>
                    <td>
                        <%# RenderUtil.FormatString(Eval("AreaName"))%>
                    </td>
                    <td>
                        <%# RenderUtil.FormatString(Eval("ItemName"))%>
                    </td>
                    <td>
                        <%# RenderUtil.FormatString(Eval("SizeText"))%>
                    </td>
                    <td>
                        <%# RenderUtil.FormatString(Eval("ColorText"))%>
                    </td>
                    <td style="text-align:right">
                        <%# RenderUtil.FormatString(Eval("BeforeQty"))%>
                    </td>
                    <td style="text-align:right">
                        <%# RenderUtil.FormatString(Eval("CurrentQty"))%>
                    </td>
                </tr>
            </ItemTemplate>
        </asp:Repeater>
    </table>
</body>
</html>
