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
				System.out.println("����:JobOrderToCRM�����Ѿ���ִ�У�ȡ���˴�����!");
				return;
			}
			isRun = true;

			// 1 ���»�Ա
			new Member2Net().execute();
			// 2 ���¶���
			new Order2Net().execute();
			// 3 ������ȯ
			//new Ticket2Net().execute();
			// 4 ������Ʒ
			//new Gift2Net().execute();
			
		} catch (Exception e) {
			//e.printStackTrace();
			log.error("--execute order to crm error---",e);
		} finally {
			isRun = false;
		}
	}
}
