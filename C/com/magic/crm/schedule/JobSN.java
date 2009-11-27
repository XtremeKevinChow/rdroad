package com.magic.crm.schedule;

import java.util.Date;
import java.util.Calendar;
import org.quartz.*;
//import com.magic.utils.*;
import java.sql.*;
import com.magic.crm.util.*;
import org.apache.log4j.*;
/**
 * *************************************************************
 * (�����Ѿ�����,ʹ��jobPrintSNDelivery��jobPrintSNPost����)
 * *************************************************************
 * ���������� 
 * ÿ���������������
 * @author magic
 * @Vender �Ϻ��������ϵͳ���޹�˾ (magic Software System Co.,ltd)
 */
public class JobSN implements Job
{
    private static Logger log = Logger.getLogger(JobSN.class);
	static int delivery_type=3;
    private static boolean isRun =false;
    public JobSN()
    {
    }
  /**
   * ִ�в�����������̨���񣨷�ֱ�ͺ��ʾֽ�����У�
   * @param context
   * @throws org.quartz.JobExecutionException
   */
    public void execute(JobExecutionContext context) throws JobExecutionException
	  {    
      try{
      if(isRun){
           System.out.println("����:JobSN�����Ѿ���ִ�У�ȡ���˴�����!");
           return;
      }        
      isRun=true;
      //JOB_SN_START= config.getValue("JOB_SN_START");        
      //JOB_SN_END=config.getValue("JOB_SN_END");        
      //if��ǰʱ��>18:00 <18:05 
      //{
      //  JobShippingNotices job=new JobShippingNotices(3);
      //  rerurn;
     // }       
      //ȡ�õ�ǰʱ���Сʱ�ͷ�����
       Calendar calendar = Calendar.getInstance();
       int hours = calendar.get(Calendar.HOUR_OF_DAY);
       int minutes = calendar.get(Calendar.MINUTE);
       int current_seconds=hours*3600+minutes*60;
     //ȡ�������ļ��е�ʱ��
       String job_sn_start=Config.getValue("JOB_SN_START").trim();
       String job_sn_end=Config.getValue("JOB_SN_END").trim();
     //�ֽⲢת�������ļ���ȡ�õ�ʱ��
       String start[]=this.stringToArray(job_sn_start);
       String end[]=this.stringToArray(job_sn_end);           
       int start_hours=Integer.parseInt(start[0]);
       int start_minutes=Integer.parseInt(start[1]);
       int end_hours=Integer.parseInt(end[0]);
       int end_minutes=Integer.parseInt(end[1]);
       int start_seconds=start_hours*3600+start_minutes*60;
       int end_seconds=end_hours*3600+end_minutes*60; 
       //��ǰʱ�����18:00��С��18:05ʱ
      int temp_start_sec=18 * 3600;
      int temp_end_sec  =18 * 3600 + 05 * 60;       
      if((current_seconds>temp_start_sec)&&(current_seconds<temp_end_sec))
      {
        //JobShippingNotices job=new JobShippingNotices(3);
        return;
       }
       
        //if��ǰʱ��<JOB_SN_START or >JOB_SN_END return;
       if(current_seconds<start_seconds||current_seconds>end_seconds){
          return;
       }
      
      if(delivery_type==1) 
      {
         delivery_type=3;
         System.out.println("����ֱ�Ͷ���");
      }
      else
      {
         delivery_type=1;
         System.out.println("�����ʾֶ���");
      }
      //JobShippingNotices job=new JobShippingNotices(delivery_type);
      //job.run();
      }catch(Exception e){
        e.printStackTrace();
        log.error(e);
      }
      finally{
         isRun=false;
      }
    }
    
     /**
   * �ֽ��ַ���
   * @param str
   * @return 
   */
  public static String[] stringToArray(String str){
    if(str.equals("")||str==null){
       return null;
    }
    str=str.trim();
    int count=str.length();
    String  array[]=new String[count];
    int index=0,arr_index=0;
    index=str.indexOf(":");
    if(index==-1){
       array[arr_index]=str;       
    }
    while(index!=-1){
        array[arr_index]=str.substring(0,index);          
        arr_index++;
        str=str.substring(index+1);
        index=str.indexOf(":");
        if(index==-1){
         array[arr_index]=str;      
        }        
    }     
    return array;
  }
    
}