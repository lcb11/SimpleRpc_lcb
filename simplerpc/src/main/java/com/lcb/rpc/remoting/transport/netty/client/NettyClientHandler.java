package com.lcb.rpc.remoting.transport.netty.client;

import com.lcb.enumeration.RpcMessageTypeEnum;
import com.lcb.factory.SingletonFactory;
import com.lcb.rpc.remoting.dto.RpcRequest;
import com.lcb.rpc.remoting.dto.RpcResponse;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 *
   * @Author lcb
   * @Description 自定义客户端ChanelHandler处理服务端发送过来的数据
   * @Date 2022/10/26
   * @Param
   * @return
   **/
@Slf4j
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    private final UnprocessedRequest unprocessedRequest;

    public NettyClientHandler() {
        this.unprocessedRequest= SingletonFactory.getInstance(UnprocessedRequest.class);
    }

    /**
      * @Description 读取服务端消息
      **/
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            log.info("client receive msg:[{}]",msg);
            RpcResponse rpcResponse=(RpcResponse)msg;
            unprocessedRequest.complete(rpcResponse);
        }finally {
            ReferenceCountUtil.release(msg);
        }
    }

    /**
      * @Description 心跳事件捕捉
      **/
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            IdleState state = ((IdleStateEvent) evt).state();
            if(state==IdleState.WRITER_IDLE){
                log.info("write idle happen [{}]",ctx.channel().remoteAddress());
                Channel channel = ChannelProvider.get((InetSocketAddress) ctx.channel().remoteAddress());
                RpcRequest.RpcRequestBuilder rpcRequest = RpcRequest.builder().rpcMessageTypeEnum(RpcMessageTypeEnum.HEART_BEAT);
                channel.writeAndFlush(rpcRequest).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);

            }
        }else {
            super.userEventTriggered(ctx,evt);
        }
    }

    /**
      * @Description 客户端发生异常时调用
      **/
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("client catch exception",cause);
        cause.printStackTrace();
        ctx.close();
    }
}
