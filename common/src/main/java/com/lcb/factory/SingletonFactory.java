package com.lcb.factory;

import java.util.HashMap;
import java.util.Map;

/**
 *
   * @Author lcb
   * @Description 获取单列对象的工厂类
   * @Date 2022/10/26
   * @Param
   * @return
   **/
public final class SingletonFactory {
    private static Map<String,Object> objectMap=new HashMap<>();

    private SingletonFactory(){

    }

    public static <T> T getInstance(Class<T> c){
        String key = c.toString();
        Object instance=objectMap.get(key);
        synchronized (c){
            if(instance==null){
                try {
                    instance = c.newInstance();
                    objectMap.put(key,instance);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e.getMessage(),e);
                } catch (InstantiationException e) {
                    throw new RuntimeException(e.getMessage(),e);
                }
            }
        }
        return c.cast(instance);
    }
}
