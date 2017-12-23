package com.jt.manage.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.vo.ItemCatData;
import com.jt.common.vo.ItemCatResult;
import com.jt.manage.mapper.ItemCatMapper;
import com.jt.manage.pojo.ItemCat;
import com.mysql.jdbc.StringUtils;

import redis.clients.jedis.JedisCluster;

@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private ItemCatMapper itemCatMapper;

	@Autowired
	private static final Logger log = Logger.getLogger(ItemCatServiceImpl.class);

	// @Autowired
	// private RedisService redisService;

	// @Autowired
	// private RedisSentinelService redisService;

	@Autowired
	private JedisCluster jedisCluster;

	private static final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public List<ItemCat> findItemCatList(Long parentId) {

		List<ItemCat> itemCatList = new ArrayList<ItemCat>();
		String key = "ITEM_CAT_" + parentId;
		String result = jedisCluster.get(key);
		try {

			if (StringUtils.isEmptyOrWhitespaceOnly(result)) {

				ItemCat itemCat = new ItemCat();
				itemCat.setParentId(parentId);
				itemCatList = itemCatMapper.select(itemCat);

				String value = objectMapper.writeValueAsString(itemCatList);
				jedisCluster.set(key, value);
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

	@Override
	public ItemCatResult webFindItemCatList() {

		ItemCat ic = new ItemCat();
		ic.setStatus(1);
		// 为了减少访问数据库,一次性查询出所有的商品分类
		List<ItemCat> itemCatList = itemCatMapper.select(ic);

		// 根据商品分类的上级分类id进行分类
		Map<Long, List<ItemCat>> itemCatMap = new HashMap<Long, List<ItemCat>>();
		for (ItemCat itemCat : itemCatList) {

			if (itemCatMap.containsKey(itemCat.getParentId())) {

				itemCatMap.get(itemCat.getParentId()).add(itemCat);
			} else {

				itemCatMap.put(itemCat.getParentId(), new ArrayList<ItemCat>());
				itemCatMap.get(itemCat.getParentId()).add(itemCat);
			}
		}

		// 对数据进行整合
		ItemCatResult result = new ItemCatResult();

		// 一级菜单
		List<ItemCatData> itemCats1 = new ArrayList<ItemCatData>();
		for (ItemCat itemCat1 : itemCatMap.get(0L)) {

			ItemCatData itemCatData1 = new ItemCatData();
			itemCatData1.setUrl("/products/" + itemCat1.getId() + ".html"); // Url
			itemCatData1.setName("<a herf='" + itemCatData1.getUrl() + "'>" + itemCat1.getName() + "<a/>"); // Name

			// 二级菜单
			List<ItemCatData> itemCats2 = new ArrayList<ItemCatData>(); // List<?>
			for (ItemCat itemCat2 : itemCatMap.get(itemCat1.getId())) {

				ItemCatData itemCatData2 = new ItemCatData();
				itemCatData2.setUrl("/products/" + itemCat2.getId()); // Url
				itemCatData2.setName(itemCat2.getName()); // Name

				// 三级菜单
				List<String> itemCats3 = new ArrayList<String>(); // List<?>
				for (ItemCat itemCat3 : itemCatMap.get(itemCat2.getId())) {

					String itemCatData3 = "/products/" + itemCat3.getId() + "|" + itemCat3.getName(); // Name
					itemCats3.add(itemCatData3);
				}

				itemCatData2.setItems(itemCats3);
				itemCats2.add(itemCatData2);
			}

			itemCatData1.setItems(itemCats2);

			if (itemCats1.size() > 13) {

				break;
			}

			itemCats1.add(itemCatData1);
		}

		result.setItemCats(itemCats1);
		return result;
	}

}
