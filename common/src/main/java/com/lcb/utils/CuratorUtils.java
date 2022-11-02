package com.lcb.utils;

import com.lcb.exception.RpcException;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
   * @Author zookeeper工具
   * @Description zo
   * @Date 2022/11/2
   * @Param
   * @return
   **/
@Slf4j
public class CuratorUtils {
    private static final int BASE_SLEEP_TIME=1000;
    private static final int MAX_RETRIES=5;
    private static final String CONNECT_STRING="192.168.248.135";
    public static final String ZK_REGISTER_ROOT_PATH="/myrpc";
    private static Map<String, List<String>> serviceAddressMap=new ConcurrentHashMap<>();
    private static Set<String> registeredPathSet=ConcurrentHashMap.newKeySet();
    private static CuratorFramework zkClient;

    static {
        zkClient=getZkClient();
    }


    private CuratorUtils(){

    }

    /**
      * @Description 创建持久化节点，不同于临时节点，持久化节点不会因为客户端断开连接而被删除
      **/
    public static void createPersistentNode(String path)  {
        try{
            if(registeredPathSet.contains(path)||zkClient.checkExists().forPath(path)!=null){
                log.info("节点已经存在，节点为：[{}]",path);
            }else {
                // /myrpc/path
                zkClient.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path);
                log.info("节点创建成功，节点为[{}]",path);
            }
            registeredPathSet.add(path);
        } catch (Exception e) {
            throw new RpcException(e.getMessage(),e.getCause());
        }

    }

    private static CuratorFramework getZkClient() {
        //重试策略，重试3次，并且增加重试之间的睡眠时间
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(BASE_SLEEP_TIME, MAX_RETRIES);
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                //要连接的服务器
                .connectString(CONNECT_STRING)
                .retryPolicy(retryPolicy)
                .build();
        curatorFramework.start();
        return curatorFramework;
    }
}
