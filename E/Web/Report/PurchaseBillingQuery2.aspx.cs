using System;
using System.Collections.Generic;
using System.Data;
using System.Text;
using System.Web.UI;
using Magic.Basis;
using Magic.Framework.Data;
using Magic.Framework.ORM;
using Magic.Framework.Utils;
using Magic.Web.UI;

public partial class Report_PurchaseBillingQuery2 : System.Web.UI.Page
{
    public DataSet _ds = null;
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            this.txtDateFrom.Text = DateTime.Now.AddDays(-1).ToString("yyyy-MM-dd");
            this.txtDateTo.Text = DateTime.Now.ToString("yyyy-MM-dd");

            using (ISession session = new Session())
            {
                IList<Vendor> vendors = session.CreateEntityQuery<Vendor>()
                    .OrderBy("ShortName")
                    .List<Vendor>();
                this.drpVendor.Items.Clear();
                this.drpVendor.Items.Add(new System.Web.UI.WebControls.ListItem("", ""));
                foreach (Vendor v in vendors)
                    this.drpVendor.Items.Add(new System.Web.UI.WebControls.ListItem(v.ShortName, v.VendorID.ToString()));
                this.QueryAndBindData(session, 1, this.magicPagerMain.PageSize, true);
            }
        }
    }

    private void QueryAndBindData(ISession session, int pageIndex, int pageSize, bool fetchRecordCount)
    {
        int count = 0;
        this._ds = this.Query(session, pageIndex, pageSize, fetchRecordCount, ref count);
        if (fetchRecordCount)
            this.magicPagerMain.RecordCount = this.magicPager1.RecordCount = count;
        WebUtil.SetMagicPager(magicPagerMain, pageSize, pageIndex);
        WebUtil.SetMagicPager(magicPager1, pageSize, pageIndex);
    }
    private DataSet Query(ISession session, int pageIndex, int pageSize, bool fetch, ref int count)
    {
        DbSession dbsession = session.DbSession as DbSession;
        DateTime dtstart = Cast.DateTime(this.txtDateFrom.Text, new DateTime(2008, 10, 1)), dtend = Cast.DateTime(this.txtDateTo.Text, new DateTime(2099, 1, 1));
        int intstart = Cast.Int(dtstart.ToString("yyyyMMdd")), intend = Cast.Int(dtend.ToString("yyyyMMdd"));
        string po = this.txtPO.Text.Trim().Replace("'", "''");
        string itemCode = this.txtItemCode.Text.Trim().Replace("'", "''");
        string color = this.txtColor.Text.Trim().Replace("'", "''");
        string size = this.txtSize.Text.Trim().Replace("'", "''");
        int vendor = Cast.Int(this.drpVendor.SelectedValue, 0);

        //sql语句
        string sql = string.Format(@"
Select po.*,rc.RcvNum,rc.RcvLine,rc.RcvQty
   ,Case When rc.RcvQty Is Null Then Null Else po.Price*rc.RcvQty End As TaxInAmt
   ,Case When rc.RcvQty Is Null Then Null Else po.Price*rc.RcvQty*rc.TaxVal/(1+rc.TaxVal) End As TaxAmt
   ,Case When rc.RcvQty Is Null Then Null Else po.Price*rc.RcvQty/(1+rc.TaxVal) End As TaxExAmt
   ,0 As RowSpan
From(
    Select Rownum As rn,b.* From(
        Select ph.ord_num As PONum,pl.line_num As POLine,pl.pur_qty As PurQty,pl.pur_price As Price
           ,sku.itm_code As ItemCode,it.itm_name As ItemName,sku.color_code As ColorCode,c.name As ColorName,sku.size_code As SizeCode
        From(
           Select Distinct h.ref_ord_num As PONum
           From inv_trans_head h
           Where h.commit_date>={0} And h.commit_date<={1} And h.ref_ord_type='PO0'
        ) a
        Inner Join ord_pur_head ph On a.PONum=ph.ord_num
        Inner Join ord_pur_line pl On ph.ord_num=pl.ord_num
        Left Join prd_item_sku sku On pl.sku_id=sku.sku_id
        Left Join prd_item it On it.itm_id=sku.itm_id
        Left Join prd_item_color c On c.code=sku.color_code
        Where 1=1 {2} {3} {4} {5} {6}
        Order By a.PONum Desc,pl.line_num Asc
    )b
)po Inner Join(
    Select tl.org_ord_num As PONum,tl.org_odr_line As POLine
       ,rl.ord_num As RcvNum,rl.line_num As RcvLine,tl.trans_qty As RcvQty,tl.tax_val As TaxVal
    From inv_trans_line tl
    Inner Join ord_rcv_line rl On tl.ref_ord_num=rl.ord_num And tl.ref_odr_line=rl.line_num
    Where tl.trans_type_code='101' And tl.trans_date>={7} And tl.trans_date<={8}
)rc On po.PONum=rc.PONum And po.POLine=rc.POLine"
            , intstart
            , intend
            , string.IsNullOrEmpty(po) ? "" : "And ph.ord_num='" + po + "'"
            , vendor <= 0 ? "" : "And ph.ven_id=" + vendor.ToString()
            , string.IsNullOrEmpty(itemCode) ? "" : "And sku.itm_code='" + itemCode + "'"
            , string.IsNullOrEmpty(color) ? "" : "And sku.color_code='" + color + "'"
            , string.IsNullOrEmpty(size) ? "" : "And sku.size_code='" + size + "'"
            , intstart
            , intend);

        //查询数据
        IDbCommand cmd = dbsession.CreateSqlStringCommand("");
        if (fetch)
        {
            cmd.CommandText = "select count(1) from(" + sql + ")t";
            count = Cast.Int(dbsession.ExecuteScalar(cmd));
        }
        DataSet ds = null;
        if (pageIndex <= 0)
        {
            cmd.CommandText = sql + " order by po.rn";
            ds = dbsession.ExecuteDataSet(cmd);
        }
        else
        {

            int startIndex = (pageIndex - 1) * pageSize + 1, endIndex = pageIndex * pageSize;
            cmd.CommandText = sql + " where po.rn>=" + startIndex.ToString() + " and po.rn<=" + endIndex.ToString() + " order by po.rn";
            ds = dbsession.ExecuteDataSet(cmd);
        }

        //处理行合并问题
        string prevPo = "", prevLine = "";
        int rowCount = 0;
        for (int i = 0; i < ds.Tables[0].Rows.Count; i++)
        {
            DataRow row = ds.Tables[0].Rows[i];
            if (i == 0)
            {
                prevPo = Cast.String(row["PONum"]);
                prevLine = Cast.String(row["POLine"]);
                continue;
            }
            rowCount++;
            if (prevPo == Cast.String(row["PONum"]) && prevLine == Cast.String(row["POLine"]))
            {
                row["PONum"] = DBNull.Value;
                row["POLine"] = DBNull.Value;
                row["PurQty"] = DBNull.Value;
                row["Price"] = DBNull.Value;
                row["ItemCode"] = DBNull.Value;
                row["ItemName"] = DBNull.Value;
                row["ColorCode"] = DBNull.Value;
                row["ColorName"] = DBNull.Value;
                row["SizeCode"] = DBNull.Value;
                row["RowSpan"] = 0;
                continue;
            }
            ds.Tables[0].Rows[i - rowCount]["RowSpan"] = rowCount;
            prevPo = Cast.String(row["PONum"]);
            prevLine = Cast.String(row["POLine"]);
            rowCount = 0;
        }
        if (ds.Tables[0].Rows.Count > 0)
            ds.Tables[0].Rows[ds.Tables[0].Rows.Count - 1 - rowCount]["RowSpan"] = rowCount + 1;

        return ds;
    }
    protected void btnQuery_Click(object sender, ImageClickEventArgs e)
    {
        using (ISession session = new Session())
        {
            this.QueryAndBindData(session, 1, this.magicPagerMain.PageSize, true);
        }
    }
    protected void MagicPager_PageChanged(object src, Magic.Web.UI.PageChangedEventArgs e)
    {
        this.magicPager1.PageSize = this.magicPagerMain.PageSize;
        using (ISession session = new Session())
        {
            this.QueryAndBindData(session, e.NewPageIndex, e.Pager.PageSize, false);
        }
    }
    protected void MagicItemCommand(object sender, MagicItemEventArgs e)
    {
        if (e.CommandName != "Download")
            return;

        using (ISession session = new Session())
        {
            int count = 0;
            DataSet ds = this.Query(session, -1, 0, false, ref count);
            string fileName = DownloadUtil.DownloadXls("PO_Receive_Sum2_" + DateTime.Now.ToString("yyMMdd") + ".xls", "RPT_PO_RCV_SUM2_",
                new List<DownloadFormat>()
                    {
                        new DownloadFormat(DataType.Text, "采购订单", "PONum"),
                        new DownloadFormat(DataType.NumberText, "行号", "POLine"),
                        new DownloadFormat(DataType.Number, "采购量", "PurQty"),
                        new DownloadFormat(DataType.NumberText, "货号", "ItemCode"),
                        new DownloadFormat(DataType.Text, "名称", "ItemName"),
                        new DownloadFormat(DataType.Text, "颜色", "ColorCode", "ColorName"),
                        new DownloadFormat(DataType.NumberText, "尺码", "SizeCode"),
                        new DownloadFormat(DataType.Text, "收货单", "RcvNum"),
                        new DownloadFormat(DataType.NumberText, "行号", "RcvLine"),                       
                        new DownloadFormat(DataType.Number, "收货量", "RcvQty"),
                        new DownloadFormat(DataType.Number, "含税金额", "TaxInAmt"),
                        new DownloadFormat(DataType.Number, "税额", "TaxAmt"),
                        new DownloadFormat(DataType.Number, "成本金额", "TaxExAmt")
                    }, ds);
            this.frameDownload.Attributes["src"] = fileName;
        }
    }
}