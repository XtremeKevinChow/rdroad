/*
 * add by Floyd Jiang 2009.11.30
 */
package com.magic.crm.member.upload;

import com.magic.crm.util.Constants;
import com.magic.crm.util.Message;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import com.magic.utils.StringUtil;
import java.util.Date;
import java.util.Random;

import com.magic.utils.reader.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletConfig;

import org.apache.log4j.Logger;

import com.magic.crm.common.CharacterFormat;
import com.magic.crm.order.dao.OrderDAO;
import com.magic.crm.member.bo.MemberBO;
import com.magic.crm.member.dao.MemberDAO;
import com.magic.crm.member.dao.MemberAddressDAO;
import com.magic.crm.member.entity.Member;
import com.magic.crm.member.entity.MemberAddresses;
import com.magic.crm.user.entity.User;
import com.magic.crm.util.DBManager;
import java.util.HashMap;
import java.util.ArrayList;

public class TaobaoMemberUpload extends HttpServlet {
	/**
	 * 格式:
	 * 
	 * 订单编号	买家会员名	买家支付宝账号	收货人姓名	收货地址 
	 * 
	 */
	
	public final static long serialVersionUID = 54960818L;
	
	private static Logger logger = Logger.getLogger(TaobaoMemberUpload.class);
	
	
	/**
	 * the file import path key, configured in the web.xml
	 */
	private static final String IMPORT_PATH_KEY = "import";
	
	/**
	 * the file export path key, configured in the web.xml
	 */
	private static final String EXPORT_PATH_KEY = "export";

	/**
	 * default file import path
	 */
	private static final String DEFAULT_IMPORT_PATH = "D:\\";

	/**
	 * default file import path
	 */
	private static final String DEFAULT_EXPORT_PATH = "D:\\";
	
	/**
	 * common file import path
	 */
	private String importPath = null;
	
	/**
	 * common file export path
	 */
	private String exportPath = null;
	
	public void init(ServletConfig conf) {

		this.importPath = conf.getInitParameter(IMPORT_PATH_KEY);
		
		this.exportPath = conf.getInitParameter(EXPORT_PATH_KEY);
		
		if (importPath == null) {
			
			importPath = DEFAULT_IMPORT_PATH;
		}
		
		if (exportPath == null) {
			
			exportPath = DEFAULT_EXPORT_PATH;
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setCharacterEncoding("GBK");
		HttpSession session = request.getSession();
		
		HashMap errMap = null;
		
		HashMap readMap = null;
		
		HashMap sxkMap = null;
		
		String fileName = null;

		int id = 0;

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");

		String t = formatter.format(new Date());

		//String type = request.getParameter("type");
		
		String fileType = request.getParameter("filetype");
		
		Connection conn = null;

		ReadToolsIF readtool = null;
		try {
			
			readtool = ReadFactory.factory(Integer.parseInt(fileType));//工厂方法，产生文件读取器
			
		} catch (FileTypeException fte) {
			
			fte.getMessage();
			StringBuffer sb = new StringBuffer();
			sb.append("<html>");
			sb.append("<head>");
			sb.append("<link rel='stylesheet' href='css/style.css' type='text/css'>");
			sb.append("</head>");
			sb.append("<table width='100%' border='0' cellspacing='0' cellpadding='0'>");
		    sb.append("<tr>");
		    sb.append("<td width='21'>&nbsp;</td>");
		    sb.append("<td>");
			sb.append("<b><font color='838383'>当前位置</font></b><font color='838383'> : </font><font color='838383'>帐户管理</font><font color='838383'>"); 
			sb.append("-&gt; </font><font color='838383'>导入结果</font><font color='838383'>"); 
		    sb.append("</td> </tr></table>");
		    sb.append("<br><br>");
		    sb.append("<table width='90%' align='center' border='0' cellspacing='3' cellpadding='3'>");
		    sb.append("<tr>");
			sb.append("<td align='left'>  目前不支持这种格式文件导入。</td>");
			sb.append("<tr>");
			sb.append("<td><form action='/member/member_import_taobao.jsp'><input value='返回' type='submit'></form></td>");
			sb.append("</tr></table>");
			sb.append("</html>");
			response.getWriter().println(sb);
			return;
			
		}
		//文件格式:0_123456_2008-01-01
		fileName = "Taobao_"  + id + "" + t + "." + ReadFactory.FILE_MAP.get(fileType);
		
		java.io.File f = new java.io.File(importPath);
		if (!f.exists()) {
			f.mkdirs();
		}
		java.io.File f2 = new java.io.File(exportPath);
		if (!f2.exists()) {
			f2.mkdirs();
		}
		FileImport.fileUpload(request, importPath + fileName);//文件上传
		
		readtool.setFile(importPath + fileName);

		readtool.read();

		readMap = readtool.getData();//得到整个excel数据

		/*
		 * 读取文件并插入Member表里
		 */
		int j = 0, k = 0, m = 0;
		
		User user = new User();
		user = (User) session.getAttribute(Constants.USER_KEY);
		
		
		ArrayList list = new ArrayList();//error
		ArrayList list2 = new ArrayList();//sxk
		String[][] data = null;
		 StringBuffer strError = new StringBuffer();
			
		try {

			conn = DBManager.getConnection();

			if (readMap.get(ReadToolsIF.R_DATA) instanceof String[][]) {

				data = (String[][]) readMap.get(ReadToolsIF.R_DATA);//从map中取出二维矩阵
				if (data == null) {
					logger.error("null file ");
					return;
				}
				
				// step1  检查格式
				if (data[0].length < 7)
				{
					StringBuffer sb = new StringBuffer();
					sb.append("<html>");
					sb.append("<head>");
					sb.append("<link rel='stylesheet' href='css/style.css' type='text/css'>");
					sb.append("</head>");
					sb.append("<table width='100%' border='0' cellspacing='0' cellpadding='0'>");
				    sb.append("<tr>");
				    sb.append("<td width='21'>&nbsp;</td>");
				    sb.append("<td>");
					//sb.append("<b><font color='838383'>当前位置</font></b><font color='838383'> : </font><font color='838383'>帐户管理</font><font color='838383'>"); 
					sb.append("-&gt; </font><font color='838383'>导入结果</font><font color='838383'>"); 
				    sb.append("</td> </tr></table>");
				    sb.append("<br><br>");
				    sb.append("<table width='90%' align='center' border='0' cellspacing='3' cellpadding='3'>");
				    sb.append("<tr>");
					sb.append("<td align='left'>请注意格式：淘宝导入数据格式。</td>");
					sb.append("<tr>");
					sb.append("<td><form action='/member/member_import_taobao.jsp'><input value='返回' type='submit'></form></td>");
					sb.append("</html>");
					response.getWriter().println(sb);
					
					java.io.File f3 = new java.io.File(importPath + fileName);
					
					boolean b=f3.delete();
					logger.info("delete file : " +b);
					return;
				}	
					
				
				
				/* step2 数据处理
				 * 插入数据到mbr_member
				 */
				String condition = "";
				MemberDAO memberDAO = new MemberDAO();
				Member member = new Member();
				 MemberBO memberBO = new MemberBO();//member业务实体
				 MemberAddresses memberAddr = new MemberAddresses();
				 int indexNickName = 1;
					int indexTaobaoId = 2;
					int indexName = 3;
					int indexAddress = 4;
					int indexTelephone = 5;
					int indexMobile = 6;
				for (int i = 1; i < data.length; i++) {
					
					try {
						//* 淘宝旺旺号,会员姓名，电话，地址，邮编,email
						// 订单编号	买家会员名	买家支付宝账号	收货人姓名	收货地址 ,联系电话 	联系手机

						
						member.setTaobaoWangId(data[i][indexTaobaoId]);
						member.setNAME(data[i][indexName]);
						member.setTELEPHONE(data[i][indexMobile]);
						member.setFAMILY_PHONE(data[i][indexTelephone]);
						member.setAddress(data[i][indexAddress]);
						member.setAddressDetail(data[i][indexAddress]);
						member.setPostcode(" ");
						if(data[i][indexTaobaoId].indexOf("@")>0)
							member.setEMAIL(data[i][indexTaobaoId]);
						else
							member.setEMAIL(" ");
						member.setMSC_CODE("taobao");
						member.setBIRTHDAY("2000-01-01");
						member.setGENDER("0");
						member.setCOMMENTS(data[i][indexNickName]);
						
						memberAddr.setDelivery_address(data[i][indexAddress]);						
						memberAddr.setMember_ID(0);
						memberAddr.setTelephone(data[i][indexTelephone]);
						memberAddr.setTelephone2(data[i][indexMobile]);
						memberAddr.setRelation_person(data[i][indexName]);						
						memberAddr.setPostcode(" ");
						memberAddr.setSection("");
						
						
						 condition =  " and taobaowang_id='" + member.getTaobaoWangId() + "'";
						 		                    
		            if (memberDAO.checkMembers(conn, condition)) {
		            	logger.info("重复淘宝旺旺号："+ member.getTaobaoWangId());
		            	strError.append("重复淘宝旺旺号：" + member.getTaobaoWangId());
		            	strError.append("<br/>");
		            	j++;
		            	continue;
		            }
		            //System.out.println("准备插入，淘宝旺旺号："+ member.getTaobaoWangId());
					
		            
		            /*********/
		          //** 得到会员seq **/
		            int memberId = memberDAO.getMemberSEQ(conn);

		            //** 得到会员地址seq **/
		            int addressesId = memberDAO.getMemberAddressSEQ(conn);

		            String cardid = memberDAO.getMBCardIDSEQ(conn); 
		            
		            //** 插入会员表 **
		            member.setID(memberId);
		            member.setCARD_ID(cardid);
		            member.setADDRESS_ID(String.valueOf(addressesId));
		            member.setIS_ORGANIZATION("0");
		            
		            //System.out.println("插入前，会员ID："+ member.getID());
		            memberDAO.insert(conn, member);
		            
		            //System.out.println("成功插入，会员ID："+ member.getID());
					
		       		            
		            //** 插入会员地址和邮编到会员地址表MBR_ADDRESSES **/
		            memberAddr.setMember_ID(memberId);
		            memberAddr.setID(addressesId);
		            memberAddr.setRelation_person(member.getNAME());
		                     
		            
		            MemberAddressDAO memberAddressDAO = new MemberAddressDAO();
		            memberAddressDAO.insert(conn, memberAddr);
		            
		            //System.out.println("成功插入，会员地址："+ memberAddr.getID());
					strError.append("成功导入，淘宝旺旺号："+ member.getTaobaoWangId());
					strError.append("<br/>");
		            /*********/

		            m++;		            
		     					
					} catch (Exception e) {
						list.add(data[i]);//收集不合法的行
						strError.append("淘宝旺旺号："+ member.getTaobaoWangId()+"，导入失败");
						strError.append("<br/>");
						System.out.println("淘宝旺旺号:"+ member.getTaobaoWangId()+ "，错误："+e.toString() + e.getLocalizedMessage());
						j ++;
						
					}
				}
			} else {
				logger.error("type change error");
			}
			errMap = new HashMap();
			errMap.put(ReadToolsIF.W_DATA, list);
			if (list.size() > 0) {
				readtool.setErrorFile(exportPath + "error_"
						+ fileName);
				readtool.write(errMap, data[0].length);//生成不合法xls文件
			}
			sxkMap = new HashMap();
			sxkMap.put(ReadToolsIF.W_DATA, list2);
			if (list2.size() > 0) {
				readtool.setErrorFile(exportPath + "sxk_" + fileName);
				readtool.write(sxkMap, data[0].length);//书香卡法xls文件
			}
		} catch (SQLException e) {
			
			logger.error("database connect error");
			
		}finally {
			try {
				if ( conn!= null )
					conn.close();
			}catch(SQLException se){
				logger.error("database colose error");
			}
		}
		logger.info("write error data into excel succeed!");
		
		String errUrl = request.getContextPath() + "/upload/export/"+ "error_" + fileName;
		String sxkUrl = request.getContextPath() + "/upload/export/"+ "sxk_" + fileName;
		StringBuffer sb = new StringBuffer();
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<link rel='stylesheet' href='css/style.css' type='text/css'>");
		sb.append("</head>");
		sb.append("<table width='100%' border='0' cellspacing='2' cellpadding='2'>");
	    sb.append("<tr>");
	    sb.append("<td width='21'>&nbsp;</td>");
	    sb.append("<td>");
		//sb.append("<b><font color='838383'>当前位置</font></b><font color='838383'> : </font><font color='838383'>帐户管理</font><font color='838383'>"); 
		sb.append("-&gt; </font><font color='838383'>导入结果</font><font color='838383'>"); 
	    sb.append("</td> </tr></table>");
	    sb.append("<br><br>");
	    sb.append("<table width='90%' align='center' border='0' cellspacing='3' cellpadding='3'>");
	    sb.append("<tr>");
		sb.append("<td align='left'>文件导入成功</td>");
		sb.append("<tr>");
		sb.append("<td>");
		sb.append("插入成功: " + m + "条<br>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td>");
		sb.append("插入失败: " + j + "条<br>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td>详细：");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td>");
		sb.append(strError);
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td>");		
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td><a href='member_import_taobao.jsp'>返回</a>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("</table>");
		sb.append("</html>");
		
		response.getWriter().println(sb);
			//response.sendRedirect("../member/memberqueryMoney.do?filename="+fileName+"&type="+type);
		//}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		this.doPost(request, response);
	}

	public void destory() {

		super.destroy();

	}
	
	/**
	 * 定义一个小方法，产生随机数
	 * 如果存在表示已经导入过了，如果没有，表示没有到导入过
	 * @param max
	 * @return
	 */
	private int numRandom(int max) {
		Random rd = new Random();
		
		return rd.nextInt(max);
	}
	
	
	
}