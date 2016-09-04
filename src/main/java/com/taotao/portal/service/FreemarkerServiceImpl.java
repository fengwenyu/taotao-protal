package com.taotao.portal.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.taotao.common.result.TaotaoResult;
import com.taotao.portal.pojo.Item;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateNotFoundException;

/**
 * 生成静态页面Service
 * <p>Title: FreemarkerServiceImpl</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.com</p> 
 * @author	入云龙
 * @date	2015年10月30日下午4:07:32
 * @version 1.0
 */

@Service
public class FreemarkerServiceImpl implements FreemarkerService {

	@Autowired
	private ItemService itemService;
	
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Value("${ITEM_HTML_PATH}")
	private String ITEM_HTML_PATH;
	
	@Override
	public TaotaoResult genItemHtml(Long itemId) throws Exception {
		//商品基本信息
		Item item = itemService.getItemById(itemId);
		//商品描述
		String itemDesc = itemService.getItemDescById(itemId);
		//商品规格参数
		String itemParam = itemService.getItemParamById(itemId);
		//创建一个数据集
		Map dataModel = new HashMap<>();
		dataModel.put("item", item);
		dataModel.put("itemDesc", itemDesc);
		dataModel.put("itemParam", itemParam);
		//生成静态页面
		Configuration configuration = freeMarkerConfigurer.getConfiguration();
		Template template = configuration.getTemplate("item.ftl");
		//判断路径是否存在
		File path = new File(ITEM_HTML_PATH);
		if (!path.exists()) {
			path.mkdirs();
		}
		//指定页面生成的路径
		FileWriter out = new FileWriter(new File(ITEM_HTML_PATH + itemId +".html"));
		template.process(dataModel, out);
		out.flush();
		out.close();
		
		return TaotaoResult.ok();
	}

}
