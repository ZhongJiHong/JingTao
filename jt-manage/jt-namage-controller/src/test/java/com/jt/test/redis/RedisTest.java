package com.jt.test.redis;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.jt.common.service.RedisService;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@Controller
public class RedisTest {

	@Test // 测试redis
	public void test01() {

		// jedis表示redis客户端对象
		Jedis jedis = new Jedis("106.75.28.165", 6379);
		String result = jedis.set("name", "1708");
		System.out.println(result);
		String value = jedis.get("name");
		System.out.println(value);
		jedis.close();
	}

	// Redis分片测试
	@Test
	public void test02() {

		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		shards.add(new JedisShardInfo("106.75.28.165", 6379));
		shards.add(new JedisShardInfo("106.75.35.187", 6379));
		shards.add(new JedisShardInfo("106.75.95.167", 6379));

		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(100);

		ShardedJedisPool jedisPool = new ShardedJedisPool(poolConfig, shards);

		ShardedJedis jedis = jedisPool.getResource();
		jedis.set("name", "1707");
		jedis.set("pobaby", "popoAndyaya");
		
		jedisPool.returnResource(jedis);
		jedisPool.close();
	}
	
	// Spring整合Redis分片测试
	@Test
	public void test03(){
		
	}
}
