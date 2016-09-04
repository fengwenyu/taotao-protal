package com.taotao.portal.service;

import com.taotao.common.result.TaotaoResult;

public interface FreemarkerService {

	TaotaoResult genItemHtml(Long itemId)  throws Exception;
}
