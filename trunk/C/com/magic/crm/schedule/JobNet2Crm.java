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
public class JobNet2Crm implements Job {
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
			new Member2CRM().execute();
			// 2 ���¶���
			new Order2CRM().execute();
			
		} catch (Exception e) {
			log.error("--execute net to crm exception ---",e);
		} finally {
			isRun = false;
		}
	}
}
