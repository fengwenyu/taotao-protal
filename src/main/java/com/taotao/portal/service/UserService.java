package com.taotao.portal.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.result.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.pojo.TbUser;

@Service
public class UserService {

	@Value("${SSO_BASE_URL}")
	public String SSO_BASE_URL;
	@Value("${SSO_USER_TOKEN_URL}")
	public String SSO_USER_TOKEN_URL;
	@Value("${SSO_USER_LOGIN_URL}")
	public String SSO_USER_LOGIN_URL;
	
	public TbUser getUserByToken(String token) {
		try {
			//调用sso系统的服务根据token取用户信息
			String json = HttpClientUtil.doGet(SSO_BASE_URL + SSO_USER_TOKEN_URL + token);
			//转换成java对象
			TaotaoResult result = TaotaoResult.formatToPojo(json, TbUser.class);
			//取user对象
			TbUser user = (TbUser) result.getData();
			return user;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
