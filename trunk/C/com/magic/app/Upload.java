/*
 * Created on 2005-3-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.app;
import java.io.*;
import java.util.*;
import java.sql.SQLException;
import java.text.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.commons.fileupload.*;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.sql.Connection;
import com.magic.crm.util.DBManager;
import com.magic.crm.member.action.MemberEventListAction;
import com.magic.crm.member.dao.MemberaddMoneyDAO;

import com.magic.crm.member.entity.MemberaddMoney;
import com.magic.crm.user.entity.User;

/**
 * @author user1
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Upload extends HttpServlet {

	private Logger logerr = Logger.getLogger(Upload.class);	
public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
{
	HttpSession session=request.getSession(); 
    SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd" ); 
    Date now = new Date();
    String t = formatter.format(now);	
   
    
    String type=request.getParameter("type");
    int id=0;
    Connection conn=null;
    
    try { 
    	conn=DBManager.getConnection();
    	id=MemberaddMoneyDAO.getImportFileId(conn);
	} catch(SQLException se) {
	  	throw new ServletException(se);
	 } finally {
		 try {
			 conn.close();
		  } catch(SQLException sqe) {
			  throw new ServletException(sqe);
		  }

	 }  
	 
	 String fileName="";
	 String path=request.getRealPath("/");
	 System.out.println("path is "+path);
    try {
        DiskFileUpload fu = new DiskFileUpload();
        // ��������ļ��ߴ磬������4MB
        fu.setSizeMax(4194304);
        // ���û�������С��������4kb
        fu.setSizeThreshold(4096);
        // ������ʱĿ¼��
        //fu.setRepositoryPath(tempPath);

        // �õ����е��ļ���
        List fileItems = fu.parseRequest(request);
        Iterator i = fileItems.iterator();
        // ���δ���ÿһ���ļ���

        while(i.hasNext()) {
            FileItem fi = (FileItem)i.next();
            // ����ļ���������ļ�������·����
            fileName= fi.getName();
            if(fileName!=null) {
                // д���ļ�a.txt����Ҳ���Դ�fileName����ȡ�ļ�����
                fi.write(new File(path+"\\upload\\" +type+id+t+".txt"));      
                System.out.println("fileName is "+fileName);
            }
        }
    }catch(Exception e) {
       System.out.println("�ϴ��ļ�����");
       e.printStackTrace();
    }	
	/*
	   ��ȡ�ļ����������ݿ�MBR_MONEY_INPUT
	   
	*/
	User user=new User();
	user = (User)session.getAttribute("user");	
		MemberaddMoneyDAO  memberaddMoneyDAO=new MemberaddMoneyDAO();
		MemberaddMoney memberaddMoney=new MemberaddMoney();
	 	FileReader fr=new FileReader(new File(path+"\\upload\\" +type+id+t+".txt"));
	 	BufferedReader br=new BufferedReader(fr);
	 	String Line=br.readLine(); 		 	
	 	
try { 

 	conn=DBManager.getConnection();
 	String path1=request.getRealPath("."); 
 	FileWriter fw=new FileWriter(path1 + "\\errlog\\"+t+"errdata.txt");//����FileWriter���󣬲�ʵ����fw 
	while (Line!=null) {
 		
		String[] a = Line.split("\t");
		
		try{
				memberaddMoney.setMB_ID(0);
				memberaddMoney.setMB_CODE(a[2]);
				memberaddMoney.setORDER_ID(0);
		
				memberaddMoney.setORDER_CODE(a[4]);
				memberaddMoney.setREMARK(a[3]);
					
				memberaddMoney.setMONEY(Double.parseDouble(a[1]));
				
				memberaddMoney.setREF_ID(a[0]);
				memberaddMoney.setStatus(0);
				memberaddMoney.setOPERATOR_ID(Integer.parseInt(user.getId()));
				memberaddMoney.setTYPE(type);
				memberaddMoney.setPostCode(a[5]);
				memberaddMoney.setPostDate(a[6]);
                if(a[5].length() > 0)
                    memberaddMoneyDAO.insert(conn, memberaddMoney);
                else
                    fw.write(Line + '\r' + '\n');
		}catch(Exception e){
			
			//�Ѵ�������д���ļ�
 
			//PropertyConfigurator.configure("E:\\application\\jboss-3.2.5\\server\\default\\deploy\\99CRM.war\\WEB-INF\\classes\\log4j2.properties");
			//PropertyConfigurator.configure("D:\\jboss-3.2.5\\server\\default\\deploy\\99CRM.war\\WEB-INF\\classes\\log4j2.properties");
			//logerr.error("����δ����Ĵ����ֵ����"+Line+"<br>");
				//���ַ���д���ļ� 
			fw.write(Line + '\r' + '\n');
			
		}	
		
				Line=br.readLine();	

		}	
	fw.close();
		br.close();//�ر�BufferedReader����
		fr.close();//�ر��ļ�	
	
	}catch(SQLException x) {		
		x.printStackTrace();
	}finally {
		
		 try {
			 conn.close();
		  } catch(SQLException sqe) {
			 
		  }
		  
	 }

	 	response.sendRedirect("../member/memberqueryMoney.do?filename="+fileName+"&type="+type);

}
}


