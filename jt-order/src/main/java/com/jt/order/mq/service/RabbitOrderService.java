package com.jt.order.mq.service;

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
