package com.xuanwu.web.common.entity;

import org.apache.shiro.authz.Permission;

/**
 * @Description 实际分配出去的权限，包含了数据范围
 * @author <a href="mailto:liushuaiying@139130.net">Shuaiying.Liu</a>
 * @Data 2012-9-5
 * @Version 1.0.0
 */
public class RolePermission implements org.apache.shiro.authz.Permission {

	/** permission id */
	private Integer permissionId;

	/** base URL: '/' + area_name + '/' + controller_name + '/' + action_name */
	private String baseUrl;

	/** role id */
	private Integer roleId;

	/** data scope */
	private Integer dataScopeId;
	private DataScope dataScope;

	/** platform */
	private int platformId;// 平台Id:(0:Backend; 1:UMP; 2:FrontKit;)
	private Platform platform;

	public RolePermission() {
		// TODO Auto-generated constructor stub
	}

	public RolePermission(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public Integer getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Integer permissionId) {
		this.permissionId = permissionId;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getDataScopeId() {
		return dataScopeId;
	}

	public void setDataScopeId(Integer dataScopeId) {
		this.dataScope = DataScope.getType(dataScopeId);
		this.dataScopeId = dataScopeId;
	}

	public DataScope getDataScope() {
		return dataScope;
	}

	public void setDataScope(DataScope dataScope) {
		this.dataScope = dataScope;
	}

	public int getPlatformId() {
		return platformId;
	}

	public void setPlatformId(int platformId) {
		this.platform = Platform.getType(platformId);
		this.platformId = platformId;
	}

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((baseUrl == null) ? 0 : baseUrl.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		RolePermission other = (RolePermission) obj;
		if (baseUrl == null) {
			if (other.baseUrl != null) {
				return false;
			}
		} else if (!baseUrl.equals(other.baseUrl)) {
			return false;
		}
		return true;
	}

	@Override
	public boolean implies(Permission p) {
		return false;
	}

}
