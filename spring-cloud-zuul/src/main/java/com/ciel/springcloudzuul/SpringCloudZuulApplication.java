package com.ciel.springcloudzuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy  //开启路由

@EnableConfigServer //集中配置
public class SpringCloudZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudZuulApplication.class, args);
    }

}
//http://10.0.75.1:8089/zuul/springcloud-consumer0/consumer0/search/ap
//http://10.0.75.1:8089/zuul/application/dev
//http://10.0.75.1:8089/zuul/master/application-dev.yml