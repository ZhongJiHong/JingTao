package com.jt.manage.service;

import java.util.Date;
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

	@Override
	public void saveItem(Item item) {

		item.setCreated(new Date());
		item.setStatus(1);
		item.setUpdated(item.getCreated());
		itemMapper.insertSelective(item);
	}

	@Override
	public void updateItem(Item item) {

		item.setUpdated(new Date());
		itemMapper.updateByPrimaryKeySelective(item);
	}

	@Override
	public void deleteItems(Integer status,Long[] ids) {

		itemMapper.updateItemStatus(status, ids);
	}

	@Override
	public void updateInstockItem(Integer status, Long[] ids) {

		itemMapper.updateItemStatus(status, ids);
	}

	@Override
	public void updateReshelItem(Integer status, Long[] ids) {

		itemMapper.updateItemStatus(status, ids);
	}
}
