# SimpleRpc_lcb
简易rpc框架，基于Netty+Zookeeper+Kyro

![image](https://user-images.githubusercontent.com/46276651/225788651-e53b3ffc-0b10-46e9-94f3-9c42856ab98e.png)



- [x] 基于 Netty 的长连接式 RPC，包括心跳保持、断连重连、解决粘包拆包实现请求鉴权、分组治理、IP 直连以及简单的灰度发布。
- [x] 使用 Zookeeper 作为注册中心，根据内部节点变化来实现服务权重属性的动态更新，并实现了基于一致性哈希、随机哈希的负载均衡算法。
- [x] 自定义客户端服务端通信协议，实现了包括 Kyro 等三种序列化方式以及基于 Gzip 的压缩算法。
- [x] 扩展 Java 原生的 SPI 机制，实现可拔插式扩展组件。
- [x] 通过对 CompletableFuture 的支持，实现调用端和服务端完全异步，提高单机吞吐量。
- [x] 通过自定义注解，简化服务注册和消费的部分代码，降低业务入侵度，自定义 starter 完成与 Spring 的接入，完成自动装配。
