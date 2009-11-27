<%@ Control Language="C#" AutoEventWireup="false" %>
<%@ Import Namespace="System.Collections.Generic" %>
<%@ Import Namespace="Magic.Sys" %>
<%@ Import Namespace="Magic.Basis" %>
<%@ Import Namespace="Magic.Framework.Utils" %>
<%@ Import Namespace="Magic.Framework.ORM" %>
<%@ Import Namespace="Magic.Framework.ORM.Query" %>
<table id="queryArea" cellpadding="0" cellspacing="0" style="width: 100%;">
    <tr>
        <td class="label" style="width: 60px;">登陆帐号:</td>
        <td><input  id="txtUserName"  type="text"  style="width:70px;" class="input" /></td>
        <td class="label" style="width: 60px;">用户姓名:</td>
        <td><input  id="txtFullName"  type="text"  style="width:70px;" class="input" /></td>
        <td class="queryButton">
            <input type="image" style="border-width: 0px;" src="../Images/search.gif" id="sel_cmdQuery" />
            <input type="text" style="display:none"; id="txtGroupCode" value="" />
        </td>		 
    <tr>        
</table>
<div style="height: 4px; overflow: hidden; width: 50%;"></div>
<table id="dataArea" cellpadding="0" cellspacing="0" style="width: 100%; height: 100%">
    <tr>
        <th style="width: 20px;">&nbsp;</th>
        <th style="width: 90px;">登陆帐号</th>
        <th>用户姓名</th>
    </tr>
    <%
        int count = -1;
        string userName = WebUtil.Param("userName");
        string fullName = WebUtil.Param("fullName");
        string groupCode = WebUtil.Param("groupCode");
		
        using (ISession session = new Session())
        {
            int index = WebUtil.ParamInt("pi", -1);
            if (index <= 0) index = 1;
            int queryType = WebUtil.ParamInt("qt", -1);
            if (queryType < 1 || queryType > 5) queryType = 1;

            ObjectQuery query = session.CreateObjectQuery("select u.* from User u where u.UserId not in (select UserID from PurchaseGroup2User where PurchGroupCode=?)")
                .Attach(typeof(User)).Attach(typeof(PurchaseGroup2User))
                .SetValue(0, groupCode, EntityManager.GetEntityMapping(typeof(PurchaseGroup2User)).GetPropertyMapping("PurchGroupCode").DbTypeInfo)
                .SetPage(index, 9)
                .Where(Exp.Eq("Status", UserStatus.Enabled)).And(Exp.Eq("UserType", OrgType.Own))
                .OrderBy("FullName");
           	if(!String.IsNullOrEmpty(userName)) query.And(Exp.Like("UserName", "%" + userName+ "%")); 
           	if(!String.IsNullOrEmpty(fullName)) query.And(Exp.Like("FullName", "%" + fullName+ "%")); 
            IList<User> users = query.List<User>();
            if (queryType == 1)
                count = (query.Count()+8) / 9;

            foreach (User item in users)
            {
    %>
    <tr>
        <td style="width:20px;">
            <input type="checkbox" userId='<%=item.UserId %>' userName='<%=item.UserName %>' fullName='<%=item.FullName %>' class="chk" />
        </td>
		<td><%= item.UserName %></td>
		<td><%= item.FullName %></td>
    </tr>
    <%
        }
        }
    %>
</table>

<script type="text/javascript">
function PurchaseGroupUser_on_load(params){
    var thisRef=this;
    var theForm = $(this.mfs_formId);
    if(thisRef.settings.mode!="s" && thisRef.settings.mode!="m") thisRef.settings.mode="s";
	$("#txtUserName", theForm).val(params.userName); 
	$("#txtFullName", theForm).val(params.fullName); 
	$("#txtGroupCode", theForm).val(params.groupCode); 
   
    if(params.selected && params.selected.constructor==Array)
        $("#dataArea", theForm).find(".chk").each(function(i, e){
            var t = $.grep(params.selected, function(n, i){ return n.userId==$(e).attr("userId"); });
            if(t.length>0) {
                $(e).attr("checked", true);
                t[0].userName = $(e).attr("userName"); 
                t[0].fullName = $(e).attr("fullName"); 
            }
            $(e).bind("click", function(){
                var isChecked = $(this).attr("checked");
                if(thisRef.settings.mode=="s") { 
                    if(isChecked) {					
                        params.selected = [ { userId:$(this).attr("userId") ,userName:$(this).attr("userName") ,fullName:$(this).attr("fullName") } ];
                        $("#dataArea", theForm).find(".chk" + "[@userId!='" +$(this).attr("userId")+"']").removeAttr("checked");
                    }
                    else params.selected = [] ;
                }else { 
                    if(isChecked)
                        params.selected.push({ userId:$(this).attr("userId") ,userName:$(this).attr("userName") ,fullName:$(this).attr("fullName") });
                    else 
                        params.selected = $.grep(params.selected, function(n, i){
                            return n.userId==$(e).attr("userId");
                        });
                }
            });//$(e).bind("click", function()
        });//$("#dataArea").find(".chk").each(function(i, e)
        
        return <%= count.ToString() %>;
}
function PurchaseGroupUser_on_query(params){
	params.userName = $("#txtUserName", $(this.mfs_formId)).val(); 
	params.fullName = $("#txtFullName", $(this.mfs_formId)).val(); 
	params.orgId = $("#txtGroupCode", $(this.mfs_formId)).val(); 
}
function PurchaseGroupUser_on_select(params){
    return params.selected;
}
function PurchaseGroupUser_on_close(){
}
</script>