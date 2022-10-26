package com.lcb.rpc.remoting.transport.netty.client;

import com.lcb.rpc.remoting.dto.RpcResponse;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
   * @Author lcb
   * @Description 为处理的请求
   * @Date 2022/10/26
   * @Param
   * @return
   **/
public class UnprocessedRequest {
    private static Map<String, CompletableFuture<RpcResponse>> unprocessedResponseFutures =new ConcurrentHashMap<>();

    public void put(String requestId,CompletableFuture<RpcResponse> future){
        unprocessedResponseFutures.put(requestId,future);
    }

    public void complete(RpcResponse rpcResponse){
        CompletableFuture<RpcResponse> future = unprocessedResponseFutures.remove(rpcResponse.getRequestId());
        if(future==null){
            future.complete(rpcResponse);
        }else {
            throw new IllegalStateException();
        }
    }
}
