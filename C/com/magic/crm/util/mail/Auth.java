/*
 * Created on 2006-3-22
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.util.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
/**
 * @author magic
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Auth extends Authenticator {
    private String user,pwd;

    public Auth( String user, String pwd ) {
        this.user = user;
        this.pwd = pwd;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication( this.user, this.pwd );
    }
}

