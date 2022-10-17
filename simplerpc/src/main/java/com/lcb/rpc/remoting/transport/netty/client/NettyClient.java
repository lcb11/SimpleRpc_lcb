package com.lcb.rpc.remoting.transport.netty.client;

import com.lcb.rpc.serialize.kyro.KryoSerializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 *
   * @Author lcb
   * @Description 初始化netty客户端包括处理连接线程bootstrap和eventLoopGroup处理事件线程
   * @Date 2022/10/17
   * @Param
   * @return
   **/
@Slf4j
public class NettyClient {
    /**
      *  用于处理客户端连接请求
      */
    private static Bootstrap bootstrap;
    /**
      * 用于处理事件请求--->真正干活的线程
      */
    private static EventLoopGroup eventLoopGroup;

    //静态代码块初始化相关的资源bootstrap和eventLoopGroup
    static {
        bootstrap=new Bootstrap();
        eventLoopGroup=new NioEventLoopGroup();
        KryoSerializer kyroSerializer=new KryoSerializer();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                //TODO
                .handler(new LoggingHandler(LogLevel.INFO))
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,5000)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        //如果15s之内没有发送数据给服务端的话，就发送一次心跳请求
                        ch.pipeline().addLast(new IdleStateHandler(0,5,0, TimeUnit.SECONDS));
                        //TODO 自定义序列化的编解码器

                        //TODO 自定义Handler

                    }
                });

    }

    @SneakyThrows
    public Channel doConnect(InetSocketAddress inetSocketAddress){
        CompletableFuture<Channel> completableFuture=new CompletableFuture<>();
        bootstrap.connect(inetSocketAddress).addListener((ChannelFutureListener)future->{
            if(future.isSuccess()){
                log.info("客户端连接成功");
                completableFuture.complete(future.channel());
            }else {
                throw new IllegalStateException();
            }
        });
        return completableFuture.get();
    }

    public void close(){
        log.info("call close method");
        eventLoopGroup.shutdownGracefully();
    }
}
