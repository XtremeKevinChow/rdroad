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
 * (现在已经废弃,使用jobPrintSNDelivery和jobPrintSNPost代替)
 * *************************************************************
 * 产生发货单 
 * 每天六点产生发货单
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
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
   * 执行产生发货单后台服务（分直送和邮局交替进行）
   * @param context
   * @throws org.quartz.JobExecutionException
   */
    public void execute(JobExecutionContext context) throws JobExecutionException
	  {    
      try{
      if(isRun){
           System.out.println("警告:JobSN任务已经在执行，取消此次任务!");
           return;
      }        
      isRun=true;
      //JOB_SN_START= config.getValue("JOB_SN_START");        
      //JOB_SN_END=config.getValue("JOB_SN_END");        
      //if当前时间>18:00 <18:05 
      //{
      //  JobShippingNotices job=new JobShippingNotices(3);
      //  rerurn;
     // }       
      //取得当前时间的小时和分钟数
       Calendar calendar = Calendar.getInstance();
       int hours = calendar.get(Calendar.HOUR_OF_DAY);
       int minutes = calendar.get(Calendar.MINUTE);
       int current_seconds=hours*3600+minutes*60;
     //取得配置文件中的时间
       String job_sn_start=Config.getValue("JOB_SN_START").trim();
       String job_sn_end=Config.getValue("JOB_SN_END").trim();
     //分解并转换配置文件中取得的时间
       String start[]=this.stringToArray(job_sn_start);
       String end[]=this.stringToArray(job_sn_end);           
       int start_hours=Integer.parseInt(start[0]);
       int start_minutes=Integer.parseInt(start[1]);
       int end_hours=Integer.parseInt(end[0]);
       int end_minutes=Integer.parseInt(end[1]);
       int start_seconds=start_hours*3600+start_minutes*60;
       int end_seconds=end_hours*3600+end_minutes*60; 
       //当前时间大于18:00且小于18:05时
      int temp_start_sec=18 * 3600;
      int temp_end_sec  =18 * 3600 + 05 * 60;       
      if((current_seconds>temp_start_sec)&&(current_seconds<temp_end_sec))
      {
        //JobShippingNotices job=new JobShippingNotices(3);
        return;
       }
       
        //if当前时间<JOB_SN_START or >JOB_SN_END return;
       if(current_seconds<start_seconds||current_seconds>end_seconds){
          return;
       }
      
      if(delivery_type==1) 
      {
         delivery_type=3;
         System.out.println("处理直送定单");
      }
      else
      {
         delivery_type=1;
         System.out.println("处理邮局定单");
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
   * 分解字符串
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