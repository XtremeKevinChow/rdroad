using System;
using System.Data;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Query;
using Magic.ERP.Orders;
using Magic.Basis;
using Magic.ERP.CRM;
using Magic.Framework.Utils;
using Magic.ERP.Core;

public partial class SaleDelivery_DeliveryQueryDetail : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            this.snView.SNNumber = WebUtil.Param("ordNum");
            this.cmdReturn1["Return"].NavigateUrl = WebUtil.Param("return");
            using (ISession session = new Session())
            {
                this.QueryAndBindData(session);
            }
        }
    }
    private void QueryAndBindData(ISession session)
    {
        string ordNum = WebUtil.Param("ordNum");
        ObjectQuery query = session.CreateObjectQuery(@"
select s.BarCode as BarCode,m.ItemCode as ItemCode,m.ItemName as ItemName
    ,color.ColorText as ColorText,s.ColorCode as ColorCode,s.SizeCode as SizeCode,st.Name as SellTypeName
    ,case when tl.TransQty is null then line.Quantity else -tl.TransQty end as Quantity
    ,line.Price as Price,tl.AvgMoveCost as AvgMoveCost,0 as CostAmt
from CRMSN sn
inner join CRMSNLine line on sn.ID=line.SNID
left join WHTransLine tl on tl.RefOrderLine=line.ID
inner join ItemSpec s on s.SKUID=line.SKUID
inner join ItemMaster m on m.ItemID=s.ItemID
left join ItemColor color on color.ColorCode=s.ColorCode
left join CRMSaleType st on st.ID=line.SellType
where sn.OrderNumber=?ordnum and tl.RefOrderType='SD0'
order by line.ID")
            .Attach(typeof(CRMSN)).Attach(typeof(CRMSNLine)).Attach(typeof(CRMSaleType)).Attach(typeof(WHTransLine))
            .Attach(typeof(ItemMaster)).Attach(typeof(ItemSpec)).Attach(typeof(ItemColor))
            .SetValue("?ordnum", ordNum, "sn.OrderNumber");

        DataSet ds = query.DataSet();
        ds.Tables[0].Columns.Add("Amt", typeof(decimal));
        decimal totalAmt = 0M, amt = 0M, totalCost = 0M, cost = 0M;
        foreach (DataRow row in ds.Tables[0].Rows)
        {
            amt = Cast.Decimal(row["Quantity"]) * Cast.Decimal(row["Price"]);
            row["Amt"] = amt;
            totalAmt += amt;
            cost = Cast.Decimal(row["AvgMoveCost"]) * Cast.Decimal(row["Quantity"]);
            row["CostAmt"] = cost;
            totalCost += cost;
        }
        this.lblTotalAmt.Text = RenderUtil.FormatNumber(totalAmt, "#,##0.#0");
        this.lblCostAmt.Text = RenderUtil.FormatNumber(totalCost, "#,##0.#0");
        this.repeatControl.DataSource = ds;
        this.repeatControl.DataBind();
    }
}