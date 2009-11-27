<%@ Control Language="C#" AutoEventWireup="false" %>
<%@ Import Namespace="System.Collections.Generic" %>
<%@ Import Namespace="Magic.Basis" %>
<%@ Import Namespace="Magic.Framework.Utils" %>
<%@ Import Namespace="Magic.Framework.ORM" %>
<%@ Import Namespace="Magic.Framework.ORM.Query" %>
<table id="queryArea" cellpadding="0" cellspacing="0" style="width: 100%;">
    <tr>
        <td style="width: 60px;">代码</td>
        <td><input  id="txtCode"  type="text"  style="width:70px;" class="input" maxlength="3" /></td>
        <td style="width: 60px;">名称</td>
        <td><input  id="txtName"  type="text"  style="width:70px;" class="input" maxlength="15" /></td>
        <td class="queryButton">
            <input type="image" style="border-width: 0px;" src="../Images/search.gif" id="sel_cmdQuery" />
        </td>		 
    <tr>        
</table>
<div style="height: 4px; overflow: hidden; width: 50%;"></div>
<table id="dataArea" cellpadding="0" cellspacing="0" style="width: 100%; height: 100%">
    <tr>
        <th style="width: 20px;">&nbsp;</th>
        <th style="width: 120px;">代码</th>
        <th>名称</th>
    </tr>
    <%
        int count = -1;
        string code = WebUtil.Param("code");
        string name = WebUtil.Param("name");
		
        using (ISession session = new Session())
        {
            int index = WebUtil.ParamInt("pi", -1);
            if (index <= 0) index = 1;
            int queryType = WebUtil.ParamInt("qt", -1);
            if (queryType < 1 || queryType > 5) queryType = 1;

            EntityQuery query = session.CreateEntityQuery<PurchaseGroup>().OrderBy("PurchGroupCode")
                .SetPage(index, 9);
            if (!String.IsNullOrEmpty(code)) query.And(Exp.Eq("PurchGroupCode", code));
            if (!String.IsNullOrEmpty(name)) query.And(Exp.Like("PurchGroupText", "%" + name + "%"));
            IList<PurchaseGroup> items = query.List<PurchaseGroup>();
            if (queryType == 1) count = (query.Count()+8) / 9;

            foreach (PurchaseGroup item in items)
            {
    %>
    <tr>
        <td style="width:20px;">
            <input type="checkbox" itemId='<%=item.PurchGroupCode %>' itemDesc='<%=item.PurchGroupText %>' class="chk" />
        </td>
		<td><%= item.PurchGroupCode%></td>
		<td><%= item.PurchGroupText%></td>
    </tr>
    <%
        }
        }
    %>
</table>

<script type="text/javascript">
function PurchaseGroup_on_load(params){
    var thisRef=this;
    var theForm = $(this.mfs_formId);
    if(thisRef.settings.mode!="s" && thisRef.settings.mode!="m") thisRef.settings.mode="s";
	$("#txtCode", theForm).val(params.code); 
	$("#txtName", theForm).val(params.name); 
   
    if(params.selected && params.selected.constructor==Array)
        $("#dataArea", theForm).find(".chk").each(function(i, e){
            var t = $.grep(params.selected, function(n, i){ return n.code==$(e).attr("itemId"); });
            if(t.length>0) {
                $(e).attr("checked", true);
                t[0].code = $(e).attr("itemId"); 
                t[0].name = $(e).attr("itemDesc"); 
            }
            $(e).bind("click", function(){
                var isChecked = $(this).attr("checked");
                if(thisRef.settings.mode=="s") { 
                    if(isChecked) {					
                        params.selected = [ { code:$(this).attr("itemId") ,name:$(this).attr("itemDesc") } ];
                        $("#dataArea", theForm).find(".chk" + "[@itemId!='" +$(this).attr("itemId")+"']").removeAttr("checked");
                    }
                    else params.selected = [] ;
                }else { 
                    if(isChecked)
                        params.selected.push({ code:$(this).attr("itemId") ,name:$(this).attr("itemDesc") });
                    else 
                        params.selected = $.grep(params.selected, function(n, i){
                            return n.code==$(e).attr("itemId");
                        });
                }
            });//$(e).bind("click", function()
        });//$("#dataArea").find(".chk").each(function(i, e)
        
        return <%= count.ToString() %>;
}
function PurchaseGroup_on_query(params){
	params.code = $("#txtCode", $(this.mfs_formId)).val(); 
	params.name = $("#txtName", $(this.mfs_formId)).val(); 
}
function PurchaseGroup_on_select(params){
    return params.selected;
}
function PurchaseGroup_on_close(){
}
</script>