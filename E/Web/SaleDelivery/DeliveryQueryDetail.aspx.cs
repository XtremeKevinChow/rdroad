using System;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Query;
using Magic.ERP.Orders;
using Magic.Basis;

public partial class SaleDelivery_DeliveryQueryDetail : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            this.cmdReturn1["Return"].NavigateUrl = WebUtil.Param("return");
            this.cmdReturn2["Return"].NavigateUrl = WebUtil.Param("return");
            this.snView.SNNumber = WebUtil.Param("ordNum");

            using (ISession session = new Session())
            {
                CRMSN  sn = CRMSN.Retrieve(session, WebUtil.Param("ordNum"));
                Response.Write(sn.ToJSon(session).ToJsonString());
                this.QueryAndBindData(session);
            }
        }
    }
    private void QueryAndBindData(ISession session)
    {
        string ordNum = WebUtil.Param("ordNum");
        ObjectQuery query = session.CreateObjectQuery(@"
select s.BarCode as BarCode,m.ItemCode as ItemCode,m.ItemName as ItemName
    ,color.ColorText as ColorText,s.ColorCode as ColorCode,s.SizeCode as SizeCode
    ,line.Quantity as Quantity,line.Price as Price
from CRMSN sn
inner join CRMSNLine line on sn.ID=line.SNID
inner join ItemSpec s on s.SKUID=line.SKUID
inner join ItemMaster m on m.ItemID=s.ItemID
left join ItemColor color on color.ColorCode=s.ColorCode
where sn.OrderNumber=?ordnum
order by line.ID")
            .Attach(typeof(CRMSN)).Attach(typeof(CRMSNLine))
            .Attach(typeof(ItemMaster)).Attach(typeof(ItemSpec)).Attach(typeof(ItemColor))
            .SetValue("?ordnum", ordNum, "sn.OrderNumber");

        this.repeatControl.DataSource = query.DataSet();
        this.repeatControl.DataBind();
    }
}