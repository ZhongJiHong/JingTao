package com.jt.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.util.CookieUtils;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.Cart;
import com.jt.web.service.CartService;
import com.jt.web.utils.UserThreadLocal;

import redis.clients.jedis.JedisCluster;

@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private JedisCluster jedisCluster;

	@Autowired
	private CartService cartService;

	private static final ObjectMapper objectMapper = new ObjectMapper();
	private static final Logger log = Logger.getLogger(CartController.class);

	// 转向购物车页面 http://http://www.jt.com/cart/show.html
	@RequestMapping("/show")
	public String toCart(HttpServletRequest request, Model model) {

		// Long userId = 13L;
		Long userId = UserThreadLocal.getUser().getId();
		try {
			List<Cart> cartList = cartService.findCartByUserId(userId);

			model.addAttribute("cartList", cartList);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return "cart";
	}

	// 购物车---商品购买数量的修改
	// http://www.jt.com/service/cart/update/num/1474391955/6
	@RequestMapping("/update/num/{itemId}/{num}")
	@ResponseBody // 查看js,并不需要任何返回数据
	public SysResult updateCartNum(@PathVariable Long itemId, @PathVariable Integer num) {

		// Long userId = 13L;
		Long userId = UserThreadLocal.getUser().getId();
		return cartService.updateCartNum(userId, itemId, num);
	}

	// 购物车--商品的删除
	// http://www.jt.com/cart/delete/1474391957.html
	@RequestMapping("/delete/{itemId}")
	public String deleteCartItem(@PathVariable Long itemId) {

		// Long userId = 13L;
		Long userId = UserThreadLocal.getUser().getId();
		SysResult sysResult = cartService.deleteCartItem(userId, itemId);
		// 不管成败与否,皆重定向到购物车
		return "redirect:/cart/show.html";
	}

	// 购物车--商品的新增 http://www.jt.com/cart/add/1474391954.html
	@RequestMapping("/add/{itemId}")
	public String saveCartItem(@PathVariable Long itemId, Cart cart) {

		// Long userId = 13L;
		Long userId = UserThreadLocal.getUser().getId();
		SysResult sysResult = cartService.saveCartItem(userId, itemId, cart);

		return "redirect:/cart/show.html";
	}

}
