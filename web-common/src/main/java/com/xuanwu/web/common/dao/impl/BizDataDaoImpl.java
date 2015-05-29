package com.xuanwu.web.common.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xuanwu.web.common.config.Config;
import com.xuanwu.web.common.dao.O2ODao;
import com.xuanwu.web.common.dao.mapper.BizDataMapper;
import com.xuanwu.web.common.entity.ConfigRecord;
import com.xuanwu.web.common.entity.Permission;
import com.xuanwu.web.common.entity.RolePermission;
import com.xuanwu.web.common.service.BizDataService.SyncVersion;

@Component
public class BizDataDaoImpl extends O2ODao {

	private Config config;
	
	public List<Permission> findAllPermissions(){
		SqlSession session = sessionFactory.openSession();
		try{
			return session.getMapper(BizDataMapper.class).findAllPermissions(config.getPlatformId());
		} finally {
			session.close();
		}
	}
	
	public List<RolePermission> findAllRolePermissions(){
		SqlSession session = sessionFactory.openSession();
		try{
			return session.getMapper(BizDataMapper.class).findAllRolePermissions();
		} finally {
			session.close();
		}
	}
	
	public int findSyncVersion(SyncVersion type){
		SqlSession session = sessionFactory.openSession();
		try{
			return session.getMapper(BizDataMapper.class).findSyncVersion(type);
		} finally {
			session.close();
		}
	}
	
	public List<ConfigRecord> findAllConfigs(){
		SqlSession session = sessionFactory.openSession();
		try{
			return session.getMapper(BizDataMapper.class).findAllConfigs();
		} finally {
			session.close();
		}
	}
	
	public int updateConfig(ConfigRecord config){
		SqlSession session = sessionFactory.openSession();
		try{
			int ret = session.getMapper(BizDataMapper.class).updateConfig(config);
			session.commit();
			return ret;
		} finally {
			session.close();
		}
	}
	
	@Autowired
	public void setConfig(Config config) {
		this.config = config;
	}
}
