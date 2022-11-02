package com.lcb.exception;


import com.lcb.enumeration.RpcErrorMessageEnum;

/**
 *
   * @Author lcb
   * @Description
   * @Date 2022/11/2
   * @Param
   * @return
   **/
public class RpcException extends RuntimeException {
    public RpcException(RpcErrorMessageEnum rpcErrorMessageEnum, String detail) {
        super(rpcErrorMessageEnum.getMessage()+":"+detail);
    }

    public RpcException(String message,Throwable cause){
        super(message,cause);
    }

    public RpcException(RpcErrorMessageEnum rpcErrorMessageEnum){
        super(rpcErrorMessageEnum.getMessage());
    }
}
