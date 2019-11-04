package com.ciel.springcloudasso;

import com.ccc.outside.XiaConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication

@EnableEurekaClient  //服务注册eureka
@EnableDiscoveryClient //开启发现服务

@ComponentScan(basePackages = "com.ciel")  //扫描整个ciel下的包
@EnableJpaRepositories(basePackages = "com.ciel.springcloudcommon.dao")
@EntityScan(basePackages = "com.ciel.entity")
@EnableJpaAuditing //开启自动填充

@EnableCircuitBreaker //开启对于hystris熔断的支持

@Import(XiaConfig.class)
public class SpringCloudAssoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudAssoApplication.class, args);
    }


}
