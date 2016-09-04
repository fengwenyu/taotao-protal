package com.taotao.portal.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.result.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.portal.pojo.Item;

/**
 * 购物车Service
 * <p>Title: CartServiceImpl</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.com</p> 
 * @author	入云龙
 * @date	2015年11月1日上午9:37:45
 * @version 1.0
 */
@Service
public class CartServiceImpl implements CartService {
	
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_ITEM_BASE}")
	private String REST_ITEM_BASE;
	@Value("${CART_EXPIRE}")
	private Integer CART_EXPIRE;

	@Override
	public TaotaoResult addCartItem(HttpServletRequest request, HttpServletResponse response,
			Long itemId, int num) {
		//取购物车商品
		Map<String, Integer> cartMap = getCartItems(request);
		//判断此商品是否在购物车中存在
		Integer itemNum = cartMap.get(itemId + "");
		if (itemNum != null) {
			cartMap.put(itemId + "", itemNum + num);
		} else {
			cartMap.put(itemId + "", num);
		}
		//把购物车商品写入cookie
		CookieUtils.setCookie(request, response, "TT_CART", 
				JsonUtils.objectToJson(cartMap), CART_EXPIRE ,true);
		
		return TaotaoResult.ok();
	}
	
	//从cookie中取购物车商品列表
	private Map<String, Integer> getCartItems(HttpServletRequest request) {
		String json = CookieUtils.getCookieValue(request, "TT_CART", true);
		Map<String, Integer> itemMap = null;
		//转换成java对象
		if (StringUtils.isNotBlank(json)) {
			itemMap = JsonUtils.jsonToPojo(json, Map.class);
		} else {
			itemMap = new HashMap<>();
		}
		return itemMap;
	}

	@Override
	public List<Item> getCartItemList(HttpServletRequest request) {
		// 取购物车商品列表
		Map<String, Integer> cartItems = getCartItems(request);
		//遍历map查询商品列表，生成一个新的商品列表
		Set keySet = cartItems.keySet();
		List<Item> items = new ArrayList<>();
		for (Object key : keySet) {
			//查询商品信息
			String jsonData = HttpClientUtil.doGet(REST_BASE_URL + REST_ITEM_BASE + key);
			//转换成java对象
			TaotaoResult taotaoResult = TaotaoResult.formatToPojo(jsonData, Item.class);
			//取商品信息
			Item item = (Item) taotaoResult.getData();
			//设置商品数量
			item.setNum(cartItems.get(key));
			//存入list
			items.add(item);
		}
		return items;
	}

	@Override
	public TaotaoResult updateCartNum(HttpServletRequest request, HttpServletResponse response, Long itemId, int num) {
		// 取购物车商品列表
		Map<String, Integer> cartItems = getCartItems(request);
		//替换id对应的商品数量
		cartItems.put(itemId.toString(), num);
		//写入cookie
		//把购物车商品写入cookie
		CookieUtils.setCookie(request, response, "TT_CART", 
				JsonUtils.objectToJson(cartItems), CART_EXPIRE ,true);
				
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult deleteCartItem(HttpServletRequest request, HttpServletResponse response, Long itemId) {
		// 取购物车商品列表
		Map<String, Integer> cartItems = this.getCartItems(request);
		//删除map中的key
		cartItems.remove(itemId.toString());
		//写入cookie
		//把购物车商品写入cookie
		CookieUtils.setCookie(request, response, "TT_CART", 
						JsonUtils.objectToJson(cartItems), CART_EXPIRE ,true);
		return TaotaoResult.ok();
	}

}
