<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.*,java.sql.*,com.magic.crm.user.entity.User,com.magic.crm.user.dao.UserDAO,java.io.*"%>
<%
response.setContentType("text/xml; charset=gbk");   
response.setHeader("Cache-Control","no-cache"); 
out.println("<?xml  version=\"1.0\"  encoding=\"gbk\"?>"); 

String  userid = request.getParameter("id");
String pwd = request.getParameter("pwd");

User user = new User();
user.setUSERID(userid);
 
Connection conn=null;

try {
    conn = DBManager.getConnection();
    user = new UserDAO().findByName(conn, user);
    if (user==null) {
        out.println("<TopCTI><Login result=\"False\">用户名不存在</Login></TopCTI>");
        return;
    }
    System.out.println(pwd);
    MD5 m= new MD5();
    String newpwd=m.getMD5ofStr(pwd);
    System.out.println(newpwd);
    if(pwd==null||!newpwd.equals(user.getPWD())) {
         out.println("<TopCTI><Login result=\"False\">密码错误</Login></TopCTI>");
         
    } else {
       out.println("<TopCTI>");
       out.println("<Login result=\"OK\">Welcome</Login>");
       out.println("<Telephony>");
       out.println("<Host>" + "192.168.0.2" + "</Host>"); //根据需要修改访问地址和端口
       out.println("<Port>" + "6001" + "</Port>"); //
       out.println("</Telephony>");
       out.println("<Page>");
	out.println("<Menu></Menu>");
       //out.println("<Menu>TCMenu.aspx?source=1&amp;userid=" + user + "&amp;id=" + user + "&amp;pwd=" + pwd + "</Menu>");
      // out.println("<Navi>Navi.aspx</Navi>");
       out.println("<Ring>index.jsp?service=1&amp;isquery=1&amp;tel=@Caller&amp;callid=@CallID</Ring>");
       out.println("</Page>");
       out.println("</TopCTI>");
       
    }
    
    	
} catch(Exception e) {
    out.println("<TopCTI><Login result=\"False\">未知错误" + e.getMessage() + "</Login></TopCTI>");
} finally {
    
    try{conn.close();}catch(Exception e1){}
}


%>