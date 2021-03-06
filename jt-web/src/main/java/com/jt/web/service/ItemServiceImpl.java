package com.jt.web.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.web.pojo.Item;

import redis.clients.jedis.JedisCluster;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private HttpClientService httpClientService;
	@Autowired
	private JedisCluster jedisCluster;

	private static final ObjectMapper objectMapper = new ObjectMapper();

	private static final Logger log = Logger.getLogger(ItemServiceImpl.class);
	
	/*
	@Override
	public Item findItemById(Long itemId) {

		String url = "http://manage.jt.com/web/item/" + itemId;
		try {
			String dataJSON = httpClientService.doGet(url);
			Item item = objectMapper.readValue(dataJSON, Item.class);
			return item;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage() + "{数据获取失败}");
		}
		return null;
	}
	*/
	
	@Override
	public Item findItemById(Long itemId) {

		String key = "ITEM_" + itemId;
		String redisJSON = jedisCluster.get(key);

		try {
			if (StringUtils.isEmpty(redisJSON)) {
				String url = "http://manage.jt.com/web/item/" + itemId;
				String dataJSON = httpClientService.doGet(url);

				Item item = objectMapper.readValue(dataJSON, Item.class);
				// 将数据装到缓存数据库中
				jedisCluster.set(key, dataJSON);

				return item;
			} else {
				Item item = objectMapper.readValue(redisJSON, Item.class);
				return item;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage() + "{数据获取失败}");
		}
		return null;
	}

}
