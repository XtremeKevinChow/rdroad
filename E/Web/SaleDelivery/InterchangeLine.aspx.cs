using System;
using System.Collections.Generic;
using System.Data;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using Magic.Framework.Utils;
using Magic.Framework.ORM;
using Magic.ERP;
using Magic.ERP.Orders;
using Magic.Basis;
using Magic.Web.UI;

public partial class SaleDelivery_InterchangeLine : System.Web.UI.Page
{
    private ICHead _head = null;

    private string OrderNumber
    {
        get
        {
            return WebUtil.Param("ordNum").Trim();
        }
    }
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            this.txtOrderNumber.Value = this.OrderNumber;
            this.txtReturnUrl.Value = WebUtil.Param("return");
            this.cmdReturn1["Return"].NavigateUrl = this.cmdReturn2["Return"].NavigateUrl = WebUtil.Param("return");
            using (ISession session = new Session())
            {
                ICHead head = ICHead.Retrieve(session, this.OrderNumber);
                this.SetView(head);
                this.QueryAndBindData(session, head);
            }
        }
        else this.frameDownload.Attributes["src"] = "about:blank;";
    }

    private void SetView(ICHead head)
    {
        switch (head.Status)
        {
            case InterchangeStatus.New:
                this.cmdEdit1.Visible = true;
                this.cmdEdit2.Visible = true;
                this.cmdClose1.Visible = false;
                this.cmdClose2.Visible = false;
                this.cmdPrint1.Visible = false;
                this.cmdPrint2.Visible = false;
                break;
            case InterchangeStatus.Release:
                this.cmdEdit1.Visible = false;
                this.cmdEdit2.Visible = false;
                this.cmdClose1.Visible = false;
                this.cmdClose2.Visible = false;
                this.cmdPrint1.Visible = true;
                this.cmdPrint2.Visible = true;
                break;
            case InterchangeStatus.Open:
                this.cmdEdit1.Visible = false;
                this.cmdEdit2.Visible = false;
                this.cmdClose1.Visible = true;
                this.cmdClose2.Visible = true;
                this.cmdPrint1.Visible = true;
                this.cmdPrint2.Visible = true;
                break;
            case InterchangeStatus.Close:
                this.cmdEdit1.Visible = false;
                this.cmdEdit2.Visible = false;
                this.cmdClose1.Visible = false;
                this.cmdClose2.Visible = false;
                this.cmdPrint1.Visible = true;
                this.cmdPrint2.Visible = true;
                break;
        }
    }

    private void QueryAndBindData(ISession session, ICHead head)
    {
        this.repeatControl.DataSource = session.CreateObjectQuery(@"
select il.LineNumber as LineNumber,il.OrderNumber as OrderNumber,sn.OrderNumber as SNNumber,sn.SaleOrderNumber as SONumber,sn.ShippingNumber as ShippingNumber
    ,m.Name as Name,sn.Contact as Contact,sn.Phone as Phone,sn.Address as Address,sn.Province as Province,sn.City as City
from ICLine il
inner join CRMSN sn on il.RefOrderNumber=sn.OrderNumber
left join Member m on m.MemberID=sn.MemberID
where il.OrderNumber=?ordNum
order by il.LineNumber")
            .Attach(typeof(ICLine)).Attach(typeof(CRMSN)).Attach(typeof(Member))
            .SetValue("?ordNum", this.OrderNumber, "il.OrderNumber")
            .DataSet();
        this._head = head;
        this.repeatControl.DataBind();
        this._head = null;
    }
    protected void repeatControl_ItemDataBound(object sender, RepeaterItemEventArgs e)
    {
        DataRowView drv = e.Item.DataItem as DataRowView;
        if (drv == null) return;
        HtmlInputCheckBox chk = e.Item.FindControl("checkbox") as HtmlInputCheckBox;
        if (this._head != null && this._head.Status != InterchangeStatus.New)
            chk.Visible = false;
    }

    protected void MagicItemCommand(object sender, MagicItemEventArgs e)
    {
        if (e.CommandName == "Delete")
        {
            #region 删除
            using (ISession session = new Session())
            {
                try
                {
                    session.BeginTransaction();
                    foreach (RepeaterItem item in this.repeatControl.Items)
                    {
                        HtmlInputCheckBox chk = item.FindControl("checkbox") as HtmlInputCheckBox;
                        if (chk.Checked)
                        {
                            ICLine line = ICLine.Retrieve(session, this.OrderNumber, chk.Value.Trim());
                            if (line != null)
                                line.Delete(session);
                        }
                    }
                    session.Commit();
                    this.QueryAndBindData(session, null);
                    WebUtil.ShowMsg(this, "选择的明细已经删除");
                }
                catch (Exception er)
                {
                    session.Rollback();
                    WebUtil.ShowError(this, er);
                }
            }
            #endregion
        }
        else if (e.CommandName == "Release")
        {
            #region 发布
            using (ISession session = new Session())
            {
                try
                {
                    session.BeginTransaction();
                    ICHead head = ICHead.Retrieve(session, this.OrderNumber);
                    head.Release(session);
                    session.Commit();
                    WebUtil.ShowMsg(this, "发布成功");
                    this.QueryAndBindData(session, head);
                    this.SetView(head);
                }
                catch (Exception er)
                {
                    session.Rollback();
                    WebUtil.ShowError(this, er);
                }
            }
            #endregion
        }
        else if (e.CommandName == "Close")
        {
            #region 关闭
            using (ISession session = new Session())
            {
                try
                {
                    ICHead head = ICHead.Retrieve(session, this.OrderNumber);
                    if (head == null) return;
                    session.BeginTransaction();
                    head.Close(session);
                    session.Commit();
                    WebUtil.ShowMsg(this, "交接单" + head.OrderNumber + "已经完成");
                    this.QueryAndBindData(session, head);
                    this.SetView(head);
                }
                catch (Exception er)
                {
                    session.Rollback();
                    WebUtil.ShowError(this, er);
                }
            }
            #endregion
        }
        else if (e.CommandName == "Download")
        {
            #region 下载
            DataSet ds = null;
            IDictionary<string, string> dic = new Dictionary<string, string>();
            #region 初始化dic
            dic["$LogisName$"] = "";
            dic["$LogisAddress$"] = "";
            dic["$LogisContact$"] = "";
            dic["$LogisPhone$"] = "";
            dic["$LogisZipCode$"] = "";
            dic["$LogisFax$"] = "";
            dic["$ICNumber$"] = "";
            dic["$ICUser$"] = "";
            dic["$AgentAmt$"] = "";
            dic["$Note$"] = "";
            dic["$Status$"] = "";
            dic["$ICTime$"] = "";
            dic["$ICBoxCount$"] = "";
            #endregion
            IList<DownloadFormat> format = new List<DownloadFormat>() {
                new DownloadFormat(DataType.NumberText, "", "OrderNumber"),
                new DownloadFormat(DataType.NumberText, "", "ShippingNumber"),
                new DownloadFormat(DataType.NumberText, "", "SaleOrderNumber"),
                new DownloadFormat(DataType.NumberText, "", "InvoiceNumber"),
                new DownloadFormat(DataType.Number, "", "PackageWeight"),
                new DownloadFormat(DataType.Number, "", "PackageCount"),
                new DownloadFormat(DataType.Text, "", "Contact"),
                new DownloadFormat(DataType.Text, "", "Province", "City"), //省市2区县待完善
                new DownloadFormat(DataType.NumberText, "", "PostCode"),
                new DownloadFormat(DataType.Text, "", "Address"),
                new DownloadFormat(DataType.NumberText, "", "Phone"),
                new DownloadFormat(DataType.NumberText, "", "Mobile"),
                new DownloadFormat(DataType.Number, "", "AgentAmt"),
                new DownloadFormat(DataType.Text, "", "Remark")
            };
            using (ISession session = new Session())
            {
                ds = session.CreateObjectQuery(@"
SELECT 
    A.OrderNumber AS OrderNumber,A.ShippingNumber as ShippingNumber,A.SaleOrderNumber AS SaleOrderNumber
    ,A.InvoiceNumber as InvoiceNumber,A.PackageWeight as PackageWeight,A.PackageCount as PackageCount
    ,A.Contact as Contact,A.Province as Province,A.City as City,A.Address as Address,A.Mobile as Mobile,A.Phone as Phone
    ,A.AgentAmt as AgentAmt,A.Remark as Remark,A.PostCode as PostCode
FROM ICLine L
inner JOIN CRMSN A ON L.RefOrderNumber=A.OrderNumber
LEFT JOIN Member E ON A.MemberID= E.MemberID
order by L.LineNumber")
                .Attach(typeof(Magic.ERP.Orders.ICLine))
                .Attach(typeof(Magic.ERP.Orders.CRMSN))
                .Attach(typeof(Magic.Basis.Member))
                .And(Magic.Framework.ORM.Query.Exp.Eq("L.OrderNumber", this.OrderNumber))
                .DataSet();

                decimal totalAmt = 0M;
                foreach (DataRow row in ds.Tables[0].Rows)
                    totalAmt += Cast.Decimal("AgentAmt");

                ICHead head = ICHead.Retrieve(session, this.OrderNumber);
                if (head == null)
                {
                    WebUtil.ShowError(this, "交接单" + this.OrderNumber + "不存在");
                    return;
                }
                dic["$ICNumber$"] = "'" + head.OrderNumber;
                dic["$AgentAmt$"] = totalAmt.ToString("#0.#0");
                dic["$Note$"] = "'" + head.Note;
                dic["$Status$"] = ERPUtil.StatusText(session, CRMSN.ORDER_TYPE_CODE_SD, head.Status);
                dic["$ICTime$"] = "'" + head.CreateTime.ToString("yyyy-MM-dd");
                dic["$ICBoxCount$"] = "'" + head.TotalPackageCount(session).ToString();
                Logistics logis = Logistics.Retrieve(session, head.LogisticCompID);
                if (logis != null)
                {
                    dic["$LogisName$"] = logis.ShortName;
                    dic["$LogisAddress$"] = logis.Address;
                    dic["$LogisContact$"] = logis.Contact;
                    dic["$LogisPhone$"] = "'" + logis.Phone;
                    dic["$LogisZipCode$"] = "'" + logis.ZipCode;
                    dic["$LogisFax$"] = "'" + logis.Fax;
                }
                if (head.CreateUser > 0)
                {
                    Magic.Sys.User user = Magic.Sys.User.Retrieve(session, head.CreateUser);
                    if (user != null) dic["$ICUser$"] = user.FullName;
                }
            }
            if (ds == null)
            {
                WebUtil.ShowError(this, "没有数据下载或者下载出错了");
                return;
            }

            string fileName = DownloadUtil.DownloadXls("IC_" + this.OrderNumber + ".xls", "IC", Server.MapPath("/Template/IC_Download.xls"), dic, 7, format, ds);
            this.frameDownload.Attributes["src"] = fileName;
            #endregion
        }
    }

    protected void cmdAddLines_Click(object sender, EventArgs e)
    {
        string[] linesArray = this.txtSkus.Value.Trim().Trim(';').Split(';');
        if (linesArray == null || linesArray.Length <= 0) return;

        using (ISession session = new Session())
        {
            ICHead head = ICHead.Retrieve(session, this.OrderNumber);
            if (head == null) return;
            try
            {
                session.BeginTransaction();
                head.AddLines(session, linesArray);
                session.Commit();
                this.QueryAndBindData(session, head);
            }
            catch (Exception er)
            {
                session.Rollback();
                WebUtil.ShowError(this, er);
            }
        }
    }
}