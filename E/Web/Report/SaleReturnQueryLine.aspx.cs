using System;
using System.Data;
using System.Web.UI.WebControls;
using Magic.Framework.Utils;
using Magic.Framework.ORM;
using Magic.ERP.Orders;
using Magic.ERP.CRM;
using Magic.Basis;

public partial class Receive_SaleReturnQueryLine : System.Web.UI.Page
{
    private static log4net.ILog log = WebUtil.Logger(typeof(Receive_SaleReturnQueryLine));

    private string OrderNumber
    {
        get
        {
            return WebUtil.Param("ordNum").Trim();
        }
    }
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            this.cmdReturn1["Return"].NavigateUrl = this.cmdReturn2["Return"].NavigateUrl = WebUtil.Param("return");
            this.txtOrderNumber.Value = this.OrderNumber;
            using (ISession session = new Session())
            {
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
                    .SetValue("?ordNum", this.OrderNumber, "rtl.OrderNumber")
                    .DataSet();
                this.repeatControl.DataSource = ds;
                this.repeatControl.DataBind();
            }
        }
    }
}