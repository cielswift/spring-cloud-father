package com.ciel.springcloudconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient //开启发现服务
@EnableEurekaClient //服务注册
//@RibbonClient(name="SPRINGCLOUD-PROVIDER",configuration = MyIRule.class) //自定义负载均衡

@ComponentScan(basePackages = {"com.ciel.entity","com.ciel.service","com.ciel.springcloudconsumer"})

@EnableFeignClients(basePackages = "com.ciel.service")  //开启Feign负载均衡

@EnableHystrixDashboard //服务监控界面
public class SpringCloudConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudConsumerApplication.class, args);

    }

}
