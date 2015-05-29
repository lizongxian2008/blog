package com.xuanwu.web.common.entity;

/**
 * @Description 动态参数
 * @author <a href="mailto:liushuaiying@139130.net">Shuaiying.Liu</a>
 * @Data 2012-9-18
 * @Version 1.0.0
 */
public class DynamicParam {

	/** 排序字段 */
	private String orderField;

	/** 是否降序 */
	private boolean isDesc;

	/** 分页类 */
	private PageInfo pi;

	/** 扩展参数1 */
	private Object ext;

	/** 扩展参数2 */
	private Object ext1;

	/** 扩展参数3 */
	private Object ext2;

	/** 扩展参数4 */
	private Object ext3;

	private boolean notIn;

	public String getOrderField() {
		return orderField;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}

	public boolean isDesc() {
		return isDesc;
	}

	public void setDesc(boolean isDesc) {
		this.isDesc = isDesc;
	}

	public PageInfo getPi() {
		return pi;
	}

	public void setPi(PageInfo pi) {
		this.pi = pi;
	}

	public Object getExt() {
		return ext;
	}

	public void setExt(Object ext) {
		this.ext = ext;
	}

	public Object getExt1() {
		return ext1;
	}

	public void setExt1(Object ext1) {
		this.ext1 = ext1;
	}

	public Object getExt2() {
		return ext2;
	}

	public void setExt2(Object ext2) {
		this.ext2 = ext2;
	}

	public Object getExt3() {
		return ext3;
	}

	public void setExt3(Object ext3) {
		this.ext3 = ext3;
	}

	public boolean isNotIn() {
		return notIn;
	}

	public void setNotIn(boolean notIn) {
		this.notIn = notIn;
	}
}
