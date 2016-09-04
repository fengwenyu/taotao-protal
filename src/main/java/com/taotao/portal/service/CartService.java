package com.taotao.portal.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taotao.common.result.TaotaoResult;
import com.taotao.portal.pojo.Item;

public interface CartService {

	TaotaoResult addCartItem(HttpServletRequest request, HttpServletResponse response,
			Long itemId, int num);
	List<Item> getCartItemList(HttpServletRequest request);
	TaotaoResult updateCartNum(HttpServletRequest request, HttpServletResponse response,
			Long itemId, int num);
	TaotaoResult deleteCartItem(HttpServletRequest request, HttpServletResponse response,
			Long itemId);
}
