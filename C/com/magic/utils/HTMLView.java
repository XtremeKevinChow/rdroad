package com.magic.utils;
import com.magic.app.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import oracle.sql.CLOB;
import com.magic.crm.util.DBManager;
/**
* 生成系统界面基类
* @author magic
* @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
*/
public class HTMLView 
{
 private static final String CONTENT_TYPE = "text/html; charset=GBK";
 
 public static final int DEFAULT_STYLE   = 100; 
 public static final int REPORT_STYLE   = 200; 
 private int style=DEFAULT_STYLE;
 
 private SessionInfo sessionInfo;
 
 private String width="100%";
 private String subject;
 private String search_bar=null;
 private boolean menu_visible=true;
 private String content="";
 private String buttons="";
 private int doc_type;

Connection conn=null;
 //private DBLink dblink=null;
 Statement stmt=null;
 ResultSet rs=null;
 
 public HTMLView()
 {
   //dblink=new DBLink();
 }
 public void setSessionInfo(SessionInfo si)
 {
     this.sessionInfo=new SessionInfo(si.getCompanyID(),si.getOperatorID(),si.getPowerMap(),si.getRequestMap());
 }
 public void setWidth(float width)
 {
   if(width<=1)
     this.width=100*width+"%";
   else
     this.width=width+"";;
 }
 
private String getHeader()
 {
   String s="";
   s="<html>\n";
   s=s+"<head>\n";
   s=s+"<title>佰明会员关系管理系统</title>\n";
   
   s=s+"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">\n";

   s=s+"<link rel=\"stylesheet\" href=\"../css/style.css\" type=\"text/css\">\n";
   s=s+"<script language=\"JavaScript\" src=\"../crmjsp/go_top.js\"></script>\n";
   s=s+"<script language=\"JavaScript\" src=\"../crmjsp/common.js\"></script>\n";
   s=s+"<script language=\"JavaScript\" src=\"../crmjsp/calendar.js\"></script>\n";   
   s=s+"<script language=\"JavaScript\">";
   s=s+"function querySubmit() {";
   	s=s+"document.fm_add.search.disabled = true;";
   	s=s+"document.fm_add.submit();";
   	s=s+"}";
   s=s+"</script>";   
   s=s+"</head>\n";
   s=s+"<body bgcolor=\"#FFFFFF\" text=\"#000000\" leftmargin=\"0\" topmargin=\"0\" >\n";
   return s;
 }
 
 private String getMenuBar()
 {
    String s="";
    s=s+"<a name=\"header_top\"><!----></a>\n";
   s=s+"<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >\n";
   s=s+"<tr> \n";
   s=s+"<td width=\"55\"><img src=\"/../crmjsp/images/logo.gif\"></td>\n";
   s=s+"<td>&nbsp;</td>\n";
   s=s+"<td align=\"right\">\n"; 
     s=s+"<table width=\"270\" border=\"0\" cellspacing=\"2\" cellpadding=\"0\">\n";
       s=s+"<tr>\n"; 
         s=s+"<td width=\"40\" align=\"center\"><a class=OraGlobalButtonText  href=\"/goto?t_code=1110\"><img src=\"/../crmjsp/images/gb_member_icon.gif\" border=\"0\" alt=\"新增会员\"></a></td>\n";
         s=s+"<td width=\"40\" align=\"center\"><a class=OraGlobalButtonText  href=\"/goto?t_code=3110\" ><img src=\"/../crmjsp/images/gb_order_icon.gif\" border=\"0\" alt=\"新增订单\"></a></td>\n";
         s=s+"<td width=\"40\" align=\"center\"><a class=OraGlobalButtonText  href=\"/goto?t_code=1190\"><img src=\"/../crmjsp/images/gb_main_icon.gif\" border=\"0\" alt=\"会员查询\"></a></td>";
         s=s+"<td width=\"40\" align=\"center\"><a class=OraGlobalButtonText  href=\"/goto?t_code=3120\"><img src=\"/../crmjsp/images/gb_member_icon_1.bmp\"  border=\"0\" alt=\"订单查询\"></a></td>\n";
         s=s+"<td width=\"40\" align=\"center\"><a class=OraGlobalButtonText  href=\"/goto?t_code=4110\"><img src=\"/../crmjsp/images/gb_product_icon.gif\"  border=\"0\" alt=\"新增产品\"></a></td>\n";
         s=s+"<td width=\"40\" align=\"center\"><a class=OraGlobalButtonText  href=\"../app/welcome\"><img src=\"/../crmjsp/images/gb-homeicon.gif\" border=\"0\" alt=\"主页\"></a></td>\n";
         s=s+"<td width=\"40\" align=\"center\"><a class=OraGlobalButtonText  href=\"/login?action=logout\"><img src=\"/../crmjsp/images/gb-logoff.gif\"  border=\"0\" alt=\"注销\"></a></td>\n";
         s=s+"<td width=\"40\" align=\"center\"><a class=OraGlobalButtonText  href=\"/help/index.html\" target=\"_balnk\"><img src=\"/../crmjsp/images/gb-helpicon.gif\" border=\"0\" alt=\"帮助\"></a></td>\n";
       s=s+"</tr>\n";
     s=s+"</table>\n";
   s=s+"</td>\n";
 s=s+"</tr>\n";
 s=s+"</table>\n";
 s=s+"<SCRIPT language=JavaScript1.2 src=\"/../crmjsp/images/stm31.js\" type=text/javascript></SCRIPT>\n";

     s=s+"<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"  height=\"28\">\n";
       s=s+"<tr>\n"; 
         s=s+"<td width=\"1%\" height=\"28\"><img src=\"/../crmjsp/images/nav-first.gif\" width=\"10\" height=\"28\"></td>\n";
         
	s=s+"<td class=OraNav2Selected background=\"/../crmjsp/images/nav_bg.gif\" width=\"68%\" align=\"left\" height=\"28\" >\n";
	//s=s+"<SCRIPT language=JavaScript1.2 type=text/javascript>\n";
 MenuBar mb=new MenuBar();
 s=s+mb.get_menu();
// s=s+"</script>\n";
	
 s=s+"</td><form name=\"transaction\" method=\"post\" action=\"/goto\">\n";
 s=s+"<td  background=\"/../crmjsp/images/nav_bg.gif\" width=\"30%\" align=\"right\" height=\"28\" valign=\"center\">\n";	
 String t_code="";
 try
 {
     t_code=StringUtil.cEmpty(this.sessionInfo.getParameter("t_code"));
 }catch(Exception e)
 {
  // e.printStackTrace();
   t_code="";
 }
			s=s+"<font color=\"#FFFFFF\" >事务代码:</font><input size=4 height=21 name=\"t_code\" value=\""+t_code+"\")><input type=\"submit\" value=\"go\"></td></form>\n";			
       s=s+"  <td width=\"1%\" height=\"28\"><img src=\"/../crmjsp/images/nav-end_l.gif\" width=\"8\" height=\"28\"></td>\n";
      s=s+" </tr>\n";
     s=s+"</table>\n";
 s=s+"<script language=\"JavaScript\" src=\"/go_top.js\"></script>\n";
 s=s+"<script language=\"JavaScript\" src=\"/common.js\"></script>\n";
 s=s+"<script language=\"JavaScript\" src=\"/calendar.js\"></script>\n";
 return s;
 }
 
 private String getFooter()
 {
   String s="";
   s=s+"</body>\n";
   s=s+"</html>\n";
   return s;
 }
 
 public void setSubject(String subject)
 {
     this.subject=subject;
 }
 
   public void addSectionSubject(String sectionSubject)
 {
     String s="<br>\n";
   s=s+"<table width=\""+this.width+"\" border=0 cellspacing=1 cellpadding=5 >\n";
   s=s+"<tr>\n";
   s=s+"<td> <span class=\"OraHeader\">"+sectionSubject+"</span>\n";
 	s=s+ "<table width=\"100%\" border=0 cellspacing=0 cellpadding=0 background=\"/../crmjsp/images/headerlinepixel_onwhite.gif\" HEIGHT=\"1\" WIDTH=\"1\">\n";
	s=s+ "<tr background=\"/../crmjsp/images/headerlinepixel_onwhite.gif\" HEIGHT=\"1\" WIDTH=\"1\">\n";
	s=s+"<td height=\"1\" width=100% background=\"/../crmjsp/images/headerlinepixel_onwhite.gif\" HEIGHT=\"1\" WIDTH=\"1\"><img src=\"/../crmjsp/images/headerlinepixel_onwhite.gif\" HEIGHT=\"1\" WIDTH=\"1\" ></td>\n";
   s=s+"</tr>\n";
	s=s+"</table>\n";
	s=s+"</td>\n";
   s=s+"</tr>\n";
   s=s+"</table>\n";
   this.content=content+s;
   
 }
 private String getSubject()
 {
   String s="";
   if(style==DEFAULT_STYLE)
   {
     s=s+"<table width=\""+this.width+"\" border=0 cellspacing=1 cellpadding=5 >\n";
     s=s+"<tr>\n";
     s=s+"<td> <span class=\"OraHeader\">"+this.subject+"</span>\n";
     s=s+ "<table width=\"100%\" border=0 cellspacing=0 cellpadding=0 background=\"/../crmjsp/images/headerlinepixel_onwhite.gif\" HEIGHT=\"1\" WIDTH=\"1\">\n";
     s=s+ "<tr background=\"/../crmjsp/images/headerlinepixel_onwhite.gif\" HEIGHT=\"1\" WIDTH=\"1\">\n";
     s=s+"<td height=\"1\" width=100% background=\"../crmjsp/images/headerlinepixel_onwhite.gif\" HEIGHT=\"1\" WIDTH=\"1\"><img src=\"../crmjsp/images/headerlinepixel_onwhite.gif\" HEIGHT=\"1\" WIDTH=\"1\" ></td>\n";
     s=s+"</tr>\n";
     s=s+"</table>\n";
     s=s+"</td>\n";
     s=s+"</tr>\n";
     s=s+"</table>\n";
   }
   if(style==REPORT_STYLE)
   {
       s=s+"<table width=\""+this.width+"\" border=0 cellspacing=1 cellpadding=5  align=\"center\" >\n";
       s=s+"<tr><td align=\"center\"><span class=OraHeader>"+subject+"</span></td></tr>\n";
       s=s+"</table>\n";
   }
   return s;
 }
 
 public void addListView(ResultSet resultset)
 {
    this.rs=resultset;
    String s="<table width=\""+this.width+"\" border=0 cellspacing=1 cellpadding=5 \n";
    if(style==DEFAULT_STYLE) s=s+"  >\n";
    if(style==REPORT_STYLE) s=s+" align=center >\n";
    Vector fds=new Vector();
    int width_count=0;
    try{
       ResultSetMetaData rsmd = rs.getMetaData();
       int numberOfColumns = rsmd.getColumnCount();
       for(int i=1;i<=numberOfColumns;i++)
       {  
           Field fd=FieldMap.getField(rsmd.getColumnName(i));
           if(fd!=null)
           {  
               if(fd.getVisible()) 
               {
                   fds.add(fd);
                   width_count=width_count+fd.getDataSize();       
               }
           }
       }
       s=s+"<tr>\n";
       for(int i=0;i<fds.size();i++)
       {
           Field fd=(Field)fds.elementAt(i);
           s=s+"<th width=\""+(fd.getDataSize()*100)/width_count+"%\" class=OraTableRowHeader noWrap align=middle  >"+fd.getCaption()+"</th>\n";
       }
          
       s=s+"</tr>\n";
       
       while(rs.next())
       {
           s=s+"<tr>\n";
           for(int i=0;i<fds.size();i++)
           {
               Field fd=(Field)fds.elementAt(i);
               s=s+"<td class=OraTableCellText noWrap align=middle >"+getCellData(fd.getName())+"</td>\n";
           }
           s=s+"</tr>\n";
       }
       rs.close();
    }catch(Exception e)
    {
        e.printStackTrace();
        System.err.println("ERROR>>"+e);
    }
     s=s+"</table>\n"; 
     this.content=this.content+s;
 }
 
 public void addDetailView(int doc_type,int doc_id)
 {
       DocType doc=new DocType(doc_type);
       this.doc_type=doc_type;
       if(!doc.isReadonly())
       {
           //this.addButton("增加","../app/viewnew?doc_type="+doc_type);
       	/*
           this.addButton("修改","../app/viewupdate?doc_type="+doc_type+"&doc_id="+doc_id);
           this.addButton("删除","../app/ctrdelete?doc_type="+doc_type+"&doc_id="+doc_id);
           */
           this.addButton("返回", "history.go(-1)");
       }

       
       Field[] fds=doc.getFields();
     
      
       String  s="<table width=\""+width+"\" border=0 cellspacing=1 cellpadding=5>\n"; 
       s=s+"<input type=\"hidden\" name=\"doc_type\" value=\""+doc_type+"\" >\n";
       try{
       	conn=DBManager.getConnection();
           String sql="select * from "+doc.getDataSource()+" where id="+doc_id;
           if(!doc.is_global()) sql=sql+" and company_id="+sessionInfo.getCompanyID();
           stmt=conn.createStatement(); rs=stmt.executeQuery(sql);
           rs.next();
           int index=0;
           for(int i=0;i<fds.length;i++)
           {  
               Field fd=fds[i];
               if(fd!=null)
               {  
                   if(fd.isDetail()) 
                   {
                         s=s+"<tr><td width=\"30%\" class=OraTableRowHeader noWrap align=right  >"+fd.getCaption()+"</th>\n";
                         s=s+"<td width=\"70%\" class=OraTableCellText noWrap align=left  >"+getCellData(fd.getName())+"</th></tr>\n";  
                   }
               }
           }
           s=s+"</table>\n";
    }catch(Exception e)
    {
        e.printStackTrace();
        System.err.println(e);
    }finally
    {
        try
        {
            if(rs!=null) rs.close();
            if(stmt!=null) stmt.close();
            conn.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
       content=content+s;

 }
 /**
  * 
  * @param doc_type     文档类型
  * @param fds          文档字段数组
  * @param fmName       form 的名称
  * @return             返回组合好的JavaScript 字符串
  */
  
 private String addJavaScript(Field fd,String fmName){
   StringBuffer s = new StringBuffer();
   if(fd.isRequired()){
     s.append("if(!validateRequired(").append(fmName).append(",'").append(fd.getName()).append("')){\n");
     s.append("alert('").append(fd.getCaption()).append("不能为空!');\n");
     s.append("return;\n");
     s.append("}\n");    
   }
   if(fd.getInputType().equals("TEXT")){
     if(fd.getName().equals("email")){
       s.append("if(!validateEmail(").append(fmName).append(",'").append(fd.getName()).append("')){\n");
       s.append("alert('").append(fd.getCaption()).append("格式不正确!');\n");
       s.append("return;\n");
       s.append("}\n");      
     }
     if(fd.getName().endsWith("phone")){
       s.append("if(!validateTelephone(").append(fmName).append(",'").append(fd.getName()).append("')){\n");
       s.append("alert('").append(fd.getCaption()).append("格式不正确!');\n");
       s.append("return;\n");
       s.append("}\n");      
     }      
     if(fd.getDataType().equals("INTEGER")){
       s.append("if(!validateInteger(").append(fmName).append(",'").append(fd.getName()).append("')){\n");
       s.append("alert('").append(fd.getCaption()).append("应为整数!');\n");
       s.append("return;\n");
       s.append("}\n");   
     }
     if(fd.getDataType().equals("FLOAT")){
       s.append("if(!validateFloat(").append(fmName).append(",'").append(fd.getName()).append("')){\n");
       s.append("alert('").append(fd.getCaption()).append("应为浮点数!');\n");
       s.append("return;\n");
       s.append("}\n");        
     }
   }    
   return s.toString();
 }
 public String addJavaScript(int doc_type,Field[] fds,String fmName,boolean isNew){
   StringBuffer s = new StringBuffer("<script language=\"JavaScript\">\n");
   s.append("function addSubmit() {\n");
   for(int i=0;i<fds.length;i++){
     if(isNew && fds[i].isNew()){            //是增加保存
         s.append(addJavaScript(fds[i],fmName));
     }
   
     if(!isNew && fds[i].isUpdate()){        //是修改保存
       s.append(addJavaScript(fds[i],fmName));
     }
   }
   s.append("document.").append(fmName).append(".submit();\n");
   s.append("document.fm_update.submitButton.disabled=true;\n");
   s.append("}\n");
   s.append("</script>\n");
   return s.toString();
 }
 public void addNewView(int doc_type)
 {
   addNewView(doc_type,0);
 }
 public void addNewView(int doc_type,int parent_doc_id)
 {
       DocType doc=new DocType(doc_type);
       Field[] fds=doc.getFields();
       this.doc_type=doc_type;
       if(doc_type==2010 || doc_type==1500){
       String strScript = "";
       strScript = addJavaScript(doc_type,fds,"fm_update",true);
       content = content + strScript;
       }
       String  s="<table width=\""+width+"\" border=0 cellspacing=1 cellpadding=5 ";
        if(style==DEFAULT_STYLE) s=s+"  >\n";
       if(style==REPORT_STYLE) s=s+" align=center >\n";
       s=s+"<form name=\"fm_update\" method=\"post\" action=\"../app/ctrnew\">\n";
       s=s+"<input type=\"hidden\" name=\"doc_type\" value=\""+doc_type+"\" >\n";
       if(parent_doc_id>0 && doc.isParentDoc()) s=s+"<input type=\"hidden\" name=\"parent_doc_id\" value=\""+parent_doc_id+"\" >\n";
       
       for(int i=0;i<fds.length;i++)
       {
           if(fds[i].isNew())
           {
               String default_value=StringUtil.cEmpty(fds[i].getDefaultValue());
               
               if(default_value.equals("TODAY"))
               {
                   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                   default_value=sdf.format(new Date()).toString();
               }
               if(default_value.equals("OPERATOR_ID"))
                  default_value=sessionInfo.getOperatorID()+"";
                   
               s=s+"<tr>\n";
               if(fds[i].isRequired())
                   s=s+"<td width=\"20%\" align=\"right\" ><font color=red>*</font>&nbsp;"+fds[i].getCaption()+"</td>\n";
               else
                  s=s+"<td width=\"20%\" align=\"right\" >&nbsp;"+fds[i].getCaption()+"</td>\n";
               s=s+"<td width=\"40%\" align=\"left\" >&nbsp;";
               if(fds[i].getInputType().equals("LOOKUP")||fds[i].getInputType().equals("KEYSET"))
               {
                    String key_value=StringUtil.cEmpty(sessionInfo.getParameter(fds[i].getName()+"_key"));
                   if(!key_value.equals(""))
                  {
                     Lookup lu=LookupMap.getLookup(fds[i].getLookup());
                   
                     try
                     {
                     	conn=DBManager.getConnection();
                        stmt=conn.createStatement();rs= stmt.executeQuery("select "+lu.getValueField()+","+lu.getKeyField()+","+lu.getDisplayField()+" from "+lu.getDataSource()+" where "+lu.getKeyField()+"='"+key_value+"'");
                       if(rs.next())    default_value=rs.getString(1);
                     }catch(Exception e){
                     e.printStackTrace();
                      System.out.println("error-"+e);
                     }finally
                     {
                         try
                           {
                                if(rs!=null) rs.close();
                                if(stmt!=null) stmt.close();
                                conn.close();
                           }catch(Exception e)
                           {
                               e.printStackTrace();
                           }
                     }
                    }
               }
               s=s+addField(fds[i].getName(),default_value);
               s=s+"</td><td width=\"40%\" class=OraTipText align=\"left\">"+fds[i].getDescription()+"</td>";
               s=s+"</tr>\n"; 
           }
       }
       s=s+"<tr><td colspan=3>\n";
    	  s=s+"<table width=\""+width+"\" border=0 cellspacing=0 cellpadding=0 background=\"../crmjsp/images/headerlinepixel_onwhite.gif\" HEIGHT=1 WIDTH=1>\n";
       s=s+"<tr background=\"../crmjsp/images/headerlinepixel_onwhite.gif\" HEIGHT=1 WIDTH=1>\n";
       s=s+"<td  height=1 width=\""+width+"\" background=\"../crmjsp/images/headerlinepixel_onwhite.gif\" HEIGHT=1 WIDTH=1><img src=\"../crmjsp/images/headerlinepixel_onwhite.gif\" HEIGHT=1 WIDTH=1></td>\n";
       s=s+"</tr></table></td></tr>\n";
       s=s+"<tr>\n";
       s=s+"<td align=\"right\" colspan=3>\n";
       if(doc_type==2010 || doc_type==1500){
         s=s+"<input name = \"submitButton\" id=\"submitButton\" type=\"button\" class=\"button2\" value=\"提交\" onClick=\"addSubmit();\">&nbsp;\n";
       }else{
         s=s+"<input name = \"submitButton\" id=\"submitButton\" type=\"button\" class=\"button2\" value=\"提交\" onClick=\"submit();document.fm_update.submitButton.disabled=true;\">&nbsp;\n";
       }
       s=s+"<input type=\"button\" class=\"button2\" value=\"取消\" onClick=\"history.back();\">\n";
		    s=s+"</tr>\n";
       s=s+"</form>\n";
       s=s+"</table>\n";
       content=content+s;
 }
 
 public String getHTML()
 {
   String s="";
   s=getHeader();

   if(search_bar!=null)
     s=s+search_bar;
   else
     s=s+"<br>\n";
   if(subject!=null)
     s=s+getSubject();
   
   s=s+this.content;
   
   s=s+getFooter();
  
   //dblink.close();
   return s;
 }
 
 public void addMessageBox(String title,String message)
 {
   String s="<br>\n";
   s=s+"<br>\n";
   s=s+"<table width=\"50%\" border=\"0\" cellspacing=1 cellpadding=5 align=\"center\">\n"; 
   s=s+"<tr>\n";
   s=s+"<th  width=\"5%\" class=OraTableRowHeader  noWrap align=middle >"+title+"&nbsp;</th>\n";
   s=s+"</tr>\n";
   s=s+"<tr>\n";
   s=s+"<td class=OraTableCellText noWrap align=middle >"+message+"</td>\n";
   s=s+"</tr>\n";
   s=s+"<tr>\n";
   s=s+"<td class=\"OraBGAccentDark\" align=center>\n";
   s=s+"<input type=\"button\" value=\"返回\"  class=\"button1\" onclick=\"history.go(-1);\"></td>\n";
   s=s+"</td>\n";
   s=s+"</tr>\n";
   s=s+"</table>\n";
   this.content=this.content+s;
 }
 
 public void addSuccessMessageBox(String title,String message)
 {
   String s="<br>\n";
   s=s+"<br>\n";
   s=s+"<table width=\"50%\" border=\"0\" cellspacing=1 cellpadding=5 align=\"center\">\n"; 
   s=s+"<tr>\n";
   s=s+"<th  width=\"5%\" class=OraTableRowHeader  noWrap align=middle >"+title+"&nbsp;</th>\n";
   s=s+"</tr>\n";
   s=s+"<tr>\n";
   s=s+"<td class=OraTableCellText noWrap align=middle >"+message+"</td>\n";
   s=s+"</tr>\n";
   s=s+"<tr>\n";
   s=s+"<td class=\"OraBGAccentDark\" align=center>\n";
   s=s+"</td>\n";
   s=s+"</td>\n";
   s=s+"</tr>\n";
   s=s+"</table>\n";
   this.content=this.content+s;
 }
 public void addButton(String name, String url_link)
 {
   buttons=buttons+"<input type=\"button\" class=\"button2\" value=\""+name+"\" ";
   if(!url_link.equals("history.go(-1)"))
   	
       buttons=buttons+" onClick=\"javascript:document.location.href='"+url_link+"';\">\n";
  
   else
       buttons=buttons+" onClick=\"history.go(-1);\">\n";
 }
 
 public void addSeparator()
 {
   String s="<br>\n";;
 	s=s+ "<table width=\""+this.width+"\"  border=0 cellspacing=0 cellpadding=0 background=\"../crmjsp/images/headerlinepixel_onwhite.gif\" HEIGHT=\"1\" WIDTH=\"1\">\n";
	s=s+ "<tr background=\"../crmjsp/images/headerlinepixel_onwhite.gif\" HEIGHT=\"1\" WIDTH=\"1\">\n";
	s=s+"<td height=\"1\" width=100% background=\"../crmjsp/images/headerlinepixel_onwhite.gif\" HEIGHT=\"1\" WIDTH=\"1\"><img src=\"../crmjsp/images/headerlinepixel_onwhite.gif\" HEIGHT=\"1\" WIDTH=\"1\" ></td>\n";
   s=s+"</tr>\n";
	s=s+"</table>\n";
    this.content=this.content+s;
 }

   public void setDocType(int doc_type)
   {
       this.doc_type = doc_type;
   }
   
   public void addButtons()
   {
       if(!buttons.equals(""))
       {
       if(style==DEFAULT_STYLE)
       {
             String s="<table width=\""+width+"\" border=0 cellspacing=1 cellpadding=5 ><tr><td >\n<table width=\""+width+"\" border=0 cellspacing=0 cellpadding=0 background=\"../crmjsp/images/headerlinepixel_onwhite.gif\" HEIGHT=1 WIDTH=1>\n";
             s=s+"<tr background=\"../crmjsp/images/headerlinepixel_onwhite.gif\" HEIGHT=1 WIDTH=1>\n";
             s=s+"<td  height=1 width=\""+width+"\" background=\"../crmjsp/images/headerlinepixel_onwhite.gif\" HEIGHT=1 WIDTH=1><img src=\"../crmjsp/images/headerlinepixel_onwhite.gif\" HEIGHT=1 WIDTH=1></td>\n";
             s=s+"</tr></table></td></tr>\n";
             s=s+"<tr>\n";
             s=s+"<td align=right> \n";
             s=s+buttons+"\n";
             s=s+"</td>\n";
             s=s+"</tr>\n";
             s=s+"</table>\n";
             content=content+s;
       }
       
       if(style==REPORT_STYLE)
       {
            String s="<table width=\""+width+"\" border=0 cellspacing=1 cellpadding=5  align=center>";
             s=s+"<tr>\n";
             s=s+"<td align=\"center\" > \n";
             s=s+buttons+"\n";
             s=s+"</td>\n";
             s=s+"</tr>\n";
             s=s+"</table>\n";
             content=content+s;
       }
       }
   }
   
   public String addPageOfNavigation(String searchStr,int current_page,int sum_page,int row_count,int row_num,int width_count,int isHeader){
     String fm = "fm_navigation" + isHeader;
     StringBuffer s = new StringBuffer();
     s.append("<tr><td colspan=").append(width_count).append(" align=\"left\" ></td></tr>\n");
     s.append("<tr><td colspan=").append(width_count).append(" align=\"left\" ><table width = 100% cellpadding = \"0\" cellspacing = \"1\">\n");
     s.append("<tr>\n");
     s.append("<td>\n");
     s.append("<table>\n");
     s.append("<tr><form method=\"post\" action=\"../app/viewlist\" name=\"").append(fm).append("\">\n");
     s.append("<input type=\"hidden\" name=\"doc_type\" value=\"").append(doc_type).append("\">\n");
     s.append("<input type=\"hidden\" name=\"query_sql\" value=\"").append(searchStr).append("\">\n");
     s.append("<input type=\"hidden\" name=\"current_page\" value=\"").append(current_page).append("\">\n");
     s.append("<input type=\"hidden\" name=\"sum_page\" value=\"").append(sum_page).append("\">\n");
     s.append("<input type=\"hidden\" name=\"method\" value=\"\">\n");
     s.append("<td NoWrap align=left width=\"90%\" valign=\"middle\">\n");
     s.append("一共有<u><b>").append(row_num).append("</b></u>条记录行满足查询条件  当前显示第<u><b>").append((current_page-1) * row_count+1).append("</u></b>条 到 第<u><b>").append((current_page)*row_count>row_num?row_num:(current_page)*row_count).append("</u></b>条 记录");
     s.append("</td>\n");
     s.append("<td NoWrap align=\"right\" width=\"10%\" valign=\"middle\">\n");
     if(current_page !=1){
       s.append("<a href=\"javascript:document.").append(fm).append(".method.value='first';document.").append(fm).append(".submit();\">").append("首页").append("</a>&nbsp;&nbsp;|&nbsp;&nbsp;");
       s.append("<a href=\"javascript:document.").append(fm).append(".method.value='previous';document.").append(fm).append(".submit();\">").append("上一页").append("</a>&nbsp;&nbsp;|&nbsp;&nbsp;");
     } else {
       s.append("首页").append("&nbsp;&nbsp;|&nbsp;&nbsp;");
       s.append("上一页").append("&nbsp;&nbsp;|&nbsp;&nbsp;");      
     }
     if(current_page!=sum_page){
       s.append("<a href=\"javascript:document.").append(fm).append(".method.value='next';document.").append(fm).append(".submit();\">").append("下一页").append("</a>&nbsp;&nbsp;|&nbsp;&nbsp;");
       s.append("<a href=\"javascript:document.").append(fm).append(".method.value='last';document.").append(fm).append(".submit();\">").append("末页").append("</a>");
     } else {
       s.append("下一页").append("&nbsp;&nbsp;|&nbsp;&nbsp;");
       s.append("末页");      
     }
     s.append("</td>\n");      
     s.append("</form>\n");
     s.append("</tr>\n");
     s.append("</table>\n");
     s.append("</td\n>");
     s.append("</tr>\n");
     s.append("</table>\n");
     s.append("</td>\n");
     s.append("</tr>\n");
     return s.toString();
   }
   
   public void addUpdateView(int doc_type,int doc_id)
  {
         addUpdateView(doc_type, doc_id,0);
  }
   public void addUpdateView(int doc_type,int doc_id,int parent_doc_id)
  {
       DocType doc=new DocType(doc_type);
       Field[] fds=doc.getFields();
       if(doc_type==1000 || doc_type==1500){
       String strScript = "";
       strScript = addJavaScript(doc_type,fds,"fm_update",false);
       content = content + strScript;
       }
       String sql="select * from "+doc.getDataSource()+" where id="+doc_id;
       
       String  s="<table width=\""+width+"\" border=0 cellspacing=1 cellpadding=5>\n"; 
       s=s+"<form name=\"fm_update\" method=\"post\" action=\"../app/ctrupdate\">\n";
       s=s+"<input type=\"hidden\" name=\"doc_type\" value=\""+doc_type+"\" >\n";
       s=s+"<input type=\"hidden\" name=\"doc_id\" value=\""+doc_id+"\" >\n";
       if(parent_doc_id>0 && doc.isParentDoc())  s=s+"<input type=\"hidden\" name=\"parent_doc_id\" value=\""+parent_doc_id+"\" >\n";
       
       try{
       	conn=DBManager.getConnection();
       stmt=conn.createStatement();rs=stmt.executeQuery(sql);
       rs.next();
       for(int i=0;i<fds.length;i++)
       {
           if(fds[i].isUpdate())
           {
               String name=fds[i].getName();
               String display=fds[i].getRefIdName();
               if(display==null || display.equals("")) display=name;
               s=s+"<tr>\n";
              if(fds[i].isRequired())
                   s=s+"<td width=\"20%\" align=\"right\" ><font color=red>*</font>&nbsp;"+fds[i].getCaption()+"</td>\n";
              else
                   s=s+"<td width=\"20%\" align=\"right\" >&nbsp;"+fds[i].getCaption()+"</td>\n";
               s=s+"<td align=\"left\" >\n";
               if(fds[i].getDataType().equals("FLOAT"))
               {
                   if(rs.getString(fds[i].getName())==null)
                     s=s+addField(fds[i].getName(),"");
                   else{
                     //s=s+addField(fds[i].getName(),rs.getFloat(fds[i].getName())+"");
                     s=s+addField(fds[i].getName(),Arith.formatValue(new Float(rs.getFloat(fds[i].getName())),"#0.####"));
                   }
               }
               else
               {
                 if(fds[i].getDataType().equals("DATE")){
                    if(rs.getDate(fds[i].getName())==null){
                      s=s+addField(fds[i].getName(),"");
                    } else {
                    s=s+addField(fds[i].getName(),rs.getDate(fds[i].getName())+"");
                    }
                 }
                 else {
                   s=s+addField(fds[i].getName(),StringUtil.cleanValue(rs.getString(fds[i].getName())));
                 }
               }
               s=s+"</td></tr>\n";  
           }
       }
       s=s+"<tr><td colspan=2>\n";
    	s=s+"<table width=\""+width+"\" border=0 cellspacing=0 cellpadding=0 background=\"../crmjsp/images/headerlinepixel_onwhite.gif\" HEIGHT=1 WIDTH=1>\n";
       s=s+"<tr background=\"../crmjsp/images/headerlinepixel_onwhite.gif\" HEIGHT=1 WIDTH=1>\n";
       s=s+"<td  height=1 width=\""+width+"\" background=\"../crmjsp/images/headerlinepixel_onwhite.gif\" HEIGHT=1 WIDTH=1><img src=\"../crmjsp/images/headerlinepixel_onwhite.gif\" HEIGHT=1 WIDTH=1></td>\n";
       s=s+"</tr></table></td></tr>\n";
       s=s+"<tr>\n";
       s=s+"<td align=\"right\" colspan=2>\n";
       if(doc_type==1000 || doc_type==1500){
         s=s+"<input name = \"submitButton\" id=\"submitButton\" type=\"button\" class=\"button2\" value=\"提交\" onClick=\"addSubmit();\">&nbsp;\n";
       }else{
         s=s+"<input type=\"button\" class=\"button2\" value=\"提交\" onClick=\"submit();\">&nbsp;\n";
       }        
       s=s+"<input type=\"button\" class=\"button2\" value=\"取消\" onClick=\"history.back();\">\n";
		s=s+"</tr>\n";
       s=s+"</form>\n";
       s=s+"</table>\n";
     }catch(Exception e)
    {
        e.printStackTrace();
        System.out.println("ERROR:"+e);
    }finally
    {
        try
        {
            if(rs!=null) rs.close();
            if(stmt!=null) stmt.close();
            conn.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
       content=content+s;
  }
  
  private String getCellData(String name)
  {
       Field fd=FieldMap.getField(name);
       String ref_field_name=fd.getRefIdName();
       String cell_data="";
       try
       {
           cell_data=StringUtil.cEmpty(rs.getString(name));
           //System.out.println(fd.getInputType());
          if(fd.getInputType().equals("RADIO"))// || fd.getInputType().equals("LOOKUP")||fd.getInputType().equals("KEYSET") ||fd.getInputType().equals("LIST") )
           {
              /* Map map=LookupMap.getListValues(fd.getLookup(),this.sessionInfo.getCompanyID(),null);
               //System.out.println(cell_data);
               //System.out.println((String)map.get(cell_data));
               cell_data=(String)map.get(cell_data);*/
           }
           else
           {
           if(fd.getDataType().equals("CHAR"))
               cell_data=StringUtil.cleanShow(rs.getString(name));
           if(fd.getDataType().equals("DATE")){ 
                 if(rs.getString(name)==null){
                cell_data="";
              } else {
               if(name.equals("print_date")|| name.equals("release_date")){
                 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                 cell_data=rs.getDate(name)+ " " + rs.getTime(name);
               } else {
                 cell_data=rs.getDate(name)+"";
               }
              }
           }
           if(fd.getDataType().equals("FLOAT"))
              if(rs.getString(name)==null){
                cell_data="";
              } else {
               cell_data=Arith.formatValue(new Float(rs.getFloat(name)),"#0.####");
              }
           if(fd.getDataType().equals("INTEGER"))
               cell_data=rs.getInt(name)+""; 
           }
       int ref_doc_type=fd.getDocType();
       if(ref_doc_type==0) ref_doc_type=this.doc_type;
       if(ref_field_name.equals("")) ref_field_name=name;
       //////////////////// modify by zhux 20050228
       //if(fd.isRefDetail())//&& !cell_data.equals("")
       //    cell_data="<a href=\"../app/viewdetail?doc_type="+ref_doc_type+"&doc_id="+rs.getString(ref_field_name)+"\">"+cell_data+"</a>";
       /////////////////////////////////////////////////
       if (fd.getName().equals("order_number")) {
       		cell_data ="<a href=\"../order/orderView.do?orderId=" + rs.getString(fd.getRefIdName()) + "\">" + rs.getString(fd.getName()) + "</a>";
       } else if(fd.getName().equals("org_order_number")) {
       		cell_data = "<a href=\"../order/groupOrderView.do?orderId=" + rs.getString(fd.getRefIdName()) + "\">" + rs.getString(fd.getName()) + "</a>";
       } else if(fd.getName().equals("cardid")) {
       		cell_data ="<a href=\"../member/memberDetail.do?id=" + rs.getString(fd.getRefIdName()) + "\">" +  rs.getString(fd.getName()) + "</a>";
       } else {
       	if(fd.isRefDetail()) {  
       		cell_data="<a href=\"../app/viewdetail?doc_type="+ref_doc_type+"&doc_id="+rs.getString(ref_field_name)+"\">"+cell_data+"</a>";
       	}
       }
       //////////////////////////////////////////////////////
       
       }catch(Exception e)
       {
           e.printStackTrace();
           System.out.println("ERROR: error in getCellData()."+e.toString());
           return "ERROR";
       }
       
       
       		
       
       return cell_data;   
  }
  
   private String getCellData(String name,String value)
  {
       Field fd=FieldMap.getField(name);
       String ref_field_name=fd.getRefIdName();
       String cell_data=StringUtil.cleanShow(value);

     /*  if(fd.getInputType().equals("RADIO") || fd.getInputType().equals("LOOKUP")|| fd.getInputType().equals("KEYSET") || fd.getInputType().equals("LIST") )
       {
           Map map=LookupMap.getListValues(fd.getLookup(),this.sessionInfo.getCompanyID(),null);
           Object[] values=map.keySet().toArray();
           cell_data=(String)map.get(value);
       }*/
       if(fd.getInputType().equals("TEXT"))
       {
          cell_data=StringUtil.cleanShow(value);
       }
      
       int ref_doc_type=fd.getDocType();
       if(ref_doc_type==0) ref_doc_type=this.doc_type;
       if(ref_field_name.equals("")) ref_field_name=name;
       if(fd.isRefDetail())
           cell_data="<a href=\"../app/viewdetail?doc_type="+ref_doc_type+"&doc_id="+value+"\">"+cell_data+"</a>";
       return cell_data;   
  }
  
  public String addField(String field_name,String value)
  {
      Field fd=FieldMap.getField(field_name);
      String field_value=StringUtil.cleanValue(value);
      String s="";
      if(fd.getDataType().equals("CLOB"))
     {
           s=s+"<textarea cols=120 rows=15 name=\""+field_name+"\" >"+value+"</textarea>\n";
           /*s=s+"<script language=\"javascript\" src=\"/rtf/fckeditor.js\"></script>\n";
           s=s+" <script language=\"javascript\">\n";
           s=s+"<!--\n";
           s=s+"var oFCKeditor ;\n";
           s=s+"oFCKeditor = new FCKeditor('"+field_name+"') ;\n";
           s=s+"oFCKeditor.BasePath = '/rtf/' ;	\n";
           s=s+"oFCKeditor.Value = '"+field_value+"' ;\n";
           s=s+"oFCKeditor.Config['StyleNames'] = ';样式一 ;样式二 ;样式三 ';\n";
           s=s+"oFCKeditor.Config['ToolbarFontNames'] = ';Arial;Courier New;Times New Roman;Verdana' ;\n";
           s=s+"oFCKeditor.Create() ;\n";
           s=s+"//-->\n";
           s=s+"			</script>\n";*/
     }
               
     if(fd.getInputType().equals("TEXT"))
     {
        
         if(fd.getDataType().equals("INTEGER"))
               s=s+"<input name=\""+field_name+"\" value=\""+field_value+"\">\n";
         else
        {
           if(fd.getDataType().equals("FLOAT")){
               s=s+"<input name=\""+field_name+"\" value=\""+field_value+"\">\n";
           }
           else
           {
              if(fd.getName().toUpperCase().equals("PWD"))
                 s=s+"<input name=\""+field_name+"\" value=\"\">\n";
              else
                 s=s+"<input name=\""+field_name+"\" value=\""+field_value+"\">\n";
           }
        }
     }
     if(fd.getInputType().equals("DATE"))
     {
         s=s+"<input id=\""+field_name+"\" name=\""+field_name+"\" value=\""+field_value+"\"> <a href=\"javascript:calendar(fm_update."+field_name+")\"><img src=\"../crmjsp/images/icon_date.gif\" border=0 align=\"top\"><a>(格式:YYYY-MM-DD)\n";
     }
     if(fd.getInputType().equals("PASSWORD"))
     {
         s=s+"<input type=\"password\" id=\""+field_name+"\" name=\""+field_name+"\" value=\"password\">\n"; 
     }
     if(fd.getInputType().equals("LOOKUP"))
     {
         String key_value="";  
         String key_description="";
          //判断传入的已知参数
         if(value!=null && !value.equals(""))
         {
             Lookup lu=LookupMap.getLookup(fd.getLookup());
            Statement stmt1=null;
            ResultSet rs1=null;
             try
             {
             	conn=DBManager.getConnection();
                  stmt1=conn.createStatement();
                  rs1= stmt1.executeQuery("select "+lu.getValueField()+","+lu.getKeyField()+","+lu.getDisplayField()+" from "+lu.getDataSource()+" where "+lu.getValueField()+"='"+value+"'");
                 if(rs1.next())
                 {
                   // value=rs.getString(1);
                    key_value=rs1.getString(2);
                    key_description=rs1.getString(3);
                 }
                 else
                 {
                  // value="";
                   key_value="";
                   key_description="";
                 }
             }catch(Exception e)
             {
               e.printStackTrace();
               System.out.println("error-"+e);
             }finally
             {
                 try
                 {
                     if(stmt1!=null) stmt1.close();
                     if(rs1!=null) rs1.close();
                     conn.close();
                 }catch(Exception e)
                 {
                     e.printStackTrace();
                 }
             }
         }
         s=s+"<input type=\"hidden\" id=\""+field_name+"\" name=\""+field_name+"\" value=\""+value+"\"> ";
         s=s+"<input id=\""+field_name+"_key\" name=\""+field_name+"_key\" value=\""+key_value+"\"  readonly onclick=\"javascript:select_item('"+field_name+"',fm_update."+field_name+",fm_update."+field_name+"_key,"+field_name+"_display);\">\n";
         s=s+"<a href=\"javascript:select_item('"+field_name+"',fm_update."+field_name+",fm_update."+field_name+"_key,"+field_name+"_display);\"><img src=\"../crmjsp/images/icon_lookup.gif\" border=0 align=\"top\"><a>\n";
         s=s+"&nbsp;<span style=\"display:none\" id=\""+field_name+"_display\" name=\""+field_name+"_display\" ></span>\n";
     }
     if(fd.getInputType().equals("LIST"))
     {
         Map map=LookupMap.getListValues(fd.getLookup(),this.sessionInfo.getCompanyID(),null);
         Object[] values=map.keySet().toArray();
         s=s+"<select id=\""+field_name+"\" name=\""+field_name+"\" > \n";
           s=s+"<option value=\"\">请选择...</option>\n";
         for(int in=0;in<values.length;in++)
         {
              if(value.equals((String)values[in]))
                 s=s+"<option value=\""+(String)values[in]+"\" selected>"+(String)map.get(values[in])+"</option>\n";
             else
                 s=s+"<option value=\""+(String)values[in]+"\" >"+(String)map.get(values[in])+"</option>\n";
         }       
         s=s+"</select>\n";
     }
     if(fd.getInputType().equals("RADIO"))
     {
         Map map=LookupMap.getListValues(fd.getLookup(),this.sessionInfo.getCompanyID(),null);
         Object[] values=map.keySet().toArray();
         for(int in=0;in<values.length;in++)
         {
              if(value.equals((String)values[in]))
                 s=s+"<input type=\"radio\" id=\""+field_name+"\" name=\""+field_name+"\" value=\""+(String)values[in]+"\" checked>"+(String)map.get(values[in])+"&nbsp;\n";
             else
                 s=s+"<input type=\"radio\" id=\""+field_name+"\" name=\""+field_name+"\" value=\""+(String)values[in]+"\" >"+(String)map.get(values[in])+"&nbsp;\n";
         }       

     }
     
     if(fd.getInputType().equals("TEXTAREA"))
     {
         s=s+"<textarea cols=30 rows=4 name=\""+field_name+"\" >"+field_value+"</textarea>\n";
     }
     
      if(fd.getInputType().equals("KEYSET"))
     {
         String key_value="";  
         String key_description="";
          //判断传入的已知参数
         if(value!=null && !value.equals(""))
         {
             Lookup lu=LookupMap.getLookup(fd.getLookup());
             Statement stmt1=null;
             ResultSet rs1=null;
             try
             {
             	conn=DBManager.getConnection();
               stmt1=conn.createStatement();
               rs1= stmt1.executeQuery("select "+lu.getValueField()+","+lu.getKeyField()+","+lu.getDisplayField()+" from "+lu.getDataSource()+" where "+lu.getValueField()+"='"+value+"'");
                 if(rs1.next())
                 {
                   // value=rs.getString(1);
                    key_value=rs1.getString(2);
                    key_description=rs1.getString(3);
                 }
                 else
                 {
                  // value="";
                   key_value="";
                   key_description="";
                 }
             }catch(Exception e)
             {
                e.printStackTrace();
               System.out.println("error-"+e);
             }finally
             {
                try{
                   if(stmt1!=null) stmt1.close();
                   if(rs1!=null) rs1.close();
                   conn.close();
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
             }
         }
         s=s+"<input type=\"hidden\" id=\""+field_name+"\" name=\""+field_name+"\" value=\""+value+"\"> ";
         s=s+"<input id=\""+field_name+"_key\" name=\""+field_name+"_key\" value=\""+key_value+"\" \n";

            //增加价目表详细的时候特殊处理
         if((doc_type==DocType.PRICELIST_LINE||doc_type==DocType.PRICELIST_GIFT||doc_type==DocType.CATALOG_LINE)&&field_name.equals("item_id"))
             s=s+"> <a href=\"javascript:select_item_ex(fm_update."+field_name+",fm_update."+field_name+"_key,"+field_name+"_display,fm_update.common_price,fm_update.card_price);\"><img src=\"../crmjsp/images/icon_lookup.gif\" border=0 align=\"top\"><a>\n";
         else
             s=s+"> <a href=\"javascript:select_item('"+field_name+"',fm_update."+field_name+",fm_update."+field_name+"_key,"+field_name+"_display);\"><img src=\"../crmjsp/images/icon_lookup.gif\" border=0 align=\"top\"><a>\n";
         s=s+"&nbsp;<span id=\""+field_name+"_display\" name=\""+field_name+"_display\" >"+key_description+"</span>\n";
     }
     return s;
  }
    public String addField(String field_name) 
  {
       return addField(field_name,"");
  }
  public void addDetailViewEx(int doc_type,int doc_id)
 {
       DocType doc=new DocType(doc_type);
       this.doc_type=doc_type;
       Field[] fds=doc.getFields();
       String sql="select * from "+doc.getDataSource()+" where id="+doc_id;
      
       String  s="<table width=\""+width+"\" cellspacing=\"0\" cellpadding=\"4\"  border=\"1\" bordercolor=\"#FFFFFF\" bordercolorlight=\"#cccccc\" align=\"center\">\n"; 
       s=s+"<input type=\"hidden\" name=\"doc_type\" value=\""+doc_type+"\" >\n";
       try{
       	conn=DBManager.getConnection();
           stmt=conn.createStatement();rs=stmt.executeQuery(sql);
           rs.next();
           int index=0;
           for(int i=0;i<fds.length;i++)
           {  
               Field fd=fds[i];
               if(fd!=null)
               {  
                   if(fd.isDetail()) 
                   {
                         if(fd.getInputType().equals("TEXTAREA"))
                         {
                             s=s+"<tr>";
                             s=s+"<td width=\"20%\" class=OraTableRowHeader noWrap align=right  >"+fd.getCaption()+"</td>\n";
                             s=s+"<td colspan=3 class=OraTableCellText noWrap align=left  >"+getCellData(fd.getName())+"&nbsp;</td>\n";   
                             s=s+"</tr>";
                             index=0;
                         }
                        else
                        {
                         if(index==0) s=s+"<tr>";
                         index++;
                         s=s+"<td width=\"20%\" class=OraTableRowHeader noWrap align=right  >"+fd.getCaption()+"</td>\n";
                         s=s+"<td width=\"30%\" class=OraTableCellText noWrap align=left  >"+getCellData(fd.getName())+"&nbsp;</td>\n";  
                         if(index==2) 
                         {
                           s=s+"</tr>";
                           index=0;
                         }
                        }
                   }
               }
           }
          
    }catch(Exception e)
    {
        e.printStackTrace();
    }finally
    {
        try
        {
            if(rs!=null) rs.close();
            if(stmt!=null) stmt.close();
            conn.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
      s=s+"</table>\n";
       content=content+s;
 }
 
 public String addQueryView(int doc_type)
 {
       String s="";
       s=s+"<table width=\"100%\" border=0 cellspacing=1 cellpadding=5 >\n";
       s=s+"<tr><td width=750>"+addQueryContent(doc_type)+"</td>";
       s=s+"<td valign=\"top\">"+addFavoriteQueryList(doc_type)+"</td>";
       s=s+"</tr></table>";
       content=content+s;
       return s;
 }
 private String addQueryContent(int doc_type)
 {
       DocType doc=new DocType(doc_type);
       Field[] fds=doc.getFields();
       String  s="<table width=\""+width+"\" border=0 cellspacing=1 cellpadding=5>\n"; 
       s=s+"<form name=\"fm_add\" method=\"post\" action=\"ctrquery\"  onsubmit=\"return querySubmit();\">\n";
       s=s+"<input type=\"hidden\" name=\"doc_type\" value=\""+doc_type+"\" >\n";
        s=s+"<input type=\"hidden\" name=\"is_detail_query_form\" value=\"1\" >\n";
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
               s=s+addSearchField(fds[i].getName());
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
    	  s=s+"<table width=\""+width+"\" border=0 cellspacing=0 cellpadding=0 background=\"../crmjsp/images/headerlinepixel_onwhite.gif\" HEIGHT=1 WIDTH=1>\n";
       s=s+"<tr background=\"../crmjsp/images/headerlinepixel_onwhite.gif\" HEIGHT=1 WIDTH=1>\n";
       s=s+"<td  height=1 width=\""+width+"\" background=\"../crmjsp/images/headerlinepixel_onwhite.gif\" HEIGHT=1 WIDTH=1><img src=\"../crmjsp/images/headerlinepixel_onwhite.gif\" HEIGHT=1 WIDTH=1></td>\n";
       s=s+"</tr></table></td></tr>\n";
       s=s+"<tr>\n";
       s=s+"<td align=\"right\" colspan=2>\n";
       s=s+"<input type=\"button\" class=\"button2\" value=\"保存查询\" onClick=\"fm_add.action='../app/savequery';fm_add.submit();\">&nbsp;\n";
       s=s+"<input type=\"submit\" class=\"button2\" name=\"search\" value=\"查询\" >&nbsp;\n";
       s=s+"<input type=\"button\" class=\"button2\" value=\"取消\" onClick=\"history.back();\">\n";
       s=s+"</tr>\n";
       s=s+"</form>\n";
       s=s+"</table>\n";
       return s;
 }

 public void addConfigListView(int doc_type)
 {
   addConfigListView(doc_type,"");
 }
 public void addConfigListView(int doc_type, String where)
 {
  
       this.doc_type=doc_type;
       DocType doc=new DocType(doc_type);
       if(doc.isReadonly())
       {
           this.addListView(doc_type,where);
           return;
       }
       
       String data_source=doc.getDataSource();
       String sql="select * from "+data_source;
     if(!doc.is_global())
     {
         sql=sql+" where company_id="+sessionInfo.getCompanyID();
         if(where!=null && !where.equals("")) sql=sql+" and "+where;
     }
     else
     {
       if(where!=null && !where.equals("")) sql=sql+" where "+where;
     }
    
      sql=sql+" order by "+ doc.getKeyField();
     int parent_doc_id=0; 
     if(where!=null && !where.equals(""))
         parent_doc_id=Integer.parseInt(where.substring(where.lastIndexOf('=')+1));

    StringBuffer ss = new StringBuffer();
    //String s="<table width=\""+this.width+"\" border=0 cellspacing=1 cellpadding=5 \n";
    ss.append("<table width=\"").append(this.width).append("\" border=0 cellspacing=1 cellpadding=5 \n");
    if(style==DEFAULT_STYLE) ss.append("  >\n");
    if(style==REPORT_STYLE) ss.append(" align=center >\n");
    ss.append("<form name=\"fm_config_").append(doc_type).append("\" method=\"post\">\n");
    ss.append("<input type=\"hidden\" name=\"doc_type\" value=\"").append(doc_type).append("\">\n");
    if(parent_doc_id>0)
       ss.append("<input type=\"hidden\" name=\"parent_doc_id\" value=\"").append(parent_doc_id).append("\">\n");
    Field[] fds=doc.getFields();
    int width_count=0;
     for(int i=0;i<fds.length;i++)
     {
         if(fds[i].isList()) 
               width_count=width_count+fds[i].getDataSize();       
     }  
     ss.append("<tr>\n");
     ss.append("<th width=\"3%\" class=OraTableRowHeader noWrap align=middle  ></th>\n");
       for(int i=0;i<fds.length;i++)
       {
           if(fds[i].isList())
           {
             ss.append("<th width=\"").append((fds[i].getDataSize()*100)/width_count).append("%\" class=OraTableRowHeader noWrap align=middle  >").append(fds[i].getCaption()).append("</th>\n");
           }
       }
          
     ss.append("</tr>\n");
       try
       { 
       	conn=DBManager.getConnection();
              stmt=conn.createStatement();rs=stmt.executeQuery(sql);
             while(rs.next())
           {
               ss.append("<tr>\n");
               ss.append("<td class=OraTableCellText noWrap align=middle ><input type=\"radio\" name=\"doc_id\" value=\"").append(rs.getInt(doc.getKeyField())).append("\"></td>\n");
               for(int i=0;i<fds.length;i++)
               {
                   if(fds[i].isList())
                     ss.append("<td class=OraTableCellText noWrap align=middle >").append(getCellData(fds[i].getName())).append("</td>\n");
               }
               ss.append("</tr>\n");
           }
            ss.append("</form>\n");
           ss.append("</table>\n");
           
       }catch(Exception e)
       {
          e.printStackTrace();
          System.out.println("ERROR:addconfiglistview"+e);
       }finally
    {
        try
        {
            if(rs!=null) rs.close();
            if(stmt!=null) stmt.close();
            conn.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

          ss.append("<table width=\"").append(this.width).append("\" border=0 cellspacing=1 cellpadding=5 \n");
          if(style==DEFAULT_STYLE) ss.append("  >\n");
          if(style==REPORT_STYLE) ss.append(" align=center >\n");
           ss.append("<tr class=\"OraBGAccentDark\">\n");
           ss.append("<td align=\"right\">");
           ss.append("<input type=\"button\" class=\"button3\" value=\"增加记录\" onClick=\"fm_config_").append(doc_type).append(".action='../app/viewnew';fm_config_").append(doc_type).append(".submit();\">");
           ss.append("<input type=\"button\" class=\"button3\" value=\"查看详细\" onClick=\"fm_config_").append(doc_type).append(".action='../app/viewdetail';fm_config_").append(doc_type).append(".submit();\">");
           ss.append("<input type=\"button\" class=\"button3\" value=\"更改记录\" onClick=\"fm_config_").append(doc_type).append(".action='../app/viewupdate';fm_config_").append(doc_type).append(".submit();\">");
           ss.append("<input type=\"button\" class=\"button3\" value=\"删除记录\" onClick=\"fm_config_").append(doc_type).append(".action='../app/ctrdelete';fm_config_").append(doc_type).append(".submit();\">");
           ss.append("</td></tr></table>");	

         this.content=this.content+ss.toString();

 }

public void addListView(int doc_type)
 {
     addListView(doc_type,null);
 }
 
public int addListView(int doc_type, String where,String method,int currentPage,int sumPage){
 this.doc_type=doc_type;
 DocType doc=new DocType(doc_type);
 String data_source=doc.getDataSource();
 StringBuffer sqlFrom = new StringBuffer(data_source);
 if(!doc.is_global()){
   sqlFrom.append(" where company_id=").append(sessionInfo.getCompanyID());
 }
 int row_count=200;
 try{
 	conn=DBManager.getConnection();
   stmt=conn.createStatement();
   rs=stmt.executeQuery("select value from s_config_keys where key='LIST_MAX_ROW_COUNT'");
   rs.next();
   row_count=Integer.parseInt(rs.getString("value"));
 }catch(Exception e){
   e.printStackTrace();
   System.out.println(e);
 }finally{
   try{
     if(rs!=null) rs.close();
     if(stmt!=null) stmt.close();
     conn.close();
   }catch(Exception e){
     e.printStackTrace();
   }
 }
 StringBuffer sqls = new StringBuffer();
 if(!(where ==null || where.equals(""))){
   if(!doc.is_global()){
     sqlFrom.append(" and ").append(where);
   }else{
     sqlFrom.append(" where ").append(where);
   }
 }
 sqls.append(sqlFrom);
 StringBuffer orderBy = new StringBuffer();
 if(where.indexOf("order by")<=0){
   orderBy.append(" order by ").append(doc.getKeyField()).append(" desc");
 }
 sqls.append(orderBy);
 int sum_num = 0 ;
 String sqlWhere = "";
 String itemCodeValue = ""; 
 String otherWhere = "";
 try{
   String countSql = "select count(*) count from " + sqlFrom.toString();
   if(doc_type==DocType.ORDER || doc_type==DocType.ORDER_SHIPPING_NOTICE){
     sqlWhere = sqlFrom.toString().substring(sqlFrom.toString().indexOf("where")+5);
     otherWhere = sqlWhere;         //除去产品编码的查询条件
     if(sqlWhere.indexOf("item_code")>=0){
       int lastAnd = sqlWhere.lastIndexOf("and");
       otherWhere = sqlWhere.substring(0,lastAnd);
       String itemCodeWhere = sqlWhere.substring(lastAnd);
       itemCodeValue = itemCodeWhere.substring(itemCodeWhere.indexOf("%")+1,itemCodeWhere.lastIndexOf("%"));      
     }
     String subSql = "";
     if(!itemCodeValue.equals("") && doc_type==DocType.ORDER){
       countSql = "(select a.* from vw_orders a ,(select distinct c.order_id,d.item_code from ord_lines c ,prd_items d where c.item_id=d.item_id and d.item_code ='" + itemCodeValue +"' ) b where a.id=b.order_id)";
       countSql = "select count(*) count from " + countSql + " where " + otherWhere;
     } else if(!itemCodeValue.equals("") && doc_type==DocType.ORDER_SHIPPING_NOTICE){
       countSql = "(select a.* from VW_SHIPPINGNOTICES a ,(select distinct c.sn_id,d.item_code from ORD_SHIPPINGNOTICE_LINES c ,prd_items d where c.item_id=d.item_id and d.item_code ='" + itemCodeValue +"' ) b where a.id=b.sn_id)";
       countSql = "select count(*) count from " + countSql + " where " + otherWhere;           
     }
   }       
   conn=DBManager.getConnection();
   stmt=conn.createStatement();
   rs=stmt.executeQuery(countSql);
   rs.next();
   sum_num = rs.getInt("count");
 }catch(Exception e){
   e.printStackTrace();
 }finally{
   try{
     if(rs!=null) rs.close();
     if(stmt!=null) stmt.close();
     conn.close();
   }catch(Exception e){
     e.printStackTrace();
   }
 } 
 int current_page = 1;
 int sum_page = 1;
 sum_page = sum_num / row_count;
 if(sum_num%row_count>0){
   sum_page = sum_page + 1;
 }
 if(method.equals("first")){
   current_page = 1;
 } else if(method.equals("next")){
   current_page = currentPage + 1;
 } else if(method.equals("previous")){
   current_page = currentPage - 1;
 }else if(method.equals("last")){
   current_page = sumPage;
 }
 if(current_page>sumPage){
   current_page = sumPage;
 }
 if(current_page<=0){
   current_page = 1;
 }
 sqls.insert(0,"select * from (select row_.*,rownum rownum_ from ( select * from ").append(") row_ where rownum<=").append((current_page) * row_count).append(") where rownum_>").append((current_page-1)*row_count);
 if(doc_type==DocType.ORDER || doc_type==DocType.ORDER_SHIPPING_NOTICE){
   String strOrder = "";
   if(orderBy.indexOf("order by")>=0){
     strOrder = orderBy.substring(orderBy.lastIndexOf("order by")-1);
   } else if(sqlFrom.toString().indexOf("order by")>=0){
     strOrder = sqlFrom.toString().substring(sqlFrom.toString().lastIndexOf("order by")-1);
   }
   String subSql = "";
   if(!itemCodeValue.equals("") && doc_type == DocType.ORDER){
     sqls = new StringBuffer();
     subSql = "(select a.* from vw_orders a ,(select distinct c.order_id,d.item_code from ord_lines c ,prd_items d where c.item_id=d.item_id and d.item_code ='" + itemCodeValue +"' ) b where a.id=b.order_id order by a.id desc)";
     sqls.append("select * from (select row_.*,rownum rownum_ from ( select * from ").append(subSql).append(" ) row_ where rownum<=").append((current_page) * row_count).append(" and ").append(otherWhere).append(") where rownum_>").append((current_page-1)*row_count);
     if(orderBy.indexOf("order by")<0){
       sqls.append(strOrder);
     }
   }else if(!itemCodeValue.equals("") && doc_type == DocType.ORDER_SHIPPING_NOTICE){
     sqls = new StringBuffer();
     subSql = "(select a.* from VW_SHIPPINGNOTICES a ,(select distinct c.sn_id,d.item_code from ORD_SHIPPINGNOTICE_LINES c ,prd_items d where c.item_id=d.item_id and d.item_code ='" + itemCodeValue +"' ) b where a.id=b.sn_id order by a.id desc)";
     sqls.append("select * from (select row_.*,rownum rownum_ from ( select * from ").append(subSql).append(" ) row_ where rownum<=").append((current_page) * row_count).append(" and ").append(otherWhere).append(") where rownum_>").append((current_page-1)*row_count);      
     if(orderBy.indexOf("order by")<0){
       sqls.append(strOrder);
     }           
   }
 }
 StringBuffer s = new StringBuffer();
 s.append("<table width=\"").append(this.width).append("\" border=0 cellspacing=1 cellpadding=5 \n");
 if(style==DEFAULT_STYLE) s.append("  >\n");
 if(style==REPORT_STYLE) s.append(" align=center >\n");
 Field[] fds=doc.getFields();
 int width_count=0;
 int row=0;
 for(int i=0;i<fds.length;i++){
   if(fds[i].isList()){ 
     width_count=width_count+fds[i].getDataSize();
   }
 }  
 s.append("<tr>\n");
 for(int i=0;i<fds.length;i++){
   if(fds[i].isList()){
     s.append("<th width=\"").append((fds[i].getDataSize()*100)/width_count).append("%\" class=OraTableRowHeader noWrap align=middle  >").append(fds[i].getCaption()).append("</th>\n");
   }
 }
 s.append("</tr>\n");
 try{ 
 	conn=DBManager.getConnection();
   stmt=conn.createStatement();
   rs=stmt.executeQuery(sqls.toString());
   while(rs.next()&&row<row_count){
     row++;
     s.append("<tr>\n");
     for(int i=0;i<fds.length;i++){
       if(fds[i].isList()){
         s.append("<td class=OraTableCellText noWrap align=middle >").append(getCellData(fds[i].getName())).append("</td>\n");
       }
     }
     s.append("</tr>\n");
   }
 }catch(Exception e){
   e.printStackTrace();
 }finally{
   try{
            if(rs!=null) rs.close();
            if(stmt!=null) stmt.close();
            conn.close();
   }catch(Exception e){
            e.printStackTrace();
   }
 }
 s.insert(0,addPageOfNavigation(where,current_page,sum_page,row_count,sum_num,width_count,1)).append(addPageOfNavigation(where,current_page,sum_page,row_count,sum_num,width_count,0));
 this.content=this.content+s.toString();
 return row;
}
 public int addListView(int doc_type, String where,boolean isDetail) {  
   if(isDetail){
     this.doc_type=doc_type;
     DocType doc=new DocType(doc_type);
     String data_source=doc.getDataSource();
     String sql_from=data_source;
     String sql="";
     if(!doc.is_global()){
       sql_from=sql_from+" where company_id="+sessionInfo.getCompanyID();
     }
     if(!(where ==null || where.equals(""))){
          if(!doc.is_global()){
             sql=sql_from+"  and "+where;   
             sql_from=sql_from+" and "+where;   
          }else{
             sql=sql_from+" where "+where;
             sql_from =sql_from+" where "+where;
         }
     }else{
       sql=sql_from;      
     }
    if(where.indexOf("order by")<=0){
     sql=sql+" order by "+ doc.getKeyField() + " desc";
    }
    sql = "select * from " + sql;
    
    String s="<table width=\""+this.width+"\" border=0 cellspacing=1 cellpadding=5 \n";
    if(style==DEFAULT_STYLE) s=s+"  >\n";
    if(style==REPORT_STYLE) s=s+" align=center >\n";
    Field[] fds=doc.getFields();     
    int width_count=0;
     for(int i=0;i<fds.length;i++){
         if(fds[i].isList()){ 
           width_count=width_count+fds[i].getDataSize();       
         }
     }  
     s=s+"<tr>\n";
     for(int i=0;i<fds.length;i++){
       if(fds[i].isList()){
         s=s+"<th width=\""+(fds[i].getDataSize()*100)/width_count+"%\" class=OraTableRowHeader noWrap align=middle  >"+fds[i].getCaption()+"</th>\n";
       }
     }     
     s=s+"</tr>\n";
     int row = 0;
     try{
     	conn=DBManager.getConnection();
     stmt=conn.createStatement();rs=stmt.executeQuery(sql);
       while(rs.next()) {
        row++;
         s=s+"<tr>\n";
         for(int i=0;i<fds.length;i++){
           if(fds[i].isList()){
             s=s+"<td class=OraTableCellText noWrap align=middle >"+getCellData(fds[i].getName())+"</td>\n";
           }
         }
         s=s+"</tr>\n";
       }
     }catch(Exception e){
         e.printStackTrace();
        System.out.println(e);
     }finally
    {
        try
        {
            if(rs!=null) rs.close();
            if(stmt!=null) stmt.close();
            conn.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
     s=s+"</table>\n";
     this.content=this.content+s;

     return row;
   } else {
     return addListView(doc_type,where);
   }     
 } 
   public int addListView(int doc_type, String where)
 {
     
       this.doc_type=doc_type;
       DocType doc=new DocType(doc_type);
       String data_source=doc.getDataSource();
       String sql_from=data_source;
       
       //System.out.println(data_source);
      if(!doc.is_global())
         sql_from=sql_from+" where company_id="+sessionInfo.getCompanyID();
    
     int row_count=200;
     try{
     	conn=DBManager.getConnection();
       stmt=conn.createStatement();rs=stmt.executeQuery("select value from s_config_keys where key='LIST_MAX_ROW_COUNT'");
       rs.next();
       row_count=Integer.parseInt(rs.getString("value"));
     }catch(Exception e)
     {
        e.printStackTrace();
       System.out.println(e);
     }finally
    {
        try
        {
            if(rs!=null) rs.close();
            if(stmt!=null) stmt.close();
            conn.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
     String sql="";
     System.out.println(sql_from);
     if(!(where ==null || where.equals("")))
     {
          if(!doc.is_global())
          {
               sql=sql_from+"  and "+where;   
               sql_from=sql_from+" and "+where;   
          }
         else
         {
               sql=sql_from+" where "+where;
               sql_from =sql_from+" where "+where;
         }
     }
     else
       sql=sql_from;
     if(where.indexOf("order by")<=0)
       sql=sql+" order by "+ doc.getKeyField() + " desc";
     sql="select row_.* from ( select * from "+ sql + ") row_ where rownum<="+row_count;
          
   
     String s="<table width=\""+this.width+"\" border=0 cellspacing=1 cellpadding=5 \n";
    if(style==DEFAULT_STYLE) s=s+"  >\n";
    if(style==REPORT_STYLE) s=s+" align=center >\n";
    Field[] fds=doc.getFields();
    int width_count=0;
    int row=0;
     for(int i=0;i<fds.length;i++)
     {
         if(fds[i].isList()) 
               width_count=width_count+fds[i].getDataSize();       
     }  
     s=s+"<tr>\n";
       for(int i=0;i<fds.length;i++)
       {
           if(fds[i].isList())
           {
             s=s+"<th width=\""+(fds[i].getDataSize()*100)/width_count+"%\" class=OraTableRowHeader noWrap align=middle  >"+fds[i].getCaption()+"</th>\n";
           }
       }
          
     s=s+"</tr>\n";
       try
       {
       	conn=DBManager.getConnection();
              stmt=conn.createStatement();rs=stmt.executeQuery(sql);
             while(rs.next()&&row<row_count)
           {
               row++;
               s=s+"<tr>\n";
               for(int i=0;i<fds.length;i++)
               {
                   if(fds[i].isList())
                     s=s+"<td class=OraTableCellText noWrap align=middle >"+getCellData(fds[i].getName())+"</td>\n";
               }
               s=s+"</tr>\n";
           }
       }catch(Exception e)
       {
           e.printStackTrace();
       }finally
       {
           try
           {
               if(rs!=null) rs.close();
               if(stmt!=null) stmt.close();
               conn.close();
           }catch(Exception e)
           {
               e.printStackTrace();
           }
       }

     if(row==row_count)
     {
       try{
       	conn=DBManager.getConnection();
       stmt=conn.createStatement();rs=stmt.executeQuery("select count(*) count from "+sql_from);
       rs.next();
       s=s+"<tr><td colspan="+width_count+" align=\"left\" >一共有<u><b>"+rs.getString("count")+"</b></u>条记录行满足查询条件,超出最大返回行数，请缩小查询条件进行查询.</td></tr>\n";
       }catch(Exception e){
        e.printStackTrace();
       System.out.println(e);}finally
    {
        try
        {
            if(rs!=null) rs.close();
            if(stmt!=null) stmt.close();
            conn.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    };
      
     }
     s=s+"</table>\n"; 
     this.content=this.content+s;
     return row;
   }
   
   public void addContent(String html)
   {
     this.content=content+html;
   }

 public int getStyle()
 {
   return style;
 }

 public void setStyle(int style)
 {
   this.style = style;
 }
 
 public void addSubject(String subject)
 {
   String s="<table width=\""+this.width+"\" border=0 cellspacing=1 cellpadding=5 \n";
   if(style==DEFAULT_STYLE) s=s+"  >\n";
   if(style==REPORT_STYLE) s=s+" align=center >\n";
     s=s+"<tr>\n";
     s=s+"<td> <span class=\"OraHeader\">"+subject+"</span>\n";
     s=s+ "<table width=\"100%\" border=0 cellspacing=0 cellpadding=0 background=\"../crmjsp/images/headerlinepixel_onwhite.gif\" HEIGHT=\"1\" WIDTH=\"1\">\n";
   s=s+ "<tr background=\"../crmjsp/images/headerlinepixel_onwhite.gif\" HEIGHT=\"1\" WIDTH=\"1\">\n";
   s=s+"<td height=\"1\" width=100% background=\"../crmjsp/images/headerlinepixel_onwhite.gif\" HEIGHT=\"1\" WIDTH=\"1\"><img src=\"../crmjsp/images/headerlinepixel_onwhite.gif\" HEIGHT=\"1\" WIDTH=\"1\" ></td>\n";
     s=s+"</tr>\n";
   s=s+"</table>\n";
   s=s+"</td>\n";
     s=s+"</tr>\n";
     s=s+"</table>\n";
     
     content=content+s;
 }
 
 public void addSearchBar(int doc_type)
 {
       DocType doc=new DocType(doc_type);
       boolean has_key=false;
       String s="<br>\n<table width = 100% class=\"OraBGAccentDark\" cellpadding = \"0\" cellspacing = \"1\">\n";
       s=s+"<tr>\n";
       s=s+"      <td width = 100% class=\"OraBGAccentLight\">\n";
       s=s+"        <table>\n";
       s=s+"          <tr> <form method=\"POST\" action=\"../app/viewlist\" name=\"fm_add\" onsubmit=\"return querySubmit();\"> \n";  
         s=s+"  <input type=\"hidden\" name=\"doc_type\" value=\""+doc_type+"\">\n";
         s=s+"            <td NoWrap class=\"OraPromptText\" width=\"50%\" valign=\"middle\">\n";
       Field[] fds=doc.getFields();
       for(int i=0;i<fds.length;i++)
       {
         if(fds[i].isQuickQuery())
         {
             has_key=true;
             s=s+"&nbsp;&nbsp;&nbsp;"+fds[i].getCaption()+"&nbsp;\n";
             s=s+addSearchField(fds[i].getName());
         }
       }
       
       s=s+"             <input type=\"submit\" class=\"button5\" name=\"search\" value=\"查询\"></td>\n";
       s=s+"            <td NoWrap class=\"OraPromptText\" width=\"25%\" align=\"left\" valign=\"middle\"><img border=0 src=\"../crmjsp/images/prompt.gif\" >&nbsp;<a href=\"../app/viewquery?doc_type="+doc_type+"\"><font class=OraChicletText>高级查询</font></td>\n";
       //if(doc.isReadonly())
          s=s+"            <td NoWrap class=\"OraPromptText\" width=\"25%\" align=\"left\" valign=\"middle\"></td>\n";
       //else
       if(doc_type==22100){
       s=s+"            <td NoWrap class=\"OraPromptText\" width=\"25%\" align=\"left\" valign=\"middle\"><img border=0 src=\"../crmjsp/images/prompt.gif\" >&nbsp;<a href=\"../app/viewnew?doc_type="+doc_type+"\"><font class=OraChicletText>增加新"+doc.getDocName()+"</font></td>\n";
       }
       s=s+"        </form></tr>\n";
       s=s+"        </table>\n";
       s=s+"      </td>\n";
       s=s+"  </tr>\n";
       s=s+"    </table>\n";
       if(has_key)
         search_bar=s;
       else 
         search_bar="<br>";
 }
 
 public String getSearchStr() throws KException
 {
 
  DocType doc=new DocType(doc_type);   
  String s="";
  Field[] fds=doc.getFields();
  String para_value="";
  String value="";
  

  for(int i=0;i<fds.length;i++)
  {
//     System.out.println("NAME:"+fds[i].getName());
//     System.out.println("INPUT_TYPE:"+fds[i].getInputType());
//     System.out.println("DataType:"+fds[i].getDataType());
//     System.out.println("isQuery:"+fds[i].isQuery());

     if(fds[i].getInputType().equals("TEXT"))
     {
         if(fds[i].getDataType().equals("INTEGER"))
           {
               para_value=StringUtil.cEmpty(sessionInfo.getParameter(fds[i].getName()+"_from"));
               if(!StringUtil.isNum(para_value) &&!para_value.equals(""))
                       throw(new KException("字段\""+fds[i].getCaption()+"\"不是整型."));
               if(!para_value.equals("")) s=s+" and "+fds[i].getName()+" >="+para_value;             
               
               para_value=StringUtil.cEmpty(sessionInfo.getParameter(fds[i].getName()+"_to"));
               if(!StringUtil.isNum(para_value) &&!para_value.equals(""))
                       throw(new KException("字段\""+fds[i].getCaption()+"\"不是整型."));
               if(!para_value.equals("")) s=s+" and "+fds[i].getName()+" <="+para_value;
           }

          if(fds[i].getDataType().equals("FLOAT"))
          {
               para_value=StringUtil.cEmpty(sessionInfo.getParameter(fds[i].getName()+"_from"));
               if(!StringUtil.isNumEx(para_value)&&!para_value.equals(""))
                       throw(new KException("字段\""+fds[i].getCaption()+"\"不是数字型."));
               if(!para_value.equals("")) s=s+" and "+fds[i].getName()+" >="+para_value;             
               
               para_value=StringUtil.cEmpty(sessionInfo.getParameter(fds[i].getName()+"_to"));
               if(!StringUtil.isNumEx(para_value)&&!para_value.equals(""))
                       throw(new KException("字段\""+fds[i].getCaption()+"\"不是数字型."));
               if(!para_value.equals("")) s=s+" and "+fds[i].getName()+" <="+para_value;
          }

          if(fds[i].getDataType().equals("CHAR"))
           {
               para_value=StringUtil.cEmpty(sessionInfo.getParameter(fds[i].getName()));
               try{
                 para_value =para_value.trim();
               }catch(Exception e){ e.printStackTrace();}
               if(!para_value.equals("")) s=s+" and "+fds[i].getName()+" like '%"+para_value+"%'";
           }
     }

     if(fds[i].getInputType().equals("DATE"))
     {   
         para_value=StringUtil.cEmpty(sessionInfo.getParameter(fds[i].getName()+"_from"));
         if(!StringUtil.isDate(para_value)&&!para_value.equals(""))
                 throw(new KException("字段\""+fds[i].getCaption()+"\"不是有效的日期类型."));
         if(!para_value.equals("")) s=s+" and "+fds[i].getName()+" >=to_date('"+para_value+" 00:00','YYYY-MM-DD HH24:MI ')";
        
         para_value=StringUtil.cEmpty(sessionInfo.getParameter(fds[i].getName()+"_to"));
         if(!StringUtil.isDate(para_value)&&!para_value.equals(""))
                 throw(new KException("字段\""+fds[i].getCaption()+"\"不是有效的日期类型."));
         if(!para_value.equals("")) s=s+" and "+fds[i].getName()+" <=to_date('"+para_value+" 23:59','YYYY-MM-DD HH24:MI')";
     }

     if(fds[i].getInputType().equals("LOOKUP"))
     {
         para_value=StringUtil.cEmpty(sessionInfo.getParameter(fds[i].getName()));
         if(!para_value.equals("")) s=s+" and "+fds[i].getName()+" = '"+para_value+"'";
     }

     if(fds[i].getInputType().equals("LIST"))
     {
         para_value=StringUtil.cEmpty(sessionInfo.getParameter(fds[i].getName()));
         if(!para_value.equals("")) s=s+" and "+fds[i].getName()+" = '"+para_value+"'";
     }

     if(fds[i].getInputType().equals("TEXTAREA"))
     {
         para_value=StringUtil.cEmpty(sessionInfo.getParameter(fds[i].getName()));
         if(!para_value.equals("")) s=s+" and "+fds[i].getName()+" like '%"+para_value+"%'";
     }
     if(fds[i].getInputType().equals("RADIO"))
     {
         para_value=StringUtil.cEmpty(sessionInfo.getParameter(fds[i].getName()));
         if(!para_value.equals("")) s=s+" and "+fds[i].getName()+" = '"+para_value+"'";
     }
  
     if(fds[i].getInputType().equals("KEYSET"))
     {   
         para_value=StringUtil.cEmpty(sessionInfo.getParameter(fds[i].getName()));
         String key_value=StringUtil.cEmpty(sessionInfo.getParameter(fds[i].getName()+"_key"));
         para_value="";
         if(!key_value.equals(""))
         {
               Lookup lu=LookupMap.getLookup(fds[i].getLookup());
              Statement stmt1=null;
              ResultSet rs1=null;
               try
               {
               	conn=DBManager.getConnection();
                   stmt1=conn.createStatement();rs1= stmt1.executeQuery("select "+lu.getValueField()+" from "+lu.getDataSource()+" where "+lu.getKeyField()+"='"+key_value+"'");
                   if(rs1.next())
                       para_value=rs1.getString(1);
                   else
                      throw(new KException("字段\""+fds[i].getCaption()+"\"没有输入有效的代码."));
               }catch(Exception e)
               {
                 throw(new KException(e.getMessage()));
               }finally
               {
                   try
                   {
                       if(stmt1!=null) stmt1.close();
                       if(rs1!=null) rs1.close();
                       conn.close();
                   }catch(Exception e)
                   {
                       e.printStackTrace();
                   }
                   
               }
         }
         if(!para_value.equals("")) s=s+" and "+fds[i].getName()+" = "+para_value;
     }
  }

 boolean ordered =false;
 Vector sort=sessionInfo.getParameterValues("sort");
 if(sort!=null)
 {
     System.out.println(sort.size());
     Iterator it=sort.iterator();
     while(it.hasNext())
     {
       String st=(String)it.next();
       if(!st.trim().equals("")) 
       {
           if(ordered)
             s=s+","+st;
           else
           {
             s=s+" order by "+st;
             ordered=true;
           }
       }
     }
 }
   s = "1=1"+s;
   if(doc_type == doc.MEMBER && StringUtil.cEmpty(sessionInfo.getParameter("is_detail_query_form")).equals("1")){
     s = " id in (select id from vw_member_query where " + s +")";
   }    
   
   return s;
 }
 
 private String addFavoriteQueryList(int doc_type)
 {
   String s=" <table width=\"100%\" border=0 cellspacing=1 cellpadding=5 >\n";
   s=s+"<tr><th colspan=2 class=OraTableRowHeader noWrap align=middle> 收藏的查询列表</th>\n";
   try{
   	conn=DBManager.getConnection();
     stmt=conn.createStatement();rs= stmt.executeQuery("select * from bas_saved_queries where doc_type="+doc_type+" and company_id="+sessionInfo.getCompanyID()+" and ( operator_id="+sessionInfo.getOperatorID()+" or is_public=1)");
     while(rs.next())
     {
         s=s+"<tr>\n";
         s=s+"<form name=\"fm_"+rs.getInt("id")+"\" action=\"../app/viewlist\" method=\"post\">\n";
         s=s+"<input type=\"hidden\" name=\"query_sql\" value=\""+rs.getString("query_sql")+"\">\n";
         s=s+"<input type=\"hidden\" name=\"doc_type\" value=\""+doc_type+"\">\n";
         s=s+"<td class=OraTableCellText noWrap width=\"90%\" align=middle ><a href=\"javascript:fm_"+rs.getInt("id")+".submit();\">"+rs.getString("name")+"</a></td>\n";
         if(rs.getInt("operator_id")==sessionInfo.getOperatorID())
           s=s+"<td class=OraTableCellText noWrap width=\"10%\" align=middle ><a href=\"../app/ctrdelete?doc_type=10100&doc_id="+rs.getInt("id")+"\">删除</a></td>\n";
         else
           s=s+"<td class=OraTableCellText noWrap width=\"10%\" align=middle ></td>\n";
         s=s+"</form>\n";
         s=s+"</tr>\n";
     }
   }catch(Exception e)
   {
      e.printStackTrace();
     System.out.println(e);
   }finally
   {
    try
    {
        if(stmt!=null) stmt.close();
        if(rs!=null) rs.close();
        conn.close();
    }catch(Exception e)
    {
        e.printStackTrace();
    }
    
}
   s=s+"</table>\n";
   return s;
 }
 
 public String addSearchField(String field_name)
 {
     Field fd=FieldMap.getField(field_name);
     String s="";
     if(fd.getInputType().equals("TEXT"))
     {
         if(fd.getDataType().equals("INTEGER")||fd.getDataType().equals("FLOAT"))
            s=s+"<input name=\""+fd.getName()+"_from\" size =8 value=\"\"> 至<input name=\""+fd.getName()+"_to\" value=\"\" size =8>";
         else
           s=s+"<input name=\""+fd.getName()+"\" value=\"\">\n";
     }
     if(fd.getInputType().equals("DATE"))
     {
         s=s+"<input id=\""+fd.getName()+"_from\" name=\""+fd.getName()+"_from\" value=\"\"> <a href=\"javascript:calendar(fm_add."+fd.getName()+"_from)\"><img src=\"../crmjsp/images/icon_date.gif\" border=0 align=\"top\"><a>";
         s=s+"至<input id=\""+fd.getName()+"_to\" name=\""+fd.getName()+"_to\" value=\"\"> <a href=\"javascript:calendar(fm_add."+fd.getName()+"_to)\"><img src=\"../crmjsp/images/icon_date.gif\" border=0 align=\"top\"><a>(格式:YYYY-MM-DD)\n";
     }
    if(fd.getInputType().equals("LIST"))
     {
         Map map=LookupMap.getListValues(fd.getLookup(),this.sessionInfo.getCompanyID(),null);
         Object[] values=map.keySet().toArray();
         s=s+"<select id=\""+fd.getName()+"\" name=\""+fd.getName()+"\" > \n";
           s=s+"<option value=\"\">请选择...</option>\n";
         for(int in=0;in<values.length;in++)
                s=s+"<option value=\""+(String)values[in]+"\">"+(String)map.get(values[in])+"</option>\n";
         s=s+"</select>\n";
     }
     if(fd.getInputType().equals("TEXTAREA"))
     {
         s=s+"<textarea cols=30 rows=2 name=\""+fd.getName()+"\" value=\"\"></textarea>\n";
     }
     if(fd.getInputType().equals("RADIO"))
     {
         Map map=LookupMap.getListValues(fd.getLookup(),this.sessionInfo.getCompanyID(),null);
         Object[] values=map.keySet().toArray();
         for(int in=0;in<values.length;in++)
             s=s+"<input type='radio' name=\""+fd.getName()+"\" value=\""+(String)values[in]+"\">"+(String)map.get(values[in])+"&nbsp;\n";
         s=s+"</select>\n";
     }
     if(fd.getInputType().equals("LOOKUP"))
     {
         //判断传入的已知参数
         String value="";
         String key_value=""; 
         String key_description="";
         s=s+"<input type=\"hidden\" id=\""+fd.getName()+"\" name=\""+fd.getName()+"\" value=\""+value+"\"> ";
         s=s+"<input id=\""+fd.getName()+"_key\" name=\""+fd.getName()+"_key\" value=\""+key_value+"\" readonly onclick=\" select_item('"+fd.getName()+"',fm_add."+fd.getName()+",fm_add."+fd.getName()+"_key,"+fd.getName()+"_display);\">";
         s=s+"<a href=\"javascript:select_item('"+fd.getName()+"',fm_add."+fd.getName()+",fm_add."+fd.getName()+"_key,"+fd.getName()+"_display);\">";
         s=s+"<img src=\"../crmjsp/images/icon_lookup.gif\" border=0 align=\"top\"><a>\n";
         s=s+"&nbsp;<span id=\""+fd.getName()+"_display\" style=\"display:none\"  name=\""+fd.getName()+"_display\" >"+key_description+"</span>\n";
     }
     if(fd.getInputType().equals("KEYSET"))
     {   
         String value="";
         String key_value="";  
         String key_description="";
         s=s+"<input type=\"hidden\" id=\""+fd.getName()+"\" name=\""+fd.getName()+"\" value=\""+value+"\"> ";
         s=s+"<input id=\""+fd.getName()+"_key\" name=\""+fd.getName()+"_key\" value=\""+key_value+"\" \n";
         s=s+"> <a href=\"javascript:select_item('"+fd.getName()+"',fm_add."+fd.getName()+",fm_add."+fd.getName()+"_key,"+fd.getName()+"_display);\"><img src=\"../crmjsp/images/icon_lookup.gif\" border=0 align=\"top\"><a>\n";
         s=s+"&nbsp;<span id=\""+fd.getName()+"_display\" name=\""+fd.getName()+"_display\" >"+key_description+"</span>\n";
     }
       return s;
 }

 
 public void setMenuVisible(boolean menu_visible)
 {
   this.menu_visible=menu_visible;
 }
 private void close()
 {
      try
   {
       if(rs!=null) rs.close();
       if(stmt!=null) stmt.close();

   }catch(Exception e)
   {
       e.printStackTrace();
   }
 }

}
	