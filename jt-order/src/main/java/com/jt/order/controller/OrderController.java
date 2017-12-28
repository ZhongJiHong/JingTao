package com.jt.order.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.order.pojo.Order;
import com.jt.order.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	private static final Logger log = Logger.getLogger(OrderController.class);

	// 创建订单 http://order.jt.com/order/create
	@RequestMapping("/create")
	@ResponseBody
	public String saveOrder(String orderJSON) {

		try {
			String orderId = orderService.saveOrder(orderJSON);
			return orderId;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return null;
		}
	}

	// 实现根据订单id查询订单 http://order.jt.com/order/query/81425700649826
	@RequestMapping("/query/{orderId}")
	@ResponseBody
	public Order findOrderByOrderId(@PathVariable String orderId) {

		Order order;
		try {
			order = orderService.findOrderByOrderId(orderId);
			return order;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return null;
		}
	}
}
