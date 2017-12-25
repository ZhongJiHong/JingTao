package com.jt.web.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.Cart;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private HttpClientService httpClientService;

	private static final ObjectMapper objectMapper = new ObjectMapper();
	private static final Logger log = Logger.getLogger(CartServiceImpl.class);

	// 跨域请求 http://cart.jt.com/cart/query/{userId}
	@Override
	public List<Cart> findCartByUserId(Long userId) {

		String url = "http://cart.jt.com/cart/query/" + userId;

		try {
			String resultJSON = httpClientService.doGet(url);

			JsonNode jsonnNode = objectMapper.readTree(resultJSON);
			String data = jsonnNode.get("data").asText();

			Cart[] carts = objectMapper.readValue(data, Cart[].class);
			List<Cart> cartList = Arrays.asList(carts);
			return cartList;

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return null;
		}
	}

	@Override
	public SysResult updateCartNum(Long userId, Long itemId, Integer num) {

		// 跨域请求 http://cart.jt.com/cart/update/num/{userId}/{itemId}/{num}
		String url = "http://cart.jt.com/cart/update/num/" + userId + "/" + itemId + "/" + num;

		try {
			String result = httpClientService.doGet(url);
			SysResult sysResult = objectMapper.readValue(result, SysResult.class);
			return sysResult;

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return SysResult.build(201, "商品数量更新失败");
		}
	}

	@Override
	public SysResult deleteCartItem(Long userId, Long itemId) {

		// 跨域请求 http://cart.jt.com/cart/delete/{userId}/{itemId}
		String url = "http://cart.jt.com/cart/delete/" + userId + "/" + itemId;

		try {
			String result = httpClientService.doGet(url);
			return objectMapper.readValue(result, SysResult.class);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage() + "{购物车中的商品删除失败}");
			return SysResult.build(201, "购物车中的商品删除失败");
		}
	}

	@Override
	public SysResult saveCartItem(Long userId, Long itemId, Cart cart) {

		String url = "http://cart.jt.com/cart/save";

		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", userId + "");
		params.put("itemId", itemId + "");
		params.put("itemTitle", cart.getItemTitle());
		params.put("itemImage", cart.getItemImage());
		params.put("itemPrice", cart.getItemPrice() + "");
		params.put("num", cart.getNum() + "");

		try {
			String result = httpClientService.doPost(url, params);
			return objectMapper.readValue(result, SysResult.class);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return SysResult.build(201, "商品添加失败");
		}

	}

}
