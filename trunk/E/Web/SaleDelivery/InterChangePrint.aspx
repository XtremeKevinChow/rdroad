<%@ page language="C#" ClassName="InterChangePrint"%>
<%@ Import Namespace="Magic.Framework.Utils" %>
<%@ Import Namespace="Magic.Framework.ORM" %>
<%@ Import Namespace="Magic.Framework.ORM.Query" %>
<%@ Import Namespace="Magic.ERP" %>
<%@ Import Namespace="Magic.ERP.Core" %>
<%@ Import Namespace="Magic.ERP.Orders" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    <script runat="server">
        private string m_StatusText = "";
        private ICHead m_Head = null;
        private Magic.Basis.Logistics m_Logistic = null;
        private Magic.Sys.User m_User = null;
        private decimal totalAgentAmt = 0M;
        private int totalPackageCount = 0;

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                string strOrderNumber = WebUtil.Param("ordNum");
                if ((strOrderNumber == null) || (strOrderNumber.Trim().Length == 0))
                {
                    Response.End();
                }
                else
                {
                    using (ISession _session = new Session())
                    {
                        this.m_Head = ICHead.Retrieve(_session, strOrderNumber);
                        if (this.m_Head == null)
                        {
                            Response.Write("没有找到编号为" + strOrderNumber + "的交接单!");
                            Response.End();
                        }
                        this.m_User = Magic.Sys.User.Retrieve(_session, this.m_Head.CompanyUser);
                        this.m_Logistic = Magic.Basis.Logistics.Retrieve(_session, this.m_Head.LogisticCompID);
                        this.m_StatusText = ERPUtil.StatusText(_session, CRMSN.ORDER_TYPE_CODE_SD, this.m_Head.Status);
                        this.LoadSD_Line(_session, strOrderNumber);
                        this.totalPackageCount = this.m_Head.TotalPackageCount(_session);
                    }
                }
            }
        }

        private void LoadSD_Line(ISession _session, string OrderNumber)
        {
            ObjectQuery query = _session.CreateObjectQuery(@"
SELECT 
    A.OrderNumber AS OrderNumber,A.ShippingNumber as ShippingNumber,A.SaleOrderNumber AS SaleOrderNumber
    ,A.InvoiceNumber as InvoiceNumber,A.PackageCount as PackageCount,A.PackageWeight as PackageWeight
    ,A.Contact as Contact,A.Province as Province,A.City as City,A.Address as Address,A.Mobile as Mobile,A.Phone as Phone
    ,A.AgentAmt as AgentAmt
FROM ICLine L
inner JOIN CRMSN A ON L.RefOrderNumber=A.OrderNumber
LEFT JOIN Member E ON A.MemberID= E.MemberID
order by L.LineNumber
")
            .Attach(typeof(Magic.ERP.Orders.ICLine))
            .Attach(typeof(Magic.ERP.Orders.CRMSN))
            .Attach(typeof(Magic.Basis.Member))
            .And(Exp.Eq("L.OrderNumber", OrderNumber.Trim()));

            System.Data.DataSet ds = query.DataSet();
            foreach (System.Data.DataRow row in ds.Tables[0].Rows)
                this.totalAgentAmt += Cast.Decimal(row["AgentAmt"], 0M);

            this.rptDLV.DataSource = ds;
            this.rptDLV.DataBind();
        }
    </script>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>打印交接单</title>
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
        function onprint(){
            document.getElementById("cmdPrint").style["display"]="none";
            window.print();
            document.getElementById("cmdPrint").style["display"]="";
        }
        </script>
</head>
<body style="margin-top:0;margin-bottom:0;margin-left:2px;margin-right:6px;padding:0;">
    <table cellpadding="0" cellspacing="0" style="width:49%;float:left;">
        <tr>
            <td colspan="6" style="font-weight:bold">配送商信息</td>
        </tr>
        <tr>
            <td class="label">配送商名称：</td>
            <td colspan="4" style="border-bottom:solid 1px #000;"><%= m_Logistic == null ? "&nbsp;" : RenderUtil.FormatString(m_Logistic.FullName)%></td>
            <td></td>
        </tr>
        <tr>
            <td class="label">地址：</td>
            <td colspan="4" style="border-bottom:solid 1px #000;"><%= m_Logistic == null ? "&nbsp;" : RenderUtil.FormatString(m_Logistic.Address)%></td>
            <td></td>
        </tr>
        <tr>
            <td style="width:100px;" class="label">联系人：</td>
            <td style="width:120px;border-bottom:solid 1px #000;"><%= m_Logistic == null ? "&nbsp;" : RenderUtil.FormatString(m_Logistic.Contact)%></td>
            <td style="width:40px;"></td>
            <td style="width:50px;" class="label">邮编：</td>
            <td style="width:120px;border-bottom:solid 1px #000;" ><%= m_Logistic == null ? "&nbsp;" : RenderUtil.FormatString(m_Logistic.ZipCode)%></td>
            <td></td>
        </tr>
        <tr>
            <td class="label">电话：</td>
            <td style="border-bottom:solid 1px #000;"><%= m_Logistic == null ? "&nbsp;" : RenderUtil.FormatString(m_Logistic.Phone)%></td>
            <td></td>
            <td  class="label">传真：</td>
            <td style="border-bottom:solid 1px #000;"><%= m_Logistic == null ? "&nbsp;" : RenderUtil.FormatString(m_Logistic.Fax)%></td>
        </tr>
    </table>
    <table cellpadding="0" cellspacing="0" style="width:49%;float:right;">
        <tr>
            <td colspan="6" style="font-weight:bold">交接单信息</td>
        </tr>
        <tr>
            <td style="width:105px;" class="label">交接单号码：</td>
            <td style="width:140px;border-bottom:solid 1px #000;"><%= m_Head == null ? "&nbsp;" : RenderUtil.FormatString(m_Head.OrderNumber)%></td>
            <td style="width:40px"></td>
            <td style="width:80px" class="label">状态：</td>
            <td style="width:120px;border-bottom:solid 1px #000;"><%=RenderUtil.FormatString(m_StatusText)%></td>
            <td></td>
        </tr>
        <tr>
            <td class="label">交接人：</td>
            <td style="border-bottom:solid 1px #000;"><%= this.m_Head == null ? "&nbsp;" : RenderUtil.FormatString(m_Head.LogisticUser)%></td>
            <td></td>
            <td class="label">交接时间：</td>
            <td style="border-bottom:solid 1px #000;"><%= m_Head == null ? "&nbsp;" : m_Head.InterchangeTime.ToString("yyyy-MM-dd HH:mm")%></td>
            <td></td>
        </tr>
        <tr>
            <td  class="label">代收款总额：</td>
            <td style="border-bottom:solid 1px #000;"><%= RenderUtil.FormatNumber(this.totalAgentAmt, "#0.#0", "&nbsp;") %></td>
            <td></td>
            <td class="label">装箱数量：</td>
            <td style="border-bottom:solid 1px #000;"><%= totalPackageCount %></td>
            <td></td>
        </tr>
        <tr>
            <td class="label">备注：</td>
            <td colspan="4" style="border-bottom:solid 1px #000;"><%= this.m_Head == null ? "&nbsp;" : RenderUtil.FormatString(m_Head.Note)%></td>
            <td></td>
        </tr>
    </table>
    <div style="height:10px;width:300px;overflow:hidden;"></div>
    <table class="detaillist" cellpadding="0" cellspacing="0">
        <tr>
            <td colspan="12" style="font-weight:bold">交接单明细</td>
        </tr>
        <tr>
            <td style="font-weight:bold; text-align:center;width:90px;">
                发货单号码
            </td>
            <td style="font-weight:bold; text-align:center;width:90px;">
                运单号码
            </td>
            <td style="font-weight:bold; text-align:center;width:80px;">
                订单号码
            </td>
            <td style="font-weight:bold; text-align:center;width:90px;">
                发票号码
            </td>
            <td style="font-weight:bold; text-align:center;width:40px;">
                重量
            </td>
            <td style="font-weight:bold; text-align:center;width:45px;">
                包裹数
            </td>
            <td style="font-weight:bold; text-align:center;width:55px;">
                收货人
            </td>
            <td style="font-weight:bold; text-align:center;width:55px;">
                省市区县
            </td>
            <td style="font-weight:bold; text-align:center;width:70px;">
                电话
            </td>
            <td style="font-weight:bold; text-align:center;width:70px;">
                手机
            </td>
            <td style="font-weight:bold; text-align:center;width:60px;">
                代收金额
            </td>
        </tr>
        <asp:Repeater ID="rptDLV" runat="server">
            <ItemTemplate>
                <tr>
                    <td>
                        <%# RenderUtil.FormatString(Eval("OrderNumber"))%>
                    </td>
                    <td>
                        <%# RenderUtil.FormatString(Eval("ShippingNumber"))%>
                    </td>
                    <td>
                        <%# RenderUtil.FormatString(Eval("SaleOrderNumber"))%>
                    </td>
                    <td>
                        <%# RenderUtil.FormatString(Eval("InvoiceNumber"))%>
                    </td>
                    <td>
                        <%# RenderUtil.FormatString(Eval("PackageWeight"))%>
                    </td>
                    <td>
                        <%# RenderUtil.FormatString(Eval("PackageCount"))%>
                    </td>
                    <td>
                        <%# RenderUtil.FormatString(Eval("Contact"))%>
                    </td>
                    <td>
                        <%# RenderUtil.FormatString(Eval("Province"))%>&nbsp;&nbsp;<%# RenderUtil.FormatString(Eval("City"))%>
                    </td>
                    <td>
                        <%# RenderUtil.FormatString(Eval("Phone"))%>&nbsp;
                    </td>
                    <td>
                        <%# RenderUtil.FormatString(Eval("Mobile"))%>&nbsp;
                    </td>
                    <td style="padding-right:4px;">
                        <%# RenderUtil.FormatString(Eval("AgentAmt"))%>
                    </td>
                </tr>
            </ItemTemplate>
        </asp:Repeater>
    </table>
    <table border="0" cellpadding="0" cellspacing="0" id="cmdPrint"><tr>
        <td style="width:40%;"></td>
        <td><input type="button" value="打印" onclick="onprint();" />&nbsp;&nbsp;&nbsp;&nbsp;
        <input type="button" value="关闭" onclick="window.close();" /></td>
        <td style="width:40%;"></td>
    </tr></table>
</body>
</html>
