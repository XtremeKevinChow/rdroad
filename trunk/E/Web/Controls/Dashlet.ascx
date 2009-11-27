<%@ Control Language="C#" AutoEventWireup="false" %>
<%@ Import Namespace="System.Collections.Generic" %>
<%@ Import Namespace="Magic.Sys" %>
<%@ Import Namespace="Magic.Framework.Utils" %>
<%@ Import Namespace="Magic.Framework.ORM" %>
<%@ Import Namespace="Magic.Framework.ORM.Query" %>
<table id="queryArea" cellpadding="0" cellspacing="0" style="width: 100%;">
	<tr>
                <td style="width: 60px;">分类</td>
                <td>
                <select ID="ddlCategory"  style="width:70px;">
                <option value="">所有</option>
                <% DropDownList list = new DropDownList();
                   EnumUtil.BindDictionaryItems2ListControl(list, "Dashlet_Category");
                   foreach (ListItem itm in list.Items)
                   {
                    %>
                    <option value='<%= itm.Value %>'><%= itm.Text %></option>
                 <%} %>
                </select></td>
                <td style="width: 60px;">标题</td>
                <td><input  id="txtTitle"  type="text"  style="width:70px;"/></td>
			<td class="queryButton">
                <input type="image" style="border-width: 0px;" src="../Images/search.gif" id="sel_cmdQuery" />
            </td>		 
    <tr>        
</table>
<div style="height: 4px; overflow: hidden; width: 50%;">
</div>
<table id="dataArea" cellpadding="0" cellspacing="0" style="width: 100%; height: 100%">
    <tr>
        <th style="width: 20px;">
        </th>
        <th>
			分类
        </th>
        <th>
			标题
        </th>
        <th>
			图标
        </th>
        <th>
			描述
        </th>
      
    </tr>
    <%
        int count = -1;
        string category = WebUtil.Param("category");
        string title = WebUtil.Param("title");
        bool exclusive = true;
        bool.TryParse(WebUtil.Param("exclusive"), out exclusive);
        
        using (ISession session = new Session())
        {
			
            int index = WebUtil.ParamInt("pi", -1);
            if (index <= 0) index = 1;
            int queryType = WebUtil.ParamInt("qt", -1);
            if (queryType < 1 || queryType > 5) queryType = 1;
            string oql = "select d.* from Dashlet d";
            if(exclusive)
                oql += string.Format(" where d.DashletId not in(select ud.DashletId from UserDashlet ud where ud.UserId ={0})", Magic.Security.SecuritySession.CurrentUser.UserId);
            oql += " and d.Category like @Category and d.Title like @Title";
            
            ObjectQuery  query = session.CreateObjectQuery(oql).Attach(typeof(Dashlet));
            if (exclusive) query.Attach(typeof(UserDashlet));

            query.SetValue("@Category", "%" + category + "%", "d.Category");

            query.SetValue("@Title", "%" + title + "%", "d.Title"); 
			
           
            query.SetPage(index, 16);
            IList<Dashlet> dashlets = query.List<Dashlet>();
            if (queryType == 1)
            {
                count = (query.Count()+15) / 16;
            }

            foreach (Dashlet item in dashlets)
            {
    %>
    <tr>
        <td>
            <input type="checkbox"   dashletId='<%=item.DashletId %>'      category='<%=item.Category %>'    title='<%=item.Title %>'    icon='<%=item.Icon %>'    description='<%=item.Description %>'   class="chk" />
        </td>
		<td>
			<%= item.Category %>
		</td>
		<td>
			<%= item.Title %>
		</td>
		<td>
			<%= item.Icon %>
		</td>
		<td>
			<%= item.Description %>
		</td>
        
    </tr>
    <%
        }
        }
    %>
</table>

<script type="text/javascript">
//params中包括Dashlet_on_query返回的各个属性，也包括调用query.fnPopup(settings)时settings.data的各个属性
function Dashlet_on_load(params){
    var thisRef=this;
    if(thisRef.settings.mode!="s" && thisRef.settings.mode!="m") thisRef.settings.mode="s";
	$("#ddlCategory").val(params.category); 
	$("#txtTitle").val(params.title); 
   
    if(params.selected && params.selected.constructor==Array && params.selected.length>0)
        $("#dataArea").find(".chk").each(function(i, e){
            //将已选择列表的checkbox勾选上
            var t = $.grep(params.selected, function(n, i){ return n.dashletId==$(e).attr("dashletId"); });
            if(t.length>0) {
                $(e).attr("checked", true);
                //已选择列表在初始调用时只传入了code而没有name，这里趁机会把name填上
                //还是会有漏掉的，例如已选择的对象这一次并没有查询出来
                t[0].category = $(e).attr("category"); 
                t[0].title = $(e).attr("title"); 
                t[0].icon = $(e).attr("icon"); 
                t[0].description = $(e).attr("description"); 
            }
            //绑定checkbox框的点击事件
            $(e).bind("click", function(){
                var isChecked = $(this).attr("checked");
                if(thisRef.settings.mode=="s") { //单选
                    if(isChecked) {					
                        params.selected = [ { dashletId:$(this).attr("dashletId") ,category:$(this).attr("category") ,title:$(this).attr("title") ,icon:$(this).attr("icon") ,description:$(this).attr("description") } ];
                        $("#dataArea").find(".chk" + "[dashletId!='" + $(this).attr("dashletId") + "']").removeAttr("checked");
                    }
                    else params.selected = [] ;
                }else { //多选
                    if(isChecked) //添加
                        params.selected.push({ dashletId:$(this).attr("dashletId") ,category:$(this).attr("category") ,title:$(this).attr("title") ,icon:$(this).attr("icon") ,description:$(this).attr("description") });
                    else //移除
                        params.selected = $.grep(params.selected, function(n, i){
                            return n.dashletId==$(e).attr("dashletId");
                        });
                }
            });//$(e).bind("click", function()
        });//$("#dataArea").find(".chk").each(function(i, e)
        
        return <%= count.ToString() %>;
}
//设置当前的查询条件值，selector用这些值调用用户控件，JSON对象的名称就是Request[" "]中的名字
//分页的页码不用返回，selector记住了当前页数
function Dashlet_on_query(params){
	params.category = $("#ddlCategory").val(); 
	params.title = $("#txtTitle").val(); 
}
//确认按钮点击事件，返回选择的结果
function Dashlet_on_select(params){
    return params.selected;
}
//关闭按钮事件
function Dashlet_on_close(){
}
</script>

