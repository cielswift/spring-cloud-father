package com.ciel.springcloudconsumer.service;

import com.ciel.entity.AppEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class SearchService {

    @Autowired
    private LoadBalancerClient loadBalancerClient; //负载均衡器

    public List<AppEntity> getOne() {
        ServiceInstance si = loadBalancerClient.choose("SPRINGCLOUD-PROVIDER0");   //根据服务名称获取服务实例

        //缓存后,eureka全部宕机,仍然可以访问其他生产者 节点;
        StringBuilder sb = new StringBuilder();

        sb.append("http://").append(si.getHost()).append(":").append(si.getPort()).append("/provider1/app/getAll");

        System.err.println("^^^^^^^^^^^^^^"+sb.toString());

        RestTemplate rt = new RestTemplate();

        ParameterizedTypeReference<List<AppEntity>> ptr = new ParameterizedTypeReference<>() {

        };

        ResponseEntity<List<AppEntity>> re = rt.exchange(sb.toString(), HttpMethod.GET,null,ptr);
        //第三个是请求参数 ,第4个是 返回值用什么包装;

        return re.getBody();
    }

}
