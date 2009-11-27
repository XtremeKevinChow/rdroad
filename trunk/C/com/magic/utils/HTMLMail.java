package com.magic.utils;

import java.util.Properties;
import java.util.Date;
import java.util.ArrayList;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
/**
 * HtmlMail处理基类
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
 */
class HtmlMail extends Mail{
    private ArrayList arrayList1 = new ArrayList();
    private ArrayList arrayList2 = new ArrayList();

    public HtmlMail(String smtpHost,String username,String password){
        super(smtpHost,username,password);
        multipart = new MimeMultipart("related");
    }

    public void setMailContent(String mailContent)throws MessagingException{
        //System.out.println("邮件原文:\n"+mailContent);
        String htmlContent = getContent("<img border=0 src=",mailContent);
        System.out.println("邮件内容:");
        //System.out.println(htmlContent);//1
        messageBodyPart.setContent(htmlContent,"text/html");
        multipart.addBodyPart(messageBodyPart);
        //调用处理html文件中的图片方法
        processHtmlImage(mailContent);
    }
    //处理html页面上的图片方法如下：
    private void processHtmlImage(String mailContent)throws MessagingException{
         for(int i=0;i<arrayList1.size();i++){
             messageBodyPart = new MimeBodyPart();
             DataSource source = new FileDataSource((String)arrayList1.get(i));
             //System.out.println("arrayList1.get(i)="+(String)(arrayList1.get(i)));
             messageBodyPart.setDataHandler(new DataHandler(source));
             String contentId = "<"+(String)arrayList2.get(i)+">";
             //System.out.println(contentId);
             messageBodyPart.setHeader("Content-ID",contentId);
             messageBodyPart.setFileName((String)arrayList1.get(i));
             multipart.addBodyPart(messageBodyPart);
         }
    }
    //处理要发送的html文件，主要是针对html文件中的图片
    private String getContent(String searchString,String mailContent){
        String afterReplaceStr = "";
        int i=0;
        int j=0;
        i=mailContent.indexOf(searchString,i);
       // System.out.println(i);
        while(i>0)
        {
              j=  mailContent.indexOf('>',i);
              String replaceStr =  mailContent.substring(i+ searchString.length()+1,j-1);
              //System.out.println(replaceStr);
              if(replaceStr.indexOf("http://") != -1){
                    //System.out.println(replaceStr);
                   // System.out.println("不需要处理图片！");
              }
              else{
                    arrayList1.add(replaceStr);
              }
              i=mailContent.indexOf(searchString,j);
        }
        afterReplaceStr =mailContent;
        //在html文件中用"cid:"+Content-ID来替换原来的图片链接
        for(int m=0;m<arrayList1.size();m++){
            arrayList2.add(createRandomStr());
            String addString = "cid:"+(String)arrayList2.get(m);
           // System.out.println((String)arrayList1.get(m));
           // System.out.println(addString);
          //  afterReplaceStr = mailContent.replaceAll((String)arrayList1.get(m),addString);
            afterReplaceStr = StringUtil.replaceStrEx(afterReplaceStr,(String)arrayList1.get(m),addString);
        }
        return afterReplaceStr;
    }
   private String createRandomStr(){
        char []randomChar = new char[8];
        for(int i=0;i<8;i++){
            randomChar[i]=(char)(Math.random()*26+'a');
        }
        String replaceStr = new String(randomChar);
        return replaceStr;
    }

}
