using System;
using System.Collections.Generic;
using System.Data;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using Magic.Framework.Utils;
using Magic.Framework.ORM;
using Magic.ERP;
using Magic.ERP.CRM;
using Magic.ERP.Core;
using Magic.ERP.Orders;
using Magic.Basis;
using Magic.Web.UI;

public partial class Receive_LogisReturnScan : System.Web.UI.Page
{
    private string OrderNumber
    {
        get
        {
            return WebUtil.Param("ordNum").Trim();
        }
    }
    private string ReturnUrl
    {
        get
        {
            string returnUrl = WebUtil.Param("return");
            if (string.IsNullOrEmpty(returnUrl) || returnUrl.Trim().Length <= 0)
                returnUrl = "LogisReturnLine.aspx?ordNum=" + this.OrderNumber;
            return returnUrl;
        }
    }
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            this.txtOrderNumber.Value = this.OrderNumber;
            this.txtReturnUrl.Value = this.ReturnUrl;
            using (ISession session = new Session())
            {
                ReturnHead head = ReturnHead.Retrieve(session, this.OrderNumber);
                if (head != null) this.snView.SNNumber = head.RefOrderNumber;
                this.QueryAndBindData(session, head);
            }
        }
    }

    private void QueryAndBindData(ISession session, ReturnHead head)
    {
        DataSet ds = session.CreateObjectQuery(@"
select snl.ID as SNDID,s.BarCode as BarCode,m.ItemCode as ItemCode,m.ItemName as ItemName
    ,s.ColorCode as ColorCode,color.ColorText as ColorText,s.SizeCode as SizeCode
    ,st.Name as SaleType,snl.Price as Price,snl.Quantity as ShippingQty,rtl.Quantity as ReturnQty
from ReturnHead rth
inner join CRMSNLine snl on rth.RefOrderID=snl.SNID
inner join ItemSpec s on s.SKUID=snl.SKUID
inner join ItemMaster m on m.ItemID=s.ItemID
left join ReturnLine rtl on rtl.OrderNumber=rth.OrderNumber and rtl.RefOrderLineID=snl.ID
left join ItemColor color on color.ColorCode=s.ColorCode
left join CRMSaleType st on st.ID=snl.SellType
where rth.OrderNumber=?ordNum
order by snl.ID")
            .Attach(typeof(ReturnHead)).Attach(typeof(ReturnLine))
            .Attach(typeof(ItemSpec)).Attach(typeof(ItemMaster)).Attach(typeof(ItemColor))
            .Attach(typeof(CRMSNLine)).Attach(typeof(CRMSaleType))
            .SetValue("?ordNum", this.OrderNumber, "rtl.OrderNumber")
            .DataSet();
        ds.Tables[0].Columns.Add("INDEX", typeof(int));
        for (int i = 0; i < ds.Tables[0].Rows.Count; i++)
            ds.Tables[0].Rows[i]["INDEX"] = i + 1;
        this.repeatControl.DataSource = ds;
        this.repeatControl.DataBind();
    }

    public string ReturnStatus(object shipQty, object returnQty)
    {
        decimal dShipQty = Cast.Decimal(shipQty), dReturnQty = Cast.Decimal(returnQty);
        if (dShipQty == 0 || dReturnQty <= 0) return "0";
        else if (dReturnQty < dShipQty) return "1";
        else if (dReturnQty == dShipQty) return "2";
        else return "3";
    }
    protected void cmdEdit1_ItemCommand(object sender, MagicItemEventArgs args)
    {
        if (args.CommandName == "Save")
        {
            HtmlInputText input = null;
            using (ISession session = new Session())
            {
                try
                {
                    IList<ReturnLine> lines = new List<ReturnLine>();
                    foreach (RepeaterItem item in this.repeatControl.Items)
                    {
                        input = item.FindControl("hidQty") as HtmlInputText;
                        int sndid = Cast.Int(input.Attributes["vv_sndid"]);
                        decimal returnQty = Cast.Decimal(input.Value);
                        if (sndid <= 0 || returnQty <= 0M) continue;
                        ReturnLine line = new ReturnLine(); 
                        line.RefOrderLineID = sndid;
                        line.Quantity = returnQty;
                        lines.Add(line);
                    }
                    if (lines.Count <= 0) this.Response.Redirect(this.ReturnUrl);

                    ReturnHead head = ReturnHead.Retrieve(session, this.OrderNumber);
                    session.BeginTransaction();
                    head.UpdateOrCreateLines(session, lines);
                    session.Commit();
                    this.Response.Redirect(this.ReturnUrl);
                }
                catch (Exception er)
                {
                    session.Rollback();
                    WebUtil.ShowError(this, er);
                }
            }
        }
    }
}