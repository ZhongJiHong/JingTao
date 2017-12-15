package com.jt.manage.service;

import com.jt.common.vo.EasyUIResult;

public interface ItemService {

	public EasyUIResult findItemList(Integer page, Integer rows);

	public String queryItemCatName(Long cid);

}
