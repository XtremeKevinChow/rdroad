using System;
using System.Collections;
using System.Collections.Generic;
using Magic.Framework.ORM;
using Magic.ERP.Orders;
using Magic.Basis;
using Magic.Framework.Utils;

public partial class SaleDelivery_DeliverDownload : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        string snNumber = WebUtil.Param("ordNum");
        if (string.IsNullOrEmpty(snNumber)) return;

        ExcelTemplate template = null;
        IDictionary<string, string> items = new Dictionary<string, string>();
        #region 获取数据
        //所有要替换的标签先初始化为空值
        items["$RecipientName$"] = "";
        items["$RecipientCity$"] = "";
        items["$RecipientArea$"] = "";
        items["$RecipientAddress$"] = "";
        items["$RecipientPhone$"] = "";
        items["$RecipientMobile$"] = "";
        items["$RecipientZipCode$"] = "";
        items["$AgencyAmt$"] = "";
        items["$PaidAmt$"] = "";
        items["$Remark$"] = "";
        items["$Weight$"] = "";
        items["$PackageCount$"] = "";
        using (ISession session = new Session())
        {
            CRMSN sn = CRMSN.Retrieve(session, snNumber);
            if (sn == null) return;
            Member mbr = Member.Retrieve(session, sn.MemberID);
            template = ExcelTemplate.Retrieve(session, WebUtil.ParamInt("tid", 0));
            if (template == null) return;

            System.Text.RegularExpressions.Regex regex = new System.Text.RegularExpressions.Regex("(<br.*?>)|(</br>)", System.Text.RegularExpressions.RegexOptions.IgnoreCase);
            items["$RecipientName$"] = sn.Contact;
            items["$RecipientCity$"] = sn.City;
            items["$RecipientArea$"] = Cast.String(sn.Province) + " " + Cast.String(sn.City); //省市区，缺少区
            items["$RecipientAddress$"] = sn.Address;
            items["$RecipientCompany$"] = "";
            items["$RecipientPhone$"] = "'" + sn.Phone;
            items["$RecipientMobile$"] = "'" + (string.IsNullOrEmpty(sn.Mobile) || sn.Mobile.Trim().Length <= 0 ? mbr.Mobile : sn.Mobile);
            items["$RecipientZipCode$"] = "'" + sn.PostCode;
            items["$AgencyAmt$"] = RenderUtil.FormatNumber(sn.AgentAmt, "#0.##", "");
            items["$PaidAmt$"] = "'" + RenderUtil.FormatNumber(sn.PaidAmt, "#0.##", "");
            items["$Remark$"] = "'" + regex.Replace(Cast.String(sn.Remark), "\r\n");
            items["$Weight$"] = sn.PackageWeight <= 0 ? "" : ("'" + sn.PackageWeight.ToString("#0.##"));
            items["$PackageCount$"] = "'" + RenderUtil.FormatNumber(sn.PackageCount, "#0.##", "");
        }
        #endregion

        this.Response.Redirect(DownloadUtil.DownloadXls("SN_" + snNumber + ".xls", "SN", Server.MapPath(template.FileVirtualPath), items));
    }
}