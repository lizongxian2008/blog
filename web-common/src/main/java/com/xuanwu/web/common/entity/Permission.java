package com.xuanwu.web.common.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 权限实体，对应于数据库中的权限记录
 * @author <a href="mailto:liushuaiying@139130.net">Shuaiying.Liu</a>
 * @Data 2012-9-5
 * @Version 1.0.0
 */
public class Permission extends BaseEntity implements Serializable {
	private static final long serialVersionUID = -1529450337715510535L;

	private int id;
	private String displayName;
	private String operationObj;
	private String areaName;
	private String controllerName;
	private String actionName;
	private int parentId;
	private boolean isMenu;
	private String menuImagePath;
	private String menuDisplayName;
	private int menuDisplayOrder;
	private boolean menuHasHyperlink;
	private boolean isDisplay;
	private int level;

	private String url;
	
	private String permUrl;

	/** supported data scopes */
	private String dataScopes;
	private int platformId;
	private Platform platform;
	private Permission parent;// 父权限
	private List<Permission> children;// 子权限列表
	private boolean checked;// 角色当中是否选中
	private int dataScopeId;// 角色当中所选权限范围

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getControllerName() {
		return controllerName;
	}

	public void setControllerName(String controllerName) {
		this.controllerName = controllerName;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public boolean isMenu() {
		return isMenu;
	}

	public void setMenu(boolean isMenu) {
		this.isMenu = isMenu;
	}

	public String getMenuImagePath() {
		return menuImagePath;
	}

	public void setMenuImagePath(String menuImagePath) {
		this.menuImagePath = menuImagePath;
	}

	public String getMenuDisplayName() {
		return menuDisplayName;
	}

	public void setMenuDisplayName(String menuDisplayName) {
		this.menuDisplayName = menuDisplayName;
	}

	public int getMenuDisplayOrder() {
		return menuDisplayOrder;
	}

	public void setMenuDisplayOrder(int menuDisplayOrder) {
		this.menuDisplayOrder = menuDisplayOrder;
	}

	public boolean isMenuHasHyperlink() {
		return menuHasHyperlink;
	}

	public void setMenuHasHyperlink(boolean menuHasHyperlink) {
		this.menuHasHyperlink = menuHasHyperlink;
	}

	public boolean isDisplay() {
		return isDisplay;
	}

	public void setDisplay(boolean isDisplay) {
		this.isDisplay = isDisplay;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getDataScopes() {
		return dataScopes;
	}

	public void setDataScopes(String dataScopes) {
		this.dataScopes = dataScopes;
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

	public List<Permission> getChildren() {
		return children;
	}

	public void setChildren(List<Permission> children) {
		this.children = children;
	}

	public void addToChildren(Permission permission) {
		if (this.children == null) {
			this.children = new ArrayList<Permission>();
		}
		this.children.add(permission);
	}

	public Permission getParent() {
		return parent;
	}

	public void setParent(Permission parent) {
		this.parent = parent;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public int getDataScopeId() {
		return dataScopeId;
	}

	public void setDataScopeId(int dataScopeId) {
		this.dataScopeId = dataScopeId;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOperationObj() {
		return operationObj;
	}

	public void setOperationObj(String operationObj) {
		this.operationObj = operationObj;
	}

	public String getUrl() {
		return url;
	}
	
	public String getPermUrl() {
		if (permUrl == null) {
			StringBuilder sb = new StringBuilder();
			sb.append("/");
			sb.append(areaName);
			sb.append("/");
			sb.append(controllerName);
			sb.append("/");
			sb.append(actionName);
			permUrl = sb.toString();
		}
		return permUrl;
	}

	@Override
	public String toString() {
		return "Permission [id=" + id + ", displayName=" + displayName
				+ ", areaName=" + areaName + ", controllerName="
				+ controllerName + ", actionName=" + actionName + ", parentID="
				+ parentId + ", isMenu=" + isMenu + ", menuImagePath="
				+ menuImagePath + ", menuDisplayName=" + menuDisplayName
				+ ", menuDisplayOrder=" + menuDisplayOrder
				+ ", menuHasHyperlink=" + menuHasHyperlink + ", isDisplay="
				+ isDisplay + ", level=" + level + ", url=" + url
				+ ", dataScope=" + dataScopes + ", platform=" + platform
				+ ", parent=" + parent + ", children=" + children
				+ ", checked=" + checked + "]";
	}

	@Override
	public String toJSON() {
		StringBuilder sb = new StringBuilder();
		sb.append("{\"id\":" + id);
		sb.append(",\"displayName\":\"" + displayName + "\"");
		sb.append(",\"areaName\":\"" + areaName + "\"");
		sb.append(",\"controllerName\":\"" + controllerName + "\"");
		sb.append(",\"actionName\":\"" + actionName + "\"");
		sb.append(",\"parentId\":" + parentId);
		sb.append(",\"isMenu\":" + isMenu);
		sb.append(",\"menuImagePath\":\"" + menuImagePath + "\"");
		sb.append(",\"menuDisplayName\":\"" + menuDisplayName + "\"");
		sb.append(",\"menuDisplayOrder\":" + menuDisplayOrder);
		sb.append(",\"menuHasHyperlink\":\"" + menuHasHyperlink + "\"");
		sb.append(",\"isDisplay\":" + isDisplay);
		sb.append(",\"level\":" + level);
		sb.append(",\"url\":\"" + url + "\"");
		sb.append(",\"permUrl\":\"" + permUrl + "\"");
		sb.append(",\"dataScopes\":\"" + dataScopes + "\"");
		sb.append(",\"platformId\":" + platform.getIndex());
		sb.append(",\"platformName\":\"" + platform.name() + "\"");
		if (children != null && children.size() > 0) {
			sb.append(",\"children\":[");
			int i = 0;
			for (Permission c : children) {
				sb.append((i++) > 0 ? "," : "");
				sb.append(c.toJSON());
			}
			sb.append("]");
		} else {
			sb.append(",\"children\":null");
		}
		sb.append(",\"checked\":" + checked);
		sb.append(",\"dataScopeId\":" + dataScopeId);
		sb.append("}");

		return sb.toString();
	}
}
