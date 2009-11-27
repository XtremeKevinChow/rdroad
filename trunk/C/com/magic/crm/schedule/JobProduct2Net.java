package com.magic.crm.schedule;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.magic.exchange.Product2Net;
import org.apache.log4j.Logger;

/**
 * ��Ʒ���µ���վ
 * 
 * @author magic
 * @Vender �Ϻ��������ϵͳ���޹�˾ (magic Software System Co.,ltd)
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
				System.out.println("����:JobProductToNet�����Ѿ���ִ�У�ȡ���˴�����!");
				return;
			}
			isRun = true;
			new Product2Net().execute();
		} catch (Exception e) {
			log.error("---ִ�в�Ʒ�������---",e);
		} finally {
			isRun = false;
		}
	}
}