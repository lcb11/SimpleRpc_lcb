package com.lcb.rpc.registry;

import java.net.InetSocketAddress;

/**
  * @Author lcb
  * @Description 服务注册接口
  * @Date 2022/11/2
  * @Param
  * @return
  **/
public interface ServiceRegistry {

    /**
     * @Description 注册服务
     *
     * servername:服务名称
     * inetSocketAddress：提供服务的地址
      **/
    void registerService(String servername, InetSocketAddress inetSocketAddress);
}
