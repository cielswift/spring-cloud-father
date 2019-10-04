package com.ciel.springcloudprovider1.controller;

import com.ciel.entity.AppEntity;
import com.ciel.service.AppService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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
        all.get(0).setName("PROVIDER-2");
        return all;
    }

    //@HystrixCommand(fallbackMethod = "byparam_err") //发生异常,调用错误处理方法,向调用方发送一个返回预期的数据
    @RequestMapping("/byparam")
    public AppEntity byparam(AppEntity appEntity){

        if(System.currentTimeMillis()%2==0){
            throw new RuntimeException("随机异常");
        }
        AppEntity appEntity1 = appService.byId(appEntity.getId());
        appEntity1.setName("PROVIDER-2");
        return appEntity1;
    }

//    public AppEntity byparam_err(AppEntity appEntity){
//        appEntity.setName("error-2");
//        return appEntity;
//    }


    @RequestMapping("/byjson")
    public AppEntity byjson(@RequestBody AppEntity appEntity){
        AppEntity appEntity1 = appService.byId(appEntity.getId());
        appEntity1.setName("PROVIDER-2");
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


}
