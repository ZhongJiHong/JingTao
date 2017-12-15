package com.jt.manage.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.EasyUIResult;
import com.jt.manage.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;

	private static final Logger log = Logger.getLogger(ItemController.class);

	// 实现分页查询
	@RequestMapping("/query")
	@ResponseBody
	public EasyUIResult findItemList(Integer page, Integer rows) {

		return itemService.findItemList(page, rows);
	}

	// 同步查询商品的分类名称
	/**
	@RequestMapping("/cat/queryItemCatName")
	public void queryItemCatName(Long cid, HttpServletResponse response) {

		String name = itemService.queryItemCatName(cid);
		response.setContentType("text/html;charset=utf-8");
		try {
			response.getWriter().write(name);
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage() + "{商品类名查询失败}");
		}
	}
	*/
	@RequestMapping(value = "/cat/queryItemCatName", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String queryItemCatName(Long cid) {

		return itemService.queryItemCatName(cid);
	}
	
}
