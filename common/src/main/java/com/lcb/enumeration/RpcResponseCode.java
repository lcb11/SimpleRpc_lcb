package com.lcb.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 *
   * @Author lcb
   * @Description 返回消息枚举类型
   * @Date 2022/10/17
   * @Param
   * @return
   **/
@AllArgsConstructor
@Getter
@ToString
public enum  RpcResponseCode {
    SUCCESS(200,"方法调用成功"),
    FAIL(500,"方法调用失败");


    private final  int code;

    private final String message;
}
