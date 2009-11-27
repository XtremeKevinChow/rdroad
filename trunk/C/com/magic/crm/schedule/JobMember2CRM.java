package com.magic.crm.schedule;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.magic.exchange.Member2CRM;

/**
 * *************************************************************
 * (现在已经废弃,使用JobNet2Crm代替)
 * *************************************************************
 * 会员信息从网站到CRM
 * 
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */
public class JobMember2CRM implements Job {
	private static boolean isRun = false;
	private static Logger log = Logger.getLogger(JobMember2CRM.class);
	public JobMember2CRM() {
	}

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		try {
			if (isRun) {
				System.out.println("警告:JobMemberToCRM任务已经在执行，取消此次任务!");
				return;
			}
			isRun = true;
			new Member2CRM().execute();
		} catch (Exception e) {
			//e.printStackTrace();
			log.error("---execute member to crm error ---",e);
		} finally {
			isRun = false;
		}
	}
}