<%@ page contentType="text/html; charset=GBK"%>
<%@page import="com.magic.crm.member.entity.MembeMoneyHistory"%>
<%@page import="com.magic.utils.JxlBean"%>
<%@page import="java.util.*"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%
Collection memberMoneyExecl=(Collection)request.getAttribute("memberMoneyExecl");
%>
<%
//String card_id=request.getParameter("MB_CODE");
//String create_date=request.getParameter("CREATE_DATE");
//String pay_id=request.getParameter("payMethod");
String name="会员帐户记录";

		 response.reset();
		 response.setContentType("application/vnd.ms-excel");
		 String filename = new String(name.getBytes("gb2312"),"iso8859-1");
		 response.setHeader("Content-Disposition", "attachment; filename=" + filename + ".xls" + "");   

		JxlBean jxlbean=new JxlBean();
        jxlbean.create(response.getOutputStream());
		jxlbean.create_sheet("会员帐户记录",0);			    
		jxlbean.setRowHeigth(0,500);
		jxlbean.setColWidth(0,13);  
		jxlbean.setColWidth(1,13);
		jxlbean.setColWidth(2,13);
		jxlbean.setColWidth(3,13);
		jxlbean.setColWidth(4,13);		 
		jxlbean.setColWidth(5,13);	
		jxlbean.setColWidth(6,13);	
		jxlbean.setColWidth(7,13);
		

		//设置浮点数的格式
		jxl.write.WritableCellFormat cf2 = new jxl.write.WritableCellFormat(jxl.write.NumberFormats.FLOAT);
		jxlbean.setTitle("会员帐户记录",7);	
		//jxlbean.setHeader(from_to,1,6);
		String header="编号,事件日期,会员号码,会员姓名,事件类型,增加（减少）的钱款,经办人员,备注";
		jxlbean.setHeader(header,1);
			     
		int jxl_line=3;
%>


<%

double sum_amount = 0; 
	   Iterator it=memberMoneyExecl.iterator();
	   MembeMoneyHistory info=new MembeMoneyHistory();
while(it.hasNext()){
	   info=(MembeMoneyHistory)it.next();
		sum_amount = sum_amount + info.getMONEY_UPDATE();
	
		jxlbean.addCell(new jxl.write.Number(0,jxl_line,info.getID()));
		jxlbean.addCell(new jxl.write.Label(1,jxl_line,info.getMODIFY_DATE()));
		jxlbean.addCell(new jxl.write.Label(2,jxl_line,info.getCARD_ID()));
		jxlbean.addCell(new jxl.write.Label(3,jxl_line,info.getCARD_NAME()));
		jxlbean.addCell(new jxl.write.Label(4,jxl_line,info.getPayMethodName()));
		jxlbean.addCell(new jxl.write.Number(5,jxl_line,info.getMONEY_UPDATE()));
		//jxlbean.addCell(new jxl.write.Number(6,jxl_line,info.getDEPOSIT()));
		jxlbean.addCell(new jxl.write.Label(6,jxl_line,info.getOPERATOR_NAME()));
		jxlbean.addCell(new jxl.write.Label(7,jxl_line,info.getCOMMENTS()));
				

   jxl_line++;
} 



//总计
jxlbean.addCell(new jxl.write.Label(0,jxl_line,"合计"));
jxlbean.addCell(new jxl.write.Number(5,jxl_line,sum_amount));
jxlbean.addCell(new jxl.write.Label(6,jxl_line,"元"));
jxlbean.close();		
    
%>


