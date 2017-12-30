package com.jt.order.service;

import java.io.IOException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.order.mapper.OrderMapper;
import com.jt.order.pojo.Order;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	private static final ObjectMapper objectMapper = new ObjectMapper();
	private static final Logger log = Logger.getLogger(OrderServiceImpl.class);

	@Override
	public String saveOrder(String orderJSON) {

		try {
			Order order = objectMapper.readValue(orderJSON, Order.class);
			// 完善数据
			String orderId = order.getUserId() + "" + System.currentTimeMillis();
			order.setOrderId(orderId);
			order.setCreateTime(new Date());
			order.setUpdateTime(order.getCreateTime());

			order.getOrderShipping().setCreated(order.getCreateTime());
			order.getOrderShipping().setUpdated(order.getCreateTime());

			// orderMapper.saveOrder(order);
			rabbitTemplate.convertAndSend("saveOrder", order);

			return orderId;
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return null;

		}
	}

	@Override
	public Order findOrderByOrderId(String orderId) {

		Order order = orderMapper.findOrderByOrderId(orderId);
		return order;
	}

}
