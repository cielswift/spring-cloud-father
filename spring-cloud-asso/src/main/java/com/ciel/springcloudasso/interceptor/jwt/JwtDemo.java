package com.ciel.springcloudasso.interceptor.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JwtDemo {

    public static void main(String [] args) throws InterruptedException {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        Algorithm algorithm = Algorithm.HMAC256("xia-pei#xin$202"); //密钥

        String token = JWT.create().withHeader(map)  //生成 头
                /* 设置 载荷 Payload */
                .withClaim("username", "ciel")
                .withClaim("password", "123")
                .withClaim("deptName", "技术部")

                .withIssuer("SERVICE") // 签名是有谁生成 例如 服务器
                .withSubject("this is test token")// 签名的主题
                // .withNotBefore(new Date())//定义在什么时间之前，该jwt都是不可用的
                .withAudience("APP")// 签名的观众 也可以理解谁接受签名的
                .withIssuedAt(new Date()) // 生成签名的时间
                .withExpiresAt(new Date(new Date().getTime() + (1000)))// 签名过期的时间 ,抛出TokenExpiredException异常
                /* 签名 Signature */
                .sign(algorithm);

        System.out.println(token);


        Thread.sleep(1000 * 3);

        JWTVerifier verifier = JWT.require(algorithm) //验证
                .withIssuer("SERVICE")
                .build();

        DecodedJWT jwt = verifier.verify(token); //获取值

        String subject = jwt.getSubject();

        List<String> audience = jwt.getAudience();
        Map<String, Claim> claims = jwt.getClaims();

        for (Map.Entry<String, Claim> entry : claims.entrySet()) {
            String key = entry.getKey();
            Claim claim = entry.getValue();

            System.out.println("key:"+key+" value:"+claim.asString());
        }

        Claim claim = claims.get("username");

    }

    public Date getAfterDate(String str) throws ParseException {
        SimpleDateFormat df =new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
        return df.parse(str);
    }

}
