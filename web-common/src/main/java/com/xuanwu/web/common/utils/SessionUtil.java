package com.xuanwu.web.common.utils;

import org.apache.shiro.SecurityUtils;

import com.xuanwu.web.common.entity.DataScope;
import com.xuanwu.web.common.entity.RolePermission;
import com.xuanwu.web.common.entity.SimpleUser;

/**
 * @Description 会话相关的工具类
 * @author <a href="mailto:liushuaiying@139130.net">Shuaiying.Liu</a>
 * @Data 2012-9-11
 * @Version 1.0.0
 */
public class SessionUtil {

	public static void setCurUser(SimpleUser user) {
		SecurityUtils.getSubject().getSession()
				.setAttribute(WebConstants.KEY_USER, user);
	}

	public static SimpleUser getCurUser() {
		return (SimpleUser) SecurityUtils.getSubject().getSession(true)
				.getAttribute(WebConstants.KEY_USER);
	}

	/**
	 * 是否有权限
	 * 
	 * @param url
	 * @return
	 */
	public static boolean hasPermission(String url) {
		return SecurityUtils.getSubject().isPermitted(url);
	}

	public static DataScope getDataScope(String url) {
		RolePermission perm = new RolePermission();
		perm.setBaseUrl(url);
		SecurityUtils.getSubject().isPermitted(perm);
		return perm.getDataScope();
	}
}
