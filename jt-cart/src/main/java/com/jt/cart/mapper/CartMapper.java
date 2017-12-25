package com.jt.cart.mapper;

import org.apache.ibatis.annotations.Param;

import com.jt.cart.pojo.Cart;
import com.jt.common.mapper.SysMapper;

public interface CartMapper extends SysMapper<Cart> {

	public void updateCartNum(@Param("userId") Long userId, @Param("itemId") Long itemId, @Param("num") Integer num);

}
