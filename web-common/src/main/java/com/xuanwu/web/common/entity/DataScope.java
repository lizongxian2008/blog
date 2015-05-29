package com.xuanwu.web.common.entity;

/**
 * @Description 数据范围
 * @author <a href="mailto:liushuaiying@139130.net">Shuaiying.Liu</a>
 * @Data 2012-9-5
 * @Version 1.0.0
 */
public enum DataScope {
	
	/** 仅个人 */
	Personal(1), 
	
	/** 仅本部门及子部门 */
	DepartmentHierarchy(2),
	
	/** 全局 */
	Global(3),
	
	/** 不控制 */
	None(9);
	
	private int index;

	private DataScope(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public static DataScope getType(int index) {
		switch (index) {
		case 1:
			return Personal;
		case 2:
			return DepartmentHierarchy;
		case 3:
			return Global;
		case 9:
			return None;
		default:
			return None;
		}
	}
}
