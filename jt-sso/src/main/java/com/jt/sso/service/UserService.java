package com.jt.sso.service;

import com.jt.common.vo.SysResult;
import com.jt.sso.pojo.User;

public interface UserService {

	public Boolean checkData(String param, int type);

	public String saveUser(User user);

	public String findUserByU_P(String username, String password);

	public SysResult findTicket(String ticket);

}
