package com.ciel.springcloudprovider.controller;

import com.ciel.entity.AppEntity;
import com.ciel.service.AppService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/app")
public class AppController {

    @Autowired
    private AppService appService;

    @RequestMapping("/getAll")
    public List<AppEntity> getAll(){
        List<AppEntity> all = appService.getAll();
        all.get(0).setName("PROVIDER-1");
        return all;
    }

    public List<AppEntity> getAll_err(){
        AppEntity appEntity = new AppEntity();
        appEntity.setName("err");
        List<AppEntity> las = new ArrayList<>();
        las.add(appEntity);
        return las;
    }

    //@HystrixCommand(fallbackMethod = "byparam_err") //发生异常,调用错误处理方法,向调用方发送一个返回预期的数据
    @RequestMapping("/byparam")
    public AppEntity byparam(AppEntity appEntity){

        if(System.currentTimeMillis()%2==0){
            throw new RuntimeException("随机异常");
        }

        AppEntity appEntity1 = appService.byId(appEntity.getId());
        appEntity1.setName("PROVIDER-1");
        return appEntity1;
    }

//    public AppEntity byparam_err(AppEntity appEntity){
//        appEntity.setName("error-1");
//        return appEntity;
//    }



    @RequestMapping("/byjson")
    public AppEntity byjson(@RequestBody AppEntity appEntity){
        AppEntity appEntity1 = appService.byId(appEntity.getId());
        appEntity1.setName("PROVIDER-1");
        return appEntity1;
    }


    @PostMapping(value = "/upload")
    public String upload(@RequestParam("file") MultipartFile file) throws IOException, JSONException {
        String fileName = file.getOriginalFilename();

        String fn = UUID.randomUUID().toString().replace("-", "") + ".jpg";

        File dest = new File(new File("c://ciel/"), fn);
        file.transferTo(dest);

        JSONObject jsonpObject = new JSONObject();
        jsonpObject.put("errno", 0);

        JSONArray jsonArray = new JSONArray();

        return StringEscapeUtils.unescapeJava(jsonpObject.toString());
    }



    @RequestMapping("/fuck")
    public String fuck(){
        HtmlUtils.htmlUnescape("fuck");
        String fuck = StringEscapeUtils.unescapeJava("fuck");
        JSONObject jsonObject = new JSONObject();
        return fuck;
    }


    @RequestMapping("/mk")
    public List<AppEntity> mk() throws JsonProcessingException {
        return appService.getByWapper();
    }

    @RequestMapping("/test")
    public String test()  {
        appService.test();
        return "a";
    }
    @RequestMapping("/test2")
    public String test2()  {
        appService.test2();
        return "a";
    }
    @RequestMapping("/test3")
    public String test3()  {
        appService.test3();
        return "a";
    }

}
