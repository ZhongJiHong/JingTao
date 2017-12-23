package com.jt.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

	// 实现页面的转向
	@RequestMapping("/user/login")
	public String toLogin() {

		return "login";
	}

	@RequestMapping("/user/register")
	public String toRegister() {

		return "register";
	}
}
