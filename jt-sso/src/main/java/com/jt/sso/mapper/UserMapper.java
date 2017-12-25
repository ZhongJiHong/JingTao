package com.jt.sso.mapper;

import org.apache.ibatis.annotations.Param;

import com.jt.common.mapper.SysMapper;
import com.jt.sso.pojo.User;

public interface UserMapper extends SysMapper<User> {

	public int findData(@Param("column") String column, @Param("param") String param);

	public User findUserByU_P(@Param("username") String username, @Param("password") String password);

}
