package com.taotao.portal.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.common.utils.CookieUtils;
import com.taotao.pojo.TbUser;
import com.taotao.portal.service.UserService;

public class LoginInterceptor implements HandlerInterceptor {
	
	@Autowired
	private UserService userService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//前处理
		// 第一步：从cookie中取TT_TOKEN内容。如果没有跳转到登录页面（需要传递回调url）。
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		// 第二步：如果有TT_TOKEN，调用taotao-sso的服务，根据token取用户信息。
		if (StringUtils.isBlank(token)) {
			//取请求的url
			String url = request.getRequestURL().toString();
			//页面跳转,跳转到登录页面。
			response.sendRedirect(userService.SSO_BASE_URL + userService.SSO_USER_LOGIN_URL 
					+ "?redirectUrl=" + url);
			//拦截
			return false;
		}
		// 第三步：如果用户信息中无内容，跳转到登录页面（需要传递回调url）。
		TbUser user = userService.getUserByToken(token);
		if (null == user) {
			//取请求的url
			String url = request.getRequestURL().toString();
			//页面跳转,跳转到登录页面。
			response.sendRedirect(userService.SSO_BASE_URL + userService.SSO_USER_LOGIN_URL 
					+ "?redirectUrl=" + url);
			//拦截
			return false;
		}
		// 第四步：有内容就放行。
		//把用户保存到Request
		request.setAttribute("user", user);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
