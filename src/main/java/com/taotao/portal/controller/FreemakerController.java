package com.taotao.portal.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.result.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.portal.service.FreemarkerService;

/**
 * 生成静态页面服务
 * <p>Title: FreemakerController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.com</p> 
 * @author	入云龙
 * @date	2015年10月30日下午4:19:49
 * @version 1.0
 */
@Controller
public class FreemakerController {

	@Autowired
	private FreemarkerService freemarkerService;
	
	@RequestMapping("/gen/item")
	@ResponseBody
	public TaotaoResult genItemHtml(Long itemId) {
		if (itemId == null) {
			return TaotaoResult.build(400, "itemId不能为空");
		}
		try {
			TaotaoResult result = freemarkerService.genItemHtml(itemId);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		
	}
}
