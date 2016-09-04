package com.taotao.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.taotao.pojo.TbUser;
import com.taotao.portal.pojo.Item;
import com.taotao.portal.pojo.Order;
import com.taotao.portal.service.CartService;
import com.taotao.portal.service.OrderService;

/**
 * 订单处理Controller
 * <p>Title: OrderController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.com</p> 
 * @author	入云龙
 * @date	2015年11月1日下午4:04:16
 * @version 1.0
 */
@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private CartService cartService;
	@Autowired
	private OrderService orderService;

	@RequestMapping("/order-cart")
	public String showOrderCart(HttpServletRequest request, Model model) {
		//取购物车商品列表
		List<Item> itemList = cartService.getCartItemList(request);
		//传递给页面
		model.addAttribute("cartList", itemList);
		//取用户信息
		TbUser user = (TbUser) request.getAttribute("user");
		//根据用户查询配送地址列表等数据
		//.....
		//返回逻辑视图
		return "order-cart";
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public String createOrder(Order order, Model model) {
		String orderId = orderService.createOrder(order);
		//传递给页面
		model.addAttribute("orderId", orderId);
		model.addAttribute("payment", order.getPayment());
		DateTime dateTime = new DateTime();
		dateTime = dateTime.plusDays(3);
		model.addAttribute("date", dateTime.toString("yyyy-MM-dd"));
		//返回逻辑视图
		return "success";
	}
	
}
