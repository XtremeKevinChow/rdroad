package com.magic.utils;
/**
 * ���ӳ�
 * @author magic
 * @Vender �Ϻ��������ϵͳ���޹�˾ (magic Software System Co.,ltd)
 */
public class FactoryMangeThread implements Runnable{
  public FactoryMangeThread(){}
 ConnectionFactory cf = null;
 long delay = 1000;
 public FactoryMangeThread(ConnectionFactory obj)
 {
  cf = obj;
 }
 
 public void run() {
  while(true){
   try{
    Thread.sleep(delay);
   }
   catch(InterruptedException e){}
   System.out.println("eeeee");
   //�ж��Ƿ��Ѿ��ر��˹������Ǿ��˳�����
   if (cf.isCreate())
    cf.schedule();
   else
    System.exit(1);
  }
 }  
}