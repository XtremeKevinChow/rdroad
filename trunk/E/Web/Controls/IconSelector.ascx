<%@ Control Language="C#" AutoEventWireup="false" %>
<%@ Import Namespace="System.Data" %>
<%@ Import Namespace="System.Collections.Generic" %>
<%@ Import Namespace="Magic.Framework.Utils" %>
<%@ Import Namespace="Magic.Framework.ORM" %>
<%@ Import Namespace="Magic.Framework.ORM.Query" %>
<%@ Import Namespace="Magic.Framework.Configuration" %>
<%@ Import Namespace="Magic.Framework.File" %>
<%@ Import Namespace="Magic.Sys" %>

<div>选择图片</div>
<div >
<table id="dataArea" cellpadding="0" cellspacing="0" style="width: 100%; height: 100%;  overflow:scroll;" >    
    <%
        int count = -1;

        string msg = "";
        IList<String> fileNames = Magic.Framework.File.FileStoreManager.ListFiles("Module", "", msg);

        if (fileNames.Count == 0 && !string.IsNullOrEmpty(msg))
        {
            %>
            <tr>
                <td>
                    <span style="color:Red"><% =msg %></span>
                </td>
            </tr>
            <%
        }
            foreach (string  file in fileNames)
            {
                
    %>
    <tr>
        <td>
            <input type="checkbox" key='<%=FileStoreManager.FormatResourceKey("Module", file) %>' name='<%= file%>' class="chk" />
        </td>
        <td>
            <img height="20" alt="" src='<%= FileStoreManager.GetAbsoluteUri("Module",file) %>' />
        </td>
        <td>
            <%= file%>
        </td>       
    </tr>
        <%
            }        
    %>
</table>
</div>
<script type="text/javascript">
function IconSelector_on_load(params){
    var thisRef=this;
    var theForm = $(this.mfs_formId);
    //if(thisRef.settings.mode!="s" && thisRef.settings.mode!="m")
     thisRef.settings.mode="s";    
    
    if(params.selected && params.selected.constructor==Array)
        $("#dataArea", theForm).find(".chk").each(function(i, e){
            var t = $.grep(params.selected, function(n, i){ return n.id==$(e).attr("key"); });
            if(t.length>0) { $(e).attr("checked", true); }
            $(e).bind("click", function(){
                var isChecked = $(this).attr("checked");
                if(thisRef.settings.mode=="s") { //单选
                    if(isChecked) {
                        params.selected = [ { id: $(this).attr("key"), name: $(this).attr("name") } ];
                        $("#dataArea", theForm).find(".chk[@key!='"+$(this).attr("key")+"']").removeAttr("checked");
                    }
                    else params.selected = [] ;
                }else { //多选
                    if(isChecked) //添加
                        params.selected.push({ id: $(this).attr("key"), name: $(this).attr("name") });
                    else //移除
                        params.selected = $.grep(params.selected, function(n, i){
                            return n.id != $(e).attr("key");
                        });
                }
            });//$(e).bind("click", function()
        });//$("#dataArea").find(".chk").each(function(i, e)
        
        return <%= count.ToString() %>;
}
function IconSelector_on_query(params){
    params.name = $("#selectorHosName", $(this.mfs_formId)).val();
    params.key = $("#selectorAreaName", $(this.mfs_formId)).val();
}
function IconSelector_on_select(params){
    return params.selected;
}
function IconSelector_on_close(){
}
</script>


