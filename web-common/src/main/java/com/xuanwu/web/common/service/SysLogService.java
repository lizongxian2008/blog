package com.xuanwu.web.common.service;

import java.util.Date;
import java.util.List;

import com.xuanwu.web.common.entity.SystemLog;

public interface SysLogService {
	/**
	 * 查询用户日志
	 */
	public List<SystemLog> findSysLog(int offset, int reqNum, String userName,
			String operationType, String operationObj, Date startTime, Date endTime,int enterpriseId);

	/**
	 * 查询用户日志记录数
	 */
	public int findSysLogCount(String userName, String operationType,
			String operationObj, Date startTime, Date endTime,int enterpriseId);

	/**
	 * 查看日志详细
	 * 
	 * @param sysLogId
	 * @return
	 */
	public SystemLog findsysLogById(int sysLogId);

	/**
	 * 查询用户最后登录记录
	 * 
	 * @param userId
	 * @return
	 */
	public SystemLog findLastLoginLogByUserId(int userId);

	public boolean addSysLog(SystemLog sysLog);

	/**
	 * 查找最新企业的操作记录
	 * 
	 * @param sysLogId
	 * @return
	 */
	public SystemLog findEntSysLogByEnterpriseId(int enterpriseId);

}
