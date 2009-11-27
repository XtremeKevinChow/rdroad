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
 * 发送催款信
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

public class JobMemberSendMail implements Job {
	private static boolean isRun = false;
	private static Logger log = Logger.getLogger(JobMemberSendMail.class);
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		try {
			if (isRun) {
				System.out.println("警告:发送催款信任务已经在执行，不需重复执行!");
				return;
			}
			isRun = true;

			// 1 发送催款邮件
			new MemberMail().execute1();
		} catch (Exception e) {
			log.error("--execute net to crm exception ---",e);
		} finally {
			isRun = false;
		}
	}
}
