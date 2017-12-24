package com.jt.sso.service;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.sso.mapper.UserMapper;
import com.jt.sso.pojo.User;

import redis.clients.jedis.JedisCluster;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private JedisCluster jedisCluster;

	private static final Logger log = Logger.getLogger(UserServiceImpl.class);
	private static final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public Boolean checkData(String param, String type) {

		String column = null;

		switch (type) {
		case "1":
			column = "username";
			break;
		case "2":
			column = "phone";
			break;
		case "3":
			column = "email";
			break;
		}

		Integer num = userMapper.findData(column, param);
		return num > 0 ? true : false;
	}

	@Override
	public String saveUser(User user) {

		// 完善数据(包括密码加密)
		String password = DigestUtils.md5Hex(user.getPassword());
		user.setPassword(password);

		user.setEmail(user.getPhone());
		user.setCreated(new Date());
		user.setUpdated(user.getCreated());

		try {
			userMapper.insert(user);
			return user.getUsername();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage() + "{用户注册数据插入数据库失败}");
			return null;
		}
	}

	@Override
	public String findUserByU_P(String username, String password) {

		// User user = new User();
		// user.setUsername(username);
		// password = DigestUtils.md5Hex(password);
		// user.setPassword(password);
		// int count = userMapper.selectCount(user);

		password = DigestUtils.md5Hex(password);

		User user = userMapper.findUserByU_P(username, password);

		try {
			if (user != null) {
				String ticket = DigestUtils.md5Hex("JT_TICKET_" + System.currentTimeMillis() + username);
				String userJSON = objectMapper.writeValueAsString(user);

				jedisCluster.set(ticket, userJSON);
				return ticket;
			} else {
				return null;
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			log.error(e.getMessage() + "{JSON转化异常}");
			return null;
		}
	}

}
