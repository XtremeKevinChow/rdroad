<%@ Control Language="C#" AutoEventWireup="false" %>
<div class="sysFormHead">
    <div class="sysFormHeadLeft"></div>
    <div class="sysFormHeadMid">
        <h6 id="magicTitle" class="sysFormDragHandle">备注</h6>
        <span class="sysFormClose" id="sel_cmdSysClose"><a href="#a">关闭</a></span>
    </div>
    <div class="sysFormHeadRight"></div>
</div>
<div class="sysFormContent">
    <div id="userAreaContainer"><div class="userArea">
    <textarea id="note_txt_area" class="input"></textarea>
    </div></div>
</div>
<div class="sysFormBottom">
    <div class="sysFormBottomLeft"></div>
    <div class="sysFormBottomMid">
        <div class="commandArea">
            <a href="#a" id="sel_cmdConfirm"><img src="../images/b_confirm.gif" /><span>确认</span> </a>
            <a href="#a" id="sel_cmdClose"><img src="../images/b_delete.gif" /><span>取消</span> </a>
            <div style="height: 14px; overflow: hidden;"></div>
        </div>
    </div>
    <div class="sysFormBottomRight"></div>
</div>