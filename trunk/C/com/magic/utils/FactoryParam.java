package com.magic.utils;
/**
 * ���ӳع�������Bean
 * @author magic
 * @Vender �Ϻ��������ϵͳ���޹�˾ (magic Software System Co.,ltd)
 */
public class FactoryParam {
//���������
 private int MaxConnectionCount = 30;
 //��С������
 private int MinConnectionCount = 5; 
 //���ղ���
 private int ManageType = 0;
 
 public FactoryParam() {
 }
 
 /**
  * �������ӳع��������Ķ���
  * @param max ���������
  * @param min ��С������
  * @param type �������
  */
 public FactoryParam(int max, int min, int type)
 {
  this.ManageType = type;
  this.MaxConnectionCount = max;
  this.MinConnectionCount = min;
 }
 
 /**
  * ��������������
  * @param value
  */
 public void setMaxConn(int value)
 {
  this.MaxConnectionCount = value;
 }
 /**
  * ��ȡ���������
  * @return
  */
 public int getMaxConn()
 {
  return this.MaxConnectionCount;
 }
 /**
  * ������С������
  * @param value
  */
 public void setMinConn(int value)
 {
  this.MinConnectionCount = value;
 }
 /**
  * ��ȡ��С������
  * @return
  */
 public int getMinConn()
 {
  return this.MinConnectionCount;
 }
 public int getType()
 {
  return this.ManageType;
 }
}