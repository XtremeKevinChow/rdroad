using System;
using System.Collections;
using System.Configuration;
using System.Data;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using Magic.Framework.Utils;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Query;
using Magic.Web.UI;
using Magic.Sys;
using Magic.Basis;
using Magic.ERP;
using Magic.ERP.Orders;
using Magic.ERP.Core;

public partial class Purchase_POLook : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            using (ISession session = new Session())
            {
                QueryAndBindData(session);
            }
        }
    }

    void QueryAndBindData(ISession session)
    {
        string OrderNumber = WebUtil.Param("OrderNum");
        string LineNumber =  WebUtil.Param("CurrentLineNumber");
        if (!string.IsNullOrEmpty(OrderNumber) && !string.IsNullOrEmpty(LineNumber))
        {
            //显示POHead内容
            POHead poHead = POHead.Retrieve(session, OrderNumber);
            if (poHead != null)
            {
                this.LabOrderNumber.Text = poHead.OrderNumber;

                this.LabTaxAmt.Text = poHead.TaxAmt.ToString();
                this.LabTaxExclusiveAmt.Text = poHead.TaxExclusiveAmt.ToString();
                this.LabTaxInclusiveAmt.Text = poHead.TaxInclusiveAmt.ToString();
                //供应商
                Vendor vendor = Vendor.Retrieve(session, poHead.VendorID);
                if (vendor != null)
                    this.LabVendorID.Text = vendor.FullName;
                //采购组
                PurchaseGroup purchasegroup = PurchaseGroup.Retrieve(session, poHead.PurchGroupCode);
                if (purchasegroup != null)
                    this.LabPurchGroupCode.Text = purchasegroup.PurchGroupText;
            }
            //POLine.SKUID=ItemSpec.SKUID  ItemSpec.ItemID=ItemMaster.ItemID
            //货号ItemMaster.ItemCode  商品名称ItemMaster.ItemName 
            //颜色ItemSpec.ColorCode  尺码  ItemSpec.SizeCode
            this.magicPagerSub.PageSize = 20;
            int pageIndex = this.magicPagerMain.CurrentPageIndex;
            int pageSize = this.magicPagerMain.PageSize;
            ObjectQuery query = session.CreateObjectQuery(@"select m.ItemCode as ItemCode,m.ItemName as ItemName,s.ColorCode as ColorCode,s.SizeCode as SizeCode,p.LineStatus as LineStatus,p.PurchaseQty as PurchaseQty,p.PlanDate as PlanDate,p.Price as Price,p.TaxInclusiveAmt as TaxInclusiveAmt,p.TaxValue as TaxValue,p.TaxAmt as TaxAmt,p.LineNumber as LineNumber,p.OrderNumber as OrderNumber from POLine p,ItemSpec s,ItemMaster m WHERE p.SKUID=s.SKUID AND s.ItemID=m.ItemID AND p.OrderNumber=?").Attach(typeof(POLine)).Attach(typeof(ItemSpec)).Attach(typeof(ItemMaster)).SetValue(0, OrderNumber, "p.OrderNumber").SetPage(pageIndex, pageSize);
            this.rptPL.DataSource = query.DataSet();
            this.rptPL.DataBind();

            this.magicPagerMain.RecordCount = this.magicPagerSub.RecordCount = query.Count();
            WebUtil.SetMagicPager(magicPagerMain, pageSize, pageIndex);
            WebUtil.SetMagicPager(magicPagerSub, pageSize, pageIndex);
        }
    }

    protected void MagicPager_PageChanged(object src, Magic.Web.UI.PageChangedEventArgs e)
    {
        //翻页事件
        this.magicPagerMain.CurrentPageIndex = this.magicPagerSub.CurrentPageIndex = e.NewPageIndex;
        this.magicPagerSub.PageSize = this.magicPagerMain.PageSize; //如果页面有2个翻页控件，则必须写上这一句(控件bug)
        using (ISession session = new Session())
        {
            QueryAndBindData(session);
        }
    }
}
