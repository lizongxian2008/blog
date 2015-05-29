/*   
 * Copyright (c) 2012 by XUANWU INFORMATION TECHNOLOGY CO. 
 *             All rights reserved                         
 */
package com.xuanwu.web.common.entity;

/**
 * @Description: 权限依赖实体
 * @Author <a href="jiji@javawind.com">Xuefang.Xu</a>
 * @Date 2013-1-7
 * @Version 1.0.0
 */
public class PermissionDepends extends BaseEntity {

	private int priId;// 权限id
	private int subId;// 依赖id

	public int getPriId() {
		return priId;
	}

	public void setPriId(int priId) {
		this.priId = priId;
	}

	public int getSubId() {
		return subId;
	}

	public void setSubId(int subId) {
		this.subId = subId;
	}

	@Override
	public String toJSON() {
		StringBuffer sb = new StringBuffer();
		sb.append("{\"priId\":" + priId);
		sb.append(",\"subId\":" + subId + "}");
		return sb.toString();
	}

}
