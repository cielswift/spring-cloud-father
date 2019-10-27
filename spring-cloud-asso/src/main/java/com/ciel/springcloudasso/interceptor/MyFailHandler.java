package com.ciel.springcloudasso.interceptor;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * 自定义失败处理器
 * @author chy
 *
 */
public class MyFailHandler implements AuthenticationFailureHandler {

	private String url;
	
	public MyFailHandler(String url) {
		super();
		this.url = url;
	}


	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		System.out.println(exception);
		response.sendRedirect("/asso"+url);

	}

}
