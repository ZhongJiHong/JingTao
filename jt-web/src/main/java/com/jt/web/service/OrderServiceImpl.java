package com.jt.web.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.web.pojo.Order;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private HttpClientService httpClientService;

	private static final ObjectMapper objectMapper = new ObjectMapper();
	private static final Logger log = Logger.getLogger(OrderServiceImpl.class);

	@Override
	public String submitOrder(Order order) {

		String url = "http://order.jt.com/order/create";

		try {
			String orderJSON = objectMapper.writeValueAsString(order);

			Map<String, String> params = new HashMap<String, String>();
			params.put("orderJSON", orderJSON);
			String orderId = httpClientService.doPost(url, params, "utf-8");

			return orderId;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return null;
		}
	}

	// http://order.jt.com/order/query/81425700649826
	@Override
	public Order findOrderByOrderId(String orderId) {

		String url = "http://order.jt.com/order/query/" + orderId;

		try {
			String orderJSON = httpClientService.doGet(url);

			return objectMapper.readValue(orderJSON, Order.class);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return null;
		}
	}

}
