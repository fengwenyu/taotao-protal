package com.taotao.portal.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.result.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.portal.pojo.Order;

@Service
public class OrderServicImpl implements OrderService {
	
	@Value("${ORDER_BASE_URL}")
	private String ORDER_BASE_URL;
	@Value("${ORDER_CREATE_URL}")
	private String ORDER_CREATE_URL;
	

	@Override
	public String createOrder(Order order) {
		String json = JsonUtils.objectToJson(order);
		//调用taotao-order的服务创建订单
		String jsonResult = HttpClientUtil.doPostJson(ORDER_BASE_URL + ORDER_CREATE_URL, json);
		//转换成java对象
		TaotaoResult taotaoResult = TaotaoResult.format(jsonResult);
		//取订单号
		String orderId = (String) taotaoResult.getData();
		return orderId;
	}

}
