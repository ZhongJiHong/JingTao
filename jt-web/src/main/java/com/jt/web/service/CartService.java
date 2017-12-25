package com.jt.web.service;

import java.util.List;

import com.jt.common.vo.SysResult;
import com.jt.web.pojo.Cart;

public interface CartService {

	/**
	 * 根据用户ID查询购物车数据
	 * 
	 * @return
	 */
	public List<Cart> findCartByUserId(Long userId);

	/**
	 * 根据userId和itemId更新商品的购买数量
	 * 
	 * @param userId:用户id
	 * @param itemId:商品id
	 * @param num:购买数量
	 * @return SysResult
	 */
	public SysResult updateCartNum(Long userId, Long itemId, Integer num);

	/**
	 * 根据userId和itemId删除购物车中的商品
	 * 
	 * @param userId:用户id
	 * @param itemId:商品id
	 * @return SysResult
	 */
	public SysResult deleteCartItem(Long userId, Long itemId);
	
	/**
	 * 根据userId和itemId将商品添加到购物车
	 * @param userId:用户id
	 * @param itemId:商品id
	 * @param cart:商品的信息(商品名称title,价格price,数量num,主图image)
	 * @return SysResult
	 */
	public SysResult saveCartItem(Long userId, Long itemId,Cart cart);

}
