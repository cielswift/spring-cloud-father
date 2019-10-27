package com.ciel.springcloudasso.interceptor;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 自定义403页面
 * 1.继承AccessDeniedHandler接口,  重写handle()方法
 * 2.添加@Component注解
 * 3.在配置类中注入
 * 4.使用
 * @author chy
 *
 */
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		//HttpServletResponse.SC_FORBIDDEN代表 403
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.setHeader("Content-Type","application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.write("{\"status\":\"error\",\"msg\":\"权限不足，请联系管理员! 159-6650-4931\"}");
		out.flush();
		out.close();
	}

}

