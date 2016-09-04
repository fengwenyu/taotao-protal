package com.taotao.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.pojo.TbItem;
import com.taotao.portal.pojo.Item;
import com.taotao.portal.service.ItemService;

/**
 * 商品页面展示Controller
 * <p>Title: ItemController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.com</p> 
 * @author	入云龙
 * @date	2015年10月30日上午10:50:54
 * @version 1.0
 */
@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;

	@RequestMapping("/item/{itemId}")
	public String getItemById(@PathVariable Long itemId, Model model) {
		Item item = itemService.getItemById(itemId);
		//把item传递给页面
		model.addAttribute("item", item);
		//返回逻辑视图
		return "item";
	}
	@RequestMapping(value="/item/desc/{itemId}", produces=MediaType.TEXT_HTML_VALUE+";charset=utf-8")
	@ResponseBody
	public String getItemDescById(@PathVariable Long itemId) {
		String html = itemService.getItemDescById(itemId);
		return html;
	}
	
	@RequestMapping(value="/item/param/{itemId}", produces=MediaType.TEXT_HTML_VALUE+";charset=utf-8")
	@ResponseBody
	public String getItemParamById(@PathVariable Long itemId) {
		String html = itemService.getItemParamById(itemId);
		return html;
	}
}
