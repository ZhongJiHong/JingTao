package com.jt.web.interseptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.util.CookieUtils;
import com.jt.web.pojo.User;
import com.jt.web.utils.UserThreadLocal;

import redis.clients.jedis.JedisCluster;

public class WebInterseptor implements HandlerInterceptor {

	@Autowired
	private JedisCluster jedisCluster;

	private static final ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * boolean值 true表示放行 false表示拦截,不允许访问本次的请求地址
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		/**
		 * 逻辑:
		 */
		String ticket = CookieUtils.getCookieValue(request, "JT_TICKET");
		if (!StringUtils.isEmpty(ticket)) {
			String userJSON = jedisCluster.get(ticket);
			if (!StringUtils.isEmpty(userJSON)) {
				User user = objectMapper.readValue(userJSON, User.class);
				UserThreadLocal.setUser(user);
				return true;
			}
		}
		
		response.sendRedirect("/user/login.html");
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
