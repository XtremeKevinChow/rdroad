using System;
using System.Data;
using System.Collections;
using System.Collections.Generic;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using Magic.Framework.Utils;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Query;
using Magic.Web.UI;
using Magic.Sys;
using Magic.Basis;
using Magic.ERP;
using Magic.ERP.Orders;
using Magic.ERP.Core;
using Magic.Security;

public partial class Purchase_PipelineInvQuery : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            int skuid = WebUtil.ParamInt("sku", 0);
            if (skuid <= 0)
            {
                this.divMsg.InnerText = "无效的SKU";
                return;
            }
            using (ISession session = new Session())
            {
                ItemSpec spec = ItemSpec.Retrieve(session, skuid);
                if (spec == null)
                {
                    this.divMsg.InnerText = "SKU " + skuid.ToString() + "不存在";
                    return;
                }
                //显示sku信息
                this.lblItemCode.Text = spec.ItemCode;
                this.lblSize.Text = spec.SizeCode;
                ItemMaster master = ItemMaster.Retrieve(session, spec.ItemCode);
                this.lblItemName.Text = master == null ? "" : master.ItemName;
                ItemColor color = ItemColor.Retrieve(session, spec.ColorCode);
                this.lblColor.Text = spec.ColorCode + " " + (color == null ? "" : color.ColorText);
                this.lblOVSQty.Text = spec.EnableOS ? RenderUtil.FormatNumber(spec.OSQty, "#0.##", "0") : "0";
                //现有库存
                StockSummary sto = StockSummary.Retrieve(session, skuid);
                this.lblStockQty.Text = sto == null ? "0" : RenderUtil.FormatNumber(sto.StockQty, "#0.##");
                this.lblFrozenQty.Text = sto == null ? "0" : RenderUtil.FormatNumber(sto.FrozenQty, "#0.##");
                //在途采购记录
                IList<POLine> lines = POHead.PipelineInvQuery(session, skuid);
                this.repeater.DataSource = lines;
                this.repeater.DataBind();
                decimal pipelineStockQty = 0M;
                //计算汇总在途量
                foreach (POLine line in lines)
                    pipelineStockQty += (line.PurchaseQty - line.ReceiveQty);
                this.lblPipelineStock.Text = RenderUtil.FormatNumber(pipelineStockQty, "#0.##");
                if (lines.Count <= 0)
                    this.divMsg.InnerText = "该SKU没有在途采购";
                else
                    this.divMsg.InnerText = "";
            }
        }
    }
    public string PipelineInvQty(object purQty, object rcvQty)
    {
        return RenderUtil.FormatNumber(Cast.Decimal(purQty) - Cast.Decimal(rcvQty), "#0.##");
    }
}