package com.ccc.outside;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class XiaConfig {

    @Bean
    public Xia xia(){
        return new Xia();
    }
}
