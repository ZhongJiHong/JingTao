package com.jt.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jt.web.pojo.Item;
import com.jt.web.service.ItemService;

@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;

	// 请求地址:/items/562379.html
	// 转发到: /WEB-INF/views/items/562379.jsp

	@RequestMapping("/items/{itemId}")
	public String findItemById(@PathVariable Long itemId, Model model) {

		Item item = itemService.findItemById(itemId);

		model.addAttribute("item", item);
		return "item";
	}
}
