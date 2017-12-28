package com.jt.order.service;

import com.jt.order.pojo.Order;

public interface OrderService {

	public String saveOrder(String orderJSON);

	public Order findOrderByOrderId(String orderId);

}
