/*
 * Created on 2005-7-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.schedule;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.magic.exchange.*;;
/**
 * @author Administrator
 * �ռ�ȱ���
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

public class JobMemberMail implements Job {
	private static boolean isRun = false;
	private static Logger log = Logger.getLogger(JobMemberMail.class);
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		try {
			if (isRun) {
				System.out.println("����:�ռ�ȱ�������Ѿ���ִ�У������ظ�ִ��!");
				return;
			}
			isRun = true;

			// 1 �ռ�����
			new MemberMail().execute();
		} catch (Exception e) {
			log.error("--execute net to crm exception ---",e);
		} finally {
			isRun = false;
		}
	}
}
