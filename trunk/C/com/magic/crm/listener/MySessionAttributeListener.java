/**
 * MySessionAttributeListener.java
 * 2008-4-22
 * ÉÏÎç09:49:53
 * user
 * MySessionAttributeListener
 */
package com.magic.crm.listener;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * @author user
 *
 */
public class MySessionAttributeListener implements HttpSessionAttributeListener {

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionAttributeListener#attributeAdded(javax.servlet.http.HttpSessionBindingEvent)
	 */
	public void attributeAdded(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("Session attribute:"+arg0.getName()+" is added......,value is:"+arg0.getValue());
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionAttributeListener#attributeRemoved(javax.servlet.http.HttpSessionBindingEvent)
	 */
	public void attributeRemoved(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("Session attribute:"+arg0.getName()+" is removed......,value is:"+arg0.getValue());
		
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionAttributeListener#attributeReplaced(javax.servlet.http.HttpSessionBindingEvent)
	 */
	public void attributeReplaced(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("Session attribute:"+arg0.getName()+" is replaced......,value is:"+arg0.getValue());
	}

}
