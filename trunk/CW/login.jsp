<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<HTML>
<HEAD>
<TITLE>上海佰明会员关系管理系统</TITLE>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gb2312">
<style>
.whiteBorder {
	border: 1px solid #8CBAE8;
}

.rowOdd {
        background-color: #FFFFFF;
}

.formStyle {
	border: 1px solid #616F45;
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 10px;
	font-weight: normal;
	color: #000000;
	width: 130px;
       	padding-left: 2px;
	background: url(..images/formshdw.gif) no-repeat -4px -4px #fff;
}

.fontBlackbold {
	font-weight: bold;
}

td {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 10px;
	font-weight: normal;
	color: #000000;
}

td a{
	color: #000000;
       text-decoration: underline;
}

body {
	padding:0px;
	margin:0px;
}

.fontWhite {
	color: #FFFFFF;
}

.fontWhitebold {
	font-weight: bold;
	color: #FFFFFF;
       text-decoration: none;

}

.formStylebuttonAct {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 10px;
	width: auto;
	padding: 0px 2px 0px 2px;
	background-image: url(..images/buttonbg.gif);
	background-color: #C6C5D7;
	cursor: pointer;
	font-weight: bold;
	height: auto;
	background-repeat: repeat-x;
}

</style>
<script language="javascript" >
function submitform(){
	var form = document.forms[0];
	if (form.userName.value == ""){
		alert("用户名不能为空！");
		return false;
	}
	
	
}
function initForm(){
	var form = document.forms[0];
	form.userName.focus();
}
</script>
</HEAD>
<BODY BGCOLOR=#FFFFFF LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0 onload="javascript:initForm()">

<html:form action="/logonnow.do" onsubmit="return submitform();">
<table width="100%" height="90%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td align="center" valign="middle"> <br><br>
      <table border="0" align="center" cellpadding="0" cellspacing="0">
        <tr align="left" valign="top"> 
          <td colspan="2" background="images/log_bluebg.gif"><img src="images/log_ltcorner.gif" width="9" height="14"></td>
          <td align="right" background="images/log_bluebg.gif"><img src="images/log_rtcorner.gif" width="9" height="14"></td>
        </tr>
        <tr> 
          <td colspan="3" background="images/log_bluebg.gif"><img src="images/spacer.gif" width="910" height="1"></td>
        </tr>
        <tr> 
          <td height="409" colspan="3" align="right" background="images/log_bg.jpg"> 
            <table width="3" border="0" cellpadding="0" cellspacing="0">
              <tr> 
                <td><img src="images/spacer.gif" width="500" height="1"></td>
                <td><img src="images/spacer.gif" width="20" height="1"></td>
                <td><img src="images/spacer.gif" width="1" height="1"></td>
                <td><img src="images/spacer.gif" width="40" height="1"></td> <!-- ## Modified ## -->
              </tr>
              <tr> 
                <td> 
                  
                  <!--img src="images/applogo.gif" width="252" height="61" hspace="20"--> 
                  <span style='font-size:26.0pt;color:red;font-family:隶书'>上海佰明会员关系管理系统</span>
                  
                </td>
                <td><img src="images/log_separator.gif" width="9" height="216"></td>
                <td> <table width="200" border="0" cellpadding="4" cellspacing="0">
                    <tr> 
                      <td colspan="2" class="fontBlack"><br> <br> <br> <div id="message"> 
                          
                        </div></td>
                    </tr>
                    <tr> 
                      <td nowrap class="fontBlackBold">用户名：</td>
                      <td nowrap> <span class="fontBlack"> 
                        <html:text property="userName"  size="15"/>
                        </span></td>
                    </tr>
                    <tr> 
                      <td nowrap class="fontBlackBold">密码：</td>
                      <td nowrap> <span class="fontBlack"> 
                        <html:password property="password"  size="15" />
                        <html:hidden property="tag"  value="1" />
                        </span></td>
                    </tr>
                   
                    <!-- changes for remember me start -->
                    <tr> 
                      <td height="26" align="left">
				      <input name="hidden" type="hidden" id="choosedomain" value='选择一个域'>
				      <input name="sso_status" type="hidden" value='false'>
				      <input name="hidden" type="hidden" id="moreOptionsMsg" value='选项'>		      
				      <input name="hidden" type="hidden" id="jserror" value='输入用户名和密码'></td>
                      <td height="26" nowrap class="fontBlackBold"> <table cellpadding="0" cellspacing="0" border="0">
                          <tr> 
                            <td align="left">
                            </td>
                            <td nowrap colspan=2>&nbsp;</td>
                            <td nowrap><input name="loginButton" type="submit" class="formStylebuttonact" value='登录' title='登录'</td>
							                        
							<td nowrap >
								<img src="images/spacer.gif" width="1" height="1">
							</td>
                            <td nowrap> 
								<img src="images/spacer.gif" width="70" height="1">                            
							</td>
							
                          </tr>
                        </table></td>
                    </tr>
                    <!-- changes for remember me end -->
                  </table>
                  </td>
                <td>&nbsp;</td>
              </tr>

              <tr> 
                <td><img src="images/spacer.gif" width="100" height="1"></td>
                <td><img src="images/spacer.gif" width="20" height="1"></td>
                <td><img src="images/spacer.gif" width="1" height="1"></td>
                <td><img src="images/spacer.gif" width="40" height="1"></td> <!-- ## Modified -->
              </tr>


	    	 <tr> 
                <td><img src="images/spacer.gif" width="100" height="1"></td>
                <td colspan="3"> 



                
                </td>
              </tr>

            </table>
           
           </td>
        </tr>
        
       <tr> 
          <td colspan="3" align="left" background="images/log_bluebg.gif" valign="top"> 
            <table border="0" cellpadding="5" cellspacing="0" width="100%">
              <tbody><tr> 
                <td class="fontBlack" style="color: rgb(255, 255, 255);" align="left" valign="top"> 
		
                  <table border="0" cellpadding="2" cellspacing="0">
                    <tbody><tr> 
                      <td width="30%"><a href="http://www.thoughtsoft.com.cn"><img src="images/logo_ts.gif" border="0" height="24" hspace="5" vspace="0" width="92"></a></td>
                      <td class="fontWhite" width="70%"> 
                        版权所有&copy; 2008 Yi Lun<br>
                         </td>
                    </tr>
                  </tbody></table>
	
		</td>
                <td class="fontBlack" style="color: rgb(255, 255, 255);" align="right" valign="top"> 
                  <table border="0" cellpadding="2" cellspacing="0">
                    <tbody><tr> 
                      <td class="fontwhiteBold" align="right" nowrap="nowrap">MagicSoft Member Relationship System
                        <span class="fontwhite">&nbsp;&nbsp;|&nbsp;&nbsp;2.0.0
                        </span></td>
                    </tr>
                    <tr> 
                      <td align="right"> 

			</td>
                    </tr>
                  </tbody></table></td>
              </tr>
            </tbody></table></td>
        </tr>
        <tr align="left" valign="bottom"> 
          <td background="images/log_bluebg.gif"><img src="images/log_lbcorner.gif" height="10" width="9"></td>
          <td background="images/log_bluebg.gif">&nbsp;</td>
          <td align="right" background="images/log_bluebg.gif"><img src="images/log_rbcorner.gif" height="10" width="9"></td>
        </tr>
        
        
        </tbody></table>
    </td>
  </tr>
</table>
</html:form>


</BODY>
</HTML>