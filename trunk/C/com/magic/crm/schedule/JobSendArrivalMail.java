package com.magic.crm.schedule;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.magic.crm.util.mail.MailGen;

/**
 * 邮件到货通知job
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
                System.out.println("警告:发送邮件任务已经在执行，不需重复执行!");
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
