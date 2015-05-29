package com.xuanwu.web.common.entity;

import java.util.Date;
import java.util.List;

import com.xuanwu.web.common.utils.StringUtil;

/**
 * @Description 用户实体抽象类
 * @author <a href="mailto:liushuaiying@139130.net">Shuaiying.Liu</a>
 * @Data 2012-9-7
 * @Version 1.0.0
 */
public abstract class GsmsUser extends BaseEntity {

	public enum State {
		/** 正常 */
		Normal(0),
		/** 暂停 */
		Suspend(1),
		/** 终止（已删除） */
		Terminate(2),
		/** 未生效 */
		NotEffect(3);

		private int index;

		private State(int index) {
			this.index = index;
		}

		public int getIndex() {
			return index;
		}

		public static State getType(int index) {
			switch (index) {
			case 0:
				return Normal;
			case 1:
				return Suspend;
			case 2:
				return Terminate;
			case 3:
				return NotEffect;
			default:
				return Terminate;
			}
		}
	}

	public enum UserType {
		Enterprise(0), Department(1), User(2), Agent(3);

		private int index;

		private UserType(int index) {
			this.index = index;
		}

		public int getIndex() {
			return index;
		}

		public static UserType getType(int index) {
			switch (index) {
			case 0:
				return Enterprise;
			case 1:
				return Department;
			case 2:
				return User;
			case 3:
				return Agent;
			default:
				throw new IllegalArgumentException("Unsupport user type: "
						+ index);
			}
		}
	}

	protected int id;
	/** 企业扣费类型: 0--按用户提交量计费、1--按系统接收量计费、2--按信息送达量计费 */
	protected int billingType;

	/** 账号状态：NORMAL(0), SUSPEND(1), TERMINATE(2); 0,1可以互转，从0或1转到2后不能再转 */
	protected int state;

	/** 父ID */
	protected int parentId;

	/** 代理商账号 **/
	protected String parentName;

	/** 代理商名称 **/
	protected String parentEntName;

	/** 父唯一标识符 */
	protected String parentIdentify;

	/** 企业标识号或用户标识码或部门编号 */
	protected String identify;

	/** 用户类型：0-企业，1-部门，2-用户 */
	// protected int type;

	/** 用户签名 */
	protected String signature;

	/** 0:后置 1:前置 */
	protected int sigLocation;

	/** 电话号码 */
	protected String telephone;

	/** 联系电话 */
	protected String phone;

	/** 联系地址 */
	protected String address;

	/** 联系EMAIL */
	protected String email;

	/** 创建时间 */
	protected Date createTime;

	/** 0:Backend; 1:UMP; 2:FrontKit; */
	protected int platformId;

	/** 联系人 */
	protected String linkMan;

	/** 路径，查询时以避免递归 */
	protected String path;

	/** 备注 */
	protected String remark;

	/** 企业ID */
	protected int enterpriseId;

	protected String enterpriseName;

	/** 账号 */
	protected String domain;

	/** 角色名组合 */
	protected String roleNames;

	/** 业务类型名组合 */
	protected String bizNames;

	/** 分配的角色列表 */
	protected List<Role> roles;

	/** 所属销售id */
	protected int salemanId;

	/** 发送优先级 */
	protected int priority;

	protected String channelNum;
	protected String cmdToken;
	protected int wechatLimitNumber;

	protected String smsAccountName;
	protected String smsAccountPassword;
	protected String smsAccountIp;
	protected int smsAccountPort;
	protected String smsMoIp;
	protected int smsMoPort;
	/** 企业注册会员赠送积分  */
	protected Double creditReg;

	public int getSalemanId() {
		return salemanId;
	}

	public void setSalemanId(int salemanId) {
		this.salemanId = salemanId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getPlatformId() {
		return platformId;
	}

	public void setPlatformId(int platformId) {
		this.platformId = platformId;
	}

	public String getIdentify() {
		return identify;
	}

	public void setIdentify(String identify) {
		this.identify = identify;
	}

	public String getParentIdentify() {
		return parentIdentify;
	}

	public void setParentIdentify(String parentIdentify) {
		this.parentIdentify = parentIdentify;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public int getSigLocation() {
		return sigLocation;
	}

	public void setSigLocation(int sigLocation) {
		this.sigLocation = sigLocation;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getRemark() {
		return remark;
	}

	public String getRemarkReplaceHtml() {
		return StringUtil.replaceHtml(remark);
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getBizNames() {
		return bizNames;
	}

	public void setBizNames(String bizNames) {
		this.bizNames = bizNames;
	}

	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public void setType(int type) {
		// do nothing
	}

	public int getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(int enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getParentEntName() {
		return parentEntName;
	}

	public void setParentEntName(String parentEntName) {
		this.parentEntName = parentEntName;
	}

	public int getpriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getBillingType() {
		return billingType;
	}

	public void setBillingType(int billingType) {
		this.billingType = billingType;
	}

	public String getChannelNum() {
		return channelNum;
	}

	public void setChannelNum(String channelNum) {
		this.channelNum = channelNum;
	}

	public String getCmdToken() {
		return cmdToken;
	}

	public void setCmdToken(String cmdToken) {
		this.cmdToken = cmdToken;
	}

	public Double getCreditReg() {
		return this.creditReg;
	}

	public void setCreditReg(Double creditReg) {
		this.creditReg = creditReg;
	}

	/**
	 * 完整路径，包含当前ID
	 *
	 * @return
	 */
	public abstract String getFullPath();

	public abstract UserType getType();

	public abstract boolean hasChild();

	public abstract void setHasChild(boolean hasChild);

	public int getWechatLimitNumber() {
		return wechatLimitNumber;
	}

	public void setWechatLimitNumber(int wechatLimitNumber) {
		this.wechatLimitNumber = wechatLimitNumber;
	}

	public String getSmsAccountName() {
		return smsAccountName;
	}

	public void setSmsAccountName(String smsAccountName) {
		this.smsAccountName = smsAccountName;
	}

	public String getSmsAccountPassword() {
		return smsAccountPassword;
	}

	public void setSmsAccountPassword(String smsAccountPassword) {
		this.smsAccountPassword = smsAccountPassword;
	}

	public String getSmsAccountIp() {
		return smsAccountIp;
	}

	public void setSmsAccountIp(String smsAccountIp) {
		this.smsAccountIp = smsAccountIp;
	}

	public int getSmsAccountPort() {
		return smsAccountPort;
	}

	public void setSmsAccountPort(int smsAccountPort) {
		this.smsAccountPort = smsAccountPort;
	}

	public String getSmsMoIp() {
		return this.smsMoIp;
	}

	public void setSmsMoIp(String smsMoIp) {
		this.smsMoIp = smsMoIp;
	}

	public int getSmsMoPort() {
		return this.smsMoPort;
	}

	public void setSmsMoPort(int smsMoPort) {
		this.smsMoPort = smsMoPort;
	}
	
	

}
