package com.jt.web.service;

import com.jt.common.vo.SysResult;
import com.jt.web.pojo.User;

public interface UserService {

	public SysResult doRegister(User user);

	public SysResult doLogin(String username, String password);

}
