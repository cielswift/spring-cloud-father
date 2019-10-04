//package com.ciel.config;
//
//import com.netflix.client.config.IClientConfig;
//import com.netflix.loadbalancer.*;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.annotation.PostConstruct;
//import java.util.List;
//
//@Configuration
//public class MyIRule {  //不能在启动类,同包或者子包下, 因为如果被@ComponetScan扫描到会导致所有的RibbonClient都去共享这个配置。
//
//
//    @Bean
//    public IRule iRule() {
//        return new AbstractLoadBalancerRule() {
//            private int count = 0;//调用次数
//            private int currentIndex = 0;//当前服务下标
//
//            @SuppressWarnings({"RCN_REDUNDANT_NULLCHECK_OF_NULL_VALUE"})
//            public Server choose(ILoadBalancer lb, Object key) {
//                if (lb == null) {
//                    return null;
//                } else {
//                    Server server = null;
//
//                    while(server == null) {
//                        if (Thread.interrupted()) {
//                            return null;
//                        }
//
//                        List<Server> upList = lb.getReachableServers();
//                        List<Server> allList = lb.getAllServers();
//                        int serverCount = allList.size();
//                        if (serverCount == 0) {
//                            return null;
//                        }
//
//                        //int index = this.rand.nextInt(serverCount);
//                        //server = (Server)upList.get(index);
//
//                        if(count < 5){
//                            count++;
//                            server = upList.get(currentIndex);
//                        }else {
//                            count = 0;
//                            currentIndex++;
//                            if (currentIndex >= serverCount){
//                                currentIndex = 0;
//                            }
//                        }
//
//                        if (server == null) {
//                            Thread.yield();
//                        } else {
//                            if (server.isAlive()) {
//                                return server;
//                            }
//
//                            server = null;
//                            Thread.yield();
//                        }
//                    }
//
//                    return server;
//                }
//            }
//
//            public Server choose(Object key) {
//                return this.choose(this.getLoadBalancer(), key);
//            }
//            public void initWithNiwsConfig(IClientConfig clientConfig) {
//
//            }
//
//        };
//
//    }
//}
