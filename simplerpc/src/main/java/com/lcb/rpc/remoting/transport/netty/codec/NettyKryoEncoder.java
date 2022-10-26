package com.lcb.rpc.remoting.transport.netty.codec;

import com.lcb.rpc.serialize.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
   * @Author lcb
   * @Description 自定义编码器。负责处理出栈消息，将消息格式转换为字节数组然后写入到字节数据的容器ByteBuf对象中，
   *              网络传输需要通过字节流来实现，ByteBuf可以看作是Netty提供的字节数据容器，使用它可以更方便的处理字节数据
   * @Date 2022/10/23
   * @Param
   * @return
   **/
@Slf4j
@AllArgsConstructor
public class NettyKryoEncoder extends MessageToByteEncoder<Object> {

    private Serializer serializer;
    private Class<?> genericClass;

    /**
      * @Description 将对象转换为字节码然后写入到ByteBuf对象中
      **/
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out){
        if(genericClass.isInstance(msg)){
            //1、将对象转换为byte
            byte[] body=serializer.serializer(0);
            //2、读取消息的长度
            int dataLength=body.length;
            //3、写入消息对应的字节数组长度，writerIndex加4
            out.writeInt(dataLength);
            //4、将字节数组写入ByteBuf对象中
            out.writeBytes(body);
        }
    }
}
