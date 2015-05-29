package com.xuanwu.web.common.entity;

import java.io.Serializable;
import java.util.Date;

import com.xuanwu.web.common.service.UserService;

/**
 * @Description 简单的用户实体，登录时使用
 * @author <a href="mailto:liushuaiying@139130.net">Shuaiying.Liu</a>
 * @Data 2012-9-5
 * @Version 1.0.0
 */
public class SimpleUser extends BaseEntity implements Serializable {
	private static final long serialVersionUID = -8763208974916391078L;

	private int id;
	private String username;
	private String password;
	private int parentId;
	private int enterpriseId;
	private int adminId;
	private int platformId;
	private String domain;
	private Platform platform;
	private Date lastLoginTime;
	private String linkMan;

	private UserService userService;
	private boolean loadExt = false;

	private String groupName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public int getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(int enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public int getAdminId() {
		loadExtInfo();
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public boolean isAdmin() {
		loadExtInfo();
		return id == adminId;
	}

	public String getDomain() {
		loadExtInfo();
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	// lazy load, load once
	private void loadExtInfo() {
		if (loadExt)
			return;
		synchronized (this) {
			if (loadExt)
				return;
			User ent = userService.findUserById(getEnterpriseId());
			User admin = userService.findAdminUser(getEnterpriseId());
			setDomain(ent.getDomain());
			setAdminId(admin.getId());
			loadExt = true;
		}
	}

	@Override
	public String toJSON() {
		StringBuilder sb = new StringBuilder();
		sb.append("{\"id\":").append(id);
		sb.append(",\"name\":\"").append(username).append("\"");
		sb.append('}');
		return sb.toString();
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
