package com.taotao.httpclient;

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.html.parser.Entity;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class HttpClietTest {

	@Test
	public void testGet() throws Exception {
		// 第一步：创建一个HttpClient对象 。
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// 第二步：创建一个HttpGet对象。可以指定一个请求的url
		HttpGet get = new HttpGet("http://www.sogou.com/");
		// 第三步：执行请求。服务端响应结果。
		CloseableHttpResponse response = httpClient.execute(get);
		// 第四步：取响应的结果。
		HttpEntity entity = response.getEntity();
		String content = EntityUtils.toString(entity, "utf-8");
		// 第五步：打印结果
		System.out.println(content);
		// 第六步：关闭HttpClient
		response.close();
		httpClient.close();
	}
	
	@Test
	public void testGetWithParam() throws Exception {
		// 第一步：创建一个HttpClient对象 。
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// 第二步：创建一个HttpGet对象。可以指定一个请求的url
		URIBuilder uriBuilder = new URIBuilder("http://www.sogou.com/web").addParameter("query", "学生替考或判刑");
		HttpGet get = new HttpGet(uriBuilder.build());
		// 第三步：执行请求。服务端响应结果。
		CloseableHttpResponse response = httpClient.execute(get);
		// 第四步：取响应的结果。
		HttpEntity entity = response.getEntity();
		String content = EntityUtils.toString(entity, "utf-8");
		// 第五步：打印结果
		System.out.println(content);
		// 第六步：关闭HttpClient
		response.close();
		httpClient.close();
	}
	
	@Test
	public void httpPostTest() throws Exception {
		// 第一步：创建一个HttpClient对象。
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// 第二步：创建一个HttpPost对象。指定一个请求的url
		HttpPost post = new HttpPost("http://localhost:8080/content/save");
		// 第三步：创建一个模拟的表单对象List<NameValuePare>
		List<NameValuePair> list = new ArrayList<>();
		list.add(new BasicNameValuePair("categoryId", "90"));
		list.add(new BasicNameValuePair("title", "添加的title"));
		list.add(new BasicNameValuePair("subTitle", "添加的subtitle"));
		list.add(new BasicNameValuePair("titleDesc", "hello"));
		// 第四步：把表单放到StringEntity（UrlEncodedFormEntity）中。封装后放到post中。
		StringEntity entity = new UrlEncodedFormEntity(list, "utf-8");
		post.setEntity(entity);
		// 第五步：执行请求。
		CloseableHttpResponse response = httpClient.execute(post);
		// 第六步：接收返回结果。
		HttpEntity entity2 = response.getEntity();
		String content = EntityUtils.toString(entity2);
		// 第七步：打印结果，关闭流。
		System.out.println(content);
		response.close();
		httpClient.close();
	}
}
