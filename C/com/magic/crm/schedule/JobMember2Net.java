package com.magic.crm.schedule;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.magic.exchange.Member2Net;

/**
  * *************************************************************
 * (�����Ѿ�����,ʹ��jobCrm2Net����)
 * *************************************************************
 * ��Ա��Ϣ���µ���վ
 * 
 * @author magic
 * @Vender �Ϻ��������ϵͳ���޹�˾ (magic Software System Co.,ltd)
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
				log.info("����:JobMemberToNet�����Ѿ���ִ�У�ȡ���˴�����!");
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