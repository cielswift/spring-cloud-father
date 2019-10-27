package com.ciel.springcloudconsumer.controller;

import com.ciel.entity.AppEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/an") //轮询负载均衡
    public String list(){

        String forObject = restTemplate.getForObject
                ("http://SPRINGCLOUD-PROVIDER/provider/app/getAll",String.class);
        return forObject;
    }

    @RequestMapping("/ap")
    public String ap(){

        Map<String,String> param = new HashMap<>();
        param.put("id","2");
        param.put("name","夏培鑫");

        String forObject = restTemplate.getForObject
                ("http://SPRINGCLOUD-PROVIDER/provider/app/byparam?id={id}&name={name}",String.class,param);

        return forObject;
    }

    @RequestMapping("/byjson")
    public String byjson() throws JsonProcessingException {

        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());

        AppEntity appEntity = new AppEntity();
        appEntity.setId(2);
        appEntity.setName("夏培鑫");
        appEntity.setUpdateDate(new Date());
        String string = new ObjectMapper().writeValueAsString(appEntity);

        HttpEntity<String> formEntity = new HttpEntity<String>(string, headers);

        String forObject = restTemplate.postForObject
                ("http://SPRINGCLOUD-PROVIDER/provider/app/byjson",formEntity,String.class);

        return forObject;
    }

    @RequestMapping("/upload")
    public String upload(){
        String forObject = restTemplate.getForObject
                ("http://SPRINGCLOUD-PROVIDER/provider/app/upload",String.class);

        return forObject;
    }
}
