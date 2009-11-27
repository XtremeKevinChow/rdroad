package com.magic.crm.schedule;

import java.util.Date;
import java.util.Calendar;
import org.quartz.*;
//import com.magic.utils.*;
import java.sql.*;
import com.magic.exchange.PrintShippingNotices;
import com.magic.crm.util.*;
import org.apache.log4j.*;

public class JobPrintSNPost
    implements Job {

  private static Logger log = Logger.getLogger(JobPrintSNPost.class);

  private static boolean isRun = false; //是否已经执行

  private static int printNumber = 100; //默认每次打印发货单的条数

  private static int minNumber = 50; //默认打印发货单的最小条数

  public JobPrintSNPost() {
  }

  /**
   * 执行打印发货单的后台服务（分直送和邮局交替进行）
   *
   * @param context
   * @throws org.quartz.JobExecutionException
   */
  public void execute(JobExecutionContext context) throws JobExecutionException {
    try {
      if (isRun) {
        System.out.println("警告:JobSN任务已经在执行，取消此次任务!");
        return;
      }
      isRun = true;

      printNumber = Integer.parseInt(Config
                                     .getValue("JOB_SN_PRINT_NUMBET"));
      minNumber = Integer.parseInt(Config
                                   .getValue("JOB_SN_PRINT_MIN_NUMBER"));

      System.out.println("打印邮局发货单");

      PrintShippingNotices job = new PrintShippingNotices(1, printNumber,
          minNumber);
      Connection conn = DBManager2.getConnection();
      job.createSN(conn);
      job.printSN(conn);
      conn.close();

    }
    catch (Exception e) {
      //e.printStackTrace();
      log.error("打印发货单出错" + e.getMessage());
    }
    finally {
      isRun = false;
    }
  }

  /**
   * 分解字符串
   *
   * @param str
   * @return
   */
  public static String[] stringToArray(String str) {
    if (str.equals("") || str == null) {
      return null;
    }
    str = str.trim();
    int count = str.length();
    String array[] = new String[count];
    int index = 0, arr_index = 0;
    index = str.indexOf(":");
    if (index == -1) {
      array[arr_index] = str;
    }
    while (index != -1) {
      array[arr_index] = str.substring(0, index);
      arr_index++;
      str = str.substring(index + 1);
      index = str.indexOf(":");
      if (index == -1) {
        array[arr_index] = str;
      }
    }
    return array;
  }
}
