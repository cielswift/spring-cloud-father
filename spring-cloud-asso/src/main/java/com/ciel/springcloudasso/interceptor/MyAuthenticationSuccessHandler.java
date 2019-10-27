package com.ciel.springcloudasso.interceptor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * 自定义成功处理器
 * @author chy
 *
 */
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		//Principal 主体，存放了登录用户的信息
		User user = (User)authentication.getPrincipal();
		System.out.println(user.getUsername());//admin
		System.out.println(user.getPassword());//密码输出为null
		System.out.println(user.getAuthorities());//
		//重定向到百度。这只是一个示例，具体需要看项目业务需求
		response.sendRedirect("/asso/hello");//[admin]

	}

}

