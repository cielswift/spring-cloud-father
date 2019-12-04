package com.ciel.springcloudasso.interceptor.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("myras")
@Data
public class Ras {

    private String public_key;
    private String private_key;

}
