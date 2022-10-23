package com.lcb.rpc.remoting.transport.netty.codec;

import com.lcb.rpc.remoting.dto.RpcResponse;
import com.lcb.rpc.serialize.Serializer;
import com.lcb.rpc.serialize.kyro.KryoSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
   * @Author lcb
   * @Description 自定义解码器，负责处理入栈消息，将消息格式化为需要的对象
   * @Date 2022/10/23
   * @Param
   * @return
   **/
@Slf4j
@AllArgsConstructor
public class NettyKryoDecoder extends ByteToMessageDecoder {

    private Serializer serializer;
    private Class<?> genericClass;

    /**
      *Netty传输的消息长度也就是对象序列化后对应的字节数组大小，存储在ByteBuf的头部
      */
    private static final int BODY_LENGTH=4;


    /**
      * @Author lcb
      * @Description 解码ByteBuf对象
      * @Date 2022/10/23
      * @Param [ctx, in, out]  ctx:解码器关联的ChannelHandlerContext对象  in：入栈数据，ByteBuf对象，  out:解码之后的数据对象添加到out对象里面
      * @return void
      **/
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //1、byteBuf中写入的消息长度已经是4了，所以byteBuf的可读字节必须大于4
        if(in.readableBytes()>=BODY_LENGTH){
            //2、标记当前readIndex的位置，以便后面重置readIndex的时候使用
            in.markReaderIndex();
            //3、读取消息长度
            //消息长度在encode的时候已经写入了
            int dataLength=in.readInt();
            //4、遇到不合理的情况直接return
            if(dataLength<0||in.readableBytes()<0){
                log.error("data length or byteBuf readableBytes is not valid");
                return;
            }
            //5、如果可读字节小于消息长度的话，说明是不完整的消息长度，重置readIndex
            if(in.readableBytes()<dataLength){
                in.resetReaderIndex();
                return;
            }
            //6、到了这里说明没有什么问题了，可以序列化了
            byte[] boty=new byte[dataLength];
            in.readBytes(boty);
            //将bytes转换为需要的对象
            Object obj=serializer.deserializer(boty,genericClass);
            out.add(obj);
            log.info("sucessful decode ByteBuf to Object");
        }

    }
}
