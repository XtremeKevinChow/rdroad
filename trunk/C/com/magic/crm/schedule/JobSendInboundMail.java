package com.magic.crm.schedule;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.magic.crm.util.mail.MailGen;

public class JobSendInboundMail implements Job {
	private static boolean isRun = false;

    private static Logger log = Logger.getLogger(JobSendInboundMail.class);

    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        try {
            if (isRun) {
                System.out.println("警告:发送邮件任务已经在执行，不需重复执行!");
                return;
            }
            isRun = true;
            new MailGen().dealInboundMail();
        } catch (Exception e) {
            log.error("--execute send email exception ---", e);
        } finally {
            isRun = false;
        }
    }
}
