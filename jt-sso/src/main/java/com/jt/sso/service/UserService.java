package com.jt.sso.service;

import com.jt.sso.pojo.User;

public interface UserService {

	public Boolean checkData(String param, String type);

	public String saveUser(User user);

	public String findUserByU_P(String username, String password);

}
