package com.jt.manage.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.EasyUIResult;
import com.jt.common.vo.SysResult;
import com.jt.manage.pojo.Item;
import com.jt.manage.pojo.ItemDesc;
import com.jt.manage.service.ItemDescService;
import com.jt.manage.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;

	@Autowired
	private ItemDescService itemDescService;

	private static final Logger log = Logger.getLogger(ItemController.class);

	// 实现分页查询
	@RequestMapping("/query")
	@ResponseBody
	public EasyUIResult findItemList(Integer page, Integer rows) {

		return itemService.findItemList(page, rows);
	}

	// 同步查询商品的分类名称
	/**
	 * @RequestMapping("/cat/queryItemCatName") public void
	 * queryItemCatName(Long cid, HttpServletResponse response) {
	 * 
	 * String name = itemService.queryItemCatName(cid);
	 * response.setContentType("text/html;charset=utf-8"); try {
	 * response.getWriter().write(name); } catch (IOException e) {
	 * e.printStackTrace(); log.error(e.getMessage() + "{商品类名查询失败}"); } }
	 */
	@RequestMapping(value = "/cat/queryItemCatName", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String queryItemCatName(Long cid) {

		return itemService.queryItemCatName(cid);
	}

	// 新增商品
	@RequestMapping("/save")
	@ResponseBody
	public SysResult saveItem(Item item, String desc) {

		try {
			itemService.saveItem(item, desc);
			return SysResult.build(200, "新增商品成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage() + "{新增商品失败}");
			return SysResult.build(201, "新增商品失败");
		}
	}

	// 商品信息修改
	@RequestMapping("/update")
	@ResponseBody
	public SysResult updateItem(Item item,String desc) {

		try {
			itemService.updateItem(item,desc);
			return SysResult.build(200, "商品修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage() + "{商品修改失败}");
			return SysResult.build(201, "商品修改失败");
		}
	}

	// 商品删除 --- 删除操作是非常敏感的操作,不是真正的删除
	@RequestMapping("/delete")
	@ResponseBody
	public SysResult deleteItem(Long[] ids) {

		try {
			Integer status = 3;
			itemService.deleteItems(status, ids);
			return SysResult.build(200, "商品删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage() + "{商品删除失败}");
			return SysResult.build(201, "商品删除失败");
		}

	}

	// 商品下架
	@RequestMapping("/instock")
	@ResponseBody
	public SysResult updateInstockItem(Long[] ids) {

		try {
			Integer status = 2;
			itemService.updateInstockItem(status, ids);
			return SysResult.build(200, "商品已成功下架");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage() + "{商品下架失败}");
			return SysResult.build(200, "商品下架失败");
		}
	}

	// 商品上架
	@RequestMapping("/reshelf")
	@ResponseBody
	public SysResult updateReshelfItem(Long[] ids) {

		try {
			Integer status = 1;
			itemService.updateReshelItem(status, ids);
			return SysResult.build(200, "商品已成功上架");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage() + "{商品上架失败}");
			return SysResult.build(200, "商品上架失败");
		}
	}

	// 商品回显时,商品详情的回显
	@RequestMapping("/query/item/desc/{itemId}")
	@ResponseBody
	public SysResult queryItemDesc(@PathVariable Long itemId) {

		try {
			ItemDesc itemDesc = itemDescService.queryItemDesc(itemId);
			return SysResult.build(200, "商品详情查询成功", itemDesc);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage() + "{商品详情查询失败}");
			return SysResult.build(201, "商品详情查询失败");
		}

	}

}
