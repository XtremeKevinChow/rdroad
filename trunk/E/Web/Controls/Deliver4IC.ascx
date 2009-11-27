<%@ Control Language="C#" AutoEventWireup="false" %>
<%@ Import Namespace="System.Collections.Generic" %>
<%@ Import Namespace="System.Data" %>
<%@ Import Namespace="Magic.ERP" %>
<%@ Import Namespace="Magic.ERP.Orders" %>
<%@ Import Namespace="Magic.Basis" %>
<%@ Import Namespace="Magic.Framework.Utils" %>
<%@ Import Namespace="Magic.Framework.ORM" %>
<%@ Import Namespace="Magic.Framework.ORM.Query" %>
<table id="queryArea" cellpadding="0" cellspacing="0" style="width: 100%; text-align:left;">
    <tr>
        <td class="label" style="width: 50px;">发货单:</td>
        <td style="width:110px;"><input  id="txtDLVNumber"  type="text"  style="width:100px;" class="input" maxlength="16" /></td>
        <td class="label" style="width: 40px;">订单:</td>
        <td style="width:110px;"><input  id="txtSONumber"  type="text"  style="width:100px;" class="input" maxlength="16" /></td>
        <td class="label" style="width: 40px;">会员:</td>
        <td style="width:80px;"><input  id="txtMember"  type="text"  style="width:70px;" class="input" maxlength="10" /></td>
        <td class="queryButton">
            <input type="image" style="border-width: 0px;" src="../Images/search.gif" id="sel_cmdQuery" />
        </td>		 
    <tr>        
</table>
<div style="height: 4px; overflow: hidden; width: 50%;"></div>
<table id="dataArea" cellpadding="0" cellspacing="0" style="width: 100%; height: 100%">
    <tr>
        <th style="width: 20px;">&nbsp;</th>
        <th style="width:100px;">发货单</th>
        <th style="width:100px;">订单</th>
        <th style="width:60px;">会员</th>
        <th style="width:60px;">联系人</th>
        <th>送货地址</th>
    </tr>
    <%
        int count = -1;
        string soNumber = WebUtil.Param("so").Trim();
        string dlvNumber = WebUtil.Param("dlv").Trim();
        string member = WebUtil.Param("mbm").Trim();
		
        using (ISession session = new Session())
        {
            int index = WebUtil.ParamInt("pi", -1);
            if (index <= 0) index = 1;
            int queryType = WebUtil.ParamInt("qt", -1);
            if (queryType < 1 || queryType > 5) queryType = 1;

            ObjectQuery query = session.CreateObjectQuery(@"
select sn.OrderNumber as SNNumber,sn.SaleOrderNumber as SONumber,m.Name as MemberName
    ,sn.Contact as Contact,sn.Address as Address
from CRMSN sn
left join Member m on m.MemberID=sn.MemberID
where sn.Status=?status and sn.OrderNumber not in(
    select distinct icl.RefOrderNumber
    from ICHead ich
    inner join ICLine icl on ich.OrderNumber=icl.OrderNumber
    where ich.Status in (?ichNew,?ichRelease,?ichOpen)
)
order by sn.CheckDate desc")
                .Attach(typeof(CRMSN)).Attach(typeof(ICHead)).Attach(typeof(ICLine)).Attach(typeof(Member))
                .SetValue("?status", CRMSNStatus.Packaged, "sn.Status")
                .SetValue("?ichNew", InterchangeStatus.New, EntityManager.GetPropMapping(typeof(ICHead), "Status").DbTypeInfo)
                .SetValue("?ichRelease", InterchangeStatus.Release, EntityManager.GetPropMapping(typeof(ICHead), "Status").DbTypeInfo)
                .SetValue("?ichOpen", InterchangeStatus.Open, EntityManager.GetPropMapping(typeof(ICHead), "Status").DbTypeInfo)
                .SetPage(index, 13);
            if (!string.IsNullOrEmpty(soNumber)) query.And(Exp.Like("sn.SaleOrderNumber", "%"+ soNumber + "%"));
            if (!string.IsNullOrEmpty(dlvNumber)) query.And(Exp.Like("sn.OrderNumber", "%" + dlvNumber + "%"));
            if (!string.IsNullOrEmpty(member)) query.And(Exp.Like("m.Name", "%" + member + "%"));
            DataSet ds = query.DataSet();
            if (queryType == 1) count = (query.Count()+12) / 13;

            foreach (DataRow row in ds.Tables[0].Rows)
            {
    %>
    <tr>
        <td style="width:20px;">
            <input type="checkbox" ordNum='<%=row["SNNumber"] %>' ordType='' class="chk" />
        </td>
		<td style="width: 100px;"><%= RenderUtil.FormatString(row["SNNumber"])%></td>
		<td style="width:100px;"><%= RenderUtil.FormatString(row["SONumber"])%></td>
		<td style="width:60px;"><%= RenderUtil.FormatString(row["MemberName"])%></td>
		<td style="width:60px;"><%= RenderUtil.FormatString(row["Contact"])%></td>
		<td><%= RenderUtil.FormatString(row["Address"])%></td>
    </tr>
    <%
        }
        }
    %>
</table>

<script type="text/javascript">
function Deliver4IC_on_load(params){
    var thisRef=this;
    var theForm = $(this.mfs_formId);
    if(thisRef.settings.mode!="s" && thisRef.settings.mode!="m") thisRef.settings.mode="s";
	$("#txtSONumber", theForm).val(params.so); 
	$("#txtDLVNumber", theForm).val(params.dlv); 
	$("#txtMember", theForm).val(params.mbm); 
   
    if(params.selected && params.selected.constructor==Array)
        $("#dataArea", theForm).find(".chk").each(function(i, e){
            var t = $.grep(params.selected, function(n, i){ return n.ordNum==$(e).attr("ordNum"); });
            if(t.length>0) {
                $(e).attr("checked", true);
                t[0].ordNum = $(e).attr("ordNum"); 
                t[0].ordType = $(e).attr("ordType"); 
            }
            $(e).bind("click", function(){
                var isChecked = $(this).attr("checked");
                if(thisRef.settings.mode=="s") { 
                    if(isChecked) {					
                        params.selected = [ { ordNum:$(this).attr("ordNum") ,ordType:$(this).attr("ordType") } ];
                        $("#dataArea", theForm).find(".chk" + "[@ordNum!='" +$(this).attr("ordNum")+"']").removeAttr("checked");
                    }
                    else params.selected = [] ;
                }else { 
                    if(isChecked)
                        params.selected.push({ ordNum:$(this).attr("ordNum") ,ordType:$(this).attr("ordType") });
                    else 
                        params.selected = $.grep(params.selected, function(n, i){
                            return n.ordNum==$(e).attr("ordNum");
                        });
                }
            });//$(e).bind("click", function()
        });//$("#dataArea").find(".chk").each(function(i, e)
        
        return <%= count.ToString() %>;
}
function Deliver4IC_on_query(params){
	params.so = $("#txtSONumber", $(this.mfs_formId)).val(); 
	params.dlv = $("#txtDLVNumber", $(this.mfs_formId)).val(); 
	params.mbm = $("#txtMember", $(this.mfs_formId)).val(); 
}
function Deliver4IC_on_select(params){
    return params.selected;
}
function Deliver4IC_on_close(){
}
</script>