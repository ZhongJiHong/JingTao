package com.jt.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.SysResult;
import com.jt.sso.pojo.User;
import com.jt.sso.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	// 请求地址:http://sso.jt.com/user/check/sunday123/1
	@RequestMapping("/check/{param}/{type}")
	@ResponseBody
	public Object checkData(@PathVariable String param, @PathVariable int type, String callback) {

		Boolean result = userService.checkData(param, type);

		MappingJacksonValue jacksonValue = new MappingJacksonValue(SysResult.oK(result));
		jacksonValue.setJsonpFunction(callback);
		return jacksonValue;
	}

	// 注册 http://sso.jt.com/user/register
	@RequestMapping("/register")
	@ResponseBody
	public SysResult saveUser(User user) {

		String result = userService.saveUser(user);

		if (StringUtils.isEmpty(result)) {

			return SysResult.build(201, "用户注册失败");
		} else {

			return SysResult.oK(result);
		}
	}

	// 登陆 http://sso.jt.com/user/login
	@RequestMapping("/login")
	@ResponseBody
	public SysResult findUserByU_P(@RequestParam("u") String username, @RequestParam("p") String password) {

		String ticket = userService.findUserByU_P(username, password);

		if (StringUtils.isEmpty(ticket)) {
			return SysResult.build(201, "用户名或密码不存在");
		} else {
			return SysResult.oK(ticket);
		}
	}

	// http://sso.jt.com/user/query/{ticket}
	@RequestMapping("/query/{ticket}")
	@ResponseBody
	public Object findTicket(@PathVariable String ticket, String callback) {

		SysResult sysResult = userService.findTicket(ticket);
		MappingJacksonValue jacksonValue = new MappingJacksonValue(sysResult);
		jacksonValue.setJsonpFunction(callback);

		return jacksonValue;
	}

}
