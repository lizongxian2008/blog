/*   
 * Copyright (c) 2012 by XUANWU INFORMATION TECHNOLOGY CO. 
 *             All rights reserved                         
 */
package com.xuanwu.web.frontkit.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.xuanwu.web.common.utils.JsonUtil;

/**
 * @Description: Base Controller
 * @Author <a href="jiji@javawind.com">Xuefang.Xu</a>
 * @Date 2012-10-8
 * @Version 1.0.0
 */
public class BaseController {

	private final DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 格式化时间选择器
	 * 
	 * @param binder
	 * @throws Exception
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) throws Exception {
		// 时间格式化
		CustomDateEditor dateEditor = new CustomDateEditor(fmt, true);
		binder.registerCustomEditor(Date.class, dateEditor);

		// 字符串Trim()
		StringTrimmerEditor stringEditor = new StringTrimmerEditor(false);
		binder.registerCustomEditor(String.class, stringEditor);
	}

	public String sendAjaxJsonRespond(Model model, boolean ret) {
		String jsonData = ret ? JsonUtil.toJSON(0) : JsonUtil.toJSON(-1);
		model.addAttribute(Keys.JSON_DATA, jsonData);
		return Keys.AJAX_JSON;
	}

	protected String getBaseUrl(HttpServletRequest request) {
		String baseUrl = "http://" + request.getServerName();
		String context = request.getContextPath();
		if (!"".equals(context) && !"/".equals(context)) {
			baseUrl += context;
		}
		return baseUrl;
	}
}
