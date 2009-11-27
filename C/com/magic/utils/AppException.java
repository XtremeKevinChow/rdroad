package com.magic.utils;
/**
 * 系统异常处理类
 * @author magic
 * @Vender 上海轶轮软件系统有限公司 (magic Software System Co.,ltd)
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
   * 得到错误编码
   * @return 
   */
  public String getErrorCode() {
    return this.code;
  }
}