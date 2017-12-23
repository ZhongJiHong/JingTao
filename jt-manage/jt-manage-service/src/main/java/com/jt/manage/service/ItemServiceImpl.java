package com.jt.manage.service;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jt.common.vo.EasyUIResult;
import com.jt.manage.mapper.ItemDescMapper;
import com.jt.manage.mapper.ItemMapper;
import com.jt.manage.pojo.Item;
import com.jt.manage.pojo.ItemDesc;

import redis.clients.jedis.JedisCluster;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private ItemDescMapper itemDescMapper;
	@Autowired
	private JedisCluster jedisCluster;

	@Value(value = "${ITEM_KEY}")
	private String itemKey;

	private static final ObjectMapper objectMapper = new ObjectMapper();
	private static final Logger log = Logger.getLogger(ItemServiceImpl.class);

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
	public void saveItem(Item item, String desc) {

		item.setCreated(new Date());
		item.setStatus(1);
		item.setUpdated(item.getCreated());

		itemMapper.insertSelective(item);

		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(item.getCreated());
		itemDesc.setUpdated(item.getCreated());

		itemDescMapper.insert(itemDesc);

		try {

			String dataJSON = objectMapper.writeValueAsString(item);
			// 需要和前台的商品缓存key值保持一致,动态获取
			jedisCluster.set(itemKey + item.getId(), dataJSON);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			log.error(e.getMessage() + "{缓存添加失败}");
		}

	}

	@Override
	public void updateItem(Item item, String desc) {

		item.setUpdated(new Date());
		itemMapper.updateByPrimaryKeySelective(item);

		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);

		itemDescMapper.updateByPrimaryKeySelective(itemDesc);

		// 删除商品缓存
		jedisCluster.del(itemKey + item.getId());
	}

	@Override
	public void deleteItems(Integer status, Long[] ids) {

		itemMapper.updateItemStatus(status, ids);

		for (Long id : ids) {
			jedisCluster.del(itemKey + id);
		}
	}

	@Override
	public void updateInstockItem(Integer status, Long[] ids) {

		itemMapper.updateItemStatus(status, ids);
	}

	@Override
	public void updateReshelItem(Integer status, Long[] ids) {

		itemMapper.updateItemStatus(status, ids);
	}

	@Override
	public Item findItemById(Long itemId) {

		return itemMapper.selectByPrimaryKey(itemId);
	}
}
