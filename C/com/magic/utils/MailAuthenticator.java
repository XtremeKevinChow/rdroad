package com.magic.utils;

import java.util.Properties;
import java.util.Date;
import java.util.ArrayList;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
/**
 * 邮件认证
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */
class MailAuthenticator extends Authenticator{

    private String username = null;
    private String userpasswd = null;

    public MailAuthenticator(){}
    public MailAuthenticator(String username,String userpasswd){
        this.username = username;
        this.userpasswd = userpasswd;
    }
    
    public void setUserName(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.userpasswd = password;
    }

    public PasswordAuthentication getPasswordAuthentication(){
        return new PasswordAuthentication(username,userpasswd);
    }
}
