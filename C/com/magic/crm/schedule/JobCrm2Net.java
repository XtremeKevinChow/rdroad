/*
 * Created on 2005-4-13
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.schedule;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.magic.exchange.*;

/**
 * @author Administrator
 *
 * TODO 99read 
 */
public class JobCrm2Net implements Job {
	private static boolean isRun = false;
	private static Logger log = Logger.getLogger(JobOrder2CRM.class);
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		try {
			if (isRun) {
				System.out.println("警告:JobOrderToCRM任务已经在执行，取消此次任务!");
				return;
			}
			isRun = true;

			// 1 更新会员
			new Member2Net().execute();
			// 2 更新订单
			new Order2Net().execute();
			// 3 更新礼券
			//new Ticket2Net().execute();
			// 4 更新礼品
			//new Gift2Net().execute();
			
		} catch (Exception e) {
			//e.printStackTrace();
			log.error("--execute order to crm error---",e);
		} finally {
			isRun = false;
		}
	}
}
