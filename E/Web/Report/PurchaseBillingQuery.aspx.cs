using System;
using System.Collections.Generic;
using System.Data;
using System.Text;
using System.Web.UI;
using Magic.Basis;
using Magic.ERP.Core;
using Magic.ERP.Report;
using Magic.Framework.Data;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Query;
using Magic.Framework.Utils;
using Magic.Web.UI;

public partial class Report_PurchaseBillingQuery : System.Web.UI.Page
{
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
                this.drpVendor.Items.Add(new System.Web.UI.WebControls.ListItem("",""));
                foreach (Vendor v in vendors)
                    this.drpVendor.Items.Add(new System.Web.UI.WebControls.ListItem(v.ShortName, v.VendorID.ToString()));
                this.QueryAndBindData(session, 1, this.magicPagerMain.PageSize, true);
            }
        }
    }

    private void QueryAndBindData(ISession session, int pageIndex, int pageSize, bool fetchRecordCount)
    {
        int count = 0;
        this.repeater.DataSource = this.Query(session, pageIndex, pageSize, fetchRecordCount, ref count);
        this.repeater.DataBind();
        if (fetchRecordCount)
            this.magicPagerMain.RecordCount = this.magicPager1.RecordCount = count;
        WebUtil.SetMagicPager(magicPagerMain, pageSize, pageIndex);
        WebUtil.SetMagicPager(magicPager1, pageSize, pageIndex);
    }
    private DataSet Query(ISession session, int pageIndex, int pageSize, bool fetch, ref int count)
    {
        DbSession dbsession = session.DbSession as DbSession;
        StringBuilder sql = new StringBuilder();
        IDbCommand cmd = dbsession.CreateSqlStringCommand("");

        DateTime dtstart = Cast.DateTime(this.txtDateFrom.Text, new DateTime(1900, 1, 1)), dtend = Cast.DateTime(this.txtDateTo.Text, new DateTime(1900, 1, 1));
        int intstart = Cast.Int(dtstart.ToString("yyyyMMdd")), intend = Cast.Int(dtend.ToString("yyyyMMdd"));
        sql.Append(@"
Select sku.itm_barcode As BarCode,sku.itm_code As ItemCode,i.itm_name As ItemName,sku.color_code As ColorCode,c.Name As ColorText,sku.size_code As SizeCode
       ,Sum(tl.trans_qty) As TransQty
       ,Sum(tl.trans_qty*tl.trans_price) As TaxInAmt
       ,Sum(tl.trans_qty*tl.trans_price*tl.tax_val/(1+tl.tax_val)) As TaxAmt
       ,Sum(tl.trans_qty*tl.trans_price/(1+tl.tax_val)) As TaxExAmt
From inv_trans_line tl
Inner Join inv_trans_head th On th.trans_num=tl.trans_num
Inner Join prd_item_sku sku On sku.sku_id=tl.sku_id
Inner Join prd_item i On i.itm_code=sku.itm_code
Inner Join ord_pur_head po On po.Ord_Num=th.Ref_Ord_Num
Left Join prd_item_color c On c.code=sku.color_code
Where tl.trans_type_code='101'")
            .Append(" And th.commit_date>=").Append(intstart)
            .Append(" And th.commit_date<=").Append(intend)
            .Append(" And tl.trans_date>=").Append(intstart)
            .Append(" And tl.trans_date<=").Append(intend);
        int ven = Cast.Int(this.drpVendor.SelectedValue);
        if (ven > 0) sql.Append(" And po.ven_id=").Append(ven);
        if (this.txtPO.Text.Trim().Length > 0)
        {
            sql.Append(" And th.Ref_Ord_Num=:po");
            dbsession.AddParameter(cmd, ":po", DbTypeInfo.AnsiString(16), this.txtPO.Text.Trim());
        }
        if (this.txtItemCode.Text.Trim().Length > 0)
        {
            sql.Append(" And sku.itm_code=:icode");
            dbsession.AddParameter(cmd, ":icode", DbTypeInfo.AnsiString(18), this.txtItemCode.Text.Trim());
        }
        if (this.txtColor.Text.Trim().Length > 0)
        {
            sql.Append(" And sku.color_code=:icolor");
            dbsession.AddParameter(cmd, ":icolor", DbTypeInfo.AnsiString(6), this.txtColor.Text.Trim());
        }
        if (this.txtSize.Text.Trim().Length > 0)
        {
            sql.Append(" And sku.size_code=:isize");
            dbsession.AddParameter(cmd, ":isize", DbTypeInfo.AnsiString(6), this.txtSize.Text.Trim());
        }
        sql.Append(@"
Group By sku.itm_barcode,sku.itm_code,i.itm_name,sku.color_code,c.Name,sku.size_code
Order By sku.itm_code,sku.color_code,sku.size_code");

        if (fetch)
        {
            cmd.CommandText = "select count(1) from(" + sql.ToString() + ")t";
            count = Cast.Int(dbsession.ExecuteScalar(cmd));
        }

        if (pageIndex <= 0)
        {
            cmd.CommandText = sql.ToString();
            return dbsession.ExecuteDataSet(cmd);
        }

        int startIndex = (pageIndex - 1) * pageSize + 1, endIndex = pageIndex * pageSize;
        StringBuilder sql2 = new StringBuilder();
        sql2.Append("SELECT * FROM(SELECT mf_t1.*,Rownum AS mf_r FROM(")
            .Append(sql.ToString())
            .Append(") mf_t1) mf_t2 WHERE mf_r>=")
            .Append(startIndex)
            .Append(" AND mf_r<=")
            .Append(endIndex);
        cmd.CommandText = sql2.ToString();
        return dbsession.ExecuteDataSet(cmd);
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
            string fileName = DownloadUtil.DownloadXls("PO_Receive_Sum_" + DateTime.Now.ToString("yyMMdd") + ".xls", "RPT_PO_RCV_SUM_",
                new List<DownloadFormat>()
                    {
                        new DownloadFormat(DataType.NumberText, "SKU", "BarCode"),
                        new DownloadFormat(DataType.NumberText, "货号", "ItemCode"),
                        new DownloadFormat(DataType.Text, "名称", "ItemName"),
                        new DownloadFormat(DataType.Text, "颜色", "ColorCode", "ColorText"),
                        new DownloadFormat(DataType.NumberText, "尺码", "SizeCode"),
                        new DownloadFormat(DataType.Number, "总收货数量", "TransQty"),
                        new DownloadFormat(DataType.Number, "含税总金额", "TaxInAmt"),
                        new DownloadFormat(DataType.Number, "税额", "TaxAmt"),
                        new DownloadFormat(DataType.Number, "成本金额", "TaxExAmt")
                    }, ds);
            this.frameDownload.Attributes["src"] = fileName;
        }
    }
}