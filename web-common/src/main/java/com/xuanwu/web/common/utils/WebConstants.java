/*   
 * Copyright (c) 2012 by XUANWU INFORMATION TECHNOLOGY CO. 
 *             All rights reserved                         
 */
package com.xuanwu.web.common.utils;

/**
 * @Description keys and other constants
 * @Author <a href="hw86xll@163.com">Wei.Huang</a>
 * @Date 2012-9-3
 * @Version 1.0.0
 */
public class WebConstants {

	/** 数据范围KEY */
	public static final String KEY_DATA_SCOPE = "_ds";

	/** 授权企业名 */
	public static final String KEY_ENT_NAME = "_entName";

	/** 授权有效期 */
	public static final String KEY_DEADLINE = "_deadline";

	/** 权限ID KEY */
	public static final String KEY_PERM_ID = "_permId";

	/** 操作对象 KEY，针对增、删、改、审、导入、导出等操作，记录日志用 */
	public static final String KEY_OP_OBJ = "_opObj";

	/** 当没有对应细分的权限时用，操作对象 KEY，针对增、删、改、审、导入、导出等操作，记录日志用 */
	public static final String KEY_OP_TYPE = "_opType";

	/** 导航栏KEY */
	public static final String KEY_LEFT_NAV = "_nav";

	/** 默认编码 */
	public static final String DEFAULT_CHARSET = "utf-8";

	/** 请求头状态 */
	public static final String HEADER_ACCESS_STATE = "Access-State";

	/** 登录用户KEY */
	public static final String KEY_USER = "_user";

	/** 全局配置KEY */
	public static final String KEY_CONFIG = "_config";

}
