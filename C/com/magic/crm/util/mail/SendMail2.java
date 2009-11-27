/*
 * Created on 2006-3-22
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.util.mail;

import com.magic.crm.letter.bo.LetterTemplate;
/**
 * @author magic
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
/*
 * Created on 2006-3-22
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.apache.log4j.Logger;

import sun.misc.BASE64Encoder;

/**
 * @author magic
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class SendMail2 {
    
    private static Logger log = Logger.getLogger("SendMail2.class");
    
    /** 邮件配置文件路径 * */
    private static String MAIL_CONFIG_PATH = "E:\\crm_service\\service\\schedule\\mail.properties";

    /** 邮件配置信息 * */
    private static Properties prop = new Properties();

    /** 得到配置文件中的配置信息(该类第一次加载时读取配置信息) * */
    static {
        FileInputStream in = null;
        try {
            log.info("load mail.properties ...");
            in = new FileInputStream(MAIL_CONFIG_PATH);
            prop.load(in);

        } catch (FileNotFoundException fnfe) {
            log.error("未找到配置文件");
            System.exit(-2);
            
        } catch(IOException ioe) {
            log.error("加载邮件配置信息出错");
            System.exit(-2);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch(IOException ioe) {
                    log.error("文件关闭发生错误");
                    System.exit(-2);
                }
            }
        }

    }

    /** 文本编码 */
    private String encode;

    /** 文本还是html内容 */
    private boolean HTML;

    private Session session;

    private Transport transport;

    private BASE64Encoder enc = new sun.misc.BASE64Encoder();

    public SendMail2() {
        this.encode = "GBK";
        this.HTML = true;
    }

    public SendMail2(String encode, boolean html) {
        this.encode = encode;
        this.HTML = html;
    }

    public boolean connect(Properties prop) throws Exception {
        boolean result = false;
        ConnectMail connect = new ConnectMail();
        result = connect.connectSmtp(prop);
        this.session = connect.getSession();
        this.transport = connect.getTransport();
        return result;
    }


    /**
     * 发送单个邮件
     * 
     * @param letter:邮件模版
     */
    public boolean send(LetterTemplate letter) throws Exception {
        String isHTML = prop.getProperty(Constant.KEY_IS_HTML);
        this.HTML = isHTML.equals("1") ? true : false;
        boolean flag = false;
        try {      
            if (connect(prop)) {//连接上服务器
                prop.put(Constant.KEY_MAIL_TITLE, letter.getTitle());
                prop.put(Constant.KEY_MAIL_CONTENT, letter.getTemplate());
                prop.put(Constant.KEY_TO_NAME, letter.getEmail());
                MimeMessage mimeMessage = createMimeMessage(prop);
                transport.sendMessage(mimeMessage, mimeMessage
                        .getAllRecipients());
                flag = true;
            } else {
                flag = false;
            }
            
        } catch (Exception e) {
            log.error(e.toString()+letter.getTitle());
            flag = false;
            throw e;
            
        } finally {
            if (this.transport != null) {
                try {
                    this.transport.close();
                } catch(Exception e) {
                    flag = false;
                }
            }    
        }
        return flag;
    }

    /**
     * 发送单个邮件
     * 
     * @param subject:邮件主题
     * @param content:邮件内容
     * @param mailto:收件人
     */
    public boolean send(String subject, String content, String mailto) {
        String isHTML = prop.getProperty(Constant.KEY_IS_HTML);
        this.HTML = isHTML.equals("1") ? true : false;
        try {
            if (connect(prop)) {
                prop.put(Constant.KEY_MAIL_TITLE, subject);
                prop.put(Constant.KEY_MAIL_CONTENT, content);
                prop.put(Constant.KEY_TO_NAME, mailto);
                MimeMessage mimeMessage = createMimeMessage(prop);

                transport.sendMessage(mimeMessage, mimeMessage
                        .getAllRecipients());

            }
            return true;
        } catch (Exception e) {
            log.error(e.toString());
            return false;
        } finally {
            
            if (this.transport != null) {
                try {
                    this.transport.close();
                } catch(Exception e) {
                    
                }
            }    
            
        }
        
    }

    private MimeMessage createMimeMessage(Properties prop)
            throws MessagingException {

        String fromName = prop.getProperty(Constant.KEY_FROM_NAME);
        try {
            fromName = new String(fromName.getBytes("ISO8859_1"), "GBK");
        }catch(Exception e) {
            e.printStackTrace();
        }
        String fromMail = prop.getProperty(Constant.KEY_FROM_MAIL);
        String toMail = prop.getProperty(Constant.KEY_TO_NAME);
        String mailTitle = prop.getProperty(Constant.KEY_MAIL_TITLE);
        String mailContent = prop.getProperty(Constant.KEY_MAIL_CONTENT);

        /** 加入调试信息 */
        session.setDebug(false);

        MimeMessage message = new MimeMessage(session);
        InternetAddress fromAddress = new InternetAddress(fromMail);
        try {
            if (fromName != null)
                fromAddress.setPersonal(fromName);
        } catch (Exception e) {
        }
        message.setFrom(fromAddress);
        InternetAddress[] toAddress = InternetAddress.parse(toMail);
        message.setRecipients(RecipientType.TO, toAddress);
        message.setSentDate(new Date());
        message.setSubject(mailTitle, this.encode);

        /** 处理附件 */

        if (HTML) {
            message.setContent(mailContent, "text/html;charset=" + this.encode);
        } else {
            message.setText(mailContent, this.encode);
        }

        return message;
    }
}
