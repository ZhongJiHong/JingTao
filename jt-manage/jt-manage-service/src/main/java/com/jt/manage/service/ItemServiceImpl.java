package com.jt.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jt.common.vo.EasyUIResult;
import com.jt.manage.mapper.ItemMapper;
import com.jt.manage.pojo.Item;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemMapper itemMapper;

	@Override
	public EasyUIResult findItemList(Integer page, Integer rows) {

		PageHelper.startPage(page, rows);
		List<Item> itemList = itemMapper.findAll();

		PageInfo<Item> pageInfo = new PageInfo<Item>(itemList);

		return new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
	}

	@Override
	public String queryItemCatName(Long cid) {

		return itemMapper.queryItemCatName(cid);
	}
}
