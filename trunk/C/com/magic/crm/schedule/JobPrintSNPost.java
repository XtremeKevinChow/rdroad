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

  private static boolean isRun = false; //�Ƿ��Ѿ�ִ��

  private static int printNumber = 100; //Ĭ��ÿ�δ�ӡ������������

  private static int minNumber = 50; //Ĭ�ϴ�ӡ����������С����

  public JobPrintSNPost() {
  }

  /**
   * ִ�д�ӡ�������ĺ�̨���񣨷�ֱ�ͺ��ʾֽ�����У�
   *
   * @param context
   * @throws org.quartz.JobExecutionException
   */
  public void execute(JobExecutionContext context) throws JobExecutionException {
    try {
      if (isRun) {
        System.out.println("����:JobSN�����Ѿ���ִ�У�ȡ���˴�����!");
        return;
      }
      isRun = true;

      printNumber = Integer.parseInt(Config
                                     .getValue("JOB_SN_PRINT_NUMBET"));
      minNumber = Integer.parseInt(Config
                                   .getValue("JOB_SN_PRINT_MIN_NUMBER"));

      System.out.println("��ӡ�ʾַ�����");

      PrintShippingNotices job = new PrintShippingNotices(1, printNumber,
          minNumber);
      Connection conn = DBManager2.getConnection();
      job.createSN(conn);
      job.printSN(conn);
      conn.close();

    }
    catch (Exception e) {
      //e.printStackTrace();
      log.error("��ӡ����������" + e.getMessage());
    }
    finally {
      isRun = false;
    }
  }

  /**
   * �ֽ��ַ���
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
