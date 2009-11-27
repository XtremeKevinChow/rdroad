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
    string po = WebUtil.Param("po");
    string rcv = WebUtil.Param("rcv");
    string line = WebUtil.Param("line");
    string itemCode = WebUtil.Param("item");
    string color = WebUtil.Param("color").Trim().ToUpper();
    string size = WebUtil.Param("size").Trim().ToUpper();

    using (ISession session = new Session())
    {
%>
<table id="queryArea" cellpadding="0" cellspacing="0" style="width: 97%;">
    <tr>
        <td class="label" style="width: 35px;">行号:</td>
        <td style="width:60px;"><input  id="selectorLine"  type="text"  style="width:50px;" class="input" /></td>
        <td class="label" style="width: 35px;">货号:</td>
        <td style="width:90px;"><input  id="selectorItem"  type="text"  style="width:80px;" class="input" /></td>
        <td class="label" style="width: 35px;">颜色:</td>
        <td style="width:65px;"><input  id="selectorColor"  type="text"  style="width:55px;" class="input" /></td>
        <td class="label" style="width: 35px;">尺码:</td>
        <td style="width:65px;"><input  id="selectorSize"  type="text"  style="width:55px;" class="input" /></td>
        <td class="queryButton">
            <input type="image" style="border-width: 0px;" src="../Images/search.gif" id="sel_cmdQuery" />
        </td>
    <tr>        
</table>
<div style="height: 4px; overflow: hidden; width: 50%;"></div>
<table id="dataArea" cellpadding="0" cellspacing="0" style="width: 97%; height: 100%;text-align:left;">
    <tr style="text-align:center;">
        <th style="width: 20px;">&nbsp;</th>
        <th style="width:40px;">行号</th>
        <th style="width:80px;">货号</th>
        <th>商品名称</th>
        <th style="width:40px;">颜色</th>
        <th style="width:40px;">尺码</th>
        <th style="width:60px;">采购数量</th>
        <th style="width:80px;">可收货数量</th>
    </tr>
    <%
        ObjectQuery query = session.CreateObjectQuery(@"
select l.LineNumber as LineNumber,m.ItemCode as ItemCode,m.ItemName as ItemName,s.ColorCode as ColorCode,s.SizeCode as SizeCode
    ,l.PurchaseQty as PurchaseQty,l.IQCQty as IQCQty,l.UnfinishedReceiveQty as UnfinishedReceiveQty
from POLine l
inner join ItemSpec s on s.SKUID=l.SKUID
inner join ItemMaster m on m.ItemID=s.ItemID
where l.OrderNumber=?ordNum and l.LineStatus=?status
    and l.PurchaseQty-l.ReceiveQty-l.UnfinishedReceiveQty>0
    and l.LineNumber not in (select RefOrderLine from RCVLine where OrderNumber=?rcvNum)    
order by l.LineNumber")
            .Attach(typeof(POLine)).Attach(typeof(ItemSpec)).Attach(typeof(ItemMaster)).Attach(typeof(RCVLine))
            .SetValue("?ordNum", po, "l.OrderNumber")
            .SetValue("?status", POLineStatus.Open, "l.LineStatus")
            .SetValue("?rcvNum", rcv, EntityManager.GetPropMapping(typeof(RCVLine), "OrderNumber").DbTypeInfo);
        if (!string.IsNullOrEmpty(line)) query.And(Exp.Eq("l.LineNumber", line));
        if (!string.IsNullOrEmpty(itemCode)) query.And(Exp.Like("m.ItemCode", "%" + itemCode + "%"));
        if (!string.IsNullOrEmpty(color)) query.And(Exp.Like("s.ColorCode", "%" + color + "%"));
        if (!string.IsNullOrEmpty(size)) query.And(Exp.Like("s.SizeCode", "%" + size + "%"));

        DataSet ds = query.DataSet();
            foreach (DataRow row in ds.Tables[0].Rows)
            {
    %>
    <tr>
        <td style="width:20px;">
            <input type="checkbox" line='<%=row["LineNumber"] %>' class="chk" />
        </td>
        <td><%= row["LineNumber"]%></td>
		<td><%= row["ItemCode"]%></td>
		<td><%= row["ItemName"]%></td>
		<td><%= row["ColorCode"]%></td>
		<td><%= row["SizeCode"]%></td>
		<td style="text-align:right;"><%= Cast.Decimal(row["PurchaseQty"]).ToString() %></td>
		<td style="text-align:right;"><%= (Cast.Decimal(row["PurchaseQty"]) - Cast.Decimal(row["IQCQty"]) - Cast.Decimal(row["UnfinishedReceiveQty"])).ToString()%></td>
    </tr>
    <%
        }
        }
    %>
</table>

<script type="text/javascript">
function POLine4RCV_on_load(params){
    var thisRef=this;
    var theForm = $(this.mfs_formId);
    if(thisRef.settings.mode!="s" && thisRef.settings.mode!="m") thisRef.settings.mode="s";
	$("#selectorLine", theForm).val(params.line); 
	$("#selectorItem", theForm).val(params.item); 
	$("#selectorColor", theForm).val(params.color); 
	$("#selectorSize", theForm).val(params.size); 
   
    if(params.selected && params.selected.constructor==Array)
        $("#dataArea", theForm).find(".chk").each(function(i, e){
            var t = $.grep(params.selected, function(n, i){ return n.line==$(e).attr("line"); });
            if(t.length>0) {
                $(e).attr("checked", true);
            }
            $(e).bind("click", function(){
                var isChecked = $(this).attr("checked");
                if(thisRef.settings.mode=="s") { 
                    if(isChecked) {					
                        params.selected = [ { line:$(this).attr("line") } ];
                        $("#dataArea", theForm).find(".chk" + "[@line!='" +$(this).attr("line")+"']").removeAttr("checked");
                    }
                    else params.selected = [] ;
                }else { 
                    if(isChecked)
                        params.selected.push({ line:$(this).attr("line") });
                    else 
                        params.selected = $.grep(params.selected, function(n, i){
                            return n.line==$(e).attr("line");
                        });
                }
            });//$(e).bind("click", function()
        });//$("#dataArea").find(".chk").each(function(i, e)
        
        return 1;
}
function POLine4RCV_on_query(params){
	params.line = $("#selectorLine", $(this.mfs_formId)).val(); 
	params.item = $("#selectorItem", $(this.mfs_formId)).val(); 
	params.color = $("#selectorColor", $(this.mfs_formId)).val(); 
	params.size = $("#selectorSize", $(this.mfs_formId)).val(); 
}
function POLine4RCV_on_select(params){
    return params.selected;
}
function POLine4RCV_on_close(){
}
</script>