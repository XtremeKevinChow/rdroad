package com.magic.utils;

import java.util.*;
/**
 * ��ȡϵͳ�����ļ�
 * @author magic
 * @Vender �Ϻ��������ϵͳ���޹�˾ (magic Software System Co.,ltd)
 */
public class ApplicationProperties {

  private Properties props = new Properties();
  /**
   * �õ�����
   * @return  Properties
   */
  public Properties getProperties() {

    Properties fileProps = PropertiesFileHandler.getProperties();
    for (Enumeration e = fileProps.propertyNames(); e.hasMoreElements(); ) {
      String propName = (String) e.nextElement();
      props.setProperty(propName, fileProps.getProperty(propName));
    }
    return props;
  }

  public ApplicationProperties() {
    getProperties();
  }
  /**
   * �õ�long ����ֵ
   * @param name ��������
   * @param defaultValue Ĭ��ֵ��������ʱ����Ĭ��ֵ
   * @return 
   */
  public long getLongProperty(String name, long defaultValue) {
    String value = props.getProperty(name);
    try {
      return (value == null ? defaultValue : Long.parseLong(value));
    }
    catch (NumberFormatException nfe) {
      return defaultValue;
    }
  }
  /**
   * �õ�����ֵ
   * @param name ��������
   * @return ����ֵ
   */
  public String getProperty(String name) {
    return props.getProperty(name);
  }
  
  /**
   * �õ�����ֵ�����û�ҵ�����Ĭ��ֵ
   * @param name ��������
   * @param defaultValue  Ĭ��ֵ
   * @return ֵ�ַ���
   */
  public String getProperty(String name, String defaultValue) {
    return props.getProperty(name, defaultValue);
  }
  /**
   * �õ�boolean���͵�����ֵ����������ڷ���Ĭ��ֵ
   * @param name ��������
   * @param defaultValue Ĭ��ֵ
   * @return ֵ(boolean����)
   */
  public boolean getBooleanProperty(String name, boolean defaultValue) {
    String value = props.getProperty(name);
    return (value == null ? defaultValue : new Boolean(value).booleanValue());
  }

}