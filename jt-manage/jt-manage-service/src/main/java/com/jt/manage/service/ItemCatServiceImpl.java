package com.jt.manage.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.RedisSentinelService;
import com.jt.common.service.RedisService;
import com.jt.manage.mapper.ItemCatMapper;
import com.jt.manage.pojo.ItemCat;
import com.mysql.jdbc.StringUtils;

@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private ItemCatMapper itemCatMapper;

	@Autowired
	private static final Logger log = Logger.getLogger(ItemCatServiceImpl.class);

	// @Autowired
	// private RedisService redisService;

	@Autowired
	private RedisSentinelService redisService;

	private static final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public List<ItemCat> findItemCatList(Long parentId) {

		List<ItemCat> itemCatList = new ArrayList<ItemCat>();
		String key = "ITEM_CAT_" + parentId;
		String result = redisService.get(key);
		try {

			if (StringUtils.isEmptyOrWhitespaceOnly(result)) {

				ItemCat itemCat = new ItemCat();
				itemCat.setParentId(parentId);
				itemCatList = itemCatMapper.select(itemCat);

				String value = objectMapper.writeValueAsString(itemCatList);
				redisService.set(key, value);
			} else {

				// JSON串转数组,数组转ArrayList集合
				ItemCat[] itemCats = objectMapper.readValue(result, ItemCat[].class);
				itemCatList = Arrays.asList(itemCats);
				return itemCatList;
			}
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage() + "{Redis缓存异常}");
		}
		return itemCatList;
	}
}
