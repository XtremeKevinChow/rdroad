<%@ Control Language="C#" AutoEventWireup="false" %>

<div >
<div>请填写新增的DashPage的名称，选择页上项目的摆放布局</div>
<table id="dataArea" cellpadding="1" cellspacing="1" style="width: 100%; height: 100%; " >    
   <tr>
    <td style="width:80px" align="right">名称</td>
    <td><input type="text" id="DashPage_Name" /></td>
    
    </tr>
    <tr>
    <td align="right">布局样式</td>
    <td>
        <ul  style="list-style:none; margin:0; padding:0">
            <li><input type="radio" name="DashPageLayout" value="1" />1列</li>
            <li><input type="radio" name="DashPageLayout" value="2" />2列</li>
            <li><input type="radio" name="DashPageLayout" value="3" />3列</li>
        </ul>        
    </td>    
   </tr>
   <tr>
    <td align="right">描述</td>
    <td><textarea id="DashPage_Desc" style="width:100%;height:50px"></textarea></td>
   </tr>
</table>
</div>
<script type="text/javascript">
function AddDashPage_on_load(params){
    var thisRef=this;
    var theForm = $(this.mfs_formId);
    //if(thisRef.settings.mode!="s" && thisRef.settings.mode!="m")
     thisRef.settings.mode="s";
    
   
        return 0;
}
function AddDashPage_on_query(params){
 
}
function AddDashPage_on_select(params){
    params.selected =  [ { layout: $("input[@name='DashPageLayout'][checked]").val(), name: $("#DashPage_Name").val(), desc:$("#DashPage_Desc").val() } ];
    return params.selected;
}
function AddDashPage_on_close(){
}
</script>


