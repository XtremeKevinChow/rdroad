<%@ Control Language="C#" AutoEventWireup="false" %>
<%@ Import Namespace="System.Collections.Generic" %>
<%@ Import Namespace="Magic.Basis" %>
<%@ Import Namespace="Magic.Framework.Utils" %>
<%@ Import Namespace="Magic.Framework.ORM" %>
<%@ Import Namespace="Magic.Framework.ORM.Query" %>
<table id="queryArea" cellpadding="0" cellspacing="0" style="width: 100%;">
    <tr>
        <td style="width: 60px;">简称</td>
        <td><input  id="txtShortName"  type="text"  style="width:70px;" class="input" /></td>
        <td style="width: 60px;">全称</td>
        <td><input  id="txtFullName"  type="text"  style="width:70px;" class="input" /></td>
        <td class="queryButton">
            <input type="image" style="border-width: 0px;" src="../Images/search.gif" id="sel_cmdQuery" />
        </td>		 
    <tr>        
</table>
<div style="height: 4px; overflow: hidden; width: 50%;"></div>
<table id="dataArea" cellpadding="0" cellspacing="0" style="width: 100%; height: 100%">
    <tr>
        <th style="width: 20px;">&nbsp;</th>
        <th style="width: 120px;">简称</th>
        <th>全称</th>
    </tr>
    <%
        int count = -1;
        string shortName = WebUtil.Param("shortName");
        string fullName = WebUtil.Param("fullName");
		
        using (ISession session = new Session())
        {
            int index = WebUtil.ParamInt("pi", -1);
            if (index <= 0) index = 1;
            int queryType = WebUtil.ParamInt("qt", -1);
            if (queryType < 1 || queryType > 5) queryType = 1;

            EntityQuery query = session.CreateEntityQuery<Vendor>()
                .Where(Exp.Eq("Status", VendorStatus.Enable))
                .SetPage(index, 9)
                .OrderBy("ShortName");
            if (!String.IsNullOrEmpty(shortName)) query.And(Exp.Like("ShortName", "%" + shortName + "%"));
            if (!String.IsNullOrEmpty(fullName)) query.And(Exp.Like("FullName", "%" + fullName + "%"));
            IList<Vendor> items = query.List<Vendor>();
            if (queryType == 1)
                count = (query.Count()+8) / 9;

            foreach (Vendor item in items)
            {
    %>
    <tr>
        <td style="width:20px;">
            <input type="checkbox" itemId='<%=item.VendorID %>' itemDesc='<%=item.ShortName %>' class="chk" />
        </td>
		<td><%= item.ShortName%></td>
		<td><%= item.FullName%></td>
    </tr>
    <%
        }
        }
    %>
</table>

<script type="text/javascript">
function Vendor_on_load(params){
    var thisRef=this;
    var theForm = $(this.mfs_formId);
    if(thisRef.settings.mode!="s" && thisRef.settings.mode!="m") thisRef.settings.mode="s";
	$("#txtShortName", theForm).val(params.shortName); 
	$("#txtFullName", theForm).val(params.fullName); 
   
    if(params.selected && params.selected.constructor==Array)
        $("#dataArea", theForm).find(".chk").each(function(i, e){
            var t = $.grep(params.selected, function(n, i){ return n.vendorId==$(e).attr("itemId"); });
            if(t.length>0) {
                $(e).attr("checked", true);
                t[0].vendorId = $(e).attr("itemId"); 
                t[0].vendorName = $(e).attr("itemDesc"); 
            }
            $(e).bind("click", function(){
                var isChecked = $(this).attr("checked");
                if(thisRef.settings.mode=="s") { 
                    if(isChecked) {					
                        params.selected = [ { vendorId:$(this).attr("itemId") ,vendorName:$(this).attr("itemDesc") } ];
                        $("#dataArea", theForm).find(".chk" + "[@itemId!='" +$(this).attr("itemId")+"']").removeAttr("checked");
                    }
                    else params.selected = [] ;
                }else { 
                    if(isChecked)
                        params.selected.push({ vendorId:$(this).attr("itemId") ,vendorName:$(this).attr("itemDesc") });
                    else 
                        params.selected = $.grep(params.selected, function(n, i){
                            return n.vendorId==$(e).attr("itemId");
                        });
                }
            });//$(e).bind("click", function()
        });//$("#dataArea").find(".chk").each(function(i, e)
        
        return <%= count.ToString() %>;
}
function Vendor_on_query(params){
	params.shortName = $("#txtShortName", $(this.mfs_formId)).val(); 
	params.fullName = $("#txtFullName", $(this.mfs_formId)).val(); 
}
function Vendor_on_select(params){
    return params.selected;
}
function Vendor_on_close(){
}
</script>