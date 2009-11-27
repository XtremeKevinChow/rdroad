/*
 * Created on 2006-3-22
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.util.mail;

import java.util.Properties;

import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;

import org.apache.log4j.Logger;

/**
 * @author magic
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ConnectMail {
    
    private static Logger log = Logger.getLogger("ConnectMail.class");
    
    private Session session;

    private Transport transport;

    private Store store;

    //private Properties properties;

    /** 返回过的实例在验证通过后使用 */

    public Session getSession() {
        return session;
    }

    public Store getStore() {
        return store;
    }

    public Transport getTransport() {
        return transport;
    }

    public boolean connectSmtp(Properties prop) throws Exception {
        boolean flag = false;
        try {
            
            Auth auth = new Auth(prop.getProperty(Constant.KEY_USER), prop.getProperty(Constant.KEY_PASSWORD));
            session = Session.getInstance(prop, auth);

            transport = session.getTransport(Constant.TRANS_PROTOCOL);
            transport.connect();
            flag = true;
        } catch (Exception e) {
            log.error(e.toString());
            flag = false;
            throw e;
            
        }
        return flag;
    }
/**
    public boolean connectPop3(String popHost, String uid, String pwd) {

        try {
            properties = new java.util.Properties();

            session = Session.getDefaultInstance(properties, null);
            store = session.getStore("pop3");
            store.connect(popHost, uid, pwd);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }  
*/

}
