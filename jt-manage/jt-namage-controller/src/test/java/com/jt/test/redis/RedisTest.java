package com.jt.test.redis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.springframework.stereotype.Controller;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@Controller
public class RedisTest {
	
	// 测试redis
	/**
	@Test 
	public void test01() {

		// jedis表示redis客户端对象
		Jedis jedis = new Jedis("106.75.28.165", 6379);
		String result = jedis.set("name", "1708");
		System.out.println(result);
		String value = jedis.get("name");
		System.out.println(value);
		jedis.close();
	}
	*/
	
	
	// Redis分片测试
	/**
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
	*/

	
	// 通过Jedis操作哨兵
	/**
	@Test
	public void test03() {

		Set<String> sentinels = new HashSet<String>();
		sentinels.add(new HostAndPort("106.75.28.165", 26379).toString());
		sentinels.add(new HostAndPort("106.75.35.187", 26379).toString());
		sentinels.add(new HostAndPort("106.75.95.167", 26379).toString());

		JedisSentinelPool jedisPool = new JedisSentinelPool("mymaster", sentinels);
		Jedis jedis = jedisPool.getResource();
		jedis.set("age", "123");
		String result = jedis.get("age");
		System.out.println(result);
	}
	*/
}
