package com.jt.cart.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.cart.pojo.Cart;
import com.jt.cart.service.CartService;
import com.jt.common.vo.SysResult;

@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;

	private static final Logger log = Logger.getLogger(CartController.class);
	private static final ObjectMapper objectMapper = new ObjectMapper();

	// 实现购物车页面数据的查询 http://cart.jt.com/cart/query/{userId}
	@RequestMapping("/query/{userId}")
	@ResponseBody
	public SysResult findCartByUserId(@PathVariable Long userId) {

		try {
			List<Cart> cartList = cartService.findCartByUserId(userId);
			String cartJSON = objectMapper.writeValueAsString(cartList);
			return SysResult.oK(cartJSON);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return SysResult.build(201, "购物车查询失败");
		}

	}

	// 实现购物车商品购买数量的修改 http://cart.jt.com/cart/update/num/{userId}/{itemId}/{num}
	@RequestMapping("/update/num/{userId}/{itemId}/{num}")
	@ResponseBody
	public SysResult updateCartNum(@PathVariable Long userId, @PathVariable Long itemId, @PathVariable Integer num) {

		return cartService.updateCartNum(userId, itemId, num);
	}

	// 实现购物车中商品的删除 http://cart.jt.com/cart/delete/{userId}/{itemId}
	@RequestMapping("/delete/{userId}/{itemId}")
	@ResponseBody
	public SysResult deleteCartItem(@PathVariable Long userId, @PathVariable Long itemId) {

		return cartService.deleteCartItem(userId, itemId);
	}

	// 实现将商品添加到购物车 http://cart.jt.com/cart/save
	@RequestMapping("/save")
	@ResponseBody
	public SysResult saveCartItem(Cart cart) {

		return cartService.saveCartItem(cart);
	}

}
