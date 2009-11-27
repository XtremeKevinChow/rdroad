<%@ Control Language="C#" AutoEventWireup="false" %>
<%@ Import Namespace="System.Data" %>
<%@ Import Namespace="System.Collections.Generic" %>
<%@ Import Namespace="Magic.Basis" %>
<%@ Import Namespace="Magic.ERP.Core" %>
<%@ Import Namespace="Magic.Framework.Utils" %>
<%@ Import Namespace="Magic.Framework.ORM" %>
<%@ Import Namespace="Magic.Framework.ORM.Query" %>
<table id="queryArea" cellpadding="0" cellspacing="0" style="width: 100%;">
    <tr>
        <td class="label" style="width: 35px;">货号:</td>
        <td><input  id="selectorItemCode"  type="text"  style="width:70px;" class="input" /></td>
        <td class="label" style="width: 35px;">名称:</td>
        <td><input  id="selectorItemName"  type="text"  style="width:170px;" class="input" /></td>
        <td class="label" style="width: 35px;">颜色:</td>
        <td><input  id="selectorColor"  type="text"  style="width:40px;" class="input" /></td>
        <td class="label" style="width: 35px;">尺码:</td>
        <td><input  id="selectorSize"  type="text"  style="width:50px;" class="input" /></td>
        <td class="queryButton">
            <input type="image" style="border-width: 0px;" src="../Images/search.gif" id="sel_cmdQuery" />
        </td>
    <tr>        
</table>
<div style="height: 4px; overflow: hidden; width: 50%;"></div>
<table id="dataArea" cellpadding="0" cellspacing="0" style="width: 100%; height: 100%; text-align:left;">
    <tr>
        <th style="width: 20px;">&nbsp;</th>
        <th style="width:100px;">货号</th>
        <th>名称</th>
        <th style="width:50px;">颜色</th>
        <th style="width:70px;">尺码</th>
    </tr>
    <%
        int pageSize = 50;
        int count = -1;
        string code = WebUtil.Param("itemCode");
        string name = WebUtil.Param("itemName");
        string color = WebUtil.Param("colorCode");
        string size = WebUtil.Param("sizeCode");
		
        using (ISession session = new Session())
        {
            int index = WebUtil.ParamInt("pi", -1);
            if (index <= 0) index = 1;
            int queryType = WebUtil.ParamInt("qt", -1);
            if (queryType < 1 || queryType > 5) queryType = 1;

            ObjectQuery query = session.CreateObjectQuery(@"
select m.ItemID as ItemID,m.ItemCode as ItemCode,m.ItemName as ItemName,
    s.SKUID as SKUID,s.ColorCode as ColorCode,s.SizeCode as SizeCode
from ItemMaster m
inner join ItemSpec s on m.ItemID=s.ItemID
where m.ItemType in (@assistantItem, @normalItem)
order by m.ItemCode,s.ColorCode,s.SizeCode")
                     .Attach(typeof(ItemMaster)).Attach(typeof(ItemSpec))
                     .SetValue("@assistantItem", ItemType.AssistantItem, "m.ItemType")
                     .SetValue("@normalItem", ItemType.NormalItem, "m.ItemType")
                     .SetPage(index, pageSize);
            if (!string.IsNullOrEmpty(code)) query.And(Exp.Like("m.ItemCode", "%" + code + "%"));
            if (!string.IsNullOrEmpty(name)) query.And(Exp.Like("m.ItemName", "%" + name + "%"));
            if (!string.IsNullOrEmpty(color)) query.And(Exp.Like("s.ColorCode", "%" + color + "%"));
            if (!string.IsNullOrEmpty(size)) query.And(Exp.Like("s.SizeCode", "%" + size + "%"));
            DataSet ds = query.DataSet();
            if (queryType == 1)
                count = (query.Count() + pageSize - 1) / pageSize;

            foreach (DataRow row in ds.Tables[0].Rows)
            {
    %>
    <tr>
        <td style="width:20px;">
            <input type="checkbox" itemId='<%=row["ItemID"] %>' itemCode='<%=row["ItemCode"] %>' itemName='<%=row["ItemName"] %>' skuId='<%=row["SKUID"] %>' colorCode='<%=row["ColorCode"] %>' sizeCode='<%=row["SizeCode"] %>' class="chk" />
        </td>
		<td><%= row["ItemCode"] %></td>
		<td><%= row["ItemName"] %></td>
		<td><%= row["ColorCode"]%></td>
		<td><%= row["SizeCode"]%></td>
    </tr>
    <%
        }
        }
    %>
</table>

<script type="text/javascript">
function Item_on_load(params){
    var thisRef=this;
    var theForm = $(this.mfs_formId);
    if(thisRef.settings.mode!="s" && thisRef.settings.mode!="m") thisRef.settings.mode="s";
	$("#selectorItemCode", theForm).val(params.itemCode); 
	$("#selectorItemName", theForm).val(params.itemName); 
	$("#selectorColor", theForm).val(params.colorCode); 
	$("#selectorSize", theForm).val(params.sizeCode); 
   
    if(params.selected && params.selected.constructor==Array)
        $("#dataArea", theForm).find(".chk").each(function(i, e){
            var t = $.grep(params.selected, function(n, i){ return n.skuId==$(e).attr("skuId"); });
            if(t.length>0) {
                $(e).attr("checked", true);
                t[0].itemId = $(e).attr("itemId"); 
                t[0].itemCode = $(e).attr("itemCode"); 
                t[0].itemName = $(e).attr("ItemName"); 
                t[0].skuId = $(e).attr("skuId"); 
                t[0].colorCode = $(e).attr("colorCode"); 
                t[0].sizeCode = $(e).attr("sizeCode"); 
            }
            $(e).bind("click", function(){
                var isChecked = $(this).attr("checked");
                if(thisRef.settings.mode=="s") { 
                    if(isChecked) {					
                        params.selected = [ { itemId:$(this).attr("itemId"), itemCode:$(this).attr("itemCode") ,itemName:$(this).attr("itemName") ,skuId:$(this).attr("skuId") , colorCode:$(this).attr("colorCode") ,sizeCode:$(this).attr("sizeCode") } ];
                        $("#dataArea", theForm).find(".chk" + "[@skuId!='" +$(this).attr("skuId")+"']").removeAttr("checked");
                    }
                    else params.selected = [] ;
                }else { 
                    if(isChecked){
                        params.selected.push({ itemId:$(this).attr("itemId"), itemCode:$(this).attr("itemCode") ,itemName:$(this).attr("itemName") ,skuId:$(this).attr("skuId") , colorCode:$(this).attr("colorCode") ,sizeCode:$(this).attr("sizeCode") });
                    } else {
                        params.selected = $.grep(params.selected, function(n, i){
                            return n.skuId==$(e).attr("skuId");
                        });
                    }
                }
            });//$(e).bind("click", function()
        });//$("#dataArea").find(".chk").each(function(i, e)
        
        return <%= count.ToString() %>;
}
function Item_on_query(params){
	params.itemCode = $("#selectorItemCode", $(this.mfs_formId)).val(); 
	params.itemName = $("#selectorItemName", $(this.mfs_formId)).val(); 
	params.colorCode = $("#selectorColor", $(this.mfs_formId)).val(); 
	params.sizeCode = $("#selectorSize", $(this.mfs_formId)).val(); 
}
function Item_on_select(params){
    return params.selected;
}
function Item_on_close(){
}
</script>