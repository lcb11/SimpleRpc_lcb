package com.lcb.rpc.serialize.kyro;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.lcb.exception.SerializeException;
import com.lcb.rpc.remoting.dto.RpcRequest;
import com.lcb.rpc.remoting.dto.RpcResponse;
import com.lcb.rpc.serialize.Serializer;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


/**
 *
   * @Author lcb
   * @Description Kryo序列化类
   * @Date 2022/10/17
   * @Param
   * @return
   **/
@Slf4j
public class KryoSerializer implements Serializer {

    private final  ThreadLocal<Kryo> kryoThreadLocal=ThreadLocal.withInitial(()->{
        Kryo kryo = new Kryo();
        kryo.register(RpcResponse.class);
        kryo.register(RpcRequest.class);
        //默认值为true，即是否关闭注册行为，关闭之后可能存在序列化问题，一般设置为true
        kryo.setReferences(true);
        //默认值为false，是否关闭循环应用，可以提高性能，一般设置为false
        kryo.setRegistrationRequired(false);
        return kryo;
    });

    @Override
    public byte[] serializer(Object obj) {
        try(ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            Output output=new Output(byteArrayOutputStream)){
            Kryo kryo = kryoThreadLocal.get();
            //将传入的对象序列化为byte数组
            kryo.writeObject(output,obj);
            kryoThreadLocal.remove();
            return output.toBytes();
        } catch (IOException e) {
            throw new SerializeException("序列化失败");
        }
    }

    @Override
    public <T> T deserializer(byte[] bytes, Class<T> classes) {
        try(ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(bytes);
            Input input=new Input(byteArrayInputStream)){
            Kryo kryo = kryoThreadLocal.get();
            //将bate数组反序列化为对象
            Object t = kryo.readObject(input, classes);
            kryoThreadLocal.remove();
            return classes.cast(t);
        } catch (IOException e) {
            throw new SerializeException("反序列化失败");
        }
    }
}
