package com.xuanwu.web.common.entity;

import java.util.Date;
import java.util.List;

import com.xuanwu.web.common.utils.DateUtil;
import com.xuanwu.web.common.utils.DateUtil.DateTimeType;
import com.xuanwu.web.common.utils.PermissionUtil;
import com.xuanwu.web.common.utils.StringUtil;

/**
 * @Description: 角色实体
 * @Author <a href="jiji@javawind.com">Xuefang.Xu</a>
 * @Date 2012-9-6
 * @Version 1.0.0
 */
public class Role extends BaseEntity {

	private int id;// 角色ID
	private String name;// 角色名称
	private int userId;// 用户ID
	private String userName;// 用户名
	private String remark;// 备注
	private Date lastModifyDate;// 最后修改时间
	private int lastModifyUserId;// 最后修改的用户ID
	private String lastModifyUserName;// 最后修改的用户名
	private int roleType;// 0:默认角色,1:普通角色,9:超级管理员
	private int enterpriseId;// 企业ID
	private int baseType; //基础角色类型：当企业ID为0时不用，1--后台基础角色，2--前台企业基础角色, 3--前台代理商基础角色
	private List<Permission> permissions;// 权限列表
	private List<PermissionDepends> permissionDepends;// 权限依赖
	private List<RolePermission> rolePermissions;// 权限列表
	
	public enum RoleType {
		/** 默认角色 */
		DEFAULT(0), 
		/** 普通角色 */
		NORMAL(1), 
		/** 超级管理员 */
		ADMIN(9);
		private final int index;
		private RoleType(int index){
			this.index = index;
		}
		public int getIndex() {
			return index;
		}
		public static RoleType getType(int index){
			switch(index){
			case 0: return DEFAULT;
			case 1: return NORMAL;
			case 2: return ADMIN;
			default:
				throw new IllegalArgumentException("Unsupport role type: " + index);
			}
		}
	}
	
	/**
	 * 基础角色类型
	 */
	public enum BaseType {
		/** 后台企业基础角色 */
		BACKEND_ENT(0), 
		/** 前台企业基础角色 */
		FRONTKIT_ENT(1),
		/** 前台代理商基础角色 */
		FRONTKIT_AGENT(2);
		private final int index;
		private BaseType(int index) {
			this.index = index;
		}
		public static BaseType getType(int index){
			switch(index){
			case 1: return BACKEND_ENT;
			case 2: return FRONTKIT_ENT;
			case 3: return FRONTKIT_AGENT;
			default:
				throw new IllegalArgumentException("Unsupport base role type: " + index);
			}
		}
		public int getIndex() {
			return index;
		}
	}

	/** 是否绑定 */
	private boolean bound;

	private boolean showPermissions = false;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getLastModifyDate() {
		return lastModifyDate;
	}

	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}

	public int getLastModifyUserId() {
		return lastModifyUserId;
	}

	public void setLastModifyUserId(int lastModifyUserId) {
		this.lastModifyUserId = lastModifyUserId;
	}

	public String getLastModifyUserName() {
		return lastModifyUserName;
	}

	public void setLastModifyUserName(String lastModifyUserName) {
		this.lastModifyUserName = lastModifyUserName;
	}

	public int getRoleType() {
		return roleType;
	}

	public void setRoleType(int roleType) {
		this.roleType = roleType;
	}

	public int getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(int enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	
	public int getBaseType() {
		return baseType;
	}
	
	public void setBaseType(int baseType) {
		this.baseType = baseType;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	public List<PermissionDepends> getPermissionDepends() {
		return permissionDepends;
	}

	public void setPermissionDepends(List<PermissionDepends> permissionDepends) {
		this.permissionDepends = permissionDepends;
	}

	public List<RolePermission> getRolePermissions() {
		return rolePermissions;
	}

	public void setRolePermissions(List<RolePermission> rolePermissions) {
		this.rolePermissions = rolePermissions;
	}

	public boolean getBound() {
		return bound;
	}

	public void setBound(boolean bound) {
		this.bound = bound;
	}

	public boolean isShowPermissions() {
		return showPermissions;
	}

	public void setShowPermissions(boolean showPermissions) {
		this.showPermissions = showPermissions;
	}

	@Override
	public String toJSON() {
		StringBuffer sb = new StringBuffer();
		sb.append("{\"id\":" + id);
		sb.append(",\"name\":\"" + name + "\"");
		sb.append(",\"userId\":" + userId);
		sb.append(",\"userName\":\"" + userName + "\"");
		sb.append(",\"remark\":\"" + StringUtil.fixJsonStr(remark) + "\"");
		sb.append(",\"lastModifyDate\":\""
				+ DateUtil.format(lastModifyDate, DateTimeType.DateTime) + "\"");
		sb.append(",\"lastModifyUserId\":" + lastModifyUserId);
		sb.append(",\"lastModifyUserName\":\"" + lastModifyUserName + "\"");
		sb.append(",\"roleType\":" + roleType);
		sb.append(",\"enterpriseId\":" + enterpriseId);

		if (showPermissions) {
			if (permissions != null && permissions.size() > 0) {
				List<Permission> list = PermissionUtil
						.sortPermissions(permissions);
				sb.append(",\"permissions\":[");
				int i = 0;
				for (Permission p : list) {
					sb.append((i++) > 0 ? "," : "");
					sb.append(p.toJSON());
				}
				sb.append("]");
			} else {
				sb.append(",\"permissions\":null");
			}

			if (permissionDepends != null && permissionDepends.size() > 0) {
				sb.append(",\"permissionDepends\":[");
				int i = 0;
				for (PermissionDepends p : permissionDepends) {
					sb.append((i++) > 0 ? "," : "");
					sb.append(p.toJSON());
				}
				sb.append("]");
			} else {
				sb.append(",\"permissionDepends\":null");
			}
		}
		String permissionsStr = PermissionUtil.getPermissionsStr(permissions);
		sb.append(",\"perms\":\"" + StringUtil.fixJsonStr(permissionsStr)
				+ "\"");
		sb.append("}");

		return sb.toString();
	}

}
