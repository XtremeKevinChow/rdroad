using System;
using System.Data;
using System.Collections;
using System.Collections.Generic;
using System.Web.UI;
using System.Web.UI.WebControls;
using Magic.Framework.Utils;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Query;
using Magic.Basis;
using Magic.ERP;
using Magic.ERP.Orders;
using Magic.Web.UI;

public partial class Purchase_POSearch : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            this.txtStartDate.Value = DateTime.Now.AddMonths(-1).ToString("yyyy-MM-dd");
            this.txtEndDate.Value = DateTime.Now.ToString("yyyy-MM-dd");
            WebUtil.SetMagicPager(magicPagerMain, magicPagerMain.PageSize, 1);
            WebUtil.SetMagicPager(magicPagerSub, magicPagerMain.PageSize, 1);
        }
        else this.frameDownload.Attributes["src"] = "about:blank;";
    }

    private void QueryAndBindData(ISession session, int pageSize, int pageIndex, bool fetch)
    {
        int count = 0;
        this.rptPO.DataSource = this.QueryData(session, pageSize, pageIndex, fetch, ref count);
        this.rptPO.DataBind();

        if (fetch)
            this.magicPagerMain.RecordCount = this.magicPagerSub.RecordCount = count;
        WebUtil.SetMagicPager(magicPagerMain, pageSize, pageIndex);
        WebUtil.SetMagicPager(magicPagerSub, pageSize, pageIndex);
    }
    private DataSet QueryData(ISession session, int pageSize, int pageIndex, bool fetch, ref int count)
    {
        string itemCode = txtItemCode.Text.Trim();
        string itemName = txtItemName.Text.Trim();
        string colorCode = txtColorCode.Text.Trim().ToUpper();
        string sizeCode = txtSizeCode.Text.Trim().ToUpper();
        string orderNum = txtOrderNum.Text;
        DateTime startDate = Cast.DateTime(txtStartDate.Value, new DateTime(1900, 1, 1));
        DateTime endDate = Cast.DateTime(txtEndDate.Value, new DateTime(1900, 1, 1));
        IList<POStatus> status = new List<POStatus>();
        foreach (ListItem item in cklStatus.Items)
            if (item.Selected) status.Add(Cast.Enum<POStatus>(item.Value));
        ObjectQuery query = session.CreateObjectQuery(@"
select 
    p.OrderNumber as OrderNumber,p.LineNumber as LineNumber,p.LineStatus as LineStatus
    ,m.ItemCode as ItemCode,m.ItemName as ItemName,s.ColorCode as ColorCode,s.SizeCode as SizeCode
    ,p.PurchaseQty as PurchaseQty,p.Price as Price,p.TaxInclusiveAmt as TaxInclusiveAmt
    ,p.ActualDate as ActualDate,p.IQCQty as IQCQty
    ,0 as IQCAMT,0 as DiffQty
from POLine p
inner join POHead h on p.OrderNumber=h.OrderNumber
inner join ItemSpec s on s.SKUID=p.SKUID
inner join ItemMaster m on s.ItemID=m.ItemID
order by p.OrderNumber desc, p.LineNumber")
                  .Attach(typeof(POLine)).Attach(typeof(ItemSpec)).Attach(typeof(ItemMaster)).Attach(typeof(POHead))
                  .And(Exp.In("h.Status", status))
                  .SetPage(pageIndex, pageSize);
        if (!string.IsNullOrEmpty(itemCode.Trim())) query.And(Exp.Like("m.ItemCode", "%" + itemCode.Trim() + "%"));
        if (!string.IsNullOrEmpty(itemName.Trim())) query.And(Exp.Like("m.ItemName", "%" + itemName.Trim() + "%"));
        if (!string.IsNullOrEmpty(colorCode.Trim())) query.And(Exp.Like("s.ColorCode", "%" + colorCode.Trim() + "%"));
        if (!string.IsNullOrEmpty(sizeCode.Trim())) query.And(Exp.Like("s.SizeCode", "%" + sizeCode.Trim() + "%"));
        if (!string.IsNullOrEmpty(orderNum.Trim())) query.And(Exp.Like("p.OrderNumber", "%" + orderNum.Trim() + "%"));
        if (startDate > new DateTime(1900, 1, 1)) query.And(Exp.Ge("p.PlanDate", startDate));
        if (endDate > new DateTime(1900, 1, 1)) query.And(Exp.Le("p.PlanDate", endDate.AddDays(1)));

        if (fetch) count = query.Count();
        DataSet ds = query.DataSet();
        foreach (DataRow row in ds.Tables[0].Rows)
        {
            decimal iqcqty = Cast.Decimal(row["IQCQty"]);
            decimal price = Cast.Decimal(row["Price"]);
            row["IQCAMT"] = iqcqty * price;
            decimal poqty = Cast.Decimal(row["PurchaseQty"]);
            row["DiffQty"] = iqcqty - poqty;
        }
        return ds;
    }

    protected void MagicPager_PageChanged(object src, Magic.Web.UI.PageChangedEventArgs e)
    {
        this.magicPagerSub.PageSize = this.magicPagerMain.PageSize; //如果页面有2个翻页控件，则必须写上这一句(控件bug)
        using (ISession session = new Session())
        {
            this.QueryAndBindData(session, this.magicPagerMain.PageSize, e.NewPageIndex, false);
        }
    }
    protected void btnQuery_Click(object sender, ImageClickEventArgs e)
    {
        using (ISession session = new Session())
        {

            this.QueryAndBindData(session, this.magicPagerMain.PageSize, 1, true);
        }
    }
    protected void MagicItemCommand(object sender, MagicItemEventArgs e)
    {
        if (e.CommandName != "Download")
            return;

        using (ISession session = new Session())
        {
            int count = 0;
            DataSet ds = this.QueryData(session, 65530, 1, false, ref count);
            string fileName = DownloadUtil.DownloadXls("PO_" + DateTime.Now.ToString("yyMMdd") + ".xls", "RPT_PO_",
                new List<DownloadFormat>()
                    {
                        new DownloadFormat(DataType.Text, "采购订单", "OrderNumber"),
                        new DownloadFormat(DataType.NumberText, "行号", "LineNumber"),
                        new DownloadFormat(DataType.NumberText, "货号", "ItemCode"),
                        new DownloadFormat(DataType.NumberText, "颜色", "ColorCode"),
                        new DownloadFormat(DataType.Text, "尺码", "SizeCode"),
                        new DownloadFormat(DataType.Text, "商品名称", "ItemName"),
                        new DownloadFormat(DataType.Number, "采购数量", "PurchaseQty"),
                        new DownloadFormat(DataType.Number, "单价", "Price"),
                        new DownloadFormat(DataType.Number, "金额", "TaxInclusiveAmt"),
                        new DownloadFormat(DataType.Date, "收货时间", "ActualDate"),
                        new DownloadFormat(DataType.Number, "收货数量", "IQCQty"),
                        new DownloadFormat(DataType.Number, "收货金额", "IQCAMT"),
                        new DownloadFormat(DataType.Number, "差异数量", "DiffQty")
                    }, ds);
            this.frameDownload.Attributes["src"] = fileName;
        }
        WebUtil.DownloadHack(this, "txtStartDate", "txtEndDate");
    }
}
