package com.magic.crm.schedule;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.magic.exchange.Member2Net;

/**
  * *************************************************************
 * (现在已经废弃,使用jobCrm2Net代替)
 * *************************************************************
 * 会员信息更新到网站
 * 
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */
public class JobMember2Net implements Job {
	private static boolean isRun = false;
	private static Logger log = Logger.getLogger(JobMember2Net.class);
	public JobMember2Net() {
	}

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		try {
			if (isRun) {
				log.info("警告:JobMemberToNet任务已经在执行，取消此次任务!");
				return;
			}
			isRun = true;
			new Member2Net().execute();
		} catch (Exception e) {
			//e.printStackTrace();
			log.error("--- execute member to netshop ---",e);
		} finally {
			isRun = false;
		}
	}
}