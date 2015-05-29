package com.xuanwu.web.common.entity;

import java.util.List;

import com.xuanwu.web.common.utils.Delimiters;

/**
 * @Description 部门
 * @author <a href="mailto:liushuaiying@139130.net">Shuaiying.Liu</a>
 * @Data 2012-9-6
 * @Version 1.0.0
 */
public class Department extends User {
	
	/** 部门名称 */
	private String deptName;
	
	private String deptNoPrefix;
	
	private boolean hasChild;
	
	private boolean hasUser;
	
	private List<Integer> delRoles;
	
	private List<Integer> delBizs;

	public String getDeptName() {
		return deptName;
	}
	
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	public String getDeptNoPrefix() {
		return deptNoPrefix;
	}
	
	public void setDeptNoPrefix(String deptNoPrefix) {
		this.deptNoPrefix = deptNoPrefix;
	}
	
	@Override
	public String getFullPath() {
		return this.path + this.id + Delimiters.DOT;
	}
	
	@Override
	public UserType getType() {
		return UserType.Department;
	}
	
	@Override
	public boolean hasChild() {
		return hasChild;
	}
	
	@Override
	public void setHasChild(boolean hasChild) {
		this.hasChild = hasChild;
	}
	
	public boolean hasUser() {
		return hasUser;
	}
	
	public boolean isHasUser() {
		return hasUser;
	}

	public void setHasUser(boolean hasUser) {
		this.hasUser = hasUser;
	}

	public boolean isHasChild() {
		return hasChild;
	}

	public List<Integer> getDelRoles() {
		return delRoles;
	}

	public void setDelRoles(List<Integer> delRoles) {
		this.delRoles = delRoles;
	}

	public List<Integer> getDelBizs() {
		return delBizs;
	}

	public void setDelBizs(List<Integer> delBizs) {
		this.delBizs = delBizs;
	}
	
	@Override
	public String toJSON() {
		StringBuilder sb = new StringBuilder();
		sb.append("{\"id\":").append(id);
		sb.append(",\"pId\":").append(parentId);
		sb.append(",\"type\":").append(UserType.Department.getIndex());
		sb.append(",\"name\":\"").append(deptName).append('\"');
		sb.append(",\"path\":\"").append(path).append('\"');
		sb.append(",\"identify\":\"").append(identify).append('\"');
		sb.append(",\"bizNames\":\"").append(bizNames).append('\"');
		sb.append(",\"roleNames\":\"").append(roleNames).append('\"');
		sb.append('}');
		return sb.toString();
	}
}
