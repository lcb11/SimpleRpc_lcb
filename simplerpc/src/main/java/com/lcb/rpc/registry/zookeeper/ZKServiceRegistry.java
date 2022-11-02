package com.lcb.rpc.registry.zookeeper;

import com.lcb.rpc.registry.ServiceRegistry;

import java.net.InetSocketAddress;

/**
 *
   * @Author lcb
   * @Description 基于zookeeper实现服务注册
   * @Date 2022/11/2
   * @Param
   * @return
   **/
public class ZKServiceRegistry implements ServiceRegistry {
    @Override
    public void registerService(String servername, InetSocketAddress inetSocketAddress) {
        //根节点下注册子节点：服务
        //String servicePath=
    }
}
