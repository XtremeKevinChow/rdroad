<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="java.io.*"%>
<%@ page import="jxl.*"%>
<%@page import="java.util.*"%>
<%@page import="java.sql.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<%@ page import="com.magic.utils.MultipartFormData"%>
<%
      Connection conn=null;
      PreparedStatement pstmt=null;
      String condition="";
      String sql="";
 	int login_company_id=0;
	String session_id=(String)session.getAttribute("session_id");
	int power_id = 0 ;
	HashMap power_map = (HashMap)session.getAttribute("powermap");
	request.setCharacterEncoding("GBK");
	//用来产生今天的日期
	java.text.SimpleDateFormat pdf_df=new java.text.SimpleDateFormat("yyyy-MM-dd");
    java.util.Date pdf_Date=new java.util.Date();
	pdf_Date.setTime(pdf_Date.getTime()-86400000);
	String pdf_yesterday=pdf_df.format(pdf_Date);

	int count=0;
  String fileName="";
	// Initialization
  MultipartFormData formData;
  formData = new MultipartFormData(request);
  String origFileName = formData.getLocalPath("fileName");

	long origFileSize = formData.getFileSize("fileName");
	String origFileType = formData.getLocalPath("fileName");

	int doc_type=Integer.parseInt(request.getParameter("doc_type"));
	int doc_id=Integer.parseInt(request.getParameter("doc_id"));
try{
  conn = DBManager.getConnection();  
			  
			String filename="catalog.xls";
		if(formData.writeFile("fileName", filename)){

      InputStream is = new FileInputStream(formData.getAttachmentDirName()+"\\"+filename); 
			
      jxl.Workbook rwb = Workbook.getWorkbook(is); 
      Sheet rs = rwb.getSheet(0); 
			int columns = rs.getColumns();
      int rows = rs.getRows();
      String colContents = "test";
			//System.out.println("columns="+columns+"       rows="+rows);
			String uploadData = "";
			int i=0;
			int j=0;
			if(rows!=0 && columns!=0){
       for(i=1;i<rows;i++){
				 for(j=0;j<columns;j++){
					 
					 Cell c00 = rs.getCell(j, i);
           if((c00.getContents().trim()).equals("")){
             colContents = "";
             continue;
           }
					 uploadData =uploadData + c00.getContents() + ",";
				 }
         if(colContents.equals("")){
           colContents = "test";
           break;
         }
				 uploadData = uploadData + "|";
			 }
			}
			//System.out.println(uploadData);
			rwb.close();
			int para_index=1;
      
			if(!uploadData.equals("")){
				 CallableStatement cstmt = null;
				 if(doc_type==1680){
			    cstmt = conn.prepareCall("{?=call catalogs.F_PRICELIST_LINES_UPLOAD_ADD(?,?,?,?,?,?)}"); 
				 } else if(doc_type==1510){
          //cstmt = conn.prepareCall("{?=call catalog.f_catalog_lines_upload_add(?,?,?,?,?,?)}");  
         } else {
					//cstmt = conn.prepareCall("{?=call catalog.F_PRICELIST_UPLOAD_ADD(?,?,?,?,?,?)}"); 
				 }
			   cstmt.setLong(2,doc_id); 
			   cstmt.setString(3,uploadData);
			   cstmt.setInt(4,doc_type);
			   cstmt.setString(5,session.getId());
			   cstmt.setString(6,"fileName");
			   cstmt.setInt(7,login_company_id);
			   cstmt.registerOutParameter(1,java.sql.Types.INTEGER);
			   cstmt.execute();
			   int inbound_id=cstmt.getShort(1);
			   if(inbound_id<0) {
			     session.setAttribute("showMsgContent","未完成");
			     session.setAttribute("showMsgTitle","未完成");
			     response.sendRedirect("message.jsp");
	
			     return;
				 } else {
			     response.sendRedirect("data_upload.jsp?doc_type="+doc_type+"&pricelist_id="+doc_id+"&parent_doc_id="+doc_id+"&inbound_id="+inbound_id);
			     return;
				 }
			} else {
        		    session.setAttribute("showMsgContent","未完成");
			    session.setAttribute("showMsgTitle","未完成");
			    response.sendRedirect("message.jsp");
			    return;
			}
		}
		else{
			session.setAttribute("showMsgContent","请选择一个上传文件.");
			session.setAttribute("showMsgTitle","未完成");
			response.sendRedirect("message.jsp");
			return;
		}

} catch(Exception se) {

	se.printStackTrace();

 } finally {
			

	 try {
		 conn.close();
	 	} catch(SQLException sqe) {}

 }	
%>