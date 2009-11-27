<%@ Control Language="C#" AutoEventWireup="false" %>
<div class="sysFormHead">
    <div class="sysFormHeadLeft"></div>
    <div class="sysFormHeadMid">
        <h6 id="magicTitle" class="sysFormDragHandle">选择</h6>
        <span class="sysFormClose" id="sel_cmdSysClose"><a href="#a">关闭</a></span>
    </div>
    <div class="sysFormHeadRight"></div>
</div>
<div class="sysFormContent">
    <div id="userAreaContainer"><div class="userArea">
    </div></div>
</div>
<div class="sysFormPager" style="">
    <span style="float:right;">
        <a id="sel_cmdFirst" disabled="true">首页</a>  
        <a id="sel_cmdPrev" disabled="true">上一页</a>  
        <a id="sel_cmdNext" disabled="true">下一页</a>  
        <a id="sel_cmdLast" disabled="true">尾页</a>&nbsp;&nbsp;
        <span id="selectorPager"></span>&nbsp;&nbsp;
    </span>
</div>
<div class="sysFormBottom">
    <div class="sysFormBottomLeft"></div>
    <div class="sysFormBottomMid">
        <div class="commandArea">
            <a href="#a" id="sel_cmdConfirm"><img src="../images/b_confirm.gif" /><span>确认</span> </a>
            <a href="#a" id="sel_cmdClose"><img src="../images/b_delete.gif" /><span>关闭</span> </a>
            <div style="height: 14px; overflow: hidden;"></div>
        </div>
    </div>
    <div class="sysFormBottomRight"></div>
</div>