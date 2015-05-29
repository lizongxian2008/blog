package com.xuanwu.web.common.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xuanwu.web.common.cache.SyncTask;
import com.xuanwu.web.common.dao.impl.SysLogDaoImpl;
import com.xuanwu.web.common.entity.SystemLog;
import com.xuanwu.web.common.service.SysLogService;
import com.xuanwu.web.common.utils.DateUtil;
import com.xuanwu.web.common.utils.DateUtil.DateTimeType;
import com.xuanwu.web.common.utils.ListUtil;

@Component("sysLogService")
public class DefaultSysLogService implements SysLogService, SyncTask {

	private static final Logger logger = LoggerFactory
			.getLogger(DefaultSysLogService.class);

	private LinkedBlockingDeque<SystemLog> addQueue = new LinkedBlockingDeque<SystemLog>();

	private SysLogDaoImpl dao;

	public List<SystemLog> findSysLog(int offset, int reqNum, String userName,
			String operationType, String operationObj, Date startTime, Date endTime,int enterpriseId) {
		String startTimeStr = DateUtil.format(startTime, DateTimeType.DateTime);
		String endTimeStr = DateUtil.format(endTime, DateTimeType.DateTime);
		String operType = StringUtils.isBlank(operationType) ? null : operationType;
		return dao.findSysLog(offset, reqNum, userName, operType,
				operationObj, startTime, endTime, startTimeStr, endTimeStr,enterpriseId);
	}

	public int findSysLogCount(String userName, String operationType,
			String operationObj, Date startTime, Date endTime,int enterpriseId) {
		String operType = StringUtils.isBlank(operationType) ? null : operationType;
		return dao.findSysLogCount(userName, operType, operationObj,
				startTime, endTime,enterpriseId);
	}

	@Override
	public SystemLog findEntSysLogByEnterpriseId(int enterpriseId) {
		return dao.findEntSysLogByEnterpriseId(enterpriseId);
	}

	@Autowired
	public void setDao(SysLogDaoImpl dao) {
		this.dao = dao;
	}

	@Override
	public SystemLog findsysLogById(int sysLogId) {
		return dao.findsysLogById(sysLogId);
	}

	@Override
	public SystemLog findLastLoginLogByUserId(int userId) {
		return dao.findLastLoginLogByUserId(userId);
	}

	@Override
	public boolean addSysLog(SystemLog sysLog) {
		return addQueue.add(sysLog);
	}

	@Override
	public void run() {
		try {
			if (addQueue.isEmpty()) {
				return;
			}
			List<SystemLog> logs = new ArrayList<SystemLog>();
			addQueue.drainTo(logs);
			List<List<SystemLog>> subs = ListUtil.splitList(logs, 1000);
			for (List<SystemLog> sub : subs) {
				dao.addSysLogs(sub);
			}
		} catch (Exception e) {
			logger.error("Store system log failed: ", e);
		}
	}

	@Override
	public long getPeriod() {
		return 5000;
	}

}
