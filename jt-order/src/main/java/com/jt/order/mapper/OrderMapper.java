package com.jt.order.mapper;

import com.jt.order.pojo.Order;

public interface OrderMapper {

	public void saveOrder(Order order);

	public Order findOrderByOrderId(String orderId);

}
