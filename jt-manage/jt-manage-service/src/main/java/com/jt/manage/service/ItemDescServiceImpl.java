package com.jt.manage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.manage.mapper.ItemDescMapper;
import com.jt.manage.pojo.ItemDesc;

@Service
public class ItemDescServiceImpl implements ItemDescService {

	@Autowired
	private ItemDescMapper itemDescMapper;

	@Override
	public ItemDesc queryItemDesc(Long itemId) {

		return itemDescMapper.selectByPrimaryKey(itemId);
	}

}
