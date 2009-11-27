using System;
using System.Data;
using System.Web.UI.WebControls;
using Magic.Framework.Utils;
using Magic.Framework.ORM;
using Magic.ERP.Orders;
using Magic.Basis;

public partial class Receive_PurchaseRCVLine : System.Web.UI.Page
{
    private static log4net.ILog log = WebUtil.Logger(typeof(Receive_PurchaseRCVLine));
    public decimal TotalAmt = 0M, TotalTax = 0M, TotalCost = 0M, TotalRcvQty=0M;

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
select l.LineNumber as LineNumber,l.RefOrderLine as RefOrderLine
    ,s.BarCode as BarCode,m.ItemCode as ItemCode,m.ItemName as ItemName,s.ColorCode as ColorCode,color.ColorText as ColorText,s.SizeCode as SizeCode
    ,l.RCVTotalQty as RCVTotalQty,l.QualifiedQty as QualifiedQty,l.Price as Price,0 as TotalAmt,l.TaxValue as TaxValue,0 as TaxAmt,0 as CostAmt
from RCVLine l
inner join ItemSpec s on l.SKUID=s.SKUID
inner join ItemMaster m on m.ItemID=s.ItemID
left join ItemColor color on color.ColorCode=s.ColorCode
where l.OrderNumber=?ordNum
order by l.LineNumber")
                    .Attach(typeof(RCVLine)).Attach(typeof(ItemSpec)).Attach(typeof(ItemMaster)).Attach(typeof(ItemColor))
                    .SetValue("?ordNum", this.OrderNumber, "l.OrderNumber")
                    .DataSet();
                foreach (DataRow row in ds.Tables[0].Rows)
                {
                    decimal qty = Cast.Decimal(row["QualifiedQty"]), price = Cast.Decimal(row["Price"]), tax = Cast.Decimal(row["TaxValue"]);
                    row["TotalAmt"] = qty * price;
                    row["CostAmt"] = qty * price / (1 + tax);
                    row["TaxAmt"] = qty * price * (tax / (1 + tax));
                    this.TotalCost += qty * price / (1 + tax);
                    this.TotalAmt += qty * price;
                    this.TotalRcvQty += qty;
                }
                this.TotalTax = this.TotalAmt - this.TotalCost;
                this.lblTotalAmt.Text = RenderUtil.FormatNumber(this.TotalAmt, "#,##0.#0", "0.00");
                this.lblTotalTax.Text = RenderUtil.FormatNumber(this.TotalTax, "#,##0.#0", "0.00");
                this.lblTotalCost.Text = RenderUtil.FormatNumber(this.TotalCost, "#,##0.#0", "0.00");
                this.lblRcvQtyTotal.Text = RenderUtil.FormatNumber(this.TotalRcvQty, "#,##0.##", "0");
                this.repeatControl.DataSource = ds;
                this.repeatControl.DataBind();
            }
        }
    }
    protected void repeatControl_ItemDataBound(object sender, RepeaterItemEventArgs e)
    {
        DataRowView drv = e.Item.DataItem as DataRowView;
        if (drv == null) return;
    }
    public string CostAmt(object qty, object price, object tax)
    {
        return RenderUtil.FormatNumber(Cast.Decimal(qty) * Cast.Decimal(price) / (1 + Cast.Decimal(tax)), "#0.#0");
    }
}