package com.magic.utils;
/**
 * 连接池
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
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
   //判断是否已经关闭了工厂，那就退出监听
   if (cf.isCreate())
    cf.schedule();
   else
    System.exit(1);
  }
 }  
}