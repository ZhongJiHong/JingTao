package com.jt.cart.service;

import java.util.List;

import com.jt.cart.pojo.Cart;
import com.jt.common.vo.SysResult;

public interface CartService {
	/**
	 * 实现根据用户id查询用户购物车数据
	 * @param userId:用户id
	 * @return
	 */
	public List<Cart> findCartByUserId(Long userId);

	/**
	 * 实现购物车商品购买数量的修改
	 * 
	 * @param userId:用户id
	 * @param itemId:商品id
	 * @param num:购买数量
	 * @return SysResult
	 */
	public SysResult updateCartNum(Long userId, Long itemId, Integer num);

	/**
	 * 实现购物车商品的删除
	 * 
	 * @param userId:用户id
	 * @param itemId:商品id
	 * @return SysResult
	 */
	public SysResult deleteCartItem(Long userId, Long itemId);
	
	/**
	 * 实现将商品添加到购物车
	 * @param cart:封装的用户id,商品信息
	 * @return
	 */
	public SysResult saveCartItem(Cart cart);

}
