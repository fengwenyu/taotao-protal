package com.taotao.portal.service;

import com.taotao.pojo.TbItem;
import com.taotao.portal.pojo.Item;

/**
 * 商品信息管理Service
 * <p>Title: ItemService</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.com</p> 
 * @author	入云龙
 * @date	2015年10月30日上午10:41:15
 * @version 1.0
 */
public interface ItemService {

	Item getItemById(Long itemId);
	String getItemDescById(Long itemId);
	String getItemParamById(Long itemId);
	
}
