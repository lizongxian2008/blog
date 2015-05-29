/*
 * Copyright (c) 2012 by XUANWU INFORMATION TECHNOLOGY CO. 
 *             All rights reserved                         
 */
package com.xuanwu.web.common.security;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.AuthorizationInfo;

import com.xuanwu.web.common.entity.RolePermission;
import com.xuanwu.web.common.utils.ListUtil;

/**
 * @Description Default authorization information
 * @author <a href="mailto:liushuaiying@139130.net">Shuaiying.Liu</a>
 * @Data 2012-9-5
 * @Version 1.0.0
 */
public class ShiroAuthorizationInfo implements AuthorizationInfo {

	private static final long serialVersionUID = -1233002746492148418L;

	private HashMap<String, RolePermission> urlPermMap;

	@Override
	public Collection<String> getRoles() {
		return null;
	}

	@Override
	public Collection<String> getStringPermissions() {
		return null;
	}

	@Override
	public Collection<org.apache.shiro.authz.Permission> getObjectPermissions() {
		return null;
	}

	public RolePermission getPermission(String url) {
		if (urlPermMap == null || urlPermMap.isEmpty())
			return null;
		return urlPermMap.get(url);
	}

	public Map<String, RolePermission> getPermissions() {
		return urlPermMap;
	}

	public void addPermission(RolePermission perm) {
		if (urlPermMap == null) {
			urlPermMap = new HashMap<String, RolePermission>();
		}
		RolePermission old = urlPermMap.get(perm.getBaseUrl());
		// 如果权限已包含，那么取较大的数据范围
		if (old != null
				&& old.getDataScope().getIndex() >= perm.getDataScope()
						.getIndex()) {
			return;
		}
		urlPermMap.put(perm.getBaseUrl(), perm);
	}

	public void addPermissions(List<RolePermission> perms) {
		if (ListUtil.isBlank(perms))
			return;
		for (RolePermission perm : perms) {
			addPermission(perm);
		}
	}
}
