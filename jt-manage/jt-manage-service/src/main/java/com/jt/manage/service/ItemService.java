package com.jt.manage.service;

import com.jt.common.vo.EasyUIResult;
import com.jt.manage.pojo.Item;

public interface ItemService {

	public EasyUIResult findItemList(Integer page, Integer rows);

	public String queryItemCatName(Long cid);

	public void saveItem(Item item,String desc);

	public void updateItem(Item item,String desc);

	public void deleteItems(Integer status,Long[] ids);

	public void updateInstockItem(Integer status,Long[] ids);

	public void updateReshelItem(Integer status, Long[] ids);

}
