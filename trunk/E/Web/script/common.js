 //选择全部
 function SelectAll(selectAllBoxId, gridTBodyId)
{
   
    var chkAll = document.getElementById(selectAllBoxId);
     
    var dataBody = document.getElementById(gridTBodyId);
    
    var chkBoxs = dataBody.getElementsByTagName("input");
    if(chkBoxs != null && chkBoxs.length >0)
    {
        for(var i=0;i<chkBoxs.length;i++)
        {
            if( chkBoxs[i].type == "checkbox")
            {
                chkBoxs[i].checked = chkAll.checked;                
            }
        }
    }
}

//String
String.prototype.Trim=function(){return this.replace(/(^[ \t\n\r]*)|([ \t\n\r]*$)/g,'');};
String.prototype.LTrim=function(){return this.replace(/^[ \t\n\r]*/g,'');};
String.prototype.RTrim=function(){return this.replace(/[ \t\n\r]*$/g,'');};
Array.prototype.IndexOf=function(A){for (var i=0;i<this.length;i++){if (this[i]==A) return i;};return-1;};
String.prototype.Contains=function(A){return (this.indexOf(A)>-1);};
String.prototype.StartsWith=function(A){return (this.substr(0,A.length)==A);};
String.prototype.EndsWith=function(A,B){var C=this.length;var D=A.length;if (D>C) return false;if (B){var E=new RegExp(A+'$','i');return E.test(this);}else return (D==0||this.substr(C-D,D)==A);};
String.prototype.Remove=function(A,B){var s='';if (A>0) s=this.substring(0,A);if (A+B<this.length) s+=this.substring(A+B,this.length);return s;};
String.prototype.Equals=function(){var A=arguments;if (A.length==1&&A[0].pop) A=A[0];for (var i=0;i<A.length;i++){if (this==A[i]) return true;};return false;};
String.prototype.IEquals=function(){var A=this.toUpperCase();var B=arguments;if (B.length==1&&B[0].pop) B=B[0];for (var i=0;i<B.length;i++){if (A==B[i].toUpperCase()) return true;};return false;};
String.prototype.ReplaceAll=function(A,B){var C=this;for (var i=0;i<A.length;i++){C=C.replace(A[i],B[i]);};return C;};Array.prototype.AddItem=function(A){var i=this.length;this[i]=A;return i;};

//Browser 
var s=navigator.userAgent.toLowerCase();	
var BrowserInfo={IsIE:s.Contains('msie'),	IsIE7:s.Contains('msie 7'),IsGecko:s.Contains('gecko/'),	IsSafari:s.Contains('safari'), IsOpera:s.Contains('opera'),	IsMac:s.Contains('macintosh')};