package com.taotao.portal.pojo;

import org.apache.commons.lang3.StringUtils;

import com.taotao.pojo.TbItem;

public class Item extends TbItem {

	public String[] getImages() {
		String imageStr = this.getImage();
		if (StringUtils.isNotBlank(imageStr)) {
			String[] images = imageStr.split(",");
			return images;
		}
		return null;
	}
}
