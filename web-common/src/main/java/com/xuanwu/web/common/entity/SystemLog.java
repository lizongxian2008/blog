package com.xuanwu.web.common.entity;

import java.util.Date;

import com.xuanwu.web.common.utils.DateUtil;
import com.xuanwu.web.common.utils.StringUtil;
import com.xuanwu.web.common.utils.DateUtil.DateTimeType;

public class SystemLog extends BaseEntity {
	private int id;

	/** 用户ID **/
	private int userId;

	/** 用户名 **/
	private String userName;

	/** 操作时间 **/
	private Date operateTime;

	/** MVC区域名称 **/
	private String areaName;

	/** MVC控制器名称 **/
	private String controllerName;

	/** MVC动作名称 **/
	private String actionName;

	/** 操作对象 **/
	private String operationObj;

	/** 操作内容 **/
	private String content;

	/** 操作类型：新增（NEW），修改（MODIFY）,删除（DELETE）, **/
	private String operationType;

	/** 提交方式：GET:0， POST：1，目前全设置为1 */
	private int formMethod;

	/** 企业ID **/
	private int enterpriseId;

	/** 备注 **/
	private String remark;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
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

	public String getOperationObj() {
		return operationObj;
	}

	public void setOperationObj(String operationObj) {
		this.operationObj = operationObj;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getFormMethod() {
		return formMethod;
	}

	public void setFormMethod(int formMethod) {
		this.formMethod = formMethod;
	}

	public int getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(int enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	@Override
	public String toJSON() {
		StringBuilder sb = new StringBuilder();
		sb.append("{\"id\":")
				.append("\"" + id + "\"")
				.append(",\"userName\":")
				.append("\"" + userName + "\"")
				.append(",\"operationObj\":")
				.append("\"" + operationObj + "\"")
				.append(",\"operationType\":")
				.append("\"" + operationType + "\"")
				.append(",\"content\":")
				.append("\"" + StringUtil.replaceHtml(StringUtil.fixJsonStr(content)) + "\"")
				.append(",\"operateTime\":")
				.append("\""
						+ DateUtil.format(operateTime, DateTimeType.DateTime)
						+ "\"").append("}");
		return sb.toString();

	}

}
