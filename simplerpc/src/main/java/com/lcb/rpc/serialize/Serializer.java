package com.lcb.rpc.serialize;

/**
  * @Author lcb
  * @Description 序列化接口，所有的序列化协议都通过实现这个接口实现
  * @Date 2022/10/17
  * @Param
  * @return
  **/
public interface Serializer {

    /**
      * 序列化
      **/
    byte[] serializer(Object obj);


    /**
      *反序列化
      **/
    <T> T deserializer(byte[] bytes,Class<T> classes);
}
