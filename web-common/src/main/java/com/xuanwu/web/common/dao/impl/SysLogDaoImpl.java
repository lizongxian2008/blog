package com.xuanwu.web.common.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import com.xuanwu.web.common.dao.O2ODao;
import com.xuanwu.web.common.dao.mapper.SysLogMapper;
import com.xuanwu.web.common.entity.SystemLog;

@Component
public class SysLogDaoImpl extends O2ODao {

	public List<SystemLog> findSysLog(int offset, int reqNum, String userName,
			String operationType, String operationObj, Date startTime,
			Date endTime, String startTimeStr, String endTimeStr,int enterpriseId) {
		SqlSession session = sessionFactory.openSession(true);
		try {
			return session.getMapper(SysLogMapper.class).findSysLog(offset,
					reqNum, userName, operationType, operationObj, startTime,
					endTime, startTimeStr, endTimeStr, enterpriseId);
		} finally {
			session.close();
		}
	}

	public int findSysLogCount(String userName, String operationType,
			String operationObj, Date startTime, Date endTime,int enterpriseId) {
		SqlSession session = sessionFactory.openSession();
		try {
			return session.getMapper(SysLogMapper.class).findSysLogCount(
					userName, operationType, operationObj, startTime, endTime,enterpriseId);
		} finally {
			session.close();
		}
	}

	public SystemLog findsysLogById(int sysLogId) {
		SqlSession session = sessionFactory.openSession();
		try {
			return session.getMapper(SysLogMapper.class).findsysLogById(
					sysLogId);
		} finally {
			session.close();
		}
	}

	public SystemLog findLastLoginLogByUserId(int userId) {
		SqlSession session = sessionFactory.openSession();
		try {
			return session.getMapper(SysLogMapper.class)
					.findLastLoginLogByUserId(userId);
		} finally {
			session.close();
		}
	}

	public void addSysLogs(List<SystemLog> sysLogs) {
		SqlSession session = sessionFactory.openSession(ExecutorType.BATCH);
		try {
			SysLogMapper mapper = session.getMapper(SysLogMapper.class);
			for (SystemLog sysLog : sysLogs) {
				mapper.addSysLog(sysLog);
			}
			session.commit();
		} finally {
			session.close();
		}
	}

	public SystemLog findEntSysLogByEnterpriseId(int enterpriseId) {
		SqlSession session = sessionFactory.openSession();
		try {
			return session.getMapper(SysLogMapper.class)
					.findEntSysLogByEnterpriseId(enterpriseId);
		} finally {
			session.close();
		}
	}
}
