package com.xuanwu.web.common.dao.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xuanwu.web.common.entity.SystemLog;

public interface SysLogMapper {
	/**
	 * 查询用户日志
	 * 
	 * @param offset
	 * @param reqNum
	 * @param userName用户名
	 * @param operationType操作类型
	 * @param operationObj操作对象
	 * @param startTime开始时间
	 * @param endTime结束时间
	 * @return
	 */
	public List<SystemLog> findSysLog(@Param(value = "offset") int offset,
			@Param(value = "reqNum") int reqNum,
			@Param(value = "userName") String userName,
			@Param(value = "operationType") String operationType,
			@Param(value = "operationObj") String operationObj,
			@Param(value = "startTime") Date startTime,
			@Param(value = "endTime") Date endTime,
			@Param(value = "startTimeStr") String startTimeStr,
			@Param(value = "endTimeStr") String endTimeStr,
			@Param(value = "enterpriseId") int enterpriseId);

	/**
	 * 查询用户日志记录数
	 * 
	 * @param userName用户名
	 * @param operationType操作类型
	 * @param operationObj操作对象
	 * @param startTime开始时间
	 * @param endTime结束时间
	 * @return
	 */
	public int findSysLogCount(@Param(value = "userName") String userName,
			@Param(value = "operationType") String operationType,
			@Param(value = "operationObj") String operationObj,
			@Param(value = "startTime") Date startTime,
			@Param(value = "endTime") Date endTime,
			@Param(value = "enterpriseId") int enterpriseId);

	/**
	 * 查看日志详情
	 * 
	 * @param sysLogId
	 * @return
	 */
	public SystemLog findsysLogById(@Param(value = "sysLogId") int sysLogId);

	/**
	 * 查询用户最后登录记录
	 * 
	 * @param userId
	 * @return
	 * */
	public SystemLog findLastLoginLogByUserId(
			@Param(value = "userId") int userId);

	public int addSysLog(SystemLog sysLog);

	public SystemLog findEntSysLogByEnterpriseId(
			@Param(value = "enterpriseId") int enterpriseId);
}
