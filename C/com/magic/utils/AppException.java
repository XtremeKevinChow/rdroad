package com.magic.utils;
/**
 * ϵͳ�쳣������
 * @author magic
 * @Vender �Ϻ��������ϵͳ���޹�˾ (magic Software System Co.,ltd)
 */
public class AppException
  extends Exception {

  private String code = null;
  public AppException(String code) {
    super();
    this.code = code;
  }

  public AppException(String code, Throwable cause) {
    //super(cause);
    this.code = code;
  }
  /**
   * �õ��������
   * @return 
   */
  public String getErrorCode() {
    return this.code;
  }
}