/*
 * Copyright (c) 2012 by XUANWU INFORMATION TECHNOLOGY CO. 
 *             All rights reserved                         
 */
package com.xuanwu.web.common.security.filter;

import java.util.Date;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import com.xuanwu.web.common.entity.Permission;
import com.xuanwu.web.common.entity.RolePermission;
import com.xuanwu.web.common.entity.SimpleUser;
import com.xuanwu.web.common.entity.SystemLog;
import com.xuanwu.web.common.service.BizDataService;
import com.xuanwu.web.common.service.SysLogService;
import com.xuanwu.web.common.utils.SessionUtil;
import com.xuanwu.web.common.utils.WebConstants;

/**
 * @Description 权限过滤器，对所有的URL进行过滤，对于没有授权的访问，将拒绝
 * @author <a href="mailto:liushuaiying@139130.net">Shuaiying.Liu</a>
 * @Data 2012-9-5
 * @Version 1.0.0
 */
public class ShiroUrlBasedFilter extends AuthorizationFilter {
	
	private BizDataService bizDataService;
	private SysLogService sysLogService;
	
	@Override
	protected boolean isAccessAllowed(ServletRequest req,
			ServletResponse resp, Object mappedValue) throws Exception {
		HttpServletRequest hreq = (HttpServletRequest)req;
		HttpServletResponse hresp = (HttpServletResponse)resp;
		Subject subject = SecurityUtils.getSubject();
		boolean isPermitted = false;
		if(subject.isAuthenticated()){
			RolePermission perm = new RolePermission();
			String url = hreq.getServletPath();
			if(StringUtils.isBlank(url)){
				url = hreq.getPathInfo();//fixed for IBM WebSphere 
			}
			perm.setBaseUrl(url);
			isPermitted = subject.isPermitted(perm);
			if(perm.getDataScope() != null){
				hreq.setAttribute(WebConstants.KEY_DATA_SCOPE, perm.getDataScope());
				hreq.setAttribute(WebConstants.KEY_PERM_ID, perm.getPermissionId());
			}
			perm = null;
			if(!isPermitted){
				hresp.setHeader(WebConstants.HEADER_ACCESS_STATE, "unauthorized");
			}
		} else {
			hresp.setHeader(WebConstants.HEADER_ACCESS_STATE, "login");
		}
		return isPermitted;
	}
	
	@Override
	protected void postHandle(ServletRequest req, ServletResponse resp)
			throws Exception {
		HttpServletRequest hreq = (HttpServletRequest)req;
		recordLog(hreq);
	}
	
	private void recordLog(HttpServletRequest hreq){
		String opObj = (String)hreq.getAttribute(WebConstants.KEY_OP_OBJ);
		Integer permId = (Integer)hreq.getAttribute(WebConstants.KEY_PERM_ID);
		if(opObj == null || permId == null)
			return;
		Permission perm = bizDataService.getPermission(permId);
		String opType = (String)hreq.getAttribute(WebConstants.KEY_OP_TYPE);
		SimpleUser user = SessionUtil.getCurUser();
		if(user == null)
			return;
		SystemLog sysLog = new SystemLog();
		sysLog.setUserId(user.getId());
		sysLog.setUserName(user.getUsername());
		sysLog.setOperateTime(new Date());
		sysLog.setAreaName(perm.getAreaName());
		sysLog.setControllerName(perm.getControllerName());
		sysLog.setActionName(perm.getActionName());
		sysLog.setOperationType(opType == null ? perm.getDisplayName() : opType);
		sysLog.setOperationObj(perm.getOperationObj());
		sysLog.setContent(opObj);
		sysLog.setFormMethod(1);
		sysLog.setEnterpriseId(user.getEnterpriseId());
		sysLogService.addSysLog(sysLog);
	}
	
	public void setBizDataService(BizDataService bizDataService) {
		this.bizDataService = bizDataService;
	}
	
	public void setSysLogService(SysLogService sysLogService) {
		this.sysLogService = sysLogService;
	}
}
