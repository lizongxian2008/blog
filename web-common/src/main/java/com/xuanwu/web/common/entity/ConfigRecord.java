/**
 * Copyright (c) 2010 by XUANWU INFORMATION TECHNOLOGY CO. 
 *             All rights reserved     
 */
package com.xuanwu.web.common.entity;

/**
 * 
* @Description:
* @Author <a href="hw86xll@163.com">Wei.Huang</a>
* @Date 2012-9-20
* @Version 1.0.0
 */
public class ConfigRecord {
	/** 配置项的键 */
	private String key;
	
	/** 配置项的值 */
	private String value;
	
	/** 配置项的类型 */
	private String type;
	
	/** 0-Backend, 2-FrontKit */
	private int platformId;
	
	public ConfigRecord(){
		
	}
	public ConfigRecord(String key, String value, String type) {
		super();
		this.key = key;
		this.value = value;
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getPlatformId() {
		return platformId;
	}
	
	public void setPlatformId(int platformId) {
		this.platformId = platformId;
	}

}
