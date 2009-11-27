package com.magic.crm.schedule;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.magic.exchange.Product2Net;
import org.apache.log4j.Logger;

/**
 * 产品更新到网站
 * 
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */
public class JobProduct2Net implements Job {
	private static boolean isRun = false;
	Logger log = Logger.getLogger(JobProduct2Net.class);

	public JobProduct2Net() {
	}

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		try {
			if (isRun) {
				System.out.println("警告:JobProductToNet任务已经在执行，取消此次任务!");
				return;
			}
			isRun = true;
			new Product2Net().execute();
		} catch (Exception e) {
			log.error("---执行产品导入出错---",e);
		} finally {
			isRun = false;
		}
	}
}