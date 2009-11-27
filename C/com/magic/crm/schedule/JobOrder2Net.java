package com.magic.crm.schedule;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.magic.exchange.Order2Net;

/**
 * *************************************************************
 * (�����Ѿ�����,ʹ��jobCrm2Net����)
 * *************************************************************
 * ������Ϣ���µ���վ
 * 
 * @author magic
 * @Vender �Ϻ��������ϵͳ���޹�˾ (magic Software System Co.,ltd)
 */
public class JobOrder2Net implements Job {
	private static boolean isRun = false;
	private static Logger log = Logger.getLogger(JobPrintSNDelivery.class);
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		try {
			if (isRun) {
				System.out.println("����:JobCRMOrderUpdateNet�����Ѿ���ִ�У�ȡ���˴�����!");
				return;
			}
			isRun = true;
			new Order2Net().execute();
		} catch (Exception e) {
			
			log.error("---ִ�ж������³���---",e);
		} finally {
			isRun = false;

		}
	}

}

