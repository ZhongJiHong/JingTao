package com.jt.web.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.SysResult;
import com.jt.web.pojo.Cart;
import com.jt.web.pojo.Order;
import com.jt.web.service.CartService;
import com.jt.web.service.OrderService;
import com.jt.web.utils.UserThreadLocal;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private CartService cartService;

	private static final Logger log = Logger.getLogger(OrderController.class);

	// 跳转到添加订单页面 /order/create.html
	@RequestMapping("/create")
	public String createOrder(Model model) {

		Long userId = UserThreadLocal.getUser().getId();
		List<Cart> cartList = cartService.findCartByUserId(userId);

		model.addAttribute("carts", cartList);
		return "order-cart";
	}

	// 提交订单 http://www.jt.com/service/order/submit
	@RequestMapping("/submit")
	@ResponseBody
	public SysResult submitOrder(Order order) {

		Long userId = UserThreadLocal.getUser().getId();
		order.setUserId(userId);

		String orderId = orderService.submitOrder(order);

		if (!StringUtils.isEmpty(orderId)) {
			return SysResult.oK(orderId);
		} else {
			return SysResult.build(201, "订单添加失败");
		}
	}

	// 跳转到订单添加成功页面
	@RequestMapping("/success")
	public String succeedOrder(String id, Model model) {

		Order order = orderService.findOrderByOrderId(id);
		model.addAttribute("order", order);
		return "success";
	}

}
