package com.lcb.exception;

/**
 *
   * @Author lcb
   * @Description 序列化异常定义
   * @Date 2022/10/17
   * @Param
   * @return
   **/
public class SerializeException extends RuntimeException {

    public SerializeException(String message){
        super(message);
    }
}
