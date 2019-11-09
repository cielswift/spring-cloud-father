package com.ciel.springcloudasso.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 自定义成功处理器
 *
 */
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private RedisTemplate redisTemplate;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		//Principal 主体，存放了登录用户的信息
		User user = (User)authentication.getPrincipal();
		System.out.println(user.getUsername());//admin
		System.out.println(user.getPassword());//密码输出为null
		System.out.println(user.getAuthorities());//
		//重定向到百度。这只是一个示例，具体需要看项目业务需求

		Map<String,String> user_info = new HashMap<>();
		user_info.put("name",user.getUsername());
		user_info.put("password",user.getPassword());
		user_info.put("authorites",user.getAuthorities().toString());

		redisTemplate.opsForValue().set(System.currentTimeMillis(),user_info,1, TimeUnit.MINUTES);

		response.sendRedirect("/asso/hello");

	}

}

