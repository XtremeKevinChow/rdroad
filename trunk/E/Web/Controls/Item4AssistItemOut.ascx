﻿<%@ Control Language="C#" AutoEventWireup="false" %>
<%@ Import Namespace="System.Data" %>
<%@ Import Namespace="System.Collections.Generic" %>
<%@ Import Namespace="Magic.Basis" %>
<%@ Import Namespace="Magic.ERP.Core" %>
<%@ Import Namespace="Magic.ERP.Orders" %>
<%@ Import Namespace="Magic.Framework.Utils" %>
<%@ Import Namespace="Magic.Framework.ORM" %>
<%@ Import Namespace="Magic.Framework.ORM.Query" %>
<table id="queryArea" cellpadding="0" cellspacing="0" style="width: 100%;">
    <tr>
        <td class="label" style="width: 35px;">SKU:</td>
        <td><input  id="selectorSku"  type="text"  style="width:80px;" class="input" /></td>
        <td class="label" style="width: 35px;">货号:</td>
        <td><input  id="selectorItemCode"  type="text"  style="width:70px;" class="input" /></td>
        <td class="label" style="width: 35px;">名称:</td>
        <td><input  id="selectorItemName"  type="text"  style="width:100px;" class="input" /></td>
        <td class="label" style="width: 35px;">仓库:</td>
        <td><input  id="selectorColor"  type="text"  style="width:40px;" class="input" /></td>
        <td class="label" style="width: 35px;">货架:</td>
        <td><input  id="selectorSize"  type="text"  style="width:40px;" class="input" /></td>
        <td class="queryButton">
            <input type="image" style="border-width: 0px;" src="../Images/search.gif" id="sel_cmdQuery" />
        </td>
    <tr>        
</table>
<div style="height: 4px; overflow: hidden; width: 50%;"></div>
<table id="dataArea" cellpadding="0" cellspacing="0" style="width: 100%; height: 100%; text-align:left;">
    <tr>
        <th style="width: 20px;">&nbsp;</th>
        <th style="width:100px;">SKU</th>
        <th style="width:90px;">货号</th>
        <th>名称</th>
        <th style="width:30px;">仓库</th>
        <th style="width:30px;">货架</th>
        <th style="width:70px;">可用数量</th>
    </tr>
    <%
        int pageSize = 50;
        int count = -1;
        string ordNum = WebUtil.Param("ordNum");
        string code = WebUtil.Param("itemCode");
        string name = WebUtil.Param("itemName");
        string color = WebUtil.Param("colorCode").Trim().ToUpper();
        string size = WebUtil.Param("sizeCode").Trim().ToUpper();
        string sku = WebUtil.Param("sku");
		
        using (ISession session = new Session())
        {
            int index = WebUtil.ParamInt("pi", -1);
            if (index <= 0) index = 1;
            int queryType = WebUtil.ParamInt("qt", -1);
            if (queryType < 1 || queryType > 5) queryType = 1;

            ObjectQuery query = session.CreateObjectQuery(@"
select m.ItemID as ItemID,m.ItemCode as ItemCode,m.ItemName as ItemName,
    s.SKUID as SKUID,s.ColorCode as ColorCode,s.SizeCode as SizeCode,s.BarCode as BarCode
    ,sto.StockQty as StockQty,sto.FrozenQty as FrozenQty,sto.AreaCode as AreaCode,sto.SectionCode as SectionCode
from ItemMaster m
inner join ItemSpec s on m.ItemID=s.ItemID
inner join StockDetail sto on sto.SKUID=s.SKUID
where m.ItemType=?assistantItem and sto.StockQty>0
order by m.ItemCode,s.ColorCode,s.SizeCode")
                     .Attach(typeof(ItemMaster)).Attach(typeof(ItemSpec)).Attach(typeof(StockInLine)).Attach(typeof(StockDetail))
                     .SetValue("?assistantItem", ItemType.AssistantItem, "m.ItemType")
                     .SetValue("?ordNumber", ordNum, EntityManager.GetPropMapping(typeof(StockInLine), "OrderNumber").DbTypeInfo)
                     .SetPage(index, pageSize);
            if (!string.IsNullOrEmpty(code)) query.And(Exp.Like("m.ItemCode", "%" + code + "%"));
            if (!string.IsNullOrEmpty(name)) query.And(Exp.Like("m.ItemName", "%" + name + "%"));
            if (!string.IsNullOrEmpty(color)) query.And(Exp.Like("sto.AreaCode", "%" + color + "%"));
            if (!string.IsNullOrEmpty(size)) query.And(Exp.Like("sto.SectionCode", "%" + size + "%"));
            if (!string.IsNullOrEmpty(sku)) query.And(Exp.Like("s.BarCode", "%" + sku + "%"));
            DataSet ds = query.DataSet();
            if (queryType == 1)
                count = (query.Count() + pageSize - 1) / pageSize;

            foreach (DataRow row in ds.Tables[0].Rows)
            {
    %>
    <tr>
        <td style="width:20px;">
            <input type="checkbox" skuId='<%=row["SKUID"] %>' area='<%=row["AreaCode"] %>' section="<%=row["SectionCode"] %> " avqty='<%= RenderUtil.FormatNumber(Cast.Decimal(row["StockQty"]) - Cast.Decimal(row["FrozenQty"]), "#0.##")%>' class="chk" />
        </td>
        <td><%= row["BarCode"]%></td>
		<td><%= row["ItemCode"] %></td>
		<td><%= row["ItemName"] %></td>
		<td><%= row["AreaCode"]%></td>
		<td><%= row["SectionCode"]%></td>
		<td><%= RenderUtil.FormatNumber(Cast.Decimal(row["StockQty"]) - Cast.Decimal(row["FrozenQty"]), "#0.##")%></td>
    </tr>
    <%
        }
        }
    %>
</table>

<script type="text/javascript">
function Item4AssistItemOut_on_load(params){
    var thisRef=this;
    var theForm = $(this.mfs_formId);
    if(thisRef.settings.mode!="s" && thisRef.settings.mode!="m") thisRef.settings.mode="s";
	$("#selectorItemCode", theForm).val(params.itemCode); 
	$("#selectorItemName", theForm).val(params.itemName); 
	$("#selectorColor", theForm).val(params.colorCode); 
	$("#selectorSize", theForm).val(params.sizeCode); 
	$("#selectorSku", theForm).val(params.sku); 
   
    if(params.selected && params.selected.constructor==Array)
        $("#dataArea", theForm).find(".chk").each(function(i, e){
            $(e).bind("click", function(){
                var isChecked = $(this).attr("checked");
                if(thisRef.settings.mode=="s") { 
                    if(isChecked) {					
                        params.selected = [ { skuId:$(this).attr("skuId"), qty: $(this).attr("avqty"), area: $(this).attr("area"), section: $(this).attr("section") } ];
                        $("#dataArea", theForm).find(".chk" + "[@skuId!='" +$(this).attr("skuId")+"']").removeAttr("checked");
                    }
                    else params.selected = [] ;
                }else {
                    if(isChecked){
                        params.selected.push({ skuId:$(this).attr("skuId"), qty: $(this).attr("avqty"), area: $(this).attr("area"), section: $(this).attr("section") });
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
function Item4AssistItemOut_on_query(params){
	params.itemCode = $("#selectorItemCode", $(this.mfs_formId)).val(); 
	params.itemName = $("#selectorItemName", $(this.mfs_formId)).val(); 
	params.colorCode = $("#selectorColor", $(this.mfs_formId)).val(); 
	params.sizeCode = $("#selectorSize", $(this.mfs_formId)).val(); 
	params.sku = $("#selectorSku", $(this.mfs_formId)).val(); 
}
function Item4AssistItemOut_on_select(params){
    return params.selected;
}
function Item4AssistItemOut_on_close(){
}
</script>