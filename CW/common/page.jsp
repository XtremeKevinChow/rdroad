  <%@ page contentType="text/html;charset=GBK"%>
  <%!
      String turnPagePattern(int totalNum,int totalPage,int curPage)
  {
      String ret = "";
      ret += "<table width=\"98%\" border=\"0\" cellspacing=\"1\" cellpadding=\"2\" class=\"bg2\" align=center>";
      ret += "<tr>";
      ret += "<td width=\"64%\">��"+totalNum+"����¼ ��"+totalPage+"ҳ��ʾ&nbsp;��ǰ��"+curPage+"ҳ</td>";
      ret += "<td width=\"36%\" align=\"right\">";
      ret += "<a href=\"javascript:goPage(1);\">��ҳ</a>&nbsp;";
      //if(curPage>1)
      //{
	  ret += "<a href=\"javascript:goPage("+(curPage-1)+");\">��һҳ</a>&nbsp;";
      //}
	  ret += "<input type=\"hidden\" name=\"pagecount\" value=\""+totalPage+"\">";
	  ret += "<input type=\"hidden\" name=\"pageNo\" value=\"1\">";
      //if(curPage<totalPage)
      //{
	  ret += "<a href=\"javascript:goPage("+(curPage+1)+");\">��һҳ</a>&nbsp;";
      //}
      ret += "<a href=\"javascript:goPage("+totalPage+");\">ĩҳ</a>";
      ret += "</td>";
      ret += "</tr>";
      ret += "</table>";
      return ret;
  }


String turnPageScript(String formName)
{
	String ret = "";
	ret +=	"<script language=\"javascript\" type=\"text/javascript\">";
	ret += " function goPage(i)";
	ret += "{";
	ret += "	var frm = document.forms[0];\n";
	ret += "	frm.pageNo.value = i;";
	ret += "	if(isNaN(frm.pageNo.value))";
	ret += "	{";
	ret += "		alert(\"pageNo Value Must Be Numbers !\");";
	ret += "		return false;";
	ret += "	}";
	ret += "	else if (parseInt(frm.pageNo.value)<1)";
	ret += "	{";
	ret += "		frm.pageNo.value = 1;";
	ret += "	}";
	ret += "	else if (parseInt(frm.pageNo.value)>parseInt(frm.pagecount.value))";
	ret += "	{";
	ret += "		frm.pageNo.value = frm.pagecount.value;";
	ret += "	}";
	ret += "	frm.submit();";
	ret += "}";
	ret += "</script>";
    return ret;
}
%>
