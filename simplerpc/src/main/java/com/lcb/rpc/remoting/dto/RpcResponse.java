package com.lcb.rpc.remoting.dto;

import lombok.*;

import java.io.Serializable;

/**
 *
   * @Author lcb
   * @Description rpc响应消息
   * @Date 2022/10/16
   * @Param
   * @return
   **/
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RpcResponse<T> implements Serializable {

    private static final long serialVersionUID=23458943576090956L;
    private String requestId;//请求id
    /**
     *消息响应码；成功或者失败
     */
    private Integer code;
    /**
     *响应消息;请求调用是否成功返回消息
     */
    private String message;
    /**
     *响应数据，返回给客户端
     */
    private T data;



}
