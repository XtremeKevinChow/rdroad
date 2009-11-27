/*
 * Created on 2005-11-8
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.member.upload;

import com.magic.crm.util.Constants;

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
import com.magic.crm.member.dao.MemberDAO;
import com.magic.crm.member.dao.MemberaddMoneyDAO;
import com.magic.crm.member.entity.Member;
import com.magic.crm.member.entity.MemberaddMoney;
import com.magic.crm.user.entity.User;
import com.magic.crm.util.DBManager;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * @author user
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Upload extends HttpServlet {
	/**
	 * 大综单、加密单格式:
	 * 汇号*     金额*	 会员姓名 	     地址	 备注	   邮编*	    单据日期*
	 * 
	 */
	/**
	 * 现金、回单格式
	 * 汇号	    金额*	   会员姓名	   备注	   付款方式*    单据日期*

	 */
	public final static long serialVersionUID = 54960808L;
	
	private static Logger logger = Logger.getLogger(Upload.class);
	
	final static int COL_YJ_DZD = 7; //邮局大综单列数
	
	final static int COL_YJ_JMD = 7; //邮局加密单列数
	
	final static int COL_XJ = 6; //线下现金(回单)列数
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
	private static final String DEFAULT_IMPORT_PATH = "D:\\jboss-3.2.5\\server\\default\\deploy\\99CRM.war\\import\\";

	/**
	 * default file import path
	 */
	private static final String DEFAULT_EXPORT_PATH = "D:\\jboss-3.2.5\\server\\default\\deploy\\99CRM.war\\export\\";
	
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

		int id;

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");

		String t = formatter.format(new Date());

		String type = request.getParameter("type");
		
		String fileType = request.getParameter("filetype");
		
		Connection conn = null;

		try {

			conn = DBManager.getConnection();

			id = MemberaddMoneyDAO.getImportFileId(conn);

		} catch (SQLException se) {

			throw new ServletException(se);

		} finally {

			try {

				conn.close();

			} catch (SQLException sqe) {

				throw new ServletException(sqe);
			}

		}
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
			sb.append("-&gt; </font><font color='838383'>汇款导入结果</font><font color='838383'>"); 
		    sb.append("</td> </tr></table>");
		    sb.append("<br><br>");
		    sb.append("<table width='90%' align='center' border='0' cellspacing='3' cellpadding='3'>");
		    sb.append("<tr>");
			sb.append("<td align='left'>  目前不支持这种格式文件导入。</td>");
			sb.append("<tr>");
			sb.append("<td><form action='/member/member_addmoney_file.jsp'><input value='返回' type='submit'></form></td>");
			sb.append("</tr></table>");
			sb.append("</html>");
			response.getWriter().println(sb);
			return;
			
		}
		//文件格式:0_123456_2008-01-01
		fileName = type + "_" + id + "" + t + "." + ReadFactory.FILE_MAP.get(fileType);
		
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
		 * 读取文件并插入数据库MBR_MONEY_INPUT
		 */
		int j = 0, k = 0, m = 0;
		
		User user = new User();
		user = (User) session.getAttribute(Constants.USER_KEY);
		MemberaddMoneyDAO memberaddMoneyDAO = new MemberaddMoneyDAO();
		ArrayList list = new ArrayList();//error
		ArrayList list2 = new ArrayList();//sxk
		String[][] data = null;
		try {

			conn = DBManager.getConnection();

			if (readMap.get(ReadToolsIF.R_DATA) instanceof String[][]) {

				data = (String[][]) readMap.get(ReadToolsIF.R_DATA);//从map中取出二维矩阵
				if (data == null) {
					logger.error("null file ");
					return;
				}
				
				// step1  检查格式
				
				if (  ( type.equals("0") && data[0].length != COL_YJ_DZD ) //大综单
						|| (type.equals("2") && data[0].length != COL_YJ_JMD) //加密单
						|| (type.equals("3") && data[0].length != COL_XJ) //现金
						) {
					
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
					sb.append("-&gt; </font><font color='838383'>汇款导入结果</font><font color='838383'>"); 
				    sb.append("</td> </tr></table>");
				    sb.append("<br><br>");
				    sb.append("<table width='90%' align='center' border='0' cellspacing='3' cellpadding='3'>");
				    sb.append("<tr>");
					sb.append("<td align='left'>请注意格式：大综单是"+COL_YJ_DZD+"列；加密单是"+COL_YJ_JMD+"列；现金是"+COL_XJ+"列。</td>");
					sb.append("<tr>");
					sb.append("<td><form action='/member/member_addmoney_file.jsp'><input value='返回' type='submit'></form></td>");
					sb.append("</html>");
					response.getWriter().println(sb);
					
					java.io.File f3 = new java.io.File(importPath + fileName);
					
					boolean b=f3.delete();
					logger.info("delete file : " +b);
					return;
				}
				
				/* step2 检查重复性
				 * 如果是大综单、加密单在插入数据之前从map中随机挑出5条数据来检测看是否是文件重复导入
				 */
				if (type.equals("0") || type.equals("2")) {
					int num = 0;
					boolean isdup = false;
					for (int i = 0; i < 5; i ++) {
						if (data.length > 1) {
							num = this.numRandom(data.length - 1);
						}
						
						if (data[num][0] == null || data[num][0].trim().length() == 0) { //大综单、加密单检查汇号不存在
							i --;
							continue;
						}
						System.out.println("汇号：" + data[num][0]);
						isdup = memberaddMoneyDAO.checkIsDupImport(conn, data[num][0] == null? null : data[num][0].trim());
						if (isdup) {
							logger.info("文件重复导入");
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
							sb.append("-&gt; </font><font color='838383'>汇款导入结果</font><font color='838383'>"); 
						    sb.append("</td> </tr></table>");
						    sb.append("<br><br>");
						    sb.append("<table width='90%' align='center' border='0' cellspacing='3' cellpadding='3'>");
						    sb.append("<tr>");
							sb.append("<td align='left'>本批汇款数据已经导入系统，不能再次导入。</td>");
							sb.append("<tr>");
							sb.append("<td><form action='/member/member_addmoney_file.jsp'><input value='返回' type='submit'></form></td>");
							sb.append("</html>");
							response.getWriter().println(sb);
							
							java.io.File f3 = new java.io.File(importPath + fileName);
							
							boolean b=f3.delete();
							logger.info("delete file : " +b);
							return;
						}
					}
				}
				
				
				/* step3 数据处理
				 * 插入数据到mbr_money_input
				 */
				MemberaddMoney memberaddMoney = new MemberaddMoney();
				for (int i = 1; i < data.length; i++) {
					
					try {
						
						/*
						 * pay_method（付款方式）的确定
						 *
						 */
						if (type.equals("0") || type.equals("2")) { //大综单、加密单
							if (data[i][0] == null 
									|| data[i][0].trim().length() == 0 
									|| !StringUtil.isNum(data[i][0].trim())) {//没有汇号或者汇号不是数字的纪录不做任何处理
								list.add(data[i]);
								continue;
							}
							memberaddMoney.setREF_ID(data[i][0]==null?null:data[i][0].trim()); //邮局汇号（唯一）
							memberaddMoney.setMONEY(Double.parseDouble(data[i][1]==null?null:data[i][1].trim())); //汇款金额
							memberaddMoney.setMB_NAME(data[i][2]==null?null:data[i][2].trim());//姓名
							memberaddMoney.setADDRESS(data[i][3]==null?null:data[i][3].trim());//地址
							memberaddMoney.setREMARK(data[i][4]==null?null:data[i][4].trim());//备注
							memberaddMoney.setPostCode(data[i][5]==null?null:data[i][5].trim());//邮编
							memberaddMoney.setBill_date(data[i][6]==null?null:data[i][6].trim());//单据日期
							memberaddMoney.setPayMethod(6);//支付方式
						}
						
						else if (type.equals("3")) {// 现金回单格式
							memberaddMoney.setREF_ID(data[i][0]==null?null:data[i][0].trim());//单据号（可能为空）
							memberaddMoney.setMONEY(Double.parseDouble(data[i][1]==null?null:data[i][1].trim()));//汇款金额
							memberaddMoney.setMB_NAME(data[i][2]==null?null:data[i][2].trim());//姓名
							memberaddMoney.setREMARK(data[i][3]==null?null:data[i][3].trim());//备注
							// modify by zhux 20080727 change paymethod to 现金支付
							// memberaddMoney.setPayMethod(Integer.parseInt(data[i][4]==null?null:data[i][4].trim()));//支付方式
							memberaddMoney.setPayMethod(14);
							
							memberaddMoney.setBill_date(data[i][5]==null?null:data[i][5].trim());//单据日期
							
						}
						memberaddMoney.setMB_ID(0);
						memberaddMoney.setORDER_ID(0);
						memberaddMoney.setStatus(0);
						memberaddMoney.setOPERATOR_ID(Integer.parseInt(user
								.getId()));
						memberaddMoney.setTYPE(type);
						
						//step 4 -----------分析提取会员号、订单号--------------------------------------------
						setMbCodeAndOrderCode(memberaddMoney, conn);
						//-------------------------------------------------------------------------
						
					
						/* step5 数据插入
						 * use_type字段设置为0（预存款）
						 */
						memberaddMoney.setUSE_TYPE("0");
						memberaddMoneyDAO.insert(conn, memberaddMoney);
						m ++;
					
						
					} catch (Exception e) {
						list.add(data[i]);//收集不合法的行
						System.out.println("汇号:"+memberaddMoney.getREF_ID()+e.getMessage());
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
		//if(list.size() > 0 ) { //有异常数据
			//response.sendRedirect(request.getContextPath() + "/upload/" + ReadFactory.FILE_MAP.get(fileType) + "/"+ "error_"
					//+ fileName);
		//}else {
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
		sb.append("<b><font color='838383'>当前位置</font></b><font color='838383'> : </font><font color='838383'>帐户管理</font><font color='838383'>"); 
		sb.append("-&gt; </font><font color='838383'>汇款导入结果</font><font color='838383'>"); 
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
		sb.append("<a href='"+errUrl+"'>插入失败: " + j + "条</a><br>");
		sb.append("</td>");
		sb.append("</tr>");
		/*sb.append("<tr>");
		sb.append("<td>");
		sb.append("<a href='"+sxkUrl+"'>书香卡: " + k + "条</a><br>");
		sb.append("</td>");
		sb.append("</tr>");*/
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
	
	
	/**
	 * 分析提取会员号、订单号
	 * @param addmoney
	 * @param conn
	 * @throws Exception
	 */
	private void setMbCodeAndOrderCode(MemberaddMoney addmoney, Connection conn) throws Exception {
		
		MemberDAO mbdao = new MemberDAO();
		OrderDAO orderdao = new OrderDAO();
		
		String excelMbName = addmoney.getMB_NAME();
		String excelRemark = addmoney.getREMARK();
		String excelPostcode = addmoney.getPostCode();
		
		/* step1 提取订单号*/
		String orderCode = CharacterFormat.analyseNumber(excelRemark, 12); // 从"备注"中提取订单号(1字母+12数字)
		if (orderCode != null && !orderCode.equals("")) {
			orderCode = CharacterFormat.analyseOrderType(excelRemark.toUpperCase(), orderCode);
			if (!orderdao.checkOrderCode(conn, orderCode)) { //订单号不存在
				orderCode = null;//订单号设置空
			}
		} 
		
		//字母O替换成数字0
		if (orderCode == null ) {
			orderCode = CharacterFormat.analyseNumber(excelRemark, 11);
			if (orderCode != null && !orderCode.equals("")) {//找到11位
				orderCode = CharacterFormat.analyseOrderType(excelRemark.toUpperCase(), "O" + orderCode);
				if (orderCode != null && !orderCode.equals("")) {
					orderCode = orderCode.substring(0,1) + "0" +orderCode.substring(2);
				}
				if (!orderdao.checkOrderCode(conn, orderCode)) { //订单号不存在
					orderCode = null;//订单号设置空
				}
			}
		}
		//截取7+12位
		if (orderCode == null) {
			orderCode = CharacterFormat.analyseNumber(excelRemark, 13);
			if (orderCode != null && !orderCode.equals("")) {
				if (orderCode.startsWith("7")) {
					orderCode = "T" + orderCode.substring(1);
					if (!orderdao.checkOrderCode(conn, orderCode)) {
						orderCode = null;//订单号设置空
					}
				}
				
			}
		}
		//去所有空格后截取12位
		if (orderCode == null) {
			String trimedExcelRemark = excelRemark.replaceAll(" ", "");
			orderCode = CharacterFormat.analyseNumber(trimedExcelRemark, 12);
			if (orderCode != null && !orderCode.equals("")) {
				orderCode = CharacterFormat.analyseOrderType(trimedExcelRemark.toUpperCase(), orderCode);
				if (!orderdao.checkOrderCode(conn, orderCode)) { //订单号不存在
					orderCode = null;//订单号设置空
				}
			} 
		}
		
		if (orderCode != null && !orderCode.equals("")) {
			if (!orderdao.checkOrderCode(conn, orderCode)) { //订单号不存在
				orderCode = null;//订单号设置空
			}
		}
		
		
		/* step2 提取会员号*/
		String mbCode = CharacterFormat.analyseNumber(excelMbName, 8); // 从"汇款人姓名"提取会员号(8位)
		if (mbCode != null && !mbCode.equals("")) {
			mbCode = mbdao.checkMBCardIDSEQ2(conn, mbCode);
		}
		//如果"汇款人姓名"字段中提取不到会员号，并且之前订单号没有取到，到"会员留言"提取会员号
		if (mbCode == null && orderCode == null) {
			mbCode = CharacterFormat.analyseNumber(excelRemark, 8); // 从汇会员留言"提取会员号(8位)
			if (mbCode != null && !mbCode.equals("")) {
				mbCode = mbdao.checkMBCardIDSEQ2(conn, mbCode); //检测系统中是存在该8位数字串的会员号或者零时会员号，没有就返回null
			}
		}
		
		/* step3  如果订单号和会员号都提取不到，就要根据汇款人姓名(和邮编前4位)来匹配会员表 */
		if ((orderCode == null || orderCode.trim().equals(""))
				&& (mbCode == null || mbCode.trim().equals(""))) { // 订单号会员号都是空
			//String queryName = getMemberName(excelMbName == null ? "" : excelMbName.trim());
			String queryName = CharacterFormat.separateString(excelMbName == null ? "" : excelMbName.trim());
			String queryPostcode = "";
			// 提取邮编前4位，否则邮编为空
			if (excelPostcode != null && excelPostcode.length() >= 4) {
				queryPostcode = excelPostcode.substring(0, 4);
			}
			
			//通过截取的姓名和邮编4位查找会员信息(重复入会，无效会员应该过滤掉??????????????????????????)
			ArrayList list = (ArrayList)mbdao.queryMemberBasic(conn, queryName, queryPostcode);
			
			if (list.size() == 0) {// 没有匹配

			} else if (list.size() == 1) {// 匹配到1个

				Member member = (Member) list.get(0);
				mbCode = member.getCARD_ID(); //得到会员号

			} else {// 匹配到多个

			}

		}
		addmoney.setMB_CODE(mbCode);
		addmoney.setORDER_CODE(orderCode);
	}
}