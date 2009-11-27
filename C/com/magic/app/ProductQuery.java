package com.magic.app;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
import com.magic.utils.*;
import java.util.*;

/**
 * 产生产品高级查询界面
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */

public class ProductQuery extends BaseServlet 
{

    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
         request.setCharacterEncoding("GBK");
        int doc_type =0;
        DocType doc;
         if(!assertSession(request))
        {
           response.sendRedirect("/relogin.html");
           return;
        }
       HttpSession session=request.getSession();
        super.service(request, response);
        
           SessionInfo sessionInfo = new SessionInfo(
            ((Integer)session.getAttribute("login_company_id")).intValue(), 
            ((Integer)session.getAttribute("login_operator_id")).intValue(), 
            (java.util.HashMap)session.getAttribute("powermap"),
            request.getParameterMap());
            
            
    
        HTMLView hv=new HTMLView();
        hv.setSessionInfo(sessionInfo);
        hv.setWidth(1);
        hv.addContent(content(sessionInfo));
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter(); 
       
        out.println(hv.getHTML());
        out.close();    
    }
    
    public String content(SessionInfo sessionInfo)
    {
       String s=getSubject();
        s=s+addQueryView(sessionInfo);
          return s;
    }
    private String getSubject()
  {
    String s="<br>\n";
    s=s+"<table width=\"750\" border=0 cellspacing=1 cellpadding=5 >\n";
    s=s+"<tr>\n";
    s=s+"<td> <span class=\"OraHeader\">查询产品数据</span>\n";
  	s=s+ "<table width=\"100%\" border=0 cellspacing=0 cellpadding=0 background=\"../crmjsp/images/headerlinepixel_onwhite.gif\" HEIGHT=\"1\" WIDTH=\"1\">\n";
	s=s+ "<tr background=\"../crmjsp/images/headerlinepixel_onwhite.gif\" HEIGHT=\"1\" WIDTH=\"1\">\n";
	s=s+"<td height=\"1\" width=100% background=\"../crmjsp/images/headerlinepixel_onwhite.gif\" HEIGHT=\"1\" WIDTH=\"1\"><img src=\"../crmjsp/images/headerlinepixel_onwhite.gif\" HEIGHT=\"1\" WIDTH=\"1\" ></td>\n";
    s=s+"</tr>\n";
	s=s+"</table>\n";
	s=s+"</td>\n";
    s=s+"</tr>\n";
    s=s+"</table>\n";
    return s;
  }
  
  public String addQueryView(SessionInfo si)
  {
        SessionInfo sessionInfo=si;
        String  s="<table width=\"750\" border=0 cellspacing=1 cellpadding=5>\n"; 
        s=s+"<form name=\"fm_add\" method=\"post\" action=\"ctrquery\">\n";
        s=s+"<input type=\"hidden\" name=\"doc_type\" value=\"1560\" >\n";
        
        DocType doc=new DocType(1500);
        Field[] fds=doc.getFields();
   
        for(int i=0;i<fds.length;i++)
        {
            if(fds[i].isQuery())
            {
                s=s+"<tr>\n";
                if(fds[i].isRequired())
                    s=s+"<td width=\"20%\" align=\"right\" ><font color=red>*</font>&nbsp;"+fds[i].getCaption()+"</td>\n";
                else
                     s=s+"<td width=\"20%\" align=\"right\" >"+fds[i].getCaption()+"</td>\n";
                s=s+"<td align=\"left\" width=\"80%\" nowarp>\n";
                if(fds[i].getInputType().equals("TEXT"))
                {
                    if(fds[i].getDataType().equals("INTEGER")||fds[i].getDataType().equals("FLOAT"))
                       s=s+"<input name=\""+fds[i].getName()+"_from\" size =8 value=\"\"> 至<input name=\""+fds[i].getName()+"_to\" value=\"\" size =8>";
                    else
                      s=s+"<input name=\""+fds[i].getName()+"\" value=\"\">\n";
                }
                if(fds[i].getInputType().equals("DATE"))
                {
                    s=s+"<input id=\""+fds[i].getName()+"_from\" name=\""+fds[i].getName()+"_from\" value=\"\"> <a href=\"javascript:calendar(fm_add."+fds[i].getName()+"_from)\"><img src=\"/images/icon_date.gif\" border=0 align=\"top\"><a>";
                    s=s+"至<input id=\""+fds[i].getName()+"_to\" name=\""+fds[i].getName()+"_to\" value=\"\"> <a href=\"javascript:calendar(fm_add."+fds[i].getName()+"_to)\"><img src=\"/images/icon_date.gif\" border=0 align=\"top\"><a>(格式:YYYY-MM-DD)\n";
                }
                if(fds[i].getInputType().equals("LOOKUP"))
                {
                    s=s+"<input type=\"hidden\" id=\""+fds[i].getName()+"\" name=\""+fds[i].getName()+"\" value=\"\"> ";
                    s=s+"<input id=\""+fds[i].getName()+"_key\" name=\""+fds[i].getName()+"_key\" value=\"\" readonly onclick=\" select_item('"+fds[i].getName()+"',fm_add."+fds[i].getName()+",fm_add."+fds[i].getName()+"_key);\"> <a href=\"javascript:select_item('"+fds[i].getName()+"',fm_add."+fds[i].getName()+",fm_add."+fds[i].getName()+"_key);\"><img src=\"/images/icon_lookup.gif\" border=0 align=\"top\"><a>\n";
                    s=s+"<lable id=\""+fds[i].getName()+"_display\" name=\""+fds[i].getName()+"_display\" ></label>\n";
                }
                if(fds[i].getInputType().equals("LIST"))
                {
                    Map map=LookupMap.getListValues(fds[i].getLookup(),sessionInfo.getCompanyID(),null);
                    Object[] values=map.keySet().toArray();
                    s=s+"<select id=\""+fds[i].getName()+"\" name=\""+fds[i].getName()+"\" > \n";
                      s=s+"<option value=\"\">请选择...</option>\n";
                    for(int in=0;in<values.length;in++)
                        s=s+"<option value=\""+(String)values[in]+"\">"+(String)map.get(values[in])+"</option>\n";
                    s=s+"</select>\n";
                }
                if(fds[i].getInputType().equals("TEXTAREA"))
                {
                    s=s+"<textarea cols=30 rows=2 name=\""+fds[i].getName()+"\" value=\"\"></textarea>\n";
                }
                if(fds[i].getInputType().equals("RADIO"))
                {
                    Map map=LookupMap.getListValues(fds[i].getLookup(),sessionInfo.getCompanyID(),null);
                    Object[] values=map.keySet().toArray();
                    for(int in=0;in<values.length;in++)
                        s=s+"<input type='radio' name=\""+fds[i].getName()+"\" value=\""+(String)values[in]+"\">"+(String)map.get(values[in])+"&nbsp;\n";
                    s=s+"</select>\n";
                }
                s=s+"</td>";
                s=s+"</tr>\n";  
            }
        }
         s=s+"<tr>\n";
        s=s+"<td colspan=2> <span class=\"OraHeaderSubSub\">设置会员状态搜索条件</span>\n";
        s=s+ "<table width=\"100%\" border=0 cellspacing=0 cellpadding=0 background=\"../crmjsp/images/headerlinepixel_onwhite.gif\" HEIGHT=\"1\" WIDTH=\"1\">\n";
        s=s+ "<tr background=\"../crmjsp/images/headerlinepixel_onwhite.gif\" HEIGHT=\"1\" WIDTH=\"1\">\n";
        s=s+"<td height=\"1\" width=100% background=\"../crmjsp/images/headerlinepixel_onwhite.gif\" HEIGHT=\"1\" WIDTH=\"1\"><img src=\"../crmjsp/images/headerlinepixel_onwhite.gif\" HEIGHT=\"1\" WIDTH=\"1\" ></td>\n";
        s=s+"</tr>\n";
        s=s+"</table>\n";
        s=s+"</td>\n";
        s=s+"</tr>\n";
        DocType doc_2=new DocType(1600);
        fds=doc_2.getFields();
   
        for(int i=0;i<fds.length;i++)
        {
            if(fds[i].isQuery())
            {
                s=s+"<tr>\n";
                if(fds[i].isRequired())
                    s=s+"<td width=\"20%\" align=\"right\" ><font color=red>*</font>&nbsp;"+fds[i].getCaption()+"</td>\n";
                else
                     s=s+"<td width=\"20%\" align=\"right\" >"+fds[i].getCaption()+"</td>\n";
                s=s+"<td align=\"left\" width=\"80%\" nowarp>\n";
                if(fds[i].getInputType().equals("TEXT"))
                {
                    if(fds[i].getDataType().equals("INTEGER")||fds[i].getDataType().equals("FLOAT"))
                       s=s+"<input name=\""+fds[i].getName()+"_from\" size =8 value=\"\"> 至<input name=\""+fds[i].getName()+"_to\" value=\"\" size =8>";
                    else
                      s=s+"<input name=\""+fds[i].getName()+"\" value=\"\">\n";
                }
                if(fds[i].getInputType().equals("DATE"))
                {
                    s=s+"<input id=\""+fds[i].getName()+"_from\" name=\""+fds[i].getName()+"_from\" value=\"\"> <a href=\"javascript:calendar(fm_add."+fds[i].getName()+"_from)\"><img src=\"/images/icon_date.gif\" border=0 align=\"top\"><a>";
                    s=s+"至<input id=\""+fds[i].getName()+"_to\" name=\""+fds[i].getName()+"_to\" value=\"\"> <a href=\"javascript:calendar(fm_add."+fds[i].getName()+"_to)\"><img src=\"/images/icon_date.gif\" border=0 align=\"top\"><a>(格式:YYYY-MM-DD)\n";
                }
                if(fds[i].getInputType().equals("LOOKUP"))
                {
                    s=s+"<input type=\"hidden\" id=\""+fds[i].getName()+"\" name=\""+fds[i].getName()+"\" value=\"\"> ";
                    s=s+"<input id=\""+fds[i].getName()+"_key\" name=\""+fds[i].getName()+"_key\" value=\"\" readonly onclick=\" select_item('"+fds[i].getName()+"',fm_add."+fds[i].getName()+",fm_add."+fds[i].getName()+"_key);\"> <a href=\"javascript:select_item('"+fds[i].getName()+"',fm_add."+fds[i].getName()+",fm_add."+fds[i].getName()+"_key);\"><img src=\"/images/icon_lookup.gif\" border=0 align=\"top\"><a>\n";
                    s=s+"<lable id=\""+fds[i].getName()+"_display\" name=\""+fds[i].getName()+"_display\" ></label>\n";
                }
                if(fds[i].getInputType().equals("LIST"))
                {
                    Map map=LookupMap.getListValues(fds[i].getLookup(),sessionInfo.getCompanyID(),null);
                    Object[] values=map.keySet().toArray();
                    s=s+"<select id=\""+fds[i].getName()+"\" name=\""+fds[i].getName()+"\" > \n";
                      s=s+"<option value=\"\">请选择...</option>\n";
                    for(int in=0;in<values.length;in++)
                        s=s+"<option value=\""+(String)values[in]+"\">"+(String)map.get(values[in])+"</option>\n";
                    s=s+"</select>\n";
                }
                if(fds[i].getInputType().equals("TEXTAREA"))
                {
                    s=s+"<textarea cols=30 rows=2 name=\""+fds[i].getName()+"\" value=\"\"></textarea>\n";
                }
                if(fds[i].getInputType().equals("RADIO"))
                {
                    Map map=LookupMap.getListValues(fds[i].getLookup(),sessionInfo.getCompanyID(),null);
                    Object[] values=map.keySet().toArray();
                    for(int in=0;in<values.length;in++)
                        s=s+"<input type='radio' name=\""+fds[i].getName()+"\" value=\""+(String)values[in]+"\">"+(String)map.get(values[in])+"&nbsp;\n";
                    s=s+"</select>\n";
                }
                
                s=s+"</td>";
                s=s+"</tr>\n";  
            }
        }
        s=s+"<tr>\n";
        s=s+"<td colspan=2> <span class=\"OraHeaderSubSub\">设置排序条件</span>\n";
        s=s+ "<table width=\"100%\" border=0 cellspacing=0 cellpadding=0 background=\"../crmjsp/images/headerlinepixel_onwhite.gif\" HEIGHT=\"1\" WIDTH=\"1\">\n";
        s=s+ "<tr background=\"../crmjsp/images/headerlinepixel_onwhite.gif\" HEIGHT=\"1\" WIDTH=\"1\">\n";
        s=s+"<td height=\"1\" width=100% background=\"../crmjsp/images/headerlinepixel_onwhite.gif\" HEIGHT=\"1\" WIDTH=\"1\"><img src=\"../crmjsp/images/headerlinepixel_onwhite.gif\" HEIGHT=\"1\" WIDTH=\"1\" ></td>\n";
        s=s+"</tr>\n";
        s=s+"</table>\n";
        s=s+"</td>\n";
        s=s+"</tr>\n";
        for(int j=0;j<3;j++)
        {
            s=s+"<tr>\n";
            s=s+"<td width=\"20%\" align=\"right\" > 排序条件"+(j+1)+":</td>";
            s=s+"<td align=\"left\" >";
            s=s+"<select name=\"sort\"><option value=\"\" > 选择排序字段...</option>";
             for(int i=0;i<fds.length;i++)
            {
                if(fds[i].isQuery())
                {
                    s=s+"<option value=\""+fds[i].getName()+"\">"+fds[i].getCaption()+"</option>";
                }
            }
            s=s+"</select></td></tr>";
        }
        s=s+"<tr><td colspan=2>\n";
     	s=s+"<table width=\"100%\" border=0 cellspacing=0 cellpadding=0 background=\"../crmjsp/images/headerlinepixel_onwhite.gif\" HEIGHT=1 WIDTH=1>\n";
        s=s+"<tr background=\"../crmjsp/images/headerlinepixel_onwhite.gif\" HEIGHT=1 WIDTH=1>\n";
        s=s+"<td  height=1 width=\"100%\" background=\"../crmjsp/images/headerlinepixel_onwhite.gif\" HEIGHT=1 WIDTH=1><img src=\"../crmjsp/images/headerlinepixel_onwhite.gif\" HEIGHT=1 WIDTH=1></td>\n";
        s=s+"</tr></table></td></tr>\n";
        s=s+"<tr>\n";
        s=s+"<td align=\"right\" colspan=2>\n";
        s=s+"<input type=\"button\" class=\"button2\" value=\"提交\" onClick=\"submit();\">&nbsp;\n";
        s=s+"<input type=\"button\" class=\"button2\" value=\"取消\" onClick=\"history.back();\">\n";
		s=s+"</tr>\n";
        s=s+"</form>\n";
        s=s+"</table>\n";
        return s;
  }
}