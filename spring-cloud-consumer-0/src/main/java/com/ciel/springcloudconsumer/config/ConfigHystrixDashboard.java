package com.ciel.springcloudconsumer.config;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigHystrixDashboard {

    @Bean
    public ServletRegistrationBean getServlet() { //注册服务高可用

        HystrixMetricsStreamServlet streamServlet =new HystrixMetricsStreamServlet();

        ServletRegistrationBean registrationBean =new ServletRegistrationBean(streamServlet);

        registrationBean.setLoadOnStartup(1);

        registrationBean.addUrlMappings("/actuator/hystrix.stream");

        registrationBean.setName("HystrixMetricsStreamServlet");

        return registrationBean;
    }

}
