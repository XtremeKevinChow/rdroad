/*
 * $Header: /cvsroot/fecredit/src/com/fechina/util/Constants.java,v 1.5 2003/04/23 03:14:36 yhliao Exp $
 * $Revision: 1.5 $
 * $Date: 2003/04/23 03:14:36 $ 
 *
 * ====================================================================
 *
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 1999-2002 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "The Jakarta Project", "Struts", and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache"
 *    nor may "Apache" appear in their names without prior written
 *    permission of the Apache Group.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */



package com.magic.crm.util;


/**
 * Manifest constants for the example application.
 *
 * @author Craig R. McClanahan
 * @version $Revision: 1.5 $ $Date: 2003/04/23 03:14:36 $
 */

public final class Constants {
    //************************ add by jackey *************************
	public static final String MEMBERQUERY_KEY = "memberquery";
	public static final int PAGE_LENGTH = 5 ; //每页显示5条记录 

    /**
     * The package name for this application.
     */
    public static final String Package = "org.apache.struts.webapp.example";


    /**
     * The application scope attribute under which our user database
     * is stored.
     */
    //public static final String DATABASE_KEY = "jdbc/fedb";


    /**
     * The session scope attribute under which the Subscription object
     * currently selected by our logged-in User is stored.
     */
    public static final String SUBSCRIPTION_KEY = "subscription";


    /**
     * The session scope attribute under which the User object
     * for the currently logged in user is stored.
     */
    public static final String USER_KEY = "user";
    
    /***
     * 呼叫中心系统中当前被服务的会员对象键
     */
    public static final String CURRENT_MEMBER_KEY = "currentMember";
    
    public static final String PREVIOUS_MEMBER_KEY = "previousMember";
    
    /**
     * 中心当前主叫号码
     * 当Call Center将URL串发送过来的保存到Session中
     */
    public static final String CURRENT_COME_PHONE = "currentPhone";
    
    /**
     * The session scope attribute under which the User object
     * for the currently logged in field rights view is stored.
     */
    public static final String FIELD_RIGHTS_KEY = "field";
    /**
     * The request scope attribute under which the collection of all roles is stored
     *
     */
    public static final String ROLES = "role";
    public static final String RIGHTS = "right";
    public static final String ALL_ROLES = "allRoles";

 		/**
     * The request scope attribute under which the collection of all users is stored
     *
     */
    public static final String ALL_USERS = "allUsers";

    /**
     * The request scope attribute under which the collection of all rights is stored
     *
     */
    public static final String ALL_RIGHTS = "allRights";

    public static final String PATH_RIGHT_MAP = "pathRightMap";

    public static final String ERROR_KEY = "controlledError";

   
    /**
     *  The report related constants
     */

    //public static final String RPT_USERCONFIG = "/reports/xml/userconfig.xml";

    //public static final String RPT_HISTORY_XML_FILE = "/reports/xml/creditHistory.xml";

    //public static final String RPT_HISTORY_XSL_FILE = "/reports/xml/creditHistory.xsl";

    //public static final String RPT_HISTORY_OUT_FILE = "/reports/xml/creditHistory.pdf";

}
