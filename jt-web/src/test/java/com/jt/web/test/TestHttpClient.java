package com.jt.web.test;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class TestHttpClient {

	/**
	 * 1. 得到HttpClient对象 2. 编辑跨域请求的的url 3. 设置请求的方式 4. 发送请求 5. 获取结果
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	/*
	@Test
	public void testHttpClient() throws ClientProtocolException, IOException {

		// 获取HttpClient客户端对象
		CloseableHttpClient httpClient = HttpClients.createDefault();

		// 编辑快鱼请求的url
		String url = "http://poi.apache.org/spreadsheet/index.html";

		// 设置请求的方式
		HttpGet httpGet = new HttpGet(url);

		// 发送请求
		CloseableHttpResponse httpResponse = httpClient.execute(httpGet);

		// 获得结果
		if (httpResponse.getStatusLine().getStatusCode() == 200) {

			System.out.println("跨域请求成功");
			String html = EntityUtils.toString(httpResponse.getEntity());
			System.out.println(html);
		} else {
			System.out.println("跨域请求失败");
		}

	}
	*/
}
