<%@ Control Language="C#" AutoEventWireup="false" %>
<%@ Import Namespace="System.Data" %>
<%@ Import Namespace="System.Collections.Generic" %>
<%@ Import Namespace="Magic.Basis" %>
<%@ Import Namespace="Magic.ERP" %>
<%@ Import Namespace="Magic.ERP.Core" %>
<%@ Import Namespace="Magic.ERP.Orders" %>
<%@ Import Namespace="Magic.Framework.Utils" %>
<%@ Import Namespace="Magic.Framework.ORM" %>
<%@ Import Namespace="Magic.Framework.ORM.Query" %>
<%
    int pageSize = 30;
    int count = -1;
    string po = WebUtil.Param("po");
    int vendor = WebUtil.ParamInt("vendor", 0);
    DateTime fromDate = Cast.DateTime(WebUtil.Param("from"), new DateTime(1900, 1, 1));
    DateTime toDate = Cast.DateTime(WebUtil.Param("to"), new DateTime(1900, 1, 1));

    using (ISession session = new Session())
    {
        int index = WebUtil.ParamInt("pi", -1);
        if (index <= 0) index = 1;
        int queryType = WebUtil.ParamInt("qt", -1);
        if (queryType < 1 || queryType > 5) queryType = 1;
%>
<table id="queryArea" cellpadding="0" cellspacing="0" style="width: 100%;">
    <tr>
        <td class="label" style="width: 60px;">采购订单:</td>
        <td><input  id="selectorPONumber"  type="text"  style="width:120px;" class="input" /></td>
        <td class="label" style="width: 45px;">供应商:</td>
        <td>
            <select id="selectorVendor" style="width:100px;">
                <option value="0">　</option><%
                                                IList<Vendor> vendors = session.CreateEntityQuery<Vendor>()
                                                    .Where(Exp.Eq("Status", VendorStatus.Enable))
                                                    .OrderBy("ShortName")
                                                    .List<Vendor>();
    foreach (Vendor v in vendors)
    {
        %><option value='<%= v.VendorID %>'><%= v.ShortName%></option><%
    }
%></select>
        </td>
        <td class="label" style="width: 60px;">订单日期:</td>
        <td>
            <input  id="selectorFromDate"  type="text"  style="width:80px;" class="input" />
            <span>&nbsp;到&nbsp;</span>
            <input  id="selectorToDate"  type="text"  style="width:80px;" class="input" />
        </td>
        <td class="queryButton">
            <input type="image" style="border-width: 0px;" src="../Images/search.gif" id="sel_cmdQuery" />
        </td>
    <tr>        
</table>
<div style="height: 4px; overflow: hidden; width: 50%;"></div>
<table id="dataArea" cellpadding="0" cellspacing="0" style="width: 100%; height: 100%;text-align:left;">
    <tr style="text-align:center;">
        <th style="width: 20px;">&nbsp;</th>
        <th style="width:100px;">采购订单</th>
        <th style="width:130px;">日期</th>
        <th>供应商</th>
    </tr>
    <%
            ObjectQuery query = session.CreateObjectQuery(@"
select po.OrderNumber as OrderNumber,v.ShortName as ShortName,po.CreateTime as CreateTime
from POHead po
inner join Vendor v on po.VendorID=v.VendorID
order by po.OrderNumber desc")
                .Attach(typeof(POHead)).Attach(typeof(Vendor))
                .Where(Exp.Eq("po.Status", POStatus.Release) & Exp.Eq("po.ApproveResult", ApproveStatus.Approve))
                .SetPage(index, pageSize);
            if (!string.IsNullOrEmpty(po)) query.And(Exp.Like("po.OrderNumber", "%" + po + "%"));
            if (vendor > 0) query.And(Exp.Eq("po.VendorID", vendor));
            if (fromDate > new DateTime(1900, 1, 1)) query.And(Exp.Ge("po.CreateTime", fromDate));
            if (toDate > new DateTime(1900, 1, 1)) query.And(Exp.Le("po.CreateTime", toDate));
            DataSet ds = query.DataSet();
            if (queryType == 1)
                count = (query.Count()+8) / 9;

            foreach (DataRow row in ds.Tables[0].Rows)
            {
    %>
    <tr>
        <td style="width:20px;">
            <input type="checkbox" po='<%=row["OrderNumber"] %>' class="chk" />
        </td>
		<td><%= row["OrderNumber"]%></td>
		<td><%= RenderUtil.FormatDatetime(row["CreateTime"])%></td>
		<td><%= row["ShortName"]%></td>
    </tr>
    <%
        }
        }
    %>
</table>

<script type="text/javascript">
function PO_on_load(params){
    var thisRef=this;
    var theForm = $(this.mfs_formId);
    if(thisRef.settings.mode!="s" && thisRef.settings.mode!="m") thisRef.settings.mode="s";
	$("#selectorPONumber", theForm).val(params.po); 
	$("#selectorVendor", theForm).val(params.vendor); 
	$("#selectorFromDate", theForm).val(params.from); 
	$("#selectorToDate", theForm).val(params.to); 
   
    if(params.selected && params.selected.constructor==Array)
        $("#dataArea", theForm).find(".chk").each(function(i, e){
            var t = $.grep(params.selected, function(n, i){ return n.po==$(e).attr("po"); });
            if(t.length>0) {
                $(e).attr("checked", true);
                t[0].po = $(e).attr("po"); 
            }
            $(e).bind("click", function(){
                var isChecked = $(this).attr("checked");
                if(thisRef.settings.mode=="s") { 
                    if(isChecked) {					
                        params.selected = [ { po:$(this).attr("po") } ];
                        $("#dataArea", theForm).find(".chk" + "[@po!='" +$(this).attr("po")+"']").removeAttr("checked");
                    }
                    else params.selected = [] ;
                }else { 
                    if(isChecked)
                        params.selected.push({ po:$(this).attr("po") });
                    else 
                        params.selected = $.grep(params.selected, function(n, i){
                            return n.po==$(e).attr("po");
                        });
                }
            });//$(e).bind("click", function()
        });//$("#dataArea").find(".chk").each(function(i, e)
        
        return <%= count.ToString() %>;
}
function PO_on_query(params){
	params.po = $("#selectorPONumber", $(this.mfs_formId)).val(); 
	params.vendor = $("#selectorVendor", $(this.mfs_formId)).val(); 
	params.from = $("#selectorFromDate", $(this.mfs_formId)).val(); 
	params.to = $("#selectorToDate", $(this.mfs_formId)).val(); 
}
function PO_on_select(params){
    return params.selected;
}
function PO_on_close(){
}
</script>