package com.jt.order.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.jt.order.mapper.OrderMapper;
import com.jt.order.pojo.Order;

public class RabbitOrderService {

	@Autowired
	private OrderMapper orderMapper;

	public void saveOrder(Order order) {

		orderMapper.saveOrder(order);
	}
}
