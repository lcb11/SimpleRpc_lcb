package com.lcb.rpc.remoting.dto;

import com.lcb.enumeration.RpcMessageTypeEnum;
import lombok.*;

import java.io.Serializable;

/**
 *
   * @Author lcb
   * @Description Rpc请求消息
   * @Date 2022/10/16
   * @Param
   * @return
   **/
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
public class RpcRequest implements Serializable {
    private static  final  long serialVersionUID = 1456786092104978L;

    private String requestId;//请求id
    private String interfaceName;//请求接口名字：类名/接口名
    private String methodName;//请求方法名字
    private Object[] parameters;//请求参数名字
    private Class<?>[] paramTypes;//请求参数类型
    private RpcMessageTypeEnum rpcMessageTypeEnum;//请求消息类型；是否为心跳消息

}
