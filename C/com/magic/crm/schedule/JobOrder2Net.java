package com.magic.crm.schedule;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.magic.exchange.Order2Net;

/**
 * *************************************************************
 * (现在已经废弃,使用jobCrm2Net代替)
 * *************************************************************
 * 订单信息更新到网站
 * 
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */
public class JobOrder2Net implements Job {
	private static boolean isRun = false;
	private static Logger log = Logger.getLogger(JobPrintSNDelivery.class);
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		try {
			if (isRun) {
				System.out.println("警告:JobCRMOrderUpdateNet任务已经在执行，取消此次任务!");
				return;
			}
			isRun = true;
			new Order2Net().execute();
		} catch (Exception e) {
			
			log.error("---执行订单更新出错---",e);
		} finally {
			isRun = false;

		}
	}

}

