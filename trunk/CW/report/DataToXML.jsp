<%@ page contentType="text/html; charset=GBK" %> 
<%@ page import="org.jdom.*,org.jdom.output.*"%> 
<%@page import="java.util.*"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<% 
out.println("ss");
Document doc=new Document(new Element("CommodityInfo"));
Element ChannelInfo=new Element("ChannelInfo");//创建元素 生成JDOM树 
doc.getRootElement().addContent(ChannelInfo); 

Element element3=new Element("Property").setText("<![CDATA[6356]]>"); 
element3.setAttribute("field","store_id"); 
element3.setAttribute("isvalue","0"); 

Element element4=new Element("Property").setText("<![CDATA[0]]>"); 
element4.setAttribute("field","robot_info_id"); 
element4.setAttribute("isvalue","0"); 

Element element5=new Element("Property").setText("<![CDATA[1]]>"); 
element5.setAttribute("field","is_have_type_info"); 
element5.setAttribute("isvalue","0"); 

Element element6=new Element("Property").setText("<![CDATA[0]]>"); 
element6.setAttribute("field","is_refer"); 
element6.setAttribute("isvalue","0"); 

ChannelInfo.addContent(element3); 
ChannelInfo.addContent(element4); 
ChannelInfo.addContent(element5); 
ChannelInfo.addContent(element6); 

Element CommodityList=new Element("CommodityList");//创建元素 生成JDOM树 
doc.getRootElement().addContent(CommodityList); 
			Connection conn=null;
			String sxml="";
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      
		try{
			 conn = DBManager.getConnection();
			String sql="select distinct a.name,a.item_id,a.isbn,c.common_price from prd_items a,sto_stock b,prd_pricelist_lines c ";
			sql+=" where a.item_type=1 and a.item_id=b.item_id and c.item_id=a.item_id and c.pricelist_id=0  and b.use_qty>0 ";
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
					while(rs.next()){ 
					Element Commodity=new Element("Commodity"); 
					CommodityList.addContent(Commodity); 
						Element Property1=new Element("Property").setText("<![CDATA[http://www.99read.com/shopstreet/product.asp?gdsid="+rs.getString("item_id")+"]]>"); 
						Property1.setAttribute("field","info_url"); 
						Property1.setAttribute("isvalue","1"); 	
						
						Element Property2=new Element("Property").setText("<![CDATA["+rs.getString("common_price")+"]]>"); 
						Property2.setAttribute("field","info_price"); 
						Property2.setAttribute("isvalue","1"); 	
						
						Element Property3=new Element("Property").setText("<![CDATA["+rs.getString("name")+"]]>"); 
						Property3.setAttribute("field","info_name_ori"); 
						Property3.setAttribute("isvalue","1"); 
						
						Element Property4=new Element("Property").setText("<![CDATA[1]]>"); 
						Property4.setAttribute("field","position"); 
						Property4.setAttribute("isvalue","1"); 	
								
						Element Property5=new Element("Property").setText("<![CDATA[1]]>"); 
						Property5.setAttribute("field","channel_type"); 
						Property5.setAttribute("isvalue","0"); 		
						Element Property6=new Element("Property").setText("<![CDATA["+rs.getString("isbn")+"]]>"); 
						Property6.setAttribute("field","ISBN"); 
						Property6.setAttribute("isvalue","0"); 								
						Commodity.addContent(Property1); 
						Commodity.addContent(Property2);
						Commodity.addContent(Property3);
						Commodity.addContent(Property4);
						Commodity.addContent(Property5);
						Commodity.addContent(Property6);
					
					} 
} catch(Exception se) {

			se.printStackTrace();
	
		 } finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {}			
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {}
			 try {
				 conn.close();
			 	} catch(SQLException sqe) {}
	
		 }
Format format = Format.getCompactFormat(); 

format.setEncoding("GBK"); //设置xml文件的字符为gb2312 

format.setIndent(" "); //设置xml文件的缩进为4个空格 



XMLOutputter XMLOut = new XMLOutputter(format);//在元素后换行，每一层元素缩排四格 

XMLOut.output(doc, new FileOutputStream("E:\\99book.xml")); 


%> 