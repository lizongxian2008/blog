/*
 * Copyright (c) 2012 by XUANWU INFORMATION TECHNOLOGY CO. 
 *             All rights reserved                         
 */
package com.xuanwu.web.common.security;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.xuanwu.web.common.entity.Permission;
import com.xuanwu.web.common.entity.RolePermission;
import com.xuanwu.web.common.entity.SimpleUser;
import com.xuanwu.web.common.service.BizDataService;
import com.xuanwu.web.common.service.UserService;
import com.xuanwu.web.common.utils.ListUtil;
import com.xuanwu.web.common.utils.SessionUtil;
import com.xuanwu.web.common.utils.WebConstants;

/**
 * @Description Default DB Realm
 * @author <a href="mailto:liushuaiying@139130.net">Shuaiying.Liu</a>
 * @Data 2012-9-5
 * @Version 1.0.0
 */
public class ShiroDBRealm extends AuthorizingRealm {

	private static RolePermission mainPerm = new RolePermission("/main");
	private static RolePermission rootPathPerm = new RolePermission("/");

	private UserService userService;

	private BizDataService bizDataService;

	@Override
	public boolean isPermitted(PrincipalCollection principals, String permission) {
		ShiroAuthorizationInfo info = (ShiroAuthorizationInfo) getAuthorizationInfo(principals);
		if (info.getPermission(permission) != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isPermitted(PrincipalCollection principals,
			org.apache.shiro.authz.Permission permission) {
		ShiroAuthorizationInfo info = (ShiroAuthorizationInfo) getAuthorizationInfo(principals);
		RolePermission targetPerm = (RolePermission) permission;
		RolePermission perm = info.getPermission(targetPerm.getBaseUrl());
		if (perm != null) {
			targetPerm.setDataScope(perm.getDataScope());
			targetPerm.setPermissionId(perm.getPermissionId());
			return true;
		}
		return false;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String username = upToken.getUsername();
		checkNotNull(username, "Null username are not allowed by this realm.");
		SimpleUser user = userService.findUserByName(username);
		checkNotNull(user, "No account found for user [" + username + "]");
		SessionUtil.setCurUser(user);
		user.setUsername(username);
		return new SimpleAuthenticationInfo(username, user.getPassword()
				.toLowerCase().toCharArray(), getName());
	}

	@Override
	protected Object getAuthorizationCacheKey(PrincipalCollection principals) {
		return SecurityUtils.getSubject().getSession().getId();
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		checkNotNull(principals,
				"PrincipalCollection method argument cannot be null.");
		SimpleUser user = SessionUtil.getCurUser();
		ShiroAuthorizationInfo info = new ShiroAuthorizationInfo();
		info.addPermission(mainPerm); // main page
		info.addPermission(rootPathPerm); // root path "/"
		List<Integer> roleIds = userService.findRoleIds(user.getId());
		if (ListUtil.isBlank(roleIds)) {
			return info;
		}
		// a set for left navigation menus
		List<Permission> menus = new ArrayList<Permission>();
		List<RolePermission> perms = null;
		Permission temp = null;
		HashSet<Integer> tempSet = new HashSet<Integer>();
		for (Integer roleId : roleIds) {
			perms = bizDataService.getRolePermissions(roleId,
					user.getPlatform());
			if (ListUtil.isBlank(perms)) {
				continue;
			}
			for (RolePermission perm : perms) {
				info.addPermission(perm);
				if (!tempSet.add(perm.getPermissionId())) {// 去重
					continue;
				}
				temp = bizDataService.getPermission(perm.getPermissionId());
				if (temp != null && temp.isMenu()) {
					menus.add(temp);
				}
			}
		}
		Collections.sort(menus, orderComparator);
		// set to the session
		SecurityUtils.getSubject().getSession()
				.setAttribute(WebConstants.KEY_LEFT_NAV, menus);
		return info;
	}

	private void checkNotNull(Object reference, String message) {
		if (reference == null) {
			throw new AuthenticationException(message);
		}
	}

	private Comparator<Permission> orderComparator = new Comparator<Permission>() {
		@Override
		public int compare(Permission o1, Permission o2) {
			return o1.getMenuDisplayOrder() - o2.getMenuDisplayOrder();
		}
	};

	@Autowired
	public void setBizDataService(BizDataService bizDataService) {
		this.bizDataService = bizDataService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
