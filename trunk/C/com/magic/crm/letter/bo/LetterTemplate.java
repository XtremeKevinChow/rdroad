package com.magic.crm.letter.bo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * �ʼ�ģ�������
 * 
 * @author user
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public abstract class LetterTemplate {
    
    /** �ʼ����� **/
    private String title = null;
    
    /** �ʼ����� **/
    private String template = null;
    
    /** �ռ��˵�ַ **/
    private String email = null;
    
    /** ��ǩ��Ӧ��ֵ�������ݿ�����ȡ **/
    protected Map map = null;

    public LetterTemplate() {
        map = new HashMap(); 
    }

    /**
     * @return String
     * @roseuid 44220CAB00B5
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * @return String
     * @roseuid 44220CC701C4
     */
    public String getTemplate() {
        return this.template;
    }

    /**
     * @return String
     * @roseuid 44220CD80092
     */
    public String getEmail() {
        return this.email;
    }
    
    /**
     * @param email The email to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * @param template The template to set.
     */
    public void setTemplate(String template) {
        this.template = template;
    }
    /**
     * @param title The title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * @return String
     * @roseuid 44220CD80092
     */
    public abstract void setValue(Connection con, int orderID) throws SQLException;
    
    /**
     * @return String
     * @roseuid 44220CD80092
     */
    public abstract String replaceTitle();
    
    /**
     * @return String
     * @roseuid 44220CD80092
     */
    public abstract String replaceBody();
    
    
    /**
     * ģ�淽���������滻
     * @return String
     * @roseuid 44220CD80092
     */
    final public void buildTemplate(Connection con, int orderID) throws SQLException {
        setValue(con, orderID);
        this.title = replaceTitle();
        this.template = replaceBody();
    }
    
    /**
     * ��ǩ�滻���÷���
     * @param replaceStr
     * @param label
     * @param map
     * @return
     */
    protected String replace(String replaceStr, String[] label) {
        
        for (int i = 0; i < label.length; i++) {
            if (replaceStr.indexOf(label[i]) != -1) {
                String mapValue = (String) map.get(label[i]);
                if (mapValue == null || mapValue.equals("null")) {
                    mapValue = "";
                }
                
                replaceStr = replaceStr.replaceAll(label[i], mapValue);
            }
        }
        return replaceStr;
    }

    
    
   
}