package com.taotao.portal.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.result.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbContent;
import com.taotao.portal.pojo.Slider;

/**
 * 查询内容列表
 * <p>Title: ContentServiceImpl</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.com</p> 
 * @author	入云龙
 * @date	2015年10月25日下午2:54:08
 * @version 1.0
 */
@Service
public class ContentServiceImpl implements ContentService {
	
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_CONTENT_LIST}")
	private String REST_CONTENT_LIST;

	@Override
	public String getContentByCid(Long categoryId) {
		//调用taotao-rest的服务查询内容列表
		String result = HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_LIST + categoryId);
		//转换成java对象
		TaotaoResult taotaoResult = TaotaoResult.formatToList(result, TbContent.class);
		List<TbContent> list = (List<TbContent>) taotaoResult.getData();
		//遍历列表转换成Slider列表
		List<Slider> sList = new ArrayList<>();
		for (TbContent tbContent : list) {
			Slider slider = new Slider();
			slider.setHeight(240);
			slider.setWidth(670);
			slider.setSrc(tbContent.getPic());
			slider.setHeightB(240);
			slider.setWidthB(550);
			slider.setSrcB(tbContent.getPic2());
			slider.setAlt(tbContent.getSubTitle());
			slider.setHref(tbContent.getUrl());
			//添加到列表
			sList.add(slider);
		}
		//把列表转换成json数据
		String resultStr = JsonUtils.objectToJson(sList);
		
		return resultStr;
	}

}
