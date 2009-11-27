package com.magic.crm.schedule;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.magic.exchange.Member2CRM;

/**
 * *************************************************************
 * (�����Ѿ�����,ʹ��JobNet2Crm����)
 * *************************************************************
 * ��Ա��Ϣ����վ��CRM
 * 
 * @author magic
 * @Vender �Ϻ��������ϵͳ���޹�˾ (magic Software System Co.,ltd)
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
				System.out.println("����:JobMemberToCRM�����Ѿ���ִ�У�ȡ���˴�����!");
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