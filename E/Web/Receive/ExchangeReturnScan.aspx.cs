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
using Magic.Framework.Data;
using Magic.Web.UI;

public partial class Receive_ExchangeReturnScan : System.Web.UI.Page
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
                returnUrl = "MemberReturnLine.aspx?ordNum=" + this.OrderNumber;
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
select 
       sku.BarCode As BarCode,sku.SizeCode As SizeCode
       ,i.ItemCode As ItemCode,i.ItemName As ItemName,sku.ColorCode As ColorCode,c.Name As ColorText
       ,st.Name As SaleType,rl.Price As Price,rl.Quantity As ReturnQty
from ReturnHead rh
inner join ReturnLine rl on rh.OrderNumber=rl.OrderNumber
inner join ItemSpec sku on sku.SKUID=rl.SKUID
inner join ItemMaster i on i.ItemCode=sku.ItemCode
left join ItemColor c on c.ColorCode=sku.ColorCode
left join CRMSN sn on sn.ID=rh.RefOrderID
left join CRMSNLine snl on snl.SNID=sn.ID and snl.ID=rl.RefOrderLineID
left join CRMSaleType st on st.ID=snl.SellType
where rh.OrderNumber=?ordNum
order by rl.LineNumber")
                .Attach(typeof(ReturnHead)).Attach(typeof(ReturnLine))
                .Attach(typeof(ItemSpec)).Attach(typeof(ItemMaster)).Attach(typeof(ItemColor))
                .Attach(typeof(CRMSN)).Attach(typeof(CRMSNLine)).Attach(typeof(CRMSaleType))
                .SetValue("?ordNum", head.OrderNumber, "rh.OrderNumber")
                .DataSet();
        ds.Tables[0].Columns.Add("INDEX", typeof(int));
        for (int i = 0; i < ds.Tables[0].Rows.Count; i++)
            ds.Tables[0].Rows[i]["INDEX"] = i + 1;
        this.txtLineCount.Value = ds.Tables[0].Rows.Count.ToString();
        this.repeatControl.DataSource = ds;
        this.repeatControl.DataBind();
    }

    protected void cmdEdit1_ItemCommand(object sender, MagicItemEventArgs args)
    {
        if (args.CommandName == "Save")
        {
            using (ISession session = new Session())
            {
                try
                {
                    ReturnHead head = ReturnHead.Retrieve(session, this.OrderNumber);
                    session.BeginTransaction();
                    head.ExchangeReturnScaned(session);
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