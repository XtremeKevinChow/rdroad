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

public partial class Receive_MemberReturnScan : System.Web.UI.Page
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
        DbSession dbsession = session.DbSession as DbSession;
        IDbCommand cmd = dbsession.CreateSqlStringCommand(@"
select /*+ ordered use_nl(snl s) use_nl(s m) use_nl(snl rth) index(rtl pk_ord_rtn_line) use_nl(color s) use_nl(st snl) */
       snl.Id As SNDID,s.ITM_BARCODE As BarCode
       ,m.ITM_CODE As ItemCode,m.ITM_NAME As ItemName,s.COLOR_CODE As ColorCode,color.Name As ColorText
       ,s.SIZE_CODE As SizeCode
       ,st.Name As SaleType,snl.PRICE As Price,snl.QUANTITY As ShippingQty
       ,sum(rtl.RTN_QTY) As ReturnQty 
from ORD_SHIPPINGNOTICE_LINES snl
inner join PRD_ITEM_SKU s on s.SKU_ID=snl.SKU_ID 
inner join PRD_ITEM m on m.itm_code=s.itm_code 
Left join ORD_RTN_HEAD rth  on rth.REF_ORD_ID=snl.SN_ID
left join ORD_RTN_LINE rtl on rtl.ORD_NUM=rth.ORD_NUM and rtl.REF_ORD_LINE_ID=snl.ID 
left join PRD_ITEM_COLOR color on color.CODE=s.COLOR_CODE 
Left join S_SELL_TYPE st on st.ID=snl.SELL_TYPE
where snl.sn_id=:snid
Group By snl.Id,s.ITM_BARCODE,m.ITM_CODE,m.ITM_NAME,s.COLOR_CODE,color.Name,s.SIZE_CODE
      ,st.Name,snl.PRICE,snl.QUANTITY
order by snl.Id");
        dbsession.AddParameter(cmd, ":snid", EntityManager.GetPropMapping(typeof(ReturnHead), "RefOrderID").DbTypeInfo, head.RefOrderID);
        DataSet ds = dbsession.ExecuteDataSet(cmd);
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