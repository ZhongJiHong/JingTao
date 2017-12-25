package com.jt.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.util.CookieUtils;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.User;
import com.jt.web.service.UserService;

import redis.clients.jedis.JedisCluster;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private JedisCluster jedisCluster;

	// 实现页面的转向
	@RequestMapping("/login")
	public String toLogin() {

		return "login";
	}

	@RequestMapping("/register")
	public String toRegister() {

		return "register";
	}

	// 用户注册http://www.jt.com/service/user/doRegister
	@RequestMapping("/doRegister")
	@ResponseBody
	public SysResult doRegister(User user) {

		return userService.doRegister(user);

	}

	// 用户登陆 http://www.jt.com/service/user/doLogin
	@RequestMapping("/doLogin")
	@ResponseBody
	public SysResult doLogin(String username, String password, HttpServletRequest request,
			HttpServletResponse response) {

		// 首先判断用户名或密码是否为空
		if (StringUtils.isEmpty(username) | StringUtils.isEmpty(password)) {
			return SysResult.build(201, "用户名或密码不能为空");
		}
		SysResult result = userService.doLogin(username, password);

		String ticket = (String) result.getData();

		CookieUtils.setCookie(request, response, "JT_TICKET", ticket);
		return result;
	}

	// 用户退出 /user/logout.html
	@RequestMapping("/logout")
	public String doLogout(HttpServletRequest request, HttpServletResponse response) {

		String ticket = CookieUtils.getCookieValue(request, "JT_TICKET");
		jedisCluster.del(ticket);

		CookieUtils.deleteCookie(request, response, "JT_TICKET");

		return "redirect:/index.html";
	}

}
