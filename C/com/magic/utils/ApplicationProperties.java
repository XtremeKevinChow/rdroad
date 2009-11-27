package com.magic.utils;

import java.util.*;
/**
 * 读取系统属性文件
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */
public class ApplicationProperties {

  private Properties props = new Properties();
  /**
   * 得到属性
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
   * 得到long 属性值
   * @param name 属性名称
   * @param defaultValue 默认值，不存在时返回默认值
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
   * 得到属性值
   * @param name 属性名称
   * @return 属性值
   */
  public String getProperty(String name) {
    return props.getProperty(name);
  }
  
  /**
   * 得到属性值，如果没找到返回默认值
   * @param name 属性名称
   * @param defaultValue  默认值
   * @return 值字符串
   */
  public String getProperty(String name, String defaultValue) {
    return props.getProperty(name, defaultValue);
  }
  /**
   * 得到boolean类型的属性值，如果不存在返回默认值
   * @param name 属性名称
   * @param defaultValue 默认值
   * @return 值(boolean类型)
   */
  public boolean getBooleanProperty(String name, boolean defaultValue) {
    String value = props.getProperty(name);
    return (value == null ? defaultValue : new Boolean(value).booleanValue());
  }

}