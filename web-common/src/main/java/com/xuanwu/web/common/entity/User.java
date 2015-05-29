package com.xuanwu.web.common.entity;

import java.util.Date;

import com.xuanwu.web.common.utils.DateUtil;
import com.xuanwu.web.common.utils.DateUtil.DateTimeType;
import com.xuanwu.web.common.utils.StringUtil;

/**
 * @Description 用户 
 * @author <a href="mailto:liushuaiying@139130.net">Shuaiying.Liu</a>
 * @Data 2012-9-6
 * @Version 1.0.0
 */
public class User extends GsmsUser {

	private String userName;
	
	private String password;
	
	private boolean hasSendMessage;
	
	private String birthdayStr;
	
	private Date birthday;
	
	private int sex;
	
	private Integer isFirstTimeLogin;
	
	private Date lastLoginTime;
	
	private boolean needBizType;
	
	private int capitalAccountId;

	private String entSignature;//企业签名
	private int entSigLocation;//企业签名位置
	private String entDomain;//企业帐号
	private int entParentId;
	private String salemanName;
	private int agentId;
	private String agentName;
	private String agentEntName;
	private int entState;//企业状态
	
	public String getSignatureEncode() {
		return StringUtil.replaceHtml(getSignature());
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isHasSendMessage() {
		return hasSendMessage;
	}
	
	public void setHasSendMessage(boolean hasSendMessage) {
		this.hasSendMessage = hasSendMessage;
	}
	
	@Override
	public String getFullPath() {
		return this.path;
	}
	
	@Override
	public UserType getType() {
		return UserType.User;
	}
	
	@Override
	public boolean hasChild() {
		return false;
	}
	
	@Override
	public void setHasChild(boolean hasChild) {
		
	}
	
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	public String getBirthdayStr() {
		return birthdayStr;
	}
	
	public void setBirthdayStr(String birthdayStr) {
		this.birthdayStr = birthdayStr;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}
	
	public Integer getIsFirstTimeLogin() {
		return isFirstTimeLogin;
	}

	public void setIsFirstTimeLogin(Integer isFirstTimeLogin) {
		this.isFirstTimeLogin = isFirstTimeLogin;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	
	public boolean isNeedBizType() {
		return needBizType;
	}
	
	public void setNeedBizType(boolean needBizType) {
		this.needBizType = needBizType;
	}
	
	public int getCapitalAccountId() {
		return capitalAccountId;
	}
	
	public void setCapitalAccountId(int capitalAccountId) {
		this.capitalAccountId = capitalAccountId;
	}
	
	public String getEntSignature() {
		return entSignature;
	}

	public void setEntSignature(String entSignature) {
		this.entSignature = entSignature;
	}

	public int getEntSigLocation() {
		return entSigLocation;
	}

	public void setEntSigLocation(int entSigLocation) {
		this.entSigLocation = entSigLocation;
	}
	
	public String getEntDomain() {
		return entDomain;
	}

	public void setEntDomain(String entDomain) {
		this.entDomain = entDomain;
	}

	public int getEntParentId() {
		return entParentId;
	}

	public void setEntParentId(int entParentId) {
		this.entParentId = entParentId;
	}

	public String getSalemanName() {
		return salemanName;
	}

	public void setSalemanName(String salemanName) {
		this.salemanName = salemanName;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	
	public int getAgentId() {
		return agentId;
	}

	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}
	
	public int getEntState() {
		return entState;
	}

	public void setEntState(int entState) {
		this.entState = entState;
	}

	public String getAgentEntName() {
		return agentEntName;
	}

	public void setAgentEntName(String agentEntName) {
		this.agentEntName = agentEntName;
	}

	public String getStateStr(){
		StringBuffer sb = new StringBuffer();
		
		State _state = GsmsUser.State.getType(state);
		switch (_state) {
		case Normal:
			sb.append("启用");
			break;
		case Suspend:
			sb.append("停用");
			break;
		default:
			break;
		}
		
		return sb.toString();
	}
	
	public String getUserEntStateStr(){
		StringBuffer sb = new StringBuffer();
		
		State _entState = GsmsUser.State.getType(entState);
		switch (_entState) {
		case Normal:
			sb.append("启用");
			break;
		case Suspend:
			sb.append("停用");
			break;
		default:
			break;
		}
		
		State _state = GsmsUser.State.getType(state);
		switch (_state) {
		case Normal:
			sb.append("/").append("启用");
			break;
		case Suspend:
			sb.append("/").append("停用");
			break;
		default:
			break;
		}
		
		return sb.toString();
	}
	

	public String toJSONSimple(){
		StringBuilder sb = new StringBuilder();
		sb.append("{\"id\":").append(id);
		sb.append(",\"name\":\"").append(userName).append("\"");
		sb.append(",\"state\":").append(state);
		sb.append('}');
		return sb.toString();
	}
	
	public String toJSONLinkMan(){
		StringBuilder sb = new StringBuilder();
		sb.append("{\"id\":").append(id);
		sb.append(",\"name\":\"").append(linkMan).append("\"");
		sb.append(",\"state\":").append(state);
		sb.append('}');
		return sb.toString();
	}

	@Override
	public String toJSON() {
		StringBuilder sb = new StringBuilder();
		sb.append("{\"userId\":").append(id);
		sb.append(",\"userName\":\"").append(userName).append("\"");
		sb.append(",\"linkMan\":\"").append(linkMan).append("\"");
		sb.append(",\"phone\":\"").append(phone).append("\"");
		sb.append(",\"sex\":").append(sex);
		sb.append(",\"deptName\":\"").append(enterpriseName).append("\"");
		sb.append(",\"roleNames\":\"").append(roleNames).append("\"");
		sb.append(",\"state\":").append(state);
		sb.append(",\"entState\":").append(entState);
		sb.append(",\"stateStr\":\"").append(getUserEntStateStr()).append("\"");
		sb.append(",\"entId\":").append(enterpriseId);
		sb.append(",\"entDomain\":\"").append(entDomain).append("\"");
		sb.append(",\"entName\":\"").append(enterpriseName).append("\"");
		sb.append(",\"domain\":\"").append(domain).append("\"");
		sb.append(",\"signature\":\"").append(signature == null? "" : StringUtil.replaceHtml(StringUtil.fixJsonStr(signature))).append("\"");
		sb.append(",\"signatureEncode\":\"").append(StringUtil.fixJsonStr(getSignatureEncode())).append("\"");
		sb.append(",\"roleNames\":\"").append(StringUtil.fixJsonStr(roleNames)).append("\"");
		sb.append(",\"salemanName\":\"").append(salemanName).append("\"");
		sb.append(",\"agentId\":").append(agentId);
		sb.append(",\"agentName\":\"").append(agentName).append("\"");
		sb.append(",\"agentEntName\":\"").append(agentEntName).append("\"");
		sb.append(",\"saleType\":\"").append(entParentId == 0 ? "直销" : "分销").append("\"");
		sb.append(",\"createTime\":\"").append(DateUtil.format(createTime, DateTimeType.DateTime)).append("\"");
		sb.append('}');
		return sb.toString();
	}
}
