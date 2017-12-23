package com.jt.manage.controller.web;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.vo.ItemCatResult;
import com.jt.manage.service.ItemCatService;

@Controller
@RequestMapping("/web")
public class WebItemCatController {

	@Autowired
	private ItemCatService itemCatService;

	private static final ObjectMapper objectMapper = new ObjectMapper();

	private static final Logger log = Logger.getLogger(WebItemCatController.class);

	// http://manage.jt.com/web/itemcat/all?callback=category.getDataService
	
	@RequestMapping("/itemcat/all")
	public void webFindItemCatList(String callback, HttpServletResponse response) {

		ItemCatResult result = itemCatService.webFindItemCatList();
		try {

			String dataJSON = objectMapper.writeValueAsString(result);
			System.out.println(dataJSON);
			
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(callback + "(" + dataJSON + ")");

		} catch (Exception e) {

			e.printStackTrace();
			log.error(e.getMessage());
		}
	}
	
	
	/*
	@RequestMapping("/itemcat/all")
	@ResponseBody
	public Object webFindItemCatList(String callback) {

		ItemCatResult result = itemCatService.webFindItemCatList();

		MappingJacksonValue jacksonValue = new MappingJacksonValue(result);
		jacksonValue.setJsonpFunction(callback);
		return jacksonValue;
	}
	*/
}
