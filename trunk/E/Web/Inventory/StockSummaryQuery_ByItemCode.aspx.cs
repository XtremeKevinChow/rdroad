using System;
using System.Data;
using System.Web.UI;
using Magic.Framework.Utils;
using Magic.Framework.ORM;
using Magic.Framework.Data;
using Magic.Framework.ORM.Query;
using Magic.Basis;
using Magic.ERP.Core;
using Magic.ERP.Orders;

public partial class Inventory_StockSummaryQuery_ByItemCode : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            using (ISession session = new Session())
            {
                WebUtil.SetMagicPager(magicPagerMain, magicPagerMain.PageSize, 1);
                WebUtil.SetMagicPager(magicPagerSub, magicPagerSub.PageSize, 1);
            }
        }
    }

    void QueryAndBindData(ISession session, int pageIndex, int pageSize, bool fetchRecordCount)
    {
        //StockSummary ItemMaster ItemSpec
        string itemCode = this.txtItemCode.Text.Trim();
        string itemName = this.txtItemName.Text.Trim();
        //string colorCode = this.txtColorCode.Text.Trim();
        //string sizeCode = this.txtSizeCode.Text.Trim();
        //string sku = this.txtSku.Text.Trim();

        DbSession db = session.DbSession as DbSession;
        int startIndex = (pageIndex - 1) * pageSize + 1, endIndex = pageIndex * pageSize;
        string sql = string.Format(@"
Select it.itm_code, it.itm_name, Sum(sto.use_qty) sto_qty
   , Sum(sto.use_qty*nvl(it.sale_price2,0)) retail_amt
   , Sum(sto.use_qty*nvl(sku.avg_move_cost,0)) cost_amt
From (
    select o.sku_id,sum(o.stock_qty) use_qty
    from inv_stock_detail o
    inner join inv_wh_area a on o.area_code=a.area_code and o.loc_code=a.loc_code
    where nvl(a.is_qc,0)=0 and nvl(a.is_scrap,0)=0
    group by o.sku_id
)sto
Inner Join prd_item_sku sku On sto.sku_id=sku.sku_id
Inner Join prd_item it On it.itm_id=sku.itm_id
Where it.itm_code Like '{0}' And it.itm_name Like '{1}'
Group By it.itm_code, it.itm_name
Order By it.itm_code", "%" + this.txtItemCode.Text.Trim() + "%", "%" + this.txtItemName.Text.Trim() + "%");
        IDbCommand cmd = db.CreateSqlStringCommand(string.Format(@"
Select * From( Select t.*,Rownum rn From({0})t )t2
Where t2.rn>={1} And t2.rn<={2}
Order By t2.rn", sql, startIndex, endIndex));

        //追加条件
        //if (!string.IsNullOrEmpty(colorCode)) query.And(Exp.Like("spec.ColorCode", "%" + colorCode.ToUpper() + "%"));
        //if (!string.IsNullOrEmpty(sizeCode)) query.And(Exp.Like("spec.SizeCode", "%" + sizeCode.ToUpper() + "%"));
        //if (!string.IsNullOrEmpty(sku)) query.And(Exp.Like("spec.BarCode", "%" + sku.ToUpper() + "%"));
        this.rptPO.DataSource = db.ExecuteDataSet(cmd);
        this.rptPO.DataBind();
        if (fetchRecordCount)
        {
            cmd = db.CreateSqlStringCommand("select count(*) from (" + sql + " )tt");
            this.magicPagerMain.RecordCount = this.magicPagerSub.RecordCount = Cast.Int(db.ExecuteScalar(cmd));
        }
        WebUtil.SetMagicPager(magicPagerMain, pageSize, pageIndex);
        WebUtil.SetMagicPager(magicPagerSub, pageSize, pageIndex);

        //        query = session.CreateObjectQuery(@"
        //select sum(s.StockQty) as StockQty,sum(s.FrozenQty) as FrozenQty,sum(t.QCQty) as QCQty,sum(pr.Qty) as PeddingQty
        //from StockSummary s
        //inner join ItemSpec spec on s.SKUID = spec.SKUID
        //inner join ItemMaster master on master.ItemID = spec.ItemID
        //left join ItemColor color on color.ColorCode=spec.ColorCode
        //left join (
        //    select sd.SKUID as SKUID,sum(sd.StockQty) as QCQty
        //    from StockDetail sd
        //    inner join WHArea area on sd.AreaCode=area.AreaCode
        //    where area.IsQC=1
        //    group by sd.SKUID
        //)t on t.SKUID=s.SKUID
        //left join PendingReceipt pr on pr.SKU_ID=spec.SKUID
        //order by master.ItemCode,spec.ColorCode,spec.SizeCode")
        //            .Attach(typeof(StockSummary)).Attach(typeof(ItemMaster)).Attach(typeof(ItemSpec))
        //            .Attach(typeof(StockDetail)).Attach(typeof(WHArea))
        //            .Attach(typeof(ItemColor)).Attach(typeof(PendingReceipt))
        //            .SetPage(pageIndex, pageSize);
        ////追加条件
        //if (!string.IsNullOrEmpty(itemCode)) query.And(Exp.Like("master.ItemCode", "%" + itemCode + "%"));
        //if (!string.IsNullOrEmpty(itemName)) query.And(Exp.Like("master.ItemName", "%" + itemName + "%"));
        //if (!string.IsNullOrEmpty(colorCode)) query.And(Exp.Like("spec.ColorCode", "%" + colorCode.ToUpper() + "%"));
        //if (!string.IsNullOrEmpty(sizeCode)) query.And(Exp.Like("spec.SizeCode", "%" + sizeCode.ToUpper() + "%"));
        //if (!string.IsNullOrEmpty(sku)) query.And(Exp.Like("spec.BarCode", "%" + sku.ToUpper() + "%"));
        //System.Data.DataSet ds = query.DataSet();
        //if (ds.Tables.Count > 0 && ds.Tables[0].Rows.Count > 0)
        //{
        //    this.lblTotalStoQty.Text = Cast.Decimal(ds.Tables[0].Rows[0]["StockQty"]).ToString("#0.#0");
        //    this.lblTotalFrozenQty.Text = Cast.Decimal(ds.Tables[0].Rows[0]["FrozenQty"]).ToString("#0.#0");
        //    this.lblTotalQCQty.Text = Cast.Decimal(ds.Tables[0].Rows[0]["QCQty"]).ToString("#0.#0");
        //}
        //else
        //{
        //    this.lblTotalStoQty.Text = "0.00";
        //    this.lblTotalFrozenQty.Text = "0.00";
        //    this.lblTotalQCQty.Text = "0.00";
        //}
    }

    protected void MagicPager_PageChanged(object src, Magic.Web.UI.PageChangedEventArgs e)
    {
        //翻页事件
        this.magicPagerSub.PageSize = this.magicPagerMain.PageSize; //如果页面有2个翻页控件，则必须写上这一句(控件bug)
        using (ISession session = new Session())
        {
            QueryAndBindData(session, e.NewPageIndex, e.Pager.PageSize, false);
        }
    }
    protected void btnQuery_Click(object sender, ImageClickEventArgs e)
    {
        using (ISession session = new Session())
        {
            QueryAndBindData(session, 1, magicPagerMain.PageSize, true);
        }
    }
}
