package com.magic.crm.schedule;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.magic.crm.util.mail.MailGen;

/**
 * �ʼ�����֪ͨjob
 * @author user
 *
 */
public class JobSendArrivalMail implements Job {
	private static boolean isRun = false;

    private static Logger log = Logger.getLogger(JobSendArrivalMail.class);

    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        try {
            if (isRun) {
                System.out.println("����:�����ʼ������Ѿ���ִ�У������ظ�ִ��!");
                return;
            }
            isRun = true;
            new MailGen().dealArrivalMail();
        } catch (Exception e) {
            log.error("--execute send email exception ---", e);
        } finally {
            isRun = false;
        }
    }
}