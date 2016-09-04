package com.taotao.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.result.TaotaoResult;
import com.taotao.portal.pojo.Item;
import com.taotao.portal.service.CartService;

/**
 * 购物车Controller
 * <p>Title: CartController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.com</p> 
 * @author	入云龙
 * @date	2015年11月1日上午9:55:44
 * @version 1.0
 */
@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;
	
	@RequestMapping("/add/{itemId}")
	public String addCartItem(@PathVariable Long itemId, @RequestParam(defaultValue="1")Integer num,
			HttpServletRequest request, HttpServletResponse response) {
		cartService.addCartItem(request, response, itemId, num);
		return "cartSuccess";
	}
	
	@RequestMapping("/cart")
	public String showCartItemList(HttpServletRequest request, Model model) {
		List<Item> itemList = cartService.getCartItemList(request);
		model.addAttribute("cartList", itemList);
		return "cart";
	}
	
	@RequestMapping("/update/num/{itemId}/{num}")
	@ResponseBody
	public TaotaoResult updateNum(@PathVariable Long itemId, @PathVariable Integer num,
			HttpServletResponse response, HttpServletRequest request) {
		TaotaoResult result = cartService.updateCartNum(request, response, itemId, num);
		return result;
	}
	
	@RequestMapping("/delete/{itemId}")
	public String deleteCartItem(@PathVariable Long itemId, 
			HttpServletResponse response, HttpServletRequest request) {
		TaotaoResult result = cartService.deleteCartItem(request, response, itemId);
		return "redirect:/cart/cart.html";
	}
}
