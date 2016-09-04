package com.taotao.portal.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.result.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.portal.pojo.Item;

@Service
public class ItemServiceImpl implements ItemService {

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_ITEM_BASE}")
	private String REST_ITEM_BASE;
	@Value("${REST_ITEM_DESC}")
	private String REST_ITEM_DESC;
	@Value("${REST_ITEM_PARAM}")
	private String REST_ITEM_PARAM;

	@Override
	public Item getItemById(Long itemId) {
		// 调用taotao-rest的服务，根据商品id查询商品基本信息
		String jsonData = HttpClientUtil.doGet(REST_BASE_URL + REST_ITEM_BASE + itemId);
		// 把json转换成java对象
		TaotaoResult result = TaotaoResult.formatToPojo(jsonData, Item.class);
		// 取商品信息
		Item tbItem = (Item) result.getData();
		return tbItem;
	}

	@Override
	public String getItemDescById(Long itemId) {
		// 调用taotao-rest的服务，根据商品id查询商品的描述
		String jsonData = HttpClientUtil.doGet(REST_BASE_URL + REST_ITEM_DESC + itemId);
		// 把json转换成java对象
		TaotaoResult result = TaotaoResult.formatToPojo(jsonData, TbItemDesc.class);
		// 取商品描述pojo
		TbItemDesc itemDesc = (TbItemDesc) result.getData();
		String desc = itemDesc.getItemDesc();
		return desc;
	}

	@Override
	public String getItemParamById(Long itemId) {
		// 调用taotao-rest的服务，根据商品id查询商品的规格参数
		String jsonData = HttpClientUtil.doGet(REST_BASE_URL + REST_ITEM_PARAM + itemId);
		// 转换成java对象
		TaotaoResult taotaoResult = TaotaoResult.formatToPojo(jsonData, TbItemParamItem.class);
		// 取规格参数的pojo
		TbItemParamItem itemParamItem = (TbItemParamItem) taotaoResult.getData();
		// 取规格参数的json数据
		String jsonParam = itemParamItem.getParamData();
		// 转换成java对象
		List<Map> paramList = JsonUtils.jsonToList(jsonParam, Map.class);
		StringBuffer sb = new StringBuffer();
		sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
		sb.append("	<tbody>\n");
		for (Map map : paramList) {
			sb.append("		<tr>\n");
			sb.append("			<th class=\"tdTitle\" colspan=\"2\">" + map.get("group") + "</th>\n");
			sb.append("		</tr>\n");
			List<Map> params = (List<Map>) map.get("params");
			for (Map map2 : params) {
				sb.append("		<tr>\n");
				sb.append("			<td class=\"tdTitle\">" + map2.get("k") + "</td>\n");
				sb.append("			<td>" + map2.get("v") + "</td>\n");
				sb.append("		</tr>\n");
			}
		}
		sb.append("	</tbody>\n");
		sb.append("</table>");

		return sb.toString();
	}

}
