package com.jt.web.utils;

import com.jt.web.pojo.User;

public class UserThreadLocal{

	private static ThreadLocal<User> threadLocal = new ThreadLocal<User>();

	public static User getUser() {
		return threadLocal.get();
	}

	public static void setUser(User user) {
		threadLocal.set(user);
	}

}
