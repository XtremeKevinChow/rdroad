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
String name="��Ա�ʻ���¼";

		 response.reset();
		 response.setContentType("application/vnd.ms-excel");
		 String filename = new String(name.getBytes("gb2312"),"iso8859-1");
		 response.setHeader("Content-Disposition", "attachment; filename=" + filename + ".xls" + "");   

		JxlBean jxlbean=new JxlBean();
        jxlbean.create(response.getOutputStream());
		jxlbean.create_sheet("��Ա�ʻ���¼",0);			    
		jxlbean.setRowHeigth(0,500);
		jxlbean.setColWidth(0,13);  
		jxlbean.setColWidth(1,13);
		jxlbean.setColWidth(2,13);
		jxlbean.setColWidth(3,13);
		jxlbean.setColWidth(4,13);		 
		jxlbean.setColWidth(5,13);	
		jxlbean.setColWidth(6,13);	
		jxlbean.setColWidth(7,13);
		

		//���ø������ĸ�ʽ
		jxl.write.WritableCellFormat cf2 = new jxl.write.WritableCellFormat(jxl.write.NumberFormats.FLOAT);
		jxlbean.setTitle("��Ա�ʻ���¼",7);	
		//jxlbean.setHeader(from_to,1,6);
		String header="���,�¼�����,��Ա����,��Ա����,�¼�����,���ӣ����٣���Ǯ��,������Ա,��ע";
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



//�ܼ�
jxlbean.addCell(new jxl.write.Label(0,jxl_line,"�ϼ�"));
jxlbean.addCell(new jxl.write.Number(5,jxl_line,sum_amount));
jxlbean.addCell(new jxl.write.Label(6,jxl_line,"Ԫ"));
jxlbean.close();		
    
%>


