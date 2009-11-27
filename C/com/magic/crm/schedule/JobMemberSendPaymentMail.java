/*
 * Created on 2006-4-6
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.schedule;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.magic.crm.util.mail.MailGen;

;

/**
 * @author user
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class JobMemberSendPaymentMail implements Job {

    private static boolean isRun = false;

    private static Logger log = Logger.getLogger(JobMemberSendPaymentMail.class);

    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        try {
            if (isRun) {
                System.out.println("警告:发送邮件任务已经在执行，不需重复执行!");
                return;
            }
            isRun = true;
            new MailGen().dealPaymentMail();
        } catch (Exception e) {
            log.error("--execute send email exception ---", e);
        } finally {
            isRun = false;
        }
    }
}
