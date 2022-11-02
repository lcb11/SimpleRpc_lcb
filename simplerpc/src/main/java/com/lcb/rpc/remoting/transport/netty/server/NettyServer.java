package com.lcb.rpc.remoting.transport.netty.server;

import com.lcb.rpc.registry.ServiceRegistry;
import com.lcb.rpc.serialize.kyro.KryoSerializer;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import lombok.extern.slf4j.Slf4j;

/**
 *
   * @Author lcb
   * @Description 服务端、接受客户端消息，并且根据客户端的消息调用相应的方法，然后返回给客户端
   * @Date 2022/11/2
   * @Param
   * @return
   **/
@Slf4j
public class NettyServer {
    private String host;
    private int port;
    private KryoSerializer kryoSerializer;
    private ServiceRegistry serviceRegisty;
    private PolicyUtils.ServiceProvider serviceProvider;
}
