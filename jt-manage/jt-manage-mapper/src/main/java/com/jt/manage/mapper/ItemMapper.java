package com.jt.manage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jt.common.mapper.SysMapper;
import com.jt.manage.pojo.Item;

public interface ItemMapper extends SysMapper<Item> {

	public List<Item> findAll();

	public String queryItemCatName(Long cid);

	public void updateItemStatus(@Param("status")Integer status, @Param("ids")Long[] ids);
}
