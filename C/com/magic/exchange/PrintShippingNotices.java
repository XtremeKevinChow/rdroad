package com.magic.exchange;

import com.magic.crm.util.*;
import org.apache.log4j.*;
import com.magic.utils.StringUtil;
import java.sql.CallableStatement;
import java.sql.*;
import java.sql.Statement;
import java.sql.Types;

/**
 * 打印发货单，并修改发货单的临时批号
 *
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */
public class PrintShippingNotices {
  private static Logger log = Logger.getLogger(PrintShippingNotices.class);

  int delivery_type = 0;

  int print_number = 100;

  int min_number = 1;

  private static boolean isRun = false;

  public PrintShippingNotices(int delivery_type) {
    this.delivery_type = delivery_type;
  }

  /**
   *
   * @param delivery_type
   *            发送方式:直送，邮局
   * @param print_number
   *            产生发货单的条数（从配置文件获得）
   */
  public PrintShippingNotices(int delivery_type, int print_number,
                              int min_number) {
    this.delivery_type = delivery_type;
    this.print_number = print_number;
    this.min_number = min_number;
  }

  public static void createSN(Connection conn) {

    CallableStatement cstmt = null;
    try {
      String sql = "{call service.job_create_shippingnotice}";
      cstmt = conn.prepareCall(sql);
      cstmt.execute();
      cstmt.close();
    } catch(Exception e) {
      e.printStackTrace();
    } finally {
      try { cstmt.close();} catch(Exception e1){}
    }

  }


  /**
   * 调用修改批号的存储过程（job_event.f_print_shippingnotices(?,?)）
   * 只修改发货单的临时批号为正式批号，然后打印发货单和配货单， 直送和邮局交替进行
   */
  public String printSN(Connection conn ) {
    if (isRun) {
      System.out.println("警告:JobShippingNotices任务已经在执行，取消此次任务!");
      return "";
    }
    isRun = true;

    CallableStatement cstmt = null;
    String lot = "";
    try {
      String sql = "{?=call service.f_print_shippingnotices(?,?,?)}";
      cstmt = conn.prepareCall(sql);
      cstmt.setInt(2, delivery_type);
      cstmt.setInt(3, print_number);
      cstmt.setInt(4, min_number);
      cstmt.registerOutParameter(1, Types.VARCHAR);
      cstmt.execute();

      lot = StringUtil.cEmpty(cstmt.getString(1));
      System.out.println("批号:" + lot);
      cstmt.close();
      if (lot.equals("")) {
        System.out.println("没有打印发货单");
        return "";
      }


    }
    catch (Exception e) {
      e.printStackTrace();
      log.error("打印发货单出错：" + e.getMessage());
    }
    finally {
      try {
        if (cstmt != null)
          cstmt.close();

      }
      catch (Exception e) {
        e.printStackTrace();
      }
      isRun = false;
    }
    return lot;
  }
  
  /**
   * 调用修改批号的存储过程（job_event.f_print_shippingnotices(?,?)）
   * 只修改发货单的临时批号为正式批号，然后打印发货单和配货单， 直送和邮局交替进行
   */
  public String printSN(Connection conn ,String orderNumbers) {
    if (isRun) {
      System.out.println("警告:JobShippingNotices任务已经在执行，取消此次任务!");
      return "";
    }
    isRun = true;

    CallableStatement cstmt = null;
    String lot = "";
    try {
      String sql = "{?=call service.f_print_shippingnotices2(?)}";
      cstmt = conn.prepareCall(sql);
      cstmt.setString(2, orderNumbers);
      cstmt.registerOutParameter(1, Types.VARCHAR);
      cstmt.execute();

      lot = StringUtil.cEmpty(cstmt.getString(1));
      System.out.println("批号:" + lot);
      cstmt.close();
      if (lot.equals("")) {
        System.out.println("没有打印发货单");
        return "";
      }


    }
    catch (Exception e) {
      e.printStackTrace();
      log.error("打印发货单出错：" + e.getMessage());
    }
    finally {
      try {
        if (cstmt != null)
          cstmt.close();

      }
      catch (Exception e) {
        e.printStackTrace();
      }
      isRun = false;
    }
    return lot;
  }
  
  /**
   * 调用修改批号的存储过程（job_event.f_print_shippingnotices(?,?)）
   * 只修改发货单的临时批号为正式批号，然后打印发货单和配货单， 直送和邮局交替进行
   */
  public String checkSO(Connection conn ,String orderNumbers) {
	String ret = "";
    String[] so = orderNumbers.split(",");
    PreparedStatement ps = null;
    try {
    	ps = conn.prepareStatement("select count(1)  from ord_shippingnotices where order_number=? and status=0 ");
    	
    	for (int i=0;i<so.length;i++) {
    		ps.setString(1, so[i]);
    		ResultSet rs = ps.executeQuery();
    		if(rs.next()) {
    			if (rs.getInt(1) == 0 ) {
    				ret += so[i] + ",";
    			}
    		}
    		rs.close();
    	}
    	if (!"".equals(ret)) {
    		ret += "订单不存在或状态不可配货";
    	}
    }
    catch (Exception e) {
      e.printStackTrace();
      log.error("打印发货单出错：" + e.getMessage());
    }
    finally {
      try {
        if (ps != null)
          ps.close();}
      catch (Exception e) {
        e.printStackTrace();
      }
      
    }
    return ret;
  }

  /**
   * 填充jxc的配货单表
   * @param conn Connection
   * @param lot String
   */
  public static int fillSNTable(Connection conn, String lot) {
    int ret =-1;
    String sql = "{?=call service.f_fill_sn(?)}";
    CallableStatement cs = null;
    try {
      cs = conn.prepareCall(sql);
      cs.setString(2, lot);
      cs.registerOutParameter(1, Types.INTEGER);
      cs.execute();
      ret = cs.getInt(1);
      cs.close();
    }
    catch (SQLException ex) {
      log.error("", ex);
    }
    return ret;
  }

  /**
   * 产生补货单
   * @param conn Connection
   * @param lot String
   */
  public static int  create_supply(Connection conn, String lot) {
    int ret = -1;
    String sql = "{?=call service.f_create_supply(?)}";
    CallableStatement cs = null;
    try {
      cs = conn.prepareCall(sql);
      cs.setString(2, lot);
      cs.registerOutParameter(1, Types.INTEGER);
      cs.execute();
      ret = cs.getInt(1);
      cs.close();
    }
    catch (SQLException ex) {
      log.error("", ex);
    }
    return ret;

  }

  /**
   * 填充jxc的配货单表
   * @param conn Connection
   * @param lot String
   */
  public static int checkLot(Connection conn, String lot) {
    int ret =-1;
    int count=0;
    String sql = "select count(1) from vw_order_lot_dtl where id=?";
    PreparedStatement ps = null;
    try {
      ps = conn.prepareStatement(sql);
      ps.setString(1,lot);
      ResultSet rs = ps.executeQuery();
      if(rs.next()) {
        count = rs.getInt(1);
      }
      rs.close();
      ps.close();

      if (count ==0) {
        ret = -2;
      } else {
        ps = conn.prepareStatement(
            "select count(1) from sto_sn_dtl where lot=?");
        ps.setString(1, lot);
        rs = ps.executeQuery();
        if (rs.next()) {
          count = rs.getInt(1);
        }
        rs.close();
        ps.close();
        if( count >0)  {
          ret = 1;
        } else {
          ret = 0;
        }

      }

     }
    catch (SQLException ex) {
      log.error("", ex);
    }
    return ret;
  }

}
