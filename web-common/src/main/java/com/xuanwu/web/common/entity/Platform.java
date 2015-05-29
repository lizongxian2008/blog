package com.xuanwu.web.common.entity;

/**
 * @Description 平台
 * @author <a href="mailto:liushuaiying@139130.net">Shuaiying.Liu</a>
 * @Data 2012-9-5
 * @Version 1.0.0
 */
public enum Platform {
	/** 后端 */
	Backend(0),
	
	///** UMP */
	//UMP(1),
	
	/** 前端 */
	Frontkit(2),
	
	/** 其它 */
	Other(3);
	
	private int index;
	private Platform(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}
	
	public static Platform getType(int index){
		switch(index){
		case 0: return Backend;
		//case 1: return UMP;
		case 2: return Frontkit;
		case 3: return Other;
		default:
			return Other;
		}
	}
}
