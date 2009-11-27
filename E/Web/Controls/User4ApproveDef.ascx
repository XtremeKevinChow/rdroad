<%@ Control Language="C#" AutoEventWireup="false" %>
<%@ Import Namespace="System.Collections.Generic" %>
<%@ Import Namespace="Magic.Sys" %>
<%@ Import Namespace="Magic.ERP.Core" %>
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
					<input type="hidden" style="display:none"; id="txtOrdType" value="" />
            </td>		 
    <tr>        
</table>
<div style="height: 4px; overflow: hidden; width: 50%;">
</div>
<table id="dataArea" cellpadding="0" cellspacing="0" style="width: 100%; height: 100%">
    <tr>
        <th style="width:20px;">&nbsp;
        </th>
        <th>
			登陆帐号
        </th>
        <th>
			用户姓名
        </th>
      
    </tr>
    <%
        int count = -1;
        string userName = WebUtil.Param("userName");
        string fullName = WebUtil.Param("fullName");
        string ordType = WebUtil.Param("ordType");

        using (ISession session = new Session())
        {
            int index = WebUtil.ParamInt("pi", -1);
            if (index <= 0) index = 1;
            int queryType = WebUtil.ParamInt("qt", -1);
            if (queryType < 1 || queryType > 5) queryType = 1;

            ObjectQuery query = session.CreateObjectQuery("select u.* from User u where u.UserId not in (select UserID from OrderApproveDef where OrderTypeCode=?)")
                .Attach(typeof(User)).Attach(typeof(OrderApproveDef))
                .SetValue(0, ordType, EntityManager.GetEntityMapping(typeof(OrderApproveDef)).GetPropertyMapping("OrderTypeCode").DbTypeInfo)
                .SetPage(index, 9)
                .Where(Exp.Eq("Status", UserStatus.Enabled)).And(Exp.Eq("UserType", OrgType.Own))
                .OrderBy("FullName");
            if (!string.IsNullOrEmpty(userName)) query.And(Exp.Like("UserName", "%" + userName + "%"));
            if (!string.IsNullOrEmpty(fullName)) query.And(Exp.Like("FullName", "%" + fullName + "%"));
            IList<User> users = query.List<User>();
            if (queryType == 1)
                count = (query.Count() + 8) / 9;

            foreach (User item in users)
            {
    %>
    <tr>
        <td style="width:20px;">
            <input type="checkbox" userId='<%=item.UserId %>' userName='<%=item.UserName %>'    fullName='<%=item.FullName %>'   class="chk" />
        </td>
		<td>
			<%= item.UserName %>
		</td>
		<td>
			<%= item.FullName %>
		</td>
        
    </tr>
    <%
        }
        }
    %>
</table>

<script type="text/javascript">
//params中包括User_on_query返回的各个属性，也包括调用query.fnPopup(settings)时settings.data的各个属性
function User4ApproveDef_on_load(params){
    var thisRef=this;
    var theForm = $(this.mfs_formId);
    if(thisRef.settings.mode!="s" && thisRef.settings.mode!="m") thisRef.settings.mode="s";
	$("#txtUserName", theForm).val(params.userName); 
	$("#txtFullName", theForm).val(params.fullName); 
	$("#txtOrdType", theForm).val(params.ordType); 
   
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
//设置当前的查询条件值，selector用这些值调用用户控件，JSON对象的名称就是Request[" "]中的名字
//分页的页码不用返回，selector记住了当前页数
function User4ApproveDef_on_query(params){
	params.userName = $("#txtUserName", $(this.mfs_formId)).val(); 
	params.fullName = $("#txtFullName", $(this.mfs_formId)).val(); 
	params.ordType = $("#txtOrdType", $(this.mfs_formId)).val(); 
}
//确认按钮点击事件，返回选择的结果
function User4ApproveDef_on_select(params){
    return params.selected;
}
//关闭按钮事件
function User4ApproveDef_on_close(){
}
</script>

