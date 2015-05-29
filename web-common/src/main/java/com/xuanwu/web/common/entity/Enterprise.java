package com.xuanwu.web.common.entity;

import java.util.Date;

import com.xuanwu.web.common.utils.DateUtil;
import com.xuanwu.web.common.utils.DateUtil.DateTimeType;
import com.xuanwu.web.common.utils.StringUtil;

/**
 * @Description 企业
 * @author <a href="mailto:liushuaiying@139130.net">Shuaiying.Liu</a>
 * @Data 2012-9-6
 * @Version 1.0.0
 */
public class Enterprise extends User {

	/** 0：预付费，1:后付费 */
	private int paymentType;

	/** 企业下的用户的默认初始密码 */
	private String initPassword;

	/** 是否需要审核,0-不审核 */
	private Boolean auditingFlag;

	/** 审核基数 */
	private int auditingNum;

	/** 是否启用黑名单过滤,1--启用,0--禁用 */
	private boolean enableBlackListFilter;

	/** 是否启用关键字过滤,1--启用,0--禁用 */
	private boolean enableKeywordFilter;

	/** 标识企业在分配端口时是否允许企业可以自定义扩展 */
	private String extend;

	/** 标识企业在分配端口时是否允许企业可以切换通道 */
	private boolean shift;

	/** 标识该企业下的用户是否发送过信息 该字段只更新一次 */
	private boolean hasSendMessage;

	/** 是否需要彩信审核,0-不审核 */
	private Boolean auditingMmsFlag;

	/** 彩信审核基数 */
	private int auditingMmsNum;

	/** 是否启用彩信模块 */
	private boolean enableMms;

	/** 行业ID */
	private int industryId;

	/** 行业名称 */
	private String industryName;

	/** 地区ID */
	private int regionId;

	/** 地区名称 */
	private String regionName;

	/** 所属销售 */
	private int salemanId;

	/** 所属销售账号 **/
	protected String salemanName;

	/** 是否显示端口号 1--是, 0--否 */
	private boolean showSpecnumFlag;

	/** 是否显示状态报告1--是, 0--否 */
	private boolean showStatereportFlag;

	/** 工号 */
	private String jobNumber;

	/** 是否启用客户审核,0--否，1--是 */
	private boolean auditingCustomFlag;

	/** 企业扣费类型: 0--按用户提交量计费、1--按系统发送量计费、2--按信息送达量计费 */
	private int billingType;

	private boolean hasChild;
	/** 是否需要素材审核,0-不审核 */
	private boolean auditingMaterialFlag;

	/** 审核状态 **/
	protected int auditingState;

	/** 短信单价 单位：分 */
	private double smsPrice;

	/** 创建人 */
	private int createUser;

	/** 创建人名称 */
	private String createUserName;

	/** 更新人 */
	private int modifyUser;

	/** 更新人名字 */
	private String modifyUserName;

	/** 更新时间 */
	private Date modifyTime;

	/** 默认上行用户 */
	private int moUser;

	/** 余额提醒阀值 */
	private float balanceRemain;

	/** 指令错误回复 */
	private String cmdErrorReply;

	/** 指令标记符（分隔符） */
	private String cmdToken;

	/** 指令子标记符（分隔符） */
	private String cmdSubToken;

	/** 生成JSON TREE时使用 */
	protected boolean forTree = false;

	/** 生成审核列表时使用 **/
	protected boolean forAudit = false;

	/** 生成账号管理列表时使用 **/
	protected boolean forChargePayment = false;

	/** 查询时是否需要查找对应的审核申请表 */
	protected boolean needAuditApply = false;

	protected boolean needAccount;

	// 已到账总额 界面 当前企业已生效的到账金额之和
	private double hadPaymentCount;

	// 待到账总额 界面 待到账总额=∑充值+∑转入-∑撤销-∑转出-∑已到账 (均为已生效的支出及到账)
	private double waitPaymentCount;

	public boolean isAuditingMaterialFlag() {
		return auditingMaterialFlag;
	}

	public void setAuditingMaterialFlag(boolean auditingMaterialFlag) {
		this.auditingMaterialFlag = auditingMaterialFlag;
	}

	public int getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(int paymentType) {
		this.paymentType = paymentType;
	}

	public String getInitPassword() {
		return initPassword;
	}

	public void setInitPassword(String initPassword) {
		this.initPassword = initPassword;
	}

	public Boolean getAuditingFlag() {
		return auditingFlag;
	}

	public void setAuditingFlag(Boolean auditingFlag) {
		this.auditingFlag = auditingFlag;
	}

	public int getAuditingNum() {
		return auditingNum;
	}

	public void setAuditingNum(int auditingNum) {
		this.auditingNum = auditingNum;
	}

	public String getExtend() {
		return extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}

	public boolean isShift() {
		return shift;
	}

	public void setShift(boolean shift) {
		this.shift = shift;
	}

	public boolean isHasSendMessage() {
		return hasSendMessage;
	}

	public void setHasSendMessage(boolean hasSendMessage) {
		this.hasSendMessage = hasSendMessage;
	}

	public Boolean getAuditingMmsFlag() {
		return auditingMmsFlag;
	}

	public void setAuditingMmsFlag(Boolean auditingMmsFlag) {
		this.auditingMmsFlag = auditingMmsFlag;
	}

	public int getAuditingMmsNum() {
		return auditingMmsNum;
	}

	public void setAuditingMmsNum(int auditingMmsNum) {
		this.auditingMmsNum = auditingMmsNum;
	}

	public boolean isEnableMms() {
		return enableMms;
	}

	public void setEnableMms(boolean enableMms) {
		this.enableMms = enableMms;
	}

	public int getIndustryId() {
		return industryId;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryId(int industryId) {
		this.industryId = industryId;
	}

	public int getRegionId() {
		return regionId;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}

	public int getSalemanId() {
		return salemanId;
	}

	public void setSalemanId(int salemanId) {
		this.salemanId = salemanId;
	}

	public boolean isShowSpecnumFlag() {
		return showSpecnumFlag;
	}

	public void setShowSpecnumFlag(boolean showSpecnumFlag) {
		this.showSpecnumFlag = showSpecnumFlag;
	}

	public boolean isShowStatereportFlag() {
		return showStatereportFlag;
	}

	public void setShowStatereportFlag(boolean showStatereportFlag) {
		this.showStatereportFlag = showStatereportFlag;
	}

	public String getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}

	public boolean isAuditingCustomFlag() {
		return auditingCustomFlag;
	}

	public void setAuditingCustomFlag(boolean auditingCustomFlag) {
		this.auditingCustomFlag = auditingCustomFlag;
	}

	public int getBillingType() {
		return billingType;
	}

	public void setBillingType(int billingType) {
		this.billingType = billingType;
	}

	public void setForAudit(boolean forAudit) {
		this.forAudit = forAudit;
	}

	public void setNeedAuditApply(boolean needAuditApply) {
		this.needAuditApply = needAuditApply;
	}

	public void setForChargePayment(boolean forChargePayment) {
		this.forChargePayment = forChargePayment;
	}

	public boolean isForChargePayment() {
		return forChargePayment;
	}

	public String getCmdErrorReply() {
		return cmdErrorReply;
	}

	public void setCmdErrorReply(String cmdErrorReply) {
		this.cmdErrorReply = cmdErrorReply;
	}

	public String getCmdToken() {
		return cmdToken;
	}

	public void setCmdToken(String cmdToken) {
		this.cmdToken = cmdToken;
	}

	public String getCmdSubToken() {
		return cmdSubToken;
	}

	public void setCmdSubToken(String cmdSubToken) {
		this.cmdSubToken = cmdSubToken;
	}

	@Override
	public String getFullPath() {
		return this.path;
	}

	@Override
	public UserType getType() {
		return UserType.Enterprise;
	}

	@Override
	public boolean hasChild() {
		return hasChild;
	}

	@Override
	public void setHasChild(boolean hasChild) {
		this.hasChild = hasChild;
	}

	public boolean isEnableBlackListFilter() {
		return enableBlackListFilter;
	}

	public void setEnableBlackListFilter(boolean enableBlackListFilter) {
		this.enableBlackListFilter = enableBlackListFilter;
	}

	public boolean isEnableKeywordFilter() {
		return enableKeywordFilter;
	}

	public void setEnableKeywordFilter(boolean enableKeywordFilter) {
		this.enableKeywordFilter = enableKeywordFilter;
	}

	public String getSalemanName() {
		return salemanName;
	}

	public void setSalemanName(String salemanName) {
		this.salemanName = salemanName;
	}


	public int getAuditingState() {
		return auditingState;
	}

	// 0--待审核，1--已通过，2--未通过
	public String getAuditingStateName() {
		if (auditingState == 0) {
			return "待审核";
		} else if (auditingState == 1) {
			// 如果是初始还未修改，默认为空
			return "修改通过";
		} else if (auditingState == 2) {
			return "修改不通过";
		} else {
			return null;
		}
	}

	public void setAuditingState(int auditingState) {
		this.auditingState = auditingState;
	}

	public double getSmsPrice() {
		return smsPrice;
	}

	public void setSmsPrice(double smsPrice) {
		this.smsPrice = smsPrice;
	}

	public void setForTree(boolean forTree) {
		this.forTree = forTree;
	}

	public int getCreateUser() {
		return createUser;
	}

	public void setCreateUser(int createUser) {
		this.createUser = createUser;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public int getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(int modifyUser) {
		this.modifyUser = modifyUser;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getModifyUserName() {
		return modifyUserName;
	}

	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	public int getMoUser() {
		return moUser;
	}

	public void setMoUser(int moUser) {
		this.moUser = moUser;
	}

	public float getBalanceRemain() {
		return balanceRemain;
	}

	public void setBalanceRemain(float balanceRemain) {
		this.balanceRemain = balanceRemain;
	}

	// 审核状态: 0--待审核，1--已通过，2--未通过
	public enum AuditState {
		Auditing(0), AuditPass(1), AuditNotPass(2);

		private int index;

		private AuditState(int index) {
			this.index = index;
		}

		public int getIndex() {
			return index;
		}

		public static AuditState getAuditState(int index) {
			switch (index) {
			case 0:
				return Auditing;
			case 1:
				return AuditPass;
			case 2:
				return AuditNotPass;
			default:
				return AuditNotPass;
			}
		}
	}

	public boolean isForAudit() {
		return forAudit;
	}

	public boolean isNeedAuditApply() {
		return needAuditApply;
	}

	// 销售方式
	public String getSaleType() {
		if (parentId == 0) {
			return "直销";
		} else {
			return "分销";
		}
	}

	public double getHadPaymentCount() {
		return hadPaymentCount;
	}

	public void setHadPaymentCount(double hadPaymentCount) {
		this.hadPaymentCount = hadPaymentCount;
	}

	public double getWaitPaymentCount() {
		return waitPaymentCount;
	}
	
	public String getWaitPaymentCountStr() {
		return StringUtil.fixDoubleStr(waitPaymentCount);
	}

	public void setWaitPaymentCount(double waitPaymentCount) {
		this.waitPaymentCount = waitPaymentCount;
	}

	// 是否分销
	public boolean isDistribution() {
		if (parentId != 0) {
			return true;
		}
		return false;
	}

	public String getStateStr() {
		String s = "";
		State _state = User.State.getType(state);
		switch (_state) {
		case Normal:
			s = "启用";
			break;
		case Suspend:
			s = "停用";
			break;
		default:
			break;
		}
		return s;
	}

	// 优先级别
	public String getPriorityName() {
		if (priority == 1) {
			return "高";
		} else if (priority == 2) {
			return "较高";
		} else if (priority == 3) {
			return "中";
		} else if (priority == 4) {
			return "低";
		} else {
			return String.valueOf(priority);
		}
	}

	public boolean isNeedAccount() {
		return needAccount;
	}

	public void setNeedAccount(boolean needAccount) {
		this.needAccount = needAccount;
	}

	@Override
	public String toJSON() {
		StringBuilder sb = new StringBuilder();
		if (forTree) {
			sb.append("{\"id\":").append(id);
			sb.append(",\"pId\":").append(0);
			sb.append(",\"type\":").append(UserType.Enterprise.getIndex());
			sb.append(",\"name\":\"")
					.append(StringUtil.fixJsonStr(enterpriseName)).append('\"');
			sb.append(",\"path\":\"").append(path == null ? "" : path)
					.append('\"').append('}');
			return sb.toString();
		}
		if (forAudit) {
			return toAuditJSON();
		}
		if (forChargePayment) {
			return toChargePaymentJSON();
		}
		sb.append("{\"id\":").append(id);
		sb.append(",\"parentId\":").append(parentId);
		sb.append(",\"auditingFlag\":").append(auditingFlag);
		sb.append(",\"enterpriseId\":").append(enterpriseId);
		sb.append(",\"domain\":\"").append(StringUtil.fixJsonStr(domain))
				.append('\"');
		sb.append(",\"entName\":\"")
				.append(StringUtil.fixJsonStr(enterpriseName)).append('\"');
		sb.append(",\"saleman\":\"").append(StringUtil.fixJsonStr(salemanName))
				.append('\"');
		sb.append(",\"agent\":\"").append(StringUtil.fixJsonStr(parentName))
				.append('\"');
		sb.append(",\"modifyUserName\":\"").append(modifyUserName).append('\"');
		sb.append(",\"modifyUser\":").append(modifyUser);
		sb.append(",\"saletype\":\"").append(parentId == 0 ? "直销" : "分销")
				.append('\"');
		sb.append(",\"state\":").append(state);
		sb.append(",\"createTime\":\"")
				.append(DateUtil.format(createTime, DateTimeType.DateTime))
				.append('\"');
		sb.append(",\"auditingState\":").append(auditingState);
		sb.append(",\"signature\":\"").append(signature).append("\"");
		sb.append(",\"auditResult\":\"").append(getAuditingStateName())
				.append('\"');
		sb.append('}');
		return sb.toString();
		// }
	}

	public String toChargePaymentJSON() {
		StringBuilder sb = new StringBuilder();
		sb.append("{\"id\":").append(id);
		sb.append(",\"parentId\":").append(parentId);
		sb.append(",\"type\":").append(UserType.Enterprise.getIndex());
		sb.append(",\"entName\":\"")
				.append(StringUtil.replaceHtml(enterpriseName)).append('\"');// 企业名称
		sb.append(",\"domain\":\"").append(StringUtil.fixJsonStr(domain))
				.append('\"'); // 企业账号
		sb.append(",\"saleman\":\"").append(StringUtil.fixJsonStr(salemanName))
				.append('\"'); // 所属销售
		sb.append(",\"createUserName\":\"").append(createUserName).append('\"');// 启用停用
		sb.append(",\"signature\":\"").append(signature).append("\"");
		sb.append(",\"state\":").append(state);// 启用停用
		sb.append(",\"hadPaymentCount\":").append('\"')
				.append(StringUtil.fixDoubleStr(hadPaymentCount)).append('\"');
		sb.append(",\"waitPaymentCount\":").append('\"')
				.append(StringUtil.fixDoubleStr(waitPaymentCount)).append('\"');
		sb.append('}');
		return sb.toString();
	}

	public String toAuditJSON() {
		StringBuilder sb = new StringBuilder();
		sb.append("{\"id\":").append(id);
		sb.append(",\"parentId\":").append(parentId);
		sb.append(",\"type\":").append(UserType.Enterprise.getIndex());
		sb.append(",\"entName\":\"")
				.append(StringUtil.replaceHtml(enterpriseName)).append('\"');// 企业名称
		sb.append(",\"domain\":\"").append(StringUtil.fixJsonStr(domain))
				.append('\"'); // 企业账号
		sb.append(",\"saleman\":\"").append(StringUtil.fixJsonStr(salemanName))
				.append('\"'); // 所属销售
		sb.append(",\"agent\":\"").append(StringUtil.fixJsonStr(parentName))
				.append('\"'); // 所属代理商
		sb.append(",\"createUserName\":\"").append(createUserName).append('\"');// 启用停用
		sb.append(",\"signature\":\"").append(signature).append("\"");
		sb.append(",\"state\":").append(state);// 启用停用
		sb.append(",\"path\":\"").append(path).append('\"').append('}');
		return sb.toString();
	}

	public String toJSONSimple() {
		StringBuilder sb = new StringBuilder();
		sb.append("{\"id\":").append(id);
		sb.append(",\"name\":\"").append(domain).append("\"");
		sb.append(",\"entName\":\"").append(enterpriseName).append("\"");
		sb.append(",\"signature\":\"").append(signature).append("\"");
		sb.append(",\"state\":").append(state);
		sb.append('}');
		return sb.toString();
	}
}
