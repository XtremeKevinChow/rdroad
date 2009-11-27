package com.magic.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.text.Format;
/**
 * ��ֵ����
 * @author magic
 * @Vender �Ϻ��������ϵͳ���޹�˾ (magic Software System Co.,ltd)
 */
public class Arith {
    
    //Ĭ�ϳ������㾫��
    private static final int DEF_DIV_SCALE = 10;

    //����಻��ʵ����
    private Arith(){
    }
    /**
     * �ṩ��ȷ�ļӷ�����
     * @param v1 ������
     * @param v2 ����
     * @return ���������ĺ�
     */
    public static float add(float v1,float v2){
        BigDecimal b1 = new BigDecimal(Float.toString(v1));
        BigDecimal b2 = new BigDecimal(Float.toString(v2));
        return b1.add(b2).floatValue();
    }
  /**
   * �ṩ��ȷ�ļӷ����㣬��scale����ָ
   * �����ȣ��Ժ��������������
   * @param v1 ������
   * @param v2 ����
   * @param scale ��ʾ��Ҫ��ȷ��С�����Ժ�λ
   * @return ���������ĺ�
   */
    public static float add(float v1,float v2,int scale){
        BigDecimal b1 = new BigDecimal(Float.toString(v1));
        BigDecimal b2 = new BigDecimal(Float.toString(v2));
        return round(b1.add(b2).floatValue(),scale);
    }    

    /**
     * �ṩ��ȷ�ļ������㡣
     * @param v1 ������
     * @param v2 ����
     * @return ���������Ĳ�
     */
    public static float sub(float v1,float v2){
        BigDecimal b1 = new BigDecimal(Float.toString(v1));
        BigDecimal b2 = new BigDecimal(Float.toString(v2));
        return b1.subtract(b2).floatValue();
    }
    
    /**
     * �ṩ��ȷ�ļ������㡣
     * @param v1 ������
     * @param v2 ����
     * @return ���������Ĳ�
     */
    public static double sub(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }
    
  /**
   * �ṩ��ȷ�ļ������㡣��scale����ָ
   * �����ȣ��Ժ��������������
   * @param v1 ������
   * @param v2 ����
   * @param scale ��ʾ��Ҫ��ȷ��С�����Ժ�λ
   * @return ���������Ĳ�
   */
    public static float sub(float v1,float v2,int scale){
        BigDecimal b1 = new BigDecimal(Float.toString(v1));
        BigDecimal b2 = new BigDecimal(Float.toString(v2));
        return round(b1.subtract(b2).floatValue(),scale);
    }    
    
    /**
     * �ṩ��ȷ�ĳ˷�����
     * @param v1 ������
     * @param v2 ����
     * @return ���������Ļ�
     */
    public static double mul(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }
    
    /**
     * �ṩ����ԣ���ȷ�ĳ������㣬�����������������ʱ����ȷ��
     * С�����Ժ�10λ���Ժ��������������
     * @param v1 ������
     * @param v2 ����
     * @return ������������
     */
    public static float div(float v1,float v2){
        return div(v1,v2,DEF_DIV_SCALE);
    }
    
    /**
     * �ṩ����ԣ���ȷ�ĳ������㡣�����������������ʱ����scale����ָ
     * �����ȣ��Ժ��������������
     * @param v1 ������
     * @param v2 ����
     * @param scale ��ʾ��Ҫ��ȷ��С�����Ժ�λ
     * @return ������������
     */
    public static float div(float v1,float v2,int scale){
        if(scale<0){
            throw new IllegalArgumentException(
                "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Float.toString(v1));
        BigDecimal b2 = new BigDecimal(Float.toString(v2));
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).floatValue();
    }
    /**
     * �ṩ����ԣ���ȷ�ĳ������㡣�����������������ʱ����scale����ָ
     * �����ȣ��Ժ��������������
     * @param v1 ������
     * @param v2 ����
     * @param scale ��ʾ��Ҫ��ȷ��С�����Ժ�λ
     * @return ������������
     */
    public static double div(double v1,double v2,int scale){
        if(scale<0){
            throw new IllegalArgumentException(
                "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    /**
     * �ṩ��ȷ��С��λ�������봦��
     * @param v ��Ҫ�������������
     * @param scale С���������λ
     * @return ���������Ľ��
     */
    public static float round(float v,int scale){
        if(scale<0){
            throw new IllegalArgumentException(
                "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Float.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).floatValue();
    }
    
    /**
     * �ṩ��ȷ��С��λ�������봦��(add by magic 2005-12-29 10:37)
     * @param v ��Ҫ�������������
     * @param scale С���������λ
     * @return ���������Ľ��
     */
    public static double round(double v,int scale){
        if(scale<0){
            throw new IllegalArgumentException(
                "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    /**
     * �ṩ��ȷ��С��λ�ᴦ��
     * @param v ��Ҫ�������������
     * @param scale С���������λ
     * @return 
     */
    public static double roundX(double v,int scale){
        if(scale<0){
            throw new IllegalArgumentException(
                "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one,scale,BigDecimal.ROUND_DOWN).doubleValue();
    }
    /**
     * �ṩ��ȷ��С��λ�ᴦ��
     * @param v ��Ҫ�������������
     * @param scale С���������λ
     * @return ���������Ľ��
     */
    public static float roundX(float v,int scale){
        if(scale<0){
            throw new IllegalArgumentException(
                "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Float.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one,scale,BigDecimal.ROUND_DOWN).floatValue();
    }
    /**formatString
     * �ṩ���ָ�ʽ��
     * @param valueToFormat ��Ҫ��ʽ���Ķ���
     * @param formatString ��ʽ������ʽ
     * @return ��������ֵ��String
     */
     
  public static String formatValue(Object valueToFormat,String formatString){
    Format format = null;
    Object value = valueToFormat;
    if(value instanceof Number){
      format = NumberFormat.getNumberInstance();
      ((DecimalFormat)format).applyLocalizedPattern(formatString);
    }
    if(format!=null){
      return format.format(value);
    } else {
      return value.toString();
    }    
  }
}