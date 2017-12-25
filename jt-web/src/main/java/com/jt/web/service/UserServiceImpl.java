package com.jt.web.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private HttpClientService httpClientService;

	private static final ObjectMapper objectMapper = new ObjectMapper();
	private static final Logger log = Logger.getLogger(UserServiceImpl.class);

	@Override
	public SysResult doRegister(User user) {

		// HttpClient实现跨域请求
		String url = "http://sso.jt.com/user/register";
		Map<String, String> params = new HashMap<String, String>();
		params.put("username", user.getUsername());
		params.put("password", user.getPassword());
		params.put("phone", user.getPhone());
		params.put("email", user.getEmail());

		try {
			String resultJSON = httpClientService.doPost(url, params);

			// JSON中数据的获取
			JsonNode jsonNode = objectMapper.readTree(resultJSON);
			String data = jsonNode.get("data").asText();
			System.out.println("JSON中回去data的数据为:" + data);

			return objectMapper.readValue(resultJSON, SysResult.class);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage() + "{用户注册失败}");
			return SysResult.build(201, "用户注册失败");
		}
	}

	@Override
	public SysResult doLogin(String username, String password) {

		String url = "http://sso.jt.com/user/login";

		Map<String, String> params = new HashMap<String, String>();
		params.put("u", username);
		params.put("p", password);

		try {
			String resultJSON = httpClientService.doPost(url, params);
			JsonNode jsonNode = objectMapper.readTree(resultJSON);
			String data = jsonNode.get("data").asText();
			System.out.println("JSON中获取的data数据为:" + data);

			return objectMapper.readValue(resultJSON, SysResult.class);
		} catch (Exception e) {

			e.printStackTrace();
			log.error(e.getMessage()+"{用户登陆失败}");
			return SysResult.build(201, "登陆失败,请重新登陆");
		}
	}

}
