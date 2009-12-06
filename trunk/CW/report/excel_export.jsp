<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="com.magic.crm.util.*,java.sql.*,java.io.*,java.util.*,com.magic.utils.*,jxl.write.*"%>
<%	 
 		 
 		 /*System.out.println("---------------------------------");
		 Enumeration en = request.getSession().getAttributeNames();
			while(en.hasMoreElements()) {
			    String tag = (String) en.nextElement();
				System.out.println(tag);
				System.out.println(request.getSession().getAttribute(tag));
			}
		 System.out.println("---------------------------------");*/
		 String name=(String)request.getSession().getAttribute("excel_name");
		 String title=(String)request.getSession().getAttribute("excel_title");
		 String sql=(String)request.getSession().getAttribute("excel_sql");
		 /*System.out.println(name);
         System.out.println(title);
         System.out.println(sql);
         System.out.println("---------------------------------");*/

		 response.reset();
		 response.setContentType("application/vnd.ms-excel");
		 String filename = new String(name.getBytes("gb2312"),"iso8859-1");
		 response.setHeader("Content-Disposition", "attachment; filename=" + filename + ".xls" + "");   
		
		String[] cols = title.split(",");

		JxlBean jxlbean=new JxlBean();
        jxlbean.create(response.getOutputStream());
		jxlbean.create_sheet(name,0);			    
		jxlbean.setRowHeigth(0,500);
		for(int i=0;i<cols.length;i++) {
		    jxlbean.setColWidth(i,13);
		}
		
        jxlbean.setTitle(name,cols.length);	
		//jxlbean.setHeader(from_to,1,6);
		jxlbean.setHeader(title,1);		

		Connection conn = null;
        try {
            conn = DBManager.getConnection();
	       Statement stmt=conn.createStatement();
	       ResultSet rs=stmt.executeQuery(sql);
	       int i=2;
	       while(rs.next()){
	            for(int j=0;j<cols.length;j++) {
		            jxlbean.addCell(new Label(j,i,rs.getString(j+1)));
		        }
		        i++;
		    }
	        rs.close();
	        stmt.close();
	        jxlbean.close();
	    } catch(Exception e ) {
	        e.printStackTrace();
	    } finally {
	        conn.close();
	    }

%>
