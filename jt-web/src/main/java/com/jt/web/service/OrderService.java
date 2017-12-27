package com.jt.web.service;

import com.jt.web.pojo.Order;

public interface OrderService {

	public String submitOrder(Order order);
	
	/**
	 * 根据订单id查询订单
	 * @param orderId:订单id
	 * @return
	 */
	public Order findOrderByOrderId(String orderId);

}
