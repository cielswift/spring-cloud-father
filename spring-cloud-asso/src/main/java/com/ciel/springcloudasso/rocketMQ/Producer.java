package com.ciel.springcloudasso.rocketMQ;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.io.Serializable;

public class Producer {
    public static void main(String[] args) throws MQClientException {

        DefaultMQProducer producer = new DefaultMQProducer("ciel-test-group"); //消息发送对象,指定组

        producer.setNamesrvAddr("192.168.43.15:9876"); //namseserver 地址

        producer.setInstanceName("rmq-instance");
        producer.start(); //开启

        try {
            for (int i = 0; i < 100; i++) {

                MsgUser user = new MsgUser("xiapeixin".concat(String.valueOf(i)),"123");
                Message message =
                        new Message("log-topic", "user-tag",
                                "key".concat(String.valueOf(i)),JSON.toJSONString(user).getBytes());

                        //主题,标签(消息过滤),消息唯一值, 消息体

                System.out.println("生产者发送消息:" + JSON.toJSONString(user));
                SendResult send = producer.send(message);
                System.out.println(send);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        producer.shutdown();

    }

    @Data
    static class MsgUser implements Serializable {

        private String loginName;
        private String pwd;

        public MsgUser(String loginName, String pwd) {
            this.loginName = loginName;
            this.pwd = pwd;
        }
    }

}