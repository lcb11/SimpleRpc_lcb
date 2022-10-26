package com.lcb.rpc.remoting.transport.netty.client;

import com.lcb.factory.SingletonFactory;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
   * @Author lcb
   * @Description 用于获取channel对象
   * @Date 2022/10/26
   * @Param
   * @return
   **/
@Slf4j
public  final class ChannelProvider {
    private static Map<String, Channel> channels=new ConcurrentHashMap<>();
    private static NettyClient nettyClient;

    static {
        nettyClient= SingletonFactory.getInstance(NettyClient.class);
    }

    private ChannelProvider(){

    }

    public static Channel get(InetSocketAddress inetSocketAddress){
        String key = inetSocketAddress.toString();
        //判断是否有对应地址的连接
        if(channels.containsKey(key)){
            Channel channel = channels.get(key);
            //如果有连接，判断连接是否可用，可用的话直接获取
            if(channel!=null&&channel.isActive()){
                return channel;
            }else {
                channels.remove(key);
            }
        }
        //否则，重新获取channel
        Channel channel = nettyClient.doConnect(inetSocketAddress);
        channels.put(key,channel);
        return channel;
    }

    public static void remove(InetSocketAddress inetSocketAddress){
        String key = inetSocketAddress.toString();
        channels.remove(key);
        log.info("channel map size [{}]",channels.size());
    }
}
