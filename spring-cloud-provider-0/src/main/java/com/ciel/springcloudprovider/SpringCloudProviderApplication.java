package com.ciel.springcloudprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableEurekaClient  //服务注册eureka
@ComponentScan(basePackages = "com.ciel")  //扫描整个ciel下的包
@EnableJpaRepositories(basePackages = "com.ciel.springcloudcommon.dao")
@EntityScan(basePackages = "com.ciel.entity")
@EnableJpaAuditing //开启自动填充

@EnableFeignClients(basePackages = "com.ciel.service")  //开启Feign负载均衡
@EnableCircuitBreaker //开启对于hystris熔断的支持
//@EnableHystrix
@EnableHystrixDashboard //服务视图

public class SpringCloudProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudProviderApplication.class, args);
    }


}
