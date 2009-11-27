/*
 * Created on 2005-4-3
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.util;

/**
 * @author user1
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
import java.io.*;
import javax.servlet.*;
import org.apache.log4j.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class Log4jInit extends HttpServlet {
 public void init() throws ServletException {
  String prefix = getServletContext().getRealPath("/");
  String file = getServletConfig().getInitParameter("log4j");
  System.out.println("log4j is start");
  System.out.println("log4j is "+prefix + file);
  // 从Servlet参数读取log4j的配置文件 
  if (file != null) {
   PropertyConfigurator.configure(prefix + file);
  }
 }
 public void doGet(HttpServletRequest request,HttpServletResponse response)throws 
IOException, ServletException {}
 public void doPost(HttpServletRequest request,HttpServletResponse response)throws 
IOException, ServletException {}
}
