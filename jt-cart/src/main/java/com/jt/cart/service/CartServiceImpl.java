package com.jt.cart.service;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.cart.mapper.CartMapper;
import com.jt.cart.pojo.Cart;
import com.jt.common.service.BaseService;
import com.jt.common.vo.SysResult;

@Service
public class CartServiceImpl extends BaseService<Cart>implements CartService {

	@Autowired
	private CartMapper cartMapper;

	private static final Logger log = Logger.getLogger(CartServiceImpl.class);

	@Override
	public List<Cart> findCartByUserId(Long userId) {

		Cart cart = new Cart();
		cart.setUserId(userId);

		List<Cart> cartList = cartMapper.select(cart);
		return cartList;
	}

	@Override
	public SysResult updateCartNum(Long userId, Long itemId, Integer num) {

		try {
			cartMapper.updateCartNum(userId, itemId, num);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage() + "{购物车商品数量修改失败}");
			return SysResult.build(201, "购物车商品购买数量修改失败");
		}
	}

	@Override
	public SysResult deleteCartItem(Long userId, Long itemId) {

		Cart cart = new Cart();
		cart.setUserId(userId);
		cart.setItemId(itemId);

		try {
			cartMapper.delete(cart);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage() + "{购物车商品删除失败}");
			return SysResult.build(201, "购物车商品删除失败");
		}
	}

	@Override
	public SysResult saveCartItem(Cart cart) {

		/*
		 * 业务逻辑:商品重复
		 */
		Cart temp = new Cart();
		temp.setUserId(cart.getUserId());
		temp.setItemId(cart.getItemId());

		Cart domain = super.queryByWhere(temp);

		try {
			if (domain != null) {
				Integer num = cart.getNum() + domain.getNum();
				domain.setNum(num);

				domain.setUpdated(new Date());
				super.update(domain);
			} else {
				// 检查数据的完整性
				cart.setCreated(new Date());
				cart.setUpdated(cart.getCreated());

				cartMapper.insert(cart);
			}
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return SysResult.build(201, "商品添加失败");
		}

	}

}
